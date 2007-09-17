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
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.OrmArtifactEdit;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.OrmResource;
import org.eclipse.jpt.core.internal.resource.persistence.Persistence;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceUnit;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaFacetPostInstallDelegate 
	implements IDelegate, IJpaFacetDataModelProperties
{
	private final static String WEB_PROJECT_DEPLOY_PREFIX = J2EEConstants.WEB_INF_CLASSES;
	
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
		String deployPath = PERSISTENCE_XML_FILE_PATH;
		try {
			if (FacetedProjectFramework.hasProjectFacet(project, IModuleConstants.JST_WEB_MODULE)) {
				deployPath = WEB_PROJECT_DEPLOY_PREFIX + "/" + deployPath;
			}
		}
		catch (CoreException ce) {
			// could not determine project facets.  assume it doesn't have the facet.
			JptCorePlugin.log(ce);
		}
		
		PersistenceArtifactEdit pae = 
				PersistenceArtifactEdit.getArtifactEditForWrite(project, deployPath);
		PersistenceResource resource = pae.getPersistenceResource();
		
		// fix for bug 202811 - only add content if it does not already have content
		if (resource.getPersistence() == null) {
			Persistence persistence = PersistenceFactory.eINSTANCE.createPersistence();
			persistence.setVersion("1.0");
			PersistenceUnit pUnit = PersistenceFactory.eINSTANCE.createPersistenceUnit();
			pUnit.setName(project.getName());
			persistence.getPersistenceUnits().add(pUnit);
			resource.getContents().add(persistence);
			pae.save(null);
		}
		
		pae.dispose();
	}
	
	private void createOrmXml(IProject project, IDataModel dataModel) {
		String deployPath = ORM_XML_FILE_PATH;
		try {
			if (FacetedProjectFramework.hasProjectFacet(project, IModuleConstants.JST_WEB_MODULE)) {
				deployPath = WEB_PROJECT_DEPLOY_PREFIX + "/" + deployPath;
			}
		}
		catch (CoreException ce) {
			// could not determine project facets.  assume it doesn't have the facet.
			JptCorePlugin.log(ce);
		}
		
		OrmArtifactEdit oae =
				OrmArtifactEdit.getArtifactEditForWrite(project, deployPath);
		OrmResource resource = oae.getOrmResource();
		
		// fix for bug 202811 - only add content if it does not already have content
		if (resource.getEntityMappings() == null) {
			EntityMappings entityMappings = OrmFactory.eINSTANCE.createEntityMappings();
			entityMappings.setVersion("1.0");
			resource.getContents().add(entityMappings);
			oae.save(null);
		}
		
		oae.dispose();
	}
}
