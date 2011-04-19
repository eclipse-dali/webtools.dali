/*******************************************************************************
* Copyright (c) 2007, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.schema.generation.OutputMode;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.DatabaseSchemaWizardPage;

/**
 *  GenerateDDLWizard
 */
public class GenerateDDLWizard extends Wizard {	

	private JpaProject jpaProject;

	private DatabaseSchemaWizardPage dbSettingsPage;
	private GenerationOutputModeWizardPage generationOutputModePage;

	// ********** constructor **********

	public GenerateDDLWizard(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
		this.setWindowTitle(JptUiMessages.GenerateDDLWizard_title); 
	}

	// ********** IWizard implementation  **********

	@Override
	public void addPages() {
		super.addPages();
		if(this.getJpaProjectConnectionProfile() == null) {
			this.dbSettingsPage = new DatabaseSchemaWizardPage(this.jpaProject);
			this.addPage(this.dbSettingsPage);
		}
		this.generationOutputModePage = new GenerationOutputModeWizardPage();
		this.addPage(this.generationOutputModePage);
	}
	
	@Override
	public boolean performFinish() {
		return (this.getJpaProjectConnectionProfile() != null);
	}

    @Override
	public boolean canFinish() {
    	return this.dbSettingsPageCanFinish() && this.generationOutputModePageCanFinish();
    }
    
	// ********** intra-wizard methods **********
    
    public OutputMode getOutputMode() {
		return this.generationOutputModePage.getOutputMode();
	}
	
	// ********** internal methods **********

    private boolean dbSettingsPageCanFinish() {
		return (this.dbSettingsPage != null) ? this.dbSettingsPage.isPageComplete() : true;
	}

	private boolean generationOutputModePageCanFinish() {
		return this.generationOutputModePage.isPageComplete();
	}
	
	private ConnectionProfile getJpaProjectConnectionProfile() {
		return this.jpaProject.getConnectionProfile();
	}
}
