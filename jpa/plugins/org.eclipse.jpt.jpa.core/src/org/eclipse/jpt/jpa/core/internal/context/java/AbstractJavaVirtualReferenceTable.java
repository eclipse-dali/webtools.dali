/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.jpt.jpa.core.context.VirtualReferenceTable;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;

public abstract class AbstractJavaVirtualReferenceTable<T extends ReferenceTable>
	extends AbstractJavaVirtualTable<T>
	implements VirtualReferenceTable
{
	protected final Vector<JavaVirtualJoinColumn> specifiedJoinColumns = new Vector<JavaVirtualJoinColumn>();
	protected final SpecifiedJoinColumnContainerAdapter specifiedJoinColumnContainerAdapter = new SpecifiedJoinColumnContainerAdapter();
	protected final ReadOnlyJoinColumn.Owner joinColumnOwner;

	protected JavaVirtualJoinColumn defaultJoinColumn;


	protected AbstractJavaVirtualReferenceTable(JavaJpaContextNode parent) {
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

	public ListIterator<JavaVirtualJoinColumn> joinColumns() {
		return this.getJoinColumns().iterator();
	}

	protected ListIterable<JavaVirtualJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterator<JavaVirtualJoinColumn> specifiedJoinColumns() {
		return this.getSpecifiedJoinColumns().iterator();
	}

	protected ListIterable<JavaVirtualJoinColumn> getSpecifiedJoinColumns() {
		return new LiveCloneListIterable<JavaVirtualJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	public JavaVirtualJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumns.get(index);
	}

	protected void updateSpecifiedJoinColumns() {
		ContextContainerTools.update(this.specifiedJoinColumnContainerAdapter);
	}

	protected Iterable<JoinColumn> getOverriddenJoinColumns() {
		return CollectionTools.iterable(this.getOverriddenTable().specifiedJoinColumns());
	}

	protected void moveSpecifiedJoinColumn(int index, JavaVirtualJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected JavaVirtualJoinColumn addSpecifiedJoinColumn(int index, JoinColumn joinColumn) {
		JavaVirtualJoinColumn virtualJoinColumn = this.buildJoinColumn(joinColumn);
		this.addItemToList(index, virtualJoinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
		return virtualJoinColumn;
	}

	protected void removeSpecifiedJoinColumn(JavaVirtualJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	/**
	 * specified join column container adapter
	 */
	protected class SpecifiedJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<JavaVirtualJoinColumn, JoinColumn>
	{
		public Iterable<JavaVirtualJoinColumn> getContextElements() {
			return AbstractJavaVirtualReferenceTable.this.getSpecifiedJoinColumns();
		}
		public Iterable<JoinColumn> getResourceElements() {
			return AbstractJavaVirtualReferenceTable.this.getOverriddenJoinColumns();
		}
		public JoinColumn getResourceElement(JavaVirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
		public void moveContextElement(int index, JavaVirtualJoinColumn element) {
			AbstractJavaVirtualReferenceTable.this.moveSpecifiedJoinColumn(index, element);
		}
		public void addContextElement(int index, JoinColumn element) {
			AbstractJavaVirtualReferenceTable.this.addSpecifiedJoinColumn(index, element);
		}
		public void removeContextElement(JavaVirtualJoinColumn element) {
			AbstractJavaVirtualReferenceTable.this.removeSpecifiedJoinColumn(element);
		}
	}


	// ********** default join column **********

	public JavaVirtualJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(JavaVirtualJoinColumn joinColumn) {
		JavaVirtualJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<JavaVirtualJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<JavaVirtualJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<JavaVirtualJoinColumn>instance();
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

	protected JavaVirtualJoinColumn buildJoinColumn(JoinColumn joinColumn) {
		return this.buildJoinColumn(this.joinColumnOwner, joinColumn);
	}

	protected JavaVirtualJoinColumn buildJoinColumn(ReadOnlyJoinColumn.Owner owner, JoinColumn joinColumn) {
		return this.getJpaFactory().buildJavaVirtualJoinColumn(this, owner, joinColumn);
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
