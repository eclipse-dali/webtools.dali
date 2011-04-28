/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaPrimaryKeyJoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Java primary key join column
 */
public class GenericJavaPrimaryKeyJoinColumn
	extends AbstractJavaNamedColumn<PrimaryKeyJoinColumnAnnotation, JavaBaseJoinColumn.Owner>
	implements JavaPrimaryKeyJoinColumn
{
	/** @see AbstractJavaNamedColumn#AbstractJavaNamedColumn(org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode, org.eclipse.jpt.jpa.core.context.java.JavaNamedColumn.Owner, org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation) */
	protected /* final */ PrimaryKeyJoinColumnAnnotation columnAnnotation;  // never null

	protected String specifiedReferencedColumnName;
	protected String defaultReferencedColumnName;


	public GenericJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, JavaBaseJoinColumn.Owner owner, PrimaryKeyJoinColumnAnnotation columnAnnotation) {
		super(parent, owner, columnAnnotation);
		this.specifiedReferencedColumnName = this.buildSpecifiedReferencedColumnName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedReferencedColumnName_(this.buildSpecifiedReferencedColumnName());
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

	protected String buildSpecifiedReferencedColumnName() {
		return this.columnAnnotation.getReferencedColumnName();
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

	public boolean isDefault() {
		return this.owner.joinColumnIsDefault(this);
	}

	@Override
	public String getTable() {
		return this.owner.getDefaultTableName();
	}


	// ********** Java completion proposals **********

	@Override
	protected Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.referencedColumnNameTouches(pos, astRoot)) {
			return this.getJavaCandidateReferencedColumnNames(filter).iterator();
		}
		return null;
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.columnAnnotation.referencedColumnNameTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateReferencedColumnNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateReferencedColumnNames(filter));
	}

	protected Iterable<String> getCandidateReferencedColumnNames(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateReferencedColumnNames(), filter);
	}

	protected Iterable<String> getCandidateReferencedColumnNames() {
		Table table = this.owner.getReferencedColumnDbTable();
		return (table != null) ? table.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}


	// ********** validation **********

	@Override
	protected NamedColumnTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaPrimaryKeyJoinColumnTextRangeResolver(this, astRoot);
	}

	public TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.columnAnnotation.getReferencedColumnNameTextRange(astRoot), astRoot);
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append("=>"); //$NON-NLS-1$
		sb.append(this.getReferencedColumnName());
	}
}
