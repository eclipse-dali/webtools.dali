/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.JpaModelManager;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmResource;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmArtifactEdit;
import org.eclipse.jpt.core.internal.content.persistence.Persistence;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceResource;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaFacetPostInstallDelegate 
	implements IDelegate, IJpaFacetDataModelProperties
{
	private final static String PERSISTENCE_XML_FILE_PATH = "META-INF/persistence.xml";
	
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
		PersistenceArtifactEdit pae = 
				PersistenceArtifactEdit.getArtifactEditForWrite(project, PERSISTENCE_XML_FILE_PATH);
		PersistenceResource resource = pae.getPersistenceResource();
		Persistence persistence = PersistenceFactory.eINSTANCE.createPersistence();
		persistence.setVersion("1.0");
		PersistenceUnit pUnit = PersistenceFactory.eINSTANCE.createPersistenceUnit();
		pUnit.setName(project.getName());
		persistence.getPersistenceUnits().add(pUnit);
		resource.getContents().add(persistence);
		pae.save(null);
		pae.dispose();
	}
	
	private void createOrmXml(IProject project, IDataModel dataModel) {
		OrmArtifactEdit oae =
				OrmArtifactEdit.getArtifactEditForWrite(project, ORM_XML_FILE_PATH);
		OrmResource resource = oae.getOrmResource();
		EntityMappingsInternal entityMappings = OrmFactory.eINSTANCE.createEntityMappingsInternal();
		entityMappings.setVersion("1.0");
		resource.getContents().add(entityMappings);
		oae.save(null);
		oae.dispose();
	}
}
