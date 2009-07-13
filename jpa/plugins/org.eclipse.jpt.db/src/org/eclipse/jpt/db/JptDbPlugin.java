/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.datatools.enablement.jdt.classpath.DriverClasspathContainer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jpt.db.internal.DTPConnectionProfileFactory;
import org.osgi.framework.BundleContext;

/**
 * The JPT DB plug-in lifecycle implementation.
 * Globally available connection profile factory.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class JptDbPlugin extends Plugin {
	private DTPConnectionProfileFactory connectionProfileFactory;

	private static JptDbPlugin INSTANCE;  // sorta-final

	/**
	 * Return the singleton JPT DB plug-in.
	 */
	public static JptDbPlugin instance() {
		return INSTANCE;
	}

	/**
	 * The constructor
	 */
	public JptDbPlugin() {
		super();
		if (INSTANCE != null) {
			throw new IllegalStateException();
		}
		// this convention is *wack*...  ~bjv
		INSTANCE = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.connectionProfileFactory = DTPConnectionProfileFactory.instance();
        this.connectionProfileFactory.start();
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		this.connectionProfileFactory.stop();
		this.connectionProfileFactory = null;
		INSTANCE = null;
		super.stop(context);
	}

	public ConnectionProfileFactory getConnectionProfileFactory() {
		return this.connectionProfileFactory;
	}

	/**
	 * Creates a jar list container for the given DTP driver.
	 */
	public IClasspathContainer buildDriverClasspathContainerFor(String driverName) {
		return new DriverClasspathContainer(driverName);
	}

}
