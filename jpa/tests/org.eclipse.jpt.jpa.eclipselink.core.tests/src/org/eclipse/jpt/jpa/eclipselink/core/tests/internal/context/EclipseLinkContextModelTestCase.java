/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.internal.operations.JpaFileCreationDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformConfig;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.jpa.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationOperation;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class EclipseLinkContextModelTestCase
	extends ContextModelTestCase
{
	protected JptXmlResource eclipseLinkOrmXmlResource;

	protected EclipseLinkContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.eclipseLinkOrmXmlResource = getJpaProject().getDefaultEclipseLinkOrmXmlResource();
	}

	@Override
	protected void tearDown() throws Exception {
		this.eclipseLinkOrmXmlResource = null;
		super.tearDown();
	}

	@Override
	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild, IDataModel jpaConfig) throws Exception {
		TestJpaProject testJpaProject = super.buildJpaProject(projectName, autoBuild, jpaConfig);

		if (createEclipseLinkOrmXml()) {
			EclipseLinkOrmFileCreationOperation operation = 
				new EclipseLinkOrmFileCreationOperation(buildEclipseLinkOrmConfig(testJpaProject));
			operation.execute(null, null);
		}

		return testJpaProject;
	}

	@Override
	protected boolean createOrmXml() {
		return false;
	}

	protected boolean createEclipseLinkOrmXml() {
		return true;
	}

	protected IDataModel buildEclipseLinkOrmConfig(TestJpaProject testJpaProject) {
		IDataModel dataModel = 
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());		
		dataModel.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				testJpaProject.getProject().getFolder("src/META-INF").getFullPath()); //$NON-NLS-1$
		dataModel.setProperty(JpaFileCreationDataModelProperties.VERSION, this.getEclipseLinkSchemaVersion());
		dataModel.setProperty(OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT, Boolean.TRUE);
		return dataModel;
	}

	protected String getEclipseLinkSchemaVersion() {
		return EclipseLink.SCHEMA_VERSION;
	}

	@Override
	protected JpaPlatformConfig getJpaPlatformConfig() {
		return  EclipseLinkPlatform.VERSION_1_0;
	}

	@Override
	protected EclipseLinkJpaProject getJpaProject() {
		return (EclipseLinkJpaProject) super.getJpaProject();
	}

	@Override
	protected EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	@Override
	protected JavaEclipseLinkEntity getJavaEntity() {
		return (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
	}

	protected JavaEclipseLinkEmbeddable getJavaEmbeddable() {
		return (JavaEclipseLinkEmbeddable) getJavaPersistentType().getMapping();
	}
	
	protected JavaEclipseLinkMappedSuperclass getJavaMappedSuperclass() {
		return (JavaEclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
	}

	@Override
	protected JptXmlResource getOrmXmlResource() {
		return this.eclipseLinkOrmXmlResource;
	}

	@Override
	protected XmlEntityMappings getXmlEntityMappings() {
		return (XmlEntityMappings) super.getXmlEntityMappings();
	}

	@Override
	protected EclipseLinkEntityMappings getEntityMappings() {
		return (EclipseLinkEntityMappings) getPersistenceUnit().getSpecifiedMappingFileRefs().iterator().next().getMappingFile().getRoot();
	}
}
