/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.VirtualBaseColumn;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;

public abstract class AbstractOrmVirtualBaseColumn<O extends OrmReadOnlyBaseColumn.Owner, C extends ReadOnlyBaseColumn>
	extends AbstractOrmVirtualNamedColumn<O, C>
	implements VirtualBaseColumn, OrmReadOnlyBaseColumn
{
	protected String specifiedTable;
	protected String defaultTable;

	protected Boolean specifiedUnique;
	protected boolean defaultUnique;

	protected Boolean specifiedNullable;
	protected boolean defaultNullable;

	protected Boolean specifiedInsertable;
	protected boolean defaultInsertable;

	protected Boolean specifiedUpdatable;
	protected boolean defaultUpdatable;


	protected AbstractOrmVirtualBaseColumn(XmlContextNode parent, O owner) {
		super(parent, owner);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();

		this.setSpecifiedTable(this.buildSpecifiedTable());
		this.setDefaultTable(this.buildDefaultTable());

		this.setSpecifiedUnique(this.buildSpecifiedUnique());
		this.setDefaultUnique(this.buildDefaultUnique());

		this.setSpecifiedNullable(this.buildSpecifiedNullable());
		this.setDefaultNullable(this.buildDefaultNullable());

		this.setSpecifiedInsertable(this.buildSpecifiedInsertable());
		this.setDefaultInsertable(this.buildDefaultInsertable());

		this.setSpecifiedUpdatable(this.buildSpecifiedUpdatable());
		this.setDefaultUpdatable(this.buildDefaultUpdatable());
	}


	// ********** table **********

	public String getTable() {
		return (this.specifiedTable != null) ? this.specifiedTable : this.defaultTable;
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	protected void setSpecifiedTable(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
	}

	protected String buildSpecifiedTable() {
		return this.getOverriddenColumn().getSpecifiedTable();
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
		return this.owner.tableNameIsInvalid(this.getTable());
	}

	public Iterable<String> getCandidateTableNames() {
		return this.owner.getCandidateTableNames();
	}


	// ********** validation **********

	public TextRange getTableTextRange() {
		return this.getValidationTextRange();
	}

	@Override
	protected NamedColumnTextRangeResolver buildTextRangeResolver() {
		return new OrmBaseColumnTextRangeResolver(this);
	}
}
