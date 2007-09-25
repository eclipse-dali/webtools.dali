/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;

public class Type extends Member {

	public Type(IType type, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider) {
		super(type, modifySharedDocumentCommandExecutorProvider);
	}

	public Type(IType type, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider, AnnotationEditFormatter annotationEditFormatter) {
		super(type, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter);
	}

	@Override
	public IType getJdtMember() {
		return (IType) super.getJdtMember();
	}

	//TODO I believe this should move to the resource model and should be stored when updating from java
	public boolean isAbstract() {
		try {
			return Flags.isAbstract(this.getJdtMember().getFlags());
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Type topLevelDeclaringType() {
		return (this.getDeclaringType() == null) ? this : super.topLevelDeclaringType();
	}

	public IType[] declaredTypes() {
		try {
			return getJdtMember().getTypes();
		}
		catch(JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	public IField[] fields() {
		try {
			return getJdtMember().getFields();
		}
		catch(JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	public IMethod[] methods() {
		try {
			return getJdtMember().getMethods();
		}
		catch(JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	// ********** Member implementation **********

	@Override
	public AbstractTypeDeclaration bodyDeclaration(CompilationUnit astRoot) {
		Type declaringType = getDeclaringType();
		if (declaringType != null) {
			return typeDeclaration(declaringType.bodyDeclaration(astRoot));
		}
		return typeDeclaration(this.types(astRoot));
	}
	
	public AbstractTypeDeclaration typeDeclaration(AbstractTypeDeclaration declaringTypeDeclaration) {
		return typeDeclaration(this.types(declaringTypeDeclaration));
	}
	
	private AbstractTypeDeclaration typeDeclaration(List<AbstractTypeDeclaration> typeDeclarations) {
		String name = this.getName();
		for (AbstractTypeDeclaration typeDeclaration : typeDeclarations) {
			if (typeDeclaration.getName().getFullyQualifiedName().equals(name)) {
				return typeDeclaration;
			}
		}
		return null;
	}

	@Override
	public ITypeBinding binding(CompilationUnit astRoot) {
		return bodyDeclaration(astRoot).resolveBinding();
	}

	// ********** miscellaneous **********

	@SuppressWarnings("unchecked")
	protected List<AbstractTypeDeclaration> types(CompilationUnit astRoot) {
		return astRoot.types();
	}
	
	protected List<AbstractTypeDeclaration> types(AbstractTypeDeclaration typeDeclaration) {
		List<AbstractTypeDeclaration> typeDeclarations = new ArrayList<AbstractTypeDeclaration>();
		for (BodyDeclaration bodyDeclaration : bodyDeclarations(typeDeclaration))
			if (bodyDeclaration.getNodeType() == ASTNode.TYPE_DECLARATION ||
				bodyDeclaration.getNodeType() == ASTNode.ANNOTATION_TYPE_DECLARATION  ||
				bodyDeclaration.getNodeType() == ASTNode.ENUM_DECLARATION) {
				typeDeclarations.add((AbstractTypeDeclaration) bodyDeclaration);
			}
		return typeDeclarations;
	}
	
	@SuppressWarnings("unchecked")
	protected List<BodyDeclaration> bodyDeclarations(AbstractTypeDeclaration typeDeclaration) {
		return typeDeclaration.bodyDeclarations();
	}
	

}
