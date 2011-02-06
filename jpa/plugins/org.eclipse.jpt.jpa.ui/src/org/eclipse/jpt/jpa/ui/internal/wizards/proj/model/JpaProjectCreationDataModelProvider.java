/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.proj.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.internal.facet.FacetTools;
import org.eclipse.jst.common.project.facet.core.JavaFacet;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;
import org.eclipse.jst.j2ee.project.facet.IJ2EEFacetConstants;
import org.eclipse.jst.j2ee.project.facet.IJ2EEModuleFacetInstallDataModelProperties;
import org.eclipse.wst.common.componentcore.datamodel.FacetProjectCreationDataModelProvider;
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
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public class JpaProjectCreationDataModelProvider
		extends FacetProjectCreationDataModelProvider 
		implements JpaProjectCreationDataModelProperties {
	
	private IDataModelListener moduleFacetDataModelListener;
	
	
	public JpaProjectCreationDataModelProvider() {
		super();
		this.moduleFacetDataModelListener 
			= new IDataModelListener() {
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
			};
	}
	
	
	@Override
	public Set getPropertyNames() {
		Set names = super.getPropertyNames();
		names.add(MODULE_FACET_DATA_MODEL);
		names.add(ADDED_UTILITY_FACET);
		names.add(EAR_PROJECT_NAME);
		names.add(ADD_TO_EAR);
		return names;
	}
	
	@Override
	public void init() {
		super.init();
		
		Collection<IProjectFacet> requiredFacets = new ArrayList<IProjectFacet>();
		requiredFacets.add(JavaFacet.FACET);
		requiredFacets.add(JpaFacet.FACET);
		setProperty(REQUIRED_FACETS_COLLECTION, requiredFacets);
		
		getDataModel().addListener(
				new IDataModelListener() {
					public void propertyChanged(DataModelEvent event) {
						if (FACET_ACTION_MAP.equals(event.getPropertyName())) {
							updateModuleFacetDataModel();
						}
					}
				});
	}
	
	@Override
	public boolean isPropertyEnabled(String propertyName) {
		if (FACET_RUNTIME.equals(propertyName)) {
			if (isAddToEar()) {
				IStatus status = validateEAR(model.getStringProperty(EAR_PROJECT_NAME));
				if (status.isOK()) {
					IProject earProject = ProjectUtilities.getProject(getStringProperty(EAR_PROJECT_NAME));
					if (earProject != null) {
						IFacetedProject facetedEarProject;
						try {
							facetedEarProject = ProjectFacetsManager.create(earProject);
							if (facetedEarProject != null) {
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
		else if (EAR_PROJECT_NAME.equals(propertyName)) {
			return isAddToEar();
		}
		
		return super.isPropertyEnabled(propertyName);
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (ADDED_UTILITY_FACET.equals(propertyName)) {
			return Boolean.FALSE;
		}
		else if (ADD_TO_EAR.equals(propertyName)) {
			IDataModel moduleFacetDataModel = getModuleFacetDataModel();
			if (moduleFacetDataModel != null) {
				return moduleFacetDataModel.getDefaultProperty(IJ2EEModuleFacetInstallDataModelProperties.ADD_TO_EAR);
			}
		}
		else if (EAR_PROJECT_NAME.equals(propertyName)) {
			IDataModel moduleFacetDataModel = getModuleFacetDataModel();
			if (moduleFacetDataModel !=null) {
				return moduleFacetDataModel.getDefaultProperty(IJ2EEModuleFacetInstallDataModelProperties.EAR_PROJECT_NAME);
			}
		}
		
		return super.getDefaultProperty(propertyName);
	}
	
	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (FACET_RUNTIME.equals(propertyName) && isAddToEar()) {
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
		else if (EAR_PROJECT_NAME.equals(propertyName)) {
			IDataModel moduleFacetDataModel = getModuleFacetDataModel();
			if (moduleFacetDataModel != null) {
				return moduleFacetDataModel.getValidPropertyDescriptors(
						IJ2EEModuleFacetInstallDataModelProperties.EAR_PROJECT_NAME);
			}
			else {
				return new DataModelPropertyDescriptor[0];
			}
		}
		
		return super.getValidPropertyDescriptors(propertyName);
	}
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		if (EAR_PROJECT_NAME.equals(propertyName) || ADD_TO_EAR.equals(propertyName)) {
			IDataModel moduleFacetDataModel = getModuleFacetDataModel();
			if (moduleFacetDataModel != null){
				if (EAR_PROJECT_NAME.equals(propertyName)) {
					moduleFacetDataModel.setProperty(
							IJ2EEModuleFacetInstallDataModelProperties.EAR_PROJECT_NAME, propertyValue);
				}
				else {
					moduleFacetDataModel.setProperty(
							IJ2EEModuleFacetInstallDataModelProperties.ADD_TO_EAR, propertyValue);	
				}
			}
			if (isAddToEar()) {
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
				this.model.notifyPropertyChange(FACET_RUNTIME, IDataModel.VALID_VALUES_CHG);
				IProjectFacetVersion moduleFacet = findModuleFacet();
				if (isAddToEar()) {
					if (moduleFacet == null) {
						getFacetedProject().addProjectFacet(IJ2EEFacetConstants.UTILITY_FACET_10);
						setBooleanProperty(ADDED_UTILITY_FACET, true);
					}
				}
				else {
					if (moduleFacet != null && isAddedUtilityFacet()) {
						if (moduleFacet.equals(IJ2EEFacetConstants.UTILITY_FACET_10)) {
							getFacetedProject().removeProjectFacet(IJ2EEFacetConstants.UTILITY_FACET_10);
						}
						setBooleanProperty(ADDED_UTILITY_FACET, false);
					}
				}
				this.model.notifyPropertyChange(EAR_PROJECT_NAME, IDataModel.ENABLE_CHG);
				this.model.notifyPropertyChange(EAR_PROJECT_NAME, IDataModel.VALID_VALUES_CHG);
				this.model.notifyPropertyChange(EAR_PROJECT_NAME, IDataModel.VALUE_CHG);
			}
			this.model.notifyPropertyChange(FACET_RUNTIME, IDataModel.ENABLE_CHG);
		}
		
		return super.propertySet(propertyName, propertyValue);
	}
	
	@Override
	public IStatus validate(String propertyName) {
		IStatus status = super.validate(propertyName);
		
		if (status == null) {
			status = Status.OK_STATUS;
		}
		
		if (! status.isOK()) {
			return status;
		}
		
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
	
	protected void updateModuleFacetDataModel() {
		IDataModel newModuleFacetDataModel = findModuleFacetDataModel();
		IDataModel oldModuleFacetDataModel = getModuleFacetDataModel();
		
		if (oldModuleFacetDataModel != newModuleFacetDataModel) {
			if (oldModuleFacetDataModel != null) {
				oldModuleFacetDataModel.removeListener(this.moduleFacetDataModelListener);
			}
			if (newModuleFacetDataModel != null) {
				newModuleFacetDataModel.setProperty(
						IJ2EEModuleFacetInstallDataModelProperties.ADD_TO_EAR,
						getProperty(ADD_TO_EAR));
				newModuleFacetDataModel.setProperty(
						IJ2EEModuleFacetInstallDataModelProperties.EAR_PROJECT_NAME,
						getProperty(EAR_PROJECT_NAME));
				newModuleFacetDataModel.addListener(this.moduleFacetDataModelListener);
			}
			else {
				this.model.setBooleanProperty(ADD_TO_EAR, false);
				this.model.setBooleanProperty(ADDED_UTILITY_FACET, false);
			}
			setProperty(MODULE_FACET_DATA_MODEL, newModuleFacetDataModel);
			this.model.notifyPropertyChange(ADD_TO_EAR, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(EAR_PROJECT_NAME, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(EAR_PROJECT_NAME, IDataModel.VALID_VALUES_CHG);
		}	
	}
	
	protected IFacetedProjectWorkingCopy getFacetedProject() {
		return (IFacetedProjectWorkingCopy) this.model.getProperty(FACETED_PROJECT_WORKING_COPY);
	}
	
	protected IDataModel getModuleFacetDataModel() {
		return (IDataModel) getProperty(MODULE_FACET_DATA_MODEL);
	}
	
	protected boolean isAddedUtilityFacet() {
		return getBooleanProperty(ADDED_UTILITY_FACET);
	}
	
	protected boolean isAddToEar() {
		return getBooleanProperty(ADD_TO_EAR);
	}
	
	protected IDataModel findModuleFacetDataModel() {
		FacetActionMap map = (FacetActionMap) getProperty(FACET_ACTION_MAP);
		IProjectFacetVersion moduleFacet = findModuleFacet();
		if (moduleFacet != null) {
			IFacetedProject.Action action = map.getAction(moduleFacet.getProjectFacet().getId());
			return (action == null) ? null : (IDataModel) action.getConfig();
		}
		return null;
	}
	
	protected IProjectFacetVersion findModuleFacet() {
		return FacetTools.getModuleFacet(getFacetedProject());
	}
}
