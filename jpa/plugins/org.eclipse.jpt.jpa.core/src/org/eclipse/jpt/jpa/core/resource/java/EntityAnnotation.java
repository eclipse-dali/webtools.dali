/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA annotation
 * <code>javax.persistence.Entity</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface EntityAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = JPA.ENTITY;

	/**
	 * Corresponds to the 'name' element of the Entity annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'name' element of the Entity annotation.
	 * Set to null to remove the element.
	 */
	void setName(String name);

	/**
	 * Return the {@link TextRange} for the 'name' element. If the element 
	 * does not exist return the {@link TextRange} for the Entity annotation.
	 */
	TextRange getNameTextRange();
}
