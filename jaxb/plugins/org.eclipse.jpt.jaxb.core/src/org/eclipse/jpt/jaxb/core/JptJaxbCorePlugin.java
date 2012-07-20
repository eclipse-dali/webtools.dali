/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.jaxb.core.internal.platform.JaxbPlatformManagerImpl;
import org.eclipse.jpt.jaxb.core.internal.prefs.JaxbPreferencesManager;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.osgi.service.prefs.Preferences;

public class JptJaxbCorePlugin
	extends JptPlugin
{
	private volatile GenericJaxbProjectManager projectManager;


	// ********** singleton **********

	private static JptJaxbCorePlugin INSTANCE;

	/**
	 * Return the Dali JAXB core plug-in.
	 */
	public static JptJaxbCorePlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJaxbCorePlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJaxbCorePlugin) plugin;
	}

	@Override
	protected void stop_() throws Exception {
		try {
			if (this.projectManager != null) {
				this.projectManager.stop();
				this.projectManager = null;
			}
		} finally {
			super.stop_();
		}
	}


	// ********** project manager **********

	/**
	 * Return the singular JAXB project manager corresponding to the current workspace.
	 */
	public synchronized JaxbProjectManager getProjectManager() {
		if ((this.projectManager == null) && this.isActive()) {
			this.projectManager = this.buildProjectManager();
			this.projectManager.start();
		}
		return this.projectManager;
	}

	private GenericJaxbProjectManager buildProjectManager() {
		return new GenericJaxbProjectManager();
	}


	// ********** public static methods **********
	
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
}
