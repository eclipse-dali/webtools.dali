/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.VirtualNamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedColumn;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code><ul>
 * <li>column
 * <li>join column
 * <li>discriminator column
 * <li>order column
 * <li>primary key join column
 * </ul>
 * <strong>NB:</strong> any subclass that directly holds its XML column must:<ul>
 * <li>call the "super" constructor that takes an XML column
 *     {@link #AbstractOrmNamedColumn(NamedColumn.ParentAdapter, XmlNamedColumn)}
 * <li>override {@link #setXmlColumn(XmlNamedColumn)} to set the XML column
 *     so it is in place before the column's state (e.g. {@link #specifiedName})
 *     is initialized
 * </ul>
 * Typically, a column belonging to a list of columns will directly hold its XML
 * column; since the context column only exists if the XML column exists.
 */
public abstract class AbstractOrmNamedColumn<PA extends NamedColumn.ParentAdapter, X extends XmlNamedColumn>
	extends AbstractOrmXmlContextModel<JpaContextModel>
	implements OrmSpecifiedNamedColumn
{
	protected final PA parentAdapter;

	protected String specifiedName;
	protected String defaultName;

	protected String columnDefinition;

	protected Table dbTable;

	// ********** constructor/initialization **********

	protected AbstractOrmNamedColumn(PA parentAdapter) {
		this(parentAdapter, null);
	}

	protected AbstractOrmNamedColumn(PA parentAdapter, X xmlColumn) {
		super(parentAdapter.getColumnParent());
		this.parentAdapter = parentAdapter;
		this.setXmlColumn(xmlColumn);
		this.specifiedName = this.buildSpecifiedName();
		this.columnDefinition = this.buildColumnDefinition();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedName_(this.buildSpecifiedName());
		this.setColumnDefinition_(this.buildColumnDefinition());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultName(this.buildDefaultName());
		this.setDbTable(this.buildDbTable());
	}


	// ********** XML column **********

	/**
	 * Return <code>null</code> if XML column does not exists.
	 */
	public abstract X getXmlColumn();

	/**
	 * see class comment...
	 */
	protected void setXmlColumn(X xmlColumn) {
		if (xmlColumn != null) {
			throw new IllegalArgumentException("this method must be overridden if the XML column is not null: " + xmlColumn); //$NON-NLS-1$
		}
	}

	/**
	 * Build the XML column if it does not exist.
	 */
	protected X getXmlColumnForUpdate() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn != null) ? xmlColumn : this.buildXmlColumn();
	}

	protected abstract X buildXmlColumn();

	protected void removeXmlColumnIfUnset() {
		if (this.getXmlColumn().isUnset()) {
			this.removeXmlColumn();
		}
	}

	protected abstract void removeXmlColumn();


	// ********** name **********

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String name) {
		if (ObjectTools.notEquals(this.specifiedName, name)) {
			X xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedName_(name);
			xmlColumn.setName(name);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getName();
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

	public void setColumnDefinition(String columnDefinition) {
		if (ObjectTools.notEquals(this.columnDefinition, columnDefinition)) {
			X xmlColumn = this.getXmlColumnForUpdate();
			this.setColumnDefinition_(columnDefinition);
			xmlColumn.setColumnDefinition(columnDefinition);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setColumnDefinition_(String columnDefinition) {
		String old = this.columnDefinition;
		this.columnDefinition = columnDefinition;
		this.firePropertyChanged(COLUMN_DEFINITION_PROPERTY, old, columnDefinition);
	}

	protected String buildColumnDefinition() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getColumnDefinition();
	}


	// ********** database stuff **********

	protected Column getDbColumn() {
		return (this.dbTable == null) ? null : this.dbTable.getColumnForIdentifier(this.getName());
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
	 * Return the name of the column's table. This is overridden in
	 * {@link AbstractOrmBaseColumn} (and other places) where a table can be
	 * defined.
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
		this.buildColumnValidator().validate(messages, reporter);
	}

	protected JpaValidator buildColumnValidator() {
		return this.parentAdapter.buildColumnValidator(this);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlColumnTextRange();
		return (textRange != null) ? textRange : this.parentAdapter.getValidationTextRange();
	}

	protected TextRange getXmlColumnTextRange() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getValidationTextRange();
	}

	public TextRange getNameValidationTextRange() {
		return this.getValidationTextRange(this.getXmlColumnNameTextRange());
	}

	protected TextRange getXmlColumnNameTextRange() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getNameTextRange();
	}

	// ********** completion proposals **********

	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.columnNameTouches(pos)) {
			return this.getCandidateColumnNames();
		}
		return null;
	}

	protected boolean columnNameTouches(int pos) {
		X column = this.getXmlColumn();
		return (column != null) && (column.columnNameTouches(pos));
	}

	protected Iterable<String> getCandidateColumnNames() {
		return (this.dbTable != null) ? this.dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}
	
	// ********** misc **********

	public boolean isVirtual() {
		return false;
	}

	protected void initializeFrom(OrmSpecifiedNamedColumn oldColumn) {
		this.setSpecifiedName(oldColumn.getSpecifiedName());
		this.setColumnDefinition(oldColumn.getColumnDefinition());
	}

	protected void initializeFrom(VirtualNamedColumn virtualColumn) {
		this.setSpecifiedName(virtualColumn.getName());
		this.setColumnDefinition(virtualColumn.getColumnDefinition());
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
