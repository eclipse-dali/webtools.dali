/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class JptJaxbEclipseLinkCorePlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static JptJaxbEclipseLinkCorePlugin INSTANCE;

	/**
	 * Return the singleton Dali JAXB Oxm core plug-in.
	 */
	public static JptJaxbEclipseLinkCorePlugin instance() {
		return INSTANCE;
	}	


	// ********** Dali plug-in **********

	public JptJaxbEclipseLinkCorePlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJaxbEclipseLinkCorePlugin) plugin;
	}
}
