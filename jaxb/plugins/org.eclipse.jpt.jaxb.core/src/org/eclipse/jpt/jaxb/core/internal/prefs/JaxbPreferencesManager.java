/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.prefs;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jpt.jaxb.core.GenericJaxbPlatform;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 *  JaxbPreferencesManager
 */
public class JaxbPreferencesManager
{
	private final IProject project;

	private static volatile boolean flushPreferences = true;

	// ********** public constants **********
	
	/**
	 * The node for storing a JAXB project's platform in the project's preferences.
	 */
	protected static final String PLATFORM_PREF_NODE = "platform";  //$NON-NLS-1$
	
	/**
	 * The key for storing the platform id
	 */
	protected static final String PLATFORM_ID_PREF_KEY = "platform-id";  //$NON-NLS-1$

	/**
	 * The key for storing the default JAXB platform ID for JAXB 2.1 in the workspace preferences.
	 */
	public static final String DEFAULT_JAXB_PLATFORM_2_1_PREF_KEY = 
			"defaultJaxbPlatform_" + JaxbFacet.VERSION_2_1.getVersionString(); //$NON-NLS-1$

	/**
	 * The key for storing the default JAXB platform ID for JAXB 2.2 in the workspace preferences.
	 */
	public static final String DEFAULT_JAXB_PLATFORM_2_2_PREF_KEY = 
			"defaultJaxbPlatform_" + JaxbFacet.VERSION_2_2.getVersionString(); //$NON-NLS-1$
	
	/**
	 * The node for storing a JAXB project's schemas in the project's preferences.
	 */
	public static final String SCHEMAS_PREF_NODE = "schemas";  //$NON-NLS-1$
	
	/**
	 * The node prefix for storing a particular JAXB project schema in the project's preferences.
	 * Specific schema nodes are followed by integers ("schema-1", "schema-2", etc.)
	 */
	public static final String SCHEMA_PREF_NODE_PREFIX = "schema-";  //$NON-NLS-1$
	
	/**
	 * The key for storing a schema location (such as a uri or catalog key) in the project's preferences
	 */
	public static final String SCHEMA_LOCATION_PREF_KEY = "location";  //$NON-NLS-1$

	/**
	 * The node for storing a JAXB class generator in the project's preferences.
	 */
	protected static final String CLASS_GEN_PREF_NODE = "classgen";  //$NON-NLS-1$

	/**
	 * The key for storing a package name in the project's preferences
	 */
	protected static final String PACKAGE_PREF_KEY = "package";  //$NON-NLS-1$

	// ********** preferences **********

	/**
	 * Return the Dali preferences for the specified context.
	 */
	private static IEclipsePreferences getPreferences(IScopeContext context) {
		return context.getNode(JptJaxbCorePlugin.PLUGIN_ID);
	}

	/**
	 * Return the Dali preferences for the current workspace instance.
	 */
	public static IEclipsePreferences getWorkspacePreferences() {
		return getPreferences(InstanceScope.INSTANCE);
	}
	
	/**
	 * Return the default Dali preferences
	 * @see JpaPreferenceInitializer
	 */
	public static IEclipsePreferences getDefaultPreferences() {
		return getPreferences(DefaultScope.INSTANCE);
	}

	// ********** workspace preference **********

