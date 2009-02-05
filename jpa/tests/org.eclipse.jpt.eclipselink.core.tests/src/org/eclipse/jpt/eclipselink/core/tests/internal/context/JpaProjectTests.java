/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.orm.OrmXmlResourceProvider;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmXmlResourceProvider;

@SuppressWarnings("nls")
public class JpaProjectTests extends EclipseLinkContextModelTestCase
{
	
	public JpaProjectTests(String name) {
		super(name);
	}

	public void testGetDefaultOrmXmlResource() throws Exception {
		JpaXmlResource resource = this.getJpaProject().getDefaultOrmXmlResource();
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
	
	private void createDefaultOrmXmlFileWithEclipseLinkContentType() throws Exception {
		EclipseLinkOrmXmlResourceProvider resourceProvider = 
			EclipseLinkOrmXmlResourceProvider.getXmlResourceProvider(this.getJavaProject().getProject(), JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		resourceProvider.createFileAndResource();
	}
	
	private void createDefaultOrmXmlFile() throws Exception {
		OrmXmlResourceProvider resourceProvider = 
			OrmXmlResourceProvider.getDefaultXmlResourceProvider(this.getJavaProject().getProject());
		resourceProvider.createFileAndResource();
	}
//	public void testModelLoad() {
//		EclipseLinkOrmXmlResourceProvider modelProvider = 
//			EclipseLinkOrmXmlResourceProvider.getDefaultXmlResourceProvider(this.jpaProject.getProject());
//		assertNotNull(modelProvider);
//		JpaXmlResource resource = modelProvider.getXmlResource();
//		assertNotNull(resource);
//	}
//	
//	public void testModelLoad2() {
//		EclipseLinkOrmXmlResourceProvider modelProvider = 
//			EclipseLinkOrmXmlResourceProvider.getDefaultXmlResourceProvider(this.jpaProject.getProject());
//		assertNotNull(modelProvider);
//		JpaXmlResource resource = modelProvider.getXmlResource();
//		assertNotNull(resource);
//	}
//	
//	public void testModelLoadForDifferentlyNamedOrmXml() {
//		EclipseLinkOrmXmlResourceProvider modelProvider = 
//			EclipseLinkOrmXmlResourceProvider.getXmlResourceProvider(
//				this.jpaProject.getProject(),"META-INF/eclipselink-orm2.xml");
//		assertNotNull(modelProvider);
//		JpaXmlResource resource = modelProvider.getXmlResource();
//		assertNotNull(resource);
//	}
//	
//	public void testCreateFile() throws CoreException {
//		createFile();
//		EclipseLinkOrmXmlResourceProvider modelProvider = 
//			EclipseLinkOrmXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
//		assertNotNull(modelProvider);
//		JpaXmlResource resource = modelProvider.getXmlResource();
//		assertNotNull(resource);
//		assertTrue(resource.fileExists());
//	}
}
