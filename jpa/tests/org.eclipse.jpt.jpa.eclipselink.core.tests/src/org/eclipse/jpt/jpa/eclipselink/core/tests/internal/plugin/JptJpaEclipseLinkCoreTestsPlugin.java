/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

/**
 * Configure the core for testing:<ul>
 * <li>do not flush preferences
 * </ul>
 * The WTP build executes
 * {@link org.eclipse.jpt.jpa.eclipselink.core.tests.internal.JptJpaEclipseLinkCoreMiscTests}
 * standalone
 * (as opposed to within
 * {@link org.eclipse.jpt.jpa.eclipselink.core.tests.internal.JptJpaEclipseLinkCoreTests}).
 * As a result, the
 * {@link org.eclipse.jpt.jpa.core.tests.internal.plugin.JptJpaCoreTestsPlugin}
 * is not loaded (as it is when executing
 * {@link org.eclipse.jpt.jpa.eclipselink.core.tests.internal.JptJpaEclipseLinkCoreTests}).
 * So we need to stop preference flushes {@link #start_() here} also.
 */
public class JptJpaEclipseLinkCoreTestsPlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static JptJpaEclipseLinkCoreTestsPlugin INSTANCE;

	public static JptJpaEclipseLinkCoreTestsPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJpaEclipseLinkCoreTestsPlugin() {
		super();
	}

	@Override
	protected void start_() throws Exception {
		super.start_();
		JptPlugin.FlushPreferences = false;
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaEclipseLinkCoreTestsPlugin) plugin;
	}
}
