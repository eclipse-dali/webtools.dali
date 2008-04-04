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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.CommandExecutorProvider;

public class JDTType
	extends JDTMember
	implements Type
{

	public JDTType(IType type, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider) {
		super(type, modifySharedDocumentCommandExecutorProvider);
	}

	public JDTType(IType type, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider, AnnotationEditFormatter annotationEditFormatter) {
		super(type, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter);
	}

	@Override
	public IType getJdtMember() {
		return (IType) super.getJdtMember();
	}

	@Override
	public Type getTopLevelDeclaringType() {
		return (this.declaringType() == null) ? this : super.getTopLevelDeclaringType();
	}

	public IType[] jdtTypes() {
		try {
			return getJdtMember().getTypes();
		}
		catch(JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	public IField[] jdtFields() {
		try {
			return getJdtMember().getFields();
		}
		catch(JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	public IMethod[] jdtMethods() {
		try {
			return getJdtMember().getMethods();
		}
		catch(JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	// ********** Member implementation **********

	public AbstractTypeDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		Type declaringType = this.declaringType();
		if (declaringType != null) {
			return this.getTypeDeclaration(declaringType.getBodyDeclaration(astRoot));
		}
		return this.getTypeDeclaration(this.types(astRoot));
	}
	
	public AbstractTypeDeclaration getTypeDeclaration(AbstractTypeDeclaration declaringTypeDeclaration) {
		return getTypeDeclaration(this.types(declaringTypeDeclaration));
	}
	
	private AbstractTypeDeclaration getTypeDeclaration(List<AbstractTypeDeclaration> typeDeclarations) {
		String name = this.name();
		for (AbstractTypeDeclaration typeDeclaration : typeDeclarations) {
			if (typeDeclaration.getName().getFullyQualifiedName().equals(name)) {
				return typeDeclaration;
			}
		}
		return null;
	}

	public ITypeBinding getBinding(CompilationUnit astRoot) {
		return getBodyDeclaration(astRoot).resolveBinding();
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return new ASTNodeTextRange(getBodyDeclaration(astRoot).getName());
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
