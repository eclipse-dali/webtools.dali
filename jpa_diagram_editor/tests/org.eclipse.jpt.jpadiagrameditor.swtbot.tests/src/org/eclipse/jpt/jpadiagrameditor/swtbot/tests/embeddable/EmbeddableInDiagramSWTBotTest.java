package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.embeddable;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.ui.internal.parts.DiagramEditPart;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions.ElementIsShown;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.AbstractSwtBotEditorTest;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation.HasReferenceType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
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
public class EmbeddableInDiagramSWTBotTest extends AbstractSwtBotEditorTest {

	protected static String TEST_PROJECT = "Test_" + System.currentTimeMillis();

	@BeforeClass
	public static void beforeClass() throws Exception {
		createJPa20Project(TEST_PROJECT);
	}

	/**
	 * Add embeddable to diagram and check that it does not contain a
	 * "Primary Key", "Relation Attributes" and "Other Attributes" sections.
	 */
	@Test
	public void testAddEmbeddable() {
		Utils.sayTestStarted("testAddEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		editorProxy.addEmbeddableToDiagram(50, 50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		jpaDiagramEditor.save();
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteJPTViaButton(embeddable, true);

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
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteJPTViaMenu(embeddable);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveEmbeddableViaContextMenu");
	}

//	/**
//	 * Adds a new attribute to the embeddable using the entity's context button
//	 * "Create Attribute"
//	 */
//	@Test
//	public void testAddAttributeToEmbeddablle() {
//		Utils.sayTestStarted("testAddAttributeToEmbeddablle");
//
//		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());
//
//		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
//				50, jpaProject);
//		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
//
//		assertFalse(
//				"\"Other Attributes\" section must not be visible!",
//				editorProxy
//						.isSectionVisible(
//								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
//								embeddable));
//		
//		String attributeName = editorProxy.getUniqueAttrName(embeddable);
//		editorProxy.addAttributeToJPT(embeddable, attributeName, false);
//		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
//
//		embeddable.click();
//		editorProxy.deleteDiagramElements(false);
//		jpaDiagramEditor.save();
//
//		Utils.sayTestFinished("testAddAttributeToEmbeddablle");
//	}
	
	

	/**
	 * Removes the attribute using the "Delete Attribute" context button.
	 */
	@Test
	public void testAddRemoveAttributeFromEmbeddableViaContextButton() {
		Utils.sayTestStarted("testRemoveAttributeFromEmbeddableViaContextButton");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(embeddable, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAttributeViaButton(embeddable, attribute, attributeName, false);

		embeddable.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAttributeFromEmbeddableViaContextButton");
	}

	/**
	 * Removes the attribute using the "Delete" context menu.
	 */
	@Test
	public void testAddRemoveAttributeFromEmbeddableViaMenu() {
		Utils.sayTestStarted("testRemoveAttributeFromEmbeddableViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		
		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(embeddable, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.removeAttributeViaMenu(embeddable, attribute, attributeName);

		embeddable.click();
		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testRemoveAttributeFromEmbeddableViaMenu");
	}
	
	@Test
	public void testAddRemoveElementCollectionAttributeToEmbeddable(){
		Utils.sayTestStarted("testAddElementCollectionAttributeToEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50, 50, jpaProject);

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy.isSectionVisible(JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes, embeddable));

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		SWTBotGefEditPart attribute = editorProxy.addElementCollectionAttributeToJPT(embeddable, attributeName);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		
		editorProxy.removeAttributeViaButton(embeddable, attribute, attributeName, false);

		embeddable.click();
		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, jpaProject);
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(200, 50, jpaProject);

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy.isSectionVisible(JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes, embeddable1));

		String attributeName = editorProxy.getUniqueAttrName(embeddable1);
		SWTBotGefEditPart attribute = editorProxy.addElementCollectionAttributeToJPT(embeddable1, attributeName);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		
		editorProxy.testNoEmbeddedConnectionIsCreated(
				JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, 0, entity, embeddable1, false);
		
		editorProxy.testNoEmbeddedConnectionIsCreated(
				JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, 0, embeddable2, embeddable1, false);
		
		jpaDiagramEditor.activateDefaultTool();
		editorProxy.waitASecond();
		editorProxy.removeAttributeViaButton(embeddable1, attribute, attributeName, false);

		editorProxy._testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable1,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				3);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedCollectionOfObjectsWithCollectionAttribute");
	}

	/**
	 * Adds a new attribute and rename it
	 */
	@Test
	public void testDirectEditingAttributeInEmbeddable() {
		Utils.sayTestStarted("testDirectEditingAttributeInEmbeddable");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		editorProxy.directEditAttribute(embeddable, attributeName);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		String oldEmbeddableName = editorProxy.getJPTObjectForGefElement(embeddable).getSimpleName();
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.moveMouse(100, 70);
		embeddable.click();
		embeddable.activateDirectEdit();
		jpaDiagramEditor.directEditType("NewEntityName");
		editorProxy.moveMouse(0, 0);

		SWTBotGefEditPart oldEmbeddable = jpaDiagramEditor
				.getEditPart(oldEmbeddableName);
		SWTBotGefEditPart newEmbeddable = jpaDiagramEditor
				.getEditPart("NewEntityName");
		assertNotNull("The entity must be renamed!", newEmbeddable);
		assertNull("The attribute must be renamed!", oldEmbeddable);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.moveMouse(100, 70);
		jpaDiagramEditor.doubleClick(embeddable);
		editorProxy.moveMouse(0, 0);

		SWTBotEditor activeEditor = workbenchBot.activeEditor();
		assertEquals("The Java editor of the enity did not open!",
				"Embeddable1.java", activeEditor.getTitle());
		activeEditor.close();

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				editorProxy
						.isSectionVisible(
								JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
								embeddable));

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		SWTBotGefEditPart attribute = editorProxy.addAttributeToJPT(embeddable,
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
		embeddable.click();
		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		String oldEmbeddableName = editorProxy.getJPTObjectForGefElement(embeddable).getSimpleName();

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
				jpaDiagramEditor.getEditPart(oldEmbeddableName));

		embeddable.click();
		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		String embeddableName = editorProxy.getJPTObjectForGefElement(embeddable).getSimpleName();
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

		embeddable = jpaDiagramEditor.getEditPart(embeddableName);
		String newEntityPackage = factory
				.getMappedSuperclassPackageDeclaration(editorProxy
						.getJPTObjectForGefElement(embeddable));
		assertFalse("Entity must be moved!",
				packageName.equals(newEntityPackage));
		assertTrue("Entity must be changed!", newEntityPackage.equals("org"));

		embeddable.click();
		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		editorProxy.addAttributeToJPT(embeddable, attributeName, false);

		editorProxy.collapseExpandJPTViaButton(embeddable);

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		editorProxy.addAttributeToJPT(embeddable, attributeName, false);

		editorProxy.collapseExpandJPTViaMenu(embeddable);

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		editorProxy.discardChanges(embeddable, attributeName);

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		editorProxy.removeAndDiscardChangesViaMenu(embeddable, attributeName);

		embeddable.click();
		editorProxy.deleteDiagramElements(false);
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
	@Ignore
	@Test
	public void testRemoveAndSaveChangesToEmbeddableViaMenu() {
		Utils.sayTestStarted("testRemoveAndSaveChangesToEmbeddableViaMenu");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		editorProxy.removeAndSaveChangesViaMenu(embeddable, attributeName);

		embeddable.click();
		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		String attributeName = editorProxy.getUniqueAttrName(embeddable);
		editorProxy.saveOnlyJPT(embeddable, attributeName);

		editorProxy.deleteDiagramElements(false);
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
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				200, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy._testEmbeddedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, entity, embeddable,
				HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 2);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedSingleObjectInEntity");
	}

