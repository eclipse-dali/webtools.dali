/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;

/**
 * Used for building new mapping Annotations. If you want to provide new 
 * MappingAnnotationDefinitions you will need to create a new JpaPlatform 
 * by extending BaseJpaPlatform.
 * <p>
 * MappingAnnotationDefinition also supplies a "schema" for JPA annotations.
 * correspondingAnnotationNames() defines the non-mapping annotations that
 * make sense in the context of this mapping annotation.
 * 
 * @see BaseJpaPlatform
 */
public interface MappingAnnotationDefinition extends AnnotationDefinition
{
	/**
	 * Return the fully qualified names of the annotations that can exist
	 * with the mapping annotation.  These will be used to create Annotations.
	 */
	Iterator<String> correspondingAnnotationNames();
}
