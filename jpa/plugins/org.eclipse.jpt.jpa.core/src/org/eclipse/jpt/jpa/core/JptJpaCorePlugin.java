/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import java.util.Hashtable;

import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.internal.InternalJpaProjectManager;
import org.eclipse.jpt.jpa.core.internal.platform.JpaPlatformManagerImpl;
import org.eclipse.jpt.jpa.core.internal.prefs.JpaPreferencesManager;
import org.eclipse.jpt.jpa.core.platform.GenericPlatform;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.osgi.framework.BundleContext;
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
// TODO bjv move to internal package and move all public stuff to adapters etc.
public class JptJpaCorePlugin
	extends Plugin
{
	/**
	 * Flag necessary to handle lazy-initialization appropriately.
	 */
	private volatile boolean active = false;
	private final Hashtable<IWorkspace, InternalJpaProjectManager> jpaProjectManagers = new Hashtable<IWorkspace, InternalJpaProjectManager>();
	private volatile ServiceTracker<?, SAXParserFactory> parserTracker;

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

	static JptJpaCorePlugin INSTANCE;

	// ********** public static methods **********

	/**
	 * Return the JPA project manager corresponding to the specified workspace.
	 * <p>
	 * The preferred way to retrieve the JPA project manager is via the Eclipse
	 * adapter framework:
	 * <pre>
	 * JpaProjectManager manager = (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class)
	 * </pre>
	 * @deprecated use <code>workspace.getAdapter(JpaProjectManager.class)</code>
	 */
	@Deprecated
	public static JpaProjectManager getJpaProjectManager(IWorkspace workspace) {
		return INSTANCE.getJpaProjectManager_(workspace);
	}

	public static SAXParserFactory getSAXParserFactory() {
		return INSTANCE.getSAXParserFactory_();
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
	
	/**
	 * Return the default {@link JpaPlatformDescription} for new JPA projects with the given JPA facet version.
	 */
	public static JpaPlatformDescription getDefaultJpaPlatform(IProjectFacetVersion jpaFacetVersion) {
		JpaPlatformDescription defaultPlatform = 	getDefaultJpaPlatform(
							jpaFacetVersion, 
							JpaPreferencesManager.getWorkspacePreferences(), 
							JpaPreferencesManager.getDefaultPreferences());
		if (defaultPlatform == null) {
			// if the platform ID stored in the workspace prefs is invalid (i.e. null), look in the default prefs
			defaultPlatform = getDefaultJpaPlatform(
						jpaFacetVersion, 
						JpaPreferencesManager.getDefaultPreferences());
		}
		return defaultPlatform;
	}
	
	private static JpaPlatformDescription getDefaultJpaPlatform(IProjectFacetVersion jpaFacetVersion, Preferences ... nodes) {
		JpaPlatformDescription defaultDefaultPlatform = getDefaultJpaPlatform(
							jpaFacetVersion, 
							JpaPreferencesManager.DEFAULT_JPA_PLATFORM_PREF_KEY, 
							null, 
							nodes);
		String preferenceKey = null;
		if (jpaFacetVersion.equals(JpaFacet.VERSION_1_0)) {
			if (defaultDefaultPlatform == null) {
				defaultDefaultPlatform = GenericPlatform.VERSION_1_0;
			}
			preferenceKey = JpaPreferencesManager.DEFAULT_JPA_PLATFORM_1_0_PREF_KEY; 
		}
		else if (jpaFacetVersion.equals(JpaFacet.VERSION_2_0)) {
			if (defaultDefaultPlatform == null) {
				defaultDefaultPlatform = GenericPlatform.VERSION_2_0;
			}
			preferenceKey = JpaPreferencesManager.DEFAULT_JPA_PLATFORM_2_0_PREF_KEY;
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
			preferenceKey = JpaPreferencesManager.DEFAULT_JPA_PLATFORM_1_0_PREF_KEY;
		}
		else if (JpaFacet.VERSION_2_0.getVersionString().equals(jpaFacetVersion)) {
			preferenceKey = JpaPreferencesManager.DEFAULT_JPA_PLATFORM_2_0_PREF_KEY;
		}
		else {
			throw new IllegalArgumentException("Illegal JPA facet version: " + jpaFacetVersion); //$NON-NLS-1$
		}
		JpaPreferencesManager.setWorkspacePreference(preferenceKey, platformId);
	}
	
	/**
	 * Return the JPA platform ID associated with the specified Eclipse project.
	 */
	public static String getJpaPlatformId(IProject project) {
		return (new JpaPreferencesManager(project)).getJpaPlatformId();
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
		(new JpaPreferencesManager(project)).setJpaPlatformId(jpaPlatformId);
	}

	/**
	 * Return the JPA "discover" flag associated with the specified
	 * Eclipse project.
	 */
	public static boolean getDiscoverAnnotatedClasses(IProject project) {
		return (new JpaPreferencesManager(project)).getDiscoverAnnotatedClasses();
	}

	/**
	 * Set the JPA "discover" flag associated with the specified
	 * Eclipse project.
	 */
	public static void setDiscoverAnnotatedClasses(IProject project, boolean discoverAnnotatedClasses) {
		(new JpaPreferencesManager(project)).setDiscoverAnnotatedClasses(discoverAnnotatedClasses);
	}

	/**
	 * Return the name of the metamodel source folder associated with the
	 * specified Eclipse project.
	 */
	public static String getMetamodelSourceFolderName(IProject project) {
		return (new JpaPreferencesManager(project)).getMetamodelSourceFolderName();
	}

	/**
	 * Set the name of the metamodel source folder associated with the
	 * specified Eclipse project.
	 */
	public static void setMetamodelSourceFolderName(IProject project, String metamodelSourceFolderName) {
		(new JpaPreferencesManager(project)).setMetamodelSourceFolderName(metamodelSourceFolderName);
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

	public static boolean nodeIsXml2_0Compatible(XmlContextNode xmlContextNode) {
		return resourceTypeIsCompatible(xmlContextNode.getResourceType(), org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0.SCHEMA_VERSION);
	}

	public static boolean resourceTypeIsCompatible(JptResourceType resourceType, String version) {
		return JpaPlatform.Version.VERSION_COMPARATOR.compare(resourceType.getVersion(), version) >= 0;
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
		log(new Status(IStatus.ERROR, PLUGIN_ID, msg, throwable));
	}

	/**
	 * Log the specified status.
	 */
	public static void log(IStatus status) {
        INSTANCE.getLog().log(status);
    }

	// ********** plug-in implementation **************************************************

	public JptJpaCorePlugin() {
		super();
		if (INSTANCE != null) {
			throw new IllegalStateException();
		}
		// this convention is *wack*...  ~bjv
		INSTANCE = this;
	}


	@Override
	public synchronized void start(BundleContext context) throws Exception {
		super.start(context);
		this.active = true;
	}

	@Override
	public synchronized void stop(BundleContext context) throws Exception {
		try {
			for (InternalJpaProjectManager jpaProjectManager : this.jpaProjectManagers.values()) {
				jpaProjectManager.stop();
			}
			this.jpaProjectManagers.clear();

			if (this.parserTracker != null) {
				this.parserTracker.close();
				this.parserTracker = null;
			}
		} finally {
			this.active = false;
			super.stop(context);
		}
	}


	// ********** state **********

	/**
	 * @see #getJpaProjectManager(IWorkspace)
	 */
	private synchronized InternalJpaProjectManager getJpaProjectManager_(IWorkspace workspace) {
		InternalJpaProjectManager jpaProjectManager = this.jpaProjectManagers.get(workspace);
		if (this.active && (jpaProjectManager == null)) {
			jpaProjectManager = this.buildJpaProjectManager(workspace);
			jpaProjectManager.start();
			this.jpaProjectManagers.put(workspace, jpaProjectManager);
		}
		return jpaProjectManager;
	}

	private InternalJpaProjectManager buildJpaProjectManager(IWorkspace workspace) {
		return new InternalJpaProjectManager(workspace);
	}

	/**
	 * @see #getSAXParserFactory()
	 */
	private SAXParserFactory getSAXParserFactory_() {
		ServiceTracker<?, SAXParserFactory> tracker = this.getParserTracker();
		if (tracker == null) {
			return null;
		}
		SAXParserFactory factory = tracker.getService();
		if (factory != null) {
			factory.setNamespaceAware(true);
		}
		return factory;
	}

	private synchronized ServiceTracker<?, SAXParserFactory> getParserTracker() {
		if (this.active && (this.parserTracker == null)) {
			this.parserTracker = this.buildParserTracker();
			this.parserTracker.open();
		}
		return this.parserTracker;
	}

	private ServiceTracker<?, SAXParserFactory> buildParserTracker() {
		return new ServiceTracker<Object, SAXParserFactory>(this.getBundle().getBundleContext(), "javax.xml.parsers.SAXParserFactory", null); //$NON-NLS-1$
	}
}
