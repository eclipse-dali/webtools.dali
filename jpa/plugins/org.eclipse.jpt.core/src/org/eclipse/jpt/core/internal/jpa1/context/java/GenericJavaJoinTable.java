/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java join table
 * <p>
 * Note: The <code>JoinTable</code> annotation is one of only 2 annotations that
 * can be nested outside of an array (i.e. in an <code>AssociationOverride</code>
 * annotation); the other is {@link GenericJavaColumn Column}.
 */
public class GenericJavaJoinTable
	extends GenericJavaReferenceTable<JoinTableAnnotation>
	implements JavaJoinTable
{
	protected final Vector<JavaJoinColumn> specifiedInverseJoinColumns = new Vector<JavaJoinColumn>();
	protected final SpecifiedInverseJoinColumnContainerAdapter specifiedInverseJoinColumnContainerAdapter = new SpecifiedInverseJoinColumnContainerAdapter();
	protected final JavaJoinColumn.Owner inverseJoinColumnOwner;

	protected JavaJoinColumn defaultInverseJoinColumn;


	public GenericJavaJoinTable(JavaJoinTableRelationshipStrategy parent, Owner owner) {
		super(parent, owner);
		this.inverseJoinColumnOwner = this.buildInverseJoinColumnOwner();
		this.initializeSpecifiedInverseJoinColumns();
	}

	@Override
	protected JavaJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSpecifiedInverseJoinColumns();
		if (this.defaultInverseJoinColumn != null) {
			this.defaultInverseJoinColumn.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getSpecifiedInverseJoinColumns());
		this.updateDefaultInverseJoinColumn();
	}


	// ********** table annotation **********

	@Override
	public JoinTableAnnotation getTableAnnotation() {
		return this.getRelationshipStrategy().getJoinTableAnnotation();
	}

	@Override
	protected void removeTableAnnotation() {
		// we don't remove a join table annotation when it is empty
	}


	// ********** inverse join columns **********

	public ListIterator<JavaJoinColumn> inverseJoinColumns() {
		return this.getInverseJoinColumns().iterator();
	}

	protected ListIterable<JavaJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int inverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumnsSize() : this.defaultInverseJoinColumnsSize();
	}

	public void convertDefaultToSpecifiedInverseJoinColumn() {
		MappingTools.convertJoinTableDefaultToSpecifiedInverseJoinColumn(this);
	}


	// ********** specified inverse join columns **********

	public ListIterator<JavaJoinColumn> specifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumns().iterator();
	}

	public ListIterable<JavaJoinColumn> getSpecifiedInverseJoinColumns() {
		return new LiveCloneListIterable<JavaJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumns.size() != 0;
	}

	public JavaJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumns.get(index);
	}

	public JavaJoinColumn addSpecifiedInverseJoinColumn() {
		return this.addSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.size());
	}

	public JavaJoinColumn addSpecifiedInverseJoinColumn(int index) {
		JoinColumnAnnotation annotation = this.getTableAnnotation().addInverseJoinColumn(index);
		return this.addSpecifiedInverseJoinColumn_(index, annotation);
	}

	public void removeSpecifiedInverseJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedInverseJoinColumn(int index) {
		this.getTableAnnotation().removeInverseJoinColumn(index);
		this.removeTableAnnotationIfUnset();
		this.removeSpecifiedInverseJoinColumn_(index);
	}

	protected void removeSpecifiedInverseJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		this.getTableAnnotation().moveInverseJoinColumn(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	public void clearSpecifiedInverseJoinColumns() {
		// for now, we have to remove annotations one at a time...
		for (int i = this.specifiedInverseJoinColumns.size(); i-- > 0; ) {
			this.removeSpecifiedInverseJoinColumn(i);
		}
	}

	protected void initializeSpecifiedInverseJoinColumns() {
		for (JoinColumnAnnotation joinColumnAnnotation : this.getInverseJoinColumnAnnotations()) {
			this.specifiedInverseJoinColumns.add(this.buildInverseJoinColumn(joinColumnAnnotation));
		}
	}

	protected void syncSpecifiedInverseJoinColumns() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedInverseJoinColumnContainerAdapter);
	}

	protected Iterable<JoinColumnAnnotation> getInverseJoinColumnAnnotations() {
		return CollectionTools.iterable(this.getTableAnnotation().inverseJoinColumns());
	}

	protected void moveSpecifiedInverseJoinColumn_(int index, JavaJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	protected JavaJoinColumn addSpecifiedInverseJoinColumn_(int index, JoinColumnAnnotation joinColumnAnnotation) {
		JavaJoinColumn joinColumn = this.buildInverseJoinColumn(joinColumnAnnotation);
		this.addItemToList(index, joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
		return joinColumn;
	}

	protected void removeSpecifiedInverseJoinColumn_(JavaJoinColumn joinColumn) {
		this.removeSpecifiedInverseJoinColumn_(this.specifiedInverseJoinColumns.indexOf(joinColumn));
	}

	/**
	 * specified inverse join column container adapter
	 */
	protected class SpecifiedInverseJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<JavaJoinColumn, JoinColumnAnnotation>
	{
		public Iterable<JavaJoinColumn> getContextElements() {
			return GenericJavaJoinTable.this.getSpecifiedInverseJoinColumns();
		}
		public Iterable<JoinColumnAnnotation> getResourceElements() {
			return GenericJavaJoinTable.this.getInverseJoinColumnAnnotations();
		}
		public JoinColumnAnnotation getResourceElement(JavaJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
		public void moveContextElement(int index, JavaJoinColumn element) {
			GenericJavaJoinTable.this.moveSpecifiedInverseJoinColumn_(index, element);
		}
		public void addContextElement(int index, JoinColumnAnnotation resourceElement) {
			GenericJavaJoinTable.this.addSpecifiedInverseJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(JavaJoinColumn element) {
			GenericJavaJoinTable.this.removeSpecifiedInverseJoinColumn_(element);
		}
	}

	protected JavaJoinColumn.Owner buildInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}


	// ********** default inverse join column **********

	public JavaJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}

	protected void setDefaultInverseJoinColumn(JavaJoinColumn joinColumn) {
		JavaJoinColumn old = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<JavaJoinColumn> getDefaultInverseJoinColumns() {
		return (this.defaultInverseJoinColumn != null) ?
				new SingleElementListIterable<JavaJoinColumn>(this.defaultInverseJoinColumn) :
				EmptyListIterable.<JavaJoinColumn>instance();
	}

	protected int defaultInverseJoinColumnsSize() {
		return (this.defaultInverseJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultInverseJoinColumn() {
		if (this.buildsDefaultInverseJoinColumn()) {
			if (this.defaultInverseJoinColumn == null) {
				this.setDefaultInverseJoinColumn(this.buildInverseJoinColumn(new NullJoinColumnAnnotation(this.getTableAnnotation())));
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
	public JavaJoinTableRelationshipStrategy getParent() {
		return (JavaJoinTableRelationshipStrategy) super.getParent();
	}

	protected JavaJoinTableRelationshipStrategy getRelationshipStrategy() {
		return this.getParent();
	}

	@Override
	protected String buildDefaultName() {
		return this.getRelationshipStrategy().getJoinTableDefaultName();
	}

	public void initializeFrom(ReadOnlyJoinTable oldTable) {
		super.initializeFrom(oldTable);
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(oldTable.specifiedInverseJoinColumns())) {
			this.addSpecifiedInverseJoinColumn().initializeFrom(joinColumn);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinTable virtualTable) {
		super.initializeFromVirtual(virtualTable);
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(virtualTable.inverseJoinColumns())) {
			this.addSpecifiedInverseJoinColumn().initializeFromVirtual(joinColumn);
		}
	}

	protected JavaJoinColumn buildInverseJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.buildJoinColumn(this.inverseJoinColumnOwner, joinColumnAnnotation);
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipStrategy().getRelationship().getMapping();
	}

	public PersistentAttribute getPersistentAttribute() {
		return this.getRelationshipMapping().getPersistentAttribute();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaJoinColumn column : CollectionTools.iterable(this.inverseJoinColumns())) {
			result = column.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	protected void validateJoinColumns(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validateJoinColumns(messages, reporter, astRoot);
		this.validateJoinColumns(this.inverseJoinColumns(), messages, reporter, astRoot);
	}

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipStrategy().validatesAgainstDatabase();
	}


	// ********** join column owners **********

	/**
	 * just a little common behavior
	 */
	protected abstract class AbstractJoinColumnOwner
		implements JavaJoinColumn.Owner
	{
		protected AbstractJoinColumnOwner() {
			super();
		}

		public TypeMapping getTypeMapping() {
			return GenericJavaJoinTable.this.getRelationshipStrategy().getRelationship().getTypeMapping();
		}

		public PersistentAttribute getPersistentAttribute() {
			return GenericJavaJoinTable.this.getPersistentAttribute();
		}

		/**
		 * @see MappingTools#buildJoinColumnDefaultName(org.eclipse.jpt.core.context.ReadOnlyJoinColumn, org.eclipse.jpt.core.context.ReadOnlyJoinColumn.Owner)
		 */
		public String getDefaultColumnName() {
			throw new UnsupportedOperationException();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the join table
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

		public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
			return Tools.valuesAreEqual(GenericJavaJoinTable.this.getName(), tableName) ?
					GenericJavaJoinTable.this.getDbTable() :
					null;
		}

		/**
		 * by default, the join column is, obviously, in the join table;
		 * not sure whether it can be anywhere else...
		 */
		public String getDefaultTableName() {
			return GenericJavaJoinTable.this.getName();
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaJoinTable.this.getValidationTextRange(astRoot);
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
			return GenericJavaJoinTable.this.getRelationshipStrategy().getRelationship().getEntity();
		}

		public String getAttributeName() {
			return MappingTools.getTargetAttributeName(GenericJavaJoinTable.this.getRelationshipMapping());
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericJavaJoinTable.this.defaultJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return GenericJavaJoinTable.this.joinColumnsSize();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return GenericJavaJoinTable.this.getParent().buildJoinTableJoinColumnValidator((JoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver);
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
			RelationshipMapping relationshipMapping = GenericJavaJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			RelationshipMapping relationshipMapping = GenericJavaJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getName();
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericJavaJoinTable.this.defaultInverseJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return GenericJavaJoinTable.this.inverseJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return GenericJavaJoinTable.this.getParent().buildJoinTableInverseJoinColumnValidator((JoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver);
		}
	}
}
