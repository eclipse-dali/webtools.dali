/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.details;

import org.eclipse.swt.graphics.Image;

/**
 * A UI provider is responsible to provide the support for displaying the
 * information for a certain mapping type.
 * 
 * T represents the type of the mapping the definition represents
 * M represents the type of the object being mapped
 *
 * @version 2.3
 * @since 2.0
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingUiDefinition<M, T>
{
	/**
	 * Returns a unique string that corresponds to the key of the mapping in the
	 * core (JavaAttributeMappingDefinition and/or OrmAttributeMappingProvider).
	 *
	 * @return The key representing the mapping
	 */
	String getKey();
	
	/**
	 * Returns a human readable text of the mapping type.
	 *
	 * @return A display string for the mapping type
	 */
	String getLabel();

	/**
	 * Returns a human readable text of the mapping type to be used in the mapping change link label
	 */
	String getLinkLabel();
	
	/**
	 * Returns an image that represents the mapping type defined by this provider.
	 *
	 * @return An image representing a mapping or <code>null</code> if no image
	 * is required
	 */
	Image getImage();
	
	/**
	 * Return whether the mapping type represented by this definition is enabled for the given
	 * mappable object.  This is almost always true.
	 */
	// TODO bjv remove this; either
	// - delegate to whatever controls the list of UI definitions so it can be overridden by EclipseLink
	// or
	// - delegate to the model definitions (if we think there are more extends that need this...)
	boolean isEnabledFor(M mappableObject);
}
