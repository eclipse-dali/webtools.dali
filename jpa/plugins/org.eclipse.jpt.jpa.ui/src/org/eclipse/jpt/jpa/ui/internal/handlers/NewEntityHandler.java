/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.EntityWizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.PlatformUI;

/**
 *  NewEntityHandler
 */
public class NewEntityHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException {

		Command command = event.getCommand();

    	OpenJptWizardAction openJptWizardAction = new OpenJptWizardAction(command);
    	openJptWizardAction.run();

	    return null;
	}

	// ********** OpenJptWizardAction **********
	
	public static class OpenJptWizardAction extends AbstractOpenWizardAction {

		private final Command command; 

		public OpenJptWizardAction(Command command) {
			this.command = command;

			String commandName = null;
			try {
				commandName = this.command.getName();
			}
			catch (NotDefinedException e1) {
				commandName = "";
			}
			this.setText(commandName);  
			this.setDescription(commandName);
			this.setToolTipText(commandName);
		}
		
		@Override
		public void run() {
			Shell shell = this.getShell();
			try {
				INewWizard wizard = createWizard();
				wizard.init(PlatformUI.getWorkbench(), this.getSelection());
				
				WizardDialog dialog = new WizardDialog(shell, wizard);
				PixelConverter converter = new PixelConverter(JFaceResources.getDialogFont());
				dialog.setMinimumPageSize(converter.convertWidthInCharsToPixels(70), converter.convertHeightInCharsToPixels(20));
				dialog.create();
				int res = dialog.open();
				
				this.notifyResult(res == Window.OK);
			} 
			catch (CoreException e) {
				JptJpaUiPlugin.instance().logError(e);
			}
		}

		@Override
		protected INewWizard createWizard() throws CoreException {
			return new EntityWizard(null);
		}
	}

}