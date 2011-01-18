/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJoinColumnJoiningStrategy
	extends AbstractJavaJpaContextNode
	implements JavaJoinColumnJoiningStrategy
{
	protected final Vector<JavaJoinColumn> specifiedJoinColumns = new Vector<JavaJoinColumn>();
	protected final SpecifiedJoinColumnContainerAdapter specifiedJoinColumnContainerAdapter;
	protected final JavaJoinColumn.Owner joinColumnOwner;

	protected JavaJoinColumn defaultJoinColumn;


	protected AbstractJavaJoinColumnJoiningStrategy(JavaJoinColumnEnabledRelationshipReference parent) {
		super(parent);
		this.specifiedJoinColumnContainerAdapter = this.buildSpecifiedJoinColumnContainerAdapter();
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.initializeSpecifiedJoinColumns();
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

	public ListIterator<JavaJoinColumn> joinColumns() {
		return this.getJoinColumns().iterator();
	}

	protected ListIterable<JavaJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return this.getSpecifiedJoinColumns().iterator();
	}

	protected ListIterable<JavaJoinColumn> getSpecifiedJoinColumns() {
		return new LiveCloneListIterable<JavaJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	public JavaJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumns.get(index);
	}

	public JavaJoinColumn addSpecifiedJoinColumn() {
		return this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size());
	}

	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		JoinColumnAnnotation annotation = this.addJoinColumnAnnotation(index);
		return this.addSpecifiedJoinColumn_(index, annotation);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.removeJoinColumnAnnotation(index);
		this.removeSpecifiedJoinColumn_(index);
	}

	protected void removeSpecifiedJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.moveJoinColumnAnnotation(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected void initializeSpecifiedJoinColumns() {
		for (JoinColumnAnnotation joinColumnAnnotation : this.getJoinColumnAnnotations()) {
			this.specifiedJoinColumns.add(this.buildJoinColumn(joinColumnAnnotation));
		}
	}

	protected void syncSpecifiedJoinColumns() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedJoinColumnContainerAdapter);
	}

	protected Iterable<JoinColumnAnnotation> getJoinColumnAnnotations() {
		return CollectionTools.iterable(this.joinColumnAnnotations());
	}

	protected void moveSpecifiedJoinColumn_(int index, JavaJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected JavaJoinColumn addSpecifiedJoinColumn_(int index, JoinColumnAnnotation joinColumnAnnotation) {
		JavaJoinColumn joinColumn = this.buildJoinColumn(joinColumnAnnotation);
		this.addItemToList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
		return joinColumn;
	}

	protected void removeSpecifiedJoinColumn_(JavaJoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn_(this.specifiedJoinColumns.indexOf(joinColumn));
	}

	protected SpecifiedJoinColumnContainerAdapter buildSpecifiedJoinColumnContainerAdapter() {
		return new SpecifiedJoinColumnContainerAdapter();
	}

	/**
	 * specified join column container adapter
	 */
	protected class SpecifiedJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<JavaJoinColumn, JoinColumnAnnotation>
	{
		public Iterable<JavaJoinColumn> getContextElements() {
			return AbstractJavaJoinColumnJoiningStrategy.this.getSpecifiedJoinColumns();
		}
		public Iterable<JoinColumnAnnotation> getResourceElements() {
			return AbstractJavaJoinColumnJoiningStrategy.this.getJoinColumnAnnotations();
		}
		public JoinColumnAnnotation getResourceElement(JavaJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
		public void moveContextElement(int index, JavaJoinColumn element) {
			AbstractJavaJoinColumnJoiningStrategy.this.moveSpecifiedJoinColumn_(index, element);
		}
		public void addContextElement(int index, JoinColumnAnnotation resourceElement) {
			AbstractJavaJoinColumnJoiningStrategy.this.addSpecifiedJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(JavaJoinColumn element) {
			AbstractJavaJoinColumnJoiningStrategy.this.removeSpecifiedJoinColumn_(element);
		}
	}

	protected abstract JavaJoinColumn.Owner buildJoinColumnOwner();


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
				this.getRelationshipReference().mayHaveDefaultJoinColumn();
	}


	// ********** join column annotations **********

	protected abstract Iterator<JoinColumnAnnotation> joinColumnAnnotations();

	protected abstract JoinColumnAnnotation addJoinColumnAnnotation(int index);

	protected abstract void removeJoinColumnAnnotation(int index);

	protected abstract void moveJoinColumnAnnotation(int targetIndex, int sourceIndex);

	protected abstract JoinColumnAnnotation buildNullJoinColumnAnnotation();


	// ********** misc **********

	@Override
	public JavaJoinColumnEnabledRelationshipReference getParent() {
		return (JavaJoinColumnEnabledRelationshipReference) super.getParent();
	}

	public JavaJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.getJpaFactory().buildJavaJoinColumn(this, this.joinColumnOwner, joinColumnAnnotation);
	}

	public void initializeFrom(ReadOnlyJoinColumnJoiningStrategy oldStrategy) {
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(oldStrategy.specifiedJoinColumns())) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	public void initializeFromVirtual(ReadOnlyJoinColumnJoiningStrategy virtualStrategy) {
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(virtualStrategy.joinColumns())) {
			this.addSpecifiedJoinColumn().initializeFromVirtual(joinColumn);
		}
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getMapping();
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

	protected Iterator<String> candidateTableNames() {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping != null) ? typeMapping.allAssociatedTableNames() : EmptyIterator.<String>instance();
	}

	public void addStrategy() {
		if (this.specifiedJoinColumnsSize() == 0) {
			this.addSpecifiedJoinColumn();
		}
	}

	public void removeStrategy() {
		for (int i = this.specifiedJoinColumns.size(); i-- > 0; ) {
			this.removeSpecifiedJoinColumn(i);
		}
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaJoinColumn joinColumn : this.getJoinColumns()) {
			result = joinColumn.javaCompletionProposals(pos, filter, astRoot);
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
