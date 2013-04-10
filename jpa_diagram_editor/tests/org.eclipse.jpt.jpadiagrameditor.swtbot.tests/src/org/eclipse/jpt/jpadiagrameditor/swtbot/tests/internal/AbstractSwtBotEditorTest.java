package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellIsActive;

import java.util.Iterator;

import junit.framework.ComparisonFailure;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.ContextMenuHelper;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.EditorProxy;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.RelationshipsUtils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.After;

@SuppressWarnings("restriction")
public class AbstractSwtBotEditorTest extends SWTBotGefTestCase{
	protected static JPACreateFactory factory = JPACreateFactory.instance();
	protected static JpaProject jpaProject;

	protected static SWTGefBot bot = new SWTGefBot();
	protected static SWTWorkbenchBot workbenchBot = new SWTWorkbenchBot();
	protected static EditorProxy editorProxy;

	protected static SWTBotGefEditor jpaDiagramEditor;
	protected static RelationshipsUtils relUtils;
	protected static OrmXml ormXml;
	
	
	public static void createJPa10Project(String name) throws Exception{
		createJPa20Project(name, "1.0", false);
	}
	
	public static void createJPa20Project(String name) throws Exception{
		createJPa20Project(name, "2.0", false);
	}
	
	public static void createJPa20ProjectWithOrm(String name) throws Exception{
		createJPa20Project(name, "2.0", true);
	}
	
	private static void createJPa20Project(String name, String version, boolean withOrmXml) throws Exception{
		SWTBotPreferences.TIMEOUT = 1000;
		try {
			bot.viewByTitle("Welcome").close();
		} catch (Exception e) {
			// ignore
		} finally {
			SWTBotPreferences.TIMEOUT = 5000;
		}
		workbenchBot.perspectiveByLabel("JPA").activate();
		bot.closeAllEditors();

		factory = JPACreateFactory.instance();
		if(version.equals("1.0")){
			jpaProject = factory.createJPAProject(name);
		} else {
			jpaProject = factory.createJPA20Project(name);
		}
		assertNotNull(jpaProject);
		assertEquals(name, jpaProject.getName());
		editorProxy = new EditorProxy(workbenchBot, bot);

		if(withOrmXml) {
			JPACreateFactory.waitNonSystemJobs();
			jpaDiagramEditorPropertiesPage(jpaProject.getName());
			
			JPACreateFactory.waitNonSystemJobs();

			jpaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			//assert that the orm.xml file exists.
			ormXml = getOrmXMl(jpaProject);
			assertNotNull(ormXml);
			editorProxy.setOrmXml(ormXml);
		}

		jpaDiagramEditor = editorProxy.openDiagramOnJPAProjectNode(
				jpaProject.getName());
		editorProxy.setJpaDiagramEditor(jpaDiagramEditor);

		relUtils = new RelationshipsUtils(jpaDiagramEditor, editorProxy, jpaProject);
		if(withOrmXml) {
			assertNotNull(ormXml);
			relUtils.setOrmXml(ormXml);
		}
		
		Thread.sleep(2000);
	}
	
