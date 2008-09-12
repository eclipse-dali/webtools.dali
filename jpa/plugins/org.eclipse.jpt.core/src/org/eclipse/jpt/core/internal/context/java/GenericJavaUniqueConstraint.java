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

	protected UniqueConstraintAnnotation resourceUniqueConstraint;
	
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
		this.resourceUniqueConstraint.addColumnName(index, columnName);
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
		this.resourceUniqueConstraint.removeColumnName(index);
		fireItemRemoved(UniqueConstraint.COLUMN_NAMES_LIST, index, removedColumnName);
	}
	
	protected void removeColumnName_(int index) {
		String removedColumnName = this.columnNames.remove(index);
		fireItemRemoved(UniqueConstraint.COLUMN_NAMES_LIST, index, removedColumnName);
	}

	public void moveColumnName(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.columnNames, targetIndex, sourceIndex);
		this.resourceUniqueConstraint.moveColumnName(targetIndex, sourceIndex);
		fireItemMoved(UniqueConstraint.COLUMN_NAMES_LIST, targetIndex, sourceIndex);		
	}

	public void initialize(UniqueConstraintAnnotation uca) {
		this.resourceUniqueConstraint = uca;
		this.initializeColumnNames(uca);
	}
	
	protected void initializeColumnNames(UniqueConstraintAnnotation uca) {
		ListIterator<String> annotationColumnNames = uca.columnNames();
		
		for (String resourceColumnName : CollectionTools.iterable(annotationColumnNames)) {
			this.columnNames.add(resourceColumnName);
		}
	}

	public void update(UniqueConstraintAnnotation uca) {
		this.resourceUniqueConstraint = uca;
		this.updateColumnNames(uca);
	}

	protected void updateColumnNames(UniqueConstraintAnnotation uca) {
		ListIterator<String> annotationColumnNames = uca.columnNames();
		
		int index = 0;
		for (String resourceColumnName : CollectionTools.iterable(annotationColumnNames)) {
			if (columnNamesSize() > index) {
				if (this.columnNames.get(index) != resourceColumnName) {
					addColumnName_(index, resourceColumnName);
				}
			}
			else {
				addColumnName_(index, resourceColumnName);			
			}
			index++;
		}
		
		for ( ; index < columnNamesSize(); ) {
			removeColumnName_(index);
		}
	}


	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceUniqueConstraint.getTextRange(astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.columnNames);
	}


	// ********** code-assist **********

	@Override
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.columnNamesTouches(pos, astRoot)) {
			return this.javaCandidateColumnNames(filter);
		}
		return null;
	}

	private boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
		return this.resourceUniqueConstraint.columnNamesTouches(pos, astRoot);
	}

	private Iterator<String> javaCandidateColumnNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateColumnNames(filter));
	}

	private Iterator<String> candidateColumnNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateColumnNames(), filter);
	}

	private Iterator<String> candidateColumnNames() {
		return this.getOwner().candidateUniqueConstraintColumnNames();
	}

}
