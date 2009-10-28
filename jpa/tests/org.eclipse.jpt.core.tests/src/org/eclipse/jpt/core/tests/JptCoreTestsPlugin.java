/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.jpt.core.JpaModel;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.osgi.framework.BundleContext;

/**
 * configure the core to handle events synchronously when we are
 * running tests
 */
@SuppressWarnings("nls")
public class JptCoreTestsPlugin extends Plugin {
	
	private static JptCoreTestsPlugin INSTANCE;

	public static JptCoreTestsPlugin instance() {
		return INSTANCE;
	}


	// ********** plug-in implementation **********

	public JptCoreTestsPlugin() {
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
		JpaModel jpaModel = JptCorePlugin.getJpaModel();
		ClassTools.executeMethod(jpaModel, "handleEventsSynchronously");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

}
