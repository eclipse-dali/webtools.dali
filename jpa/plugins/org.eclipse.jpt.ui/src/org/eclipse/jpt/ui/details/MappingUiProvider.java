/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
 * A UI provider is reponsible to provide the support for displaying the
 * information for a certain mapping type.
 *
 * @see AttributeMappingUiProvider
 * @see TypeMappingUiProvider
 *
 * @version 2.0
 * @since 2.0
 */
public interface MappingUiProvider<T> {

	/**
	 * Returns a human readable text of the mapping type.
	 *
	 * @return A display string for the mapping type
	 */
	String label();

	/**
	 * Returns an image that represents the mapping type defined by this provider.
	 *
	 * @return An image representing a mapping or <code>null</code> if no image
	 * is required
	 */
	Image image();

	/**
	 * Returns a unique string that corresponds to the key of the mapping in the
	 * core (JavaAttributeMappingProvider and/or OrmAttributeMappingProvider).
	 *
	 * @return The key representing the mapping
	 */
	String mappingKey();
}
