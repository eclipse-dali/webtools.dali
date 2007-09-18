/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.ui.internal.dialogs.AddPersistentAttributeToXmlAndMapDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class AddPersistentAttributeToXmlAndMapAction
	implements IObjectActionDelegate
{
	private Shell shell;
	
	private XmlPersistentAttribute unmappedXmlAttribute;
	
	
	public AddPersistentAttributeToXmlAndMapAction() {
		super();
	}
	
	public void run(IAction action) {
		AddPersistentAttributeToXmlAndMapDialog dialog = new AddPersistentAttributeToXmlAndMapDialog(shell, unmappedXmlAttribute);
		
		dialog.create();
		dialog.setBlockOnOpen(true);
		dialog.open();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		unmappedXmlAttribute = (XmlPersistentAttribute) ((StructuredSelection) selection).getFirstElement();
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}
}
