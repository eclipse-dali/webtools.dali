/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.VirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmVirtualOverrideJoinColumnRelationshipStrategy
	extends AbstractOrmXmlContextModel
	implements VirtualJoinColumnRelationshipStrategy
{
	protected final ContextListContainer<VirtualJoinColumn, ReadOnlyJoinColumn> specifiedJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner joinColumnOwner;

	protected VirtualJoinColumn defaultJoinColumn;


	public GenericOrmVirtualOverrideJoinColumnRelationshipStrategy(VirtualOverrideRelationship parent) {
		super(parent);
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

	protected ListIterable<ReadOnlyJoinColumn> getOverriddenSpecifiedJoinColumns() {
		ReadOnlyJoinColumnRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ?
				EmptyListIterable.<ReadOnlyJoinColumn>instance() :
				new SuperListIterableWrapper<ReadOnlyJoinColumn>(overriddenStrategy.getSpecifiedJoinColumns());
	}

	protected ContextListContainer<VirtualJoinColumn, ReadOnlyJoinColumn> buildSpecifiedJoinColumnContainer() {
		return new SpecifiedJoinColumnContainer();
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedJoinColumnContainer
		extends ContextListContainer<VirtualJoinColumn, ReadOnlyJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_JOIN_COLUMNS_LIST;
		}
		@Override
		protected VirtualJoinColumn buildContextElement(ReadOnlyJoinColumn resourceElement) {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.buildJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyJoinColumn> getResourceElements() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getOverriddenSpecifiedJoinColumns();
		}
		@Override
		protected ReadOnlyJoinColumn getResourceElement(VirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}

	protected ReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
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
		ReadOnlyJoinColumn overriddenDefaultJoinColumn = this.getOverriddenDefaultJoinColumn();
		if (overriddenDefaultJoinColumn == null) {
			if (this.defaultJoinColumn != null) {
				this.setDefaultJoinColumn(null);
			}
		} else {
			if ((this.defaultJoinColumn != null) && (this.defaultJoinColumn.getOverriddenColumn() == overriddenDefaultJoinColumn)) {
				this.defaultJoinColumn.update();
			} else {
				this.setDefaultJoinColumn(this.buildJoinColumn(overriddenDefaultJoinColumn));
			}
		}
	}

	protected ReadOnlyJoinColumn getOverriddenDefaultJoinColumn() {
		ReadOnlyJoinColumnRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getDefaultJoinColumn();
	}


	// ********** misc **********

	@Override
	public VirtualOverrideRelationship getParent() {
		return (VirtualOverrideRelationship) super.getParent();
	}

	public VirtualOverrideRelationship getRelationship() {
		return this.getParent();
	}

	protected ReadOnlyJoinColumnRelationshipStrategy getOverriddenStrategy() {
		ReadOnlyJoinColumnRelationship relationship = this.getOverriddenJoinColumnRelationship();
		return (relationship == null) ? null : relationship.getJoinColumnStrategy();
	}

	protected ReadOnlyJoinColumnRelationship getOverriddenJoinColumnRelationship() {
		ReadOnlyRelationship relationship = this.resolveOverriddenRelationship();
		return (relationship instanceof ReadOnlyJoinColumnRelationship) ? (ReadOnlyJoinColumnRelationship) relationship : null;
	}

	protected ReadOnlyRelationship resolveOverriddenRelationship() {
		return this.getRelationship().resolveOverriddenRelationship();
	}

	public boolean isTargetForeignKey() {
		RelationshipMapping relationshipMapping = this.getRelationshipMapping();
		return (relationshipMapping != null) &&
				relationshipMapping.getRelationship().isTargetForeignKey();
	}

	public TypeMapping getRelationshipSource() {
		return this.isTargetForeignKey() ?
				// relationship mapping is not null
				this.getRelationshipMapping().getResolvedTargetEntity() :
				this.getRelationship().getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		return this.isTargetForeignKey() ?
				this.getRelationship().getTypeMapping() :
				// relationship mapping may still be null
				this.getRelationshipMappingTargetEntity();
	}

	protected TypeMapping getRelationshipMappingTargetEntity() {
		RelationshipMapping mapping = this.getRelationshipMapping();
		return (mapping == null) ? null : mapping.getResolvedTargetEntity();
	}

	protected Entity getRelationshipTargetEntity() {
		TypeMapping target = this.getRelationshipTarget();
		return (target instanceof Entity) ? (Entity) target : null;
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	public String getTableName() {
		return this.isTargetForeignKey() ?
				this.getTargetDefaultTableName() :
				this.getRelationship().getDefaultTableName();
	}

	protected String getTargetDefaultTableName() {
		TypeMapping typeMapping = this.getRelationshipMapping().getResolvedTargetEntity();
		return (typeMapping == null) ? null : typeMapping.getPrimaryTableName();
	}

	protected Table resolveDbTable(String tableName) {
		return this.isTargetForeignKey() ?
				this.resolveTargetDbTable(tableName) :
				this.getRelationship().resolveDbTable(tableName);
	}

	protected Table resolveTargetDbTable(String tableName) {
		TypeMapping typeMapping = this.getRelationshipMapping().getResolvedTargetEntity();
		return (typeMapping == null) ? null : typeMapping.resolveDbTable(tableName);
	}

	protected boolean tableNameIsInvalid(String tableName) {
		return this.isTargetForeignKey() ?
				this.targetTableNameIsInvalid(tableName) :
				this.getRelationship().tableNameIsInvalid(tableName);
	}

	protected boolean targetTableNameIsInvalid(String tableName) {
		TypeMapping typeMapping = this.getRelationshipMapping().getResolvedTargetEntity();
		return (typeMapping != null) && typeMapping.tableNameIsInvalid(tableName);
	}

	protected Table getReferencedColumnDbTable() {
		TypeMapping relationshipTarget = this.getRelationshipTarget();
		return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
	}

	protected Iterable<String> getCandidateTableNames() {
		return this.isTargetForeignKey() ?
				this.getTargetCandidateTableNames() :
				this.getRelationship().getCandidateTableNames();
	}

	protected Iterable<String> getTargetCandidateTableNames() {
		TypeMapping typeMapping = this.getRelationshipMapping().getResolvedTargetEntity();
		return (typeMapping != null) ? typeMapping.getAllAssociatedTableNames() : EmptyIterable.<String>instance();
	}

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

	protected String getAttributeName() {
		return this.getRelationship().getAttributeName();
	}

	protected VirtualJoinColumn buildJoinColumn(ReadOnlyJoinColumn overriddenJoinColumn) {
		return this.getContextNodeFactory().buildOrmVirtualJoinColumn(this, this.joinColumnOwner, overriddenJoinColumn);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (VirtualJoinColumn joinColumn : this.getJoinColumns()) {
			joinColumn.validate(messages, reporter);
		}
	}


	// ********** join column owner **********

	protected class JoinColumnOwner
		implements ReadOnlyJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getTableName();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return MappingTools.buildJoinColumnDefaultName((ReadOnlyJoinColumn) column, this);
		}

		public String getAttributeName() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getAttributeName();
		}

		public Entity getRelationshipTarget() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getCandidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getReferencedColumnDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationship().buildColumnValidator((ReadOnlyBaseColumn) column, this);
		}
	}
}
