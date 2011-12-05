/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;

/**
 * org.eclipse.persistence.annotations.TypeConverter
 */
public final class SourceEclipseLinkTypeConverterAnnotation
	extends SourceBaseEclipseLinkTypeConverterAnnotation
	implements EclipseLinkTypeConverterAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLink.TYPE_CONVERTERS);

	public static SourceEclipseLinkTypeConverterAnnotation buildSourceTypeConverterAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildTypeConverterDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildTypeConverterAnnotationAdapter(element, idaa);
		return new SourceEclipseLinkTypeConverterAnnotation(
			parent,
			element,
			idaa,
			iaa);
	}

	private SourceEclipseLinkTypeConverterAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement element,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** SourceNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLink.TYPE_CONVERTER__NAME;
	}


	// ********** SourceBaseTypeConverterAnnotation implementation **********

	@Override
	String getDataTypeElementName() {
		return EclipseLink.TYPE_CONVERTER__DATA_TYPE;
	}

	@Override
	String getObjectTypeElementName() {
		return EclipseLink.TYPE_CONVERTER__OBJECT_TYPE;
	}

	// ********** static methods **********

	private static IndexedAnnotationAdapter buildTypeConverterAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildTypeConverterDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
