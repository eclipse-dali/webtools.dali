package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.mappedsuperclass.relationships;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.AbstractSwtBotEditorTest;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class MappedSuperclassRelationshipsSWTBotTest extends AbstractSwtBotEditorTest {
	
	protected static String TEST_PROJECT = "Test_" + System.currentTimeMillis();
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		createJPa20Project(TEST_PROJECT);
	}
	
	/**
	 * Tests that the creation of a one-to-one unidirectional relationship from
	 * mapped superclass to entity is possible.
	 */
	@Test
	public void testOneToOneUniDirRelFromMappedSuperclass() {
		Utils.sayTestStarted("testOneToOneUniDirRelFromMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		// create One-to-One unidirectional relation from the mapped superclass
		// to entity

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						mappedSuperclass,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToOneUniDirRelFromMappedSuperclass");
	}

	/**
	 * Tests that the creation of a one-to-many unidirectional relationship from
	 * mapped superclass to entity is possible.
	 */
	@Test
	public void testOneToManyUniDirRelFromMappedSuperclass() {
		Utils.sayTestStarted("testOneToManyUniDirRelFromMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		// create One-to-many unidirectional relation from the mapped superclass
		// to entity1

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						mappedSuperclass,
						entity,
						IRelation.RelType.ONE_TO_MANY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, false);

		editorProxy.deleteDiagramElements(false);

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToManyUniDirRelFromMappedSuperclass");
	}

	/**
	 * Tests that the creation of a many-to-one unidirectional relationship from
	 * mapped superclass to entity is possible.
	 */
	@Test
	public void testManyToOneUniDirRelFromMappedSuperclass() {
		Utils.sayTestStarted("testManyToOneUniDirRelFromMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		// create Many-to-One unidirectional relation from the mapped superclass
		// to entity1

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						mappedSuperclass,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToOneUniDirRelFromMappedSuperclass");
	}

	/**
	 * Tests that the creation of a many-to-many unidirectional relationship
	 * from mapped superclass to entity is possible.
	 */
	@Test
	public void testManyToManyUniDirRelFromMappedSuperclass() {
		Utils.sayTestStarted("testManyToManyUniDirRelFromMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		// create Many-to-Many unidirectional relation from the mapped
		// superclass
		// to entity1

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						mappedSuperclass,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, false);

		editorProxy.deleteDiagramElements(false);

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToManyUniDirRelFromMappedSuperclass");
	}

	/**
	 * Test no one-to-one unidirectional or bidirectional relationship from
	 * entity to mapped superclass is created.
	 */
	@Test
	public void testOneToOneRelationFromEntityToMappedSuperclass() {
		Utils.sayTestStarted("testOneToOneRelationFromEntityToMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						2, entity, mappedSuperclass);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						3, entity, mappedSuperclass);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToOneRelationFromEntityToMappedSuperclass");
	}

	/**
	 * Test no one-to-one bidirectional relationship from mapped superclass to
	 * entity is created.
	 */
	@Test
	public void testOneToOneBiDirRelationFromMappedSuperclassToEntity() {
		Utils.sayTestStarted("testOneToOneBiDirRelationFromMappedSuperclassToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						3, mappedSuperclass, entity);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToOneBiDirRelationFromMappedSuperclassToEntity");
	}

	/**
	 * Test no one-to-many unidirectional relationship from entity to mapped
	 * superclass is created.
	 */
	@Test
	public void testOneToManyUniDirRelationFromEntityToMappedSuperclass() {
		Utils.sayTestStarted("testOneToManyUniDirRelationFromEntityToMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, entity, mappedSuperclass);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToManyUniDirRelationFromEntityToMappedSuperclass");
	}

	/**
	 * Test no many-to-one unidirectional or bidirectional relationship from
	 * entity to mapped superclass is created.
	 */
	@Test
	public void testManyToOneRelationFromEntityToMappedSuperclass() {
		Utils.sayTestStarted("testManyToOneRelationFromEntityToMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						2, entity, mappedSuperclass);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						3, entity, mappedSuperclass);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToOneRelationFromEntityToMappedSuperclass");
	}

	/**
	 * Test no many-to-one bidirectional relationship from mapped superclass to
	 * entity is created.
	 */
	@Test
	public void testManyToOneBiDirRelationFromMappedSuperclassToEntity() {
		Utils.sayTestStarted("testManyToOneBiDirRelationFromMappedSuperclassToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						3, mappedSuperclass, entity);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToOneBiDirRelationFromMappedSuperclassToEntity");
	}

	/**
	 * Test no many-to-many unidirectional or bidirectional relationship from
	 * entity to mapped superclass is created.
	 */
	@Test
	public void testManyToManyUniDirRelationFromEntityToEmbeddable() {
		Utils.sayTestStarted("testManyToManyUniDirRelationFromEntityToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						0, entity, mappedSuperclass);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, entity, mappedSuperclass);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToManyUniDirRelationFromEntityToEmbeddable");
	}

	/**
	 * Test no many-to-many bidirectional relationship from mapped superclass to
	 * entity is created.
	 */
	@Test
	public void testManyToManyBiDirRelationFromMappedSuperclassToEntity() {
		Utils.sayTestStarted("testManyToManyBiDirRelationFromMappedSuperclassToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, mappedSuperclass, entity);

		editorProxy.deleteDiagramElements(false);
//		jpaDiagramEditor.saveAndClose();

		Utils.sayTestFinished("testManyToManyBiDirRelationFromMappedSuperclassToEntity");
	}
}
