/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkMapKeyConvertAnnotation2_0;

/**
 * <code>org.eclipse.persistence.annotations.MapKeyConvert</code>
 */
public final class BinaryEclipseLinkMapKeyConvertAnnotation2_0
	extends BinaryEclipseLinkBaseConvertAnnotation
	implements EclipseLinkMapKeyConvertAnnotation2_0
{

	public BinaryEclipseLinkMapKeyConvertAnnotation2_0(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return EclipseLinkMapKeyConvertAnnotation2_0.ANNOTATION_NAME;
	}

	@Override
	protected String getValueElementName() {
		return EclipseLink.MAP_KEY_CONVERT__VALUE;
	}
}
