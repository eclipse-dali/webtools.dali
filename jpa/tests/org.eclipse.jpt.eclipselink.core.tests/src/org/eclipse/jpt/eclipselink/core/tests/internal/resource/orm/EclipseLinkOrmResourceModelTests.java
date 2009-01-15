/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.orm;

import junit.framework.TestCase;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmXmlResourceProvider;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmXmlResource;

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
		EclipseLinkOrmXmlResourceProvider modelProvider = 
			EclipseLinkOrmXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		modelProvider.createResource();
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.jpaProject.getProject().delete(true, true, null);
		this.jpaProject = null;
		super.tearDown();
	}
	
	public void testModelLoad() {
		EclipseLinkOrmXmlResourceProvider modelProvider = 
			EclipseLinkOrmXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		EclipseLinkOrmXmlResource resource = (EclipseLinkOrmXmlResource) modelProvider.getXmlResource();
		assertNotNull(resource);
	}
	
	public void testModelLoad2() {
		EclipseLinkOrmXmlResourceProvider modelProvider = 
			EclipseLinkOrmXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		EclipseLinkOrmXmlResource resource = (EclipseLinkOrmXmlResource) modelProvider.getXmlResource();
		assertNotNull(resource);
	}
	
	public void testModelLoadForDifferentlyNamedOrmXml() {
		EclipseLinkOrmXmlResourceProvider modelProvider = 
			EclipseLinkOrmXmlResourceProvider.getXmlResourceProvider(
				jpaProject.getProject(),"META-INF/eclipselink-orm2.xml");
		assertNotNull(modelProvider);
		EclipseLinkOrmXmlResource resource = (EclipseLinkOrmXmlResource) modelProvider.getXmlResource();
		assertNotNull(resource);
	}
	
	public void testCreateFile() throws CoreException {
		createFile();
		EclipseLinkOrmXmlResourceProvider modelProvider = 
			EclipseLinkOrmXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		EclipseLinkOrmXmlResource resource = (EclipseLinkOrmXmlResource) modelProvider.getXmlResource();
		assertNotNull(resource);
		assertTrue(resource.exists());
	}
}
