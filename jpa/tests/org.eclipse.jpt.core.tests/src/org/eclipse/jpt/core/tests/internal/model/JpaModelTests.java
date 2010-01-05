/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import junit.framework.TestCase;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.core.tests.internal.projects.TestFacetedProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestPlatformProject;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class JpaModelTests extends TestCase {

	/** carriage return */
	public static final String CR = System.getProperty("line.separator");

	protected TestFacetedProject testProject;
	public JpaModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (this.debug()) {
			this.printName();
		}
		this.testProject = this.buildTestProject();
	}

	private boolean debug() {
		Boolean debug = (Boolean) ReflectionTools.getStaticFieldValue(this.getGenericJpaModelClass(), "DEBUG");
		return debug.booleanValue();
	}

	// GenericJpaModel is package-private
	private Class<?> getGenericJpaModelClass() {
		return JptCorePlugin.getJpaModel().getClass();
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
		this.testProject.getProject().delete(true, true, null);
		this.testProject = null;
		super.tearDown();
	}

	/** 
	 * Builds a project with the java and utility facets installed, and with
	 * pre-existing entities added.
	 */
	private TestFacetedProject buildTestProject() throws Exception {
		TestJavaProject tjp = TestJavaProject.buildJavaProject(this.getClass().getSimpleName(), true);
		tjp.installFacet("jst.utility", "1.0");
		tjp.createCompilationUnit("test.pkg", "TestEntity.java", "@Entity public class TestEntity {}");
		tjp.createCompilationUnit("test.pkg", "TestEntity2.java", "@Entity public class TestEntity2 {}");
		return tjp;
	}	

	private IFile getFile(TestPlatformProject p, String path) {
		return p.getProject().getFile(new Path(path));
	}

	/**
	 * make sure the DEBUG constants are 'false' before checking in the code
	 */
	public void testDEBUG() {
		this.verifyDEBUG(this.getGenericJpaModelClass());
	}

	private void verifyDEBUG(Class<?> clazz) {
		assertFalse("Recompile with \"DEBUG = false\": " + clazz.getName(),
				((Boolean) ReflectionTools.getStaticFieldValue(clazz, "DEBUG")).booleanValue());
	}
	
	public void testJpaModel() {
		assertNotNull(JptCorePlugin.getJpaModel());
	}
	
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetInstallDataModelProvider());
		dataModel.setProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}

	public void testProjectCloseReopen() throws Exception {
		this.testProject.installFacet(JptCorePlugin.FACET_ID, "1.0", buildJpaConfigDataModel());
		JpaProject jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNotNull(jpaProject);

		this.testProject.getProject().close(null);
		assertFalse("Project is not closed", this.testProject.getProject().isOpen());
		jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNull("JpaProject is not null", jpaProject);

		this.testProject.getProject().open(null);
		assertTrue(this.testProject.getProject().isOpen());
		jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNotNull("JpaProject is null", jpaProject);
		assertEquals(4, jpaProject.jpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity2.java")));

		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/orm.xml")));
	}

	public void testProjectDeleteReimport() throws Exception {
		this.testProject.installFacet(JptCorePlugin.FACET_ID, "1.0", buildJpaConfigDataModel());
		JpaProject jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNotNull(jpaProject);
		assertEquals(1, JptCorePlugin.getJpaModel().getJpaProjectsSize());

		this.testProject.getProject().delete(false, true, null);
		jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNull(jpaProject);
		assertEquals(0, JptCorePlugin.getJpaModel().getJpaProjectsSize());
		assertEquals(0, ResourcesPlugin.getWorkspace().getRoot().getProjects().length);

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(this.testProject.getProject().getName());
		project.create(null);
		assertEquals(1, ResourcesPlugin.getWorkspace().getRoot().getProjects().length);
		project.open(null);

		assertTrue(project.isOpen());
		assertTrue(JptCorePlugin.projectHasJpaFacet(project));
		jpaProject = JptCorePlugin.getJpaProject(project);
		assertNotNull(jpaProject);
		assertEquals(4, jpaProject.jpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity2.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/orm.xml")));
	}

	public void testFacetInstallUninstall() throws Exception {
		assertNull(JptCorePlugin.getJpaProject(this.testProject.getProject()));

		this.testProject.installFacet(JptCorePlugin.FACET_ID, "1.0", buildJpaConfigDataModel());
		assertEquals(1, JptCorePlugin.getJpaModel().getJpaProjectsSize());
		JpaProject jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNotNull(jpaProject);
		assertEquals(4, jpaProject.jpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity2.java")));

		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/orm.xml")));

		this.testProject.uninstallFacet(JptCorePlugin.FACET_ID, "1.0");
		assertEquals(0, JptCorePlugin.getJpaModel().getJpaProjectsSize());
		jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNull(jpaProject);
	}

	public void testEditFacetSettingsFileAddThenRemoveJpaFacet() throws Exception {
		assertNull(JptCorePlugin.getJpaProject(this.testProject.getProject()));

		// add the JPA facet by modifying the facet settings file directly
		IFile facetSettingsFile = this.getFile(this.testProject, ".settings/org.eclipse.wst.common.project.facet.core.xml");
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

		assertEquals(1, JptCorePlugin.getJpaModel().getJpaProjectsSize());
		JpaProject jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNotNull(jpaProject);
		// persistence.xml and orm.xml do not get created in this situation (?)
		assertEquals(2, jpaProject.jpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity2.java")));
//		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/persistence.xml")));
//		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/orm.xml")));


		// now remove the JPA facet
		facetSettingsFile.setContents(new ByteArrayInputStream(oldDocument.getBytes()), false, false, null);
		assertEquals(0, JptCorePlugin.getJpaModel().getJpaProjectsSize());
		jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNull(jpaProject);
	}
	
	public void testEditFacetSettingsFileRemoveThenAddJpaFacet() throws Exception {
		this.testProject.installFacet(JptCorePlugin.FACET_ID, "1.0", buildJpaConfigDataModel());
		JpaProject jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNotNull(jpaProject);

		// remove the JPA facet by modifying the facet settings file directly
		IFile facetSettingsFile = this.getFile(this.testProject, ".settings/org.eclipse.wst.common.project.facet.core.xml");
		InputStream inStream = new BufferedInputStream(facetSettingsFile.getContents());
		int fileSize = inStream.available();
		byte[] buf = new byte[fileSize];
		inStream.read(buf);
		inStream.close();

		String oldDocument = new String(buf);
		String oldString = "<installed facet=\"jst.utility\" version=\"1.0\"/>" + CR + "  " + "<installed facet=\"jpt.jpa\" version=\"1.0\"/>";
		String newString = "<installed facet=\"jst.utility\" version=\"1.0\"/>";
		String newDocument = oldDocument.replaceAll(oldString, newString);

		facetSettingsFile.setContents(new ByteArrayInputStream(newDocument.getBytes()), false, false, null);
		assertEquals(0, JptCorePlugin.getJpaModel().getJpaProjectsSize());
		jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNull(jpaProject);

		// now add the JPA facet back
		facetSettingsFile.setContents(new ByteArrayInputStream(oldDocument.getBytes()), false, false, null);
		assertEquals(1, JptCorePlugin.getJpaModel().getJpaProjectsSize());
		jpaProject = JptCorePlugin.getJpaProject(this.testProject.getProject());
		assertNotNull(jpaProject);
		assertEquals(4, jpaProject.jpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/test/pkg/TestEntity2.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/orm.xml")));
	}
}
