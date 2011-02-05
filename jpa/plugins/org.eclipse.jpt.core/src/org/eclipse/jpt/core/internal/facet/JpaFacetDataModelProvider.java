/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.libprov.JpaLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.DatabaseIdentifierAdapter;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;
import org.eclipse.jst.common.project.facet.core.libprov.IPropertyChangeListener;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderOperationConfig;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public abstract class JpaFacetDataModelProvider
	extends FacetInstallDataModelProvider
	implements JpaFacetDataModelProperties
{
	protected static final String EJB_FACET_ID = IModuleConstants.JST_EJB_MODULE;
	
	protected static final IStatus PLATFORM_NOT_SPECIFIED_STATUS = 
			buildErrorStatus(JptCoreMessages.VALIDATE_PLATFORM_NOT_SPECIFIED);
	
	protected static final IStatus CONNECTION_NOT_CONNECTED_STATUS = 
			buildInfoStatus(JptCoreMessages.VALIDATE_CONNECTION_NOT_CONNECTED);
	
	protected static final IStatus USER_OVERRIDE_DEFAULT_CATALOG_NOT_SPECIFIED_STATUS = 
			buildErrorStatus(JptCoreMessages.VALIDATE_DEFAULT_CATALOG_NOT_SPECIFIED);
	
	protected static final IStatus USER_OVERRIDE_DEFAULT_SCHEMA_NOT_SPECIFIED_STATUS = 
			buildErrorStatus(JptCoreMessages.VALIDATE_DEFAULT_SCHEMA_NOT_SPECIFIED);
	
	
	private LibraryInstallDelegate defaultLibraryProvider;
	
	/** cache the connection profile - change it whenever the user selects a different name */
	private ConnectionProfile connectionProfile;
	
	
	/**
	 * required default constructor
	 */
	protected JpaFacetDataModelProvider() {
		super();
	}
	
	
	@Override
	public Set<String> getPropertyNames() {
		@SuppressWarnings("unchecked") Set<String> propertyNames = super.getPropertyNames();
		propertyNames.add(RUNTIME);
		propertyNames.add(PLATFORM);
		propertyNames.add(LIBRARY_PROVIDER_DELEGATE);
		propertyNames.add(CONNECTION);
		propertyNames.add(CONNECTION_ACTIVE);
		propertyNames.add(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG);
		propertyNames.add(USER_OVERRIDE_DEFAULT_CATALOG);
		propertyNames.add(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA);
		propertyNames.add(USER_OVERRIDE_DEFAULT_SCHEMA);
		propertyNames.add(DISCOVER_ANNOTATED_CLASSES);
		propertyNames.add(LIST_ANNOTATED_CLASSES);
		return propertyNames;
	}
	
	
	// ********** properties **********
	
	protected IFacetedProjectWorkingCopy getFacetedProjectWorkingCopy() {
		return (IFacetedProjectWorkingCopy) this.getProperty(FACETED_PROJECT_WORKING_COPY);
	}
	
	protected IProjectFacetVersion getProjectFacetVersion() {
		return (IProjectFacetVersion) this.getProperty(FACET_VERSION);
	}
	
	protected IRuntime getRuntime() {
		return (IRuntime) this.getProperty(RUNTIME);
	}
	
	protected JpaPlatformDescription getPlatform() {
		return (JpaPlatformDescription) getProperty(PLATFORM);
	}
	
	protected LibraryInstallDelegate getLibraryInstallDelegate() {
		return (LibraryInstallDelegate) this.getProperty(LIBRARY_PROVIDER_DELEGATE);
	}
	
	protected String getConnectionName() {
		return (String) this.getProperty(CONNECTION);
	}
	
	protected boolean userWantsToOverrideDefaultCatalog() {
		return this.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG);
	}
	
	protected String getUserOverrideDefaultCatalog() {
		return (String) this.getProperty(USER_OVERRIDE_DEFAULT_CATALOG);
	}
	
	protected boolean userWantsToOverrideDefaultSchema() {
		return this.getBooleanProperty(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA);
	}
	
	protected String getUserOverrideDefaultSchema() {
		return (String) this.getProperty(USER_OVERRIDE_DEFAULT_SCHEMA);
	}
	
	protected boolean discoverAnnotatedClasses() {
		return this.getBooleanProperty(DISCOVER_ANNOTATED_CLASSES);
	}
	
	
	// ********** enabled **********
	
	@Override
	public boolean isPropertyEnabled(String propertyName) {
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
		if (propertyName.equals(FACET_ID)) {
			return JpaFacet.ID;
		}
		if (propertyName.equals(RUNTIME)) {
			return null;
		}
		if (propertyName.equals(PLATFORM)) {
			return getDefaultPlatform();
		}
		if (propertyName.equals(LIBRARY_PROVIDER_DELEGATE)) {
			return getDefaultLibraryProvider();
		}
		if (propertyName.equals(CONNECTION)) {
			return getDefaultConnection();
		}
		if (propertyName.equals(CONNECTION_ACTIVE)) {
			return Boolean.valueOf(this.connectionIsActive());
		}
		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG)) {
			return getDefaultUserWantsToOverrideDefaultCatalog();
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_CATALOG)) {
			return getDefaultCatalogIdentifier();
		}
		if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA)) {
			return getDefaultUserWantsToOverrideDefaultSchema();
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return getDefaultSchemaIdentifier();
		}
		if (propertyName.equals(DISCOVER_ANNOTATED_CLASSES)) {
			return getDefaultDiscoverAnnotatedClasses();
		}
		if (propertyName.equals(LIST_ANNOTATED_CLASSES)) {
			return getDefaultListAnnotatedClasses();
		}
		
		return super.getDefaultProperty(propertyName);
	}
	
	protected abstract JpaPlatformDescription getDefaultPlatform();
	
	protected LibraryInstallDelegate getDefaultLibraryProvider() {
		// delegate itself changes only when facet version changes
		if (this.defaultLibraryProvider == null) {
			this.defaultLibraryProvider = buildDefaultLibraryProvider();
		}
		else if (! this.defaultLibraryProvider.getProjectFacetVersion().equals(getProjectFacetVersion())) {
			this.defaultLibraryProvider.dispose();
			this.defaultLibraryProvider = buildDefaultLibraryProvider();
		}
		return defaultLibraryProvider;
	}
	
	protected  LibraryInstallDelegate buildDefaultLibraryProvider() {
		IFacetedProjectWorkingCopy fpjwc = this.getFacetedProjectWorkingCopy();
		if (fpjwc == null) {
			return null;
		}
		IProjectFacetVersion pfv = this.getProjectFacetVersion();
		if (pfv == null) {
			return null;
		}
		LibraryInstallDelegate lp = new LibraryInstallDelegate(fpjwc, pfv);
		lp.addListener(buildLibraryProviderListener());
		return lp;
	}
	
	protected IPropertyChangeListener buildLibraryProviderListener() {
		return new IPropertyChangeListener() {
				public void propertyChanged(String property, Object oldValue, Object newValue ) {
					if (LibraryInstallDelegate.PROP_AVAILABLE_PROVIDERS.equals(property)) {
						adjustLibraryProviders();
					}
					JpaFacetDataModelProvider.this.getDataModel().notifyPropertyChange(
							LIBRARY_PROVIDER_DELEGATE, IDataModel.VALUE_CHG);
				}
			};
	}
	
	protected abstract String getDefaultConnection();
	
	protected abstract Boolean getDefaultUserWantsToOverrideDefaultCatalog();
	
	protected abstract String getDefaultCatalogIdentifier();
	
	protected abstract Boolean getDefaultUserWantsToOverrideDefaultSchema();
	
	protected abstract String getDefaultSchemaIdentifier();
	
	protected abstract Boolean getDefaultDiscoverAnnotatedClasses();
	
	protected Boolean getDefaultListAnnotatedClasses() {
		return Boolean.valueOf( ! this.discoverAnnotatedClasses());
	}
	
	protected boolean runtimeSupportsEjb30() {
		IRuntime runtime = this.getRuntime();
		return (runtime != null) && runtime.supports(this.getEJB30());
	}
	
	protected IProjectFacetVersion getEJB30() {
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
			adjustLibraryProviders();
			this.model.notifyPropertyChange(PLATFORM, IDataModel.DEFAULT_CHG);
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
		else if (propertyName.equals(PLATFORM)) {
			adjustLibraryProviders();
		}
		else if (propertyName.equals(CONNECTION)) {
			this.setBooleanProperty(CONNECTION_ACTIVE, this.connectionIsActive());
			
			// JpaFacetWizardPage sets the connection when the user adds a new connection
			// implying that there is a new set of valid connections to choose from
			this.model.notifyPropertyChange(CONNECTION, IDataModel.VALID_VALUES_CHG);
			
			// catalog
			if ((propertyValue != null) && this.databaseSupportsCatalogs()) {  // connection set to something that supports catalogs
				this.setProperty(USER_OVERRIDE_DEFAULT_CATALOG, this.getDefaultCatalogIdentifier());
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
				this.setProperty(USER_OVERRIDE_DEFAULT_SCHEMA, this.getDefaultSchemaIdentifier());
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
		else if (propertyName.equals(USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG)) {
			this.model.notifyPropertyChange(USER_OVERRIDE_DEFAULT_CATALOG, IDataModel.ENABLE_CHG);
			if (this.propertyValueIsFalse(propertyValue)) {
				this.setProperty(USER_OVERRIDE_DEFAULT_CATALOG, null);
			}
		}
		else if (propertyName.equals(USER_OVERRIDE_DEFAULT_CATALOG)) {
			this.setProperty(USER_OVERRIDE_DEFAULT_SCHEMA, this.getDefaultSchemaIdentifier());
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
	
	protected boolean propertyValueIsFalse(Object propertyValue) {
		return ! this.propertyValueIsTrue(propertyValue);
	}
	
	protected boolean propertyValueIsTrue(Object propertyValue) {
		return ((Boolean) propertyValue).booleanValue();
	}
	
	protected void adjustLibraryProviders() {
		LibraryInstallDelegate lid = this.getLibraryInstallDelegate();
		if (lid != null) {
			List<JpaLibraryProviderInstallOperationConfig> jpaConfigs 
					= new ArrayList<JpaLibraryProviderInstallOperationConfig>();
			// add the currently selected one first
			JpaLibraryProviderInstallOperationConfig currentJpaConfig = null;
			LibraryProviderOperationConfig config = lid.getLibraryProviderOperationConfig();
			if (config instanceof JpaLibraryProviderInstallOperationConfig) {
				currentJpaConfig = (JpaLibraryProviderInstallOperationConfig) config;
				jpaConfigs.add(currentJpaConfig);
			}
			for (ILibraryProvider lp : lid.getLibraryProviders()) {
				config = lid.getLibraryProviderOperationConfig(lp);
				if (config instanceof JpaLibraryProviderInstallOperationConfig
						&& ! config.equals(currentJpaConfig)) {
					jpaConfigs.add((JpaLibraryProviderInstallOperationConfig) config);
				}
			}
			for (JpaLibraryProviderInstallOperationConfig jpaConfig : jpaConfigs) {
				jpaConfig.setJpaPlatform(getPlatform());
			}
		}
	}
	
	
	// ********** property descriptors **********
	
	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(PLATFORM)) {
			return this.buildValidPlatformDescriptors();
		}
		if (propertyName.equals(CONNECTION)) {
			return this.buildValidConnectionDescriptors();
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_CATALOG)) {
			return this.buildValidCatalogDescriptors();
		}
		if (propertyName.equals(USER_OVERRIDE_DEFAULT_SCHEMA)) {
			return this.buildValidSchemaDescriptors();
		}
		
		return super.getValidPropertyDescriptors(propertyName);
	}
	
	protected DataModelPropertyDescriptor[] buildValidPlatformDescriptors() {
		Iterable<JpaPlatformDescription> validPlatformDescriptions = buildValidPlatformDescriptions();
		Iterable<DataModelPropertyDescriptor> validPlatformDescriptors =
				new TransformationIterable<JpaPlatformDescription, DataModelPropertyDescriptor>(validPlatformDescriptions) {
					@Override
					protected DataModelPropertyDescriptor transform(JpaPlatformDescription desc) {
						return buildPlatformDescriptor(desc);
					}
				};
		return ArrayTools.sort(ArrayTools.array(validPlatformDescriptors, EMPTY_DMPD_ARRAY), DESCRIPTOR_COMPARATOR);
	}
	
	protected Iterable<JpaPlatformDescription> buildValidPlatformDescriptions() {
		return new FilteringIterable<JpaPlatformDescription>(
					JptCorePlugin.getJpaPlatformManager().getJpaPlatforms()) {
				@Override
				protected boolean accept(JpaPlatformDescription o) {
					return o.supportsJpaFacetVersion(getProjectFacetVersion());
				}
			};
	}
	
	protected static final Comparator<DataModelPropertyDescriptor> DESCRIPTOR_COMPARATOR =
			new Comparator<DataModelPropertyDescriptor>() {
				public int compare(DataModelPropertyDescriptor dmpd1, DataModelPropertyDescriptor dmpd2) {
					return dmpd1.getPropertyDescription().compareTo(dmpd2.getPropertyDescription());
				}
			};
	
	protected DataModelPropertyDescriptor[] buildValidConnectionDescriptors() {
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
	protected List<String> buildValidConnectionNames() {
		List<String> connectionNames = CollectionTools.sort(CollectionTools.list(this.getConnectionProfileNames()));
		connectionNames.add(0, null);
		return connectionNames;
	}
	
	protected DataModelPropertyDescriptor[] buildValidCatalogDescriptors() {
		Database db = this.getDatabase();
		return (db == null) ? EMPTY_DMPD_ARRAY : this.buildDescriptors(this.buildValidCatalogIdentifiers(db));
	}
	
	/**
	 * pre-condition: 'db' is not null
	 */
	protected List<String> buildValidCatalogIdentifiers(Database db) {
		// use catalog *identifiers* since the string ends up being the "default" for various text entries
		return this.buildValidStrings(db.getSortedCatalogIdentifiers(), this.getDefaultCatalogIdentifier());
	}
	
	protected DataModelPropertyDescriptor[] buildValidSchemaDescriptors() {
		Database db = this.getDatabase();
		return (db == null) ? EMPTY_DMPD_ARRAY : this.buildDescriptors(this.buildValidSchemaIdentifiers());
	}
	
	protected List<String> buildValidSchemaIdentifiers() {
		return this.buildValidStrings(this.getSchemaIdentifiers(), this.getDefaultSchemaIdentifier());
	}
	
	protected Iterable<String> getSchemaIdentifiers() {
		SchemaContainer sc = this.getSchemaContainer();
		// use schema *identifiers* since the string ends up being the "default" for various text entries
		return (sc != null) ? sc.getSortedSchemaIdentifiers() : EmptyIterable.<String>instance();
	}
	
	/**
	 * put an entry for the default at the top of the list
	 */
	protected List<String> buildValidStrings(Iterable<String> strings, String defaultString) {
		List<String> validStrings = CollectionTools.list(strings);
		if ((defaultString != null) && ! validStrings.contains(defaultString)) {
			validStrings.add(0, defaultString);
		}
		return validStrings;
	}
	
	protected DataModelPropertyDescriptor[] buildDescriptors(List<String> strings) {
		DataModelPropertyDescriptor[] descriptors = new DataModelPropertyDescriptor[strings.size()];
		for (int i = 0; i < descriptors.length; i++) {
			descriptors[i] = new DataModelPropertyDescriptor(strings.get(i));
		}
		return descriptors;
	}
	
	protected static final DataModelPropertyDescriptor[] EMPTY_DMPD_ARRAY = new DataModelPropertyDescriptor[0];
	
	/**
	 * platform and connection have 'description's (in addition to 'value's)
	 */
	@Override
	public DataModelPropertyDescriptor getPropertyDescriptor(String propertyName) {
		if (propertyName.equals(PLATFORM)) {
			return buildPlatformDescriptor(this.getPlatform());
		}
		if (propertyName.equals(CONNECTION)) {
			return buildConnectionDescriptor(this.getConnectionName());
		}
		return super.getPropertyDescriptor(propertyName);
	}
	
	protected DataModelPropertyDescriptor buildPlatformDescriptor(JpaPlatformDescription platform) {
		return new DataModelPropertyDescriptor(platform, platform.getLabel());
	}
	
	protected String getPlatformLabel(String platformId) {
		return JptCorePlugin.getJpaPlatformManager().getJpaPlatform(platformId).getLabel();
	}
	
	protected DataModelPropertyDescriptor buildConnectionDescriptor(String connectionName) {
		String description = (connectionName == null) ? JptCoreMessages.NONE : null;
		return new DataModelPropertyDescriptor(connectionName, description);
	}
	
	
	// ********** database **********
	
	protected SchemaContainer getSchemaContainer() {
		return this.databaseSupportsCatalogs() ? this.getCatalog() : this.getDatabase();
	}
	
	protected Catalog getCatalog() {
		String name = this.getUserOverrideDefaultCatalog();
		return (name == null) ? null : this.getCatalog(name);
	}
	
	/**
	 * pre-condition: 'name' is not null
	 */
	protected Catalog getCatalog(String name) {
		Database db = this.getDatabase();
		return (db == null) ? null : db.getCatalogForIdentifier(name);
	}
	
	protected boolean databaseSupportsCatalogs() {
		Database db = this.getDatabase();
		return (db != null) && db.supportsCatalogs();
	}
	
	protected Database getDatabase() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp == null) ? null : cp.getDatabase();
	}
	
	protected boolean connectionIsActive() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp != null) && cp.isActive();
	}
	
	protected ConnectionProfile getConnectionProfile() {
		String name = this.getConnectionName();
		return (name == null) ? null : this.getConnectionProfile(name);
	}
	
	/**
	 * pre-condition: 'name' is not null
	 */
	protected ConnectionProfile getConnectionProfile(String name) {
		if (this.cachedConnectionProfileIsStale(name)) {
			this.connectionProfile = this.buildConnectionProfile(name);
		}
		return this.connectionProfile;
	}
	
	protected boolean cachedConnectionProfileIsStale(String name) {
		return (this.connectionProfile == null) || ! this.connectionProfile.getName().equals(name);
	}
	
	protected ConnectionProfile buildConnectionProfile(String name) {
		return this.getConnectionProfileFactory().buildConnectionProfile(name, DatabaseIdentifierAdapter.Default.instance());
	}
	
	protected Iterable<String> getConnectionProfileNames() {
		return this.getConnectionProfileFactory().getConnectionProfileNames();
	}
	
	protected ConnectionProfileFactory getConnectionProfileFactory() {
		// we don't have a JPA project yet, so go to the db plug-in directly to get the factory
		return JptDbPlugin.getConnectionProfileFactory();
	}
	
	
	// ********** validation **********
	
	@Override
	public IStatus validate(String propertyName) {
		if (propertyName.equals(PLATFORM)) {
			return this.validatePlatform();
		}
		if (propertyName.equals(LIBRARY_PROVIDER_DELEGATE)) {
		    return this.getLibraryInstallDelegate().validate();
		}
		if (propertyName.equals(CONNECTION)) {
			return this.validateConnection();
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
	
	protected IStatus validatePlatform() {
		return (this.getPlatform() == null) ? PLATFORM_NOT_SPECIFIED_STATUS : OK_STATUS;
	}
	
	protected IStatus validateConnection() {
		String connectionName = this.getConnectionName();
		return (connectionName == null) ? OK_STATUS : this.validateNonNullConnection(connectionName);
	}
	
	protected IStatus validateNonNullConnection(String connectionName) {
		ConnectionProfile cp = this.getConnectionProfile(connectionName);
		if (cp == null) {
			return buildErrorStatus(NLS.bind(JptCoreMessages.VALIDATE_CONNECTION_INVALID, connectionName));
		}
		if ( ! cp.isActive()) {
			return CONNECTION_NOT_CONNECTED_STATUS;
		}
		return OK_STATUS;
	}
	
	protected IStatus validateUserOverrideDefaultCatalog() {
		if (this.userWantsToOverrideDefaultCatalog()) {
			if (this.getUserOverrideDefaultCatalog() == null) {
				return USER_OVERRIDE_DEFAULT_CATALOG_NOT_SPECIFIED_STATUS;
			}
		}
		return OK_STATUS;
	}
	
	protected IStatus validateUserOverrideDefaultSchema() {
		if (this.userWantsToOverrideDefaultSchema()) {
			if (this.getUserOverrideDefaultSchema() == null) {
				return USER_OVERRIDE_DEFAULT_SCHEMA_NOT_SPECIFIED_STATUS;
			}
		}
		return OK_STATUS;
	}
	
	protected IStatus validatePersistentClassManagement() {
		// TODO warning if "discovery" is used, but no runtime specified ??
		// boolean discoverAnnotatedClasses = this.discoverAnnotatedClasses();
		return OK_STATUS;
	}
	
	
	// ********** static methods **********
	
	protected static IStatus buildInfoStatus(String message) {
		return buildStatus(IStatus.INFO, message);
	}
	
//	private static IStatus buildWarningStatus(String message) {
//		return buildStatus(IStatus.WARNING, message);
//	}
	
	protected static IStatus buildErrorStatus(String message) {
		return buildStatus(IStatus.ERROR, message);
	}
	
	protected static IStatus buildStatus(int severity, String message) {
		return new Status(severity, JptCorePlugin.PLUGIN_ID, message);
	}
}
