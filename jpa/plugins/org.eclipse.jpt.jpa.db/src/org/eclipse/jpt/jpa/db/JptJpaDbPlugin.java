/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.datatools.enablement.jdt.classpath.DriverClasspathContainer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jpt.jpa.db.internal.DTPConnectionProfileFactory;
import org.osgi.framework.BundleContext;

/**
 * The Dali JPA DB plug-in lifecycle implementation.
 * Globally available connection profile factory.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class JptJpaDbPlugin
	extends Plugin
{
	// lazy-initialized
	private DTPConnectionProfileFactory connectionProfileFactory;

	private static JptJpaDbPlugin INSTANCE;  // sorta-final

	public static final String PLUGIN_ID = "org.eclipse.jpt.jpa.db";  //$NON-NLS-1$

	/**
	 * Return the singleton JPT DB plug-in.
	 */
	public static JptJpaDbPlugin instance() {
		return INSTANCE;
	}


	// ********** public static methods **********

	public static ConnectionProfileFactory getConnectionProfileFactory() {
		return INSTANCE.getConnectionProfileFactory_();
	}


	// ********** logging **********

	/**
	 * Log the specified message.
	 */
	public static void log(String msg) {
        log(msg, null);
    }

	/**
	 * Log the specified exception or error.
	 */
	public static void log(Throwable throwable) {
		log(throwable.getLocalizedMessage(), throwable);
	}

	/**
	 * Log the specified message and exception or error.
	 */
	public static void log(String msg, Throwable throwable) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, throwable));
	}

	/**
	 * Log the specified status.
	 */
	public static void log(IStatus status) {
        INSTANCE.getLog().log(status);
    }


	// ********** plug-in implementation **********

	/**
	 * The constructor
	 */
	public JptJpaDbPlugin() {
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
		// the connection profile factory is lazy-initialized...
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		if (this.connectionProfileFactory != null) {
			this.connectionProfileFactory.stop();
			this.connectionProfileFactory = null;
		}
		INSTANCE = null;
		super.stop(context);
	}

	private synchronized ConnectionProfileFactory getConnectionProfileFactory_() {
		if (this.connectionProfileFactory == null) {
			this.connectionProfileFactory = this.buildConnectionProfileFactory();
	        this.connectionProfileFactory.start();			
		}
		return this.connectionProfileFactory;
	}

	private DTPConnectionProfileFactory buildConnectionProfileFactory() {
		return DTPConnectionProfileFactory.instance();
	}

	/**
	 * Creates a jar list container for the given DTP driver.
	 */
	public IClasspathContainer buildDriverClasspathContainerFor(String driverName) {
		return new DriverClasspathContainer(driverName);
	}
}
