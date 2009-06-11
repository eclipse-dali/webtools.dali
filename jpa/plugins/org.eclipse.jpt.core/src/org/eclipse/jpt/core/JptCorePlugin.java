/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.JpaModelManager;
import org.eclipse.jpt.core.internal.platform.GenericJpaPlatformProvider;
import org.eclipse.jpt.core.internal.platform.JpaPlatformRegistry;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceConstants;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceInitializer;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * The JPT plug-in lifecycle implementation.
 * A number of globally-available constants and methods.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
// TODO keep preferences in synch with the JPA project
// (connection profile name, "discover" flag)
// use listeners?
public class JptCorePlugin extends Plugin {


	// ********** public constants **********

	/**
	 * The plug-in identifier of the persistence support
	 * (value <code>"org.eclipse.jpt.core"</code>).
	 */
	public static final String PLUGIN_ID = "org.eclipse.jpt.core";  //$NON-NLS-1$
	public static final String PLUGIN_ID_ = PLUGIN_ID + '.';

	/**
	 * The identifier for the JPA facet
	 * (value <code>"jpt.jpa"</code>).
	 */
	public static final String FACET_ID = "jpt.jpa";  //$NON-NLS-1$

	/**
	 * The key for storing a JPA project's platform in the Eclipse
	 * project's preferences.
	 */
	public static final String JPA_PLATFORM = PLUGIN_ID_ + "platform";  //$NON-NLS-1$
	
	/**
	 * The key for storing a JPA project's "discover" flag in the Eclipse
	 * project's preferences.
	 */
	public static final String DISCOVER_ANNOTATED_CLASSES = PLUGIN_ID_ + "discoverAnnotatedClasses";  //$NON-NLS-1$

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
	public static final QualifiedName USER_OVERRIDE_DEFAULT_CATALOG_NAME = 
		new QualifiedName(PLUGIN_ID, "userOverrideDefaultCatalogName");  //$NON-NLS-1$

	/**
	 * The key for storing a JPA project's user overridden default schema name
	 * in the Eclipse project's persistent properties.
	 */
	public static final QualifiedName USER_OVERRIDE_DEFAULT_SCHEMA_NAME = 
			new QualifiedName(PLUGIN_ID, "userOverrideDefaultSchemaName");  //$NON-NLS-1$
	
	/**
	 * The identifier for the JPA validator
	 * (value <code>"org.eclipse.jpt.core.jpaValidator"</code>).
	 */
	public static final String VALIDATOR_ID = PLUGIN_ID_ + "jpaValidator";  //$NON-NLS-1$

	/**
	 * The content type for Java source code files.
	 */
	public static final IContentType JAVA_SOURCE_CONTENT_TYPE = getContentType(JavaCore.JAVA_SOURCE_CONTENT_TYPE);

	public static final IContentType MAPPING_FILE_CONTENT_TYPE = getJpaContentType("mappingFile"); //$NON-NLS-1$

	/**
	 * The content type for orm.xml mappings files. Use this to add new 
	 * orm.xml-like extensions to this content type.
	 */
	public static final IContentType ORM_XML_CONTENT_TYPE = getJpaContentType("orm"); //$NON-NLS-1$

	/**
	 * The content type for 2.0 orm.xml mappings files. Use this to add new 
	 * orm.xml-like extensions to this content type.
	 */
	public static final IContentType ORM2_0_XML_CONTENT_TYPE = getJpaContentType("orm2_0"); //$NON-NLS-1$

	/**
	 * Ditto for persistence.xml.
	 * @see #ORM_XML_CONTENT_TYPE
	 */
	public static final IContentType PERSISTENCE_XML_CONTENT_TYPE = getJpaContentType("persistence"); //$NON-NLS-1$

	public static final IContentType PERSISTENCE2_0_XML_CONTENT_TYPE = getJpaContentType("persistence2_0"); //$NON-NLS-1$

	/**
	 * The content type for Java archives (JARs).
	 */
	public static final IContentType JAR_CONTENT_TYPE = getJpaContentType("jar"); //$NON-NLS-1$

	/**
	 * Web projects have some special exceptions.
	 */
	public static final String WEB_PROJECT_FACET_ID = IModuleConstants.JST_WEB_MODULE;

	/**
	 * Web projects have some special exceptions.
	 */
	public static final String WEB_PROJECT_DEPLOY_PREFIX = J2EEConstants.WEB_INF_CLASSES;

	public static final String DEFAULT_PERSISTENCE_XML_FILE_PATH = "META-INF/persistence.xml"; //$NON-NLS-1$

	public static final String DEFAULT_ORM_XML_FILE_PATH = "META-INF/orm.xml"; //$NON-NLS-1$

