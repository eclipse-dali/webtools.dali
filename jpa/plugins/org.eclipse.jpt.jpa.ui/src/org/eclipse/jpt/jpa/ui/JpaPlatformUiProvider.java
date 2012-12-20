/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.jpt.jpa.core.JpaPlatform;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to
 * JPA UI functionality. This is intended to work in conjunction with a core
 * JPA platform ({@link JpaPlatform}) implementation with the same ID.
 * <p>
 * Any implementation should be <em>stateless</em> in nature.
 * <p>
 * The "generic" extension supplies UI for the core platform extension with the same
 * ID.
 * <p>
 * See the extension point: <code>org.eclipse.jpt.jpa.ui.jpaPlatformUis</code>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaPlatformUiProvider {

	/**
	 * Return the platform's resource UI definitions.
	 */
	Iterable<ResourceUiDefinition> getResourceUiDefinitions();
}
