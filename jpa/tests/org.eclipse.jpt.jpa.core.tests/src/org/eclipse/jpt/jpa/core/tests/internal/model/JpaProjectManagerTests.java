/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import junit.framework.TestCase;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.core.tests.internal.projects.TestFacetedProject;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.common.core.tests.internal.projects.TestPlatformProject;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class JpaProjectManagerTests
	extends TestCase
{
	/** carriage return */
	public static final String CR = System.getProperty("line.separator");

	private TestFacetedProject testProjectHarness;


	public JpaProjectManagerTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (this.debug()) {
			this.printName();
		}
		this.testProjectHarness = this.buildTestProjectHarness();
	}

	private boolean debug() {
		return TestTools.debug(this.getGenericJpaProjectManagerClass());
	}

	// InternalJpaProjectManager is package-private
	private Class<?> getGenericJpaProjectManagerClass() {
		return this.getJpaProjectManager().getClass();
	}

	protected JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
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
		this.testProjectHarness.getProject().delete(true, true, null);
		this.testProjectHarness = null;
		super.tearDown();
	}

	private JpaProject getJpaProject() {
		return this.getJpaProject(this.getProject());
	}

	private JpaProject getJpaProject(IProject project) {
		try {
			return this.getJpaProject_(project);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	private JpaProject getJpaProject_(IProject project) throws InterruptedException {
		return this.getJpaProjectReference(project).getValue();
	}

	private JpaProject.Reference getJpaProjectReference(IProject project) {
		return (JpaProject.Reference) project.getAdapter(JpaProject.Reference.class);
	}

	private IProject getProject() {
		return this.testProjectHarness.getProject();
	}

	/** 
	 * Builds a project with the java and utility facets installed, and with
	 * pre-existing entities added.
	 */
	private TestFacetedProject buildTestProjectHarness() throws Exception {
		TestJavaProject tjp = TestJavaProject.buildJavaProject(this.getClass().getSimpleName(), true);
		tjp.createCompilationUnit("test.pkg", "TestEntity.java", "@Entity public class TestEntity {}");
		tjp.createCompilationUnit("test.pkg", "TestEntity2.java", "@Entity public class TestEntity2 {}");
		return tjp;
	}	

	private IFile getFile(TestPlatformProject p, String path) {
		return p.getProject().getFile(new Path(path));
	}

	/**
	 * Make sure the <code>DEBUG</code> constant is <code>false</code>
	 * before checking in the code.
	 */
	public void testDEBUG() {
		TestTools.assertFalseDEBUG(this.getGenericJpaProjectManagerClass());
	}

	public void testJpaProjectManager() {
		assertNotNull(this.getJpaProjectManager());
	}
	
	private IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetInstallDataModelProvider());
		dataModel.setProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}

	public void testProjectCloseReopen() throws Exception {
		this.testProjectHarness.installFacet(JpaFacet.ID, "1.0", buildJpaConfigDataModel());
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);

		this.testProjectHarness.getProject().close(null);
		assertFalse("Project is not closed", this.testProjectHarness.getProject().isOpen());
		jpaProject = this.getJpaProject();
		assertNull("JpaProject is not null", jpaProject);

		this.testProjectHarness.getProject().open(null);
		assertTrue(this.testProjectHarness.getProject().isOpen());
		jpaProject = this.getJpaProject();
		assertNotNull("JpaProject is null", jpaProject);
		assertEquals(4, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity2.java")));

		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/META-INF/orm.xml")));
	}

	public void testProjectDeleteReimport() throws Exception {
		this.testProjectHarness.installFacet(JpaFacet.ID, "1.0", buildJpaConfigDataModel());
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);
		assertEquals(1, CollectionTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));

		this.testProjectHarness.getProject().delete(false, true, null);
		jpaProject = this.getJpaProject();
		assertNull(jpaProject);
		assertEquals(0, CollectionTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		assertEquals(0, ResourcesPlugin.getWorkspace().getRoot().getProjects().length);

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(this.testProjectHarness.getProject().getName());
		project.create(null);
		assertEquals(1, ResourcesPlugin.getWorkspace().getRoot().getProjects().length);
		project.open(null);

		assertTrue(project.isOpen());
		assertTrue(JpaFacet.isInstalled(project));
		jpaProject = this.getJpaProject(project);
		assertNotNull(jpaProject);
		assertEquals(4, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity2.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/META-INF/orm.xml")));
	}

	public void testFacetInstallUninstall() throws Exception {
		assertNull(this.getJpaProject());

		this.testProjectHarness.installFacet(JpaFacet.ID, "1.0", buildJpaConfigDataModel());
		assertEquals(1, CollectionTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);
		assertEquals(4, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity2.java")));

		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/META-INF/orm.xml")));

		this.testProjectHarness.uninstallFacet(JpaFacet.ID, "1.0");
		assertEquals(0, CollectionTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		jpaProject = this.getJpaProject();
		assertNull(jpaProject);
	}

	public void testEditFacetSettingsFileAddThenRemoveJpaFacet() throws Exception {
		assertNull(this.getJpaProject());

		// add the JPA facet by modifying the facet settings file directly
		IFile facetSettingsFile = this.getFile(this.testProjectHarness, ".settings/org.eclipse.wst.common.project.facet.core.xml");
		InputStream inStream = new BufferedInputStream(facetSettingsFile.getContents());
		int fileSize = inStream.available();
		byte[] buf = new byte[fileSize];
		inStream.read(buf);
		inStream.close();

		String oldDocument = new String(buf);
		String oldString = "<installed facet=\"java\" version=\"1.5\"/>";
		String newString = oldString + CR + "  " + "<installed facet=\"jpt.jpa\" version=\"1.0\"/>";
		String newDocument = oldDocument.replaceAll(oldString, newString);

		facetSettingsFile.setContents(new ByteArrayInputStream(newDocument.getBytes()), false, false, null);

		assertEquals(1, CollectionTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);
		// persistence.xml and orm.xml do not get created in this situation (?)
		assertEquals(2, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity2.java")));
//		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/persistence.xml")));
//		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/orm.xml")));


		// now remove the JPA facet
		facetSettingsFile.setContents(new ByteArrayInputStream(oldDocument.getBytes()), false, false, null);
		assertEquals(0, CollectionTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		jpaProject = this.getJpaProject();
		assertNull(jpaProject);
	}
	
	public void testEditFacetSettingsFileRemoveThenAddJpaFacet() throws Exception {
		this.testProjectHarness.installFacet(JpaFacet.ID, "1.0", buildJpaConfigDataModel());
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);

		// remove the JPA facet by modifying the facet settings file directly
		IFile facetSettingsFile = this.getFile(this.testProjectHarness, ".settings/org.eclipse.wst.common.project.facet.core.xml");
		InputStream inStream = new BufferedInputStream(facetSettingsFile.getContents());
		int fileSize = inStream.available();
		byte[] buf = new byte[fileSize];
		inStream.read(buf);
		inStream.close();

		String oldDocument = new String(buf);
		String oldString = "<installed facet=\"java\" version=\"1.5\"/>" + CR + "  " + "<installed facet=\"jpt.jpa\" version=\"1.0\"/>";
		String newString = "<installed facet=\"java\" version=\"1.5\"/>";
		String newDocument = oldDocument.replaceAll(oldString, newString);

		facetSettingsFile.setContents(new ByteArrayInputStream(newDocument.getBytes()), false, false, null);
		assertEquals(0, CollectionTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		jpaProject = this.getJpaProject();
		assertNull(jpaProject);

		// now add the JPA facet back
		facetSettingsFile.setContents(new ByteArrayInputStream(oldDocument.getBytes()), false, false, null);
		assertEquals(1, CollectionTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);
		assertEquals(4, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/test/pkg/TestEntity2.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProjectHarness, "src/META-INF/orm.xml")));
	}
}
