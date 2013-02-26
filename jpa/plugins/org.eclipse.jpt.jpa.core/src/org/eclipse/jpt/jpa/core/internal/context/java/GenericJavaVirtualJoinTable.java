/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.VirtualRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java virtual join table
 */
public class GenericJavaVirtualJoinTable
	extends AbstractJavaVirtualReferenceTable<ReadOnlyJoinTable>
	implements VirtualJoinTable
{

	protected final ContextListContainer<VirtualJoinColumn, ReadOnlyJoinColumn> specifiedInverseJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner inverseJoinColumnOwner;

	protected VirtualJoinColumn defaultInverseJoinColumn;


	public GenericJavaVirtualJoinTable(VirtualJoinTableRelationshipStrategy parent, Owner owner, ReadOnlyJoinTable overriddenTable) {
		super(parent, owner, overriddenTable);
		this.inverseJoinColumnOwner = this.buildInverseJoinColumnOwner();
		this.specifiedInverseJoinColumnContainer = this.buildSpecifiedInverseJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedInverseJoinColumns();
		this.updateDefaultInverseJoinColumn();
	}


	// ********** inverse join columns **********

	public ListIterable<VirtualJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int getInverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumnsSize() : this.getDefaultInverseJoinColumnsSize();
	}


	// ********** inverse specified join columns **********

	public ListIterable<VirtualJoinColumn> getSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumnsSize() != 0;
	}

	public VirtualJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumnContainer.getContextElement(index);
	}

	protected void updateSpecifiedInverseJoinColumns() {
		this.specifiedInverseJoinColumnContainer.update();
	}

	protected ListIterable<ReadOnlyJoinColumn> getOverriddenInverseJoinColumns() {
		return new SuperListIterableWrapper<ReadOnlyJoinColumn>(this.getOverriddenTable().getSpecifiedInverseJoinColumns());
	}

	protected void moveSpecifiedInverseJoinColumn(int index, VirtualJoinColumn joinColumn) {
		this.specifiedInverseJoinColumnContainer.moveContextElement(index, joinColumn);
	}

	protected VirtualJoinColumn addSpecifiedInverseJoinColumn(int index, ReadOnlyJoinColumn joinColumn) {
		return this.specifiedInverseJoinColumnContainer.addContextElement(index, joinColumn);
	}

	protected void removeSpecifiedInverseJoinColumn(VirtualJoinColumn joinColumn) {
		this.specifiedInverseJoinColumnContainer.removeContextElement(joinColumn);
	}

	protected ContextListContainer<VirtualJoinColumn, ReadOnlyJoinColumn> buildSpecifiedInverseJoinColumnContainer() {
		return new SpecifiedInverseJoinColumnContainer();
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedInverseJoinColumnContainer
		extends ContextListContainer<VirtualJoinColumn, ReadOnlyJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_INVERSE_JOIN_COLUMNS_LIST;
		}
		@Override
		protected VirtualJoinColumn buildContextElement(ReadOnlyJoinColumn resourceElement) {
			return GenericJavaVirtualJoinTable.this.buildInverseJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyJoinColumn> getResourceElements() {
			return GenericJavaVirtualJoinTable.this.getOverriddenInverseJoinColumns();
		}
		@Override
		protected ReadOnlyJoinColumn getResourceElement(VirtualJoinColumn contextElement) {
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
	public VirtualJoinTableRelationshipStrategy getParent() {
		return (VirtualJoinTableRelationshipStrategy) super.getParent();
	}

	protected VirtualJoinTableRelationshipStrategy getRelationshipStrategy() {
		return this.getParent();
	}

	@Override
	protected ReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected ReadOnlyJoinColumn.Owner buildInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}

	protected VirtualJoinColumn buildInverseJoinColumn(ReadOnlyJoinColumn joinColumn) {
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
		this.validateModels(this.getInverseJoinColumns(), messages, reporter);
	}

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipStrategy().validatesAgainstDatabase();
	}


	// ********** join column owners **********

	/**
	 * just a little common behavior
	 */
	protected abstract class AbstractJoinColumnOwner
		implements ReadOnlyJoinColumn.Owner
	{
		protected AbstractJoinColumnOwner() {
			super();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return MappingTools.buildJoinColumnDefaultName((ReadOnlyJoinColumn) column, this);
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
			return ObjectTools.equals(GenericJavaVirtualJoinTable.this.getName(), tableName) ?
					GenericJavaVirtualJoinTable.this.getDbTable() :
					null;
		}

		/**
		 * by default, the join column is, obviously, in the join table;
		 * not sure whether it can be anywhere else...
		 */
		public String getDefaultTableName() {
			return GenericJavaVirtualJoinTable.this.getName();
		}

		public TextRange getValidationTextRange() {
			return GenericJavaVirtualJoinTable.this.getValidationTextRange();
		}

		protected VirtualRelationship getRelationship() {
			return GenericJavaVirtualJoinTable.this.getRelationshipStrategy().getRelationship();
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
			return MappingTools.getTargetAttributeName(GenericJavaVirtualJoinTable.this.getRelationshipMapping());
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		protected TypeMapping getTypeMapping() {
			return this.getRelationship().getTypeMapping();
		}

		public int getJoinColumnsSize() {
			return GenericJavaVirtualJoinTable.this.getJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return GenericJavaVirtualJoinTable.this.getRelationshipStrategy().buildJoinTableJoinColumnValidator((ReadOnlyJoinColumn) column, this);
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
			RelationshipMapping relationshipMapping = GenericJavaVirtualJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			RelationshipMapping relationshipMapping = GenericJavaVirtualJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getName();
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericJavaVirtualJoinTable.this.getInverseJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return GenericJavaVirtualJoinTable.this.getRelationshipStrategy().buildJoinTableInverseJoinColumnValidator((ReadOnlyJoinColumn) column, this);
		}
	}
}
