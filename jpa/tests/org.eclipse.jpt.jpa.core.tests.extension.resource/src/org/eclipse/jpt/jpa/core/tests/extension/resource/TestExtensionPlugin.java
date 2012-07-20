/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.extension.resource;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class TestExtensionPlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static volatile TestExtensionPlugin INSTANCE;

	/**
	 * Return the singleton test extension plug-in.
	 */
	public static TestExtensionPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public TestExtensionPlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (TestExtensionPlugin) plugin;
	}
}
