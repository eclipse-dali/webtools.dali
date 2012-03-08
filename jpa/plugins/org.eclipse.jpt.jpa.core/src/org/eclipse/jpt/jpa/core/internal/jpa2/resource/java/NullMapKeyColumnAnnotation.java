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

import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullCompleteColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumn2_0Annotation;

/**
 * <code>javax.persistence.MapKeyColumn</code>
 */
public final class NullMapKeyColumnAnnotation
	extends NullCompleteColumnAnnotation<MapKeyColumn2_0Annotation>
	implements MapKeyColumn2_0Annotation
{
	public NullMapKeyColumnAnnotation(JavaResourceNode parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
}
