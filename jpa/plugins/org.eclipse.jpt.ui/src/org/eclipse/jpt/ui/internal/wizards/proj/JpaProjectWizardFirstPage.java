/*******************************************************************************
 *  Copyright (c) 2006, 2010 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards.proj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.internal.facet.FacetTools;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.wizards.proj.model.JpaProjectCreationDataModelProperties;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetCreationWizardPage;

public class JpaProjectWizardFirstPage
		extends DataModelFacetCreationWizardPage  {
	
	private AddToEarComposite addToEarComposite;
	
	public JpaProjectWizardFirstPage(IDataModel dataModel, String pageName) {
		super(dataModel, pageName);
		setTitle(JptUiMessages.NewJpaProjectWizard_firstPage_title);
		setDescription(JptUiMessages.NewJpaProjectWizard_firstPage_description);
		setInfopopID(JpaHelpContextIds.NEW_JPA_PROJECT);
	}
	
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		final Composite top = super.createTopLevelComposite(parent);
		createEarComposite(top);
		createWorkingSetGroupPanel(top, new String[] { RESOURCE_WORKING_SET, JAVA_WORKING_SET });
		return top;
	}
	
	private void createEarComposite(Composite top) {
		this.addToEarComposite = new AddToEarComposite(getDataModel(), top);
	}
	
	@Override
	public boolean internalLaunchNewRuntimeWizard(Shell shell, IDataModel model) {
		IFacetedProjectWorkingCopy fpwc = (IFacetedProjectWorkingCopy) model.getProperty(FACETED_PROJECT_WORKING_COPY);
		IProjectFacetVersion moduleFacet = FacetTools.getModuleFacet(fpwc);
		if (moduleFacet != null) {
			return launchNewRuntimeWizard(shell, model, moduleFacet.getProjectFacet().getId());
		}
		else {
			return launchNewRuntimeWizard(shell, model);
		}
	}
	
	@Override
	protected String getModuleTypeID() {
		return JpaFacet.ID;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.addToEarComposite.dispose();
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
    
	@Override
	protected String[] getValidationPropertyNames() {
		String[] superProperties = super.getValidationPropertyNames();
		List list = Arrays.asList(superProperties);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.addAll(list);
		arrayList.add(JpaProjectCreationDataModelProperties.EAR_PROJECT_NAME);
		arrayList.add(JpaProjectCreationDataModelProperties.ADD_TO_EAR );
		return arrayList.toArray( new String[0] );
	}
}
