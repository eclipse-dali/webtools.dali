/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class JptJaxbEclipseLinkCorePlugin
		extends Plugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.jpt.jaxb.eclipselink.core"; //$NON-NLS-1$
	
	
	// ********** singleton **********
	private static JptJaxbEclipseLinkCorePlugin INSTANCE;
	
	/**
	 * Return the singleton JPT EclipseLink plug-in.
	 */
	public static JptJaxbEclipseLinkCorePlugin instance() {
		return INSTANCE;
	}	
	
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
	
	public JptJaxbEclipseLinkCorePlugin() {
		super();
	}
		
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		INSTANCE = null;
		super.stop(context);
	}
}
