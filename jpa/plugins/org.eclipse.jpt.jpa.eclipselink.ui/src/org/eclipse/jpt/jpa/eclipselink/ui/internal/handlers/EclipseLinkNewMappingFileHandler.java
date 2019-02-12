/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.EclipseLinkMappingFileWizard;
import org.eclipse.jpt.jpa.ui.internal.handlers.NewEntityHandler.OpenJptWizardAction;
import org.eclipse.ui.INewWizard;

/**
 *  NewEclipseLinkMappingFileHandler
 */
public class EclipseLinkNewMappingFileHandler
	extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException {

			Command command = event.getCommand();

	    	OpenJptWizardAction openJptWizardAction = new OpenEclipseLinkMappingFileWizardAction(command);
	    	openJptWizardAction.run();
		    	
			return null;
		}

	// ********** OpenEclipseLinkMappingFileWizardAction **********

	public static class OpenEclipseLinkMappingFileWizardAction extends OpenJptWizardAction {

		public OpenEclipseLinkMappingFileWizardAction(Command command) {
			super(command);
		}

		@Override
		protected INewWizard createWizard() throws CoreException {
			return new EclipseLinkMappingFileWizard(null);
		}
	}
}
