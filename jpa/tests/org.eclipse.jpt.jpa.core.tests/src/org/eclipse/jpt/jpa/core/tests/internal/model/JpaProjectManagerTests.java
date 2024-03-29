/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.core.internal.utility.ProjectTools;
import org.eclipse.jpt.common.core.tests.internal.projects.FacetedProjectTestHarness;
import org.eclipse.jpt.common.core.tests.internal.projects.ProjectTestHarness;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class JpaProjectManagerTests
	extends TestCase
{
	private FacetedProjectTestHarness projectTestHarness;


	public JpaProjectManagerTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.trace("+++++ " + this.getName() + " +++++");
		this.projectTestHarness = this.buildProjectTestHarness();
	}

	private void trace(String message) {
		ClassTools.execute(this.getGenericJpaProjectManagerClass(), "trace", String.class, message);
	}

	// InternalJpaProjectManager is package-private
	private Class<?> getGenericJpaProjectManagerClass() {
		return this.getJpaProjectManager().getClass();
	}

	protected JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}

	@Override
	protected void tearDown() throws Exception {
		this.projectTestHarness.getProject().delete(true, true, null);
		this.projectTestHarness = null;
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
		return this.projectTestHarness.getProject();
	}

	/** 
	 * Builds a project with the java and utility facets installed, and with
	 * pre-existing entities added.
	 */
	private FacetedProjectTestHarness buildProjectTestHarness() throws Exception {
		JavaProjectTestHarness harness = new JavaProjectTestHarness(this.getClass().getSimpleName(), true);
		harness.createCompilationUnit("test.pkg", "TestEntity.java", "@Entity public class TestEntity {}");
		harness.createCompilationUnit("test.pkg", "TestEntity2.java", "@Entity public class TestEntity2 {}");
		return harness;
	}	

	private IFile getFile(ProjectTestHarness harness, String path) {
		return harness.getProject().getFile(new Path(path));
	}

	public void testJpaProjectManager() {
		assertNotNull(this.getJpaProjectManager());
	}
	
	private IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = DataModelFactory.createDataModel(new JpaFacetInstallDataModelProvider());
		return dataModel;
	}

	private void createDefaultOrmXmlFile() throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				this.getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.getDefaultOperation().execute(null, null);
	}

	public void testProjectCloseReopen() throws Exception {
		this.projectTestHarness.installFacet(JpaProject.FACET_ID, JpaProject.FACET_VERSION_STRING, buildJpaConfigDataModel());
		this.createDefaultOrmXmlFile();
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);

		this.projectTestHarness.getProject().close(null);
		assertFalse("Project is not closed", this.projectTestHarness.getProject().isOpen());
		jpaProject = this.getJpaProject();
		assertNull("JpaProject is not null", jpaProject);

		this.projectTestHarness.getProject().open(null);
		assertTrue(this.projectTestHarness.getProject().isOpen());
		jpaProject = this.getJpaProject();
		assertNotNull("JpaProject is null", jpaProject);
		assertEquals(1, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		assertEquals(4, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity2.java")));

		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/META-INF/orm.xml")));
	}

	public void testProjectDeleteReimport() throws Exception {
		this.projectTestHarness.installFacet(JpaProject.FACET_ID, JpaProject.FACET_VERSION_STRING, buildJpaConfigDataModel());
		this.createDefaultOrmXmlFile();
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);
		assertEquals(1, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));

		// flush the prefs file so it will still be there after we delete the project
		// and our settings are discovered when the project is restored
		this.flushProjectPrefs();

		this.projectTestHarness.getProject().delete(false, true, null);
		jpaProject = this.getJpaProject();
		assertNull(jpaProject);
		assertEquals(0, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		assertEquals(0, ResourcesPlugin.getWorkspace().getRoot().getProjects().length);

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(this.projectTestHarness.getProject().getName());
		project.create(null);
		assertEquals(1, ResourcesPlugin.getWorkspace().getRoot().getProjects().length);
		project.open(null);

		assertTrue(project.isOpen());
		assertTrue(ProjectTools.hasFacet(project, JpaProject.FACET));
		jpaProject = this.getJpaProject(project);
		assertNotNull(jpaProject);
		assertEquals(1, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		assertEquals(4, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity2.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/META-INF/persistence.xml")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/META-INF/orm.xml")));
	}

	protected void flushProjectPrefs() throws Exception {
		JptPlugin plugin = this.getPlugin();
		IEclipsePreferences prefs = (IEclipsePreferences) ObjectTools.execute(plugin, "getProjectPreferences", IProject.class, this.projectTestHarness.getProject());
		prefs.flush();
	}

	protected JptPlugin getPlugin() throws Exception {
		return (JptPlugin) ClassTools.execute(JpaPreferences.class, "getPlugin");
	}

	public void testFacetInstallUninstall() throws Exception {
		assertNull(this.getJpaProject());

		this.projectTestHarness.installFacet(JpaProject.FACET_ID, JpaProject.FACET_VERSION_STRING, buildJpaConfigDataModel());
		assertEquals(1, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);
		assertEquals(3, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity2.java")));

		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/META-INF/persistence.xml")));
		assertNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/META-INF/orm.xml")));

		this.projectTestHarness.uninstallFacet(JpaProject.FACET_ID, "1.0");
		assertEquals(0, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		jpaProject = this.getJpaProject();
		assertNull(jpaProject);
	}

	public void testEditFacetSettingsFileAddThenRemoveJpaFacet() throws Exception {
		assertNull(this.getJpaProject());

		// add the JPA facet by modifying the facet settings file directly
		IFile facetSettingsFile = this.getFile(this.projectTestHarness, ".settings/org.eclipse.wst.common.project.facet.core.xml");
		InputStream inStream = new BufferedInputStream(facetSettingsFile.getContents());
		int fileSize = inStream.available();
		byte[] buf = new byte[fileSize];
		inStream.read(buf);
		inStream.close();

		String oldDocument = new String(buf);
		String oldString = "<installed facet=\"java\" version=\"1.5\"/>";
		String newString = oldString + StringTools.CR + "  " + "<installed facet=\"jpt.jpa\" version=\"1.0\"/>";
		String newDocument = oldDocument.replaceAll(oldString, newString);

		facetSettingsFile.setContents(new ByteArrayInputStream(newDocument.getBytes()), false, false, null);

		assertEquals(1, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);
		// persistence.xml and orm.xml do not get created in this situation (?)
		assertEquals(2, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity2.java")));
//		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/persistence.xml")));
//		assertNotNull(jpaProject.getJpaFile(this.getFile(this.testProject, "src/META-INF/orm.xml")));


		// now remove the JPA facet
		facetSettingsFile.setContents(new ByteArrayInputStream(oldDocument.getBytes()), false, false, null);
		assertEquals(0, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		jpaProject = this.getJpaProject();
		assertNull(jpaProject);
	}
	
	public void testEditFacetSettingsFileRemoveThenAddJpaFacet() throws Exception {
		this.projectTestHarness.installFacet(JpaProject.FACET_ID, JpaProject.FACET_VERSION_STRING, buildJpaConfigDataModel());
		JpaProject jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);

		// remove the JPA facet by modifying the facet settings file directly
		IFile facetSettingsFile = this.getFile(this.projectTestHarness, ".settings/org.eclipse.wst.common.project.facet.core.xml");
		InputStream inStream = new BufferedInputStream(facetSettingsFile.getContents());
		int fileSize = inStream.available();
		byte[] buf = new byte[fileSize];
		inStream.read(buf);
		inStream.close();

		String oldDocument = new String(buf);
		String oldString = "<installed facet=\"java\" version=\"1.5\"/>" + StringTools.CR + "  " + "<installed facet=\"jpt.jpa\" version=\"1.0\"/>";
		String newString = "<installed facet=\"java\" version=\"1.5\"/>";
		String newDocument = oldDocument.replaceAll(oldString, newString);

		facetSettingsFile.setContents(new ByteArrayInputStream(newDocument.getBytes()), false, false, null);
		assertEquals(0, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		jpaProject = this.getJpaProject();
		assertNull(jpaProject);

		// now add the JPA facet back
		facetSettingsFile.setContents(new ByteArrayInputStream(oldDocument.getBytes()), false, false, null);
		assertEquals(1, IterableTools.size(this.getJpaProjectManager().waitToGetJpaProjects()));
		jpaProject = this.getJpaProject();
		assertNotNull(jpaProject);
		assertEquals(3, jpaProject.getJpaFilesSize());
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/test/pkg/TestEntity2.java")));
		assertNotNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/META-INF/persistence.xml")));
		assertNull(jpaProject.getJpaFile(this.getFile(this.projectTestHarness, "src/META-INF/orm.xml")));
	}
}
