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
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class JoinColumnImpl extends AbstractColumnImpl implements NestableJoinColumn
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.JOIN_COLUMN);

	// hold this so we can get the 'referenced column name' text range
	private final DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;

	private final AnnotationElementAdapter<String> referencedColumnNameAdapter;

	private String referencedColumnName;
	
	public JoinColumnImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.referencedColumnNameDeclarationAdapter = this.buildStringElementAdapter(JPA.JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		this.referencedColumnNameAdapter = this.buildShortCircuitElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}
	
	public JoinColumnImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}
	
	public JoinColumnImpl(JavaResource parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, member, idaa, new MemberIndexedAnnotationAdapter(member, idaa));
	}
	
	@Override
	protected String nameElementName() {
		return JPA.JOIN_COLUMN__NAME;
	}
	
	@Override
	protected String columnDefinitionElementName() {
		return JPA.JOIN_COLUMN__COLUMN_DEFINITION;
	}
	
	@Override
	protected String tableElementName() {
		return JPA.JOIN_COLUMN__TABLE;
	}

	@Override
	protected String uniqueElementName() {
		return JPA.JOIN_COLUMN__UNIQUE;
	}

	@Override
	protected String nullableElementName() {
		return JPA.JOIN_COLUMN__NULLABLE;
	}

	@Override
	protected String insertableElementName() {
		return JPA.JOIN_COLUMN__INSERTABLE;
	}

	@Override
	protected String updatableElementName() {
		return JPA.JOIN_COLUMN__UPDATABLE;
	}

	public String getAnnotationName() {
		return JPA.JOIN_COLUMN;
	}
	
	private IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}

	public void moveAnnotation(int newIndex) {
		getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		// TODO add tests and support this
		
	}
	
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}
	
	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
		this.referencedColumnNameAdapter.setValue(referencedColumnName);
	}
	
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setReferencedColumnName(this.referencedColumnNameAdapter.getValue(astRoot));
	}
	// ********** static methods **********

	static JoinColumnImpl createJoinColumn(JavaResource parent, Member member) {
		return new JoinColumnImpl(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	static JoinColumnImpl createNestedJoinColumn(JavaResource parent, Member member, int index, DeclarationAnnotationAdapter joinColumnsAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, joinColumnsAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new JoinColumnImpl(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter joinColumnsAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(joinColumnsAdapter, index, JPA.JOIN_COLUMN);
	}
	static NestableJoinColumn createJoinTableJoinColumn(JavaResource parent, Member member, int index) {
		return new JoinColumnImpl(parent, member, buildJoinTableAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JoinTableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

	static NestableJoinColumn createJoinTableInverseJoinColumn(JavaResource parent,  Member member, int index) {
		return new JoinColumnImpl(parent, member, buildJoinTableInverseAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableInverseAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JoinTableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__INVERSE_JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}
}
