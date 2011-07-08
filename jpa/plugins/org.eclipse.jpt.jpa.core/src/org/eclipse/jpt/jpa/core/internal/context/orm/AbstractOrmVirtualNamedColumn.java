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

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.VirtualNamedColumn;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> virtual<ul>
 * <li>column
 * <li>join column
 * <li>primary key join column
 * </ul>
 * <strong>NB:</strong> all state is sync'ed/updated in {@link #update()}
 * because <em>all</em> of its derived from the context model (i.e. none of it
 * is derived from the resource model).
 */
public abstract class AbstractOrmVirtualNamedColumn<O extends ReadOnlyNamedColumn.Owner, C extends ReadOnlyNamedColumn>
	extends AbstractOrmXmlContextNode
	implements VirtualNamedColumn, OrmReadOnlyNamedColumn
{
	protected final O owner;

	protected String specifiedName;
	protected String defaultName;

	protected String columnDefinition;


	protected AbstractOrmVirtualNamedColumn(XmlContextNode parent, O owner) {
		super(parent);
		this.owner = owner;
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();

		this.setSpecifiedName(this.buildSpecifiedName());
		this.setDefaultName(this.buildDefaultName());

		this.setColumnDefinition(this.buildColumnDefinition());
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
		return this.owner.getDefaultColumnName();
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
		return this.owner.resolveDbTable(this.getTable());
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
		return this.getParent().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange();
	}


	// ********** misc **********

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}

	/**
	 * This is used by the subclasses that implement {@link ReadOnlyBaseJoinColumn#isVirtual()}.
	 */
	public boolean isVirtual() {
		return true;
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
