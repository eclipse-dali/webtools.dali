/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class RemovePersistentClassAction
	implements IObjectActionDelegate
{
	private XmlPersistentType persistentType;
	
	
	public RemovePersistentClassAction() {
		super();
	}
	
	public void run(IAction action) {
		this.persistentType.entityMappings().removeXmlPersistentType(this.persistentType);
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.persistentType = (XmlPersistentType) ((StructuredSelection) selection).getFirstElement();
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing
	}
}
