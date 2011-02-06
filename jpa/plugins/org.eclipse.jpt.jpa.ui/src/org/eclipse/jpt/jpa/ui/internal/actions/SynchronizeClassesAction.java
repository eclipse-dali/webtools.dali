/*******************************************************************************
 *  Copyright (c) 2007, 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.synch.SynchronizeClassesJob;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class SynchronizeClassesAction 
	implements IObjectActionDelegate 
{
	private IFile file;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// no-op for now
	}

	public void run(IAction action) {
		SynchronizeClassesJob job = new SynchronizeClassesJob(file);
		job.schedule();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// Action is contributed for IFile's named "persistence.xml" and
		// for PeristenceXml objects.
		// There is always only one element in actual selection.
		Object selectedObject = ((StructuredSelection) selection).getFirstElement();
		
		if (selectedObject instanceof IFile) {
			file = (IFile) selectedObject;
		}
		else if (selectedObject instanceof PersistenceXml) {
			file = (IFile) ((PersistenceXml) selectedObject).getResource();
		}
	}
}
