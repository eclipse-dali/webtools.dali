/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class JptCommonUiPlugin 
	extends AbstractUIPlugin
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

	public static void log(IStatus status) {
        INSTANCE.getLog().log(status);
    }

	public static void log(String msg) {
        log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, null));
    }

	public static void log(Throwable throwable) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, throwable.getLocalizedMessage(), throwable));
	}


	// ********** construction **********

	public JptCommonUiPlugin() {
		super();
		if (INSTANCE != null) {
			throw new IllegalStateException();
		}
		INSTANCE = this;
	}

}
