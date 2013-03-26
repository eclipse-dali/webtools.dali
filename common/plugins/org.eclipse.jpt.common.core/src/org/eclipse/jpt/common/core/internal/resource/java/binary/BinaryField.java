/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;

/**
 * binary field
 */
final class BinaryField
	extends BinaryAttribute
	implements JavaResourceField
{
	BinaryField(JavaResourceType parent, IField field) {
		this(parent,new FieldAdapter(field));
	}
	
	private BinaryField(JavaResourceType parent, FieldAdapter adapter) {
		super(parent, adapter);
		// put initialization here, if any becomes needed
	}
	
	
	public AstNodeType getAstNodeType() {
		return AstNodeType.FIELD;
	}
	
	public void synchronizeWith(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclaration) {
		throw new UnsupportedOperationException();
	}

	public void resolveTypes(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclaration) {
		throw new UnsupportedOperationException();
	}
	
	// ********** adapters **********

	/**
	 * IField adapter
	 */
	static class FieldAdapter
		implements AttributeAdapter
	{
		private final IField field;
		/* cached, but only during initialization */
		private final ITypeBinding typeBinding;
		
		FieldAdapter(IField field) {
			super();
			this.field = field;
			this.typeBinding = this.buildTypeBinding();
		}
		
		protected ITypeBinding buildTypeBinding() {
			IVariableBinding binding = (IVariableBinding) ASTTools.createBinding(this.field);
			// the binding can be null in certain cases (e.g. java.lang.Integer.$assertionsDisabled)
			return (binding == null) ? null : binding.getType();
		}
		
		public IField getElement() {
			return this.field;
		}
		
		public IAnnotation[] getAnnotations() throws JavaModelException {
			return this.field.getAnnotations();
		}
		
		public String getAttributeName() {
			return this.field.getElementName();
		}
		
		public ITypeBinding getTypeBinding() {
			return this.typeBinding;
		}
	}
}
