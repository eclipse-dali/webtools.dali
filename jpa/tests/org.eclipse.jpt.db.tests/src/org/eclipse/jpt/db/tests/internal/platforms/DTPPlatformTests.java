/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal.platforms;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.datatools.connectivity.ConnectionProfileException;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IManagedConnection;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.datatools.connectivity.drivers.IDriverMgmtConstants;
import org.eclipse.datatools.connectivity.drivers.IPropertySet;
import org.eclipse.datatools.connectivity.drivers.PropertySetImpl;
import org.eclipse.datatools.connectivity.drivers.XMLFileManager;
import org.eclipse.datatools.connectivity.drivers.jdbc.IJDBCDriverDefinitionConstants;
import org.eclipse.datatools.connectivity.internal.ConnectivityPlugin;
import org.eclipse.datatools.connectivity.sqm.core.connection.ConnectionInfo;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.ConnectionProfileListener;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.DatabaseIdentifierAdapter;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.db.Sequence;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.db.ForeignKey.ColumnPair;
import org.eclipse.jpt.db.tests.internal.JptDbTestsPlugin;
import org.eclipse.jpt.utility.IndentingPrintWriter;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ResultSetIterator;

/**
 * Base class for testing DTP wrappers on various databases.
 */
@SuppressWarnings("nls")
public abstract class DTPPlatformTests extends TestCase {

	/**
	 * The platform properties are loaded from a Java properties file in the
	 * 'org.eclipse.jpt.db.tests/config' directory. Each database platform has
	 * its own properties file (e.g. 'derby.properties').
	 */
	private Properties platformProperties;

	/**
	 * This is the Dali connection profile wrapper.
	 */
	protected ConnectionProfile connectionProfile;


	// ********** constants **********

	private static final String PLATFORM_CONFIG_DIRECTORY = "config";

	private static final String DB_USER_ID_PROPERTY = "userID";
		private static final String DB_USER_ID_DEFAULT = "user";

	private static final String DB_PASSWORD_PROPERTY = "password";
		private static final String DB_PASSWORD_DEFAULT = "";

	private static final String DB_DRIVER_JARS_PROPERTY = "jars";
		// required - no default

	private static final String DB_URL_PROPERTY = "url";
		// required - no default



	// ********** constructor **********

	protected DTPPlatformTests(String name) {
		super(name);
	}


