/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;

/**
 * Used for annotations that have no behavior, just a name
 */
public final class BinaryNamedAnnotation
	extends BinaryAnnotation
{

	private final String annotationName;

	public BinaryNamedAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation, String annotationName) {
		super(parent, jdtAnnotation);
		this.annotationName = annotationName;
	}

	public String getAnnotationName() {
		return this.annotationName;
	}
}
