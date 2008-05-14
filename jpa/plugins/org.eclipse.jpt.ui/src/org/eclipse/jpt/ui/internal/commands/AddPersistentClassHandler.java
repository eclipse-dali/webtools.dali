/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.ui.internal.dialogs.AddPersistentClassDialog;
import org.eclipse.jpt.ui.internal.selection.DefaultJpaSelection;
import org.eclipse.jpt.ui.internal.selection.JpaSelectionManager;
import org.eclipse.jpt.ui.internal.selection.SelectionManagerFactory;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddPersistentClassHandler extends AbstractHandler
{
	public Object execute(ExecutionEvent executionEvent) throws ExecutionException {
		final IWorkbenchWindow window = 
			HandlerUtil.getActiveWorkbenchWindowChecked(executionEvent);
		
		IStructuredSelection selection 
			= (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(executionEvent);
		
		// only applies for a singly selected EntityMappings object in a tree
		EntityMappings entityMappings =
			(EntityMappings) selection.getFirstElement();
		
		AddPersistentClassDialog dialog = 
			new AddPersistentClassDialog(window.getShell(), entityMappings);
		dialog.create();
		dialog.setBlockOnOpen(true);
		final OrmPersistentType type = dialog.openAndReturnType();
		
		if (type != null) {
			window.getShell().getDisplay().asyncExec(
				new Runnable() {
					public void run() {
						JpaSelectionManager selectionManager = SelectionManagerFactory.getSelectionManager(window);
						selectionManager.select(new DefaultJpaSelection(type), null);
					}
				});
		}
		
		return null;
	}
}
