/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.OrderColumn2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

/**
 * Java order column
 */
public class GenericJavaOrderColumn2_0
	extends AbstractJavaNamedColumn<OrderColumn2_0Annotation, JavaReadOnlyNamedColumn.Owner>
	implements JavaOrderColumn2_0
{
	protected Boolean specifiedNullable;
	protected boolean defaultNullable;

	protected Boolean specifiedInsertable;
	protected boolean defaultInsertable;

	protected Boolean specifiedUpdatable;
	protected boolean defaultUpdatable;

	// JPA 1.0
	protected OrderColumn2_0Annotation nullColumnAnnotation;


	public GenericJavaOrderColumn2_0(JavaOrderable2_0 parent, JavaReadOnlyNamedColumn.Owner owner) {
		super(parent, owner);
		this.specifiedNullable = this.buildSpecifiedNullable();
		this.specifiedInsertable = this.buildSpecifiedInsertable();
		this.specifiedUpdatable = this.buildSpecifiedUpdatable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedNullable_(this.buildSpecifiedNullable());
		this.setSpecifiedInsertable_(this.buildSpecifiedInsertable());
		this.setSpecifiedUpdatable_(this.buildSpecifiedUpdatable());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultNullable(this.buildDefaultNullable());
		this.setDefaultInsertable(this.buildDefaultInsertable());
		this.setDefaultUpdatable(this.buildDefaultUpdatable());
	}


	// ********** column annotation **********

	/**
	 * If we are in a JPA 1.0 project, return a null annotation.
	 */
	@Override
	public OrderColumn2_0Annotation getColumnAnnotation() {
		// hmmmm...
		return this.isJpa2_0Compatible() ?
				(OrderColumn2_0Annotation) this.getResourcePersistentAttribute().getNonNullAnnotation(OrderColumn2_0Annotation.ANNOTATION_NAME) :
				this.getNullColumnAnnotation();
	}

	protected OrderColumn2_0Annotation getNullColumnAnnotation() {
		if (this.nullColumnAnnotation == null) {
			this.nullColumnAnnotation = this.buildNullColumnAnnotation();
		}
		return this.nullColumnAnnotation;
	}

	protected OrderColumn2_0Annotation buildNullColumnAnnotation() {
		// hmmmm...
		return (OrderColumn2_0Annotation) OrderColumn2_0AnnotationDefinition.instance().buildNullAnnotation(this.getResourcePersistentAttribute());
	}

	@Override
	protected void removeColumnAnnotation() {
		if (this.isJpa2_0Compatible()) {
			this.getResourcePersistentAttribute().removeAnnotation(OrderColumn2_0Annotation.ANNOTATION_NAME);
		} else {
			throw new IllegalStateException();
		}
	}


	// ********** nullable **********

	public boolean isNullable() {
		return (this.specifiedNullable != null) ? this.specifiedNullable.booleanValue() : this.isDefaultNullable();
	}

	public Boolean getSpecifiedNullable() {
		return this.specifiedNullable;
	}

	public void setSpecifiedNullable(Boolean nullable) {
		if (this.valuesAreDifferent(this.specifiedNullable, nullable)) {
			this.getColumnAnnotation().setNullable(nullable);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedNullable_(nullable);
		}
	}

	protected void setSpecifiedNullable_(Boolean nullable) {
		Boolean old = this.specifiedNullable;
		this.specifiedNullable = nullable;
		this.firePropertyChanged(SPECIFIED_NULLABLE_PROPERTY, old, nullable);
	}

	protected Boolean buildSpecifiedNullable() {
		return this.getColumnAnnotation().getNullable();
	}

	public boolean isDefaultNullable() {
		return this.defaultNullable;
	}

	protected void setDefaultNullable(boolean nullable) {
		boolean old = this.defaultNullable;
		this.defaultNullable = nullable;
		this.firePropertyChanged(DEFAULT_NULLABLE_PROPERTY, old, nullable);
	}

	protected boolean buildDefaultNullable() {
		return DEFAULT_NULLABLE;
	}


	// ********** insertable **********

	public boolean isInsertable() {
		return (this.specifiedInsertable != null) ? this.specifiedInsertable.booleanValue() : this.isDefaultInsertable();
	}

	public Boolean getSpecifiedInsertable() {
		return this.specifiedInsertable;
	}

	public void setSpecifiedInsertable(Boolean insertable) {
		if (this.valuesAreDifferent(this.specifiedInsertable, insertable)) {
			this.getColumnAnnotation().setInsertable(insertable);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedInsertable_(insertable);
		}
	}

	protected void setSpecifiedInsertable_(Boolean insertable) {
		Boolean old = this.specifiedInsertable;
		this.specifiedInsertable = insertable;
		this.firePropertyChanged(SPECIFIED_INSERTABLE_PROPERTY, old, insertable);
	}

	protected Boolean buildSpecifiedInsertable() {
		return this.getColumnAnnotation().getInsertable();
	}

	public boolean isDefaultInsertable() {
		return this.defaultInsertable;
	}

	protected void setDefaultInsertable(boolean insertable) {
		boolean old = this.defaultInsertable;
		this.defaultInsertable = insertable;
		this.firePropertyChanged(DEFAULT_INSERTABLE_PROPERTY, old, insertable);
	}

	protected boolean buildDefaultInsertable() {
		return DEFAULT_INSERTABLE;
	}


	// ********** updatable **********

	public boolean isUpdatable() {
		return (this.specifiedUpdatable != null) ? this.specifiedUpdatable.booleanValue() : this.isDefaultUpdatable();
	}

	public Boolean getSpecifiedUpdatable() {
		return this.specifiedUpdatable;
	}

	public void setSpecifiedUpdatable(Boolean updatable) {
		if (this.valuesAreDifferent(this.specifiedUpdatable, updatable)) {
			this.getColumnAnnotation().setUpdatable(updatable);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedUpdatable_(updatable);
		}
	}

	protected void setSpecifiedUpdatable_(Boolean updatable) {
		Boolean old = this.specifiedUpdatable;
		this.specifiedUpdatable = updatable;
		this.firePropertyChanged(SPECIFIED_UPDATABLE_PROPERTY, old, updatable);
	}

	protected Boolean buildSpecifiedUpdatable() {
		return this.getColumnAnnotation().getUpdatable();
	}

	public boolean isDefaultUpdatable() {
		return this.defaultUpdatable;
	}

	protected void setDefaultUpdatable(boolean updatable) {
		boolean old = this.defaultUpdatable;
		this.defaultUpdatable = updatable;
		this.firePropertyChanged(DEFAULT_UPDATABLE_PROPERTY, old, updatable);
	}

	protected boolean buildDefaultUpdatable() {
		return DEFAULT_UPDATABLE;
	}


	// ********** misc **********

	@Override
	public JavaOrderable2_0 getParent() {
		return (JavaOrderable2_0) super.getParent();
	}

	protected JavaOrderable2_0 getOrderable() {
		return this.getParent();
	}

	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.getOrderable().getResourcePersistentAttribute();
	}

	@Override
	public String getTable() {
		return this.getOrderable().getDefaultTableName();
	}
}
