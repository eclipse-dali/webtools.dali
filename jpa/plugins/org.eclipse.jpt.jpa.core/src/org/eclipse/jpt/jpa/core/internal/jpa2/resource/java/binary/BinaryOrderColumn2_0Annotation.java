/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryNamedColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.OrderColumn</code>
 */
public class BinaryOrderColumn2_0Annotation
	extends BinaryNamedColumnAnnotation
	implements OrderColumnAnnotation2_0
{
	private Boolean nullable;
	private Boolean insertable;
	private Boolean updatable;
	

	public BinaryOrderColumn2_0Annotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.nullable = this.buildNullable();
		this.insertable = this.buildInsertable();
		this.updatable = this.buildUpdatable();
	}

	@Override
	public void update() {
		super.update();
		this.setNullable_(this.buildNullable());
		this.setInsertable_(this.buildInsertable());
		this.setUpdatable_(this.buildUpdatable());
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	
	//************* BinaryNamedColumnAnnotation implementation *************
	
	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.COLUMN__COLUMN_DEFINITION;
	}

	@Override
	protected String getNameElementName() {
		return JPA.COLUMN__NAME;
	}
	
	//************* OrderColumn2_0Annotation implementation *************
	
	// ***** nullable
	public Boolean getNullable() {
		return this.nullable;
	}

	public void setNullable(Boolean nullable) {
		throw new UnsupportedOperationException();
	}

	private void setNullable_(Boolean nullable) {
		Boolean old = this.nullable;
		this.nullable = nullable;
		this.firePropertyChanged(NULLABLE_PROPERTY, old, nullable);
	}

	private Boolean buildNullable() {
		return (Boolean) this.getJdtMemberValue(getNullableElementName());
	}
	
	String getNullableElementName() {
		return JPA.COLUMN__NULLABLE;
	}

	public TextRange getNullableTextRange() {
		throw new UnsupportedOperationException();
	}
	
	// ***** insertable
	public Boolean getInsertable() {
		return this.insertable;
	}

	public void setInsertable(Boolean insertable) {
		throw new UnsupportedOperationException();
	}

	private void setInsertable_(Boolean insertable) {
		Boolean old = this.insertable;
		this.insertable = insertable;
		this.firePropertyChanged(INSERTABLE_PROPERTY, old, insertable);
	}

	private Boolean buildInsertable() {
		return (Boolean) this.getJdtMemberValue(this.getInsertableElementName());
	}
	
	String getInsertableElementName() {
		return JPA.COLUMN__INSERTABLE;
	}

	public TextRange getInsertableTextRange() {
		throw new UnsupportedOperationException();
	}
	
	// ***** updatable
	public Boolean getUpdatable() {
		return this.updatable;
	}

	public void setUpdatable(Boolean updatable) {
		throw new UnsupportedOperationException();
	}

	private void setUpdatable_(Boolean updatable) {
		Boolean old = this.updatable;
		this.updatable = updatable;
		this.firePropertyChanged(UPDATABLE_PROPERTY, old, updatable);
	}

	private Boolean buildUpdatable() {
		return (Boolean) this.getJdtMemberValue(this.getUpdatableElementName());
	}

	String getUpdatableElementName() {
		return JPA.COLUMN__UPDATABLE;
	}

	public TextRange getUpdatableTextRange() {
		throw new UnsupportedOperationException();
	}
}
