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
		return variableBinding.getType().getQualifiedName() + "." + variableBinding.getName();
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
		if (expression.getNodeType() == ASTNode.TYPE_LITERAL) {
			ITypeBinding resolvedTypeBinding = ((TypeLiteral) expression).getType().resolveBinding();
			if (resolvedTypeBinding != null) {
				return resolvedTypeBinding.getQualifiedName();
			}
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

}
