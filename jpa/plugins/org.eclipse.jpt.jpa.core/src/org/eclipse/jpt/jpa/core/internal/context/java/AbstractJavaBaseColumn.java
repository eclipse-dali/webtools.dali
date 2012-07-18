/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.resource.java.BaseColumnAnnotation;

/**
 * Java column or join column
 */
public abstract class AbstractJavaBaseColumn<A extends BaseColumnAnnotation, O extends JavaReadOnlyBaseColumn.Owner>
	extends AbstractJavaNamedColumn<A, O>
	implements JavaBaseColumn
{
	protected String specifiedTable;
	protected String defaultTable;

	protected Boolean specifiedUnique;
	protected boolean defaultUnique;

	protected Boolean specifiedNullable;
	protected boolean defaultNullable;

	protected Boolean specifiedInsertable;
	protected boolean defaultInsertable;

	protected Boolean specifiedUpdatable;
	protected boolean defaultUpdatable;


	protected AbstractJavaBaseColumn(JavaJpaContextNode parent, O owner) {
		this(parent, owner, null);
	}

	protected AbstractJavaBaseColumn(JavaJpaContextNode parent, O owner, A columnAnnotation) {
		super(parent, owner, columnAnnotation);
		//build defaults during construction for performance
		this.defaultTable = this.buildDefaultTable();
		this.defaultUnique = this.buildDefaultUnique();
		this.defaultNullable = this.buildDefaultNullable();
		this.defaultInsertable = this.buildDefaultInsertable();
		this.defaultUpdatable = this.buildDefaultUpdatable();
	}

	@Override
	protected void initialize(A columnAnnotation) {
		super.initialize(columnAnnotation);
		this.specifiedTable = this.buildSpecifiedTable(columnAnnotation);
		this.specifiedUnique = this.buildSpecifiedUnique(columnAnnotation);
		this.specifiedNullable = this.buildSpecifiedNullable(columnAnnotation);
		this.specifiedInsertable = this.buildSpecifiedInsertable(columnAnnotation);
		this.specifiedUpdatable = this.buildSpecifiedUpdatable(columnAnnotation);
	}

	// ********** synchronize/update **********

	@Override
	protected void synchronizeWithResourceModel(A columnAnnotation) {
		super.synchronizeWithResourceModel(columnAnnotation);
		this.setSpecifiedTable_(this.buildSpecifiedTable(columnAnnotation));
		this.setSpecifiedUnique_(this.buildSpecifiedUnique(columnAnnotation));
		this.setSpecifiedNullable_(this.buildSpecifiedNullable(columnAnnotation));
		this.setSpecifiedInsertable_(this.buildSpecifiedInsertable(columnAnnotation));
		this.setSpecifiedUpdatable_(this.buildSpecifiedUpdatable(columnAnnotation));		
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultTable(this.buildDefaultTable());
		this.setDefaultUnique(this.buildDefaultUnique());
		this.setDefaultNullable(this.buildDefaultNullable());
		this.setDefaultInsertable(this.buildDefaultInsertable());
		this.setDefaultUpdatable(this.buildDefaultUpdatable());
	}


	// ********** table **********

	@Override
	public String getTable() {
		return (this.specifiedTable != null) ? this.specifiedTable : this.defaultTable;
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String table) {
		if (this.valuesAreDifferent(this.specifiedTable, table)) {
			this.getColumnAnnotation().setTable(table);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedTable_(table);
		}
	}

	protected void setSpecifiedTable_(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
	}

	protected String buildSpecifiedTable(A columnAnnotation) {
		return columnAnnotation.getTable();
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}

	protected void setDefaultTable(String table) {
		String old = this.defaultTable;
		this.defaultTable = table;
		this.firePropertyChanged(DEFAULT_TABLE_PROPERTY, old, table);
	}

	protected String buildDefaultTable() {
		return this.owner.getDefaultTableName();
	}

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getColumnAnnotation().getTableTextRange(), astRoot);
	}


	// ********** unique **********

	public boolean isUnique() {
		return (this.specifiedUnique != null) ? this.specifiedUnique.booleanValue() : this.isDefaultUnique();
	}

	public Boolean getSpecifiedUnique() {
		return this.specifiedUnique;
	}

	public void setSpecifiedUnique(Boolean unique) {
		if (this.valuesAreDifferent(this.specifiedUnique, unique)) {
			this.getColumnAnnotation().setUnique(unique);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedUnique_(unique);
		}
	}

	protected void setSpecifiedUnique_(Boolean unique) {
		Boolean old = this.specifiedUnique;
		this.specifiedUnique = unique;
		this.firePropertyChanged(SPECIFIED_UNIQUE_PROPERTY, old, unique);
	}

	protected Boolean buildSpecifiedUnique(A columnAnnotation) {
		return columnAnnotation.getUnique();
	}

	public boolean isDefaultUnique() {
		return this.defaultUnique;
	}

	protected void setDefaultUnique(boolean unique) {
		boolean old = this.defaultUnique;
		this.defaultUnique = unique;
		this.firePropertyChanged(DEFAULT_UNIQUE_PROPERTY, old, unique);
	}

	protected boolean buildDefaultUnique() {
		return DEFAULT_UNIQUE;
	}


	// ********** nullable **********

	public boolean isNullable() {
		return (this.specifiedNullable != null) ? this.specifiedNullable.booleanValue() : this.isDefaultNullable();
	}

	public Boolean getSpecifiedNullable() {
		return this.specifiedNullable;
	}

	public void setSpecifiedNullable(Boolean nullable) {
		if (this.valuesAreDifferent(this.specifiedNullable, nullable)) {
			this.getColumnAnnotation().setNullable(nullable);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedNullable_(nullable);
		}
	}

	protected void setSpecifiedNullable_(Boolean nullable) {
		Boolean old = this.specifiedNullable;
		this.specifiedNullable = nullable;
		this.firePropertyChanged(SPECIFIED_NULLABLE_PROPERTY, old, nullable);
	}

	protected Boolean buildSpecifiedNullable(A columnAnnotation) {
		return columnAnnotation.getNullable();
	}

	public boolean isDefaultNullable() {
		return this.defaultNullable;
	}

	protected void setDefaultNullable(boolean nullable) {
		boolean old = this.defaultNullable;
		this.defaultNullable = nullable;
		this.firePropertyChanged(DEFAULT_NULLABLE_PROPERTY, old, nullable);
	}

	protected boolean buildDefaultNullable() {
		return DEFAULT_NULLABLE;
	}


	// ********** insertable **********

	public boolean isInsertable() {
		return (this.specifiedInsertable != null) ? this.specifiedInsertable.booleanValue() : this.isDefaultInsertable();
	}

	public Boolean getSpecifiedInsertable() {
		return this.specifiedInsertable;
	}

	public void setSpecifiedInsertable(Boolean insertable) {
		if (this.valuesAreDifferent(this.specifiedInsertable, insertable)) {
			this.getColumnAnnotation().setInsertable(insertable);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedInsertable_(insertable);
		}
	}

	protected void setSpecifiedInsertable_(Boolean insertable) {
		Boolean old = this.specifiedInsertable;
		this.specifiedInsertable = insertable;
		this.firePropertyChanged(SPECIFIED_INSERTABLE_PROPERTY, old, insertable);
	}

	protected Boolean buildSpecifiedInsertable(A columnAnnotation) {
		return columnAnnotation.getInsertable();
	}

	public boolean isDefaultInsertable() {
		return this.defaultInsertable;
	}

	protected void setDefaultInsertable(boolean insertable) {
		boolean old = this.defaultInsertable;
		this.defaultInsertable = insertable;
		this.firePropertyChanged(DEFAULT_INSERTABLE_PROPERTY, old, insertable);
	}

	protected boolean buildDefaultInsertable() {
		return DEFAULT_INSERTABLE;
	}


	// ********** updatable **********

	public boolean isUpdatable() {
		return (this.specifiedUpdatable != null) ? this.specifiedUpdatable.booleanValue() : this.isDefaultUpdatable();
	}

	public Boolean getSpecifiedUpdatable() {
		return this.specifiedUpdatable;
	}

	public void setSpecifiedUpdatable(Boolean updatable) {
		if (this.valuesAreDifferent(this.specifiedUpdatable, updatable)) {
			this.getColumnAnnotation().setUpdatable(updatable);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedUpdatable_(updatable);
		}
	}

	protected void setSpecifiedUpdatable_(Boolean updatable) {
		Boolean old = this.specifiedUpdatable;
		this.specifiedUpdatable = updatable;
		this.firePropertyChanged(SPECIFIED_UPDATABLE_PROPERTY, old, updatable);
	}

	protected Boolean buildSpecifiedUpdatable(A columnAnnotation) {
		return columnAnnotation.getUpdatable();
	}

	public boolean isDefaultUpdatable() {
		return this.defaultUpdatable;
	}

	protected void setDefaultUpdatable(boolean updatable) {
		boolean old = this.defaultUpdatable;
		this.defaultUpdatable = updatable;
		this.firePropertyChanged(DEFAULT_UPDATABLE_PROPERTY, old, updatable);
	}

	protected boolean buildDefaultUpdatable() {
		return DEFAULT_UPDATABLE;
	}


	// ********** misc **********

	protected void initializeFrom(ReadOnlyBaseColumn oldColumn) {
		super.initializeFrom(oldColumn);
		this.setSpecifiedTable(oldColumn.getSpecifiedTable());
		this.setSpecifiedUnique(oldColumn.getSpecifiedUnique());
		this.setSpecifiedNullable(oldColumn.getSpecifiedNullable());
		this.setSpecifiedInsertable(oldColumn.getSpecifiedInsertable());
		this.setSpecifiedUpdatable(oldColumn.getSpecifiedUpdatable());
	}

	protected void initializeFromVirtual(ReadOnlyBaseColumn virtualColumn) {
		super.initializeFromVirtual(virtualColumn);
		this.setSpecifiedTable(virtualColumn.getTable());
		// ignore other settings?
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos)) {
			return this.getJavaCandidateTableNames(filter);
		}
		return null;
	}

	protected boolean tableTouches(int pos) {
		return this.getColumnAnnotation().tableTouches(pos);
	}

	protected Iterable<String> getJavaCandidateTableNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateTableNames(filter));
	}

	protected Iterable<String> getCandidateTableNames(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateTableNames(), filter);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.owner.getCandidateTableNames();
	}


	// ********** validation **********

	public boolean tableNameIsInvalid() {
		return this.owner.tableNameIsInvalid(this.getTable());
	}

	@Override
	protected NamedColumnTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaTableColumnTextRangeResolver(this, astRoot);
	}
}
