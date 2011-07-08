/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualColumn;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Virtual <code>orm.xml</code> attribute override
 */
public class GenericOrmVirtualAttributeOverride
	extends AbstractOrmVirtualOverride<OrmAttributeOverrideContainer>
	implements OrmVirtualAttributeOverride, OrmVirtualColumn.Owner
{
	protected final OrmVirtualColumn column;


	public GenericOrmVirtualAttributeOverride(OrmAttributeOverrideContainer parent, String name) {
		super(parent, name);
		this.column = this.buildColumn();
	}

	@Override
	public void update() {
		super.update();
		this.column.update();
	}

	@Override
	public OrmAttributeOverride convertToSpecified() {
		return (OrmAttributeOverride) super.convertToSpecified();
	}


	// ********** column **********

	public OrmVirtualColumn getColumn() {
		return this.column;
	}

	/**
	 * The original column should be available (since the presence of its
	 * attribute is what precipitated the creation of the virtual override).
	 */
	protected OrmVirtualColumn buildColumn() {
		return this.getContextNodeFactory().buildOrmVirtualColumn(this, this);
	}


	// ********** column owner implementation **********

	@Override
	public String getDefaultTableName() {
		String overriddenColumnTable = this.getOverriddenColumnTable();
		return (overriddenColumnTable != null) ? overriddenColumnTable : super.getDefaultTableName();
	}

	protected String getOverriddenColumnTable() {
		ReadOnlyColumn overriddenColumn = this.resolveOverriddenColumn();
		// pretty sure this is the *specified* table...
		return (overriddenColumn == null) ? null : overriddenColumn.getSpecifiedTable();
	}

	public String getDefaultColumnName() {
		String overriddenColumnName = this.getOverriddenColumnName();
		return (overriddenColumnName != null) ? overriddenColumnName : this.name;
	}

	protected String getOverriddenColumnName() {
		ReadOnlyColumn overriddenColumn = this.resolveOverriddenColumn();
		return (overriddenColumn == null) ? null : overriddenColumn.getName();
	}

	public ReadOnlyColumn resolveOverriddenColumn() {
		return this.getContainer().resolveOverriddenColumn(this.name);
	}

	public JptValidator buildColumnValidator(ReadOnlyNamedColumn col, NamedColumnTextRangeResolver textRangeResolver) {
		return this.getContainer().buildColumnValidator(this, (ReadOnlyBaseColumn) col, this, (BaseColumnTextRangeResolver) textRangeResolver);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.column.validate(messages, reporter);
	}
}
