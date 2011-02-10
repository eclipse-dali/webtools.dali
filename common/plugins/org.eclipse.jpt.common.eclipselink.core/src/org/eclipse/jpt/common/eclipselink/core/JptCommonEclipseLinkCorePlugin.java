/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.eclipselink.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;


public class JptCommonEclipseLinkCorePlugin
		extends Plugin {
	
	// ********** public constants **********
	
	/**
	 * The plug-in identifier of the jpt common core support
	 * (value <code>"org.eclipse.jpt.common.core"</code>).
	 */
	public static final String PLUGIN_ID = "org.eclipse.jpt.common.eclipselink.core";  //$NON-NLS-1$
	public static final String PLUGIN_ID_ = PLUGIN_ID + '.';
	
	
	// ********** singleton **********
	
	private static JptCommonEclipseLinkCorePlugin INSTANCE;
	
	/**
	 * Return the singleton jpt common eclipselink core plug-in.
	 */
	public static JptCommonEclipseLinkCorePlugin instance() {
		return INSTANCE;
	}
	
	
	// ********** public static methods **********
	
	/**
	 * Log the specified status.
	 */
	public static void log(IStatus status) {
        INSTANCE.getLog().log(status);
    }
	
	/**
	 * Log the specified message.
	 */
	public static void log(String msg) {
        log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, null));
    }
	
	/**
	 * Log the specified exception or error.
	 */
	public static void log(Throwable throwable) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, throwable.getLocalizedMessage(), throwable));
	}
	
	
	// ********** plug-in implementation **********
	
	public JptCommonEclipseLinkCorePlugin() {
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
		// nothing yet...
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		// nothing yet...		
	}
}
