/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.jaxb.ui.internal.platform.JaxbPlatformUiManagerImpl;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUiManager;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class JptJaxbUiPlugin
	extends JptUIPlugin
{
	// ********** singleton **********

	private static JptJaxbUiPlugin INSTANCE;

	/**
	 * Return the singleton Dali JAXB UI plug-in.
	 */
	public static JptJaxbUiPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJaxbUiPlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJaxbUiPlugin) plugin;
	}


	// ********** misc **********

	public static JaxbPlatformUiManager getJaxbPlatformUiManager() {
		return JaxbPlatformUiManagerImpl.instance();
	}
}
