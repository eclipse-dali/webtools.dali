/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.internal.prefs.JpaPreferencesManager;
import org.osgi.framework.BundleContext;

/**
 * Configure the core for testing:<ul>
 * <li>handle events synchronously
 * <li>do not flush preferences
 * </ul>
 */
@SuppressWarnings("nls")
public class JptJpaCoreTestsPlugin
	extends Plugin
{	
	private static JptJpaCoreTestsPlugin INSTANCE;

	public static JptJpaCoreTestsPlugin instance() {
		return INSTANCE;
	}


	// ********** plug-in implementation **********

	public JptJpaCoreTestsPlugin() {
		super();
		if (INSTANCE != null) {
			throw new IllegalStateException();
		}
		// this convention is *wack*...  ~bjv
		INSTANCE = this;
	}


	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		JpaProjectManager jpaProjectManager = this.getJpaProjectManager();
		ReflectionTools.executeMethod(jpaProjectManager, "executeCommandsSynchronously");
		ReflectionTools.setFieldValue(jpaProjectManager, "test", Boolean.TRUE);
		ReflectionTools.executeStaticMethod(JpaPreferencesManager.class, "doNotFlushPreferences");
	}

	private JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}
}
