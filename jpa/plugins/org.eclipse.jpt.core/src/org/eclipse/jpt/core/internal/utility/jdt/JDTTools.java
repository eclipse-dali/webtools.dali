/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.utility.JavaType;
import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.SimpleJavaType;
import org.eclipse.jpt.utility.internal.SimpleMethodSignature;

public class JDTTools {

	/**
	 * Build an AST for the specified compilation unit with its bindings
	 * resolved (and the resultant performance hit).
	 */
	public static CompilationUnit buildASTRoot(ICompilationUnit compilationUnit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(compilationUnit);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);  // see bugs 196200, 222735
		return (CompilationUnit) parser.createAST(null);
	}

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
			return null;  // TODO figure why this is null sometimes
		}
		if (binding.getKind() != IBinding.VARIABLE) {
			return null;
		}
		IVariableBinding variableBinding = (IVariableBinding) binding;
		return variableBinding.getType().getQualifiedName() + '.' + variableBinding.getName();
	}
	
	public static String resolveAnnotation(Annotation node) {
		IAnnotationBinding annotationBinding = node.resolveAnnotationBinding();
		if (annotationBinding == null) {
			return null;
		}
		ITypeBinding annotationTypeBinding = annotationBinding.getAnnotationType();
		if (annotationTypeBinding == null) {
			return null;
		}
		return annotationTypeBinding.getQualifiedName();
	}
	
	public static String resolveFullyQualifiedName(Expression expression) {
		ITypeBinding resolvedTypeBinding = resolveTypeBinding(expression);
		if (resolvedTypeBinding != null) {
			return resolvedTypeBinding.getQualifiedName();
		}
		return null;
	}
	
	public static ITypeBinding resolveTypeBinding(Expression expression) {
		if (expression.getNodeType() == ASTNode.TYPE_LITERAL) {
			return ((TypeLiteral) expression).getType().resolveBinding();
		}
		return null;
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
			parameterTypes[i] = new SimpleJavaType(type.getQualifiedName(), type.getDimensions());
		}
		return parameterTypes;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private static List<SingleVariableDeclaration> parameters(MethodDeclaration methodDeclaration) {
		return methodDeclaration.parameters();
	}
	
	/**
	 * Given an Expression return the ITypeBinding for the fullyQualifiedTypeName if it 
	 * exists in the type hierarchy.  The expression should be a TypeLiteral or this will
	 * just return null.
	 */
	public static ITypeBinding findTypeInHierarchy(Expression expression, String fullyQualifiedTypeName) {
		ITypeBinding typeBinding = resolveTypeBinding(expression);
		if (typeBinding != null) {
			return findTypeInHierarchy(typeBinding, fullyQualifiedTypeName);
		}
		return null;
	}
	
	/**
	 * Finds a type binding for a given fully qualified type in the hierarchy of a type.
	 * Returns <code>null</code> if no type binding is found.
	 * @param hierarchyType the binding representing the hierarchy
	 * @param fullyQualifiedTypeName the fully qualified name to search for
	 * @return the type binding
	 */
	//copied from org.eclipse.jdt.internal.corext.dom.Bindings
	public static ITypeBinding findTypeInHierarchy(ITypeBinding hierarchyType, String fullyQualifiedTypeName) {
		if (hierarchyType.isArray() || hierarchyType.isPrimitive()) {
			return null;
		}
		if (fullyQualifiedTypeName.equals(hierarchyType.getQualifiedName())) {
			return hierarchyType;
		}
		ITypeBinding superClass= hierarchyType.getSuperclass();
		if (superClass != null) {
			ITypeBinding res= findTypeInHierarchy(superClass, fullyQualifiedTypeName);
			if (res != null) {
				return res;
			}
		}
		ITypeBinding[] superInterfaces= hierarchyType.getInterfaces();
		for (int i= 0; i < superInterfaces.length; i++) {
			ITypeBinding res= findTypeInHierarchy(superInterfaces[i], fullyQualifiedTypeName);
			if (res != null) {
				return res;
			}			
		}
		return null;
	}

}
