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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJoinColumnRelationshipStrategy<P extends JavaJoinColumnRelationship>
	extends AbstractJavaContextModel<P>
	implements JavaSpecifiedJoinColumnRelationshipStrategy
{
	protected final ContextListContainer<JavaSpecifiedJoinColumn, JoinColumnAnnotation> specifiedJoinColumnContainer;
	protected final JoinColumn.ParentAdapter joinColumnParentAdapter;

	protected JavaSpecifiedJoinColumn defaultJoinColumn;


	protected AbstractJavaJoinColumnRelationshipStrategy(P parent) {
		super(parent);
		this.joinColumnParentAdapter = this.buildJoinColumnParentAdapter();
		this.specifiedJoinColumnContainer = this.buildSpecifiedJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncSpecifiedJoinColumns(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getSpecifiedJoinColumns(), monitor);
		this.updateDefaultJoinColumn(monitor);
	}


	// ********** join columns **********

	public ListIterable<JavaSpecifiedJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int getJoinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterable<JavaSpecifiedJoinColumn> getSpecifiedJoinColumns() {
		return this.specifiedJoinColumnContainer;
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public JavaSpecifiedJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.get(index);
	}

	public JavaSpecifiedJoinColumn addSpecifiedJoinColumn() {
		return this.addSpecifiedJoinColumn(this.getSpecifiedJoinColumnsSize());
	}

	public JavaSpecifiedJoinColumn addSpecifiedJoinColumn(int index) {
		JoinColumnAnnotation annotation = this.addJoinColumnAnnotation(index);
		return this.specifiedJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumnContainer.indexOf((JavaSpecifiedJoinColumn) joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.removeJoinColumnAnnotation(index);
		this.specifiedJoinColumnContainer.remove(index);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.moveJoinColumnAnnotation(targetIndex, sourceIndex);
		this.specifiedJoinColumnContainer.move(targetIndex, sourceIndex);
	}

	//default join column will get set in the update
	public void convertDefaultJoinColumnsToSpecified() {
		if (this.defaultJoinColumn == null) {
			throw new IllegalStateException("default join column is null"); //$NON-NLS-1$
		}
		// Add a join column by creating a specified one using the default one
		String columnName = this.defaultJoinColumn.getDefaultName();
		String referencedColumnName = this.defaultJoinColumn.getDefaultReferencedColumnName();

		SpecifiedJoinColumn joinColumn = this.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName(columnName);
		joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
	}

	public void clearSpecifiedJoinColumns() {
		for (int index = getSpecifiedJoinColumnsSize(); --index >= 0; ) {
			this.removeJoinColumnAnnotation(index);
		}
		this.specifiedJoinColumnContainer.clear();
	}

	protected void syncSpecifiedJoinColumns(IProgressMonitor monitor) {
		this.specifiedJoinColumnContainer.synchronizeWithResourceModel(monitor);
	}

	protected ContextListContainer<JavaSpecifiedJoinColumn, JoinColumnAnnotation> buildSpecifiedJoinColumnContainer() {
		return this.buildSpecifiedContextListContainer(SPECIFIED_JOIN_COLUMNS_LIST, new SpecifiedJoinColumnContainerAdapter());
	}

	/**
	 * specified join column container adapter
	 */
	public class SpecifiedJoinColumnContainerAdapter
		extends AbstractContainerAdapter<JavaSpecifiedJoinColumn, JoinColumnAnnotation>
	{
		public JavaSpecifiedJoinColumn buildContextElement(JoinColumnAnnotation resourceElement) {
			return AbstractJavaJoinColumnRelationshipStrategy.this.buildJoinColumn(resourceElement);
		}
		public ListIterable<JoinColumnAnnotation> getResourceElements() {
			return AbstractJavaJoinColumnRelationshipStrategy.this.getJoinColumnAnnotations();
		}
		public JoinColumnAnnotation extractResourceElement(JavaSpecifiedJoinColumn contextElement) {
			return (JoinColumnAnnotation) contextElement.getColumnAnnotation();
		}
	}

	protected abstract JoinColumn.ParentAdapter buildJoinColumnParentAdapter();


	// ********** default join column **********

	public JavaSpecifiedJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(JavaSpecifiedJoinColumn joinColumn) {
		JavaSpecifiedJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<JavaSpecifiedJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<JavaSpecifiedJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<JavaSpecifiedJoinColumn>instance();
	}

	protected int getDefaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultJoinColumn(IProgressMonitor monitor) {
		if (this.buildsDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(this.buildNullJoinColumnAnnotation()));
			} else {
				this.defaultJoinColumn.update(monitor);
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

	protected JavaSpecifiedJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.getJpaFactory().buildJavaJoinColumn(this.joinColumnParentAdapter, joinColumnAnnotation);
	}

	public void initializeFrom(VirtualJoinColumnRelationshipStrategy virtualStrategy) {
		for (VirtualJoinColumn joinColumn : virtualStrategy.getJoinColumns()) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
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
		for (JavaSpecifiedJoinColumn joinColumn : this.getJoinColumns()) {
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
		for (JavaSpecifiedJoinColumn joinColumn : this.getJoinColumns()) {
			joinColumn.validate(messages, reporter);
		}
	}

	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}

}
