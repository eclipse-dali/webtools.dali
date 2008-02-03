package org.eclipse.jpt.core.tests.internal.platform;

import junit.framework.TestCase;
import org.eclipse.jpt.core.tests.internal.ProjectUtility;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;

public class BaseJpaPlatformTests extends TestCase
{
	protected TestJpaProject jpaProject;

	protected static final String PROJECT_NAME = "PlatformTestProject";
	protected static final String PACKAGE_NAME = "platform.test";
	protected static final String PERSISTENCE_XML_LOCATION = "src/META-INF/persistence.xml";
	protected static final String ORM_XML_LOCATION = "src/META-INF/orm.xml";
	
	
	public BaseJpaPlatformTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ProjectUtility.deleteAllProjects();
		jpaProject = this.buildJpaProject(PROJECT_NAME, false);  // false = no auto-build
	}

	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJpaProject(projectName, autoBuild);  // false = no auto-build
	}

	@Override
	protected void tearDown() throws Exception {
		ProjectUtility.deleteAllProjects();
		jpaProject = null;
		super.tearDown();
	}
	
//	public void testPersistentTypes() throws CoreException, IOException {
//		IFile persistenceXmlIFile = jpaProject.getProject().getFile(PERSISTENCE_XML_LOCATION);
//		IJpaFile persistenceXmlJpaFile = jpaProject.getJpaProject().getJpaFile(persistenceXmlIFile);
//		PersistenceXmlRootContentNode persistenceRoot = (PersistenceXmlRootContentNode) persistenceXmlJpaFile.getContent();
//		XmlPersistence persistence = persistenceRoot.getPersistence();
//		
//		IFile ormXmlIFile = jpaProject.getProject().getFile(ORM_XML_LOCATION);
//		IJpaFile ormXmlJpaFile = jpaProject.getJpaProject().getJpaFile(ormXmlIFile);
//		XmlRootContentNode ormRoot = (XmlRootContentNode) ormXmlJpaFile.getContent();
//		EntityMappingsInternal entityMappings = ormRoot.getEntityMappings();
//		
//		// add xml persistent type
//		XmlEntityInternal xmlEntity = OrmFactory.eINSTANCE.createXmlEntityInternal();
//		xmlEntity.setSpecifiedName("XmlEntity");
//		entityMappings.getTypeMappings().add(xmlEntity);
//		entityMappings.eResource().save(null);
//		assertEquals(1, CollectionTools.size(jpaProject.getJpaProject().getPlatform().persistentTypes(PROJECT_NAME)));
//		
//		// add java persistent type
//		jpaProject.createType(PACKAGE_NAME, "JavaEntity.java", 
//				"@Entity public class JavaEntity {}"
//			);
//		XmlJavaClassRef javaClassRef = PersistenceFactory.eINSTANCE.createJavaClassRef();
//		javaClassRef.setJavaClass(PACKAGE_NAME + ".JavaEntity");
//		persistence.getPersistenceUnits().get(0).getClasses().add(javaClassRef);
//		persistence.eResource().save(null);
//		
//		assertEquals(2, CollectionTools.size(jpaProject.getJpaProject().getPlatform().persistentTypes(PROJECT_NAME)));
//	}
}
