/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.ResourceLocator;
import org.eclipse.jst.j2ee.internal.J2EEConstants;

public class SimpleJavaResourceLocator 
		implements ResourceLocator {
	
	protected static IPath META_INF_PATH = new Path(J2EEConstants.META_INF);
	
	/**
	 * Simply ensure the container is either:
	 * - on the java project classpath
	 *   or
	 * - in the non-java resources, but not in the project output location
	 */
	public boolean acceptResourceLocation(IProject project, IContainer container) {
		IJavaProject javaProject = getJavaProject(project);
		if (javaProject.isOnClasspath(container)) {
			return true;
		}
		try {
			IPath outputLocation = javaProject.getOutputLocation();
			if (container.equals(project) && outputLocation.isPrefixOf(container.getFullPath())) {
				return true;
			}
			for (Object resource : javaProject.getNonJavaResources()) {
				if (resource instanceof IFolder) {
					IFolder folder = (IFolder) resource;
					if ((folder.equals(container) || folder.contains(container))
							&& ! outputLocation.isPrefixOf(container.getFullPath())) {
						return true;
					}
				}
			}
		}
		catch (JavaModelException jme) {
			JptCorePlugin.log(jme);
			// only happens if the java project doesn't exist.  fall through.
		}
		return false;
	}
	
	/**
	 * Return 
	 * - the first package fragment root (source folder) META-INF folder if it exists, 
	 *    or 
	 * - the project rooted META-INF folder if it exists
	 *    or 
	 * - the non-existent META-INF folder in the first package fragment root (source folder)
	 */
	public IContainer getDefaultResourceLocation(IProject project) {
		IJavaProject javaProject = getJavaProject(project);
		IContainer defaultLocation = null;
		try {
			for (IPackageFragmentRoot root : javaProject.getPackageFragmentRoots()) {
				if (root.getKind() == IPackageFragmentRoot.K_SOURCE) {
					IContainer rootContainer = (IContainer) root.getUnderlyingResource();
					IFolder metaInfFolder = rootContainer.getFolder(META_INF_PATH);
					if (metaInfFolder.exists()) {
						return metaInfFolder;
					}
					if (defaultLocation == null) {
						// hold on to this in case the META-INF folder can't be found
						defaultLocation = metaInfFolder;
					}
				}
			}
			IFolder metaInfFolder = project.getFolder(META_INF_PATH);
			if (metaInfFolder.exists()) {
				return metaInfFolder;
			}
		}
		catch (JavaModelException jme) {
			// only happens if the java project doesn't exist or there is some problem with the 
			// java resources.  fall through.
			JptCorePlugin.log(jme);
		}
		
		return defaultLocation;
	}
	
	public IPath getResourcePath(IProject project, IPath runtimePath) {
		IJavaProject javaProject = getJavaProject(project);
		IPath firstResourcePath = null;
		try {
			for (IPackageFragmentRoot root : javaProject.getPackageFragmentRoots()) {
				if (root.getKind() == IPackageFragmentRoot.K_SOURCE) {
					IContainer rootContainer = (IContainer) root.getUnderlyingResource();
					IPath resourcePath = rootContainer.getFullPath().append(runtimePath);
					if (firstResourcePath == null) {
						firstResourcePath = resourcePath;
					}
					IFile file = project.getWorkspace().getRoot().getFile(resourcePath);
					if (file.exists()) {
						return file.getFullPath();
					}
				}
			}
		}
		catch (JavaModelException jme) {
			JptCorePlugin.log(jme);
			return null;
		}
		return firstResourcePath;
	}
	
	private IJavaProject getJavaProject(IProject project) {
		return JavaCore.create(project);
	}
}
