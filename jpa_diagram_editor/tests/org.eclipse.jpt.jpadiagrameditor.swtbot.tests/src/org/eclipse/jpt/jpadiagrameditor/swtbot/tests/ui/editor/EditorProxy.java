package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.ui.editor;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.asyncExec;
import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellIsActive;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.internal.contextbuttons.ContextButton;
import org.eclipse.graphiti.ui.internal.contextbuttons.ContextButtonPad;
import org.eclipse.graphiti.ui.internal.parts.DiagramEditPart;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IsARelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefConnectionEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.Position;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;

public class EditorProxy {
	
	private final SWTWorkbenchBot workbenchBot;
	protected SWTGefBot bot;


	/**
	 * Create proxy object.
	 *
	 * @param bot
	 */
	public EditorProxy(SWTWorkbenchBot workbenchBot, SWTGefBot bot) {
		this.workbenchBot = workbenchBot;
		this.bot = bot;
	}
	
	public SWTBotGefEditor openDiagramOnJPAContentNode(String name) {
		SWTBotTree projectTree = workbenchBot.viewByTitle("Project Explorer").bot().tree();
		projectTree.expandNode(name).expandNode("JPA Content").select();
		ContextMenuHelper.clickContextMenu(projectTree, "Open Diagram");
		
		workbenchBot.waitUntil(shellIsActive(JPAEditorMessages.OpenJpaDiagramActionDelegate_jpaSupportWarningTitle), 5000);
		SWTBotShell jpaSupportWarningDialog = workbenchBot.shell(JPAEditorMessages.OpenJpaDiagramActionDelegate_jpaSupportWarningTitle);
		getOkButton(jpaSupportWarningDialog).click();
		
		SWTBotGefEditor jpaDiagramEditor = bot.gefEditor(name);
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());
		
		List<SWTBotGefEditPart> entities = jpaDiagramEditor.mainEditPart().children();
		assertTrue("Editor must not contains any entities!", entities.isEmpty());
		
