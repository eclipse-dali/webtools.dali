/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

import java.util.Iterator;
import java.util.Set;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.AbstractJpaProject;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public abstract class AbstractJpaFileCreationDataModelProvider
	extends AbstractDataModelProvider
	implements JpaFileCreationDataModelProperties
{
	protected AbstractJpaFileCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public Set<String> getPropertyNames() {
		@SuppressWarnings("unchecked")
		Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(PROJECT_NAME);
		propertyNames.add(SOURCE_FOLDER);
		propertyNames.add(FILE_PATH);
		propertyNames.add(VERSION);
		return propertyNames;
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(SOURCE_FOLDER)) {
			IContainer sourceFolder = getDefaultSourceFolder();
			if (sourceFolder != null && sourceFolder.exists()) {
				return sourceFolder.getFullPath().toPortableString();
			}
		}
		else if (propertyName.equals(FILE_PATH)) {
			return getDefaultFilePath();
		}
		else if (propertyName.equals(VERSION)) {
			return getDefaultVersion();
		}
		return super.getDefaultProperty(propertyName);
	}
	
	protected abstract String getDefaultFilePath();
	
	protected abstract String getDefaultVersion();
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		if (propertyName.equals(PROJECT_NAME)) {
			this.model.notifyPropertyChange(SOURCE_FOLDER, IDataModel.DEFAULT_CHG);
		}
		return ok;
	}
	
	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(PROJECT_NAME)) {
			return ArrayTools.array(
				new TransformationIterator<IProject, DataModelPropertyDescriptor>(jpaIProjects()) {
					@Override
					protected DataModelPropertyDescriptor transform(IProject next) {
						return new DataModelPropertyDescriptor(next.getName());
					}
				},
				new DataModelPropertyDescriptor[0]);
		}
		return super.getValidPropertyDescriptors(propertyName);
	}
	
	@Override
	public DataModelPropertyDescriptor getPropertyDescriptor(String propertyName) {
		if (propertyName.equals(PROJECT_NAME)) {
			return new DataModelPropertyDescriptor(getStringProperty(PROJECT_NAME));
		}
		return super.getPropertyDescriptor(propertyName);
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public IStatus validate(String propertyName) {
		IStatus status = Status.OK_STATUS;
		if (propertyName.equals(PROJECT_NAME)
				|| propertyName.equals(SOURCE_FOLDER)
				|| propertyName.equals(FILE_PATH)) {
			status = validateProjectSourceFolderAndFilePath();
		}
		if (! status.isOK()) {
			return status;
		}
		
		if (propertyName.equals(PROJECT_NAME)
				|| propertyName.equals(VERSION)) {
			status = validateVersion();
		}
		if (! status.isOK()) {
			return status;
		}
		
		return status;
	}
	
	protected IStatus validateProjectSourceFolderAndFilePath() {
		String projectName = (String) getProperty(PROJECT_NAME);
		if (StringTools.stringIsEmpty(projectName)) {
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID, 
				JptCoreMessages.VALIDATE_PROJECT_NOT_SPECIFIED);
		}
		String sourceFolderPath = getStringProperty(SOURCE_FOLDER);
		if (StringTools.stringIsEmpty(sourceFolderPath)) {
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				JptCoreMessages.VALIDATE_SOURCE_FOLDER_NOT_SPECIFIED);
		}
		if (sourceFolderIsIllegal(sourceFolderPath)) {
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				JptCoreMessages.VALIDATE_SOURCE_FOLDER_ILLEGAL);
		}
		if (sourceFolderNotInProject(sourceFolderPath)) {
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				NLS.bind(
					JptCoreMessages.VALIDATE_SOURCE_FOLDER_NOT_IN_PROJECT, 
					sourceFolderPath, projectName));
		}
		if (getVerifiedSourceFolder() == null) {
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				NLS.bind(JptCoreMessages.VALIDATE_SOURCE_FOLDER_DOES_NOT_EXIST, sourceFolderPath));
		}
		String filePath = getStringProperty(FILE_PATH);
		if (StringTools.stringIsEmpty(filePath)) {
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				JptCoreMessages.VALIDATE_FILE_PATH_NOT_SPECIFIED);
		}
		if (getExistingFile() != null) {
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				JptCoreMessages.VALIDATE_FILE_ALREADY_EXISTS);
		}
		return Status.OK_STATUS;
	}
	
	protected IStatus validateVersion() {
		if (getProject() == null) {
			return Status.OK_STATUS;
		}
		String fileVersion = getStringProperty(VERSION);
		if (! fileVersionSupported(fileVersion)) {
			return new Status(
					IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
					JptCoreMessages.VALIDATE_FILE_VERSION_NOT_SUPPORTED);
		}
		try {
			String jpaFacetVersion = getJpaFacetVersion(getProject());
			if (! fileVersionSupportedForFacetVersion(fileVersion, jpaFacetVersion)) {
				return new Status(
						IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
						JptCoreMessages.VALIDATE_FILE_VERSION_NOT_SUPPORTED_FOR_FACET_VERSION);
			}
		}
		catch (CoreException ce) {
			// project should have been validated already, so assume that this will never get hit
			// fall through to final return
		}
		return Status.OK_STATUS;
	}
	
	protected abstract boolean fileVersionSupported(String fileVersion);
	
	protected abstract boolean fileVersionSupportedForFacetVersion(String fileVersion, String jpaFacetVersion);
	
	
	// **************** helper methods *****************************************
	
	// Copied from ArtifactEditOperationDataModelProvider
	protected IProject getProject() {
		String projectName = (String) model.getProperty(PROJECT_NAME);
		if (StringTools.stringIsEmpty(projectName)) {
			return null;
		}
		return ProjectUtilities.getProject(projectName);
	}
	
	protected JpaProject getJpaProject() {
		IProject project = getProject();
		return (project == null) ? null : JptCorePlugin.getJpaProject(project);
	}
	
	/**
	 * Return a best guess source folder for the specified project
	 */
	protected IContainer getDefaultSourceFolder() {
		IProject project = getProject();
		if (project == null) {
			return null;
		}
		IContainer folder = AbstractJpaProject.getBundleRoot(project);
		if (folder != null) {
			return folder;
		}
		IPackageFragmentRoot[] sources = J2EEProjectUtilities.getSourceContainers(project);
		// Try and return the first source folder
		if (sources.length > 0) {
			try {
				return (IFolder) sources[0].getCorrespondingResource();
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Return whether the path provided can not be a valid IContainer path
	 */
	protected boolean sourceFolderIsIllegal(String containerPath) {
		IProject project = getProject();
		if (project == null) {
			return false;
		}
		IContainer container = PlatformTools.getContainer(new Path(containerPath));
		if (container == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return whether the path provided is in the current project
	 */
	protected boolean sourceFolderNotInProject(String folderPath) {
		IProject project = getProject();
		if (project == null) {
			return false;
		}
		IContainer container = PlatformTools.getContainer(new Path(folderPath));
		return ! project.equals(container.getProject());
	}
	
	/**
	 * Return an IContainer represented by the SOURCE_FOLDER property, verified
	 * to exist
	 */
	protected IContainer getVerifiedSourceFolder() {
		String folderPath = getStringProperty(SOURCE_FOLDER);
		IProject project = getProject();
		if (project == null) {
			return null;
		}
		IContainer container = PlatformTools.getContainer(new Path(folderPath));
		if (container == null || ! container.exists()) {
			return null;
		}
		return container;
	}
	
	protected IFile getExistingFile() {
		IContainer container = getVerifiedSourceFolder();
		if (container == null) {
			return null;
		}
		String filePath = getStringProperty(FILE_PATH);
		IFile existingFile = container.getFile(new Path(filePath));
		if (! existingFile.exists()) {
			return null;
		}
		return existingFile;
	}
	
	protected Iterator<IProject> jpaIProjects() {
		return new FilteringIterator<IProject>(this.allIProjects(), this.buildJpaIProjectsFilter());
	}

	protected Iterator<IProject> allIProjects() {
		return CollectionTools.iterator(ProjectUtilities.getAllProjects());
	}

	protected Filter<IProject> buildJpaIProjectsFilter() {
		return new JpaIProjectsFilter();
	}

	protected class JpaIProjectsFilter implements Filter<IProject> {
		public boolean accept(IProject project) {
			try {
				return this.accept_(project);
			} catch (CoreException ex) {
				return false;
			}
		}
		protected boolean accept_(IProject project) throws CoreException {
			return hasJpaFacet(project) && hasSupportedPlatformId(project);
		}
	}
	
	protected boolean hasJpaFacet(IProject project) throws CoreException {
		return FacetedProjectFramework.hasProjectFacet(project, JptCorePlugin.FACET_ID);
	}
	
	protected String getJpaFacetVersion(IProject project) throws CoreException {
		IFacetedProject fproj = ProjectFacetsManager.create(project);
		return fproj.getProjectFacetVersion(
				ProjectFacetsManager.getProjectFacet(JptCorePlugin.FACET_ID)).getVersionString();
	}
	
	protected boolean hasSupportedPlatformId(IProject project) {
		JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
		return (jpaProject != null) && isSupportedPlatformId(jpaProject.getJpaPlatform().getId());
	}
	
	protected boolean isSupportedPlatformId(String id) {
		return true;
	}
}
