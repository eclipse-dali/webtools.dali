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
package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.entity;

import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.internal.parts.DiagramEditPart;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions.ElementAppearsInDiagram;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions.ElementIsShown;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.AbstractSwtBotEditorTest;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.JPACreateFactory;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
@SuppressWarnings("restriction")
public class EntitiesInDiagramSWTBotTest extends AbstractSwtBotEditorTest {

	private static String TEST_PROJECT = "Test_" + System.currentTimeMillis();
	private static JpaProject jpaProject;

	@BeforeClass
	public static void beforeClass() throws Exception {
		jpaProject = createJPa20Project(TEST_PROJECT);
	}

	/**
	 * Add entity to diagram and check that it contains a "Primary Key" section
	 * with one attribute "id" and no "Relation Attributes" and
	 * "Other Attributes" sections.
	 */
	@Test
	public void testAddEntity() {
		Utils.sayTestStarted("testAddEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		editorProxy.addEntityToDiagram(50, 50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		jpaDiagramEditor.save();
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddEntity");
	}

	/**
	 * Remove an entity from the diagram using the entity's context button
	 * "Delete"
	 */
	@Test
	public void testRemoveEntityViaButton() {
		Utils.sayTestStarted("testRemoveEntityViaButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		editorProxy.deleteJPTViaButton(entity, true);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveEntityViaButton");
	}

	/**
	 * Remove an entity from the diagram using the entity's context menu
	 * "Delete"
	 */
	@Test
	public void testRemoveEntityViaContextMenu() {
		Utils.sayTestStarted("testRemoveEntityViaContextMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		editorProxy.deleteJPTViaMenu(entity);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveEntityViaContextMenu");
	}

	@Test
	public void testAddElementCollectionAttribute() {
		Utils.sayTestStarted("testAddElementCollectionAttribute");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								entity));

