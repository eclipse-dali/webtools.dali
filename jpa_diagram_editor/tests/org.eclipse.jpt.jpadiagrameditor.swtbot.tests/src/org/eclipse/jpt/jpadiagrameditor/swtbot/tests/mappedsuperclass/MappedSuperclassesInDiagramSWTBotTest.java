/*******************************************************************************
 * Copyright (c) 2012, 2019 IBM Corporation and others.
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
package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.mappedsuperclass;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.ui.internal.parts.DiagramEditPart;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions.ElementIsShown;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.AbstractSwtBotEditorTest;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
@SuppressWarnings("restriction")
public class MappedSuperclassesInDiagramSWTBotTest extends AbstractSwtBotEditorTest {

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
	public void testAddMappedSuperclass() {
		Utils.sayTestStarted("testAddMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		editorProxy.addMappedSuperclassToDiagram(50, 50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		jpaDiagramEditor.save();
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddMappedSuperclass");
	}

	/**
	 * Remove a mapped superclass from the diagram using the entity's context
	 * button "Delete"
	 */
	@Test
	public void testRemoveEntityViaButton() {
		Utils.sayTestStarted("testRemoveEntityViaButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		editorProxy.deleteJPTViaButton(mappedSuperclass, true);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveEntityViaButton");
	}

	/**
	 * Remove a mapped superclass from the diagram using the entity's context
	 * menu "Delete"
	 */
	@Test
	public void testRemoveEntityViaContextMenu() {
		Utils.sayTestStarted("testRemoveEntityViaContextMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		editorProxy.deleteJPTViaMenu(mappedSuperclass);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveEntityViaContextMenu");
	}

	@Test
	public void testAddElementCollectionAttributeToMappedSuperclass() {
		Utils.sayTestStarted("testAddElementCollectionAttributeToMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								mappedSuperclass));

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		SWTBotGefEditPart attribute = editorProxy.addElementCollectionAttributeToJPT(mappedSuperclass, attributeName);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		
		editorProxy.removeAttributeViaButton(mappedSuperclass, attribute, attributeName, false);

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddElementCollectionAttributeToMappedSuperclass");
	}

	/**
	 * Removes the attribute using the "Delete Attribute" context button.
	 */
	@Test
	public void testAddRemoveAttributeViaContextButton() {
		Utils.sayTestStarted("testRemoveAttributeViaContextButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(mappedSuperclass, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAttributeViaButton(mappedSuperclass, attribute, attributeName, false);

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAttributeViaContextButton");
	}

	/**
	 * Removes the attribute using the "Delete" context menu.
	 */
	@Test
	public void testAddRemoveAttributeViaMenu() {
		Utils.sayTestStarted("testRemoveAttributeViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(mappedSuperclass, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAttributeViaMenu(mappedSuperclass, attribute, attributeName);

		mappedSuperclass.click();
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

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		editorProxy.directEditAttribute(mappedSuperclass, attributeName);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDirectEditingAttribute");
	}

	/**
	 * Adds a new mapped superclass and rename it
	 */
	@Ignore
	@Test
	public void testDirectEditingEntity() {
		Utils.sayTestStarted("testDirectEditingEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);
		String oldMpdSuprclsName = editorProxy.getJPTObjectForGefElement(mappedSuperclass).getSimpleName();
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.moveMouse(100, 70);
		mappedSuperclass.click();
		mappedSuperclass.activateDirectEdit();
		jpaDiagramEditor.directEditType("NewEntityName");
		editorProxy.moveMouse(0, 0);

		SWTBotGefEditPart oldMappedSuperclass = jpaDiagramEditor
				.getEditPart(oldMpdSuprclsName);
		SWTBotGefEditPart newMappedSuperclass = jpaDiagramEditor
				.getEditPart("NewEntityName");
		assertNotNull("The entity must be renamed!", newMappedSuperclass);
		assertNull("The attribute must be renamed!", oldMappedSuperclass);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDirectEditingEntity");
	}

	/**
	 * Test that the source of the mapped superclass is opened, when is double
	 * clicked on it
	 */
	@Ignore
	@Test
	public void testDoubleClickOnEntity() {
		Utils.sayTestStarted("testDoubleClickOnEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		editorProxy.moveMouse(100, 70);
		jpaDiagramEditor.doubleClick(mappedSuperclass);
		editorProxy.moveMouse(0, 0);

		SWTBotEditor activeEditor = workbenchBot.activeEditor();
		assertEquals("The Java editor of the enity did not open!",
				"MpdSuprcls1.java", activeEditor.getTitle());
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

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								mappedSuperclass));

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(
				mappedSuperclass, attributeName, false);
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
		mappedSuperclass.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testChangeAttributeType");
	}

	/**
	 * Rename the mapped superclass using its context menu
	 * "Refactor Entity Class -> Rename..."
	 */
	@Ignore
	@Test
	public void testRenameEntityViaMenu() {
		Utils.sayTestStarted("testRenameEntityViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);
		String oldMappedSuperclassName = editorProxy.getJPTObjectForGefElement(mappedSuperclass).getSimpleName();

		mappedSuperclass.click();
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

		mappedSuperclass = jpaDiagramEditor.getEditPart("NewEntityName");
		assertNotNull("Entity name must be changed!", mappedSuperclass);
		
		assertNull("Entity naem must be changed!",
				jpaDiagramEditor.getEditPart(oldMappedSuperclassName));

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRenameEntityViaMenu");
	}

	/**
	 * Move the mapped superclass class using the entity's context menu
	 * "Refactor Entity Class -> Move..."
	 */
	@Ignore
	@Test
	public void testMoveEntityViaMenu() throws JavaModelException {
		Utils.sayTestStarted("testMoveEntityViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);
		String mappedSuperclassName = editorProxy.getJPTObjectForGefElement(mappedSuperclass).getSimpleName();

		JpaArtifactFactory factory = JpaArtifactFactory.instance();
		String packageName = factory
				.getMappedSuperclassPackageDeclaration(editorProxy
						.getJPTObjectForGefElement(mappedSuperclass));

		mappedSuperclass.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_moveEntityClass);

		SWTBotShell moveEntityDialog = editorProxy.getMoveEntityDialog();
		moveEntityDialog.bot().tree().getTreeItem(TEST_PROJECT).select()
				.expandNode("src").expandNode("org").select().click();
		assertTrue(editorProxy.getOkButton(moveEntityDialog).isEnabled());
		editorProxy.getOkButton(moveEntityDialog).click();
		editorProxy.waitASecond();

		mappedSuperclass = jpaDiagramEditor.getEditPart(mappedSuperclassName);
		String newEntityPackage = factory
				.getMappedSuperclassPackageDeclaration(editorProxy
						.getJPTObjectForGefElement(mappedSuperclass));
		assertFalse("Entity must be moved!",
				packageName.equals(newEntityPackage));
		assertTrue("Entity must be changed!", newEntityPackage.equals("org"));

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testMoveEntityViaMenu");
	}

	/**
	 * Collapse/expand mapped superclass using its context buttons
	 */
	@Test
	public void testCollapseExapandEntityViaContextButton() {
		Utils.sayTestStarted("testCollapseExapandEntityViaContextButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		editorProxy.addAttributeToJPT(mappedSuperclass, attributeName, false);

		editorProxy.collapseExpandJPTViaButton(mappedSuperclass);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExapandEntityViaContextButton");
	}

	/**
	 * Collapse/expand mapped superclass using its context menus
	 */
	@Test
	public void testCollapseExapandEntityViaMenu() {
		Utils.sayTestStarted("testCollapseExapandEntityViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		editorProxy.addAttributeToJPT(mappedSuperclass, attributeName, false);

		editorProxy.collapseExpandJPTViaMenu(mappedSuperclass);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExapandEntityViaMenu");
	}

	/**
	 * Add a new attribute without saving the mapped superclass and call the
	 * "Discard Changes" context menu. Assert that the newly added attribute is
	 * removed and the mapped superclass does not contain unsaved changes.
	 */
	@Ignore
	@Test
	public void testDiscardChanges() {
		Utils.sayTestStarted("testDiscardChanges");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		editorProxy.discardChanges(mappedSuperclass, attributeName);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDiscardChanges");
	}

	/**
	 * Add a new attribute without saving the mapped superclass and call the
	 * mapped superclass'es context menu "Remove All Entities from Diagram ->
	 * ... and Discard
	 * Changes" context menu. Assert that the diagram is empty. Call "Show All
	 * Entities" context menu and assert that the newly added attribute is
	 * removed and the mapped superclass does not contain unsaved changes.
	 */
	@Ignore
	@Test
	public void testRemoveAndDiscardChangesViaMenu() {
		Utils.sayTestStarted("testRemoveAndDiscardChangesViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		editorProxy.removeAndDiscardChangesViaMenu(mappedSuperclass,
				attributeName);

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAndDiscardChangesViaMenu");
	}

	/**
	 * Add a new attribute without saving the mapped superclass and call the
	 * mapped superclass'es context menu "Remove All Entities from Diagram ->
	 * ... and Save
	 * Changes" context menu. Assert that the diagram is empty. Call "Show All
	 * Entities" context menu and assert that the newly added attribute is added
	 * and the mapped superclass does not contain unsaved changes.
	 */
	@Test
	public void testRemoveAndSaveChangesViaMenu() {
		Utils.sayTestStarted("testRemoveAndSaveChangesViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		editorProxy.removeAndSaveChangesViaMenu(mappedSuperclass, attributeName);

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAndSaveChangesViaMenu");
	}

	/**
	 * Add a new attribute to the mapped superclass. From the mapped
	 * superclass'es context menu select "Save". Assert that the mapped
	 * superclass does not contain any unsaved changes, but the diagram editor
	 * is still dirty.
	 */
	@Test
	public void testSaveOnlyEntity() {
		Utils.sayTestStarted("testSaveOnlyEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);
		String attributeName = editorProxy.getUniqueAttrName(mappedSuperclass);
		editorProxy.saveOnlyJPT(mappedSuperclass, attributeName);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testSaveOnlyEntity");
	}

	/**
	 * Creates a new Inherited entity by mapped superclass. Assert that the
	 * inherited entity contains a primary key.
	 */
	@Test
	public void testInheritedEntityByMappedSuperclass() {
		Utils.sayTestStarted("testInheritedEntityByMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);
		
		editorProxy.createInheritedEntity(mappedSuperclass, jpaProject,
				MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY,
				true, false);

		editorProxy.deleteDiagramElements(false);

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testInheritedEntityByMappedSuperclass");
	}

	/**
	 * Create one entity and one mapped superclass in the diagram. From the
	 * "Inheritance" section of the palette select "Inherit Persistent Type".
	 * Click on the entity and then click on the mapped superclass. Assert that
	 * an is-a relation is created.
	 */
	@Test
	public void testIsARelationBetweenExistingEntityAndMappedSuperclass() {
		Utils.sayTestStarted("testIsARelationBetweenExistingEntityAndMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart superclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);
		String superclassName = editorProxy.getJPTObjectForGefElement(
				superclass).getSimpleName();

		SWTBotGefEditPart subclass = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String subclassName = editorProxy.getJPTObjectForGefElement(subclass)
				.getSimpleName();

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureName);
		jpaDiagramEditor.click(superclass);
		jpaDiagramEditor.click(subclass);

		editorProxy.waitASecond();

		editorProxy.testCreateAndDeleteIsARelation(superclass, subclassName,
				MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY,
				true, superclassName, subclass, false);

		editorProxy.deleteDiagramElements(false);

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testIsARelationBetweenExistingEntityAndMappedSuperclass");
	}

	/**
	 * Create one entity and one mapped superclass in the diagram. From the
	 * "Inheritance" section of the palette select "Inherit Persistent Type".
	 * Click on the entity and then click on the mapped superclass. Assert that
	 * an is-a relation is created. Add new entity in the diagram. Try to create
	 * new is-a relation between the the second entity and the first one.
	 * Assert that no connection is created. Try to create new is-a relation between
	 * the first entity and the second one. Assert that a new connecton is created
	 * in the diagram.
	 */
	@Test
	public void testNoIsARelationIsCreated() {
		Utils.sayTestStarted("testNoIsARelationIsCreated");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart superclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, jpaProject);
		String superclassName = editorProxy.getJPTObjectForGefElement(
				superclass).getSimpleName();

		SWTBotGefEditPart subclass = editorProxy.addEntityToDiagram(50, 300,
				jpaProject);
		String subclassName = editorProxy.getJPTObjectForGefElement(subclass)
				.getSimpleName();

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureName);
		jpaDiagramEditor.click(superclass);
		jpaDiagramEditor.click(subclass);

		editorProxy.waitASecond();

		editorProxy.testIsARelationProperties(superclass, subclassName,
				MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY,
				true, superclassName, subclass, false);

		SWTBotGefEditPart secondSuperclass = editorProxy.addEntityToDiagram(
				300, 50, jpaProject);

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureName);
		jpaDiagramEditor.click(secondSuperclass);
		jpaDiagramEditor.click(subclass);

		editorProxy.waitASecond();

		assertTrue("There is no connection created.", secondSuperclass
				.targetConnections().isEmpty());
		assertTrue("There is no connection created.", secondSuperclass
				.sourceConnections().isEmpty());

		editorProxy.deleteDiagramElements(false);

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testNoIsARelationIsCreated");
	}

}
