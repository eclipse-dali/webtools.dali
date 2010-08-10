/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jst.common.project.facet.core.JavaFacet;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;
import org.eclipse.jst.j2ee.project.facet.IJ2EEFacetConstants;
import org.eclipse.jst.j2ee.project.facet.IJ2EEModuleFacetInstallDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelEvent;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelListener;
import org.eclipse.wst.common.frameworks.internal.operations.ProjectCreationDataModelProviderNew;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonMessages;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public class JpaEEProjectCreationDataModelProvider
		extends JpaProjectCreationDataModelProvider {
	
	public JpaEEProjectCreationDataModelProvider() {
		super();
	}
	
	
	@Override
	public void init() {
		super.init();
		
		Collection<IProjectFacet> requiredFacets = new ArrayList<IProjectFacet>();
		requiredFacets.add(JavaFacet.FACET);
		requiredFacets.add(JpaFacet.FACET);
		requiredFacets.add(IJ2EEFacetConstants.UTILITY_FACET);
		setProperty(REQUIRED_FACETS_COLLECTION, requiredFacets);
		
		FacetDataModelMap map = (FacetDataModelMap) getProperty(FACET_DM_MAP);
		
		IDataModel utilFacet = map.getFacetDataModel(IJ2EEFacetConstants.UTILITY_FACET.getId());
		utilFacet.addListener(
			new IDataModelListener() {
				public void propertyChanged(DataModelEvent event) {
					if (IJ2EEModuleFacetInstallDataModelProperties.EAR_PROJECT_NAME.equals(event.getPropertyName())) {
						if (isPropertySet(EAR_PROJECT_NAME)) {
							setProperty(EAR_PROJECT_NAME, event.getProperty());
						}
						else {
							model.notifyPropertyChange(EAR_PROJECT_NAME, IDataModel.DEFAULT_CHG);
						}
					}
					else if (IJ2EEModuleFacetInstallDataModelProperties.ADD_TO_EAR.equals(event.getPropertyName())) {
						setProperty(ADD_TO_EAR, event.getProperty());
					}
				}
			});
	}
	
	@Override
	public boolean isPropertyEnabled(String propertyName) {
		if (FACET_RUNTIME.equals(propertyName)) {
			if (getBooleanProperty(ADD_TO_EAR)) {
				IStatus status = validateEAR(model.getStringProperty(EAR_PROJECT_NAME));
				if (status.isOK()) {
					IProject earProject = ProjectUtilities.getProject(getStringProperty(EAR_PROJECT_NAME));
					if (earProject != null) {
						IFacetedProject facetdEarProject;
						try {
							facetdEarProject = ProjectFacetsManager.create(earProject);
							if (facetdEarProject != null) {
								return false;
							}
						} catch (CoreException e) {
							J2EEPlugin.logError(e);
						}
					}
				}
			}
			return true;
		}
		return super.isPropertyEnabled(propertyName);
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (EAR_PROJECT_NAME.equals(propertyName)) {
			IDataModel utilityFacetModel = getUtilityFacetModel();
			if (utilityFacetModel !=null) {
				return utilityFacetModel.getProperty(IJ2EEModuleFacetInstallDataModelProperties.EAR_PROJECT_NAME);
			}
		}
		return super.getDefaultProperty(propertyName);
	}
	
	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (FACET_RUNTIME.equals(propertyName) && getBooleanProperty(ADD_TO_EAR)) {
			DataModelPropertyDescriptor[] descriptors = super.getValidPropertyDescriptors(propertyName);
			List list = new ArrayList();
			for (int i = 0; i < descriptors.length; i++) {
				IRuntime rt = (IRuntime) descriptors[i].getPropertyValue();
				if (rt == null || rt.supports(IJ2EEFacetConstants.ENTERPRISE_APPLICATION_FACET)) {
					list.add(descriptors[i]);
				}
			}
			descriptors = new DataModelPropertyDescriptor[list.size()];
			for (int i = 0; i < descriptors.length; i++) {
				descriptors[i] = (DataModelPropertyDescriptor) list.get(i);
			}
			return descriptors;
		}
		return super.getValidPropertyDescriptors(propertyName);
	}
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		if (EAR_PROJECT_NAME.equals(propertyName) || ADD_TO_EAR.equals(propertyName)) {
			IDataModel utilityFacetModel = getUtilityFacetModel();
			if (null != utilityFacetModel){
				if (EAR_PROJECT_NAME.equals(propertyName)) {
					utilityFacetModel.setProperty(IJ2EEModuleFacetInstallDataModelProperties.EAR_PROJECT_NAME, propertyValue);
				}
				else {
					utilityFacetModel.setProperty(IJ2EEModuleFacetInstallDataModelProperties.ADD_TO_EAR, propertyValue);	
				}
			}
			if (getBooleanProperty(ADD_TO_EAR)) {
				IStatus status = validateEAR(model.getStringProperty(EAR_PROJECT_NAME));
				if (status.isOK()) {
					IProject earProject = ProjectUtilities.getProject(getStringProperty(EAR_PROJECT_NAME));
					if (earProject != null) {
						IFacetedProject facetedEarProject;
						try {
							facetedEarProject = ProjectFacetsManager.create(earProject);
							if (facetedEarProject != null) {
								setProperty(FACET_RUNTIME, facetedEarProject.getPrimaryRuntime());
							}
						}
						catch (CoreException e) {
							J2EEPlugin.logError(e);
						}
					}
				}
			}
			if (ADD_TO_EAR.equals(propertyName)) {
				model.notifyPropertyChange(FACET_RUNTIME, IDataModel.VALID_VALUES_CHG);
			}
			model.notifyPropertyChange(FACET_RUNTIME, IDataModel.ENABLE_CHG);
		}
		return super.propertySet(propertyName, propertyValue);
	}
	
	@Override
	public IStatus validate(String propertyName) {
		IStatus status = super.validate(propertyName);
		if (status == null) {
			status = Status.OK_STATUS;
		}
		if (status.isOK()) {
			if (ADD_TO_EAR.equals(propertyName) || EAR_PROJECT_NAME.equals(propertyName) || FACET_PROJECT_NAME.equals(propertyName)) {
				if (model.getBooleanProperty(ADD_TO_EAR)) {
					status = validateEAR(model.getStringProperty(EAR_PROJECT_NAME));
					if (status.isOK()) {
						if (getStringProperty(FACET_PROJECT_NAME).equals(getStringProperty(EAR_PROJECT_NAME))) {
							String errorMessage = WTPCommonPlugin.getResourceString(WTPCommonMessages.SAME_MODULE_AND_EAR_NAME, new Object[]{getStringProperty(EAR_PROJECT_NAME)});
							status = WTPCommonPlugin.createErrorStatus(errorMessage);
						}
					}
				}
			}
		}
		return status;
	}

	protected IStatus validateEAR(String earName) {
		if (earName.indexOf("#") != -1 || earName.indexOf("/") != -1) { //$NON-NLS-1$ //$NON-NLS-2$
			String errorMessage = WTPCommonPlugin.getResourceString(WTPCommonMessages.ERR_INVALID_CHARS);
			return WTPCommonPlugin.createErrorStatus(errorMessage);
		} 
		else if (earName.equals("")) { //$NON-NLS-1$
			String errorMessage = WTPCommonPlugin.getResourceString(WTPCommonMessages.ERR_EMPTY_MODULE_NAME);
			return WTPCommonPlugin.createErrorStatus(errorMessage);
		}
		
		IStatus status = ProjectCreationDataModelProviderNew.validateProjectName(earName);
		//check for the deleted case, the project is deleted from the workspace but still exists in the
		//file system.
		if (status.isOK()) {
			IProject earProject = ProjectUtilities.getProject(getStringProperty(EAR_PROJECT_NAME));
			if (! earProject.exists()) {
				IPath path = ResourcesPlugin.getWorkspace().getRoot().getLocation();
				path = path.append(earName);
				status = ProjectCreationDataModelProviderNew.validateExisting(earName, path.toString());
			}
		}
		return status;
	}
	
	protected IDataModel getUtilityFacetModel() {
		final IFacetedProjectWorkingCopy fpjwc
				= (IFacetedProjectWorkingCopy) this.model.getProperty(FACETED_PROJECT_WORKING_COPY);
		IProjectFacet utilityFacet = IJ2EEFacetConstants.UTILITY_FACET;
		
		if (fpjwc.hasProjectFacet(utilityFacet)) {
			final IFacetedProject.Action action = fpjwc.getProjectFacetAction(utilityFacet);
			return (IDataModel) action.getConfig();
		}
		
		return null;
	}
}
