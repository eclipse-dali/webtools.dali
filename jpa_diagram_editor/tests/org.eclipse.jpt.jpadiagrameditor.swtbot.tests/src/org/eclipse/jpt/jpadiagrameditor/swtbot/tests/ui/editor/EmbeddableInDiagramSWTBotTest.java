package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.ui.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.ui.internal.parts.DiagramEditPart;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.JptUiDetailsMessages2_0;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.JPACreateFactory;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation.HasReferenceType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefConnectionEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
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
public class EmbeddableInDiagramSWTBotTest extends SWTBotGefTestCase {

	protected static String TEST_PROJECT;
	protected static JPACreateFactory factory = JPACreateFactory.instance();
	protected static JpaProject jpaProject;

	// protected static JpaProject jpaProject10;

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
		workbenchBot.viewByTitle("JPA Structure").close();

		TEST_PROJECT = "Test_" + System.currentTimeMillis();

		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPA20Project(TEST_PROJECT);
		assertNotNull(jpaProject);

		jpaDiagramEditor = editorProxy
				.openDiagramOnJPAContentNode(TEST_PROJECT, true);
		editorProxy.setJpaDiagramEditor(jpaDiagramEditor);

		Thread.sleep(2000);
	}

	/**
	 * Add embeddable to diagram and check that it does not contain a
	 * "Primary Key", "Relation Attributes" and "Other Attributes" sections.
	 */
	@Test
	public void testAddEmbeddable() {
		Utils.sayTestStarted("testAddEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		editorProxy.addEmbeddableToDiagram(50, 50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		jpaDiagramEditor.save();
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddEmbeddable");
	}

	/**
	 * Remove an embeddable from the diagram using the embeddable's context
	 * button "Delete"
	 */
	@Test
	public void testRemoveEmnbeddableViaButton() {
		Utils.sayTestStarted("testRemoveEmnbeddableViaButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteJPTViaButton(embeddable);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveEmnbeddableViaButton");
	}

	/**
	 * Remove an embeddable from the diagram using the embeddable's context menu
	 * "Delete"
	 */
	@Test
	public void testRemoveEmbeddableViaContextMenu() {
		Utils.sayTestStarted("testRemoveEmbeddableViaContextMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteJPTViaMenu(embeddable);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveEmbeddableViaContextMenu");
	}

	/**
	 * Adds a new attribute to the embeddable using the entity's context button
	 * "Create Attribute"
	 */
	@Test
	public void testAddAttributeToEmbeddablle() {
		Utils.sayTestStarted("testAddAttributeToEmbeddablle");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								embeddable));

		editorProxy.addAttributeToJPT(embeddable, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		embeddable.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddAttributeToEmbeddablle");
	}
	
	@Test
	public void testAddElementCollectionAttributeToEmbeddable(){
		Utils.sayTestStarted("testAddElementCollectionAttributeToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEmbeddableToDiagram(50, 50, "Embeddable1");

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy.isSectionVisible(JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes, entity));

		editorProxy.addElementCollectionAttributeToJPT(entity, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		entity.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testAddElementCollectionAttributeToEmbeddable");
	}
	
	
	/**
	 * Add two embeddables and one entity in the diagram. Add an attribute of collection type to
	 * the first embeddable. Check that it is not possible to embed a collection of the first
	 * embeddable neither into the second embeddable, nor into the entity, but it is possible to embed
	 * a collection of the second embeddable to the first embeddable.
	 */
	
	@Test
	public void testEmbedCollectionOfObjectsWithCollectionAttribute() {
		Utils.sayTestStarted("testEmbedCollectionOfObjectsWithCollectionAttribute");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable1 = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, "Embeddable2");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(200, 50, "Entity1");

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy.isSectionVisible(JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes, embeddable1));

		editorProxy.addElementCollectionAttributeToJPT(embeddable1, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		
		editorProxy.testNoEmbeddedConnectionIsCreated(
				JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, 0, entity, embeddable1, false);
		
		editorProxy.testNoEmbeddedConnectionIsCreated(
				JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, 0, embeddable2, embeddable1, false);

		_testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable1,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				3);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedCollectionOfObjectsWithCollectionAttribute");
	}

	/**
	 * Removes the attribute using the "Delete Attribute" context button.
	 */
	@Test
	public void testRemoveAttributeFromEmbeddableViaContextButton() {
		Utils.sayTestStarted("testRemoveAttributeFromEmbeddableViaContextButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAttributeViaButton(embeddable, "attribute1");

		embeddable.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAttributeFromEmbeddableViaContextButton");
	}

	/**
	 * Removes the attribute using the "Delete" context menu.
	 */
	@Test
	public void testRemoveAttributeFromEmbeddableViaMenu() {
		Utils.sayTestStarted("testRemoveAttributeFromEmbeddableViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAttributeViaMenu(embeddable, "attribute1");

		embeddable.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAttributeFromEmbeddableViaMenu");
	}

	/**
	 * Adds a new attribute and rename it
	 */
	@Test
	public void testDirectEditingAttributeInEmbeddable() {
		Utils.sayTestStarted("testDirectEditingAttributeInEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.directEditAttribute(embeddable, "attribute1");

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDirectEditingAttributeInEmbeddable");
	}

	/**
	 * Adds a new embeddable and rename it
	 */
	@Ignore
	@Test
	public void testDirectEditingEmbeddable() {
		Utils.sayTestStarted("testDirectEditingEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.moveMouse(100, 70);
		embeddable.click();
		embeddable.activateDirectEdit();
		jpaDiagramEditor.directEditType("NewEntityName");
		editorProxy.moveMouse(0, 0);

		SWTBotGefEditPart oldEmbeddable = jpaDiagramEditor
				.getEditPart("Embeddable1");
		SWTBotGefEditPart newEmbeddable = jpaDiagramEditor
				.getEditPart("NewEntityName");
		assertNotNull("The entity must be renamed!", newEmbeddable);
		assertNull("The attribute must be renamed!", oldEmbeddable);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDirectEditingEmbeddable");
	}

	/**
	 * Test that the source of the embeddable is opened, when is double clicked
	 * on it
	 */
	@Ignore
	@Test
	public void testDoubleClickOnEmbeddable() {
		Utils.sayTestStarted("testDoubleClickOnEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.moveMouse(100, 70);
		jpaDiagramEditor.doubleClick(embeddable);
		editorProxy.moveMouse(0, 0);

		SWTBotEditor activeEditor = workbenchBot.activeEditor();
		assertEquals("The Java editor of the enity did not open!",
				"Embeddable1.java", activeEditor.getTitle());
		activeEditor.close();

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDoubleClickOnEmbeddable");
	}

	/**
	 * Change the attribute type.
	 */
	@Test
	public void testChangeAttributeTypeInEmbeddable() {
		Utils.sayTestStarted("testChangeAttributeTypeInEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								embeddable));

		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(embeddable,
				"attribute1");
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
		embeddable.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testChangeAttributeTypeInEmbeddable");
	}

	/**
	 * Rename the embeddable using its context menu
	 * "Refactor Entity Class -> Rename..."
	 */
	@Ignore
	@Test
	public void testRenameEmbeddableViaMenu() {
		Utils.sayTestStarted("testRenameEmbeddableViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		embeddable.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_renameEntityClass);

		SWTBotShell renameEntityDialog = editorProxy.getRenameEntityDialog();
		SWTBotText embeddableName = renameEntityDialog.bot().textWithLabel(
				"New name:");

		// test invalid entity name
		embeddableName.setText("");
		assertFalse(editorProxy.getFinishButton(renameEntityDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(renameEntityDialog).isEnabled());

		embeddableName.setText("NewEntityName");
		assertTrue(editorProxy.getFinishButton(renameEntityDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(renameEntityDialog).isEnabled());

		editorProxy.getFinishButton(renameEntityDialog).click();
		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, "NewEntityName"),
				10000);

		embeddable = jpaDiagramEditor.getEditPart("NewEntityName");
		assertNotNull("Entity name must be changed!", embeddable);
		assertNull("Entity naem must be changed!",
				jpaDiagramEditor.getEditPart("Embeddable1"));

		embeddable.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRenameEmbeddableViaMenu");
	}

	/**
	 * Move the embeddable class using the embeddable's context menu
	 * "Refactor Entity Class -> Move..."
	 */
	@Ignore
	@Test
	public void testMoveEmbeddableViaMenu() throws JavaModelException {
		Utils.sayTestStarted("testMoveEmbeddableViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		JpaArtifactFactory factory = JpaArtifactFactory.instance();

		String packageName = factory
				.getMappedSuperclassPackageDeclaration(editorProxy
						.getJPTObjectForGefElement(embeddable));

		embeddable.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_moveEntityClass);

		SWTBotShell moveEntityDialog = editorProxy.getMoveEntityDialog();
		moveEntityDialog.bot().tree().getTreeItem(TEST_PROJECT).select()
				.expandNode("src").expandNode("org").select().click();
		assertTrue(editorProxy.getOkButton(moveEntityDialog).isEnabled());
		editorProxy.getOkButton(moveEntityDialog).click();
		editorProxy.waitASecond();

		embeddable = jpaDiagramEditor.getEditPart("Embeddable1");
		String newEntityPackage = factory
				.getMappedSuperclassPackageDeclaration(editorProxy
						.getJPTObjectForGefElement(embeddable));
		assertFalse("Entity must be moved!",
				packageName.equals(newEntityPackage));
		assertTrue("Entity must be changed!", newEntityPackage.equals("org"));

		embeddable.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testMoveEmbeddableViaMenu");
	}

	/**
	 * Collapse/expand embeddable using its context buttons
	 */
	@Test
	public void testCollapseExapandEmbeddableViaContextButton() {
		Utils.sayTestStarted("testCollapseExapandEmbeddableViaContextButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.addAttributeToJPT(embeddable, "attribute1");

		editorProxy.collapseExpandJPTViaButton(embeddable);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExapandEmbeddableViaContextButton");
	}

	/**
	 * Collapse/expand embeddable using its context menus
	 */
	@Test
	public void testCollapseExapandEmbeddableViaMenu() {
		Utils.sayTestStarted("testCollapseExapandEmbeddableViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.addAttributeToJPT(embeddable, "attribute1");

		editorProxy.collapseExpandJPTViaMenu(embeddable);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testCollapseExapandEmbeddableViaMenu");
	}

	/**
	 * Add a new attribute without saving the embeddable and call the
	 * "Discard Changes" context menu. Assert that the newly added attribute is
	 * removed and the embeddable does not contain unsaved changes.
	 */
	@Ignore
	@Test
	public void testDiscardChangesFromEmbeddable() {
		Utils.sayTestStarted("testDiscardChangesFromEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.discardChanges(embeddable, "attribute1");

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testDiscardChangesFromEmbeddable");
	}

	/**
	 * Add a new attribute without saving the embeddable and call the
	 * embeddable's context menu "Remove All Entities from Diagram -> ... and
	 * Discard
	 * Changes" context menu. Assert that the diagram is empty. Call "Show All
	 * Entities" context menu and assert that the newly added attribute is
	 * removed and the embeddable does not contain unsaved changes.
	 */
	@Test
	public void testRemoveAndDiscardChangesFromEmbeddableViaMenu() {
		Utils.sayTestStarted("testRemoveAndDiscardChangesFromEmbeddableViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAndDiscardChangesViaMenu(embeddable, "attribute1");

		embeddable.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAndDiscardChangesFromEmbeddableViaMenu");
	}

	/**
	 * Add a new attribute without saving the embeddable and call the
	 * embeddable's context menu "Remove All Entities from Diagram -> ... and
	 * Save Changes" context menu. Assert that the diagram is empty. Call "Show
	 * All Entities" context menu and assert that the newly added attribute is
	 * added and the embeddable does not contain unsaved changes.
	 */
	@Test
	public void testRemoveAndSaveChangesToEmbeddableViaMenu() {
		Utils.sayTestStarted("testRemoveAndSaveChangesToEmbeddableViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAndSaveChangesViaMenu(embeddable, "attribute1");

		embeddable.click();
		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAndSaveChangesToEmbeddableViaMenu");
	}

	/**
	 * Add a new attribute to the embeddable. From the embeddable's context menu
	 * select "Save". Assert that the embeddable does not contain any unsaved
	 * changes, but the diagram editor is still dirty.
	 */
	@Test
	public void testSaveOnlyEmbeddable() {
		Utils.sayTestStarted("testSaveOnlyEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.saveOnlyJPT(embeddable, "attribute1");

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testSaveOnlyEmbeddable");
	}

	/**
	 * Add one entity and one embeddable in the diagram. Embed a single instance
	 * of the embeddable into the entity and checks that the connection exists
	 * and a new embedded attribute is added to the entity.
	 */
	@Test
	public void testEmbedSingleObjectInEntity() {
		Utils.sayTestStarted("testEmbedSingleObjectInEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				200, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		_testEmbeddedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, entity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 2);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedSingleObjectInEntity");
	}

	/**
	 * Add one entity and one embeddable in the diagram. Embed a collection of
	 * the embeddable into the entity and checks that the connection exists and
	 * a new embedded attribute is added to the entity.
	 */
	@Ignore
	@Test
	public void testEmbedCollectionOfObjectsInEntity() {
		Utils.sayTestStarted("testEmbedCollectionOfObjectsInEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				200, "Embeddable1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		_testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, entity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				2);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedCollectionOfObjectsInEntity");
	}

	/**
	 * Add two embeddables in the diagram. Embed a single instance of the first
	 * embeddable into the second embeddable and checks that the connection
	 * exists and a new embedded attribute is added to the second embeddable.
	 */
	@Test
	public void testEmbedSingleObjectInEmbeddable() {
		Utils.sayTestStarted("testEmbedSingleObjectInEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable1 = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, "Embeddable2");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		_testEmbeddedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddable1,
				embeddable2, HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 2);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedSingleObjectInEmbeddable");
	}

	/**
	 * Add two embeddables in the diagram. Embed a collection of the first
	 * embeddable into the second embeddable and checks that the connection
	 * exists and a new embedded attribute is added to the second embeddable.
	 */
	
	@Test
	public void testEmbedCollectionOfObjectsInEmbeddable() {
		Utils.sayTestStarted("testEmbedCollectionOfObjectsInEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable1 = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, "Embeddable2");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		_testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable1,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				2);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedCollectionOfObjectsInEmbeddable");
	}

	/**
	 * Add two embeddables and one entity in the diagram. Embed a collection of
	 * the first embeddable to the second one. Check that it is not possible to
	 * embed a collection of the second embeddable into the entity, but it is
	 * possible to embed a collection of the first embeddable to the entity.
	 */
	
	@Test
	public void testEmbedCollectionOfObjectsInEmbeddableAndEntity() {
		Utils.sayTestStarted("testEmbedCollectionOfObjectsInEmbeddableAndEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable1 = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, "Embeddable2");

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable1,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				2);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(200, 50,
				"Entity1");

		editorProxy.testNoEmbeddedConnectionIsCreated(
				JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, 0, entity, embeddable1, true);

		_testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, entity,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				4);

		editorProxy.deleteAttributeInJPT(embeddable1, "embeddable2");

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedCollectionOfObjectsInEmbeddableAndEntity");
	}
	
	/**
	 * Add three embeddables in the diagram. Embed a collection of
	 * the first embeddable to the second one. Check that it is not possible to
	 * embed a collection of the second embeddable into the third one, but it is
	 * possible to embed a collection of the first embeddable to the third embeddable.
	 */
	
	@Test
	public void testEmbedCollectionOfObjectsInTwoEmbeddables() {
		Utils.sayTestStarted("testEmbedCollectionOfObjectsInTwoEmbeddables");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable1 = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, "Embeddable2");

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable1,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				2);

		SWTBotGefEditPart embeddable3 = editorProxy.addEmbeddableToDiagram(200, 50,
				"Embeddable3");

		editorProxy.testNoEmbeddedConnectionIsCreated(
				JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, 0, embeddable3, embeddable1, true);

		_testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable3,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				4);

		editorProxy.deleteAttributeInJPT(embeddable1, "embeddable2");

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedCollectionOfObjectsInTwoEmbeddables");
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						0, entity, embeddable);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						1, entity, embeddable);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, entity, embeddable);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						0, entity, embeddable);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						1, entity, embeddable);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						0, entity, embeddable);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, entity, embeddable);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						1, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						1, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testBiDirRelWithTwoMappingTypes(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelWithTwoMappingsWithoutInverseAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelWithTwoMappingsWithoutOwnerAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						0, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						0, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						0, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						1, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				3);

		editorProxy
				.testBiDirRel(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelRemoveInverseAttribute(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelRemoveOwnerAttr(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						1, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				3);

		editorProxy
				.testBiDirRelWithTwoMappingTypes(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelWithTwoMappingsWithoutInverseAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy
				.testBiDirRelWithTwoMappingsWithoutOwnerAttr(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel,
						JptUiDetailsMessages.OneToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		SWTBotGefEditPart embeddingEntity1 = editorProxy.addEntityToDiagram(
				200, 50, "Entity2");

		SWTBotGefEditPart embeddingEntity2 = editorProxy.addEntityToDiagram(
				200, 200, "Entity3");

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity1, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity2, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

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
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		SWTBotGefEditPart embeddingEntity1 = editorProxy.addEntityToDiagram(
				200, 50, "Entity2");

		SWTBotGefEditPart embeddingEntity2 = editorProxy.addEntityToDiagram(
				200, 200, "Entity3");

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity1, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity2, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

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
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		SWTBotGefEditPart embeddingEntity1 = editorProxy.addEntityToDiagram(
				200, 50, "Entity2");

		SWTBotGefEditPart embeddingEntity2 = editorProxy.addEntityToDiagram(
				200, 200, "Entity3");

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity1, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

		embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity2, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 3);

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
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_MANY,
						JptUiDetailsMessages.ManyToManyMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
						0, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.ONE_TO_ONE,
						JptUiDetailsMessages.OneToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						1, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				3);

		editorProxy
				.testUniDirRelation(
						JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		editorProxy
				.testUniDirRelRemoveOwnerAttribute(
						JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
						embeddable,
						entity,
						IRelation.RelType.MANY_TO_ONE,
						JptUiDetailsMessages.ManyToOneMappingUiProvider_linkLabel);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
						0, embeddable, entity);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				3);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						0, embeddable, entity);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
						1, embeddable, entity);

		editorProxy.deleteDiagramElements();
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
				"Entity1");
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, embeddable, entity);

		SWTBotGefEditPart embeddingEntity = editorProxy.addEntityToDiagram(200,
				50, "Entity2");

		embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddingEntity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages2_0.ElementCollectionMapping2_0_linkLabel,
				3);

		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, embeddable, entity);

		editorProxy.deleteDiagramElements();
		jpaDiagramEditor.saveAndClose();

		Utils.sayTestFinished("testOneToManyRelationFromEmbeddedCollectionToEntity");
	}

	@Test
	public void testJPA10WithEmbeddables() throws CoreException {
		Utils.sayTestStarted("testJPA10WithEmbeddables");
		
		workbenchBot.closeAllEditors();

		String name = "Test10_" + System.currentTimeMillis();
		
		JpaProject jpaProject10 = factory.createJPAProject(name);
		assertNotNull(jpaProject10);

		SWTBotGefEditor jpaDiagramEditor10 = editorProxy
				.openDiagramOnJPAContentNode(name, false);
		editorProxy.setJpaDiagramEditor(jpaDiagramEditor10);

		SWTBotGefEditPart embeddable1 = editorProxy.addEmbeddableToDiagram(50,
				50, "Embeddable1");
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, "Embeddable2");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(300, 50,
				"Entity1");
		
		boolean notAllowed = false;

		try {
			jpaDiagramEditor10.activateTool(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName);
		} catch (WidgetNotFoundException e) {
			notAllowed = true;
		}

		assertTrue(
				"The action \"Embed Collection of Elements\" must not be available by JPA projects with 1.0 facet.",
				notAllowed);
		
		editorProxy.testNoEmbeddedConnectionIsCreated(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, 0, embeddable1, embeddable2, false);
				
		embedConnection(jpaDiagramEditor10, JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, entity, embeddable1,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 2);

		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(300, 300,
				"Entity2");
		editorProxy
				.testNoConnectionIsCreated(
						JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,
						0, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName,
				0, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName,
				1, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName,
				0, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,
				1, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName,
				0, embeddable1, entity1);
		
		editorProxy
		.testNoConnectionIsCreated(
				JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,
				1, embeddable1, entity1);
		
		editorProxy.deleteAttributeInJPT(entity, "embeddable1");
		
		editorProxy.deleteDiagramElements();
		
		jpaDiagramEditor10.saveAndClose();
		
		Utils.sayTestFinished("testJPA10WithEmbeddables");

	}

	/**
	 * Test all specific for the embedded connections properties and delete the
	 * embedded attribute and check that the connection is deleted as well.
	 * 
	 * @param toolEntry
	 *            - the name of the feature in the palette
	 * @param embeddingEntity
	 * @param embeddable
	 * @param refType
	 * @param embeddedMappingKey
	 * @param linkLabel
	 * @param elementsInDiagramCount1
	 */
	private void _testEmbeddedConnection(String toolEntry,
			SWTBotGefEditPart embeddingEntity, SWTBotGefEditPart embeddable,
			HasReferenceType refType, String embeddedMappingKey,
			String linkLabel, int elementsInDiagramCount1) {
		List<SWTBotGefEditPart> entitiesInDiagram = jpaDiagramEditor
				.mainEditPart().children();
		int elementsInDiagramCount = entitiesInDiagram.size();
		assertEquals("Editor contains " + elementsInDiagramCount
				+ " pictogram elements.", elementsInDiagramCount1,
				elementsInDiagramCount);

		String attributeName = embedConnection(toolEntry, embeddingEntity,
				embeddable, refType, embeddedMappingKey, linkLabel,
				elementsInDiagramCount);

		editorProxy.deleteAttributeInJPT(embeddingEntity, attributeName);

		editorProxy.waitASecond();

		entitiesInDiagram = jpaDiagramEditor.mainEditPart().children();
		assertEquals("Connection must disappear.", elementsInDiagramCount,
				entitiesInDiagram.size());
	}

	/**
	 * Test all specific for the embedded connections properties.
	 * 
	 * @param toolEntry
	 * @param embeddingEntity
	 * @param embeddable
	 * @param refType
	 * @param embeddedMappingKey
	 * @param linkLabel
	 * @param elementsInDiagramCount
	 * @return the name of the embedded attribute
	 */
	private String embedConnection(String toolEntry,
			SWTBotGefEditPart embeddingEntity, SWTBotGefEditPart embeddable,
			HasReferenceType refType, String embeddedMappingKey,
			String linkLabel, int elementsInDiagramCount) {

		jpaDiagramEditor.activateTool(toolEntry);
		
		jpaDiagramEditor.click(embeddingEntity);
		jpaDiagramEditor.click(embeddable);

		String attributeName = checkEmbeddedConnectionProperties(
				embeddingEntity, embeddable, refType, embeddedMappingKey,
				linkLabel);
		return attributeName;
	}
	
	private String embedConnection(SWTBotGefEditor gefEditor, String toolEntry,
			SWTBotGefEditPart embeddingEntity, SWTBotGefEditPart embeddable,
			HasReferenceType refType, String embeddedMappingKey,
			String linkLabel, int elementsInDiagramCount) {

		gefEditor.activateTool(toolEntry);
		
		gefEditor.click(embeddingEntity);
		gefEditor.click(embeddable);

		String attributeName = checkEmbeddedConnectionProperties(
				embeddingEntity, embeddable, refType, embeddedMappingKey,
				linkLabel);
		return attributeName;
	}

	private String checkEmbeddedConnectionProperties(
			SWTBotGefEditPart embeddingEntity, SWTBotGefEditPart embeddable,
			HasReferenceType refType, String embeddedMappingKey,
			String linkLabel) {
		
		bot.waitUntil(new ConnectionIsShown(embeddingEntity), 30000);

//		List<SWTBotGefEditPart> entitiesInDiagram = jpaDiagramEditor
//				.mainEditPart().children();
//		assertEquals("Connection must appear.", entitiesInDiagram.size(),
//				elementsInDiagramCount + 1);

		assertFalse("The connection must appear", embeddingEntity
				.sourceConnections().isEmpty());
		SWTBotGefConnectionEditPart connection = embeddingEntity
				.sourceConnections().get(0);
		
		JavaPersistentType emb = editorProxy.getJPTObjectForGefElement(embeddable);
		String embAttr = JPAEditorUtil.decapitalizeFirstLetter(emb.getSimpleName());
		
		List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
		editParts.add(embeddingEntity);
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditpart(
				embAttr, editParts);
		
		assertNotNull("Embedded attribute must be added.", attribute);

		HasReferanceRelation rel = editorProxy
				.getHasReferenceConnection(connection);
		assertNotNull(rel);
		assertEquals(refType, rel.getReferenceType());

		String attributeName = editorProxy.testEmbeddedAttributeProperties(rel,
				embeddedMappingKey);
		assertNotNull(rel.getEmbeddedAnnotatedAttribute());

		editorProxy.assertAttributeIsCorretlyMapped(attributeName, linkLabel);

		JavaPersistentType parententity = (JavaPersistentType) rel
				.getEmbeddedAnnotatedAttribute().getOwningPersistentType();
		assertEquals("The entity must contain an embedded attribute.",
				parententity,
				editorProxy.getJPTObjectForGefElement(embeddingEntity));
		assertEquals(rel.getEmbeddingEntity(),
				editorProxy.getJPTObjectForGefElement(embeddingEntity));
		assertEquals(rel.getEmbeddable(),
				editorProxy.getJPTObjectForGefElement(embeddable));
		return attributeName;
	}
	
	@After
	public void tearDown() throws Exception {
		editorProxy.deleteDiagramElements();
		Utils.printFormatted(">>>>>>>>>>>> elements are deleted from the diagram.");
		
		ListIterator<PersistenceUnit> lit = jpaProject.getRootContextNode().getPersistenceXml().getRoot().getPersistenceUnits().iterator();		
		PersistenceUnit pu = lit.next();
		Iterator<PersistentType> persistentTypesIterator = (Iterator<PersistentType>) pu.getPersistentTypes().iterator();
		while(persistentTypesIterator.hasNext()){
			Utils.printFormatted(">>>>>>>>>>>>>> persistent type resource must be deleted.");
			PersistentType type = persistentTypesIterator.next();
			type.getResource().delete(true, new NullProgressMonitor());
		}
	}
}

