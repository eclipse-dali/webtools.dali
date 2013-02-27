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

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.ModifiableJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaModifiableJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJoinColumnRelationshipStrategy<P extends JavaJoinColumnRelationship>
	extends AbstractJavaContextModel<P>
	implements JavaJoinColumnRelationshipStrategy
{
	protected final ContextListContainer<JavaModifiableJoinColumn, JoinColumnAnnotation> specifiedJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner joinColumnOwner;

	protected JavaModifiableJoinColumn defaultJoinColumn;


	protected AbstractJavaJoinColumnRelationshipStrategy(P parent) {
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
		this.updateModels(this.getSpecifiedJoinColumns());
		this.updateDefaultJoinColumn();
	}


	// ********** join columns **********

	public ListIterable<JavaModifiableJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int getJoinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterable<JavaModifiableJoinColumn> getSpecifiedJoinColumns() {
		return this.specifiedJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public JavaModifiableJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.getContextElement(index);
	}

	public JavaModifiableJoinColumn addSpecifiedJoinColumn() {
		return this.addSpecifiedJoinColumn(this.getSpecifiedJoinColumnsSize());
	}

	public JavaModifiableJoinColumn addSpecifiedJoinColumn(int index) {
		JoinColumnAnnotation annotation = this.addJoinColumnAnnotation(index);
		return this.specifiedJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedJoinColumn(ModifiableJoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumnContainer.indexOfContextElement((JavaModifiableJoinColumn) joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.removeJoinColumnAnnotation(index);
		this.specifiedJoinColumnContainer.removeContextElement(index);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.moveJoinColumnAnnotation(targetIndex, sourceIndex);
		this.specifiedJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	//default join column will get set in the update
	public void convertDefaultJoinColumnsToSpecified() {
		if (this.defaultJoinColumn == null) {
			throw new IllegalStateException("default join column is null"); //$NON-NLS-1$
		}
		// Add a join column by creating a specified one using the default one
		String columnName = this.defaultJoinColumn.getDefaultName();
		String referencedColumnName = this.defaultJoinColumn.getDefaultReferencedColumnName();

		ModifiableJoinColumn joinColumn = this.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName(columnName);
		joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
	}

	public void clearSpecifiedJoinColumns() {
		for (int index = getSpecifiedJoinColumnsSize(); --index >= 0; ) {
			this.removeJoinColumnAnnotation(index);
		}
		this.specifiedJoinColumnContainer.clearContextList();
	}

	protected void syncSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ContextListContainer<JavaModifiableJoinColumn, JoinColumnAnnotation> buildSpecifiedJoinColumnContainer() {
		SpecifiedJoinColumnContainer container = new SpecifiedJoinColumnContainer();
		container.initialize();
		return container;
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedJoinColumnContainer
		extends ContextListContainer<JavaModifiableJoinColumn, JoinColumnAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaModifiableJoinColumn buildContextElement(JoinColumnAnnotation resourceElement) {
			return AbstractJavaJoinColumnRelationshipStrategy.this.buildJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<JoinColumnAnnotation> getResourceElements() {
			return AbstractJavaJoinColumnRelationshipStrategy.this.getJoinColumnAnnotations();
		}
		@Override
		protected JoinColumnAnnotation getResourceElement(JavaModifiableJoinColumn contextElement) {
			return (JoinColumnAnnotation) contextElement.getColumnAnnotation();
		}
	}

	protected abstract ReadOnlyJoinColumn.Owner buildJoinColumnOwner();


	// ********** default join column **********

	public JavaModifiableJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(JavaModifiableJoinColumn joinColumn) {
		JavaModifiableJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<JavaModifiableJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<JavaModifiableJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<JavaModifiableJoinColumn>instance();
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

	public JavaJoinColumnRelationship getRelationship() {
		return this.parent;
	}

	protected JavaModifiableJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
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
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (JavaModifiableJoinColumn joinColumn : this.getJoinColumns()) {
			result = joinColumn.getCompletionProposals(pos);
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
		for (JavaModifiableJoinColumn joinColumn : this.getJoinColumns()) {
			joinColumn.validate(messages, reporter);
		}
	}

	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

}
