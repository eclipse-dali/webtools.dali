/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jpt.core.internal.platform.generic.GenericPlatform;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

/**
 * The JPT plug-in lifecycle implementation.
 * A number of globally-available constants and methods.
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

	/**
	 * The identifier for the JPA facet
	 * (value <code>"jpt.jpa"</code>).
	 */
	public static final String FACET_ID = "jpt.jpa";  //$NON-NLS-1$

	/**
	 * The key for storing a JPA project's platform in the Eclipse
	 * project's preferences.
	 */
	public static final String JPA_PLATFORM = PLUGIN_ID + ".platform";  //$NON-NLS-1$

	/**
	 * The key for storing a JPA project's "discover" flag in the Eclipse
	 * project's preferences.
	 */
	public static final String DISCOVER_ANNOTATED_CLASSES = PLUGIN_ID + ".discoverAnnotatedClasses";  //$NON-NLS-1$

	/**
	 * The key for storing a JPA project's data source connection profile name
	 * in the Eclipse project's persistent property's.
	 */
	public static final QualifiedName DATA_SOURCE_CONNECTION_PROFILE_NAME = 
			new QualifiedName(PLUGIN_ID, "dataSource.connectionProfileName");  //$NON-NLS-1$

	/**
	 * The identifier for the JPA validation marker
	 * (value <code>"org.eclipse.jpt.core.jpaProblemMarker"</code>).
	 */
	public static final String VALIDATION_MARKER_ID = PLUGIN_ID + ".jpaProblemMarker";  //$NON-NLS-1$

	/**
	 * Value of the content-type for orm.xml mappings files. Use this value to retrieve 
	 * the ORM xml content type from the content type manager and to add new 
	 * orm.xml-like extensions to this content type.
	 * 
	 * @see org.eclipse.core.runtime.content.IContentTypeManager#getContentType(String)
	 */
	public static final String ORM_XML_CONTENT_TYPE = PLUGIN_ID + ".content.orm"; //$NON-NLS-1$

	/**
	 * Ditto for persistence.xml.
	 * @see #ORM_XML_CONTENT_TYPE
	 */
	public static final String PERSISTENCE_XML_CONTENT_TYPE = PLUGIN_ID + ".content.persistence"; //$NON-NLS-1$

	/**
	 * Web projects have some special exceptions.
	 */
	@SuppressWarnings("restriction")
	public static final String WEB_PROJECT_FACET_ID = org.eclipse.wst.common.componentcore.internal.util.IModuleConstants.JST_WEB_MODULE;

	/**
	 * Web projects have some special exceptions.
	 */
	@SuppressWarnings("restriction")
	public static final String WEB_PROJECT_DEPLOY_PREFIX = org.eclipse.jst.j2ee.internal.J2EEConstants.WEB_INF_CLASSES;

	public static final String DEFAULT_PERSISTENCE_XML_FILE_PATH = "META-INF/persistence.xml";

	public static final String DEFAULT_ORM_XML_FILE_PATH = "META-INF/orm.xml";


	// ********** singleton **********

	private static JptCorePlugin INSTANCE;

	/**
	 * Return the singleton JPA plug-in.
	 */
	public static JptCorePlugin instance() {
		return INSTANCE;
	}


	// ********** public static methods **********

	/**
	 * Return the singular JPA model corresponding to the current workspace.
	 */
	public static IJpaModel jpaModel() {
		return JpaModelManager.instance().jpaModel();
	}

	/**
	 * Return the JPA project corresponding to the specified Eclipse project,
	 * or null if unable to associate the specified project with a
	 * JPA project.
	 */
	public static IJpaProject jpaProject(IProject project) {
		try {
			return JpaModelManager.instance().jpaProject(project);
		} catch (CoreException ex) {
			log(ex);
			return null;
		}
	}

	/**
	 * Return the JPA file corresponding to the specified Eclipse file,
	 * or null if unable to associate the specified file with a JPA file.
	 */
	public static IJpaFile jpaFile(IFile file) {
		try {
			return jpaModel().jpaFile(file);
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
	 * Return the persistence.xml deployment URI for the specified project.
	 */
	public static String persistenceXmlDeploymentURI(IProject project) {
		return deploymentURI(project, DEFAULT_PERSISTENCE_XML_FILE_PATH);
	}

	/**
	 * Return the orm.xml deployment URI for the specified project.
	 */
	public static String ormXmlDeploymentURI(IProject project) {
		return deploymentURI(project, DEFAULT_ORM_XML_FILE_PATH);
	}

	/**
	 * Tweak the specified deployment URI if the specified project
	 * has a web facet.
	 */
	private static String deploymentURI(IProject project, String defaultURI) {
		return projectHasWebFacet(project) ?
				WEB_PROJECT_DEPLOY_PREFIX + "/" + defaultURI
			:
				defaultURI;
	}

	/**
	 * Return the JPA preferences for the specified Eclipse project.
	 */
	public static IEclipsePreferences preferences(IProject project) {
		IScopeContext context = new ProjectScope(project);
		return context.getNode(PLUGIN_ID);
	}

	/**
	 * Return the JPA platform associated with the specified Eclipse project.
	 */
	public static IJpaPlatform jpaPlatform(IProject project) {
		return JpaPlatformRegistry.instance().jpaPlatform(jpaPlatformId(project));
	}

	/**
	 * Return the JPA platform ID associated with the specified Eclipse project.
	 */
	public static String jpaPlatformId(IProject project) {
		return preferences(project).get(JPA_PLATFORM, GenericPlatform.ID);
	}

	/**
	 * Set the JPA platform ID associated with the specified Eclipse project.
	 */
	public static void setJpaPlatformId(IProject project, String jpaPlatformId) {
		IEclipsePreferences prefs = preferences(project);
		prefs.put(JPA_PLATFORM, jpaPlatformId);
		flush(prefs);
	}

	/**
	 * Return the JPA "discover" flag associated with the specified
	 * Eclipse project.
	 */
	public static boolean discoverAnnotatedClasses(IProject project) {
		return preferences(project).getBoolean(DISCOVER_ANNOTATED_CLASSES, false);
	}

	/**
	 * Set the JPA "discover" flag associated with the specified
	 * Eclipse project.
	 */
	public static void setDiscoverAnnotatedClasses(IProject project, boolean discoverAnnotatedClasses) {
		IEclipsePreferences prefs = preferences(project);
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
	public static String connectionProfileName(IProject project) {
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
