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

import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetCreationWizardPage;


public class JaxbProjectWizardFirstPage
		extends DataModelFacetCreationWizardPage {
	
	public JaxbProjectWizardFirstPage(IDataModel dataModel, String pageName) {
		super(dataModel, pageName);
		setTitle(JptJaxbUiMessages.JAXB_PROJECT_WIZARD_FIRST_PAGE_TITLE);
		setDescription(JptJaxbUiMessages.JAXB_PROJECT_WIZARD_FIRST_PAGE_DESC);
		//setInfopopID(JpaJaxbHelpContextIds.NEW_JAXB_PROJECT);
	}
	
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		final Composite top = super.createTopLevelComposite(parent);
		createWorkingSetGroupPanel(top, new String[] { RESOURCE_WORKING_SET, JAVA_WORKING_SET });
		return top;
	}
	
//	@Override
//	public boolean internalLaunchNewRuntimeWizard(Shell shell, IDataModel model) {
//		IFacetedProjectWorkingCopy fpwc = (IFacetedProjectWorkingCopy) model.getProperty(FACETED_PROJECT_WORKING_COPY);
//		IProjectFacetVersion moduleFacet = FacetTools.getModuleFacet(fpwc);
//		if (moduleFacet != null) {
//			return launchNewRuntimeWizard(shell, model, moduleFacet.getProjectFacet().getId());
//		}
//		else {
//			return launchNewRuntimeWizard(shell, model);
//		}
//	}
	
	@Override
	protected String getModuleTypeID() {
		return JaxbProject.FACET_ID;
	}
	
    @Override
	public void storeDefaultSettings() {
    	super.storeDefaultSettings();
    	// TODO
//    	IDialogSettings settings = getDialogSettings();
//    	if (settings != null) {
//    		FacetDataModelMap map = (FacetDataModelMap)model.getProperty(IFacetProjectCreationDataModelProperties.FACET_DM_MAP);
//    		String facetID = getModuleFacetID();
//    		IDataModel j2eeModel = map.getFacetDataModel(facetID);
//    		if(j2eeModel.getBooleanProperty(IJ2EEModuleFacetInstallDataModelProperties.ADD_TO_EAR)){
//    			String lastEARName = j2eeModel.getStringProperty(IJ2EEModuleFacetInstallDataModelProperties.EAR_PROJECT_NAME);
//    			settings.put(STORE_LABEL, lastEARName);
//    		}
//    	}
    }
    
    @Override
    public void restoreDefaultSettings() {
    	super.restoreDefaultSettings();
        // TODO
//    	IDialogSettings settings = getDialogSettings();
//    	if (settings != null) {
//    		String lastEARName = settings.get(STORE_LABEL);
//    		if (lastEARName != null){
//    			FacetDataModelMap map = (FacetDataModelMap)model.getProperty(IFacetProjectCreationDataModelProperties.FACET_DM_MAP);
//    			String facetID = getModuleFacetID();
//    			IDataModel j2eeModel = map.getFacetDataModel(facetID);
//    			j2eeModel.setProperty(IJ2EEModuleFacetInstallDataModelProperties.LAST_EAR_NAME, lastEARName);
//    		}
//    	}
    }
    
//    @Override
//    protected IDialogSettings getDialogSettings() {
//    	return J2EEUIPlugin.getDefault().getDialogSettings();
//    }
}
