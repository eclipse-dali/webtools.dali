/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryBaseEnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyEnumeratedAnnotation2_0;

/**
 * <code>javax.persistence.MapKeyEnumerated</code>
 */
public final class BinaryMapKeyEnumerated2_0Annotation
	extends BinaryBaseEnumeratedAnnotation
	implements MapKeyEnumeratedAnnotation2_0
{
	public BinaryMapKeyEnumerated2_0Annotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return MapKeyEnumeratedAnnotation2_0.ANNOTATION_NAME;
	}
	
	@Override
	protected String getValueElementName() {
		return JPA2_0.MAP_KEY_ENUMERATED__VALUE;
	}
}
