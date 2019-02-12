/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaBaseColumn;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.jpa.core.resource.java.CompleteJoinColumnAnnotation;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Java join column
 */
public class GenericJavaJoinColumn
	extends AbstractJavaBaseColumn<JoinColumn.ParentAdapter, CompleteJoinColumnAnnotation>
	implements JavaSpecifiedJoinColumn
{
	/** @see AbstractJavaNamedColumn#AbstractJavaNamedColumn(org.eclipse.jpt.jpa.core.context.NamedColumn.ParentAdapter, org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation) */
	protected /* final */ CompleteJoinColumnAnnotation columnAnnotation;  // never null

	protected String specifiedReferencedColumnName;
	protected String defaultReferencedColumnName;


	public GenericJavaJoinColumn(JoinColumn.ParentAdapter parentAdapter, CompleteJoinColumnAnnotation columnAnnotation) {
		super(parentAdapter, columnAnnotation);
	}

	@Override
	protected void initialize(CompleteJoinColumnAnnotation colAnnotation) {
		super.initialize(colAnnotation);
		this.specifiedReferencedColumnName = this.buildSpecifiedReferencedColumnName(colAnnotation);
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(CompleteJoinColumnAnnotation colAnnotation) {
		super.synchronizeWithResourceModel(colAnnotation);
		this.setSpecifiedReferencedColumnName_(this.buildSpecifiedReferencedColumnName(colAnnotation));
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultReferencedColumnName(this.buildDefaultReferencedColumnName());
	}


	// ********** column annotation **********

	@Override
	public CompleteJoinColumnAnnotation getColumnAnnotation() {
		return this.columnAnnotation;
	}

	@Override
	protected void setColumnAnnotation(CompleteJoinColumnAnnotation columnAnnotation) {
		this.columnAnnotation = columnAnnotation;
	}

	@Override
	protected void removeColumnAnnotation() {
		// we don't remove a join column annotation when it is empty
	}


	// ********** referenced column name **********

	public String getReferencedColumnName() {
		return (this.specifiedReferencedColumnName != null) ? this.specifiedReferencedColumnName : this.defaultReferencedColumnName;
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	public void setSpecifiedReferencedColumnName(String name) {
		if (ObjectTools.notEquals(this.specifiedReferencedColumnName, name)) {
			this.getColumnAnnotation().setReferencedColumnName(name);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedReferencedColumnName_(name);
		}
	}

	protected void setSpecifiedReferencedColumnName_(String name) {
		String old = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = name;
		this.firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedReferencedColumnName(CompleteJoinColumnAnnotation colAnnotation) {
		return colAnnotation.getReferencedColumnName();
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String name) {
		String old = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = name;
		this.firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultReferencedColumnName() {
		return MappingTools.buildJoinColumnDefaultReferencedColumnName(this.parentAdapter);
	}
	

	// ********** database stuff **********

	public Table getReferencedColumnDbTable() {
		return this.parentAdapter.getReferencedColumnDbTable();
	}

	protected Column getReferencedDbColumn() {
		Table table = this.getReferencedColumnDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getReferencedColumnName());
	}

	public boolean referencedColumnIsResolved() {
		return this.getReferencedDbColumn() != null;
	}


	// ********** misc **********

	public void initializeFrom(VirtualJoinColumn virtualColumn) {
		super.initializeFrom(virtualColumn);
		this.setSpecifiedReferencedColumnName(virtualColumn.getReferencedColumnName());
	}

	// ********** Java completion proposals **********

	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.referencedColumnNameTouches(pos)) {
			return this.getJavaCandidateReferencedColumnNames();
		}
		return null;
	}

	protected boolean referencedColumnNameTouches(int pos) {
		return this.getColumnAnnotation().referencedColumnNameTouches(pos);
	}

	protected Iterable<String> getJavaCandidateReferencedColumnNames() {
		return new TransformationIterable<String, String>(this.getCandidateReferencedColumnNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateReferencedColumnNames() {
		Table table = this.parentAdapter.getReferencedColumnDbTable();
		return (table != null) ? table.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}


	// ********** validation **********

	public TextRange getReferencedColumnNameTextRange() {
		return this.getValidationTextRange(this.getColumnAnnotation().getReferencedColumnNameTextRange());
	}
}
