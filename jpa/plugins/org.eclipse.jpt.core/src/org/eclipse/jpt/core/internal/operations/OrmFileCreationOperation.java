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
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModelProvider;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.wst.common.frameworks.datamodel.AbstractDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class OrmFileCreationOperation extends AbstractDataModelOperation
	implements OrmFileCreationDataModelProperties
{
	public OrmFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// Create source folder if it does not exist
		IFolder folder = createSourceFolder();
		// Create orm file
		createMappingFile(folder);
		// Add orm file to persistence unit if specified
		addMappingFileToPersistenceXml();
		return OK_STATUS;
	}
	
	protected IProject getProject() throws ExecutionException {
		String projectName = (String) getDataModel().getProperty(PROJECT_NAME);
		IProject project = ProjectUtilities.getProject(projectName);
		if (project == null) {
			throw new ExecutionException("No project name specified"); //$NON-NLS-1$
		}
		return project;
	}
	
	protected JpaProject getJpaProject() throws ExecutionException {
		IProject project = getProject();
		JpaProject jpaProject = JptCorePlugin.getJpaProject(project);
		if (jpaProject == null) {
			throw new ExecutionException("Project does not have JPA content"); //$NON-NLS-1$
		}
		return jpaProject;
	}
	
	protected PersistenceUnit getPersistenceUnit() throws ExecutionException {
		String pUnitName = getDataModel().getStringProperty(PERSISTENCE_UNIT);
		JpaProject jpaProject = getJpaProject();
		PersistenceXml persistenceXml = jpaProject.getRootContextNode().getPersistenceXml();
		if (persistenceXml == null) {
			throw new ExecutionException("Project does not have a persistence.xml file"); //$NON-NLS-1$
		}
		Persistence persistence = persistenceXml.getPersistence();
		if (persistence == null) {
			throw new ExecutionException("persistence.xml does not have a persistence node."); //$NON-NLS-1$
		}
		for (Iterator<PersistenceUnit> stream = persistence.persistenceUnits(); stream.hasNext(); ) {
			PersistenceUnit pUnit = stream.next();
			if (pUnitName.equals(pUnit.getName())) {
				return pUnit;
			}
		}
		throw new ExecutionException("persistence.xml does not have persistence unit named \'" + pUnitName + "\'"); //$NON-NLS-1$
	}
	
	/**
	 * This method will return the source folder as specified in the data model. 
	 * It will create the source folder if it does not exist. It will not add
	 * it as a source folder to the project build path if it is not already.
	 * This method may return null.
	 */
	// copied from NewJavaClassOperation
	protected IFolder createSourceFolder() throws ExecutionException {
		// Get the source folder name from the data model
		String folderPath = model.getStringProperty(SOURCE_FOLDER);
		IProject project = getProject();
		IFolder folder = project.getWorkspace().getRoot().getFolder(new Path(folderPath));
		// If folder does not exist, create the folder with the specified path
		if (! folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				throw new ExecutionException("Could not create folder", e); //$NON-NLS-1$
			}
		}
		// Return the source folder
		return folder;
	}
	
	@SuppressWarnings("unchecked")
	protected void createMappingFile(IFolder folder) {
		String filePath = getDataModel().getStringProperty(FILE_PATH);
		IFile file = folder.getFile(new Path(filePath));
		final OrmResourceModelProvider modelProvider =
			OrmResourceModelProvider.getModelProvider(file);
		
		modelProvider.modify(new Runnable() {
				public void run() {
					OrmResource ormResource = modelProvider.getResource();
					
					XmlEntityMappings entityMappings = OrmFactory.eINSTANCE.createXmlEntityMappings();
					entityMappings.setVersion("1.0"); //$NON-NLS-1$
					ormResource.getContents().add(entityMappings);
					
					AccessType defaultAccess = (AccessType) getDataModel().getProperty(DEFAULT_ACCESS); 
					if (defaultAccess != null) {
						XmlPersistenceUnitMetadata puMetadata = OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
						entityMappings.setPersistenceUnitMetadata(puMetadata);
						XmlPersistenceUnitDefaults puDefaults = OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
						puMetadata.setPersistenceUnitDefaults(puDefaults);
						puDefaults.setAccess(defaultAccess);
					}
				}
			});
	}
	
	protected void addMappingFileToPersistenceXml() throws ExecutionException {
		if (! getDataModel().getBooleanProperty(ADD_TO_PERSISTENCE_UNIT)) {
			return;
		}
		
		PersistenceResourceModelProvider modelProvider =
			PersistenceResourceModelProvider.getDefaultModelProvider(getProject());
		final PersistenceUnit pUnit = getPersistenceUnit();
		modelProvider.modify(new Runnable() {
				public void run() {
					String filePath = getDataModel().getStringProperty(FILE_PATH);
					for (Iterator<MappingFileRef> stream = pUnit.specifiedMappingFileRefs(); stream.hasNext(); ) {
						if (filePath.equals(stream.next().getFileName())) {
							return;
						}
					}
					MappingFileRef mfRef = pUnit.addSpecifiedMappingFileRef();
					mfRef.setFileName(new Path(filePath).toPortableString());
		
				}
			});
	}
}
