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
import org.eclipse.datatools.connectivity.ui.wizards.IWizardCategoryProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 *  ConnectionProfileUiTools
 */
public class DTPUiTools {

	private static final String DATABASE_CATEGORY_ID = "org.eclipse.datatools.connectivity.db.category"; //$NON-NLS-1$

	
	/**
	 * Launch the DTP New Connection Profile wizard to create a new database connection profile.
	 * 
	 * Returns the name of the added profile, or null if the wizard is cancelled. ConnectionProfileRepository 
	 * can be used to retrieve the added connection profile.
	 */
	public static String createNewProfile() {
		NewCPWizard wizard;
		WizardDialog wizardDialog;

		// Filter datasource category
	  	ViewerFilter viewerFilter = new ViewerFilter() {

			@Override
			public boolean select( Viewer viewer, Object parentElement, Object element) {
				
				CPWizardNode wizardNode = ( CPWizardNode) element;
				if( !( wizardNode.getProvider() instanceof IWizardCategoryProvider)) {
					ICategory cat = ConnectionProfileManager.getInstance().getProvider(
									(( ProfileWizardProvider) wizardNode.getProvider()).getProfile()).getCategory();
					
					// Only display wizards belong to database category
					while( cat != null) {
						if( cat.getId().equals(DATABASE_CATEGORY_ID))
							return true;
						cat = cat.getParent();
					}
				}
				return false;
			}
		};
		wizard = new NewCPWizard( viewerFilter, null);
		Shell currentShell = Display.getCurrent().getActiveShell();
		wizardDialog = new WizardDialog( currentShell, wizard);
		wizardDialog.setBlockOnOpen( true);
		
		LocalProfileListener listener = new LocalProfileListener();
		ProfileManager.getInstance().addProfileListener( listener);
		
		if( wizardDialog.open() == Window.CANCEL) {
			ProfileManager.getInstance().removeProfileListener( listener);
			return null;
		}
		IConnectionProfile addedProfile = listener.addedProfile;
		ProfileManager.getInstance().removeProfileListener( listener);
		
		return addedProfile.getName();
	}

	static class LocalProfileListener implements IProfileListener {
		IConnectionProfile addedProfile;
		
		public void profileAdded( IConnectionProfile profile) {
			addedProfile = profile;
		}
	
		public void profileChanged( IConnectionProfile profile) {
			// do nothing
		}
	
		public void profileDeleted( IConnectionProfile profile) {
			// do nothing
		}
	}
		  
}
