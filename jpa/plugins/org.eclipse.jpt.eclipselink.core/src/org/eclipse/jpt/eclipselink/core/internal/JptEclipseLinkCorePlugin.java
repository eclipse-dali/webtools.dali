/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.core.JptCorePlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JptEclipseLinkCorePlugin extends Plugin
{
	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.jpt.eclipselink.core";
	
	/**
	 * Value of the content-type for eclipselink-orm.xml mappings files. Use this 
	 * value to retrieve the ORM xml content type from the content type manager 
	 * and to add new eclipselink-orm.xml-like extensions to this content type.
	 * 
	 * @see org.eclipse.core.runtime.content.IContentTypeManager#getContentType(String)
	 */
	public static final String ECLIPSELINK_ORM_XML_CONTENT_TYPE = PLUGIN_ID + ".content.orm"; //$NON-NLS-1$
	
	public static final String DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH = "META-INF/eclipselink-orm.xml";
	
	
	// The shared instance
	private static JptEclipseLinkCorePlugin plugin;
	
	
	/**
	 * The constructor
	 */
	public JptEclipseLinkCorePlugin() {}
	
	
	/**
	 * Return the default mapping file (specified as "META-INF/eclipselink-orm.xml")
	 * deployment URI for the specified project.
	 */
	public static String getDefaultEclipseLinkOrmXmlDeploymentURI(IProject project) {
		return JptCorePlugin.getDeploymentURI(project, DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
	}
	
	/**
	 * Log the specified status.
	 */
	public static void log(IStatus status) {
        plugin.getLog().log(status);
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static JptEclipseLinkCorePlugin getDefault() {
		return plugin;
	}
}
