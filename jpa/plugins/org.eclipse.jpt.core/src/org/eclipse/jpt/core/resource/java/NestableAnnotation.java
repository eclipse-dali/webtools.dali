/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;

/**
 * Interface for dealing with annotations that can be "nested" within arrays.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.0
 * @since 2.0
 */
public interface NestableAnnotation
	extends Annotation
{
	/**
	 * Move the annotation to the specified index within its container array.
	 * This should only be called when the annotation is actually nested
	 * (as opposed to stand-alone).
	 */
	void moveAnnotation(int index);

	/**
	 * Convert the annotation from "stand-alone" to "nested" within the "container"
	 * annotation adapted by the specified adapter at the specified index.
	 * The index may have a value of only <code>0</code> or <code>1</code>.
	 * <p>
	 * This is used to convert an annotation that is part of the "combination"
	 * pattern where a list containing elements of the annotation
	 * (e.g. {@link JoinColumnAnnotation}) can be represented by either the
	 * annotation itself (when there is only a single element in the collection)
	 * or a "container" annotation (e.g. {@link JoinColumnsAnnotation}) that
	 * holds only a single <code>value</code> element that is the array of
	 * "nested" annnotations.
	 * 
	 * @see #convertToStandAlone()
	 */
	void convertToNested(ContainerAnnotation<? extends NestableAnnotation> containerAnnotation, DeclarationAnnotationAdapter containerAnnotationAdapter, int index);

	/**
	 * Convert the annotation from "nested" within the "container" annotation
	 * to "stand-alone".
	 * 
	 * @see #convertToNested(ContainerAnnotation, DeclarationAnnotationAdapter, int)
	 */
	void convertToStandAlone();
}
