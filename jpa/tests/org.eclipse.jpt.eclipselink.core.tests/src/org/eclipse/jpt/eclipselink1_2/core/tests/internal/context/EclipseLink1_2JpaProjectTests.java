/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink1_2.core.tests.internal.context;

import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.operations.JpaFileCreationDataModelProperties;
import org.eclipse.jpt.core.internal.operations.JptFileCreationDataModelProperties;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_2.EclipseLink1_2;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class EclipseLink1_2JpaProjectTests extends EclipseLink1_2ContextModelTestCase
{
	
	public EclipseLink1_2JpaProjectTests(String name) {
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
		
		//add the orm.xml file with eclipselink 1.2 orm content type
		createDefaultOrmXmlFileWithEclipseLink1_2ContentType();
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_2_RESOURCE_TYPE, resource.getResourceType());
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
		
		//add the eclipselink-orm.xml 1.2 file
		createDefaultEclipseLink1_2OrmXmlFile();
		resource = this.getJpaProject().getDefaultEclipseLinkOrmXmlResource();
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_2_RESOURCE_TYPE, resource.getResourceType());
		assertEquals("src/META-INF/eclipselink-orm.xml", resource.getFile().getProjectRelativePath().toString());
		
		
		//delete the eclipselink-orm.xml file and verify it is not returned from getDefaultOrmXmlResource()
		resource.delete(null);
		resource = this.getJpaProject().getDefaultEclipseLinkOrmXmlResource();
		assertNull(resource);
	}
	
	private void createDefaultOrmXmlFile() throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.getDefaultOperation().execute(null, null);
	}
	
	private void createDefaultOrmXmlFileWithEclipseLinkContentType() throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.setProperty(JptFileCreationDataModelProperties.FILE_NAME, 
				JptCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.lastSegment());
		config.getDefaultOperation().execute(null, null);
	}
	
	private void createDefaultOrmXmlFileWithEclipseLink1_2ContentType() throws Exception {
		createEclipseLink1_2OrmXmlFile(JptCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.lastSegment());
	}
	
	private void createDefaultEclipseLink1_2OrmXmlFile() throws Exception {
		createEclipseLink1_2OrmXmlFile(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH.lastSegment());
	}
	
	private void createEclipseLink1_2OrmXmlFile(String fileName) throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.setProperty(JptFileCreationDataModelProperties.FILE_NAME, fileName);
		config.setProperty(JpaFileCreationDataModelProperties.VERSION, EclipseLink1_2.SCHEMA_VERSION);
		config.getDefaultOperation().execute(null, null);
	}

	public void testGetMappingFileResource() throws Exception {
		JpaXmlResource resource = this.getJpaProject().getMappingFileXmlResource(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH);
		assertNull(resource);
		
		//add the eclipselink-orm.xml 1.2 file
		createDefaultEclipseLink1_2OrmXmlFile();
		resource = this.getJpaProject().getMappingFileXmlResource(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH);
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_2_RESOURCE_TYPE, resource.getResourceType());
		assertEquals("src/META-INF/eclipselink-orm.xml", resource.getFile().getProjectRelativePath().toString());
		
		//delete the eclipselink-orm.xml file and verify it is not returned from getMappingFileResource()
		resource.delete(null);
		resource = this.getJpaProject().getMappingFileXmlResource(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH);
		assertNull(resource);
	}
	
	public void testGetDifferentlyNamedMappingFileResource() throws Exception {
		JpaXmlResource resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNull(resource);

		//create the orm2.xml file
		createEclipseLink1_2OrmXmlFile("orm2.xml");
		resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_2_RESOURCE_TYPE, resource.getResourceType());
		assertEquals("src/META-INF/orm2.xml", resource.getFile().getProjectRelativePath().toString());
		
		//delete the orm2.xml file and verify it is not returned from getMappingFileResource()
		resource.delete(null);
		resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNull(resource);
		
		//add the orm2.xml file back
		createEclipseLink1_2OrmXmlFile("orm2.xml");
		resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNotNull(resource);
		assertEquals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_1_2_RESOURCE_TYPE, resource.getResourceType());
		assertEquals("src/META-INF/orm2.xml", resource.getFile().getProjectRelativePath().toString());
	}
}
