/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.VirtualBaseColumn;

public abstract class AbstractJavaVirtualBaseColumn<O extends ReadOnlyBaseColumn.Owner, C extends ReadOnlyBaseColumn>
	extends AbstractJavaVirtualNamedColumn<O, C>
	implements VirtualBaseColumn
{
	protected String specifiedTableName;
	protected String defaultTableName;

	protected Boolean specifiedUnique;
	protected boolean defaultUnique;

	protected Boolean specifiedNullable;
	protected boolean defaultNullable;

	protected Boolean specifiedInsertable;
	protected boolean defaultInsertable;

	protected Boolean specifiedUpdatable;
	protected boolean defaultUpdatable;


	protected AbstractJavaVirtualBaseColumn(JpaContextModel parent, O owner) {
		super(parent, owner);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();

		this.setSpecifiedTableName(this.buildSpecifiedTableName());
		this.setDefaultTableName(this.buildDefaultTableName());

		this.setSpecifiedUnique(this.buildSpecifiedUnique());
		this.setDefaultUnique(this.buildDefaultUnique());

		this.setSpecifiedNullable(this.buildSpecifiedNullable());
		this.setDefaultNullable(this.buildDefaultNullable());

		this.setSpecifiedInsertable(this.buildSpecifiedInsertable());
		this.setDefaultInsertable(this.buildDefaultInsertable());

		this.setSpecifiedUpdatable(this.buildSpecifiedUpdatable());
		this.setDefaultUpdatable(this.buildDefaultUpdatable());
	}


	// ********** table name **********

	@Override
	public String getTableName() {
		return (this.specifiedTableName != null) ? this.specifiedTableName : this.defaultTableName;
	}

	public String getSpecifiedTableName() {
		return this.specifiedTableName;
	}

	protected void setSpecifiedTableName(String tableName) {
		String old = this.specifiedTableName;
		this.specifiedTableName = tableName;
		this.firePropertyChanged(SPECIFIED_TABLE_NAME_PROPERTY, old, tableName);
	}

	protected String buildSpecifiedTableName() {
		return this.getOverriddenColumn().getSpecifiedTableName();
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
		return this.owner.getDefaultTableName();
	}


	// ********** unique **********

	public boolean isUnique() {
		return (this.specifiedUnique != null) ? this.specifiedUnique.booleanValue() : this.isDefaultUnique();
	}

	public Boolean getSpecifiedUnique() {
		return this.specifiedUnique;
	}

	protected void setSpecifiedUnique(Boolean unique) {
		Boolean old = this.specifiedUnique;
		this.specifiedUnique = unique;
		this.firePropertyChanged(SPECIFIED_UNIQUE_PROPERTY, old, unique);
	}

	protected Boolean buildSpecifiedUnique() {
		return this.getOverriddenColumn().getSpecifiedUnique();
	}

	public boolean isDefaultUnique() {
		return this.defaultUnique;
	}

	protected void setDefaultUnique(boolean unique) {
		boolean old = this.defaultUnique;
		this.defaultUnique = unique;
		this.firePropertyChanged(DEFAULT_UNIQUE_PROPERTY, old, unique);
	}

	protected boolean buildDefaultUnique() {
		return DEFAULT_UNIQUE;
	}


	// ********** nullable **********

	public boolean isNullable() {
		return (this.specifiedNullable != null) ? this.specifiedNullable.booleanValue() : this.isDefaultNullable();
	}

	public Boolean getSpecifiedNullable() {
		return this.specifiedNullable;
	}

	protected void setSpecifiedNullable(Boolean nullable) {
		Boolean old = this.specifiedNullable;
		this.specifiedNullable = nullable;
		this.firePropertyChanged(SPECIFIED_NULLABLE_PROPERTY, old, nullable);
	}

	protected Boolean buildSpecifiedNullable() {
		return this.getOverriddenColumn().getSpecifiedNullable();
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

	protected void setSpecifiedInsertable(Boolean insertable) {
		Boolean old = this.specifiedInsertable;
		this.specifiedInsertable = insertable;
		this.firePropertyChanged(SPECIFIED_INSERTABLE_PROPERTY, old, insertable);
	}

	protected Boolean buildSpecifiedInsertable() {
		return this.getOverriddenColumn().getSpecifiedInsertable();
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

	protected void setSpecifiedUpdatable(Boolean updatable) {
		Boolean old = this.specifiedUpdatable;
		this.specifiedUpdatable = updatable;
		this.firePropertyChanged(SPECIFIED_UPDATABLE_PROPERTY, old, updatable);
	}

	protected Boolean buildSpecifiedUpdatable() {
		return this.getOverriddenColumn().getSpecifiedUpdatable();
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

	public boolean tableNameIsInvalid() {
		return this.owner.tableNameIsInvalid(this.getTableName());
	}

	public Iterable<String> getCandidateTableNames() {
		return this.owner.getCandidateTableNames();
	}


	// ********** validation **********

	public TextRange getTableNameValidationTextRange() {
		return this.getValidationTextRange();
	}
}
