package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.entity.relationships;

import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.AbstractSwtBotEditorTest;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class EntityRelationshipsSWTBotTest extends AbstractSwtBotEditorTest {
	
	protected static String TEST_PROJECT = "Test_" + System.currentTimeMillis();
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		createJPa20Project(TEST_PROJECT);
	}
	

	@Test
	public void testOneToOneUniDirRelationship() throws InterruptedException {
		Utils.sayTestStarted("testOneToOneUniDirRelationship");
		relUtils.oneToOneUniDirRelationship(false);
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
		relUtils.selfOneToOneUniDirRelationship(false);
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
		relUtils.oneToOneBiDirRelationship(false);
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
		relUtils.selfOneToOneBiDirRelationship(false);
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
		relUtils.oneToManyUniDirRelationship(false);
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
		relUtils.selfOneToManyUniDirRelationship(false);
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
		relUtils.manyToOneUniDirRelationship(false);
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
		relUtils.selfManyToOneUniDirRelationship(false);
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
		relUtils.manyToOneBiDirRelationship(false);
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
		relUtils.selfManyToOneBiDirRelationship(false);
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
		relUtils.manyToManyUniDirRelationship(false);
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
		relUtils.selfManyToManyUniDirRelationship(false);
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
		relUtils.manyToManyBiDirRelationship(false);
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
		relUtils.selfManyToManyBiDirRelationship(false);
		Utils.sayTestFinished("testSelfManyToManyBiDirRelationship");
	}

	/**
	 * Creates a new Inherited entity by entity. Assert that the inherited
	 * entity does not contain a primary key.
	 */
	@Test
	public void testInheritedEntityByEntity() {
		Utils.sayTestStarted("testInheritedEntityByEntity");
		relUtils.inheritedEntityByEntity(false);
		Utils.sayTestFinished("testInheritedEntityByEntity");
	}

	/**
	 * Create two entities in the diagram. From the "Inheritance" section of the palette
	 * select "Inherit Persistent Type". Clock on the first entity and then click on the
	 * second one. Assert that an is-a relation is created.
	 */
	@Test
	public void testIsARelationBetweenExistingEntities() {
		Utils.sayTestStarted("testIsARelationBetweenExistingEntities");
		relUtils.isARelationBetweenExistingEntities(false);
		Utils.sayTestFinished("testIsARelationBetweenExistingEntities");
	}
}
