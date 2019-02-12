/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmNamedColumn;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOrderColumn;

/**
 * <code>orm.xml</code> order column
 */
public class GenericOrmOrderColumn2_0
	extends AbstractOrmNamedColumn<OrmSpecifiedOrderColumn2_0.ParentAdapter, XmlOrderColumn>
	implements OrmSpecifiedOrderColumn2_0
{
	// TODO defaults from java for all of these settings
	protected Boolean specifiedNullable;

	protected Boolean specifiedInsertable;

	protected Boolean specifiedUpdatable;


	public GenericOrmOrderColumn2_0(OrmSpecifiedOrderColumn2_0.ParentAdapter parentAdapter) {
		super(parentAdapter);
		this.specifiedNullable = this.buildSpecifiedNullable();
		this.specifiedInsertable = this.buildSpecifiedInsertable();
		this.specifiedUpdatable = this.buildSpecifiedUpdatable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedNullable_(this.buildSpecifiedNullable());
		this.setSpecifiedInsertable_(this.buildSpecifiedInsertable());
		this.setSpecifiedUpdatable_(this.buildSpecifiedUpdatable());
	}


	// ********** XML column **********

	@Override
	public XmlOrderColumn getXmlColumn() {
		return this.parentAdapter.getXmlColumn();
	}

	@Override
	protected XmlOrderColumn buildXmlColumn() {
		return this.parentAdapter.buildXmlColumn();
	}

	@Override
	protected void removeXmlColumn() {
		this.parentAdapter.removeXmlColumn();
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
			XmlOrderColumn xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedNullable_(nullable);
			xmlColumn.setNullable(nullable);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedNullable_(Boolean nullable) {
		Boolean old = this.specifiedNullable;
		this.specifiedNullable = nullable;
		this.firePropertyChanged(SPECIFIED_NULLABLE_PROPERTY, old, nullable);
	}

	protected Boolean buildSpecifiedNullable() {
		XmlOrderColumn xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getNullable();
	}

	public boolean isDefaultNullable() {
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
			XmlOrderColumn xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedInsertable_(insertable);
			xmlColumn.setInsertable(insertable);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedInsertable_(Boolean insertable) {
		Boolean old = this.specifiedInsertable;
		this.specifiedInsertable = insertable;
		this.firePropertyChanged(SPECIFIED_INSERTABLE_PROPERTY, old, insertable);
	}

	protected Boolean buildSpecifiedInsertable() {
		XmlOrderColumn xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getInsertable();
	}

	public boolean isDefaultInsertable() {
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
			XmlOrderColumn xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedUpdatable_(updatable);
			xmlColumn.setUpdatable(updatable);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedUpdatable_(Boolean updatable) {
		Boolean old = this.specifiedUpdatable;
		this.specifiedUpdatable = updatable;
		this.firePropertyChanged(SPECIFIED_UPDATABLE_PROPERTY, old, updatable);
	}

	protected Boolean buildSpecifiedUpdatable() {
		XmlOrderColumn xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getUpdatable();
	}

	public boolean isDefaultUpdatable() {
		return DEFAULT_UPDATABLE;
	}


	// ********** misc **********

	@Override
	public String getTableName() {
		return this.parentAdapter.getDefaultTableName();
	}
}
