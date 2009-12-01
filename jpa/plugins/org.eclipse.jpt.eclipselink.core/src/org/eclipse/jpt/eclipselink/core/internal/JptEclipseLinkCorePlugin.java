/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_2.EclipseLink1_2;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JptEclipseLinkCorePlugin extends Plugin
{
	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.jpt.eclipselink.core"; //$NON-NLS-1$
	
	/**
	 * Version string for EclipseLink platform version 1.0
	 */
	public static final String ECLIPSELINK_PLATFORM_VERSION_1_0 = "1.0";  //$NON-NLS-1$
	
	/**
	 * Version string for EclipseLink platform version 1.1
	 */
	public static final String ECLIPSELINK_PLATFORM_VERSION_1_1 = "1.1";  //$NON-NLS-1$
	
	/**
	 * Version string for EclipseLink platform version 1.2
	 */
	public static final String ECLIPSELINK_PLATFORM_VERSION_1_2 = "1.2";  //$NON-NLS-1$
	
	/**
	 * Version string for EclipseLink platform version 2.0
	 */
	public static final String ECLIPSELINK_PLATFORM_VERSION_2_0 = "2.0";  //$NON-NLS-1$
	
	/**
	 * Value of the content-type for eclipselink-orm.xml  mappings files. Use this 
	 * value to retrieve the ORM xml content type from the content type manager 
	 * and to add new eclipselink-orm.xml-like extensions to this content type.
	 * 
	 * @see org.eclipse.core.runtime.content.IContentTypeManager#getContentType(String)
	 */
	public static final IContentType ECLIPSELINK_ORM_XML_CONTENT_TYPE = 
			Platform.getContentTypeManager().getContentType(PLUGIN_ID + ".content.orm"); //$NON-NLS-1$
	
	/**
	 * The resource type for eclipselink-orm.xml version 1.0 mapping files
	 */
	public static final JpaResourceType ECLIPSELINK_ORM_XML_1_0_RESOURCE_TYPE
			= new JpaResourceType(ECLIPSELINK_ORM_XML_CONTENT_TYPE, EclipseLink.SCHEMA_VERSION);
	
	/**
	 * The resource type for eclipselink-orm.xml version 1.1 mapping files
	 */
	public static final JpaResourceType ECLIPSELINK_ORM_XML_1_1_RESOURCE_TYPE
			= new JpaResourceType(ECLIPSELINK_ORM_XML_CONTENT_TYPE, EclipseLink1_1.SCHEMA_VERSION);
	
	/**
	 * The resource type for eclipselink-orm.xml version 1.1 mapping files
	 */
	public static final JpaResourceType ECLIPSELINK_ORM_XML_1_2_RESOURCE_TYPE
			= new JpaResourceType(ECLIPSELINK_ORM_XML_CONTENT_TYPE, EclipseLink1_2.SCHEMA_VERSION);
	
	/**
	 * The resource type for eclipselink-orm.xml version 2.0 mapping files
	 */
	public static final JpaResourceType ECLIPSELINK_ORM_XML_2_0_RESOURCE_TYPE
			= new JpaResourceType(ECLIPSELINK_ORM_XML_CONTENT_TYPE, EclipseLink2_0.SCHEMA_VERSION);
	
	public static final String DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH = "META-INF/eclipselink-orm.xml"; //$NON-NLS-1$
	
	
	// ********** singleton **********
	private static JptEclipseLinkCorePlugin INSTANCE;
	
	/**
	 * Return the singleton JPT EclipseLink plug-in.
	 */
	public static JptEclipseLinkCorePlugin instance() {
		return INSTANCE;
	}	
	
	/**
	 * Return the default mapping file deployment URI for the specified project.
	 * ("WEB-INF/classes/META-INF/eclipselink-orm.xml" for web projects and
	 *  "META-INF/eclipselink-orm.xml" in other cases)
	 */
	public static String getDefaultEclipseLinkOrmXmlDeploymentURI(IProject project) {
		return JptCorePlugin.getDeploymentURI(project, DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
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
	
	public JptEclipseLinkCorePlugin() {
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
