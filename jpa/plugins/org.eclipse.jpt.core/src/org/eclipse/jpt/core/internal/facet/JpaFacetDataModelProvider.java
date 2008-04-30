/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
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
	implements JpaFacetDataModelProperties
{
	private static final String EJB_FACET_ID = IModuleConstants.JST_EJB_MODULE;

	private static final String RUNTIME_NONE = 
			WTPCommonPlugin.getResourceString(WTPCommonMessages.RUNTIME_NONE, null);
	
	private static final IStatus PLATFORM_NOT_SPECIFIED_STATUS = 
			buildErrorStatus(JptCoreMessages.VALIDATE_PLATFORM_NOT_SPECIFIED);
	
	private static final IStatus CONNECTION_NOT_CONNECTED_STATUS = 
			buildInfoStatus(JptCoreMessages.VALIDATE_CONNECTION_NOT_CONNECTED);
	
	private static final IStatus USER_OVERRIDE_DEFAULT_SCHEMA_NOT_SPECIFIED_STATUS = 
			buildErrorStatus(JptCoreMessages.VALIDATE_DEFAULT_SCHEMA_NOT_SPECIFIED);
	
	private static final IStatus RUNTIME_NOT_SPECIFIED_STATUS = 
			buildWarningStatus(JptCoreMessages.VALIDATE_RUNTIME_NOT_SPECIFIED);
	
	private static final IStatus RUNTIME_DOES_NOT_SUPPORT_EJB_30_STATUS = 
			buildWarningStatus(JptCoreMessages.VALIDATE_RUNTIME_DOES_NOT_SUPPORT_EJB_30);
	
	private static final IStatus LIBRARY_NOT_SPECIFIED_STATUS = 
			buildWarningStatus(JptCoreMessages.VALIDATE_LIBRARY_NOT_SPECIFIED);

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
		propertyNames.add(CONNECTION_ACTIVE);
		propertyNames.add(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA);
		propertyNames.add(USER_OVERRIDE_DEFAULT_SCHEMA);
		propertyNames.add(RUNTIME);
		propertyNames.add(USE_SERVER_JPA_IMPLEMENTATION);
		propertyNames.add(JPA_LIBRARY);
		propertyNames.add(DISCOVER_ANNOTATED_CLASSES);
		propertyNames.add(CREATE_ORM_XML);
		return propertyNames;
	}
	
	@Override
	public boolean isPropertyEnabled(String propertyName) {
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA);
		}
		return super.isPropertyEnabled(propertyName);
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(FACET_ID)) {
			return JptCorePlugin.FACET_ID;
		}
		if (propertyName.equals(PLATFORM_ID)) {
			return JptCorePlugin.getDefaultJpaPlatformId();
		}
		if (propertyName.equals(CONNECTION)) {
			return "";
		}
		if (propertyName.equals(CONNECTION_ACTIVE)) {
			return connectionIsActive();
		}
		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			return Boolean.FALSE;
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return getDefaultSchemaName();
		}
		if (propertyName.equals(RUNTIME)) {
			return null;
		}
		if (propertyName.equals(USE_SERVER_JPA_IMPLEMENTATION)) {
			return Boolean.valueOf(this.runtimeSupportsEjb30(this.runtime()));
		}
		if (propertyName.equals(JPA_LIBRARY)) {
			return JptCorePlugin.getDefaultJpaLibrary();
		}
		if (propertyName.equals(DISCOVER_ANNOTATED_CLASSES)) {
			return Boolean.valueOf(this.runtimeSupportsEjb30(this.runtime()));
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
		if (propertyName.equals(CONNECTION)) {
			this.model.setBooleanProperty(CONNECTION_ACTIVE, connectionIsActive());
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.VALID_VALUES_CHG);
		}
		if (propertyName.equals(CONNECTION_ACTIVE)) {
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.VALID_VALUES_CHG);
		}
		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.ENABLE_CHG);
			if (! (Boolean) propertyValue) {
				this.model.setProperty(USER_OVERRIDE_DEFAULT_SCHEMA, null);
			}
		}
		return ok;
	}

	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return CollectionTools.array(
				new TransformationIterator<String, DataModelPropertyDescriptor>(schemas()) {
					@Override
					protected DataModelPropertyDescriptor transform(String next) {
						return new DataModelPropertyDescriptor(next);
					}
				},
				new DataModelPropertyDescriptor[0]);
		}
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
		if (name.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)
				|| name.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return this.validateUserOverrideDefaultSchema();
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
	
	private ConnectionProfile getConnection() {
		String connectionName = getStringProperty(CONNECTION);
		return JptDbPlugin.instance().getConnectionProfileRepository().connectionProfileNamed(connectionName);
	}
	
	private boolean connectionIsActive() {
		return getConnection().isActive();
	}
	
	private String getDefaultSchemaName() {
		Schema defaultSchema = getConnection().getDefaultSchema();
		return (defaultSchema == null) ? null : defaultSchema.getName();
	}
	
	private Iterator<String> schemas() {
		String setValue = getStringProperty(USER_OVERRIDE_DEFAULT_SCHEMA);
		
		List<String> schemas = CollectionTools.sort(CollectionTools.list(
			new TransformationIterator<Schema, String>(getConnection().getDatabase().schemata()) {
				@Override
				protected String transform(Schema next) {
					return next.getName();
				}
			}));
		
		if (! StringTools.stringIsEmpty(setValue) && ! schemas.contains(setValue)) {
			return new CompositeIterator<String>(setValue, schemas.iterator());
		}
		else {
			return schemas.iterator();
		}
	}


	// ********** validation **********

	private IStatus validatePlatformId(String platformId) {
		return StringTools.stringIsEmpty(platformId) ?
				PLATFORM_NOT_SPECIFIED_STATUS
			:
				OK_STATUS;
	}

	private IStatus validateConnectionName(String connectionName) {
		if (StringTools.stringIsEmpty(connectionName)) {
			return OK_STATUS;
		}
		return JptDbPlugin.instance().getConnectionProfileRepository().connectionProfileNamed(connectionName).isActive() ?
				OK_STATUS
			:
				CONNECTION_NOT_CONNECTED_STATUS;
	}
	
	private IStatus validateUserOverrideDefaultSchema() {
		if (getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			if (StringTools.stringIsEmpty(getStringProperty(USER_OVERRIDE_DEFAULT_SCHEMA))) {
				return USER_OVERRIDE_DEFAULT_SCHEMA_NOT_SPECIFIED_STATUS;
			}
		}
		return OK_STATUS;
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
