/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui;

import java.util.ListIterator;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to
 * JPA UI functionality.  This is intended to work in conjunction with a core
 * JPA platform ({@link JpaPlatform}) implementation with the same ID.
 * <p>
 * Any implementation should be <i>stateless</i> in nature.
 * <p>
 * The "generic" extension supplies UI for the core platform extension with the same
 * ID.
 *
 * See the extension point: org.eclipse.jpt.ui.jpaPlatformUis
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaPlatformUiProvider
{
	/**
	 * Return the details providers that apply to this platform.
	 */
	ListIterator<JpaDetailsProvider> detailsProviders();
	
	/**
	 * Return the resource ui definitions that apply to this platform.
	 */
	ListIterator<ResourceUiDefinition> resourceUiDefinitions();
	
}
