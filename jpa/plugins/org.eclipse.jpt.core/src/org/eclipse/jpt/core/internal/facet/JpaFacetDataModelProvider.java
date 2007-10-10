/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.platform.generic.GenericPlatform;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceConstants;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public class JpaFacetDataModelProvider
	extends FacetInstallDataModelProvider
	implements IJpaFacetDataModelProperties
{
	@SuppressWarnings("restriction")
	private static final String EJB_FACET_ID = org.eclipse.wst.common.componentcore.internal.util.IModuleConstants.JST_EJB_MODULE;

	@SuppressWarnings("restriction")
	private static final String RUNTIME_NONE = org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin.getResourceString(org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonMessages.RUNTIME_NONE, null);

	private static final IStatus PLATFORM_NOT_SPECIFIED_STATUS = buildErrorStatus(JptCoreMessages.VALIDATE_PLATFORM_NOT_SPECIFIED);
	private static final IStatus CONNECTION_NOT_CONNECTED_STATUS = buildInfoStatus(JptCoreMessages.VALIDATE_CONNECTION_NOT_CONNECTED);
	private static final IStatus RUNTIME_NOT_SPECIFIED_STATUS = buildWarningStatus(JptCoreMessages.VALIDATE_RUNTIME_NOT_SPECIFIED);
	private static final IStatus RUNTIME_DOES_NOT_SUPPORT_EJB_30_STATUS = buildWarningStatus(JptCoreMessages.VALIDATE_RUNTIME_DOES_NOT_SUPPORT_EJB_30);
	private static final IStatus LIBRARY_NOT_SPECIFIED_STATUS = buildWarningStatus(JptCoreMessages.VALIDATE_LIBRARY_NOT_SPECIFIED);

	/**
	 * required default constructor
	 */
	public JpaFacetDataModelProvider() {
		super();
	}
	
	
	@Override
	public Set<String> getPropertyNames() {
		@SuppressWarnings("unchecked")
		Set<String> propertyNames = super.getPropertyNames();
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
		if (propertyName.equals(FACET_ID)) {
			return JptCorePlugin.FACET_ID;
		}
		if (propertyName.equals(PLATFORM_ID)) {
			return GenericPlatform.ID;
		}
		if (propertyName.equals(CONNECTION)) {
			return "";
		}
		if (propertyName.equals(RUNTIME)) {
			return null;
		}
		if (propertyName.equals(USE_SERVER_JPA_IMPLEMENTATION)) {
			return this.runtimeSupportsEjb30(this.runtime());
		}
		if (propertyName.equals(JPA_LIBRARY)) {
			return JptCorePlugin.instance().getPluginPreferences().getString(JpaPreferenceConstants.PREF_DEFAULT_JPA_LIB);
		}
		if (propertyName.equals(DISCOVER_ANNOTATED_CLASSES)) {
			return runtimeSupportsEjb30(this.runtime());
		}
		if (propertyName.equals(CREATE_ORM_XML)) {
			return Boolean.TRUE;
		}
		return super.getDefaultProperty(propertyName);
	}

	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		if (propertyName.equals(RUNTIME)) {
			this.model.notifyPropertyChange(USE_SERVER_JPA_IMPLEMENTATION, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(DISCOVER_ANNOTATED_CLASSES, IDataModel.DEFAULT_CHG);
		}
		return ok;
	}

	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(JPA_LIBRARY)) {
			String[] libraries = CollectionTools.sort(JavaCore.getUserLibraryNames());
			DataModelPropertyDescriptor[] descriptors = new DataModelPropertyDescriptor[libraries.length + 1];
			descriptors[0] = new DataModelPropertyDescriptor("", RUNTIME_NONE);
			
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
		if (name.equals(PLATFORM_ID)) {
			return this.validatePlatformId(this.getStringProperty(name));
		}
		if (name.equals(CONNECTION)) {
			return this.validateConnectionName(this.getStringProperty(name));
		}
		if (name.equals(USE_SERVER_JPA_IMPLEMENTATION)) {
			return this.validateJpaLibrary(this.getBooleanProperty(name));
		}
		if (name.equals(DISCOVER_ANNOTATED_CLASSES)) {
			return this.validatePersistentClassManagement(this.getBooleanProperty(name));
		}
		return super.validate(name);
	}

	private IRuntime runtime() {
		return (IRuntime) this.getProperty(RUNTIME);
	}

	private boolean runtimeSupportsEjb30(IRuntime runtime) {
		IProjectFacetVersion ejb30 = ProjectFacetsManager.getProjectFacet(EJB_FACET_ID).getVersion("3.0");
		return (runtime == null) ? false : runtime.supports(ejb30);
	}


	// ********** validation **********

	private IStatus validatePlatformId(String platformId) {
		return StringTools.stringIsEmpty(platformId) ?
				PLATFORM_NOT_SPECIFIED_STATUS
			:
				OK_STATUS;
	}

	private IStatus validateConnectionName(String connectionName) {
		return ConnectionProfileRepository.instance().profileNamed(connectionName).isConnected() ?
				OK_STATUS
			:
				CONNECTION_NOT_CONNECTED_STATUS;
	}

	private IStatus validateJpaLibrary(boolean useServerJpaImplementation) {
		if (useServerJpaImplementation) {
			IRuntime runtime = this.runtime();
			if (runtime == null) {
				return RUNTIME_NOT_SPECIFIED_STATUS;
			}
			if ( ! this.runtimeSupportsEjb30(runtime)) {
				return RUNTIME_DOES_NOT_SUPPORT_EJB_30_STATUS;
			}
		} else {
			if (StringTools.stringIsEmpty(this.getStringProperty(JPA_LIBRARY))) {
				return LIBRARY_NOT_SPECIFIED_STATUS;
			}
		}
		return OK_STATUS;
	}

	private IStatus validatePersistentClassManagement(boolean discoverAnnotatedClasses) {
		// TODO warning if "discovery" is used, but no runtime specified ??
		return OK_STATUS;
	}


	// ********** static methods **********

	private static IStatus buildInfoStatus(String message) {
		return buildStatus(IStatus.INFO, message);
	}

	private static IStatus buildWarningStatus(String message) {
		return buildStatus(IStatus.WARNING, message);
	}

	private static IStatus buildErrorStatus(String message) {
		return buildStatus(IStatus.ERROR, message);
	}

	private static IStatus buildStatus(int severity, String message) {
		return new Status(severity, JptCorePlugin.PLUGIN_ID, message);
	}

}
