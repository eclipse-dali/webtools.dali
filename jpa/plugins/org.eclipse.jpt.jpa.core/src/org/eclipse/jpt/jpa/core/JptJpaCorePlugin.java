/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import javax.xml.parsers.SAXParserFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.internal.JptCoreMessages;
import org.eclipse.jpt.jpa.core.internal.platform.JpaPlatformManagerImpl;
import org.eclipse.jpt.jpa.core.internal.prefs.JpaPreferenceInitializer;
import org.eclipse.jpt.jpa.core.platform.GenericPlatform;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The Dali core plug-in lifecycle implementation.
 * A number of globally-available constants and methods.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public class JptJpaCorePlugin
	extends Plugin
{
	private volatile GenericJpaProjectManager jpaProjectManager;
	private volatile ServiceTracker<?, SAXParserFactory> parserTracker;
	private static volatile boolean flushPreferences = true;


	// ********** public constants **********

	/**
	 * The plug-in identifier of the persistence support
	 * (value <code>"org.eclipse.jpt.jpa.core"</code>).
	 */
	public static final String PLUGIN_ID = "org.eclipse.jpt.jpa.core";  //$NON-NLS-1$
	public static final String PLUGIN_ID_ = PLUGIN_ID + '.';

	/**
	 * The legacy plug-in identifier of the persistence support, used for project preferences
	 * (value <code>"org.eclipse.jpt.core"</code>).
	 */
	public static final String LEGACY_PLUGIN_ID = "org.eclipse.jpt.core";  //$NON-NLS-1$
	public static final String LEGACY_PLUGIN_ID_ = LEGACY_PLUGIN_ID + '.';

	/**
	 * The key for storing a JPA project's platform ID in the Eclipse
	 * project's preferences.
	 */
	private static final String JPA_PLATFORM_PREF_KEY = LEGACY_PLUGIN_ID_ + "platform";  //$NON-NLS-1$

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
	public static final String DISCOVER_ANNOTATED_CLASSES = PLUGIN_ID_ + "discoverAnnotatedClasses";  //$NON-NLS-1$

	/**
	 * The key for storing the name of a JPA project's metamodel source folder
	 * in the Eclipse project's preferences.
	 */
	public static final String METAMODEL_SOURCE_FOLDER_NAME = PLUGIN_ID_ + "metamodelSourceFolderName";  //$NON-NLS-1$

	/**
	 * The key for storing a JPA project's data source connection profile name
	 * in the Eclipse project's persistent properties.
	 */
	public static final QualifiedName DATA_SOURCE_CONNECTION_PROFILE_NAME = 
			new QualifiedName(PLUGIN_ID, "dataSource.connectionProfileName");  //$NON-NLS-1$

	/**
	 * The key for storing a JPA project's user overridden default catalog name
	 * in the Eclipse project's persistent properties.
	 */
	public static final QualifiedName USER_OVERRIDE_DEFAULT_CATALOG = 
		new QualifiedName(PLUGIN_ID, "userOverrideDefaultCatalogName");  //$NON-NLS-1$

	/**
	 * The key for storing a JPA project's user overridden default schema name
	 * in the Eclipse project's persistent properties.
	 */
	public static final QualifiedName USER_OVERRIDE_DEFAULT_SCHEMA = 
			new QualifiedName(PLUGIN_ID, "userOverrideDefaultSchemaName");  //$NON-NLS-1$


	/**
	 * The identifier for the JPA validation marker
	 * (value <code>"org.eclipse.jpt.jpa.core.jpaProblemMarker"</code>).
	 */
	public static final String VALIDATION_MARKER_ID = PLUGIN_ID + ".jpaProblemMarker";  //$NON-NLS-1$

	/**
	 * The identifier for the JPA validator
	 * (value <code>"org.eclipse.jpt.jpa.core.jpaValidator"</code>).
	 */
	public static final String VALIDATOR_ID = PLUGIN_ID_ + "jpaValidator";  //$NON-NLS-1$

	/**
	 * The content type for <code>persistence.xml</code> files.
	 */
	public static final IContentType PERSISTENCE_XML_CONTENT_TYPE = getJpaContentType("persistence"); //$NON-NLS-1$
	
	/**
	 * The resource type for <code>persistence.xml</code> version 1.0 files
	 */
	public static final JptResourceType PERSISTENCE_XML_1_0_RESOURCE_TYPE = 
			new JptResourceType(PERSISTENCE_XML_CONTENT_TYPE, org.eclipse.jpt.jpa.core.resource.persistence.JPA.SCHEMA_VERSION);
	
	/**
	 * The resource type for <code>persistence.xml</code> version 2.0 files
	 */
	public static final JptResourceType PERSISTENCE_XML_2_0_RESOURCE_TYPE = 
			new JptResourceType(PERSISTENCE_XML_CONTENT_TYPE, org.eclipse.jpt.jpa.core.resource.persistence.v2_0.JPA2_0.SCHEMA_VERSION);
	
	/**
	 * The base content type for all mapping files.
	 */
	public static final IContentType MAPPING_FILE_CONTENT_TYPE = getJpaContentType("mappingFile"); //$NON-NLS-1$

	/**
	 * The content type for <code>orm.xml</code> mapping files.
	 */
	public static final IContentType ORM_XML_CONTENT_TYPE = getJpaContentType("orm"); //$NON-NLS-1$
	
	/**
	 * The resource type for <code>orm.xml</code> version 1.0 mapping files
	 */
	public static final JptResourceType ORM_XML_1_0_RESOURCE_TYPE = 
			new JptResourceType(ORM_XML_CONTENT_TYPE, org.eclipse.jpt.jpa.core.resource.orm.JPA.SCHEMA_VERSION);
	
	/**
	 * The resource type for <code>orm.xml</code> version 2.0 mapping files
	 */
	public static final JptResourceType ORM_XML_2_0_RESOURCE_TYPE = 
			new JptResourceType(ORM_XML_CONTENT_TYPE, org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0.SCHEMA_VERSION);
	
	/**
	 * Web projects have some special exceptions.
	 */
	public static final IProjectFacet WEB_FACET
			= ProjectFacetsManager.getProjectFacet(IModuleConstants.JST_WEB_MODULE);

	public static final IPath DEFAULT_PERSISTENCE_XML_RUNTIME_PATH = new Path("META-INF/persistence.xml"); //$NON-NLS-1$

	public static final IPath DEFAULT_ORM_XML_RUNTIME_PATH = new Path("META-INF/orm.xml"); //$NON-NLS-1$
	
	private static IContentType getJpaContentType(String jpaContentType) {
		return getContentType(CONTENT_PREFIX_ + jpaContentType);
	}
	
	public static final String CONTENT_PREFIX = PLUGIN_ID_ + "content"; //$NON-NLS-1$
	
	public static final String CONTENT_PREFIX_ = CONTENT_PREFIX + '.';
	
	private static IContentType getContentType(String contentType) {
		return Platform.getContentTypeManager().getContentType(contentType);
	}
	
	
	// ********** singleton **********

	private static JptJpaCorePlugin INSTANCE;

	/**
	 * Return the singleton Dali core plug-in.
	 */
	public static JptJpaCorePlugin instance() {
		return INSTANCE;
	}


	// ********** public static methods **********

	/**
	 * Return the singular JPA project manager corresponding
	 * to the current workspace.
	 */
	public static JpaProjectManager getJpaProjectManager() {
		return INSTANCE.getJpaProjectManager_();
	}

	/**
	 * Return the JPA project corresponding to the specified Eclipse project,
	 * or <code>null</code> if unable to associate the specified project with a
	 * JPA project.
	 */
	public static JpaProject getJpaProject(IProject project) {
		return getJpaProjectManager().getJpaProject(project);
	}

	/**
	 * Return the JPA file corresponding to the specified Eclipse file,
	 * or <code>null</code> if unable to associate the specified file with a JPA file.
	 */
	public static JpaFile getJpaFile(IFile file) {
		return getJpaProjectManager().getJpaFile(file);
	}

	/**
	 * The JPA settings associated with the specified Eclipse project
	 * have changed in such a way as to require the associated
	 * JPA project to be completely rebuilt
	 * (e.g. when the user changes a project's JPA platform).
	 */
	public static void rebuildJpaProject(IProject project) {
		getJpaProjectManager().rebuildJpaProject(project);
	}

	/**
	 * Return whether the specified Eclipse project has a Web facet.
	 */
	public static boolean projectHasWebFacet(IProject project) {
		return projectHasFacet(project, WEB_FACET);
	}

	/**
	 * Checked exceptions bite.
	 */
	private static boolean projectHasFacet(IProject project, IProjectFacet facet) {
		try {
			return FacetedProjectFramework.hasProjectFacet(project, facet.getId());
		} catch (CoreException ex) {
			log(ex);  // problems reading the project metadata - assume facet doesn't exist - return 'false'
			return false;
		}
	}
	
	public static JpaFile getJpaFile(IProject project, IPath runtimePath) {
		IFile xmlFile = JptCommonCorePlugin.getPlatformFile(project, runtimePath);
		return xmlFile.exists() ? getJpaFile(xmlFile) : null;
	}
	
	/**
	 * Return the runtime path to which JARs are relatively specified for 
	 * the given project.
	 * (Web projects have a different runtime structure than non-web projects.)
	 */
	public static IPath getJarRuntimeRootPath(IProject project) {
		return projectHasWebFacet(project) ? 
				new Path("/" + J2EEConstants.WEB_INF)  //$NON-NLS-1$
				: new Path("/");  //$NON-NLS-1$
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
	
	/**
	 * Return the default Dali preferences
	 * @see JpaPreferenceInitializer
	 */
	public static IEclipsePreferences getDefaultPreferences() {
		return getPreferences(new DefaultScope());
	}

	/**
	 * Return the Dali preferences for the current workspace instance.
	 */
	public static IEclipsePreferences getWorkspacePreferences() {
		return getPreferences(new InstanceScope());
	}
	
	/**
	 * Set the workspace preference.
	 */
	public static void setWorkspacePreference(String preferenceKey, String preferenceValue) {
		IEclipsePreferences prefs = getWorkspacePreferences();
		prefs.put(preferenceKey, preferenceValue);
		flush(prefs);
	}

	/**
	 * Return the Dali preferences for the specified Eclipse project.
	 */
	public static IEclipsePreferences getProjectPreferences(IProject project) {
		return getPreferences(new ProjectScope(project));
	}
	
	/**
	 * Set the project preference
	 */
	public static void setProjectPreference(IProject project, String preferenceKey, String preferenceValue) {
		IEclipsePreferences prefs = getProjectPreferences(project);
		if (preferenceValue == null) {
			prefs.remove(preferenceKey);
		}
		else {
			prefs.put(preferenceKey, preferenceValue);
		}
		flush(prefs);
	}
	
	/**
	 * Set the project preference
	 */
	public static void setProjectPreference(IProject project, String preferenceKey, boolean preferenceValue) {
		IEclipsePreferences prefs = getProjectPreferences(project);
		prefs.putBoolean(preferenceKey, preferenceValue);
		flush(prefs);
	}
	
	/**
	 * Clears the project of JPA-specific preferences
	 */
	public static void clearProjectPreferences(IProject project) {
		clearProjectPreferences(
				project, 
				JPA_PLATFORM_PREF_KEY, DISCOVER_ANNOTATED_CLASSES, METAMODEL_SOURCE_FOLDER_NAME);
	}
	
	/**
	 * Clears the specified preferences
	 */
	public static void clearProjectPreferences(IProject project, String ... preferenceKeys) {
		IEclipsePreferences prefs = getProjectPreferences(project);
		for (String preferenceKey : preferenceKeys) {
			prefs.remove(preferenceKey);
		}
		flush(prefs);
	}
	
	/**
	 * Return the Dali preferences for the specified context.
	 */
	private static IEclipsePreferences getPreferences(IScopeContext context) {
		return context.getNode(LEGACY_PLUGIN_ID);
	}
	
	/**
	 * Return the default {@link JpaPlatformDescription} for new JPA projects with the given JPA facet version.
	 */
	public static JpaPlatformDescription getDefaultJpaPlatform(IProjectFacetVersion jpaFacetVersion) {
		JpaPlatformDescription defaultPlatform = 
				getDefaultJpaPlatform(jpaFacetVersion, getWorkspacePreferences(), getDefaultPreferences());
		if (defaultPlatform == null) {
			// if the platform ID stored in the workspace prefs is invalid (i.e. null), look in the default prefs
			defaultPlatform = getDefaultJpaPlatform(jpaFacetVersion, getDefaultPreferences());
		}
		return defaultPlatform;
	}
	
	private static JpaPlatformDescription getDefaultJpaPlatform(IProjectFacetVersion jpaFacetVersion, Preferences ... nodes) {
		JpaPlatformDescription defaultDefaultPlatform = 
				getDefaultJpaPlatform(jpaFacetVersion, DEFAULT_JPA_PLATFORM_PREF_KEY, null, nodes);
		String preferenceKey = null;
		if (jpaFacetVersion.equals(JpaFacet.VERSION_1_0)) {
			if (defaultDefaultPlatform == null) {
				defaultDefaultPlatform = GenericPlatform.VERSION_1_0;
			}
			preferenceKey = DEFAULT_JPA_PLATFORM_1_0_PREF_KEY; 
		}
		else if (jpaFacetVersion.equals(JpaFacet.VERSION_2_0)) {
			if (defaultDefaultPlatform == null) {
				defaultDefaultPlatform = GenericPlatform.VERSION_2_0;
			}
			preferenceKey = DEFAULT_JPA_PLATFORM_2_0_PREF_KEY;
		}
		else {
			throw new IllegalArgumentException("Illegal JPA facet version: " + jpaFacetVersion); //$NON-NLS-1$
		}
		return getDefaultJpaPlatform(jpaFacetVersion, preferenceKey, defaultDefaultPlatform, nodes);
	}
	
	private static JpaPlatformDescription getDefaultJpaPlatform(
			IProjectFacetVersion jpaFacetVersion, String preferenceKey, JpaPlatformDescription defaultDefault, Preferences ... nodes) {	
		
		String defaultDefaultId = (defaultDefault == null) ? null : defaultDefault.getId();
		String defaultPlatformId = Platform.getPreferencesService().get(preferenceKey, defaultDefaultId, nodes);
		JpaPlatformDescription defaultPlatform = getJpaPlatformManager().getJpaPlatform(defaultPlatformId);
		if (defaultPlatform != null && defaultPlatform.supportsJpaFacetVersion(jpaFacetVersion)) {
			return defaultPlatform;
		}
		else if (defaultDefault != null && defaultDefault.supportsJpaFacetVersion(jpaFacetVersion)) {
			return defaultDefault;
		}
		return null;
	}
	
	public static JpaPlatformManager getJpaPlatformManager() {
		return JpaPlatformManagerImpl.instance();
	}

	/**
	 * Set the default JPA platform ID for creating new JPA projects
	 */
	public static void setDefaultJpaPlatformId(String jpaFacetVersion, String platformId) {
		String preferenceKey = null;
		if (JpaFacet.VERSION_1_0.getVersionString().equals(jpaFacetVersion)) {
			preferenceKey = DEFAULT_JPA_PLATFORM_1_0_PREF_KEY;
		}
		else if (JpaFacet.VERSION_2_0.getVersionString().equals(jpaFacetVersion)) {
			preferenceKey = DEFAULT_JPA_PLATFORM_2_0_PREF_KEY;
		}
		else {
			throw new IllegalArgumentException("Illegal JPA facet version: " + jpaFacetVersion); //$NON-NLS-1$
		}
		setWorkspacePreference(preferenceKey, platformId);
	}
	
	/**
	 * Return the JPA platform ID associated with the specified Eclipse project.
	 */
	public static String getJpaPlatformId(IProject project) {
		return getProjectPreferences(project).get(JPA_PLATFORM_PREF_KEY, GenericPlatform.VERSION_1_0.getId());
	}
	
	/**
	 * Return the JPA platform description associated with the specified Eclipse project.
	 */
	public static JpaPlatformDescription getJpaPlatformDescription(IProject project) {
		String jpaPlatformId = getJpaPlatformId(project);
		return getJpaPlatformManager().getJpaPlatform(jpaPlatformId);
	}

	/**
	 * Set the JPA platform ID associated with the specified Eclipse project.
	 */
	public static void setJpaPlatformId(IProject project, String jpaPlatformId) {
		setProjectPreference(project, JPA_PLATFORM_PREF_KEY, jpaPlatformId);
	}

	/**
	 * Return the preferences key used to look up an Eclipse project's
	 * JPA platform ID.
	 */
	public static String getJpaPlatformIdPrefKey() {
		return JPA_PLATFORM_PREF_KEY;
	}

	/**
	 * Return the JPA "discover" flag associated with the specified
	 * Eclipse project.
	 */
	public static boolean discoverAnnotatedClasses(IProject project) {
		return getProjectPreferences(project).getBoolean(DISCOVER_ANNOTATED_CLASSES, false);
	}

	/**
	 * Set the JPA "discover" flag associated with the specified
	 * Eclipse project.
	 */
	public static void setDiscoverAnnotatedClasses(IProject project, boolean discoverAnnotatedClasses) {
		setProjectPreference(project, DISCOVER_ANNOTATED_CLASSES, discoverAnnotatedClasses);
	}

	/**
	 * Return the name of the metamodel source folder associated with the
	 * specified Eclipse project.
	 */
	public static String getMetamodelSourceFolderName(IProject project) {
		return getProjectPreferences(project).get(METAMODEL_SOURCE_FOLDER_NAME, null);
	}

	/**
	 * Set the name of the metamodel source folder associated with the
	 * specified Eclipse project.
	 */
	public static void setMetamodelSourceFolderName(IProject project, String metamodelSourceFolderName) {
		setProjectPreference(project, METAMODEL_SOURCE_FOLDER_NAME, metamodelSourceFolderName);
	}

	/**
	 * This method is called (via reflection) when the test plug-in is loaded.
	 * The preferences end up getting flushed after the test case has deleted
	 * its project, resulting in resource exceptions in the log, e.g.
	 * <pre>
	 *     Resource '/JpaProjectManagerTests' is not open.
	 * </pre>
	 * See <code>JptCoreTestsPlugin.start(BundleContext)</code>
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
			} catch(BackingStoreException ex) {
				log(ex);
			}
			return Status.OK_STATUS;
		}
	}

	/**
	 * Return the name of the connection profile associated with the specified
	 * Eclipse project.
	 */
	public static String getConnectionProfileName(IProject project) {
		try {
			String connectionProfileName = project.getPersistentProperty(DATA_SOURCE_CONNECTION_PROFILE_NAME);
			// some old connection profile names were stored as empty strings instead of nulls :-(
			// convert them here
			return (StringTools.stringIsEmpty(connectionProfileName)) ? null : connectionProfileName;
		} catch (CoreException ex) {
			log(ex);
			return null;
		}
	}

	/**
	 * Set the name of the connection profile associated with the specified
	 * Eclipse project.
	 */
	public static void setConnectionProfileName(IProject project, String connectionProfileName) {
		try {
			project.setPersistentProperty(DATA_SOURCE_CONNECTION_PROFILE_NAME, connectionProfileName);
		} catch (CoreException ex) {
			log(ex);
		}
	}

	/**
	 * Return the default catalog (identifier) associated with the specified Eclipse project.
	 * @see JpaProject#getUserOverrideDefaultCatalog()
	 */
	public static String getUserOverrideDefaultCatalog(IProject project) {
		try {
			return project.getPersistentProperty(USER_OVERRIDE_DEFAULT_CATALOG);
		} catch (CoreException ex) {
			log(ex);
			return null;
		}
	}

	/**
	 * Set the default catalog (identifier) associated with the specified Eclipse project.
	 * @see JpaProject#setUserOverrideDefaultCatalog(String)
	 */
	public static void setUserOverrideDefaultCatalog(IProject project, String defaultCatalog) {
		try {
			project.setPersistentProperty(USER_OVERRIDE_DEFAULT_CATALOG, defaultCatalog);
		} catch (CoreException ex) {
			log(ex);
		}
	}

	/**
	 * Return the default schema (identifier) associated with the specified Eclipse project.
	 * @see JpaProject#getUserOverrideDefaultSchema()
	 */
	public static String getUserOverrideDefaultSchema(IProject project) {
		try {
			return project.getPersistentProperty(USER_OVERRIDE_DEFAULT_SCHEMA);
		} catch (CoreException ex) {
			log(ex);
			return null;
		}
	}

	/**
	 * Set the default schema (identifier) associated with the specified Eclipse project.
	 * @see JpaProject#setUserOverrideDefaultSchema(String)
	 */
	public static void setUserOverrideDefaultSchema(IProject project, String defaultSchema) {
		try {
			project.setPersistentProperty(USER_OVERRIDE_DEFAULT_SCHEMA, defaultSchema);
		} catch (CoreException ex) {
			log(ex);
		}
	}
	
	/**
	 * Clear the JPA-specific project properties
	 */
	public static void clearProjectPersistentProperties(IProject project) {
		try {
			project.setPersistentProperty(DATA_SOURCE_CONNECTION_PROFILE_NAME, null);
			project.setPersistentProperty(USER_OVERRIDE_DEFAULT_CATALOG, null);
			project.setPersistentProperty(USER_OVERRIDE_DEFAULT_SCHEMA, null);
		}
		catch (CoreException ce) {
			log(ce);
		}
	}

	public static boolean nodeIsJpa2_0Compatible(JpaNode jpaNode) {
		return jpaNode.getJpaProject().getJpaPlatform().getJpaVersion().isCompatibleWithJpaVersion(JpaFacet.VERSION_2_0.getVersionString());
	}

	/**
	 * Return whether the JPA project manager's Java change listener is active.
	 */
	public static boolean javaElementChangeListenerIsActive() {
		return getJpaProjectManager().javaElementChangeListenerIsActive();
	}

	/**
	 * Set whether the JPA project manager's Java change listener is active.
	 */
	public static void setJavaElementChangeListenerIsActive(boolean javaElementChangeListenerIsActive) {
		getJpaProjectManager().setJavaElementChangeListenerIsActive(javaElementChangeListenerIsActive);
	}

	/**
	 * Log the specified message.
	 */
	public static void log(String msg) {
        log(msg, null);
    }

	/**
	 * Log the specified exception or error.
	 */
	public static void log(Throwable throwable) {
		log(throwable.getLocalizedMessage(), throwable);
	}

	/**
	 * Log the specified message and exception or error.
	 */
	public static void log(String msg, Throwable throwable) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, throwable));
	}

	/**
	 * Log the specified status.
	 */
	public static void log(IStatus status) {
        INSTANCE.getLog().log(status);
    }


	// ********** plug-in implementation **********

	public JptJpaCorePlugin() {
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
			if (this.jpaProjectManager != null) {
				this.jpaProjectManager.stop();
				this.jpaProjectManager = null;
			}
			if (this.parserTracker != null) {
				this.parserTracker.close();
				this.parserTracker = null;
			}
		} finally {
			super.stop(context);
		}
	}

	private synchronized GenericJpaProjectManager getJpaProjectManager_() {
		if (this.jpaProjectManager == null) {
			this.jpaProjectManager = this.buildJpaProjectManager();
			this.jpaProjectManager.start();
		}
		return this.jpaProjectManager;
	}

	private GenericJpaProjectManager buildJpaProjectManager() {
		return new GenericJpaProjectManager();
	}

	public synchronized SAXParserFactory getSAXParserFactory() {
		SAXParserFactory factory = this.getParserTracker().getService();
		if (factory != null) {
			factory.setNamespaceAware(true);
		}
		return factory;
	}

	private ServiceTracker<?, SAXParserFactory> getParserTracker() {
		if (this.parserTracker == null) {
			this.parserTracker = this.buildParserTracker();
			this.parserTracker.open();
		}
		return this.parserTracker;
	}

	private ServiceTracker<?, SAXParserFactory> buildParserTracker() {
		return new ServiceTracker<Object, SAXParserFactory>(this.getBundle().getBundleContext(), "javax.xml.parsers.SAXParserFactory", null); //$NON-NLS-1$
	}

}
