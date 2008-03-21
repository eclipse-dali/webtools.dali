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
import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;

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
		monitor.beginTask(JptCoreMessages.SYNCHRONIZING_CLASSES_TASK, 100);
		
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		
		JpaProject jpaProject = JptCorePlugin.jpaProject(this.persistenceXmlFile.getProject());

		PersistenceArtifactEdit persistenceArtifactEdit = PersistenceArtifactEdit.getArtifactEditForWrite(this.persistenceXmlFile.getProject());
		PersistenceResource persistenceResource = persistenceArtifactEdit.getResource(this.persistenceXmlFile);
		
		monitor.worked(20);

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
		
		monitor.worked(20);

		//TODO njh - should be checking to see if the reference is necessary
		//			ref is not necessary if defined in the XML, see commented code below
		Iterator<IType> stream = jpaProject.annotatedClasses();
		
		monitor.worked(20);
		while (stream.hasNext()) {
			XmlJavaClassRef classRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
			classRef.setJavaClass(stream.next().getFullyQualifiedName());
			persistenceUnitResource.getClasses().add(classRef);
		}
		
		monitor.worked(20);
		
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
}
//	private Iterator<String> sortedMappedTypeNames(XmlPersistenceUnit persistenceUnit) {
//		return CollectionTools.sort(this.mappedTypeNames(persistenceUnit));
//	}
//	
//	private Iterator<String> mappedTypeNames(XmlPersistenceUnit persistenceUnit) {
//		return new TransformationIterator<IPersistentType, String>(this.mappedTypes(persistenceUnit)) {
//			@Override
//			protected String transform(IPersistentType pType) {
//				return pType.resource().findJdtType().getFullyQualifiedName();
//			}
//		};
//	}
//	
//	private Iterator<IPersistentType> mappedTypes(XmlPersistenceUnit persistenceUnit) {
//		return new FilteringIterator<IPersistentType>(allJavaTypes(persistenceUnit.getJpaProject()), filter(persistenceUnit));
//	}
//	
//	private Iterator<IPersistentType> allJavaTypes(IJpaProject jpaProject) {
//		return new TransformationIterator<IJpaFile, IPersistentType>(jpaProject.jpaFiles(JptCorePlugin.JAVA_CONTENT_TYPE).iterator()) {
//			@Override
//			protected IPersistentType transform(IJpaFile next) {
//				JpaCompilationUnit jcu = (JpaCompilationUnit) next.getContent();
//				return (jcu.getTypes().isEmpty()) ? null : jcu.getTypes().get(0);
//			}
//		};
//	}
//	
//	private Filter<IPersistentType> filter(final XmlPersistenceUnit persistenceUnit) {
//		return new Filter<IPersistentType>() {
//			public boolean accept(IPersistentType o) {
//				if (o == null) {
//					return false;
//				}
//				if (o.getMappingKey() == IMappingKeys.NULL_TYPE_MAPPING_KEY) {
//					return false;
//				}
//				IType jdtType = o.findJdtType();
//				if (jdtType == null) {
//					return false;
//				}
//				for (XmlMappingFileRef mappingFileRef : persistenceUnit.getMappingFiles()) {
//					if (containsType(mappingFileRef, jdtType)) {
//						return false;
//					}
//				}
//				return true;
//			}
//		};
//	}
//	
//	private boolean containsType(XmlMappingFileRef mappingFileRef, IType jdtType) {
//		IJpaFile mappingFile = mappingFileRef.getMappingFile();
//		if (mappingFile == null) {
//			return false;
//		}
//		
//		XmlRootContentNode root;
//		try {
//			root = (XmlRootContentNode) mappingFile.getContent();
//		}
//		catch (ClassCastException cce) {
//			return false;
//		}
//		
//		EntityMappingsInternal entityMappings = root.getEntityMappings();
//		
//		if (entityMappings == null) {
//			return false;
//		}
//		
//		for (IPersistentType persistentType : entityMappings.getPersistentTypes()) {
//			IType otherJdtType = persistentType.findJdtType();
//			if (otherJdtType != null && otherJdtType.equals(jdtType)) {
//				return true;
//			}
//		}
//		
//		return false;
//	}

