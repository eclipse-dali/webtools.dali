/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import java.util.List;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

//copied from org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil
//deleted code to limit the number of classes I had to copy
public class ASTNodeSearchUtil {

	private ASTNodeSearchUtil() {
		//no instance
	}


	public static MethodDeclaration getMethodDeclarationNode(IMethod iMethod, CompilationUnit cuNode) throws JavaModelException {
		return (MethodDeclaration)ASTNodes.getParent(getNameNode(iMethod, cuNode), MethodDeclaration.class);
	}
	
	public static ASTNode getParent(ASTNode node, Class<?> parentClass) {
		do {
			node= node.getParent();
		} while (node != null && !parentClass.isInstance(node));
		return node;
	}
	

	public static AnnotationTypeMemberDeclaration getAnnotationTypeMemberDeclarationNode(IMethod iMethod, CompilationUnit cuNode) throws JavaModelException {
		return (AnnotationTypeMemberDeclaration) ASTNodes.getParent(getNameNode(iMethod, cuNode), AnnotationTypeMemberDeclaration.class);
	}

	public static VariableDeclarationFragment getFieldDeclarationFragmentNode(IField iField, CompilationUnit cuNode) throws JavaModelException {
		ASTNode node= getNameNode(iField, cuNode);
		if (node instanceof VariableDeclarationFragment)
			return  (VariableDeclarationFragment)node;
		return (VariableDeclarationFragment)ASTNodes.getParent(node, VariableDeclarationFragment.class);
	}
		
	public static FieldDeclaration getFieldDeclarationNode(IField iField, CompilationUnit cuNode) throws JavaModelException {
		return (FieldDeclaration) ASTNodes.getParent(getNameNode(iField, cuNode), FieldDeclaration.class);
	}

	public static EnumConstantDeclaration getEnumConstantDeclaration(IField iField, CompilationUnit cuNode) throws JavaModelException {
		return (EnumConstantDeclaration) ASTNodes.getParent(getNameNode(iField, cuNode), EnumConstantDeclaration.class);
	}

	public static EnumDeclaration getEnumDeclarationNode(IType iType, CompilationUnit cuNode) throws JavaModelException {
		return (EnumDeclaration) ASTNodes.getParent(getNameNode(iType, cuNode), EnumDeclaration.class);
	}

	public static AnnotationTypeDeclaration getAnnotationTypeDeclarationNode(IType iType, CompilationUnit cuNode) throws JavaModelException {
		return (AnnotationTypeDeclaration) ASTNodes.getParent(getNameNode(iType, cuNode), AnnotationTypeDeclaration.class);
	}

	public static BodyDeclaration getBodyDeclarationNode(IMember iMember, CompilationUnit cuNode) throws JavaModelException {
		return (BodyDeclaration) ASTNodes.getParent(getNameNode(iMember, cuNode), BodyDeclaration.class);
	}

	public static AbstractTypeDeclaration getAbstractTypeDeclarationNode(IType iType, CompilationUnit cuNode) throws JavaModelException {
		return (AbstractTypeDeclaration) ASTNodes.getParent(getNameNode(iType, cuNode), AbstractTypeDeclaration.class);
	}

	public static TypeDeclaration getTypeDeclarationNode(IType iType, CompilationUnit cuNode) throws JavaModelException {
		return (TypeDeclaration) ASTNodes.getParent(getNameNode(iType, cuNode), TypeDeclaration.class);
	}
	
	public static ClassInstanceCreation getClassInstanceCreationNode(IType iType, CompilationUnit cuNode) throws JavaModelException {
		return (ClassInstanceCreation) ASTNodes.getParent(getNameNode(iType, cuNode), ClassInstanceCreation.class);
	}
	
	@SuppressWarnings("unchecked")
	public static List<BodyDeclaration> getBodyDeclarationList(IType iType, CompilationUnit cuNode) throws JavaModelException {
		if (iType.isAnonymous())
			return getClassInstanceCreationNode(iType, cuNode).getAnonymousClassDeclaration().bodyDeclarations();
		return getAbstractTypeDeclarationNode(iType, cuNode).bodyDeclarations();
	}

	private static ASTNode getNameNode(IMember iMember, CompilationUnit cuNode) throws JavaModelException {
		return NodeFinder.perform(cuNode, iMember.getNameRange());
	}
}

