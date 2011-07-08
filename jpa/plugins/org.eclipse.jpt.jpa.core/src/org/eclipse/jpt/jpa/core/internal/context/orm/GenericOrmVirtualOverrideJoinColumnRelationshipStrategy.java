/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmVirtualOverrideJoinColumnRelationshipStrategy
	extends AbstractOrmXmlContextNode
	implements OrmVirtualJoinColumnRelationshipStrategy
{
	protected final Vector<OrmVirtualJoinColumn> specifiedJoinColumns = new Vector<OrmVirtualJoinColumn>();
	protected final SpecifiedJoinColumnContainerAdapter specifiedJoinColumnContainerAdapter;
	protected final OrmReadOnlyJoinColumn.Owner joinColumnOwner;

	protected OrmVirtualJoinColumn defaultJoinColumn;


	public GenericOrmVirtualOverrideJoinColumnRelationshipStrategy(OrmVirtualOverrideRelationship parent) {
		super(parent);
		this.specifiedJoinColumnContainerAdapter = this.buildSpecifiedJoinColumnContainerAdapter();
		this.joinColumnOwner = this.buildJoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedJoinColumns();
		this.updateDefaultJoinColumn();
	}


	// ********** join columns **********

	public ListIterator<OrmVirtualJoinColumn> joinColumns() {
		return this.getJoinColumns().iterator();
	}

	protected ListIterable<OrmVirtualJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterator<OrmVirtualJoinColumn> specifiedJoinColumns() {
		return this.getSpecifiedJoinColumns().iterator();
	}

	protected ListIterable<OrmVirtualJoinColumn> getSpecifiedJoinColumns() {
		return new LiveCloneListIterable<OrmVirtualJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	public OrmVirtualJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumns.get(index);
	}

	protected void updateSpecifiedJoinColumns() {
		ContextContainerTools.update(this.specifiedJoinColumnContainerAdapter);
	}

	protected Iterable<ReadOnlyJoinColumn> getOverriddenSpecifiedJoinColumns() {
		ReadOnlyJoinColumnRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ?
				EmptyIterable.<ReadOnlyJoinColumn>instance() :
				CollectionTools.iterable(overriddenStrategy.specifiedJoinColumns());
	}

	protected void moveSpecifiedJoinColumn(int index, OrmVirtualJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected OrmVirtualJoinColumn addSpecifiedJoinColumn(int index, ReadOnlyJoinColumn joinColumn) {
		OrmVirtualJoinColumn virtualJoinColumn = this.buildJoinColumn(joinColumn);
		this.addItemToList(index, virtualJoinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
		return virtualJoinColumn;
	}

	protected void removeSpecifiedJoinColumn(OrmVirtualJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected SpecifiedJoinColumnContainerAdapter buildSpecifiedJoinColumnContainerAdapter() {
		return new SpecifiedJoinColumnContainerAdapter();
	}

	/**
	 * specified join column container adapter
	 */
	protected class SpecifiedJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmVirtualJoinColumn, ReadOnlyJoinColumn>
	{
		public Iterable<OrmVirtualJoinColumn> getContextElements() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getSpecifiedJoinColumns();
		}
		public Iterable<ReadOnlyJoinColumn> getResourceElements() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getOverriddenSpecifiedJoinColumns();
		}
		public ReadOnlyJoinColumn getResourceElement(OrmVirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
		public void moveContextElement(int index, OrmVirtualJoinColumn element) {
			GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.moveSpecifiedJoinColumn(index, element);
		}
		public void addContextElement(int index, ReadOnlyJoinColumn resourceElement) {
			GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.addSpecifiedJoinColumn(index, resourceElement);
		}
		public void removeContextElement(OrmVirtualJoinColumn element) {
			GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.removeSpecifiedJoinColumn(element);
		}
	}

	protected OrmReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}


	// ********** default join column **********

	public OrmVirtualJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(OrmVirtualJoinColumn joinColumn) {
		OrmVirtualJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<OrmVirtualJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<OrmVirtualJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<OrmVirtualJoinColumn>instance();
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
	public OrmVirtualOverrideRelationship getParent() {
		return (OrmVirtualOverrideRelationship) super.getParent();
	}

	public OrmVirtualOverrideRelationship getRelationship() {
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

	protected Iterator<String> candidateTableNames() {
		return this.isTargetForeignKey() ?
				this.targetCandidateTableNames() :
				this.getRelationship().candidateTableNames();
	}

	protected Iterator<String> targetCandidateTableNames() {
		TypeMapping typeMapping = this.getRelationshipMapping().getResolvedTargetEntity();
		return (typeMapping != null) ? typeMapping.allAssociatedTableNames() : EmptyIterator.<String>instance();
	}

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

	protected String getAttributeName() {
		return this.getRelationship().getAttributeName();
	}

	protected OrmVirtualJoinColumn buildJoinColumn(ReadOnlyJoinColumn overriddenJoinColumn) {
		return this.getContextNodeFactory().buildOrmVirtualJoinColumn(this, this.joinColumnOwner, overriddenJoinColumn);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (OrmVirtualJoinColumn joinColumn : this.getJoinColumns()) {
			joinColumn.validate(messages, reporter);
		}
	}


	// ********** join column owner **********

	protected class JoinColumnOwner
		implements OrmReadOnlyJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getTableName();
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getAttributeName();
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.candidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getReferencedColumnDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return false;
		}

		public int joinColumnsSize() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.joinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return GenericOrmVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationship().buildColumnValidator((ReadOnlyBaseColumn) column, this, (BaseColumnTextRangeResolver) textRangeResolver);
		}
	}
}
