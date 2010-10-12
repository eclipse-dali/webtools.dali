/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestablePrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.PrimaryKeyJoinColumn
 */
public final class SourcePrimaryKeyJoinColumnAnnotation
	extends SourceNamedColumnAnnotation
	implements NestablePrimaryKeyJoinColumnAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;
	private final AnnotationElementAdapter<String> referencedColumnNameAdapter;
	private String referencedColumnName;


	public SourcePrimaryKeyJoinColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.referencedColumnNameDeclarationAdapter = this.buildStringElementAdapter(JPA.PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		this.referencedColumnNameAdapter = this.buildShortCircuitElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}

	public SourcePrimaryKeyJoinColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new ElementAnnotationAdapter(member, daa));
	}

	public SourcePrimaryKeyJoinColumnAnnotation(JavaResourceNode parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, member, idaa, new ElementIndexedAnnotationAdapter(member, idaa));
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
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncReferencedColumnName(this.buildReferencedColumnName(astRoot));
	}


	// ********** JavaSourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION;
	}


	// ********** PrimaryKeyJoinColumn implementation **********

	// ***** referenced column name
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		if (this.attributeValueHasChanged(this.referencedColumnName, referencedColumnName)) {
			this.referencedColumnName = referencedColumnName;
			this.referencedColumnNameAdapter.setValue(referencedColumnName);
		}
	}

	private void syncReferencedColumnName(String astReferencedColumnName) {
		String old = this.referencedColumnName;
		this.referencedColumnName = astReferencedColumnName;
		this.firePropertyChanged(REFERENCED_COLUMN_NAME_PROPERTY, old, astReferencedColumnName);
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
		PrimaryKeyJoinColumnAnnotation oldJoinColumn = (PrimaryKeyJoinColumnAnnotation) oldAnnotation;
		this.setReferencedColumnName(oldJoinColumn.getReferencedColumnName());
	}

	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}

	protected IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}


	// ********** static methods **********

	public static SourcePrimaryKeyJoinColumnAnnotation createPrimaryKeyJoinColumn(JavaResourceNode parent, Member member) {
		return new SourcePrimaryKeyJoinColumnAnnotation(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}

	static SourcePrimaryKeyJoinColumnAnnotation createNestedPrimaryKeyJoinColumn(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter pkJoinColumnsAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, pkJoinColumnsAdapter);
		IndexedAnnotationAdapter annotationAdapter = new ElementIndexedAnnotationAdapter(member, idaa);
		return new SourcePrimaryKeyJoinColumnAnnotation(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter pkJoinColumnsAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(pkJoinColumnsAdapter, index, JPA.PRIMARY_KEY_JOIN_COLUMN);
	}

	static NestablePrimaryKeyJoinColumnAnnotation createSecondaryTablePrimaryKeyJoinColumn(DeclarationAnnotationAdapter secondaryTableAdapter, JavaResourceNode parent, Member member, int index) {
		return new SourcePrimaryKeyJoinColumnAnnotation(parent, member, buildSecondaryTableAnnotationAdapter(secondaryTableAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildSecondaryTableAnnotationAdapter(DeclarationAnnotationAdapter secondaryTableAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(secondaryTableAdapter, JPA.SECONDARY_TABLE__PK_JOIN_COLUMNS, index, JPA.PRIMARY_KEY_JOIN_COLUMN);
	}

}
