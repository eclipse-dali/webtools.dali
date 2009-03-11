/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
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

	private void execute_(IProject project, @SuppressWarnings("unused") IProjectFacetVersion fv, 
				Object config, IProgressMonitor monitor) throws CoreException {
		
		monitor.beginTask("", 1); //$NON-NLS-1$

		// NB: WTP Natures (including the JavaEMFNature)
		// should already be added, as this facet should 
		// always coexist with a module facet.

		IJavaProject javaProject = JavaCore.create(project);
		IDataModel dataModel = (IDataModel) config;
		
		// project settings
		JptCorePlugin.setJpaPlatformId(project, dataModel.getStringProperty(PLATFORM_ID));
		JptCorePlugin.setConnectionProfileName(project, dataModel.getStringProperty(CONNECTION));
		if (dataModel.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG)) {
			JptCorePlugin.setUserOverrideDefaultCatalogName(project, dataModel.getStringProperty(USER_OVERRIDE_DEFAULT_CATALOG));
		}
		if (dataModel.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			JptCorePlugin.setUserOverrideDefaultSchemaName(project, dataModel.getStringProperty(USER_OVERRIDE_DEFAULT_SCHEMA));
		}

		this.addDbDriverLibraryToClasspath(javaProject, dataModel, monitor);

		JptCorePlugin.setDiscoverAnnotatedClasses(project, dataModel.getBooleanProperty(DISCOVER_ANNOTATED_CLASSES));
		
		// defaults settings
		JptCorePlugin.setDefaultJpaPlatformId(dataModel.getStringProperty(PLATFORM_ID));
		
		//Delegate to LibraryInstallDelegate to configure the project classpath
		((LibraryInstallDelegate) dataModel.getProperty(JpaFacetDataModelProperties.LIBRARY_PROVIDER_DELEGATE)).execute(new NullProgressMonitor());
		
		monitor.worked(1);
	}

	private void addDbDriverLibraryToClasspath(IJavaProject javaProject, IDataModel dataModel, IProgressMonitor monitor) throws CoreException {
		if( ! dataModel.getBooleanProperty(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			return;
		}
		String driverName = dataModel.getStringProperty(DB_DRIVER_NAME);

		IClasspathContainer container = JptDbPlugin.instance().buildDriverClasspathContainerFor(driverName);
		IClasspathEntry entry = JavaCore.newContainerEntry(container.getPath());
		this.addClasspathEntryToProject(entry, javaProject, monitor);
	}
	
	private void addClasspathEntryToProject(IClasspathEntry classpathEntry, IJavaProject javaProject, IProgressMonitor monitor) throws CoreException {

		// if the classpathEntry is already there, do nothing
		IClasspathEntry[] classpath = javaProject.getRawClasspath();
		if (CollectionTools.contains(classpath, classpathEntry)) {
			return;
		}

		// add the given classpathEntry to the project classpath
		int len = classpath.length;
		IClasspathEntry[] newClasspath = new IClasspathEntry[len + 1];
		System.arraycopy(classpath, 0, newClasspath, 0, len);
		newClasspath[len] = classpathEntry;
		javaProject.setRawClasspath(newClasspath, monitor);
	}

	private IProgressMonitor nonNullMonitor(IProgressMonitor monitor) {
		return (monitor != null) ? monitor : new NullProgressMonitor();
	}

}
