package org.eclipse.jpt.core.tests.internal.platform;

import java.io.IOException;
import junit.framework.TestCase;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal;
import org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode;
import org.eclipse.jpt.core.internal.content.persistence.JavaClassRef;
import org.eclipse.jpt.core.internal.content.persistence.Persistence;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceXmlRootContentNode;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.utility.internal.CollectionTools;

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
		jpaProject = this.buildJpaProject(PROJECT_NAME, false);  // false = no auto-build
	}

	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJpaProject(projectName, autoBuild);  // false = no auto-build
	}

	@Override
	protected void tearDown() throws Exception {
		jpaProject.dispose();
		jpaProject = null;
		super.tearDown();
	}
	
	
	public void testPersistentTypes() throws CoreException, IOException {
		IFile persistenceXmlIFile = jpaProject.getProject().getFile(PERSISTENCE_XML_LOCATION);
		IJpaFile persistenceXmlJpaFile = jpaProject.getJpaProject().getJpaFile(persistenceXmlIFile);
		PersistenceXmlRootContentNode persistenceRoot = (PersistenceXmlRootContentNode) persistenceXmlJpaFile.getContent();
		Persistence persistence = persistenceRoot.getPersistence();
		
		IFile ormXmlIFile = jpaProject.getProject().getFile(ORM_XML_LOCATION);
		IJpaFile ormXmlJpaFile = jpaProject.getJpaProject().getJpaFile(ormXmlIFile);
		XmlRootContentNode ormRoot = (XmlRootContentNode) ormXmlJpaFile.getContent();
		EntityMappingsInternal entityMappings = ormRoot.getEntityMappings();
		
		// add xml persistent type
		XmlEntityInternal xmlEntity = OrmFactory.eINSTANCE.createXmlEntityInternal();
		xmlEntity.setSpecifiedName("XmlEntity");
		entityMappings.getTypeMappings().add(xmlEntity);
		entityMappings.eResource().save(null);
		
		assertEquals(1, CollectionTools.size(jpaProject.getJpaProject().getPlatform().persistentTypes(PROJECT_NAME)));
		
		// add java persistent type
		jpaProject.createType(PACKAGE_NAME, "JavaEntity.java", 
				"@Entity public class JavaEntity {}"
			);
		JavaClassRef javaClassRef = PersistenceFactory.eINSTANCE.createJavaClassRef();
		javaClassRef.setJavaClass(PACKAGE_NAME + ".JavaEntity");
		persistence.getPersistenceUnits().get(0).getClasses().add(javaClassRef);
		persistence.eResource().save(null);
		
		assertEquals(2, CollectionTools.size(jpaProject.getJpaProject().getPlatform().persistentTypes(PROJECT_NAME)));
	}
}
