/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlColumn;

/**
 * <code>orm.xml</code> column or join column
 */
public abstract class AbstractOrmBaseColumn<X extends AbstractXmlColumn, O extends OrmReadOnlyBaseColumn.Owner>
	extends AbstractOrmNamedColumn<X, O>
	implements OrmBaseColumn
{
	protected String specifiedTable;
	protected String defaultTable;

	// TODO defaults from java for all of these settings
	protected Boolean specifiedUnique;

	protected Boolean specifiedNullable;

	protected Boolean specifiedInsertable;

	protected Boolean specifiedUpdatable;


	// ********** constructor/initialization **********

	protected AbstractOrmBaseColumn(XmlContextNode parent, O owner) {
		this(parent, owner, null);
	}

	protected AbstractOrmBaseColumn(XmlContextNode parent, O owner, X xmlColumn) {
		super(parent, owner, xmlColumn);
		this.specifiedTable = this.buildSpecifiedTable();
		this.specifiedUnique = this.buildSpecifiedUnique();
		this.specifiedNullable = this.buildSpecifiedNullable();
		this.specifiedInsertable = this.buildSpecifiedInsertable();
		this.specifiedUpdatable = this.buildSpecifiedUpdatable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTable_(this.buildSpecifiedTable());
		this.setSpecifiedUnique_(this.buildSpecifiedUnique());
		this.setSpecifiedNullable_(this.buildSpecifiedNullable());
		this.setSpecifiedInsertable_(this.buildSpecifiedInsertable());
		this.setSpecifiedUpdatable_(this.buildSpecifiedUpdatable());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultTable(this.buildDefaultTable());
	}


	// ********** table **********

	@Override
	public String getTable() {
		return (this.specifiedTable != null) ? this.specifiedTable : this.defaultTable;
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String table) {
		if (this.valuesAreDifferent(this.specifiedTable, table)) {
			X xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedTable_(table);
			xmlColumn.setTable(table);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedTable_(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
	}

	protected String buildSpecifiedTable() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getTable();
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}

	protected void setDefaultTable(String table) {
		String old = this.defaultTable;
		this.defaultTable = table;
		this.firePropertyChanged(DEFAULT_TABLE_PROPERTY, old, table);
	}

	protected String buildDefaultTable() {
		return this.owner.getDefaultTableName();
	}


	// ********** unique **********

	public boolean isUnique() {
		return (this.specifiedUnique != null) ? this.specifiedUnique.booleanValue() : this.isDefaultUnique();
	}

	public Boolean getSpecifiedUnique() {
		return this.specifiedUnique;
	}

	public void setSpecifiedUnique(Boolean unique) {
		if (this.valuesAreDifferent(this.specifiedUnique, unique)) {
			X xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedUnique_(unique);
			xmlColumn.setUnique(unique);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedUnique_(Boolean unique) {
		Boolean old = this.specifiedUnique;
		this.specifiedUnique = unique;
		this.firePropertyChanged(SPECIFIED_UNIQUE_PROPERTY, old, unique);
	}

	protected Boolean buildSpecifiedUnique() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getUnique();
	}

	public boolean isDefaultUnique() {
		return DEFAULT_UNIQUE;
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
			X xmlColumn = this.getXmlColumnForUpdate();
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
		X xmlColumn = this.getXmlColumn();
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
			X xmlColumn = this.getXmlColumnForUpdate();
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
		X xmlColumn = this.getXmlColumn();
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
			X xmlColumn = this.getXmlColumnForUpdate();
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
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getUpdatable();
	}

	public boolean isDefaultUpdatable() {
		return DEFAULT_UPDATABLE;
	}


	// ********** misc **********

	protected void initializeFrom(ReadOnlyBaseColumn oldColumn) {
		super.initializeFrom(oldColumn);
		this.setSpecifiedTable(oldColumn.getSpecifiedTable());
		this.setSpecifiedUnique(oldColumn.getSpecifiedUnique());
		this.setSpecifiedNullable(oldColumn.getSpecifiedNullable());
		this.setSpecifiedInsertable(oldColumn.getSpecifiedInsertable());
		this.setSpecifiedUpdatable(oldColumn.getSpecifiedUpdatable());
	}

	protected void initializeFromVirtual(ReadOnlyBaseColumn virtualColumn) {
		super.initializeFromVirtual(virtualColumn);
		this.setSpecifiedTable(virtualColumn.getTable());
		// ignore other settings?
	}

	public boolean tableNameIsInvalid() {
		return this.owner.tableNameIsInvalid(this.getTable());
	}

	public Iterator<String> candidateTableNames() {
		return this.owner.candidateTableNames();
	}


	// ********** validation **********

	public TextRange getTableTextRange() {
		return this.getValidationTextRange(this.getXmlColumnTableTextRange());
	}

	protected TextRange getXmlColumnTableTextRange() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getTableTextRange();
	}

	@Override
	protected NamedColumnTextRangeResolver buildTextRangeResolver() {
		return new OrmBaseColumnTextRangeResolver(this);
	}
}
