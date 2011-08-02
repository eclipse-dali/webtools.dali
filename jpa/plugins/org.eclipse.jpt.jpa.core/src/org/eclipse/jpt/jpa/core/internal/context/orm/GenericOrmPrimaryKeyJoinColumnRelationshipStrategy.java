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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.OneToOnePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmMappingPrimaryKeyJoinColumnRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumnContainer;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmPrimaryKeyJoinColumnRelationshipStrategy
	extends AbstractOrmXmlContextNode
	implements OrmMappingPrimaryKeyJoinColumnRelationshipStrategy2_0
{
	protected final PrimaryKeyJoinColumnContainer primaryKeyJoinColumnContainer;
	protected final OrmReadOnlyJoinColumn.Owner primaryKeyJoinColumnOwner;


	public GenericOrmPrimaryKeyJoinColumnRelationshipStrategy(OrmPrimaryKeyJoinColumnRelationship parent) {
		super(parent);
		this.primaryKeyJoinColumnContainer = new PrimaryKeyJoinColumnContainer();
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
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
		this.updateNodes(this.getPrimaryKeyJoinColumns());
	}


	// ********** primary key join columns **********

	public ListIterable<OrmPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.primaryKeyJoinColumnContainer.getContextElements();
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasPrimaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumnsSize() != 0;
	}

	public OrmPrimaryKeyJoinColumn getPrimaryKeyJoinColumn(int index) {
		return this.primaryKeyJoinColumnContainer.getContextElement(index);
	}

	public OrmPrimaryKeyJoinColumn addPrimaryKeyJoinColumn() {
		return this.addPrimaryKeyJoinColumn(this.getPrimaryKeyJoinColumnsSize());
	}

	public OrmPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn xmlJoinColumn = this.buildXmlPrimaryKeyJoinColumn();
		OrmPrimaryKeyJoinColumn joinColumn = this.primaryKeyJoinColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlPrimaryKeyJoinColumnContainer().getPrimaryKeyJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlPrimaryKeyJoinColumn buildXmlPrimaryKeyJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn();
	}

	public void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn joinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumnContainer.indexOfContextElement((OrmPrimaryKeyJoinColumn) joinColumn));
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
		return new LiveCloneListIterable<XmlPrimaryKeyJoinColumn>(this.getXmlPrimaryKeyJoinColumnContainer().getPrimaryKeyJoinColumns());
	}

	/**
	 *  primary key join column container
	 */
	protected class PrimaryKeyJoinColumnContainer
		extends ContextListContainer<OrmPrimaryKeyJoinColumn, XmlPrimaryKeyJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return PRIMARY_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected OrmPrimaryKeyJoinColumn buildContextElement(XmlPrimaryKeyJoinColumn resourceElement) {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlPrimaryKeyJoinColumn> getResourceElements() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getXmlPrimaryKeyJoinColumns();
		}
		@Override
		protected XmlPrimaryKeyJoinColumn getResourceElement(OrmPrimaryKeyJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected OrmReadOnlyJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected OrmPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn xmlJoinColumn) {
		return this.getContextNodeFactory().buildOrmPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, xmlJoinColumn);
	}


	// ********** misc **********

	@Override
	public OrmPrimaryKeyJoinColumnRelationship getParent() {
		return (OrmPrimaryKeyJoinColumnRelationship) super.getParent();
	}

	public OrmPrimaryKeyJoinColumnRelationship getRelationship() {
		return this.getParent();
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
		return JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY;
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
		for (OrmPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
			pkJoinColumn.validate(messages, reporter);
		}
	}


	// ********** join column owner **********

	protected class PrimaryKeyJoinColumnOwner
		implements OrmReadOnlyJoinColumn.Owner
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

		protected PersistentAttribute getPersistentAttribute() {
			return this.getRelationshipMapping().getPersistentAttribute();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return this.getTypeMapping().tableNameIsInvalid(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return this.getTypeMapping().getAllAssociatedTableNames();
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getTypeMapping();
		}

		public Table resolveDbTable(String tableName) {
			return this.getTypeMapping().resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return false;
		}

		public String getDefaultColumnName() {
			return null;
		}

		public int getJoinColumnsSize() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new OneToOnePrimaryKeyJoinColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseJoinColumn) column, this, (BaseJoinColumnTextRangeResolver) textRangeResolver);
		}

		protected RelationshipMapping getRelationshipMapping() {
			return GenericOrmPrimaryKeyJoinColumnRelationshipStrategy.this.getRelationshipMapping();
		}
	}
}
