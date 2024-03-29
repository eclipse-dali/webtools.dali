/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
public final class BinaryMapKeyEnumeratedAnnotation2_0
	extends BinaryBaseEnumeratedAnnotation
	implements MapKeyEnumeratedAnnotation2_0
{
	public BinaryMapKeyEnumeratedAnnotation2_0(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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
