/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyReferenceTable;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmReferenceTable;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
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
public abstract class GenericOrmReferenceTable<X extends AbstractXmlReferenceTable>
	extends AbstractOrmTable<X>
	implements OrmReferenceTable
{
	protected final Vector<OrmJoinColumn> specifiedJoinColumns = new Vector<OrmJoinColumn>();
	protected final SpecifiedJoinColumnContainerAdapter specifiedJoinColumnContainerAdapter = new SpecifiedJoinColumnContainerAdapter();
	protected final OrmJoinColumn.Owner joinColumnOwner;

	protected OrmJoinColumn defaultJoinColumn;


	protected GenericOrmReferenceTable(XmlContextNode parent, Owner owner) {
		super(parent, owner);
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.initializeSpecifiedJoinColumns();
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
		this.updateNodes(this.getSpecifiedJoinColumns());
		this.updateDefaultJoinColumn();
	}


	// ********** join columns **********

	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.getJoinColumns().iterator();
	}

	protected ListIterable<OrmJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}

	public void convertDefaultToSpecifiedJoinColumn() {
		MappingTools.convertReferenceTableDefaultToSpecifiedJoinColumn(this);
	}


	// ********** specified join columns **********

	public ListIterator<OrmJoinColumn> specifiedJoinColumns() {
		return this.getSpecifiedJoinColumns().iterator();
	}

	protected ListIterable<OrmJoinColumn> getSpecifiedJoinColumns() {
		return new LiveCloneListIterable<OrmJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	public OrmJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumns.get(index);
	}

	public OrmJoinColumn addSpecifiedJoinColumn() {
		return this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size());
	}

	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
		X xmlTable = this.getXmlTableForUpdate();
		XmlJoinColumn xmlJoinColumn = this.buildXmlJoinColumn();
		OrmJoinColumn joinColumn = this.addSpecifiedJoinColumn_(index, xmlJoinColumn);
		xmlTable.getJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlJoinColumn buildXmlJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlJoinColumn();
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.removeSpecifiedJoinColumn_(index);
		this.getXmlTable().getJoinColumns().remove(index);
		this.removeXmlTableIfUnset();
	}

	protected void removeSpecifiedJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
		this.getXmlTable().getJoinColumns().move(targetIndex, sourceIndex);
	}

	protected void initializeSpecifiedJoinColumns() {
		for (XmlJoinColumn xmlJoinColumn : this.getXmlJoinColumns()) {
			this.specifiedJoinColumns.add(this.buildJoinColumn(xmlJoinColumn));
		}
	}

	protected void syncSpecifiedJoinColumns() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedJoinColumnContainerAdapter);
	}

	protected Iterable<XmlJoinColumn> getXmlJoinColumns() {
		X xmlTable = this.getXmlTable();
		return (xmlTable == null) ?
				EmptyIterable.<XmlJoinColumn>instance() :
				// clone to reduce chance of concurrency problems
				new LiveCloneIterable<XmlJoinColumn>(xmlTable.getJoinColumns());
	}

	protected void moveSpecifiedJoinColumn_(int index, OrmJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected OrmJoinColumn addSpecifiedJoinColumn_(int index, XmlJoinColumn xmlJoinColumn) {
		OrmJoinColumn joinColumn = this.buildJoinColumn(xmlJoinColumn);
		this.addItemToList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
		return joinColumn;
	}

	protected void removeSpecifiedJoinColumn_(OrmJoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn_(this.specifiedJoinColumns.indexOf(joinColumn));
	}

	/**
	 * specified join column container adapter
	 */
	protected class SpecifiedJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmJoinColumn, XmlJoinColumn>
	{
		public Iterable<OrmJoinColumn> getContextElements() {
			return GenericOrmReferenceTable.this.getSpecifiedJoinColumns();
		}
		public Iterable<XmlJoinColumn> getResourceElements() {
			return GenericOrmReferenceTable.this.getXmlJoinColumns();
		}
		public XmlJoinColumn getResourceElement(OrmJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
		public void moveContextElement(int index, OrmJoinColumn element) {
			GenericOrmReferenceTable.this.moveSpecifiedJoinColumn_(index, element);
		}
		public void addContextElement(int index, XmlJoinColumn resourceElement) {
			GenericOrmReferenceTable.this.addSpecifiedJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(OrmJoinColumn element) {
			GenericOrmReferenceTable.this.removeSpecifiedJoinColumn_(element);
		}
	}

	protected abstract OrmJoinColumn.Owner buildJoinColumnOwner();


	// ********** default join column **********

	public OrmJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(OrmJoinColumn joinColumn) {
		OrmJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<OrmJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<OrmJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<OrmJoinColumn>instance();
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
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(oldTable.specifiedJoinColumns())) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	protected void initializeFromVirtual(ReadOnlyReferenceTable virtualTable) {
		super.initializeFromVirtual(virtualTable);
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(virtualTable.joinColumns())) {
			this.addSpecifiedJoinColumn().initializeFromVirtual(joinColumn);
		}
	}

	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn xmlJoinColumn) {
		return this.getContextNodeFactory().buildOrmJoinColumn(this, this.joinColumnOwner, xmlJoinColumn);
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
		this.validateJoinColumns(this.getJoinColumns(), messages, reporter);
	}

	protected void validateJoinColumns(Iterable<OrmJoinColumn> joinColumns, List<IMessage> messages, IReporter reporter) {
		for (OrmJoinColumn joinColumn : joinColumns) {
			joinColumn.validate(messages, reporter);
		}
	}
}
