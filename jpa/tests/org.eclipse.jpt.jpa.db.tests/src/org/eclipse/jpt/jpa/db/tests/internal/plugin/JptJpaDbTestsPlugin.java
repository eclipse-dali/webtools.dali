/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.tests.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

/**
 * Need plugin to simplify reading <code>config/*.properties</code> files.
 */
public class JptJpaDbTestsPlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static JptJpaDbTestsPlugin INSTANCE;

	public static JptJpaDbTestsPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJpaDbTestsPlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaDbTestsPlugin) plugin;
	}
}
