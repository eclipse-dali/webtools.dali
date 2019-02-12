/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.eclipselink.core.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class JptCommonEclipseLinkCorePlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static volatile JptCommonEclipseLinkCorePlugin INSTANCE;

	/**
	 * Return the singleton Dali common EclipseLink core plug-in.
	 */
	public static JptCommonEclipseLinkCorePlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptCommonEclipseLinkCorePlugin() {
		super();
	}


	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptCommonEclipseLinkCorePlugin) plugin;
	}
}