	// ********** set-up/tear-down **********

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.platformProperties = this.loadPlatformProperties();
		this.buildDTPDriverDefinitionFile();
		this.buildDTPConnectionProfile();
		this.connectionProfile = this.getConnectionProfileFactory().buildConnectionProfile(this.getProfileName(), DatabaseIdentifierAdapter.Default.instance());
	}

	@Override
	protected void tearDown() throws Exception {
		this.connectionProfile = null;
		this.platformProperties = null;

		super.tearDown();
	}

	// ***** platform properties file
	private Properties loadPlatformProperties() throws IOException {
		Properties p = new Properties();
		p.load(this.buildPlatformPropertiesFileURL().openStream());
		return p;
	}

	private URL buildPlatformPropertiesFileURL() {
		return Platform.getBundle(this.getTestPluginBundleID()).getEntry(this.getPlatformPropertiesFilePath());
	}

	private String getTestPluginBundleID() {
		return JptDbTestsPlugin.BUNDLE_ID;
	}

	private String getPlatformPropertiesFilePath() {
		return this.getPlatformPropertiesDirectoryName() + '/' + this.getPlatformPropertiesFileName();
	}

	private String getPlatformPropertiesDirectoryName() {
		return PLATFORM_CONFIG_DIRECTORY;
	}

	/**
	 * Each database platform has a separate properties file in the 'config'
	 * directory that must be customized by whomever is executing the tests.
	 */
	protected abstract String getPlatformPropertiesFileName();

	// ***** driver definition file
	private void buildDTPDriverDefinitionFile() throws CoreException {
		XMLFileManager.setStorageLocation(this.getDTPDriverDefinitionLocation());
		XMLFileManager.setFileName(this.getDTPDriverFileName());

		IPropertySet[] sets = XMLFileManager.loadPropertySets();
		for (IPropertySet set : sets) {
			if (set.getID().equals(this.getDriverDefinitionID())) {
				return;  // property sets live across tests
			}
		}

		XMLFileManager.saveNamedPropertySet(this.buildDTPDriverDefinitionPropertySets());

		// verify the file was created:
		File driverDefinitioneFile = this.getDTPDriverDefinitionLocation().append(this.getDTPDriverFileName()).toFile();
		assertTrue(driverDefinitioneFile.exists());
	}

	private IPath getDTPDriverDefinitionLocation() {
		return ConnectivityPlugin.getDefault().getStateLocation();
	}

	private String getDTPDriverFileName() {
		return IDriverMgmtConstants.DRIVER_FILE;
	}

	private IPropertySet[] buildDTPDriverDefinitionPropertySets() {
		IPropertySet[] propertySets = new IPropertySet[1];
		PropertySetImpl propertySet = new PropertySetImpl(this.getDriverName(), this.getDriverDefinitionID());
		propertySet.setProperties(this.getDriverDefinitionID(), this.buildDTPDriverDefinitionProperties());
		propertySets[0] = propertySet;
		return propertySets;
	}

	protected abstract String getDriverName();

	protected abstract String getDriverDefinitionID();

	private Properties buildDTPDriverDefinitionProperties() {
		Properties p = new Properties();
		p.setProperty(ConnectionProfile.DRIVER_DEFINITION_TYPE_PROP_ID, this.getDriverDefinitionType());
		p.setProperty(ConnectionProfile.DRIVER_JAR_LIST_PROP_ID, this.getJDBCDriverJarList());
		p.setProperty(IJDBCDriverDefinitionConstants.DATABASE_VENDOR_PROP_ID, this.getDatabaseVendor());
		p.setProperty(IJDBCDriverDefinitionConstants.DATABASE_VERSION_PROP_ID, this.getDatabaseVersion());
		p.setProperty(IJDBCDriverDefinitionConstants.DRIVER_CLASS_PROP_ID, this.getDriverClass());
		p.setProperty(IJDBCDriverDefinitionConstants.URL_PROP_ID, this.getJDBCURL());
		p.setProperty(IJDBCDriverDefinitionConstants.USERNAME_PROP_ID, this.getUserID());
		p.setProperty(IJDBCDriverDefinitionConstants.PASSWORD_PROP_ID, this.getPassword());
		return p;
	}

	protected abstract String getDriverDefinitionType();

	/**
	 * The JAR list is workspace-specific and is set in the properties file.
	 */
	private String getJDBCDriverJarList() {
		return this.getRequiredPlatformProperty(DB_DRIVER_JARS_PROPERTY);
	}

	protected abstract String getDatabaseVendor();

	protected abstract String getDatabaseVersion();

	protected abstract String getDriverClass();

	/**
	 * The database URL is workspace-specific and is set in the properties file
	 * for some databases.
	 */
	private String getJDBCURL() {
		return this.platformProperties.getProperty(DB_URL_PROPERTY, this.getDefaultJDBCURL());
	}

	protected String getDefaultJDBCURL() {
		return "";
	}

	/**
	 * The user ID is optional and can be set in the properties file.
	 */
	protected String getUserID() {
		return this.platformProperties.getProperty(DB_USER_ID_PROPERTY, DB_USER_ID_DEFAULT);
	}

	/**
	 * The password is optional and can be set in the properties file.
	 */
	private String getPassword() {
		return this.platformProperties.getProperty(DB_PASSWORD_PROPERTY, DB_PASSWORD_DEFAULT);
	}

	// ***** DTP connection profile
	private void buildDTPConnectionProfile() throws ConnectionProfileException {
		if (this.getDTPProfileManager().getProfileByName(this.getProfileName()) != null) {
			return;  // profiles live across tests
		}
		this.createProfile(this.getProfileName());
		assertNotNull(this.getDTPProfileManager().getProfileByName(this.getProfileName()));
	}

	protected void createProfile(String profileName) throws ConnectionProfileException {
		this.getDTPProfileManager().createProfile(
				profileName,
				this.getProfileDescription(),
				this.getProviderID(),
				this.buildDTPConnectionProfileProperties()
		);
	}

	protected abstract String getProfileName();

	protected abstract String getProfileDescription();

	protected String getProviderID() {
		return ConnectionProfile.CONNECTION_PROFILE_TYPE;
	}
	 
	protected Properties buildDTPConnectionProfileProperties() {
		Properties p = new Properties();
		p.setProperty(IJDBCDriverDefinitionConstants.USERNAME_PROP_ID, this.getUserID());
		p.setProperty(IJDBCDriverDefinitionConstants.PASSWORD_PROP_ID, this.getPassword());
		p.setProperty(ConnectionProfile.DRIVER_DEFINITION_PROP_ID, this.getDriverDefinitionID());

		p.setProperty(IJDBCDriverDefinitionConstants.DRIVER_CLASS_PROP_ID, this.getDriverClass());
		p.setProperty(IJDBCDriverDefinitionConstants.URL_PROP_ID, this.getJDBCURL());
		p.setProperty(IJDBCDriverDefinitionConstants.DATABASE_VENDOR_PROP_ID, this.getDatabaseVendor());
		p.setProperty(IJDBCDriverDefinitionConstants.DATABASE_VERSION_PROP_ID, this.getDatabaseVersion());

		p.setProperty(ConnectionProfile.DATABASE_SAVE_PWD_PROP_ID, this.passwordIsSaved());
		return p;
	}

	private String passwordIsSaved() {
		return "true";
	}


	// ********** tests **********

	public void testConnectionProfileListener() throws ConnectionProfileException {
		TestConnectionProfileListener listener = new TestConnectionProfileListener();
		this.getConnectionProfileFactory().addConnectionProfileListener(listener);

		String cpName1 = this.getProfileName() + "1";
		this.createProfile(cpName1);
		IConnectionProfile dtpCP = this.getDTPProfileManager().getProfileByName(cpName1);
		assertNotNull(dtpCP);

		assertEquals(cpName1, listener.addedName);
		listener.clear();

		String cpName2 = this.getProfileName() + "2";
		this.getDTPProfileManager().modifyProfile(dtpCP, cpName2, null);
		assertEquals(cpName1, listener.renamedOldName);
		assertEquals(cpName2, listener.renamedNewName);
		listener.clear();

		ConnectionProfile cp = this.getConnectionProfileFactory().buildConnectionProfile(cpName2);
		assertNotNull(cp);

		this.getDTPProfileManager().deleteProfile(dtpCP);
		assertEquals(cpName2, listener.removedName);
		listener.clear();

		cp = this.getConnectionProfileFactory().buildConnectionProfile(cpName2);
		assertNull(cp);

		this.getConnectionProfileFactory().removeConnectionProfileListener(listener);
	}

	public void testName() {
		assertEquals(this.getProfileName(), this.connectionProfile.getName());
	}

	public void testConnection() throws Exception {
		assertTrue(this.connectionProfile.isInactive());
		assertTrue(this.connectionProfile.isDisconnected());
		this.connectionProfile.connect();
		assertTrue(this.connectionProfile.isActive());
		assertTrue(this.connectionProfile.isConnected());

		this.verifyDatabaseVersionNumber();
		this.verifyDatabaseVendor();
		this.verifyDatabaseContent();

		this.connectionProfile.disconnect();
		assertTrue(this.connectionProfile.isInactive());
		assertTrue(this.connectionProfile.isDisconnected());
	}

	private void verifyDatabaseVersionNumber() {
		Database database = this.connectionProfile.getDatabase();
		assertNotNull(database);

		String actual = database.getVersion();
		String expected = this.getDatabaseVersion();
		String errorMessage = "expected: " + expected + " - actual: " + actual;
		// partial match is good enough
		assertTrue(errorMessage, actual.indexOf(expected) != -1);
	}

	private void verifyDatabaseVendor() {
		Database database = this.connectionProfile.getDatabase();
		String actual = database.getVendorName();
		String expected = this.getDatabaseVendor();
		assertEquals(expected, actual);
	}

	private void verifyDatabaseContent() {
		Database database = this.connectionProfile.getDatabase();
		assertTrue(database.getSchemataSize() >= 0);

		Schema schema = database.getDefaultSchema();
		if (schema != null) {
			if (schema.getTablesSize() > 0) {
				Table table = schema.getTables().iterator().next();
				assertTrue(table.getColumnsSize() >= 0);
				assertTrue(table.getPrimaryKeyColumnsSize() >= 0);
				assertTrue(table.getForeignKeysSize() >= 0);
			}
		}
	}

	protected abstract boolean executeOfflineTests();

	public final void testOffline() {
		if ( ! this.executeOfflineTests()) {
			return;
		}
		if ( ! this.connectionProfile.supportsWorkOfflineMode()) {
			return;
		}

		this.prepareForOfflineWork();

		IStatus status = this.connectionProfile.workOffline();
		assertTrue(status.isOK());
		assertTrue(this.connectionProfile.isActive());
		assertTrue(this.connectionProfile.isWorkingOffline());

		this.connectionProfile.disconnect();
		assertTrue(this.connectionProfile.isInactive());
		assertTrue(this.connectionProfile.isDisconnected());
	}

	protected void prepareForOfflineWork() {
		if ( ! this.connectionProfile.canWorkOffline()) {
			this.connectionProfile.connect();
			IStatus status = this.connectionProfile.saveWorkOfflineData();
			assertTrue(status.isOK());
			this.connectionProfile.disconnect();
			assertTrue(this.connectionProfile.canWorkOffline());
		}
	}

	public void testConnectionListenerConnect() {
		assertTrue(this.connectionProfileHasNoListeners());
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);
		assertTrue(this.connectionProfileHasAnyListeners());

		this.connectionProfile.connect();
		assertSame(this.connectionProfile, listener.openedProfile);
		listener.clear();

		this.connectionProfile.disconnect();
		assertSame(this.connectionProfile, listener.okToCloseProfile);
		assertSame(this.connectionProfile, listener.aboutToCloseProfile);
		assertSame(this.connectionProfile, listener.closedProfile);

		this.connectionProfile.removeConnectionListener(listener);
		assertTrue(this.connectionProfileHasNoListeners());
	}

	public final void testConnectionListenerOffline() {
		if ( ! this.executeOfflineTests()) {
			return;
		}
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.prepareForOfflineWork();
		listener.clear();

		this.connectionProfile.workOffline();
		assertSame(this.connectionProfile, listener.openedProfile);
		listener.clear();

		this.connectionProfile.disconnect();
		assertSame(this.connectionProfile, listener.okToCloseProfile);
		assertSame(this.connectionProfile, listener.aboutToCloseProfile);
		assertSame(this.connectionProfile, listener.closedProfile);
		listener.clear();

		this.connectionProfile.removeConnectionListener(listener);
	}

	public void testConnectionListenerDatabase() {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		((ICatalogObject) this.getDTPDatabase()).refresh();
		assertSame(this.connectionProfile.getDatabase(), listener.changedDatabase);

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testConnectionListenerCatalog() {
		this.connectionProfile.connect();
		if ( ! this.connectionProfile.getDatabase().supportsCatalogs()) {
			this.connectionProfile.disconnect();
			return;
		}
			
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		// take the first catalog
		org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog = this.getFirstDTPCatalog();
		Catalog catalog = this.getCatalogNamed(dtpCatalog.getName());
		((ICatalogObject) dtpCatalog).refresh();
		assertSame(catalog, listener.changedCatalog);

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testConnectionListenerSchema() {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs = this.getDTPCatalogs();
		org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog = null;
		org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema = null;
		Schema schema = null;
		if (this.connectionProfile.getDatabase().supportsCatalogs()) {
			dtpCatalog = dtpCatalogs.get(0);
			dtpSchema = (org.eclipse.datatools.modelbase.sql.schema.Schema) dtpCatalog.getSchemas().get(0);
			schema = this.getCatalogNamed(dtpCatalog.getName()).getSchemaNamed(dtpSchema.getName());
		} else {
			if (dtpCatalogs.isEmpty()) {
				dtpSchema = (org.eclipse.datatools.modelbase.sql.schema.Schema) this.getDTPDatabase().getSchemas().get(0);
				schema = this.connectionProfile.getDatabase().getSchemaNamed(dtpSchema.getName());
			} else {
				dtpCatalog = dtpCatalogs.get(0);  // should be the "virtual" catalog
				assertEquals("", dtpCatalog.getName());
				dtpSchema = (org.eclipse.datatools.modelbase.sql.schema.Schema) dtpCatalog.getSchemas().get(0);
				// the schemata are held directly by the database in this situation
				schema = this.getDatabase().getSchemaNamed(dtpSchema.getName());
			}
		}
		assertTrue(schema.getTablesSize() >= 0);  // force tables to be loaded
		((ICatalogObject) dtpSchema).refresh();
		assertSame(schema, listener.changedSchema);

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testSupportsCatalogs() {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		boolean supportsCatalogs = this.supportsCatalogs();
		assertEquals(supportsCatalogs, this.connectionProfile.getDatabase().supportsCatalogs());
		if (supportsCatalogs) {
			assertTrue(this.connectionProfile.getDatabase().getCatalogsSize() > 0);
			assertEquals(0, this.connectionProfile.getDatabase().getSchemataSize());
		} else {
			assertEquals(0, this.connectionProfile.getDatabase().getCatalogsSize());
			assertTrue(this.connectionProfile.getDatabase().getSchemataSize() > 0);
		}

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	protected abstract boolean supportsCatalogs();

//	public void testDEBUG() throws Exception {
//		this.connectionProfile.connect();
//		this.dumpJDBCCatalogs();
//		this.dumpJDBCSchemata();
//		this.dumpDatabaseContainers();
//		this.connectionProfile.disconnect();
//	}


	// ********** convenience methods **********

	protected ConnectionProfileFactory getConnectionProfileFactory() {
		return JptDbPlugin.getConnectionProfileFactory();
	}

	protected ConnectionProfile getConnectionProfile() {
		return this.connectionProfile;
	}

	protected Database getDatabase() {
		return this.connectionProfile.getDatabase();
	}

	protected Catalog getDefaultCatalog() {
		return this.getDatabase().getDefaultCatalog();
	}

	/**
	 * only valid on databases that do not support catalogs
	 */
	protected Schema getDefaultSchema() {
		return this.getDatabase().getDefaultSchema();
	}

	protected Catalog getCatalogNamed(String catalogName) {
		return this.connectionProfile.getDatabase().getCatalogNamed(catalogName);
	}

	protected String getRequiredPlatformProperty(String propertyKey) {
		String propertyValue = this.platformProperties.getProperty(propertyKey);
		if (StringTools.stringIsEmpty(propertyValue)) {
			throw new IllegalArgumentException("The database platform properties file '" + this.getPlatformPropertiesFilePath()
					+ "' is missing a value for the property '" + propertyKey + "'.");
		}
		return propertyValue;
	}

	protected boolean connectionProfileHasAnyListeners() {
		return connectionProfileHasAnyListeners(this.connectionProfile);
	}

	protected static boolean connectionProfileHasAnyListeners(ConnectionProfile cp) {
		return ((Boolean) ReflectionTools.executeMethod(cp, "hasAnyListeners")).booleanValue();
	}

	protected boolean connectionProfileHasNoListeners() {
		return connectionProfileHasNoListeners(this.connectionProfile);
	}

	protected static boolean connectionProfileHasNoListeners(ConnectionProfile cp) {
		return ((Boolean) ReflectionTools.executeMethod(cp, "hasNoListeners")).booleanValue();
	}


	// ********** DTP model **********

	protected ProfileManager getDTPProfileManager() {
		return ProfileManager.getInstance();
	}

	protected IConnectionProfile getDTPConnectionProfile() {
		return getDTPConnectionProfile(this.connectionProfile);
	}

	protected static IConnectionProfile getDTPConnectionProfile(ConnectionProfile cp) {
		return (IConnectionProfile) ReflectionTools.getFieldValue(cp, "dtpConnectionProfile");
	}

	protected IManagedConnection getDTPManagedConnection() {
		return (IManagedConnection) ReflectionTools.getFieldValue(this.connectionProfile, "dtpManagedConnection");
	}

	protected org.eclipse.datatools.modelbase.sql.schema.Database getDTPDatabase() {
		return getDTPDatabase(this.connectionProfile.getDatabase());
	}

	protected static org.eclipse.datatools.modelbase.sql.schema.Database getDTPDatabase(Database database) {
		return (org.eclipse.datatools.modelbase.sql.schema.Database) ReflectionTools.getFieldValue(database, "dtpDatabase");
	}

	@SuppressWarnings("unchecked")
	protected List<org.eclipse.datatools.modelbase.sql.schema.Catalog> getDTPCatalogs() {
		return this.getDTPDatabase().getCatalogs();
	}

	protected org.eclipse.datatools.modelbase.sql.schema.Catalog getFirstDTPCatalog() {
		return this.getDTPCatalogs().get(0);
	}

	protected org.eclipse.datatools.modelbase.sql.schema.Catalog getDTPCatalogNamed(String name) {
		return getDTPCatalog(this.getDatabase().getCatalogNamed(name));
	}

	protected static org.eclipse.datatools.modelbase.sql.schema.Catalog getDTPCatalog(Catalog catalog) {
		return (org.eclipse.datatools.modelbase.sql.schema.Catalog) ReflectionTools.getFieldValue(catalog, "dtpCatalog");
	}

	protected org.eclipse.datatools.modelbase.sql.schema.Schema getDTPSchemaNamed(String name) {
		return getDTPSchema(this.getDatabase().getSchemaNamed(name));
	}

	protected static org.eclipse.datatools.modelbase.sql.schema.Schema getDTPSchema(Schema schema) {
		return (org.eclipse.datatools.modelbase.sql.schema.Schema) ReflectionTools.getFieldValue(schema, "dtpSchema");
	}


	// ********** execute SQL **********

	/**
	 * ignore any errors (useful for dropping database objects that might
	 * not be there)
	 */
	protected void executeUpdateIgnoreErrors(String sql) {
		try {
			this.executeUpdate(sql);
		} catch (Exception ex) {
//			System.err.println("SQL: " + sql);
//			ex.printStackTrace();
		}
	}

	protected void executeUpdate(String sql) throws SQLException {
		Statement jdbcStatement = this.createJDBCStatement();
		try {
			jdbcStatement.executeUpdate(sql);
		} finally {
			jdbcStatement.close();
		}
	}

	protected void dump(String sql) throws SQLException {
		this.dump(sql, 20);
	}

	protected void dump(String sql, int columnWidth) throws SQLException {
		IndentingPrintWriter pw = new IndentingPrintWriter(new OutputStreamWriter(System.out));
		// synchronize the console so everything is contiguous
		synchronized (System.out) {
			this.dumpOn(sql, pw, columnWidth);
		}
		pw.flush();
	}

	protected void dumpOn(String sql, IndentingPrintWriter pw, int columnWidth) throws SQLException {
		pw.println(sql);
		for (ArrayList<Object> row : this.execute(sql)) {
			for (Object columnValue : row) {
				StringTools.padOrTruncateOn(String.valueOf(columnValue), columnWidth, pw);
				pw.print(' ');
			}
			pw.println();
		}
	}

	protected ArrayList<ArrayList<Object>> execute(String sql) throws SQLException {
		Statement jdbcStatement = this.createJDBCStatement();
		jdbcStatement.execute(sql);
		ArrayList<ArrayList<Object>> rows = this.buildRows(jdbcStatement.getResultSet());
		jdbcStatement.close();
		return rows;
	}

	protected ArrayList<ArrayList<Object>> buildRows(ResultSet resultSet) throws SQLException {
		ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
		for (Iterator<ArrayList<Object>> stream = this.buildArrayIterator(resultSet); stream.hasNext(); ) {
			rows.add(stream.next());
		}
		return rows;
	}

	protected Iterator<ArrayList<Object>> buildArrayIterator(ResultSet resultSet) throws SQLException {
		return new ResultSetIterator<ArrayList<Object>>(resultSet, new ListResultSetIteratorAdapter(resultSet.getMetaData().getColumnCount()));
	}

	public static class ListResultSetIteratorAdapter implements ResultSetIterator.Adapter<ArrayList<Object>> {
		private final int columnCount;
		public ListResultSetIteratorAdapter(int columnCount) {
			super();
			this.columnCount = columnCount;
		}
		public ArrayList<Object> buildNext(ResultSet rs) throws SQLException {
			ArrayList<Object> list = new ArrayList<Object>(this.columnCount);
			for (int i = 1; i <= this.columnCount; i++) {  // NB: ResultSet index/subscript is 1-based
				list.add(rs.getObject(i));
			}
			return list;
		}
	}

	protected Statement createJDBCStatement() throws SQLException {
		return this.getJDBCConnection().createStatement();
	}

	protected Connection getJDBCConnection() {
		return ((ConnectionInfo) this.getDTPManagedConnection().getConnection().getRawConnection()).getSharedConnection();
	}

	protected DatabaseMetaData getDatabaseMetaData() throws SQLException {
		return this.getJDBCConnection().getMetaData();
	}


	// ********** dump DTP metadata **********

	/**
	 * dump all the database metadata to the console
	 */
	protected void dumpDatabase() {
		this.dumpDatabase(true);
	}

	/**
	 * dump the database catalogs and schemata to the console
	 */
	protected void dumpDatabaseContainers() {
		this.dumpDatabase(false);
	}

	protected void dumpDatabase(boolean deep) {
		IndentingPrintWriter pw = new IndentingPrintWriter(new OutputStreamWriter(System.out));
		// synchronize the console so everything is contiguous
		synchronized (System.out) {
			this.dumpDatabaseOn(pw, deep);
		}
		pw.flush();
	}

	protected void dumpDatabaseOn(IndentingPrintWriter pw, boolean deep) {
		Database database = this.connectionProfile.getDatabase();
		pw.print("database: ");
		pw.println(database.getName());
		if (database.supportsCatalogs()) {
			for (Catalog catalog : database.getCatalogs()) {
				this.dumpCatalogOn(catalog, pw, deep);
			}
		} else {
			this.dumpSchemaContainerOn(database, pw, deep);
		}
	}

	protected void dumpCatalogOn(Catalog catalog, IndentingPrintWriter pw, boolean deep) {
		pw.print("catalog: ");
		pw.println(catalog.getName());
		pw.indent();
			this.dumpSchemaContainerOn(catalog, pw, deep);
		pw.undent();
	}

	protected void dumpSchemaContainerOn(SchemaContainer schemaContainer, IndentingPrintWriter pw, boolean deep) {
		for (Schema schema : schemaContainer.getSchemata()) {
			this.dumpSchemaOn(schema, pw, deep);
		}
	}

	protected void dumpSchema(Schema schema) {
		this.dumpSchema(schema, true);
	}

	protected void dumpSchema(Schema schema, boolean deep) {
		IndentingPrintWriter pw = new IndentingPrintWriter(new OutputStreamWriter(System.out));
		// synchronize the console so everything is contiguous
		synchronized (System.out) {
			this.dumpSchemaOn(schema, pw, deep);
		}
		pw.flush();
	}

	protected void dumpSchemaOn(Schema schema, IndentingPrintWriter pw, boolean deep) {
		pw.print("schema: ");
		pw.println(schema.getName());
		if (deep) {
			pw.indent();
				for (Table table : schema.getTables()) {
					this.dumpTableOn(table, pw);
				}
				for (Sequence sequence : schema.getSequences()) {
					this.dumpSequenceOn(sequence, pw);
				}
			pw.undent();
		}
	}

	protected void dumpTableOn(Table table, IndentingPrintWriter pw) {
		pw.print("table: ");
		pw.println(table.getName());
		pw.indent();
			for (Column column : table.getColumns()) {
				this.dumpColumnOn(column, pw);
			}
			for (ForeignKey foreignKey : table.getForeignKeys()) {
				this.dumpForeignKeyOn(foreignKey, pw);
			}
		pw.undent();
	}

	protected void dumpColumnOn(Column column, IndentingPrintWriter pw) {
		pw.print("column: ");
		pw.print(column.getName());
		pw.print(" : ");
		pw.print(column.getDataTypeName());
		if (column.isPartOfPrimaryKey()) {
			pw.print(" [primary key]");
		}
		pw.println();
	}

	protected void dumpForeignKeyOn(ForeignKey foreignKey, IndentingPrintWriter pw) {
		pw.print("foreign key: ");
		pw.print(foreignKey.getName());
		pw.print("=>");
		pw.print(foreignKey.getReferencedTable().getName());
		pw.print(" (");
		for (Iterator<ColumnPair> stream = foreignKey.getColumnPairs().iterator(); stream.hasNext(); ) {
			ColumnPair cp = stream.next();
			pw.print(cp.getBaseColumn().getName());
			pw.print("=>");
			pw.print(cp.getReferencedColumn().getName());
			if (stream.hasNext()) {
				pw.print(", ");
			}
		}
		pw.print(')');
		pw.println();
	}

	protected void dumpSequenceOn(Sequence sequence, IndentingPrintWriter pw) {
		pw.print("sequence: ");
		pw.println(sequence.getName());
	}


	// ********** dump JDBC metadata **********

	protected void dumpJDBCCatalogs() throws SQLException {
		IndentingPrintWriter pw = new IndentingPrintWriter(new OutputStreamWriter(System.out));
		// synchronize the console so everything is contiguous
		synchronized (System.out) {
			this.dumpJDBCCatalogsOn(pw);
		}
		pw.flush();
	}

	protected void dumpJDBCCatalogsOn(IndentingPrintWriter pw) throws SQLException {
		pw.println("JDBC catalogs: ");
		pw.indent();
			ArrayList<ArrayList<Object>> rows = this.buildRows(this.getDatabaseMetaData().getCatalogs());
			for (Iterator<ArrayList<Object>> stream = rows.iterator(); stream.hasNext(); ) {
				pw.println(stream.next().get(0));
			}
		pw.undent();
	}

	protected void dumpJDBCSchemata() throws SQLException {
		IndentingPrintWriter pw = new IndentingPrintWriter(new OutputStreamWriter(System.out));
		// synchronize the console so everything is contiguous
		synchronized (System.out) {
			this.dumpJDBCSchemataOn(pw);
		}
		pw.flush();
	}

	protected void dumpJDBCSchemataOn(IndentingPrintWriter pw) throws SQLException {
		pw.println("JDBC schemata: ");
		pw.indent();
			ArrayList<ArrayList<Object>> rows = this.buildRows(this.getDatabaseMetaData().getSchemas());
			for (ArrayList<Object> row : rows) {
				if (row.size() == 2) {  // catalogs were added in jdk 1.4
					Object catalog = row.get(1);
					pw.print(catalog);
					pw.print('.');
				}
				Object schema = row.get(0);
				pw.println(schema);
			}
		pw.undent();
	}


	// ********** connection profile listener **********

	protected static class TestConnectionProfileListener implements ConnectionProfileListener {
		public String addedName;
		public String removedName;
		public String renamedOldName;
		public String renamedNewName;

		public void connectionProfileAdded(String name) {
			this.addedName = name;
		}
		public void connectionProfileRemoved(String name) {
			this.removedName = name;
		}
		public void connectionProfileRenamed(String oldName, String newName) {
			this.renamedOldName = oldName;
			this.renamedNewName = newName;
		}
		public void clear() {
			this.addedName = null;
			this.removedName = null;
			this.renamedOldName = null;
			this.renamedNewName = null;
		}
	}


	// ********** connection listener **********

	protected static class TestConnectionListener implements ConnectionListener {
		public ConnectionProfile openedProfile;
		public ConnectionProfile modifiedProfile;
		public ConnectionProfile okToCloseProfile;
		public ConnectionProfile aboutToCloseProfile;
		public ConnectionProfile closedProfile;
		public Database changedDatabase;
		public Catalog changedCatalog;
		public Schema changedSchema;
		public Sequence changedSequence;
		public Table changedTable;
		public Column changedColumn;
		public ForeignKey changedForeignKey;

		public void opened(ConnectionProfile profile) {
			this.openedProfile = profile;
		}
		public void modified(ConnectionProfile profile) {
			this.modifiedProfile = profile;
		}
		public boolean okToClose(ConnectionProfile profile) {
			this.okToCloseProfile = profile;
			return true;
		}
		public void aboutToClose(ConnectionProfile profile) {
			this.aboutToCloseProfile = profile;
		}
		public void closed(ConnectionProfile profile) {
			this.closedProfile = profile;
		}
		public void databaseChanged(ConnectionProfile profile, Database database) {
			this.changedDatabase = database;
		}
		public void catalogChanged(ConnectionProfile profile, Catalog catalog) {
			this.changedCatalog = catalog;
		}
		public void schemaChanged(ConnectionProfile profile, Schema schema) {
			this.changedSchema = schema;
		}
		public void sequenceChanged(ConnectionProfile profile, Sequence sequence) {
			this.changedSequence = sequence;
		}
		public void tableChanged(ConnectionProfile profile, Table table) {
			this.changedTable = table;
		}
		public void columnChanged(ConnectionProfile profile, Column column) {
			this.changedColumn = column;
		}
		public void foreignKeyChanged(ConnectionProfile profile, ForeignKey foreignKey) {
			this.changedForeignKey = foreignKey;
		}
		public void clear() {
			this.openedProfile = null;
			this.modifiedProfile = null;
			this.okToCloseProfile = null;
			this.aboutToCloseProfile = null;
			this.closedProfile = null;
			this.changedDatabase = null;
			this.changedCatalog = null;
			this.changedSchema = null;
			this.changedSequence = null;
			this.changedTable = null;
			this.changedColumn = null;
			this.changedForeignKey = null;
		}
	}

}
