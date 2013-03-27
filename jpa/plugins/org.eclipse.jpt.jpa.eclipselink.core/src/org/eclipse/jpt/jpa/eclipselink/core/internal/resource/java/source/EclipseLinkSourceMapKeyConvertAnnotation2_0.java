/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MapKeyConvertAnnotation2_0;

/**
 * <code>org.eclipse.persistence.annotations.MapKeyConvert</code>
 */
public final class EclipseLinkSourceMapKeyConvertAnnotation2_0
	extends SourceEclipseLinkBaseConvertAnnotation
	implements MapKeyConvertAnnotation2_0
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(MapKeyConvertAnnotation2_0.ANNOTATION_NAME);


	public EclipseLinkSourceMapKeyConvertAnnotation2_0(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return MapKeyConvertAnnotation2_0.ANNOTATION_NAME;
	}

	@Override
	protected String getValueElementName() {
		return EclipseLink.MAP_KEY_CONVERT__VALUE;
	}
}
