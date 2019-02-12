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
