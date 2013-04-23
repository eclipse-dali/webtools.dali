package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.internal.contextbuttons.ContextButton;
import org.eclipse.graphiti.ui.internal.contextbuttons.ContextButtonPad;
import org.eclipse.graphiti.ui.internal.parts.DiagramEditPart;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandContext;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions.ConnectionIsShown;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions.DiagramIsEmpty;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions.ElementAppearsInDiagram;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions.ElementDisappears;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions.ElementIsShown;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal.JPACreateFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation.HasReferenceType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IsARelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
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
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.Position;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;

@SuppressWarnings({ "restriction", "deprecation", "unchecked" })
public class EditorProxy {

	private final SWTWorkbenchBot workbenchBot;
	protected SWTGefBot bot;

	private SWTBotGefEditor jpaDiagramEditor;
	private JpaArtifactFactory jpaFactory;
	private OrmXml ormXml;

	/**
	 * Create proxy object.
	 * 
	 * @param bot
	 */
	public EditorProxy(SWTWorkbenchBot workbenchBot, SWTGefBot bot) {
		this.workbenchBot = workbenchBot;
		this.bot = bot;
		jpaFactory = JpaArtifactFactory.instance();
	}

	public SWTBotGefEditor openDiagramOnJPAContentNode(String name) {
		SWTBotTree projectTree = workbenchBot.viewByTitle("Project Explorer")
				.bot().tree();
		SWTBotTreeItem item = projectTree.expandNode(name)
				.expandNode("JPA Content").select();
		assertTrue("The JPA Content node is disabled.", item.isEnabled());
		ContextMenuHelper.clickContextMenu(projectTree, "Open Diagram");

		SWTBotGefEditor jpaDiagramEditor = bot.gefEditor(name);
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());

		List<SWTBotGefEditPart> entities = jpaDiagramEditor.mainEditPart()
				.children();
		assertTrue("Editor must not contains any entities!", entities.isEmpty());

