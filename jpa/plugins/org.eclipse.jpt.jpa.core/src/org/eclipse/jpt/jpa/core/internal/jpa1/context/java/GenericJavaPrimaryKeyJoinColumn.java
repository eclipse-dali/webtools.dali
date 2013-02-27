/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Java primary key join column
 */
public class GenericJavaPrimaryKeyJoinColumn
	extends AbstractJavaNamedColumn<JpaContextModel, PrimaryKeyJoinColumnAnnotation, BaseJoinColumn.Owner>
	implements JavaSpecifiedPrimaryKeyJoinColumn
{
	/** @see AbstractJavaNamedColumn#AbstractJavaNamedColumn(JpaContextModel, org.eclipse.jpt.jpa.core.context.NamedColumn.Owner, org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation) */
	protected /* final */ PrimaryKeyJoinColumnAnnotation columnAnnotation;  // never null

	protected String specifiedReferencedColumnName;
	protected String defaultReferencedColumnName;


	public GenericJavaPrimaryKeyJoinColumn(JpaContextModel parent, BaseJoinColumn.Owner owner, PrimaryKeyJoinColumnAnnotation columnAnnotation) {
		super(parent, owner, columnAnnotation);
	}

	@Override
	protected void initialize(PrimaryKeyJoinColumnAnnotation annotation) {
		super.initialize(annotation);
		this.specifiedReferencedColumnName = this.buildSpecifiedReferencedColumnName(annotation);
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(PrimaryKeyJoinColumnAnnotation annotation) {
		super.synchronizeWithResourceModel(annotation);
		this.setSpecifiedReferencedColumnName_(this.buildSpecifiedReferencedColumnName(annotation));
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultReferencedColumnName(this.buildDefaultReferencedColumnName());
	}


	// ********** column annotation **********

	@Override
	public PrimaryKeyJoinColumnAnnotation getColumnAnnotation() {
		return this.columnAnnotation;
	}

	@Override
	protected void setColumnAnnotation(PrimaryKeyJoinColumnAnnotation columnAnnotation) {
		this.columnAnnotation = columnAnnotation;
	}

	@Override
	protected void removeColumnAnnotation() {
		// we don't remove a pk join column annotation when it is empty
	}


	// ********** referenced column name **********

	public String getReferencedColumnName() {
		return (this.specifiedReferencedColumnName != null) ? this.specifiedReferencedColumnName : this.defaultReferencedColumnName;
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	public void setSpecifiedReferencedColumnName(String name) {
		if (this.valuesAreDifferent(this.specifiedReferencedColumnName, name)) {
			this.columnAnnotation.setReferencedColumnName(name);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedReferencedColumnName_(name);
		}
	}

	protected void setSpecifiedReferencedColumnName_(String name) {
		String old = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = name;
		this.firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedReferencedColumnName(PrimaryKeyJoinColumnAnnotation annotation) {
		return annotation.getReferencedColumnName();
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String name) {
		String old = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = name;
		this.firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, old, name);
	}

	// TODO not correct when we start supporting
	// primary key join columns in 1-1 mappings
	protected String buildDefaultReferencedColumnName() {
		return this.buildDefaultName();
	}


	// ********** database stuff **********

	public Table getReferencedColumnDbTable() {
		return this.owner.getReferencedColumnDbTable();
	}

	protected Column getReferencedDbColumn() {
		Table table = this.getReferencedColumnDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getReferencedColumnName());
	}

	public boolean referencedColumnIsResolved() {
		return this.getReferencedDbColumn() != null;
	}


	// ********** misc **********

	@Override
	public String getTableName() {
		return this.owner.getDefaultTableName();
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
		return this.columnAnnotation.referencedColumnNameTouches(pos);
	}

	protected Iterable<String> getJavaCandidateReferencedColumnNames() {
		return new TransformationIterable<String, String>(this.getCandidateReferencedColumnNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateReferencedColumnNames() {
		Table table = this.owner.getReferencedColumnDbTable();
		return (table != null) ? table.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}


	// ********** validation **********

	public TextRange getReferencedColumnNameTextRange() {
		return this.getValidationTextRange(this.columnAnnotation.getReferencedColumnNameTextRange());
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append("=>"); //$NON-NLS-1$
		sb.append(this.getReferencedColumnName());
	}
}
