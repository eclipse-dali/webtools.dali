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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
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
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualRelationship;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
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
	protected final ReadOnlyJoinTable overriddenTable;

	protected final Vector<JavaVirtualJoinColumn> specifiedInverseJoinColumns = new Vector<JavaVirtualJoinColumn>();
	protected final SpecifiedInverseJoinColumnContainerAdapter specifiedInverseJoinColumnContainerAdapter = new SpecifiedInverseJoinColumnContainerAdapter();
	protected final JavaReadOnlyJoinColumn.Owner inverseJoinColumnOwner;

	protected JavaVirtualJoinColumn defaultInverseJoinColumn;


	public GenericJavaVirtualJoinTable(JavaVirtualJoinTableRelationshipStrategy parent, Owner owner, ReadOnlyJoinTable overriddenTable) {
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

	public ListIterator<JavaVirtualJoinColumn> inverseJoinColumns() {
		return this.getInverseJoinColumns().iterator();
	}

	protected ListIterable<JavaVirtualJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int inverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumnsSize() : this.getDefaultInverseJoinColumnsSize();
	}


	// ********** inverse specified join columns **********

	public ListIterator<JavaVirtualJoinColumn> specifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumns().iterator();
	}

	protected ListIterable<JavaVirtualJoinColumn> getSpecifiedInverseJoinColumns() {
		return new LiveCloneListIterable<JavaVirtualJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumns.size() != 0;
	}

	public JavaVirtualJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumns.get(index);
	}

	protected void updateSpecifiedInverseJoinColumns() {
		ContextContainerTools.update(this.specifiedInverseJoinColumnContainerAdapter);
	}

	protected Iterable<ReadOnlyJoinColumn> getOverriddenInverseJoinColumns() {
		return CollectionTools.iterable(this.getOverriddenTable().specifiedInverseJoinColumns());
	}

	protected void moveSpecifiedInverseJoinColumn(int index, JavaVirtualJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	protected JavaVirtualJoinColumn addSpecifiedInverseJoinColumn(int index, ReadOnlyJoinColumn joinColumn) {
		JavaVirtualJoinColumn virtualJoinColumn = this.buildInverseJoinColumn(joinColumn);
		this.addItemToList(index, virtualJoinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
		return virtualJoinColumn;
	}

	protected void removeSpecifiedInverseJoinColumn(JavaVirtualJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	/**
	 * specified inverse join column container adapter
	 */
	protected class SpecifiedInverseJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<JavaVirtualJoinColumn, ReadOnlyJoinColumn>
	{
		public Iterable<JavaVirtualJoinColumn> getContextElements() {
			return GenericJavaVirtualJoinTable.this.getSpecifiedInverseJoinColumns();
		}
		public Iterable<ReadOnlyJoinColumn> getResourceElements() {
			return GenericJavaVirtualJoinTable.this.getOverriddenInverseJoinColumns();
		}
		public ReadOnlyJoinColumn getResourceElement(JavaVirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
		public void moveContextElement(int index, JavaVirtualJoinColumn element) {
			GenericJavaVirtualJoinTable.this.moveSpecifiedInverseJoinColumn(index, element);
		}
		public void addContextElement(int index, ReadOnlyJoinColumn element) {
			GenericJavaVirtualJoinTable.this.addSpecifiedInverseJoinColumn(index, element);
		}
		public void removeContextElement(JavaVirtualJoinColumn element) {
			GenericJavaVirtualJoinTable.this.removeSpecifiedInverseJoinColumn(element);
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
		public Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
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

		public int joinColumnsSize() {
			return GenericJavaVirtualJoinTable.this.joinColumnsSize();
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

		public int joinColumnsSize() {
			return GenericJavaVirtualJoinTable.this.inverseJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return GenericJavaVirtualJoinTable.this.getRelationshipStrategy().buildJoinTableInverseJoinColumnValidator((ReadOnlyJoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver);
		}
	}
}
