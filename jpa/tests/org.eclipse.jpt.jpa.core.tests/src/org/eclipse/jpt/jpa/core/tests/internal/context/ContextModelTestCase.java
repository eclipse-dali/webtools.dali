/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.context.JpaContextRoot;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformFactory;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationOperation;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.tests.internal.projects.JpaProjectTestHarness;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

@SuppressWarnings("nls")
public abstract class ContextModelTestCase
	extends AnnotationTestCase
{
	protected static final String BASE_PROJECT_NAME = "ContextModelTestProject";
	
	protected JptXmlResource persistenceXmlResource;
	
	protected JptXmlResource ormXmlResource;
	
	
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
		JpaPreferences.removePreferences();
		this.waitForWorkspaceJobsToFinish();
		super.tearDown();
	}
	
	@Override
	protected JavaProjectTestHarness buildJavaProjectTestHarness(boolean autoBuild) throws Exception {
		return buildJpaProjectTestHarness(BASE_PROJECT_NAME, autoBuild, this.buildJpaConfigDataModel());
	}
	
	protected JpaProjectTestHarness buildJpaProjectTestHarness(String projectName, boolean autoBuild, IDataModel jpaConfig) 
			throws Exception {
		JpaProjectTestHarness harness = new JpaProjectTestHarness(projectName, autoBuild, jpaConfig);

		if (createOrmXml()) {
			OrmFileCreationOperation operation = 
					new OrmFileCreationOperation(buildGenericOrmConfig(harness));
			operation.execute(null, null);
		}

		return harness;
	}
	
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetInstallDataModelProvider());		
		dataModel.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, this.getJpaFacetVersionString());
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM, this.getJpaPlatformConfig());
		return dataModel;
	}
	
	protected IDataModel buildGenericOrmConfig(JpaProjectTestHarness harness) {
		IDataModel config =
				DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider());
			config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
					harness.getProject().getFolder("src/META-INF").getFullPath());
			return config;
	}

	// default facet version is the latest version - but most tests use 1.0
	protected String getJpaFacetVersionString() {
		return JpaProject.FACET_VERSION_STRING;
	}
	
	// most tests use the basic generic platform
	protected final JpaPlatform.Config getJpaPlatformConfig() {
		return this.getJpaPlatformManager().getJpaPlatformConfig(this.getJpaPlatformID());
	}

	protected String getJpaPlatformID() {
		return GenericJpaPlatformFactory.ID;
	}

	protected JpaPlatformManager getJpaPlatformManager() {
		return this.getJpaWorkspace().getJpaPlatformManager();
	}

	protected JpaWorkspace getJpaWorkspace() {
		return (JpaWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JpaWorkspace.class);
	}

	// most tests do use an orm.xml
	protected boolean createOrmXml() {
		return true;
	}

	protected JpaProject getJpaProject() {
		return getJavaProjectTestHarness().getJpaProject();
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
	
	protected JptXmlResource getPersistenceXmlResource() {
		return this.persistenceXmlResource;
	}
	
	protected JptXmlResource getOrmXmlResource() {
		return this.ormXmlResource;
	}

	/**
	 * It's nice to be able to call this method from the debugger,
	 * to force the XML files to be written out so you can see their current state.
	 */
	protected void saveXmlFiles() {
		try {
			this.saveXmlFiles_();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	protected void saveXmlFiles_() throws Exception {
		this.persistenceXmlResource.saveIfNecessary();
		this.ormXmlResource.saveIfNecessary();
	}
	
	protected XmlEntityMappings getXmlEntityMappings() {
		return (XmlEntityMappings) getOrmXmlResource().getRootObject();
	}
	
	protected XmlPersistence getXmlPersistence() {
		return (XmlPersistence) getPersistenceXmlResource().getRootObject();
	}

	protected MappingFile getMappingFile() {
		return this.getPersistenceUnit().getMappingFileRefs().iterator().next().getMappingFile();
	}

	protected EntityMappings getEntityMappings() {
		MappingFile mappingFile = this.getMappingFile();
		return (mappingFile == null) ? null : (EntityMappings) mappingFile.getRoot();
	}
	
	protected XmlPersistenceUnit getXmlPersistenceUnit() {
		return getXmlPersistence().getPersistenceUnits().get(0);
	}
	
	protected PersistenceUnit getPersistenceUnit() {
		return getContextModelRoot().getPersistenceXml().getRoot().getPersistenceUnits().iterator().next();
	}
	
	protected ClassRef getSpecifiedClassRef() {
		return getPersistenceUnit().getSpecifiedClassRefs().iterator().next();
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

	protected JpaContextRoot getContextModelRoot() {
		return getJavaProjectTestHarness().getJpaProject().getContextRoot();
	}
	
	@Override
	protected JpaProjectTestHarness getJavaProjectTestHarness() {
		return (JpaProjectTestHarness) super.getJavaProjectTestHarness();
	}
	
	protected void deleteResource(Resource resource) throws CoreException {
		WorkbenchResourceHelper.deleteResource(resource);
	}
}
