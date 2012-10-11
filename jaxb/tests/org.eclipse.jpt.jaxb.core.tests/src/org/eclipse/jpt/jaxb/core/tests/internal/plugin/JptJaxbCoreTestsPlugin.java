/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.plugin;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;

/**
 * Configure the core for testing:<ul>
 * <li>handle events synchronously
 * <li>do not flush preferences
 * </ul>
 */
@SuppressWarnings("nls")
public class JptJaxbCoreTestsPlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static JptJaxbCoreTestsPlugin INSTANCE;

	public static JptJaxbCoreTestsPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJaxbCoreTestsPlugin() {
		super();
	}

	@Override
	protected void start_() throws Exception {
		super.start_();
		JaxbProjectManager jaxbProjectManager = this.getJaxbProjectManager();
		ObjectTools.execute(jaxbProjectManager, "handleEventsSynchronously");
		JptPlugin.FlushPreferences = false;
	}

	private JaxbProjectManager getJaxbProjectManager() {
		return this.getJaxbWorkspace().getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJaxbCoreTestsPlugin) plugin;
	}
}
