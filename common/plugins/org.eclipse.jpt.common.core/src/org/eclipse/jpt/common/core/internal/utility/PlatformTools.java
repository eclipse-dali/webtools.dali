/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jpt.common.core.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.osgi.framework.Bundle;

/**
 * A collection of utilities for dealing with the Eclipse platform API.
 */
public class PlatformTools {

	// ********** adapter **********

	/**
	 * Add some Generic Goodness to the method signature.
	 * @see org.eclipse.core.runtime.IAdapterManager#getAdapter(Object, Class)
	 */
	@SuppressWarnings("unchecked")
	public static <A> A getAdapter(Object o, Class<A> adapterType) {
		return (A) Platform.getAdapterManager().getAdapter(o, adapterType);
	}


	// ********** resources **********

	/**
	 * Return the {@link IContainer} with the workspace-relative "full" path
	 */
	public static IContainer getContainer(IPath fullContainerPath) {
		// changed to handle non-workspace projects
		String projectName = fullContainerPath.segment(0).toString();
		IPath projectRelativePath = fullContainerPath.removeFirstSegments(1);
		IProject project = getWorkspaceRoot().getProject(projectName);
		return (projectRelativePath.isEmpty()) ? project : project.getFolder(projectRelativePath);
	}
	
	/**
	 * Return the {@link IFile} with the workspace relative "full" path
	 */
	public static IFile getFile(IPath fullFilePath) {
		// changed to handle non-workspace projects
		String projectName = fullFilePath.segment(0).toString();
		IPath projectRelativePath = fullFilePath.removeFirstSegments(1);
		IProject project = getWorkspaceRoot().getProject(projectName);
		return project.getFile(projectRelativePath);
	}

	private static IWorkspaceRoot getWorkspaceRoot() {
		return getWorkspace().getRoot();
	}
	
