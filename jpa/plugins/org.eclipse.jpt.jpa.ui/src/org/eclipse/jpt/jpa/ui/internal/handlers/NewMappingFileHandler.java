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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.jpa.ui.internal.handlers.NewEntityHandler.OpenJptWizardAction;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.MappingFileWizard;
import org.eclipse.ui.INewWizard;

/**
 *  NewMappingFileHandler
 */
public class NewMappingFileHandler extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Command command = event.getCommand();

    	OpenJptWizardAction openJptWizardAction = new OpenJpaMappingFileWizardAction(command);
    	openJptWizardAction.run();

    	return null;
		}

	// ********** OpenJpaMappingFileWizardAction **********

	public static class OpenJpaMappingFileWizardAction extends OpenJptWizardAction {

		public OpenJpaMappingFileWizardAction(Command command) {
			super(command);
		}

		@Override
		protected INewWizard createWizard() throws CoreException {
			return new MappingFileWizard(null);
		}
	}
}