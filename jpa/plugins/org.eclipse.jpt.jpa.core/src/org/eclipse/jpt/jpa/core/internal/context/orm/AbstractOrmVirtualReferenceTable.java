/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;

import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyReferenceTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualReferenceTable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmVirtualReferenceTable<P extends JpaContextModel, T extends ReadOnlyReferenceTable>
	extends AbstractOrmVirtualTable<P, T>
	implements VirtualReferenceTable
{
	protected final ContextListContainer<VirtualJoinColumn, JoinColumn> specifiedJoinColumnContainer;
	protected final JoinColumn.Owner joinColumnOwner;

	protected VirtualJoinColumn defaultJoinColumn;


	protected AbstractOrmVirtualReferenceTable(P parent, Owner owner, T overridenTable) {
		super(parent, owner, overridenTable);
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.specifiedJoinColumnContainer = this.buildSpecifiedJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedJoinColumns();
		this.updateDefaultJoinColumn();
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
		return this.specifiedJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public VirtualJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.getContextElement(index);
	}

	protected void updateSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.update();
	}

	protected ListIterable<JoinColumn> getOverriddenJoinColumns() {
		return new SuperListIterableWrapper<JoinColumn>(this.getOverriddenTable().getSpecifiedJoinColumns());
	}

	protected void moveSpecifiedJoinColumn(int index, VirtualJoinColumn joinColumn) {
		this.specifiedJoinColumnContainer.moveContextElement(index, joinColumn);
	}

	protected VirtualJoinColumn addSpecifiedJoinColumn(int index, JoinColumn joinColumn) {
		return this.specifiedJoinColumnContainer.addContextElement(index, joinColumn);
	}

	protected void removeSpecifiedJoinColumn(VirtualJoinColumn joinColumn) {
		this.specifiedJoinColumnContainer.removeContextElement(joinColumn);
	}

	protected ContextListContainer<VirtualJoinColumn, JoinColumn> buildSpecifiedJoinColumnContainer() {
		return new SpecifiedJoinColumnContainer();
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedJoinColumnContainer
		extends ContextListContainer<VirtualJoinColumn, JoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_JOIN_COLUMNS_LIST;
		}
		@Override
		protected VirtualJoinColumn buildContextElement(JoinColumn resourceElement) {
			return AbstractOrmVirtualReferenceTable.this.buildJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<JoinColumn> getResourceElements() {
			return AbstractOrmVirtualReferenceTable.this.getOverriddenJoinColumns();
		}
		@Override
		protected JoinColumn getResourceElement(VirtualJoinColumn contextElement) {
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

	protected VirtualJoinColumn buildJoinColumn(JoinColumn joinColumn) {
		return this.buildJoinColumn(this.joinColumnOwner, joinColumn);
	}

	protected VirtualJoinColumn buildJoinColumn(JoinColumn.Owner columnOwner, JoinColumn joinColumn) {
		return this.getContextModelFactory().buildOrmVirtualJoinColumn(this, columnOwner, joinColumn);
	}

	protected abstract JoinColumn.Owner buildJoinColumnOwner();

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
