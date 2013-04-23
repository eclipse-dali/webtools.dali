package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.entity.orm;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.AbstractSwtBotEditorTest;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
@SuppressWarnings("restriction")
public class OrmPersistentTypesSWTBotTest extends AbstractSwtBotEditorTest {
	
	private static String TEST_PROJECT = "Test_" + System.currentTimeMillis();
	private static JpaProject jpaProject;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		jpaProject = createJPa20ProjectWithOrm(TEST_PROJECT);
	}
	
	/**
	 * Add an entity in the diagram and assert that it is registered in the orm.xml.
	 * Add an attribute to the entity and assert that it is registered in the orm.xml.
	 * Delete the attribute from the diagram and assert that the attribute is deleted from the orm.xml.
	 * Delete the entity from the diagram and assert that it is also deleted from the orm.xml.
	 * @throws InterruptedException
	 */
	@Test
	public void testAddEntityAndAttributeInOrmXml() throws InterruptedException {
		Utils.sayTestStarted("testAddEntityAndAttributeInOrmXml");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		//add entity in diagram
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		String entitySimpleName = editorProxy.getJPTObjectForGefElement(entity).getSimpleName();
		String entityName = editorProxy.getJPTObjectForGefElement(entity).getName();

		//assert that entity is added in the orm.xml file
		assertNotNull(ormXml.getPersistentType(entityName));
		
		jpaDiagramEditor.save();
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());
		
		String attributeName = editorProxy.getUniqueAttrName(entity);
		//add attribute to the entity
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(entity, attributeName, true);
		PersistentAttribute pa = editorProxy.getJPAObjectForGefElement(attribute);
		assertNotNull(pa);
		assertNotNull("Attribute must be added into the orm.xml", JpaArtifactFactory.instance().getORMPersistentAttribute(pa));
		//delete the attribute from the entity
		editorProxy.removeAttributeViaButton(entity, attribute, attributeName, true);
		assertNull("Attribute must be deleted from the orm.xml", JpaArtifactFactory.instance().getORMPersistentAttribute(pa));
		pa = editorProxy.getJPAObjectForGefElement(attribute);
		assertNull(pa);
		
		jpaDiagramEditor.save();
		//delete the entity from the diagram
		entity = jpaDiagramEditor.getEditPart(entitySimpleName);
		editorProxy.deleteJPTViaButton(entity, true);
		assertNull(ormXml.getPersistentType(entityName));

		editorProxy.deleteDiagramElements(true);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddEntityAndAttributeInOrmXml");
	}
	
	@Test
	public void testOneToOneUniDirRelationship() throws InterruptedException {
		Utils.sayTestStarted("testOneToOneUniDirRelationship");
		relUtils.oneToOneUniDirRelationship(true);
		Utils.sayTestFinished("testOneToOneUniDirRelationship");
	}
	
	
	/**
	 * Creates "One to One" unidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfOneToOneUniDirRelationship() {
		Utils.sayTestStarted("testSelfOneToOneUniDirRelationship");
		relUtils.selfOneToOneUniDirRelationship(true);
		Utils.sayTestFinished("testSelfOneToOneUniDirRelationship");
	}

	/**
	 * Creates "One to One" bidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the inverse attribute will be deleted the connection will be
	 * transformed into one-to-one unidirectional relationship. Test that if the
	 * owner attribute will be deleted, the relationship will disappear.
	 */
	@Test
	public void testOneToOneBiDirRelationship() {
		Utils.sayTestStarted("testOneToOneBiDirRelationship");
		relUtils.oneToOneBiDirRelationship(true);
		Utils.sayTestFinished("testOneToOneBiDirRelationship");
	}

	/**
	 * Creates "One to One" bidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfOneToOneBiDirRelationship() {
		Utils.sayTestStarted("testSelfOneToOneBiDirRelationship");
		relUtils.selfOneToOneBiDirRelationship(true);
		Utils.sayTestFinished("testSelfOneToOneBiDirRelationship");
	}

	/**
	 * Creates "One to Many" unidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	@Test
	public void testOneToManyUniDirRelationship() {
		Utils.sayTestStarted("testOneToManyUniDirRelationship");
		relUtils.oneToManyUniDirRelationship(true);
		Utils.sayTestFinished("testOneToManyUniDirRelationship");
	}

	/**
	 * Creates "One to Many" unidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfOneToManyUniDirRelationship() {
		Utils.sayTestStarted("testSelfOneToManyUniDirRelationship");
		relUtils.selfOneToManyUniDirRelationship(true);
		Utils.sayTestFinished("testSelfOneToManyUniDirRelationship");
	}

	/**
	 * Creates "Many to One" unidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	@Test
	public void testManyToOneUniDirRelationship() {
		Utils.sayTestStarted("testManyToOneUniDirRelationship");
		relUtils.manyToOneUniDirRelationship(true);
		Utils.sayTestFinished("testManyToOneUniDirRelationship");
	}

	/**
	 * Creates "Many to One" unidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfManyToOneUniDirRelationship() {
		Utils.sayTestStarted("testSelfManyToOneUniDirRelationship");
		relUtils.selfManyToOneUniDirRelationship(true);
		Utils.sayTestFinished("testSelfManyToOneUniDirRelationship");
	}

	/**
	 * Creates a "Many to One" bidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the inverse attribute will be deleted the connection will be
	 * transformed into many-to-one unidirectional relationship. Test that if
	 * the owner attribute will be deleted, the relationship will disappear.
	 */
	@Test
	public void testManyToOneBiDirRelationship() {
		Utils.sayTestStarted("testManyToOneBiDirRelationship");
		relUtils.manyToOneBiDirRelationship(true);
		Utils.sayTestFinished("testManyToOneBiDirRelationship");
	}

	/**
	 * Creates a "Many to One" bidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfManyToOneBiDirRelationship() {
		Utils.sayTestStarted("testSelfManyToOneBiDirRelationship");
		relUtils.selfManyToOneBiDirRelationship(true);
		Utils.sayTestFinished("testSelfManyToOneBiDirRelationship");
	}

	/**
	 * Creates "Many to Many" unidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	@Test
	public void testManyToManyUniDirRelationship() {
		Utils.sayTestStarted("testManyToManyUniDirRelationship");
		relUtils.manyToManyUniDirRelationship(true);
		Utils.sayTestFinished("testManyToManyUniDirRelationship");
	}

	/**
	 * Creates "Many to Many" unidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfManyToManyUniDirRelationship() {
		Utils.sayTestStarted("testSelfManyToManyUniDirRelationship");
		relUtils.selfManyToManyUniDirRelationship(true);
		Utils.sayTestFinished("testSelfManyToManyUniDirRelationship");
	}

	/**
	 * Creates a "Many to Many" bidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the inverse attribute will be deleted the connection will be
	 * transformed into many-to-many unidirectional relationship. Test that if
	 * the owner attribute will be deleted, the relationship will disappear.
	 */
	@Test
	public void testManyToManyBiDirRelationship() {
		Utils.sayTestStarted("testManyToManyBiDirRelationship");
		relUtils.manyToManyBiDirRelationship(true);
		Utils.sayTestFinished("testManyToManyBiDirRelationship");
	}

	/**
	 * Creates a "Many to Many" bidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfManyToManyBiDirRelationship() {
		Utils.sayTestStarted("testSelfManyToManyBiDirRelationship");
		relUtils.selfManyToManyBiDirRelationship(true);
		Utils.sayTestFinished("testSelfManyToManyBiDirRelationship");
	}
}
