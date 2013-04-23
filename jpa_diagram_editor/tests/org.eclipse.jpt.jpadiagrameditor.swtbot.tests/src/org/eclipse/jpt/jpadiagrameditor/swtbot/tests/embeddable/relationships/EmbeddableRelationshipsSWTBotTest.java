package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.embeddable.relationships;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.AbstractSwtBotEditorTest;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation.HasReferenceType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
@SuppressWarnings("restriction")
public class EmbeddableRelationshipsSWTBotTest extends AbstractSwtBotEditorTest {

	private static String TEST_PROJECT = "Test_" + System.currentTimeMillis();
	private static JpaProject jpaProject;
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		jpaProject = createJPa20Project(TEST_PROJECT);
	}
	
	/**
	 * Test no one-to-one unidirectional relationship from entity to embeddable
	 * is created.
	 */
	@Test
	public void testOneToOneUniDirRelationFromEntityToEmbeddable() {
		Utils.sayTestStarted("testOneToOneUniDirRelationFromEntityToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						2, entity, embeddable);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToOneUniDirRelationFromEntityToEmbeddable");
	}

	/**
	 * Test no one-to-one bidirectional relationship from entity to embeddable
	 * is created.
	 */
	@Test
	public void testOneToOneBiDirRelationFromEntityToEmbeddable() {
		Utils.sayTestStarted("testOneToOneBiDirRelationFromEntityToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						3, entity, embeddable);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToOneBiDirRelationFromEntityToEmbeddable");
	}

	/**
	 * Test no one-to-many unidirectional relationship from entity to embeddable
	 * is created.
	 */
	@Test
	public void testOneToManyUniDirRelationFromEntityToEmbeddable() {
		Utils.sayTestStarted("testOneToManyUniDirRelationFromEntityToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, entity, embeddable);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToManyUniDirRelationFromEntityToEmbeddable");
	}

	/**
	 * Test no many-to-one unidirectional relationship from entity to embeddable
	 * is created.
	 */
	@Test
	public void testManyToOneUniDirRelationFromEntityToEmbeddable() {
		Utils.sayTestStarted("testManyToOneUniDirRelationFromEntityToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						2, entity, embeddable);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToOneUniDirRelationFromEntityToEmbeddable");
	}

	/**
	 * Test no many-to-one bidirectional relationship from entity to embeddable
	 * is created.
	 */
	@Test
	public void testManyToOneBiDirRelationFromEntityToEmbeddable() {
		Utils.sayTestStarted("testManyToOneBiDirRelationFromEntityToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						3, entity, embeddable);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToOneBiDirRelationFromEntityToEmbeddable");
	}

	/**
	 * Test no many-to-many unidirectional relationship from entity to
	 * embeddable is created.
	 */
	@Test
	public void testManyToManyUniDirRelationFromEntityToEmbeddable() {
		Utils.sayTestStarted("testManyToManyUniDirRelationFromEntityToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						0, entity, embeddable);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToManyUniDirRelationFromEntityToEmbeddable");
	}

	/**
	 * Test no many-to-many bidirectional relationship from entity to embeddable
	 * is created.
	 */
	@Test
	public void testManyToManyBiDirRelationFromEntityToEmbeddable() {
		Utils.sayTestStarted("testManyToManyBiDirRelationFromEntityToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, entity, embeddable);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToManyBiDirRelationFromEntityToEmbeddable");
	}

	/**
	 * Test that if the embeddable is not embedded, no one-to-one bidirectional
	 * relationship will be created from the embeddable to the entity. Add
	 * second entity and embed a single value of the embeddable. Check that a
	 * one-to-one relationship will be created from embeddable to the entity.
	 * Check that if the inverse attribute will be deleted the connection will
	 * be transformed into one-to-one unidirectional relationship. Test that if
	 * the owner attribute will be deleted, the relationship will disappear.
	 */
	@Test
	public void testOneToOneBiDirRelationFromEmbeddableToEntity() {
		Utils.sayTestStarted("testOneToOneBiDirRelationFromEmbeddableToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						3, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToOneBiDirRelationFromEmbeddableToEntity");
	}

	/**
	 * Test that if the embeddable is not embedded, no many-to-one bidirectional
	 * relationship will be created from the embeddable to the entity. Add
	 * second entity and embed a single value of the embeddable. Check that a
	 * many-to-one relationship will be created from embeddable to the entity.
	 * Check that if the inverse attribute will be deleted the connection will
	 * be transformed into one-to-one unidirectional relationship. Test that if
	 * the owner attribute will be deleted, the relationship will disappear.
	 */
	@Test
	public void testManyToOneBiDirRelationFromEmbeddableToEntity() {
		Utils.sayTestStarted("testManyToOneBiDirRelationFromEmbeddableToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						3, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testBiDirRelWithTwoMappingTypes(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testBiDirRelWithTwoMappingsWithoutInverseAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testBiDirRelWithTwoMappingsWithoutOwnerAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToOneBiDirRelationFromEmbeddableToEntity");
	}

	/**
	 * Test that if the embeddable is not embedded, no many-to-many
	 * bidirectional relationship will be created from the embeddable to the
	 * entity. Add second entity and embed a single value of the embeddable.
	 * Check that a many-to-many relationship will be created from embeddable to
	 * the entity. Check that if the inverse attribute will be deleted the
	 * connection will be transformed into one-to-one unidirectional
	 * relationship. Test that if the owner attribute will be deleted, the
	 * relationship will disappear.
	 */
	@Test
	public void testManyToManyBiDirRelationFromEmbeddableToEntity() {
		Utils.sayTestStarted("testManyToManyBiDirRelationFromEmbeddableToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, false);

		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, false);

		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToManyBiDirRelationFromEmbeddableToEntity");
	}

	/**
	 * Test that if the embeddable is not embedded, no one-to-one unidirectional
	 * relationship will be created from the embeddable to the entity. Add
	 * second entity and embed a single value of the embeddable. Check that a
	 * one-to-one relationship will be created from embeddable to the entity.
	 * Check that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	@Test
	public void testOneToOneUniDirRelationFromEmbeddableToEntity() {
		Utils.sayTestStarted("testOneToOneUniDirRelationFromEmbeddableToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						2, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToOneUniDirRelationFromEmbeddableToEntity");
	}

	/**
	 * Test that if the embeddable is not embedded, no many-to-one
	 * unidirectional relationship will be created from the embeddable to the
	 * entity. Add second entity and embed a single value of the embeddable.
	 * Check that a many-to-one relationship will be created from embeddable to
	 * the entity. Check that if the owner attribute will be deleted, the
	 * relationship will disappear.
	 */
	@Test
	public void testManyToOneUniDirRelationFromEmbeddableToEntity() {
		Utils.sayTestStarted("testManyToOneUniDirRelationFromEmbeddableToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						2, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToOneUniDirRelationFromEmbeddableToEntity");
	}

	/**
	 * Test that if the embeddable is not embedded, no many-to-many
	 * unidirectional relationship will be created from the embeddable to the
	 * entity. Add second entity and embed a single value of the embeddable.
	 * Check that a many-to-many relationship will be created from embeddable to
	 * the entity. Check that if the owner attribute will be deleted, the
	 * relationship will disappear.
	 */
	@Test
	public void testManyToManyUniDirRelationFromEmbeddableToEntity() {
		Utils.sayTestStarted("testManyToManyUniDirRelationFromEmbeddableToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						0, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, false);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToManyUniDirRelationFromEmbeddableToEntity");
	}

	/**
	 * Test that if the embeddable is not embedded, no one-to-one bidirectional
	 * relationship will be created from the embeddable to the entity. Add
	 * second entity and embed a collection of the embeddable. Check that a
	 * one-to-one relationship will be created from embeddable to the entity.
	 * Check that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	@Test
	public void testOneToOneBiDirRelationFromEmbeddedCollectionToEntity() {
		Utils.sayTestStarted("testOneToOneBiDirRelationFromEmbeddableToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						3, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				3);

		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToOneBiDirRelationFromEmbeddableToEntity");
	}

	/**
	 * Test that if the embeddable is not embedded, no many-to-one bidirectional
	 * relationship will be created from the embeddable to the entity. Add
	 * second entity and embed a collection of the embeddable. Check that a
	 * many-to-one relationship will be created from embeddable to the entity.
	 * Check that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	
	@Test
	public void testManyToOneBiDirRelationFromEmbeddedCollectionToEntity() {
		Utils.sayTestStarted("testManyToOneBiDirRelationFromEmbeddableToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						3, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				3);

		editorProxy
				.testBiDirRelWithTwoMappingTypes(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testBiDirRelWithTwoMappingsWithoutInverseAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testBiDirRelWithTwoMappingsWithoutOwnerAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToOneBiDirRelationFromEmbeddableToEntity");
	}

	/**
	 * Test that no one-to-one bidirectional relationship will be created, if
	 * the embeddable is embedded in two entities.
	 */
	@Test
	public void testNoOneToOneBiDirConnectionIsCreatedInEmbeddableInTwoEntities() {
		Utils.sayTestStarted("testNoOneToOneBiDirConnectionIsCreatedInEmbeddableInTwoEntities");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		SWTBotGefEditPart embeddingEntity1 = editorProxy.addEntityToDiagram(
				200, 50, jpaProject);

		SWTBotGefEditPart embeddingEntity2 = editorProxy.addEntityToDiagram(
				200, 200, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity1, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity2, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testNoConnectionIsCreatedWithEmbeddable(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						1, entity, embeddable);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testNoOneToOneBiDirConnectionIsCreatedInEmbeddableInTwoEntities");
	}

	/**
	 * Test that no many-to-one bidirectional relationship will be created, if
	 * the embeddable is embedded in two entities.
	 */
	@Test
	public void testNoManyToOneBiDirConnectionIsCreatedInEmbeddableInTwoEntities() {
		Utils.sayTestStarted("testNoBiDirConnectionIsCreatedInEmbeddableInTwoEntities");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		SWTBotGefEditPart embeddingEntity1 = editorProxy.addEntityToDiagram(
				200, 50, jpaProject);

		SWTBotGefEditPart embeddingEntity2 = editorProxy.addEntityToDiagram(
				200, 200, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity1, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity2, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testNoConnectionIsCreatedWithEmbeddable(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						1, entity, embeddable);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testNoBiDirConnectionIsCreatedInEmbeddableInTwoEntities");
	}

	/**
	 * Test that no many-to-many bidirectional relationship will be created, if
	 * the embeddable is embedded in two entities.
	 */
	@Test
	public void testNoManyToManyBiDirConnectionIsCreatedInEmbeddableInTwoEntities() {
		Utils.sayTestStarted("testNoManyToManyBiDirConnectionIsCreatedInEmbeddableInTwoEntities");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		SWTBotGefEditPart embeddingEntity1 = editorProxy.addEntityToDiagram(
				200, 50, jpaProject);

		SWTBotGefEditPart embeddingEntity2 = editorProxy.addEntityToDiagram(
				200, 200, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity1, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy.embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity2, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testNoConnectionIsCreatedWithEmbeddable(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, entity, embeddable);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, false);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testNoManyToManyBiDirConnectionIsCreatedInEmbeddableInTwoEntities");
	}

	/**
	 * Test that if the embeddable is not embedded, no one-to-one unidirectional
	 * relationship will be created from the embeddable to the entity. Add
	 * second entity and embed a collection of the embeddable. Check that a
	 * one-to-one relationship will be created from embeddable to the entity.
	 * Check that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	@Test
	public void testOneToOneUniDirRelationFromEmbeddedCollectionToEntity() {
		Utils.sayTestStarted("testOneToOneUniDirRelationFromEmbeddedCollectionToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						2, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testOneToOneUniDirRelationFromEmbeddedCollectionToEntity");
	}

	/**
	 * Test that if the embeddable is not embedded, no many-to-one
	 * unidirectional relationship will be created from the embeddable to the
	 * entity. Add second entity and embed a collection of the embeddable. Check
	 * that a many-to-one relationship will be created from embeddable to the
	 * entity. Check that if the owner attribute will be deleted, the
	 * relationship will disappear.
	 */
	@Test
	public void testManyToOneUniDirRelationFromEmbeddedCollectionToEntity() {
		Utils.sayTestStarted("testManyToOneUniDirRelationFromEmbeddedCollectionToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						2, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToOneUniDirRelationFromEmbeddedCollectionToEntity");
	}

	/**
	 * Test that if a collection of embeddable is embedded into an entity,
	 * neither uni- nor bi-directional many-to-many relationships will be
	 * created.
	 */
	@Test
	public void testManyToManyRelationFromEmbeddedCollectionToEntity() {
		Utils.sayTestStarted("testManyToManyRelationFromEmbeddedCollectionToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						0, embeddable, entity);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				3);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						0, embeddable, entity);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, embeddable, entity);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testManyToManyRelationFromEmbeddedCollectionToEntity");
	}

	/**
	 * Test that if a collection of embeddable is embedded into an entity, no
	 * one-to-many unidirectional relationships will be created.
	 */
	@Test
	public void testOneToManyRelationFromEmbeddedCollectionToEntity() {
		Utils.sayTestStarted("testOneToManyRelationFromEmbeddedCollectionToEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, jpaProject);

		editorProxy.embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				3);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, embeddable, entity);

		editorProxy.deleteDiagramElements(false);
//		jpaDiagramEditor.saveAndClose();

		Utils.sayTestFinished("testOneToManyRelationFromEmbeddedCollectionToEntity");
	}
}
