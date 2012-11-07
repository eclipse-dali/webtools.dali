package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.ui.editor;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.ui.internal.parts.DiagramEditPart;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.selection.JpaEditorManager;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.JPACreateFactory;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
@SuppressWarnings("restriction")
public class MappedSuperclassesInDiagramSWTBotTest extends SWTBotGefTestCase {

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
	public void testAddMappedSuperclass() {
		Utils.sayTestStarted("testAddMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		editorProxy.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		jpaDiagramEditor.save();
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.deleteJPTViaButton(mappedSuperclass);

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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.deleteJPTViaMenu(mappedSuperclass);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveEntityViaContextMenu");
	}

	/**
	 * Adds a new attribute to the mapped superclass using the entity's context
	 * button "Create Attribute"
	 */
	@Test
	public void testAddAttribute() {
		Utils.sayTestStarted("testAddAttribute");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								mappedSuperclass));

		editorProxy.addAttributeToJPT(mappedSuperclass, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddAttribute");
	}

	@Test
	public void testAddElementCollectionAttributeToMappedSuperclass() {
		Utils.sayTestStarted("testAddElementCollectionAttributeToMappedSuperclass");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addMappedSuperclassToDiagram(50,
				50, "MpdSuprcls1");

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								entity));

		editorProxy.addElementCollectionAttributeToJPT(entity, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		entity.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddElementCollectionAttributeToMappedSuperclass");
	}

	/**
	 * Removes the attribute using the "Delete Attribute" context button.
	 */
	@Test
	public void testRemoveAttributeViaContextButton() {
		Utils.sayTestStarted("testRemoveAttributeViaContextButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.removeAttributeViaButton(mappedSuperclass, "attribute1");

		mappedSuperclass.click();
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

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.removeAttributeViaMenu(mappedSuperclass, "attribute1");

		mappedSuperclass.click();
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

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.directEditAttribute(mappedSuperclass, "attribute1");

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.moveMouse(100, 70);
		mappedSuperclass.click();
		mappedSuperclass.activateDirectEdit();
		jpaDiagramEditor.directEditType("NewEntityName");
		editorProxy.moveMouse(0, 0);

		SWTBotGefEditPart oldMappedSuperclass = jpaDiagramEditor
				.getEditPart("MpdSuprcls1");
		SWTBotGefEditPart newMappedSuperclass = jpaDiagramEditor
				.getEditPart("NewEntityName");
		assertNotNull("The entity must be renamed!", newMappedSuperclass);
		assertNull("The attribute must be renamed!", oldMappedSuperclass);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		editorProxy.moveMouse(100, 70);
		jpaDiagramEditor.doubleClick(mappedSuperclass);
		editorProxy.moveMouse(0, 0);

		SWTBotEditor activeEditor = workbenchBot.activeEditor();
		assertEquals("The Java editor of the enity did not open!",
				"MpdSuprcls1.java", activeEditor.getTitle());
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

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								mappedSuperclass));

		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(
				mappedSuperclass, "attribute1");
		assertNotNull("The attribute must not be renamed!", attribute);

		final IFeatureProvider fp = ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();

		String currentAttributeType = editorProxy.getAttributeType(
				"attribute1", fp);
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
		currentAttributeType = editorProxy.getAttributeType("attribute1", fp);
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
				.getAttributeType("attribute1", fp);
		assertFalse("The attribute type must be changed!",
				("java.lang.String").equals(newAttributeType));
		assertEquals("The attribute type must be changed!", "int",
				newAttributeType);

		assertTrue("Editor must be dirty!", jpaDiagramEditor.isDirty());
		mappedSuperclass.click();
		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

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
				jpaDiagramEditor.getEditPart("MpdSuprcls1"));

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
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

		mappedSuperclass = jpaDiagramEditor.getEditPart("MpdSuprcls1");
		String newEntityPackage = factory
				.getMappedSuperclassPackageDeclaration(editorProxy
						.getJPTObjectForGefElement(mappedSuperclass));
		assertFalse("Entity must be moved!",
				packageName.equals(newEntityPackage));
		assertTrue("Entity must be changed!", newEntityPackage.equals("org"));

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.addAttributeToJPT(mappedSuperclass, "attribute1");

		editorProxy.collapseExpandJPTViaButton(mappedSuperclass);

		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.addAttributeToJPT(mappedSuperclass, "attribute1");

		editorProxy.collapseExpandJPTViaMenu(mappedSuperclass);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExapandEntityViaMenu");
	}

	/**
	 * Add a new attribute without saving the mapped superclass and call the
	 * "Discard Changes" context menu. Assert that the newly added attribute is
	 * removed and the mapped superclass does not contain unsaved changes.
	 */
	@Test
	public void testDiscardChanges() {
		Utils.sayTestStarted("testDiscardChanges");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(200, 50, "MpdSuprcls1");

		editorProxy.discardChanges(mappedSuperclass, "attribute1");

		editorProxy.deleteDiagramElements();
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
	@Test
	public void testRemoveAndDiscardChangesViaMenu() {
		Utils.sayTestStarted("testRemoveAndDiscardChangesViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor
				.mainEditPart().children().isEmpty());

		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.removeAndDiscardChangesViaMenu(mappedSuperclass,
				"attribute1");

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.removeAndSaveChangesViaMenu(mappedSuperclass, "attribute1");

		mappedSuperclass.click();
		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		editorProxy.saveOnlyJPT(mappedSuperclass, "attribute1");

		editorProxy.deleteDiagramElements();
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy.createInheritedEntity(mappedSuperclass, "Entity1",
				JptUiDetailsMessages.MappedSuperclassUiProvider_linkLabel,
				true, false);

		editorProxy.deleteDiagramElements();

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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		String superclassName = editorProxy.getJPTObjectForGefElement(
				superclass).getSimpleName();

		SWTBotGefEditPart subclass = editorProxy.addEntityToDiagram(50, 300,
				"Entity1");
		String subclassName = editorProxy.getJPTObjectForGefElement(subclass)
				.getSimpleName();

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureName);
		jpaDiagramEditor.click(superclass);
		jpaDiagramEditor.click(subclass);

		editorProxy.waitASecond();

		editorProxy.testCreateAndDeleteIsARelation(superclass, subclassName,
				JptUiDetailsMessages.MappedSuperclassUiProvider_linkLabel,
				true, superclassName, subclass, false);

		editorProxy.deleteDiagramElements();

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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		String superclassName = editorProxy.getJPTObjectForGefElement(
				superclass).getSimpleName();

		SWTBotGefEditPart subclass = editorProxy.addEntityToDiagram(50, 300,
				"Entity1");
		String subclassName = editorProxy.getJPTObjectForGefElement(subclass)
				.getSimpleName();

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureName);
		jpaDiagramEditor.click(superclass);
		jpaDiagramEditor.click(subclass);

		editorProxy.waitASecond();

		editorProxy.testIsARelationProperties(superclass, subclassName,
				JptUiDetailsMessages.MappedSuperclassUiProvider_linkLabel,
				true, superclassName, subclass, false);

		SWTBotGefEditPart secondSuperclass = editorProxy.addEntityToDiagram(
				300, 50, "Entity2");

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateIsARelationFeature_CreateIsARelationFeatureName);
		jpaDiagramEditor.click(secondSuperclass);
		jpaDiagramEditor.click(subclass);

		editorProxy.waitASecond();

		assertTrue("There is no connection created.", secondSuperclass
				.targetConnections().isEmpty());
		assertTrue("There is no connection created.", secondSuperclass
				.sourceConnections().isEmpty());

		editorProxy.deleteDiagramElements();

		jpaDiagramEditor.save();

		Utils.sayTestFinished("testNoIsARelationIsCreated");
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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");

		// create One-to-One unidirectional relation from the mapped superclass
		// to entity

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						mappedSuperclass,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");

		// create One-to-many unidirectional relation from the mapped superclass
		// to entity1

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						mappedSuperclass,
						entity,
						IRelation.RelType.ONE_TO_MANY,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");

		// create Many-to-One unidirectional relation from the mapped superclass
		// to entity1

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						mappedSuperclass,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

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
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");

		// create Many-to-Many unidirectional relation from the mapped
		// superclass
		// to entity1

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						mappedSuperclass,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();

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

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						0, entity, mappedSuperclass);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						1, entity, mappedSuperclass);

		editorProxy.deleteDiagramElements();
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

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						1, mappedSuperclass, entity);

		editorProxy.deleteDiagramElements();
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

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, entity, mappedSuperclass);

		editorProxy.deleteDiagramElements();
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

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						0, entity, mappedSuperclass);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						1, entity, mappedSuperclass);

		editorProxy.deleteDiagramElements();
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

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						1, mappedSuperclass, entity);

		editorProxy.deleteDiagramElements();
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

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						0, entity, mappedSuperclass);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, entity, mappedSuperclass);

		editorProxy.deleteDiagramElements();
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

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 200,
				"Entity1");
		SWTBotGefEditPart mappedSuperclass = editorProxy
				.addMappedSuperclassToDiagram(50, 50, "MpdSuprcls1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, mappedSuperclass, entity);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.saveAndClose();

		Utils.sayTestFinished("testManyToManyBiDirRelationFromMappedSuperclassToEntity");
	}

	@After
	public void tearDown() throws Exception {
		editorProxy.deleteDiagramElements();
		Utils.printFormatted(">>>>>>>>>>>> elements are deleted from the diagram.");

		ListIterator<PersistenceUnit> lit = jpaProject.getRootContextNode()
				.getPersistenceXml().getRoot().getPersistenceUnits().iterator();
		PersistenceUnit pu = lit.next();
		Iterator<PersistentType> persistentTypesIterator = (Iterator<PersistentType>) pu
				.getPersistentTypes().iterator();
		while (persistentTypesIterator.hasNext()) {
			Utils.printFormatted(">>>>>>>>>>>>>> persistent type resource must be deleted.");

			PersistentType type = persistentTypesIterator.next();
			type.getResource().delete(true, new NullProgressMonitor());
		}
	}
}
