/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.io.IOException;
import java.util.Iterator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jem.util.emf.workbench.WorkbenchResourceHelperBase;
import org.eclipse.jpt.core.internal.JpaModelManager;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmXmlResource;
import org.eclipse.jpt.core.internal.content.persistence.Persistence;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceResource;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaFacetPostInstallDelegate 
	implements IDelegate, IJpaFacetDataModelProperties
{
	private final static String ORM_XML_FILE_PATH = "META-INF/orm.xml";
	
	public void execute(IProject project, IProjectFacetVersion fv, 
			Object config, IProgressMonitor monitor) throws CoreException {
		
		if (monitor != null) {
			monitor.beginTask("", 2); //$NON-NLS-1$
		}
		
		JpaModelManager.instance().createJpaProject(project);
		IDataModel dataModel = (IDataModel) config;
		JpaFacetUtils.setPlatform(project, dataModel.getStringProperty(PLATFORM_ID));
		JpaFacetUtils.setConnectionName(project, dataModel.getStringProperty(CONNECTION));
		JpaFacetUtils.setDiscoverAnnotatedClasses(project, dataModel.getBooleanProperty(DISCOVER_ANNOTATED_CLASSES));
		
		createPersistenceXml(project, dataModel);
		
		if (dataModel.getBooleanProperty(CREATE_ORM_XML)) {
			createOrmXml(project, dataModel);
		}
		
		if (monitor != null) {
			monitor.worked(1);
		}
		
		JpaModelManager.instance().fillJpaProject(project);
		
		if (monitor != null) {
			monitor.worked(2);
		}
	}
	
	private void createPersistenceXml(IProject project, IDataModel dataModel) {
		String sourceFolder = computeSourceFolder(project);
		
		URI fileURI = URI.createPlatformResourceURI(sourceFolder + "/META-INF/persistence.xml", false);
		PersistenceResource resource = (PersistenceResource) getResourceSet(project).createResource(fileURI);
		Persistence persistence = PersistenceFactory.eINSTANCE.createPersistence();
		persistence.setVersion("1.0");
		PersistenceUnit pUnit = PersistenceFactory.eINSTANCE.createPersistenceUnit();
		pUnit.setName(project.getName());
		persistence.getPersistenceUnits().add(pUnit);
		
		try {
			resource.getContents().add(persistence);
			resource.save(null);
		}
		catch (IOException e) {
			JptCorePlugin.log(e);
		}
	}
	
	private void createOrmXml(IProject project, IDataModel dataModel) {
		String sourceFolder = computeSourceFolder(project);
		
		URI fileURI = URI.createPlatformResourceURI(sourceFolder + "/" + ORM_XML_FILE_PATH, false);
		OrmXmlResource resource = (OrmXmlResource) getResourceSet(project).createResource(fileURI);
		EntityMappingsInternal entityMappings = OrmFactory.eINSTANCE.createEntityMappingsInternal();
		entityMappings.setVersion("1.0");
		
		try {
			resource.getContents().add(entityMappings);
			resource.save(null);
		}
		catch (IOException e) {
			JptCorePlugin.log(e);
		}
	}
	
	private String computeSourceFolder(IProject project) {
		IJavaProject jproject = JavaCore.create(project);
		IClasspathEntry[] classpath;
		
		try {
			classpath = jproject.getRawClasspath();
		}
		catch (JavaModelException jme) {
			classpath = new IClasspathEntry[0];
		}
		
		for (Iterator stream = CollectionTools.iterator(classpath); stream.hasNext(); ) {
			IClasspathEntry next = (IClasspathEntry) stream.next();
			if (next.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				return next.getPath().toString();
			}
		}
		
		return "/" + project.getName();
	}
	
	private ResourceSet getResourceSet(IProject project) {
		return WorkbenchResourceHelperBase.getResourceSet(project);
	}
}
