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

import java.util.List;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class Type extends Member {

	public Type(IType type) {
		super(type);
	}

	@Override
	public IType getJdtMember() {
		return (IType) super.getJdtMember();
	}

	@Override
	public TypeDeclaration bodyDeclaration() {
		return (TypeDeclaration) super.bodyDeclaration();
	}

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

	public String getFullyQualifiedName() {
		return this.getJdtMember().getFullyQualifiedName();
	}


	// ********** Member implementation **********

	@Override
	public TypeDeclaration bodyDeclaration(CompilationUnit astRoot) {
		String name = this.getName();
		for (AbstractTypeDeclaration typeDeclaration : this.types(astRoot)) {
			if (typeDeclaration.getName().getFullyQualifiedName().equals(name)) {
				return (TypeDeclaration) typeDeclaration;  // assume no enum or annotation declarations
			}
		}
		return null;
	}
	

	// ********** miscellaneous **********

	@SuppressWarnings("unchecked")
	protected List<AbstractTypeDeclaration> types(CompilationUnit astRoot) {
		return astRoot.types();
	}

}
