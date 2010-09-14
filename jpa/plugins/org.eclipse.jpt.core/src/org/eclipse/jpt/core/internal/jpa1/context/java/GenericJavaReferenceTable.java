/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaReferenceTable;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaTable;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.ReferenceTableAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java join table
 */
public abstract class GenericJavaReferenceTable
	extends AbstractJavaTable
	implements JavaReferenceTable
{

	protected JavaJoinColumn defaultJoinColumn;

	protected final Vector<JavaJoinColumn> specifiedJoinColumns = new Vector<JavaJoinColumn>();
	protected final JavaJoinColumn.Owner joinColumnOwner;


	protected GenericJavaReferenceTable(JavaJpaContextNode parent, Owner owner) {
		super(parent, owner);
		this.joinColumnOwner = this.buildJoinColumnOwner();
	}

	protected abstract JavaJoinColumn.Owner buildJoinColumnOwner();

	public void initialize(ReferenceTableAnnotation referenceTableAnnotation) {
		super.initialize(referenceTableAnnotation);
		this.initializeSpecifiedJoinColumns(referenceTableAnnotation);
		this.initializeDefaultJoinColumn(referenceTableAnnotation);
	}

	public void update(ReferenceTableAnnotation referenceTableAnnotation) {
		super.update(referenceTableAnnotation);
		this.updateSpecifiedJoinColumns(referenceTableAnnotation);
		this.updateDefaultJoinColumn(referenceTableAnnotation);
	}


	// ********** AbstractJavaTable implementation **********

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}

	@Override
	protected abstract ReferenceTableAnnotation getAnnotation();


	// ********** Table implementation **********

	public boolean isResourceSpecified() {
		return this.getAnnotation().isSpecified();
	}


	// ********** join columns **********

	public ListIterator<JavaJoinColumn> joinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumns() : this.defaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}

	public void convertDefaultToSpecifiedJoinColumn() {
		MappingTools.convertReferenceTableDefaultToSpecifiedJoinColumn(this);
	}

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.buildJoinColumn(joinColumnAnnotation, this.joinColumnOwner);
	}


	// ********** default join column **********

	public JavaJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(JavaJoinColumn defaultJoinColumn) {
		JavaJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = defaultJoinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, old, defaultJoinColumn);
	}

	protected ListIterator<JavaJoinColumn> defaultJoinColumns() {
		if (this.defaultJoinColumn != null) {
			return new SingleElementListIterator<JavaJoinColumn>(this.defaultJoinColumn);
		}
		return EmptyListIterator.instance();
	}

	protected int defaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void initializeDefaultJoinColumn(ReferenceTableAnnotation referenceTableAnnotation) {
		if (this.shouldBuildDefaultJoinColumn()) {
			this.defaultJoinColumn = this.buildJoinColumn(new NullJoinColumnAnnotation(referenceTableAnnotation));
		}
	}

	protected void updateDefaultJoinColumn(ReferenceTableAnnotation referenceTableAnnotation) {
		if (this.shouldBuildDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(new NullJoinColumnAnnotation(referenceTableAnnotation)));
			} else {
				this.defaultJoinColumn.update(new NullJoinColumnAnnotation(referenceTableAnnotation));
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}

	protected boolean shouldBuildDefaultJoinColumn() {
		return ! this.hasSpecifiedJoinColumns();
	}


	// ********** specified join columns **********

	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		// Clear out the default now so it doesn't get removed during an update and
		// cause change notifications to be sent to the UI in the wrong order.
		// If the default is already null, nothing will happen.
		JoinColumn oldDefault = this.defaultJoinColumn;
		this.defaultJoinColumn = null;

		JavaJoinColumn joinColumn = this.getJpaFactory().buildJavaJoinColumn(this, this.joinColumnOwner);
		this.specifiedJoinColumns.add(index, joinColumn);
		ReferenceTableAnnotation referenceTableAnnotation = this.getAnnotation();
		JoinColumnAnnotation joinColumnAnnotation = referenceTableAnnotation.addJoinColumn(index);
		joinColumn.initialize(joinColumnAnnotation);
		this.fireItemAdded(SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);

		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, oldDefault, null);
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, JavaJoinColumn joinColumn) {
		this.addItemToList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected void addSpecifiedJoinColumn(JavaJoinColumn joinColumn) {
		this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size(), joinColumn);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedJoinColumn(int index) {
		JavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		if ( ! this.hasSpecifiedJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = this.buildJoinColumn(new NullJoinColumnAnnotation(this.getAnnotation()));
		}
		this.getAnnotation().removeJoinColumn(index);
		this.fireItemRemoved(SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);
		}
	}

	protected void removeSpecifiedJoinColumn_(JavaJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.getAnnotation().moveJoinColumn(targetIndex, sourceIndex);
		this.fireItemMoved(SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public void clearSpecifiedJoinColumns() {
		// for now, we have to remove annotations one at a time...
		for (int i = this.specifiedJoinColumns.size(); i-- > 0; ) {
			this.removeSpecifiedJoinColumn(i);
		}
	}

	protected void initializeSpecifiedJoinColumns(ReferenceTableAnnotation referenceTableAnnotation) {
		for (ListIterator<JoinColumnAnnotation> stream = referenceTableAnnotation.joinColumns(); stream.hasNext(); ) {
			this.specifiedJoinColumns.add(this.buildJoinColumn(stream.next()));
		}
	}

	protected void updateSpecifiedJoinColumns(ReferenceTableAnnotation referenceTableAnnotation) {
		ListIterator<JavaJoinColumn> joinColumns = this.specifiedJoinColumns();
		ListIterator<JoinColumnAnnotation> joinColumnAnnotations = referenceTableAnnotation.joinColumns();

		while (joinColumns.hasNext()) {
			JavaJoinColumn joinColumn = joinColumns.next();
			if (joinColumnAnnotations.hasNext()) {
				joinColumn.update(joinColumnAnnotations.next());
			} else {
				this.removeSpecifiedJoinColumn_(joinColumn);
			}
		}

		while (joinColumnAnnotations.hasNext()) {
			this.addSpecifiedJoinColumn(this.buildJoinColumn(joinColumnAnnotations.next()));
		}
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


	// ********** misc **********

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation, JavaJoinColumn.Owner owner) {
		JavaJoinColumn joinColumn = this.getJpaFactory().buildJavaJoinColumn(this, owner);
		joinColumn.initialize(joinColumnAnnotation);
		return joinColumn;
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

