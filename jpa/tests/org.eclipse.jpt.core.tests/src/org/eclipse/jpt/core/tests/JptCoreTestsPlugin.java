/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.core.JpaProjectManager;
import org.eclipse.jpt.core.JptCorePlugin;
import org.osgi.framework.BundleContext;

/**
 * Configure the core for testing:<ul>
 * <li>handle events synchronously
 * <li>do not flush preferences
 * </ul>
 */
@SuppressWarnings("nls")
public class JptCoreTestsPlugin
	extends Plugin
{
	
	private static JptCoreTestsPlugin INSTANCE;

	public static JptCoreTestsPlugin instance() {
		return INSTANCE;
	}


	// ********** plug-in implementation **********

	public JptCoreTestsPlugin() {
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
		JpaProjectManager jpaProjectManager = JptCorePlugin.getJpaProjectManager();
		ReflectionTools.executeMethod(jpaProjectManager, "handleEventsSynchronously");
		ReflectionTools.executeStaticMethod(JptCorePlugin.class, "doNotFlushPreferences");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

}
