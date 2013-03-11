/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullCompleteColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumnAnnotation2_0;

/**
 * <code>javax.persistence.MapKeyColumn</code>
 */
public final class NullMapKeyColumnAnnotation
	extends NullCompleteColumnAnnotation<MapKeyColumnAnnotation2_0>
	implements MapKeyColumnAnnotation2_0
{
	public NullMapKeyColumnAnnotation(JavaResourceModel parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
}
