/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.OneToOnePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumnContainer;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmPrimaryKeyJoinColumnRelationshipStrategy
	extends AbstractOrmXmlContextModel<OrmPrimaryKeyJoinColumnRelationship>
	implements MappingRelationshipStrategy2_0, OrmPrimaryKeyJoinColumnRelationshipStrategy
{
	protected final ContextListContainer<OrmSpecifiedPrimaryKeyJoinColumn, XmlPrimaryKeyJoinColumn> primaryKeyJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner primaryKeyJoinColumnOwner;


	public GenericOrmPrimaryKeyJoinColumnRelationshipStrategy(OrmPrimaryKeyJoinColumnRelationship parent) {
		super(parent);
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.primaryKeyJoinColumnContainer = this.buildPrimaryKeyJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncPrimaryKeyJoinColumns();
	}

	@Override
	public void update() {
		super.update();
		this.updateModels(this.getPrimaryKeyJoinColumns());
	}


	// ********** primary key join columns **********

	public ListIterable<OrmSpecifiedPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.primaryKeyJoinColumnContainer.getContextElements();
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasPrimaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumnsSize() != 0;
	}

	public OrmSpecifiedPrimaryKeyJoinColumn getPrimaryKeyJoinColumn(int index) {
		return this.primaryKeyJoinColumnContainer.getContextElement(index);
	}

	public OrmSpecifiedPrimaryKeyJoinColumn addPrimaryKeyJoinColumn() {
		return this.addPrimaryKeyJoinColumn(this.getPrimaryKeyJoinColumnsSize());
	}

	public OrmSpecifiedPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn xmlJoinColumn = this.buildXmlPrimaryKeyJoinColumn();
		OrmSpecifiedPrimaryKeyJoinColumn joinColumn = this.primaryKeyJoinColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlPrimaryKeyJoinColumnContainer().getPrimaryKeyJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlPrimaryKeyJoinColumn buildXmlPrimaryKeyJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn();
	}

	public void removePrimaryKeyJoinColumn(SpecifiedPrimaryKeyJoinColumn joinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumnContainer.indexOfContextElement((OrmSpecifiedPrimaryKeyJoinColumn) joinColumn));
	}

	public void removePrimaryKeyJoinColumn(int index) {
		this.primaryKeyJoinColumnContainer.removeContextElement(index);
		this.getXmlPrimaryKeyJoinColumnContainer().getPrimaryKeyJoinColumns().remove(index);
	}

	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.primaryKeyJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
		this.getXmlPrimaryKeyJoinColumnContainer().getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
	}


	protected void syncPrimaryKeyJoinColumns() {
		this.primaryKeyJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlPrimaryKeyJoinColumn> getXmlPrimaryKeyJoinColumns() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.getXmlPrimaryKeyJoinColumnContainer().getPrimaryKeyJoinColumns());
	}

	/**
	 *  primary key join column container
	 */
	protected class PrimaryKeyJoinColumnContainer
		extends ContextListContainer<OrmSpecifiedPrimaryKeyJoinColumn, XmlPrimaryKeyJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return PRIMARY_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected OrmSpecifiedPrimaryKeyJoinColumn buildContextElement(XmlPrimaryKeyJoinColumn resourceElement) {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlPrimaryKeyJoinColumn> getResourceElements() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getXmlPrimaryKeyJoinColumns();
		}
		@Override
		protected XmlPrimaryKeyJoinColumn getResourceElement(OrmSpecifiedPrimaryKeyJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected ReadOnlyJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected OrmSpecifiedPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn xmlJoinColumn) {
		return this.getContextModelFactory().buildOrmPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, xmlJoinColumn);
	}

	protected ContextListContainer<OrmSpecifiedPrimaryKeyJoinColumn, XmlPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnContainer() {
		PrimaryKeyJoinColumnContainer container = new PrimaryKeyJoinColumnContainer();
		container.initialize();
		return container;
	}


	// ********** misc **********

	public OrmPrimaryKeyJoinColumnRelationship getRelationship() {
		return this.parent;
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	protected XmlPrimaryKeyJoinColumnContainer getXmlPrimaryKeyJoinColumnContainer() {
		return this.getRelationship().getXmlContainer();
	}

	public String getTableName() {
		return this.getTypeMapping().getPrimaryTableName();
	}

	public Table resolveDbTable(String tableName) {
		return this.getTypeMapping().resolveDbTable(tableName);
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getTypeMapping().tableNameIsInvalid(tableName);
	}

	public String getColumnTableNotValidDescription() {
		return JptJpaCoreValidationArgumentMessages.NOT_VALID_FOR_THIS_ENTITY;
	}

	protected TypeMapping getTypeMapping() {
		return this.getRelationshipMapping().getTypeMapping();
	}

	public ReadOnlyRelationshipStrategy selectOverrideStrategy(ReadOnlyOverrideRelationship2_0 overrideRelationship) {
		return null;  // pk join column strategies cannot be overridden
	}

	public void addStrategy() {
		if (this.getPrimaryKeyJoinColumnsSize() == 0) {
			this.addPrimaryKeyJoinColumn();
		}
	}

	public void removeStrategy() {
		for (int i = this.getPrimaryKeyJoinColumnsSize(); i-- > 0; ) {
			this.removePrimaryKeyJoinColumn(i);
		}
	}

	public boolean isOverridable() {
		return false;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (OrmSpecifiedPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
			pkJoinColumn.validate(messages, reporter);
		}
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmSpecifiedPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
			result = column.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	// ********** join column owner **********

	protected class PrimaryKeyJoinColumnOwner
		implements ReadOnlyJoinColumn.Owner
	{
		protected PrimaryKeyJoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getTableName();
		}

		public Entity getRelationshipTarget() {
			return this.getRelationshipMapping().getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return this.getRelationshipMapping().getName();
		}

		protected ModifiablePersistentAttribute getPersistentAttribute() {
			return this.getRelationshipMapping().getPersistentAttribute();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return this.getTypeMapping().tableNameIsInvalid(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return this.getTypeMapping().getAllAssociatedTableNames();
		}

		protected TypeMapping getTypeMapping() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getTypeMapping();
		}

		public Table resolveDbTable(String tableName) {
			return this.getTypeMapping().resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
		}

		public String getDefaultColumnName(NamedColumn column) {
			return null;
		}

		public int getJoinColumnsSize() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(NamedColumn column) {
			return new OneToOnePrimaryKeyJoinColumnValidator(this.getPersistentAttribute(), (BaseJoinColumn) column, this);
		}

		protected RelationshipMapping getRelationshipMapping() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getRelationshipMapping();
		}
	}
}
