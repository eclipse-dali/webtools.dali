/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.web.ui.internal.wizards.NewProjectDataModelFacetWizard;

public abstract class JpaProjectWizard
		extends NewProjectDataModelFacetWizard {
	
	protected JpaProjectWizard() {
		super();
	}
	
	protected JpaProjectWizard(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return JptUiPlugin.getImageDescriptor(JptUiIcons.JPA_WIZ_BANNER);
	}
	
	@Override
	protected IWizardPage createFirstPage() {
		return new JpaProjectWizardFirstPage(model, "first.page"); //$NON-NLS-1$ 
	}
	
	@Override
	protected String getFinalPerspectiveID() {
		return "org.eclipse.jpt.ui.jpaPerspective";
	}
}
