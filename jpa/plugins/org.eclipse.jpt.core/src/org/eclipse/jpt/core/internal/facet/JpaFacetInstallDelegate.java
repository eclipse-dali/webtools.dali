/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jst.j2ee.classpathdep.ClasspathDependencyUtil;
import org.eclipse.jst.j2ee.classpathdep.IClasspathDependencyConstants;
import org.eclipse.jst.j2ee.project.EarUtilities;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * We don't really "install" the JPA facet here. We simply store all the various
 * data model properties in the appropriate preferences. These settings will
 * used in the POST_INSTALL event listener to build the JPA project.
 */
public class JpaFacetInstallDelegate 
	implements IDelegate, JpaFacetDataModelProperties
{

	public void execute(IProject project, IProjectFacetVersion fv, 
				Object config, IProgressMonitor monitor) throws CoreException {
		monitor = this.nonNullMonitor(monitor);
		try {
			this.execute_(project, fv, config, monitor);
		} finally {
			monitor.done();
		}
	}

	private void execute_(IProject project, IProjectFacetVersion fv, 
				Object config, IProgressMonitor monitor) throws CoreException {
		
		monitor.beginTask("", 1); //$NON-NLS-1$

		// NB: WTP Natures (including the JavaEMFNature)
		// should already be added, as this facet should 
		// always coexist with a module facet.

		IJavaProject javaProject = JavaCore.create(project);
		IDataModel dataModel = (IDataModel) config;
		this.configureClasspath(javaProject, dataModel, monitor);
		
		// project settings
		JptCorePlugin.setJpaPlatformId(project, dataModel.getStringProperty(PLATFORM_ID));
		JptCorePlugin.setConnectionProfileName(project, dataModel.getStringProperty(CONNECTION));
		JptCorePlugin.setDiscoverAnnotatedClasses(project, dataModel.getBooleanProperty(DISCOVER_ANNOTATED_CLASSES));
		
		// defaults settings
		JptCorePlugin.setDefaultJpaPlatformId(dataModel.getStringProperty(PLATFORM_ID));
		
		monitor.worked(1);
	}

	private void configureClasspath(IJavaProject javaProject, IDataModel dataModel, IProgressMonitor monitor) throws CoreException {
		boolean useServerLibrary = dataModel.getBooleanProperty(USE_SERVER_JPA_IMPLEMENTATION);
		if (useServerLibrary) {
			return;
		}

		String jpaLibrary = dataModel.getStringProperty(JPA_LIBRARY);
		if (StringTools.stringIsEmpty(jpaLibrary)) {
			return;
		}

		// build the JPA library to be added to the classpath
		IClasspathAttribute[] attributes = this.buildClasspathAttributes(javaProject.getProject());
		IClasspathEntry jpaLibraryEntry = 
			JavaCore.newContainerEntry(
				new Path(JavaCore.USER_LIBRARY_CONTAINER_ID + "/" + jpaLibrary),
				null, attributes, true
			);

		// if the JPA library is already there, do nothing
		IClasspathEntry[] classpath = javaProject.getRawClasspath();
		if (CollectionTools.contains(classpath, jpaLibraryEntry)) {
			return;
		}

		// add the JPA library to the classpath
		int len = classpath.length;
		IClasspathEntry[] newClasspath = new IClasspathEntry[len + 1];
		System.arraycopy(classpath, 0, newClasspath, 0, len);
		newClasspath[len] = jpaLibraryEntry;
		javaProject.setRawClasspath(newClasspath, monitor);
	}

	private static final IClasspathAttribute[] EMPTY_CLASSPATH_ATTRIBUTES = new IClasspathAttribute[0];

	private IClasspathAttribute[] buildClasspathAttributes(IProject project) {
		boolean webApp = JptCorePlugin.projectHasWebFacet(project);
		if ( ! webApp && this.projectIsStandalone(project)) {
			return EMPTY_CLASSPATH_ATTRIBUTES;
		}
		return new IClasspathAttribute[] {
				JavaCore.newClasspathAttribute(
					IClasspathDependencyConstants.CLASSPATH_COMPONENT_DEPENDENCY,
					ClasspathDependencyUtil.getDefaultRuntimePath(webApp).toString()
				)
		};
	}

	private boolean projectIsStandalone(IProject project) {
		return EarUtilities.isStandaloneProject(project);
	}

	private IProgressMonitor nonNullMonitor(IProgressMonitor monitor) {
		return (monitor != null) ? monitor : new NullProgressMonitor();
	}

}
