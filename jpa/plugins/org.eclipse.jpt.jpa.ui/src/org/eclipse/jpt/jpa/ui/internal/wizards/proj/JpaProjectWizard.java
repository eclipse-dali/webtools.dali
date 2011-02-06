/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.proj;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.proj.model.JpaProjectCreationDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectTemplate;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.web.ui.internal.wizards.NewProjectDataModelFacetWizard;

public class JpaProjectWizard
		extends NewProjectDataModelFacetWizard {
	
	public JpaProjectWizard() {
		super();
		setWindowTitle(JptUiMessages.JpaProjectWizard_title);
	}
	
	public JpaProjectWizard(IDataModel dataModel) {
		super(dataModel);
		setWindowTitle(JptUiMessages.JpaProjectWizard_title);
	}
	
	
	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return JptJpaUiPlugin.getImageDescriptor(JptUiIcons.JPA_WIZ_BANNER);
	}
	
	@Override
	protected IWizardPage createFirstPage() {
		return new JpaProjectWizardFirstPage(model, "first.page"); //$NON-NLS-1$ 
	}
	
	@Override
	protected IDataModel createDataModel() {
		return DataModelFactory.createDataModel(new JpaProjectCreationDataModelProvider());
	}
	
	@Override
	protected IFacetedProjectTemplate getTemplate() {
		return ProjectFacetsManager.getTemplate("jpt.jpa.template");
	}
	
	@Override
	protected String getFinalPerspectiveID() {
		return "org.eclipse.jpt.jpa.ui.jpaPerspective";
	}
}
