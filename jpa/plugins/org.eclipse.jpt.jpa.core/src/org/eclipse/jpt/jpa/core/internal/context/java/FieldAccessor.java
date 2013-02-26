/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.ModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaElementReference;
import org.eclipse.jpt.jpa.core.context.java.JavaModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.PersistentFieldValidator;

public class FieldAccessor
	extends AbstractAccessor
{
	private final JavaResourceField resourceField;


	public FieldAccessor(ReadOnlyPersistentAttribute parent, JavaResourceField resourceField) {
		super(parent);
		this.resourceField = resourceField;
	}

	public JavaResourceField getResourceAttribute() {
		return this.resourceField;
	}

	public boolean isFor(JavaResourceField field) {
		return this.resourceField == field;
	}

	public boolean isFor(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		return false;
	}

	public AccessType getDefaultAccess() {
		return AccessType.FIELD;
	}

	public boolean isPublic() {
		return this.resourceField.isPublic();
	}

	public boolean isFinal() {
		return this.resourceField.isFinal();
	}

	public JptValidator buildAttributeValidator(ModifiablePersistentAttribute persistentAttribute) {
		return new PersistentFieldValidator(persistentAttribute, this);
	}

	public JavaModifiablePersistentAttribute buildUnannotatedJavaAttribute(PersistentType type) {
		return this.buildJavaAttribute(type, this.buildUnannotatedJavaResourceField());
	}

	/**
	 * Build a Java resource field that wraps the original Java resource
	 * field and behaves as though it has no annotations. This will cause
	 * all the settings in the Java <em>context</em> attribute to default.
	 */
	protected JavaResourceField buildUnannotatedJavaResourceField() {
		return new UnannotatedJavaResourceField(this.resourceField);
	}

	protected JavaModifiablePersistentAttribute buildJavaAttribute(PersistentType type, JavaResourceField javaResourceField) {
		return this.getJpaFactory().buildJavaPersistentField(type, javaResourceField);
	}

	public IJavaElement getJavaElement() {
		PersistentType persistentType = this.getAttribute().getOwningPersistentType();
		if (persistentType instanceof JavaElementReference) {
			IType jdtType = (IType) ((JavaElementReference) persistentType).getJavaElement();
			return (jdtType == null) ? null : jdtType.getField(this.getAttribute().getName());
		}
		return null;
	}


	// ********** unannotated Java resource field **********

	/**
	 * Wrap another Java resource field and suppress all its annotations.
	 */
	protected class UnannotatedJavaResourceField
		extends UnannotatedJavaResourceAttribute<JavaResourceField>
		implements JavaResourceField
	{
		protected UnannotatedJavaResourceField(JavaResourceField field){
			super(field);
		}

		public AstNodeType getAstNodeType() {
			return AstNodeType.FIELD;
		}

		public void synchronizeWith(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclaration) {
			// NOP
		}

		public void resolveTypes(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclaration) {
			// NOP
		}
	}
}
