package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.ui.editor;

import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

public class ElementIsShown extends DefaultCondition{
	
	private String entityName;
	private SWTBotGefEditor gefEditor;
	
	public ElementIsShown(SWTBotGefEditor gefEditor, String entityName){
		super();
		this.gefEditor = gefEditor;
		this.entityName = entityName;
	}

	public boolean test() throws Exception {
		return gefEditor.getEditPart(entityName) != null;
	}

	public String getFailureMessage() {
		return "Element with name ''" + entityName +  "'' did not show.";
	}

}
