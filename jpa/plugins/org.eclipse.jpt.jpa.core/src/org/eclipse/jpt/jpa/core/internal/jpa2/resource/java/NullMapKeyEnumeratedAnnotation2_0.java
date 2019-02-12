/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullBaseEnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyEnumeratedAnnotation2_0;

/**
 * <code>javax.persistence.MapKeyEnumerated</code>
 */
public final class NullMapKeyEnumeratedAnnotation2_0
	extends NullBaseEnumeratedAnnotation<MapKeyEnumeratedAnnotation2_0>
	implements MapKeyEnumeratedAnnotation2_0
{
	protected NullMapKeyEnumeratedAnnotation2_0(JavaResourceAnnotatedElement parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
}
