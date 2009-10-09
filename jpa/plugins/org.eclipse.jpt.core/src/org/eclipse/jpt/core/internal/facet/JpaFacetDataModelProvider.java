/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
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
import org.eclipse.jpt.core.internal.JpaPlatformRegistry;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.DatabaseFinder;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
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
		propertyNames.add(PLATFORM_ID);
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
	
	protected String getPlatformId() {
		return (String) this.getProperty(PLATFORM_ID);
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
			return JptCorePlugin.FACET_ID;
		}
		if (propertyName.equals(RUNTIME)) {
			return null;
		}
		if (propertyName.equals(PLATFORM_ID)) {
			return getDefaultPlatformId();
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
	
	protected abstract String getDefaultPlatformId();
	
	protected LibraryInstallDelegate getDefaultLibraryProvider() {
		// delegate itself changes, not the instance of delegate
		if (this.defaultLibraryProvider == null) {
			this.defaultLibraryProvider = this.buildDefaultLibraryProvider();
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
		LibraryInstallDelegate lp = new LibraryInstallDelegate(fpjwc, pfv, this.buildEnablementVariables());
		lp.addListener(this.buildLibraryProviderListener());
		return lp;
	}
	
	protected Map<String, Object> buildEnablementVariables() {
		Map<String, Object> enablementVariables = new HashMap<String, Object>();
		enablementVariables.put(JpaLibraryProviderConstants.EXPR_VAR_JPA_PLATFORM, this.getPlatformId());
		return enablementVariables;
	}
	
	protected IPropertyChangeListener buildLibraryProviderListener() {
		return new IPropertyChangeListener() {
				public void propertyChanged(String property, Object oldValue, Object newValue ) {
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
	
	
	// ********** property descriptors **********
	
	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(PLATFORM_ID)) {
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
		Iterable<String> validPlatformIds = buildValidPlatformIds();
		Iterable<DataModelPropertyDescriptor> validPlatformDescriptors =
				new TransformationIterable<String, DataModelPropertyDescriptor>(validPlatformIds) {
					@Override
					protected DataModelPropertyDescriptor transform(String platformId) {
						return JpaFacetDataModelProvider.this.buildPlatformIdDescriptor(platformId);
					}
				};
		return ArrayTools.sort(ArrayTools.array(validPlatformDescriptors, EMPTY_DMPD_ARRAY), DESCRIPTOR_COMPARATOR);
	}
	
	protected Iterable<String> buildValidPlatformIds() {
		String jpaFacetVersion = this.getProjectFacetVersion().getVersionString();
		return JpaPlatformRegistry.instance().getJpaPlatformIdsForJpaFacetVersion(jpaFacetVersion);
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
		List<String> connectionNames = CollectionTools.sort(CollectionTools.list(this.connectionProfileNames()));
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
		return this.buildValidStrings(db.sortedCatalogIdentifiers(), this.getDefaultCatalogIdentifier());
	}
	
	protected DataModelPropertyDescriptor[] buildValidSchemaDescriptors() {
		Database db = this.getDatabase();
		return (db == null) ? EMPTY_DMPD_ARRAY : this.buildDescriptors(this.buildValidSchemaIdentifiers());
	}
	
	protected List<String> buildValidSchemaIdentifiers() {
		return this.buildValidStrings(this.schemaIdentifiers(), this.getDefaultSchemaIdentifier());
	}
	
	protected Iterator<String> schemaIdentifiers() {
		SchemaContainer sc = this.getSchemaContainer();
		// use schema *identifiers* since the string ends up being the "default" for various text entries
		return (sc != null) ? sc.sortedSchemaIdentifiers() : EmptyIterator.<String>instance();
	}
	
	/**
	 * put an entry for the default at the top of the list
	 */
	protected List<String> buildValidStrings(Iterator<String> stream, String defaultString) {
		List<String> strings = CollectionTools.list(stream);
		if ((defaultString != null) && ! strings.contains(defaultString)) {
			strings.add(0, defaultString);
		}
		return strings;
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
		if (propertyName.equals(PLATFORM_ID)) {
			return buildPlatformIdDescriptor(this.getPlatformId());
		}
		if (propertyName.equals(CONNECTION)) {
			return buildConnectionDescriptor(this.getConnectionName());
		}
		return super.getPropertyDescriptor(propertyName);
	}
	
	protected DataModelPropertyDescriptor buildPlatformIdDescriptor(String platformId) {
		return new DataModelPropertyDescriptor(platformId, this.getPlatformLabel(platformId));
	}
	
	protected String getPlatformLabel(String platformId) {
		return JpaPlatformRegistry.instance().getJpaPlatformLabel(platformId);
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
		return this.getConnectionProfileFactory().buildConnectionProfile(name, DatabaseFinder.Default.instance());
	}
	
	protected Iterator<String> connectionProfileNames() {
		return this.getConnectionProfileFactory().connectionProfileNames();
	}
	
	protected ConnectionProfileFactory getConnectionProfileFactory() {
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
	
	protected IStatus validatePlatformId() {
		return (this.getPlatformId() == null) ? PLATFORM_NOT_SPECIFIED_STATUS : OK_STATUS;
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
