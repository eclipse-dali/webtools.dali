/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class JptJaxbEclipseLinkCoreTestsPlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static JptJaxbEclipseLinkCoreTestsPlugin INSTANCE;

	public static JptJaxbEclipseLinkCoreTestsPlugin instance() {
		return INSTANCE;
	}
	
		
	// ********** Dali plug-in **********
	
	public JptJaxbEclipseLinkCoreTestsPlugin() {
		super();
	}
	
	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJaxbEclipseLinkCoreTestsPlugin) plugin;
	}
}
