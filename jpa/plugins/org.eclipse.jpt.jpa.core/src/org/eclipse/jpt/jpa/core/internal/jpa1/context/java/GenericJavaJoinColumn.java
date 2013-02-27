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
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaModifiableJoinColumn;
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
	extends AbstractJavaBaseColumn<CompleteJoinColumnAnnotation, ReadOnlyJoinColumn.Owner>
	implements JavaModifiableJoinColumn
{
	/** @see AbstractJavaNamedColumn#AbstractJavaNamedColumn(JpaContextModel, org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn.Owner, org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation) */
	protected /* final */ CompleteJoinColumnAnnotation columnAnnotation;  // never null

	protected String specifiedReferencedColumnName;
	protected String defaultReferencedColumnName;


	public GenericJavaJoinColumn(JpaContextModel parent, ReadOnlyJoinColumn.Owner owner, CompleteJoinColumnAnnotation columnAnnotation) {
		super(parent, owner, columnAnnotation);
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
	public void update() {
		super.update();
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
		if (this.valuesAreDifferent(this.specifiedReferencedColumnName, name)) {
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
		return MappingTools.buildJoinColumnDefaultReferencedColumnName(this.owner);
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

	public void initializeFrom(ReadOnlyJoinColumn oldColumn) {
		super.initializeFrom(oldColumn);
		this.setSpecifiedReferencedColumnName(oldColumn.getSpecifiedReferencedColumnName());
	}

	public void initializeFromVirtual(ReadOnlyJoinColumn virtualColumn) {
		super.initializeFromVirtual(virtualColumn);
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
		Table table = this.owner.getReferencedColumnDbTable();
		return (table != null) ? table.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}


	// ********** validation **********

	public TextRange getReferencedColumnNameTextRange() {
		return this.getValidationTextRange(this.getColumnAnnotation().getReferencedColumnNameTextRange());
	}
}
