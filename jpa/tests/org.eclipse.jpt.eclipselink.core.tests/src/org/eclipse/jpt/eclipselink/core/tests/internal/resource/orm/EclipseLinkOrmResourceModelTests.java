/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.orm;

import junit.framework.TestCase;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.eclipselink.core.internal.resource.elorm.EclipseLinkOrmResourceModelProvider;
import org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmResource;

public class EclipseLinkOrmResourceModelTests extends TestCase
{
	static final String BASE_PROJECT_NAME = EclipseLinkOrmResourceModelTests.class.getSimpleName();
	
	TestJpaProject jpaProject;

	
	public EclipseLinkOrmResourceModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.jpaProject = TestJpaProject.buildJpaProject(BASE_PROJECT_NAME, false); // false = no auto-build
	}
	
	protected void createFile() throws CoreException {
		EclipseLinkOrmResourceModelProvider modelProvider = 
			EclipseLinkOrmResourceModelProvider.getDefaultModelProvider(jpaProject.getProject());
		modelProvider.createResource();
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.jpaProject.getProject().delete(true, true, null);
		this.jpaProject = null;
		super.tearDown();
	}
	
	public void testModelLoad() {
		EclipseLinkOrmResourceModelProvider modelProvider = 
			EclipseLinkOrmResourceModelProvider.getDefaultModelProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		EclipseLinkOrmResource resource = (EclipseLinkOrmResource) modelProvider.getResource();
		assertNotNull(resource);
	}
	
	public void testModelLoad2() {
		EclipseLinkOrmResourceModelProvider modelProvider = 
			EclipseLinkOrmResourceModelProvider.getDefaultModelProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		EclipseLinkOrmResource resource = (EclipseLinkOrmResource) modelProvider.getResource();
		assertNotNull(resource);
	}
	
	public void testModelLoadForDifferentlyNamedOrmXml() {
		EclipseLinkOrmResourceModelProvider modelProvider = 
			EclipseLinkOrmResourceModelProvider.getModelProvider(
				jpaProject.getProject(),"META-INF/eclipselink-orm2.xml");
		assertNotNull(modelProvider);
		EclipseLinkOrmResource resource = (EclipseLinkOrmResource) modelProvider.getResource();
		assertNotNull(resource);
	}
	
	public void testCreateFile() throws CoreException {
		createFile();
		EclipseLinkOrmResourceModelProvider modelProvider = 
			EclipseLinkOrmResourceModelProvider.getDefaultModelProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		EclipseLinkOrmResource resource = (EclipseLinkOrmResource) modelProvider.getResource();
		assertNotNull(resource);
		assertTrue(resource.exists());
	}
}
