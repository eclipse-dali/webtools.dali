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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JpaModel;
import org.eclipse.jpt.core.internal.JpaModelManager;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.tests.internal.ProjectUtility;
import org.eclipse.jpt.core.tests.internal.projects.TestFacetedProject;
import org.eclipse.jpt.core.tests.internal.projects.TestPlatformProject;
import org.eclipse.jpt.utility.internal.ClassTools;

import junit.framework.TestCase;

public class JpaModelTests extends TestCase {

	/** carriage return */
	public static final String CR = System.getProperty("line.separator");

	public JpaModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (this.debug()) {
			this.printName();
		}
		ProjectUtility.deleteAllProjects();
	}

	private boolean debug() {
		Boolean debug = (Boolean) ClassTools.getStaticFieldValue(JpaModelManager.class, "DEBUG");
		return debug.booleanValue();
	}

	private void printName() {
		String name = this.getName();
		System.out.println();
		System.out.println();
		this.printNameBorder(name);
		System.out.println(name);
		this.printNameBorder(name);
	}

	private void printNameBorder(String name) {
		for (int i = name.length(); i-- > 0; ) {
			System.out.print('=');
		}
		System.out.println();
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
	private TestFacetedProject buildTestProject() throws Exception {
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

	private IFile file(TestPlatformProject p, String path) {
		return p.getProject().getFile(new Path(path));
	}

	public void testJpaModel() {
		assertNotNull(JptCorePlugin.jpaModel());
	}

	public void testFacetInstallUninstall() throws Exception {
		TestFacetedProject testProject = this.buildTestProject();
		assertNull(JptCorePlugin.jpaProject(testProject.getProject()));

		testProject.installFacet("jpt.jpa", "1.0");
		assertEquals(1, JptCorePlugin.jpaModel().jpaProjectsSize());
		IJpaProject jpaProject = JptCorePlugin.jpaProject(testProject.getProject());
		assertNotNull(jpaProject);
		assertEquals(4, jpaProject.jpaFilesSize());
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/test.pkg/TestEntity.java")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/test.pkg/TestEntity2.java")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/META-INF/orm.xml")));

		testProject.uninstallFacet("jpt.jpa", "1.0");
		assertEquals(0, JptCorePlugin.jpaModel().jpaProjectsSize());
		jpaProject = JptCorePlugin.jpaProject(testProject.getProject());
		assertNull(jpaProject);
	}

	public void testProjectCloseReopen() throws Exception {
		TestFacetedProject testProject = this.buildTestProject();
		testProject.installFacet("jpt.jpa", "1.0");

		testProject.getProject().close(null);
		assertFalse(testProject.getProject().isOpen());
		IJpaProject jpaProject = JptCorePlugin.jpaProject(testProject.getProject());
		assertNull(jpaProject);

		testProject.getProject().open(null);
		jpaProject = JptCorePlugin.jpaProject(testProject.getProject());
		assertNotNull(jpaProject);
		assertEquals(4, jpaProject.jpaFilesSize());
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/test.pkg/TestEntity.java")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/test.pkg/TestEntity2.java")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/META-INF/orm.xml")));
	}

	public void testProjectDeleteReimport() throws Exception {
		TestFacetedProject testProject = this.buildTestProject();
		testProject.installFacet("jpt.jpa", "1.0");
		IJpaProject jpaProject = JptCorePlugin.jpaProject(testProject.getProject());
		assertNotNull(jpaProject);
		assertEquals(1, JptCorePlugin.jpaModel().jpaProjectsSize());

		testProject.getProject().delete(false, true, null);
		jpaProject = JptCorePlugin.jpaProject(testProject.getProject());
		assertNull(jpaProject);
		assertEquals(0, JptCorePlugin.jpaModel().jpaProjectsSize());
		assertEquals(0, ResourcesPlugin.getWorkspace().getRoot().getProjects().length);

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(testProject.getProject().getName());
		project.create(null);
		assertEquals(1, ResourcesPlugin.getWorkspace().getRoot().getProjects().length);
		project.open(null);

		assertTrue(project.isOpen());
		assertTrue(JptCorePlugin.projectHasJpaFacet(project));
		jpaProject = JptCorePlugin.jpaProject(project);
		assertNotNull(jpaProject);
		assertEquals(4, jpaProject.jpaFilesSize());
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/test.pkg/TestEntity.java")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/test.pkg/TestEntity2.java")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/META-INF/orm.xml")));
	}

	public void testEditFacetSettingsFile() throws Exception {
		TestFacetedProject testProject = this.buildTestProject();
		assertNull(JptCorePlugin.jpaProject(testProject.getProject()));

		// add the JPA facet by modifying the facet settings file directly
		IFile facetSettingsFile = this.file(testProject, ".settings/org.eclipse.wst.common.project.facet.core.xml");
		InputStream inStream = new BufferedInputStream(facetSettingsFile.getContents());
		int fileSize = inStream.available();
		byte[] buf = new byte[fileSize];
		inStream.read(buf);
		inStream.close();

		String oldDocument = new String(buf);
		String oldString = "<installed facet=\"jst.utility\" version=\"1.0\"/>";
		String newString = oldString + CR + "  " + "<installed facet=\"jpt.jpa\" version=\"1.0\"/>";
		String newDocument = oldDocument.replaceAll(oldString, newString);

		facetSettingsFile.setContents(new ByteArrayInputStream(newDocument.getBytes()), false, false, null);

		assertEquals(1, JptCorePlugin.jpaModel().jpaProjectsSize());
		IJpaProject jpaProject = JptCorePlugin.jpaProject(testProject.getProject());
		assertNotNull(jpaProject);
		// persistence.xml and orm.xml do not get created in this situation (?)
		assertEquals(2, jpaProject.jpaFilesSize());
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/test.pkg/TestEntity.java")));
		assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/test.pkg/TestEntity2.java")));
		// assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/META-INF/persistence.xml")));
		// assertNotNull(jpaProject.jpaFile(this.file(testProject, "src/META-INF/orm.xml")));

		// now remove the JPA facet
		facetSettingsFile.setContents(new ByteArrayInputStream(oldDocument.getBytes()), false, false, null);
		assertEquals(0, JptCorePlugin.jpaModel().jpaProjectsSize());
		jpaProject = JptCorePlugin.jpaProject(testProject.getProject());
		assertNull(jpaProject);
	}

	/**
	 * make sure the DEBUG constants are 'false' before checking in the code
	 */
	public void testDEBUG() {
		this.verifyDEBUG(JpaModelManager.class);
		this.verifyDEBUG(JpaModel.class);
	}

	private void verifyDEBUG(Class<?> clazz) {
		assertFalse("Recompile with \"DEBUG = false\": " + clazz.getName(),
				((Boolean) ClassTools.getStaticFieldValue(clazz, "DEBUG")).booleanValue());
	}

}
