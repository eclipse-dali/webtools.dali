/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceBaseJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.NestableMapKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;

/**
 * <code>javax.persistence.MapKeyJoinColumn</code>
 */
public final class SourceMapKeyJoinColumn2_0Annotation
	extends SourceBaseJoinColumnAnnotation
	implements NestableMapKeyJoinColumnAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);


	public SourceMapKeyJoinColumn2_0Annotation(JavaResourceNode parent, Attribute attribute, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, attribute, daa, annotationAdapter);
	}

	public SourceMapKeyJoinColumn2_0Annotation(JavaResourceNode parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		this(parent, attribute, daa, new ElementAnnotationAdapter(attribute, daa));
	}

	public SourceMapKeyJoinColumn2_0Annotation(JavaResourceNode parent, Attribute attribute, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, attribute, idaa, new ElementIndexedAnnotationAdapter(attribute, idaa));
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

	public static SourceMapKeyJoinColumn2_0Annotation createMapKeyJoinColumn(JavaResourceNode parent, Attribute attribute) {
		return new SourceMapKeyJoinColumn2_0Annotation(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
	}

	static SourceMapKeyJoinColumn2_0Annotation createNestedMapKeyJoinColumn(JavaResourceNode parent, Attribute attribute, int index, DeclarationAnnotationAdapter joinColumnsAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, joinColumnsAdapter, ANNOTATION_NAME);
		IndexedAnnotationAdapter annotationAdapter = new ElementIndexedAnnotationAdapter(attribute, idaa);
		return new SourceMapKeyJoinColumn2_0Annotation(parent, attribute, idaa, annotationAdapter);
	}
}
