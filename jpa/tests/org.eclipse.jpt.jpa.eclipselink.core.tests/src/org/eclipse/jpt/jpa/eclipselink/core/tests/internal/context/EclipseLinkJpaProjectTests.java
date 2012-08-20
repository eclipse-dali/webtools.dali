/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class EclipseLinkJpaProjectTests
	extends ContextModelTestCase
{
	
	public EclipseLinkJpaProjectTests(String name) {
		super(name);
	}

	@Override
	protected boolean createOrmXml() {
		return true;
	}

	@Override
	protected JpaPlatformDescription getJpaPlatformDescription() {
		return  EclipseLinkPlatform.VERSION_1_0;
	}

	@Override
	protected EclipseLinkJpaProject getJpaProject() {
		return (EclipseLinkJpaProject) super.getJpaProject();
	}
	
	public void testGetDefaultOrmXmlResource() throws Exception {
		JptXmlResource resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());	
		
		//delete the orm.xml file and verify it is not returned from getDefaultOrmXmlResource()
		resource.delete(null);
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNull(resource);
		
		//add the orm.xml file with eclipselink orm content type
		createDefaultOrmXmlFileWithEclipseLinkContentType();
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());
		
		
		//delete the orm.xml file and verify it is not returned from getDefaultOrmXmlResource()
		resource.delete(null);
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNull(resource);
		
		
		//add the orm.xml file this time with orm content type
		createDefaultOrmXmlFile();
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());
	}
	
	public void testGetDefaultEclipseLinkOrmXmlResource() throws Exception {
		JptXmlResource resource = this.getJpaProject().getDefaultEclipseLinkOrmXmlResource();
		assertNull(resource);
		
		//add the eclipselink-orm.xml file
		createDefaultEclipseLinkOrmXmlFile();
		resource = this.getJpaProject().getDefaultEclipseLinkOrmXmlResource();
		assertNotNull(resource);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
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
		createEclipseLinkOrmXmlFile(XmlEntityMappings.DEFAULT_RUNTIME_PATH.lastSegment());
	}
	
	private void createDefaultEclipseLinkOrmXmlFile() throws Exception {
		createEclipseLinkOrmXmlFile(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH.lastSegment());
	}
	
	private void createEclipseLinkOrmXmlFile(String fileName) throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.setProperty(JptFileCreationDataModelProperties.FILE_NAME, fileName);
		config.getDefaultOperation().execute(null, null);
	}
	
	public void testGetMappingFileResource() throws Exception {
		JptXmlResource resource = this.getJpaProject().getMappingFileXmlResource(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH);
		assertNull(resource);
		
		//add the eclipselink-orm.xml file
		createDefaultEclipseLinkOrmXmlFile();
		resource = this.getJpaProject().getMappingFileXmlResource(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH);
		assertNotNull(resource);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/eclipselink-orm.xml", resource.getFile().getProjectRelativePath().toString());
		
		//delete the eclipselink-orm.xml file and verify it is not returned from getMappingFileResource()
		resource.delete(null);
		resource = this.getJpaProject().getMappingFileXmlResource(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH);
		assertNull(resource);
	}
	
	public void testGetDifferentlyNamedMappingFileResource() throws Exception {
		JptXmlResource resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNull(resource);

		//create the orm2.xml file
		createEclipseLinkOrmXmlFile("orm2.xml");
		resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNotNull(resource);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm2.xml", resource.getFile().getProjectRelativePath().toString());
		
		//delete the orm2.xml file and verify it is not returned from getMappingFileResource()
		resource.delete(null);
		resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNull(resource);
		
		//add the orm2.xml file back
		createEclipseLinkOrmXmlFile("orm2.xml");
		resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNotNull(resource);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm2.xml", resource.getFile().getProjectRelativePath().toString());
	}
}
