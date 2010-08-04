/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.GenericJpaPlatformProvider;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

@SuppressWarnings("nls")
public abstract class ContextModelTestCase extends AnnotationTestCase
{
	protected static final String BASE_PROJECT_NAME = "ContextModelTestProject";
	
	protected JpaXmlResource persistenceXmlResource;
	
	protected JpaXmlResource ormXmlResource;
	
	
	protected ContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.persistenceXmlResource = getJpaProject().getPersistenceXmlResource();
		this.ormXmlResource = getJpaProject().getDefaultOrmXmlResource();
		this.waitForWorkspaceJobsToFinish();
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.persistenceXmlResource = null;
		this.ormXmlResource = null;
		JptCorePlugin.getWorkspacePreferences().clear();
		this.waitForWorkspaceJobsToFinish();
		super.tearDown();
	}
	
	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return buildJpaProject(BASE_PROJECT_NAME, autoBuild, buildJpaConfigDataModel());
	}
	
	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild, IDataModel jpaConfig) 
			throws Exception {
		return TestJpaProject.buildJpaProject(projectName, autoBuild, jpaConfig);
	}
	
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetInstallDataModelProvider());		
		// default facet version is 2.0 - most tests use 1.0
		dataModel.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, JpaFacet.VERSION_1_0.getVersionString());
		// most tests use the basic generic platform
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM_ID, GenericJpaPlatformProvider.ID);
		// most tests do use an orm.xml
		dataModel.setProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}
	
	protected JpaProject getJpaProject() {
		return getJavaProject().getJpaProject();
	}
	
	protected void waitForWorkspaceJobsToFinish() throws InterruptedException {
		// This job will not start running until all the other workspace jobs are done
		Job waitJob = new Job("Wait job") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					return Status.OK_STATUS;
				}
			};
		waitJob.setRule(ResourcesPlugin.getWorkspace().getRoot());
		waitJob.schedule();
		waitJob.join();
	}
	
	protected JpaXmlResource getPersistenceXmlResource() {
		return this.persistenceXmlResource;
	}
	
	protected JpaXmlResource getOrmXmlResource() {
		return this.ormXmlResource;
	}
	
	protected XmlEntityMappings getXmlEntityMappings() {
		return (XmlEntityMappings) getOrmXmlResource().getRootObject();
	}
	
	protected XmlPersistence getXmlPersistence() {
		return (XmlPersistence) getPersistenceXmlResource().getRootObject();
	}
	
	protected EntityMappings getEntityMappings() {
		MappingFile mappingFile = getPersistenceUnit().mappingFileRefs().next().getMappingFile();
		return (mappingFile == null) ? null : (EntityMappings) mappingFile.getRoot();
	}
	
	protected XmlPersistenceUnit getXmlPersistenceUnit() {
		return getXmlPersistence().getPersistenceUnits().get(0);
	}
	
	protected PersistenceUnit getPersistenceUnit() {
		return getRootContextNode().getPersistenceXml().getPersistence().persistenceUnits().next();
	}
	
	protected ClassRef getSpecifiedClassRef() {
		return getPersistenceUnit().specifiedClassRefs().next();
	}
	
	protected JavaPersistentType getJavaPersistentType() {
		return getSpecifiedClassRef().getJavaPersistentType();
	}
	
	protected JavaEntity getJavaEntity() {
		return (JavaEntity) getJavaPersistentType().getMapping();
	}
	
	protected void addXmlClassRef(String className) {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass(className);
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
	}
	
	protected void removeXmlClassRef(String className) {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		XmlJavaClassRef xmlJavaClassRefToRemove  = null;
		for (XmlJavaClassRef xmlJavaClassRef : xmlPersistenceUnit.getClasses()) {
			if (xmlJavaClassRef.getJavaClass().equals(className)) {
				xmlJavaClassRefToRemove = xmlJavaClassRef;
			}
		}
		if (xmlJavaClassRefToRemove == null) {
			throw new IllegalArgumentException();
		}
		xmlPersistenceUnit.getClasses().remove(xmlJavaClassRefToRemove);
	}
	
	protected void addXmlMappingFileRef(String fileName) {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName(fileName);
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
	}

	protected JpaRootContextNode getRootContextNode() {
		return getJavaProject().getJpaProject().getRootContextNode();
	}
	
	@Override
	protected TestJpaProject getJavaProject() {
		return (TestJpaProject) super.getJavaProject();
	}
	
	protected void deleteResource(Resource resource) throws CoreException {
		WorkbenchResourceHelper.deleteResource(resource);
	}
}
