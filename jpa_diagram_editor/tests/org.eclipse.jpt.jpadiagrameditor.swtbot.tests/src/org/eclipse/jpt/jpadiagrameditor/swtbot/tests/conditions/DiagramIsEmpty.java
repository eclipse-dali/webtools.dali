package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.conditions;

import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

public class DiagramIsEmpty extends DefaultCondition{
	
	private SWTBotGefEditor gefEditor;
	
	public DiagramIsEmpty(SWTBotGefEditor gefEditor){
		super();
		this.gefEditor = gefEditor;
	}

	public boolean test() throws Exception {
		return gefEditor.mainEditPart().children().isEmpty();
	}

	public String getFailureMessage() {
		return "Diagram is not empty";
	}


}
