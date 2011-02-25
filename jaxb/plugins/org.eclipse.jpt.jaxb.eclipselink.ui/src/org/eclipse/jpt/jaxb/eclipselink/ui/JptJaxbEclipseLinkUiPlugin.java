/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The activator class controls the plug-in life cycle
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
@SuppressWarnings("nls")
public class JptJaxbEclipseLinkUiPlugin
		extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.jpt.jaxb.eclipselink.ui";
	
	
	// ********** singleton **********
	
	private static JptJaxbEclipseLinkUiPlugin INSTANCE;
	
	/**
	 * Returns the singleton Plugin
	 */
	public static JptJaxbEclipseLinkUiPlugin instance() {
		return INSTANCE;
	}
	
	
	// ********** error logging **********
	
	public static void log(IStatus status) {
        INSTANCE.getLog().log(status);
    }
	
	public static void log(String msg) {
        log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, null));
    }
	
	public static void log(Throwable throwable) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, throwable.getLocalizedMessage(), throwable));
	}
	
	
	// ********** constructors **********
	
	public JptJaxbEclipseLinkUiPlugin() {
		super();
		if (INSTANCE != null) {
			throw new IllegalStateException();
		}
		INSTANCE = this;
	}
}
