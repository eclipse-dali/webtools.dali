/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal.platforms;

import java.util.Properties;
import org.eclipse.datatools.connectivity.drivers.jdbc.IJDBCDriverDefinitionConstants;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;

/**
 * MySQL
 * 
 * Notes:
 * - We can only get database objects from the database associated with our
 *     connection profile.
 * - We can reference objects across multiple databases, so they are sorta like
 *     schemas....
 * - Foreign keys must be defined as table-level constraints; they cannot be
 *     defined as part of the column clause.
 * - Case-sensitivity and -folding is whacked on MySQL....
 */
@SuppressWarnings("nls")
public class MySQLTests extends DTPPlatformTests {

	public MySQLTests( String name) {
		super(name);
	}

	@Override
	protected String getPlatformPropertiesFileName() {
		return "mysql.properties";
	}

	@Override
	protected String getDriverName() {
		return "MySQL JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionID() {
		return "DriverDefn.MySQL JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionType() {
		return "org.eclipse.datatools.enablement.mysql.4_1.driverTemplate";
	}

	@Override
	protected String getDatabaseVendor() {
		return "MySql";
	}

	@Override
	protected String getDatabaseVersion() {
		return "4.1";
	}

	@Override
	protected String getDriverClass() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	protected String getDefaultJDBCURL() {
		return "jdbc:mysql://localhost:3306";
	}

	@Override
	protected String getProfileName() {
		return "MySQL_4.1";
	}

	@Override
	protected String getProfileDescription() {
		return "MySQL 4.1 JDBC Profile [Test]";
	}

	@Override
	protected boolean supportsCatalogs() {
		return false;
	}

	@Override
	protected Properties buildDTPConnectionProfileProperties() {
		Properties p = super.buildDTPConnectionProfileProperties();
		p.setProperty(IJDBCDriverDefinitionConstants.DATABASE_NAME_PROP_ID, this.getDatabaseName());
		return p;
	}

	private String getDatabaseName() {
		return "dalitest";
	}

	@Override
	protected boolean executeOfflineTests() {
		return true;  // seems to work...
	}

	public void testDatabase() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		// DTP: MySQL has a single schema with the same name as the database
		Schema schema = this.getDatabase().getSchemaNamed(this.getDatabaseName());
		assertNotNull(schema);
		assertSame(this.getDatabase().getDefaultSchema(), schema);

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testTable() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropDatabase(this.getDatabaseName());
		this.executeUpdate("CREATE DATABASE " + this.getDatabaseName());
		this.getJDBCConnection().setCatalog(this.getDatabaseName());

		this.dropTable(this.getDatabaseName(), "foo_baz");
		this.dropTable(this.getDatabaseName(), "baz");
		this.dropTable(this.getDatabaseName(), "foo");
		this.dropTable(this.getDatabaseName(), "bar");

		this.executeUpdate(this.buildBarDDL());
		this.executeUpdate(this.buildFooDDL());
		this.executeUpdate(this.buildBazDDL());
		this.executeUpdate(this.buildFooBazDDL());
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getDefaultSchema();

		// foo
		Table fooTable = schema.getTableNamed("foo");
		assertEquals(3, fooTable.columnsSize());
		assertEquals(1, fooTable.primaryKeyColumnsSize());
		assertEquals(1, fooTable.foreignKeysSize());

		Column pkColumn = fooTable.getPrimaryKeyColumn();
		assertEquals("id", pkColumn.getName());
		Column idColumn = fooTable.getColumnNamed("id");
		assertSame(pkColumn, idColumn);
		assertEquals("INT", idColumn.getDataTypeName());
		assertSame(fooTable, idColumn.getTable());
		assertTrue(idColumn.isPartOfPrimaryKey());
		assertFalse(idColumn.isPartOfForeignKey());
		assertEquals("int", idColumn.getJavaTypeDeclaration());

		Column nameColumn = fooTable.getColumnNamed("name");
		assertEquals("VARCHAR", nameColumn.getDataTypeName());
		assertEquals("java.lang.String", nameColumn.getJavaTypeDeclaration());
		assertFalse(nameColumn.isPartOfPrimaryKey());

		Column barColumn = fooTable.getColumnNamed("bar_id");
		assertEquals("INT", barColumn.getDataTypeName());
		assertTrue(barColumn.isPartOfForeignKey());
		assertFalse(barColumn.isPartOfPrimaryKey());

		ForeignKey barFK = fooTable.foreignKeys().next();  // there should only be 1 foreign key
		assertEquals(1, barFK.columnPairsSize());
		assertEquals("bar", barFK.getAttributeName());
		assertNull(barFK.getJoinColumnAnnotationIdentifier("bar"));
		assertEquals("bar_id", barFK.getJoinColumnAnnotationIdentifier("primaryBar"));
		assertSame(fooTable, barFK.getBaseTable());

		assertFalse(fooTable.isPossibleJoinTable());
		assertSame(schema, fooTable.getSchema());

		// BAR
		Table barTable = schema.getTableNamed("bar");
		assertEquals(2, barTable.columnsSize());
		assertEquals(1, barTable.primaryKeyColumnsSize());
		assertEquals(0, barTable.foreignKeysSize());
		assertEquals("id", barTable.getPrimaryKeyColumn().getName());
		assertFalse(barTable.isPossibleJoinTable());
		assertEquals("BLOB", barTable.getColumnNamed("chunk").getDataTypeName());
		assertEquals("byte[]", barTable.getColumnNamed("chunk").getJavaTypeDeclaration());
		assertTrue(barTable.getColumnNamed("chunk").isLOB());
		assertSame(barTable, barFK.getReferencedTable());

