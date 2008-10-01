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
package org.eclipse.jpt.core.tests.internal.resource;

import junit.framework.TestCase;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModelProvider;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;

public class OrmModelTests extends TestCase
{
	static final String BASE_PROJECT_NAME = OrmModelTests.class.getSimpleName();
	
	TestJpaProject jpaProject;

	
	public OrmModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		jpaProject = TestJpaProject.buildJpaProject(BASE_PROJECT_NAME, false); // false = no auto-build
	}
	
	protected void createFile() throws CoreException {
		OrmResourceModelProvider modelProvider = 
			OrmResourceModelProvider.getDefaultModelProvider(jpaProject.getProject());
		modelProvider.createResource();
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.jpaProject.getProject().delete(true, true, null);
		jpaProject = null;
		super.tearDown();
	}
	
	public void testModelLoad() {
		OrmResourceModelProvider modelProvider = 
			OrmResourceModelProvider.getDefaultModelProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		OrmResource resource = modelProvider.getResource();
		assertNotNull(resource);
	}
	
	public void testModelLoad2() {
		OrmResourceModelProvider modelProvider = 
			OrmResourceModelProvider.getDefaultModelProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		OrmResource resource = modelProvider.getResource();
		assertNotNull(resource);
	}
	
	public void testModelLoadForDifferentlyNamedOrmXml() {
		OrmResourceModelProvider modelProvider = 
			OrmResourceModelProvider.getModelProvider(jpaProject.getProject(), "META-INF/orm2.xml");
		assertNotNull(modelProvider);
		OrmResource resource = modelProvider.getResource();
		assertNotNull(resource);
	}
	
	public void testCreateFile() throws CoreException {
		createFile();
		OrmResourceModelProvider modelProvider = 
			OrmResourceModelProvider.getDefaultModelProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		OrmResource resource = modelProvider.getResource();
		assertNotNull(resource);
		assertTrue(resource.exists());
	}
}
