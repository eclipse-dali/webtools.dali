/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.ModifiableJoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyReferenceTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiableJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmReferenceTable;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmTable;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlReferenceTable;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> join table or collection table
 */
public abstract class GenericOrmReferenceTable<P extends JpaContextModel, X extends AbstractXmlReferenceTable>
	extends AbstractOrmTable<P, X>
	implements OrmReferenceTable
{
	protected final ContextListContainer<OrmModifiableJoinColumn, XmlJoinColumn> specifiedJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner joinColumnOwner;

	protected OrmModifiableJoinColumn defaultJoinColumn;


	protected GenericOrmReferenceTable(P parent, Owner owner) {
		super(parent, owner);
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.specifiedJoinColumnContainer = this.buildSpecifiedJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSpecifiedJoinColumns();
	}

	@Override
	public void update() {
		super.update();
		this.updateModels(this.getSpecifiedJoinColumns());
		this.updateDefaultJoinColumn();
	}


	// ********** join columns **********

	public ListIterable<OrmModifiableJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int getJoinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}

	public void convertDefaultJoinColumnToSpecified() {
		MappingTools.convertReferenceTableDefaultToSpecifiedJoinColumn(this);
	}


	// ********** specified join columns **********

	public ListIterable<OrmModifiableJoinColumn> getSpecifiedJoinColumns() {
		return this.specifiedJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public OrmModifiableJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.getContextElement(index);
	}

	public OrmModifiableJoinColumn addSpecifiedJoinColumn() {
		return this.addSpecifiedJoinColumn(this.getSpecifiedJoinColumnsSize());
	}

	public OrmModifiableJoinColumn addSpecifiedJoinColumn(int index) {
		X xmlTable = this.getXmlTableForUpdate();
		XmlJoinColumn xmlJoinColumn = this.buildXmlJoinColumn();
		OrmModifiableJoinColumn joinColumn = this.specifiedJoinColumnContainer.addContextElement(index, xmlJoinColumn);
		xmlTable.getJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlJoinColumn buildXmlJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlJoinColumn();
	}

	public void removeSpecifiedJoinColumn(ModifiableJoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumnContainer.indexOfContextElement((OrmModifiableJoinColumn) joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.specifiedJoinColumnContainer.removeContextElement(index);
		this.getXmlTable().getJoinColumns().remove(index);
		this.removeXmlTableIfUnset();
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.specifiedJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
		this.getXmlTable().getJoinColumns().move(targetIndex, sourceIndex);
	}

	public void clearSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.clearContextList();
		this.getXmlTable().getJoinColumns().clear();
	}

	protected void syncSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlJoinColumn> getXmlJoinColumns() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ?
				EmptyListIterable.<XmlJoinColumn>instance() :
				// clone to reduce chance of concurrency problems
				IterableTools.cloneLive(xmlTable.getJoinColumns());
	}

	protected ContextListContainer<OrmModifiableJoinColumn, XmlJoinColumn> buildSpecifiedJoinColumnContainer() {
		SpecifiedJoinColumnContainer container =  new SpecifiedJoinColumnContainer();
		container.initialize();
		return container;
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedJoinColumnContainer
		extends ContextListContainer<OrmModifiableJoinColumn, XmlJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_JOIN_COLUMNS_LIST;
		}
		@Override
		protected OrmModifiableJoinColumn buildContextElement(XmlJoinColumn resourceElement) {
			return GenericOrmReferenceTable.this.buildJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlJoinColumn> getResourceElements() {
			return GenericOrmReferenceTable.this.getXmlJoinColumns();
		}
		@Override
		protected XmlJoinColumn getResourceElement(OrmModifiableJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected abstract ReadOnlyJoinColumn.Owner buildJoinColumnOwner();


	// ********** default join column **********

	public OrmModifiableJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(OrmModifiableJoinColumn joinColumn) {
		OrmModifiableJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<OrmModifiableJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<OrmModifiableJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<OrmModifiableJoinColumn>instance();
	}

	protected int getDefaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultJoinColumn() {
		if (this.buildsDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(null));
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

	protected void initializeFrom(ReadOnlyReferenceTable oldTable) {
		super.initializeFrom(oldTable);
		for (ReadOnlyJoinColumn joinColumn : oldTable.getSpecifiedJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	protected void initializeFromVirtual(ReadOnlyReferenceTable virtualTable) {
		super.initializeFromVirtual(virtualTable);
		for (ReadOnlyJoinColumn joinColumn : virtualTable.getJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFromVirtual(joinColumn);
		}
	}

	protected OrmModifiableJoinColumn buildJoinColumn(XmlJoinColumn xmlJoinColumn) {
		return this.getContextModelFactory().buildOrmJoinColumn(this, this.joinColumnOwner, xmlJoinColumn);
	}

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

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmModifiableJoinColumn column : this.getJoinColumns()) {
			result = column.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}
