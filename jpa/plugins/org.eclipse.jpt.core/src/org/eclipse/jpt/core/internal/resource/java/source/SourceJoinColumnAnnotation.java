/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.JoinColumn
 */
public final class SourceJoinColumnAnnotation
	extends SourceBaseColumnAnnotation
	implements NestableJoinColumnAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;
	private final AnnotationElementAdapter<String> referencedColumnNameAdapter;
	private String referencedColumnName;


	public SourceJoinColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.referencedColumnNameDeclarationAdapter = this.buildStringElementAdapter(JPA.JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		this.referencedColumnNameAdapter = this.buildShortCircuitElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}

	public SourceJoinColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}

	public SourceJoinColumnAnnotation(JavaResourceNode parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, member, idaa, new MemberIndexedAnnotationAdapter(member, idaa));
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.referencedColumnName = this.buildReferencedColumnName(astRoot);
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.setReferencedColumnName(this.buildReferencedColumnName(astRoot));
	}


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.JOIN_COLUMN__NAME;
	}

	@Override
	String getColumnDefinitionElementName() {
		return JPA.JOIN_COLUMN__COLUMN_DEFINITION;
	}


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	String getTableElementName() {
		return JPA.JOIN_COLUMN__TABLE;
	}

	@Override
	String getUniqueElementName() {
		return JPA.JOIN_COLUMN__UNIQUE;
	}

	@Override
	String getNullableElementName() {
		return JPA.JOIN_COLUMN__NULLABLE;
	}

	@Override
	String getInsertableElementName() {
		return JPA.JOIN_COLUMN__INSERTABLE;
	}

	@Override
	String getUpdatableElementName() {
		return JPA.JOIN_COLUMN__UPDATABLE;
	}


	//************ JoinColumn implementation ***************

	// referenced column name
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		if (this.attributeValueHasNotChanged(this.referencedColumnName, referencedColumnName)) {
			return;
		}
		String old = this.referencedColumnName;
		this.referencedColumnName = referencedColumnName;
		this.referencedColumnNameAdapter.setValue(referencedColumnName);
		this.firePropertyChanged(REFERENCED_COLUMN_NAME_PROPERTY, old, referencedColumnName);
	}

	private String buildReferencedColumnName(CompilationUnit astRoot) {
		return this.referencedColumnNameAdapter.getValue(astRoot);
	}

	public TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.referencedColumnNameDeclarationAdapter, astRoot);
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.referencedColumnNameDeclarationAdapter, pos, astRoot);
	}


	 // ********** NestableAnnotation implementation **********

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		JoinColumnAnnotation oldJoinColumn = (JoinColumnAnnotation) oldAnnotation;
		this.setReferencedColumnName(oldJoinColumn.getReferencedColumnName());
	}

	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}

	protected IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}


	// ********** static methods **********

	public static SourceJoinColumnAnnotation createJoinColumn(JavaResourceNode parent, Member member) {
		return new SourceJoinColumnAnnotation(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}

	static SourceJoinColumnAnnotation createNestedJoinColumn(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter joinColumnsAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, joinColumnsAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new SourceJoinColumnAnnotation(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter joinColumnsAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(joinColumnsAdapter, index, JPA.JOIN_COLUMN);
	}

	static NestableJoinColumnAnnotation createJoinTableJoinColumn(JavaResourceNode parent, Member member, int index) {
		return new SourceJoinColumnAnnotation(parent, member, buildJoinTableAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(SourceJoinTableAnnotation.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

	static NestableJoinColumnAnnotation createJoinTableInverseJoinColumn(JavaResourceNode parent, Member member, int index) {
		return new SourceJoinColumnAnnotation(parent, member, buildJoinTableInverseAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableInverseAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(SourceJoinTableAnnotation.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__INVERSE_JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}


	static NestableJoinColumnAnnotation createAssociationOverrideJoinColumn(DeclarationAnnotationAdapter associationOverrideAdapter, JavaResourceNode parent, Member member, int index) {
		return new SourceJoinColumnAnnotation(parent, member, buildAssociationOverrideAnnotationAdapter(associationOverrideAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildAssociationOverrideAnnotationAdapter(DeclarationAnnotationAdapter associationOverrideAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(associationOverrideAdapter, JPA.ASSOCIATION_OVERRIDE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

}
