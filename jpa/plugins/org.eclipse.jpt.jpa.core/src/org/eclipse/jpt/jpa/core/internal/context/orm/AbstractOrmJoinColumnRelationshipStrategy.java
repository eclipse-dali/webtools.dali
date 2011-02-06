/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumnContainer;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmJoinColumnRelationshipStrategy
	extends AbstractOrmXmlContextNode
	implements OrmJoinColumnRelationshipStrategy
{
	protected final Vector<OrmJoinColumn> specifiedJoinColumns = new Vector<OrmJoinColumn>();
	protected final SpecifiedJoinColumnContainerAdapter specifiedJoinColumnContainerAdapter;
	protected final OrmJoinColumn.Owner joinColumnOwner;

	protected OrmJoinColumn defaultJoinColumn;


	protected AbstractOrmJoinColumnRelationshipStrategy(OrmJoinColumnRelationship parent) {
		super(parent);
		this.specifiedJoinColumnContainerAdapter = this.buildSpecifiedJoinColumnContainerAdapter();
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


	// ********** XML join column container **********

	protected XmlJoinColumnContainer getXmlJoinColumnContainer() {
		return this.getRelationship().getXmlContainer();
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
		XmlJoinColumn xmlJoinColumn = this.buildXmlJoinColumn();
		OrmJoinColumn joinColumn = this.addSpecifiedJoinColumn_(index, xmlJoinColumn);
		this.getXmlJoinColumnContainer().getJoinColumns().add(index, xmlJoinColumn);
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
		this.getXmlJoinColumnContainer().getJoinColumns().remove(index);
	}

	protected void removeSpecifiedJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
		this.getXmlJoinColumnContainer().getJoinColumns().move(targetIndex, sourceIndex);
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
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlJoinColumn>(this.getXmlJoinColumnContainer().getJoinColumns());
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

	protected SpecifiedJoinColumnContainerAdapter buildSpecifiedJoinColumnContainerAdapter() {
		return new SpecifiedJoinColumnContainerAdapter();
	}

	/**
	 * specified join column container adapter
	 */
	protected class SpecifiedJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmJoinColumn, XmlJoinColumn>
	{
		public Iterable<OrmJoinColumn> getContextElements() {
			return AbstractOrmJoinColumnRelationshipStrategy.this.getSpecifiedJoinColumns();
		}
		public Iterable<XmlJoinColumn> getResourceElements() {
			return AbstractOrmJoinColumnRelationshipStrategy.this.getXmlJoinColumns();
		}
		public XmlJoinColumn getResourceElement(OrmJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
		public void moveContextElement(int index, OrmJoinColumn element) {
			AbstractOrmJoinColumnRelationshipStrategy.this.moveSpecifiedJoinColumn_(index, element);
		}
		public void addContextElement(int index, XmlJoinColumn resourceElement) {
			AbstractOrmJoinColumnRelationshipStrategy.this.addSpecifiedJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(OrmJoinColumn element) {
			AbstractOrmJoinColumnRelationshipStrategy.this.removeSpecifiedJoinColumn_(element);
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
		return ! this.hasSpecifiedJoinColumns() &&
				this.getRelationship().mayHaveDefaultJoinColumn();
	}


	// ********** misc **********

	@Override
	public OrmJoinColumnRelationship getParent() {
		return (OrmJoinColumnRelationship) super.getParent();
	}

	public OrmJoinColumnRelationship getRelationship() {
		return this.getParent();
	}

	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn xmlJoinColumn) {
		return this.getContextNodeFactory().buildOrmJoinColumn(this, this.joinColumnOwner, xmlJoinColumn);
	}

	public void initializeFrom(ReadOnlyJoinColumnRelationshipStrategy oldStrategy) {
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(oldStrategy.specifiedJoinColumns())) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinColumnRelationshipStrategy virtualStrategy) {
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(virtualStrategy.joinColumns())) {
			this.addSpecifiedJoinColumn().initializeFromVirtual(joinColumn);
		}
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	public String getTableName() {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping == null) ? null : typeMapping.getPrimaryTableName();
	}

	public Table resolveDbTable(String tableName) {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping == null) ? null : typeMapping.resolveDbTable(tableName);
	}

	public boolean tableNameIsInvalid(String tableName) {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping != null) && typeMapping.tableNameIsInvalid(tableName);
	}

	// subclasses like this to be public
	public Table getReferencedColumnDbTable() {
		TypeMapping relationshipTarget = this.getRelationshipTarget();
		return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
	}
	
	protected Iterator<String> candidateTableNames() {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping != null) ? typeMapping.allAssociatedTableNames() : EmptyIterator.<String>instance();
	}

	public void addStrategy() {
		if (this.specifiedJoinColumnsSize() == 0) {
			this.addSpecifiedJoinColumn();
		}
	}

	public void removeStrategy() {
		for (int i = this.specifiedJoinColumns.size(); i-- > 0; ) {
			this.removeSpecifiedJoinColumn(i);
		}
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (OrmJoinColumn joinColumn : this.getJoinColumns()) {
			joinColumn.validate(messages, reporter);
		}
	}
}
