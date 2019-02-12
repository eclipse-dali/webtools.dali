/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;

public class JptDbwsEclipseLinkUiPlugin
	extends JptUIPlugin
{
	// ********** singleton **********

	private static volatile JptDbwsEclipseLinkUiPlugin INSTANCE;

	/**
	 * Return the singleton Dali DBWS EclipseLink UI plug-in.
	 */
	public static JptDbwsEclipseLinkUiPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptDbwsEclipseLinkUiPlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptDbwsEclipseLinkUiPlugin) plugin;
	}
}