	private static void jpaDiagramEditorPropertiesPage(String name) {
		SWTBot propertiesPageBot = ContextMenuHelper.openProjectProperties(workbenchBot, name);
		
		//Test JPA Diagram Editor properties page
		SWTBotTree peropertiesPageTree = propertiesPageBot.tree();
		ContextMenuHelper.selectNodeInTree(peropertiesPageTree, "JPA");
		
		try{
			assertEquals("Could Not Accept Changes ", propertiesPageBot.activeShell().getText());
			propertiesPageBot.activeShell().bot().button(IDialogConstants.OK_LABEL).click();
		} catch (ComparisonFailure e) {
			
		}
		
		propertiesPageBot.comboBoxWithLabel("Type:").setSelection("Disable Library Configuration");
		propertiesPageBot.button("Apply").click();
	
		try {
			JPACreateFactory.waitNonSystemJobs();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ContextMenuHelper.selectNodeInTree(peropertiesPageTree, "JPA", "JPA Diagram Editor");
		assertFalse(propertiesPageBot.checkBoxInGroup("Add to entity mappings in XML", "XML entity mappings").isChecked());
		// assert the "XML entity mappings" group is disabled
		assertFalse(propertiesPageBot.labelInGroup("Mapping file:", "XML entity mappings").isEnabled());
		assertFalse(propertiesPageBot.textWithLabelInGroup("Mapping file:", "XML entity mappings").isEnabled());
		assertEquals("META-INF/orm.xml", propertiesPageBot.textWithLabelInGroup("Mapping file:", "XML entity mappings").getText());
		assertFalse(propertiesPageBot.buttonInGroup("Browse...", "XML entity mappings").isEnabled());
	
		propertiesPageBot.checkBoxInGroup("Add to entity mappings in XML", "XML entity mappings").select();
		// assert the "XML entity mappings" group is enabled
		assertTrue(propertiesPageBot.labelInGroup("Mapping file:", "XML entity mappings").isEnabled());
		assertTrue(propertiesPageBot.textWithLabelInGroup("Mapping file:", "XML entity mappings").isEnabled());
		assertEquals("META-INF/orm.xml", propertiesPageBot.textWithLabelInGroup("Mapping file:", "XML entity mappings").getText());
		assertTrue(propertiesPageBot.buttonInGroup("Browse...", "XML entity mappings").isEnabled());
		
		//checks that the orm.xml does not exist
		//assert that there is an error message and the OK button is disabled
		SWTBotText errorMsg = propertiesPageBot.text(1);
		assertEquals(JPAEditorMessages.JPADiagramPropertyPage_NotExistsXmlErrorMsg, errorMsg.getText());
		assertFalse(propertiesPageBot.button(IDialogConstants.OK_LABEL).isEnabled());

		//create new orm.xml with the dialogs which open, when the Browse button is pressed
		//press the Browse button
		propertiesPageBot.buttonInGroup("Browse...", "XML entity mappings").click();
		//assert that the "Mapping File" dialog appears
		workbenchBot.waitUntil(shellIsActive("Mapping File"), 10000);
		SWTBotShell createMappingdialog = workbenchBot.shell("Mapping File");
		//assert that the "Ok" button of the "Mapping File" dialog is disabled, because there is no
		//orm.xml
		assertFalse(createMappingdialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		//press the "New..." button of the "mapping File" dialog to create a new orm.xml file
		createMappingdialog.bot().button("New...").click();
		//assert that the "New Mapping File" dialog is opened.
		workbenchBot.waitUntil(shellIsActive("New Mapping File"));
		//press the "Finish" button of the "New Mapping File" dialog to create new orm.xml
		workbenchBot.shell("New Mapping File").bot().button(IDialogConstants.FINISH_LABEL).click();
		//wait the "New Mapping File" dialog to be closed.
		workbenchBot.waitUntil(shellCloses(workbenchBot.shell("New Mapping File")));
		//assert that the "Ok" button of the "Mapping File" dialog is now enabled and press it.
		assertTrue(createMappingdialog.bot().button(IDialogConstants.OK_LABEL).isEnabled());
		createMappingdialog.bot().button(IDialogConstants.OK_LABEL).click();
		//wait until the "Mapping File" dialog is closed.
		workbenchBot.waitUntil(shellCloses(createMappingdialog));
		
		//assert that there is no error message in the properties page, saying that the orm.xml file does
		//not exists.
		errorMsg = propertiesPageBot.text(1);
		assertNotSame(JPAEditorMessages.JPADiagramPropertyPage_NotExistsXmlErrorMsg, errorMsg.getText());
		//assert that the "Ok" button of the "JPA Diagram Editor" property page is already enabled
		//and press it.
		assertTrue(propertiesPageBot.button(IDialogConstants.OK_LABEL).isEnabled());		
		propertiesPageBot.button(IDialogConstants.OK_LABEL).click();
		//wait until the property page is closed.
		try {
			workbenchBot.waitUntil(shellCloses(propertiesPageBot.activeShell()));
		} catch (TimeoutException e) {
			
		}
	}
	
//	private static void createJpaProjectViaWizard(String name, String version) {
//		ContextMenuHelper.clickMenu("File", "New", "Other...");
//		SWTBot swtBot = workbenchBot.shell("New").bot();
//		ContextMenuHelper.selectTreeItem(swtBot.tree(), "JPA", "JPA Project");
//		swtBot.button(IDialogConstants.NEXT_LABEL).click();
//		
//		swtBot.textWithLabel("Project name:").setText(name);
//		swtBot.comboBoxInGroup("Target runtime").setSelection("jre6");
//		swtBot.comboBoxInGroup("JPA version").setSelection(version);
//		swtBot.button(IDialogConstants.NEXT_LABEL).click();
//		
//		swtBot.button(IDialogConstants.NEXT_LABEL).click();
//		
//		swtBot.comboBoxInGroup("Platform").setSelection("Generic 2.0");
//		swtBot.comboBoxWithLabel("Type:").setSelection("Disable Library Configuration");
//		
////		swtBot.checkBox("Create mapping file (orm.xml)").select();
//				
//		swtBot.button(IDialogConstants.FINISH_LABEL).click();
//		
//		swtBot.waitUntil(shellCloses(swtBot.activeShell()), 30000);
//	}
	
	@After
	public void tearDown() throws Exception {
		editorProxy.deleteResources(jpaProject, ormXml!=null);
	}

	private static OrmXml getOrmXMl(JpaProject jpaProject){
		jpaProject.getContextModelRoot().synchronizeWithResourceModel();
		
		try {
			JPACreateFactory.waitNonSystemJobs();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PersistenceUnit unit = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		assertNotNull(unit);
		unit.synchronizeWithResourceModel();
		unit.update();
		
		
		assertTrue(unit.getMappingFileRefsSize() > 0);
		if(unit.getMappingFileRefsSize() == 0)
			return null;

	    String ormFileName = JPADiagramPropertyPage.getOrmXmlFileName(jpaProject.getProject());	    
	    assertNotNull(ormFileName);
	    Iterator<MappingFileRef> iter = unit.getMappingFileRefs().iterator();
	    while(iter.hasNext()){
	    	MappingFileRef mapFile = iter.next();
	    	assertEquals(ormFileName, mapFile.getFileName());
	    	if(mapFile.getFileName().equals(ormFileName)){
	    		OrmXml ormXml = (OrmXml) mapFile.getMappingFile();
	    	    return ormXml;
	    	}
	    }
	   return null;
	}
}