		return jpaDiagramEditor;
	}

	public SWTBotGefEditor openDiagramOnJPAProjectNode(String name) {
		Utils.waitBuildAndRerfreshJobs();
		try {
			Utils.waitNonSystemJobs();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		SWTBotTree projectTree = workbenchBot.viewByTitle("Project Explorer")
				.bot().tree();
		projectTree.getTreeItem(name).select();
		ContextMenuHelper.clickContextMenu(projectTree, "JPA Tools",
				"Open Diagram");

		waitASecond();
		SWTBotGefEditor jpaDiagramEditor = bot.gefEditor(name);
		assertNotNull(jpaDiagramEditor);
		assertFalse("Editor must not be dirty!", jpaDiagramEditor.isDirty());
		return jpaDiagramEditor;
	}

	/**
	 * Gets the "Select Type" dialog that appears when the attribute's context
	 * menu "Refactor Attribute Type..." is selected
	 * 
	 * @param attribute
	 * @return the "Select Type" dialog
	 */
	public SWTBotShell getSelectNewAttributeTypeDialog(
			SWTBotGefEditPart attribute) {
		attribute.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_refactorAttributeType);

		workbenchBot
				.waitUntil(
						shellIsActive(JPAEditorMessages.SelectTypeDialog_chooseAttributeTypeDialogWindowTitle),
						10000);
		SWTBotShell changeTypeDialog = workbenchBot
				.shell(JPAEditorMessages.SelectTypeDialog_chooseAttributeTypeDialogWindowTitle);
		getNewTypeInputField(changeTypeDialog);
		return changeTypeDialog;
	}

	/**
	 * Gets the text input field of the "Select Type" dialog, which appears when
	 * the attribute's context menu "Refcator Attribute Type..." is selected
	 * 
	 * @param changeTypeDialog
	 *            - the "Select Type" dialog
	 * @return the text input field
	 */
	public SWTBotText getNewTypeInputField(SWTBotShell changeTypeDialog) {
		SWTBotText attributeType = changeTypeDialog.bot().textWithLabel(
				JPAEditorMessages.SelectTypeDialog_typeLabel);
		assertEquals("java.lang.String", attributeType.getText());
		assertTrue(getOkButton(changeTypeDialog).isEnabled());
		assertTrue(getCancelButton(changeTypeDialog).isEnabled());
		return attributeType;
	}

	/**
	 * Gets the current attribute type value
	 * 
	 * @param attributeName
	 * @param fp
	 * @return the value of the attribute's type
	 */
	public String getAttributeType(String attributeName,
			final IFeatureProvider fp) {
		SWTBotGefEditPart attribute = jpaDiagramEditor
				.getEditPart(attributeName);
		PictogramElement el = (PictogramElement) attribute.part().getModel();
		Object bo = fp.getBusinessObjectForPictogramElement(el);
		assertTrue("The selected element is not an attribute!",
				(bo instanceof PersistentAttribute));
		String currentAttributeType = JPAEditorUtil
				.getAttributeTypeName((PersistentAttribute) bo);
		return currentAttributeType;
	}

	/**
	 * Adds a new attribute to the entity and checks that the "Other Attributes" section is visible
	 * 
	 * @param attributeName
	 *            - the name of the attribute
	 * @return the newly added attribute
	 */
	public SWTBotGefEditPart addAttributeToJPT(SWTBotGefEditPart jptType,
			String attributeName, boolean isOrmXml) {

		PersistentType jpt = getJPTObjectForGefElement(jptType);

		System.out.println(">>>>>> Attribute is trying to be added in "
				+ jpt.getName());

		pressEntityContextButton(
				jptType,
				JPAEditorMessages.JPAEditorToolBehaviorProvider_createAttributeButtonlabel);

		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, attributeName),
				10000);
		List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
		editParts.add(jptType);
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditpart(
				attributeName, editParts);
		assertNotNull("Atrribute is not added.", attribute);

		System.out.println(">>>>>> Attribute is successfully added in "
				+ jpt.getName());

		assertTrue(
				"\"Other Attributes\" section must be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
						jptType));
		
		PersistentAttribute jpa = getJPAObjectForGefElement(attribute);
		assertNotNull(jpa);
		if(isOrmXml) {
			assertNotNull(jpaFactory.getORMPersistentAttribute(jpa));
		}
		
		assertEquals(
				"The newly added attribute must be mapped as basic attribute.",
				MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY,
				jpaFactory.getAttributeMapping(jpa).getKey());

		return attribute;
	}

	public SWTBotGefEditPart addElementCollectionAttributeToJPT(
			SWTBotGefEditPart jptType, String attributeName) {
		pressEntityContextButton(
				jptType,
				JPAEditorMessages.JPAEditorToolBehaviorProvider_CreateElementCollectionAttributeButtonLabel);

		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, attributeName),
				10000);
		List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
		editParts.add(jptType);
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditpart(
				attributeName, editParts);
		assertNotNull("Atrribute is not added.", attribute);

		assertTrue(
				"\"Other Attributes\" section must be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
						jptType));

		PersistentAttribute jpa = getJPAObjectForGefElement(attribute);
		assertEquals(
				"The newly added attribute must be mapped as element-collection.",
				MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
				jpaFactory.getAttributeMapping(jpa).getKey());

		return attribute;
	}

	/**
	 * Checks whether a section with the specified name is visible
	 * 
	 * @param sectionTitle
	 *            - the name of the section
	 * @return true, if the section with the specified name is visible, false
	 *         otherwise
	 */
	public boolean isSectionVisible(String sectionTitle,
			SWTBotGefEditPart editPart) {
		List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
		editParts.add(editPart);
		SWTBotGefEditPart section = jpaDiagramEditor.getEditpart(sectionTitle,
				editParts);
		((PictogramElement) section.part().getModel()).isVisible();
		IFigure figure = ((GraphicalEditPart) section.part()).getFigure();
		return figure.isVisible();
	}

	/**
	 * Add entity to diagram
	 * 
	 * @param x
	 * @param y
	 * @param jpaProject
	 * @return the newly added entity
	 */
	public SWTBotGefEditPart addEntityToDiagram(int x, int y,
			JpaProject jpaProject) {
		
		SWTBotGefEditPart entity = addEnt(x, y, jpaProject);

		assertNotNull("Entity is not added!", entity);

		SWTBotGefEditPart idAttribute = jpaDiagramEditor.getEditPart("id");
		assertNotNull("Entity must have a primary key attribute!", idAttribute);

		assertTrue(
				"\"Primary Key\" section must be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape,
						entity));
		assertFalse(
				"\"Relation Attributes\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes,
						entity));

		return entity;
	}

	private SWTBotGefEditPart addEnt(int x, int y, JpaProject jpaProject) {
		
		String entityName = getUniqueEntityName(jpaProject);

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateJPAEntityFeature_jpaEntityFeatureName);
		jpaDiagramEditor.doubleClick(x, y);
		
		SWTBotGefEditPart entity = null;

		try {
			bot.waitUntil(new ElementIsShown(jpaDiagramEditor, entityName));
			entity = jpaDiagramEditor.getEditPart(entityName);
		} catch (TimeoutException e) {
			
			if(!jpaDiagramEditor.selectedEditParts().isEmpty()) {
				assertEquals(1, jpaDiagramEditor.selectedEditParts().size());
			
				PersistentType pers = getJPTObjectForGefElement(jpaDiagramEditor.selectedEditParts().get(0));
				assertNotNull(pers);
				assertEquals(entityName, pers.getSimpleName());
			
				jpaDiagramEditor.save();
			
				entity = jpaDiagramEditor.selectedEditParts().get(0);
			
				deleteJPTViaButton(entity, false);
				bot.waitUntil(new ElementDisappears(jpaDiagramEditor, entityName));
						
				entity = addEnt(x, y, jpaProject);
			} else {
				addEnt(x, y, jpaProject);
			}
		}
		return entity;
	}
	
	
	private SWTBotGefEditPart addEmb(int x, int y, JpaProject jpaProject) {
		
		String embeddableName = getUniqueEmbeddableName(jpaProject);

		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateEmbeddableFeature_EmbeddableFeatureName);
		jpaDiagramEditor.doubleClick(x, y);
		
		SWTBotGefEditPart embeddable = null;

		try {
			bot.waitUntil(new ElementIsShown(jpaDiagramEditor, embeddableName));
			embeddable = jpaDiagramEditor.getEditPart(embeddableName);
		} catch (TimeoutException e) {
			
			if(!jpaDiagramEditor.selectedEditParts().isEmpty()) {
				assertEquals(1, jpaDiagramEditor.selectedEditParts().size());
			
				PersistentType pers = getJPTObjectForGefElement(jpaDiagramEditor.selectedEditParts().get(0));
				assertNotNull(pers);
				assertEquals(embeddableName, pers.getSimpleName());
			
				jpaDiagramEditor.save();
			
				embeddable = jpaDiagramEditor.selectedEditParts().get(0);
			
				deleteJPTViaButton(embeddable, false);
				bot.waitUntil(new ElementDisappears(jpaDiagramEditor, embeddableName));
						
				embeddable = addEmb(x, y, jpaProject);
			} else {
				addEmb(x, y, jpaProject);
			}
		}
		return embeddable;
	}
	
	private SWTBotGefEditPart addMpdSprcls(int x, int y, JpaProject jpaProject) {
		String mappedSuperclassName = getUniqueMappedSuperclassName(jpaProject);
		
		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateMappedSuperclassFeature_createMappedSuperclassFeatureName);
		jpaDiagramEditor.doubleClick(x, y);
		
		SWTBotGefEditPart mappedSuperclass = null;

		try {
			bot.waitUntil(new ElementIsShown(jpaDiagramEditor, mappedSuperclassName));
			mappedSuperclass = jpaDiagramEditor.getEditPart(mappedSuperclassName);
		} catch (TimeoutException e) {
			if(!jpaDiagramEditor.selectedEditParts().isEmpty()) {
				assertEquals(1, jpaDiagramEditor.selectedEditParts().size());
			
				PersistentType pers = getJPTObjectForGefElement(jpaDiagramEditor.selectedEditParts().get(0));
				assertNotNull(pers);
				assertEquals(mappedSuperclassName, pers.getSimpleName());
			
				jpaDiagramEditor.save();
			
				mappedSuperclass = jpaDiagramEditor.selectedEditParts().get(0);
			
				deleteJPTViaButton(mappedSuperclass, false);
				bot.waitUntil(new ElementDisappears(jpaDiagramEditor, mappedSuperclassName));
						
				mappedSuperclass = addMpdSprcls(x, y,  jpaProject);
			} else {
				addMpdSprcls(x, y, jpaProject);
			}
		}
		return mappedSuperclass;
	}

	/**
	 * Add mapped superclass to diagram
	 * 
	 * @param x
	 * @param y
	 * @param jpaProject
	 * @return the newly added mapped superclass
	 */
	public SWTBotGefEditPart addMappedSuperclassToDiagram(int x, int y,
			JpaProject jpaProject) {

		SWTBotGefEditPart mappedSuperclass = addMpdSprcls(x, y, jpaProject);
		
		assertNotNull("Mapped superclass is not added!", mappedSuperclass);

		List<SWTBotGefEditPart> parts = new ArrayList<SWTBotGefEditPart>();
		parts.add(mappedSuperclass);

		SWTBotGefEditPart idAttribute = jpaDiagramEditor.getEditpart("id",
				parts);
		assertNull("Mapped superclass must not have a primary key attribute!",
				idAttribute);

		assertFalse(
				"\"Primary Key\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape,
						mappedSuperclass));
		assertFalse(
				"\"Relation Attributes\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes,
						mappedSuperclass));

		return mappedSuperclass;
	}

	/**
	 * Add embeddable to diagram
	 * 
	 * @param x
	 * @param y
	 * @param jpaProject
	 * @return the newly added embeddable
	 */
	public SWTBotGefEditPart addEmbeddableToDiagram(int x, int y,
			JpaProject jpaProject) {

		
		SWTBotGefEditPart embeddable = addEmb(x, y, jpaProject);

		assertNotNull("Embeddable is not added!", embeddable);

		List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
		editParts.add(embeddable);
		SWTBotGefEditPart idAttribute = jpaDiagramEditor.getEditpart("id",
				editParts);
		assertNull("Embeddablemust not have a primary key attribute!",
				idAttribute);

		assertFalse(
				"\"Primary Key\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape,
						embeddable));
		assertFalse(
				"\"Relation Attributes\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes,
						embeddable));
		return embeddable;
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
	 * Gets the dialog that appears after the "Delete" context button/menu is
	 * pressed.
	 * 
	 * @return the question dialog, asking whether to the delete the selected
	 *         entity
	 */
	public SWTBotShell getDeleteEntityDialog() {
		workbenchBot.waitUntil(
				shellIsActive(JPAEditorMessages.DeleteFeature_deleteConfirm),
				10000);
		SWTBotShell shell = workbenchBot
				.shell(JPAEditorMessages.DeleteFeature_deleteConfirm);
		return shell;
	}

	/**
	 * Gets the dialog that appears after the
	 * "Refactor Entity Class -> Rename..." context menu is pressed.
	 * 
	 * @return the "Rename Compilation Unit" dialog
	 */
	public SWTBotShell getRenameEntityDialog() {
		workbenchBot.waitUntil(shellIsActive("Rename Compilation Unit"), 10000);
		SWTBotShell shell = workbenchBot.shell("Rename Compilation Unit");
		assertFalse(getFinishButton(shell).isEnabled());
		assertTrue(getCancelButton(shell).isEnabled());
		return shell;
	}

	/**
	 * Gets the dialog that appears after the "Refactor Entity Class -> Move..."
	 * context menu is pressed.
	 * 
	 * @return the "Move" dialog
	 */
	public SWTBotShell getMoveEntityDialog() {
		workbenchBot.waitUntil(shellIsActive("Move"), 10000);
		SWTBotShell shell = workbenchBot.shell("Move");
		assertFalse(getOkButton(shell).isEnabled());
		assertTrue(getCancelButton(shell).isEnabled());
		return shell;
	}

	/**
	 * Gets the dialog that appears after the
	 * "Remove All Entities from Diagram -> ...and Save/Discard Changes" context
	 * menu is pressed. Press the OK button.
	 */
	public void confirmRemoveEntitiesFromDiagramDialog() {
		workbenchBot
				.waitUntil(
						shellIsActive(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAllEntitiesMenu),
						10000);
		SWTBotShell shell = workbenchBot
				.shell(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAllEntitiesMenu);
		assertTrue("Ok button is disabled", getOkButton(shell).isEnabled());
		assertTrue(getCancelButton(shell).isEnabled());
		getOkButton(shell).click();
	}

	/**
	 * Deletes an entity with the specified name using the context button.
	 * 
	 * @param entityName
	 *            - the name of the entity to be deleted
	 */
	public void deleteDiagramElements(boolean isOrmXml) {
		
		try {
			Utils.waitNonSystemJobs(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jpaDiagramEditor.save();
		List<SWTBotGefEditPart> entitiesInDiagram = jpaDiagramEditor
				.mainEditPart().children();
		// assertFalse("Diagram must contain at least one entity!",
		// entitiesInDiagram.isEmpty());

		for (int i = 0; i < entitiesInDiagram.size(); i++) {
			SWTBotGefEditPart editPart = entitiesInDiagram.get(i);
			assertNotNull(editPart);
			PersistentType type = getJPTObjectForGefElement(editPart);
			deleteAllAttributesFromJPT(editPart, type);
			if (type != null) {
				editPart.select();
//				deletePersistentObject(editPart, type
//								.getSimpleName());
				jpaDiagramEditor.clickContextMenu("Delete");
				confirmDelete();
				bot.waitUntil(
						new ElementDisappears(jpaDiagramEditor, type
								.getSimpleName()), 10000);
				editPart = jpaDiagramEditor.getEditPart(type.getSimpleName());
				assertNull("Entity is not deleted!", editPart);
				if(isOrmXml){
					assertNull(ormXml.getPersistentType(type.getName()));
				}
			}
		}
		
		bot.waitUntil(new DiagramIsEmpty(jpaDiagramEditor), 20000 );
		jpaDiagramEditor.save();
		
		try {
			Utils.waitNonSystemJobs();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// assertTrue("Editor must be dirty!", jpaDiagramEditor.isDirty());
	}

	private void deleteAllAttributesFromJPT(SWTBotGefEditPart editPart,
			PersistentType type) {
		if(type!= null) {
			for(PersistentAttribute pa : type.getAttributes()){
				if(!(pa.getName().equals("id"))) {
					deleteAttributeInJPT(editPart, pa.getName());
				}
			}
			jpaDiagramEditor.activateDefaultTool();
			if(isJPTDirty(editPart)) {
				jpaDiagramEditor.select(editPart);
				jpaDiagramEditor.click(editPart);
				jpaDiagramEditor.clickContextMenu("Save");
			}
			jpaDiagramEditor.save();
		}
	}

	public void deleteAttributeInJPT(SWTBotGefEditPart jpt, String attributeName) {
		
		try {
			Utils.waitNonSystemJobs(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		jpaDiagramEditor.activateDefaultTool();

		SWTBotGefEditPart attribute = getAttributeInPE(jpt, attributeName);
		attribute.select();
		attribute.click();
		try {
			jpaDiagramEditor.clickContextMenu("Delete");
		} catch (WidgetNotFoundException e) {
			pressAttributeDeleteContextButton(attribute);
		}
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, jpt,
				attributeName), 10000);
	}

	/**
	 * Press some of the entity's context buttons
	 * 
	 * @param jpaDiagramEditor
	 * @param contextButtonName
	 *            - the name of the button to be pressed
	 */
	public void pressEntityContextButton(SWTBotGefEditPart part,
			String contextButtonName) {
		pressContextButton(part, contextButtonName);
	}

	/**
	 * Press the "Delete Attribute" attribute's context button
	 * 
	 */
	public void pressAttributeDeleteContextButton(SWTBotGefEditPart part) {
		pressContextButton(
				part,
				JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteAttributeButtonlabel);
	}

	/**
	 * Assert that the context button pad is shown, when the mouse is placed
	 * over the entity and press the the desired button
	 * 
	 * @param contextButtonName
	 *            - the name of the button to be pressed.
	 */
	private void pressContextButton(SWTBotGefEditPart part,
			String contextButtonName) {
		jpaDiagramEditor.click(0, 0);
		jpaDiagramEditor.click(part);

		ContextButtonPad pad = findContextButtonPad();
		assertNotNull(pad);
		for (final Object button : pad.getChildren()) {
			if (((ContextButton) button).getEntry().getText()
					.equals(contextButtonName)) {
				asyncExec(new VoidResult() {
					public void run() {
						((ContextButton) button).doClick();

					}
				});
			}
		}
	}

	/**
	 * Place the mouse over the entity to show the context button pad.
	 * 
	 */
	public void moveMouse(final int x, final int y) {
		syncExec(new VoidResult() {
			public void run() {
				Robot r;
				try {
					r = new Robot();
					Point p = getOrigin();
					r.mouseMove(p.x + x, p.y + y);
				} catch (AWTException e) {
					fail(e.getMessage());
				}
			}
		});
	}

	/**
	 * Gets the context button pad, after placing the mouse over the entity
	 * 
	 * @return the entity's context button pad
	 */
	private ContextButtonPad findContextButtonPad() {
		SWTBotGefEditPart rootEditPart = jpaDiagramEditor.rootEditPart();
		IFigure feedbackLayer = ((ScalableFreeformRootEditPart) rootEditPart
				.part()).getLayer(LayerConstants.HANDLE_LAYER);
		ContextButtonPad cbp = null;
		for (Object obj : feedbackLayer.getChildren()) {
			if (obj instanceof ContextButtonPad) {
				cbp = (ContextButtonPad) obj;
				break;
			}
		}
		return cbp;
	}

	private FigureCanvas getCanvas() {
		IEditorReference reference = jpaDiagramEditor.getReference();
		final IEditorPart editor = reference.getEditor(true);
		GraphicalViewer graphicalViewer = (GraphicalViewer) editor
				.getAdapter(GraphicalViewer.class);
		final Control control = graphicalViewer.getControl();
		if (control instanceof FigureCanvas) {
			FigureCanvas c = (FigureCanvas) control;
			return c;
		}
		return null;
	}

	private Point getOrigin() {
		Canvas c = getCanvas();
		Point p = c.toDisplay(0, 0);
		return p;
	}

	/**
	 * Get the error message that appears in the "Select Type" dialog
	 * 
	 * @param dialog
	 * @return the error message
	 */
	public SWTBotText getDialogErroMessage(SWTBotShell dialog) {
		return dialog.bot().text(1);
	}

	/**
	 * Gets the "OK" button of a dialog
	 * 
	 * @param dialog
	 * @return the "OK" button
	 */
	public SWTBotButton getOkButton(SWTBotShell dialog) {
		return dialog.bot().button(IDialogConstants.OK_LABEL);
	}

	/**
	 * Gets the "Cancel" button of a dialog
	 * 
	 * @param dialog
	 * @return the "Cancel" button
	 */
	public SWTBotButton getCancelButton(SWTBotShell dialog) {
		return dialog.bot().button(IDialogConstants.CANCEL_LABEL);
	}

	/**
	 * Gets the "Finish" button of a dialog
	 * 
	 * @param dialog
	 * @return the "Finish" button
	 */
	public SWTBotButton getFinishButton(SWTBotShell dialog) {
		return dialog.bot().button("Finish");
	}

	/**
	 * Find the IRelation object for the given GEF Connection
	 * 
	 * @param gefConn
	 * @return the IRelation object for the given GEF Connection
	 */
	public IRelation getConnection(SWTBotGefConnectionEditPart gefConn) {
		IFeatureProvider fp = ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();
		FreeFormConnection conn = (FreeFormConnection) gefConn.part()
				.getModel();
		Object ob = fp.getBusinessObjectForPictogramElement(conn);
		if (ob instanceof IRelation) {
			return (IRelation) ob;
		}

		return null;
	}

	/**
	 * Find the IRelation object for the given GEF Connection
	 * 
	 * @param gefConn
	 * @return the IRelation object for the given GEF Connection
	 */
	public HasReferanceRelation getHasReferenceConnection(
			SWTBotGefConnectionEditPart gefConn, SWTBotGefEditPart attribute) {

		IJPAEditorFeatureProvider fp = (IJPAEditorFeatureProvider) ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();
		FreeFormConnection conn = (FreeFormConnection) gefConn.part()
				.getModel();
		assertNotNull("Relation is not created.", conn);

		Object ob = fp.getBusinessObjectForPictogramElement(conn);
		if (ob == null) {
			PersistentAttribute jpa = getJPAObjectForGefElement(attribute);
			HasReferanceRelation rel = fp
					.getEmbeddedRelationRelatedToAttribute(jpa);
			assertNotNull(rel);
			FreeFormConnection connection = (FreeFormConnection) fp
					.getPictogramElementForBusinessObject(rel);
			assertNotNull(connection);
			return rel;
		} else {
			if (ob instanceof HasReferanceRelation) {
				return (HasReferanceRelation) ob;
			}
		}
		return null;
	}

	protected PersistentType getPersistentType(IFeatureProvider fp,
			Anchor anchor) {
		if (anchor != null) {
			Object refObject = fp.getBusinessObjectForPictogramElement(anchor
					.getParent());
			if (refObject instanceof PersistentType) {
				return (PersistentType) refObject;
			}
		}
		return null;
	}

	/**
	 * Gets the business object (PersistentType) for the given GEF element
	 * 
	 * @param element
	 * @return the java persistent type for the given element, null if the
	 *         selected element is not an entity
	 */
	public PersistentType getJPTObjectForGefElement(
			SWTBotGefEditPart element) {
		final IFeatureProvider fp = ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();
		PictogramElement el = (PictogramElement) element.part().getModel();
		if (el == null)
			return null;
		Object bo = fp.getBusinessObjectForPictogramElement(el);
		if (bo instanceof PersistentType) {
			return (PersistentType) bo;
		}
		return null;
	}

	public PersistentAttribute getJPAObjectForGefElement(
			SWTBotGefEditPart element) {
		final IFeatureProvider fp = ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();
		PictogramElement el = (PictogramElement) element.part().getModel();
		Object bo = fp.getBusinessObjectForPictogramElement(el);
		if (bo instanceof PersistentAttribute) {
			return (PersistentAttribute) bo;
		}
		return null;
	}

	/**
	 * Gets the existing isARelation
	 * 
	 * @return the existing isArelation if exists, null otherwise
	 */
	public IsARelation getIsARelationship() {
		IJPAEditorFeatureProvider fp = (IJPAEditorFeatureProvider) ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();
		Set<IsARelation> isARelationships = fp.getAllExistingIsARelations();
		assertFalse(isARelationships.isEmpty());
		assertEquals(1, isARelationships.size());
		IsARelation relation = isARelationships.iterator().next();
		return relation;
	}

	/**
	 * CHecks whether the Entity contains unsaved changes.
	 * 
	 * @param element
	 * @return true if the entity contains unsaved changes, false otherwise
	 */
	public boolean isJPTDirty(SWTBotGefEditPart element) {
		final IFeatureProvider fp = ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();
		assertNotNull(element);
		PictogramElement el = (PictogramElement) element.part().getModel();
		Object bo = fp.getBusinessObjectForPictogramElement(el);
		IResource res = null;
		if (bo instanceof PersistentAttribute) {
			res = ((PersistentAttribute) bo).getResource();
		} else if (bo instanceof PersistentType) {
			res = ((PersistentType) bo).getResource();
		}

		if (res != null) {
			ICompilationUnit unit = JPAEditorUtil
					.getCompilationUnit((IFile) res);
			try {
				return unit.hasUnsavedChanges();
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Select the bidirectional relation and call its "Delete" context menu. On
	 * the confirmation dialog press "No" and assert that the connection and the
	 * relative relation attributes still exist and the "Relation Attributes"
	 * sections of the entities' are visible.
	 * 
	 * @param entity1
	 * @param entity2
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertBiDirRelationIsNotDeleted(SWTBotGefEditPart entity1,
			SWTBotGefEditPart entity2, SWTBotGefConnectionEditPart connection,
			String ownerAttributeName, String inverseAttributeName, boolean isDerivedIdFeature) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		denyDelete();
		
		jpaDiagramEditor.save();

		assertFalse(entity1.sourceConnections().isEmpty());
		assertFalse(entity2.targetConnections().isEmpty());
		connection = getConnection(entity1, entity2);
		assertNotNull("Attribute must not be deleted!", connection);
		assertNotNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		if(isDerivedIdFeature){
			assertFalse(
					"\"Relation Attributes\" section of the owner entity must not be visible!",
					isSectionVisible(
							entity1,
							JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		} else {
			assertTrue(
				"\"Relation Attributes\" section of the owner entity must be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		}
		assertTrue(
				"\"Relation Attributes\" section of the inverse entity must be visible!",
				isSectionVisible(
						entity2,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}

	/**
	 * Select the bidirectional self relation and call its "Delete" context
	 * menu. On the confirmation dialog press "No" and assert that the
	 * connection and the relative relation attributes still exist and the
	 * "Relation Attributes" sections of the entities' are visible.
	 * 
	 * @param entity1
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertSelfBiDirRelationIsNotDeleted(SWTBotGefEditPart entity1,
			SWTBotGefConnectionEditPart connection, String ownerAttributeName,
			String inverseAttributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		denyDelete();
		
		jpaDiagramEditor.save();

		assertFalse(entity1.sourceConnections().isEmpty());
		assertFalse(entity1.targetConnections().isEmpty());
		connection = getConnection(entity1, entity1);
		assertNotNull("Attribute must not be deleted!", connection);
		assertNotNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertTrue(
				"\"Relation Attributes\" section of the owner entity must be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}

	/**
	 * Select the unidirectional relation and call its "Delete" context menu. On
	 * the confirmation dialog press "No" and assert that the connection and the
	 * relative relation attributes still exist and the "Relation Attributes"
	 * sections of the entities' are visible.
	 * 
	 * @param entity1
	 * @param entity2
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertUniDirRelationIsNotDeleted(SWTBotGefEditPart entity1,
			SWTBotGefEditPart entity2, SWTBotGefConnectionEditPart connection,
			String attributeName, boolean isDerivedIdFeature) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		denyDelete();
		
		jpaDiagramEditor.save();

		assertFalse(entity1.sourceConnections().isEmpty());
		assertFalse(entity2.targetConnections().isEmpty());
		connection = getConnection(entity1, entity2);
		assertNotNull("Attribute must not be deleted!", connection);
		assertNotNull(jpaDiagramEditor.getEditPart(attributeName));
		
		if(isDerivedIdFeature){
			assertFalse(
					"\"Relation Attributes\" section of the inverse entity must not be visible!",
					isSectionVisible(
							entity1,
							JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		} else {
		assertTrue(
				"\"Relation Attributes\" section of the owner entity must be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		}
		assertFalse(
				"\"Relation Attributes\" section of the inverse entity must not be visible!",
				isSectionVisible(
						entity2,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}

	/**
	 * Select the unidirectional self relation and call its "Delete" context
	 * menu. On the confirmation dialog press "No" and assert that the
	 * connection and the relative relation attributes still exist and the
	 * "Relation Attributes" sections of the entities' are visible.
	 * 
	 * @param entity1
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertSelfUniDirRelationIsNotDeleted(SWTBotGefEditPart entity1,
			SWTBotGefConnectionEditPart connection, String attributeName) {
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		denyDelete();
		
		jpaDiagramEditor.save();
		
		assertFalse(entity1.sourceConnections().isEmpty());
		assertFalse(entity1.targetConnections().isEmpty());
		connection = getConnection(entity1, entity1);
		assertNotNull("Attribute must not be deleted!", connection);
		assertNotNull(jpaDiagramEditor.getEditPart(attributeName));
		assertTrue(
				"\"Relation Attributes\" section of the owner entity must be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}

	/**
	 * Select the bidirectional relation and call its "Delete" context menu. On
	 * the confirmation dialog press "Yes" and assert that the connection and
	 * the relative relation attributes do not exist anymore and the
	 * "Relation Attributes" sections of the entities' are not visible.
	 * 
	 * @param entity1
	 * @param entity2
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertBiDirRelationIsDeleted(SWTBotGefEditPart entity1,
			SWTBotGefEditPart entity2, SWTBotGefConnectionEditPart connection,
			String ownerAttributeName, String inverseAttributeName, boolean isOrmXml) {
		
		PersistentAttribute ownerAt = getJPAObjectForGefElement(jpaDiagramEditor.getEditPart(ownerAttributeName));
		PersistentAttribute inverseAt = getJPAObjectForGefElement(jpaDiagramEditor.getEditPart(inverseAttributeName));
		
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor,
				ownerAttributeName), 10000);
		
		jpaDiagramEditor.save();

//		assertTrue(entity1.sourceConnections().isEmpty());
//		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNull(jpaDiagramEditor.getEditPart(inverseAttributeName));

		assertFalse(
				"\"Relation Attributes\" section of the owner entity must not be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse(
				"\"Relation Attributes\" section of the inverse entity must not be visible!",
				isSectionVisible(
						entity2,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		if(isOrmXml){
			assertOrmAttrIsDeleted(ownerAt);
			assertOrmAttrIsDeleted(inverseAt);
		}
	}

	/**
	 * Select the bidirectional self relation and call its "Delete" context
	 * menu. On the confirmation dialog press "Yes" and assert that the
	 * connection and the relative relation attributes do not exist anymore and
	 * the "Relation Attributes" sections of the entities' are not visible.
	 * 
	 * @param entity1
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertSelfBiDirRelationIsDeleted(SWTBotGefEditPart entity1,
			SWTBotGefConnectionEditPart connection, String ownerAttributeName,
			String inverseAttributeName, boolean isOrmXml) {
		PersistentAttribute ownerAt = getJPAObjectForGefElement(jpaDiagramEditor.getEditPart(ownerAttributeName));
		PersistentAttribute inverseAt = getJPAObjectForGefElement(jpaDiagramEditor.getEditPart(inverseAttributeName));

		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor,
				ownerAttributeName), 10000);
		
		jpaDiagramEditor.save();
		
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity1.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse(
				"\"Relation Attributes\" section of the owner entity must not be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		if(isOrmXml){
			assertOrmAttrIsDeleted(ownerAt);
			assertOrmAttrIsDeleted(inverseAt);			
		}
	}

	/**
	 * Select the unidirectional relation and call its "Delete" context menu. On
	 * the confirmation dialog press "Yes" and assert that the connection and
	 * the relative relation attributes do not exist anymore and the
	 * "Relation Attributes" sections of the entities' are not visible.
	 * 
	 * @param entity1
	 * @param entity2
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertUniDirRelationIsDeleted(SWTBotGefEditPart entity1,
			SWTBotGefEditPart entity2, SWTBotGefConnectionEditPart connection,
			String attributeName, boolean isOrmXml) {
		
		PersistentAttribute at = getJPAObjectForGefElement(jpaDiagramEditor.getEditPart(attributeName));
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, attributeName),
				20000);
		
		jpaDiagramEditor.save();

//		assertTrue(entity1.sourceConnections().isEmpty());
//		assertTrue(entity2.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(attributeName));
		assertFalse(
				"\"Relation Attributes\" section of the owner entity must not be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse(
				"\"Relation Attributes\" section of the inverse entity must not be visible!",
				isSectionVisible(
						entity2,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		
		if(isOrmXml){
			assertOrmAttrIsDeleted(at);
		}
	}

	private void assertOrmAttrIsDeleted(PersistentAttribute at) {
		OrmPersistentType ormJpt = ormXml.getPersistentType(at.getDeclaringPersistentType().getName());
		assertNotNull(ormJpt);
		OrmPersistentAttribute ormAttr = ormJpt.getAttributeNamed(at.getName());
		try {
			assertNull(ormAttr);
		} catch (AssertionError e) {
			assertNotNull(ormAttr);
			assertTrue(ormAttr.isVirtual());
		}
	}

	/**
	 * Select the unidirectional self relation and call its "Delete" context
	 * menu. On the confirmation dialog press "Yes" and assert that the
	 * connection and the relative relation attributes do not exist anymore and
	 * the "Relation Attributes" sections of the entities' are not visible.
	 * 
	 * @param entity1
	 * @param connection
	 * @param ownerAttributeName
	 * @param inverseAttributeName
	 */
	public void assertSelfUniDirRelationIsDeleted(SWTBotGefEditPart entity1,
			SWTBotGefConnectionEditPart connection, String attributeName, boolean isOrmXml) {
		PersistentAttribute at = getJPAObjectForGefElement(jpaDiagramEditor.getEditPart(attributeName));
		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, attributeName),
				10000);
		assertTrue(entity1.sourceConnections().isEmpty());
		assertTrue(entity1.targetConnections().isEmpty());
		
		jpaDiagramEditor.save();
		
		assertNull(jpaDiagramEditor.getEditPart(attributeName));
		assertFalse(
				"\"Relation Attributes\" section of the owner entity must not be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		if(isOrmXml){
			assertOrmAttrIsDeleted(at);
		}
	}

	/**
	 * Assert that there is exactly one GEF element representing the
	 * relationship
	 * 
	 * @param entity1
	 * @param entity2
	 */
	public void assertConnectionIsCreated(SWTBotGefEditPart entity1,
			SWTBotGefEditPart entity2, boolean isBiDIr) {
		// assert that there is exactly one relationship, which start from
		// entity1
		// and that there is no relationship which starts from entity2
		assertFalse(entity1.sourceConnections().isEmpty());
		assertTrue(entity2.sourceConnections().isEmpty());

		// assert that there is exactly one relationship which ends in entity2
		// and that there is no relationship which end in entity1.
		assertFalse(entity2.targetConnections().isEmpty());
		assertEquals(1, entity2.targetConnections().size());
//		assertTrue(entity1.targetConnections().isEmpty());

		assertTrue(
				"\"Relation Attributes\" section of the owner entity must be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		if (isBiDIr) {
			assertTrue(
					"\"Relation Attributes\" section of the inverse entity must be visible!",
					isSectionVisible(
							entity2,
							JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		} else {
			assertFalse(
					"\"Relation Attributes\" section of the inverse entity must not be visible!",
					isSectionVisible(
							entity2,
							JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		}
	}

	/**
	 * Assert that there is exactly one GEF element representing the
	 * relationship
	 * 
	 * @param entity1
	 * @param entity2
	 */
	public void assertDerivedIdConnectionIsCreated(SWTBotGefEditPart entity1,
			SWTBotGefEditPart entity2, boolean isBiDIr) {

		assertFalse(
				"\"Relation Attributes\" section of the owner entity must not be visible!",
				isSectionVisible(
						entity1,
						JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	
		if (isBiDIr) {
			assertTrue(
					"\"Relation Attributes\" section of the inverse entity must be visible!",
					isSectionVisible(
							entity2,
							JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		} else {
			assertFalse(
					"\"Relation Attributes\" section of the inverse entity must not be visible!",
					isSectionVisible(
							entity2,
							JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		}
	}
	public void assertIsARelationExists(SWTBotGefEditPart entity1,
			SWTBotGefEditPart entity2) {
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
	 * Assert that there is exactly one GEF element representing the self
	 * relationship
	 * 
	 * @param entity1
	 */
	public void assertSelfConnectionIsCreated(SWTBotGefEditPart entity1) {
		// assert that there is exactly one relationship, which start from
		// entity1
		// and ends in entity2
		assertFalse(entity1.sourceConnections().isEmpty());
		assertEquals(1, entity1.sourceConnections().size());

		assertFalse(entity1.targetConnections().isEmpty());
		assertEquals(1, entity1.targetConnections().size());

		assertTrue(isSectionVisible(entity1,
				JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}

	/**
	 * Assert that the owner relationship attribute exists
	 * 
	 * @param rel
	 * @return the name of the owner relationship attribute
	 */
	public String testOwnerRelationAttributeProperties(IRelation rel, String mappingKey, boolean isOrmXml) {
		PersistentAttribute ownerAttr = rel.getOwnerAnnotatedAttribute();
		String attributeName = rel.getOwnerAttributeName();
		assertNotNull(ownerAttr);
		assertNotNull(jpaDiagramEditor.getEditPart(attributeName));
		if(isOrmXml) {
			OrmPersistentType ormJpt = ormXml.getPersistentType(ownerAttr.getDeclaringPersistentType().getName());
			assertNotNull(ormJpt);
			OrmPersistentAttribute ormAttr = ormJpt.getAttributeNamed(attributeName);
			assertNotNull(ormAttr);
			assertFalse(ormAttr.isVirtual());
		}
		
		assertIsMappedAttribute(ownerAttr, mappingKey);
		
		return attributeName;
	}

	/**
	 * Assert that the embedded attribute exists
	 * 
	 * @param rel
	 * @return the name of the embedding attribute
	 */
	public String testEmbeddedAttributeProperties(HasReferanceRelation rel,
			String attributeMapping) {
		PersistentAttribute embeddedAttr = rel
				.getEmbeddedAnnotatedAttribute();
		String attributeName = embeddedAttr.getName();
		assertNotNull(embeddedAttr);
		assertNotNull(jpaDiagramEditor.getEditPart(attributeName));
		AttributeMapping attrMapping = jpaFactory.getAttributeMapping(embeddedAttr);
		assertEquals("The attribute must be embedded attribute.",
				attributeMapping, attrMapping.getKey());

		return attributeName;
	}

	/**
	 * Assert that the inverse relationship attribute exists.
	 * 
	 * @param rel
	 * @return the name of the inverse relationship attribute
	 */
	public String testInverseRelationAttributeProperties(IRelation rel, String mappingKey, boolean isOrmXml) {
		PersistentAttribute inverseAttr = rel
				.getInverseAnnotatedAttribute();
		String inverseAttributeName = rel.getInverseAttributeName();
		assertNotNull(inverseAttr);
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		if(isOrmXml) {
			OrmPersistentType ormJpt = ormXml.getPersistentType(inverseAttr.getDeclaringPersistentType().getName());
			assertNotNull(ormJpt);
			OrmPersistentAttribute ormAttr = ormJpt.getAttributeNamed(inverseAttributeName);
			assertNotNull(ormAttr);
			assertFalse(ormAttr.isVirtual());
		}
		
		assertIsMappedAttribute(inverseAttr, mappingKey);
		return inverseAttributeName;
	}

	/**
	 * Checks whether a section of a particular entity is visible
	 * 
	 * @param editPart
	 *            - the particular entity
	 * @param sectionTitle
	 *            - the title of the section to be checked
	 * @return true, if the sections is visible, false otherwise
	 */
	public boolean isSectionVisible(SWTBotGefEditPart editPart,
			String sectionTitle) {
		List<SWTBotGefEditPart> children = editPart.children();
		SWTBotGefEditPart section = jpaDiagramEditor.getEditpart(sectionTitle,
				children);
		((PictogramElement) section.part().getModel()).isVisible();
		IFigure figure = ((GraphicalEditPart) section.part()).getFigure();
		return figure.isVisible() || figure.isShowing();
	}

	public SWTBotGefEditPart getSectionInJPT(SWTBotGefEditPart editPart,
			String sectionTitle) {
		List<SWTBotGefEditPart> children = editPart.children();
		SWTBotGefEditPart section = jpaDiagramEditor.getEditpart(sectionTitle,
				children);
		return section;
	}

	/**
	 * Change the mapping of the type or attribute.
	 * 
	 * @param newMappingType
	 */
	public void changeMappingtype(String newMappingType) {
		workbenchBot.waitUntil(shellIsActive("Mapping Type Selection"), 10000);
		SWTBotShell mappingTypeShell = workbenchBot
				.shell("Mapping Type Selection");
		mappingTypeShell.bot().table().getTableItem(newMappingType).select();
		getOkButton(mappingTypeShell).click();
	}

	/**
	 * Click on the mapping type link in the JPA Details view
	 * 
	 * @param styledText
	 * @param position
	 */
	public void clickOnStyledText(final SWTBotStyledText styledText,
			int position) {
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
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * Assert that the relation attribute is correctly mapped in the JPA Details
//	 * view
//	 * 
//	 * @param attributeName
//	 * @param relationAttributeMapping
//	 *            - the expected attribute mapping
//	 */
//	public void assertAttributeIsCorretlyMapped(String attributeName,
//			String relationAttributeMapping) {
//
//		// assert that the JPA Details view is opened
//		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
//		jpaDetailsView.setFocus();
//		assertTrue("JPA Details view must be opened!",
//				jpaDetailsView.isActive());
//
//		SWTBotGefEditPart attribute = jpaDiagramEditor
//				.getEditPart(attributeName);
//		attribute.select();
//		attribute.click();
//
//		// assert that the default entity's attribute is mapped as primary key
//		jpaDetailsView.show();
//		SWTBot jpaDetailsBot = jpaDetailsView.bot();
//		SWTBotStyledText styledText = jpaDetailsBot.styledText();
//		assertEquals("Attribute '" + attributeName + "' is mapped as "
//				+ relationAttributeMapping + ".", styledText.getText());
//	}
	
	public void assertIsMappedAttribute(PersistentAttribute jpa, String mappingKey){
		assertEquals(
				"The newly added attribute must be mapped as basic attribute.",
				mappingKey,
				jpaFactory.getAttributeMapping(jpa).getKey());
	}

	/**
	 * Assert that the type is correctly mapped in the JPA Details view
	 * 
	 * @param typeName
	 * @param typeMapping
	 *            - the expected type mapping
	 */
	public void assertTypeIsCorretlyMapped(SWTBotGefEditPart type,
			String typeMapping) {
		// workbenchBot.viewByTitle("JPA Details").close();

		// SWTBotGefEditPart type = jpaDiagramEditor.getEditPart(typeName);

		// JavaPersistentType jpt = getJPTObjectForGefElement(type);
		// assertEquals("Type is not mapped correctly.", jpt.getMappingKey(),
		// typeMapping);

		// type.click();
		//
		// jpaDiagramEditor
		// .clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_openJPADetailsView);
		// // assert that the JPA Details view is opened
		// SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		// assertTrue("JPA Details view must be opened!",
		// jpaDetailsView.isActive());
		//
		// // assert that the default entity's attribute is mapped as the given
		// // mapping key
		// SWTBot jpaDetailsBot = jpaDetailsView.bot();
		// SWTBotStyledText styledText = jpaDetailsBot.styledText();
		// assertEquals("Type '" + typeName + "' is mapped as " + typeMapping
		// + ".", styledText.getText());

		// assert that the JPA Details view is opened
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		jpaDetailsView.show();
		assertTrue("JPA Details view must be opened!",
				jpaDetailsView.isActive());

		// SWTBotGefEditPart type = jpaDiagramEditor.getEditPart(typeName);

		type.select();
		type.click();

		String typeName = getJPTObjectForGefElement(type).getSimpleName();

		// assert that the default entity's attribute is mapped as primary key
		SWTBot jpaDetailsBot = jpaDetailsView.bot();
		SWTBotStyledText styledText = jpaDetailsBot.styledText();
		assertEquals("Type '" + typeName + "' is mapped as " + typeMapping
				+ ".", styledText.getText());
	}

	public void deleteJPTViaButton(SWTBotGefEditPart jptType, boolean must) {
		
		try {
			Utils.waitNonSystemJobs(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jptName = getJPTObjectForGefElement(jptType).getSimpleName();

		pressEntityContextButton(
				jptType,
				JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonLabel);
		denyDelete();
		if(must) {
			jptType = jpaDiagramEditor.getEditPart(jptName);
			assertNotNull("Entity is deleted!", jptType);
		}

		deletePersistentObject(jptType, jptName);
	}

	private void deletePersistentObject(SWTBotGefEditPart jptType,
			String jptName) {
		pressEntityContextButton(
				jptType,
				JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonLabel);
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, jptName), 10000);
		jptType = jpaDiagramEditor.getEditPart(jptName);
		assertNull("Entity is not deleted!", jptType);
	}

	public void deleteJPTViaMenu(SWTBotGefEditPart jptType) {
		
		try {
			Utils.waitNonSystemJobs(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jptName = getJPTObjectForGefElement(jptType).getSimpleName();

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonLabel);
		denyDelete();

		jptType = jpaDiagramEditor.getEditPart(jptName);
		assertNotNull("Entity is deleted!", jptType);

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_deleteEntityFromModelButtonLabel);
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, jptName), 10000);
		jptType = jpaDiagramEditor.getEditPart(jptName);
		assertNull("Entity is not deleted!", jptType);
	}

	public void removeAttributeViaButton(SWTBotGefEditPart jptType, SWTBotGefEditPart attribute,
			String attributeName, boolean isOrmXml) {

		attribute.click();
		
		pressAttributeDeleteContextButton(attribute);
		denyDelete();
		attribute = jpaDiagramEditor.getEditPart(attributeName);
		assertNotNull("Attribute must not be deleted!", attribute);

		pressAttributeDeleteContextButton(attribute);
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, attributeName),
				20000);
		attribute = jpaDiagramEditor.getEditPart(attributeName);
		assertNull("Attribute must be deleted!", attribute);

		jpaDiagramEditor.save();
		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
						jptType));

	}

	public void removeAttributeViaMenu(SWTBotGefEditPart jptType, SWTBotGefEditPart attribute,
			String attributeName) {
		attribute.click();
		jpaDiagramEditor.clickContextMenu("Delete");
		denyDelete();
		attribute = jpaDiagramEditor.getEditPart(attributeName);
		assertNotNull("Attribute must not be deleted!", attribute);

		attribute.click();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, attributeName),
				20000);
		attribute = jpaDiagramEditor.getEditPart(attributeName);
		assertNull("Attribute must be deleted!", attribute);

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
						jptType));
	}

	public void directEditAttribute(SWTBotGefEditPart jptType,
			String attributeName) {

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
						jptType));

		SWTBotGefEditPart attribute = addAttributeToJPT(jptType, attributeName, false);
		assertNotNull("The attribute must not be renamed!", attribute);

		jpaDiagramEditor.directEditType("newAttrName");
		SWTBotGefEditPart oldAttribute = jpaDiagramEditor
				.getEditPart(attributeName);
		SWTBotGefEditPart newAttribute = jpaDiagramEditor
				.getEditPart("newAttrName");
		assertNotNull("The attribute must be renamed!", newAttribute);
		assertNull("The attribute must be renamed!", oldAttribute);
	}

	public void collapseExpandJPTViaButton(SWTBotGefEditPart jptType) {

		int heigth = ((PictogramElement) jptType.part().getModel())
				.getGraphicsAlgorithm().getHeight();

		pressEntityContextButton(jptType, "Collapse");
		waitASecond();

		int newHeight = ((PictogramElement) jptType.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be collapsed!",
				JPAEditorConstants.ENTITY_MIN_HEIGHT, newHeight);
		assertTrue(newHeight < heigth);

		pressEntityContextButton(jptType, "Expand");
		waitASecond();

		newHeight = ((PictogramElement) jptType.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be expanded!", heigth, newHeight);
		assertTrue(newHeight > JPAEditorConstants.ENTITY_MIN_HEIGHT);
	}

	public void collapseExpandJPTViaMenu(SWTBotGefEditPart jptType) {

		int heigth = ((PictogramElement) jptType.part().getModel())
				.getGraphicsAlgorithm().getHeight();

		jpaDiagramEditor.click(jptType);

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseEntityMenuItem);
		waitASecond();

		int newHeight = ((PictogramElement) jptType.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be collapsed!",
				JPAEditorConstants.ENTITY_MIN_HEIGHT, newHeight);
		assertTrue(newHeight < heigth);

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandEntityMenuItem);
		waitASecond();

		newHeight = ((PictogramElement) jptType.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be expanded!", heigth, newHeight);
		assertTrue(newHeight > JPAEditorConstants.ENTITY_MIN_HEIGHT);
	}

	public void collapseExpandAllJPTsViaMenu(SWTBotGefEditPart jptType1,
			SWTBotGefEditPart jptType2) {

		int heigth1 = ((PictogramElement) jptType1.part().getModel())
				.getGraphicsAlgorithm().getHeight();

		int heigth2 = ((PictogramElement) jptType2.part().getModel())
				.getGraphicsAlgorithm().getHeight();

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_collapseAllEntitiesMenuItem);
		waitASecond();

		// check that entity1 is collapsed
		int newHeight1 = ((PictogramElement) jptType1.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Entity1 must be collapsed!",
				JPAEditorConstants.ENTITY_MIN_HEIGHT, newHeight1);
		assertTrue(newHeight1 < heigth1);

		// check that entity2 is collapsed
		int newHeight2 = ((PictogramElement) jptType2.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Entity2 must be collapsed!",
				JPAEditorConstants.ENTITY_MIN_HEIGHT, newHeight2);
		assertTrue(newHeight2 < heigth2);

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_expandAllEntitiesMenuItem);
		waitASecond();

		// check that entity1 is expanded
		newHeight1 = ((PictogramElement) jptType1.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be expanded!", heigth1, newHeight1);
		assertTrue(newHeight1 > JPAEditorConstants.ENTITY_MIN_HEIGHT);

		// check that entity2 is expanded
		newHeight2 = ((PictogramElement) jptType2.part().getModel())
				.getGraphicsAlgorithm().getHeight();
		assertEquals("Entity must be expanded!", heigth2, newHeight2);
		assertTrue(newHeight2 > JPAEditorConstants.ENTITY_MIN_HEIGHT);
	}

	public void discardChanges(SWTBotGefEditPart jptType, String attributeName) {

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
						jptType));
		assertFalse(isJPTDirty(jptType));

		addAttributeToJPT(jptType, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertTrue(isJPTDirty(jptType));

		jptType.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_discardChangesMenuItem);
		SWTBotGefEditPart attribute = jpaDiagramEditor
				.getEditPart(attributeName);
		assertNull("Changes must be discard!", attribute);
		assertFalse(isJPTDirty(jptType));
	}

	public void removeAndDiscardChangesViaMenu(SWTBotGefEditPart jptType,
			String attributeName) {

		assertFalse("Diagram must contain at least one entity!",
				jpaDiagramEditor.mainEditPart().children().isEmpty());

		assertFalse(
				"\"Other Attributes\" section must not be visible!",
				isSectionVisible(
						JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
						jptType));
		assertFalse(isJPTDirty(jptType));

		addAttributeToJPT(jptType, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertTrue(isJPTDirty(jptType));

		jptType.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndDiscardAllEntitiesAction);
		confirmRemoveEntitiesFromDiagramDialog();
		assertTrue("Diagram must be empty!", jpaDiagramEditor.mainEditPart()
				.children().isEmpty());

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_showAllTheEntities);

		bot.waitUntil(new ElementAppearsInDiagram(jpaDiagramEditor), 20000);

		assertFalse("Diagram must contain at least one entity!",
				jpaDiagramEditor.mainEditPart().children().isEmpty());
		
		assertEquals(1, jpaDiagramEditor.mainEditPart().children().size());

		jptType = jpaDiagramEditor.mainEditPart().children().get(0);
		assertNotNull(jptType);
		SWTBotGefEditPart attribute = jpaDiagramEditor
				.getEditPart(attributeName);
		assertNull("Changes must be discard!", attribute);
		
//		waitASecond();
				
//		assertFalse(isJPTDirty(jptType));
	}

	public void removeAndSaveChangesViaMenu(SWTBotGefEditPart jptType,
			String attributeName) {

		assertFalse("Diagram must contain at least one entity!",
				jpaDiagramEditor.mainEditPart().children().isEmpty());

		String jptName = getJPTObjectForGefElement(jptType).getSimpleName();

//		assertFalse(
//				"\"Other Attributes\" section must not be visible!",
//				isSectionVisible(
//						JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
//						jptType));
		assertFalse(isJPTDirty(jptType));

		addAttributeToJPT(jptType, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertTrue(isJPTDirty(jptType));

		jptType.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_removeAndSaveAllEntitiesAction);
		confirmRemoveEntitiesFromDiagramDialog();
		assertTrue("Diagram must be empty!", jpaDiagramEditor.mainEditPart()
				.children().isEmpty());

		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_showAllTheEntities);

		bot.waitUntil(new ElementAppearsInDiagram(jpaDiagramEditor), 30000);

		assertFalse("Diagram must contain at least one entity!",
				jpaDiagramEditor.mainEditPart().children().isEmpty());

		jptType = jpaDiagramEditor.getEditPart(jptName);
		SWTBotGefEditPart attribute = jpaDiagramEditor
				.getEditPart(attributeName);
		assertNotNull("Changes must be discard!", attribute);
		assertFalse(isJPTDirty(jptType));
	}

	public void saveOnlyJPT(SWTBotGefEditPart jptType, String attributeName) {

//		assertFalse(
//				"\"Other Attributes\" section must not be visible!",
//				isSectionVisible(
//						JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes,
//						jptType));
		assertFalse(isJPTDirty(jptType));

		addAttributeToJPT(jptType, attributeName, false);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertTrue(isJPTDirty(jptType));

		jptType.click();
		jpaDiagramEditor
				.clickContextMenu(JPAEditorMessages.JPAEditorToolBehaviorProvider_saveButtonText);
		assertTrue("Editor must be dirty", jpaDiagramEditor.isDirty());
		assertFalse(isJPTDirty(jptType));
	}

	public void testUniDirRelation(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse,
			RelType reltype, String mappingKey, boolean canBeDerivedIdFeature, boolean isOrmXml) {

		int sourceConSize = owner.sourceConnections().size();
		
		if(canBeDerivedIdFeature) {
			jpaDiagramEditor.activateTool(relationFeatureName, 2);
		} else {
			jpaDiagramEditor.activateTool(relationFeatureName, 0);
		}

		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);
		bot.waitUntil(new ConnectionIsShown(owner, sourceConSize), 10000);

		jpaDiagramEditor.save();
		
		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertConnectionIsCreated(owner, inverse, false);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());

		String attributeName = testOwnerRelationAttributeProperties(rel, mappingKey, isOrmXml);
		assertNull(rel.getInverseAnnotatedAttribute());

		assertUniDirRelationIsNotDeleted(owner, inverse, connection,
				attributeName, false);

		assertUniDirRelationIsDeleted(owner, inverse, connection, attributeName, isOrmXml);
	}	
	
	public void testUniDirDerivedIdWithIdAnnotation(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse,
			RelType reltype, String relationAnnotation, String derivedIdAnnotation, String mappingKey, boolean isOrmXml){
		jpaDiagramEditor
		.activateTool(relationFeatureName, 0);
		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);
		
		bot.waitUntil(new ConnectionIsShown(owner));
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertDerivedIdConnectionIsCreated(owner, inverse, false);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());

		String ownerAttributeName = testOwnerRelationAttributeProperties(rel, mappingKey, isOrmXml);
		
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		jpaDetailsView.show();
		assertTrue("JPA Details view must be opened!",
				jpaDetailsView.isActive());

		SWTBotGefEditPart attribute = jpaDiagramEditor
				.getEditPart(ownerAttributeName);
//		attribute.select();
		attribute.click();
		
		PersistentAttribute jpa = getJPAObjectForGefElement(attribute);
		
//		HashSet<String> annotations = getAnnotationNames(jpa);
//		assertTrue(annotations.contains(relationAnnotation));
//		assertTrue(annotations.contains(derivedIdAnnotation));
		
		AttributeMapping attributeMapping = jpaFactory.getAttributeMapping(jpa);
		
		if(relationAnnotation.equals("OneToOne")){
			assertTrue(attributeMapping instanceof OneToOneMapping2_0);
		} else {
			assertTrue(attributeMapping instanceof ManyToOneMapping2_0);
		}
		
		assertTrue(attributeMapping instanceof SingleRelationshipMapping2_0);
		DerivedIdentity2_0 identity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
		if(derivedIdAnnotation.equals("Id")) {
			assertTrue(identity.usesIdDerivedIdentityStrategy());
		} else {
			assertTrue(identity.usesMapsIdDerivedIdentityStrategy());
		}
		
		assertUniDirRelationIsNotDeleted(owner, inverse, connection, ownerAttributeName, true);
		
		assertUniDirRelationIsDeleted(owner, inverse, connection, ownerAttributeName, isOrmXml);

	}
	
	public void testUniDirDerivedIdWithEmbeddedPk(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse, SWTBotGefEditPart embeddable,
			RelType reltype, String relationAnnotation, String derivedIdAnnotation, String mappingKey,
			boolean isBiDir, boolean isSamePK, String idClassFQN, boolean isOrmXml){
		
		int sourceConnSize = owner.sourceConnections().size();		
				
		if(isBiDir){
			jpaDiagramEditor.activateTool(relationFeatureName, 1);
		} else {
			jpaDiagramEditor.activateTool(relationFeatureName, 0);
		}
		jpaDiagramEditor.click(owner);
				
		jpaDiagramEditor.select(inverse);
		jpaDiagramEditor.click(inverse);
		
		try {
			bot.waitUntil(new ConnectionIsShown(owner, sourceConnSize), 20000);
		} catch (TimeoutException e) {
			jpaDiagramEditor.click(owner);
			jpaDiagramEditor.click(50, 50);
			
			bot.waitUntil(new ConnectionIsShown(owner, sourceConnSize), 20000);
		}
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertDerivedIdConnectionIsCreated(owner, inverse, isBiDir);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		if(isBiDir){
			assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		} else {
			assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		}
		assertEquals(reltype, rel.getRelType());

		String ownerAttributeName = testOwnerRelationAttributeProperties(rel, mappingKey, isOrmXml);
		String inverseAttributeName = null;
		if(isBiDir){
			inverseAttributeName = testInverseRelationAttributeProperties(rel, getInverseMappingKey(mappingKey), isOrmXml);
		}

		SWTBotGefEditPart attribute = jpaDiagramEditor
				.getEditPart(ownerAttributeName);
		attribute.select();
		attribute.click();
		
		PersistentAttribute jpa = getJPAObjectForGefElement(attribute);
		AttributeMapping attributeMapping = jpaFactory.getAttributeMapping(jpa);
		
		if(relationAnnotation.equals("OneToOne")){
			assertTrue(attributeMapping instanceof OneToOneMapping2_0);
		} else {
			assertTrue(attributeMapping instanceof ManyToOneMapping2_0);
		}
		
		assertTrue(attributeMapping instanceof SingleRelationshipMapping2_0);
		DerivedIdentity2_0 identity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
		if(derivedIdAnnotation.equals("Id")) {
			assertTrue(identity.usesIdDerivedIdentityStrategy());
		} else {
			assertTrue(identity.usesMapsIdDerivedIdentityStrategy());
		}
		
		String helperAttributeName = null;
		if(!isSamePK) {
			helperAttributeName = assertContainsHelperAttrInEmbeddable(inverse,	embeddable, jpa, idClassFQN, isOrmXml);
		}
		
		if(isBiDir){
			assertBiDirRelationIsNotDeleted(owner, inverse, connection, ownerAttributeName, inverseAttributeName, true);
			assertBiDirRelationIsDeleted(owner, inverse, connection, ownerAttributeName, inverseAttributeName, isOrmXml);
		} else {
			assertUniDirRelationIsNotDeleted(owner, inverse, connection, ownerAttributeName, true);
			assertUniDirRelationIsDeleted(owner, inverse, connection, ownerAttributeName, isOrmXml);
		}
		
		if(!isSamePK)
			assertNull(getAttributeInPE(embeddable, helperAttributeName));
	}
	
	public void testUniDirDerivedIdWithIdClassPk(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse, JavaPersistentType idClass,
			RelType reltype, String relationAnnotation, String derivedIdAnnotation, String mappingKey,
			boolean isBiDir, boolean isSamePK, String idClassFQN, boolean isOrmXml) throws JavaModelException{
		if(isBiDir){
			jpaDiagramEditor.activateTool(relationFeatureName, 1);
		} else {
			jpaDiagramEditor.activateTool(relationFeatureName, 0);
		}
		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);
		
		bot.waitUntil(new ConnectionIsShown(owner), 10000);
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertDerivedIdConnectionIsCreated(owner, inverse, isBiDir);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		if(isBiDir){
			assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		} else {
			assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		}
		assertEquals(reltype, rel.getRelType());

		String ownerAttributeName = testOwnerRelationAttributeProperties(rel, mappingKey, isOrmXml);
		String inverseAttributeName = null;
		if(isBiDir){
			inverseAttributeName = testInverseRelationAttributeProperties(rel, getInverseMappingKey(mappingKey), isOrmXml);
		}

		SWTBotGefEditPart attribute = jpaDiagramEditor
				.getEditPart(ownerAttributeName);
		attribute.select();
		attribute.click();
		
		PersistentAttribute jpa = getJPAObjectForGefElement(attribute);
		
		AttributeMapping attributeMapping = jpaFactory.getAttributeMapping(jpa);
		
		if(relationAnnotation.equals("OneToOne")){
			assertTrue(attributeMapping instanceof OneToOneMapping2_0);
		} else {
			assertTrue(attributeMapping instanceof ManyToOneMapping2_0);
		}
		
		assertTrue(attributeMapping instanceof SingleRelationshipMapping2_0);
		DerivedIdentity2_0 identity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
		assertTrue(identity.usesIdDerivedIdentityStrategy());
		
		String helperAttributeName = null;
		if(!isSamePK) {
			helperAttributeName = assertContainsHelperAttrInIdClass(inverse, idClass, jpa, idClassFQN);
		}
		
		if(isBiDir){
			assertBiDirRelationIsNotDeleted(owner, inverse, connection, ownerAttributeName, inverseAttributeName, true);
			assertBiDirRelationIsDeleted(owner, inverse, connection, ownerAttributeName, inverseAttributeName, isOrmXml);
		} else {
			assertUniDirRelationIsNotDeleted(owner, inverse, connection, ownerAttributeName, true);
			assertUniDirRelationIsDeleted(owner, inverse, connection, ownerAttributeName, isOrmXml);
		}
		
		if(!isSamePK) {
//			IType idClassType = JavaCore.createCompilationUnitFrom(idClass).findPrimaryType();
			assertNull(idClass.getAttributeNamed(helperAttributeName));
		}
	}

	private String assertContainsHelperAttrInEmbeddable(
			SWTBotGefEditPart inverse, SWTBotGefEditPart embeddable,
			PersistentAttribute jpa, String IdClassFQN, boolean isOrmXml) {
		String helperAttributeName = JPAEditorUtil.decapitalizeFirstLetter(getJPTObjectForGefElement(inverse).getSimpleName());
		
		PersistentType pt = getJPTObjectForGefElement(embeddable);
		pt.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		pt.synchronizeWithResourceModel();
		pt.update();
		PersistentAttribute helperAttr = pt.getAttributeNamed(helperAttributeName);
		assertNotNull(helperAttr);
		
		if(isOrmXml) {
			assertNotNull(jpaFactory.getORMPersistentAttribute(helperAttr));
		}
		
		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(jpa);
		assertTrue(SingleRelationshipMapping2_0.class.isInstance(attributeMapping));
		DerivedIdentity2_0 identity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
		assertTrue(identity.usesMapsIdDerivedIdentityStrategy());
		assertEquals(helperAttributeName, identity.getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
						
		String helperAttrType = JPAEditorUtil.getAttributeTypeNameWithGenerics(helperAttr);
			
		if(IdClassFQN != null){
			assertEquals(helperAttrType, IdClassFQN);
		} else {
			String primaryKeyType = JPAEditorUtil.getAttributeTypeNameWithGenerics(getJPAObjectForGefElement(getAttributeInPE(inverse, "id")));
			assertEquals(helperAttrType, primaryKeyType);
		}
		return helperAttributeName;
	}
	
	
	private String assertContainsHelperAttrInIdClass(
			SWTBotGefEditPart inverse, JavaPersistentType idClass,
			PersistentAttribute jpa, String IdClassFQN) throws JavaModelException {

		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(jpa);
		assertTrue(SingleRelationshipMapping2_0.class.isInstance(attributeMapping));
		DerivedIdentity2_0 identity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
		assertTrue(identity.usesIdDerivedIdentityStrategy());

		JavaSpecifiedPersistentAttribute helperAttr = idClass.getAttributeNamed(jpa.getName());
		assertNotNull(helperAttr);		
	
		String helperAttrType = helperAttr.getTypeName();
			
		if(IdClassFQN != null){
			assertEquals(helperAttrType, IdClassFQN);
		} else {
			String primaryKeyType = JPAEditorUtil.getAttributeTypeNameWithGenerics(getJPAObjectForGefElement(getAttributeInPE(inverse, "id")));
			assertEquals(helperAttrType, primaryKeyType);
		}
		return helperAttr.getName();
	}
	
	public SWTBotGefEditPart getAttributeInPE(
			SWTBotGefEditPart dependentEntity, String embeddedAttributeName) {
		List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
		editParts.add(dependentEntity);
		SWTBotGefEditPart embeddedAttribute = jpaDiagramEditor.getEditpart(
				embeddedAttributeName, editParts);
		return embeddedAttribute;
	}
	
	public void testBiDirDerivedIdWithIdAnnotation(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse,
			RelType reltype, String relationAnnotation, String derivedIdAnnotation, String mappingKey, boolean isOrmXml){
		jpaDiagramEditor
		.activateTool(relationFeatureName, 1);
		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);
		
		bot.waitUntil(new ConnectionIsShown(owner));
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertDerivedIdConnectionIsCreated(owner, inverse, true);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());

		String ownerAttributeName = testOwnerRelationAttributeProperties(rel, mappingKey, isOrmXml);
		String inverseAttributeName = testInverseRelationAttributeProperties(rel, getInverseMappingKey(mappingKey), isOrmXml);
		
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		jpaDetailsView.show();
		assertTrue("JPA Details view must be opened!",
				jpaDetailsView.isActive());

		SWTBotGefEditPart attribute = jpaDiagramEditor
				.getEditPart(ownerAttributeName);
		attribute.select();
		attribute.click();
		
		PersistentAttribute jpa = getJPAObjectForGefElement(attribute);

		AttributeMapping attributeMapping = jpaFactory.getAttributeMapping(jpa);
		
		if(relationAnnotation.equals("OneToOne")){
			assertTrue(attributeMapping instanceof OneToOneMapping2_0);
		} else {
			assertTrue(attributeMapping instanceof ManyToOneMapping2_0);
		}
		
		assertTrue(attributeMapping instanceof SingleRelationshipMapping2_0);
		DerivedIdentity2_0 identity = ((SingleRelationshipMapping2_0)attributeMapping).getDerivedIdentity();
		if(derivedIdAnnotation.equals("Id")) {
			assertTrue(identity.usesIdDerivedIdentityStrategy());
		} else {
			assertTrue(identity.usesMapsIdDerivedIdentityStrategy());
		}
		
		assertBiDirRelationIsNotDeleted(owner, inverse, connection, ownerAttributeName, inverseAttributeName, true);
		
		assertBiDirRelationIsDeleted(owner, inverse, connection, ownerAttributeName, inverseAttributeName, isOrmXml);
	}
	
	private String getInverseMappingKey(String mappingKey){
		String inverseMappingKey = null;
		if(mappingKey.equals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)){
			inverseMappingKey = MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
		} else if (mappingKey.equals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY)){
			inverseMappingKey = MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
		} else {
			inverseMappingKey = mappingKey;
		}
		
		return inverseMappingKey;
	}
	
	public void testUniDirDerivedIdWithMapsIdAnnotation(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse,
			RelType reltype, String mappingKey, boolean isOrmXml){
		jpaDiagramEditor
		.activateTool(relationFeatureName, 0);
		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);
		
		bot.waitUntil(new ConnectionIsShown(owner));
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertDerivedIdConnectionIsCreated(owner, inverse, false);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());

		String ownerAttributeName = testOwnerRelationAttributeProperties(rel, mappingKey, isOrmXml);
		
		SWTBotView jpaDetailsView = workbenchBot.viewByTitle("JPA Details");
		jpaDetailsView.show();
		assertTrue("JPA Details view must be opened!",
				jpaDetailsView.isActive());

		SWTBotGefEditPart attribute = jpaDiagramEditor
				.getEditPart(ownerAttributeName);
		attribute.select();
		attribute.click();
		
		PersistentAttribute jpa = getJPAObjectForGefElement(attribute);
		
		AttributeMapping attributeMapping = jpaFactory.getAttributeMapping(jpa);
		assertTrue(attributeMapping instanceof OneToOneMapping2_0);
		
		DerivedIdentity2_0 identity = ((OneToOneMapping2_0)attributeMapping).getDerivedIdentity();
		assertTrue(identity.usesMapsIdDerivedIdentityStrategy());
