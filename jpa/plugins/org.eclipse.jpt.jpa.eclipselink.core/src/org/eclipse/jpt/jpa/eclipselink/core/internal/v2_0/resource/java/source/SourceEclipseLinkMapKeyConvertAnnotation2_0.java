/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.resource.java.source;

import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source.SourceBaseEclipseLinkConvertAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.resource.java.EclipseLink2_0;
import org.eclipse.jpt.jpa.eclipselink.core.v2_0.resource.java.EclipseLinkMapKeyConvertAnnotation2_0;

/**
 * org.eclipse.persistence.annotations.MapKeyConvert
 */
public final class SourceEclipseLinkMapKeyConvertAnnotation2_0
	extends SourceBaseEclipseLinkConvertAnnotation
	implements EclipseLinkMapKeyConvertAnnotation2_0
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(EclipseLinkMapKeyConvertAnnotation2_0.ANNOTATION_NAME);


	public SourceEclipseLinkMapKeyConvertAnnotation2_0(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return EclipseLinkMapKeyConvertAnnotation2_0.ANNOTATION_NAME;
	}

	@Override
	protected String getValueElementName() {
		return EclipseLink2_0.MAP_KEY_CONVERT__VALUE;
	}

}
