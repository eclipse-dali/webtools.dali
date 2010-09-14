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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.ReferenceTable;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmReferenceTable;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmTable;
import org.eclipse.jpt.core.resource.orm.AbstractXmlReferenceTable;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class GenericOrmReferenceTable
	extends AbstractOrmTable
	implements OrmReferenceTable
{
	protected OrmJoinColumn defaultJoinColumn;

	protected final Vector<OrmJoinColumn> specifiedJoinColumns = new Vector<OrmJoinColumn>();
	protected final OrmJoinColumn.Owner joinColumnOwner;


	protected GenericOrmReferenceTable(XmlContextNode parent, Owner owner) {
		super(parent, owner);
		this.joinColumnOwner = this.buildJoinColumnOwner();
	}

	protected abstract OrmJoinColumn.Owner buildJoinColumnOwner();

	public void initializeFrom(ReferenceTable oldReferenceTable) {
		super.initializeFrom(oldReferenceTable);
		for (Iterator<OrmJoinColumn> stream = oldReferenceTable.specifiedJoinColumns(); stream.hasNext(); ) {
			this.addSpecifiedJoinColumnFrom(stream.next());
		}
	}

	protected void initialize(AbstractXmlReferenceTable xmlReferenceTable) {
		super.initialize(xmlReferenceTable);
		this.initializeSpecifiedJoinColumns(xmlReferenceTable);
		this.initializeDefaultJoinColumn();
	}

	public void update() {
		this.update(this.getResourceTable());
	}

	protected void update(AbstractXmlReferenceTable xmlReferenceTable) {
		super.update(xmlReferenceTable);
		this.updateSpecifiedJoinColumns(xmlReferenceTable);
		this.updateDefaultJoinColumn();
	}


	// ********** AbstractOrmTable implementation **********

	/**
	 * if the join table is on the "mappedBy" side, it's bogus;
	 * so don't give it a default schema
	 */
	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}

	@Override
	protected abstract AbstractXmlReferenceTable getResourceTable();


	// ********** join columns **********

	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumns() : this.defaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}

	public void convertDefaultToSpecifiedJoinColumn() {
		MappingTools.convertReferenceTableDefaultToSpecifiedJoinColumn(this);
	}


	// ********** default join column **********

	public OrmJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(OrmJoinColumn defaultJoinColumn) {
		OrmJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = defaultJoinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, old, defaultJoinColumn);
	}

	protected ListIterator<OrmJoinColumn> defaultJoinColumns() {
		if (this.defaultJoinColumn != null) {
			return new SingleElementListIterator<OrmJoinColumn>(this.defaultJoinColumn);
		}
		return EmptyListIterator.instance();
	}

	protected int defaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void initializeDefaultJoinColumn() {
		if (this.shouldBuildDefaultJoinColumn()) {
			this.defaultJoinColumn = this.buildJoinColumn(null);
		}
	}

	protected void updateDefaultJoinColumn() {
		if (this.shouldBuildDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(null));
			} else {
				this.defaultJoinColumn.update(null);
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}

	protected boolean shouldBuildDefaultJoinColumn() {
		return ! this.hasSpecifiedJoinColumns();
	}


	// ********** specified join columns **********

	public ListIterator<OrmJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	protected void addSpecifiedJoinColumnFrom(OrmJoinColumn oldJoinColumn) {
		OrmJoinColumn newJoinColumn = this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size());
		newJoinColumn.initializeFrom(oldJoinColumn);
	}

	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
		if (this.getResourceTable() == null) {
			this.addResourceTable();
		}
		XmlJoinColumn xmlJoinColumn = OrmFactory.eINSTANCE.createXmlJoinColumn();
		OrmJoinColumn joinColumn = this.buildJoinColumn(xmlJoinColumn);
		this.specifiedJoinColumns.add(index, joinColumn);
		this.getResourceTable().getJoinColumns().add(index, xmlJoinColumn);
		this.fireItemAdded(SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, OrmJoinColumn joinColumn) {
		this.addItemToList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected void addSpecifiedJoinColumn(OrmJoinColumn joinColumn) {
		this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size(), joinColumn);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		OrmJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		if ( ! this.hasSpecifiedJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = this.buildJoinColumn(null);
		}
		this.getResourceTable().getJoinColumns().remove(index);
		this.fireItemRemoved(SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);
		}
	}

	protected void removeSpecifiedJoinColumn_(OrmJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.getResourceTable().getJoinColumns().move(targetIndex, sourceIndex);
		this.fireItemMoved(SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public void clearSpecifiedJoinColumns() {
		this.specifiedJoinColumns.clear();
		this.defaultJoinColumn = this.buildJoinColumn(null);
		this.getResourceTable().getJoinColumns().clear();
		this.fireListCleared(SPECIFIED_JOIN_COLUMNS_LIST);
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);
	}

	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn resourceJoinColumn) {
		return this.buildJoinColumn(resourceJoinColumn, this.joinColumnOwner);
	}

	protected void initializeSpecifiedJoinColumns(AbstractXmlReferenceTable xmlReferenceTable) {
		if (xmlReferenceTable != null) {
			for (XmlJoinColumn xmlJoinColumn : xmlReferenceTable.getJoinColumns()) {
				this.specifiedJoinColumns.add(this.buildJoinColumn(xmlJoinColumn));
			}
		}
	}

	protected void updateSpecifiedJoinColumns(AbstractXmlReferenceTable xmlReferenceTable) {
		Iterator<XmlJoinColumn> xmlJoinColumns = this.xmlJoinColumns(xmlReferenceTable);

		for (Iterator<OrmJoinColumn> contextJoinColumns = this.specifiedJoinColumns(); contextJoinColumns.hasNext(); ) {
			OrmJoinColumn contextJoinColumn = contextJoinColumns.next();
			if (xmlJoinColumns.hasNext()) {
				contextJoinColumn.update(xmlJoinColumns.next());
			} else {
				this.removeSpecifiedJoinColumn_(contextJoinColumn);
			}
		}

		while (xmlJoinColumns.hasNext()) {
			this.addSpecifiedJoinColumn(this.buildJoinColumn(xmlJoinColumns.next()));
		}
	}

	protected Iterator<XmlJoinColumn> xmlJoinColumns(AbstractXmlReferenceTable xmlReferenceTable) {
		// make a copy of the XML join columns (to prevent ConcurrentModificationException)
		return (xmlReferenceTable == null) ? EmptyIterator.<XmlJoinColumn>instance()
			: new CloneIterator<XmlJoinColumn>(xmlReferenceTable.getJoinColumns());
	}


	// ********** misc **********

	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn resourceJoinColumn, OrmJoinColumn.Owner owner) {
		return this.getXmlContextNodeFactory().buildOrmJoinColumn(this, owner, resourceJoinColumn);
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
		this.validateJoinColumns(this.joinColumns(), messages, reporter);		
	}

	protected void validateJoinColumns(Iterator<OrmJoinColumn> joinColumns, List<IMessage> messages, IReporter reporter) {
		while (joinColumns.hasNext()) {
			joinColumns.next().validate(messages, reporter);
		}
	}
}
