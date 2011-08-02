/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubListIterableWrapper;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.OneToOnePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMappingPrimaryKeyJoinColumnRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaPrimaryKeyJoinColumnRelationshipStrategy
	extends AbstractJavaJpaContextNode
	implements JavaMappingPrimaryKeyJoinColumnRelationshipStrategy2_0
{
	protected final ContextListContainer<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> primaryKeyJoinColumnContainer;
	protected final JavaReadOnlyJoinColumn.Owner primaryKeyJoinColumnOwner;


	public GenericJavaPrimaryKeyJoinColumnRelationshipStrategy(JavaPrimaryKeyJoinColumnRelationship parent) {
		super(parent);
		this.primaryKeyJoinColumnContainer = this.buildPrimaryKeyJoinColumnContainer();
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncPrimaryKeyJoinColumns();
	}

	@Override
	public void update() {
		super.update();
		this.updatePrimaryKeyJoinColumns();
	}


	// ********** primary key join columns **********

	public ListIterable<JavaPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.primaryKeyJoinColumnContainer.getContextElements();
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasPrimaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumnsSize() != 0;
	}

	public JavaPrimaryKeyJoinColumn getPrimaryKeyJoinColumn(int index) {
		return this.primaryKeyJoinColumnContainer.getContextElement(index);
	}

	public JavaPrimaryKeyJoinColumn addPrimaryKeyJoinColumn() {
		return this.addPrimaryKeyJoinColumn(this.getPrimaryKeyJoinColumnsSize());
	}

	public JavaPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		PrimaryKeyJoinColumnAnnotation annotation = this.addPrimaryKeyJoinColumnAnnotation(index);
		return this.primaryKeyJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn joinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumnContainer.indexOfContextElement((JavaPrimaryKeyJoinColumn) joinColumn));
	}

	public void removePrimaryKeyJoinColumn(int index) {
		this.removePrimaryKeyJoinColumnAnnotation(index);
		this.primaryKeyJoinColumnContainer.removeContextElement(index);
	}

	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.movePrimaryKeyJoinColumnAnnotation(targetIndex, sourceIndex);
		this.primaryKeyJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected void syncPrimaryKeyJoinColumns() {
		this.primaryKeyJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected void updatePrimaryKeyJoinColumns() {
		this.primaryKeyJoinColumnContainer.update();
	}

	protected ContextListContainer<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> buildPrimaryKeyJoinColumnContainer() {
		return new PrimaryKeyJoinColumnContainer();
	}

	/**
	 *  primary key join column container
	 */
	protected class PrimaryKeyJoinColumnContainer
		extends ContextListContainer<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return PRIMARY_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaPrimaryKeyJoinColumn buildContextElement(PrimaryKeyJoinColumnAnnotation resourceElement) {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<PrimaryKeyJoinColumnAnnotation> getResourceElements() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumnAnnotations();
		}
		@Override
		protected PrimaryKeyJoinColumnAnnotation getResourceElement(JavaPrimaryKeyJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
	}

	protected JavaReadOnlyJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected JavaPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation annotation) {
		return this.getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, annotation);
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

	@Override
	public JavaPrimaryKeyJoinColumnRelationship getParent() {
		return (JavaPrimaryKeyJoinColumnRelationship) super.getParent();
	}

	public JavaPrimaryKeyJoinColumnRelationship getRelationship() {
		return this.getParent();
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
		return JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY;
	}

	protected TypeMapping getTypeMapping() {
		return this.getRelationshipMapping().getTypeMapping();
	}

	public ReadOnlyRelationshipStrategy selectOverrideStrategy(ReadOnlyOverrideRelationship2_0 overrideRelationship) {
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
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
			result = column.getJavaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		for (JavaPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
			pkJoinColumn.validate(messages, reporter, astRoot);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationship().getValidationTextRange(astRoot);
	}


	// ********** join column owner **********

	protected class PrimaryKeyJoinColumnOwner
		implements JavaReadOnlyJoinColumn.Owner
	{
		protected PrimaryKeyJoinColumnOwner() {
			super();
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

		protected PersistentAttribute getPersistentAttribute() {
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

		public TypeMapping getTypeMapping() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getTypeMapping();
		}

		public Table resolveDbTable(String tableName) {
			return this.getTypeMapping().resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			Entity targetEntity = this.getRelationshipTarget();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return false;
		}

		public String getDefaultColumnName() {
			return null;
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getValidationTextRange(astRoot);
		}

		public int getJoinColumnsSize() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new OneToOnePrimaryKeyJoinColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseJoinColumn) column, this, (BaseJoinColumnTextRangeResolver) textRangeResolver);
		}

		protected JavaRelationshipMapping getRelationshipMapping() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getRelationshipMapping();
		}
	}
}
