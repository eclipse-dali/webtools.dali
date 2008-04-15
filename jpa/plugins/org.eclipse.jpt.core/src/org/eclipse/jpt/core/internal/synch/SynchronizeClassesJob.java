/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.jpt.core.internal.synch;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * Synchronizes the lists of persistent classes in a persistence unit and a 
 * persistence project.
 */
public class SynchronizeClassesJob extends WorkspaceJob
{
	private IFile persistenceXmlFile;
	
	
	public SynchronizeClassesJob(IFile file) {
		super(JptCoreMessages.SYNCHRONIZE_CLASSES_JOB);
		setRule(file.getProject());
		this.persistenceXmlFile = file;
	}
	
	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(JptCoreMessages.SYNCHRONIZING_CLASSES_TASK, 200);
		
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		
		JpaProject jpaProject = JptCorePlugin.getJpaProject(this.persistenceXmlFile.getProject());

		PersistenceArtifactEdit persistenceArtifactEdit = PersistenceArtifactEdit.getArtifactEditForWrite(this.persistenceXmlFile.getProject());
		PersistenceResource persistenceResource = persistenceArtifactEdit.getResource(this.persistenceXmlFile);
		
		monitor.worked(25);

		XmlPersistence persistence = persistenceResource.getPersistence();
		
		if (persistence == null) {
			persistence = PersistenceFactory.eINSTANCE.createXmlPersistence();
			persistenceResource.getContents().add(persistence);
		}
		
		XmlPersistenceUnit persistenceUnitResource;
		
		if (persistence.getPersistenceUnits().size() > 0) {
			persistenceUnitResource = persistence.getPersistenceUnits().get(0);
		}
		else {
			persistenceUnitResource = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
			persistenceUnitResource.setName(this.persistenceXmlFile.getProject().getName());
			persistence.getPersistenceUnits().add(persistenceUnitResource);
		}
		
		persistenceUnitResource.getClasses().clear();
		
		monitor.worked(25);		
		
		for (IType type : CollectionTools.iterable(jpaProject.annotatedClasses())) {
			String fullyQualifiedTypeName = type.getFullyQualifiedName('.');
			if (!mappingFileContains(jpaProject, fullyQualifiedTypeName)) {
				XmlJavaClassRef classRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
				classRef.setJavaClass(fullyQualifiedTypeName);
				persistenceUnitResource.getClasses().add(classRef);
			}
		}
		
		monitor.worked(100);
		
		try {
			persistenceResource.save(null);
		}
		catch (IOException ioe) {
			return new Status(IStatus.ERROR, JptCorePlugin.PLUGIN_ID, JptCoreMessages.ERROR_WRITING_FILE, ioe);
		}
		finally {
			persistenceArtifactEdit.dispose();			
			monitor.done();
		}

		return Status.OK_STATUS;
	}
	
	private boolean mappingFileContains(JpaProject jpaProject, String fullyQualifiedTypeName) {
		PersistenceXml persistenceXml = jpaProject.getRootContext().getPersistenceXml();
		if (persistenceXml == null) {
			return false;
		}
		Persistence persistence = persistenceXml.getPersistence();
		if (persistence == null) {
			return false;
		}
		if (persistence.persistenceUnitsSize() == 0) {
			return false;
		}
		PersistenceUnit persistenceUnit = persistence.persistenceUnits().next();
		for (MappingFileRef mappingFileRef : CollectionTools.iterable(persistenceUnit.mappingFileRefs())) {
			if (mappingFileRef.getPersistentType(fullyQualifiedTypeName) != null) {
				return true;
			}
		}
		return false;
	}
}