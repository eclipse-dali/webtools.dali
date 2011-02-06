/*******************************************************************************
* Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.DatabaseSchemaWizardPage;

/**
 *  GenerateDDLWizard
 */
public class GenerateDDLWizard extends Wizard {	

	private JpaProject jpaProject;

	private DatabaseSchemaWizardPage dbSettingsPage;

	public GenerateDDLWizard(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
		this.setWindowTitle(JptUiMessages.GenerateDDLWizard_title); 
	}
	
	@Override
	public void addPages() {
		super.addPages();
		if (this.getJpaProjectConnectionProfile() == null) {
			this.dbSettingsPage = new DatabaseSchemaWizardPage(this.jpaProject);
			this.addPage(this.dbSettingsPage);
		}
	}
	
	@Override
	public boolean performFinish() {
		return (this.getJpaProjectConnectionProfile() != null);
	}
    
    @Override
	public boolean canFinish() {
        return this.dbSettingsPage.isPageComplete();
    }
    
	private ConnectionProfile getJpaProjectConnectionProfile() {
		return this.jpaProject.getConnectionProfile();
	}

}
