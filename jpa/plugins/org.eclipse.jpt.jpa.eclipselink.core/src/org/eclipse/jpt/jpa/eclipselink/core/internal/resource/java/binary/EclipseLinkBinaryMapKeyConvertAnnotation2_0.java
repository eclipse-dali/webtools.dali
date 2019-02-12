/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MapKeyConvertAnnotation2_0;

/**
 * <code>org.eclipse.persistence.annotations.MapKeyConvert</code>
 */
public final class EclipseLinkBinaryMapKeyConvertAnnotation2_0
	extends EclipseLinkBinaryBaseConvertAnnotation
	implements MapKeyConvertAnnotation2_0
{
	public EclipseLinkBinaryMapKeyConvertAnnotation2_0(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return MapKeyConvertAnnotation2_0.ANNOTATION_NAME;
	}

	@Override
	protected String getValueElementName() {
		return EclipseLink.MAP_KEY_CONVERT__VALUE;
	}
}
