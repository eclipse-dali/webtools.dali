/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.jpt.jpa.core.context.VirtualReferenceTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;

public abstract class AbstractOrmVirtualReferenceTable<T extends ReferenceTable>
	extends AbstractOrmVirtualTable<T>
	implements VirtualReferenceTable
{
	protected final Vector<OrmVirtualJoinColumn> specifiedJoinColumns = new Vector<OrmVirtualJoinColumn>();
	protected final SpecifiedJoinColumnContainerAdapter specifiedJoinColumnContainerAdapter = new SpecifiedJoinColumnContainerAdapter();
	protected final ReadOnlyJoinColumn.Owner joinColumnOwner;

	protected OrmVirtualJoinColumn defaultJoinColumn;


	protected AbstractOrmVirtualReferenceTable(JpaContextNode parent) {
		super(parent);
		this.joinColumnOwner = this.buildJoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedJoinColumns();
		this.updateDefaultJoinColumn();
	}


	// ********** join columns **********

	public ListIterator<OrmVirtualJoinColumn> joinColumns() {
		return this.getJoinColumns().iterator();
	}

	protected ListIterable<OrmVirtualJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterator<OrmVirtualJoinColumn> specifiedJoinColumns() {
		return this.getSpecifiedJoinColumns().iterator();
	}

	protected ListIterable<OrmVirtualJoinColumn> getSpecifiedJoinColumns() {
		return new LiveCloneListIterable<OrmVirtualJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	public OrmVirtualJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumns.get(index);
	}

	protected void updateSpecifiedJoinColumns() {
		ContextContainerTools.update(this.specifiedJoinColumnContainerAdapter);
	}

	protected Iterable<JoinColumn> getOverriddenJoinColumns() {
		return CollectionTools.iterable(this.getOverriddenTable().specifiedJoinColumns());
	}

	protected void moveSpecifiedJoinColumn(int index, OrmVirtualJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected OrmVirtualJoinColumn addSpecifiedJoinColumn(int index, JoinColumn joinColumn) {
		OrmVirtualJoinColumn virtualJoinColumn = this.buildJoinColumn(joinColumn);
		this.addItemToList(index, virtualJoinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
		return virtualJoinColumn;
	}

	protected void removeSpecifiedJoinColumn(OrmVirtualJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	/**
	 * specified join column container adapter
	 */
	protected class SpecifiedJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmVirtualJoinColumn, JoinColumn>
	{
		public Iterable<OrmVirtualJoinColumn> getContextElements() {
			return AbstractOrmVirtualReferenceTable.this.getSpecifiedJoinColumns();
		}
		public Iterable<JoinColumn> getResourceElements() {
			return AbstractOrmVirtualReferenceTable.this.getOverriddenJoinColumns();
		}
		public JoinColumn getResourceElement(OrmVirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
		public void moveContextElement(int index, OrmVirtualJoinColumn element) {
			AbstractOrmVirtualReferenceTable.this.moveSpecifiedJoinColumn(index, element);
		}
		public void addContextElement(int index, JoinColumn element) {
			AbstractOrmVirtualReferenceTable.this.addSpecifiedJoinColumn(index, element);
		}
		public void removeContextElement(OrmVirtualJoinColumn element) {
			AbstractOrmVirtualReferenceTable.this.removeSpecifiedJoinColumn(element);
		}
	}


	// ********** default join column **********

	public OrmVirtualJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(OrmVirtualJoinColumn joinColumn) {
		OrmVirtualJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<OrmVirtualJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<OrmVirtualJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<OrmVirtualJoinColumn>instance();
	}

	protected int getDefaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultJoinColumn() {
		if (this.buildsDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(this.getOverriddenTable().getDefaultJoinColumn()));
			} else {
				this.defaultJoinColumn.update();
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}

	protected boolean buildsDefaultJoinColumn() {
		return ! this.hasSpecifiedJoinColumns();
	}


	// ********** misc **********

	protected OrmVirtualJoinColumn buildJoinColumn(JoinColumn joinColumn) {
		return this.buildJoinColumn(this.joinColumnOwner, joinColumn);
	}

	protected OrmVirtualJoinColumn buildJoinColumn(ReadOnlyJoinColumn.Owner owner, JoinColumn joinColumn) {
		return this.getContextNodeFactory().buildOrmVirtualJoinColumn(this, owner, joinColumn);
	}

	protected abstract ReadOnlyJoinColumn.Owner buildJoinColumnOwner();

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}
}
