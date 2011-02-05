/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmBaseColumn;
import org.eclipse.jpt.core.internal.context.orm.OrmJoinColumnTextRangeResolver;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;

/**
 * <code>orm.xml</code> join column
 */
public class GenericOrmJoinColumn
	extends AbstractOrmBaseColumn<XmlJoinColumn, OrmJoinColumn.Owner>
	implements OrmJoinColumn
{
	/** @see org.eclipse.jpt.core.internal.context.orm.AbstractOrmNamedColumn#AbstractOrmNamedColumn(XmlContextNode, org.eclipse.jpt.core.context.orm.OrmNamedColumn.Owner, org.eclipse.jpt.core.resource.orm.AbstractXmlNamedColumn) */
	protected /* final */ XmlJoinColumn xmlColumn;  // null for default join columns

	protected String specifiedReferencedColumnName;
	protected String defaultReferencedColumnName;


	public GenericOrmJoinColumn(XmlContextNode parent, OrmJoinColumn.Owner owner) {
		this(parent, owner, null);
	}

	public GenericOrmJoinColumn(XmlContextNode parent, OrmJoinColumn.Owner owner, XmlJoinColumn xmlColumn) {
		super(parent, owner, xmlColumn);
		this.specifiedReferencedColumnName = this.buildSpecifiedReferencedColumnName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedReferencedColumnName_(this.buildSpecifiedReferencedColumnName());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultReferencedColumnName(this.buildDefaultReferencedColumnName());
	}


	// ********** XML column **********

	@Override
	public XmlJoinColumn getXmlColumn() {
		return this.xmlColumn;
	}

	@Override
	protected void setXmlColumn(XmlJoinColumn xmlColumn) {
		this.xmlColumn = xmlColumn;
	}

	/**
	 * join columns are part of a collection;
	 * the 'join-column' element will be removed/added
	 * when the XML join column is removed from/added to
	 * the owner's collection
	 */
	@Override
	protected XmlJoinColumn buildXmlColumn() {
		throw new IllegalStateException("XML join column is missing"); //$NON-NLS-1$
	}

	/**
	 * @see #buildXmlColumn()
	 */
	@Override
	protected void removeXmlColumn() {
		// do nothing
	}


	// ********** referenced column name **********

	public String getReferencedColumnName() {
		return (this.specifiedReferencedColumnName != null) ? this.specifiedReferencedColumnName : this.defaultReferencedColumnName;
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	public void setSpecifiedReferencedColumnName(String name) {
		this.setSpecifiedReferencedColumnName_(name);
		this.getXmlColumn().setReferencedColumnName(name);
	}

	protected void setSpecifiedReferencedColumnName_(String name) {
		String old = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = name;
		this.firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedReferencedColumnName() {
		XmlJoinColumn joinColumn = this.getXmlColumn();
		return (joinColumn == null) ? null : joinColumn.getReferencedColumnName();
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


	// ********** database stuff **********

	public Table getReferencedColumnDbTable() {
		return this.owner.getReferencedColumnDbTable();
	}

	protected Column getReferencedDbColumn() {
		Table table = this.getReferencedColumnDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getReferencedColumnName());
	}

	public boolean referencedColumnIsResolved() {
		return this.getReferencedDbColumn() != null;
	}


	// ********** misc **********

	public void initializeFrom(ReadOnlyJoinColumn oldColumn) {
		super.initializeFrom(oldColumn);
		this.setSpecifiedReferencedColumnName(oldColumn.getSpecifiedReferencedColumnName());
	}

	public void initializeFromVirtual(ReadOnlyJoinColumn virtualColumn) {
		super.initializeFromVirtual(virtualColumn);
		this.setSpecifiedReferencedColumnName(virtualColumn.getReferencedColumnName());
	}

	public boolean isDefault() {
		return this.owner.joinColumnIsDefault(this);
	}

	@Override
	protected String buildDefaultName() {
		return MappingTools.buildJoinColumnDefaultName(this, this.owner);
	}

	public TextRange getReferencedColumnNameTextRange() {
		return this.getTextRange(this.xmlColumn.getReferencedColumnNameTextRange());
	}

	@Override
	protected NamedColumnTextRangeResolver buildTextRangeResolver() {
		return new OrmJoinColumnTextRangeResolver(this);
	}
}
