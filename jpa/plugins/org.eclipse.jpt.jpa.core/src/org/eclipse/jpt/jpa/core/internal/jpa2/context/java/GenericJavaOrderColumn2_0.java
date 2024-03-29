/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumnAnnotation2_0;

/**
 * Java order column
 */
public class GenericJavaOrderColumn2_0
	extends AbstractJavaNamedColumn<JavaSpecifiedOrderColumn2_0.ParentAdapter, OrderColumnAnnotation2_0>
	implements JavaSpecifiedOrderColumn2_0
{
	protected Boolean specifiedNullable;
	protected boolean defaultNullable;

	protected Boolean specifiedInsertable;
	protected boolean defaultInsertable;

	protected Boolean specifiedUpdatable;
	protected boolean defaultUpdatable;


	public GenericJavaOrderColumn2_0(JavaSpecifiedOrderColumn2_0.ParentAdapter parentAdapter) {
		super(parentAdapter);
		//build defaults during construction for performance
		this.defaultNullable = this.buildDefaultNullable();
		this.defaultInsertable = this.buildDefaultInsertable();
		this.defaultUpdatable = this.buildDefaultUpdatable();
	}

	@Override
	protected void initialize(OrderColumnAnnotation2_0 columnAnnotation) {
		super.initialize(columnAnnotation);
		this.specifiedNullable = this.buildSpecifiedNullable(columnAnnotation);
		this.specifiedInsertable = this.buildSpecifiedInsertable(columnAnnotation);
		this.specifiedUpdatable = this.buildSpecifiedUpdatable(columnAnnotation);
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(OrderColumnAnnotation2_0 columnAnnotation) {
		super.synchronizeWithResourceModel(columnAnnotation);
		this.setSpecifiedNullable_(this.buildSpecifiedNullable(columnAnnotation));
		this.setSpecifiedInsertable_(this.buildSpecifiedInsertable(columnAnnotation));
		this.setSpecifiedUpdatable_(this.buildSpecifiedUpdatable(columnAnnotation));
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultNullable(this.buildDefaultNullable());
		this.setDefaultInsertable(this.buildDefaultInsertable());
		this.setDefaultUpdatable(this.buildDefaultUpdatable());
	}


	// ********** column annotation **********

	/**
	 * If we are in a JPA 1.0 project, return a <em>null</em> annotation.
	 */
	@Override
	public OrderColumnAnnotation2_0 getColumnAnnotation() {
		return this.parentAdapter.getColumnAnnotation();
	}

	@Override
	protected void removeColumnAnnotation() {
		this.parentAdapter.removeColumnAnnotation();
	}


	// ********** nullable **********

	public boolean isNullable() {
		return (this.specifiedNullable != null) ? this.specifiedNullable.booleanValue() : this.isDefaultNullable();
	}

	public Boolean getSpecifiedNullable() {
		return this.specifiedNullable;
	}

	public void setSpecifiedNullable(Boolean nullable) {
		if (ObjectTools.notEquals(this.specifiedNullable, nullable)) {
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

	protected Boolean buildSpecifiedNullable(OrderColumnAnnotation2_0 columnAnnotation) {
		return columnAnnotation.getNullable();
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
		if (ObjectTools.notEquals(this.specifiedInsertable, insertable)) {
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

	protected Boolean buildSpecifiedInsertable(OrderColumnAnnotation2_0 columnAnnotation) {
		return columnAnnotation.getInsertable();
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
		if (ObjectTools.notEquals(this.specifiedUpdatable, updatable)) {
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

	protected Boolean buildSpecifiedUpdatable(OrderColumnAnnotation2_0 columnAnnotation) {
		return columnAnnotation.getUpdatable();
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
	public String getTableName() {
		return this.parentAdapter.getDefaultTableName();
	}
}
