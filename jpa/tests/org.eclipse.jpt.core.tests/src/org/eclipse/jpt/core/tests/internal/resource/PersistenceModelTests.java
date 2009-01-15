/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource;

import junit.framework.TestCase;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceXmlResourceProvider;
import org.eclipse.jpt.core.resource.persistence.PersistenceXmlResource;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;

public class PersistenceModelTests extends TestCase
{
	static final String BASE_PROJECT_NAME = PersistenceModelTests.class.getSimpleName();
	
	TestJpaProject jpaProject;

	
	public PersistenceModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.jpaProject = TestJpaProject.buildJpaProject(BASE_PROJECT_NAME, false); // false = no auto-build
	}
	
	protected void createFile() {
		PersistenceXmlResourceProvider modelProvider = 
			PersistenceXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		modelProvider.modify(new Runnable() {
			public void run() {
				
			}
		});
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.jpaProject.getProject().delete(true, true, null);
		this.jpaProject = null;
		super.tearDown();
	}
	
	public void testModelLoad() {
		PersistenceXmlResourceProvider modelProvider = 
			PersistenceXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		PersistenceXmlResource resource = modelProvider.getXmlResource();
		assertNotNull(resource);
	}
	
	public void testModelLoad2() {
		PersistenceXmlResourceProvider modelProvider = 
			PersistenceXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		PersistenceXmlResource resource = modelProvider.getXmlResource();
		assertNotNull(resource);
	}
	
	public void testCreateFile() {
		createFile();
		PersistenceXmlResourceProvider modelProvider = 
			PersistenceXmlResourceProvider.getDefaultXmlResourceProvider(jpaProject.getProject());
		assertNotNull(modelProvider);
		PersistenceXmlResource resource = modelProvider.getXmlResource();
		assertNotNull(resource);
		assertTrue(resource.exists());
	}
}
