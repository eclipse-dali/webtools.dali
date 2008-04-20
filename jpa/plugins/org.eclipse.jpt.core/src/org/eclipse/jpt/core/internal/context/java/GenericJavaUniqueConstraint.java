/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public class GenericJavaUniqueConstraint extends AbstractJavaJpaContextNode
	implements JavaUniqueConstraint
{

	protected final List<String> columnNames;

	protected UniqueConstraintAnnotation uniqueConstraintAnnotation;
	
	protected Owner owner;
	public GenericJavaUniqueConstraint(JavaJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
		this.columnNames = new ArrayList<String>();
	}

	public Owner getOwner() {
		return this.owner;
	}
	
	public ListIterator<String> columnNames() {
		return new CloneListIterator<String>(this.columnNames);
	}

	public int columnNamesSize() {
		return this.columnNames.size();
	}

	public void addColumnName(int index, String columnName) {
		this.columnNames.add(index, columnName);
		this.uniqueConstraintAnnotation.addColumnName(index, columnName);
		fireItemAdded(UniqueConstraint.COLUMN_NAMES_LIST, index, columnName);		
	}	
	
	protected void addColumnName_(int index, String columnName) {
		this.columnNames.add(index, columnName);
		fireItemAdded(UniqueConstraint.COLUMN_NAMES_LIST, index, columnName);		
	}	

	public void removeColumnName(String columnName) {
		this.removeColumnName(this.columnNames.indexOf(columnName));
	}

	public void removeColumnName(int index) {
		String removedColumnName = this.columnNames.remove(index);
		this.uniqueConstraintAnnotation.removeColumnName(index);
		fireItemRemoved(UniqueConstraint.COLUMN_NAMES_LIST, index, removedColumnName);
	}
	
	protected void removeColumnName_(int index) {
		String removedColumnName = this.columnNames.remove(index);
		fireItemRemoved(UniqueConstraint.COLUMN_NAMES_LIST, index, removedColumnName);
	}

	public void moveColumnName(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.columnNames, targetIndex, sourceIndex);
		this.uniqueConstraintAnnotation.moveColumnName(targetIndex, sourceIndex);
		fireItemMoved(UniqueConstraint.COLUMN_NAMES_LIST, targetIndex, sourceIndex);		
	}

	public void initialize(UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		this.uniqueConstraintAnnotation = uniqueConstraintAnnotation;
		this.initializeColumnNames(uniqueConstraintAnnotation);
	}
	
	protected void initializeColumnNames(UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		ListIterator<String> annotationColumnNames = uniqueConstraintAnnotation.columnNames();
		
		for (String annotationColumnName : CollectionTools.iterable(annotationColumnNames)) {
			this.columnNames.add(annotationColumnName);
		}
	}

	public void update(UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		this.uniqueConstraintAnnotation = uniqueConstraintAnnotation;
		this.updateColumnNames(uniqueConstraintAnnotation);
	}

	protected void updateColumnNames(UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		ListIterator<String> annotationColumnNames = uniqueConstraintAnnotation.columnNames();
		
		int index = 0;
		for (String annotationColumnName : CollectionTools.iterable(annotationColumnNames)) {
			if (columnNamesSize() > index) {
				if (this.columnNames.get(index) != annotationColumnName) {
					addColumnName_(index, annotationColumnName);
				}
			}
			else {
				addColumnName_(index, annotationColumnName);			
			}
			index++;
		}
		
		for ( ; index < columnNamesSize(); ) {
			removeColumnName_(index);
		}
	}


	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.uniqueConstraintAnnotation.getTextRange(astRoot);
	}	

	private boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
		return this.uniqueConstraintAnnotation.columnNamesTouches(pos, astRoot);
	}

	private Iterator<String> candidateColumnNames() {
		return this.getOwner().candidateUniqueConstraintColumnNames();
	}

	private Iterator<String> candidateColumnNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateColumnNames(), filter);
	}

	private Iterator<String> quotedCandidateColumnNames(Filter<String> filter) {
		return StringTools.quote(this.candidateColumnNames(filter));
	}

	@Override
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.columnNamesTouches(pos, astRoot)) {
			return this.quotedCandidateColumnNames(filter);
		}
		return null;
	}
}
