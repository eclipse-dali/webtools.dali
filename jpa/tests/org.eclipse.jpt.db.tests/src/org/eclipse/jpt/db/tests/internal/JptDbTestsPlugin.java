/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
	private static JptDbTestsPlugin INSTANCE;

	public static final String BUNDLE_ID = "org.eclipse.jpt.db.tests"; //$NON-NLS-1$

	/**
	 * Returns the shared instance
	 */
	public static JptDbTestsPlugin instance() {
		return INSTANCE;
	}
	
	/**
	 * The constructor
	 */
	public JptDbTestsPlugin() {
		super();
		INSTANCE = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		INSTANCE = null;
		super.stop(context);
	}
}
