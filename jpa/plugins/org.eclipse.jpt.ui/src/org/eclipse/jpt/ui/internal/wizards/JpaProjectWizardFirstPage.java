/*******************************************************************************
 *  Copyright (c) 2006, 2010 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.internal.facet.JpaProjectCreationDataModelProperties;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jst.j2ee.project.facet.IJ2EEFacetConstants;
import org.eclipse.jst.j2ee.ui.project.facet.EarSelectionPanel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetCreationWizardPage;

public class JpaProjectWizardFirstPage
		extends DataModelFacetCreationWizardPage  {
	
	protected EarSelectionPanel earComposite;
	
	
	public JpaProjectWizardFirstPage(IDataModel dataModel, String pageName) {
		super(dataModel, pageName);
		setTitle(JptUiMessages.NewJpaProjectWizard_firstPage_title);
		setDescription(JptUiMessages.NewJpaProjectWizard_firstPage_description);
		setInfopopID(JpaHelpContextIds.NEW_JPA_PROJECT);
	}
	
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		final Composite top = super.createTopLevelComposite(parent);
		if (shouldAddEARComposite()) {
			createEarComposite(top);
			createWorkingSetGroupPanel(top, new String[] { RESOURCE_WORKING_SET, JAVA_WORKING_SET });
		}
		return top;
	}
	
	private void createEarComposite(Composite top) {
		if (hasUtilityFacet()) {
			final IFacetedProject.Action action 
					= getFacetedProjectWorkingCopy().getProjectFacetAction(IJ2EEFacetConstants.UTILITY_FACET);
			this.earComposite = new EarSelectionPanel( (IDataModel) action.getConfig(), top);
		}
	}
	
	@Override
	public boolean internalLaunchNewRuntimeWizard(Shell shell, IDataModel model) {
		if (hasUtilityFacet()) {
			return launchNewRuntimeWizard(shell, model, IJ2EEFacetConstants.UTILITY);
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
		if (this.earComposite != null) {
			earComposite.dispose();
		}
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
	
	protected boolean shouldAddEARComposite() {
		return hasUtilityFacet();
	}
	
	protected boolean hasUtilityFacet() {
		return getFacetedProjectWorkingCopy().hasProjectFacet(IJ2EEFacetConstants.UTILITY_FACET);
	}
	
	protected IFacetedProjectWorkingCopy getFacetedProjectWorkingCopy() {
		return (IFacetedProjectWorkingCopy) this.model.getProperty(FACETED_PROJECT_WORKING_COPY);
	}
}
