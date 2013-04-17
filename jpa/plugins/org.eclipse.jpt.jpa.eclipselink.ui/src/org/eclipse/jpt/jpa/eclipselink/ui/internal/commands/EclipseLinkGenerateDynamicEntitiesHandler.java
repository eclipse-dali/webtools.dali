/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.gen.EclipseLinkGenerateDynamicEntitiesFromSchemaWizard;
import org.eclipse.ui.handlers.HandlerUtil;


public class EclipseLinkGenerateDynamicEntitiesHandler
		extends AbstractHandler {
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.execute_(event);
		return null;
	}

	private void execute_(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		JpaProject project = PlatformTools.getAdapter(selection.getFirstElement(), JpaProject.class);
		EclipseLinkGenerateDynamicEntitiesFromSchemaWizard wizard = new EclipseLinkGenerateDynamicEntitiesFromSchemaWizard(project, selection);
		WizardDialog dialog = new WizardDialog(HandlerUtil.getActiveShellChecked(event), wizard);
		dialog.create();
		int returnCode = dialog.open();
		if (returnCode != Window.OK) {
			return;
		}
	}
}
