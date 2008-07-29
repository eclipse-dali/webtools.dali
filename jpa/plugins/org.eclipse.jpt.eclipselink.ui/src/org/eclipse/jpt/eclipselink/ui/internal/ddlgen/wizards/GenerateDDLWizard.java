/*******************************************************************************
* Copyright (c) 2007, 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.ddlgen.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.ui.internal.wizards.DatabaseConnectionWizardPage;

/**
 *  GenerateDDLWizard
 */
public class GenerateDDLWizard extends Wizard {	

	private JpaProject jpaProject;

	private DatabaseConnectionWizardPage dbSettingsPage;

	public GenerateDDLWizard(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
		this.setWindowTitle("DDL Generation");  // TODO
	}
	
	@Override
	public void addPages() {
		super.addPages();
		if (this.getProjectConnectionProfile() == null) {
			this.dbSettingsPage = new DatabaseConnectionWizardPage(this.jpaProject);
			this.addPage(this.dbSettingsPage);
		}
	}
	
	@Override
	public boolean performFinish() {
        if (this.getProjectConnectionProfile() != null) {
        	return true;
        }
		ConnectionProfile cp = this.dbSettingsPage.getSelectedConnectionProfile();
		if (cp == null) {
			return false;
		}
		this.setProjectConnectionProfileName(cp.getName());
		return true;
	}
    
    @Override
	public boolean canFinish() {
        return this.dbSettingsPage.isPageComplete();
    }
    
	private ConnectionProfile getProjectConnectionProfile() {
		return this.jpaProject.getConnectionProfile();
	}

	private void setProjectConnectionProfileName(String connectionProfileName) {
		this.jpaProject.getDataSource().setConnectionProfileName(connectionProfileName);
		JptCorePlugin.setConnectionProfileName(this.jpaProject.getProject(), connectionProfileName);
	}

}