	private static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}
	

	// ********** resource type **********

	public static JptResourceType getResourceType(IContentType contentType) {
		JptWorkspace jptWorkspace = getJptWorkspace();
		return (jptWorkspace == null) ? null : jptWorkspace.getResourceTypeManager().getResourceType(contentType);
	}
	
	public static JptResourceType getResourceType(IContentType contentType, String version) {
		JptWorkspace jptWorkspace = getJptWorkspace();
		return (jptWorkspace == null) ? null : jptWorkspace.getResourceTypeManager().getResourceType(contentType, version);
	}

	private static JptWorkspace getJptWorkspace() {
		return getAdapter(getWorkspace(), JptWorkspace.class);
	}


	// ********** content type **********

	/**
	 * Return the specified file's content type,
	 * using the Eclipse platform's content type manager.
	 */
	public static IContentType getContentType(IFile file) {
		String fileName = file.getName();
		InputStream fileContents = null;
		try {
			fileContents = file.getContents();
		} catch (CoreException ex) {
			// seems like we can ignore any exception that might occur here;
			// e.g. we get a FNFE if the workspace is out of sync with the O/S file system
			// JptCorePlugin.log(ex);

			// look for content type based on the file name only(?)
			return findContentTypeFor(fileName);
		}

		IContentType contentType = null;
		try {
			contentType = findContentTypeFor(fileContents, fileName);
		} catch (IOException ex) {
			JptCommonCorePlugin.instance().logError(ex);
		} finally {
			try {
				fileContents.close();
			} catch (IOException ex) {
				JptCommonCorePlugin.instance().logError(ex);
			}
		}
		return contentType;
	}

	private static IContentType findContentTypeFor(InputStream fileContents, String fileName) throws IOException {
		return getContentTypeManager().findContentTypeFor(fileContents, fileName);
	}

	private static IContentType findContentTypeFor(String fileName) {
		return getContentTypeManager().findContentTypeFor(fileName);
	}

	private static IContentTypeManager getContentTypeManager() {
		return Platform.getContentTypeManager();
	}


	// ********** instantiation **********

	/**
	 * Load the specified class, using the specified bundle, and, if it is a
	 * sub-type of the specified interface, instantiate it and return the resulting
	 * object, cast appropriately.
	 * Log an error and return <code>null</code> for any of the following
	 * conditions:<ul>
	 * <li>the bundle cannot be resolved
	 * <li>the class fails to load
	 * <li>the loaded class is not a sub-type of the specified interface
	 * <li>the loaded class cannot be instantiated
	 * </ul>
	 */
	public static <T> T instantiate(String pluginID, String extensionPoint, String className, Class<T> interfaze) {
		Class<T> clazz = loadClass(pluginID, extensionPoint, className, interfaze);
		return (clazz == null) ? null : instantiate(pluginID, extensionPoint, clazz);
    }

	/**
	 * Load the specified class, using the specified bundle, and cast it to the
	 * specified interface before returning it.
	 * Log an error and return <code>null</code> for any of the following
	 * conditions:<ul>
	 * <li>the bundle cannot be resolved
	 * <li>the class fails to load
	 * <li>the loaded class is not a sub-type of the specified interface
	 * </ul>
	 */
	private static <T> Class<T> loadClass(String pluginID, String extensionPoint, String className, Class<T> interfaze) {
		Bundle bundle = Platform.getBundle(pluginID);
		if (bundle == null) {
			logError(JptCommonCoreMessages.REGISTRY_MISSING_BUNDLE, pluginID);
			return null;
		}

		Class<?> clazz;
		try {
			clazz = bundle.loadClass(className);
		} catch (Exception ex) {
			logFailedClassLoad(ex, pluginID, extensionPoint, className);
			return null;
		}

		if ( ! interfaze.isAssignableFrom(clazz)) {
			logFailedInterfaceAssignment(pluginID, extensionPoint, clazz, interfaze);
			return null;
		}

		@SuppressWarnings("unchecked")
		Class<T> clazzT = (Class<T>) clazz;
		return clazzT;
    }

	private static void logFailedClassLoad(Exception ex, String pluginID, String extensionPoint, String className) {
		logError(ex, JptCommonCoreMessages.REGISTRY_FAILED_CLASS_LOAD,
				className,
				extensionPoint,
				pluginID
			);
	}

	private static void logFailedInterfaceAssignment(String pluginID, String extensionPoint, Class<?> clazz, Class<?> interfaze) {
		logError(JptCommonCoreMessages.REGISTRY_FAILED_INTERFACE_ASSIGNMENT,
				clazz.getName(),
				extensionPoint,
				pluginID,
				interfaze.getName()
			);
	}

	/**
	 * Instantiate the specified class.
	 * Log an error and return <code>null</code> if the instantiation fails.
	 */
	private static <T> T instantiate(String pluginID, String extensionPoint, Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception ex) {
			logFailedInstantiation(ex, pluginID, extensionPoint, clazz);
			return null;
		}
	}

	private static void logFailedInstantiation(Exception ex, String pluginID, String extensionPoint, Class<?> clazz) {
		logError(ex, JptCommonCoreMessages.REGISTRY_FAILED_INSTANTIATION,
				clazz.getName(),
				extensionPoint,
				pluginID
			);
	}

	private static void logError(String msg, Object... args) {
		JptCommonCorePlugin.instance().logError(msg, args);
	}

	private static void logError(Throwable ex, String msg, Object... args) {
		JptCommonCorePlugin.instance().logError(ex, msg, args);
	}


	// ********** workspace preferences **********

	public static String getNewTextFileLineDelimiter() {
		IScopeContext[] contexts = new IScopeContext[] { DefaultScope.INSTANCE };
		return Platform.getPreferencesService().getString(
									Platform.PI_RUNTIME, 
									Platform.PREF_LINE_SEPARATOR, 
									StringTools.CR, 
									contexts);
	}


	// ********** disabled constructor **********

	private PlatformTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
