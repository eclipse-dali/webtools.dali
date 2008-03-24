/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AbstractColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public abstract class AbstractColumnImpl extends AbstractNamedColumn implements AbstractColumnAnnotation
{
	// hold this so we can get the 'table' text range
	private final DeclarationAnnotationElementAdapter<String> tableDeclarationAdapter;
	
	// hold this so we can get the 'unique' text range
	private final DeclarationAnnotationElementAdapter<Boolean> uniqueDeclarationAdapter;
	
	// hold this so we can get the 'nullable' text range
	private final DeclarationAnnotationElementAdapter<Boolean> nullableDeclarationAdapter;
	
	// hold this so we can get the 'insertable' text range
	private final DeclarationAnnotationElementAdapter<Boolean> insertableDeclarationAdapter;
	
	// hold this so we can get the 'updatable' text range
	private final DeclarationAnnotationElementAdapter<Boolean> updatableDeclarationAdapter;

	private final AnnotationElementAdapter<String> tableAdapter;

	private final AnnotationElementAdapter<Boolean> uniqueAdapter;

	private final AnnotationElementAdapter<Boolean> nullableAdapter;

	private final AnnotationElementAdapter<Boolean> insertableAdapter;

	private final AnnotationElementAdapter<Boolean> updatableAdapter;

	private String table;
	private Boolean unique;
	private Boolean nullable;
	private Boolean insertable;
	private Boolean updatable;
	

	public AbstractColumnImpl(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}
	
	public AbstractColumnImpl(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.tableDeclarationAdapter = this.buildStringElementAdapter(this.tableElementName());
		this.tableAdapter = this.buildShortCircuitElementAdapter(this.tableDeclarationAdapter);
		this.uniqueDeclarationAdapter = this.buildBooleanElementAdapter(this.uniqueElementName());
		this.uniqueAdapter = this.buildShortCircuitBooleanElementAdapter(this.uniqueDeclarationAdapter);
		this.nullableDeclarationAdapter = this.buildBooleanElementAdapter(this.nullableElementName());
		this.nullableAdapter = this.buildShortCircuitBooleanElementAdapter(this.nullableDeclarationAdapter);
		this.insertableDeclarationAdapter = this.buildBooleanElementAdapter(this.insertableElementName());
		this.insertableAdapter = this.buildShortCircuitBooleanElementAdapter(this.insertableDeclarationAdapter);
		this.updatableDeclarationAdapter = this.buildBooleanElementAdapter(this.updatableElementName());
		this.updatableAdapter = this.buildShortCircuitBooleanElementAdapter(this.updatableDeclarationAdapter);
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.table = this.table(astRoot);
		this.unique = this.unique(astRoot);
		this.nullable = this.nullable(astRoot);
		this.insertable = this.insertable(astRoot);
		this.updatable = this.updatable(astRoot);
	}
	
	protected abstract String tableElementName();

	protected abstract String uniqueElementName();

	protected abstract String nullableElementName();

	protected abstract String insertableElementName();

	protected abstract String updatableElementName();

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		AbstractColumnAnnotation oldColumn = (AbstractColumnAnnotation) oldAnnotation;
		setTable(oldColumn.getTable());
		setUnique(oldColumn.getUnique());
		setNullable(oldColumn.getNullable());
		setInsertable(oldColumn.getInsertable());
		setUpdatable(oldColumn.getUpdatable());
	}

	//************* AbstractColumn implementation *************
	public String getTable() {
		return this.table;
	}

	public void setTable(String newTable) {
		String oldTable = this.table;
		this.table = newTable;
		this.tableAdapter.setValue(newTable);
		firePropertyChanged(TABLE_PROPERTY, oldTable, newTable);
	}
	
	public Boolean getUnique() {
		return this.unique;
	}

	public void setUnique(Boolean newUnique) {
		Boolean oldUnique = this.unique;
		this.unique = newUnique;
		this.uniqueAdapter.setValue(newUnique);
		firePropertyChanged(UNIQUE_PROPERTY, oldUnique, newUnique);
	}

	public Boolean getNullable() {
		return this.nullable;
	}

	public void setNullable(Boolean newNullable) {
		Boolean oldNullable = this.nullable;
		this.nullable = newNullable;
		this.nullableAdapter.setValue(newNullable);
		firePropertyChanged(NULLABLE_PROPERTY, oldNullable, newNullable);
	}

	public Boolean getInsertable() {
		return this.insertable;
	}

	public void setInsertable(Boolean newInsertable) {
		Boolean oldInsertable = this.insertable;
		this.insertable = newInsertable;
		this.insertableAdapter.setValue(newInsertable);
		firePropertyChanged(INSERTABLE_PROPERTY, oldInsertable, newInsertable);
	}

	public Boolean getUpdatable() {
		return this.updatable;
	}

	public void setUpdatable(Boolean newUpdatable) {
		Boolean oldUpdatable = this.updatable;
		this.updatable = newUpdatable;
		this.updatableAdapter.setValue(newUpdatable);
		firePropertyChanged(UPDATABLE_PROPERTY, oldUpdatable, newUpdatable);
	}

	public TextRange nullableTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.nullableDeclarationAdapter, astRoot);
	}
	
	public TextRange insertableTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.insertableDeclarationAdapter, astRoot);
	}
	
	public TextRange uniqueTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.uniqueDeclarationAdapter, astRoot);
	}
	
	public TextRange updatableTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.updatableDeclarationAdapter, astRoot);
	}
	
	public TextRange tableTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.tableDeclarationAdapter, astRoot);
	}
	
	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.tableDeclarationAdapter, pos, astRoot);
	}


	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setTable(this.table(astRoot));
		this.setUnique(this.unique(astRoot));
		this.setNullable(this.nullable(astRoot));
		this.setInsertable(this.insertable(astRoot));
		this.setUpdatable(this.updatable(astRoot));
	}
	
	protected String table(CompilationUnit astRoot) {
		return this.tableAdapter.getValue(astRoot);
	}
	
	protected Boolean unique(CompilationUnit astRoot) {
		return this.uniqueAdapter.getValue(astRoot);
	}
	
	protected Boolean nullable(CompilationUnit astRoot) {
		return this.nullableAdapter.getValue(astRoot);
	}
	
	protected Boolean insertable(CompilationUnit astRoot) {
		return this.insertableAdapter.getValue(astRoot);
	}
	
	protected Boolean updatable(CompilationUnit astRoot) {
		return this.updatableAdapter.getValue(astRoot);
	}

}
