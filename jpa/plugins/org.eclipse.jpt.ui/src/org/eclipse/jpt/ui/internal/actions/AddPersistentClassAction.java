package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.jpt.ui.internal.dialogs.AddPersistentClassDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class AddPersistentClassAction
	implements IObjectActionDelegate
{
	private EntityMappingsInternal entityMappings;
	
	
	public AddPersistentClassAction() {
		super();
	}
	
	public void run(IAction action) {
		Shell shell = JptUiPlugin.getPlugin().getWorkbench().getActiveWorkbenchWindow().getShell();
		AddPersistentClassDialog dialog = new AddPersistentClassDialog(shell, entityMappings);
		
		dialog.create();
		dialog.setBlockOnOpen(true);
		dialog.open();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		entityMappings = (EntityMappingsInternal) ((StructuredSelection) selection).getFirstElement();
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing
	}
}
