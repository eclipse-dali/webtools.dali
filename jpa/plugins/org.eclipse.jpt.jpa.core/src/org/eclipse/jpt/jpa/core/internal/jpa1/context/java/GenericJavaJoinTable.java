/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationship;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;
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
	extends GenericJavaReferenceTable<JavaSpecifiedJoinTableRelationshipStrategy, JavaSpecifiedJoinTable.ParentAdapter, JoinTableAnnotation>
	implements JavaSpecifiedJoinTable
{
	protected final ContextListContainer<JavaSpecifiedJoinColumn, JoinColumnAnnotation> specifiedInverseJoinColumnContainer;
	protected final JoinColumn.ParentAdapter inverseJoinColumnParentAdapter;

	protected JavaSpecifiedJoinColumn defaultInverseJoinColumn;


	public GenericJavaJoinTable(JavaSpecifiedJoinTable.ParentAdapter parentAdapter) {
		super(parentAdapter);
		this.inverseJoinColumnParentAdapter = this.buildInverseJoinColumnParentAdapter();
		this.specifiedInverseJoinColumnContainer = this.buildSpecifiedInverseJoinColumnContainer();
	}

	@Override
	protected JoinColumn.ParentAdapter buildJoinColumnParentAdapter() {
		return new JoinColumnParentAdapter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncSpecifiedInverseJoinColumns(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getSpecifiedInverseJoinColumns(), monitor);
		this.updateDefaultInverseJoinColumn(monitor);
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

	public ListIterable<JavaSpecifiedJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int getInverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumnsSize() : this.defaultInverseJoinColumnsSize();
	}

	public void convertDefaultInverseJoinColumnToSpecified() {
		MappingTools.convertJoinTableDefaultToSpecifiedInverseJoinColumn(this);
	}


	// ********** specified inverse join columns **********

	public ListIterable<JavaSpecifiedJoinColumn> getSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumnContainer;
	}

	public int getSpecifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumnContainer.size();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumnsSize() != 0;
	}

	public JavaSpecifiedJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumnContainer.get(index);
	}

	public JavaSpecifiedJoinColumn addSpecifiedInverseJoinColumn() {
		return this.addSpecifiedInverseJoinColumn(this.getSpecifiedInverseJoinColumnsSize());
	}

	public JavaSpecifiedJoinColumn addSpecifiedInverseJoinColumn(int index) {
		JoinColumnAnnotation annotation = this.getTableAnnotation().addInverseJoinColumn(index);
		return this.specifiedInverseJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedInverseJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.removeSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumnContainer.indexOf((JavaSpecifiedJoinColumn) joinColumn));
	}

	public void removeSpecifiedInverseJoinColumn(int index) {
		this.getTableAnnotation().removeInverseJoinColumn(index);
		this.removeTableAnnotationIfUnset();
		this.specifiedInverseJoinColumnContainer.remove(index);
	}

	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		this.getTableAnnotation().moveInverseJoinColumn(targetIndex, sourceIndex);
		this.specifiedInverseJoinColumnContainer.move(targetIndex, sourceIndex);
	}

	public void clearSpecifiedInverseJoinColumns() {
		// for now, we have to remove annotations one at a time...
		for (int index = this.getSpecifiedInverseJoinColumnsSize(); --index >= 0; ) {
			this.getTableAnnotation().removeInverseJoinColumn(index);
		}
		this.removeTableAnnotationIfUnset();
		this.specifiedInverseJoinColumnContainer.clear();
	}

	protected void syncSpecifiedInverseJoinColumns(IProgressMonitor monitor) {
		this.specifiedInverseJoinColumnContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<JoinColumnAnnotation> getInverseJoinColumnAnnotations() {
		return this.getTableAnnotation().getInverseJoinColumns();
	}

	protected ContextListContainer<JavaSpecifiedJoinColumn, JoinColumnAnnotation> buildSpecifiedInverseJoinColumnContainer() {
		return this.buildSpecifiedContextListContainer(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, new SpecifiedInverseJoinColumnContainerAdapter());
	}

	/**
	 * specified inverse join column container adapter
	 */
	public class SpecifiedInverseJoinColumnContainerAdapter
		extends AbstractContainerAdapter<JavaSpecifiedJoinColumn, JoinColumnAnnotation>
	{
		public JavaSpecifiedJoinColumn buildContextElement(JoinColumnAnnotation resourceElement) {
			return GenericJavaJoinTable.this.buildInverseJoinColumn(resourceElement);
		}
		public ListIterable<JoinColumnAnnotation> getResourceElements() {
			return GenericJavaJoinTable.this.getInverseJoinColumnAnnotations();
		}
		public JoinColumnAnnotation extractResourceElement(JavaSpecifiedJoinColumn contextElement) {
			return (JoinColumnAnnotation) contextElement.getColumnAnnotation();
		}
	}

	protected JoinColumn.ParentAdapter buildInverseJoinColumnParentAdapter() {
		return new InverseJoinColumnParentAdapter();
	}


	// ********** default inverse join column **********

	public JavaSpecifiedJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}

	protected void setDefaultInverseJoinColumn(JavaSpecifiedJoinColumn joinColumn) {
		JavaSpecifiedJoinColumn old = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<JavaSpecifiedJoinColumn> getDefaultInverseJoinColumns() {
		return (this.defaultInverseJoinColumn != null) ?
				new SingleElementListIterable<JavaSpecifiedJoinColumn>(this.defaultInverseJoinColumn) :
				EmptyListIterable.<JavaSpecifiedJoinColumn>instance();
	}

	protected int defaultInverseJoinColumnsSize() {
		return (this.defaultInverseJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultInverseJoinColumn(IProgressMonitor monitor) {
		if (this.buildsDefaultInverseJoinColumn()) {
			if (this.defaultInverseJoinColumn == null) {
				this.setDefaultInverseJoinColumn(this.buildInverseJoinColumn(new NullJoinColumnAnnotation(this.getTableAnnotation())));
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

	protected JavaSpecifiedJoinTableRelationshipStrategy getRelationshipStrategy() {
		return this.parent;
	}

	@Override
	protected String buildDefaultName() {
		return this.getRelationshipStrategy().getJoinTableDefaultName();
	}

	public void initializeFrom(VirtualJoinTable virtualTable) {
		super.initializeFrom(virtualTable);
		for (VirtualJoinColumn joinColumn : virtualTable.getInverseJoinColumns()) {
			this.addSpecifiedInverseJoinColumn().initializeFrom(joinColumn);
		}
	}

	protected JavaSpecifiedJoinColumn buildInverseJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.buildJoinColumn(this.inverseJoinColumnParentAdapter, joinColumnAnnotation);
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipStrategy().getRelationship().getMapping();
	}

	public SpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getRelationshipMapping().getPersistentAttribute();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (JavaSpecifiedJoinColumn column : this.getInverseJoinColumns()) {
			result = column.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
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
			return GenericJavaJoinTable.this;
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
			return ObjectTools.equals(GenericJavaJoinTable.this.getName(), tableName) ?
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

		public TextRange getValidationTextRange() {
			return GenericJavaJoinTable.this.getValidationTextRange();
		}

		protected SpecifiedRelationship getRelationship() {
			return this.getRelationshipStrategy().getRelationship();
		}

		protected JavaSpecifiedJoinTableRelationshipStrategy getRelationshipStrategy() {
			return GenericJavaJoinTable.this.getRelationshipStrategy();
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
			return MappingTools.getTargetAttributeName(GenericJavaJoinTable.this.getRelationshipMapping());
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		protected TypeMapping getTypeMapping() {
			return this.getRelationship().getTypeMapping();
		}

		public int getJoinColumnsSize() {
			return GenericJavaJoinTable.this.getJoinColumnsSize();
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
			RelationshipMapping relationshipMapping = GenericJavaJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			RelationshipMapping relationshipMapping = GenericJavaJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getName();
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericJavaJoinTable.this.getInverseJoinColumnsSize();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return this.getRelationshipStrategy().buildJoinTableInverseJoinColumnValidator((JoinColumn) column, this);
		}
	}
}
