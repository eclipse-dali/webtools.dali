/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

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
		for (String columnName : this.getResourceColumnNames()) {
			this.columnNames.add(columnName);
		}
	}

	@Override
	protected Iterable<String> getResourceColumnNames() {
		return this.uniqueConstraintAnnotation.getColumnNames();
	}

	// ********** Java completion proposals **********

	@Override
	protected Iterable<String> getConnectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getConnectedJavaCompletionProposals(pos, filter, astRoot);
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

	protected Iterable<String> javaCandidateColumnNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateColumnNames(filter));
	}

	protected Iterable<String> getCandidateColumnNames(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateColumnNames(), filter);
	}

	protected Iterable<String> getCandidateColumnNames() {
		return this.owner.getCandidateUniqueConstraintColumnNames();
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.uniqueConstraintAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
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
