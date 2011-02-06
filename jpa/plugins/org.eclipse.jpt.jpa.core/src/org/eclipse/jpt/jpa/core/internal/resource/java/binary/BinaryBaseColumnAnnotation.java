/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.BaseColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;

/**
 * javax.persistence.Column
 * javax.persistence.JoinColumn
 */
public abstract class BinaryBaseColumnAnnotation
	extends BinaryNamedColumnAnnotation
	implements BaseColumnAnnotation
{
	private String table;
	private Boolean unique;
	private Boolean nullable;
	private Boolean insertable;
	private Boolean updatable;
	

	protected BinaryBaseColumnAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.table = this.buildTable();
		this.unique = this.buildUnique();
		this.nullable = this.buildNullable();
		this.insertable = this.buildInsertable();
		this.updatable = this.buildUpdatable();
	}

	@Override
	public void update() {
		super.update();
		this.setTable_(this.buildTable());
		this.setUnique_(this.buildUnique());
		this.setNullable_(this.buildNullable());
		this.setInsertable_(this.buildInsertable());
		this.setUpdatable_(this.buildUpdatable());
	}


	//************* BaseColumnAnnotation implementation *************

	// ***** table
	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		throw new UnsupportedOperationException();
	}
	
	private void setTable_(String table) {
		String old = this.table;
		this.table = table;
		this.firePropertyChanged(TABLE_PROPERTY, old, table);
	}
	
	private String buildTable() {
		return (String) this.getJdtMemberValue(this.getTableElementName());
	}
	
	protected abstract String getTableElementName();

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** unique
	public Boolean getUnique() {
		return this.unique;
	}

	public void setUnique(Boolean unique) {
		throw new UnsupportedOperationException();
	}

	private void setUnique_(Boolean unique) {
		Boolean old = this.unique;
		this.unique = unique;
		this.firePropertyChanged(UNIQUE_PROPERTY, old, unique);
	}

	private Boolean buildUnique() {
		return (Boolean) this.getJdtMemberValue(this.getUniqueElementName());
	}
	
	protected abstract String getUniqueElementName();

	public TextRange getUniqueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	// ***** nullable
	public Boolean getNullable() {
		return this.nullable;
	}

	public void setNullable(Boolean nullable) {
		throw new UnsupportedOperationException();
	}

	private void setNullable_(Boolean nullable) {
		Boolean old = this.nullable;
		this.nullable = nullable;
		this.firePropertyChanged(NULLABLE_PROPERTY, old, nullable);
	}

	private Boolean buildNullable() {
		return (Boolean) this.getJdtMemberValue(this.getNullableElementName());
	}
	
	protected abstract String getNullableElementName();

	public TextRange getNullableTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	// ***** insertable
	public Boolean getInsertable() {
		return this.insertable;
	}

	public void setInsertable(Boolean insertable) {
		throw new UnsupportedOperationException();
	}

	private void setInsertable_(Boolean insertable) {
		Boolean old = this.insertable;
		this.insertable = insertable;
		this.firePropertyChanged(INSERTABLE_PROPERTY, old, insertable);
	}

	private Boolean buildInsertable() {
		return (Boolean) this.getJdtMemberValue(this.getInsertableElementName());
	}
	
	protected abstract String getInsertableElementName();

	public TextRange getInsertableTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	// ***** updatable
	public Boolean getUpdatable() {
		return this.updatable;
	}

	public void setUpdatable(Boolean updatable) {
		throw new UnsupportedOperationException();
	}

	private void setUpdatable_(Boolean updatable) {
		Boolean old = this.updatable;
		this.updatable = updatable;
		this.firePropertyChanged(UPDATABLE_PROPERTY, old, updatable);
	}

	private Boolean buildUpdatable() {
		return (Boolean) this.getJdtMemberValue(this.getUpdatableElementName());
	}

	protected abstract String getUpdatableElementName();

	public TextRange getUpdatableTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
}
