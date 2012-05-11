/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface BaseEmbeddedMapping
	extends AttributeMapping
{
	AttributeOverrideContainer getAttributeOverrideContainer();
	
	/**
	 * Return the Embeddable that matches the type of this mapping.
	 * If none, return null.
	 */
	Embeddable getTargetEmbeddable();
	
	/**
	 * String associated with property change events for the target embeddable
	 */
	public final static String TARGET_EMBEDDABLE_PROPERTY = "targetEmbeddable";  //$NON-NLS-1$
}
