/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
public final class NullMapKeyColumnAnnotation2_0
	extends NullCompleteColumnAnnotation<MapKeyColumnAnnotation2_0>
	implements MapKeyColumnAnnotation2_0
{
	public NullMapKeyColumnAnnotation2_0(JavaResourceModel parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
}
