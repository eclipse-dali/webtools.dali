/*******************************************************************************
 * Copyright (c) 2013, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;

@SuppressWarnings("restriction")
public class RelationshipsUtils {
	
	private SWTBotGefEditor jpaDiagramEditor;
	private EditorProxy editorProxy;
	private JpaProject jpaProject;
	private OrmXml ormXml;
	
	public RelationshipsUtils(SWTBotGefEditor jpaDiagramEditor, EditorProxy editorProxy, JpaProject jpaProject) {
		this.jpaDiagramEditor = jpaDiagramEditor;
		this.editorProxy = editorProxy;
		this.jpaProject = jpaProject;
	}
	
	
	/**
	 * Creates "One to One" unidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 * 
	 */
	public void oneToOneUniDirRelationship(boolean isOrmXml) {

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName1 = editorProxy.getJPTObjectForGefElement(entity1).getName();
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);
		String entityName2 = editorProxy.getJPTObjectForGefElement(entity2).getName();
		
		if(isOrmXml) {
			assertNotNull(ormXml.getPersistentType(entityName1));
			assertNotNull(ormXml.getPersistentType(entityName2));
		}

		// create One-to-One unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		// create One-to-One unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates "One to One" unidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	public void selfOneToOneUniDirRelationship(boolean isOrmXml) {

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName = editorProxy.getJPTObjectForGefElement(entity1).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName));
		}
		// create One-to-One unidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						entity1,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates "One to One" bidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the inverse attribute will be deleted the connection will be
	 * transformed into one-to-one unidirectional relationship. Test that if the
	 * owner attribute will be deleted, the relationship will disappear.
	 */
	public void oneToOneBiDirRelationship(boolean isOrmXml) {

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName1 = editorProxy.getJPTObjectForGefElement(entity1).getName();
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);
		String entityName2 = editorProxy.getJPTObjectForGefElement(entity2).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName1));
			assertNotNull(ormXml.getPersistentType(entityName2));
		}

		// create One-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		// create One-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		// create One-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates "One to One" bidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	public void selfOneToOneBiDirRelationship(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		String entityName = editorProxy.getJPTObjectForGefElement(entity1).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName));
		}
		// create One-to-One bidirectional self relation from entity1 to entity1
		editorProxy
				.testSelfBiDirRel(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						entity1,
						IRelation.RelType.ONE_TO_ONE,
						MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates "One to Many" unidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	public void oneToManyUniDirRelationship(boolean isOrmXml) {

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName1 = editorProxy.getJPTObjectForGefElement(entity1).getName();
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);
		String entityname2 = editorProxy.getJPTObjectForGefElement(entity2).getName();

		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName1));
			assertNotNull(ormXml.getPersistentType(entityname2));
		}
		// create One-to-Many unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_MANY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		// create One-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_MANY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates "One to Many" unidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	public void selfOneToManyUniDirRelationship(boolean isOrmXml) {

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName = editorProxy.getJPTObjectForGefElement(entity1).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName));
		}

		// create One-to-Many unidirectional self relation from entity1 to
		// entity1

		editorProxy
				.testSelfUniDirRelation(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						entity1,
						IRelation.RelType.ONE_TO_MANY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates "Many to One" unidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	public void manyToOneUniDirRelationship(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName1 = editorProxy.getJPTObjectForGefElement(entity1).getName();
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);
		String entityName2 = editorProxy.getJPTObjectForGefElement(entity2).getName();
		if(isOrmXml) {
			assertNotNull(ormXml.getPersistentType(entityName1));
			assertNotNull(ormXml.getPersistentType(entityName2));
		}

		// create Many-to-One unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		// create Many-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates "Many to One" unidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	public void selfManyToOneUniDirRelationship(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName = editorProxy.getJPTObjectForGefElement(entity1).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName));
		}

		// create Many-to-One unidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						entity1,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates a "Many to One" bidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the inverse attribute will be deleted the connection will be
	 * transformed into many-to-one unidirectional relationship. Test that if
	 * the owner attribute will be deleted, the relationship will disappear.
	 */
	public void manyToOneBiDirRelationship(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName1 = editorProxy.getJPTObjectForGefElement(entity1).getName();
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);
		String entityName2 = editorProxy.getJPTObjectForGefElement(entity2).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName1));
			assertNotNull(ormXml.getPersistentType(entityName2));
		}

		// create Many-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelWithTwoMappingTypes(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		// create Many-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelWithTwoMappingsWithoutInverseAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		// create Many-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelWithTwoMappingsWithoutOwnerAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates a "Many to One" bidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	public void selfManyToOneBiDirRelationship(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName = editorProxy.getJPTObjectForGefElement(entity1).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName));
		}

		// create Many-to-Many bidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfBiDirRelWithTwoMappings(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						entity1,
						IRelation.RelType.MANY_TO_ONE,
						MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
						MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, true, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);
		jpaDiagramEditor.save();
	}

	/**
	 * Creates "Many to Many" unidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 */
	public void manyToManyUniDirRelationship(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName1 = editorProxy.getJPTObjectForGefElement(entity1).getName();
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);
		String entityName2 = editorProxy.getJPTObjectForGefElement(entity2).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName1));
			assertNotNull(ormXml.getPersistentType(entityName2));
		}

		// create Many-to-Many unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		// create Many-to-Many bidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates "Many to Many" unidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	public void selfManyToManyUniDirRelationship(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName = editorProxy.getJPTObjectForGefElement(entity1).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName));
		}

		// create Many-to-Many unidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfUniDirRelation(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						entity1,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates a "Many to Many" bidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the inverse attribute will be deleted the connection will be
	 * transformed into many-to-many unidirectional relationship. Test that if
	 * the owner attribute will be deleted, the relationship will disappear.
	 */
	public void manyToManyBiDirRelationship(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName1 = editorProxy.getJPTObjectForGefElement(entity1).getName();
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);
		String entityName2 = editorProxy.getJPTObjectForGefElement(entity2).getName();
		if(isOrmXml) {
			assertNotNull(ormXml.getPersistentType(entityName1));
			assertNotNull(ormXml.getPersistentType(entityName2));
		}

		// create Many-to-Many bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		// create Many-to-Many bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		// create Many-to-Many bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates a "Many to Many" bidirectional self relationship (from entity1 to
	 * entity1). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore.
	 */
	public void selfManyToManyBiDirRelationship(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName = editorProxy.getJPTObjectForGefElement(entity1).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName));
		}
		// create Many-to-Many bidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfBiDirRel(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						entity1,
						IRelation.RelType.MANY_TO_MANY,
						MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, false, isOrmXml);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Creates a new Inherited entity by entity. Assert that the inherited
	 * entity does not contain a primary key.
	 */
	public void inheritedEntityByEntity(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName = editorProxy.getJPTObjectForGefElement(entity1).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(entityName));
		}

		editorProxy.createInheritedEntity(entity1, jpaProject,
				JptJpaUiDetailsMessages.EntityUiProvider_linkLabel, false, false);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}

	/**
	 * Create two entities in the diagram. From the "Inheritance" section of the palette
	 * select "Inherit Persistent Type". Clock on the first entity and then click on the
	 * second one. Assert that an is-a relation is created.
	 */
	public void isARelationBetweenExistingEntities(boolean isOrmXml) {
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart superclass = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String superclassName = editorProxy.getJPTObjectForGefElement(
				superclass).getSimpleName();

		SWTBotGefEditPart subclass = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String subclassName = editorProxy.getJPTObjectForGefElement(subclass)
				.getSimpleName();
		
		if(isOrmXml) {
			assertNotNull(ormXml.getPersistentType(subclassName));
			assertNotNull(ormXml.getPersistentType(superclassName));
		}

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureName);
		jpaDiagramEditor.click(superclass);
		jpaDiagramEditor.click(subclass);
		
		editorProxy.waitASecond();

		editorProxy.testCreateAndDeleteIsARelation(superclass, subclassName,
				JptJpaUiDetailsMessages.EntityUiProvider_linkLabel, false,
				superclassName, subclass, true);

		editorProxy.deleteDiagramElements(isOrmXml);

		jpaDiagramEditor.save();
	}
	
	/**
	 * Create two entities in the diagram. From the second entity, remove the default
	 * primary key attribute. From the "Derived Identifiers" select "One-to-One" unidirectional
	 * relation feature and click first on the second entity and then on the first one.
	 * Assert that the connection appears. Assert that the owner attribute of the relation is mapped
	 * as primary key attribute in the second entity and there is no "Relation Attributes" section.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void simpleDerivedIdWithoutDefaultPK(boolean isOrmXml){		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}
		
		editorProxy.deleteEntityDefaultPK(dependentEntity, isOrmXml);	
		
		editorProxy.testUniDirDerivedIdWithIdAnnotation(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"Id", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, isOrmXml);

		editorProxy.testBiDirDerivedIdWithIdAnnotation(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"Id", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdAnnotation(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"Id", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, isOrmXml);

		editorProxy.testBiDirDerivedIdWithIdAnnotation(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"Id", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, isOrmXml);
	}
	
	/**
	 * Create two entities in the diagram. From the "Derived Identifiers" select "One-to-One"
	 * unidirectional relation feature and click first on the second entity and then on the first one.
	 * Assert that the connection appears. Assert that the owner attribute of the relation is mapped
	 * with the MapsId annotation in the second entity and there is no "Relation Attributes" section.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void simpleDerivedIdWithDefaultPK(boolean isOrmXml){		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}
		
		editorProxy.testUniDirDerivedIdWithIdAnnotation(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, isOrmXml);

		editorProxy.testBiDirDerivedIdWithIdAnnotation(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdAnnotation(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, isOrmXml);

		editorProxy.testBiDirDerivedIdWithIdAnnotation(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, isOrmXml);
	}
	
	
	/**
	 * Create two entities and one embeddable in the diagram. From the second entity, remove the default
	 * primary key attribute. From the "Composition" section, select "Embed Single object" and embed the
	 * embeddable into the entity2. From the "JPA Details" view, change the mapping of the embedded attribute
	 * in the entity2 to EmbeddedId. From the "Derived Identifiers" select "One-to-One"
	 * unidirectional relation feature and click first on the second entity and then on the first one.
	 * Assert that the connection appears. Assert that the owner attribute of the relation is mapped
	 * with the MapsId annotation in the second entity and there is no "Relation Attributes" section.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithEmbeddedPK(boolean isOrmXml){		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}
		
		editorProxy.deleteEntityDefaultPK(dependentEntity, isOrmXml);	
		
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(300, 50, jpaProject);
		String embeddableName = editorProxy.getJPTObjectForGefElement(embeddable).getName();
		if(isOrmXml) {
			assertNotNull(ormXml.getPersistentType(embeddableName));
		}
		
		editorProxy.addEmbeddedIdToEntity(dependentEntity, embeddable, isOrmXml);
		
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, null, isOrmXml);

		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, null, isOrmXml);
	}
	
	/**
	 * Create two entities in the diagram. Create a simple java class. From the second entity, remove the default
	 * primary key attribute. Use the created java class as IDClass in entity2. From the "Derived Identifiers" select "One-to-One"
	 * unidirectional relation feature and click first on the second entity and then on the first one.
	 * Assert that the connection appears. Assert that the owner attribute of the relation is mapped
	 * as primary key attribute in the second entity and there is no "Relation Attributes" section.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithIdClassPK(boolean isOrmXml) throws Exception{		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		PersistentType depEntity = editorProxy.getJPTObjectForGefElement(dependentEntity);
		String dependentEntityName = depEntity.getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}
		JavaPersistentType idClass = editorProxy.setIdClass(dependentEntity, "TestIdClass", jpaProject, true);
		
		assertNotNull(idClass);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, null, isOrmXml);

		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, null, isOrmXml);		
	}
	
	/**
	 * Create two entities in the diagram. Create a simple java class. Remove the default
	 * primary key attribute from both entities. Use the created java class as IDClass in both entities.
	 * From the "Derived Identifiers" select "One-to-One" unidirectional relation feature and click first on the second entity and then on the first one.
	 * Assert that the connection appears. Assert that the owner attribute of the relation is mapped
	 * as primary key attribute in the second entity and there is no "Relation Attributes" section.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithSameIdClassPK(boolean isOrmXml) throws Exception{		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}
		
		JavaPersistentType idClass = editorProxy.setIdClass(parentEntity, "TestParentIdClass", jpaProject, true);
		String idClassFQN = "TestParentIdClass";
		
		PersistentType parentJPTType= editorProxy.getJPTObjectForGefElement(dependentEntity);
		
		TypeMapping typeMapping = JpaArtifactFactory.instance().getTypeMapping(parentJPTType);
		assertTrue(Entity.class.isInstance(typeMapping));
		((Entity)typeMapping).getIdClassReference().setSpecifiedIdClassName(idClassFQN);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, true, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, true, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, true, null, isOrmXml);

		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, true, null, isOrmXml);		
	}
	
	/**
	 * Create two entities in the diagram. Create two simple java class. Remove the default
	 * primary key attribute from both entities. Use the first java class as IDClass in the first entity and the second
	 * java class as IdClass for the second entity. From the "Derived Identifiers" select "One-to-One"
	 * unidirectional relation feature and click first on the second entity and then on the first one.
	 * Assert that the connection appears. Assert that the owner attribute of the relation is mapped
	 * as primary key attribute in the second entity and there is no "Relation Attributes" section.
	 * Assert that a new helper attribute is automatically added in the second java class and its type is the type
	 * of the first java class, used as IDClass.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithDifferentIdClassPK(boolean isOrmXml) throws Exception{		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		PersistentType parEntity = editorProxy.getJPTObjectForGefElement(parentEntity);
		String parentEntityName = parEntity.getName();

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}
		
		JavaPersistentType parentIdClass = editorProxy.setIdClass(parentEntity, "TestParentIdClass", jpaProject, true);
		assertNotNull(parentIdClass);
		String idClassFQN = parentIdClass.getName();
//		String idClassFQN = "TestParentIdClass";

		JavaPersistentType idClass = editorProxy.setIdClass(dependentEntity, "TestIdClass", jpaProject, true);
		
		assertNotNull(idClass);
				
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, idClassFQN, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, idClassFQN, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, idClassFQN, isOrmXml);

		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, idClassFQN, isOrmXml);
	}	
	
	/**
	 * Create two entities and one embeddable in the diagram. Remove the default primary key attribute from both entities. 
	 * Embed the embeddable in both entities and change the mappig of the embedded attributes to EmbeddedIds.
	 * From the "Derived Identifiers" select "One-to-One" unidirectional relation feature and click first on the second
	 * entity and then on the first one. Assert that the connection appears. Assert that the owner attribute of the relation is mapped
	 * with MapsId in the second entity and there is no "Relation Attributes" section.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithSameEmbeddedPK(boolean isOrmXml){		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}
		
		editorProxy.deleteEntityDefaultPK(dependentEntity, isOrmXml);
		editorProxy.deleteEntityDefaultPK(parentEntity, isOrmXml);
		
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(300, 50, jpaProject);
		String embeddableName = editorProxy.getJPTObjectForGefElement(embeddable).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(embeddableName));
		}
		
		editorProxy.addEmbeddedIdToEntity(dependentEntity, embeddable, isOrmXml);
		
		editorProxy.addEmbeddedIdToEntity(parentEntity, embeddable, isOrmXml);
		
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, true, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, true, null, isOrmXml);		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, true, null, isOrmXml);

		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, true, null, isOrmXml);		
	}
	
	/**
	 * Create two entities and two embeddable in the diagram. Remove the default primary key attribute from both entities. 
	 * Embed the first embeddable in the first enetity and the second one in the second entity. Change the mapping of the
	 * embedded attributes to EmbeddedIds. From the "Derived Identifiers" select "One-to-One" unidirectional relation feature
	 * and click first on the second entity and then on the first one. Assert that the connection appears. Assert that the owner
	 * attribute of the relation is mapped with MapsId in the second entity and there is no "Relation Attributes" section.
	 * Assert that e new helper attribute is added in the second embeddable and its type is of the first embeddable.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithDifferentEmbeddedPK(boolean isOrmXml){		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();

		editorProxy.deleteEntityDefaultPK(parentEntity, isOrmXml);
		
		editorProxy.waitASecond();
		
		SWTBotGefEditPart parentEmbeddable = editorProxy.addEmbeddableToDiagram(300, 50, jpaProject);
		String parentEmbeddableName = editorProxy.getJPTObjectForGefElement(parentEmbeddable).getName();
		
		editorProxy.addEmbeddedIdToEntity(parentEntity, parentEmbeddable, isOrmXml);
		
		editorProxy.waitASecond();
		
		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		editorProxy.deleteEntityDefaultPK(dependentEntity, isOrmXml);
		
		editorProxy.waitASecond();

		SWTBotGefEditPart dependentEmbeddable = editorProxy.addEmbeddableToDiagram(300, 300, jpaProject);				
		String dependentEmbeddableName = editorProxy.getJPTObjectForGefElement(dependentEmbeddable).getName();
		editorProxy.addEmbeddedIdToEntity(dependentEntity, dependentEmbeddable, isOrmXml);
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(parentEmbeddableName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEmbeddableName));
		}
		
		String idClassFQN = editorProxy.getJPTObjectForGefElement(parentEmbeddable).getName();

		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, dependentEmbeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, idClassFQN, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, dependentEmbeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, idClassFQN, isOrmXml);		
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, dependentEmbeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, idClassFQN, isOrmXml);

		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, dependentEmbeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, idClassFQN, isOrmXml);
	}
	
	/**
	 * Create two entities and one embeddable in the diagram. Create a simple java class. Remove the default primary key attribute
	 * from both entities. Set the java class as IDClass to the first entity and embed the embeddable in the second entity.
	 * Change the mapping of the embedded attributes to EmbeddedIds. From the "Derived Identifiers" select "One-to-One" unidirectional relation feature
	 * and click first on the second entity and then on the first one. Assert that the connection appears. Assert that the owner
	 * attribute of the relation is mapped with MapsId in the second entity and there is no "Relation Attributes" section.
	 * Assert that e new helper attribute is added in the embeddable and its type is of the java class used as IdClass.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithIdClassAndEmbeddedPK(boolean isOrmXml) throws Exception {		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}
		
		editorProxy.deleteEntityDefaultPK(dependentEntity, isOrmXml);
		String idClassFQN = "org.persistence.TestIdClass";
		
		editorProxy.setIdClass(parentEntity, "TestIdClass", jpaProject, true);
		
		editorProxy.waitASecond();
		
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(300, 50, jpaProject);
		String embeddableName = editorProxy.getJPTObjectForGefElement(embeddable).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(embeddableName));
		}
		editorProxy.addEmbeddedIdToEntity(dependentEntity, embeddable, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, idClassFQN, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, idClassFQN, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, idClassFQN, isOrmXml);

		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, idClassFQN, isOrmXml);
	}
	
	
	/**
	 * Create two entities and one embeddable in the diagram.Remove the default primary key attribute
	 * from both entities. Set the embeddable as IDClass to the first entity and embed the embeddable in the second entity.
	 * Change the mapping of the embedded attributes to EmbeddedIds. From the "Derived Identifiers" select "One-to-One" unidirectional relation feature
	 * and click first on the second entity and then on the first one. Assert that the connection appears. Assert that the owner
	 * attribute of the relation is mapped with MapsId in the second entity and there is no "Relation Attributes" section.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithIdClassAndSameEmbeddedPK(boolean isOrmXml) throws Exception {		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();
		
		editorProxy.deleteEntityDefaultPK(parentEntity, isOrmXml);
		String attrnamString = editorProxy.getUniqueAttrName(parentEntity);
		editorProxy.addAttributeToJPT(parentEntity, attrnamString, isOrmXml);

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		editorProxy.deleteEntityDefaultPK(dependentEntity, isOrmXml);
		
		editorProxy.waitASecond();

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(300, 50, jpaProject);
		String embeddableName = editorProxy.getJPTObjectForGefElement(embeddable).getName();
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
			assertNotNull(ormXml.getPersistentType(embeddableName));
		}
		
		editorProxy.addEmbeddedIdToEntity(dependentEntity, embeddable, isOrmXml);
		
		String attrname = editorProxy.getUniqueAttrName(embeddable);
		jpaDiagramEditor.activateDefaultTool();
		editorProxy.waitASecond();
		editorProxy.addAttributeToJPT(embeddable, attrname, isOrmXml);
	
		String className = editorProxy.getJPTObjectForGefElement(embeddable).getSimpleName();
		
		editorProxy.setIdClass(parentEntity, className, jpaProject, false);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, true, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, true, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, true, null, isOrmXml);

		editorProxy.testUniDirDerivedIdWithEmbeddedPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, embeddable, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, true, null, isOrmXml);
	}

	/**
	 * Create two entities and one embeddable in the diagram. Create a simple java class. Remove the default primary key attribute
	 * from both entities. Embed the embeddable in the first entity and set the java class as IDClass to the second entity.
	 * Change the mapping of the embedded attributes to EmbeddedIds. From the "Derived Identifiers" select "One-to-One" unidirectional relation feature
	 * and click first on the second entity and then on the first one. Assert that the connection appears. Assert that the owner
	 * attribute of the relation is mapped as primary key attribute in the second entity and there is no "Relation Attributes" section.
	 * Assert that e new helper attribute is added in the java class and its type is of the embeddable.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithEmbeddedPkAndIdClass(boolean isOrmXml) throws Exception {		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();
		
		editorProxy.deleteEntityDefaultPK(parentEntity, isOrmXml);
		
		editorProxy.waitASecond();
		
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(300, 50, jpaProject);
		String embeddableName = editorProxy.getJPTObjectForGefElement(embeddable).getName();
		
		editorProxy.addEmbeddedIdToEntity(parentEntity, embeddable, isOrmXml);

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(embeddableName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}
		
		String idClassFQN = editorProxy.getJPTObjectForGefElement(embeddable).getName();

		JavaPersistentType idClass = editorProxy.setIdClass(dependentEntity, "TestIdClass", jpaProject, true);
		assertNotNull(idClass);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, idClassFQN, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, idClassFQN, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, false, idClassFQN, isOrmXml);

		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, false, idClassFQN, isOrmXml);		
	}
	
	/**
	 * Create two entities and one embeddable in the diagram. Remove the default primary key attribute
	 * from both entities. Embed the embeddable in the first entity and set the embeddable as IDClass to the second entity.
	 * Change the mapping of the embedded attributes to EmbeddedIds. From the "Derived Identifiers" select "One-to-One" unidirectional relation feature
	 * and click first on the second entity and then on the first one. Assert that the connection appears. Assert that the owner
	 * attribute of the relation is mapped as primary key attribute in the second entity and there is no "Relation Attributes" section.
	 * Test that the created relation is successfully deleted. Repeats all steps for the other three
	 * types of relation also.
	 */
	public void derivedIdWithEmbeddedPkAndSameIdClass(boolean isOrmXml) throws Exception {		
		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart parentEntity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String parentEntityName = editorProxy.getJPTObjectForGefElement(parentEntity).getName();
		
		editorProxy.deleteEntityDefaultPK(parentEntity, isOrmXml);
		
		editorProxy.waitASecond();
		
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(300, 50, jpaProject);
		PersistentType embeddableJPT = editorProxy.getJPTObjectForGefElement(embeddable); 
		String embeddableName = embeddableJPT.getName();
		
		editorProxy.addEmbeddedIdToEntity(parentEntity, embeddable, isOrmXml);
		
		String attrname = editorProxy.getUniqueAttrName(embeddable);
		jpaDiagramEditor.activateDefaultTool();
		editorProxy.waitASecond();
		editorProxy.addAttributeToJPT(embeddable, attrname, isOrmXml);

		SWTBotGefEditPart dependentEntity = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String dependentEntityName = editorProxy.getJPTObjectForGefElement(dependentEntity).getName();
		
		if(isOrmXml){
			assertNotNull(ormXml.getPersistentType(parentEntityName));
			assertNotNull(ormXml.getPersistentType(embeddableName));
			assertNotNull(ormXml.getPersistentType(dependentEntityName));
		}

		editorProxy.getJPTObjectForGefElement(dependentEntity);
		JavaPersistentType idClass = editorProxy.setIdClass(dependentEntity, embeddableJPT.getSimpleName(), jpaProject, false);
		
		editorProxy.deleteEntityDefaultPK(dependentEntity, isOrmXml);
		String attrnamString = editorProxy.getUniqueAttrName(dependentEntity);
		SWTBotGefEditPart attr = editorProxy.addAttributeToJPT(dependentEntity, attrnamString, isOrmXml);
		PersistentAttribute jpa  = editorProxy.getJPAObjectForGefElement(attr);
		
		jpa.getJavaPersistentAttribute().setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		if(isOrmXml) {
			OrmPersistentType ormJpt = ormXml.getPersistentType(jpa.getDeclaringPersistentType().getName());
			assertNotNull(ormJpt);
			OrmPersistentAttribute ormAttribute = ormJpt.getAttributeNamed(jpa.getName());
			assertNotNull(ormAttribute);
			assertFalse(ormAttribute.isVirtual());
			((OrmSpecifiedPersistentAttribute)ormAttribute).setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		}
		
		if(embeddableJPT instanceof OrmPersistentType){
			idClass = ((OrmPersistentType)embeddableJPT).getJavaPersistentType();
		} else {
			idClass = (JavaPersistentType) embeddableJPT;
		}
		
		assertNotNull(idClass);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, true, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.ONE_TO_ONE, "OneToOne",
				"MapsId", MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, true, null, isOrmXml);
		
		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, false, true, null, isOrmXml);

		editorProxy.testUniDirDerivedIdWithIdClassPk(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				dependentEntity, parentEntity, idClass, IRelation.RelType.MANY_TO_ONE, "ManyToOne",
				"MapsId", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, true, true, null, isOrmXml);
	}

	public void setOrmXml(OrmXml ormXml) {
		this.ormXml = ormXml;
	}

}
