/*******************************************************************************
 * Copyright (c) 2013, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.annotate.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class JptJpaAnnotatePlugin extends JptPlugin 
{
	// ********** singleton **********

	private static volatile JptJpaAnnotatePlugin INSTANCE;

	/**
	 * Return the singleton Dali common core plug-in.
	 */
	public static JptJpaAnnotatePlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJpaAnnotatePlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaAnnotatePlugin) plugin;
	}

}
