/*******************************************************************************
* Copyright (c) 2007, 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.db.ui.internal;

import org.eclipse.datatools.connectivity.ICategory;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IProfileListener;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.datatools.connectivity.internal.ConnectionProfileManager;
import org.eclipse.datatools.connectivity.internal.ui.wizards.CPWizardNode;
import org.eclipse.datatools.connectivity.internal.ui.wizards.NewCPWizard;
import org.eclipse.datatools.connectivity.internal.ui.wizards.ProfileWizardProvider;
import org.eclipse.datatools.connectivity.ui.wizards.IProfileWizardProvider;
import org.eclipse.datatools.connectivity.ui.wizards.IWizardCategoryProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

/**
 * DTP UI tools
 */
public class DTPUiTools {


	/**
	 * Launch the DTP New Connection Profile wizard to create a new database connection profile.
	 * 
	 * Returns the name of the added profile, or null if the wizard was cancelled.
	 * The name can be used to build a Dali connection profile from
	 * JptDbPlugin.getConnectionProfileFactory().buildConnectionProfile(String).
	 */
	public static String createNewConnectionProfile() {
		// Filter datasource category
		NewCPWizard wizard = new NewCPWizard(new LocalViewerFilter(), null);
		WizardDialog wizardDialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
		wizardDialog.setBlockOnOpen(true);

		LocalProfileListener listener = new LocalProfileListener();
		ProfileManager.getInstance().addProfileListener(listener);

		String newCPName = null;
		if (wizardDialog.open() == Window.OK) {
			// assume the last added profile is the one we want
			newCPName = listener.addedProfile.getName();
		}
		ProfileManager.getInstance().removeProfileListener(listener);

		return newCPName;
	}


	// ********** DTP profile listener **********

	/**
	 * This listener simply holds on to the most recently added connection
	 * profile.
	 */
	static class LocalProfileListener implements IProfileListener {
		IConnectionProfile addedProfile;
		
		public void profileAdded(IConnectionProfile profile) {
			this.addedProfile = profile;
		}
	
		public void profileChanged(IConnectionProfile profile) {
			// do nothing
		}
	
		public void profileDeleted(IConnectionProfile profile) {
			// do nothing
		}
	}


	// ********** viewer filter **********

	static class LocalViewerFilter extends ViewerFilter {

		private static final String DATABASE_CATEGORY_ID = "org.eclipse.datatools.connectivity.db.category"; //$NON-NLS-1$

		LocalViewerFilter() {
			super();
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			CPWizardNode wizardNode = (CPWizardNode) element;
			IProfileWizardProvider wizardProvider = wizardNode.getProvider();
			if (wizardProvider instanceof IWizardCategoryProvider) {
				return false;
			}
			ICategory category = ConnectionProfileManager.getInstance().getProvider(
							((ProfileWizardProvider) wizardProvider).getProfile()).getCategory();
			
			// Only display wizards belong to database category
			while (category != null) {
				if (category.getId().equals(DATABASE_CATEGORY_ID)) {
					return true;
				}
				category = category.getParent();
			}
			return false;
		}
	}

}
