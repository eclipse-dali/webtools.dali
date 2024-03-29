/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.exception.RuntimeExceptionHandler;

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
	// ********** singleton **********

	private static JptCommonUiPlugin INSTANCE;

	/**
	 * Returns the singleton JPT UI plug-in.
	 */
	public static JptCommonUiPlugin instance() {
		return INSTANCE;
	}

	/**
	 * Provider an exception handler for non-OSGi UIs.
	 */
	public static final ExceptionHandler exceptionHandler() {
		return (INSTANCE != null) ? INSTANCE.getExceptionHandler() : RuntimeExceptionHandler.instance();
	}


	// ********** Dali plug-in **********

	public JptCommonUiPlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptCommonUiPlugin) plugin;
	}
}
