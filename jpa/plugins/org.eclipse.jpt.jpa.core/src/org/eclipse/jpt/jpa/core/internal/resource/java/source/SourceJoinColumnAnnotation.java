/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;

import org.eclipse.jpt.common.core.internal.utility.jdt.JakartaAwareDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;

/**
 * <code>javax.persistence.JoinColumn</code>
 */
public final class SourceJoinColumnAnnotation
	extends SourceBaseJoinColumnAnnotation
	implements JoinColumnAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = JakartaAwareDeclarationAnnotationAdapter.forJavax(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = JakartaAwareDeclarationAnnotationAdapter.forJavax(JPA.JOIN_COLUMNS);
	private static final DeclarationAnnotationAdapter JAKARTA_DECLARATION_ANNOTATION_ADAPTER = JakartaAwareDeclarationAnnotationAdapter.forJakarta(JPA.JAKARTA_PACKAGE + JPA.JOIN_COLUMN.substring(JPA.JAVAX_PACKAGE.length()));
	private static final DeclarationAnnotationAdapter JAKARTA_CONTAINER_DECLARATION_ANNOTATION_ADAPTER = JakartaAwareDeclarationAnnotationAdapter.forJakarta(JPA.JAKARTA_PACKAGE + JPA.JOIN_COLUMNS.substring(JPA.JAVAX_PACKAGE.length()));


	public static SourceJoinColumnAnnotation buildSourceJoinColumnAnnotation(
			JavaResourceModel parent,
			AnnotatedElement element) {

		return new SourceJoinColumnAnnotation(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public static SourceJoinColumnAnnotation buildSourceJoinColumnAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement annotatedElement,
			int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildJoinColumnDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildJoinColumnAnnotationAdapter(annotatedElement, idaa);
		return new SourceJoinColumnAnnotation(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}

	public static SourceJoinColumnAnnotation buildJakartaSourceJoinColumnAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement annotatedElement,
			int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildJakartaJoinColumnDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildJoinColumnAnnotationAdapter(annotatedElement, idaa);
		return new SourceJoinColumnAnnotation(parent, annotatedElement, idaa, iaa);
	}

	
	public static SourceJoinColumnAnnotation buildNestedSourceJoinColumnAnnotation(
			JavaResourceModel parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceJoinColumnAnnotation(parent, element, idaa);
	}

	private SourceJoinColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	private SourceJoinColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	private SourceJoinColumnAnnotation(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
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

	private static IndexedAnnotationAdapter buildJoinColumnAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinColumnDeclarationAnnotationAdapter(int index) {
		return new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
	}

	private static IndexedDeclarationAnnotationAdapter buildJakartaJoinColumnDeclarationAnnotationAdapter(int index) {
		return new CombinationIndexedDeclarationAnnotationAdapter(
				JAKARTA_DECLARATION_ANNOTATION_ADAPTER,
				JAKARTA_CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				JPA.JAKARTA_PACKAGE + JPA.JOIN_COLUMN.substring(JPA.JAVAX_PACKAGE.length()));
	}
}
