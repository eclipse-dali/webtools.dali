/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.VirtualNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java virtual<ul>
 * <li>column
 * <li>join column
 * </ul>
 * <strong>NB:</strong> all state is sync'ed/updated in {@link #update(IProgressMonitor)}
 * because <em>all</em> of it is derived from the context model (i.e. none of it
 * is derived from the resource model).
 */
public abstract class AbstractJavaVirtualNamedColumn<PA extends NamedColumn.ParentAdapter, C extends NamedColumn>
	extends AbstractJavaContextModel<JpaContextModel>
	implements VirtualNamedColumn
{
	protected final PA parentAdapter;

	protected String specifiedName;
	protected String defaultName;

	protected String columnDefinition;

	protected Table dbTable;

	protected AbstractJavaVirtualNamedColumn(PA parentAdapter) {
		super(parentAdapter.getColumnParent());
		this.parentAdapter = parentAdapter;
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);

		this.setSpecifiedName(this.buildSpecifiedName());
		this.setDefaultName(this.buildDefaultName());

		this.setColumnDefinition(this.buildColumnDefinition());

		this.setDbTable(this.buildDbTable());
	}


	// ********** column **********

	/**
	 * This should never return <code>null</code>.
	 */
	public abstract C getOverriddenColumn();


	// ********** name **********

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	protected void setSpecifiedName(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		return this.getOverriddenColumn().getSpecifiedName();
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String name) {
		String old = this.defaultName;
		this.defaultName = name;
		this.firePropertyChanged(DEFAULT_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultName() {
		return this.parentAdapter.getDefaultColumnName(this);
	}


	// ********** column definition **********

	public String getColumnDefinition() {
		return this.columnDefinition;
	}

	protected void setColumnDefinition(String columnDefinition) {
		String old = this.columnDefinition;
		this.columnDefinition = columnDefinition;
		this.firePropertyChanged(COLUMN_DEFINITION_PROPERTY, old, columnDefinition);
	}

	protected String buildColumnDefinition() {
		return this.getOverriddenColumn().getColumnDefinition();
	}


	// ********** database stuff **********

	protected Column getDbColumn() {
		Table table = this.getDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getName());
	}

	public Table getDbTable() {
		return this.dbTable;
	}

	protected void setDbTable(Table dbTable) {
		Table old = this.dbTable;
		this.dbTable = dbTable;
		this.firePropertyChanged(DB_TABLE_PROPERTY, old, dbTable);
	}

	protected Table buildDbTable() {
		return this.parentAdapter.resolveDbTable(this.getTableName());
	}

	/**
	 * Return the name of the column's table. This is overridden
	 * in {@link AbstractJavaVirtualBaseColumn} where a table can be defined.
	 */
	public String getTableName() {
		return this.parentAdapter.getDefaultTableName();
	}

	public boolean isResolved() {
		return this.getDbColumn() != null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.buildValidator().validate(messages, reporter);
	}

	protected JpaValidator buildValidator() {
		return this.parentAdapter.buildColumnValidator(this);
	}

	public TextRange getValidationTextRange() {
		return this.parent.getValidationTextRange();
	}

	public TextRange getNameValidationTextRange() {
		return this.getValidationTextRange();
	}


	// ********** misc **********

	public boolean isVirtual() {
		return true;
	}

	@Override
	public void toString(StringBuilder sb) {
		String table = this.getTableName();
		if (table != null) {
			sb.append(table);
			sb.append('.');
		}
		sb.append(this.getName());
	}
}
