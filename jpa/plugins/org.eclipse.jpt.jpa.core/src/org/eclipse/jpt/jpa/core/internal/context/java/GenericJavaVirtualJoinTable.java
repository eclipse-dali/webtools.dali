/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java virtual join table
 */
public class GenericJavaVirtualJoinTable
	extends AbstractJavaVirtualReferenceTable<ReadOnlyJoinTable>
	implements JavaVirtualJoinTable
{

	protected final ContextListContainer<JavaVirtualJoinColumn, ReadOnlyJoinColumn> specifiedInverseJoinColumnContainer;
	protected final JavaReadOnlyJoinColumn.Owner inverseJoinColumnOwner;

	protected JavaVirtualJoinColumn defaultInverseJoinColumn;


	public GenericJavaVirtualJoinTable(JavaVirtualJoinTableRelationshipStrategy parent, Owner owner, ReadOnlyJoinTable overriddenTable) {
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

	public ListIterable<JavaVirtualJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int getInverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumnsSize() : this.getDefaultInverseJoinColumnsSize();
	}


	// ********** inverse specified join columns **********

	public ListIterable<JavaVirtualJoinColumn> getSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumnsSize() != 0;
	}

	public JavaVirtualJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumnContainer.getContextElement(index);
	}

	protected void updateSpecifiedInverseJoinColumns() {
		this.specifiedInverseJoinColumnContainer.update();
	}

	protected ListIterable<ReadOnlyJoinColumn> getOverriddenInverseJoinColumns() {
		return new SuperListIterableWrapper<ReadOnlyJoinColumn>(this.getOverriddenTable().getSpecifiedInverseJoinColumns());
	}

	protected void moveSpecifiedInverseJoinColumn(int index, JavaVirtualJoinColumn joinColumn) {
		this.specifiedInverseJoinColumnContainer.moveContextElement(index, joinColumn);
	}

	protected JavaVirtualJoinColumn addSpecifiedInverseJoinColumn(int index, ReadOnlyJoinColumn joinColumn) {
		return this.specifiedInverseJoinColumnContainer.addContextElement(index, joinColumn);
	}

	protected void removeSpecifiedInverseJoinColumn(JavaVirtualJoinColumn joinColumn) {
		this.specifiedInverseJoinColumnContainer.removeContextElement(joinColumn);
	}

	protected ContextListContainer<JavaVirtualJoinColumn, ReadOnlyJoinColumn> buildSpecifiedInverseJoinColumnContainer() {
		return new SpecifiedInverseJoinColumnContainer();
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedInverseJoinColumnContainer
		extends ContextListContainer<JavaVirtualJoinColumn, ReadOnlyJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_INVERSE_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaVirtualJoinColumn buildContextElement(ReadOnlyJoinColumn resourceElement) {
			return GenericJavaVirtualJoinTable.this.buildInverseJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyJoinColumn> getResourceElements() {
			return GenericJavaVirtualJoinTable.this.getOverriddenInverseJoinColumns();
		}
		@Override
		protected ReadOnlyJoinColumn getResourceElement(JavaVirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}


	// ********** default inverse join column **********

	public JavaVirtualJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}

	protected void setDefaultInverseJoinColumn(JavaVirtualJoinColumn joinColumn) {
		JavaVirtualJoinColumn old = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<JavaVirtualJoinColumn> getDefaultInverseJoinColumns() {
		return (this.defaultInverseJoinColumn != null) ?
				new SingleElementListIterable<JavaVirtualJoinColumn>(this.defaultInverseJoinColumn) :
				EmptyListIterable.<JavaVirtualJoinColumn>instance();
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
	public JavaVirtualJoinTableRelationshipStrategy getParent() {
		return (JavaVirtualJoinTableRelationshipStrategy) super.getParent();
	}

	protected JavaVirtualJoinTableRelationshipStrategy getRelationshipStrategy() {
		return this.getParent();
	}

	@Override
	protected JavaReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected JavaReadOnlyJoinColumn.Owner buildInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}

	protected JavaVirtualJoinColumn buildInverseJoinColumn(ReadOnlyJoinColumn joinColumn) {
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
	protected void validateJoinColumns(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validateJoinColumns(messages, reporter, astRoot);
		this.validateNodes(this.getInverseJoinColumns(), messages, reporter, astRoot);
	}

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipStrategy().validatesAgainstDatabase();
	}


	// ********** join column owners **********

	/**
	 * just a little common behavior
	 */
	protected abstract class AbstractJoinColumnOwner
		implements JavaReadOnlyJoinColumn.Owner
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
		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return Tools.valuesAreEqual(GenericJavaVirtualJoinTable.this.getName(), tableName) ?
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

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaVirtualJoinTable.this.getValidationTextRange(astRoot);
		}

		protected JavaVirtualRelationship getRelationship() {
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

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericJavaVirtualJoinTable.this.defaultJoinColumn == joinColumn;
		}

		public int getJoinColumnsSize() {
			return GenericJavaVirtualJoinTable.this.getJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return GenericJavaVirtualJoinTable.this.getRelationshipStrategy().buildJoinTableJoinColumnValidator((ReadOnlyJoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver);
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

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericJavaVirtualJoinTable.this.defaultInverseJoinColumn == joinColumn;
		}

		public int getJoinColumnsSize() {
			return GenericJavaVirtualJoinTable.this.getInverseJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return GenericJavaVirtualJoinTable.this.getRelationshipStrategy().buildJoinTableInverseJoinColumnValidator((ReadOnlyJoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver);
		}
	}
}
