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

import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

public class ConnectionIsShown extends DefaultCondition{
	
	private SWTBotGefEditPart entity;
	private int sourceConnectionSize;
	
	public ConnectionIsShown(SWTBotGefEditPart entity, int sourceConnectionSize){
		super();
		this.entity = entity;
		this.sourceConnectionSize = sourceConnectionSize;
	}
	
	public ConnectionIsShown(SWTBotGefEditPart entity){
		super();
		this.entity = entity;
	}

	public boolean test() throws Exception {
		if(sourceConnectionSize != 0) {
			return ((!entity.sourceConnections().isEmpty() && (entity.sourceConnections().size() == sourceConnectionSize + 1)) || !entity.targetConnections().isEmpty());
		}
		return (!entity.sourceConnections().isEmpty() || !entity.targetConnections().isEmpty());
	}

	public String getFailureMessage() {
		return "Relationship did not show.";
	}

}

