/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.internal.jaxb22.GenericJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * Public access to the Dali JAXB preferences.
 * <p>
 * Preferences are a cross between public, model-related state and private,
 * plug-in-related state; thus this public facade to state that is
 * (traditionally) scoped by the source code's plug-in location.
 * Another complication is that preferences must be available even when a
 * model is not (yet) present (e.g. for a "creation" wizard).
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public class JaxbPreferences {

	// ********** project JAXB platform ID **********

	public static String getJaxbPlatformID(IProject project) {
		return getJaxbPlatformID(project, GenericJaxb_2_2_PlatformDefinition.ID);
	}

	private static String getJaxbPlatformID(IProject project, String def) {
		Preferences prefs = getJaxbPlatformPreferences(project);
		return (prefs == null) ? def : prefs.get(JAXB_PLATFORM_ID, def);
	}

	public static void setJaxbPlatformID(IProject project, String jaxbPlatformID) {
		Preferences prefs = getJaxbPlatformPreferences(project);
		if (prefs != null) {
			prefs.put(JAXB_PLATFORM_ID, jaxbPlatformID);
			flushPreferences(prefs);
		}
	}

	private static Preferences getJaxbPlatformPreferences(IProject project) {
		Preferences prefs = getPreferences(project);
		return (prefs == null) ? null : prefs.node(JAXB_PLATFORM_NODE);
	}

	private static final String JAXB_PLATFORM_NODE = "platform";  //$NON-NLS-1$
	private static final String JAXB_PLATFORM_ID = "platform-id";  //$NON-NLS-1$


	// ********** schema locations **********

	public static List<String> getSchemaLocations(IProject project) {
		return getSchemaLocations(project, Collections.<String>emptyList());
	}

	private static List<String> getSchemaLocations(IProject project, List<String> def) {
		Preferences prefs = getSchemasPreferences(project);
		if (prefs == null) {
			return def;
		}

		try {
			ArrayList<String> schemaLocations = new ArrayList<String>();
			int i = 1;
			String nodeName = null;
			while (prefs.nodeExists(nodeName = buildSchemaNodeName(i))) {
				Preferences schemaPrefs = prefs.node(nodeName);
				String location = schemaPrefs.get(SCHEMA_LOCATION, null);
				if (location != null) {
					schemaLocations.add(location);
				}
				i++;
			}
			return schemaLocations;
		} catch (BackingStoreException ex) {
			JptJaxbCorePlugin.instance().logError(ex);
			return def;
		}
	}

	public static void setSchemaLocations(IProject project, List<String> schemaLocations) {
		Preferences prefs = getSchemasPreferences(project);
		if (prefs == null) {
			return;
		}

		try {
			int i = 1;
			String nodeName = null;
			for (String location : schemaLocations) {
				nodeName = buildSchemaNodeName(i);
				Preferences schemaPref = prefs.node(nodeName);
				schemaPref.put(SCHEMA_LOCATION, location);
				i++;
			}
			while (prefs.nodeExists(nodeName = buildSchemaNodeName(i))) {
				prefs.node(nodeName).removeNode();
				i++;
			}
			flushPreferences(prefs);
		} catch (BackingStoreException ex) {
			JptJaxbCorePlugin.instance().logError(ex);
		}
	}

	private static Preferences getSchemasPreferences(IProject project) {
		Preferences prefs = getPreferences(project);
		return (prefs == null) ? null : prefs.node(SCHEMAS_NODE);
	}

	private static String buildSchemaNodeName(int i) {
		return SCHEMA_NODE_PREFIX + String.valueOf(i);
	}

	private static final String SCHEMAS_NODE = "schemas";  //$NON-NLS-1$
	/**
	 * Converted into <code>"schema-1"</code>, <code>"schema-2"</code>, etc.
	 */
	private static final String SCHEMA_NODE_PREFIX = "schema-";  //$NON-NLS-1$
	private static final String SCHEMA_LOCATION = "location";  //$NON-NLS-1$


	// ********** class gen package **********

	public static String getClassGenPackage(IProject project) {
		return getClassGenPackage(project, StringTools.EMPTY_STRING);
	}

	private static String getClassGenPackage(IProject project, String def) {
		Preferences prefs = getClassGenPreferences(project);
		return (prefs == null) ? def : prefs.get(PACKAGE, def);
	}

	public static void setClassGenPackage(IProject project, String packageName) {
		Preferences prefs = getClassGenPreferences(project);
		if (prefs != null) {
			prefs.put(PACKAGE, packageName);
			flushPreferences(prefs);
		}
	}

	private static Preferences getClassGenPreferences(IProject project) {
		Preferences prefs = getPreferences(project);
		return (prefs == null) ? null : prefs.node(CLASS_GEN_NODE);
	}

	private static final String CLASS_GEN_NODE = "classgen";  //$NON-NLS-1$
	private static final String PACKAGE = "package";  //$NON-NLS-1$


	// ********** flush preferences **********

	private static void flushPreferences(Preferences prefs) {
		try {
			prefs.flush();
		} catch (BackingStoreException ex) {
			JptJaxbCorePlugin.instance().logError(ex);
		}
	}


	// ********** misc **********

	/**
	 * <strong>NB:</strong> Return the <em>faceted project</em> preferences.
	 */
	private static Preferences getPreferences(IProject project) {
		IFacetedProject facetedProject = getFacetedProject(project);
		try {
			return (facetedProject == null) ? null : facetedProject.getPreferences(JaxbProject.FACET);
		} catch (BackingStoreException ex) {
			JptJaxbCorePlugin.instance().logError(ex);
			return null;
		}
	}

	private static IFacetedProject getFacetedProject(IProject project) {
		try {
			return ProjectFacetsManager.create(project);
		} catch (CoreException ex) {
			JptJaxbCorePlugin.instance().logError(ex);
			return null;
		}
	}

	private JaxbPreferences() {
		throw new UnsupportedOperationException();
	}
}