		String attributeName = editorProxy.getUniqueAttrName(entity);
		SWTBotGefEditPart attribute = editorProxy.addElementCollectionAttributeToJPT(entity, attributeName);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		editorProxy.removeAttributeViaButton(entity, attribute, attributeName, false);
		
		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddElementCollectionAttribute");
	}

	/**
	 * Removes the attribute using the "Delete Attribute" context button.
	 */
	@Test
	public void testRemoveAttributeViaContextButton() {
		Utils.sayTestStarted("testRemoveAttributeViaContextButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(entity);
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(entity, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAttributeViaButton(entity, attribute, attributeName, false);

		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAttributeViaContextButton");
	}

	/**
	 * Removes the attribute using the "Delete" context menu.
	 */
	@Test
	public void testRemoveAttributeViaMenu() {
		Utils.sayTestStarted("testRemoveAttributeViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(entity);
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(entity, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAttributeViaMenu(entity, attribute, attributeName);

		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAttributeViaMenu");
	}

	/**
	 * Adds a new attribute and rename it
	 */
	@Test
	public void testDirectEditingAttribute() {
		Utils.sayTestStarted("testDirectEditingAttribute");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(entity);
		editorProxy.directEditAttribute(entity, attributeName);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDirectEditingAttribute");
	}

	/**
	 * Adds a new entity and rename it
	 */
	@Ignore
	@Test
	public void testDirectEditingEntity() {
		Utils.sayTestStarted("testDirectEditingEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		
		String oldEntityName = editorProxy.getJPTObjectForGefElement(entity).getSimpleName();
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.moveMouse(100, 70);
		entity.click();
		entity.activateDirectEdit();
		jpaDiagramEditor.directEditType("NewEntityName");
		editorProxy.moveMouse(0, 0);

		SWTBotGefEditPart oldEntity = jpaDiagramEditor.getEditPart(oldEntityName);
		SWTBotGefEditPart newEntity = jpaDiagramEditor
				.getEditPart("NewEntityName");
		assertNotNull("The entity must be renamed!", newEntity);
		assertNull("The attribute must be renamed!", oldEntity);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDirectEditingEntity");
	}

	/**
	 * Test that the source of the entity is opened, when is double clicked on
	 * it
	 */
	@Ignore
	@Test
	public void testDoubleClickOnEntity() {
		Utils.sayTestStarted("testDoubleClickOnEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		editorProxy.moveMouse(100, 70);
		jpaDiagramEditor.doubleClick(entity);
		editorProxy.moveMouse(0, 0);

		SWTBotEditor activeEditor = workbenchBot.activeEditor();
		assertEquals("The Java editor of the enity did not open!",
				"Entity1.java", activeEditor.getTitle());
		activeEditor.close();

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDoubleClickOnEntity");
	}

	/**
	 * Change the attribute type.
	 */
	@Test
	public void testChangeAttributeType() {
		Utils.sayTestStarted("testChangeAttributeType");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								entity));

		String attributeName = editorProxy.getUniqueAttrName(entity);
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(entity,
				attributeName, false);
		assertNotNull("The attribute must not be renamed!", attribute);

		final IFeatureProvider fp = ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();

		String currentAttributeType = editorProxy.getAttributeType(
				attributeName, fp);
		assertEquals("java.lang.String", currentAttributeType);

		SWTBotShell changeTypeDialog = editorProxy
				.getSelectNewAttributeTypeDialog(attribute);
		SWTBotText attributeType = editorProxy
				.getNewTypeInputField(changeTypeDialog);

		// test invalid attribute type
		attributeType.setText("");
		assertFalse(editorProxy.getOkButton(changeTypeDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(changeTypeDialog).isEnabled());
		SWTBotText dialogError = editorProxy
				.getDialogErroMessage(changeTypeDialog);
		assertEquals(" The new type name must not be empty",
				dialogError.getText());
		// cancel the dialog
		editorProxy.getCancelButton(changeTypeDialog).click();

		// assert that the attribute type is not changed
		currentAttributeType = editorProxy.getAttributeType(attributeName, fp);
		assertEquals("The attribute type must not be changed!",
				"java.lang.String", currentAttributeType);

		changeTypeDialog = editorProxy
				.getSelectNewAttributeTypeDialog(attribute);
		attributeType = editorProxy.getNewTypeInputField(changeTypeDialog);

		// change the attribute type to int
		attributeType.setText("int");
		assertTrue(editorProxy.getOkButton(changeTypeDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(changeTypeDialog).isEnabled());
		// confirm the dialog
		editorProxy.getOkButton(changeTypeDialog).click();
		editorProxy.waitASecond();
		// assert that the attribute's type is changed
		String newAttributeType = editorProxy
				.getAttributeType(attributeName, fp);
		assertFalse("The attribute type must be changed!",
				("java.lang.String").equals(newAttributeType));
		assertEquals("The attribute type must be changed!", "int",
				newAttributeType);

		assertTrue("Editor must be dirty!", jpaDiagramEditor.isDirty());
		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testChangeAttributeType");
	}

	/**
	 * Rename the entity using its context menu
	 * "Refactor Entity Class -> Rename..."
	 */
	@Ignore
	@Test
	public void testRenameEntityViaMenu() {
		Utils.sayTestStarted("testRenameEntityViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String oldEntityName = editorProxy.getJPTObjectForGefElement(entity).getSimpleName();

		entity.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_renameEntityClass);

		SWTBotShell renameEntityDialog = editorProxy.getRenameEntityDialog();
		SWTBotText entityName = renameEntityDialog.bot().textWithLabel(
				"New name:");

		// test invalid entity name
		entityName.setText("");
		assertFalse(editorProxy.getFinishButton(renameEntityDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(renameEntityDialog).isEnabled());

		entityName.setText("NewEntityName");
		assertTrue(editorProxy.getFinishButton(renameEntityDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(renameEntityDialog).isEnabled());

		editorProxy.getFinishButton(renameEntityDialog).click();
		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, "NewEntityName"),
				10000);

		entity = jpaDiagramEditor.getEditPart("NewEntityName");
		assertNotNull("Entity name must be changed!", entity);
		assertNull("Entity naem must be changed!",
				jpaDiagramEditor.getEditPart(oldEntityName));

		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRenameEntityViaMenu");
	}

	/**
	 * Move the entity class using the entity's context menu
	 * "Refactor Entity Class -> Move..."
	 */
	@Ignore
	@Test
	public void testMoveEntityViaMenu() throws JavaModelException {
		Utils.sayTestStarted("testMoveEntityViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName = editorProxy.getJPTObjectForGefElement(entity).getSimpleName();
		JpaArtifactFactory factory = JpaArtifactFactory.instance();

		String packageName = factory
				.getMappedSuperclassPackageDeclaration(editorProxy
						.getJPTObjectForGefElement(entity));

		entity.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_moveEntityClass);

		SWTBotShell moveEntityDialog = editorProxy.getMoveEntityDialog();
		moveEntityDialog.bot().tree().getTreeItem(TEST_PROJECT).select()
				.expandNode("src").expandNode("org").select().click();
		assertTrue(editorProxy.getOkButton(moveEntityDialog).isEnabled());
		editorProxy.getOkButton(moveEntityDialog).click();
		editorProxy.waitASecond();

		entity = jpaDiagramEditor.getEditPart(entityName);
		String newEntityPackage = factory
				.getMappedSuperclassPackageDeclaration(editorProxy
						.getJPTObjectForGefElement(entity));
		assertFalse("Entity must be moved!",
				packageName.equals(newEntityPackage));
		assertTrue("Entity must be changed!", newEntityPackage.equals("org"));

		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testMoveEntityViaMenu");
	}

	/**
	 * Collapse/expand entity using its context buttons
	 */
	@Test
	public void testCollapseExapandEntityViaContextButton() {
		Utils.sayTestStarted("testCollapseExapandEntityViaContextButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		editorProxy.collapseExpandJPTViaButton(entity);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExapandEntityViaContextButton");
	}

	/**
	 * Collapse/expand entity using its context menus
	 */
	@Test
	public void testCollapseExapandEntityViaMenu() {
		Utils.sayTestStarted("testCollapseExapandEntityViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		editorProxy.collapseExpandJPTViaMenu(entity);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExapandEntityViaMenu");
	}

	/**
	 * Collapse/expand all entities using the context menus
	 */
	@Test
	public void testCollapseExapandAllEntitiesViaMenu() {
		Utils.sayTestStarted("testCollapseExapandAllEntitiesViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(300, 50,
				jpaProject);

		editorProxy.collapseExpandAllJPTsViaMenu(entity1, entity2);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExapandAllEntitiesViaMenu");
	}

	/**
	 * Add a new attribute without saving the entity and call the
	 * "Discard Changes" context menu. Assert that the newly added attribute is
	 * removed and the entity does not contain unsaved changes.
	 */
	@Ignore
	@Test
	public void testDiscardChanges() {
		Utils.sayTestStarted("testDiscardChanges");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(entity);
		editorProxy.discardChanges(entity, attributeName);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDiscardChanges");
	}

	/**
	 * Add a new attribute without saving the entity and call the entity's
	 * context menu "Remove All Entities from Diagram -> ... and Discard
	 * Changes" context menu. Assert that the diagram is empty. Call "Show All
	 * Entities" context menu and assert that the newly added attribute is
	 * removed and the entity does not contain unsaved changes.
	 */
	@Ignore
	@Test
	public void testRemoveAndDiscardChangesViaMenu() {
		Utils.sayTestStarted("testRemoveAndDiscardChangesViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(entity);
		editorProxy.removeAndDiscardChangesViaMenu(entity, attributeName);

		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAndDiscardChangesViaMenu");
	}

	/**
	 * Add a new attribute without saving the entity and call the entity's
	 * context menu "Remove All Entities from Diagram -> ... and Save
	 * Changes" context menu. Assert that the diagram is empty. Call "Show All
	 * Entities" context menu and assert that the newly added attribute is added
	 * and the entity does not contain unsaved changes.
	 */
	@Test
	public void testRemoveAndSaveChangesViaMenu() {
		Utils.sayTestStarted("testRemoveAndSaveChangesViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(entity);
		editorProxy.removeAndSaveChangesViaMenu(entity, attributeName);

		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAndSaveChangesViaMenu");
	}

	/**
	 * Add a new attribute to the entity. From the entity's context menu select
	 * "Save". Assert that the entity does not contain any unsaved changes, but
	 * the diagram editor is still dirty.
	 */
	@Test
	public void testSaveOnlyEntity() {
		Utils.sayTestStarted("testSaveOnlyEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String attributeName = editorProxy.getUniqueAttrName(entity);
		editorProxy.saveOnlyJPT(entity, attributeName);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testSaveOnlyEntity");
	}

	/**
	 * Create a JPA project and one entity in it. Open the JPA diagram editor
	 * and call the "Show All Entities" context menu. Assert that the previously
	 * created entity is shown in the diagram.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowAllEntities() throws Exception {
		Utils.sayTestStarted("testShowAllEntities");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		JPACreateFactory.instance().createEntity(jpaProject, "com.sap.test.Customer");
		assertTrue(jpaDiagramEditor.mainEditPart().children().isEmpty());

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_showAllTheEntities);

		bot.waitUntil(new ElementAppearsInDiagram(jpaDiagramEditor), 20000);

		List<SWTBotGefEditPart> entities = jpaDiagramEditor.mainEditPart()
				.children();
		assertFalse("Diagram editor must contain at least one entity!",
				entities.isEmpty());

		SWTBotGefEditPart entity = jpaDiagramEditor.getEditPart("Customer");
		assertNotNull(entity);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testShowAllEntities");
	}

	/**
	 * Collapse/expand "Primary Key" section by double click on it
	 */
	@Ignore
	@Test
	public void testCollapseExpandCompartmentByDoubleClick() {
		Utils.sayTestStarted("testCollapseExpandCompartmentByDoubleClick");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		editorProxy.addEntityToDiagram(50, 50, jpaProject);

		editorProxy.moveMouse(100, 100);
		SWTBotGefEditPart primaryKeySection = jpaDiagramEditor
				.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);

		int height = ((PictogramElement) primaryKeySection.part().getModel())
				.getGraphicsAlgorithm().getHeight();

		primaryKeySection.click();

		jpaDiagramEditor.doubleClick(primaryKeySection);
		primaryKeySection = jpaDiagramEditor
				.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		int newHeight = ((PictogramElement) primaryKeySection.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Primary Key section must be collapsed!",
				JPAEditorConstants.COMPARTMENT_MIN_HEIGHT, newHeight);
		assertTrue(newHeight < height);

		jpaDiagramEditor.doubleClick(primaryKeySection);
		primaryKeySection = jpaDiagramEditor
				.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		int newHeight1 = ((PictogramElement) primaryKeySection.part()
				.getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Primary Key section must be collapsed!", height,
				newHeight1);
		assertTrue(newHeight1 > JPAEditorConstants.COMPARTMENT_MIN_HEIGHT);

		editorProxy.moveMouse(0, 0);
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExpandCompartmentByDoubleClick");
	}

	/**
	 * Collapse/expand compartment by its context menu
	 */
	@Ignore
	@Test
	public void testCollapseExpandCompartmentByContextMenu() {
		Utils.sayTestStarted("testCollapseExpandCompartmentByContextMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		// editorProxy.moveMouse(jpaDiagramEditor, 100, 100);
		SWTBotGefEditPart primaryKeySection = editorProxy.getSectionInJPT(
				entity, JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		// SWTBotGefEditPart primaryKeySection =
		// jpaDiagramEditor.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		primaryKeySection.select();
		int height = ((PictogramElement) primaryKeySection.part().getModel())
				.getGraphicsAlgorithm().getHeight();

		primaryKeySection.click();

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseAttrGroupMenuItem);
		primaryKeySection = jpaDiagramEditor
				.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		int newHeight = ((PictogramElement) primaryKeySection.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Primary Key section must be collapsed!",
				JPAEditorConstants.COMPARTMENT_MIN_HEIGHT, newHeight);
		assertTrue(newHeight < height);

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandAttrMenuItem);
		primaryKeySection = jpaDiagramEditor
				.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		int newHeight1 = ((PictogramElement) primaryKeySection.part()
				.getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Primary Key section must be collapsed!", height,
				newHeight1);
		assertTrue(newHeight1 > JPAEditorConstants.COMPARTMENT_MIN_HEIGHT);

		editorProxy.moveMouse(0, 0);
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExpandCompartmentByContextMenu");
	}

	/**
	 * Test the integration between the JPA diagram editor and the JPA Details
	 * view: 1.) Close the JPA Details view 2.) Create an entity and call the
	 * context menu "Open JPA Details View" 3.) Assert that the JPA Details view
	 * is opened and the appropriate information is shown (the type is mapped as
	 * entity and the expandable sections are available). 4.) Change the mapping
	 * type to "Mapped Superclass" 5.) Change the mapping type to "Embeddable"
	 * and assert that the type is removed from the diagram 6.) Revert the
	 * mapping type to be "Entity" and call the "Show All Entities" context menu
	 * to visualize the entity in the diagram editor.
	 */
	@Ignore
	@Test
	public void testChangeEntityMappingTypeViaJPADetailsView() {
		Utils.sayTestStarted("testChangeEntityMappingTypeViaJPADetailsView");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		workbenchBot.viewByTitle("JPA Details").close();
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		String entityName = editorProxy.getJPTObjectForGefElement(entity).getSimpleName();

		jpaDiagramEditor.save();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_openJPADetailsView);

		// assert that the JPA Details view is opened
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		assertTrue("JPA Details view must be opened!",
				jpaDetailsView.isActive());

		// assert that the type is mapped as entity in the JPA Deatils view
		entity.click();
		SWTBot jpaDetailsBot = jpaDetailsView.bot();
		SWTBotStyledText styledText = jpaDetailsBot.styledText();
		assertEquals("Type '" +entityName+ "' is mapped as entity.",
				styledText.getText());
		assertNotNull("Entity must be shown in the diagram!",
				jpaDiagramEditor.getEditPart(entityName));
		// assert that the entity's sections are enabled
		assertTrue(jpaDetailsBot.label("Entity").isEnabled());
		assertTrue(jpaDetailsBot.label("Queries").isEnabled());
		assertTrue(jpaDetailsBot.label("Inheritance").isEnabled());
		assertTrue(jpaDetailsBot.label("Attribute Overrides").isEnabled());
		assertTrue(jpaDetailsBot.label("Primary Key Generation").isEnabled());
		assertTrue(jpaDetailsBot.label("Secondary Tables").isEnabled());

		// change the mapping type to "Mapped Superclass" and assert that the
		// type is mapped
		// as mapped superclass in the JPA Details view
		editorProxy.clickOnStyledText(styledText, 30);
		editorProxy.changeMappingtype("Mapped Superclass");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Type '" + entityName + "' is mapped as mapped superclass.",
				styledText.getText());
		assertNotNull("Entity must be shown in the diagram!",
				jpaDiagramEditor.getEditPart(entityName));
		// assert that the mapped superclass'es sections are enabled
		assertTrue(jpaDetailsBot.label("Mapped Superclass").isEnabled());
		assertTrue(jpaDetailsBot.label("Queries").isEnabled());

		// change the mapping type to "Embeddable" and assert that the element
		// is removed from the diagram
		// and the type is mapped as embeddable in the JPA Details view
		editorProxy.clickOnStyledText(styledText, 30);
		editorProxy.changeMappingtype("Embeddable");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Type '" + entityName + "' is mapped as embeddable.",
				styledText.getText());
		assertNotNull("Entity must disappear from the diagram!",
				jpaDiagramEditor.getEditPart(entityName));
		// assert that the embeddable's section is enabled
		// assertTrue(jpaDetailsBot.label("Embeddable").isEnabled());

		// revert the mapping type to Entity
		editorProxy.clickOnStyledText(styledText, 30);
		editorProxy.changeMappingtype("Entity");
		assertNotNull("Entity must disappear from the diagram!",
				jpaDiagramEditor.getEditPart(entityName));

		jpaDiagramEditor.click(0, 0);

		jpaDiagramEditor.select(entityName);
		styledText = jpaDetailsBot.styledText();
		assertEquals("Type '" + entityName + "' is mapped as entity.",
				styledText.getText());
		// assert that the entity's sections are enabled
		assertTrue(jpaDetailsBot.label("Entity").isEnabled());
		assertTrue(jpaDetailsBot.label("Queries").isEnabled());
		assertTrue(jpaDetailsBot.label("Inheritance").isEnabled());
		assertTrue(jpaDetailsBot.label("Attribute Overrides").isEnabled());
		assertTrue(jpaDetailsBot.label("Primary Key Generation").isEnabled());
		assertTrue(jpaDetailsBot.label("Secondary Tables").isEnabled());

		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testChangeEntityMappingTypeViaJPADetailsView");
	}

	/**
	 * Test the integration between the JPA diagram editor and the JPA Details
	 * view: 1.) Close the JPA Details view 2.) Create an entity and call the
	 * context menu "Open JPA Details View" 3.) Assert that the JPA Details view
	 * is opened and the appropriate information is shown (the id attribute is
	 * mapped as id and the expandable sections are available). 4.) Change the
	 * attribute mapping type to "Basic" and assert that the attribute is moved
	 * to "Other Attributes" compartment 5.) Change the attribute mapping type
	 * to "On-To-Many" and assert that the attribute is moved to
	 * "Relation Attributes" compartment 7.) Revert the attribute mapping to
	 * "ID" and assert that the attributes is moved back to the "Primary Key"
	 * compartment
	 */
	@Ignore
	@Test
	public void testChangeAttributeMappingTypeViaJPADetailsView() {
		Utils.sayTestStarted("testChangeAttributeMappingTypeViaJPADetailsView");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		workbenchBot.viewByTitle("JPA Details").close();
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		jpaDiagramEditor.save();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_openJPADetailsView);

		// assert that the JPA Details view is opened
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		assertTrue("JPA Details view must be opened!",
				jpaDetailsView.isActive());

		SWTBotGefEditPart primaryKeyAttr = jpaDiagramEditor.getEditPart("id");
		primaryKeyAttr.click();

		// assert that the default entity's attribute is mapped as primary key
		SWTBot jpaDetailsBot = jpaDetailsView.bot();
		SWTBotStyledText styledText = jpaDetailsBot.styledText();
		assertEquals("Attribute 'id' is mapped as ID.", styledText.getText());
		// assert that the attribute is under "Primary Key" section
		assertTrue(
				"Attribute must be in the \"Primary Key\" section of the entity!",
				editorProxy.isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape,
						entity));
		assertFalse(
				"Attribute must be in the \"Primary Key\" section of the entity!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes,
								entity));
		assertFalse(
				"Attribute must be in the \"Primary Key\" section of the entity!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								entity));
		// assert that the ID's sections are available
		assertTrue(jpaDetailsBot.label("ID").isEnabled());
		assertTrue(jpaDetailsBot.label("Type").isEnabled());
		assertTrue(jpaDetailsBot.label("Primary Key Generation").isEnabled());

		// change the attribute type to basic
		editorProxy.clickOnStyledText(styledText, 29);
		editorProxy.changeMappingtype("Basic");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Attribute 'id' is mapped as basic.", styledText.getText());
		// assert that the attribute is moved under "Other Attributes" section
		assertFalse(
				"Attribute must be in the \"Primary Key\" section of the entity!",
				editorProxy.isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape,
						entity));
		assertFalse(
				"Attribute must be in the \"Relation Attributes\" section of the entity!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes,
								entity));
		assertTrue(
				"Attribute must be in the \"Other Attributes\" section of the entity!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								entity));
		// assert that the Basic's sections are available
		assertTrue(jpaDetailsBot.label("Basic").isEnabled());
		assertTrue(jpaDetailsBot.label("Type").isEnabled());

		// change the attribute type to Many to Many
		editorProxy.clickOnStyledText(styledText, 29);
		editorProxy.changeMappingtype("Many to Many");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Attribute 'id' is mapped as many to many.",
				styledText.getText());
		// assert that the attribute is moved under the "Relation Attributes"
		// section
		assertFalse(
				"Attribute must be in the \"Primary key\" section of the entity!",
				editorProxy.isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape,
						entity));
		assertTrue(
				"Attribute must be in the \"Relation Attributes\" section of the entity!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes,
								entity));
		assertFalse(
				"Attribute must be in the \"Other Attributes\" section of the entity!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								entity));
		// assert that the Many to Many's sections are available
		assertTrue(jpaDetailsBot.label("Many to Many").isEnabled());
		assertTrue(jpaDetailsBot.label("Joining Strategy").isEnabled());
		assertTrue(jpaDetailsBot.label("Ordering").isEnabled());

		// revert the attribute type back to Id
		editorProxy.clickOnStyledText(styledText, 29);
		editorProxy.changeMappingtype("ID");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Attribute 'id' is mapped as ID.", styledText.getText());
		// assert that the attribute is under "Primary Key" section
		assertTrue(
				"Attribute must be in the \"Primary Key\" section of the entity!",
				editorProxy.isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape,
						entity));
		assertFalse(
				"Attribute must be in the \"Relation Attributes\" section of the entity!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes,
								entity));
		assertFalse(
				"Attribute must be in the \"Other Attributes\" section of the entity!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								entity));
		// assert that the ID's sections are available
		assertTrue(jpaDetailsBot.label("ID").isEnabled());
		assertTrue(jpaDetailsBot.label("Type").isEnabled());
		assertTrue(jpaDetailsBot.label("Primary Key Generation").isEnabled());

		entity.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testChangeAttributeMappingTypeViaJPADetailsView");
	}
}
