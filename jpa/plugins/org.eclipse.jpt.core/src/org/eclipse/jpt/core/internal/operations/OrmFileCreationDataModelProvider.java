/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

import java.util.Iterator;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;

public class OrmFileCreationDataModelProvider extends AbstractDataModelProvider
	implements OrmFileCreationDataModelProperties
{
	/**
	 * required default constructor
	 */
	public OrmFileCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public IDataModelOperation getDefaultOperation() {
		return new OrmFileCreationOperation(getDataModel());
	}
	
	@Override
	public Set<String> getPropertyNames() {
		@SuppressWarnings("unchecked")
		Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(PROJECT_NAME);
		propertyNames.add(SOURCE_FOLDER);
		propertyNames.add(FILE_PATH);
		propertyNames.add(DEFAULT_ACCESS);
		propertyNames.add(ADD_TO_PERSISTENCE_UNIT);
		propertyNames.add(PERSISTENCE_UNIT);
		return propertyNames;
	}
	
	@Override
	public boolean isPropertyEnabled(String propertyName) {
		if (propertyName.equals(PERSISTENCE_UNIT)) {
			return getBooleanProperty(ADD_TO_PERSISTENCE_UNIT);
		}
		return super.isPropertyEnabled(propertyName);
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(SOURCE_FOLDER)) {
			IFolder sourceFolder = getDefaultSourceFolder();
			if (sourceFolder != null && sourceFolder.exists()) {
				return sourceFolder.getFullPath().toPortableString();
			}
		}
		else if (propertyName.equals(FILE_PATH)) {
			return new Path(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH).toPortableString();
		}
		else if (propertyName.equals(DEFAULT_ACCESS)) {
			return null;
		}
		else if (propertyName.equals(ADD_TO_PERSISTENCE_UNIT)) {
			return Boolean.FALSE;
		}
		else if (propertyName.equals(PERSISTENCE_UNIT)) {
			PersistenceUnit pUnit = getDefaultPersistenceUnit();
			if (pUnit != null) {
				return pUnit.getName();
			}
		}
		return super.getDefaultProperty(propertyName);
	}
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		if (propertyName.equals(PROJECT_NAME)) {
			this.model.notifyPropertyChange(SOURCE_FOLDER, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(PERSISTENCE_UNIT, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(PERSISTENCE_UNIT, IDataModel.VALID_VALUES_CHG);
		}
		else if (propertyName.equals(ADD_TO_PERSISTENCE_UNIT)) {
			this.model.notifyPropertyChange(PERSISTENCE_UNIT, IDataModel.ENABLE_CHG);
		}
		return ok;
	}
	
	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(PROJECT_NAME)) {
			return CollectionTools.array(
				new TransformationIterator<IProject, DataModelPropertyDescriptor>(jpaIProjects()) {
					@Override
					protected DataModelPropertyDescriptor transform(IProject next) {
						return new DataModelPropertyDescriptor(next.getName());
					}
				},
				new DataModelPropertyDescriptor[0]);
		}
		else if (propertyName.equals(DEFAULT_ACCESS)) {
			DataModelPropertyDescriptor[] accessTypes = new DataModelPropertyDescriptor[3];
			accessTypes[0] = accessPropertyDescriptor(null);
			accessTypes[1] = accessPropertyDescriptor(AccessType.FIELD);
			accessTypes[2] = accessPropertyDescriptor(AccessType.PROPERTY);
			return accessTypes;
		}
		else if (propertyName.equals(PERSISTENCE_UNIT)) {
			return CollectionTools.array(
				new TransformationIterator<String, DataModelPropertyDescriptor>(new CompositeIterator<String>(null, persistenceUnitNames())) {
					@Override
					protected DataModelPropertyDescriptor transform(String next) {
						return persistenceUnitPropertyDescriptor(next);
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
		else if (propertyName.equals(DEFAULT_ACCESS)) {
			return accessPropertyDescriptor((AccessType) getProperty(DEFAULT_ACCESS)); 
		}
		else if (propertyName.equals(PERSISTENCE_UNIT)) {
			return persistenceUnitPropertyDescriptor(getStringProperty(PERSISTENCE_UNIT));
		}
		return super.getPropertyDescriptor(propertyName);
	}
	
	private DataModelPropertyDescriptor accessPropertyDescriptor(AccessType accessType) {
		if (accessType == null) {
			return new DataModelPropertyDescriptor(null, JptCoreMessages.NONE);
		}
		return new DataModelPropertyDescriptor(accessType, accessType.getName());
	}
	
	DataModelPropertyDescriptor persistenceUnitPropertyDescriptor(String persistenceUnitName) {
		if (StringTools.stringIsEmpty(persistenceUnitName)) {
			return new DataModelPropertyDescriptor(null, JptCoreMessages.NONE);
		}
		return new DataModelPropertyDescriptor(persistenceUnitName);
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public IStatus validate(String propertyName) {
		if (propertyName.equals(PROJECT_NAME)
				|| propertyName.equals(SOURCE_FOLDER)
				|| propertyName.equals(FILE_PATH)) {
			return validateProjectSourceFolderAndFilePath();
		}
		else if (propertyName.equals(ADD_TO_PERSISTENCE_UNIT)
				|| propertyName.equals(PERSISTENCE_UNIT)) {
			return validatePersistenceUnit();
		}
		return super.validate(propertyName);
	}
	
	private IStatus validateProjectSourceFolderAndFilePath() {
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
		if (getVerifiedJavaSourceFolder() == null) {
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				NLS.bind(JptCoreMessages.VALIDATE_SOURCE_FOLDER_NOT_SOURCE_FOLDER, sourceFolderPath));
		}
		if (getExistingOrmFile() != null) {
			return new Status(
				IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
				JptCoreMessages.VALIDATE_ORM_FILE_ALREADY_EXISTS);
		}
		return Status.OK_STATUS;
	}
	
	private IStatus validatePersistenceUnit() {
		boolean addToPUnit = getBooleanProperty(ADD_TO_PERSISTENCE_UNIT);
		String projectName = getStringProperty(PROJECT_NAME);
		String pUnitName = getStringProperty(PERSISTENCE_UNIT);
		if (addToPUnit) {
			if (StringTools.stringIsEmpty(pUnitName)) {
				return new Status(
					IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
					NLS.bind(JptCoreMessages.VALIDATE_PERSISTENCE_UNIT_DOES_NOT_SPECIFIED, pUnitName));
			}
			if (getPersistenceUnit() == null) {
				return new Status(
					IStatus.ERROR, JptCorePlugin.PLUGIN_ID,
					NLS.bind(JptCoreMessages.VALIDATE_PERSISTENCE_UNIT_NOT_IN_PROJECT, pUnitName, projectName));
			}
		}
		return Status.OK_STATUS;
	}
	
	
	// **************** helper methods *****************************************
	
	// Copied from ArtifactEditOperationDataModelProvider
	private IProject getProject() {
		String projectName = (String) model.getProperty(PROJECT_NAME);
		if (StringTools.stringIsEmpty(projectName)) {
			return null;
		}
		return ProjectUtilities.getProject(projectName);
	}
	
	private JpaProject getJpaProject() {
		IProject project = getProject();
		if (project == null) {
			return null;
		}
		return JptCorePlugin.getJpaProject(project);
	}
	
	/**
	 * Return a best guess java source folder for the specified project
	 */
	// Copied from NewJavaClassDataModelProvider
	private IFolder getDefaultSourceFolder() {
		IProject project = getProject();
		if (project == null) {
			return null;
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
	 * Return whether the path provided can not be a valid IFolder path
	 */
	private boolean sourceFolderIsIllegal(String folderPath) {
		IProject project = getProject();
		if (project == null) {
			return false;
		}
		try {
			project.getWorkspace().getRoot().getFolder(new Path(folderPath));
		}
		catch (IllegalArgumentException e) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return whether the path provided is in the current project
	 */
	private boolean sourceFolderNotInProject(String folderPath) {
		IProject project = getProject();
		if (project == null) {
			return false;
		}
		IFolder folder;
		try {
			folder = project.getWorkspace().getRoot().getFolder(new Path(folderPath));
		}
		catch (IllegalArgumentException e) {
			return false;
		}
		return ! project.equals(folder.getProject());
	}
	
	/**
	 * Return an IFolder represented by the SOURCE_FOLDER property, verified
	 * to exist
	 */
	private IFolder getVerifiedSourceFolder() {
		String folderPath = getStringProperty(SOURCE_FOLDER);
		IProject project = getProject();
		if (project == null) {
			return null;
		}
		IFolder folder;
		try {
			folder = project.getWorkspace().getRoot().getFolder(new Path(folderPath));
		}
		catch (IllegalArgumentException e) {
			return null;
		}
		if (folder == null || ! folder.exists()) {
			return null;
		}
		return folder;
	}
	
	/**
	 * Return the source folder, provided it is verified to be an actual java
	 * source folder
	 */
	private IFolder getVerifiedJavaSourceFolder() {
		IFolder folder = getVerifiedSourceFolder();
		if (folder == null) {
			return null;
		}
		IJavaProject jProject = JavaCore.create(getProject());
		if (jProject == null) {
			return null;
		}
		IPackageFragmentRoot packageFragmentRoot = jProject.getPackageFragmentRoot(folder);
		if (packageFragmentRoot == null || ! packageFragmentRoot.exists()) {
			return null;
		}
		return folder;
	}
	
	private IFile getExistingOrmFile() {
		IFolder folder = getVerifiedSourceFolder();
		if (folder == null) {
			return null;
		}
		String filePath = getStringProperty(FILE_PATH);
		IFile existingFile = folder.getFile(new Path(filePath));
		if (! existingFile.exists()) {
			return null;
		}
		return existingFile;
	}
	
	private PersistenceUnit getDefaultPersistenceUnit() {
		JpaProject jpaProject = getJpaProject();
		if (jpaProject == null) {
			return null;
		}
		PersistenceXml persistenceXml = jpaProject.getRootContext().getPersistenceXml();
		if (persistenceXml == null) {
			return null;
		}
		Persistence persistence = persistenceXml.getPersistence();
		if (persistence == null) {
			return null;
		}
		if (persistence.persistenceUnitsSize() == 0) {
			return null;
		}
		return persistence.persistenceUnits().next();
	}
	
	private PersistenceUnit getPersistenceUnit() {
		String pUnitName = getStringProperty(PERSISTENCE_UNIT);
		JpaProject jpaProject = 
			(StringTools.stringIsEmpty(pUnitName)) ? null : getJpaProject();
		PersistenceXml persistenceXml = 
			(jpaProject == null) ? null : jpaProject.getRootContext().getPersistenceXml();
		Persistence persistence = 
			(persistenceXml == null) ? null : persistenceXml.getPersistence();
		if (persistence != null) {
			for (Iterator<PersistenceUnit> stream = persistence.persistenceUnits(); stream.hasNext(); ) {
				PersistenceUnit next = stream.next();
				if (pUnitName.equals(next.getName())) {
					return next;
				}
			}
		}
		return null;
	}
	
	private Iterator<IProject> jpaIProjects() {
		return new FilteringIterator<IProject, IProject>(CollectionTools.iterator(ProjectUtilities.getAllProjects())) {
			@Override
			protected boolean accept(IProject project) {
				try {
					return FacetedProjectFramework.hasProjectFacet(project, JptCorePlugin.FACET_ID);
				}
				catch (CoreException ce) {
					return false;
				}
			}
		};
	}
	
	private Iterator<PersistenceUnit> persistenceUnits() {
		return new CompositeIterator<PersistenceUnit>(
			new TransformationIterator<IProject, Iterator<PersistenceUnit>>(jpaIProjects()) {
				@Override
				protected Iterator<PersistenceUnit> transform(IProject jpaIProject) {
					JpaProject jpaProject = JptCorePlugin.getJpaProject(jpaIProject);
					PersistenceXml persistenceXml = 
						(jpaProject == null) ? null : jpaProject.getRootContext().getPersistenceXml();
					Persistence persistence = 
						(persistenceXml == null) ? null : persistenceXml.getPersistence();
					return (persistence == null) ? EmptyIterator.<PersistenceUnit>instance() : persistence.persistenceUnits();
				}
			});
	}
	
	private Iterator<String> persistenceUnitNames() {
		return new TransformationIterator<PersistenceUnit, String>(persistenceUnits()) {
			@Override
			protected String transform(PersistenceUnit next) {
				return next.getName();
			}
		};
	}
}
