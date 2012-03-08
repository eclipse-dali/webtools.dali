/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullBaseTemporalAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyTemporal2_0Annotation;

/**
 * <code>javax.persistence.MapKeyTemporal</code>
 */
public final class NullMapKeyTemporal2_0Annotation
	extends NullBaseTemporalAnnotation<MapKeyTemporal2_0Annotation>
	implements MapKeyTemporal2_0Annotation
{
	protected NullMapKeyTemporal2_0Annotation(JavaResourceAnnotatedElement parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
}
