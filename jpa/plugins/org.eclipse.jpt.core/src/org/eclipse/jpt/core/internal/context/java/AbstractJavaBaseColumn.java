/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.core.context.java.JavaBaseColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.resource.java.BaseColumnAnnotation;

/**
 * Java column or join column
 */
public abstract class AbstractJavaBaseColumn<A extends BaseColumnAnnotation, O extends JavaBaseColumn.Owner>
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
		this.specifiedTable = this.buildSpecifiedTable();
		this.specifiedUnique = this.buildSpecifiedUnique();
		this.specifiedNullable = this.buildSpecifiedNullable();
		this.specifiedInsertable = this.buildSpecifiedInsertable();
		this.specifiedUpdatable = this.buildSpecifiedUpdatable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTable_(this.buildSpecifiedTable());
		this.setSpecifiedUnique_(this.buildSpecifiedUnique());
		this.setSpecifiedNullable_(this.buildSpecifiedNullable());
		this.setSpecifiedInsertable_(this.buildSpecifiedInsertable());
		this.setSpecifiedUpdatable_(this.buildSpecifiedUpdatable());
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

	protected String buildSpecifiedTable() {
		return this.getColumnAnnotation().getTable();
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
		TextRange textRange = this.getColumnAnnotation().getTableTextRange(astRoot);
		return (textRange != null) ? textRange : this.owner.getValidationTextRange(astRoot);
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

	protected Boolean buildSpecifiedUnique() {
		return this.getColumnAnnotation().getUnique();
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

	protected Boolean buildSpecifiedNullable() {
		return this.getColumnAnnotation().getNullable();
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

	protected Boolean buildSpecifiedInsertable() {
		return this.getColumnAnnotation().getInsertable();
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

	protected Boolean buildSpecifiedUpdatable() {
		return this.getColumnAnnotation().getUpdatable();
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

	public boolean tableNameIsInvalid() {
		return this.owner.tableNameIsInvalid(this.getTable());
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos, astRoot)) {
			return this.javaCandidateTableNames(filter);
		}
		return null;
	}

	protected boolean tableTouches(int pos, CompilationUnit astRoot) {
		return this.getColumnAnnotation().tableTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateTableNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateTableNames(filter));
	}

	protected Iterator<String> candidateTableNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateTableNames(), filter);
	}

	public Iterator<String> candidateTableNames() {
		return this.owner.candidateTableNames();
	}

	@Override
	protected NamedColumnTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaBaseColumnTextRangeResolver(this, astRoot);
	}
}
