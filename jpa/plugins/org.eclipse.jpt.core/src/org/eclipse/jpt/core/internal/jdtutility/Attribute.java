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

import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;

/**
 * Combine behavior common to FieldAttribute and MethodAttribute.
 */
public abstract class Attribute extends Member {

	Attribute(IMember jdtMember, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider) {
		super(jdtMember, modifySharedDocumentCommandExecutorProvider);
	}
	
	Attribute(IMember jdtMember, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider, AnnotationEditFormatter annotationEditFormatter) {
		super(jdtMember, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter);
	}

	public boolean isField() {
		return false;
	}

	public boolean isMethod() {
		return false;
	}

	public abstract String attributeName();

	public boolean typeIs(String fullyQualifiedTypeName, CompilationUnit astRoot) {
		return fullyQualifiedTypeName.equals(this.resolvedTypeName(astRoot));
	}

	/**
	 * Resolve the attribute.
	 * Return the fully-qualified type name or return null if it cannot be
	 * resolved unambiguously.
	 */
	public String resolvedTypeName(CompilationUnit astRoot) {
		ITypeBinding typeBinding = typeBinding(astRoot);
		if (typeBinding != null) {
			return typeBinding.getQualifiedName();
		}
		return null;
	}

	/**
	 * this will throw a NPE for a top-level type
	 */
	TypeDeclaration declaringTypeDeclaration(CompilationUnit astRoot) {
		//assume no enums or annotation types since they have no field or method declarations
		return (TypeDeclaration) this.getDeclaringType().bodyDeclaration(astRoot);
	}

	public abstract ITypeBinding typeBinding(CompilationUnit astRoot);
}
