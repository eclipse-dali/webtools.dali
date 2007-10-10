/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.tests.internal.ProjectUtility;
import org.eclipse.jpt.core.tests.internal.projects.TestFacetedProject;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;

import junit.framework.TestCase;

public class ModelInitializationTests extends TestCase 
{
	
	public ModelInitializationTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ProjectUtility.deleteAllProjects();
	}
	
	@Override
	protected void tearDown() throws Exception {
		ProjectUtility.deleteAllProjects();
		super.tearDown();
	}

	/** 
	 * Builds a project with the java and utility facets installed, and with
	 * pre-existing entities added.
	 */
	private TestFacetedProject buildTestProject() throws CoreException {
		TestFacetedProject testProject = TestFacetedProject.buildFacetedProject(ClassTools.shortClassNameForObject(this), true);
		testProject.installFacet("jst.java", "5.0");
		testProject.installFacet("jst.utility", "1.0");
		testProject.createFile(
			new Path("src/test.pkg/TestEntity.java"),
			"package test.pkg; @Entity public class TestEntity {}");
		testProject.createFile(
			new Path("src/test.pkg/TestEntity2.java"),
			"package test.pkg; @Entity public class TestEntity2 {}");
		return testProject;
	}	
		
	public void testBasic() {
		assertNotNull(JptCorePlugin.jpaModel());
	}
	
	public void testFacetInstallation() throws CoreException {
		TestFacetedProject testProject = buildTestProject();
		assertNull(JptCorePlugin.jpaProject(testProject.getProject()));
		testProject.installFacet("jpt.jpa", "1.0");
		assertEquals(1, JptCorePlugin.jpaModel().jpaProjectsSize());
		IJpaProject jpaProject = JptCorePlugin.jpaProject(testProject.getProject());
		assertNotNull(jpaProject);
		assertEquals(4, CollectionTools.size(jpaProject.jpaFiles()));
		assertNotNull(jpaProject.jpaFile(testProject.getProject().getFile(new Path("src/test.pkg/TestEntity.java"))));
		assertNotNull(jpaProject.jpaFile(testProject.getProject().getFile(new Path("src/test.pkg/TestEntity2.java"))));
	}
	
	public void testProjectOpening() throws CoreException {
		TestFacetedProject helper = buildTestProject();
		helper.installFacet("jpt.jpa", "1.0");
		helper.getProject().close(null);
		assertFalse(helper.getProject().isOpen());
		helper.getProject().open(null);
		IJpaProject jpaProject = JptCorePlugin.jpaProject(helper.getProject());
		assertNotNull(jpaProject);
		assertNotNull(jpaProject.jpaFile(helper.getProject().getFile(new Path("src/test.pkg/TestEntity.java"))));
		assertNotNull(jpaProject.jpaFile(helper.getProject().getFile(new Path("src/test.pkg/TestEntity2.java"))));
	}
	
}
