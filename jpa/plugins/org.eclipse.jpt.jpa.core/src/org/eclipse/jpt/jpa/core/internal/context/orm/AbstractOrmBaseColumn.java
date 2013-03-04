/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedBaseColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlBaseColumn;

/**
 * <code>orm.xml</code> column or join column
 */
public abstract class AbstractOrmBaseColumn<PA extends TableColumn.ParentAdapter, X extends XmlBaseColumn>
	extends AbstractOrmNamedColumn<PA, X>
	implements OrmSpecifiedBaseColumn
{
	protected String specifiedTableName;
	protected String defaultTableName;

	// TODO defaults from java for all of these settings
	protected Boolean specifiedUnique;

	protected Boolean specifiedNullable;

	protected Boolean specifiedInsertable;

	protected Boolean specifiedUpdatable;


	// ********** constructor/initialization **********

	protected AbstractOrmBaseColumn(PA parentAdapter) {
		this(parentAdapter, null);
	}

	protected AbstractOrmBaseColumn(PA parentAdapter, X xmlColumn) {
		super(parentAdapter, xmlColumn);
		this.specifiedTableName = this.buildSpecifiedTableName();
		this.specifiedUnique = this.buildSpecifiedUnique();
		this.specifiedNullable = this.buildSpecifiedNullable();
		this.specifiedInsertable = this.buildSpecifiedInsertable();
		this.specifiedUpdatable = this.buildSpecifiedUpdatable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTableName_(this.buildSpecifiedTableName());
		this.setSpecifiedUnique_(this.buildSpecifiedUnique());
		this.setSpecifiedNullable_(this.buildSpecifiedNullable());
		this.setSpecifiedInsertable_(this.buildSpecifiedInsertable());
		this.setSpecifiedUpdatable_(this.buildSpecifiedUpdatable());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultTableName(this.buildDefaultTableName());
	}


	// ********** table name **********

	@Override
	public String getTableName() {
		return (this.specifiedTableName != null) ? this.specifiedTableName : this.defaultTableName;
	}

	public String getSpecifiedTableName() {
		return this.specifiedTableName;
	}

	public void setSpecifiedTableName(String tableName) {
		if (this.valuesAreDifferent(this.specifiedTableName, tableName)) {
			X xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedTableName_(tableName);
			xmlColumn.setTable(tableName);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedTableName_(String tableName) {
		String old = this.specifiedTableName;
		this.specifiedTableName = tableName;
		this.firePropertyChanged(SPECIFIED_TABLE_NAME_PROPERTY, old, tableName);
	}

	protected String buildSpecifiedTableName() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getTable();
	}

	public String getDefaultTableName() {
		return this.defaultTableName;
	}

	protected void setDefaultTableName(String tableName) {
		String old = this.defaultTableName;
		this.defaultTableName = tableName;
		this.firePropertyChanged(DEFAULT_TABLE_NAME_PROPERTY, old, tableName);
	}

	protected String buildDefaultTableName() {
		return this.parentAdapter.getDefaultTableName();
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

	protected void initializeFrom(BaseColumn oldColumn) {
		super.initializeFrom(oldColumn);
		this.setSpecifiedTableName(oldColumn.getSpecifiedTableName());
		this.setSpecifiedUnique(oldColumn.getSpecifiedUnique());
		this.setSpecifiedNullable(oldColumn.getSpecifiedNullable());
		this.setSpecifiedInsertable(oldColumn.getSpecifiedInsertable());
		this.setSpecifiedUpdatable(oldColumn.getSpecifiedUpdatable());
	}

	protected void initializeFromVirtual(BaseColumn virtualColumn) {
		super.initializeFromVirtual(virtualColumn);
		this.setSpecifiedTableName(virtualColumn.getTableName());
		// ignore other settings?
	}

	public boolean tableNameIsInvalid() {
		return this.parentAdapter.tableNameIsInvalid(this.getTableName());
	}

	public Iterable<String> getCandidateTableNames() {
		return this.parentAdapter.getCandidateTableNames();
	}


	// ********** validation **********

	public TextRange getTableNameValidationTextRange() {
		return this.getValidationTextRange(this.getXmlColumnTableTextRange());
	}

	protected TextRange getXmlColumnTableTextRange() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getTableTextRange();
	}
	
	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos)) {
			return this.getCandidateTableNames();
		}
		return null;
	}

	protected boolean tableTouches(int pos) {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn != null) && (xmlColumn.tableTouches(pos));
	}
}
