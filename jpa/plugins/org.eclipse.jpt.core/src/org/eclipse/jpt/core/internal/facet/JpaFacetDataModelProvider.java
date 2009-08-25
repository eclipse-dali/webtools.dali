/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.platform.JpaPlatformRegistry;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.DatabaseFinder;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jst.common.project.facet.core.libprov.IPropertyChangeListener;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public class JpaFacetDataModelProvider
	extends FacetInstallDataModelProvider
	implements JpaFacetDataModelProperties
{
	private LibraryInstallDelegate defaultLibraryProvider;

	/** cache the connection profile - change it whenever the user selects a different name */
	private ConnectionProfile connectionProfile;
	
	
	private static final String EJB_FACET_ID = IModuleConstants.JST_EJB_MODULE;

	private static final IStatus PLATFORM_NOT_SPECIFIED_STATUS = 
			buildErrorStatus(JptCoreMessages.VALIDATE_PLATFORM_NOT_SPECIFIED);
	
	private static final IStatus CONNECTION_NOT_CONNECTED_STATUS = 
			buildInfoStatus(JptCoreMessages.VALIDATE_CONNECTION_NOT_CONNECTED);
	
	private static final IStatus USER_OVERRIDE_DEFAULT_CATALOG_NOT_SPECIFIED_STATUS = 
			buildErrorStatus(JptCoreMessages.VALIDATE_DEFAULT_CATALOG_NOT_SPECIFIED);
	
	private static final IStatus USER_OVERRIDE_DEFAULT_SCHEMA_NOT_SPECIFIED_STATUS = 
			buildErrorStatus(JptCoreMessages.VALIDATE_DEFAULT_SCHEMA_NOT_SPECIFIED);
	

	/**
	 * required default constructor
	 */
	public JpaFacetDataModelProvider() {
		super();
	}

	@Override
	public Set<String> getPropertyNames() {
		@SuppressWarnings("unchecked") Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(RUNTIME);
		propertyNames.add(PLATFORM_ID);
		propertyNames.add(LIBRARY_PROVIDER_DELEGATE);
		propertyNames.add(CONNECTION);
		propertyNames.add(CONNECTION_ACTIVE);
		propertyNames.add(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH);
		propertyNames.add(DB_DRIVER_NAME);
		propertyNames.add(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG);
		propertyNames.add(USER_OVERRIDE_DEFAULT_CATALOG);
		propertyNames.add(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA);
		propertyNames.add(USER_OVERRIDE_DEFAULT_SCHEMA);
		propertyNames.add(DISCOVER_ANNOTATED_CLASSES);
		propertyNames.add(LIST_ANNOTATED_CLASSES);
		propertyNames.add(CREATE_ORM_XML);
		return propertyNames;
	}


	// ********** properties **********

	private String getPlatformId() {
		return (String) this.getProperty(PLATFORM_ID);
	}
	
	private String getConnectionName() {
		return (String) this.getProperty(CONNECTION);
	}

	private boolean userWantsToAddDbDriverJarsToClasspath() {
		return this.getBooleanProperty(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH);
	}
	
	private String getDriverName() {
		return (String) this.getProperty(DB_DRIVER_NAME);
	}

	private boolean userWantsToOverrideDefaultCatalog() {
		return this.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG);
	}
	
	private String getUserOverrideDefaultCatalog() {
		return (String) this.getProperty(USER_OVERRIDE_DEFAULT_CATALOG);
	}

	private boolean userWantsToOverrideDefaultSchema() {
		return this.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA);
	}
	
	private String getUserOverrideDefaultSchema() {
		return (String) this.getProperty(USER_OVERRIDE_DEFAULT_SCHEMA);
	}

	private boolean discoverAnnotatedClasses() {
		return this.getBooleanProperty(DISCOVER_ANNOTATED_CLASSES);
	}

	private IFacetedProjectWorkingCopy getFacetedProjectWorkingCopy() {
		return (IFacetedProjectWorkingCopy) this.getProperty(FACETED_PROJECT_WORKING_COPY);
	}

	private IProjectFacetVersion getProjectFacetVersion() {
		return (IProjectFacetVersion) this.getProperty(FACET_VERSION);
	}

	private IRuntime getRuntime() {
		return (IRuntime) this.getProperty(RUNTIME);
	}

	private LibraryInstallDelegate getLibraryInstallDelegate() {
		return (LibraryInstallDelegate) this.getProperty(LIBRARY_PROVIDER_DELEGATE);
	}


	// ********** enabled **********

	@Override
	public boolean isPropertyEnabled(String propertyName) {
		if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			return (this.getConnectionProfile() != null);
		}
		if (propertyName.equals(DB_DRIVER_NAME)) {
			return this.userWantsToAddDbDriverJarsToClasspath();
		}

		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG)) {
			return this.connectionIsActive() && this.databaseSupportsCatalogs();
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_CATALOG)) {
			return this.userWantsToOverrideDefaultCatalog();
		}

		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			return this.connectionIsActive();
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return this.userWantsToOverrideDefaultSchema();
		}

		return super.isPropertyEnabled(propertyName);
	}


	// ********** defaults **********

	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(RUNTIME)) {
			return null;
		}
		if (propertyName.equals(FACET_ID)) {
			return JptCorePlugin.FACET_ID;
		}
		if (propertyName.equals(PLATFORM_ID)) {
			return JptCorePlugin.getDefaultJpaPlatformId();
		}
		if (propertyName.equals(LIBRARY_PROVIDER_DELEGATE)) {
			return this.getDefaultLibraryProvider();
		}
		if (propertyName.equals(CONNECTION)) {
			return null;
		}
		if (propertyName.equals(CONNECTION_ACTIVE)) {
			return Boolean.valueOf(this.connectionIsActive());
		}
		if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			return Boolean.FALSE;
		}
		if (propertyName.equals(DB_DRIVER_NAME)) {
			return this.getDefaultDriverName();
		}
		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG)) {
			return Boolean.FALSE;
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_CATALOG)) {
			return this.getDefaultCatalogName();
		}
		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			return Boolean.FALSE;
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return this.getDefaultSchemaName();
		}
		if (propertyName.equals(DISCOVER_ANNOTATED_CLASSES)) {
			return Boolean.valueOf(this.runtimeSupportsEjb30());
		}
		if (propertyName.equals(LIST_ANNOTATED_CLASSES)) {
			return Boolean.valueOf( ! this.discoverAnnotatedClasses());
		}
		if (propertyName.equals(CREATE_ORM_XML)) {
			return Boolean.TRUE;
		}
		
		return super.getDefaultProperty(propertyName);
	}

	private LibraryInstallDelegate getDefaultLibraryProvider() {
		// delegate itself changes, not the instance of delegate
		if (this.defaultLibraryProvider == null) {
			this.defaultLibraryProvider = this.buildDefaultLibraryProvider();
		}
		return defaultLibraryProvider;
	}
	
	private LibraryInstallDelegate buildDefaultLibraryProvider() {
		IFacetedProjectWorkingCopy fpjwc = this.getFacetedProjectWorkingCopy();
		if (fpjwc == null) {
			return null;
		}
		IProjectFacetVersion pfv = this.getProjectFacetVersion();
		if (pfv == null) {
			return null;
		}
		LibraryInstallDelegate lp = new LibraryInstallDelegate(fpjwc, pfv, this.buildEnablementVariables());
		lp.addListener(this.buildLibraryProviderListener());
		return lp;
	}

	private Map<String, Object> buildEnablementVariables() {
		Map<String, Object> enablementVariables = new HashMap<String, Object>();
		enablementVariables.put(JpaLibraryProviderConstants.EXPR_VAR_JPA_PLATFORM, this.getPlatformId());
		return enablementVariables;
	}

	private IPropertyChangeListener buildLibraryProviderListener() {
		return new IPropertyChangeListener() {
				public void propertyChanged(String property, Object oldValue, Object newValue ) {
					JpaFacetDataModelProvider.this.getDataModel().notifyPropertyChange(LIBRARY_PROVIDER_DELEGATE, IDataModel.VALUE_CHG);
				}
			};
	}

	private String getDefaultDriverName() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp == null) ? null : cp.getDriverName();
	}

	private String getDefaultCatalogName() {
		Database db = this.getDatabase();
		return (db == null) ? null : db.getDefaultCatalogIdentifier();
	}

	private String getDefaultSchemaName() {
		SchemaContainer sc = this.getSchemaContainer();
		return (sc == null) ? null : sc.getDefaultSchemaIdentifier();
	}

	private boolean runtimeSupportsEjb30() {
		IRuntime runtime = this.getRuntime();
		return (runtime != null) && runtime.supports(this.getEJB30());
	}

	private IProjectFacetVersion getEJB30() {
		return ProjectFacetsManager.getProjectFacet(EJB_FACET_ID).getVersion("3.0"); //$NON-NLS-1$
	}


	// ********** synchronize data model **********

	/**
	 * The specified property's value has changed to the specified value.
	 * Return whether to fire a VALUE_CHG DataModelEvent.
	 */
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		
		if (propertyName.equals(FACETED_PROJECT_WORKING_COPY)) {
			//no-op
		}
		else if (propertyName.equals(FACET_VERSION)) {
			this.model.notifyPropertyChange(LIBRARY_PROVIDER_DELEGATE, IDataModel.DEFAULT_CHG);
		}
		else if (propertyName.equals(RUNTIME)) {
			LibraryInstallDelegate lid = this.getLibraryInstallDelegate();
			if (lid != null) {
				// may be null while model is being built up
				// ... or in tests
				lid.refresh();
			}
			this.model.notifyPropertyChange(DISCOVER_ANNOTATED_CLASSES, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(LIST_ANNOTATED_CLASSES, IDataModel.DEFAULT_CHG);
		}
		else if (propertyName.equals(PLATFORM_ID)) {
			LibraryInstallDelegate lid = this.getLibraryInstallDelegate();
			if (lid != null) {
				// may be null while model is being built up
				// ... or in tests
				lid.setEnablementContextVariable(JpaLibraryProviderConstants.EXPR_VAR_JPA_PLATFORM, propertyValue);
			}
		}
		else if (propertyName.equals(CONNECTION)) {
			this.setBooleanProperty(CONNECTION_ACTIVE, this.connectionIsActive());

			// JpaFacetWizardPage sets the connection when the user adds a new connection
			// implying that there is a new set of valid connections to choose from
			this.model.notifyPropertyChange(CONNECTION, IDataModel.VALID_VALUES_CHG);

			// db driver
			if (propertyValue == null) {  // connection set to '<None>'
				this.setBooleanProperty(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH, false);
			}
			this.model.notifyPropertyChange(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.VALID_VALUES_CHG);

			// catalog
			if ((propertyValue != null) && this.databaseSupportsCatalogs()) {  // connection set to something that supports catalogs
				this.setProperty(USER_OVERRIDE_DEFAULT_CATALOG, this.getDefaultCatalogName());
			} else {  // connection either '<None>' or non-catalog database
				this.setBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG, false);
			}
			this.model.notifyPropertyChange(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_CATALOG, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_CATALOG, IDataModel.VALID_VALUES_CHG);

			// schema
			if (propertyValue == null) {  // connection set to '<None>'
				this.setBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA, false);
			} else {
				this.setProperty(USER_OVERRIDE_DEFAULT_SCHEMA, this.getDefaultSchemaName());
			}
			this.model.notifyPropertyChange(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.VALID_VALUES_CHG);
		}
		else if (propertyName.equals(CONNECTION_ACTIVE)) {
			// catalog
			if (this.propertyValueIsFalse(propertyValue)) {  // connection is inactive
				this.setBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG, false);
			}
			this.model.notifyPropertyChange(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_CATALOG, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_CATALOG, IDataModel.VALID_VALUES_CHG);

			// schema
			if (this.propertyValueIsFalse(propertyValue)) {  // connection is inactive
				this.setBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA, false);
			}
			this.model.notifyPropertyChange(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA, IDataModel.ENABLE_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.VALID_VALUES_CHG);
		}
		else if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)) {
			this.model.notifyPropertyChange(DB_DRIVER_NAME, IDataModel.ENABLE_CHG);
			if (this.propertyValueIsFalse(propertyValue)) {
				this.setProperty(DB_DRIVER_NAME, null);
			}
		}
		else if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG)) {
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_CATALOG, IDataModel.ENABLE_CHG);
			if (this.propertyValueIsFalse(propertyValue)) {
				this.setProperty(USER_OVERRIDE_DEFAULT_CATALOG, null);
			}
		}
		else if (propertyName.equals(USER_OVERRIDE_DEFAULT_CATALOG)) {
			this.setProperty(USER_OVERRIDE_DEFAULT_SCHEMA, this.getDefaultSchemaName());
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.VALID_VALUES_CHG);
		}
		else if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_SCHEMA, IDataModel.ENABLE_CHG);
			if (this.propertyValueIsFalse(propertyValue)) {
				this.setProperty(USER_OVERRIDE_DEFAULT_SCHEMA, null);
			}
		}
		else if (propertyName.equals(DISCOVER_ANNOTATED_CLASSES)) {
			this.setBooleanProperty(LIST_ANNOTATED_CLASSES, this.propertyValueIsFalse(propertyValue));
		}
		else if (propertyName.equals(LIST_ANNOTATED_CLASSES)) {
			this.setBooleanProperty(DISCOVER_ANNOTATED_CLASSES, this.propertyValueIsFalse(propertyValue));
		}
		return ok;
	}

	private boolean propertyValueIsFalse(Object propertyValue) {
		return ! this.propertyValueIsTrue(propertyValue);
	}

	private boolean propertyValueIsTrue(Object propertyValue) {
		return ((Boolean) propertyValue).booleanValue();
	}


	// ********** property descriptors **********

	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(PLATFORM_ID)) {
			return this.buildValidPlatformDescriptors();
		}

		if (propertyName.equals(CONNECTION)) {
			return this.buildValidConnectionDescriptors();
		}

		if (propertyName.equals(DB_DRIVER_NAME)) {
			return this.buildValidDriverDescriptors();
		}

		if (propertyName.equals(USER_OVERRIDE_DEFAULT_CATALOG)) {
			return this.buildValidCatalogDescriptors();
		}

		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return this.buildValidSchemaDescriptors();
		}

		return super.getValidPropertyDescriptors(propertyName);
	}

	private DataModelPropertyDescriptor[] buildValidPlatformDescriptors() {
		List<String> platformIDs = CollectionTools.list(JpaPlatformRegistry.instance().jpaPlatformIds());
		DataModelPropertyDescriptor[] descriptors = new DataModelPropertyDescriptor[platformIDs.size()];
		for (int i = 0; i < descriptors.length; i++) {
			descriptors[i] = this.buildPlatformIdDescriptor(platformIDs.get(i));
		}
		return CollectionTools.sort(descriptors, this.buildDescriptorComparator());
	}

	/**
	 * sort the descriptors by 'description' (as opposed to 'value')
	 */
	private Comparator<DataModelPropertyDescriptor> buildDescriptorComparator() {
		return new Comparator<DataModelPropertyDescriptor>() {
				public int compare(DataModelPropertyDescriptor o1, DataModelPropertyDescriptor o2) {
					return (o1.getPropertyDescription().compareTo(o2.getPropertyDescription()));
				}
			};
	}

	private DataModelPropertyDescriptor[] buildValidConnectionDescriptors() {
		List<String> connectionNames = this.buildValidConnectionNames();
		DataModelPropertyDescriptor[] descriptors = new DataModelPropertyDescriptor[connectionNames.size()];
		for (int i = 0; i < descriptors.length; i++) {
			descriptors[i] = this.buildConnectionDescriptor(connectionNames.get(i));
		}
		return descriptors;
	}

	/**
	 * put a null entry at the top of the list (for <none>)
	 */
	private List<String> buildValidConnectionNames() {
		List<String> connectionNames = CollectionTools.sort(CollectionTools.list(this.connectionProfileNames()));
		connectionNames.add(0, null);
		return connectionNames;
	}

	private DataModelPropertyDescriptor[] buildValidDriverDescriptors() {
		return new DataModelPropertyDescriptor[] { new DataModelPropertyDescriptor(this.getDriverName()) };
	}

	private DataModelPropertyDescriptor[] buildValidCatalogDescriptors() {
		Database db = this.getDatabase();
		return (db == null) ? EMPTY_DMPD_ARRAY : this.buildDescriptors(this.buildValidCatalogNames(db));
	}

	/**
	 * pre-condition: 'db' is not null
	 */
	private List<String> buildValidCatalogNames(Database db) {
		return this.buildValidStrings(db.sortedCatalogIdentifiers(), this.getDefaultCatalogName());
	}

	private DataModelPropertyDescriptor[] buildValidSchemaDescriptors() {
		Database db = this.getDatabase();
		return (db == null) ? EMPTY_DMPD_ARRAY : this.buildDescriptors(this.buildValidSchemaNames());
	}

	private List<String> buildValidSchemaNames() {
		return this.buildValidStrings(this.schemaNames(), this.getDefaultSchemaName());
	}

	private Iterator<String> schemaNames() {
		SchemaContainer sc = this.getSchemaContainer();
		return (sc == null) ? EmptyIterator.<String>instance() : sc.sortedSchemaIdentifiers();
	}

	/**
	 * put an entry for the default at the top of the list
	 */
	private List<String> buildValidStrings(Iterator<String> stream, String defaultString) {
		List<String> strings = CollectionTools.list(stream);
		if ((defaultString != null) && ! strings.contains(defaultString)) {
			strings.add(0, defaultString);
		}
		return strings;
	}

	private DataModelPropertyDescriptor[] buildDescriptors(List<String> strings) {
		DataModelPropertyDescriptor[] descriptors = new DataModelPropertyDescriptor[strings.size()];
		for (int i = 0; i < descriptors.length; i++) {
			descriptors[i] = new DataModelPropertyDescriptor(strings.get(i));
		}
		return descriptors;
	}

	private static final DataModelPropertyDescriptor[] EMPTY_DMPD_ARRAY = new DataModelPropertyDescriptor[0];

	/**
	 * platform and connection have 'description's (in addition to 'value's)
	 */
	@Override
	public DataModelPropertyDescriptor getPropertyDescriptor(String propertyName) {
		if (propertyName.equals(PLATFORM_ID)) {
			return this.buildPlatformIdDescriptor(this.getPlatformId());
		}
		if (propertyName.equals(CONNECTION)) {
			return this.buildConnectionDescriptor(this.getConnectionName());
		}
		return super.getPropertyDescriptor(propertyName);
	}

	DataModelPropertyDescriptor buildPlatformIdDescriptor(String platformId) {
		return new DataModelPropertyDescriptor(platformId, this.getPlatformLabel(platformId));
	}

	private String getPlatformLabel(String platformId) {
		return JpaPlatformRegistry.instance().getJpaPlatformLabel(platformId);
	}

	private DataModelPropertyDescriptor buildConnectionDescriptor(String connectionName) {
		String description = (connectionName == null) ? JptCoreMessages.NONE : null;
		return new DataModelPropertyDescriptor(connectionName, description);
	}


	// ********** database **********

	private SchemaContainer getSchemaContainer() {
		return this.databaseSupportsCatalogs() ? this.getCatalog() : this.getDatabase();
	}

	private Catalog getCatalog() {
		String name = this.getUserOverrideDefaultCatalog();
		return (name == null) ? null : this.getCatalog(name);
	}

	/**
	 * pre-condition: 'name' is not null
	 */
	private Catalog getCatalog(String name) {
		Database db = this.getDatabase();
		return (db == null) ? null : db.getCatalogForIdentifier(name);
	}

	private boolean databaseSupportsCatalogs() {
		Database db = this.getDatabase();
		return (db != null) && db.supportsCatalogs();
	}
	
	private Database getDatabase() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp == null) ? null : cp.getDatabase();
	}

	private boolean connectionIsActive() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp != null) && cp.isActive();
	}

	private ConnectionProfile getConnectionProfile() {
		String name = this.getConnectionName();
		return (name == null) ? null : this.getConnectionProfile(name);
	}

	/**
	 * pre-condition: 'name' is not null
	 */
	private ConnectionProfile getConnectionProfile(String name) {
		if (this.cachedConnectionProfileIsStale(name)) {
			this.connectionProfile = this.buildConnectionProfile(name);
		}
		return this.connectionProfile;
	}

	private boolean cachedConnectionProfileIsStale(String name) {
		return (this.connectionProfile == null) || ! this.connectionProfile.getName().equals(name);
	}

	private ConnectionProfile buildConnectionProfile(String name) {
		return this.getConnectionProfileFactory().buildConnectionProfile(name, DatabaseFinder.Default.instance());
	}

	private Iterator<String> connectionProfileNames() {
		return this.getConnectionProfileFactory().connectionProfileNames();
	}
	
	private ConnectionProfileFactory getConnectionProfileFactory() {
		// we don't have a JPA project yet, so go to the db plug-in directly to get the factory
		return JptDbPlugin.instance().getConnectionProfileFactory();
	}


	// ********** validation **********

	@Override
	public IStatus validate(String propertyName) {
		if (propertyName.equals(PLATFORM_ID)) {
			return this.validatePlatformId();
		}
		if (propertyName.equals(LIBRARY_PROVIDER_DELEGATE)) {
		    return this.getLibraryInstallDelegate().validate();
		}
		if (propertyName.equals(CONNECTION)) {
			return this.validateConnection();
		}
		if (propertyName.equals(USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH)
				|| propertyName.equals(DB_DRIVER_NAME)) {
			return this.validateDbDriverName();
		}
		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG)
				|| propertyName.equals(USER_OVERRIDE_DEFAULT_CATALOG)) {
			return this.validateUserOverrideDefaultCatalog();
		}
		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)
				|| propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return this.validateUserOverrideDefaultSchema();
		}
		if (propertyName.equals(DISCOVER_ANNOTATED_CLASSES)) {
			return this.validatePersistentClassManagement();
		}
		
		return super.validate(propertyName);
	}

	private IStatus validatePlatformId() {
		return (this.getPlatformId() == null) ? PLATFORM_NOT_SPECIFIED_STATUS : OK_STATUS;
	}

	private IStatus validateConnection() {
		String connectionName = this.getConnectionName();
		return (connectionName == null) ? OK_STATUS : this.validateNonNullConnection(connectionName);
	}
	
	private IStatus validateNonNullConnection(String connectionName) {
		ConnectionProfile cp = this.getConnectionProfile(connectionName);
		if (cp == null) {
			return buildErrorStatus(NLS.bind(JptCoreMessages.VALIDATE_CONNECTION_INVALID, connectionName));
		}
		if ( ! cp.isActive()) {
			return CONNECTION_NOT_CONNECTED_STATUS;
		}
		return OK_STATUS;
	}
	
	private IStatus validateDbDriverName() {
		return OK_STATUS;
	}

	private IStatus validateUserOverrideDefaultCatalog() {
		if (this.userWantsToOverrideDefaultCatalog()) {
			if (this.getUserOverrideDefaultCatalog() == null) {
				return USER_OVERRIDE_DEFAULT_CATALOG_NOT_SPECIFIED_STATUS;
			}
		}
		return OK_STATUS;
	}
	
	private IStatus validateUserOverrideDefaultSchema() {
		if (this.userWantsToOverrideDefaultSchema()) {
			if (this.getUserOverrideDefaultSchema() == null) {
				return USER_OVERRIDE_DEFAULT_SCHEMA_NOT_SPECIFIED_STATUS;
			}
		}
		return OK_STATUS;
	}

	private IStatus validatePersistentClassManagement() {
		@SuppressWarnings("unused") boolean discoverAnnotatedClasses = this.discoverAnnotatedClasses();
		// TODO warning if "discovery" is used, but no runtime specified ??
		return OK_STATUS;
	}


	// ********** static methods **********

	private static IStatus buildInfoStatus(String message) {
		return buildStatus(IStatus.INFO, message);
	}

//	private static IStatus buildWarningStatus(String message) {
//		return buildStatus(IStatus.WARNING, message);
//	}

	private static IStatus buildErrorStatus(String message) {
		return buildStatus(IStatus.ERROR, message);
	}

	private static IStatus buildStatus(int severity, String message) {
		return new Status(severity, JptCorePlugin.PLUGIN_ID, message);
	}

}
