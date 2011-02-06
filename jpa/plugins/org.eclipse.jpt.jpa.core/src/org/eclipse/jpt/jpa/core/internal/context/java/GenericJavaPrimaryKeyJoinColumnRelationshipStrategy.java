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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterators.SubIteratorWrapper;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.OneToOnePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaPrimaryKeyJoinColumnRelationshipStrategy
	extends AbstractJavaJpaContextNode
	implements JavaPrimaryKeyJoinColumnRelationshipStrategy
{
	protected final Vector<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = new Vector<JavaPrimaryKeyJoinColumn>();
	protected final PrimaryKeyJoinColumnContainerAdapter primaryKeyJoinColumnContainerAdapter;
	protected final JavaJoinColumn.Owner primaryKeyJoinColumnOwner;


	public GenericJavaPrimaryKeyJoinColumnRelationshipStrategy(JavaPrimaryKeyJoinColumnRelationship parent) {
		super(parent);
		this.primaryKeyJoinColumnContainerAdapter = this.buildPrimaryKeyJoinColumnContainerAdapter();
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.initializePrimaryKeyJoinColumns();
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
		this.updateNodes(this.getPrimaryKeyJoinColumns());
	}


	// ********** primary key join columns **********

	public ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumns().iterator();
	}

	public ListIterable<JavaPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return new LiveCloneListIterable<JavaPrimaryKeyJoinColumn>(this.primaryKeyJoinColumns);
	}

	public int primaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumns.size();
	}

	public boolean hasPrimaryKeyJoinColumns() {
		return this.primaryKeyJoinColumns.size() != 0;
	}

	public JavaPrimaryKeyJoinColumn getPrimaryKeyJoinColumn(int index) {
		return this.primaryKeyJoinColumns.get(index);
	}

	public JavaPrimaryKeyJoinColumn addPrimaryKeyJoinColumn() {
		return this.addPrimaryKeyJoinColumn(this.primaryKeyJoinColumns.size());
	}

	public JavaPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		PrimaryKeyJoinColumnAnnotation annotation = this.addPrimaryKeyJoinColumnAnnotation(index);
		return this.addPrimaryKeyJoinColumn_(index, annotation);
	}

	public void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn joinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumns.indexOf(joinColumn));
	}

	public void removePrimaryKeyJoinColumn(int index) {
		this.removePrimaryKeyJoinColumnAnnotation(index);
		this.removePrimaryKeyJoinColumn_(index);
	}

	protected void removePrimaryKeyJoinColumn_(int index) {
		this.removeItemFromList(index, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.movePrimaryKeyJoinColumnAnnotation(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected void initializePrimaryKeyJoinColumns() {
		for (PrimaryKeyJoinColumnAnnotation annotation : this.getPrimaryKeyJoinColumnAnnotations()) {
			this.primaryKeyJoinColumns.add(this.buildPrimaryKeyJoinColumn(annotation));
		}
	}

	protected void syncPrimaryKeyJoinColumns() {
		ContextContainerTools.synchronizeWithResourceModel(this.primaryKeyJoinColumnContainerAdapter);
	}

	protected Iterable<PrimaryKeyJoinColumnAnnotation> getPrimaryKeyJoinColumnAnnotations() {
		return CollectionTools.iterable(this.primaryKeyJoinColumnAnnotations());
	}

	protected void movePrimaryKeyJoinColumn_(int index, JavaPrimaryKeyJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected JavaPrimaryKeyJoinColumn addPrimaryKeyJoinColumn_(int index, PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation) {
		JavaPrimaryKeyJoinColumn joinColumn = this.buildPrimaryKeyJoinColumn(pkJoinColumnAnnotation);
		this.addItemToList(index, joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
		return joinColumn;
	}

	protected void removePrimaryKeyJoinColumn_(JavaPrimaryKeyJoinColumn joinColumn) {
		this.removePrimaryKeyJoinColumn_(this.primaryKeyJoinColumns.indexOf(joinColumn));
	}

	protected PrimaryKeyJoinColumnContainerAdapter buildPrimaryKeyJoinColumnContainerAdapter() {
		return new PrimaryKeyJoinColumnContainerAdapter();
	}

	/**
	 * primary key join column container adapter
	 */
	protected class PrimaryKeyJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<JavaPrimaryKeyJoinColumn, PrimaryKeyJoinColumnAnnotation>
	{
		public Iterable<JavaPrimaryKeyJoinColumn> getContextElements() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumns();
		}
		public Iterable<PrimaryKeyJoinColumnAnnotation> getResourceElements() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getPrimaryKeyJoinColumnAnnotations();
		}
		public PrimaryKeyJoinColumnAnnotation getResourceElement(JavaPrimaryKeyJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
		public void moveContextElement(int index, JavaPrimaryKeyJoinColumn element) {
			GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.movePrimaryKeyJoinColumn_(index, element);
		}
		public void addContextElement(int index, PrimaryKeyJoinColumnAnnotation resourceElement) {
			GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.addPrimaryKeyJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(JavaPrimaryKeyJoinColumn element) {
			GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.removePrimaryKeyJoinColumn_(element);
		}
	}

	protected JavaJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected JavaPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation annotation) {
		return this.getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, annotation);
	}
	

	// ********** primary key join column annotations **********

	protected Iterator<PrimaryKeyJoinColumnAnnotation> primaryKeyJoinColumnAnnotations() {
		return new SubIteratorWrapper<NestableAnnotation, PrimaryKeyJoinColumnAnnotation>(this.primaryKeyJoinColumnAnnotations_());
	}

	protected Iterator<NestableAnnotation> primaryKeyJoinColumnAnnotations_() {
		return this.getResourcePersistentAttribute().annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
	}

	protected PrimaryKeyJoinColumnAnnotation addPrimaryKeyJoinColumnAnnotation(int index) {
		return (PrimaryKeyJoinColumnAnnotation) this.getResourcePersistentAttribute().addAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
	}

	protected void removePrimaryKeyJoinColumnAnnotation(int index) {
		this.getResourcePersistentAttribute().removeAnnotation(index, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
	}

	protected void movePrimaryKeyJoinColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getResourcePersistentAttribute().moveAnnotation(targetIndex, sourceIndex, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
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

	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.getRelationship().getMapping().getResourcePersistentAttribute();
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

	public void addStrategy() {
		if (this.primaryKeyJoinColumns.size() == 0) {
			this.addPrimaryKeyJoinColumn();
		}
	}

	public void removeStrategy() {
		for (int i = this.primaryKeyJoinColumns.size(); i-- > 0; ) {
			this.removePrimaryKeyJoinColumn(i);
		}
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
			result = column.javaCompletionProposals(pos, filter, astRoot);
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
		implements JavaJoinColumn.Owner
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

		public PersistentAttribute getPersistentAttribute() {
			return this.getRelationshipMapping().getPersistentAttribute();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return this.getTypeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public Iterator<String> candidateTableNames() {
			return this.getTypeMapping().allAssociatedTableNames();
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

		public int joinColumnsSize() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.primaryKeyJoinColumnsSize();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new OneToOnePrimaryKeyJoinColumnValidator((BaseJoinColumn) column, this, (BaseJoinColumnTextRangeResolver) textRangeResolver);
		}

		protected JavaRelationshipMapping getRelationshipMapping() {
			return GenericJavaPrimaryKeyJoinColumnRelationshipStrategy.this.getRelationshipMapping();
		}
	}
}
