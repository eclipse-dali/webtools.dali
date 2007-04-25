/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 *  JptDbTestsPlugin
 */
public class JptDbTestsPlugin extends Plugin {

	// The shared instance
	private static JptDbTestsPlugin plugin;

	public static final String BUNDLE_ID = "org.eclipse.jpt.db.tests"; //$NON-NLS-1$

	/**
	 * Returns the shared instance
	 */
	public static JptDbTestsPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * The constructor
	 */
	public JptDbTestsPlugin() {
		super();
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
}