	private static IContentType getJpaContentType(String jpaContentType) {
		return getContentType(CONTENT_PREFIX_ + jpaContentType);
	}
	public static final String CONTENT_PREFIX = PLUGIN_ID_ + "content"; //$NON-NLS-1$
	public static final String CONTENT_PREFIX_ = CONTENT_PREFIX + '.';

	private static IContentType getContentType(String contentType) {
		return Platform.getContentTypeManager().getContentType(contentType);
	}

	// ********** singleton **********

	private static JptCorePlugin INSTANCE;

	/**
	 * Return the singleton JPT plug-in.
	 */
	public static JptCorePlugin instance() {
		return INSTANCE;
	}


	// ********** public static methods **********

	/**
	 * Return the singular JPA model corresponding to the current workspace.
	 */
	public static JpaModel getJpaModel() {
		return JpaModelManager.instance().getJpaModel();
	}

	/**
	 * Return the JPA project corresponding to the specified Eclipse project,
	 * or null if unable to associate the specified project with a
	 * JPA project.
	 */
	public static JpaProject getJpaProject(IProject project) {
		try {
			return JpaModelManager.instance().getJpaProject(project);
		} catch (CoreException ex) {
			log(ex);
			return null;
		}
	}

	/**
	 * Return the JPA file corresponding to the specified Eclipse file,
	 * or null if unable to associate the specified file with a JPA file.
	 */
	public static JpaFile getJpaFile(IFile file) {
		try {
			return JpaModelManager.instance().getJpaFile(file);
		} catch (CoreException ex) {
			log(ex);
			return null;
		}
	}

	/**
	 * Return whether the specified Eclipse project has a JPA facet.
	 */
	public static boolean projectHasJpaFacet(IProject project) {
		return projectHasFacet(project, FACET_ID);
	}

	/**
	 * Return whether the specified Eclipse project has a JPA facet.
	 */
	public static boolean projectHasWebFacet(IProject project) {
		return projectHasFacet(project, WEB_PROJECT_FACET_ID);
	}

	/**
	 * Checked exceptions bite.
	 */
	private static boolean projectHasFacet(IProject project, String facetId) {
		try {
			return FacetedProjectFramework.hasProjectFacet(project, facetId);
		} catch (CoreException ex) {
			log(ex);  // problems reading the project metadata - assume facet doesn't exist - return 'false'
			return false;
		}
	}

	/**
	 * Return the persistence.xml (specified as "META-INF/persistence.xml")
	 * deployment URI for the specified project.
	 */
	public static String getPersistenceXmlDeploymentURI(IProject project) {
		return getDeploymentURI(project, DEFAULT_PERSISTENCE_XML_FILE_PATH);
	}

	/**
	 * Return the default mapping file (specified as "META-INF/orm.xml")
	 * deployment URI for the specified project.
	 */
	public static String getDefaultOrmXmlDeploymentURI(IProject project) {
		return getDeploymentURI(project, DEFAULT_ORM_XML_FILE_PATH);
	}
	
	/**
	 * Return the mapping file (specified as "META-INF/<mappingFileName>")
	 * deployment URI for the specified project.
	 */
	public static String getOrmXmlDeploymentURI(IProject project, String mappingFileName) {
		return getDeploymentURI(project, mappingFileName);
	}
	
	/**
	 * Tweak the specified deployment URI if the specified project
	 * has a web facet.
	 */
	public static String getDeploymentURI(IProject project, String defaultURI) {
		return projectHasWebFacet(project) ?
				WEB_PROJECT_DEPLOY_PREFIX + '/' + defaultURI
			:
				defaultURI;
	}
	
	/**
	 * Return the deployment path to which jars are relatively specified for 
	 * the given project
	 * (Web projects have a different deployment structure than non-web projects)
	 */
	public static IPath getJarDeploymentRootPath(IProject project) {
		return projectHasWebFacet(project) ? 
			new Path("/" + J2EEConstants.WEB_INF) : 
			new Path("/");
	}
	
	public static IFile getPlatformFile(IProject project, String defaultURI) {
		IPath deploymentPath = new Path(getDeploymentURI(project, defaultURI));
		IVirtualFile vFile = ComponentCore.createFile(project, deploymentPath);
		return vFile.getUnderlyingFile();

	}
	
	public static JpaFile getJpaFile(IProject project, String defaultURI) {
		IFile xmlFile = getPlatformFile(project, defaultURI);
		return xmlFile.exists() ? getJpaFile(xmlFile) : null;
	}
	
	/**
	 * Return the default JPA preferences
	 * @see JpaPreferenceInitializer
	 */
	public static IEclipsePreferences getDefaultPreferences() {
		IScopeContext context = new DefaultScope();
		return context.getNode(PLUGIN_ID);
	}
	
	/**
	 * Return the JPA preferences for the current workspace instance.
	 */
	public static IEclipsePreferences getWorkspacePreferences() {
		IScopeContext context = new InstanceScope();
		return context.getNode(PLUGIN_ID);
	}
	
