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
import org.eclipse.jpt.core.context.JoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaJoinColumnJoiningStrategy 
	extends AbstractJavaJpaContextNode
	implements JavaJoinColumnJoiningStrategy
{
	protected JavaJoinColumn defaultJoinColumn;
	
	protected final Vector<JavaJoinColumn> specifiedJoinColumns = new Vector<JavaJoinColumn>();
	protected final JavaJoinColumn.Owner joinColumnOwner;
	
	
	protected AbstractJavaJoinColumnJoiningStrategy(JoinColumnEnabledRelationshipReference parent) {
		super(parent);
		this.joinColumnOwner = this.buildJoinColumnOwner();
	}
	
	protected abstract JavaJoinColumn.Owner buildJoinColumnOwner();
	
	public void initializeFrom(JoinColumnJoiningStrategy oldStrategy) {
		for (JoinColumn joinColumn : CollectionTools.iterable(oldStrategy.joinColumns())) {
			JoinColumn newJoinColumn = this.addSpecifiedJoinColumn(this.specifiedJoinColumnsSize());
			newJoinColumn.setSpecifiedName(joinColumn.getName());
			newJoinColumn.setSpecifiedReferencedColumnName(joinColumn.getReferencedColumnName());			
		}
	}

	@Override
	public JoinColumnEnabledRelationshipReference getParent() {
		return (JoinColumnEnabledRelationshipReference) super.getParent();
	}
	
	public JoinColumnEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	public void addStrategy() {
		if (specifiedJoinColumnsSize() == 0) {
			addSpecifiedJoinColumn(0);
		}
	}
	
	public void removeStrategy() {
		for (JoinColumn each : CollectionTools.iterable(specifiedJoinColumns())) {
			removeSpecifiedJoinColumn(each);
		}
	}
	
	
	// **************** join columns *******************************************
	
	public ListIterator<JavaJoinColumn> joinColumns() {
		return this.hasSpecifiedJoinColumns() ? 
			this.specifiedJoinColumns() : this.defaultJoinColumns();
	}
	
	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? 
			this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}
	
	
	// **************** default join column ************************************
	
	public JavaJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}
	
	protected void setDefaultJoinColumn(JavaJoinColumn column) {
		JavaJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = column;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, column);
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
	
	
	// **************** specified join columns *********************************
	
	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedJoinColumns);
	}
	
	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}
	
	public boolean hasSpecifiedJoinColumns() {
		return ! this.specifiedJoinColumns.isEmpty();
	}
	
	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		// Clear out the default now so it doesn't get removed during an update and
		// cause change notifications to be sent to the UI in the wrong order.
		JavaJoinColumn oldDefault = this.defaultJoinColumn;
		this.defaultJoinColumn = null;

		JavaJoinColumn joinColumn = this.getJpaFactory().buildJavaJoinColumn(this, this.joinColumnOwner);
		this.specifiedJoinColumns.add(index, joinColumn);
		JoinColumnAnnotation joinColumnAnnotation = this.addAnnotation(index);
		joinColumn.initialize(joinColumnAnnotation);
		this.fireItemAdded(SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);

		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, oldDefault, null);
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, JavaJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected void addSpecifiedJoinColumn(JavaJoinColumn joinColumn) {
		addSpecifiedJoinColumn(this.specifiedJoinColumns.size(), joinColumn);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		JavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		if (this.specifiedJoinColumns.isEmpty()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = buildJoinColumn(buildNullJoinColumnAnnotation());
		}
		removeAnnotation(index);
		fireItemRemoved(SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, null, this.defaultJoinColumn);		
		}
	}

	protected void removeSpecifiedJoinColumn_(JavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		moveAnnotation(targetIndex, sourceIndex);
		fireItemMoved(SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	protected abstract JoinColumnAnnotation addAnnotation(int index);
	
	protected abstract void removeAnnotation(int index);
	
	protected abstract void moveAnnotation(int targetIndex, int sourceIndex);
	
	protected abstract Iterator<JoinColumnAnnotation> joinColumnAnnotations();
	
	protected abstract JoinColumnAnnotation buildNullJoinColumnAnnotation();
	
	
	// **************** resource => context ************************************

	public void initialize() {
		initializeSpecifiedJoinColumns();
		initializeDefaultJoinColumn();
	}
	
	protected void initializeSpecifiedJoinColumns() {
		Iterator<JoinColumnAnnotation> annotations = joinColumnAnnotations();
		while (annotations.hasNext()) {
			this.specifiedJoinColumns.add(buildJoinColumn(annotations.next()));
		}
	}
	
	public void initializeDefaultJoinColumn() {
		if (mayHaveDefaultJoinColumn()) {
			this.defaultJoinColumn = 
				buildJoinColumn(buildNullJoinColumnAnnotation());
		}
	}
	
	public void update() {
		updateSpecifiedJoinColumns();
		updateDefaultJoinColumn();
	}
	
	protected void updateSpecifiedJoinColumns() {
		ListIterator<JavaJoinColumn> joinColumns = specifiedJoinColumns();
		Iterator<JoinColumnAnnotation> resourceJoinColumns = joinColumnAnnotations();
		
		while (joinColumns.hasNext()) {
			JavaJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn_(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(buildJoinColumn(resourceJoinColumns.next()));
		}
	}
	
	protected void updateDefaultJoinColumn() {
		if (mayHaveDefaultJoinColumn()) {
			JoinColumnAnnotation nullAnnotation = buildNullJoinColumnAnnotation();
			if (this.defaultJoinColumn == null) {
				setDefaultJoinColumn(this.buildJoinColumn(nullAnnotation));
			}
			this.defaultJoinColumn.update(nullAnnotation);
		}
		else {
			if (this.defaultJoinColumn != null) {
				setDefaultJoinColumn(null);
			}
		}
	}
	
	protected boolean mayHaveDefaultJoinColumn() {
		return getRelationshipReference().mayHaveDefaultJoinColumn()
			&& ! hasSpecifiedJoinColumns();
	}

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnResource) {
		JavaJoinColumn joinColumn = getJpaFactory().buildJavaJoinColumn(this, this.joinColumnOwner);
		joinColumn.initialize(joinColumnResource);
		return joinColumn;
	}
	
	
	// **************** Java completion proposals ******************************
	
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
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		for (Iterator<JavaJoinColumn> stream = this.joinColumns(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter, astRoot);
		}
	}
}