		return jpaDiagramEditor;
	}
	
	public SWTBotGefEditor openDiagramOnJPAProjectNode(String name) {
		SWTBotTree projectTree = workbenchBot.viewByTitle("Project Explorer").bot().tree();
		projectTree.expandNode(name).select();
		ContextMenuHelper.clickContextMenu(projectTree, "JPA Tools", "Open Diagram");
		
		workbenchBot.waitUntil(shellIsActive(JPAEditorMessages.OpenJpaDiagramActionDelegate_jpaSupportWarningTitle), 5000);
		SWTBotShell jpaSupportWarningDialog = workbenchBot.shell(JPAEditorMessages.OpenJpaDiagramActionDelegate_jpaSupportWarningTitle);
		getOkButton(jpaSupportWarningDialog).click();
		
		SWTBotGefEditor jpaDiagramEditor = bot.gefEditor(name);
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());
		return jpaDiagramEditor;
	}
	
	/**
	 * Gets the "Select Type" dialog that appears when the attribute's context menu
	 * "Refactor Attribute Type..." is selected
	 * @param jpaDiagramEditor
	 * @param attribute
	 * @return the "Select Type" dialog
	 */
	public SWTBotShell getSelectNewAttributeTypeDialog(final SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart attribute) {
		attribute.click();
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_refactorAttributeType);
		
		workbenchBot.waitUntil(shellIsActive(JPAEditorMessages.SelectTypeDialog_chooseAttributeTypeDialogWindowTitle), 5000);
		SWTBotShell changeTypeDialog = workbenchBot.shell(JPAEditorMessages.SelectTypeDialog_chooseAttributeTypeDialogWindowTitle);
		getNewTypeInputField(changeTypeDialog);
		return changeTypeDialog;
	}

	/**
	 * Gets the text input field of the "Select Type" dialog, which appears when
	 * the attribute's context menu "Refcator Attribute Type..." is selected
	 * @param changeTypeDialog - the "Select Type" dialog
	 * @return the text input field
	 */
	public SWTBotText getNewTypeInputField(SWTBotShell changeTypeDialog) {
		SWTBotText attributeType = changeTypeDialog.bot().textWithLabel(JPAEditorMessages.SelectTypeDialog_typeLabel);
		assertEquals("java.lang.String", attributeType.getText());
		assertTrue(getOkButton(changeTypeDialog).isEnabled());
		assertTrue(getCancelButton(changeTypeDialog).isEnabled());
		return attributeType;
	}

	/**
	 * Gets the current attribute type value
	 * @param jpaDiagramEditor
	 * @param attributeName
	 * @param fp
	 * @return the value of the attribute's type
	 */
	public String getAttributeType(SWTBotGefEditor jpaDiagramEditor, String attributeName, final IFeatureProvider fp) {
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditPart(attributeName);
		PictogramElement el = (PictogramElement) attribute.part().getModel();
		Object bo = fp.getBusinessObjectForPictogramElement(el);
		assertTrue("The selected element is not an attribute!", (bo instanceof JavaPersistentAttribute));
		String currentAttributeType = JPAEditorUtil.getAttributeTypeName((JavaPersistentAttribute)bo);
		return currentAttributeType;
	}
	
	/**
	 * Adds a new attribute to the entity and checks that:
	 * 1. The newly created attribute is selected
	 * 2. The "Other Attributes" section is visible
	 * @param jpaDiagramEditor
	 * @param attributeName - the name of the attribute
	 * @return the newly added attribute
	 */
	public SWTBotGefEditPart addAttributeToEntity(final SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart entity, String attributeName) {
		pressEntityContextButton(jpaDiagramEditor, entity, JPAEditorMessages.JPAEditorToolBehaviorProvider_createAttributeButtonlabel);
		
		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, attributeName), 30000);		
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditPart(attributeName);
		assertNotNull("Atrribute is not added.", attribute);
		
		assertTrue("The newly added attribute must be selected.", jpaDiagramEditor.selectedEditParts().size() == 1);
		assertTrue("The newly added attribute must be selected.", jpaDiagramEditor.selectedEditParts().contains(attribute));
		
		assertTrue("\"Other Attributes\" section must be visible!", isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		
		return attribute;
	}
	
	/**
	 * Checks whether a section with the specified name is visible
	 * @param diagramEditor
	 * @param sectionTitle - the name of the section
	 * @return true, if the section with the specified name is visible,
	 * false otherwise
	 */
	public boolean isSectionVisible(SWTBotGefEditor diagramEditor, String sectionTitle){
		SWTBotGefEditPart section = diagramEditor.getEditPart(sectionTitle);
		((PictogramElement)section.part().getModel()).isVisible();
		IFigure figure = ((GraphicalEditPart) section.part()).getFigure();
		return figure.isVisible();
	}
	
	/**
	 * Adds an entity to the diagram
	 * @param jpaDiagramEditor
	 * @param entityName - the name of the entity to be added
	 * @return the added entity
	 */
	public SWTBotGefEditPart addEntityToDiagram(final SWTBotGefEditor jpaDiagramEditor, int x, int y, String entityName) {
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateJPAEntityFeature_jpaEntityFeatureName);
		jpaDiagramEditor.doubleClick(x, y);
		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, entityName), 30000);
		
		List<SWTBotGefEditPart> entities = jpaDiagramEditor.mainEditPart().children();
		assertFalse("Editor must contains at least one entity!", entities.isEmpty());
		
		SWTBotGefEditPart entity = jpaDiagramEditor.getEditPart(entityName);
		assertNotNull("Entity is not added!", entity);
		
		SWTBotGefEditPart idAttribute = jpaDiagramEditor.getEditPart("id");
		assertNotNull("Entity must have a primary key attribute!", idAttribute);

		assertTrue("\"Primary Key\" section must be visible!", isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));
		assertFalse("\"Relation Attributes\" section must not be visible!", isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse("\"Other Attributes\" section must not be visible!", isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		
		return entity;
	}
	
	/**
	 * Adds mapped superclass  to the diagram
	 * @param jpaDiagramEditor
	 * @param entityName - the name of the mapped superclass to be added
	 * @return the added mapped superclass
	 */
	public SWTBotGefEditPart addMappedSuperclassToDiagram(final SWTBotGefEditor jpaDiagramEditor, int x, int y, String entityName) {
		jpaDiagramEditor.activateTool(JPAEditorMessages.CreateMappedSuperclassFeature_createMappedSuperclassFeatureName);
		jpaDiagramEditor.doubleClick(x, y);		
		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, entityName), 30000);
		
		List<SWTBotGefEditPart> entities = jpaDiagramEditor.mainEditPart().children();
		assertFalse("Editor must contains at least one mapped superclass!", entities.isEmpty());
		
		SWTBotGefEditPart mappedSuperclass = jpaDiagramEditor.getEditPart(entityName);
		assertNotNull("Mapped superclass is not added!", mappedSuperclass);
		
		SWTBotGefEditPart idAttribute = jpaDiagramEditor.getEditPart("id");
		assertNull("Mapped superclass must not have a primary key attribute!", idAttribute);

		assertFalse("\"Primary Key\" section must not be visible!", isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));
		assertFalse("\"Relation Attributes\" section must not be visible!", isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse("\"Other Attributes\" section must not be visible!", isSectionVisible(jpaDiagramEditor, JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		
		return mappedSuperclass;
	}
	

	
	/**
	 * Pressing the "Yes" button of the "Confirm Delete" question dialog.
	 */
	public void confirmDelete() {
		SWTBotShell shell = getDeleteEntityDialog();
		shell.bot().button("Yes").click();
	}
	
	/**
	 * Pressing the "No" button of the "Confirm Delete" question dialog.
	 */
	public void denyDelete() {
		SWTBotShell shell = getDeleteEntityDialog();
		shell.bot().button("No").click();
	}

	/**
	 * Gets the dialog that appears after the "Delete" context button/menu
	 * is pressed.
	 * @return the question dialog, asking whether to the delete the selected entity
	 */
	public SWTBotShell getDeleteEntityDialog() {
		workbenchBot.waitUntil(shellIsActive(JPAEditorMessages.DeleteFeature_deleteConfirm), 5000);
		SWTBotShell shell = workbenchBot.shell(JPAEditorMessages.DeleteFeature_deleteConfirm);
		return shell;
	}
	
	/**
	 * Gets the dialog that appears after the "Refactor Entity Class -> Rename..." context menu
	 * is pressed.
	 * @return the "Rename Compilation Unit" dialog
	 */
	public SWTBotShell getRenameEntityDialog() {
		workbenchBot.waitUntil(shellIsActive("Rename Compilation Unit"), 5000);
		SWTBotShell shell = workbenchBot.shell("Rename Compilation Unit");
		assertFalse(getFinishButton(shell).isEnabled());
		assertTrue(getCancelButton(shell).isEnabled());
		return shell;
	}
	
	/**
	 * Gets the dialog that appears after the "Refactor Entity Class -> Move..." context menu
	 * is pressed.
	 * @return the "Move" dialog
	 */
	public SWTBotShell getMoveEntityDialog() {
		workbenchBot.waitUntil(shellIsActive("Move"), 5000);
		SWTBotShell shell = workbenchBot.shell("Move");
		assertFalse(getOkButton(shell).isEnabled());
		assertTrue(getCancelButton(shell).isEnabled());
		return shell;
	}
	
	/**
	 * Gets the dialog that appears after the "Remove All Entities from Diagram -> ...and Save/Discard Changes" context menu
	 * is pressed. Press the OK button.
	 */
	public void confirmRemoveEntitiesFromDiagramDialog() {
		workbenchBot.waitUntil(shellIsActive(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAllEntitiesMenu), 5000);
		SWTBotShell shell = workbenchBot.shell(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAllEntitiesMenu);
		assertTrue("Ok button is disabled", getOkButton(shell).isEnabled());
		assertTrue(getCancelButton(shell).isEnabled());
		getOkButton(shell).click();
	}
	
	
	/**
	 * Deletes an entity with the specified name using the context button.
	 * @param jpaDiagramEditor
	 * @param entityName - the name of the entity to be deleted
	 */
	public void deleteDiagramElements(SWTBotGefEditor jpaDiagramEditor){
		
		jpaDiagramEditor.save();

		List<SWTBotGefEditPart> entitiesInDiagram = jpaDiagramEditor.mainEditPart().children();
		assertFalse("Diagram must contain at least one entity!", entitiesInDiagram.isEmpty());
		
		Iterator<SWTBotGefEditPart> iterator = entitiesInDiagram.iterator();
		while (iterator.hasNext()) {
			SWTBotGefEditPart editPart = iterator.next();
			assertNotNull(editPart);
			editPart.select();
			jpaDiagramEditor.clickContextMenu("Delete");
			confirmDelete();
		}

		entitiesInDiagram = jpaDiagramEditor.mainEditPart().children();
		assertTrue("Diagram must be empty!", entitiesInDiagram.isEmpty());
		assertTrue("Editor must be dirty!", jpaDiagramEditor.isDirty());
	}
	
	/**
	 * Press some of the entity's context buttons
	 * @param jpaDiagramEditor
	 * @param contextButtonName - the name of the button to be pressed
	 */
	public void pressEntityContextButton(SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart part, String contextButtonName){
		pressContextButton(jpaDiagramEditor, part, contextButtonName);
	}
	
	/**
	 * Press the "Delete Attribute" attribute's context button
	 * @param jpaDiagramEditor
	 */
	public void pressAttributeDeleteContextButton(SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart part){
		pressContextButton(jpaDiagramEditor, part, JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteAttributeButtonlabel);
	}

	/**
	 * Assert that the context button pad is shown, when the mouse is placed over the
	 * entity and press the the desired button
	 * @param jpaDiagramEditor
	 * @param contextButtonName - the name of the button to be pressed.
	 */
	@SuppressWarnings("restriction")
	private void pressContextButton(final SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart part, String contextButtonName) {
		jpaDiagramEditor.click(0, 0);
		jpaDiagramEditor.click(part);
		
		ContextButtonPad pad = findContextButtonPad(jpaDiagramEditor);
		assertNotNull(pad);
		for(final Object button : pad.getChildren()){
			if(((ContextButton)button).getEntry().getText().equals(contextButtonName)){
				asyncExec(new VoidResult() {
					public void run() {
						((ContextButton)button).doClick();
						
					}
				});
			}
		}
	}

	/**
	 * Place the mouse over the entity to show the context button pad.
	 * @param jpaDiagramEditor
	 */
	public void moveMouse(final SWTBotGefEditor jpaDiagramEditor, final int x, final int y) {
		syncExec(new VoidResult() {
			public void run() {
				Robot r;
				try {
					r = new Robot();
					Point p = getOrigin(jpaDiagramEditor);
					r.mouseMove(p.x + x, p.y + y);
				} catch (AWTException e) {
					fail(e.getMessage());
				}
			}
		});
	}

	/**
	 * Gets the context button pad, after placing the mouse over the entity
	 * @param editor
	 * @return the entity's context button pad
	 */
	@SuppressWarnings("restriction")
	private ContextButtonPad findContextButtonPad(SWTBotGefEditor editor) {
		SWTBotGefEditPart rootEditPart = editor.rootEditPart();
		IFigure feedbackLayer = ((ScalableFreeformRootEditPart) rootEditPart.part())
				.getLayer(LayerConstants.HANDLE_LAYER);
		ContextButtonPad cbp = null;
		for (Object obj : feedbackLayer.getChildren()) {
			if (obj instanceof ContextButtonPad) {
				cbp = (ContextButtonPad) obj;
				break;
			}
		}
		return cbp;
	}

	
	private FigureCanvas getCanvas(SWTBotGefEditor editorGef) {
		IEditorReference reference = editorGef.getReference();
		final IEditorPart editor = reference.getEditor(true);
		GraphicalViewer graphicalViewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
		final Control control = graphicalViewer.getControl();
		if (control instanceof FigureCanvas) {
			FigureCanvas c = (FigureCanvas) control;
			return c;
		}
		return null;
	}
	
	private Point getOrigin(SWTBotGefEditor editorGef) {
		Canvas c = getCanvas(editorGef);
		Point p = c.toDisplay(0, 0);
		return p;
	}
	
	/**
	 * Get the error message that appears in the "Select Type" dialog
	 * @param dialog
	 * @return the error message
	 */
	public SWTBotText getDialogErroMessage(SWTBotShell dialog){
		return dialog.bot().text(1);
	}
	
	/**
	 * Gets the "OK" button of a dialog
	 * @param dialog
	 * @return the "OK" button
	 */
	public SWTBotButton getOkButton(SWTBotShell dialog){
		return dialog.bot().button(IDialogConstants.OK_LABEL);
	}
	
	/**
	 * Gets the "Cancel" button of a dialog
	 * @param dialog
	 * @return the "Cancel" button
	 */
	public SWTBotButton getCancelButton(SWTBotShell dialog){
		return dialog.bot().button(IDialogConstants.CANCEL_LABEL);
	}
	
	/**
	 * Gets the "Finish" button of a dialog
	 * @param dialog
	 * @return the "Finish" button
	 */
	public SWTBotButton getFinishButton(SWTBotShell dialog) {
		return dialog.bot().button("Finish");
	}
	
	/**
	 * Find the IRelation object for the given GEF Connection
	 * @param jpaDiagramEditor
	 * @param gefConn
	 * @return the IRelation object for the given GEF Connection
	 */
	@SuppressWarnings("restriction")
	public IRelation getConnection(SWTBotGefEditor jpaDiagramEditor, SWTBotGefConnectionEditPart gefConn){
		 IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		 FreeFormConnection conn = (FreeFormConnection) gefConn.part().getModel();
		 Object ob = fp.getBusinessObjectForPictogramElement(conn);
		 if(ob instanceof IRelation){
			 return (IRelation)ob;
		 }
		 
		 return null;
	}
	
	/**
	 * Gets the business object (JavaPersistentType) for the given GEF element
	 * @param jpaDiagramEditor
	 * @param element
	 * @return the java persistent type for the given element, null if the selected element is not an entity
	 */
	@SuppressWarnings("restriction")
	public JavaPersistentType getEntityForElement(SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart element){
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		PictogramElement el = (PictogramElement) element.part().getModel();
		Object bo = fp.getBusinessObjectForPictogramElement(el);
		if (bo instanceof JavaPersistentType){
			return (JavaPersistentType)bo;
		}
		return null;
	}
	
	/**
	 * Gets the existing isARelation
	 * @param jpaDiagramEditor
	 * @return the existing isArelation if exists, null otherwise
	 */
	@SuppressWarnings("restriction")
	public IsARelation getIsARelationship(SWTBotGefEditor jpaDiagramEditor){
		IJPAEditorFeatureProvider fp = (IJPAEditorFeatureProvider) ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		Set<IsARelation> isARelationships = fp.getAllExistingIsARelations();
		assertFalse(isARelationships.isEmpty());
		assertEquals(1, isARelationships.size());
		IsARelation relation = isARelationships.iterator().next();
		return relation;
	}
	
	/**
	 * CHecks whether the Entity contains unsaved changes.
	 * @param jpaDiagramEditor
	 * @param element
	 * @return true if the entity contains unsaved changes, false otherwise
	 */
	@SuppressWarnings("restriction")
	public boolean isEntityDirty(SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart element){
		final IFeatureProvider fp = ((DiagramEditPart)jpaDiagramEditor.mainEditPart().part()).getFeatureProvider();
		PictogramElement el = (PictogramElement) element.part().getModel();
		Object bo = fp.getBusinessObjectForPictogramElement(el);
		IResource res = null;
		if(bo instanceof JavaPersistentAttribute){
			res = ((JavaPersistentAttribute)bo).getResource();
		} else if (bo instanceof JavaPersistentType){
			res = ((JavaPersistentType)bo).getResource();
		}
		
		if(res != null){
			ICompilationUnit unit = JPAEditorUtil.getCompilationUnit((IFile)res);
			try {
				return unit.hasUnsavedChanges();
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	/**
	 * Select the bidirectional relation and call its "Delete" context menu.
	 * On the confirmation dialog press "No" and assert that
	 * the connection and the relative relation attributes still exist
	 * and the "Relation Attributes" sections of the entities' are visible.
	 * @param jpaDiagramEditor
	 * @param entity1
	 * @param entity2
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertBiDirRelationIsNotDeleted(
			SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart entity1,
			SWTBotGefEditPart entity2,
			SWTBotGefConnectionEditPart connection,
			String ownerAttributeName, String inverseAttributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		denyDelete();
		assertFalse(entity1.sourceConnections().isEmpty());
		assertFalse(entity2.targetConnections().isEmpty());
		connection = entity1.sourceConnections().get(0);
		assertNotNull("Attribute must not be deleted!", connection);
		assertNotNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertTrue("\"Relation Attributes\" section of the owner entity must be visible!", isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertTrue("\"Relation Attributes\" section of the inverse entity must be visible!", isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}
	
	/**
	 * Select the bidirectional self relation and call its "Delete" context menu.
	 * On the confirmation dialog press "No" and assert that
	 * the connection and the relative relation attributes still exist
	 * and the "Relation Attributes" sections of the entities' are visible.
	 * @param jpaDiagramEditor
	 * @param entity1
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertSelfBiDirRelationIsNotDeleted(
			SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart entity1,
			SWTBotGefConnectionEditPart connection,
			String ownerAttributeName, String inverseAttributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		denyDelete();
		assertFalse(entity1.sourceConnections().isEmpty());
		assertFalse(entity1.targetConnections().isEmpty());
		connection = entity1.sourceConnections().get(0);
		assertNotNull("Attribute must not be deleted!", connection);
		assertNotNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertTrue("\"Relation Attributes\" section of the owner entity must be visible!", isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}
	
	/**
	 * Select the unidirectional relation and call its "Delete" context menu.
	 * On the confirmation dialog press "No" and assert that
	 * the connection and the relative relation attributes still exist
	 * and the "Relation Attributes" sections of the entities' are visible.
	 * @param jpaDiagramEditor
	 * @param entity1
	 * @param entity2
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertUniDirRelationIsNotDeleted(
			SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart entity1,
			SWTBotGefEditPart entity2,
			SWTBotGefConnectionEditPart connection, String attributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		denyDelete();
		assertFalse(entity1.sourceConnections().isEmpty());
		assertFalse(entity2.targetConnections().isEmpty());
		connection = entity1.sourceConnections().get(0);
		assertNotNull("Attribute must not be deleted!", connection);
		assertNotNull(jpaDiagramEditor.getEditPart(attributeName));
		assertTrue("\"Relation Attributes\" section of the owner entity must be visible!",isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse("\"Relation Attributes\" section of the inverse entity must not be visible!", isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}
	
	/**
	 * Select the unidirectional self relation and call its "Delete" context menu.
	 * On the confirmation dialog press "No" and assert that
	 * the connection and the relative relation attributes still exist
	 * and the "Relation Attributes" sections of the entities' are visible.
	 * @param jpaDiagramEditor
	 * @param entity1
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertSelfUniDirRelationIsNotDeleted(
			SWTBotGefEditor jpaDiagramEditor, SWTBotGefEditPart entity1,
			SWTBotGefConnectionEditPart connection, String attributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		denyDelete();
		assertFalse(entity1.sourceConnections().isEmpty());
		assertFalse(entity1.targetConnections().isEmpty());
		connection = entity1.sourceConnections().get(0);
		assertNotNull("Attribute must not be deleted!", connection);
		assertNotNull(jpaDiagramEditor.getEditPart(attributeName));
		assertTrue("\"Relation Attributes\" section of the owner entity must be visible!", isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}

	/**
	 * Select the bidirectional relation and call its "Delete" context menu.
	 * On the confirmation dialog press "Yes" and assert that
	 * the connection and the relative relation attributes do not exist anymore
	 * and the "Relation Attributes" sections of the entities' are not visible.
	 * @param jpaDiagramEditor
	 * @param entity1
	 * @param entity2
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertBiDirRelationIsDeleted(SWTBotGefEditor jpaDiagramEditor,
			SWTBotGefEditPart entity1, SWTBotGefEditPart entity2,
			SWTBotGefConnectionEditPart connection,
			String ownerAttributeName, String inverseAttributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, ownerAttributeName));
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse("\"Relation Attributes\" section of the owner entity must not be visible!", isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse("\"Relation Attributes\" section of the inverse entity must not be visible!", isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}
	
	/**
	 * Select the bidirectional self relation and call its "Delete" context menu.
	 * On the confirmation dialog press "Yes" and assert that
	 * the connection and the relative relation attributes do not exist anymore
	 * and the "Relation Attributes" sections of the entities' are not visible.
	 * @param jpaDiagramEditor
	 * @param entity1
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertSelfBiDirRelationIsDeleted(SWTBotGefEditor jpaDiagramEditor,
			SWTBotGefEditPart entity1,
			SWTBotGefConnectionEditPart connection,
			String ownerAttributeName, String inverseAttributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, ownerAttributeName));
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity1.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse("\"Relation Attributes\" section of the owner entity must not be visible!", isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}
	
	/**
	 * Select the unidirectional relation and call its "Delete" context menu.
	 * On the confirmation dialog press "Yes" and assert that
	 * the connection and the relative relation attributes do not exist anymore
	 * and the "Relation Attributes" sections of the entities' are not visible.
	 * @param jpaDiagramEditor
	 * @param entity1
	 * @param entity2
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertUniDirRelationIsDeleted(SWTBotGefEditor jpaDiagramEditor,
			SWTBotGefEditPart entity1, SWTBotGefEditPart entity2,
			SWTBotGefConnectionEditPart connection, String attributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, attributeName));
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(attributeName));
		assertFalse("\"Relation Attributes\" section of the owner entity must not be visible!", isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse("\"Relation Attributes\" section of the inverse entity must not be visible!", isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}
	
	/**
	 * Select the unidirectional self relation and call its "Delete" context menu.
	 * On the confirmation dialog press "Yes" and assert that
	 * the connection and the relative relation attributes do not exist anymore
	 * and the "Relation Attributes" sections of the entities' are not visible.
	 * @param jpaDiagramEditor
	 * @param entity1
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertSelfUniDirRelationIsDeleted(SWTBotGefEditor jpaDiagramEditor,
			SWTBotGefEditPart entity1,
			SWTBotGefConnectionEditPart connection, String attributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, attributeName));
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity1.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(attributeName));
		assertFalse("\"Relation Attributes\" section of the owner entity must not be visible!", isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}
	
	/**
	 * Assert that there is exactly one GEF element representing the relationship 
	 * @param jpaDiagramEditor
	 * @param entity1
	 * @param entity2
	 */
	public void assertConnectionIsCreated(SWTBotGefEditor jpaDiagramEditor,
			SWTBotGefEditPart entity1, SWTBotGefEditPart entity2, boolean isBiDIr) {
		//assert that there is exactly one relationship, which start from entity1
		//and that there is no relationship which starts from entity2
		assertFalse(entity1.sourceConnections().isEmpty());
		assertEquals(1, entity1.sourceConnections().size());
		assertTrue(entity2.sourceConnections().isEmpty());
		
		//assert that there is exactly one relationship which ends in entity2
		//and that there is no relationship which end in entity1.
		assertFalse(entity2.targetConnections().isEmpty());
		assertEquals(1, entity2.targetConnections().size());
		assertTrue(entity1.targetConnections().isEmpty());
		
		assertTrue("\"Relation Attributes\" section of the owner entity must be visible!", isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		if(isBiDIr){
			assertTrue("\"Relation Attributes\" section of the inverse entity must be visible!", isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		} else {
			assertFalse("\"Relation Attributes\" section of the inverse entity must not be visible!", isSectionVisible(jpaDiagramEditor, entity2, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		}
	}
	
	public void assertIsARelationExists(SWTBotGefEditor jpaDiagramEditor,
			SWTBotGefEditPart entity1, SWTBotGefEditPart entity2) {
		// assert that there is exactly one relationship, which start from
		// entity2 and that there is no relationship which starts from entity2
		assertFalse(entity2.sourceConnections().isEmpty());
		assertEquals(1, entity2.sourceConnections().size());
		assertTrue(entity1.sourceConnections().isEmpty());

		// assert that there is exactly one relationship which ends in entity1
		// and that there is no relationship which end in entity2.
		assertFalse(entity1.targetConnections().isEmpty());
		assertEquals(1, entity1.targetConnections().size());
		assertTrue(entity2.targetConnections().isEmpty());
	}

	/**
	 * Assert that there is exactly one GEF element representing the self relationship 
	 * @param jpaDiagramEditor
	 * @param entity1
	 */
	public void assertSelfConnectionIsCreated(SWTBotGefEditor jpaDiagramEditor,
			SWTBotGefEditPart entity1) {
		//assert that there is exactly one relationship, which start from entity1
		//and ends in entity2
		assertFalse(entity1.sourceConnections().isEmpty());
		assertEquals(1, entity1.sourceConnections().size());

		assertFalse(entity1.targetConnections().isEmpty());
		assertEquals(1, entity1.targetConnections().size());
		
		assertTrue(isSectionVisible(jpaDiagramEditor, entity1, JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}
	
	/**
	 * Assert that the owner relationship attribute exists
	 * @param jpaDiagramEditor
	 * @param rel
	 * @return the name of the owner relationship attribute
	 */
	public String testOwnerRelationAttributeProperties(
			SWTBotGefEditor jpaDiagramEditor, IRelation rel) {
		JavaPersistentAttribute ownerAttr = rel.getOwnerAnnotatedAttribute();
		String attributeName = rel.getOwnerAttributeName();
		assertNotNull(ownerAttr);
		assertNotNull(jpaDiagramEditor.getEditPart(attributeName));
		
		return attributeName;
	}
	
	/**
	 * Assert that the inverse relationship attribute exists.
	 * @param jpaDiagramEditor
	 * @param rel
	 * @return the name of the inverse relationship attribute
	 */
	public String testInverseRelationAttributeProperties(
			SWTBotGefEditor jpaDiagramEditor, IRelation rel) {
		JavaPersistentAttribute inverseAttr = rel.getInverseAnnotatedAttribute();
		String inverseAttributeName = rel.getInverseAttributeName();
		assertNotNull(inverseAttr);
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		return inverseAttributeName;
	}
	
	
	/**
	 * Checks whether a section of a particular entity is visible
	 * @param diagramEditor
	 * @param editPart - the particular entity
	 * @param sectionTitle - the title of the section to be checked
	 * @return true,  if the sections is visible, false otherwise
	 */
	@SuppressWarnings("deprecation")
	public boolean isSectionVisible(SWTBotGefEditor diagramEditor, SWTBotGefEditPart editPart,  String sectionTitle){
		List<SWTBotGefEditPart> children = editPart.children();
		SWTBotGefEditPart section = diagramEditor.getEditpart(sectionTitle, children);
		((PictogramElement)section.part().getModel()).isVisible();
		IFigure figure = ((GraphicalEditPart) section.part()).getFigure();
		return figure.isVisible();
	}

	/**
	 * Change the mapping of the type or attribute.
	 * @param newMappingType
	 */
	public void changeMappingtype(String newMappingType) {
		workbenchBot.waitUntil(shellIsActive("Mapping Type Selection"), 5000);
		SWTBotShell mappingTypeShell = workbenchBot.shell("Mapping Type Selection");
		mappingTypeShell.bot().table().getTableItem(newMappingType).select();		
		getOkButton(mappingTypeShell).click();
	}

	/**
	 * Click on the mapping type link in the JPA Details view
	 * @param styledText
	 * @param position
	 */
	public void clickOnStyledText(final SWTBotStyledText styledText, int position) {
		styledText.navigateTo(new Position(0, position));		
		asyncExec(new VoidResult() {
			public void run() {
				styledText.widget.notifyListeners(SWT.MouseDown, new Event());
				styledText.widget.notifyListeners(SWT.MouseUp, new Event());
			}
		});
	}

	public void waitASecond() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Assert that the relation attribute is correctly mapped in the JPA Details view
	 * @param jpaDiagramEditor
	 * @param attributeName
	 * @param relationAttributeMapping - the expected attribute mapping
	 */
	public void assertAttributeIsCorretlyMapped(
			SWTBotGefEditor jpaDiagramEditor, String attributeName,  String relationAttributeMapping) {

		//assert that the JPA Details view is opened
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		jpaDetailsView.setFocus();
		assertTrue("JPA Details view must be opened!", jpaDetailsView.isActive());
		
		SWTBotGefEditPart oneToManyAttr = jpaDiagramEditor.getEditPart(attributeName);
		oneToManyAttr.click();

		//assert that the default entity's attribute is mapped as primary key
		SWTBot jpaDetailsBot = jpaDetailsView.bot();
		SWTBotStyledText styledText = jpaDetailsBot.styledText();
		assertEquals("Attribute '" +attributeName+ "' is mapped as " +relationAttributeMapping+ ".", styledText.getText());
	}
	
	/**
	 * Assert that the type is correctly mapped in the JPA Details view
	 * @param jpaDiagramEditor
	 * @param typeName
	 * @param typeMapping - the expected type mapping
	 */
	public void assertTypeIsCorretlyMapped(
			SWTBotGefEditor jpaDiagramEditor, String typeName,  String typeMapping) {
		workbenchBot.viewByTitle("JPA Details").close();
		jpaDiagramEditor.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_openJPADetailsView);
		//assert that the JPA Details view is opened
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		assertTrue("JPA Details view must be opened!", jpaDetailsView.isActive());
		
		SWTBotGefEditPart type = jpaDiagramEditor.getEditPart(typeName);
		type.click();

		//assert that the default entity's attribute is mapped as the given mapping key
		SWTBot jpaDetailsBot = jpaDetailsView.bot();
		SWTBotStyledText styledText = jpaDetailsBot.styledText();
		assertEquals("Type '" +typeName+ "' is mapped as " +typeMapping+ ".", styledText.getText());
	}
}
