/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;

public abstract class AbstractColumnImpl extends AbstractNamedColumn implements AbstractColumn
{
	// hold this so we can get the 'table' text range
	private final DeclarationAnnotationElementAdapter<String> tableDeclarationAdapter;
	
	// hold this so we can get the 'unique' text range
	private final DeclarationAnnotationElementAdapter<String> uniqueDeclarationAdapter;
	
	// hold this so we can get the 'nullable' text range
	private final DeclarationAnnotationElementAdapter<String> nullableDeclarationAdapter;
	
	// hold this so we can get the 'insertable' text range
	private final DeclarationAnnotationElementAdapter<String> insertableDeclarationAdapter;
	
	// hold this so we can get the 'updatable' text range
	private final DeclarationAnnotationElementAdapter<String> updatableDeclarationAdapter;

	private final AnnotationElementAdapter<String> tableAdapter;

	private final AnnotationElementAdapter<String> uniqueAdapter;

	private final AnnotationElementAdapter<String> nullableAdapter;

	private final AnnotationElementAdapter<String> insertableAdapter;

	private final AnnotationElementAdapter<String> updatableAdapter;

	private String table;
	private Boolean unique;
	private Boolean nullable;
	private Boolean insertable;
	private Boolean updatable;
	

	public AbstractColumnImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}
	
	public AbstractColumnImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.tableDeclarationAdapter = this.buildStringElementAdapter(this.tableElementName());
		this.tableAdapter = this.buildShortCircuitElementAdapter(this.tableDeclarationAdapter);
		this.uniqueDeclarationAdapter = this.buildBooleanElementAdapter(this.uniqueElementName());
		this.uniqueAdapter = this.buildShortCircuitElementAdapter(this.uniqueDeclarationAdapter);
		this.nullableDeclarationAdapter = this.buildBooleanElementAdapter(this.nullableElementName());
		this.nullableAdapter = this.buildShortCircuitElementAdapter(this.nullableDeclarationAdapter);
		this.insertableDeclarationAdapter = this.buildBooleanElementAdapter(this.insertableElementName());
		this.insertableAdapter = this.buildShortCircuitElementAdapter(this.insertableDeclarationAdapter);
		this.updatableDeclarationAdapter = this.buildBooleanElementAdapter(this.updatableElementName());
		this.updatableAdapter = this.buildShortCircuitElementAdapter(this.updatableDeclarationAdapter);
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
		AbstractColumn oldColumn = (AbstractColumn) oldAnnotation;
		setTable(oldColumn.getTable());
		setUnique(oldColumn.isUnique());
		setNullable(oldColumn.isNullable());
		setInsertable(oldColumn.isInsertable());
		setUpdatable(oldColumn.isUpdatable());
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
	
	public Boolean isUnique() {
		return this.unique;
	}

	public void setUnique(Boolean newUnique) {
		Boolean oldUnique = this.unique;
		this.unique = newUnique;
		this.uniqueAdapter.setValue(BooleanUtility.toJavaAnnotationValue(newUnique));
		firePropertyChanged(UNIQUE_PROPERTY, oldUnique, newUnique);
	}

	public Boolean isNullable() {
		return this.nullable;
	}

	public void setNullable(Boolean newNullable) {
		Boolean oldNullable = this.nullable;
		this.nullable = newNullable;
		this.nullableAdapter.setValue(BooleanUtility.toJavaAnnotationValue(newNullable));
		firePropertyChanged(NULLABLE_PROPERTY, oldNullable, newNullable);
	}

	public Boolean isInsertable() {
		return this.insertable;
	}

	public void setInsertable(Boolean newInsertable) {
		Boolean oldInsertable = this.insertable;
		this.insertable = newInsertable;
		this.insertableAdapter.setValue(BooleanUtility.toJavaAnnotationValue(newInsertable));
		firePropertyChanged(INSERTABLE_PROPERTY, oldInsertable, newInsertable);
	}

	public Boolean isUpdatable() {
		return this.updatable;
	}

	public void setUpdatable(Boolean newUpdatable) {
		Boolean oldUpdatable = this.updatable;
		this.updatable = newUpdatable;
		this.updatableAdapter.setValue(BooleanUtility.toJavaAnnotationValue(newUpdatable));
		firePropertyChanged(UPDATABLE_PROPERTY, oldUpdatable, newUpdatable);
	}

	public ITextRange nullableTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.nullableDeclarationAdapter, astRoot);
	}
	
	public ITextRange insertableTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.insertableDeclarationAdapter, astRoot);
	}
	
	public ITextRange uniqueTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.uniqueDeclarationAdapter, astRoot);
	}
	
	public ITextRange updatableTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.updatableDeclarationAdapter, astRoot);
	}
	
	public ITextRange tableTextRange(CompilationUnit astRoot) {
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
		return BooleanUtility.fromJavaAnnotationValue(this.uniqueAdapter.getValue(astRoot));
	}
	
	protected Boolean nullable(CompilationUnit astRoot) {
		return BooleanUtility.fromJavaAnnotationValue(this.nullableAdapter.getValue(astRoot));
	}
	
	protected Boolean insertable(CompilationUnit astRoot) {
		return BooleanUtility.fromJavaAnnotationValue(this.insertableAdapter.getValue(astRoot));
	}
	
	protected Boolean updatable(CompilationUnit astRoot) {
		return BooleanUtility.fromJavaAnnotationValue(this.updatableAdapter.getValue(astRoot));
	}

}
