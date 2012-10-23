package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

public class ElementDisappears extends DefaultCondition{
	
	private String name;
	private SWTBotGefEditor gefEditor;
	private SWTBotGefEditPart entity;
	
	public ElementDisappears(SWTBotGefEditor gefEditor, String name){
		this(gefEditor, null, name);

	}
	
	public ElementDisappears(SWTBotGefEditor gefEditor, SWTBotGefEditPart entity, String name){
		super();
		this.gefEditor = gefEditor;
		this.entity = entity;
		this.name = name;
	}

	@SuppressWarnings("deprecation")
	public boolean test() throws Exception {
		if(entity != null){
			List<SWTBotGefEditPart> parts = new ArrayList<SWTBotGefEditPart>();
			parts.add(entity);
			return gefEditor.getEditpart(name, parts) == null;
		}
		return gefEditor.getEditPart(name) == null;
	}

	public String getFailureMessage() {
		return "Element with name ''" + name +  "'' did not disappear.";
	}


}
