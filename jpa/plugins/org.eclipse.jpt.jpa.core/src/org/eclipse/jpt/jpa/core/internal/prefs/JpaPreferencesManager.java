/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.prefs;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.internal.JptCoreMessages;
import org.eclipse.jpt.jpa.core.internal.platform.JpaPlatformManagerImpl;
import org.eclipse.jpt.jpa.core.platform.GenericPlatform;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.osgi.util.NLS;
import org.osgi.service.prefs.BackingStoreException;

/**
 *  JptPreferencesManager
 */
public class JpaPreferencesManager
{
	private final IProject project;

	private static volatile boolean flushPreferences = true;

	// ********** public constants **********
	
	/**
	 * The key for storing a JPA project's platform ID in the Eclipse
	 * project's preferences.
	 */
	protected static final String JPA_PLATFORM_PREF_KEY = JptJpaCorePlugin.LEGACY_PLUGIN_ID_ + "platform";  //$NON-NLS-1$

	/**
	 * The old key for storing the default JPA platform ID in the workspace preferences.
	 * @deprecated  As of version 2.3.  Instead use {@link #DEFAULT_JPA_PLATFORM_1_0_PREF_KEY} or 
	 * 		{@link #DEFAULT_JPA_PLATFORM_2_0_PREF_KEY}
	 */
	@Deprecated
	public static final String DEFAULT_JPA_PLATFORM_PREF_KEY = "defaultJpaPlatform"; //$NON-NLS-1$
	
	/**
	 * The key for storing the default JPA platform ID for JPA 1.0 in the workspace preferences.
	 */
	public static final String DEFAULT_JPA_PLATFORM_1_0_PREF_KEY = 
			DEFAULT_JPA_PLATFORM_PREF_KEY + "_" + JpaFacet.VERSION_1_0.getVersionString(); //$NON-NLS-1$
	
	/**
	 * The key for storing the default JPA platform ID for JPA 2.0 in the workspace preferences.
	 */
	public static final String DEFAULT_JPA_PLATFORM_2_0_PREF_KEY = 
			DEFAULT_JPA_PLATFORM_PREF_KEY + "_" + JpaFacet.VERSION_2_0.getVersionString(); //$NON-NLS-1$

	/**
	 * The key for storing a JPA project's "discover" flag in the Eclipse
	 * project's preferences.
	 */
	public static final String DISCOVER_ANNOTATED_CLASSES = JptJpaCorePlugin.PLUGIN_ID_ + "discoverAnnotatedClasses";  //$NON-NLS-1$

	//bug 354780 - made the mistake of changing the project metadata in the 3.0 release
	protected static final String LEGACY_DISCOVER_ANNOTATED_CLASSES = JptJpaCorePlugin.LEGACY_PLUGIN_ID_ + "discoverAnnotatedClasses";  //$NON-NLS-1$

	/**
	 * The key for storing the name of a JPA project's metamodel source folder
	 * in the Eclipse project's preferences.
	 */
	public static final String METAMODEL_SOURCE_FOLDER_NAME = JptJpaCorePlugin.PLUGIN_ID_ + "metamodelSourceFolderName";  //$NON-NLS-1$

	//bug 354780 - made the mistake of changing the project metadata in the 3.0 release
	protected static final String LEGACY_METAMODEL_SOURCE_FOLDER_NAME = JptJpaCorePlugin.LEGACY_PLUGIN_ID_ + "metamodelSourceFolderName";  //$NON-NLS-1$

	
	// ********** static methods **************************************************
	
	public static void clearWorkspacePreferences() throws BackingStoreException {
		getLegacyWorkspacePreferences().clear();
	}

	// ********** preferences **********

	/**
	 * Return the legacy Dali (org.eclipse.jpt.core) preferences for the specified context.
	 */
	private static IEclipsePreferences getLegacyPreferences(IScopeContext context) {
		return context.getNode(JptJpaCorePlugin.LEGACY_PLUGIN_ID);
	}
	
	/**
	 * Return the Dali (org.eclipse.jpt.jpa.core) preferences for the specified context.
	 */
	private static IEclipsePreferences getPreferences(IScopeContext context) {
		return context.getNode(JptJpaCorePlugin.PLUGIN_ID);
	}
	
	/**
	 * Return the legacy Dali (org.eclipse.jpt.core) preferences for the current workspace instance.
	 */
	public static IEclipsePreferences getLegacyWorkspacePreferences() {
		return getLegacyPreferences(InstanceScope.INSTANCE);
	}
	
	/**
	 * Return the legacy Dali (org.eclipse.jpt.core) default preferences
	 */
	 public static IEclipsePreferences getLegacyDefaultPreferences() {
		return getLegacyPreferences(DefaultScope.INSTANCE);
	}
	 
	 /**
	  * Return the Dali (org.eclipse.jpt.jpa.core) default preferences
	  * @see JpaPreferenceInitializer
	  */
	public static IEclipsePreferences getDefaultPreferences() {
		return getPreferences(DefaultScope.INSTANCE);
	}
	
