/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.VirtualRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> virtual join table
 */
public class GenericOrmVirtualJoinTable
	extends AbstractOrmVirtualReferenceTable<VirtualJoinTableRelationshipStrategy, VirtualJoinTable.ParentAdapter, JoinTable>
	implements VirtualJoinTable
{

	protected final ContextListContainer<VirtualJoinColumn, JoinColumn> specifiedInverseJoinColumnContainer;
	protected final JoinColumn.ParentAdapter inverseJoinColumnParentAdapter;

	protected VirtualJoinColumn defaultInverseJoinColumn;


	public GenericOrmVirtualJoinTable(VirtualJoinTable.ParentAdapter parentAdapter, JoinTable overriddenTable) {
		super(parentAdapter, overriddenTable);
		this.inverseJoinColumnParentAdapter = this.buildInverseJoinColumnParentAdapter();
		this.specifiedInverseJoinColumnContainer = this.buildSpecifiedInverseJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateSpecifiedInverseJoinColumns(monitor);
		this.updateDefaultInverseJoinColumn(monitor);
	}


	// ********** inverse join columns **********

	public ListIterable<VirtualJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int getInverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumnsSize() : this.getDefaultInverseJoinColumnsSize();
	}


	// ********** specified inverse join columns **********

	public ListIterable<VirtualJoinColumn> getSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumnContainer;
	}

	public int getSpecifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumnContainer.size();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumnsSize() != 0;
	}

	public VirtualJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumnContainer.get(index);
	}

	protected void updateSpecifiedInverseJoinColumns(IProgressMonitor monitor) {
		this.specifiedInverseJoinColumnContainer.update(monitor);
	}

	protected ListIterable<JoinColumn> getOverriddenInverseJoinColumns() {
		return new SuperListIterableWrapper<JoinColumn>(this.getOverriddenTable().getSpecifiedInverseJoinColumns());
	}

	protected void moveSpecifiedInverseJoinColumn(int index, VirtualJoinColumn joinColumn) {
		this.specifiedInverseJoinColumnContainer.move(index, joinColumn);
	}

	protected VirtualJoinColumn addSpecifiedInverseJoinColumn(int index, JoinColumn joinColumn) {
		return this.specifiedInverseJoinColumnContainer.addContextElement(index, joinColumn);
	}

	protected void removeSpecifiedInverseJoinColumn(VirtualJoinColumn joinColumn) {
		this.specifiedInverseJoinColumnContainer.remove(joinColumn);
	}

	protected ContextListContainer<VirtualJoinColumn, JoinColumn> buildSpecifiedInverseJoinColumnContainer() {
		return this.buildVirtualContextListContainer(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, new SpecifiedInverseJoinColumnContainerAdapter());
	}

	/**
	 * specified inverse join column container adapter
	 */
	public class SpecifiedInverseJoinColumnContainerAdapter
		extends AbstractContainerAdapter<VirtualJoinColumn, JoinColumn>
	{
		public VirtualJoinColumn buildContextElement(JoinColumn resourceElement) {
			return GenericOrmVirtualJoinTable.this.buildInverseJoinColumn(resourceElement);
		}
		public ListIterable<JoinColumn> getResourceElements() {
			return GenericOrmVirtualJoinTable.this.getOverriddenInverseJoinColumns();
		}
		public JoinColumn extractResourceElement(VirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}


	// ********** default inverse join column **********

	public VirtualJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}

	protected void setDefaultInverseJoinColumn(VirtualJoinColumn joinColumn) {
		VirtualJoinColumn old = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<VirtualJoinColumn> getDefaultInverseJoinColumns() {
		return (this.defaultInverseJoinColumn != null) ?
				new SingleElementListIterable<VirtualJoinColumn>(this.defaultInverseJoinColumn) :
				EmptyListIterable.<VirtualJoinColumn>instance();
	}

	protected int getDefaultInverseJoinColumnsSize() {
		return (this.defaultInverseJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultInverseJoinColumn(IProgressMonitor monitor) {
		if (this.buildsDefaultInverseJoinColumn()) {
			if (this.defaultInverseJoinColumn == null) {
				this.setDefaultInverseJoinColumn(this.buildInverseJoinColumn(this.getOverriddenTable().getDefaultInverseJoinColumn()));
			} else {
				this.defaultInverseJoinColumn.update(monitor);
			}
		} else {
			this.setDefaultInverseJoinColumn(null);
		}
	}

	protected boolean buildsDefaultInverseJoinColumn() {
		return ! this.hasSpecifiedInverseJoinColumns();
	}


	// ********** misc **********

	protected VirtualJoinTableRelationshipStrategy getRelationshipStrategy() {
		return this.parent;
	}

	@Override
	protected JoinColumn.ParentAdapter buildJoinColumnParentAdapter() {
		return new JoinColumnParentAdapter();
	}

	protected JoinColumn.ParentAdapter buildInverseJoinColumnParentAdapter() {
		return new InverseJoinColumnParentAdapter();
	}

	protected VirtualJoinColumn buildInverseJoinColumn(JoinColumn joinColumn) {
		return this.buildJoinColumn(this.inverseJoinColumnParentAdapter, joinColumn);
	}

	@Override
	protected String buildDefaultName() {
		return this.getRelationshipStrategy().getJoinTableDefaultName();
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipStrategy().getRelationship().getMapping();
	}

	public SpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getRelationshipMapping().getPersistentAttribute();
	}


	// ********** validation **********

	@Override
	protected void validateJoinColumns(List<IMessage> messages, IReporter reporter) {
		super.validateJoinColumns(messages, reporter);
		this.validateModels(this.getInverseJoinColumns(), messages, reporter);
	}

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipStrategy().validatesAgainstDatabase();
	}


	// ********** join column parent adapters **********

	/**
	 * just a little common behavior
	 */
	public abstract class AbstractJoinColumnParentAdapter
		implements JoinColumn.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return GenericOrmVirtualJoinTable.this;
		}

		public String getDefaultColumnName(NamedColumn column) {
			return MappingTools.buildJoinColumnDefaultName((JoinColumn) column, this);
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name. The table is always the join table.
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return ObjectTools.notEquals(this.getDefaultTableName(), tableName);
		}

		/**
		 * the join column can only be on the join table itself
		 */
		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return ObjectTools.equals(GenericOrmVirtualJoinTable.this.getName(), tableName) ?
					GenericOrmVirtualJoinTable.this.getDbTable() :
					null;
		}

		/**
		 * by default, the join column is, obviously, in the join table;
		 * not sure whether it can be anywhere else...
		 */
		public String getDefaultTableName() {
			return GenericOrmVirtualJoinTable.this.getName();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmVirtualJoinTable.this.getValidationTextRange();
		}

		protected VirtualRelationship getRelationship() {
			return this.getRelationshipStrategy().getRelationship();
		}

		protected VirtualJoinTableRelationshipStrategy getRelationshipStrategy() {
			return GenericOrmVirtualJoinTable.this.getRelationshipStrategy();
		}
	}


	/**
	 * parent adapter for "back-pointer" join columns;
	 * these point at the source/owning entity
	 */
	public class JoinColumnParentAdapter
		extends AbstractJoinColumnParentAdapter
	{
		public Entity getRelationshipTarget() {
			return this.getRelationship().getEntity();
		}

		public String getAttributeName() {
			return MappingTools.getTargetAttributeName(GenericOrmVirtualJoinTable.this.getRelationshipMapping());
		}

		@Override
		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			org.eclipse.jpt.jpa.db.Table dbTable = super.resolveDbTable(tableName);
			return (dbTable != null) ? dbTable : this.getTypeMapping().resolveDbTable(tableName);
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		protected TypeMapping getTypeMapping() {
			return this.getRelationship().getTypeMapping();
		}

		public int getJoinColumnsSize() {
			return GenericOrmVirtualJoinTable.this.getJoinColumnsSize();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return this.getRelationshipStrategy().buildJoinTableJoinColumnValidator((JoinColumn) column, this);
		}
	}


	/**
	 * parent adapter for "forward-pointer" join columns;
	 * these point at the target/inverse entity
	 */
	public class InverseJoinColumnParentAdapter
		extends AbstractJoinColumnParentAdapter
	{
		public Entity getRelationshipTarget() {
			RelationshipMapping relationshipMapping = GenericOrmVirtualJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			RelationshipMapping relationshipMapping = GenericOrmVirtualJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getName();
		}

		@Override
		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			org.eclipse.jpt.jpa.db.Table dbTable = super.resolveDbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.resolveDbTable(tableName);
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericOrmVirtualJoinTable.this.getInverseJoinColumnsSize();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return this.getRelationshipStrategy().buildJoinTableInverseJoinColumnValidator((JoinColumn) column, this);
		}
	}
}
