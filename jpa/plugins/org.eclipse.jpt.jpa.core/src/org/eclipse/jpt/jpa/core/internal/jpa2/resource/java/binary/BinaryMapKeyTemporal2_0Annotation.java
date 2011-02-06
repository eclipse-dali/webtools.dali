/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryBaseTemporalAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyTemporal2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

/**
 * javax.persistence.MapKeyTemporal
 */
public final class BinaryMapKeyTemporal2_0Annotation
	extends BinaryBaseTemporalAnnotation
	implements MapKeyTemporal2_0Annotation
{
	
	public BinaryMapKeyTemporal2_0Annotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return MapKeyTemporal2_0Annotation.ANNOTATION_NAME;
	}
	
	@Override
	protected String getValueElementName() {
		return JPA2_0.MAP_KEY_TEMPORAL__VALUE;
	}
}
