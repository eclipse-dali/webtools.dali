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
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJoinColumnRelationshipStrategy
	extends AbstractJavaJpaContextNode
	implements JavaJoinColumnRelationshipStrategy
{
	protected final ContextListContainer<JavaJoinColumn, JoinColumnAnnotation> specifiedJoinColumnContainer;
	protected final JavaReadOnlyJoinColumn.Owner joinColumnOwner;

	protected JavaJoinColumn defaultJoinColumn;


	protected AbstractJavaJoinColumnRelationshipStrategy(JavaJoinColumnRelationship parent) {
		super(parent);
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.specifiedJoinColumnContainer = this.buildSpecifiedJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSpecifiedJoinColumns();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getSpecifiedJoinColumns());
		this.updateDefaultJoinColumn();
	}


	// ********** join columns **********

	public ListIterable<JavaJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int getJoinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterable<JavaJoinColumn> getSpecifiedJoinColumns() {
		return this.specifiedJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public JavaJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.getContextElement(index);
	}

	public JavaJoinColumn addSpecifiedJoinColumn() {
		return this.addSpecifiedJoinColumn(this.getSpecifiedJoinColumnsSize());
	}

	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		JoinColumnAnnotation annotation = this.addJoinColumnAnnotation(index);
		return this.specifiedJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumnContainer.indexOfContextElement((JavaJoinColumn) joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.removeJoinColumnAnnotation(index);
		this.specifiedJoinColumnContainer.removeContextElement(index);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.moveJoinColumnAnnotation(targetIndex, sourceIndex);
		this.specifiedJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected void updateSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.update();
	}

	protected ContextListContainer<JavaJoinColumn, JoinColumnAnnotation> buildSpecifiedJoinColumnContainer() {
		return new SpecifiedJoinColumnContainer();
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedJoinColumnContainer
		extends ContextListContainer<JavaJoinColumn, JoinColumnAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaJoinColumn buildContextElement(JoinColumnAnnotation resourceElement) {
			return AbstractJavaJoinColumnRelationshipStrategy.this.buildJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<JoinColumnAnnotation> getResourceElements() {
			return AbstractJavaJoinColumnRelationshipStrategy.this.getJoinColumnAnnotations();
		}
		@Override
		protected JoinColumnAnnotation getResourceElement(JavaJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
	}

	protected abstract JavaReadOnlyJoinColumn.Owner buildJoinColumnOwner();


	// ********** default join column **********

	public JavaJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(JavaJoinColumn joinColumn) {
		JavaJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<JavaJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<JavaJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<JavaJoinColumn>instance();
	}

	protected int getDefaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultJoinColumn() {
		if (this.buildsDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(this.buildNullJoinColumnAnnotation()));
			} else {
				this.defaultJoinColumn.update();
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}

	protected boolean buildsDefaultJoinColumn() {
		return ! this.hasSpecifiedJoinColumns() &&
				this.getRelationship().mayHaveDefaultJoinColumn();
	}


	// ********** join column annotations **********

	protected abstract ListIterable<JoinColumnAnnotation> getJoinColumnAnnotations();

	protected abstract JoinColumnAnnotation addJoinColumnAnnotation(int index);

	protected abstract void removeJoinColumnAnnotation(int index);

	protected abstract void moveJoinColumnAnnotation(int targetIndex, int sourceIndex);

	protected abstract JoinColumnAnnotation buildNullJoinColumnAnnotation();


	// ********** misc **********

	@Override
	public JavaJoinColumnRelationship getParent() {
		return (JavaJoinColumnRelationship) super.getParent();
	}

	public JavaJoinColumnRelationship getRelationship() {
		return this.getParent();
	}

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.getJpaFactory().buildJavaJoinColumn(this, this.joinColumnOwner, joinColumnAnnotation);
	}

	public void initializeFrom(ReadOnlyJoinColumnRelationshipStrategy oldStrategy) {
		for (ReadOnlyJoinColumn joinColumn : oldStrategy.getSpecifiedJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinColumnRelationshipStrategy virtualStrategy) {
		for (ReadOnlyJoinColumn joinColumn : virtualStrategy.getJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFromVirtual(joinColumn);
		}
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	public String getTableName() {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping == null) ? null : typeMapping.getPrimaryTableName();
	}

	public Table resolveDbTable(String tableName) {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping == null) ? null : typeMapping.resolveDbTable(tableName);
	}

	public boolean tableNameIsInvalid(String tableName) {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping != null) && typeMapping.tableNameIsInvalid(tableName);
	}

	// subclasses like this to be public
	public Table getReferencedColumnDbTable() {
		TypeMapping relationshipTarget = this.getRelationshipTarget();
		return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
	}

	protected Iterable<String> getCandidateTableNames() {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping != null) ? typeMapping.getAllAssociatedTableNames() : EmptyIterable.<String>instance();
	}

	public void addStrategy() {
		if (this.getSpecifiedJoinColumnsSize() == 0) {
			this.addSpecifiedJoinColumn();
		}
	}

	public void removeStrategy() {
		for (int i = this.getSpecifiedJoinColumnsSize(); i-- > 0; ) {
			this.removeSpecifiedJoinColumn(i);
		}
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaJoinColumn joinColumn : this.getJoinColumns()) {
			result = joinColumn.getJavaCompletionProposals(pos, filter, astRoot);
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
		for (JavaJoinColumn joinColumn : this.getJoinColumns()) {
			joinColumn.validate(messages, reporter, astRoot);
		}
	}
}