	public static void setWorkspacePreference(String key, String value) {
		IEclipsePreferences wkspPrefs = getWorkspacePreferences();
		if(value == null) {
			wkspPrefs.remove(key);
		}
		else {
			wkspPrefs.put(key, value);
		}
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
	 * See <code>JptJaxbCoreTestsPlugin.start(BundleContext)</code>
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
			super(NLS.bind(JptJaxbCoreMessages.PREFERENCES_FLUSH_JOB_NAME, prefs.absolutePath()));
			this.prefs = prefs;
		}
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				prefs.flush();
			} 
			catch(BackingStoreException ex) {
				JptJaxbCorePlugin.log(ex);
			}
			return Status.OK_STATUS;
		}
	}

	// ********** implementation **************************************************

	public JaxbPreferencesManager(IProject project) {
		if(project == null) {
			throw new RuntimeException("Project is null"); 	//$NON-NLS-1$
		}
		this.project = project;
	}

	// ********** preferences **********
	
	protected Preferences getProjectPreferences() {
		try {
			IFacetedProject fproj = ProjectFacetsManager.create(this.project);
			return fproj.getPreferences(JaxbFacet.FACET);
		}
		catch (BackingStoreException bse) {
			JptJaxbCorePlugin.log(bse);
		}
		catch (CoreException ce) {
			JptJaxbCorePlugin.log(ce);
		}
		return null;
	}

	// ********** getters/setters *********

	/**
	 * Return the JAXB platform ID associated with the specified Eclipse project.
	 */
	public String getJaxbPlatformId() {
		Preferences prefs = this.getProjectPreferences();
		Preferences platformPrefs = prefs.node(PLATFORM_PREF_NODE);
		return platformPrefs.get(PLATFORM_ID_PREF_KEY, GenericJaxbPlatform.VERSION_2_1.getId());
	}

	/**
	 * Set the {@link JaxbPlatformDescription} associated with the specified Eclipse project.
	 */
	public void setJaxbPlatform(JaxbPlatformDescription platform) {
		Preferences prefs = this.getProjectPreferences();
		Preferences platformPrefs = prefs.node(PLATFORM_PREF_NODE);
		platformPrefs.put(PLATFORM_ID_PREF_KEY, platform.getId());
		try {
			platformPrefs.flush();
		}
		catch (BackingStoreException bse) {
			JptJaxbCorePlugin.log(bse);
		}
	}

	public List<String> getSchemaLocations() {
		List<String> schemaLocations = new Vector<String>();
		Preferences prefs = this.getProjectPreferences();
		Preferences schemasPrefs = prefs.node(SCHEMAS_PREF_NODE);
		try {
			boolean checkAnotherNode = true;
			for (int i = 1; checkAnotherNode; i++ ) {
				String nodeName = SCHEMA_PREF_NODE_PREFIX + String.valueOf(i);
				if (schemasPrefs.nodeExists(nodeName)) {
					Preferences schemaPrefs = schemasPrefs.node(nodeName);
					String location = schemaPrefs.get(SCHEMA_LOCATION_PREF_KEY, null);
					if (location != null) {
						schemaLocations.add(location);
					}
				}
				else {
					checkAnotherNode = false;
				}
			}
		}
		catch (BackingStoreException bse) {
			// this means that the prefs are corrupted, in which case reading anything is unlikely
			JptJaxbCorePlugin.log(bse);
		}
		return schemaLocations;
	}

	public String getClassGenPackage() {
		Preferences prefs = this.getProjectPreferences();
		Preferences classgenPrefs = prefs.node(CLASS_GEN_PREF_NODE);
		return classgenPrefs.get(PACKAGE_PREF_KEY, "");  //$NON-NLS-1$
	}

	public void setClassGenPackage(String packageName) {
		Preferences prefs = this.getProjectPreferences();
		Preferences classgenPrefs = prefs.node(CLASS_GEN_PREF_NODE);
		classgenPrefs.put(PACKAGE_PREF_KEY, packageName);
		try {
			classgenPrefs.flush();
		}
		catch (BackingStoreException bse) {
			JptJaxbCorePlugin.log(bse);
		}
	}

	public void setSchemaLocations(List<String> schemaLocations) {
		Preferences prefs = this.getProjectPreferences();
		Preferences schemasPrefs = prefs.node(SCHEMAS_PREF_NODE);
		try {
			int i = 1;
			for (String location : schemaLocations) {
				String nodeName = SCHEMA_PREF_NODE_PREFIX + String.valueOf(i);
				Preferences schemaPref = schemasPrefs.node(nodeName);
				schemaPref.put(SCHEMA_LOCATION_PREF_KEY, location);
				i ++;
			}
			boolean checkAnotherNode = true;
			for ( ; checkAnotherNode; i++ ) {
				String nodeName = SCHEMA_PREF_NODE_PREFIX + String.valueOf(i);
				if (schemasPrefs.nodeExists(nodeName)) {
					schemasPrefs.node(nodeName).removeNode();
				}
				else {
					checkAnotherNode = false;
				}
			}
			schemasPrefs.flush();
		}
		catch (BackingStoreException bse) {
			// this means that the prefs are corrupted, in which case reading anything is unlikely
			JptJaxbCorePlugin.log(bse);
		}
	}
	
}
