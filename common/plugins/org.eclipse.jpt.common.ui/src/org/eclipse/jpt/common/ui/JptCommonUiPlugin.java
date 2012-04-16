/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui;

import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Dali UI plug-in.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class JptCommonUiPlugin 
	extends JptUIPlugin
{
	// ********** constants **********

	/**
	 * The plug-in identifier of JPT Common UI support (value {@value}).
	 */
	public static final String PLUGIN_ID = "org.eclipse.jpt.common.ui"; //$NON-NLS-1$
	public static final String PLUGIN_ID_ = PLUGIN_ID + '.';

	// ********** singleton **********

	private static JptCommonUiPlugin INSTANCE;

	/**
	 * Returns the singleton JPT UI plug-in.
	 */
	public static JptCommonUiPlugin instance() {
		return INSTANCE;
	}


	// ********** logging **********

	public static void log(String msg) {
        INSTANCE.logError(msg);
    }

	public static void log(Throwable throwable) {
        INSTANCE.logError(throwable);
	}


	// ********** construction **********

	public JptCommonUiPlugin() {
		super();
		if (INSTANCE != null) {
			throw new IllegalStateException();
		}
		// this convention is *wack*...  ~bjv
		INSTANCE = this;
	}

	@Override
	public synchronized void start(BundleContext context) throws Exception {
		super.start(context);
		// nothing yet...
	}

	@Override
	public synchronized void stop(BundleContext context) throws Exception {
		try {
			// nothing yet...
		} finally {
			super.stop(context);
		}
	}
}
