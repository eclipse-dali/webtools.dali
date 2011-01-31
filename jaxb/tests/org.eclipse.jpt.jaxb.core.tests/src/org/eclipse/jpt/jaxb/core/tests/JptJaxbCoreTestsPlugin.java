/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.osgi.framework.BundleContext;

/**
 * configure the core to handle events synchronously when we are
 * running tests
 */
@SuppressWarnings("nls")
public class JptJaxbCoreTestsPlugin extends Plugin {

	private static JptJaxbCoreTestsPlugin INSTANCE;

	public static JptJaxbCoreTestsPlugin instance() {
		return INSTANCE;
	}


	// ********** plug-in implementation **********

	public JptJaxbCoreTestsPlugin() {
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
		JaxbProjectManager jaxbProjectManager = JptJaxbCorePlugin.getProjectManager();
		ReflectionTools.executeMethod(jaxbProjectManager, "handleEventsSynchronously");
		ReflectionTools.executeStaticMethod(JptJaxbCorePlugin.class, "doNotFlushPreferences");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

}
