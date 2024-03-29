/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.facet;

import static org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties.CONTAINER_PATH;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.operations.PersistenceFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.validation.ValidationFramework;

/**
 * We don't really "install" the JPA facet here. We simply store all the various
 * data model properties in the appropriate preferences. These settings will
 * used in the POST_INSTALL event listener to build the JPA project.
 */
public class JpaFacetInstallDelegate 
	extends JpaFacetActionDelegate
	implements JpaFacetInstallDataModelProperties
{
	@Override
	protected void execute_(
			IProject project, IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException {
		
		SubMonitor sm = SubMonitor.convert(monitor, 10);
		
		ValidationFramework.getDefault().addValidationBuilder(project);
		
		super.execute_(project, fv, config, sm.newChild(1));
		
		IJavaProject javaProject = JavaCore.create(project);
		IDataModel dataModel = (IDataModel) config;
		
		// project settings
		this.addDbDriverLibraryToClasspath(javaProject, dataModel, sm.newChild(1));

		// create project XML files
		this.createProjectXml(project, sm.newChild(8));
	}
	
	protected void addDbDriverLibraryToClasspath(
			IJavaProject javaProject, IDataModel dataModel, 
			IProgressMonitor monitor) throws CoreException {
		
		if( ! dataModel.getBooleanProperty(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			return;
		}
		String driverName = dataModel.getStringProperty(DB_DRIVER_NAME);
		
		ConnectionProfileFactory factory = this.getConnectionProfileFactory(javaProject.getProject());
		if (factory == null) {
			return;
		}
		IClasspathContainer container = factory.buildDriverClasspathContainer(driverName);
		IClasspathEntry entry = JavaCore.newContainerEntry(container.getPath());
		this.addClasspathEntryToProject(entry, javaProject, monitor);
	}
	
	private ConnectionProfileFactory getConnectionProfileFactory(IProject project) {
		JpaWorkspace jpaWorkspace = this.getJpaWorkspace(project);
		return (jpaWorkspace == null) ? null : jpaWorkspace.getConnectionProfileFactory();
	}

	private void addClasspathEntryToProject(
			IClasspathEntry classpathEntry, IJavaProject javaProject, IProgressMonitor monitor) 
			throws CoreException {
		
		// add the classpath entry to the classpath if it is not already present
		IClasspathEntry[] classpath = javaProject.getRawClasspath();
		if ( ! ArrayTools.contains(classpath, classpathEntry)) {
			javaProject.setRawClasspath(ArrayTools.add(classpath, classpathEntry), monitor);
		}
	}
	
	private void createProjectXml(IProject project, IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 1);
		this.createPersistenceXml(project, sm.newChild(1));
	}

	private void createPersistenceXml(IProject project, IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 5);

		IDataModel config = DataModelFactory.createDataModel(new PersistenceFileCreationDataModelProvider());
		config.setProperty(CONTAINER_PATH, defaultResourceLocation(project));
		sm.worked(1);
		// default values for all other properties should suffice
		try {
			config.getDefaultOperation().execute(sm.newChild(4), null);
		} catch (ExecutionException ex) {
			JptJpaCorePlugin.instance().logError(ex);
		}
	}

	protected IPath defaultResourceLocation(IProject project) {
		ProjectResourceLocator resourceLocator = (ProjectResourceLocator) project.getAdapter(ProjectResourceLocator.class);
		if (resourceLocator == null) {
			JptJpaCorePlugin.instance().logError("No resource locator for project: " + project); //$NON-NLS-1$
			return null;
		}
		return resourceLocator.getDefaultLocation().getFullPath();
	}
}
