/*******************************************************************************
* Copyright (c) 2009, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.source;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceNamedQueryAnnotation;

/**
 * <code>javax.persistence.NamedQuery</code>
 */
public final class SourceNamedQueryAnnotation1_0
	extends SourceNamedQueryAnnotation
{
	public static SourceNamedQueryAnnotation1_0 buildSourceNamedQueryAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildNamedQueryDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildNamedQueryAnnotationAdapter(annotatedElement, idaa);
		return new SourceNamedQueryAnnotation1_0(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}

	private SourceNamedQueryAnnotation1_0(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement annotatedElement,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		super(parent, annotatedElement, daa, annotationAdapter);
	}
}
