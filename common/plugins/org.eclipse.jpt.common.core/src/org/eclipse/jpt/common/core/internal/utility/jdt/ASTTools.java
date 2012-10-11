/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.HashSet;
import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.common.core.internal.utility.SimpleTextRange;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.JavaType;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.SimpleJavaType;
import org.eclipse.jpt.common.utility.internal.SimpleMethodSignature;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;

/**
 * Convenience methods for dealing with JDT ASTs.
 */
public class ASTTools {

	/**
	 * Build an AST without method bodies for the specified compilation unit
	 * with its bindings resolved (and the resultant performance hit).
	 */
	public static CompilationUnit buildASTRoot(ICompilationUnit compilationUnit) {
		ASTParser parser = astParser();
		parser.setSource(compilationUnit);
		return (CompilationUnit) parser.createAST(null);
	}
	
	/**
	 * Create an {@link IBinding} for the specified {@link IMember}
	 * with its bindings resolved
	 */
	public static IBinding createBinding(IMember member) {
		ASTParser parser = astParser();
		parser.setProject(member.getJavaProject());
		return parser.createBindings(new IJavaElement[] { member }, null)[0];
	}
	
	private static ASTParser astParser() {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setIgnoreMethodBodies(true);  // we don't need method bodies
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);  // see bugs 196200, 222735
		return parser;
	}


	// ********** JDT DOM **********

	public static String resolveEnum(Expression expression) {
		if (expression == null) {
			return null;
		}
		switch (expression.getNodeType()) {
			case ASTNode.QUALIFIED_NAME:
			case ASTNode.SIMPLE_NAME:
				return resolveEnum((Name) expression);
			default:
				return null;
		}
	}

	public static String resolveEnum(Name enumExpression) {
		IBinding binding = enumExpression.resolveBinding();
		if (binding == null) {
			return null;  // TODO figure out why this is null sometimes
		}
		if (binding.getKind() != IBinding.VARIABLE) {
			return null;
		}
		IVariableBinding variableBinding = (IVariableBinding) binding;
		return variableBinding.getType().getQualifiedName() + '.' + variableBinding.getName();
	}

	/**
	 * If the specified expression is a type literal, return the type's fully-
	 * qualified name. Return null otherwise.
	 */
	public static String resolveFullyQualifiedName(Expression expression) {
		ITypeBinding resolvedTypeBinding = resolveTypeBinding(expression);
		return (resolvedTypeBinding == null) ? null : resolvedTypeBinding.getQualifiedName();
	}
	
	/**
	 * If the specified expression is an array initializer, return an an iterable
	 * on the types' fully qualfified names.  
	 * The results may include nulls.
	 */
	public static Iterable<String> resolveFullyQualifiedNames(Expression expression) {
		return new TransformationIterable<ITypeBinding, String>(resolveTypeBindings(expression)) {
			@Override
			protected String transform(ITypeBinding o) {
				return (o == null) ? null : o.getQualifiedName();
			}
		};
	}
	
	/**
	 * If the specified expression is a type literal, return the corresponding
	 * type binding.
	 */
	public static ITypeBinding resolveTypeBinding(Expression expression) {
		if (expression != null && expression.getNodeType() == ASTNode.TYPE_LITERAL) {
			return ((TypeLiteral) expression).getType().resolveBinding();
		}
		return null;
	}
	
	/**
	 * If the specified expression is an array initializer, return an iterable of
	 * the corresponding type bindings for each sub-expression.
	 * The result may include <code>null</code> elements.
	 */
	public static Iterable<ITypeBinding> resolveTypeBindings(Expression expression) {
		return (expression != null && expression.getNodeType() == ASTNode.ARRAY_INITIALIZER) ?
				resolveTypeBindings((ArrayInitializer) expression) :
				EmptyIterable.<ITypeBinding>instance();
	}
	
	private static Iterable<ITypeBinding> resolveTypeBindings(ArrayInitializer arrayExpression) {
		@SuppressWarnings("unchecked")
		Iterable<Expression> expressions = arrayExpression.expressions();
		return new TransformationIterable<Expression, ITypeBinding>(expressions) {
			@Override
			protected ITypeBinding transform(Expression expression) {
				return resolveTypeBinding(expression);
			}
		};
	}
	
	public static MethodSignature buildMethodSignature(MethodDeclaration methodDeclaration) {
		return new SimpleMethodSignature(
				methodDeclaration.getName().getFullyQualifiedName(),
				buildParameterTypes(methodDeclaration)
			);
	}

	public static JavaType[] buildParameterTypes(MethodDeclaration methodDeclaration) {
		List<SingleVariableDeclaration> parameters = parameters(methodDeclaration);
		int len = parameters.size();
		JavaType[] parameterTypes = new JavaType[len];
		for (int i = 0; i < len; i++) {
			ITypeBinding type = parameters.get(i).getType().resolveBinding();
			parameterTypes[i] = SimpleJavaType.buildSimpleJavaType(type.getQualifiedName(), type.getDimensions());
		}
		return parameterTypes;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private static List<SingleVariableDeclaration> parameters(MethodDeclaration methodDeclaration) {
		return methodDeclaration.parameters();
	}

	/**
	 * Return whether a type binding with the specified type name exists in
	 * the specified type binding's inheritance hierarchy (superclasses
	 * and interfaces).
	 */
	public static boolean typeIsSubTypeOf(ITypeBinding typeBinding, String searchTypeName) {
		return findTypeInHierarchy(typeBinding, searchTypeName) != null;
	}

	/**
	 * Return the type binding corresponding to the specified type name if it
	 * exists in the specified type binding's inheritance hierarchy (superclasses
	 * and interfaces). Return null otherwise.
	 */
	public static ITypeBinding findTypeInHierarchy(ITypeBinding typeBinding, String searchTypeName) {
		return findTypeInHierarchy(typeBinding, searchTypeName, new HashSet<String>());
	}

	private static ITypeBinding findTypeInHierarchy(ITypeBinding typeBinding, String searchTypeName, HashSet<String> visited) {
		String typeName = typeBinding.getTypeDeclaration().getQualifiedName();
		if (visited.contains(typeName)) {
			return null;
		}
		if (typeName.equals(searchTypeName)) {
			return typeBinding;
		}
		visited.add(typeName);
		
		ITypeBinding interfaceBinding = findTypeInInterfaces(typeBinding, searchTypeName, visited);
		if (interfaceBinding != null) {
			return interfaceBinding;
		}
		
		return findTypeInSuperclasses(typeBinding, searchTypeName, visited);
	}

	private static ITypeBinding findTypeInInterfaces(ITypeBinding typeBinding, String searchTypeName, HashSet<String> visited) {
		ITypeBinding[] interfaceBindings = typeBinding.getInterfaces();
		for (ITypeBinding interfaceBinding : interfaceBindings) {  // recurse up interfaces
			ITypeBinding result = findTypeInHierarchy(interfaceBinding, searchTypeName, visited);
			if (result != null) {
 				return result;
			}
		}
		return null;
	}
	
	private static ITypeBinding findTypeInSuperclasses(ITypeBinding typeBinding, String searchTypeName, HashSet<String> visited) {
		ITypeBinding superBinding = typeBinding.getSuperclass();
		if (superBinding != null) {  // recurse up superclasses
			ITypeBinding result = findTypeInHierarchy(superBinding, searchTypeName, visited);
			if (result != null) {
				return result;
			}
		}
		return null;		
	}

	/**
	 * Return whether a type binding with the specified interface name exists in
	 * the specified type binding's inheritance hierarchy (superclasses
	 * and interfaces).
	 */
	public static boolean typeImplementsInterface(ITypeBinding typeBinding, String searchInterfaceName) {
		return findInterfaceInHierarchy(typeBinding, searchInterfaceName) != null;
	}
	
	private static ITypeBinding findInterfaceInHierarchy(ITypeBinding typeBinding, String searchInterfaceName) {
		HashSet<String> visited = new HashSet<String>();
		ITypeBinding interfaceBinding = findTypeInInterfaces(typeBinding, searchInterfaceName, visited);
		if (interfaceBinding != null) {
			return interfaceBinding;
		}
		
		return findTypeInSuperclasses(typeBinding, searchInterfaceName, visited);

	}

	/**
	 * Build and return a text range for the specified AST node.
	 */
	public static TextRange buildTextRange(ASTNode astNode) {
		return buildTextRange(astNode, null);
	}

	/**
	 * Build and return a text range for the specified AST node if it differs
	 * from the specified text range or the specified text range is
	 * <code>null</code>. If the AST node already matches the
	 * specified text range, simply return the text range unchanged.
	 */
	public static TextRange buildTextRange(ASTNode astNode, TextRange textRange) {
		int offset = astNode.getStartPosition();
		int length = astNode.getLength();
		int lineNumber = ((CompilationUnit) astNode.getRoot()).getLineNumber(offset);
		return (textRange == null) ?
				new SimpleTextRange(offset, length, lineNumber) :
				textRange.buildTextRange(offset, length, lineNumber);
	}
}