	public static void initializeDefaultPreferences() {
		IEclipsePreferences node = getDefaultPreferences();

		// default JPA platforms
		JpaPlatformDescription defaultPlatform_1_0 = 
				JpaPlatformManagerImpl.instance().getDefaultJpaPlatform(JpaFacet.VERSION_1_0);
		if (defaultPlatform_1_0 == null) {
			defaultPlatform_1_0 = GenericPlatform.VERSION_1_0;
		}
		node.put(DEFAULT_JPA_PLATFORM_1_0_PREF_KEY, defaultPlatform_1_0.getId());
		
		JpaPlatformDescription defaultPlatform_2_0 = 
				JpaPlatformManagerImpl.instance().getDefaultJpaPlatform(JpaFacet.VERSION_2_0);
		if (defaultPlatform_2_0 == null) {
			defaultPlatform_2_0 = GenericPlatform.VERSION_2_0;
		}
		node.put(DEFAULT_JPA_PLATFORM_2_0_PREF_KEY, defaultPlatform_2_0.getId());
	}

	// ********** workspace preference **********

	protected static String getLegacyWorkspacePreference(String key, String defaultValue) {
		String value = getLegacyWorkspacePreferences().get(key, defaultValue);
		return (StringTools.stringIsEmpty(value)) ? defaultValue : value;
	}

	protected static String getLegacyWorkspacePreference(String key) {
		return getLegacyWorkspacePreferences().get(key, null);
	}

	public static void setLegacyWorkspacePreference(String key, String value) {
		IEclipsePreferences wkspPrefs = getLegacyWorkspacePreferences();
		if(value == null) {
			wkspPrefs.remove(key);
		}
		else {
			wkspPrefs.put(key, value);
		}
		flush(wkspPrefs);
	}
	
	protected static boolean getLegacyWorkspacePreference(String key, boolean defaultBooleanValue) {
		return getLegacyWorkspacePreferences().getBoolean(key, defaultBooleanValue);
	}

	public static void setLegacyWorkspacePreference(String key, boolean booleanValue) {
		IEclipsePreferences wkspPrefs = getLegacyWorkspacePreferences();
		wkspPrefs.putBoolean(key, booleanValue);
		flush(wkspPrefs);
	}

	public static void removeLegacyWorkspacePreference(String key) {
		IEclipsePreferences wkspPrefs = getLegacyWorkspacePreferences();
		wkspPrefs.remove(key);
		flush(wkspPrefs);
	}

	// ********** private static methods **********

	/**
	 * This method is called (via reflection) when the test plug-in is loaded.
	 * The preferences end up getting flushed after the test case has deleted
	 * its project, resulting in resource exceptions in the log, e.g.
	 * <pre>
	 *     Resource '/JpaProjectManagerTests' is not open.
	 * </pre>
	 * See <code>JptJpaCoreTestsPlugin.start(BundleContext)</code>
	 */
	@SuppressWarnings("unused")
	private static void doNotFlushPreferences() {
		flushPreferences = false;
	}

	/**
	 * Flush preferences in an asynchronous Job because the flush request will
	 * trigger a lock on the project, which can cause us some deadlocks (e.g.
	 * when deleting the metamodel source folder).
	 * Note: the flush will also remove the prefs node if it is empty
	 */
	private static void flush(IEclipsePreferences prefs) {
		if (flushPreferences) {
			new PreferencesFlushJob(prefs).schedule();
		}
	}

