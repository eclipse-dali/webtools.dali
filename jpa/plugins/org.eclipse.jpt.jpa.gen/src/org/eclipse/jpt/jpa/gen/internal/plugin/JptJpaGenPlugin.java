/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.gen.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class JptJpaGenPlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static volatile JptJpaGenPlugin INSTANCE;

	/**
	 * Return the singleton Dali common core plug-in.
	 */
	public static JptJpaGenPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJpaGenPlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaGenPlugin) plugin;
	}
}
