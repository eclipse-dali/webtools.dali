/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.OneToOnePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.OverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedMappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaPrimaryKeyJoinColumnRelationshipStrategy
	extends AbstractJavaContextModel<JavaPrimaryKeyJoinColumnRelationship>
	implements SpecifiedMappingRelationshipStrategy2_0, JavaSpecifiedPrimaryKeyJoinColumnRelationshipStrategy
{
	protected final ContextListContainer<JavaSpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> primaryKeyJoinColumnContainer;
	protected final JoinColumn.ParentAdapter primaryKeyJoinColumnParentAdapter;


	public GenericJavaPrimaryKeyJoinColumnRelationshipStrategy(JavaPrimaryKeyJoinColumnRelationship parent) {
		super(parent);
		this.primaryKeyJoinColumnParentAdapter = this.buildPrimaryKeyJoinColumnParentAdapter();
		this.primaryKeyJoinColumnContainer = this.buildPrimaryKeyJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncPrimaryKeyJoinColumns(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getPrimaryKeyJoinColumns(), monitor);
	}


	// ********** primary key join columns **********

	public ListIterable<JavaSpecifiedPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.primaryKeyJoinColumnContainer;
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumnContainer.size();
	}

	public boolean hasPrimaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumnsSize() != 0;
	}

	public JavaSpecifiedPrimaryKeyJoinColumn getPrimaryKeyJoinColumn(int index) {
		return this.primaryKeyJoinColumnContainer.get(index);
	}

	public JavaSpecifiedPrimaryKeyJoinColumn addPrimaryKeyJoinColumn() {
		return this.addPrimaryKeyJoinColumn(this.getPrimaryKeyJoinColumnsSize());
	}

	public JavaSpecifiedPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		PrimaryKeyJoinColumnAnnotation annotation = this.addPrimaryKeyJoinColumnAnnotation(index);
		return this.primaryKeyJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removePrimaryKeyJoinColumn(SpecifiedPrimaryKeyJoinColumn joinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumnContainer.indexOf((JavaSpecifiedPrimaryKeyJoinColumn) joinColumn));
	}

	public void removePrimaryKeyJoinColumn(int index) {
		this.removePrimaryKeyJoinColumnAnnotation(index);
		this.primaryKeyJoinColumnContainer.remove(index);
	}

	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.movePrimaryKeyJoinColumnAnnotation(targetIndex, sourceIndex);
		this.primaryKeyJoinColumnContainer.move(targetIndex, sourceIndex);
	}

	protected void syncPrimaryKeyJoinColumns(IProgressMonitor monitor) {
		this.primaryKeyJoinColumnContainer.synchronizeWithResourceModel(monitor);
	}

	protected ContextListContainer<JavaSpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> buildPrimaryKeyJoinColumnContainer() {
		return this.buildSpecifiedContextListContainer(PRIMARY_KEY_JOIN_COLUMNS_LIST, new PrimaryKeyJoinColumnContainerAdapter());
	}

	/**
	 * primary key join column container adapter
	 */
	public class PrimaryKeyJoinColumnContainerAdapter
		extends AbstractContainerAdapter<JavaSpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation>
	{
		public JavaSpecifiedPrimaryKeyJoinColumn buildContextElement(PrimaryKeyJoinColumnAnnotation resourceElement) {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		public ListIterable<PrimaryKeyJoinColumnAnnotation> getResourceElements() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumnAnnotations();
		}
		public PrimaryKeyJoinColumnAnnotation extractResourceElement(JavaSpecifiedPrimaryKeyJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
	}

	protected JoinColumn.ParentAdapter buildPrimaryKeyJoinColumnParentAdapter() {
		return new PrimaryKeyJoinColumnParentAdapter();
	}

	protected JavaSpecifiedPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation annotation) {
		return this.getJpaFactory().buildJavaPrimaryKeyJoinColumn(this.primaryKeyJoinColumnParentAdapter, annotation);
	}
	

	// ********** primary key join column annotations **********

	protected ListIterable<PrimaryKeyJoinColumnAnnotation> getPrimaryKeyJoinColumnAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, PrimaryKeyJoinColumnAnnotation>(this.getNestablePrimaryKeyJoinColumnAnnotations());
	}

	protected ListIterable<NestableAnnotation> getNestablePrimaryKeyJoinColumnAnnotations() {
		return this.getResourceAttribute().getAnnotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
	}

	protected PrimaryKeyJoinColumnAnnotation addPrimaryKeyJoinColumnAnnotation(int index) {
		return (PrimaryKeyJoinColumnAnnotation) this.getResourceAttribute().addAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
	}

	protected void removePrimaryKeyJoinColumnAnnotation(int index) {
		this.getResourceAttribute().removeAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
	}

	protected void movePrimaryKeyJoinColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getResourceAttribute().moveAnnotation(targetIndex, sourceIndex, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
	}


	// ********** misc **********

	public JavaPrimaryKeyJoinColumnRelationship getRelationship() {
		return this.parent;
	}

	protected JavaRelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getRelationship().getMapping().getResourceAttribute();
	}

	public String getTableName() {
		return this.getTypeMapping().getPrimaryTableName();
	}

	public Table resolveDbTable(String tableName) {
		return this.getTypeMapping().resolveDbTable(tableName);
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getTypeMapping().tableNameIsInvalid(tableName);
	}

	public boolean isOverridable() {
		return false;
	}

	public String getColumnTableNotValidDescription() {
		return JptJpaCoreValidationArgumentMessages.NOT_VALID_FOR_THIS_ENTITY;
	}

	protected TypeMapping getTypeMapping() {
		return this.getRelationshipMapping().getTypeMapping();
	}

	public RelationshipStrategy selectOverrideStrategy(OverrideRelationship2_0 overrideRelationship) {
		return null;  // pk join column strategies cannot be overridden
	}

	public void addStrategy() {
		if (this.getPrimaryKeyJoinColumnsSize() == 0) {
			this.addPrimaryKeyJoinColumn();
		}
	}

	public void removeStrategy() {
		for (int i = this.getPrimaryKeyJoinColumnsSize(); i-- > 0; ) {
			this.removePrimaryKeyJoinColumn(i);
		}
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (JavaSpecifiedPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
			result = column.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (JavaSpecifiedPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
			pkJoinColumn.validate(messages, reporter);
		}
	}

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}


	// ********** join column parent adapter **********

	public class PrimaryKeyJoinColumnParentAdapter
		implements JoinColumn.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this;
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getTableName();
		}

		public Entity getRelationshipTarget() {
			return this.getRelationshipMapping().getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return this.getRelationshipMapping().getName();
		}

		protected SpecifiedPersistentAttribute getPersistentAttribute() {
			return this.getRelationshipMapping().getPersistentAttribute();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return this.getTypeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public Iterable<String> getCandidateTableNames() {
			return this.getTypeMapping().getAllAssociatedTableNames();
		}

		protected TypeMapping getTypeMapping() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getTypeMapping();
		}

		public Table resolveDbTable(String tableName) {
			return this.getTypeMapping().resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			Entity targetEntity = this.getRelationshipTarget();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}

		public String getDefaultColumnName(NamedColumn column) {
			return null;
		}

		public TextRange getValidationTextRange() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public int getJoinColumnsSize() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumnsSize();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new OneToOnePrimaryKeyJoinColumnValidator(this.getPersistentAttribute(), (BaseJoinColumn) column, this);
		}

		protected JavaRelationshipMapping getRelationshipMapping() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getRelationshipMapping();
		}
	}
}