		// FOO_BAZ
		Table foo_bazTable = schema.getTableNamed("foo_baz");
		assertEquals(2, foo_bazTable.columnsSize());
		assertEquals(0, foo_bazTable.primaryKeyColumnsSize());
		assertEquals(2, foo_bazTable.foreignKeysSize());
		assertTrue(foo_bazTable.isPossibleJoinTable());
		assertTrue(foo_bazTable.joinTableNameIsDefault());
		assertTrue(foo_bazTable.getColumnNamed("foo_id").isPartOfForeignKey());

		this.dropTable(this.getDatabaseName(), "foo_baz");
		this.dropTable(this.getDatabaseName(), "baz");
		this.dropTable(this.getDatabaseName(), "foo");
		this.dropTable(this.getDatabaseName(), "bar");

		this.dropDatabase(this.getDatabaseName());

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	private static final String CR = System.getProperty("line.separator");  //$NON-NLS-1$

	private String buildBarDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE bar (").append(CR);
		sb.append("    id INTEGER PRIMARY KEY,").append(CR);
		sb.append("    chunk BLOB").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE foo (").append(CR);
		sb.append("    id INTEGER PRIMARY KEY,").append(CR);
		sb.append("    name VARCHAR(20),").append(CR);
		sb.append("    bar_id INTEGER,").append(CR);
		sb.append("    FOREIGN KEY (bar_id) REFERENCES bar(id)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE baz (").append(CR);
		sb.append("    id INTEGER PRIMARY KEY,").append(CR);
		sb.append("    name VARCHAR(20)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE foo_baz (").append(CR);
		sb.append("    foo_id INT,").append(CR);
		sb.append("    baz_id INT,").append(CR);
		sb.append("    FOREIGN KEY (foo_id) REFERENCES foo(id),").append(CR);
		sb.append("    FOREIGN KEY (baz_id) REFERENCES baz(id)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	/**
	 * On Windows, table names get folded to lowercase by default;
	 * even if the name is delimited (apparently).
	 */
	public void testTableLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropDatabase(this.getDatabaseName());
		this.executeUpdate("CREATE DATABASE " + this.getDatabaseName());
		this.getJDBCConnection().setCatalog(this.getDatabaseName());

		this.dropTable(this.getDatabaseName(), "test1");
		this.dropTable(this.getDatabaseName(), "TEST2");
		this.dropTable(this.getDatabaseName(), "`TEST3`");

		this.executeUpdate("CREATE TABLE test1 (id INTEGER, name VARCHAR(20))");
		this.executeUpdate("CREATE TABLE TEST2 (id INTEGER, name VARCHAR(20))");
		this.executeUpdate("CREATE TABLE `TEST3` (id INTEGER, name VARCHAR(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getDefaultSchema();

		Table test1Table = schema.getTableForIdentifier("test1");
		assertNotNull(test1Table);

		// this probably only works on Windows
		Table test2Table = schema.getTableForIdentifier("test2");
		assertNotNull(test2Table);

		// this probably only works on Windows
		Table test3Table = schema.getTableForIdentifier("`test3`");
		assertNotNull(test3Table);

		this.dropTable(this.getDatabaseName(), "test1");
		this.dropTable(this.getDatabaseName(), "TEST2");
		this.dropTable(this.getDatabaseName(), "`TEST3`");

		this.dropDatabase(this.getDatabaseName());

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testColumnLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropDatabase(this.getDatabaseName());
		this.executeUpdate("CREATE DATABASE " + this.getDatabaseName());
		this.getJDBCConnection().setCatalog(this.getDatabaseName());

		this.dropTable(this.getDatabaseName(), "test");

		// lowercase
		this.executeUpdate("CREATE TABLE test (id INTEGER, name VARCHAR(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Table table = this.getDatabase().getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnNamed("id"));
		assertNotNull(table.getColumnNamed("name"));

		this.dropTable(this.getDatabaseName(), "test");

		// uppercase
		this.executeUpdate("CREATE TABLE test (ID INTEGER, NAME VARCHAR(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnNamed("ID"));
		assertNotNull(table.getColumnNamed("NAME"));

		this.dropTable(this.getDatabaseName(), "test");

		// mixed case
		this.executeUpdate("CREATE TABLE test (Id INTEGER, Name VARCHAR(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnNamed("Id"));
		assertNotNull(table.getColumnNamed("Name"));

		this.dropTable(this.getDatabaseName(), "test");

		// delimited
		this.executeUpdate("CREATE TABLE test (`Id` INTEGER, `Name` VARCHAR(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnForIdentifier("`Id`"));
		assertNotNull(table.getColumnForIdentifier("`Name`"));

		this.dropTable(this.getDatabaseName(), "test");

		this.dropDatabase(this.getDatabaseName());

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	private void dropTable(String dbName, String tableName) throws Exception {
		this.executeUpdate("DROP TABLE IF EXISTS " + dbName + '.' + tableName);
	}

	private void dropDatabase(String name) throws Exception {
		this.executeUpdate("DROP DATABASE IF EXISTS " + name);
	}

}
