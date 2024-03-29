/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaElementReference;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.PersistentFieldValidator;

public class FieldAccessor
	extends AbstractAccessor
{
	private final JavaResourceField resourceField;


	public FieldAccessor(PersistentAttribute parent, JavaResourceField resourceField) {
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

	public JpaValidator buildAttributeValidator(SpecifiedPersistentAttribute persistentAttribute) {
		return new PersistentFieldValidator(persistentAttribute, this);
	}

	public JavaSpecifiedPersistentAttribute buildUnannotatedJavaAttribute(PersistentType type) {
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

	protected JavaSpecifiedPersistentAttribute buildJavaAttribute(PersistentType type, JavaResourceField javaResourceField) {
		return this.getJpaFactory().buildJavaPersistentField(type, javaResourceField);
	}

	public IJavaElement getJavaElement() {
		PersistentType persistentType = this.getAttribute().getDeclaringPersistentType();
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
