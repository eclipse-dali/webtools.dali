/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmNamedColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;

/**
 * <code>orm.xml</code> primary key join column
 */
public class GenericOrmPrimaryKeyJoinColumn
	extends AbstractOrmNamedColumn<BaseJoinColumn.ParentAdapter, XmlPrimaryKeyJoinColumn>
	implements OrmSpecifiedPrimaryKeyJoinColumn
{
	/** @see org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmNamedColumn#AbstractOrmNamedColumn(org.eclipse.jpt.jpa.core.context.NamedColumn.ParentAdapter, org.eclipse.jpt.jpa.core.resource.orm.XmlNamedColumn) */
	protected /* final */ XmlPrimaryKeyJoinColumn xmlColumn;  // null for default pk join columns

	protected String specifiedReferencedColumnName;
	protected String defaultReferencedColumnName;


	public GenericOrmPrimaryKeyJoinColumn(BaseJoinColumn.ParentAdapter parentAdapter, XmlPrimaryKeyJoinColumn xmlColumn) {
		super(parentAdapter, xmlColumn);
		this.specifiedReferencedColumnName = this.buildSpecifiedReferencedColumnName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedReferencedColumnName_(this.buildSpecifiedReferencedColumnName());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultReferencedColumnName(this.buildDefaultReferencedColumnName());
	}


	// ********** XML column **********

	@Override
	public XmlPrimaryKeyJoinColumn getXmlColumn() {
		return this.xmlColumn;
	}

	@Override
	protected void setXmlColumn(XmlPrimaryKeyJoinColumn xmlColumn) {
		this.xmlColumn = xmlColumn;
	}

	/**
	 * primary key join columns are part of a collection;
	 * the 'primary-key-join-column' element will be removed/added
	 * when the XML join column is removed from/added to
	 * the parent's collection
	 */
	@Override
	protected XmlPrimaryKeyJoinColumn buildXmlColumn() {
		throw new IllegalStateException("XML primary key join column is missing"); //$NON-NLS-1$
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
		this.xmlColumn.setReferencedColumnName(name);
	}

	protected void setSpecifiedReferencedColumnName_(String name) {
		String old = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = name;
		this.firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedReferencedColumnName() {
		return (this.xmlColumn == null) ? null : this.xmlColumn.getReferencedColumnName();
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String name) {
		String old = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = name;
		this.firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, old, name);
	}

	// TODO not correct when we start supporting
	// primary key join columns in 1-1 mappings
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

	public void initializeFrom(OrmSpecifiedPrimaryKeyJoinColumn oldColumn) {
		super.initializeFrom(oldColumn);
		this.setSpecifiedReferencedColumnName(oldColumn.getSpecifiedReferencedColumnName());
	}

	public void initializeFrom(OrmVirtualPrimaryKeyJoinColumn virtualColumn) {
		super.initializeFrom(virtualColumn);
		this.setSpecifiedReferencedColumnName(virtualColumn.getSpecifiedReferencedColumnName());
	}

	@Override
	public String getTableName() {
		return this.parentAdapter.getDefaultTableName();
	}


	// ********** validation **********

	public TextRange getReferencedColumnNameTextRange() {
		return this.getValidationTextRange(this.xmlColumn.getReferencedColumnNameTextRange());
	}

	// ********** completion proposals **********

	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.referencedColumnNameTouches(pos)) {
			return this.getCandidateReferencedColumnNames();
		}
		return null;
	}

	public boolean referencedColumnNameTouches(int pos) {
		XmlPrimaryKeyJoinColumn column = this.getXmlColumn();
		return (column != null) && (column.referencedColumnNameTouches(pos));
	}

	protected Iterable<String> getCandidateReferencedColumnNames() {
		Table table = this.parentAdapter.getReferencedColumnDbTable();
		return (table != null) ? table.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}
}
