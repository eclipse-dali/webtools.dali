/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.proj;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
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
		setWindowTitle(JptJaxbUiMessages.JAXB_PROJECT_WIZARD_TITLE);
	}
	
	public JaxbProjectWizard(IDataModel dataModel) {
		super(dataModel);
		setWindowTitle(JptJaxbUiMessages.JAXB_PROJECT_WIZARD_TITLE);
	}
	
	
	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return JptJaxbUiImages.JAXB_PROJECT_BANNER;
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
