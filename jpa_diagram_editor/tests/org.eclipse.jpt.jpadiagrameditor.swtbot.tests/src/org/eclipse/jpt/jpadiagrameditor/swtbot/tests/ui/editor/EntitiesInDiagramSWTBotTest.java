package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.ui.editor;

import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.internal.parts.DiagramEditPart;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.JPACreateFactory;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
@SuppressWarnings("restriction")
public class EntitiesInDiagramSWTBotTest extends SWTBotGefTestCase {

	protected static String TEST_PROJECT;
	protected static JPACreateFactory factory = JPACreateFactory.instance();
	protected static JpaProject jpaProject;

	protected static SWTGefBot bot = new SWTGefBot();
	protected static SWTWorkbenchBot workbenchBot = new SWTWorkbenchBot();
	protected static EditorProxy editorProxy = new EditorProxy(workbenchBot,
			bot);

	protected static SWTBotGefEditor jpaDiagramEditor;

	@BeforeClass
	public static void beforeClass() throws Exception {

		SWTBotPreferences.TIMEOUT = 1000;
		try {
			bot.viewByTitle("Welcome").close();
		} catch (Exception e) {
			// ignore
		} finally {
			SWTBotPreferences.TIMEOUT = 5000;
		}
		workbenchBot.perspectiveByLabel("JPA").activate();

		TEST_PROJECT = "Test_" + System.currentTimeMillis();

		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPA20Project(TEST_PROJECT);
		assertNotNull(jpaProject);
		
		workbenchBot.closeAllEditors();

		jpaDiagramEditor = editorProxy.openDiagramOnJPAProjectNode(
				TEST_PROJECT, true);
		editorProxy.setJpaDiagramEditor(jpaDiagramEditor);

		Thread.sleep(2000);
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

		editorProxy.deleteDiagramElements();
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

		editorProxy.deleteJPTViaButton(entity);

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

	/**
	 * Adds a new attribute to the entity using the entity's context button
	 * "Create Attribute"
	 */
	@Test
	public void testAddAttribute() {
		Utils.sayTestStarted("testAddAttribute");

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
		editorProxy.addAttributeToJPT(entity, attributeName);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		entity.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddAttribute");
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
		editorProxy.addElementCollectionAttributeToJPT(entity, attributeName);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		entity.click();
		editorProxy.deleteDiagramElements();
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
		editorProxy.removeAttributeViaButton(entity, attributeName);

		entity.click();
		editorProxy.deleteDiagramElements();
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
		editorProxy.removeAttributeViaMenu(entity, attributeName);

		entity.click();
		editorProxy.deleteDiagramElements();
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

		editorProxy.deleteDiagramElements();
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

		editorProxy.deleteDiagramElements();
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

		editorProxy.deleteDiagramElements();
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
				attributeName);
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
		editorProxy.deleteDiagramElements();
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
		editorProxy.deleteDiagramElements();
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
		editorProxy.deleteDiagramElements();
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

		editorProxy.deleteDiagramElements();
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

		editorProxy.deleteDiagramElements();
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

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExapandAllEntitiesViaMenu");
	}

	/**
	 * Add a new attribute without saving the entity and call the
	 * "Discard Changes" context menu. Assert that the newly added attribute is
	 * removed and the entity does not contain unsaved changes.
	 */
	@Test
	public void testDiscardChanges() {
		Utils.sayTestStarted("testDiscardChanges");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		String attributeName = editorProxy.getUniqueAttrName(entity);
		editorProxy.discardChanges(entity, attributeName);

		editorProxy.deleteDiagramElements();
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
		editorProxy.deleteDiagramElements();
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
		editorProxy.deleteDiagramElements();
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

		editorProxy.deleteDiagramElements();
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

		factory.createEntity(jpaProject, "com.sap.test.Customer");
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

		editorProxy.deleteDiagramElements();
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
		editorProxy.deleteDiagramElements();
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
		editorProxy.deleteDiagramElements();
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
		editorProxy.deleteDiagramElements();
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
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testChangeAttributeMappingTypeViaJPADetailsView");
	}

