/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.tests.internal.context;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class EclipseLink1_1JpaProjectTests extends EclipseLink1_1ContextModelTestCase
{
	
	public EclipseLink1_1JpaProjectTests(String name) {
		super(name);
	}
	
	public void testGetDefaultOrmXmlResource() throws Exception {
		JpaXmlResource resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(JptCorePlugin.ORM_XML_CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());	
		
		//delete the orm.xml file and verify it is not returned from getDefaultOrmXmlResource()
		resource.delete(null);
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNull(resource);
		
		//add the orm.xml file with eclipselink 1.1 orm content type
		createDefaultOrmXmlFileWithEclipseLink1_1ContentType();
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_1_RESOURCE_TYPE, resource.getResourceType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());
		
		
		//delete the orm.xml file and verify it is not returned from getDefaultOrmXmlResource()
		resource.delete(null);
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNull(resource);
		
		
		//add the orm.xml file with eclipselink orm content type
		createDefaultOrmXmlFileWithEclipseLinkContentType();
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());
		
		
		//delete the orm.xml file and verify it is not returned from getDefaultOrmXmlResource()
		resource.delete(null);
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNull(resource);
		
		
		//add the orm.xml file this time with orm content type
		createDefaultOrmXmlFile();
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(JptCorePlugin.ORM_XML_CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());
	}
	
	public void testGetDefaultEclipseLinkOrmXmlResource() throws Exception {
		JpaXmlResource resource = this.getJpaProject().getDefaultEclipseLinkOrmXmlResource();
		assertNull(resource);
		
		//add the eclipselink-orm.xml 1.1 file
		createDefaultEclipseLink1_1OrmXmlFile();
		resource = this.getJpaProject().getDefaultEclipseLinkOrmXmlResource();
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_1_RESOURCE_TYPE, resource.getResourceType());
		assertEquals("src/META-INF/eclipselink-orm.xml", resource.getFile().getProjectRelativePath().toString());
		
		
		//delete the eclipselink-orm.xml file and verify it is not returned from getDefaultOrmXmlResource()
		resource.delete(null);
		resource = this.getJpaProject().getDefaultEclipseLinkOrmXmlResource();
		assertNull(resource);
	}
	
	private void createDefaultOrmXmlFile() throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider());
		config.setProperty(OrmFileCreationDataModelProperties.PROJECT_NAME, getJpaProject().getProject().getName());
		config.getDefaultOperation().execute(null, null);
	}
	
	private void createDefaultOrmXmlFileWithEclipseLinkContentType() throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());
		config.setProperty(OrmFileCreationDataModelProperties.PROJECT_NAME, getJpaProject().getProject().getName());
		config.setProperty(OrmFileCreationDataModelProperties.FILE_PATH, JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		config.getDefaultOperation().execute(null, null);
	}
	
	private void createDefaultOrmXmlFileWithEclipseLink1_1ContentType() throws Exception {
		createEclipseLink1_1OrmXmlFile(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
	}
	
	private void createDefaultEclipseLink1_1OrmXmlFile() throws Exception {
		createEclipseLink1_1OrmXmlFile(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
	}
	
	private void createEclipseLink1_1OrmXmlFile(String filePath) throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());
		config.setProperty(OrmFileCreationDataModelProperties.PROJECT_NAME, getJpaProject().getProject().getName());
		config.setProperty(OrmFileCreationDataModelProperties.VERSION, EclipseLink1_1.SCHEMA_VERSION);
		config.setProperty(OrmFileCreationDataModelProperties.FILE_PATH, filePath);
		config.getDefaultOperation().execute(null, null);
	}

	public void testGetMappingFileResource() throws Exception {
		JpaXmlResource resource = this.getJpaProject().getMappingFileXmlResource(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
		assertNull(resource);
		
		//add the eclipselink-orm.xml 1.1 file
		createDefaultEclipseLink1_1OrmXmlFile();
		resource = this.getJpaProject().getMappingFileXmlResource(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_1_RESOURCE_TYPE, resource.getResourceType());
		assertEquals("src/META-INF/eclipselink-orm.xml", resource.getFile().getProjectRelativePath().toString());
		
		//delete the eclipselink-orm.xml file and verify it is not returned from getMappingFileResource()
		resource.delete(null);
		resource = this.getJpaProject().getMappingFileXmlResource(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
		assertNull(resource);
	}
	
	public void testGetDifferentlyNamedMappingFileResource() throws Exception {
		JpaXmlResource resource = this.getJpaProject().getMappingFileXmlResource("META-INF/orm2.xml");
		assertNull(resource);

		//create the orm2.xml file
		createEclipseLink1_1OrmXmlFile("META-INF/orm2.xml");
		resource = this.getJpaProject().getMappingFileXmlResource("META-INF/orm2.xml");
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_1_RESOURCE_TYPE, resource.getResourceType());
		assertEquals("src/META-INF/orm2.xml", resource.getFile().getProjectRelativePath().toString());
		
		//delete the orm2.xml file and verify it is not returned from getMappingFileResource()
		resource.delete(null);
		resource = this.getJpaProject().getMappingFileXmlResource("META-INF/orm2.xml");
		assertNull(resource);
		
		//add the orm2.xml file back
		createEclipseLink1_1OrmXmlFile("META-INF/orm2.xml");
		resource = this.getJpaProject().getMappingFileXmlResource("META-INF/orm2.xml");
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_1_RESOURCE_TYPE, resource.getResourceType());
		assertEquals("src/META-INF/orm2.xml", resource.getFile().getProjectRelativePath().toString());
	}
}
