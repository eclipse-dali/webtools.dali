/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.proj;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiIcons;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.wizards.proj.model.JaxbProjectCreationDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectTemplate;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.web.ui.internal.wizards.NewProjectDataModelFacetWizard;


public class JaxbProjectWizard
		extends NewProjectDataModelFacetWizard {
	
	public JaxbProjectWizard() {
		super();
		setWindowTitle(JptJaxbUiMessages.JaxbProjectWizard_title);
	}
	
	public JaxbProjectWizard(IDataModel dataModel) {
		super(dataModel);
		setWindowTitle(JptJaxbUiMessages.JaxbProjectWizard_title);
	}
	
	
	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return JptJaxbUiPlugin.getImageDescriptor(JptJaxbUiIcons.JAXB_WIZ_BANNER);
	}
	
	@Override
	protected IWizardPage createFirstPage() {
		return new JaxbProjectWizardFirstPage(model, "first.page"); //$NON-NLS-1$ 
	}
	
	@Override
	protected IDataModel createDataModel() {
		return DataModelFactory.createDataModel(new JaxbProjectCreationDataModelProvider());
	}
	
	@Override
	protected IFacetedProjectTemplate getTemplate() {
		return ProjectFacetsManager.getTemplate("jpt.jaxb.template");
	}
}
