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
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * Adapt and extend a jdt field.
 * Attribute based on a Java field, e.g.
 *     private int foo;
 */
public class FieldAttribute extends Attribute {

	public FieldAttribute(IField field) {
		super(field);
	}

	@Override
	public IField getJdtMember() {
		return (IField) super.getJdtMember();
	}

	// ********** Member implementation **********

	@Override
	public FieldDeclaration bodyDeclaration(CompilationUnit astRoot) {
		String fieldName = this.getName();
		for (FieldDeclaration fieldDeclaration : this.declaringTypeDeclaration(astRoot).getFields()) {
			// handle multiple fields declared in a single statement:
			//     private int foo, bar;
			for (VariableDeclarationFragment fragment : this.fragments(fieldDeclaration)) {
				if (fragment.getName().getFullyQualifiedName().equals(fieldName)) {
					return fieldDeclaration;
				}
			}
		}
		return null;		
	}


	// ********** Attribute implementation **********

	@Override
	public boolean isField() {
		return true;
	}

	@Override
	public String attributeName() {
		return this.getName();
	}

	@Override
	public ITypeBinding typeBinding(CompilationUnit astRoot) {
		return bodyDeclaration(astRoot).getType().resolveBinding();
	}


	// ********** miscellaneous **********

	@SuppressWarnings("unchecked")
	protected List<VariableDeclarationFragment> fragments(FieldDeclaration fd) {
		return fd.fragments();
	}

}
