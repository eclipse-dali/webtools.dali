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
		this.uniqueAdapter = this.buildShortCircuitBooleanElementAdapter(this.uniqueElementName());
		this.nullableAdapter = this.buildShortCircuitBooleanElementAdapter(this.nullableElementName());
		this.insertableAdapter = this.buildShortCircuitBooleanElementAdapter(this.insertableElementName());
		this.updatableAdapter = this.buildShortCircuitBooleanElementAdapter(this.updatableElementName());

	}
	protected abstract String tableElementName();

	protected abstract String uniqueElementName();

	protected abstract String nullableElementName();

	protected abstract String insertableElementName();

	protected abstract String updatableElementName();

	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		AbstractColumn oldColumn = (AbstractColumn) oldAnnotation;
		setTable(oldColumn.getTable());
		setUnique(oldColumn.isUnique());
		setNullable(oldColumn.isNullable());
		setInsertable(oldColumn.isInsertable());
		setUpdatable(oldColumn.isUpdatable());
	}

	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		this.table = table;
		this.tableAdapter.setValue(table);
	}
	
	public Boolean isUnique() {
		return this.unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
		this.uniqueAdapter.setValue(BooleanUtility.toJavaAnnotationValue(unique));
	}

	public Boolean isNullable() {
		return this.nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
		this.nullableAdapter.setValue(BooleanUtility.toJavaAnnotationValue(nullable));
	}

	public Boolean isInsertable() {
		return this.insertable;
	}

	public void setInsertable(Boolean insertable) {
		this.insertable = insertable;
		this.insertableAdapter.setValue(BooleanUtility.toJavaAnnotationValue(insertable));
	}

	public Boolean isUpdatable() {
		return this.updatable;
	}

	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
		this.updatableAdapter.setValue(BooleanUtility.toJavaAnnotationValue(updatable));
	}

	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setTable(this.tableAdapter.getValue(astRoot));
		this.setUnique(BooleanUtility.fromJavaAnnotationValue(this.uniqueAdapter.getValue(astRoot)));
		this.setNullable(BooleanUtility.fromJavaAnnotationValue(this.nullableAdapter.getValue(astRoot)));
		this.setInsertable(BooleanUtility.fromJavaAnnotationValue(this.insertableAdapter.getValue(astRoot)));
		this.setUpdatable(BooleanUtility.fromJavaAnnotationValue(this.updatableAdapter.getValue(astRoot)));
	}

}