	/**
	 * Return the JPA preferences for the specified Eclipse project.
	 */
	public static IEclipsePreferences getProjectPreferences(IProject project) {
		IScopeContext context = new ProjectScope(project);
		return context.getNode(PLUGIN_ID);
	}
	
	/**
	 * Return the default JPA platform ID for creating new JPA projects
	 */
	public static String getDefaultJpaPlatformId() {
		String platformId = 
			Platform.getPreferencesService().get(
				JpaPreferenceConstants.PREF_DEFAULT_JPA_PLATFORM, GenericJpaPlatformProvider.ID,
				new Preferences[] {getWorkspacePreferences(), getDefaultPreferences()});
		if (! JpaPlatformRegistry.instance().containsPlatform(platformId)) {
			platformId = 
				Platform.getPreferencesService().get(
					JpaPreferenceConstants.PREF_DEFAULT_JPA_PLATFORM, GenericJpaPlatformProvider.ID,
					new Preferences[] {getDefaultPreferences()});
		}
		return platformId;
	}
	
	/**
	 * Set the default JPA platform ID for creating new JPA projects
	 */
	public static void setDefaultJpaPlatformId(String platformId) {
		IEclipsePreferences prefs = getWorkspacePreferences();
		prefs.put(JpaPreferenceConstants.PREF_DEFAULT_JPA_PLATFORM, platformId);
		flush(prefs);
	}

	/**
	 * Return the JPA platform associated with the specified Eclipse project.
	 */
	public static JpaPlatform getJpaPlatform(IProject project) {
		return JpaPlatformRegistry.instance().getJpaPlatform(project);
	}
	
	/**
	 * Return the JPA platform ID associated with the specified Eclipse project.
	 */
	public static String getJpaPlatformId(IProject project) {
		return getProjectPreferences(project).get(JPA_PLATFORM, GenericJpaPlatformProvider.ID);
	}

	/**
	 * Set the JPA platform ID associated with the specified Eclipse project.
	 */
	public static void setJpaPlatformId(IProject project, String jpaPlatformId) {
		IEclipsePreferences prefs = getProjectPreferences(project);
		prefs.put(JPA_PLATFORM, jpaPlatformId);
		flush(prefs);
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
		IEclipsePreferences prefs = getProjectPreferences(project);
		prefs.putBoolean(DISCOVER_ANNOTATED_CLASSES, discoverAnnotatedClasses);
		flush(prefs);
	}

	/**
	 * checked exceptions bite
	 */
	private static void flush(IEclipsePreferences prefs) {
		try {
			prefs.flush();
		} catch(BackingStoreException ex) {
			log(ex);
		}
	}

	/**
	 * Return the name of the connection profile associated with the specified
	 * Eclipse project.
	 */
	public static String getConnectionProfileName(IProject project) {
		try {
			return project.getPersistentProperty(DATA_SOURCE_CONNECTION_PROFILE_NAME);
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
	 * Return the default catalog name associated with the specified Eclipse project.
	 * @see JpaProject#getUserOverrideDefaultCatalogName()
	 */
	public static String getUserOverrideDefaultCatalogName(IProject project) {
		try {
			return project.getPersistentProperty(USER_OVERRIDE_DEFAULT_CATALOG_NAME);
		}
		catch (CoreException ce) {
			log(ce);
			return null;
		}
	}

	/**
	 * Set the default catalog name associated with the specified Eclipse project.
	 * @see JpaProject#setUserOverrideDefaultCatalogName()
	 */
	public static void setUserOverrideDefaultCatalogName(IProject project, String defaultCatalogName) {
		try {
			project.setPersistentProperty(USER_OVERRIDE_DEFAULT_CATALOG_NAME, defaultCatalogName);
		}
		catch (CoreException ce) {
			log(ce);
		}
	}

	/**
	 * Return the default schema name associated with the specified Eclipse project.
	 * @see JpaProject#getUserOverrideDefaultSchemaName()
	 */
	public static String getUserOverrideDefaultSchemaName(IProject project) {
		try {
			return project.getPersistentProperty(USER_OVERRIDE_DEFAULT_SCHEMA_NAME);
		}
		catch (CoreException ce) {
			log(ce);
			return null;
		}
	}
	
	/**
	 * Set the default schema name associated with the specified Eclipse project.
	 * @see JpaProject#setUserOverrideDefaultSchemaName()
	 */
	public static void setUserOverrideDefaultSchemaName(IProject project, String defaultSchemaName) {
		try {
			project.setPersistentProperty(USER_OVERRIDE_DEFAULT_SCHEMA_NAME, defaultSchemaName);
		}
		catch (CoreException ce) {
			log(ce);
		}
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

	public JptCorePlugin() {
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
		JpaModelManager.instance().start();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			JpaModelManager.instance().stop();
		} finally {
			super.stop(context);
		}
	}
}