	/**
	 * Creates "One to One" unidirectional relationship (from entity1 to
	 * entity2). Assert that the relation attributes exists. Delete the
	 * relationship and assert that the attributes do not exists anymore. Check
	 * that if the owner attribute will be deleted, the relationship will
	 * disappear.
	 * 
	 */
	@Test
	public void testOneToOneUniDirRelationship() {
		Utils.sayTestStarted("testOneToOneUniDirRelationship");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);

		// create One-to-One unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		// create One-to-One unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		// create One-to-One unidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						entity1,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);

		// create One-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		// create One-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		// create One-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		// create One-to-One bidirectional self relation from entity1 to entity1
		editorProxy
				.testSelfBiDirRel(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						entity1,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);

		// create One-to-Many unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_MANY,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		// create One-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.ONE_TO_MANY,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		// create One-to-Many unidirectional self relation from entity1 to
		// entity1

		editorProxy
				.testSelfUniDirRelation(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						entity1,
						IRelation.RelType.ONE_TO_MANY,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);

		// create Many-to-One unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		// create Many-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		// create Many-to-One unidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						entity1,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);

		// create Many-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelWithTwoMappingTypes(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		// create Many-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelWithTwoMappingsWithoutInverseAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		// create Many-to-One bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelWithTwoMappingsWithoutOwnerAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		// create Many-to-Many bidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfBiDirRelWithTwoMappings(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						entity1,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);

		// create Many-to-Many unidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		// create Many-to-Many bidirectional relation from entity1 to entity2
		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		// create Many-to-Many unidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfUniDirRelation(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						entity1,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);

		// create Many-to-Many bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		// create Many-to-Many bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		// create Many-to-Many bidirectional relation from entity1 to entity2
		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						entity1,
						entity2,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		// create Many-to-Many bidirectional self relation from entity1 to
		// entity1
		editorProxy
				.testSelfBiDirRel(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						entity1,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testSelfManyToManyBiDirRelationship");
	}

	/**
	 * Creates a new Inherited entity by entity. Assert that the inherited
	 * entity does not contain a primary key.
	 */
	@Test
	public void testInheritedEntityByEntity() {
		Utils.sayTestStarted("testInheritedEntityByEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);

		editorProxy.createInheritedEntity(entity1, jpaProject,
				JptUiDetailsMessages.EntityUiProvider_linkLabel, false, false);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

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

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureName);
		jpaDiagramEditor.click(superclass);
		jpaDiagramEditor.click(subclass);
		
		editorProxy.waitASecond();

		editorProxy.testCreateAndDeleteIsARelation(superclass, subclassName,
				JptUiDetailsMessages.EntityUiProvider_linkLabel, false,
				superclassName, subclass, true);

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testIsARelationBetweenExistingEntities");
	}

	/**
	 * Test that the JPA Diagram editor is opened when the context menu
	 * "JPA Tools -> Open Diagram" of the project is called.
	 */
	@Test
	public void testOpenDiagramOnProjectLevel() {
		Utils.sayTestStarted("testOpenDiagramOnProjectLevel");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		workbenchBot.closeAllEditors();
		// open JPA diagram editor on project level: JPA Tools -> Open Diagram
		SWTBotGefEditor diagramEditor = editorProxy
				.openDiagramOnJPAProjectNode(TEST_PROJECT, true);
		diagramEditor.close();

		Utils.sayTestFinished("testOpenDiagramOnProjectLevel");
	}

	/**
	 * Test that the JPA Diagram editor is opened when the context menu
	 * "Open Diagram" of the JPA content node is called.
	 */
	@Test
	public void testOpenDiagramOnJPAContentNodeLevel() {
		Utils.sayTestStarted("testOpenDiagramOnJPAContentNodeLevel");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		workbenchBot.closeAllEditors();
		// open JPA diagram editor on JPA content level: Open Diagram
		SWTBotGefEditor diagramEditor = editorProxy
				.openDiagramOnJPAContentNode(TEST_PROJECT);
		diagramEditor.close();

		Utils.sayTestFinished("testOpenDiagramOnJPAContentNodeLevel");
	}

	@After
	public void tearDown() throws Exception {
		editorProxy.deleteResources(jpaProject);
	}
}
