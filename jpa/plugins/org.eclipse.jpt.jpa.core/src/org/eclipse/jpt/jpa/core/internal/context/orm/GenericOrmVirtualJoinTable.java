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
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualRelationship;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> virtual join table
 */
public class GenericOrmVirtualJoinTable
	extends AbstractOrmVirtualReferenceTable<ReadOnlyJoinTable>
	implements OrmVirtualJoinTable
{
	protected final ReadOnlyJoinTable overriddenTable;

	protected final Vector<OrmVirtualJoinColumn> specifiedInverseJoinColumns = new Vector<OrmVirtualJoinColumn>();
	protected final SpecifiedInverseJoinColumnContainerAdapter specifiedInverseJoinColumnContainerAdapter = new SpecifiedInverseJoinColumnContainerAdapter();
	protected final OrmReadOnlyJoinColumn.Owner inverseJoinColumnOwner;

	protected OrmVirtualJoinColumn defaultInverseJoinColumn;


	public GenericOrmVirtualJoinTable(OrmVirtualJoinTableRelationshipStrategy parent, Owner owner, ReadOnlyJoinTable overriddenTable) {
		super(parent, owner);
		this.overriddenTable = overriddenTable;
		this.inverseJoinColumnOwner = this.buildInverseJoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedInverseJoinColumns();
		this.updateDefaultInverseJoinColumn();
	}


	// ********** table **********

	@Override
	public ReadOnlyJoinTable getOverriddenTable() {
		return this.overriddenTable;
	}


	// ********** inverse join columns **********

	public ListIterator<OrmVirtualJoinColumn> inverseJoinColumns() {
		return this.getInverseJoinColumns().iterator();
	}

	protected ListIterable<OrmVirtualJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int inverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumnsSize() : this.getDefaultInverseJoinColumnsSize();
	}


	// ********** specified inverse join columns **********

	public ListIterator<OrmVirtualJoinColumn> specifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumns().iterator();
	}

	protected ListIterable<OrmVirtualJoinColumn> getSpecifiedInverseJoinColumns() {
		return new LiveCloneListIterable<OrmVirtualJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumns.size() != 0;
	}

	public OrmVirtualJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumns.get(index);
	}

	protected void updateSpecifiedInverseJoinColumns() {
		ContextContainerTools.update(this.specifiedInverseJoinColumnContainerAdapter);
	}

	protected Iterable<ReadOnlyJoinColumn> getOverriddenInverseJoinColumns() {
		return CollectionTools.iterable(this.getOverriddenTable().specifiedInverseJoinColumns());
	}

	protected void moveSpecifiedInverseJoinColumn(int index, OrmVirtualJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	protected OrmVirtualJoinColumn addSpecifiedInverseJoinColumn(int index, ReadOnlyJoinColumn joinColumn) {
		OrmVirtualJoinColumn virtualJoinColumn = this.buildInverseJoinColumn(joinColumn);
		this.addItemToList(index, virtualJoinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
		return virtualJoinColumn;
	}

	protected void removeSpecifiedInverseJoinColumn(OrmVirtualJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	/**
	 * specified inverse join column container adapter
	 */
	protected class SpecifiedInverseJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmVirtualJoinColumn, ReadOnlyJoinColumn>
	{
		public Iterable<OrmVirtualJoinColumn> getContextElements() {
			return GenericOrmVirtualJoinTable.this.getSpecifiedInverseJoinColumns();
		}
		public Iterable<ReadOnlyJoinColumn> getResourceElements() {
			return GenericOrmVirtualJoinTable.this.getOverriddenInverseJoinColumns();
		}
		public ReadOnlyJoinColumn getResourceElement(OrmVirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
		public void moveContextElement(int index, OrmVirtualJoinColumn element) {
			GenericOrmVirtualJoinTable.this.moveSpecifiedInverseJoinColumn(index, element);
		}
		public void addContextElement(int index, ReadOnlyJoinColumn element) {
			GenericOrmVirtualJoinTable.this.addSpecifiedInverseJoinColumn(index, element);
		}
		public void removeContextElement(OrmVirtualJoinColumn element) {
			GenericOrmVirtualJoinTable.this.removeSpecifiedInverseJoinColumn(element);
		}
	}


	// ********** default inverse join column **********

	public OrmVirtualJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}

	protected void setDefaultInverseJoinColumn(OrmVirtualJoinColumn joinColumn) {
		OrmVirtualJoinColumn old = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<OrmVirtualJoinColumn> getDefaultInverseJoinColumns() {
		return (this.defaultInverseJoinColumn != null) ?
				new SingleElementListIterable<OrmVirtualJoinColumn>(this.defaultInverseJoinColumn) :
				EmptyListIterable.<OrmVirtualJoinColumn>instance();
	}

	protected int getDefaultInverseJoinColumnsSize() {
		return (this.defaultInverseJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultInverseJoinColumn() {
		if (this.buildsDefaultInverseJoinColumn()) {
			if (this.defaultInverseJoinColumn == null) {
				this.setDefaultInverseJoinColumn(this.buildInverseJoinColumn(this.getOverriddenTable().getDefaultInverseJoinColumn()));
			} else {
				this.defaultInverseJoinColumn.update();
			}
		} else {
			this.setDefaultInverseJoinColumn(null);
		}
	}

	protected boolean buildsDefaultInverseJoinColumn() {
		return ! this.hasSpecifiedInverseJoinColumns();
	}


	// ********** misc **********

	@Override
	public OrmVirtualJoinTableRelationshipStrategy getParent() {
		return (OrmVirtualJoinTableRelationshipStrategy) super.getParent();
	}

	protected OrmVirtualJoinTableRelationshipStrategy getRelationshipStrategy() {
		return this.getParent();
	}

	@Override
	protected OrmReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected OrmReadOnlyJoinColumn.Owner buildInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}

	protected OrmVirtualJoinColumn buildInverseJoinColumn(ReadOnlyJoinColumn joinColumn) {
		return this.buildJoinColumn(this.inverseJoinColumnOwner, joinColumn);
	}

	@Override
	protected String buildDefaultName() {
		return this.getRelationshipStrategy().getJoinTableDefaultName();
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipStrategy().getRelationship().getMapping();
	}

	public PersistentAttribute getPersistentAttribute() {
		return this.getRelationshipMapping().getPersistentAttribute();
	}


	// ********** validation **********

	@Override
	protected void validateJoinColumns(List<IMessage> messages, IReporter reporter) {
		super.validateJoinColumns(messages, reporter);
		this.validateNodes(this.getInverseJoinColumns(), messages, reporter);
	}

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipStrategy().validatesAgainstDatabase();
	}


	// ********** join column owners **********

	/**
	 * just a little common behavior
	 */
	protected abstract class AbstractJoinColumnOwner
		implements OrmReadOnlyJoinColumn.Owner
	{
		protected AbstractJoinColumnOwner() {
			super();
		}

		public TypeMapping getTypeMapping() {
			return this.getRelationship().getTypeMapping();
		}

		/**
		 * @see MappingTools#buildJoinColumnDefaultName(org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn, org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn.Owner)
		 */
		public String getDefaultColumnName() {
			throw new UnsupportedOperationException();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name. The table is always the join table.
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return Tools.valuesAreDifferent(this.getDefaultTableName(), tableName);
		}

		/**
		 * the join column can only be on the join table itself
		 */
		public Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return Tools.valuesAreEqual(GenericOrmVirtualJoinTable.this.getName(), tableName) ?
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

		protected OrmVirtualRelationship getRelationship() {
			return this.getRelationshipStrategy().getRelationship();
		}

		protected OrmVirtualJoinTableRelationshipStrategy getRelationshipStrategy() {
			return GenericOrmVirtualJoinTable.this.getRelationshipStrategy();
		}
	}


	/**
	 * owner for "back-pointer" join columns;
	 * these point at the source/owning entity
	 */
	protected class JoinColumnOwner
		extends AbstractJoinColumnOwner
	{
		protected JoinColumnOwner() {
			super();
		}

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

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericOrmVirtualJoinTable.this.defaultJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return GenericOrmVirtualJoinTable.this.joinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return this.getRelationshipStrategy().buildJoinTableJoinColumnValidator((ReadOnlyJoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver);
		}
	}


	/**
	 * owner for "forward-pointer" join columns;
	 * these point at the target/inverse entity
	 */
	protected class InverseJoinColumnOwner
		extends AbstractJoinColumnOwner
	{
		protected InverseJoinColumnOwner() {
			super();
		}

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

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericOrmVirtualJoinTable.this.defaultInverseJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return GenericOrmVirtualJoinTable.this.inverseJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return this.getRelationshipStrategy().buildJoinTableInverseJoinColumnValidator((ReadOnlyJoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver);
		}
	}
}
