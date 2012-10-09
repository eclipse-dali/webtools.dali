package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.ui.editor;


import static org.junit.Assert.assertFalse;

import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.internal.parts.DiagramEditPart;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.JPACreateFactory;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IsARelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefConnectionEditPart;
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
public class JPADiagramEditorSWTBotTest extends SWTBotGefTestCase {
	
	protected static String TEST_PROJECT;
	protected static JPACreateFactory factory = JPACreateFactory.instance();
	protected static JpaProject jpaProject;
	
	protected static SWTGefBot bot = new SWTGefBot();
	protected static SWTWorkbenchBot workbenchBot = new SWTWorkbenchBot();
	protected static EditorProxy editorProxy = new EditorProxy(workbenchBot, bot);
	
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
		
		jpaDiagramEditor = editorProxy.openDiagramOnJPAContentNode(TEST_PROJECT);		
		
		Thread.sleep(2000);
	}
	
//	@After
//	public void tearDown() throws Exception {
//		editorProxy.deleteDiagramElements(jpaDiagramEditor);
//	}
	
	/**
	 * Add entity to diagram and check that it contains a "Primary Key" section
	 * with one attribute "id" and no "Relation Attributes" and "Other Attributes"
	 * sections.
	 */
	@Test
	public void testAddEntity() {
		Utils.sayTestStarted("testAddEntity");
				
		editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		jpaDiagramEditor.save();
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());

		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testAddEntity");
	}
	
	/**
	 * Remove an entity from the diagram using the entity's context button "Delete"
	 */
	@Test
	public void testRemoveEntityViaButton(){
		Utils.sayTestStarted("testRemoveEntityViaButton");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		editorProxy.pressEntityContextButton(jpaDiagramEditor, entity, JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonLabel);
		editorProxy.denyDelete();		
		entity = jpaDiagramEditor.getEditPart("Entity1");
		assertNotNull("Entity is deleted!", entity);
		
		editorProxy.pressEntityContextButton(jpaDiagramEditor, entity, JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonLabel);
		editorProxy.confirmDelete();
		entity = jpaDiagramEditor.getEditPart("Entity1");
		assertNull("Entity is not deleted!", entity);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testRemoveEntityViaButton");
	}
	
	/**
	 * Remove an entity from the diagram using the entity's context menu "Delete"
	 */
	@Test
	public void testRemoveEntityViaContextMenu(){
		Utils.sayTestStarted("testRemoveEntityViaContextMenu");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonLabel);
		editorProxy.denyDelete();		
		entity = jpaDiagramEditor.getEditPart("Entity1");
		assertNotNull("Entity is deleted!", entity);
		
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonLabel);
		editorProxy.confirmDelete();
		entity = jpaDiagramEditor.getEditPart("Entity1");
		assertNull("Entity is not deleted!", entity);

		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testRemoveEntityViaContextMenu");
	}
	
	/**
	 * Adds a new attribute to the entity using the entity's context button "Create Attribute"
	 */
	@Test
	public void testAddAttribute(){
		Utils.sayTestStarted("testRemoveEntityViaContextMenu");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
				
		editorProxy.addAttributeToEntity(jpaDiagramEditor, entity, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());		
		
		entity.click();
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testRemoveEntityViaContextMenu");
	}
	
	/**
	 * Removes the attribute using the "Delete Attribute" context button.
	 */
	@Test
	public void testRemoveAttributeViaContextButton(){
		Utils.sayTestStarted("testRemoveAttributeViaContextButton");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");		
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
				
		SWTBotGefEditPart attribute = editorProxy.addAttributeToEntity(jpaDiagramEditor, entity, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();
		
		editorProxy.pressAttributeDeleteContextButton(jpaDiagramEditor, attribute);
		editorProxy.denyDelete();
		attribute = jpaDiagramEditor.getEditPart("attribute1");
		assertNotNull("Attribute must not be deleted!", attribute);
		
		editorProxy.pressAttributeDeleteContextButton(jpaDiagramEditor, attribute);
		editorProxy.confirmDelete();
		attribute = jpaDiagramEditor.getEditPart("attribute1");
		assertNull("Attribute must be deleted!", attribute);
		
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));

		entity.click();
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testRemoveAttributeViaContextButton");
	}
	
	/**
	 * Removes the attribute using the "Delete" context menu.
	 */
	@Test
	public void testRemoveAttributeViaMenu(){
		Utils.sayTestStarted("testRemoveAttributeViaMenu");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
				
		SWTBotGefEditPart attribute = editorProxy.addAttributeToEntity(jpaDiagramEditor, entity, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		jpaDiagramEditor.save();
		
		attribute.click();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.denyDelete();
		attribute = jpaDiagramEditor.getEditPart("attribute1");
		assertNotNull("Attribute must not be deleted!", attribute);
		
		attribute.click();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		attribute = jpaDiagramEditor.getEditPart("attribute1");
		assertNull("Attribute must be deleted!", attribute);
		
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));

		entity.click();
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testRemoveAttributeViaMenu");
	}
	
	/**
	 * Adds a new attribute and rename it
	 */
	@Test
	public void testDirectEditingAttribute(){
		Utils.sayTestStarted("testDirectEditingAttribute");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
				
		SWTBotGefEditPart attribute = editorProxy.addAttributeToEntity(jpaDiagramEditor, entity, "attribute1");
		assertNotNull("The attribute must not be renamed!", attribute);
		
		jpaDiagramEditor.directEditType("newAttrName");
		SWTBotGefEditPart oldAttribute = jpaDiagramEditor.getEditPart("attribute1");
		SWTBotGefEditPart newAttribute = jpaDiagramEditor.getEditPart("newAttrName");
		assertNotNull("The attribute must be renamed!", newAttribute);
		assertNull("The attribute must be renamed!", oldAttribute);
		
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testDirectEditingAttribute");
	}
	
	/**
	 * Adds a new entity and rename it
	 */
	@Ignore
	@Test
	public void testDirectEditingEntity(){
		Utils.sayTestStarted("testDirectEditingEntity");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());

		editorProxy.moveMouse(jpaDiagramEditor, 100, 70);
		entity.click();
		entity.activateDirectEdit();
		jpaDiagramEditor.directEditType("NewEntityName");
		editorProxy.moveMouse(jpaDiagramEditor, 0, 0);
		
		SWTBotGefEditPart oldEntity = jpaDiagramEditor.getEditPart("Entity1");
		SWTBotGefEditPart newEntity = jpaDiagramEditor.getEditPart("NewEntityName");
		assertNotNull("The entity must be renamed!", newEntity);
		assertNull("The attribute must be renamed!", oldEntity);
		
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testDirectEditingEntity");
	}
	
	/**
	 * Test that the source of the entity is opened, when is double clicked on it
	 */
	@Ignore
	@Test
	public void testDoubleClickOnEntity(){
		Utils.sayTestStarted("testDoubleClickOnEntity");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		editorProxy.moveMouse(jpaDiagramEditor, 100, 70);
		jpaDiagramEditor.doubleClick(entity);
		editorProxy.moveMouse(jpaDiagramEditor, 0, 0);
		
		SWTBotEditor activeEditor = workbenchBot.activeEditor();
		assertEquals("The Java editor of the enity did not open!", "Entity1.java", activeEditor.getTitle());
		activeEditor.close();
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testDoubleClickOnEntity");
	}
	
	/**
	 * Change the attribute type.
	 */
	@Test
	@SuppressWarnings("restriction")
	public void testChangeAttributeType(){
		Utils.sayTestStarted("testChangeAttributeType");
		
		List<SWTBotGefEditPart> entities = jpaDiagramEditor.mainEditPart().children();
		assertTrue("Editor must not contain any entities!", entities.isEmpty());
				
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
				
		SWTBotGefEditPart attribute = editorProxy.addAttributeToEntity(jpaDiagramEditor, entity, "attribute1");
		assertNotNull("The attribute must not be renamed!", attribute);
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		String currentAttributeType = editorProxy.getAttributeType(jpaDiagramEditor, "attribute1", fp);
		assertEquals("java.lang.String", currentAttributeType);
		
		SWTBotShell changeTypeDialog = editorProxy.getSelectNewAttributeTypeDialog(jpaDiagramEditor, attribute);
		SWTBotText attributeType = editorProxy.getNewTypeInputField(changeTypeDialog);
		
		//test invalid attribute type
		attributeType.setText("");
		assertFalse(editorProxy.getOkButton(changeTypeDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(changeTypeDialog).isEnabled());
		SWTBotText dialogError = editorProxy.getDialogErroMessage(changeTypeDialog);
		assertEquals(" The new type name must not be empty", dialogError.getText());
		//cancel the dialog
		editorProxy.getCancelButton(changeTypeDialog).click();
		
		//assert that the attribute type is not changed
		currentAttributeType = editorProxy.getAttributeType(jpaDiagramEditor, "attribute1", fp);
		assertEquals("The attribute type must not be changed!", "java.lang.String", currentAttributeType);
		
		changeTypeDialog = editorProxy.getSelectNewAttributeTypeDialog(jpaDiagramEditor, attribute);
		attributeType = editorProxy.getNewTypeInputField(changeTypeDialog);
		
		//change the attribute type to int
		attributeType.setText("int");
		assertTrue(editorProxy.getOkButton(changeTypeDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(changeTypeDialog).isEnabled());
		//confirm the dialog
		editorProxy.getOkButton(changeTypeDialog).click();
		//assert that the attribute's type is changed
		String newAttributeType = currentAttributeType = editorProxy.getAttributeType(jpaDiagramEditor, "attribute1", fp);
		assertFalse("The attribute type must be changed!", ("java.lang.String").equals(newAttributeType));
		assertEquals("The attribute type must be changed!", "int", newAttributeType);

		assertTrue("Editor must be dirty!", jpaDiagramEditor.isDirty());
		entity.click();
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testChangeAttributeType");
	}
	
	/**
	 * Rename the entity using its context menu "Refactor Entity Class -> Rename..."
	 */
	@Ignore
	@Test
	public void testRenameEntityViaMenu(){
		Utils.sayTestStarted("testRenameEntityViaMenu");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
	
		entity.click();
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_renameEntityClass);
		
		SWTBotShell renameEntityDialog = editorProxy.getRenameEntityDialog();
		SWTBotText entityName = renameEntityDialog.bot().textWithLabel("New name:");
		
		//test invalid entity name
		entityName.setText("");
		assertFalse(editorProxy.getFinishButton(renameEntityDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(renameEntityDialog).isEnabled());
		
		entityName.setText("NewEntityName");
		assertTrue(editorProxy.getFinishButton(renameEntityDialog).isEnabled());
		assertTrue(editorProxy.getCancelButton(renameEntityDialog).isEnabled());
		
		editorProxy.getFinishButton(renameEntityDialog).click();
		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, "NewEntityName"), 30000);
		
		entity = jpaDiagramEditor.getEditPart("NewEntityName");
		assertNotNull("Entity name must be changed!", entity);
		assertNull("Entity naem must be changed!", jpaDiagramEditor.getEditPart("Entity1"));
		
		entity.click();		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testRenameEntityViaMenu");
	}
	
	/**
	 * Move the entity class using the entity's context menu "Refactor Entity Class -> Move..."
	 */
	@Ignore
	@Test
	public void testMoveEntityViaMenu() throws JavaModelException{
		Utils.sayTestStarted("testMoveEntityViaMenu");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		JpaArtifactFactory factory = JpaArtifactFactory.instance();
		
		String packageName = factory.getMappedSuperclassPackageDeclaration(editorProxy.getEntityForElement(jpaDiagramEditor, entity));
		
		entity.click();
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_moveEntityClass);
		
		SWTBotShell moveEntityDialog = editorProxy.getMoveEntityDialog();
		moveEntityDialog.bot().tree().getTreeItem(TEST_PROJECT).select().expandNode("src").expandNode("org").select().click();
		assertTrue(editorProxy.getOkButton(moveEntityDialog).isEnabled());
		editorProxy.getOkButton(moveEntityDialog).click();
		editorProxy.waitASecond();
		
		entity = jpaDiagramEditor.getEditPart("Entity1");		
		String newEntityPackage= factory.getMappedSuperclassPackageDeclaration(editorProxy.getEntityForElement(jpaDiagramEditor, entity));
		assertFalse("Entity must be moved!", packageName.equals(newEntityPackage));
		assertTrue("Entity must be changed!", newEntityPackage.equals("org"));
		
		entity.click();		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testMoveEntityViaMenu");
	}
	
	/**
	 * Collapse/expand entity using its context buttons
	 */
	@Test
	public void testCollapseExapandEntityViaContextButton(){
		Utils.sayTestStarted("testCollapseExapandEntityViaContextButton");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		int heigth = ((PictogramElement)entity.part().getModel()).getGraphicsAlgorithm().getHeight();
		
		editorProxy.pressEntityContextButton(jpaDiagramEditor, entity, "Collapse");
		
		entity = jpaDiagramEditor.getEditPart("Entity1");
		int newHeight = ((PictogramElement)entity.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be collapsed!", JPAEditorConstants.ENTITY_MIN_HEIGHT, newHeight); 
		assertTrue(newHeight < heigth);

		editorProxy.pressEntityContextButton(jpaDiagramEditor, entity, "Expand");
		entity = jpaDiagramEditor.getEditPart("Entity1");
		newHeight = ((PictogramElement)entity.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be expanded!", heigth, newHeight);
		assertTrue(newHeight > JPAEditorConstants.ENTITY_MIN_HEIGHT); 
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testCollapseExapandEntityViaContextButton");
	}
	
	/**
	 * Collapse/expand entity using its context menus
	 */
	@Test
	public void testCollapseExapandEntityViaMenu(){
		Utils.sayTestStarted("testCollapseExapandEntityViaMenu");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		int heigth = ((PictogramElement)entity.part().getModel()).getGraphicsAlgorithm().getHeight();
		
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseEntityMenuItem);
		
		entity = jpaDiagramEditor.getEditPart("Entity1");
		int newHeight = ((PictogramElement)entity.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be collapsed!", JPAEditorConstants.ENTITY_MIN_HEIGHT, newHeight); 
		assertTrue(newHeight < heigth);

		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandEntityMenuItem);
		entity = jpaDiagramEditor.getEditPart("Entity1");
		newHeight = ((PictogramElement)entity.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be expanded!", heigth, newHeight);
		assertTrue(newHeight > JPAEditorConstants.ENTITY_MIN_HEIGHT); 
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testCollapseExapandEntityViaMenu");
	}

	/**
	 * Collapse/expand all entities using the context menus
	 */
	@Test
	public void testCollapseExapandAllEntitiesViaMenu(){
		Utils.sayTestStarted("testCollapseExapandAllEntitiesViaMenu");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		int heigth1 = ((PictogramElement)entity1.part().getModel()).getGraphicsAlgorithm().getHeight();
		
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 300, 50, "Entity2");
		
		int heigth2 = ((PictogramElement)entity2.part().getModel()).getGraphicsAlgorithm().getHeight();
		
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseAllEntitiesMenuItem);
		
		//check that entity1 is collapsed
		entity1 = jpaDiagramEditor.getEditPart("Entity1");
		int newHeight1 = ((PictogramElement)entity1.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Entity1 must be collapsed!", JPAEditorConstants.ENTITY_MIN_HEIGHT, newHeight1); 
		assertTrue(newHeight1 < heigth1);
		
		//check that entity2 is collapsed
		entity2 = jpaDiagramEditor.getEditPart("Entity2");
		int newHeight2 = ((PictogramElement)entity2.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Entity2 must be collapsed!", JPAEditorConstants.ENTITY_MIN_HEIGHT, newHeight2); 
		assertTrue(newHeight2 < heigth2);

		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandAllEntitiesMenuItem);
		
		//check that entity1 is expanded
		entity1 = jpaDiagramEditor.getEditPart("Entity1");
		newHeight1 = ((PictogramElement)entity1.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be expanded!", heigth1, newHeight1);
		assertTrue(newHeight1 > JPAEditorConstants.ENTITY_MIN_HEIGHT);
		
		//check that entity2 is expanded
		entity2 = jpaDiagramEditor.getEditPart("Entity2");
		newHeight2 = ((PictogramElement)entity2.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be expanded!", heigth2, newHeight2);
		assertTrue(newHeight2 > JPAEditorConstants.ENTITY_MIN_HEIGHT); 
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testCollapseExapandAllEntitiesViaMenu");
	}

	/**
	 * Add a new attribute without saving the entity and call the "Discard Changes" context menu.
	 * Assert that the newly added attribute is removed and the entity does not contain unsaved
	 * changes.
	 */
	@Test
	public void testDiscardChanges(){
		Utils.sayTestStarted("testDiscardChanges");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		assertFalse(editorProxy.isEntityDirty(jpaDiagramEditor, entity));
		
		editorProxy.addAttributeToEntity(jpaDiagramEditor, entity, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertTrue(editorProxy.isEntityDirty(jpaDiagramEditor, entity));
		
		entity.click();
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_discardChangesMenuItem);
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditPart("attribute1");
		assertNull("Changes must be discard!", attribute);
		assertFalse(editorProxy.isEntityDirty(jpaDiagramEditor, entity));		
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testDiscardChanges");
	}
	
	/**
	 * Add a new attribute without saving the entity and call the entity's context menu "Remove All Entities from Diagram ->
	 * ... and Discard Changes" context menu. Assert that the diagram is empty. Call "Show All Entities" context menu and
	 * assert that the newly added attribute is removed and the entity does not contain unsaved changes.
	 */
	@Test
	public void testRemoveAndDiscardChangesViaMenu(){
		Utils.sayTestStarted("testRemoveAndDiscardChangesViaMenu");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		assertFalse("Diagram must contain at least one entity!", jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		assertFalse(editorProxy.isEntityDirty(jpaDiagramEditor, entity));
		
		editorProxy.addAttributeToEntity(jpaDiagramEditor, entity, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertTrue(editorProxy.isEntityDirty(jpaDiagramEditor, entity));
		
		entity.click();
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndDiscardAllEntitiesAction);
		editorProxy.confirmRemoveEntitiesFromDiagramDialog();
		assertTrue("Diagram must be empty!", jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_showAllTheEntities);
		assertFalse("Diagram must contain at least one entity!", jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		entity = jpaDiagramEditor.getEditPart("Entity1");		
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditPart("attribute1");
		assertNull("Changes must be discard!", attribute);
		assertFalse(editorProxy.isEntityDirty(jpaDiagramEditor, entity));		
		
		entity.click();
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testRemoveAndDiscardChangesViaMenu");
	}
	
	/**
	 * Add a new attribute without saving the entity and call the entity's context menu "Remove All Entities from Diagram ->
	 * ... and Save Changes" context menu. Assert that the diagram is empty. Call "Show All Entities" context menu and
	 * assert that the newly added attribute is added and the entity does not contain unsaved changes.
	 */
	@Test
	public void testRemoveAndSaveChangesViaMenu(){
		Utils.sayTestStarted("testRemoveAndSaveChangesViaMenu");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		assertFalse("Diagram must contain at least one entity!", jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		assertFalse(editorProxy.isEntityDirty(jpaDiagramEditor, entity));
		
		editorProxy.addAttributeToEntity(jpaDiagramEditor, entity, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertTrue(editorProxy.isEntityDirty(jpaDiagramEditor, entity));
		
		entity.click();
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndSaveAllEntitiesAction);
		editorProxy.confirmRemoveEntitiesFromDiagramDialog();
		assertTrue("Diagram must be empty!", jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_showAllTheEntities);
		assertFalse("Diagram must contain at least one entity!", jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		entity = jpaDiagramEditor.getEditPart("Entity1");		
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditPart("attribute1");
		assertNotNull("Changes must be discard!", attribute);
		assertFalse(editorProxy.isEntityDirty(jpaDiagramEditor, entity));		
		
		entity.click();
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testRemoveAndSaveChangesViaMenu");
	}
	
	/**
	 * Add a new attribute to the entity. From the entity's context menu
	 * select "Save". Assert that the entity does not contain any unsaved changes,
	 * but the diagram editor is still dirty.
	 */
	@Test
	public void testSaveOnlyEntity(){
		Utils.sayTestStarted("testSaveOnlyEntity");
		
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		assertFalse("\"Other Attributes\" section must not be visible!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		assertFalse(editorProxy.isEntityDirty(jpaDiagramEditor, entity));
		
		editorProxy.addAttributeToEntity(jpaDiagramEditor, entity, "attribute1");
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertTrue(editorProxy.isEntityDirty(jpaDiagramEditor, entity));
		
		entity.click();
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_saveButtonText);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertFalse(editorProxy.isEntityDirty(jpaDiagramEditor, entity));		
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testSaveOnlyEntity");
	}
	
	/**
	 * Create a JPA project and one entity in it.
	 * Open the JPA diagram editor and call the
	 * "Show All Entities" context menu.
	 * Assert that the previously created entity is
	 * shown in the diagram.
	 * @throws Exception
	 */
	@Test
	public void testShowAllEntities() throws Exception{
		Utils.sayTestStarted("testShowAllEntities");
		
		factory.createEntity(jpaProject, "com.sap.test.Customer");
		assertTrue(jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_showAllTheEntities);
		
		List<SWTBotGefEditPart> entities = jpaDiagramEditor.mainEditPart().children();
		assertFalse("Diagram editor must contain at least one entity!", entities.isEmpty());
		
		SWTBotGefEditPart entity = jpaDiagramEditor.getEditPart("Customer");
		assertNotNull(entity);		
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testShowAllEntities");
	}
	
	/**
	 * Collapse/expand "Primary Key" section by double click on it
	 */
	@Ignore
	@Test
	public void testCollapseExpandCompartmentByDoubleClick(){
		Utils.sayTestStarted("testCollapseExpandCompartmentByDoubleClick");
		
		editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		editorProxy.moveMouse(jpaDiagramEditor, 100, 100);
		SWTBotGefEditPart primaryKeySection = jpaDiagramEditor.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		
		int height = ((PictogramElement)primaryKeySection.part().getModel()).getGraphicsAlgorithm().getHeight();
		
		primaryKeySection.click();
		
		jpaDiagramEditor.doubleClick(primaryKeySection);
		primaryKeySection = jpaDiagramEditor.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		int newHeight = ((PictogramElement)primaryKeySection.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Primary Key section must be collapsed!", JPAEditorConstants.COMPARTMENT_MIN_HEIGHT, newHeight); 
		assertTrue(newHeight < height);
		
		jpaDiagramEditor.doubleClick(primaryKeySection);
		primaryKeySection = jpaDiagramEditor.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		int newHeight1 = ((PictogramElement)primaryKeySection.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Primary Key section must be collapsed!", height, newHeight1); 
		assertTrue(newHeight1 > JPAEditorConstants.COMPARTMENT_MIN_HEIGHT);
		
		editorProxy.moveMouse(jpaDiagramEditor, 0, 0);
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testCollapseExpandCompartmentByDoubleClick");
	}
	
	/**
	 * Collapse/expand compartment by its context menu
	 */
	@Ignore
	@Test
	public void testCollapseExpandCompartmentByContextMenu(){
		Utils.sayTestStarted("testCollapseExpandCompartmentByContextMenu");
		
		editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		editorProxy.moveMouse(jpaDiagramEditor, 100, 100);
		SWTBotGefEditPart primaryKeySection = jpaDiagramEditor.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		
		int height = ((PictogramElement)primaryKeySection.part().getModel()).getGraphicsAlgorithm().getHeight();
		
		primaryKeySection.click();
		
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseAttrGroupMenuItem);
		primaryKeySection = jpaDiagramEditor.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		int newHeight = ((PictogramElement)primaryKeySection.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Primary Key section must be collapsed!", JPAEditorConstants.COMPARTMENT_MIN_HEIGHT, newHeight); 
		assertTrue(newHeight < height);
		
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandAttrMenuItem);
		primaryKeySection = jpaDiagramEditor.getEditPart(JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape);
		int newHeight1 = ((PictogramElement)primaryKeySection.part().getModel()).getGraphicsAlgorithm().getHeight();
		assertEquals("Primary Key section must be collapsed!", height, newHeight1); 
		assertTrue(newHeight1 > JPAEditorConstants.COMPARTMENT_MIN_HEIGHT);
		
		editorProxy.moveMouse(jpaDiagramEditor, 0, 0);
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testCollapseExpandCompartmentByContextMenu");
	}
	
	/**
	 * Test the integration between the JPA diagram editor and the JPA Details view:
	 * 1.) Close the JPA Details view
	 * 2.) Create an entity and call the context menu "Open JPA Details View"
	 * 3.) Assert that the JPA Details view is opened and the appropriate information
	 * is shown (the type is mapped as entity and the expandable sections are available).
	 * 4.) Change the mapping type to "Mapped Superclass"
	 * 5.) Change the mapping type to "Embeddable" and assert that the type is removed from the diagram
	 * 6.) Revert the mapping type to be "Entity" and call the "Show All Entities" context menu
	 * to visualize the entity in the diagram editor.
	 */
	@Ignore
	@Test
	public void testChangeEntityMappingTypeViaJPADetailsView(){
		Utils.sayTestStarted("testChangeEntityMappingTypeViaJPADetailsView");
		
		workbenchBot.viewByTitle("JPA Details").close();
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		jpaDiagramEditor.save();	
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_openJPADetailsView);
		
		//assert that the JPA Details view is opened
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		assertTrue("JPA Details view must be opened!", jpaDetailsView.isActive());

		//assert that the type is mapped as entity in the JPA Deatils view
		entity.click();
		SWTBot jpaDetailsBot = jpaDetailsView.bot();
		SWTBotStyledText styledText = jpaDetailsBot.styledText();
		assertEquals("Type 'Entity1' is mapped as entity.", styledText.getText());
		assertNotNull("Entity must be shown in the diagram!", jpaDiagramEditor.getEditPart("Entity1"));
		//assert that the entity's sections are enabled
		assertTrue(jpaDetailsBot.label("Entity").isEnabled());
		assertTrue(jpaDetailsBot.label("Queries").isEnabled());
		assertTrue(jpaDetailsBot.label("Inheritance").isEnabled());
		assertTrue(jpaDetailsBot.label("Attribute Overrides").isEnabled());
		assertTrue(jpaDetailsBot.label("Primary Key Generation").isEnabled());
		assertTrue(jpaDetailsBot.label("Secondary Tables").isEnabled());

		
		//change the mapping type to "Mapped Superclass" and assert that the type is mapped
		//as mapped superclass in the JPA Details view
		editorProxy.clickOnStyledText(styledText, 30);
		editorProxy.changeMappingtype("Mapped Superclass");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Type 'Entity1' is mapped as mapped superclass.", styledText.getText());
		assertNotNull("Entity must be shown in the diagram!", jpaDiagramEditor.getEditPart("Entity1"));
		//assert that the mapped superclass'es sections are enabled
		assertTrue(jpaDetailsBot.label("Mapped Superclass").isEnabled());
		assertTrue(jpaDetailsBot.label("Queries").isEnabled());
		
		//change the mapping type to "Embeddable" and assert that the element is removed from the diagram
		//and the type is mapped as embeddable in the JPA Details view
		editorProxy.clickOnStyledText(styledText, 30);
		editorProxy.changeMappingtype("Embeddable");
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, "Entity1"));
		styledText = jpaDetailsBot.styledText();
		assertEquals("Type 'Entity1' is mapped as embeddable.", styledText.getText());
		assertNull("Entity must disappear from the diagram!", jpaDiagramEditor.getEditPart("Entity1"));
		//assert that the embeddable's section is enabled
//		assertTrue(jpaDetailsBot.label("Embeddable").isEnabled());
		
		//revert the mapping type to Entity
		editorProxy.clickOnStyledText(styledText, 30);
		editorProxy.changeMappingtype("Entity");
		assertNull("Entity must disappear from the diagram!", jpaDiagramEditor.getEditPart("Entity1"));
		
		jpaDiagramEditor.click(0, 0);
		
		//call the "Show All Entities" diagram context menu to visualize the element in the diagram and
		//assert that the type is mapped as entity in the JPA Details view
		jpaDiagramEditor.clickContextMenu("Show All Entities");
		assertNotNull("Entity must be shown in the diagram!", jpaDiagramEditor.getEditPart("Entity1"));
		jpaDiagramEditor.select("Entity1");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Type 'Entity1' is mapped as entity.", styledText.getText());
		//assert that the entity's sections are enabled
		assertTrue(jpaDetailsBot.label("Entity").isEnabled());
		assertTrue(jpaDetailsBot.label("Queries").isEnabled());
		assertTrue(jpaDetailsBot.label("Inheritance").isEnabled());
		assertTrue(jpaDetailsBot.label("Attribute Overrides").isEnabled());
		assertTrue(jpaDetailsBot.label("Primary Key Generation").isEnabled());
		assertTrue(jpaDetailsBot.label("Secondary Tables").isEnabled());

		entity.click();
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testChangeEntityMappingTypeViaJPADetailsView");
	}
	
	/**
	 * Test the integration between the JPA diagram editor and the JPA Details view:
	 * 1.) Close the JPA Details view
	 * 2.) Create an entity and call the context menu "Open JPA Details View"
	 * 3.) Assert that the JPA Details view is opened and the appropriate information
	 * is shown (the id attribute is mapped as id and the expandable sections are available).
	 * 4.) Change the attribute mapping type to "Basic" and assert that the attribute is moved to "Other Attributes" compartment
	 * 5.) Change the attribute mapping type to "On-To-Many" and assert that the attribute is moved to "Relation Attributes" compartment
	 * 7.) Revert the attribute mapping to "ID" and assert that the attributes is moved back to the "Primary Key" compartment
	 */
	@Ignore
	@Test
	public void testChangeAttributeMappingTypeViaJPADetailsView(){
		Utils.sayTestStarted("testChangeAttributeMappingTypeViaJPADetailsView");
		
		workbenchBot.viewByTitle("JPA Details").close();
		SWTBotGefEditPart entity = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
			
		jpaDiagramEditor.save();	
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_openJPADetailsView);
		
		//assert that the JPA Details view is opened
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		assertTrue("JPA Details view must be opened!", jpaDetailsView.isActive());
		
		SWTBotGefEditPart primaryKeyAttr = jpaDiagramEditor.getEditPart("id");
		primaryKeyAttr.click();

		//assert that the default entity's attribute is mapped as primary key
		SWTBot jpaDetailsBot = jpaDetailsView.bot();
		SWTBotStyledText styledText = jpaDetailsBot.styledText();
		assertEquals("Attribute 'id' is mapped as ID.", styledText.getText());
		//assert that the attribute is under "Primary Key" section
		assertTrue("Attribute must be in the \"Primary Key\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));
		assertFalse("Attribute must be in the \"Primary Key\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse("Attribute must be in the \"Primary Key\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		//assert that the ID's sections are available
		assertTrue(jpaDetailsBot.label("ID").isEnabled());
		assertTrue(jpaDetailsBot.label("Type").isEnabled());
		assertTrue(jpaDetailsBot.label("Primary Key Generation").isEnabled());
		
		//change the attribute type to basic
		editorProxy.clickOnStyledText(styledText, 29);
		editorProxy.changeMappingtype("Basic");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Attribute 'id' is mapped as basic.", styledText.getText());
		//assert that the attribute is moved under "Other Attributes" section
		assertFalse("Attribute must be in the \"Primary Key\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));
		assertFalse("Attribute must be in the \"Relation Attributes\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertTrue("Attribute must be in the \"Other Attributes\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		//assert that the Basic's sections are available
		assertTrue(jpaDetailsBot.label("Basic").isEnabled());
		assertTrue(jpaDetailsBot.label("Type").isEnabled());
		
		//change the attribute type to Many to Many
		editorProxy.clickOnStyledText(styledText, 29);
		editorProxy.changeMappingtype("Many to Many");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Attribute 'id' is mapped as many to many.", styledText.getText());
		//assert that the attribute is moved under the "Relation Attributes" section
		assertFalse("Attribute must be in the \"Primary key\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));
		assertTrue("Attribute must be in the \"Relation Attributes\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse("Attribute must be in the \"Other Attributes\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		//assert that the Many to Many's sections are available
		assertTrue(jpaDetailsBot.label("Many to Many").isEnabled());
		assertTrue(jpaDetailsBot.label("Joining Strategy").isEnabled());
		assertTrue(jpaDetailsBot.label("Ordering").isEnabled());
		
		//revert the attribute type back to Id
		editorProxy.clickOnStyledText(styledText, 29);
		editorProxy.changeMappingtype("ID");
		styledText = jpaDetailsBot.styledText();
		assertEquals("Attribute 'id' is mapped as ID.", styledText.getText());
		//assert that the attribute is under "Primary Key" section
		assertTrue("Attribute must be in the \"Primary Key\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));
		assertFalse("Attribute must be in the \"Relation Attributes\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse("Attribute must be in the \"Other Attributes\" section of the entity!", editorProxy.isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		//assert that the ID's sections are available
		assertTrue(jpaDetailsBot.label("ID").isEnabled());
		assertTrue(jpaDetailsBot.label("Type").isEnabled());
		assertTrue(jpaDetailsBot.label("Primary Key Generation").isEnabled());

		entity.click();
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testChangeAttributeMappingTypeViaJPADetailsView");
	}
	
	/**
	 * Creates "One to One" unidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testOneToOneUniDirRelationship(){
		Utils.sayTestStarted("testOneToOneUniDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create One-to-One unidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));
		
		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_ONE, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertUniDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, entity2, connection, attributeName);
		
		editorProxy.assertUniDirRelationIsDeleted(jpaDiagramEditor, entity1, entity2,
				connection, attributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testOneToOneUniDirRelationship");
	}
	
	/**
	 * Creates a "One to One" unidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the owner relationship attributes and 
	 * assert that the connection does not exists anymore.
	 */
	@Test
	public void testOneToOneUniDirRelationshipRemoveOwnerAttribute(){
		Utils.sayTestStarted("testOneToOneUniDirRelationshipRemoveOwnerAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create One-to-One unidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_ONE, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttrPart = jpaDiagramEditor.getEditPart(attributeName);
		ownerAttrPart.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(attributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testOneToOneUniDirRelationshipRemoveOwnerAttribute");
	}
	
	/**
	 * Creates "One to One" unidirectional self relationship (from entity1 to entity1).
	 * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfOneToOneUniDirRelationship(){
		Utils.sayTestStarted("testSelfOneToOneUniDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		//create One-to-One unidirectional self relation from entity1 to entity1
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity1);
		bot.waitUntil(new ConnectionIsShown(entity1));
		
		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertSelfConnectionIsCreated(jpaDiagramEditor, entity1);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_ONE, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertSelfUniDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, connection, attributeName);
		
		editorProxy.assertSelfUniDirRelationIsDeleted(jpaDiagramEditor, entity1,
				connection, attributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testSelfOneToOneUniDirRelationship");
	}
	
	/**
	 * Creates "One to One" bidirectional relationship (from entity1 to entity2).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testOneToOneBiDirRelationship(){
		Utils.sayTestStarted("testOneToOneBiDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create One-to-One bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));
		
		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, true);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_ONE, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertBiDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, entity2, connection, ownerAttributeName,
				inverseAttributeName);
		
		editorProxy.assertBiDirRelationIsDeleted(jpaDiagramEditor, entity1, entity2,
				connection, ownerAttributeName, inverseAttributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testOneToOneBiDirRelationship");
	}
	
	/**
	 * Creates a "One to One" bidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the inverse relationship attribute and 
	 * assert that the relationship is converted from bidirectional to unidirectional "One to One"
	 * relationship. Delete also the owner attribute and assert that there is no connection anymore.
	 */
	@Test
	public void testOneToOneBiDirRelationshipRemoveInverseAttribute(){
		Utils.sayTestStarted("testOneToOneBiDirRelationshipRemoveInverseAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create One-to-One bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, true);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_ONE, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		//delete the inverse attribute
		SWTBotGefEditPart inverseAttr = jpaDiagramEditor.getEditPart(inverseAttributeName);
		inverseAttr.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		
		//assert that the connection still exists, but it is unidirectional now
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		connection = entity1.sourceConnections().get(0);
		rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_ONE, rel.getRelType());
		assertEquals(ownerAttributeName, editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel));
		assertNull(rel.getInverseAnnotatedAttribute());
		
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttr = jpaDiagramEditor.getEditPart(ownerAttributeName);
		ownerAttr.click();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testOneToOneBiDirRelationshipRemoveInverseAttribute");
	}
	
	/**
	 * Creates a "One to One" bidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the owner relationship attributes and 
	 * assert that the connection does not exists anymore. Assert that the inverse attribute still exists.
	 */
	@Test
	public void testOneToOneBiDirRelationshipRemoveOwnerAttribute(){
		Utils.sayTestStarted("testOneToOneBiDirRelationshipRemoveOwnerAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create One-to-One bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, true);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_ONE, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttrPart = jpaDiagramEditor.getEditPart(ownerAttributeName);
		ownerAttrPart.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		//assert that the inverse attribute still exists
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		//assert that the inverse attribute still exists
		assertTrue(editorProxy.isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testOneToOneBiDirRelationshipRemoveOwnerAttribute");
	}
	
	/**
	 * Creates "One to One" bidirectional self relationship (from entity1 to entity1).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfOneToOneBiDirRelationship(){
		Utils.sayTestStarted("testSelfOneToOneBiDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
				
		//create One-to-One bidirectional self relation from entity1 to entity1
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToOneBiDirRelationFeature_oneToOneBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity1);		
		bot.waitUntil(new ConnectionIsShown(entity1));
		
		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertSelfConnectionIsCreated(jpaDiagramEditor, entity1);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_ONE, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);

		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertSelfBiDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, connection, ownerAttributeName,
				inverseAttributeName);
		
		editorProxy.assertSelfBiDirRelationIsDeleted(jpaDiagramEditor, entity1,
				connection, ownerAttributeName, inverseAttributeName);
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testSelfOneToOneBiDirRelationship");
	}
	
	/**
	 * Creates "One to Many" unidirectional relationship (from entity1 to entity2).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testOneToManyUniDirRelationship(){
		Utils.sayTestStarted("testOneToManyUniDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create One-to-Many unidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);		
		bot.waitUntil(new ConnectionIsShown(entity1));
		
		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_MANY, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertUniDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, entity2, connection, attributeName);
		
		editorProxy.assertUniDirRelationIsDeleted(jpaDiagramEditor, entity1, entity2,
				connection, attributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testOneToManyUniDirRelationship");
	}
	
	/**
	 * Creates a "One to Many" unidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the owner relationship attributes and 
	 * assert that the connection does not exists anymore.
	 */
	@Test
	public void testOneToManyUniDirRelationshipRemoveOwnerAttribute(){
		Utils.sayTestStarted("testOneToManyUniDirRelationshipRemoveOwnerAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create One-to-One bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_MANY, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttrPart = jpaDiagramEditor.getEditPart(attributeName);
		ownerAttrPart.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(attributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testOneToManyUniDirRelationshipRemoveOwnerAttribute");
	}	

	/**
	 * Creates "One to Many" unidirectional self relationship (from entity1 to entity1).
	 * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfOneToManyUniDirRelationship(){
		Utils.sayTestStarted("testSelfOneToManyUniDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		//create One-to-Many unidirectional self relation from entity1 to entity1
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity1);
		bot.waitUntil(new ConnectionIsShown(entity1));
		
		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertSelfConnectionIsCreated(jpaDiagramEditor, entity1);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.ONE_TO_MANY, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertSelfUniDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, connection, attributeName);
		
		editorProxy.assertSelfUniDirRelationIsDeleted(jpaDiagramEditor, entity1,
				connection, attributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testSelfOneToManyUniDirRelationship");
	}
	
	/**
	 * Creates "Many to One" unidirectional relationship (from entity1 to entity2).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testManyToOneUniDirRelationship(){
		Utils.sayTestStarted("testManyToOneUniDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-One unidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_ONE, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertUniDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, entity2, connection, attributeName);
		
		editorProxy.assertUniDirRelationIsDeleted(jpaDiagramEditor, entity1, entity2,
				connection, attributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToOneUniDirRelationship");
	}
	
	/**
	 * Creates a "Many to One" unidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the owner relationship attributes and 
	 * assert that the connection does not exists anymore.
	 */
	@Test
	public void testManyToOneUniDirRelationshipRemoveOwnerAttribute(){
		Utils.sayTestStarted("testManyToOneUniDirRelationshipRemoveOwnerAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-One bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_ONE, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttrPart = jpaDiagramEditor.getEditPart(attributeName);
		ownerAttrPart.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(attributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToOneUniDirRelationshipRemoveOwnerAttribute");
	}
	
	/**
	 * Creates "Many to One" unidirectional self relationship (from entity1 to entity1).
	 * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfManyToOneUniDirRelationship(){
		Utils.sayTestStarted("testSelfManyToOneUniDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		//create Many-to-One unidirectional self relation from entity1 to entity1
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToOneUniDirRelationFeature_manyToOneUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity1);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertSelfConnectionIsCreated(jpaDiagramEditor, entity1);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_ONE, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertSelfUniDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, connection, attributeName);
		
		editorProxy.assertSelfUniDirRelationIsDeleted(jpaDiagramEditor, entity1,
				connection, attributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testSelfManyToOneUniDirRelationship");
	}
	
	/**
	 * Creates a "Many to One" bidirectional relationship (from entity1 to entity2).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testManyToOneBiDirRelationship(){
		Utils.sayTestStarted("testManyToOneBiDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-One bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, true);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_ONE, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		editorProxy.assertBiDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, entity2, connection, ownerAttributeName,
				inverseAttributeName);
		
		editorProxy.assertBiDirRelationIsDeleted(jpaDiagramEditor, entity1, entity2,
				connection, ownerAttributeName, inverseAttributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToOneBiDirRelationship");
	}
	
	/**
	 * Creates a "Many to One" bidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the inverse relationship attribute and 
	 * assert that the relationship is converted from bidirectional to unidirectional "Many to One"
	 * relationship. Delete als the owner attribute and assert that there is no connection anymore.
	 */
	@Test
	public void testManyToOneBiDirRelationshipRemoveInverseAttribute(){
		Utils.sayTestStarted("testManyToOneBiDirRelationshipRemoveInverseAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-One bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, true);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_ONE, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		//delete the inverse attribute
		SWTBotGefEditPart inverseAttr = jpaDiagramEditor.getEditPart(inverseAttributeName);
		inverseAttr.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		
		//assert that the connection still exists, but it is unidirectional now
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		connection = entity1.sourceConnections().get(0);
		rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_ONE, rel.getRelType());
		assertEquals(ownerAttributeName, editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel));
		assertNull(rel.getInverseAnnotatedAttribute());
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttr = jpaDiagramEditor.getEditPart(ownerAttributeName);
		ownerAttr.click();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToOneBiDirRelationshipRemoveInverseAttribute");
	}
	
	/**
	 * Creates a "Many to One" bidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the owner relationship attributes and 
	 * assert that the connection does not exists anymore. Assert that the inverse attribute still exists.
	 */
	@Test
	public void testManyToOneBiDirRelationshipRemoveOwnerAttribute(){
		Utils.sayTestStarted("testManyToOneBiDirRelationshipRemoveOwnerAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-One bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, true);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_ONE, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttrPart = jpaDiagramEditor.getEditPart(ownerAttributeName);
		ownerAttrPart.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		//assert that the inverse attribute still exists
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		//assert that the inverse attribute still exists
		assertTrue(editorProxy.isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToOneBiDirRelationshipRemoveOwnerAttribute");
	}
	
	/**
	 * Creates a "Many to One" bidirectional self relationship (from entity1 to entity1).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfManyToOneBiDirRelationship(){
		Utils.sayTestStarted("testSelfManyToOneBiDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		//create Many-to-Many bidirectional self relation from entity1 to entity1
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity1);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertSelfConnectionIsCreated(jpaDiagramEditor, entity1);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_ONE, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		editorProxy.assertSelfBiDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, connection, ownerAttributeName,
				inverseAttributeName);
		
		editorProxy.assertSelfBiDirRelationIsDeleted(jpaDiagramEditor, entity1,
				connection, ownerAttributeName, inverseAttributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testSelfManyToOneBiDirRelationship");
	}
	
	/**
	 * Creates "Many to Many" unidirectional relationship (from entity1 to entity2).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testManyToManyUniDirRelationship() {
		Utils.sayTestStarted("testManyToManyUniDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-Many unidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));
		
		editorProxy.waitASecond();
		
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_MANY, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertUniDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, entity2, connection, attributeName);
		
		editorProxy.assertUniDirRelationIsDeleted(jpaDiagramEditor, entity1, entity2,
				connection, attributeName);
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToManyUniDirRelationship");
	}
	
	/**
	 * Creates a "Many to Many" unidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the owner relationship attributes and 
	 * assert that the connection does not exists anymore.
	 */
	@Test
	public void testManyToManyUniDirRelationshipRemoveOwnerAttribute(){
		Utils.sayTestStarted("testManyToManyUniDirRelationshipRemoveOwnerAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-Many bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_MANY, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttrPart = jpaDiagramEditor.getEditPart(attributeName);
		ownerAttrPart.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(attributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToManyUniDirRelationshipRemoveOwnerAttribute");
	}
	
	/**
	 * Creates "Many to Many" unidirectional self relationship (from entity1 to entity1).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfManyToManyUniDirRelationship(){
		Utils.sayTestStarted("testSelfManyToManyUniDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		//create Many-to-Many unidirectional self relation from entity1 to entity1
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName, 0);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity1);
		bot.waitUntil(new ConnectionIsShown(entity1));
		
		editorProxy.waitASecond();
		
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertSelfConnectionIsCreated(jpaDiagramEditor, entity1);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_MANY, rel.getRelType());
		
		String attributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		assertNull(rel.getInverseAnnotatedAttribute());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, attributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		editorProxy.assertSelfUniDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, connection, attributeName);
		
		editorProxy.assertSelfUniDirRelationIsDeleted(jpaDiagramEditor, entity1,
				connection, attributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testSelfManyToManyUniDirRelationship");
	}
	
	/**
	 * Creates a "Many to Many" bidirectional relationship (from entity1 to entity2).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testManyToManyBiDirRelationship(){
		Utils.sayTestStarted("testManyToManyBiDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-Many bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, true);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_MANY, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		editorProxy.assertBiDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, entity2, connection, ownerAttributeName,
				inverseAttributeName);
		
		editorProxy.assertBiDirRelationIsDeleted(jpaDiagramEditor, entity1, entity2,
				connection, ownerAttributeName, inverseAttributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToManyBiDirRelationship");
	}
	
	/**
	 * Creates a "Many to Many" bidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the inverse relationship attribute and 
	 * assert that the relationship is converted from bidirectional to unidirectional "Many to Many"
	 * relationship. Delete also the owner attribute and assert that there is no connection anymore.
	 */
	@Test
	public void testManyToManyBiDirRelationshipRemoveInverseAttribute(){
		Utils.sayTestStarted("testManyToManyBiDirRelationshipRemoveInverseAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-Many bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, true);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_MANY, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		//delete the inverse attribute
		SWTBotGefEditPart inverseAttr = jpaDiagramEditor.getEditPart(inverseAttributeName);
		inverseAttr.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		
		//assert that the connection still exists, but it is unidirectional now
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, false);
		connection = entity1.sourceConnections().get(0);
		rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_MANY, rel.getRelType());
		
		assertEquals(ownerAttributeName, editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel));		
		assertNull(rel.getInverseAnnotatedAttribute());		
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttr = jpaDiagramEditor.getEditPart(ownerAttributeName);
		ownerAttr.click();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToManyBiDirRelationshipRemoveInverseAttribute");
	}
	
	/**
	 * Creates a "Many to Many" bidirectional relationship (from entity1 to entity2).
	 * Assert that the relation attributes exists. Delete the owner relationship attributes and 
	 * assert that the connection does not exists anymore. Assert that the inverse attribute still exists.
	 */
	@Test
	public void testManyToManyBiDirRelationshipRemoveOwnerAttribute(){
		Utils.sayTestStarted("testManyToManyBiDirRelationshipRemoveOwnerAttribute");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		SWTBotGefEditPart entity2 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 200, "Entity2");
		
		//create Many-to-Many bidirectional relation from entity1 to entity2
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity2);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertConnectionIsCreated(jpaDiagramEditor, entity1, entity2, true);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_MANY, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		//delete the owner attribute
		SWTBotGefEditPart ownerAttrPart = jpaDiagramEditor.getEditPart(ownerAttributeName);
		ownerAttrPart.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		editorProxy.confirmDelete();
		//assert that the connection does not exists anymore
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		//assert that the inverse attribute still exists
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		//assert that the inverse attribute still exists
		assertTrue(editorProxy.isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testManyToManyBiDirRelationshipRemoveOwnerAttribute");
	}
	
	/**
	 * Creates a "Many to Many" bidirectional self relationship (from entity1 to entity1).
	  * Assert that the relation attributes exists. Delete the relationship and 
	 * assert that the attributes do not exists anymore.
	 */
	@Test
	public void testSelfManyToManyBiDirRelationship(){
		Utils.sayTestStarted("testSelfManyToManyBiDirRelationship");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");
		
		//create Many-to-Many bidirectional self relation from entity1 to entity1
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName, 1);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(entity1);
		bot.waitUntil(new ConnectionIsShown(entity1));

		editorProxy.waitASecond();
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertSelfConnectionIsCreated(jpaDiagramEditor, entity1);
		
		SWTBotGefConnectionEditPart connection = entity1.sourceConnections().get(0);
		IRelation rel = editorProxy.getConnection(jpaDiagramEditor, connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(IRelation.RelType.MANY_TO_MANY, rel.getRelType());
		
		String ownerAttributeName = editorProxy.testOwnerRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		String inverseAttributeName = editorProxy.testInverseRelationAttributeProperties(
				jpaDiagramEditor, rel);
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();

		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, ownerAttributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		editorProxy.assertAttributeIsCorretlyMapped(fp, jpaDiagramEditor, inverseAttributeName, MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		editorProxy.assertSelfBiDirRelationIsNotDeleted(jpaDiagramEditor,
				entity1, connection, ownerAttributeName,
				inverseAttributeName);
		
		editorProxy.assertSelfBiDirRelationIsDeleted(jpaDiagramEditor, entity1,
				connection, ownerAttributeName, inverseAttributeName);

		editorProxy.deleteDiagramElements(jpaDiagramEditor);

		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testSelfManyToManyBiDirRelationship");
	}
	
	/**
	 * Creates a new Inherited entity by entity. Assert that the inherited entity
	 * does not contain a primary key.
	 */
	@Test
	public void testInheritedEntityByEntity(){
		Utils.sayTestFinished("testInheritedEntityByEntity");
		
		SWTBotGefEditPart entity1 = editorProxy.addEntityToDiagram(jpaDiagramEditor, 50, 50, "Entity1");

		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateJPAEntityFromMappedSuperclassFeature_createInheritedEntityFeatureName);		
		jpaDiagramEditor.click(entity1);
		jpaDiagramEditor.click(50, 200);

		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, "Entity2"), 30000);
		
		SWTBotGefEditPart inheritedEntity = jpaDiagramEditor.getEditPart("Entity2");
		assertNotNull(inheritedEntity);
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, inheritedEntity, JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, inheritedEntity, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, inheritedEntity, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertIsARelationExists(jpaDiagramEditor, entity1, inheritedEntity);
		
		SWTBotGefConnectionEditPart connection = entity1.targetConnections().get(0);
		FreeFormConnection conn = (FreeFormConnection) connection.part().getModel();
		assertTrue(IsARelation.isIsAConnection(conn));
		IsARelation rel = editorProxy.getIsARelationship(jpaDiagramEditor);
		assertNotNull(rel);
		
		assertEquals(editorProxy.getEntityForElement(jpaDiagramEditor, inheritedEntity), rel.getSubclass());
		assertEquals(editorProxy.getEntityForElement(jpaDiagramEditor, entity1), rel.getSuperclass());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		
		editorProxy.assertTypeIsCorretlyMapped(fp, jpaDiagramEditor, "Entity1", MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		editorProxy.assertTypeIsCorretlyMapped(fp, jpaDiagramEditor, "Entity2", MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testInheritedEntityByEntity");
	}
	
	/**
	 * Creates a new Inherited entity by mapped superclass. Assert that the inherited entity
	 * contains a primary key.
	 */
	@Test
	public void testInheritedEntityByMappedSuperclass(){
		Utils.sayTestStarted("testInheritedEntityByMappedSuperclass");
		
		SWTBotGefEditPart mappedSuperclass = editorProxy.addMappedSuperclassToDiagram(jpaDiagramEditor, 50, 50, "MpdSuprcls1");
		
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateJPAEntityFromMappedSuperclassFeature_createInheritedEntityFeatureName);		
		jpaDiagramEditor.click(mappedSuperclass);
		jpaDiagramEditor.click(50, 200);

		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, "Entity1"), 30000);
		
		SWTBotGefEditPart inheritedEntity = jpaDiagramEditor.getEditPart("Entity1");
		assertNotNull(inheritedEntity);
		assertTrue(editorProxy.isSectionVisible(jpaDiagramEditor, inheritedEntity, JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));		
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, inheritedEntity, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse(editorProxy.isSectionVisible(jpaDiagramEditor, inheritedEntity, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		
		jpaDiagramEditor.activateDefaultTool();
		
		editorProxy.assertIsARelationExists(jpaDiagramEditor, mappedSuperclass, inheritedEntity);
		
		SWTBotGefConnectionEditPart connection = mappedSuperclass.targetConnections().get(0);
		FreeFormConnection conn = (FreeFormConnection) connection.part().getModel();
		assertTrue(IsARelation.isIsAConnection(conn));
		IsARelation rel = editorProxy.getIsARelationship(jpaDiagramEditor);
		assertNotNull(rel);
		
		assertEquals(editorProxy.getEntityForElement(jpaDiagramEditor, inheritedEntity), rel.getSubclass());
		assertEquals(editorProxy.getEntityForElement(jpaDiagramEditor, mappedSuperclass), rel.getSuperclass());
		
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		
		editorProxy.assertTypeIsCorretlyMapped(fp, jpaDiagramEditor, "MpdSuprcls1", MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		editorProxy.assertTypeIsCorretlyMapped(fp, jpaDiagramEditor, "Entity1", MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		editorProxy.deleteDiagramElements(jpaDiagramEditor);
		
		jpaDiagramEditor.save();
		
		Utils.sayTestFinished("testInheritedEntityByMappedSuperclass");
	}

	/**
	 * Test that the JPA Diagram editor is opened when the context menu
	 * "JPA Tools -> Open Diagram" of the project is called.
	 */
	@Test
	public void testOpenDiagramOnProjectLevel(){
		Utils.sayTestStarted("testOpenDiagramOnProjectLevel");
		
		workbenchBot.closeAllEditors();
		//open JPA diagram editor on project level: JPA Tools -> Open Diagram
		SWTBotGefEditor diagramEditor = editorProxy.openDiagramOnJPAProjectNode(TEST_PROJECT);
		diagramEditor.close();
		
		Utils.sayTestFinished("testOpenDiagramOnProjectLevel");
	}
	
	/**
	 * Test that the JPA Diagram editor is opened when the context menu "Open Diagram"
	 * of the JPA content node is called.
	 */
	@Test
	public void testOpenDiagramOnJPAContentNodeLevel() {
		Utils.sayTestStarted("testOpenDiagramOnJPAContentNodeLevel");
		
		workbenchBot.closeAllEditors();
		//open JPA diagram editor on JPA content level: Open Diagram
		SWTBotGefEditor diagramEditor = editorProxy.openDiagramOnJPAContentNode(TEST_PROJECT);
		diagramEditor.close();
		
		Utils.sayTestFinished("testOpenDiagramOnJPAContentNodeLevel");
	}
}
