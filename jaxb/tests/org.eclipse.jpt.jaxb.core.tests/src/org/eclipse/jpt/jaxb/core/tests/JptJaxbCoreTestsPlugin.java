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
import org.osgi.framework.BundleContext;

/**
 * configure the core to handle events synchronously when we are
 * running tests
 */
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
//		JpaProjectManager jpaProjectManager = JptCorePlugin.getJpaProjectManager();
//		ReflectionTools.executeMethod(jpaProjectManager, "handleEventsSynchronously");
//		ReflectionTools.executeStaticMethod(JptCorePlugin.class, "doNotFlushPreferences");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

}
