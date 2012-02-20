/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jaxb.core.internal.platform.JaxbPlatformManagerImpl;
import org.eclipse.jpt.jaxb.core.internal.prefs.JaxbPreferencesManager;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.Preferences;

/**
 * The Dali JAXB core plug-in lifecycle implementation.
 * A number of globally-available constants and methods.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public class JptJaxbCorePlugin
		extends Plugin {

	// ********** public constants **********
	
	/**
	 * The plug-in identifier of Dali JAXB core
	 * (value <code>"org.eclipse.jpt.jaxb.core"</code>).
	 */
	public static final String PLUGIN_ID = "org.eclipse.jpt.jaxb.core";  //$NON-NLS-1$
	public static final String PLUGIN_ID_ = PLUGIN_ID + '.';

	/**
	 * The identifier for the JAXB validation marker
	 * (value <code>"org.eclipse.jpt.jaxb.core.jaxbProblemMarker"</code>).
	 */
	public static final String VALIDATION_MARKER_ID = PLUGIN_ID + ".jaxbProblemMarker";  //$NON-NLS-1$

	/**
	 * The content type for jaxb.index files
	 */
	public static final IContentType JAXB_INDEX_CONTENT_TYPE = getJaxbContentType("jaxbIndex");
	
	/**
	 * The resource type for jaxb.index files
	 */
	public static final JptResourceType JAXB_INDEX_RESOURCE_TYPE = new JptResourceType(JAXB_INDEX_CONTENT_TYPE);
	
	/**
	 * The content type for jaxb.properties files
	 */
	public static final IContentType JAXB_PROPERTIES_CONTENT_TYPE = getJaxbContentType("jaxbProperties");
	
	/**
	 * The resource type for jaxb.properties files
	 */
	public static final JptResourceType JAXB_PROPERTIES_RESOURCE_TYPE = new JptResourceType(JAXB_PROPERTIES_CONTENT_TYPE);
	
	private static IContentType getJaxbContentType(String contentType) {
		return getContentType(CONTENT_PREFIX_ + contentType);
	}
	
	public static final String CONTENT_PREFIX = PLUGIN_ID_ + "content"; //$NON-NLS-1$
	
	public static final String CONTENT_PREFIX_ = CONTENT_PREFIX + '.';
	
	private static IContentType getContentType(String contentType) {
		return Platform.getContentTypeManager().getContentType(contentType);
	}
	
	
	// **************** fields ************************************************
	
	private volatile GenericJaxbProjectManager projectManager;
	
	
	// **************** singleton *********************************************
	
	private static JptJaxbCorePlugin INSTANCE;
	
	/**
	 * Return the singleton plug-in
	 */
	public static JptJaxbCorePlugin instance() {
		return INSTANCE;
	}
	
	
	// ********** public static methods **********

	/**
	 * Return the singular JAXB project manager corresponding to the current workspace.
	 */
	public static JaxbProjectManager getProjectManager() {
		return INSTANCE.getProjectManager_();
	}

	/**
	 * Return the JAXB project corresponding to the specified Eclipse project,
	 * or <code>null</code> if unable to associate the specified project with a
	 * JAXB project.
	 */
	public static JaxbProject getJaxbProject(IProject project) {
		return getProjectManager().getJaxbProject(project);
	}
	
	public static JaxbPlatformManager getJaxbPlatformManager() {
		return JaxbPlatformManagerImpl.instance();
	}
	
	/**
	 * Set the default {@link JaxbPlatformDescription} for new JAXB projects with the given
	 * JAXB facet version.
	 */
	public static void setDefaultJaxbPlatform(IProjectFacetVersion jaxbFacetVersion, JaxbPlatformDescription platform) {
		String preferenceKey = null;
		if (JaxbFacet.VERSION_2_1.equals(jaxbFacetVersion)) {
			preferenceKey = JaxbPreferencesManager.DEFAULT_JAXB_PLATFORM_2_1_PREF_KEY;
		}
		else if (JaxbFacet.VERSION_2_2.equals(jaxbFacetVersion)) {
			preferenceKey = JaxbPreferencesManager.DEFAULT_JAXB_PLATFORM_2_2_PREF_KEY;
		}
		else {
			throw new IllegalArgumentException("Illegal JAXB facet version: " + jaxbFacetVersion); //$NON-NLS-1$
		}
		JaxbPreferencesManager.setWorkspacePreference(preferenceKey, platform.getId());
	}
	
	/** 
	 * Return the default {@link JaxbPlatformDescription} for new JAXB projects with the given 
	 * JAXB facet version.
	 */
	public static JaxbPlatformDescription getDefaultPlatform(IProjectFacetVersion jaxbFacetVersion) {
		
		JaxbPlatformDescription defaultPlatform = 
				getDefaultPlatform(
					jaxbFacetVersion, 
					JaxbPreferencesManager.getWorkspacePreferences(), 
					JaxbPreferencesManager.getDefaultPreferences());
		if (defaultPlatform == null) {
			// if the platform ID stored in the workspace prefs is invalid (i.e. null), look in the default prefs
			defaultPlatform = getDefaultPlatform(jaxbFacetVersion, JaxbPreferencesManager.getDefaultPreferences());
		}
		return defaultPlatform;
	}
	
	// 
	private static JaxbPlatformDescription getDefaultPlatform(
				IProjectFacetVersion jaxbFacetVersion, Preferences ... nodes) {
		
		JaxbPlatformDescription defaultDefaultPlatform;
		String preferenceKey;
		if (jaxbFacetVersion.equals(JaxbFacet.VERSION_2_1)) {
			defaultDefaultPlatform = GenericJaxbPlatform.VERSION_2_1;
			preferenceKey = JaxbPreferencesManager.DEFAULT_JAXB_PLATFORM_2_1_PREF_KEY; 
		}
		else if (jaxbFacetVersion.equals(JaxbFacet.VERSION_2_2)) {
			defaultDefaultPlatform = GenericJaxbPlatform.VERSION_2_2;
			preferenceKey = JaxbPreferencesManager.DEFAULT_JAXB_PLATFORM_2_2_PREF_KEY; 
		}
		else {
			throw new IllegalArgumentException("Illegal JAXB facet version: " + jaxbFacetVersion); //$NON-NLS-1$
		}
		return getDefaultPlatform(jaxbFacetVersion, preferenceKey, defaultDefaultPlatform, nodes);
	}
	
	private static JaxbPlatformDescription getDefaultPlatform(
			IProjectFacetVersion jaxbFacetVersion, String preferenceKey, 
			JaxbPlatformDescription defaultDefault, Preferences ... nodes) {	
		
		String defaultDefaultId = (defaultDefault == null) ? null : defaultDefault.getId();
		String defaultPlatformId = Platform.getPreferencesService().get(preferenceKey, defaultDefaultId, nodes);
		JaxbPlatformDescription defaultPlatform = getJaxbPlatformManager().getJaxbPlatform(defaultPlatformId);
		if (defaultPlatform != null && defaultPlatform.supportsJaxbFacetVersion(jaxbFacetVersion)) {
			return defaultPlatform;
		}
		else if (defaultDefault != null && defaultDefault.supportsJaxbFacetVersion(jaxbFacetVersion)) {
			return defaultDefault;
		}
		return null;
	}

	/**
	 * Return the JAXB platform ID associated with the specified Eclipse project.
	 */
	public static String getJaxbPlatformId(IProject project) {
		return (new JaxbPreferencesManager(project)).getJaxbPlatformId();
	}

	/**
	 * Return the {@link JaxbPlatformDescription} associated with the specified Eclipse project.
	 */
	public static JaxbPlatformDescription getJaxbPlatformDescription(IProject project) {
		String jpaPlatformId = getJaxbPlatformId(project);
		return getJaxbPlatformManager().getJaxbPlatform(jpaPlatformId);
	}
	
	/**
	 * Set the {@link JaxbPlatformDescription} associated with the specified Eclipse project.
	 */
	public static void setJaxbPlatform(IProject project, JaxbPlatformDescription platform) {
		(new JaxbPreferencesManager(project)).setJaxbPlatform(platform);
	}
	
	public static List<String> getSchemaLocations(IProject project) {
		return (new JaxbPreferencesManager(project)).getSchemaLocations();
	}
	
	public static void setSchemaLocations(IProject project, List<String> schemaLocations) {
		(new JaxbPreferencesManager(project)).setSchemaLocations(schemaLocations);
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
	
	
	// ********** plug-in implementation **************************************************

	public JptJaxbCorePlugin() {
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
		// nothing yet...
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			if (this.projectManager != null) {
				this.projectManager.stop();
				this.projectManager = null;
			}
		} finally {
			super.stop(context);
		}
	}
	
	private synchronized GenericJaxbProjectManager getProjectManager_() {
		if (this.projectManager == null) {
			this.projectManager = this.buildProjectManager();
			this.projectManager.start();
		}
		return this.projectManager;
	}

	private GenericJaxbProjectManager buildProjectManager() {
		return new GenericJaxbProjectManager();
	}
}
