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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.ui.internal.wizards.DatabaseReconnectWizardPage;

/**
 *  GenerateDDLWizard
 */
public class GenerateDDLWizard extends Wizard {	

	private JpaProject jpaProject;

	private IStructuredSelection selection;

	private DatabaseReconnectWizardPage dbSettingsPage;

	public GenerateDDLWizard(JpaProject jpaProject, IStructuredSelection selection) {
		super();
		this.jpaProject = jpaProject;
		this.selection = selection;
		this.setWindowTitle("DDL Generation");
	}
	
	public void addPages() {
		super.addPages();
		if ( ! this.jpaProjectHasConnection()) {
			this.dbSettingsPage = new DatabaseReconnectWizardPage(this.jpaProject);
			this.addPage(this.dbSettingsPage);
		}
	}
	
	@SuppressWarnings("restriction")
	public boolean performFinish() {
        String name = getProjectConnectionProfile().getName();
		if ( ! this.jpaProjectHasConnection()) {

			String connectionProfileName = this.dbSettingsPage.getSelectedConnectionProfileName();
			ConnectionProfile profile = JptDbPlugin.instance().getConnectionProfileRepository().connectionProfileNamed( connectionProfileName);
			if( profile.isNull()) {
				this.dbSettingsPage.clearConnectionProfileName();
				return false;
			}
			this.setProjectConnectionProfileName(connectionProfileName);
		}
		return true;
	}
    
    public boolean canFinish() {
        boolean canFinish = true;
        if ( ! this.dbSettingsPage.isPageComplete()) {
        	canFinish = false;
        }
        return canFinish;
    }
    
	private boolean jpaProjectHasConnection() {
		return ! this.getProjectConnectionProfile().isNull();
	}
	
	private ConnectionProfile getProjectConnectionProfile() {
		return this.jpaProject.getConnectionProfile();
	}

	private void setProjectConnectionProfileName(String connectionProfileName) {
		this.jpaProject.getDataSource().setConnectionProfileName(connectionProfileName);
		JptCorePlugin.setConnectionProfileName(this.jpaProject.getProject(), connectionProfileName);
	}

}
