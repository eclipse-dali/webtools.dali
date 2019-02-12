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
