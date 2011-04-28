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

import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmVirtualBaseColumn;

/**
 * <code>orm.xml</code> virtual join column
 */
public class GenericOrmVirtualJoinColumn
	extends AbstractOrmVirtualBaseColumn<ReadOnlyJoinColumn.Owner, JoinColumn>
	implements OrmVirtualJoinColumn
{
	protected final JoinColumn overriddenColumn;

	protected String specifiedReferencedColumnName;
	protected String defaultReferencedColumnName;


	public GenericOrmVirtualJoinColumn(XmlContextNode parent, ReadOnlyJoinColumn.Owner owner, JoinColumn overriddenColumn) {
		super(parent, owner);
		this.overriddenColumn = overriddenColumn;
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();

		this.setSpecifiedReferencedColumnName(this.buildSpecifiedReferencedColumnName());
		this.setDefaultReferencedColumnName(this.buildDefaultReferencedColumnName());
	}


	// ********** column **********

	@Override
	public JoinColumn getOverriddenColumn() {
		return this.overriddenColumn;
	}

	public boolean isDefault() {
		return this.owner.joinColumnIsDefault(this);
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
		return MappingTools.buildJoinColumnDefaultReferencedColumnName(this.owner);
	}


	// ********** misc **********

	@Override
	protected String buildDefaultName() {
		return MappingTools.buildJoinColumnDefaultName(this, this.owner);
	}
}
