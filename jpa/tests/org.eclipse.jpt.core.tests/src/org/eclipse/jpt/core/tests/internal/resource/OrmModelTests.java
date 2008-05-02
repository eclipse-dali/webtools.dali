/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource;

import junit.framework.TestCase;
import org.eclipse.jpt.core.resource.orm.OrmArtifactEdit;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.tests.internal.ProjectUtility;
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
		ProjectUtility.deleteAllProjects();
		jpaProject = TestJpaProject.buildJpaProject(BASE_PROJECT_NAME, false); // false = no auto-build
	}
	
	@Override
	protected void tearDown() throws Exception {
		jpaProject = null;
		super.tearDown();
	}
	
	public void testModelLoad() {
		OrmArtifactEdit artifactEdit = OrmArtifactEdit.getArtifactEditForRead(jpaProject.getProject());
		assertNotNull(artifactEdit);
		OrmResource resource = artifactEdit.getResource("META-INF/orm.xml");
		assertNotNull(resource);
	}
	
	public void testModelLoad2() {
		OrmArtifactEdit artifactEdit = OrmArtifactEdit.getArtifactEditForRead(jpaProject.getProject());
		assertNotNull(artifactEdit);
		OrmResource resource = artifactEdit.getResource("META-INF/orm.xml");
		assertNotNull(resource);
	}
}
