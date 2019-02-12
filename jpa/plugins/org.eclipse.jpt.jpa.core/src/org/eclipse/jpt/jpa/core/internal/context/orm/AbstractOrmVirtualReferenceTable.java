/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualReferenceTable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmVirtualReferenceTable<P extends JpaContextModel, PA extends Table.ParentAdapter<P>, T extends ReferenceTable>
	extends AbstractOrmVirtualTable<P, PA, T>
	implements VirtualReferenceTable
{
	protected final ContextListContainer<VirtualJoinColumn, JoinColumn> specifiedJoinColumnContainer;
	protected final JoinColumn.ParentAdapter joinColumnParentAdapter;

	protected VirtualJoinColumn defaultJoinColumn;


	protected AbstractOrmVirtualReferenceTable(PA parentAdapter, T overriddenTable) {
		super(parentAdapter, overriddenTable);
		this.joinColumnParentAdapter = this.buildJoinColumnParentAdapter();
		this.specifiedJoinColumnContainer = this.buildSpecifiedJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateSpecifiedJoinColumns(monitor);
		this.updateDefaultJoinColumn(monitor);
	}


	// ********** join columns **********

	public ListIterable<VirtualJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int getJoinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterable<VirtualJoinColumn> getSpecifiedJoinColumns() {
		return this.specifiedJoinColumnContainer;
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public VirtualJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.get(index);
	}

	protected void updateSpecifiedJoinColumns(IProgressMonitor monitor) {
		this.specifiedJoinColumnContainer.update(monitor);
	}

	protected ListIterable<JoinColumn> getOverriddenJoinColumns() {
		return new SuperListIterableWrapper<JoinColumn>(this.getOverriddenTable().getSpecifiedJoinColumns());
	}

	protected void moveSpecifiedJoinColumn(int index, VirtualJoinColumn joinColumn) {
		this.specifiedJoinColumnContainer.move(index, joinColumn);
	}

	protected VirtualJoinColumn addSpecifiedJoinColumn(int index, JoinColumn joinColumn) {
		return this.specifiedJoinColumnContainer.addContextElement(index, joinColumn);
	}

	protected void removeSpecifiedJoinColumn(VirtualJoinColumn joinColumn) {
		this.specifiedJoinColumnContainer.remove(joinColumn);
	}

	protected ContextListContainer<VirtualJoinColumn, JoinColumn> buildSpecifiedJoinColumnContainer() {
		return this.buildVirtualContextListContainer(SPECIFIED_JOIN_COLUMNS_LIST, new SpecifiedJoinColumnContainerAdapter());
	}

	/**
	 * specified join column container adapter
	 */
	public class SpecifiedJoinColumnContainerAdapter
		extends AbstractContainerAdapter<VirtualJoinColumn, JoinColumn>
	{
		public VirtualJoinColumn buildContextElement(JoinColumn resourceElement) {
			return AbstractOrmVirtualReferenceTable.this.buildJoinColumn(resourceElement);
		}
		public ListIterable<JoinColumn> getResourceElements() {
			return AbstractOrmVirtualReferenceTable.this.getOverriddenJoinColumns();
		}
		public JoinColumn extractResourceElement(VirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}


	// ********** default join column **********

	public VirtualJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(VirtualJoinColumn joinColumn) {
		VirtualJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<VirtualJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<VirtualJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<VirtualJoinColumn>instance();
	}

	protected int getDefaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultJoinColumn(IProgressMonitor monitor) {
		if (this.buildsDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(this.getOverriddenTable().getDefaultJoinColumn()));
			} else {
				this.defaultJoinColumn.update(monitor);
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}

	protected boolean buildsDefaultJoinColumn() {
		return ! this.hasSpecifiedJoinColumns();
	}


	// ********** misc **********

	protected VirtualJoinColumn buildJoinColumn(JoinColumn joinColumn) {
		return this.buildJoinColumn(this.joinColumnParentAdapter, joinColumn);
	}

	protected VirtualJoinColumn buildJoinColumn(JoinColumn.ParentAdapter columnParentAdapter, JoinColumn joinColumn) {
		return this.getContextModelFactory().buildOrmVirtualJoinColumn(columnParentAdapter, joinColumn);
	}

	protected abstract JoinColumn.ParentAdapter buildJoinColumnParentAdapter();

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		boolean continueValidating = this.buildTableValidator().validate(messages, reporter);

		//join column validation will handle the check for whether to validate against the database
		//some validation messages are not database specific. If the database validation for the
		//table fails we will stop there and not validate the join columns at all
		if (continueValidating) {
			this.validateJoinColumns(messages, reporter);
		}
	}

	protected void validateJoinColumns(List<IMessage> messages, IReporter reporter) {
		this.validateModels(this.getJoinColumns(), messages, reporter);
	}
}
