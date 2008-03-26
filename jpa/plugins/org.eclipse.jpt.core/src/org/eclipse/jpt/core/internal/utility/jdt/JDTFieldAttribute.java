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
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.FieldAttribute;
import org.eclipse.jpt.utility.CommandExecutorProvider;

/**
 * Adapt and extend a jdt field.
 * Attribute based on a Java field, e.g.
 *     private int foo;
 */
public class JDTFieldAttribute
	extends JDTAttribute
	implements FieldAttribute
{

	public JDTFieldAttribute(IField field, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider) {
		super(field, modifySharedDocumentCommandExecutorProvider);
	}
	
	public JDTFieldAttribute(IField field, CommandExecutorProvider modifySharedDocumentCommandExecutorProvider, AnnotationEditFormatter annotationEditFormatter) {
		super(field, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter);
	}

	@Override
	public IField getJdtMember() {
		return (IField) super.getJdtMember();
	}


	// ********** Member implementation **********

	public FieldDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		String fieldName = this.name();
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

	private VariableDeclarationFragment getFragment(CompilationUnit astRoot) {
		FieldDeclaration fieldDeclaration = getBodyDeclaration(astRoot);
		for (VariableDeclarationFragment fragment : this.fragments(fieldDeclaration)) {
			if (fragment.getName().getFullyQualifiedName().equals(name())) {
				return fragment;
			}
		}
		//TODO could this ever happen, should I throw an exception instead?
		return null;
	}
	
	public IVariableBinding getBinding(CompilationUnit astRoot) {
		return getFragment(astRoot).resolveBinding();
	}
	
	// ********** Attribute implementation **********

	@Override
	public boolean isField() {
		return true;
	}

	public String getAttributeName() {
		return this.name();
	}

	public ITypeBinding getTypeBinding(CompilationUnit astRoot) {
		return getBodyDeclaration(astRoot).getType().resolveBinding();
	}

	// ********** miscellaneous **********

	@SuppressWarnings("unchecked")
	protected List<VariableDeclarationFragment> fragments(FieldDeclaration fd) {
		return fd.fragments();
	}

}
