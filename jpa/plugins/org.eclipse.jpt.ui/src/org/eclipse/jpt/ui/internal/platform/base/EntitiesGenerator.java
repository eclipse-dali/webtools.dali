/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.base;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.ui.internal.wizards.gen.GenerateEntitiesFromSchemaWizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 *  EntitiesGenerator
 */
public class EntitiesGenerator {
	private JpaProject project;
	private IStructuredSelection selection;

	public static void generate(JpaProject project, IStructuredSelection selection) {
		new EntitiesGenerator(project, selection).generate();
	}
	
	private EntitiesGenerator(JpaProject project, IStructuredSelection selection) {
		super();
		if (project == null) {
			throw new NullPointerException();
		}
		this.project = project;
		this.selection = selection;
	}


	// ********** generate **********

	/**
	 * prompt the user with a wizard;
	 * schedule a job to generate the entities;
	 * optionally schedule a job to synchronize persistence.xml to
	 * run afterwards
	 */
	protected void generate() {
		GenerateEntitiesFromSchemaWizard wizard = new GenerateEntitiesFromSchemaWizard(this.project, this.selection);
		WizardDialog dialog = new WizardDialog(this.getCurrentShell(), wizard);
		dialog.create();
		int returnCode = dialog.open();
		if (returnCode != Window.OK) {
			return;
		}
		//Entities generation happens in the GenerateEntitiesFromSchemaWizard.performFinish()
		//method
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}


}
