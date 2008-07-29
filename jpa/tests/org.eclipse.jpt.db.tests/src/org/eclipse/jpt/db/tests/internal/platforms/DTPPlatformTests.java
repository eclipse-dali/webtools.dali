/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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
import org.eclipse.jpt.db.DatabaseFinder;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.db.Sequence;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.db.ForeignKey.ColumnPair;
import org.eclipse.jpt.db.tests.internal.JptDbTestsPlugin;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.IndentingPrintWriter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ResultSetIterator;

import junit.framework.TestCase;

/**
 * Base class for testing DTP wrappers on various databases.
 */
@SuppressWarnings("nls")
public abstract class DTPPlatformTests extends TestCase {

	/**
	 * The platform properties are loaded from a Java properties file in the
	 * 'org.eclipse.jpt.db.tests/config' directory. Each database platform has
	 * its own properties file (e.g. 'derby101.properties').
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

	private static final String DB_DATABASE_PROPERTY = "database";
		private static final String DB_DATABASE_DEFAULT = "testdb";



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
		this.connectionProfile = this.getConnectionProfileFactory().buildConnectionProfile(this.getProfileName(), DatabaseFinder.Default.instance());
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
		p.setProperty(IJDBCDriverDefinitionConstants.DATABASE_NAME_PROP_ID, this.getDatabaseName());
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

	/**
	 * The database name is optional and can be set in the properties file.
	 */
	protected String getDatabaseName() {
		return this.platformProperties.getProperty(DB_DATABASE_PROPERTY, DB_DATABASE_DEFAULT);
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
	 
	private Properties buildDTPConnectionProfileProperties() {
		Properties p = new Properties();
		p.setProperty(IJDBCDriverDefinitionConstants.DATABASE_NAME_PROP_ID, this.getDatabaseName());
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
		String actual = database.getVendor();
		String expected = this.getDatabaseVendor();
		assertEquals(expected, actual);
	}

	private void verifyDatabaseContent() {
		Database database = this.connectionProfile.getDatabase();
		assertTrue(database.schemataSize() > 0);

		Schema schema = database.getDefaultSchema();
		if (schema != null) {
			if (schema.tablesSize() > 0) {
				Table table = schema.tables().next();
				assertTrue(table.columnsSize() >= 0);
				assertTrue(table.primaryKeyColumnsSize() >= 0);
				assertTrue(table.foreignKeysSize() >= 0);
			}
		}
	}

	public void testOffline() {
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

	public void testConnectionListenerOffline() {
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
		Catalog catalog = this.connectionProfile.getDatabase().getCatalogNamed(dtpCatalog.getName());
		((ICatalogObject) dtpCatalog).refresh();
		assertSame(catalog, listener.changedCatalog);

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testConnectionListenerSchema() {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema = null;
		Schema schema = null;
		if (this.connectionProfile.getDatabase().supportsCatalogs()) {
			org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog = this.getFirstDTPCatalog();
			dtpSchema = (org.eclipse.datatools.modelbase.sql.schema.Schema) dtpCatalog.getSchemas().get(0);
			schema = this.connectionProfile.getDatabase().getCatalogNamed(dtpCatalog.getName()).getSchemaNamed(dtpSchema.getName());
		} else {
			dtpSchema = (org.eclipse.datatools.modelbase.sql.schema.Schema) this.getDTPDatabase().getSchemas().get(0);
			schema = this.connectionProfile.getDatabase().getSchemaNamed(dtpSchema.getName());
		}
		((ICatalogObject) dtpSchema).refresh();
		assertSame(schema, listener.changedSchema);

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}


	// ********** convenience methods **********

	protected ConnectionProfileFactory getConnectionProfileFactory() {
		return JptDbPlugin.instance().getConnectionProfileFactory();
	}

	protected ConnectionProfile getConnectionProfile() {
		return this.connectionProfile;
	}

	protected Database getDatabase() {
		return this.connectionProfile.getDatabase();
	}

	protected Catalog getCatalogNamed(String catalogName) {
		return this.connectionProfile.getDatabase().getCatalogNamed(catalogName);
	}

	protected Schema getSchemaNamed(String schemaName) {
		return this.connectionProfile.getDatabase().getSchemaNamed(schemaName);
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
		return ((Boolean) ClassTools.executeMethod(cp, "hasAnyListeners")).booleanValue();
	}

	protected boolean connectionProfileHasNoListeners() {
		return connectionProfileHasNoListeners(this.connectionProfile);
	}

	protected static boolean connectionProfileHasNoListeners(ConnectionProfile cp) {
		return ((Boolean) ClassTools.executeMethod(cp, "hasNoListeners")).booleanValue();
	}


	// ********** DTP model **********

	protected ProfileManager getDTPProfileManager() {
		return ProfileManager.getInstance();
	}

	protected IConnectionProfile getDTPConnectionProfile() {
		return getDTPConnectionProfile(this.connectionProfile);
	}

	protected static IConnectionProfile getDTPConnectionProfile(ConnectionProfile cp) {
		return (IConnectionProfile) ClassTools.fieldValue(cp, "dtpConnectionProfile");
	}

	protected IManagedConnection getDTPManagedConnection() {
		return (IManagedConnection) ClassTools.fieldValue(this.connectionProfile, "dtpManagedConnection");
	}

	protected org.eclipse.datatools.modelbase.sql.schema.Database getDTPDatabase() {
		return getDTPDatabase(this.connectionProfile.getDatabase());
	}

	protected static org.eclipse.datatools.modelbase.sql.schema.Database getDTPDatabase(Database database) {
		return (org.eclipse.datatools.modelbase.sql.schema.Database) ClassTools.fieldValue(database, "dtpDatabase");
	}

	protected org.eclipse.datatools.modelbase.sql.schema.Catalog getFirstDTPCatalog() {
		return (org.eclipse.datatools.modelbase.sql.schema.Catalog) this.getDTPDatabase().getCatalogs().get(0);
	}

	protected org.eclipse.datatools.modelbase.sql.schema.Catalog getDTPCatalogNamed(String name) {
		return getDTPCatalog(this.getDatabase().getCatalogNamed(name));
	}

	protected static org.eclipse.datatools.modelbase.sql.schema.Catalog getDTPCatalog(Catalog catalog) {
		return (org.eclipse.datatools.modelbase.sql.schema.Catalog) ClassTools.fieldValue(catalog, "dtpCatalog");
	}

	protected org.eclipse.datatools.modelbase.sql.schema.Schema getDTPSchemaNamed(String name) {
		return getDTPSchema(this.getDatabase().getSchemaNamed(name));
	}

	protected static org.eclipse.datatools.modelbase.sql.schema.Schema getDTPSchema(Schema schema) {
		return (org.eclipse.datatools.modelbase.sql.schema.Schema) ClassTools.fieldValue(schema, "dtpSchema");
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
			// ignore
		}
	}

	protected void executeUpdate(String sql) throws SQLException {
		Statement jdbcStatement = this.createJDBCStatement();
		jdbcStatement.executeUpdate(sql);
		jdbcStatement.close();
	}

	protected List<Object[]> execute(String sql) throws SQLException {
		Statement jdbcStatement = this.createJDBCStatement();
		jdbcStatement.execute(sql);
		ResultSet resultSet = jdbcStatement.getResultSet();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		for (Iterator<Object[]> stream = new ResultSetIterator<Object[]>(resultSet, new LocalResultSetIteratorAdapter(columnCount)); stream.hasNext(); ) {
			rows.add(stream.next());
		}
		jdbcStatement.close();
		return rows;
	}

	public class LocalResultSetIteratorAdapter implements ResultSetIterator.Adapter<Object[]> {
		private int columnCount;
		public LocalResultSetIteratorAdapter(int columnCount) {
			super();
			this.columnCount = columnCount;
		}
		public Object[] buildNext(ResultSet rs) throws SQLException {
			Object[] row = new Object[this.columnCount];
			for (int i = 0; i < row.length; i++) {
				row[i] = rs.getObject(i + 1);
			}
			return row;
		}
	}

	protected Statement createJDBCStatement() throws SQLException {
		return this.getJDBCConnection().createStatement();
	}

	protected Connection getJDBCConnection() {
		return ((ConnectionInfo) this.getDTPManagedConnection().getConnection().getRawConnection()).getSharedConnection();
	}


	// ********** dump database schema **********

	/**
	 * dump all the database metadata to the console
	 */
	protected void dumpDatabase() {
		IndentingPrintWriter pw = new IndentingPrintWriter(new OutputStreamWriter(System.out));
		// synchronize the console so everything is contiguous
		synchronized (System.out) {
			this.dumpDatabaseOn(pw);
		}
		pw.flush();
	}

	protected void dumpDatabaseOn(IndentingPrintWriter pw) {
		Database database = this.connectionProfile.getDatabase();
		pw.print("database: ");
		pw.println(database.getName());
		if (database.supportsCatalogs()) {
			for (Iterator<Catalog> stream = database.catalogs(); stream.hasNext(); ) {
				this.dumpCatalogOn(stream.next(), pw);
			}
		} else {
			this.dumpSchemaOnContainerOn(database, pw);
		}
	}

	protected void dumpCatalogOn(Catalog catalog, IndentingPrintWriter pw) {
		pw.print("catalog: ");
		pw.println(catalog.getName());
		pw.indent();
		this.dumpSchemaOnContainerOn(catalog, pw);
		pw.undent();
	}

	protected void dumpSchemaOnContainerOn(SchemaContainer schemaContainer, IndentingPrintWriter pw) {
		for (Iterator<Schema> stream = schemaContainer.schemata(); stream.hasNext(); ) {
			this.dumpSchemaOn(stream.next(), pw);
		}
	}

	protected void dumpSchemaOn(Schema schema, IndentingPrintWriter pw) {
		pw.print("schema: ");
		pw.println(schema.getName());
		pw.indent();
		for (Iterator<Table> stream = schema.tables(); stream.hasNext(); ) {
			this.dumpTableOn(stream.next(), pw);
		}
		for (Iterator<Sequence> stream = schema.sequences(); stream.hasNext(); ) {
			this.dumpSequenceOn(stream.next(), pw);
		}
		pw.undent();
	}

	protected void dumpTableOn(Table table, IndentingPrintWriter pw) {
		pw.print("table: ");
		pw.println(table.getName());
		pw.indent();
		for (Iterator<Column> stream = table.columns(); stream.hasNext(); ) {
			this.dumpColumnOn(stream.next(), pw);
		}
		for (Iterator<ForeignKey> stream = table.foreignKeys(); stream.hasNext(); ) {
			this.dumpForeignKeyOn(stream.next(), pw);
		}
		pw.undent();
	}

	protected void dumpColumnOn(Column column, IndentingPrintWriter pw) {
		pw.print("column: ");
		pw.print(column.getName());
		pw.print(" : ");
		pw.print(column.getDataTypeName());
		if (column.isPrimaryKeyColumn()) {
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
		for (Iterator<ColumnPair> stream = foreignKey.columnPairs(); stream.hasNext(); ) {
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


	// ********** connection profile listener **********

	protected class TestConnectionProfileListener implements ConnectionProfileListener {
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

	protected class TestConnectionListener implements ConnectionListener {
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
