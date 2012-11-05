package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.ui.editor;

import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

public class ElementAppearsInDiagram extends DefaultCondition{
	
	private SWTBotGefEditor gefEditor;
	
	public ElementAppearsInDiagram(SWTBotGefEditor gefEditor){
		super();
		this.gefEditor = gefEditor;
	}

	public boolean test() throws Exception {
		return !(gefEditor.mainEditPart().children().isEmpty());
	}

	public String getFailureMessage() {
		return "Diagram is still empty.";
	}

}
