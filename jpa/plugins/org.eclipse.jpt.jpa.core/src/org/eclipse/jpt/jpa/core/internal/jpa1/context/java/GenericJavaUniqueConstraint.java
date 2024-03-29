/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.SpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

public class GenericJavaUniqueConstraint
	extends AbstractJavaUniqueConstraint<SpecifiedUniqueConstraint.Parent>
	implements JavaSpecifiedUniqueConstraint
{
	protected final UniqueConstraintAnnotation uniqueConstraintAnnotation;


	public GenericJavaUniqueConstraint(Parent parent, UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		super(parent);
		this.uniqueConstraintAnnotation = uniqueConstraintAnnotation;
		this.initializeColumnNames();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
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
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.columnNamesTouches(pos)) {
			return this.getJavaCandidateColumnNames();
		}
		return null;
	}

	protected boolean columnNamesTouches(int pos) {
		return this.uniqueConstraintAnnotation.columnNamesTouches(pos);
	}

	protected Iterable<String> getJavaCandidateColumnNames() {
		return new TransformationIterable<String, String>(this.getCandidateColumnNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateColumnNames() {
		return this.parent.getCandidateUniqueConstraintColumnNames();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.uniqueConstraintAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	public boolean isEquivalentTo(SpecifiedUniqueConstraint uniqueConstraint) {
		return  columnNamesAreEquivalent(uniqueConstraint);
	}

	protected boolean columnNamesAreEquivalent(SpecifiedUniqueConstraint uniqueConstraint) {
		if (this.getColumnNamesSize() != uniqueConstraint.getColumnNamesSize()) {
			return false;
		} 

		for (int i=0; i<this.getColumnNamesSize(); i++) {
			if (ObjectTools.notEquals(this.columnNames.get(i), uniqueConstraint.getColumnName(i))) {
				return false;
			}
		}
		return true;
	}

	// ********** misc **********

	public UniqueConstraintAnnotation getUniqueConstraintAnnotation() {
		return this.uniqueConstraintAnnotation;
	}

	public void initializeFrom(UniqueConstraint oldUniqueConstraint) {
		for (String columnName : oldUniqueConstraint.getColumnNames()) {
			this.addColumnName(columnName);
		}
	}
}
