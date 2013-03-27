/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ReadTransformerAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.ReadTransformer</code>
 */
public final class EclipseLinkSourceReadTransformerAnnotation
	extends EclipseLinkSourceTransformerAnnotation
	implements ReadTransformerAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);


	public EclipseLinkSourceReadTransformerAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** SourceTransformerAnnotation implementation **********

	@Override
	String getTransformerClassElementName() {
		return EclipseLink.READ_TRANSFORMER__TRANSFORMER_CLASS;
	}

	@Override
	String getMethodElementName() {
		return EclipseLink.READ_TRANSFORMER__METHOD;
	}
}