	/**
	 * Add one entity and one embeddable in the diagram. Embed a collection of
	 * the embeddable into the entity and checks that the connection exists and
	 * a new embedded attribute is added to the entity.
	 */
	@Test
	public void testEmbedCollectionOfObjectsInEntity() {
		Utils.sayTestStarted("testEmbedCollectionOfObjectsInEntity");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(50, 50,
				jpaProject);
		SWTBotGefEditPart embeddable = editorProxy.addEmbeddableToDiagram(50,
				200, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy._testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, entity,
				embeddable, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				2);

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy._testEmbeddedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddable1,
				embeddable2, HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel, 2);

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, jpaProject);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy._testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable1,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				2);

		editorProxy.deleteDiagramElements(false);
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
				50, jpaProject);
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, jpaProject);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable1,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				2);

		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(200, 50,
				jpaProject);

		editorProxy.testNoEmbeddedConnectionIsCreated(
				JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, 0, entity, embeddable1, true);

		editorProxy._testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, entity,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				4);

		
		String embeddableName = editorProxy.getJPTObjectForGefElement(embeddable2).getSimpleName();
		String embeddingAttributeName = JPAEditorUtil.decapitalizeFirstLetter(embeddableName);
		editorProxy.deleteAttributeInJPT(embeddable1, embeddingAttributeName);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedCollectionOfObjectsInEmbeddableAndEntity");
	}
	
	/**
	 * Add three embeddables in the diagram. Embed a collection of
	 * the first embeddable to the second one. Check that it is not possible to
	 * embed a collection of the second embeddable into the third one, but it is
	 * possible to embed a collection of the first embeddable to the third embeddable.
	 */
	@Ignore
	@Test
	public void testEmbedCollectionOfObjectsInTwoEmbeddables() {
		Utils.sayTestStarted("testEmbedCollectionOfObjectsInTwoEmbeddables");

		assertTrue("The diagram must be empty.", jpaDiagramEditor.mainEditPart().children().isEmpty());

		SWTBotGefEditPart embeddable1 = editorProxy.addEmbeddableToDiagram(50,
				50, jpaProject);
		SWTBotGefEditPart embeddable2 = editorProxy.addEmbeddableToDiagram(50,
				200, jpaProject);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.embedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable1,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				2);

		SWTBotGefEditPart embeddable3 = editorProxy.addEmbeddableToDiagram(200, 50,
				jpaProject);

		editorProxy.testNoEmbeddedConnectionIsCreated(
				JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, 0, embeddable3, embeddable1, true);

		editorProxy._testEmbeddedConnection(JPAEditorMessages.EmbedCollectionOfObjectsFeature_ElementCollectionFeatureName, embeddable3,
				embeddable2, HasReferenceType.COLLECTION,
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL,
				4);

		String embeddableName = editorProxy.getJPTObjectForGefElement(embeddable2).getSimpleName();
		String embeddingAttributeName = JPAEditorUtil.decapitalizeFirstLetter(embeddableName);
		editorProxy.deleteAttributeInJPT(embeddable1, embeddingAttributeName);

		editorProxy.deleteDiagramElements(false);
		jpaDiagramEditor.save();

		Utils.sayTestFinished("testEmbedCollectionOfObjectsInTwoEmbeddables");
	}
}

