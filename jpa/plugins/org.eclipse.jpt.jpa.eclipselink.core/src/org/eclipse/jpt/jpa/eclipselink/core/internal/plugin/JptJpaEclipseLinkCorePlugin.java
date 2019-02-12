/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class JptJpaEclipseLinkCorePlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static volatile JptJpaEclipseLinkCorePlugin INSTANCE;

	/**
	 * Return the singleton Dali EclipseLink core plug-in.
	 */
	public static JptJpaEclipseLinkCorePlugin instance() {
		return INSTANCE;
	}	


	// ********** Dali plug-in **********

	public JptJpaEclipseLinkCorePlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaEclipseLinkCorePlugin) plugin;
	}
}
