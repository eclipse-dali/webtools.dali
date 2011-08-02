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

import java.util.List;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyJoinColumn;
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
	protected final ContextListContainer<OrmJoinColumn, XmlJoinColumn> specifiedJoinColumnContainer;
	protected final OrmReadOnlyJoinColumn.Owner joinColumnOwner;

	protected OrmJoinColumn defaultJoinColumn;


	protected AbstractOrmJoinColumnRelationshipStrategy(OrmJoinColumnRelationship parent) {
		super(parent);
		this.specifiedJoinColumnContainer = this.buildSpecifiedJoinColumnContainer();
		this.joinColumnOwner = this.buildJoinColumnOwner();
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
		this.updateNodes(getSpecifiedJoinColumns());
		this.updateDefaultJoinColumn();
	}


	// ********** XML join column container **********

	protected XmlJoinColumnContainer getXmlJoinColumnContainer() {
		return this.getRelationship().getXmlContainer();
	}


	// ********** join columns **********

	public ListIterable<OrmJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int getJoinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterable<OrmJoinColumn> getSpecifiedJoinColumns() {
		return this.specifiedJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public OrmJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.getContextElement(index);
	}

	public OrmJoinColumn addSpecifiedJoinColumn() {
		return this.addSpecifiedJoinColumn(this.getSpecifiedJoinColumnsSize());
	}

	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
		XmlJoinColumn xmlJoinColumn = this.buildXmlJoinColumn();
		OrmJoinColumn joinColumn = this.specifiedJoinColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlJoinColumnContainer().getJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlJoinColumn buildXmlJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlJoinColumn();
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumnContainer.indexOfContextElement((OrmJoinColumn) joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.specifiedJoinColumnContainer.removeContextElement(index);
		this.getXmlJoinColumnContainer().getJoinColumns().remove(index);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.specifiedJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
		this.getXmlJoinColumnContainer().getJoinColumns().move(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlJoinColumn> getXmlJoinColumns() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlJoinColumn>(this.getXmlJoinColumnContainer().getJoinColumns());
	}

	protected ContextListContainer<OrmJoinColumn, XmlJoinColumn> buildSpecifiedJoinColumnContainer() {
		return new SpecifiedJoinColumnContainer();
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedJoinColumnContainer
		extends ContextListContainer<OrmJoinColumn, XmlJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_JOIN_COLUMNS_LIST;
		}
		@Override
		protected OrmJoinColumn buildContextElement(XmlJoinColumn resourceElement) {
			return AbstractOrmJoinColumnRelationshipStrategy.this.buildJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlJoinColumn> getResourceElements() {
			return AbstractOrmJoinColumnRelationshipStrategy.this.getXmlJoinColumns();
		}
		@Override
		protected XmlJoinColumn getResourceElement(OrmJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected abstract OrmReadOnlyJoinColumn.Owner buildJoinColumnOwner();


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
		for (ReadOnlyJoinColumn joinColumn : oldStrategy.getSpecifiedJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinColumnRelationshipStrategy virtualStrategy) {
		for (ReadOnlyJoinColumn joinColumn : virtualStrategy.getJoinColumns()) {
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
	
	protected Iterable<String> getCandidateTableNames() {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping != null) ? typeMapping.getAllAssociatedTableNames() : EmptyIterable.<String>instance();
	}

	public void addStrategy() {
		if (this.getSpecifiedJoinColumnsSize() == 0) {
			this.addSpecifiedJoinColumn();
		}
	}

	public void removeStrategy() {
		for (int i = this.getSpecifiedJoinColumnsSize(); i-- > 0; ) {
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
