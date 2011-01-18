/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmNamedColumn;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;

/**
 * <code>orm.xml</code> order column
 */
public class GenericOrmOrderColumn2_0
	extends AbstractOrmNamedColumn<XmlOrderColumn, OrmOrderColumn2_0.Owner>
	implements OrmOrderColumn2_0
{
	// TODO defaults from java for all of these settings
	protected Boolean specifiedNullable;

	protected Boolean specifiedInsertable;

	protected Boolean specifiedUpdatable;


	public GenericOrmOrderColumn2_0(OrmOrderable2_0 parent, OrmOrderColumn2_0.Owner owner) {
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


	// ********** XML column **********

	@Override
	public XmlOrderColumn getXmlColumn() {
		return this.owner.getXmlColumn();
	}

	@Override
	protected XmlOrderColumn buildXmlColumn() {
		return this.owner.buildXmlColumn();
	}

	@Override
	protected void removeXmlColumn() {
		this.owner.removeXmlColumn();
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
		if (this.valuesAreDifferent(this.specifiedInsertable, insertable)) {
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
		if (this.valuesAreDifferent(this.specifiedUpdatable, updatable)) {
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
	public OrmOrderable2_0 getParent() {
		return (OrmOrderable2_0) super.getParent();
	}

	protected OrmOrderable2_0 getOrderable() {
		return this.getParent();
	}

	protected OrmAttributeMapping getAttributeMapping() {
		return this.getOrderable().getParent();
	}

	protected PersistentAttribute getPersistentAttribute() {
		return this.getAttributeMapping().getPersistentAttribute();
	}

	@Override
	public String getTable() {
		return this.getParent().getDefaultTableName();
	}

}
