/*******************************************************************************
 * Copyright (c) 2007, 2011s Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlNamedColumn;
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
 *     {@link #AbstractOrmNamedColumn(XmlContextNode, OrmNamedColumn.Owner, AbstractXmlNamedColumn)}
 * <li>override {@link #setXmlColumn(AbstractXmlNamedColumn)} to set the XML column
 *     so it is in place before the column's state (e.g. {@link #specifiedName})
 *     is initialized
 * </ul>
 * Typically, a column belonging to a list of columns will directly hold its XML
 * column; since the context column only exists if the XML column exists.
 */
public abstract class AbstractOrmNamedColumn<X extends AbstractXmlNamedColumn, O extends OrmNamedColumn.Owner>
	extends AbstractOrmXmlContextNode
	implements OrmNamedColumn
{
	protected final O owner;

	protected String specifiedName;
	protected String defaultName;

	protected String columnDefinition;


	// ********** constructor/initialization **********

	protected AbstractOrmNamedColumn(XmlContextNode parent, O owner) {
		this(parent, owner, null);
	}

	protected AbstractOrmNamedColumn(XmlContextNode parent, O owner, X xmlColumn) {
		super(parent);
		this.owner = owner;
		this.setXmlColumn(xmlColumn);
		this.specifiedName = this.buildSpecifiedName();
		this.columnDefinition = this.buildColumnDefinition();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedName_(this.buildSpecifiedName());
		this.setColumnDefinition_(this.buildColumnDefinition());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultName(this.buildDefaultName());
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
		if (this.valuesAreDifferent(this.specifiedName, name)) {
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
		return this.owner.getDefaultColumnName();
	}


	// ********** column definition **********

	public String getColumnDefinition() {
		return this.columnDefinition;
	}

	public void setColumnDefinition(String columnDefinition) {
		if (this.valuesAreDifferent(this.columnDefinition, columnDefinition)) {
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
		Table table = this.getDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getName());
	}

	public Table getDbTable() {
		return this.owner.resolveDbTable(this.getTable());
	}

	/**
	 * Return the name of the column's table. This is overridden in
	 * {@link AbstractOrmBaseColumn} (and other places) where a table can be
	 * defined.
	 */
	public String getTable() {
		return this.owner.getTypeMapping().getPrimaryTableName();
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

	protected JptValidator buildColumnValidator() {
		return this.owner.buildColumnValidator(this, this.buildTextRangeResolver());
	}

	protected NamedColumnTextRangeResolver buildTextRangeResolver() {
		return new OrmNamedColumnTextRangeResolver(this);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlColumnTextRange();
		return (textRange != null) ? textRange : this.owner.getValidationTextRange();
	}

	protected TextRange getXmlColumnTextRange() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.getXmlColumnNameTextRange());
	}

	protected TextRange getXmlColumnNameTextRange() {
		X xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getNameTextRange();
	}


	// ********** misc **********

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}

	/**
	 * This is used by the subclasses that implement {@link BaseJoinColumn}.
	 */
	public boolean isVirtual() {
		return false;
	}

	protected void initializeFrom(ReadOnlyNamedColumn oldColumn) {
		this.setSpecifiedName(oldColumn.getSpecifiedName());
		this.setColumnDefinition(oldColumn.getColumnDefinition());
	}

	protected void initializeFromVirtual(ReadOnlyNamedColumn virtualColumn) {
		this.setSpecifiedName(virtualColumn.getName());
		this.setColumnDefinition(virtualColumn.getColumnDefinition());
	}

	@Override
	public void toString(StringBuilder sb) {
		String table = this.getTable();
		if (table != null) {
			sb.append(table);
			sb.append('.');
		}
		sb.append(this.getName());
	}
}
