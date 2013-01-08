/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.plugin;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.osgi.framework.BundleContext;

/**
 * Configure the core for testing:<ul>
 * <li>handle events synchronously
 * <li>do not flush preferences
 * </ul>
 */
@SuppressWarnings("nls")
public class JptJpaCoreTestsPlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static JptJpaCoreTestsPlugin INSTANCE;

	public static JptJpaCoreTestsPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJpaCoreTestsPlugin() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		JpaProjectManager jpaProjectManager = this.getJpaProjectManager();
		ObjectTools.execute(jpaProjectManager, "executeCommandsSynchronously");
		JptPlugin.FlushPreferences = false;
	}

	private JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaCoreTestsPlugin) plugin;
	}
}
