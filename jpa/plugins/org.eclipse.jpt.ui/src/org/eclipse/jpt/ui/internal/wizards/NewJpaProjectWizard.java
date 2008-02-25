/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jst.j2ee.ui.project.facet.UtilityProjectWizard;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectTemplate;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class NewJpaProjectWizard extends UtilityProjectWizard {

	public NewJpaProjectWizard() {
		super();
		setWindowTitle(JptUiMessages.NewJpaProjectWizard_title);
	}

	public NewJpaProjectWizard(IDataModel model) {
		super(model);
		setWindowTitle(JptUiMessages.NewJpaProjectWizard_title);
	}
	
	// TODO - when we have a data model to add
//	protected IDataModel createDataModel() {
//		return DataModelFactory.createDataModel(new UtilityProjectCreationDataModelProvider());
//	}
	
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return JptUiPlugin.getPlugin().getImageDescriptor(JptUiIcons.JPA_WIZ_BANNER);
	}
	
	protected IFacetedProjectTemplate getTemplate() {
		return ProjectFacetsManager.getTemplate("jpt.jpa.template");
	}
	
	protected IWizardPage createFirstPage() {
		return new NewJpaProjectFirstPage(model, "first.page"); //$NON-NLS-1$ 
	}
	
	protected String getFinalPerspectiveID() {
		return "org.eclipse.jpt.ui.jpaPerspective";
	}
}
