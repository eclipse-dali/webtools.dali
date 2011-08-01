/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.PersistentFieldValidator;

public class FieldAccessor 
	extends AbstractAccessor
{
	private final JavaResourceField resourceField;
	
	public FieldAccessor(ReadOnlyPersistentAttribute parent, JavaResourceField resourceField) {
		super(parent);
		this.resourceField = resourceField;
	}

	public JavaResourceAttribute getResourceAttribute() {
		return this.getField();
	}

	public JavaResourceField getField() {
		return this.resourceField;
	}

	public boolean isFor(JavaResourceField resourceField) {
		return this.resourceField == resourceField;
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

	public JptValidator buildAttributeValidator(PersistentAttribute persistentAttribute, PersistentAttributeTextRangeResolver textRangeResolver) {
		return new PersistentFieldValidator(persistentAttribute, this, textRangeResolver);
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getResourceAttribute().getNameTextRange(astRoot);
	}

	public JavaPersistentAttribute buildUnannotatedJavaAttribute(PersistentType parent) {
		return this.buildJavaAttribute(parent, this.buildUnannotatedJavaResourceField());
	}

	/**
	 * Build a Java resource field that wraps the original Java resource
	 * field and behaves as though it has no annotations. This will cause
	 * all the settings in the Java <em>context</em> attribute to default.
	 */
	protected JavaResourceField buildUnannotatedJavaResourceField() {
		return new UnannotatedJavaResourceField(this.getField());
	}

	protected JavaPersistentAttribute buildJavaAttribute(PersistentType parent, JavaResourceField resourceField) {
		return this.getJpaFactory().buildJavaPersistentField(parent, resourceField);
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

		public Kind getKind() {
			return Kind.FIELD;
		}
	}

}
