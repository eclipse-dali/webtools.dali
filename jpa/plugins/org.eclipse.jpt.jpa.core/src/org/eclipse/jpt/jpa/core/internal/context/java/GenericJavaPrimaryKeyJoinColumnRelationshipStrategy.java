/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.OneToOnePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaPrimaryKeyJoinColumnRelationshipStrategy
	extends AbstractJavaContextModel<JavaPrimaryKeyJoinColumnRelationship>
	implements MappingRelationshipStrategy2_0, JavaPrimaryKeyJoinColumnRelationshipStrategy
{
	protected final ContextListContainer<JavaSpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> primaryKeyJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner primaryKeyJoinColumnOwner;


	public GenericJavaPrimaryKeyJoinColumnRelationshipStrategy(JavaPrimaryKeyJoinColumnRelationship parent) {
		super(parent);
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.primaryKeyJoinColumnContainer = this.buildPrimaryKeyJoinColumnContainer();
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
		this.updateModels(this.getPrimaryKeyJoinColumns());
	}


	// ********** primary key join columns **********

	public ListIterable<JavaSpecifiedPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.primaryKeyJoinColumnContainer.getContextElements();
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasPrimaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumnsSize() != 0;
	}

	public JavaSpecifiedPrimaryKeyJoinColumn getPrimaryKeyJoinColumn(int index) {
		return this.primaryKeyJoinColumnContainer.getContextElement(index);
	}

	public JavaSpecifiedPrimaryKeyJoinColumn addPrimaryKeyJoinColumn() {
		return this.addPrimaryKeyJoinColumn(this.getPrimaryKeyJoinColumnsSize());
	}

	public JavaSpecifiedPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		PrimaryKeyJoinColumnAnnotation annotation = this.addPrimaryKeyJoinColumnAnnotation(index);
		return this.primaryKeyJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removePrimaryKeyJoinColumn(SpecifiedPrimaryKeyJoinColumn joinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumnContainer.indexOfContextElement((JavaSpecifiedPrimaryKeyJoinColumn) joinColumn));
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

	protected ContextListContainer<JavaSpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation> buildPrimaryKeyJoinColumnContainer() {
		PrimaryKeyJoinColumnContainer container = new PrimaryKeyJoinColumnContainer();
		container.initialize();
		return container;
	}

	/**
	 *  primary key join column container
	 */
	protected class PrimaryKeyJoinColumnContainer
		extends ContextListContainer<JavaSpecifiedPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return PRIMARY_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaSpecifiedPrimaryKeyJoinColumn buildContextElement(PrimaryKeyJoinColumnAnnotation resourceElement) {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<PrimaryKeyJoinColumnAnnotation> getResourceElements() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumnAnnotations();
		}
		@Override
		protected PrimaryKeyJoinColumnAnnotation getResourceElement(JavaSpecifiedPrimaryKeyJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
	}

	protected ReadOnlyJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected JavaSpecifiedPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation annotation) {
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


	// ********** join column owner **********

	protected class PrimaryKeyJoinColumnOwner
		implements ReadOnlyJoinColumn.Owner
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

		protected ModifiablePersistentAttribute getPersistentAttribute() {
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

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return null;
		}

		public TextRange getValidationTextRange() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public int getJoinColumnsSize() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return new OneToOnePrimaryKeyJoinColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseJoinColumn) column, this);
		}

		protected JavaRelationshipMapping getRelationshipMapping() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getRelationshipMapping();
		}
	}
}