	private static class PreferencesFlushJob extends Job {
		private final IEclipsePreferences prefs;
		PreferencesFlushJob(IEclipsePreferences prefs) {
			super(NLS.bind(JptCoreMessages.PREFERENCES_FLUSH_JOB_NAME, prefs.absolutePath()));
			this.prefs = prefs;
		}
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				this.prefs.flush();
			} 
			catch(BackingStoreException ex) {
				JptJpaCorePlugin.log(ex);
			}
			return Status.OK_STATUS;
		}
	}

	// ********** implementation **************************************************

	public JpaPreferencesManager(IProject project) {
		if(project == null) {
			throw new RuntimeException("Project is null"); 	//$NON-NLS-1$
		}
		this.project = project;
	}

	// ********** query **********

	protected String getLegacyPreference(String key) {
		String preferenceValue = this.getLegacyProjectPreference(key);
		
		// check workspace preferences if not a project preference
		if(preferenceValue == null) {
			preferenceValue = getLegacyWorkspacePreference(key);
		}
		return preferenceValue;
	}
	
	protected String getLegacyPreference(String key, String defaultValue) {
		String preferenceValue = this.getLegacyPreference(key);
		if(StringTools.stringIsEmpty(preferenceValue)) {
			return defaultValue;
		}
		return preferenceValue;
	}

	// ********** preferences **********

	/**
	 * Return the legacy Dali (org.eclipse.jpt.core) preferences for the specified Eclipse project.
	 */
	protected IEclipsePreferences getLegacyProjectPreferences() {
		return getLegacyPreferences(new ProjectScope(this.project));
	}
	
	/**
	 * Clears the project of JPA-specific preferences
	 */
	public void clearProjectPreferences() {
		this.clearLegacyProjectPreferences(
				JPA_PLATFORM_PREF_KEY,
				DISCOVER_ANNOTATED_CLASSES,
				METAMODEL_SOURCE_FOLDER_NAME,
				LEGACY_DISCOVER_ANNOTATED_CLASSES,
				LEGACY_METAMODEL_SOURCE_FOLDER_NAME);
	}
	
	/**
	 * Clears the specified legacy Dali (org.eclipse.jpt.core) preferences
	 */
	public void clearLegacyProjectPreferences(String ... preferenceKeys) {
		IEclipsePreferences projectPrefs = this.getLegacyProjectPreferences();
		for(String preferenceKey : preferenceKeys) {
			projectPrefs.remove(preferenceKey);
		}
		flush(projectPrefs);
	}

	// ********** project preference **********

	protected String getLegacyProjectPreference(String key, String defaultValue) {
		String value = this.getLegacyProjectPreferences().get(key, defaultValue);
		return (StringTools.stringIsEmpty(value)) ? defaultValue : value;
	}
	
	protected String getLegacyProjectPreference(String key) {
		return this.getLegacyProjectPreferences().get(key, null);
	}
	
	protected void setLegacyProjectPreference(String key, String value) {
		IEclipsePreferences projectPrefs = this.getLegacyProjectPreferences();
		if(value == null) {
			projectPrefs.remove(key);
		}
		else {
			projectPrefs.put(key, value);
		}
		flush(projectPrefs);
	}

	protected boolean getLegacyProjectPreference(String key, boolean defaultBooleanValue) {
		return this.getLegacyProjectPreferences().getBoolean(key, defaultBooleanValue);
	}

	protected void setLegacyProjectPreference(String key, boolean booleanValue) {
		IEclipsePreferences projectPrefs = this.getLegacyProjectPreferences();
		projectPrefs.putBoolean(key, booleanValue);
		flush(projectPrefs);
	}

	protected void removeLegacyProjectPreference(String key) {
		IEclipsePreferences projectPrefs = this.getLegacyProjectPreferences();
		projectPrefs.remove(key);
		flush(projectPrefs);
	}

	// ********** getters/setters *********

	/**
	 * Return the JPA platform ID associated with the specified Eclipse project.
	 */
	public String getJpaPlatformId() {
		return this.getLegacyProjectPreference(JPA_PLATFORM_PREF_KEY, GenericPlatform.VERSION_1_0.getId());
	}

	/**
	 * Set the JPA platform ID associated with the specified Eclipse project.
	 */
	public void setJpaPlatformId(String jpaPlatformId) {
		this.setLegacyProjectPreference(JPA_PLATFORM_PREF_KEY, jpaPlatformId);
	}

	/**
	 * Return the JPA "discover" flag associated with the specified
	 * Eclipse project.
	 */
	public boolean getDiscoverAnnotatedClasses() {
		if (this.getLegacyProjectPreference(DISCOVER_ANNOTATED_CLASSES, null) != null) {
			return this.getLegacyProjectPreference(DISCOVER_ANNOTATED_CLASSES, false);
		}
		//bug 354780 - made the mistake of changing the project metadata in the 3.0 release
		return this.getLegacyProjectPreference(LEGACY_DISCOVER_ANNOTATED_CLASSES, false);
	}
	
	 /** 
	  * Set the JPA "discover" flag associated with the specified
	 * Eclipse project.
	 */
	public void setDiscoverAnnotatedClasses(boolean discoverAnnotatedClasses) {
		this.setLegacyProjectPreference(DISCOVER_ANNOTATED_CLASSES, discoverAnnotatedClasses);
	}

	/**
	 * Return the name of the metamodel source folder associated with the
	 * specified Eclipse project.
	 */
	public String getMetamodelSourceFolderName() {
		String metamodelSourceFolderName = this.getLegacyProjectPreference(METAMODEL_SOURCE_FOLDER_NAME, null);
		if (metamodelSourceFolderName != null) {
			return metamodelSourceFolderName;
		}
		//bug 354780 - made the mistake of changing the project metadata in the 3.0 release
		return this.getLegacyProjectPreference(LEGACY_METAMODEL_SOURCE_FOLDER_NAME, null);
	}

	/**
	 * Set the name of the metamodel source folder associated with the
	 * specified Eclipse project.
	 */
	public void setMetamodelSourceFolderName(String metamodelSourceFolderName) {
		this.setLegacyProjectPreference(METAMODEL_SOURCE_FOLDER_NAME, metamodelSourceFolderName);
		//bug 354780 - made the mistake of changing the project metadata in the 3.0 release.
		//make sure legacy setting is removed when turning off metamodel gen, if we don't then
		//there will be no way to turn off meatamodel gen without sacrificing backwards compatibility
		if(metamodelSourceFolderName == null) {
			this.setLegacyProjectPreference(LEGACY_METAMODEL_SOURCE_FOLDER_NAME, null);
		}
	}

	// ********** protected methods **********
	
	protected IProject getProject() {
		return this.project;
	}
}
