/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.platform;

import org.eclipse.jpt.jaxb.ui.navigator.JaxbNavigatorUi;

/**
 * This interface is to be implemented by a JAXB implementation vendor to provide extensions 
 * to JAXB UI functionality.
 * <p>
 * Any implementation should be <i>stateless</i> in nature.
 * <p>
 * See the extension point: org.eclipse.jpt.jaxb.ui.jaxbPlatforms
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbPlatformUi {
	
	// ********** navigator UI **********
	
	/**
	 * Return the {@link JaxbNavigatorUi} for this platform, 
	 * which determines Project Explorer content
	 */
	JaxbNavigatorUi getNavigatorUi();
}
