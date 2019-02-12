/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.platform;

import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;

/**
 * The <code>org.eclipse.jpt.jaxb.ui.jaxbPlatformUis</code> extension point
 * corresponding to a {@link JaxbWorkbench Dali JAXB workbench}.
 * <p>
 * See <code>org.eclipse.jpt.jaxb.ui/plugin.xml:jaxbPlatformUis</code>.
 * <p>
 * Not intended to be implemented by clients.
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
public interface JaxbPlatformUiManager {
	/**
	 * Return the manager's Dali JAXB workbench.
	 */
	JaxbWorkbench getJaxbWorkbench();


	// ********** JAXB platform UIs **********

	/**
	 * Return the UI for the specified JAXB platform.
	 */
	JaxbPlatformUi getJaxbPlatformUi(JaxbPlatform jaxbPlatform);
}
