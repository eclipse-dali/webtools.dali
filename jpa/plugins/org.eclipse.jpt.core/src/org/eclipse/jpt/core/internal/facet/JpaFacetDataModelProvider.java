/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.Arrays;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.platform.generic.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceConstants;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonMessages;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public class JpaFacetDataModelProvider
	extends FacetInstallDataModelProvider
	implements IJpaFacetDataModelProperties
{
	public JpaFacetDataModelProvider() {
		super();
	}
	
	
	@Override
	public Set getPropertyNames() {
		Set propertyNames = super.getPropertyNames();
		propertyNames.add(PLATFORM_ID);
		propertyNames.add(CONNECTION);
		propertyNames.add(RUNTIME);
		propertyNames.add(USE_SERVER_JPA_IMPLEMENTATION);
		propertyNames.add(JPA_LIBRARY);
		propertyNames.add(DISCOVER_ANNOTATED_CLASSES);
		propertyNames.add(CREATE_ORM_XML);
		return propertyNames;
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (FACET_ID.equals(propertyName)) {
			return JptCorePlugin.FACET_ID;
		}
		else if (PLATFORM_ID.equals(propertyName)) {
			return GenericJpaPlatform.ID;
		}
		else if (CONNECTION.equals(propertyName)) {
			return "";
		}
		else if (RUNTIME.equals(propertyName)) {
			return null;
		}
		else if (USE_SERVER_JPA_IMPLEMENTATION.equals(propertyName)) {
			return runtimeSupportsEjb30(getRuntime());
		}
		else if (JPA_LIBRARY.equals(propertyName)) {
			return JptCorePlugin.getPlugin().getPluginPreferences()
					.getString(JpaPreferenceConstants.PREF_DEFAULT_JPA_LIB);
		}
		else if (DISCOVER_ANNOTATED_CLASSES.equals(propertyName)) {
			return runtimeSupportsEjb30(getRuntime());
		}
		else if (CREATE_ORM_XML.equals(propertyName)) {
			return true;
		}
		else {
			return super.getDefaultProperty(propertyName);
		}
	}
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		if (RUNTIME.equals(propertyName)) {
			model.notifyPropertyChange(USE_SERVER_JPA_IMPLEMENTATION, IDataModel.DEFAULT_CHG);
			model.notifyPropertyChange(DISCOVER_ANNOTATED_CLASSES, IDataModel.DEFAULT_CHG);
		}
		return ok;
	}
	
	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (JPA_LIBRARY.equals(propertyName)) {
			String[] libraries = JavaCore.getUserLibraryNames();
			Arrays.sort(libraries);
			DataModelPropertyDescriptor[] descriptors = new DataModelPropertyDescriptor[libraries.length + 1];
			
			descriptors[0] = new DataModelPropertyDescriptor("", WTPCommonPlugin.getResourceString(WTPCommonMessages.RUNTIME_NONE, null));
			
			int i = 1;
			for (String library : libraries) {
				descriptors[i++] = new DataModelPropertyDescriptor(library, library);
			}	
			return descriptors;
		}
		
		return super.getValidPropertyDescriptors(propertyName);
	}
	
	@Override
	public IStatus validate(String name) {
		if (PLATFORM_ID.equals(name)) {
			return validatePlatform(getStringProperty(name));
		}
		else if (CONNECTION.equals(name)) {
			return validateConnection(getStringProperty(name));
		}
		else if (USE_SERVER_JPA_IMPLEMENTATION.equals(name) || JPA_LIBRARY.equals(name)) {
			return validateJpaLibrary();
		}
		else if (DISCOVER_ANNOTATED_CLASSES.equals(name)) {
			return validatePersistentClassManagement();
		}
		else {
			return super.validate(name);
		}
	}
	
	private IRuntime getRuntime() {
		return (IRuntime) getProperty(RUNTIME);
	}
	
	private boolean runtimeSupportsEjb30(IRuntime runtime) {
		IProjectFacetVersion ejb30 = ProjectFacetsManager.getProjectFacet(IModuleConstants.JST_EJB_MODULE).getVersion("3.0");
		return (runtime == null) ? false : runtime.supports(ejb30);
	}
	
	private IStatus validatePlatform(String platformId) {
		if (platformId == null || platformId.equals("")) {
			return new Status(IStatus.ERROR, JptCorePlugin.PLUGIN_ID, JptCoreMessages.VALIDATE_PLATFORM_NOT_SPECIFIED);
		}
		else {
			return OK_STATUS;
		}
	}
	
	private IStatus validateConnection(String connectionName) {
		if (connectionName == null || connectionName.equals("") || ! ConnectionProfileRepository.instance().profileNamed(connectionName).isConnected()) {
			return new Status(IStatus.INFO, JptCorePlugin.PLUGIN_ID, JptCoreMessages.VALIDATE_CONNECTION_NOT_CONNECTED);
		}
		else {
			return OK_STATUS;
		}
	}
	
	private IStatus validateJpaLibrary() {
		if (getBooleanProperty(USE_SERVER_JPA_IMPLEMENTATION)) {
			IRuntime runtime = getRuntime();
			if (runtime == null) {
				return new Status(IStatus.WARNING, JptCorePlugin.PLUGIN_ID, JptCoreMessages.VALIDATE_RUNTIME_NOT_SPECIFIED);
			}
			if (! runtimeSupportsEjb30(runtime)) {
				return new Status(IStatus.WARNING, JptCorePlugin.PLUGIN_ID, JptCoreMessages.VALIDATE_RUNTIME_DOES_NOT_SUPPORT_EJB_30);
			}
		}
		else {
			if (StringTools.stringIsEmpty(getStringProperty(JPA_LIBRARY))) {
				return new Status(IStatus.WARNING, JptCorePlugin.PLUGIN_ID, JptCoreMessages.VALIDATE_LIBRARY_NOT_SPECIFIED);
			}
		}
		return OK_STATUS;
	}
	
	private IStatus validatePersistentClassManagement() {
		// warning if "discovery" is used, but no runtime specified ??
		return OK_STATUS;
	}
}
