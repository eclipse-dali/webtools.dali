/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;

/**
 * <code>javax.persistence.Column</code>
 */
public class NullColumnAnnotation
	extends NullCompleteColumnAnnotation<ColumnAnnotation>
	implements ColumnAnnotation
{
	public NullColumnAnnotation(JavaResourceModel parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
}
