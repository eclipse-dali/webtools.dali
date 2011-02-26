/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionDelegate;

/**
 *  GenerateEntitiesAction
 */
public abstract class ObjectAction extends ActionDelegate implements IObjectActionDelegate 
{
	private ISelection currentSelection;

	public ObjectAction() {
		super();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
        this.currentSelection = selection;
	}

	@Override
	public void run(IAction action) {
		if (this.currentSelection instanceof ITreeSelection) {
			for (Iterator<?> stream = ((ITreeSelection) this.currentSelection).iterator(); stream.hasNext(); ) {
				this.execute(stream.next());
			}
		}
	}

	protected void execute(Object selection) {
		
		if(selection instanceof IFile) {
			this.execute((IFile)selection);
		}
	}

	@SuppressWarnings("unused")
	protected void execute(IFile file) {
		throw new UnsupportedOperationException();
	}

}
