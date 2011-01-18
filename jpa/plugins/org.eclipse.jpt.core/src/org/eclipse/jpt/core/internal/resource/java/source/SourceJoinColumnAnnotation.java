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

import org.eclipse.jpt.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * <code>javax.persistence.JoinColumn</code>
 */
public final class SourceJoinColumnAnnotation
	extends SourceBaseJoinColumnAnnotation
	implements NestableJoinColumnAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);


	public SourceJoinColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
	}

	public SourceJoinColumnAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new ElementAnnotationAdapter(member, daa));
	}

	public SourceJoinColumnAnnotation(JavaResourceNode parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, member, idaa, new ElementIndexedAnnotationAdapter(member, idaa));
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// ********** SourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.JOIN_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.JOIN_COLUMN__COLUMN_DEFINITION;
	}


	// ********** SourceBaseColumnAnnotation implementation **********

	@Override
	protected String getTableElementName() {
		return JPA.JOIN_COLUMN__TABLE;
	}

	@Override
	protected String getUniqueElementName() {
		return JPA.JOIN_COLUMN__UNIQUE;
	}

	@Override
	protected String getNullableElementName() {
		return JPA.JOIN_COLUMN__NULLABLE;
	}

	@Override
	protected String getInsertableElementName() {
		return JPA.JOIN_COLUMN__INSERTABLE;
	}

	@Override
	protected String getUpdatableElementName() {
		return JPA.JOIN_COLUMN__UPDATABLE;
	}


	// ********** SourceBaseJoinColumnAnnotation implementation **********

	@Override
	protected String getReferencedColumnNameElementName() {
		return JPA.JOIN_COLUMN__REFERENCED_COLUMN_NAME;
	}


	// ********** static methods **********

	public static SourceJoinColumnAnnotation createJoinColumn(JavaResourceNode parent, Member member) {
		return new SourceJoinColumnAnnotation(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}

	static SourceJoinColumnAnnotation createNestedJoinColumn(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter joinColumnsAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, joinColumnsAdapter, ANNOTATION_NAME);
		IndexedAnnotationAdapter annotationAdapter = new ElementIndexedAnnotationAdapter(member, idaa);
		return new SourceJoinColumnAnnotation(parent, member, idaa, annotationAdapter);
	}

	static NestableJoinColumnAnnotation createAssociationOverrideJoinColumn(DeclarationAnnotationAdapter associationOverrideAdapter, JavaResourceNode parent, Member member, int index) {
		return new SourceJoinColumnAnnotation(parent, member, buildAssociationOverrideAnnotationAdapter(associationOverrideAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildAssociationOverrideAnnotationAdapter(DeclarationAnnotationAdapter associationOverrideAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(associationOverrideAdapter, JPA.ASSOCIATION_OVERRIDE__JOIN_COLUMNS, index, ANNOTATION_NAME);
	}
}
