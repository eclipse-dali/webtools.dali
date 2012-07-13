/*******************************************************************************
* Copyright (c) 2007, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen.wizards.GenerateDDLWizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 *  EclipseLinkDLLGeneratorUi is used by the EclipseLinkPlatformUi to initiate 
 *  the execution of EclipseLink DDL generator.
 */
public class EclipseLinkDDLGeneratorUi
{
	private final JpaProject project;
	private static final String CR = StringTools.CR;

	// ********** static method **********
	
	public static void generate(JpaProject project) {
		new EclipseLinkDDLGeneratorUi(project).generate();
	}

	// ********** constructors **********
	
	protected EclipseLinkDDLGeneratorUi(JpaProject project) {
		super();
		if (project == null) {
			throw new NullPointerException();
		}
		this.project = project;
	}

	// ********** behavior **********
	
	protected void generate() {

		PersistenceUnit persistenceUnit = this.getPersistenceUnits().iterator().next(); // Take the first persistenceUnit
		String puName = persistenceUnit.getName();

		GenerateDDLWizard wizard = this.buildGenerateDDLWizard(this.project, puName);
		WizardDialog wizardDialog = new WizardDialog(this.getCurrentShell(), wizard);
		wizardDialog.create();
		if(wizard.getPageCount() > 0) {
			wizardDialog.open();
		}
	}
	
	protected GenerateDDLWizard buildGenerateDDLWizard(JpaProject project, String puName) {
		return new GenerateDDLWizard(project, puName);
	}
	
	private Shell getCurrentShell() {
	    return Display.getCurrent().getActiveShell();
	}

	// ********** Persistence Unit **********

	protected JpaPlatform getPlatform() {
		return this.project.getJpaPlatform();
	}
	
	protected ListIterable<PersistenceUnit> getPersistenceUnits() {
		return this.getPersistence().getPersistenceUnits();
	}

	protected Persistence getPersistence() {
		return this.project.getRootContextNode().getPersistenceXml().getPersistence();
	}
}
