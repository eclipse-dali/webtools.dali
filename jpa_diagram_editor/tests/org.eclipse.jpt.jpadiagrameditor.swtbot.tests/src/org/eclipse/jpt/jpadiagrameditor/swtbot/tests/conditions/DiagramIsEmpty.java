/*******************************************************************************
 * Copyright (c) 2012, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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