//		
//		HashSet<String> annotations = getAnnotationNames(jpa);
//		assertTrue(annotations.contains("OneToOne"));
//		assertTrue(annotations.contains("MapsId"));
	}

	public void testSelfUniDirRelation(String relationFeatureName,
			SWTBotGefEditPart entity, RelType reltype, String mappingKey, boolean canBeDerivedIdFeature, boolean isOrmXml) {

		if(canBeDerivedIdFeature) {
			jpaDiagramEditor.activateTool(relationFeatureName, 2);
		} else {
			jpaDiagramEditor.activateTool(relationFeatureName, 0);
		}
		jpaDiagramEditor.click(entity);
		jpaDiagramEditor.click(entity);
		bot.waitUntil(new ConnectionIsShown(entity));
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

//		assertSelfConnectionIsCreated(entity);

		SWTBotGefConnectionEditPart connection = getConnection(entity, entity);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());
		
		String attributeName = testOwnerRelationAttributeProperties(rel, mappingKey, isOrmXml);
		assertNull(rel.getInverseAnnotatedAttribute());

		assertSelfUniDirRelationIsNotDeleted(entity, connection, attributeName);

		assertSelfUniDirRelationIsDeleted(entity, connection, attributeName, isOrmXml);
	}

	public void testUniDirRelRemoveOwnerAttribute(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse,
			RelType reltype, String mappingKey, boolean canBeDerivedIdFeature, boolean isOrmXml) {

		if(canBeDerivedIdFeature) {
			jpaDiagramEditor.activateTool(relationFeatureName, 2);
		} else {
			jpaDiagramEditor.activateTool(relationFeatureName, 0);
		}
		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);
		bot.waitUntil(new ConnectionIsShown(owner));
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertConnectionIsCreated(owner, inverse, false);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());

		String attributeName = testOwnerRelationAttributeProperties(rel, mappingKey, isOrmXml);
		assertNull(rel.getInverseAnnotatedAttribute());

		// delete the owner attribute
		SWTBotGefEditPart ownerAttrPart = jpaDiagramEditor
				.getEditPart(attributeName);
		PersistentAttribute jpa = getJPAObjectForGefElement(ownerAttrPart);
		ownerAttrPart.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, attributeName),
				10000);
		// assert that the connection does not exists anymore
		assertTrue(owner.sourceConnections().isEmpty());
		assertTrue(inverse.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(attributeName));
		if(isOrmXml){
			assertOrmAttrIsDeleted(jpa);
		}
		assertFalse(isSectionVisible(owner,
				JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}

	public void testBiDirRel(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse,
			RelType reltype, String mappingKey, boolean canBeDerivedIdFeature, boolean isOrmXml) {
		testBiDirRelWithTwoMappingTypes(relationFeatureName, owner, inverse,
				reltype, mappingKey, mappingKey, canBeDerivedIdFeature, isOrmXml);
	}

	public void testSelfBiDirRel(String relationFeatureName,
			SWTBotGefEditPart owner, RelType reltype, String mappingKey, boolean canBeDerivedIdFeature, boolean isOrmXml) {
		testSelfBiDirRelWithTwoMappings(relationFeatureName, owner, reltype,
				mappingKey, mappingKey, canBeDerivedIdFeature, isOrmXml);
	}

	public void testBiDirRelWithTwoMappingTypes(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse,
			RelType reltype, String ownerMappingKey, String inverseMappingKey, boolean isSimpleRelKind, boolean isOrmXml) {

		if(isSimpleRelKind) {
			jpaDiagramEditor.activateTool(relationFeatureName, 3);
		} else {
			jpaDiagramEditor.activateTool(relationFeatureName, 1);
		}
		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);
		bot.waitUntil(new ConnectionIsShown(owner));
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertConnectionIsCreated(owner, inverse, true);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());

		String ownerAttributeName = testOwnerRelationAttributeProperties(rel, ownerMappingKey, isOrmXml);

		String inverseAttributeName = testInverseRelationAttributeProperties(rel, inverseMappingKey, isOrmXml);

		assertBiDirRelationIsNotDeleted(owner, inverse, connection,
				ownerAttributeName, inverseAttributeName, false);

		assertBiDirRelationIsDeleted(owner, inverse, connection,
				ownerAttributeName, inverseAttributeName, isOrmXml);
	}

	public void testSelfBiDirRelWithTwoMappings(String relationFeatureName,
			SWTBotGefEditPart entity, RelType reltype, String ownerMappingKey,
			String inverseMappingKey, boolean canBeDerivedIdFeature, boolean isOrmXml) {

		if(canBeDerivedIdFeature) {
			jpaDiagramEditor.activateTool(relationFeatureName, 3);
		} else {
			jpaDiagramEditor.activateTool(relationFeatureName, 1);
		}
		jpaDiagramEditor.click(entity);
		jpaDiagramEditor.click(entity);
		bot.waitUntil(new ConnectionIsShown(entity));
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertSelfConnectionIsCreated(entity);

		SWTBotGefConnectionEditPart connection = getConnection(entity, entity);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());

		String ownerAttributeName = testOwnerRelationAttributeProperties(rel, ownerMappingKey, isOrmXml);

		String inverseAttributeName = testInverseRelationAttributeProperties(rel, inverseMappingKey, isOrmXml);

		assertSelfBiDirRelationIsNotDeleted(entity, connection,
				ownerAttributeName, inverseAttributeName);

		assertSelfBiDirRelationIsDeleted(entity, connection,
				ownerAttributeName, inverseAttributeName, isOrmXml);
	}

	public void testBiDirRelRemoveInverseAttribute(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse,
			RelType reltype, String linkLabel, boolean canBeDerivedIdFeature, boolean isOrmXml) {

		testBiDirRelWithTwoMappingsWithoutInverseAttr(relationFeatureName,
				owner, inverse, reltype, linkLabel, linkLabel, canBeDerivedIdFeature, isOrmXml);
	}

	public void testBiDirRelWithTwoMappingsWithoutInverseAttr(
			String relationFeatureName, SWTBotGefEditPart owner,
			SWTBotGefEditPart inverse, RelType reltype, String ownerMappingKey,
			String inverseMappingKey, boolean canBeDerivedIdFeature, boolean isOrmXml) {

		if(canBeDerivedIdFeature) {
			jpaDiagramEditor.activateTool(relationFeatureName, 3);
		} else {
			jpaDiagramEditor.activateTool(relationFeatureName, 1);
		}
		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);
		bot.waitUntil(new ConnectionIsShown(owner));
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertConnectionIsCreated(owner, inverse, true);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());

		String ownerAttributeName = testOwnerRelationAttributeProperties(rel, ownerMappingKey, isOrmXml);

		String inverseAttributeName = testInverseRelationAttributeProperties(rel, inverseMappingKey, isOrmXml);

		// delete the inverse attribute
		SWTBotGefEditPart inverseAttr = jpaDiagramEditor
				.getEditPart(inverseAttributeName);
		inverseAttr.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();

		waitASecond();
		// assert that the connection still exists, but it is unidirectional now
		assertConnectionIsCreated(owner, inverse, false);
		connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.UNI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());
		assertEquals(ownerAttributeName,
				testOwnerRelationAttributeProperties(rel, ownerMappingKey, isOrmXml));
		assertNull(rel.getInverseAnnotatedAttribute());

		// delete the owner attribute
		SWTBotGefEditPart ownerAttr = jpaDiagramEditor
				.getEditPart(ownerAttributeName);
		ownerAttr.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor,
				ownerAttributeName), 10000);
		// assert that the connection does not exists anymore
		assertTrue(owner.sourceConnections().isEmpty());
		assertTrue(inverse.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		assertNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse(isSectionVisible(owner,
				JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		assertFalse(isSectionVisible(inverse,
				JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}

	public void testBiDirRelRemoveOwnerAttr(String relationFeatureName,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse,
			RelType reltype, String linkLabel, boolean canBeDerivedIdFeature, boolean isOrmXml) {
		testBiDirRelWithTwoMappingsWithoutOwnerAttr(relationFeatureName, owner,
				inverse, reltype, linkLabel, linkLabel, canBeDerivedIdFeature, isOrmXml);
	}

	public void testBiDirRelWithTwoMappingsWithoutOwnerAttr(
			String relationFeatureName, SWTBotGefEditPart owner,
			SWTBotGefEditPart inverse, RelType reltype, String ownerMappingKey,
			String inverseMappingKey, boolean canBeDerivedIdFeature, boolean isOrmXml) {

		if(canBeDerivedIdFeature) {
			jpaDiagramEditor.activateTool(relationFeatureName, 3);
		} else {
			jpaDiagramEditor.activateTool(relationFeatureName, 1);
		}
		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);
		bot.waitUntil(new ConnectionIsShown(owner));
		
		jpaDiagramEditor.save();

		waitASecond();
		jpaDiagramEditor.activateDefaultTool();

		assertConnectionIsCreated(owner, inverse, true);

		SWTBotGefConnectionEditPart connection = getConnection(owner, inverse);
		assertNotNull("Connection must be shown in the diagram.", connection);
		IRelation rel = getConnection(connection);
		assertNotNull(rel);
		assertEquals(IRelation.RelDir.BI, rel.getRelDir());
		assertEquals(reltype, rel.getRelType());

		String ownerAttributeName = testOwnerRelationAttributeProperties(rel, ownerMappingKey, isOrmXml);

		String inverseAttributeName = testInverseRelationAttributeProperties(rel, inverseMappingKey, isOrmXml);

		// delete the owner attribute
		SWTBotGefEditPart ownerAttrPart = jpaDiagramEditor
				.getEditPart(ownerAttributeName);
		ownerAttrPart.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor,
				ownerAttributeName), 10000);
		// assert that the connection does not exists anymore
		assertTrue(owner.sourceConnections().isEmpty());
		assertTrue(inverse.targetConnections().isEmpty());
		assertNull(jpaDiagramEditor.getEditPart(ownerAttributeName));
		// assert that the inverse attribute still exists
		assertNotNull(jpaDiagramEditor.getEditPart(inverseAttributeName));
		assertFalse(isSectionVisible(owner,
				JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		// assert that the inverse attribute still exists
		assertTrue(isSectionVisible(inverse,
				JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
	}

	public void createInheritedEntity(SWTBotGefEditPart superclass,
			JpaProject jpaProject, String superclassMappingLinkLabel,
			boolean byMappedSuperclass, boolean existing) {

		String superclassName = getJPTObjectForGefElement(superclass)
				.getSimpleName();

		String subclassName = getUniqueEntityName(jpaProject);
		jpaDiagramEditor
				.activateTool(JPAEditorMessages.CreateJPAEntityFromMappedSuperclassFeature_createInheritedEntityFeatureName);
		jpaDiagramEditor.click(superclass);
		jpaDiagramEditor.click(50, 200);

		bot.waitUntil(new ElementIsShown(jpaDiagramEditor, subclassName), 10000);

		SWTBotGefEditPart inheritedEntity = jpaDiagramEditor
				.getEditPart(subclassName);
		assertNotNull(inheritedEntity);
		testCreateAndDeleteIsARelation(superclass, subclassName,
				superclassMappingLinkLabel, byMappedSuperclass, superclassName,
				inheritedEntity, existing);
	}

	public void testCreateAndDeleteIsARelation(SWTBotGefEditPart superclass,
			String subclassName, String superclassMappingKey,
			boolean byMappedSuperclass, String superclassName,
			SWTBotGefEditPart inheritedEntity, boolean existing) {

		SWTBotGefConnectionEditPart connection = testIsARelationProperties(
				superclass, subclassName, superclassMappingKey,
				byMappedSuperclass, superclassName, inheritedEntity, existing);

		connection.select();
		jpaDiagramEditor.clickContextMenu("Delete");
		confirmDelete();
		waitASecond();
		assertTrue(superclass.sourceConnections().isEmpty());
		assertTrue(inheritedEntity.targetConnections().isEmpty());

		assertNull("The entity must not has a super persitent type.",
				getJPTObjectForGefElement(inheritedEntity)
						.getSuperPersistentType());
	}

	public SWTBotGefConnectionEditPart testIsARelationProperties(
			SWTBotGefEditPart superclass, String subclassName,
			String mappingKey, boolean byMappedSuperclass,
			String superclassName, SWTBotGefEditPart inheritedEntity,
			boolean existing) {
		if (byMappedSuperclass) {
			assertTrue(isSectionVisible(inheritedEntity,
					JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));
		} else {
			assertFalse(isSectionVisible(inheritedEntity,
					JPAEditorMessages.AddJPAEntityFeature_primaryKeysShape));
		}
		assertFalse(isSectionVisible(inheritedEntity,
				JPAEditorMessages.AddJPAEntityFeature_relationAttributesShapes));
		if (existing) {
			assertTrue(isSectionVisible(inheritedEntity,
					JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		} else {
			assertFalse(isSectionVisible(inheritedEntity,
					JPAEditorMessages.AddJPAEntityFeature_basicAttributesShapes));
		}

		jpaDiagramEditor.activateDefaultTool();

		assertIsARelationExists(superclass, inheritedEntity);

		SWTBotGefConnectionEditPart connection = getConnection(inheritedEntity, superclass);
		assertNotNull("Connection must be shown in the diagram.", connection);
		FreeFormConnection conn = (FreeFormConnection) connection.part().getModel();
		assertTrue(IsARelation.isIsAConnection(conn));
		IsARelation rel = getIsARelationship();
		assertNotNull(rel);

		PersistentType subclassType = getJPTObjectForGefElement(inheritedEntity);
		PersistentType superclassType = getJPTObjectForGefElement(superclass);
		assertEquals(subclassType, rel.getSubclass());
		assertEquals(superclassType, rel.getSuperclass());

		
		assertEquals(mappingKey, jpaFactory.getTypeMapping(superclassType).getKey());
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, jpaFactory.getTypeMapping(subclassType).getKey());

		PersistentType superJPT = subclassType.getSuperPersistentType();
		assertNotNull("The entity must has a super persitent type.", superJPT);
		assertEquals(
				"The super persistent type must be the source of the connection.",
				superJPT.getName(), superclassType.getName());

		return connection;
	}

	public void testNoConnectionIsCreated(String relationFeatureName,
			int indexInPallete, SWTBotGefEditPart owner,
			SWTBotGefEditPart inverse) {

		jpaDiagramEditor.activateTool(relationFeatureName, indexInPallete);

		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);

		waitASecond();

		assertTrue("There is no connection created.", owner.sourceConnections()
				.isEmpty());
		assertTrue("There is no connection created.", inverse
				.sourceConnections().isEmpty());
		assertTrue("There is no connection.", inverse.targetConnections()
				.isEmpty());
	}

	public void testNoConnectionIsCreatedWithEmbeddable(
			String relationFeatureName, int indexInPallete,
			SWTBotGefEditPart owner, SWTBotGefEditPart inverse) {

		jpaDiagramEditor.activateTool(relationFeatureName, indexInPallete);

		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);

		waitASecond();

		assertTrue("There is no connection created.", owner.sourceConnections()
				.isEmpty());
		assertTrue("There is no connection created.", inverse
				.sourceConnections().isEmpty());
	}

	public void testNoEmbeddedConnectionIsCreated(String relationFeatureName,
			int indexInPallete, SWTBotGefEditPart owner,
			SWTBotGefEditPart inverse, boolean alreadyEmbed) {

		jpaDiagramEditor.activateTool(relationFeatureName, indexInPallete);

		jpaDiagramEditor.click(owner);
		jpaDiagramEditor.click(inverse);

		waitASecond();

		assertTrue("There is no connection created.", owner.sourceConnections()
				.isEmpty());
		if (alreadyEmbed) {
			assertFalse("There is no connection created.", inverse
					.sourceConnections().isEmpty());
		} else {
			assertTrue("There is no connection created.", inverse
					.sourceConnections().isEmpty());
		}
		assertTrue("There is no connection.", inverse.targetConnections()
				.isEmpty());
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
	public void _testEmbeddedConnection(String toolEntry,
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

		deleteAttributeInJPT(embeddingEntity, attributeName);

		waitASecond();

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
	public String embedConnection(String toolEntry,
			SWTBotGefEditPart embeddingEntity, SWTBotGefEditPart embeddable,
			HasReferenceType refType, String embeddedMappingKey,
			String linkLabel, int elementsInDiagramCount) {
		
		jpaDiagramEditor.activateTool(toolEntry);
		
		jpaDiagramEditor.click(embeddingEntity);
		
		waitASecond();
		
		jpaDiagramEditor.click(embeddable);

		String attributeName = checkEmbeddedConnectionProperties(
				embeddingEntity, embeddable, refType, embeddedMappingKey,
				linkLabel);
		return attributeName;
	}

	public String embedConnection(SWTBotGefEditor gefEditor, String toolEntry,
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

	private SWTBotGefConnectionEditPart getConnection(SWTBotGefEditPart start,
			SWTBotGefEditPart end) {
		List<SWTBotGefConnectionEditPart> connections = start
				.sourceConnections();
		assertFalse(connections.isEmpty());
		for (SWTBotGefConnectionEditPart connection : connections) {
			FreeFormConnection freeFormConnection = (FreeFormConnection) connection.part().getModel();
			if (freeFormConnection.getStart() != null && freeFormConnection.getEnd() != null) {
				PictogramElement startPe = freeFormConnection.getStart().getParent();
				PictogramElement endPe = freeFormConnection.getEnd().getParent();
				if (startPe.equals(start.part().getModel())	&& endPe.equals(end.part().getModel()))
					return connection;
			}
		}

		return null;
	}

	public String checkEmbeddedConnectionProperties(
			SWTBotGefEditPart embeddingEntity, SWTBotGefEditPart embeddable,
			HasReferenceType refType, String embeddedMappingKey,
			String linkLabel) {

		bot.waitUntil(new ConnectionIsShown(embeddingEntity), 30000);
		
		jpaDiagramEditor.save();

		assertFalse("The connection must appear", embeddingEntity
				.sourceConnections().isEmpty());
		SWTBotGefConnectionEditPart connection = getConnection(embeddingEntity,
				embeddable);
		assertNotNull("Connection must be shown in the diagram.", connection);

		PersistentType emb = getJPTObjectForGefElement(embeddable);
		String embAttr = JPAEditorUtil.decapitalizeFirstLetter(emb
				.getSimpleName());

		List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
		editParts.add(embeddingEntity);
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditpart(embAttr,
				editParts);

		assertNotNull("Embedded attribute must be added.", attribute);

		HasReferanceRelation rel = getHasReferenceConnection(connection,
				attribute);
		assertNotNull("Such a connection must exist.", rel);
		assertEquals(refType, rel.getReferenceType());

		String attributeName = testEmbeddedAttributeProperties(rel,
				embeddedMappingKey);
		assertNotNull(rel.getEmbeddedAnnotatedAttribute());

		PersistentType parententity =  rel.getEmbeddedAnnotatedAttribute().getDeclaringPersistentType();
		assertEquals("The entity must contain an embedded attribute.",
				parententity, getJPTObjectForGefElement(embeddingEntity));
		assertEquals(rel.getEmbeddingEntity(),
				getJPTObjectForGefElement(embeddingEntity));
		assertEquals(rel.getEmbeddable(), getJPTObjectForGefElement(embeddable));
		return attributeName;
	}

	public void setJpaDiagramEditor(SWTBotGefEditor jpaDiagramEditor) {
		this.jpaDiagramEditor = jpaDiagramEditor;
	}

	public String getUniqueAttrName(SWTBotGefEditPart jptType) {
		IJPAEditorFeatureProvider fp = (IJPAEditorFeatureProvider) ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();
		PersistentType jpt = getJPTObjectForGefElement(jptType);
		String attrName = JpaArtifactFactory.instance().genUniqueAttrName(jpt,
				"java.lang.String", fp);

		return attrName;
	}

	public String getUniqueMappedSuperclassName(JpaProject jpaProject) {
		IJPAEditorFeatureProvider fp = (IJPAEditorFeatureProvider) ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();

		String mappedSuperclassName = fp.getJPAEditorUtil()
				.generateUniqueTypeName(
						jpaProject,
						JPADiagramPropertyPage.getDefaultPackage(jpaProject
								.getProject()), ".MpdSuprcls", fp); //$NON-NLS-1$

		return JPAEditorUtil.returnSimpleName(mappedSuperclassName);
	}

	public String getUniqueEntityName(JpaProject jpaProject) {
		IJPAEditorFeatureProvider fp = (IJPAEditorFeatureProvider) ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();
		
		if(fp == null){
			System.out.println("ibi goooo ");
			jpaDiagramEditor.isActive();
		}

		assertNotNull(fp);
		assertNotNull(fp.getJPAEditorUtil());
		String entityName = fp.getJPAEditorUtil().generateUniqueTypeName(
				jpaProject,
				JPADiagramPropertyPage.getDefaultPackage(jpaProject
						.getProject()), ".Entity", fp); //$NON-NLS-1$

		return JPAEditorUtil.returnSimpleName(entityName);
	}

	public String getUniqueEmbeddableName(JpaProject jpaProject) {
		IJPAEditorFeatureProvider fp = (IJPAEditorFeatureProvider) ((DiagramEditPart) jpaDiagramEditor
				.mainEditPart().part()).getFeatureProvider();

		String embeddableName = fp.getJPAEditorUtil().generateUniqueTypeName(
				jpaProject,
				JPADiagramPropertyPage.getDefaultPackage(jpaProject
						.getProject()), ".Embeddable", fp); //$NON-NLS-1$

		return JPAEditorUtil.returnSimpleName(embeddableName);
	}

	public void deleteResources(JpaProject jpaProject, boolean isOrmXml) throws CoreException {
		deleteDiagramElements(isOrmXml);
		Utils.printFormatted(">>>>>>>>>>>> elements are deleted from the diagram.");

		ListIterator<PersistenceUnit> lit = jpaProject.getContextModelRoot()
				.getPersistenceXml().getRoot().getPersistenceUnits().iterator();
		PersistenceUnit pu = lit.next();
		Iterator<PersistentType> persistentTypesIterator = (Iterator<PersistentType>) pu
				.getPersistentTypes().iterator();
		while (persistentTypesIterator.hasNext()) {
			Utils.printFormatted(">>>>>>>>>>>>>> persistent type resource must be deleted.");
			PersistentType type = persistentTypesIterator.next();
			type.getResource().delete(true, new NullProgressMonitor());
		}
		
		jpaProject.getResource().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
	}
	
	public void deleteResources(JpaProject jpaProject, String name){
		Utils.printFormatted(">>>>>>>>>>>> elements are deleted from the diagram.");		
		try {
			jpaProject.getProject().getFile(name).delete(true, new NullProgressMonitor());
			jpaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Embed the given embeddedable into the given embedding entity and change the mapping of the
	 * embedded attribute to EmbeddedId.
	 * @param embeddingEntity
	 * @param embeddable
	 */
	public void addEmbeddedIdToEntity(SWTBotGefEditPart embeddingEntity,
			SWTBotGefEditPart embeddable, boolean isOrmXml) {
		
		jpaDiagramEditor.activateDefaultTool();
		embeddingEntity.select();
		embeddingEntity.click();
		
		String embeddedAttributeName = embedConnection(JPAEditorMessages.EmbedSingleObjectFeature_EmbeddedFeatureName, embeddingEntity,
				embeddable, HasReferenceType.SINGLE,
				MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
				JptJpaUiDetailsMessages.EmbeddedMappingUiProvider_linkLabel,
				3);
				
		SWTBotGefEditPart embeddedAttribute = getAttributeInPE(embeddingEntity,
				embeddedAttributeName);
		
		assertNotNull(embeddedAttribute);
		PersistentAttribute jpa = getJPAObjectForGefElement(embeddedAttribute);
//		jpa.getJavaPersistentAttribute().setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
//		
//		
			PersistentAttribute ormAttribute = jpaFactory.getORMPersistentAttribute(jpa);
			if(isOrmXml) {
				assertNotNull(ormAttribute);
				((OrmSpecifiedPersistentAttribute)ormAttribute).setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
			} else {
				jpa.getJavaPersistentAttribute().setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);

			}

	}
	
	/**
	 * Delete the default primary key from the selected entity in the diagram
	 * @param entity - the entity from which the primary key to be deleted
	 */
	public void deleteEntityDefaultPK(
			SWTBotGefEditPart entity, boolean isOrmXml) {
		List<SWTBotGefEditPart> editParts = new ArrayList<SWTBotGefEditPart>();
		editParts.add(entity);
		SWTBotGefEditPart attribute = jpaDiagramEditor.getEditpart(
				"id", editParts);
		
		PersistentAttribute pa = getJPAObjectForGefElement(attribute);
		if(isOrmXml) {
			OrmPersistentType ormJpt = ormXml.getPersistentType(pa.getDeclaringPersistentType().getName());
			assertNotNull(ormJpt);
			OrmPersistentAttribute ormAttr = ormJpt.getAttributeNamed(pa.getName());
			assertNotNull(ormAttr);
		}		
		
		pressAttributeDeleteContextButton(attribute);
		confirmDelete();
		bot.waitUntil(new ElementDisappears(jpaDiagramEditor, entity, "id"),
				20000);
		
		jpaDiagramEditor.save();
		
		attribute = jpaDiagramEditor.getEditpart(
				"id", editParts);
		assertNull("Attribute must be deleted!", attribute);
		if(isOrmXml) {
			assertOrmAttrIsDeleted(pa);
		}
	}
	
	/**
	 * Creates a new simple java class and set this class as IDClass for the given entity.
	 * @param entity - entity to which the idClass annotation will be added.
	 * @param idClassName - the name of the java class
	 * @param jpaProject - the given jpa project
	 * @return the simple java class file.
	 * @throws IOException
	 * @throws CoreException
	 * @throws JavaModelException
	 */
	public JavaPersistentType setIdClass(SWTBotGefEditPart entity, String idClassName, JpaProject jpaProject, boolean create)
			throws IOException, CoreException, JavaModelException {
		if(create) {
			JPACreateFactory.instance().createIdClassInProject(jpaProject.getProject(), new String[] {"org", "persistence"}, idClassName);
		}
		
		PersistentType jptType= getJPTObjectForGefElement(entity);
		
		Command command = new SetIdClassCommand(jptType, idClassName);
		try {
			getJpaProjectManager().execute(command, SynchronousUiCommandContext.instance());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		TypeMapping typeMapping = JpaArtifactFactory.instance().getTypeMapping(jptType);
		assertTrue(Entity.class.isInstance(typeMapping));
		
		jptType.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		jptType.synchronizeWithResourceModel();
		jptType.update();
		
		JavaPersistentType idClass = ((Entity)typeMapping).getIdClass();
		return idClass;
	}
	
	private JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}
	
	private class SetIdClassCommand implements Command {
		
		private PersistentType jptType;
		private String idClassName;
		
		public SetIdClassCommand(PersistentType jptType, String idClassName){
			this.jptType = jptType;
			this.idClassName = idClassName;
		}

		@Override
		public void execute() {
			idClassName = "org.persistence." + idClassName;
			TypeMapping typeMapping = JpaArtifactFactory.instance().getTypeMapping(jptType);
			assertTrue(Entity.class.isInstance(typeMapping));
			((Entity)typeMapping).getIdClassReference().setSpecifiedIdClassName(idClassName);
		}
		
	}
	
	/**
	 * Get all annotations as string for the given attribute.
	 * @param persistentAttribite
	 * @return a set of strings of all current annotation names of the given attribute.
	 */
	public HashSet<String> getAnnotationNames(PersistentAttribute persistentAttribite) {
		HashSet<String> res = new HashSet<String>();
		if(persistentAttribite != null) {
			JavaResourceAttribute jrpt = persistentAttribite.getJavaPersistentAttribute().getResourceAttribute();
			for (Annotation annotation : jrpt.getAnnotations()) {
				res.add(JPAEditorUtil.returnSimpleName(annotation.getAnnotationName()));
			}
		}
		return res;
	}

	public void setOrmXml(OrmXml ormXml) {
		this.ormXml = ormXml;
	}
}
