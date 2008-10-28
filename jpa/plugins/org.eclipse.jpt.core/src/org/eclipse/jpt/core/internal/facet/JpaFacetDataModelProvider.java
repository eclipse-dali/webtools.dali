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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.platform.JpaPlatformRegistry;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonMessages;
import org.eclipse.wst.common.frameworks.internal.plugin.WTPCommonPlugin;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public class JpaFacetDataModelProvider extends FacetInstallDataModelProvider
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
		@SuppressWarnings("unchecked") Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(PLATFORM_ID);
		propertyNames.add(CONNECTION);
		propertyNames.add(CONNECTION_ACTIVE);
		propertyNames.add(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH);
		propertyNames.add(DB_DRIVER_NAME);
		propertyNames.add(DB_DRIVER_JARS);
		propertyNames.add(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA);
		propertyNames.add(USER_OVERRIDE_DEFAULT_SCHEMA);
		propertyNames.add(RUNTIME);
		propertyNames.add(USE_SERVER_JPA_IMPLEMENTATION);
		propertyNames.add(USE_USER_JPA_LIBRARY);
		propertyNames.add(JPA_LIBRARY);
		propertyNames.add(DISCOVER_ANNOTATED_CLASSES);
		propertyNames.add(LIST_ANNOTATED_CLASSES);
		propertyNames.add(CREATE_ORM_XML);
		return propertyNames;
	}
	
	@Override
	public boolean isPropertyEnabled(String propertyName) {
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA);
		}
		if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			return getConnectionProfile() != null;
		}
		if (propertyName.equals(DB_DRIVER_NAME)) {
			return getBooleanProperty(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH);
		}
		if (propertyName.equals(JPA_LIBRARY)) {
			return getBooleanProperty(USE_USER_JPA_LIBRARY);
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
			return null;
		}
		if (propertyName.equals(CONNECTION_ACTIVE)) {
			return Boolean.valueOf(connectionIsActive());
		}
		if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			return Boolean.valueOf(connectionIsActive());
		}
		if (propertyName.equals(DB_DRIVER_NAME)) {
			return getDefaultDriverName();
		}
		if (propertyName.equals(DB_DRIVER_JARS)) {
			return getDefaultDriverJars();
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
		if (propertyName.equals(USE_USER_JPA_LIBRARY)) {
			return Boolean.valueOf( ! getBooleanProperty(USE_SERVER_JPA_IMPLEMENTATION));
		}
		if (propertyName.equals(JPA_LIBRARY)) {
			return JptCorePlugin.getDefaultJpaLibrary();
		}
		if (propertyName.equals(DISCOVER_ANNOTATED_CLASSES)) {
			return Boolean.valueOf(this.runtimeSupportsEjb30(this.runtime()));
		}
		if (propertyName.equals(LIST_ANNOTATED_CLASSES)) {
			return Boolean.valueOf( ! getBooleanProperty(DISCOVER_ANNOTATED_CLASSES));
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
			this.model.notifyPropertyChange(USE_USER_JPA_LIBRARY, IDataModel.DEFAULT_CHG);
			// need to fire that the default change for using user library may have
			// actually changed enablement for library
			this.model.notifyPropertyChange(JPA_LIBRARY, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(DISCOVER_ANNOTATED_CLASSES, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(LIST_ANNOTATED_CLASSES, IDataModel.DEFAULT_CHG);
		}
		if (propertyName.equals(CONNECTION)) {
			this.model.notifyPropertyChange(CONNECTION, IDataModel.VALID_VALUES_CHG);
			this.model.setBooleanProperty(CONNECTION_ACTIVE, connectionIsActive());
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.VALID_VALUES_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_JARS, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_JARS, IDataModel.VALID_VALUES_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.VALID_VALUES_CHG);
		}
		if (propertyName.equals(CONNECTION_ACTIVE)) {
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.VALID_VALUES_CHG);
			this.model.notifyPropertyChange(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_JARS, IDataModel.ENABLE_CHG);
		}
		if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_JARS, IDataModel.ENABLE_CHG);
			if (! ((Boolean) propertyValue).booleanValue()) {
				this.model.setProperty(DB_DRIVER_NAME, null);
				this.model.setProperty(DB_DRIVER_JARS, null);
			}
		}
		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.ENABLE_CHG);
			if (! ((Boolean) propertyValue).booleanValue()) {
				this.model.setProperty(USER_OVERRIDE_DEFAULT_SCHEMA, null);
			}
		}
		if (propertyName.equals(USE_SERVER_JPA_IMPLEMENTATION)) {
			this.model.setBooleanProperty(USE_USER_JPA_LIBRARY, ! ((Boolean) propertyValue).booleanValue());
		}
		if (propertyName.equals(USE_USER_JPA_LIBRARY)) {
			this.model.setBooleanProperty(USE_SERVER_JPA_IMPLEMENTATION, ! ((Boolean) propertyValue).booleanValue());
			this.model.notifyPropertyChange(JPA_LIBRARY, IDataModel.ENABLE_CHG);
		}
		if (propertyName.equals(DISCOVER_ANNOTATED_CLASSES)) {
			this.model.setBooleanProperty(LIST_ANNOTATED_CLASSES, ! ((Boolean) propertyValue).booleanValue());
		}
		if (propertyName.equals(LIST_ANNOTATED_CLASSES)) {
			this.model.setBooleanProperty(DISCOVER_ANNOTATED_CLASSES, ! ((Boolean) propertyValue).booleanValue());
		}
		return ok;
	}

	private static final DataModelPropertyDescriptor[] EMPTY_DMPD_ARRAY = new DataModelPropertyDescriptor[0];

	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(PLATFORM_ID)) {
			return CollectionTools.array(
				new TransformationIterator<String, DataModelPropertyDescriptor>(
						JpaPlatformRegistry.instance().jpaPlatformIds()) {
					@Override
					protected DataModelPropertyDescriptor transform(String platformId) {
						return platformIdPropertyDescriptor(platformId);
					}
				},
				EMPTY_DMPD_ARRAY);
		}
		if (propertyName.equals(CONNECTION)) {
			return CollectionTools.array(
				new TransformationIterator<String, DataModelPropertyDescriptor>(
						new CompositeIterator<String>(null, connectionNames())) {
					@Override
					protected DataModelPropertyDescriptor transform(String next) {
						return connectionPropertyDescriptor(next);
					}
				},
				EMPTY_DMPD_ARRAY);
		}
		if (propertyName.equals(DB_DRIVER_NAME)) {
			return CollectionTools.array(
				new TransformationIterator<String, DataModelPropertyDescriptor>(driverNames()) {
					@Override
					protected DataModelPropertyDescriptor transform(String next) {
						return new DataModelPropertyDescriptor(next);
					}
				},
				EMPTY_DMPD_ARRAY);
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return CollectionTools.array(
				new TransformationIterator<String, DataModelPropertyDescriptor>(schemaNames()) {
					@Override
					protected DataModelPropertyDescriptor transform(String next) {
						return new DataModelPropertyDescriptor(next);
					}
				},
				EMPTY_DMPD_ARRAY);
		}
		if (propertyName.equals(JPA_LIBRARY)) {
			String[] libraries = CollectionTools.sort(JavaCore.getUserLibraryNames());
			DataModelPropertyDescriptor[] descriptors = new DataModelPropertyDescriptor[libraries.length + 1];
			descriptors[0] = new DataModelPropertyDescriptor("", RUNTIME_NONE); //$NON-NLS-1$

			int i = 1;
			for (String library : libraries) {
				descriptors[i++] = new DataModelPropertyDescriptor(library, library);
			}
			return descriptors;
		}

		return super.getValidPropertyDescriptors(propertyName);
	}
	
	@Override
	public DataModelPropertyDescriptor getPropertyDescriptor(String propertyName) {
		if (propertyName.equals(PLATFORM_ID)) {
			return platformIdPropertyDescriptor(getStringProperty(PLATFORM_ID));
		}
		if (propertyName.equals(CONNECTION)) {
			return connectionPropertyDescriptor(getStringProperty(CONNECTION));
		}
		return super.getPropertyDescriptor(propertyName);
	}
	
	DataModelPropertyDescriptor platformIdPropertyDescriptor(String platformId) {
		return new DataModelPropertyDescriptor(
			platformId, JpaPlatformRegistry.instance().getJpaPlatformLabel(platformId));
	}
	
	DataModelPropertyDescriptor connectionPropertyDescriptor(String connection) {
		return StringTools.stringIsEmpty(connection) ?
					new DataModelPropertyDescriptor(null, JptCoreMessages.NONE)
				:
					new DataModelPropertyDescriptor(connection);
	}

	@Override
	public IStatus validate(String name) {
		if (name.equals(PLATFORM_ID)) {
			return this.validatePlatformId(this.getStringProperty(name));
		}
		if (name.equals(CONNECTION)) {
			return this.validateConnectionName(this.getStringProperty(name));
		}
		if (name.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)
				|| name.equals(DB_DRIVER_NAME)) {
			return this.validateDbDriverName();
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
		IProjectFacetVersion ejb30 = ProjectFacetsManager.getProjectFacet(EJB_FACET_ID).getVersion("3.0"); //$NON-NLS-1$
		return (runtime == null) ? false : runtime.supports(ejb30);
	}

	private String getConnectionName() {
		return this.getStringProperty(CONNECTION);
	}
	
	private ConnectionProfile getConnectionProfile() {
		return this.buildConnectionProfile(this.getConnectionName());
	}

	private ConnectionProfileFactory getConnectionProfileFactory() {
		// we don't have a JPA project yet, so go to the db plug-in directly to get the factory
		return JptDbPlugin.instance().getConnectionProfileFactory();
	}

	private ConnectionProfile buildConnectionProfile(String name) {
		return this.getConnectionProfileFactory().buildConnectionProfile(name);
	}

	private boolean connectionIsActive() {
		return this.connectionIsActive(this.getConnectionName());
	}

	private boolean connectionIsActive(String connectionName) {
		ConnectionProfile cp = this.buildConnectionProfile(connectionName);
		return (cp != null) && cp.isActive();
	}

	private String getDefaultDriverName() {
		ConnectionProfile cp = this.getConnectionProfile();
		if (cp == null) {
			return null;
		}
		return cp.getDriverName();
	}

	private String getDefaultDriverJars() {
		ConnectionProfile cp = this.getConnectionProfile();
		if (cp == null) {
			return null;
		}
		return cp.getDriverJarList(this.getStringProperty(DB_DRIVER_NAME));
	}

	private Iterator<String> connectionNames() {
		String setValue = getStringProperty(CONNECTION);
		
		List<String> connectionNames = CollectionTools.sort(CollectionTools.list(
			this.getConnectionProfileFactory().connectionProfileNames()));
		
		if (! StringTools.stringIsEmpty(setValue) && ! connectionNames.contains(setValue)) {
			return new CompositeIterator<String>(setValue, connectionNames.iterator());
		}
		return connectionNames.iterator();
	}
	
	private String getDefaultSchemaName() {
		ConnectionProfile cp = this.getConnectionProfile();
		if (cp == null) {
			return null;
		}
		Database db = cp.getDatabase();
		if (db == null) {
			return null;
		}
		Schema schema = db.getDefaultSchema();
		return (schema == null) ? null : schema.getIdentifier();
	}

	private List<String> buildSortedSchemaNames() {
		ConnectionProfile cp = this.getConnectionProfile();
		if (cp == null) {
			return Collections.emptyList();
		}
		Database db = cp.getDatabase();
		if (db == null) {
			return Collections.emptyList();
		}
		// TODO catalogs...
		return CollectionTools.list(db.sortedSchemaIdentifiers());  // use identifiers? names seem OK since combo-box is read-only?
	}

	private Iterator<String> schemaNames() {
		String setValue = getStringProperty(USER_OVERRIDE_DEFAULT_SCHEMA);
		List<String> schemaNames = this.buildSortedSchemaNames();
		
		if (StringTools.stringIsEmpty(setValue) || schemaNames.contains(setValue)) {
			return schemaNames.iterator();
		}
		return new CompositeIterator<String>(setValue, schemaNames.iterator());
	}
	
	private Iterator<String> driverNames() {
		String setValue = getStringProperty(DB_DRIVER_NAME);
		List<String> driverNames = this.buildSortedDriverNames();
		
		if (StringTools.stringIsEmpty(setValue) || driverNames.contains(setValue)) {
			return driverNames.iterator();
		}
		return new CompositeIterator<String>(setValue, driverNames.iterator());
	}

	private List<String> buildSortedDriverNames() {
		ConnectionProfile cp = this.getConnectionProfile();
		if (cp == null) {
			return Collections.emptyList();
		}
		return CollectionTools.list(cp.sortedDriverNames());
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
		ConnectionProfile connectionProfile = getConnectionProfile();
		if (connectionProfile == null) {
			return buildErrorStatus(NLS.bind(JptCoreMessages.VALIDATE_CONNECTION_INVALID, connectionName));
	
		}
		if (! connectionProfile.isActive()) {
			return CONNECTION_NOT_CONNECTED_STATUS;
		}
		return OK_STATUS;
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

	private IStatus validateDbDriverName() {
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
