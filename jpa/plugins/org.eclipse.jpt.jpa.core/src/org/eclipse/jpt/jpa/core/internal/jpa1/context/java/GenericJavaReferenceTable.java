/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyReferenceTable;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaReferenceTable;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaTable;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ReferenceTableAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java join table or collection table
 */
public abstract class GenericJavaReferenceTable<A extends ReferenceTableAnnotation>
	extends AbstractJavaTable<A>
	implements JavaReferenceTable
{
	protected final Vector<JavaJoinColumn> specifiedJoinColumns = new Vector<JavaJoinColumn>();
	protected final SpecifiedJoinColumnContainerAdapter specifiedJoinColumnContainerAdapter = new SpecifiedJoinColumnContainerAdapter();
	protected final JavaJoinColumn.Owner joinColumnOwner;

	protected JavaJoinColumn defaultJoinColumn;


	protected GenericJavaReferenceTable(JavaJpaContextNode parent, Owner owner) {
		super(parent, owner);
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

	public void convertDefaultToSpecifiedJoinColumn() {
		MappingTools.convertReferenceTableDefaultToSpecifiedJoinColumn(this);
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
		JoinColumnAnnotation annotation = this.getTableAnnotation().addJoinColumn(index);
		return this.addSpecifiedJoinColumn_(index, annotation);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		this.getTableAnnotation().removeJoinColumn(index);
		this.removeTableAnnotationIfUnset();
		this.removeSpecifiedJoinColumn_(index);
	}

	protected void removeSpecifiedJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.getTableAnnotation().moveJoinColumn(targetIndex, sourceIndex);
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
		return CollectionTools.iterable(this.getTableAnnotation().joinColumns());
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

	/**
	 * specified join column container adapter
	 */
	protected class SpecifiedJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<JavaJoinColumn, JoinColumnAnnotation>
	{
		public Iterable<JavaJoinColumn> getContextElements() {
			return GenericJavaReferenceTable.this.getSpecifiedJoinColumns();
		}
		public Iterable<JoinColumnAnnotation> getResourceElements() {
			return GenericJavaReferenceTable.this.getJoinColumnAnnotations();
		}
		public JoinColumnAnnotation getResourceElement(JavaJoinColumn contextElement) {
			return contextElement.getColumnAnnotation();
		}
		public void moveContextElement(int index, JavaJoinColumn element) {
			GenericJavaReferenceTable.this.moveSpecifiedJoinColumn_(index, element);
		}
		public void addContextElement(int index, JoinColumnAnnotation resourceElement) {
			GenericJavaReferenceTable.this.addSpecifiedJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(JavaJoinColumn element) {
			GenericJavaReferenceTable.this.removeSpecifiedJoinColumn_(element);
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
				this.setDefaultJoinColumn(this.buildJoinColumn(new NullJoinColumnAnnotation(this.getTableAnnotation())));
			} else {
				this.defaultJoinColumn.update();
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}

	protected boolean buildsDefaultJoinColumn() {
		return ! this.hasSpecifiedJoinColumns();
	}


	// ********** misc **********

	protected void initializeFrom(ReadOnlyReferenceTable oldTable) {
		super.initializeFrom(oldTable);
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(oldTable.specifiedJoinColumns())) {
			this.addSpecifiedJoinColumn().initializeFrom(joinColumn);
		}
	}

	protected void initializeFromVirtual(ReadOnlyReferenceTable virtualTable) {
		super.initializeFromVirtual(virtualTable);
		for (ReadOnlyJoinColumn joinColumn : CollectionTools.iterable(virtualTable.joinColumns())) {
			this.addSpecifiedJoinColumn().initializeFromVirtual(joinColumn);
		}
	}

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.buildJoinColumn(this.joinColumnOwner, joinColumnAnnotation);
	}

	protected JavaJoinColumn buildJoinColumn(JavaJoinColumn.Owner jcOwner, JoinColumnAnnotation joinColumnAnnotation) {
		return this.getJpaFactory().buildJavaJoinColumn(this, jcOwner, joinColumnAnnotation);
	}

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaJoinColumn column : CollectionTools.iterable(this.joinColumns())) {
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
		boolean continueValidating = this.buildTableValidator(astRoot).validate(messages, reporter);

		//join column validation will handle the check for whether to validate against the database
		//some validation messages are not database specific. If the database validation for the
		//table fails we will stop there and not validate the join columns at all
		if (continueValidating) {
			this.validateJoinColumns(messages, reporter, astRoot);
		}
	}

	protected void validateJoinColumns(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.validateJoinColumns(this.joinColumns(), messages, reporter, astRoot);		
	}

	protected void validateJoinColumns(Iterator<JavaJoinColumn> joinColumns, List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		while (joinColumns.hasNext()) {
			joinColumns.next().validate(messages, reporter, astRoot);
		}
	}
}

