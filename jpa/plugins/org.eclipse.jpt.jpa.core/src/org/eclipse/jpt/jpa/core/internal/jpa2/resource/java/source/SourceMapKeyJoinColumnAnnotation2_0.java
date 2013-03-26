/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceBaseJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyJoinColumnAnnotation2_0;

/**
 * <code>javax.persistence.MapKeyJoinColumn</code>
 */
public final class SourceMapKeyJoinColumnAnnotation2_0
	extends SourceBaseJoinColumnAnnotation
	implements MapKeyJoinColumnAnnotation2_0
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA2_0.MAP_KEY_JOIN_COLUMNS);

	
	public static SourceMapKeyJoinColumnAnnotation2_0 buildSourceMapKeyJoinColumnAnnotation(
			JavaResourceModel parent, 
			AnnotatedElement element) {
		
		return new SourceMapKeyJoinColumnAnnotation2_0(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public static SourceMapKeyJoinColumnAnnotation2_0 buildSourceMapKeyJoinColumnAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement annotatedElement, 
			int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildMapKeyJoinColumnDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildMapKeyJoinColumnAnnotationAdapter(annotatedElement, idaa);
		return new SourceMapKeyJoinColumnAnnotation2_0(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}
	
	public static SourceMapKeyJoinColumnAnnotation2_0 buildNestedSourceMapKeyJoinColumnAnnotation(
			JavaResourceModel parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceMapKeyJoinColumnAnnotation2_0(parent, element, idaa);
	}

	private SourceMapKeyJoinColumnAnnotation2_0(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	private SourceMapKeyJoinColumnAnnotation2_0(JavaResourceModel parent, AnnotatedElement element, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	private SourceMapKeyJoinColumnAnnotation2_0(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// ********** SourceNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__COLUMN_DEFINITION;
	}


	// ********** SourceBaseColumnAnnotation implementation **********

	@Override
	protected String getTableElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__TABLE;
	}

	@Override
	protected String getUniqueElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__UNIQUE;
	}

	@Override
	protected String getNullableElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__NULLABLE;
	}

	@Override
	protected String getInsertableElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__INSERTABLE;
	}

	@Override
	protected String getUpdatableElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__UPDATABLE;
	}


	// ********** SourceBaseJoinColumnAnnotation implementation **********

	@Override
	protected String getReferencedColumnNameElementName() {
		return JPA2_0.MAP_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME;
	}

	// ********** static methods **********

	private static IndexedAnnotationAdapter buildMapKeyJoinColumnAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildMapKeyJoinColumnDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
