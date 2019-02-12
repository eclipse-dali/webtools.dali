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

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Common protocol for<code><ul>
 * <li>javax.persistence.AttributeOverride
 * <li>javax.persistence.AssociationOverride
 * </ul></code>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface OverrideAnnotation
	extends NestableAnnotation
{
	/**
	 * Corresponds to the 'name' element of the override annotations.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'name' element of the override annotations.
	 * Set to null to remove the element.
	 */
	void setName(String name);

	/**
	 * Return the {@link TextRange} for the 'name' element. If element
	 * does not exist return the {@link TextRange} for the override annotation.
	 */
	TextRange getNameTextRange();

	/**
	 * Return whether the specified position touches the 'name' element.
	 * Return false if the element does not exist in Java.
	 */
	boolean nameTouches(int pos);
}
