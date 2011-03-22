/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.tests.internal.platforms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.eclipse.datatools.connectivity.drivers.jdbc.IJDBCDriverDefinitionConstants;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;

/**
 * MySQL
 * 
 * Notes:<ul>
 * <li>We can only get database objects from the database associated with our
 * connection profile.
 * <li>We can reference objects across multiple databases, so they are sorta like
 * schemas....
 * <li>Foreign keys must be defined as table-level constraints; they cannot be
 * defined as part of the column clause.
 * <li>Case-sensitivity and -folding is whacked on MySQL....
 * </ul>
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
		return this.getUserID();  // by convention...
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
		assertSame(this.getDefaultSchema(), schema);

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testTable() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.getJDBCConnection().setCatalog(this.getDatabaseName());

		this.dropTable("foo_baz");
		this.dropTable("baz");
		this.dropTable("foo");
		this.dropTable("bar");

		this.executeUpdate(this.buildBarDDL());
		this.executeUpdate(this.buildFooDDL());
		this.executeUpdate(this.buildBazDDL());
		this.executeUpdate(this.buildFooBazDDL());
		// the MySQL database does NOT refresh - see bug 279721...
		((ICatalogObject) this.getDTPDatabase()).refresh();
		// ...refresh the single schema instead
		((ICatalogObject) getDTPSchema(this.getDefaultSchema())).refresh();

		Schema schema = this.getDefaultSchema();

		// foo
		Table fooTable = schema.getTableNamed("foo");
		assertEquals(3, fooTable.getColumnsSize());
		assertEquals(1, fooTable.getPrimaryKeyColumnsSize());
		// if the tables are created with MyISAM as the backing store
		// there will be no foreign keys
		assertEquals(1, fooTable.getForeignKeysSize());

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

		ForeignKey barFK = fooTable.getForeignKeys().iterator().next();  // there should only be 1 foreign key
		assertEquals(1, barFK.getColumnPairsSize());
		assertEquals("bar", barFK.getAttributeName());
		assertNull(barFK.getJoinColumnAnnotationIdentifier("bar"));
		assertEquals("bar_id", barFK.getJoinColumnAnnotationIdentifier("primaryBar"));
		assertSame(fooTable, barFK.getBaseTable());

		assertFalse(fooTable.isPossibleJoinTable());
		assertSame(schema, fooTable.getSchema());

		// BAR
		Table barTable = schema.getTableNamed("bar");
		assertEquals(2, barTable.getColumnsSize());
		assertEquals(1, barTable.getPrimaryKeyColumnsSize());
		assertEquals(0, barTable.getForeignKeysSize());
		assertEquals("id", barTable.getPrimaryKeyColumn().getName());
		assertFalse(barTable.isPossibleJoinTable());
		assertEquals("BLOB", barTable.getColumnNamed("chunk").getDataTypeName());
		assertEquals("byte[]", barTable.getColumnNamed("chunk").getJavaTypeDeclaration());
		assertTrue(barTable.getColumnNamed("chunk").isLOB());
		assertSame(barTable, barFK.getReferencedTable());

		// FOO_BAZ
		Table foo_bazTable = schema.getTableNamed("foo_baz");
		assertEquals(2, foo_bazTable.getColumnsSize());
		assertEquals(0, foo_bazTable.getPrimaryKeyColumnsSize());
		assertEquals(2, foo_bazTable.getForeignKeysSize());
		assertTrue(foo_bazTable.isPossibleJoinTable());
		assertTrue(foo_bazTable.joinTableNameIsDefault());
		assertTrue(foo_bazTable.getColumnNamed("foo_id").isPartOfForeignKey());

		this.dropTable("foo_baz");
		this.dropTable("baz");
		this.dropTable("foo");
		this.dropTable("bar");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	private static final String CR = System.getProperty("line.separator");

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
		sb.append("    CONSTRAINT BAR FOREIGN KEY (bar_id) REFERENCES bar(id)").append(CR);
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

		this.getJDBCConnection().setCatalog(this.getDatabaseName());

		this.dropTable("test1");
		this.dropTable("TEST2");
		this.dropTable("`TEST3`");

		this.executeUpdate("CREATE TABLE test1 (id INTEGER, name VARCHAR(20))");
		this.executeUpdate("CREATE TABLE TEST2 (id INTEGER, name VARCHAR(20))");
		this.executeUpdate("CREATE TABLE `TEST3` (id INTEGER, name VARCHAR(20))");
		// the MySQL database does NOT refresh - see bug 279721...
		((ICatalogObject) this.getDTPDatabase()).refresh();
		// ...refresh the single schema instead
		((ICatalogObject) getDTPSchema(this.getDefaultSchema())).refresh();

		Schema schema = this.getDefaultSchema();

		Table test1Table = schema.getTableForIdentifier("test1");
		assertNotNull(test1Table);

		// if 'lctn' is 0 (UNIX), the table name is case-sensitive
		int lctn = this.getLowerCaseTableNamesFromDatabase();

		String test2Identifier = (lctn == 0) ? "TEST2" : "test2";
		Table test2Table = schema.getTableForIdentifier(test2Identifier);
		assertNotNull(test2Table);

		String test3Identifier = (lctn == 0) ? "`TEST3`" : "`test3`";
		Table test3Table = schema.getTableForIdentifier(test3Identifier);
		assertNotNull(test3Table);

		this.dropTable("test1");
		this.dropTable("TEST2");
		this.dropTable("`TEST3`");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	protected int getLowerCaseTableNamesFromDatabase() throws SQLException {
		// the underscore is a wild character on MySQL, so we need to escape it
		ArrayList<HashMap<String, Object>> rows = this.execute("show variables like 'lower\\_case\\_table\\_names'");
		Map<String, Object> row = rows.get(0);
		return Integer.valueOf((String) row.get("Value")).intValue();
	}

	/**
	 * MySQL preserves the case of column names, delimited or not;
	 * but they are <em>not</em> case-sensitive when used in SQL.
	 * Delimiters are useful for reserved identifiers and special characters.
	 */
	public void testColumnLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.getJDBCConnection().setCatalog(this.getDatabaseName());

		this.dropTable("test");

		// lowercase
		this.executeUpdate("CREATE TABLE test (id INTEGER, name VARCHAR(20))");
		// the MySQL database does NOT refresh - see bug 279721...
		((ICatalogObject) this.getDTPDatabase()).refresh();
		// ...refresh the single schema instead
		((ICatalogObject) getDTPSchema(this.getDefaultSchema())).refresh();

		Table table = this.getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnNamed("id"));
		assertNotNull(table.getColumnNamed("name"));
		assertNotNull(table.getColumnForIdentifier("id"));
		assertNotNull(table.getColumnForIdentifier("name"));

		this.dropTable("test");

		// uppercase
		this.executeUpdate("CREATE TABLE test (ID INTEGER, NAME VARCHAR(20))");
		// the MySQL database does NOT refresh - see bug 279721...
		((ICatalogObject) this.getDTPDatabase()).refresh();
		// ...refresh the single schema instead
		((ICatalogObject) getDTPSchema(this.getDefaultSchema())).refresh();

		table = this.getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnNamed("ID"));
		assertNotNull(table.getColumnNamed("NAME"));
		assertNotNull(table.getColumnForIdentifier("id"));
		assertNotNull(table.getColumnForIdentifier("name"));

		this.dropTable("test");

		// mixed case
		this.executeUpdate("CREATE TABLE test (Id INTEGER, Name VARCHAR(20))");
		// the MySQL database does NOT refresh - see bug 279721...
		((ICatalogObject) this.getDTPDatabase()).refresh();
		// ...refresh the single schema instead
		((ICatalogObject) getDTPSchema(this.getDefaultSchema())).refresh();

		table = this.getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnNamed("Id"));
		assertNotNull(table.getColumnNamed("Name"));
		assertNotNull(table.getColumnForIdentifier("id"));
		assertNotNull(table.getColumnForIdentifier("name"));

		this.dropTable("test");

		// delimited
		this.executeUpdate("CREATE TABLE test (`Id` INTEGER, `Name` VARCHAR(20))");
		// the MySQL database does NOT refresh - see bug 279721...
		((ICatalogObject) this.getDTPDatabase()).refresh();
		// ...refresh the single schema instead
		((ICatalogObject) getDTPSchema(this.getDefaultSchema())).refresh();

		table = this.getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnNamed("Id"));
		assertNotNull(table.getColumnNamed("Name"));
		assertNotNull(table.getColumnForIdentifier("id"));
		assertNotNull(table.getColumnForIdentifier("name"));

		boolean quotes = this.getANSIQuotesFromDatabase();
		assertNotNull(table.getColumnForIdentifier("`Id`"));
		if (quotes) {
			assertNotNull(table.getColumnForIdentifier("\"Id\""));
		}

		assertNotNull(table.getColumnForIdentifier("`Name`"));
		if (quotes) {
			assertNotNull(table.getColumnForIdentifier("\"Name\""));
		}

		this.dropTable("test");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	protected boolean getANSIQuotesFromDatabase() throws SQLException {
		ArrayList<HashMap<String, Object>> rows = this.execute("SELECT @@SESSION.sql_mode");
		Map<String, Object> row = rows.get(0);
		String sql_mode = (String) row.get("@@SESSION.sql_mode");
		String[] modes = sql_mode.split(",");
		return Boolean.valueOf(ArrayTools.contains(modes, "ANSI_QUOTES")).booleanValue();
	}

	private void dropTable(String tableName) throws Exception {
		this.executeUpdate("DROP TABLE IF EXISTS " + tableName);
	}
}
