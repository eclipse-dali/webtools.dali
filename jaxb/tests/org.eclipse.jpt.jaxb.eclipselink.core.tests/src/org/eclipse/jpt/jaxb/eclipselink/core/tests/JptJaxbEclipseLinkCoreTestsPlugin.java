/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.osgi.framework.BundleContext;

/**
 * configure the core to handle events synchronously when we are
 * running tests
 */
@SuppressWarnings("nls")
public class JptJaxbEclipseLinkCoreTestsPlugin
		extends Plugin {
	
	private static JptJaxbEclipseLinkCoreTestsPlugin INSTANCE;
	
	public static final String ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.eclipselink.jar";
	
	
	public static JptJaxbEclipseLinkCoreTestsPlugin instance() {
		return INSTANCE;
	}
	
	public static String eclipseLinkJarName() {
		return getSystemProperty(ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY);
	}

	private static String getSystemProperty(String propertyName) {
		return System.getProperty(propertyName);
	}
	
	
	// ********** plug-in implementation **********
	
	public JptJaxbEclipseLinkCoreTestsPlugin() {
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
