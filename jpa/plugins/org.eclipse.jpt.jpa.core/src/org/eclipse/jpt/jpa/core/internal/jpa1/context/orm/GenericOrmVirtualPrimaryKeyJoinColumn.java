/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmVirtualNamedColumn;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;

/**
 * <code>orm.xml</code> virtual primary key join column
 */
public class GenericOrmVirtualPrimaryKeyJoinColumn
	extends AbstractOrmVirtualNamedColumn<BaseJoinColumn.ParentAdapter, JavaSpecifiedPrimaryKeyJoinColumn>
	implements OrmVirtualPrimaryKeyJoinColumn
{
	protected final JavaSpecifiedPrimaryKeyJoinColumn javaColumn;

	protected String specifiedReferencedColumnName;
	protected String defaultReferencedColumnName;


	public GenericOrmVirtualPrimaryKeyJoinColumn(BaseJoinColumn.ParentAdapter parentAdapter, JavaSpecifiedPrimaryKeyJoinColumn javaColumn) {
		super(parentAdapter);
		this.javaColumn = javaColumn;
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);

		this.setSpecifiedReferencedColumnName(this.buildSpecifiedReferencedColumnName());
		this.setDefaultReferencedColumnName(this.buildDefaultReferencedColumnName());
	}


	// ********** column **********

	@Override
	public JavaSpecifiedPrimaryKeyJoinColumn getOverriddenColumn() {
		return this.javaColumn;
	}


	// ********** referenced column name **********

	public String getReferencedColumnName() {
		return (this.specifiedReferencedColumnName != null) ? this.specifiedReferencedColumnName : this.defaultReferencedColumnName;
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	protected void setSpecifiedReferencedColumnName(String name) {
		String old = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = name;
		this.firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedReferencedColumnName() {
		return this.getOverriddenColumn().getSpecifiedReferencedColumnName();
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String name) {
		String old = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = name;
		this.firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultReferencedColumnName() {
		return this.buildDefaultName();
	}


	// ********** database stuff **********

	public Table getReferencedColumnDbTable() {
		return this.parentAdapter.getReferencedColumnDbTable();
	}

	protected Column getReferencedDbColumn() {
		Table table = this.getReferencedColumnDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getReferencedColumnName());
	}

	public boolean referencedColumnIsResolved() {
		return this.getReferencedDbColumn() != null;
	}


	// ********** misc **********

	public String getTableName() {
		return this.parentAdapter.getDefaultTableName();
	}


	// ********** validation **********

	public TextRange getReferencedColumnNameTextRange() {
		return this.getValidationTextRange();
	}
}
