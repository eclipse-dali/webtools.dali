/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.ResourceLocator;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.iterable.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jst.j2ee.internal.J2EEConstants;

public class SimpleJavaResourceLocator
	implements ResourceLocator
{
	protected static final IPath META_INF_PATH = new Path(J2EEConstants.META_INF);

	/**
	 * Return whether the specified container is either:<ul>
	 * <li>on the corresponding Java project's classpath; or
	 * <li>in the non-Java resources, but not in the project output location
	 * </ul>
	 */
	public boolean resourceLocationIsValid(IProject project, IContainer container) {
		try {
			return this.resourceLocationIsValid_(project, container);
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			// happens if the Java project does not exist
			return false;
		}
	}

	protected boolean resourceLocationIsValid_(IProject project, IContainer container) throws JavaModelException {
		IJavaProject javaProject = this.getJavaProject(project);
		if (javaProject.isOnClasspath(container)) {
			return true;
		}

		IPath outputPath = javaProject.getOutputLocation();
		IPath containerPath = container.getFullPath();
		if (container.equals(project) && outputPath.isPrefixOf(containerPath)) {
			return true;
		}

		if (outputPath.isPrefixOf(containerPath)) {
			return false;
		}
		for (Object resource : javaProject.getNonJavaResources()) {
			if (resource instanceof IFolder) {
				IFolder folder = (IFolder) resource;
				if (folder.getFullPath().isPrefixOf(containerPath)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Return the default resource location for the specified project:<ul>
	 * <li>the first package fragment root (source folder) <code>META-INF</code>
	 *     folder if it exists; or
	 * <li>the project rooted <code>META-INF</code> folder if it exists; or
	 * <li>the non-existent <code>META-INF</code> folder in the first package
	 *     fragment root (source folder)
	 * </ul>
	 */
	public IContainer getDefaultResourceLocation(IProject project) {
		try {
			return this.getDefaultResourceLocation_(project);
		} catch (Exception ex) {
			JptCommonCorePlugin.instance().logError(ex);
			// happens if the Java project does not exist or there is a problem with the
			// Java resources
			return null;
		}
	}

	protected IContainer getDefaultResourceLocation_(IProject project) throws JavaModelException {
		IContainer defaultLocation = null;
		for (IContainer sourceFolder : this.getSourceFolders(project)) {
			IFolder metaInfFolder = sourceFolder.getFolder(META_INF_PATH);
			if (metaInfFolder.exists()) {
				return metaInfFolder;
			}
			if (defaultLocation == null) {
				// save the first one, to use if none found
				defaultLocation = metaInfFolder;
			}
		}
		IFolder metaInfFolder = project.getFolder(META_INF_PATH);
		if (metaInfFolder.exists()) {
			return metaInfFolder;
		}
		return defaultLocation;
	}

	public IPath getResourcePath(IProject project, IPath runtimePath) {
		try {
			return this.getResourcePath_(project, runtimePath);
		} catch (Exception ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return null;
		}
	}

	protected IPath getResourcePath_(IProject project, IPath runtimePath) throws JavaModelException {
		IPath defaultResourcePath = null;
		for (IContainer sourceFolder : this.getSourceFolders(project)) {
			IPath resourcePath = sourceFolder.getFullPath().append(runtimePath);
			IFile file = project.getWorkspace().getRoot().getFile(resourcePath);
			if (file.exists()) {
				return file.getFullPath();
			}
			if (defaultResourcePath == null) {
				// save the first one, to use if none found
				defaultResourcePath = resourcePath;
			}
		}
		return defaultResourcePath;
	}

	public IPath getRuntimePath(IProject project, IPath resourcePath) {
		try {
			return this.getRuntimePath_(project, resourcePath);
		} catch (Exception ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return resourcePath.makeRelativeTo(project.getFullPath());
		}
	}

	protected IPath getRuntimePath_(IProject project, IPath resourcePath) throws JavaModelException {
		IFile file = PlatformTools.getFile(resourcePath);
		for (IContainer sourceFolder : this.getSourceFolders(project)) {
			if (sourceFolder.contains(file)) {
				return resourcePath.makeRelativeTo(sourceFolder.getFullPath());
			}
		}
		return resourcePath.makeRelativeTo(project.getFullPath());
	}

	protected Iterable<IContainer> getSourceFolders(IProject project) throws JavaModelException {
		return new TransformationIterable<IPackageFragmentRoot, IContainer>(this.getSourcePackageFragmentRoots(project), ROOT_CONTAINER_TRANSFORMER);
	}

	protected static final Transformer<IPackageFragmentRoot, IContainer> ROOT_CONTAINER_TRANSFORMER = new RootContainerTransformer();

	protected static class RootContainerTransformer
		implements Transformer<IPackageFragmentRoot, IContainer>
	{
		public IContainer transform(IPackageFragmentRoot pfr) {
			try {
				return this.transform_(pfr);
			} catch (JavaModelException ex) {
				throw new RuntimeException(ex);
			}
		}
		protected IContainer transform_(IPackageFragmentRoot pfr) throws JavaModelException {
			return (IContainer) pfr.getUnderlyingResource();
		}
	}

	protected Iterable<IPackageFragmentRoot> getSourcePackageFragmentRoots(IProject project) throws JavaModelException {
		return new FilteringIterable<IPackageFragmentRoot>(this.getPackageFragmentRoots(project), SOURCE_ROOT_FILTER);
	}

	protected static final Filter<IPackageFragmentRoot> SOURCE_ROOT_FILTER = new SourceRootFilter();

	protected static class SourceRootFilter
		implements Filter<IPackageFragmentRoot>
	{
		public boolean accept(IPackageFragmentRoot pfr) {
			try {
				return this.accept_(pfr);
			} catch (JavaModelException ex) {
				throw new RuntimeException(ex);
			}
		}
		protected boolean accept_(IPackageFragmentRoot pfr) throws JavaModelException {
			// accept both source folders and *class* folders (xml files may exist there as well)
			IResource resource = pfr.getUnderlyingResource();
			return resource != null && resource.getType() == IResource.FOLDER;
		}
	}

	protected Iterable<IPackageFragmentRoot> getPackageFragmentRoots(IProject project) throws JavaModelException {
		return new ArrayIterable<IPackageFragmentRoot>(this.getPackageFragmentRootsArray(project));
	}

	protected IPackageFragmentRoot[] getPackageFragmentRootsArray(IProject project) throws JavaModelException {
		return this.getJavaProject(project).getPackageFragmentRoots();
	}

	protected IJavaProject getJavaProject(IProject project) {
		return JavaCore.create(project);
	}
}
