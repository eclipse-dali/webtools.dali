/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
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
	extends GenericJavaReferenceTable<JoinTableAnnotation>
	implements JavaJoinTable
{
	protected final ContextListContainer<JavaJoinColumn, JoinColumnAnnotation> specifiedInverseJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner inverseJoinColumnOwner;

	protected JavaJoinColumn defaultInverseJoinColumn;


	public GenericJavaJoinTable(JavaJoinTableRelationshipStrategy parent, Owner owner) {
		super(parent, owner);
		this.inverseJoinColumnOwner = this.buildInverseJoinColumnOwner();
		this.specifiedInverseJoinColumnContainer = this.buildSpecifiedInverseJoinColumnContainer();
	}

	@Override
	protected ReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSpecifiedInverseJoinColumns();
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

	public ListIterable<JavaJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int getInverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumnsSize() : this.defaultInverseJoinColumnsSize();
	}

	public void convertDefaultInverseJoinColumnToSpecified() {
		MappingTools.convertJoinTableDefaultToSpecifiedInverseJoinColumn(this);
	}


	// ********** specified inverse join columns **********

	public ListIterable<JavaJoinColumn> getSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumnsSize() != 0;
	}

	public JavaJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumnContainer.getContextElement(index);
	}

	public JavaJoinColumn addSpecifiedInverseJoinColumn() {
		return this.addSpecifiedInverseJoinColumn(this.getSpecifiedInverseJoinColumnsSize());
	}

	public JavaJoinColumn addSpecifiedInverseJoinColumn(int index) {
		JoinColumnAnnotation annotation = this.getTableAnnotation().addInverseJoinColumn(index);
		return this.specifiedInverseJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedInverseJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumnContainer.indexOfContextElement((JavaJoinColumn) joinColumn));
	}

	public void removeSpecifiedInverseJoinColumn(int index) {
		this.getTableAnnotation().removeInverseJoinColumn(index);
		this.removeTableAnnotationIfUnset();
		this.specifiedInverseJoinColumnContainer.removeContextElement(index);
	}

	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		this.getTableAnnotation().moveInverseJoinColumn(targetIndex, sourceIndex);
		this.specifiedInverseJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	public void clearSpecifiedInverseJoinColumns() {
		// for now, we have to remove annotations one at a time...
		for (int index = this.getSpecifiedInverseJoinColumnsSize(); --index >= 0; ) {
			this.getTableAnnotation().removeInverseJoinColumn(index);
		}
		this.removeTableAnnotationIfUnset();
		this.specifiedInverseJoinColumnContainer.clearContextList();
	}

	protected void syncSpecifiedInverseJoinColumns() {
		this.specifiedInverseJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<JoinColumnAnnotation> getInverseJoinColumnAnnotations() {
		return this.getTableAnnotation().getInverseJoinColumns();
	}

	/**
	 * inverse join column container
	 */
	protected class SpecifiedInverseJoinColumnContainer
		extends ContextListContainer<JavaJoinColumn, JoinColumnAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_INVERSE_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaJoinColumn buildContextElement(JoinColumnAnnotation resourceElement) {
			return GenericJavaJoinTable.this.buildInverseJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<JoinColumnAnnotation> getResourceElements() {
			return GenericJavaJoinTable.this.getInverseJoinColumnAnnotations();
		}
		@Override
		protected JoinColumnAnnotation getResourceElement(JavaJoinColumn contextElement) {
			return (JoinColumnAnnotation) contextElement.getColumnAnnotation();
		}
	}

	protected ReadOnlyJoinColumn.Owner buildInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}

	protected ContextListContainer<JavaJoinColumn, JoinColumnAnnotation> buildSpecifiedInverseJoinColumnContainer() {
		SpecifiedInverseJoinColumnContainer container = new SpecifiedInverseJoinColumnContainer();
		container.initialize();
		return container;
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
		for (ReadOnlyJoinColumn joinColumn : oldTable.getSpecifiedInverseJoinColumns()) {
			this.addSpecifiedInverseJoinColumn().initializeFrom(joinColumn);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinTable virtualTable) {
		super.initializeFromVirtual(virtualTable);
		for (ReadOnlyJoinColumn joinColumn : virtualTable.getInverseJoinColumns()) {
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
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (JavaJoinColumn column : this.getInverseJoinColumns()) {
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
			return Tools.valuesAreDifferent(this.getDefaultTableName(), tableName);
		}

		/**
		 * the join column can only be on the join table itself
		 */
		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
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

		public TextRange getValidationTextRange() {
			return GenericJavaJoinTable.this.getValidationTextRange();
		}

		protected JavaRelationship getRelationship() {
			return this.getRelationshipStrategy().getRelationship();
		}

		protected JavaJoinTableRelationshipStrategy getRelationshipStrategy() {
			return GenericJavaJoinTable.this.getRelationshipStrategy();
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

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return this.getRelationshipStrategy().buildJoinTableJoinColumnValidator((ReadOnlyJoinColumn) column, this);
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

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericJavaJoinTable.this.getInverseJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return this.getRelationshipStrategy().buildJoinTableInverseJoinColumnValidator((ReadOnlyJoinColumn) column, this);
		}
	}
}
