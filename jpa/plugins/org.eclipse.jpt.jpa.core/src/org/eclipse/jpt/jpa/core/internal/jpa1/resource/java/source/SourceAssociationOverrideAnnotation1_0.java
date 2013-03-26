/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceAssociationOverrideAnnotation;

/**
 * <code>javax.persistence.AssociationOverride</code>
 */
public final class SourceAssociationOverrideAnnotation1_0
	extends SourceAssociationOverrideAnnotation
{
	
	public static SourceAssociationOverrideAnnotation1_0 buildSourceAssociationOverrideAnnotation(
			JavaResourceModel parent, 
			AnnotatedElement element) {

		return new SourceAssociationOverrideAnnotation1_0(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public static SourceAssociationOverrideAnnotation1_0 buildSourceAssociationOverrideAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement annotatedElement, 
			int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildAssociationOverrideDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildAssociationOverrideAnnotationAdapter(annotatedElement, idaa);
		return new SourceAssociationOverrideAnnotation1_0(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}

	public static SourceAssociationOverrideAnnotation1_0 buildNestedSourceAssociationOverrideAnnotation(
			JavaResourceModel parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceAssociationOverrideAnnotation1_0(parent, element, idaa);
	}

	private SourceAssociationOverrideAnnotation1_0(
			JavaResourceModel parent, 
			AnnotatedElement element, 
			DeclarationAnnotationAdapter daa) {

		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	private SourceAssociationOverrideAnnotation1_0(
			JavaResourceModel parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {

		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	private SourceAssociationOverrideAnnotation1_0(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
	}
}
