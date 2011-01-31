/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaReadOnlyUniqueConstraint;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public class GenericJavaUniqueConstraint
	extends AbstractJavaReadOnlyUniqueConstraint
	implements JavaUniqueConstraint
{
	protected Owner owner;
	protected final UniqueConstraintAnnotation uniqueConstraintAnnotation;


	public GenericJavaUniqueConstraint(JavaJpaContextNode parent, Owner owner, UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		super(parent);
		this.owner = owner;
		this.uniqueConstraintAnnotation = uniqueConstraintAnnotation;
		this.initializeColumnNames();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncColumnNames();
	}


	// ********** column names **********

	public void addColumnName(String columnName) {
		this.addColumnName(this.columnNames.size(), columnName);
	}

	public void addColumnName(int index, String columnName) {
		this.uniqueConstraintAnnotation.addColumnName(index, columnName);
		this.addItemToList(index, columnName, this.columnNames, COLUMN_NAMES_LIST);
	}

	public void removeColumnName(String columnName) {
		this.removeColumnName(this.columnNames.indexOf(columnName));
	}

	public void removeColumnName(int index) {
		this.uniqueConstraintAnnotation.removeColumnName(index);
		this.removeItemFromList(index, this.columnNames, COLUMN_NAMES_LIST);
	}

	public void moveColumnName(int targetIndex, int sourceIndex) {
		this.uniqueConstraintAnnotation.moveColumnName(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.columnNames, COLUMN_NAMES_LIST);
	}

	protected void initializeColumnNames() {
		for (Iterator<String> stream = this.uniqueConstraintAnnotation.columnNames(); stream.hasNext(); ) {
			this.columnNames.add(stream.next());
		}
	}

	@Override
	protected Iterable<String> getResourceColumnNames() {
		return CollectionTools.iterable(this.uniqueConstraintAnnotation.columnNames());
	}

	// ********** Java completion proposals **********

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

	protected boolean columnNamesTouches(int pos, CompilationUnit astRoot) {
		return this.uniqueConstraintAnnotation.columnNamesTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateColumnNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateColumnNames(filter));
	}

	protected Iterator<String> candidateColumnNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateColumnNames(), filter);
	}

	protected Iterator<String> candidateColumnNames() {
		return this.owner.candidateUniqueConstraintColumnNames();
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.uniqueConstraintAnnotation.getTextRange(astRoot);
	}


	// ********** misc **********

	public UniqueConstraintAnnotation getUniqueConstraintAnnotation() {
		return this.uniqueConstraintAnnotation;
	}

	public void initializeFrom(ReadOnlyUniqueConstraint oldUniqueConstraint) {
		for (String columnName : oldUniqueConstraint.getColumnNames()) {
			this.addColumnName(columnName);
		}
	}
}
