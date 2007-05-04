/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class JptDbPlugin extends Plugin {
	private ConnectionProfileRepository connectionProfileRepository;

	// The shared instance
	private static JptDbPlugin plugin;

	/**
	 * Returns the shared instance
	 */
	public static JptDbPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * The constructor
	 */
	public JptDbPlugin() {
		super();
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.connectionProfileRepository = ConnectionProfileRepository.instance();
        this.connectionProfileRepository.initializeListeners();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		this.connectionProfileRepository.disposeListeners();
		this.connectionProfileRepository = null;
		plugin = null;
		super.stop(context);
	}


	public ConnectionProfileRepository getConnectionProfileRepository() {
		return this.connectionProfileRepository;
	}
}
