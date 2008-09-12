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

import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;

/**
 *  Derby 10.1 Embedded Driver Test
 */
@SuppressWarnings("nls")
public class DerbyTests extends DTPPlatformTests {

	public DerbyTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Override
	protected String getPlatformPropertiesFileName() {
		return "derby.properties";
	}

	@Override
	protected String getDriverName() {
		return "Derby Embedded JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionID() {
		return "DriverDefn.Derby Embedded JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionType() {
		return "org.eclipse.datatools.connectivity.db.derby101.genericDriverTemplate";
	}

	@Override
	protected String getDatabaseVendor() {
		return "Derby";
	}

	@Override
	protected String getDatabaseVersion() {
		return "10.1";
	}

	@Override
	protected String getDriverClass() {
		return "org.apache.derby.jdbc.EmbeddedDriver";
	}

	@Override
	protected String getProfileName() {
		return "Derby_10.1_Embedded";
	}

	@Override
	protected String getProfileDescription() {
		return "Derby 10.1 Embedded JDBC Profile [Test]";
	}

	@Override
	protected String getProviderID() {
		return "org.eclipse.datatools.connectivity.db.derby.embedded.connectionProfile";
	}

	@Override
	protected boolean supportsCatalogs() {
		return true;
	}

	public void testSchema() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropSchema("TEST1");
		this.dropSchema("TEST2");

		this.executeUpdate("CREATE SCHEMA TEST1");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema1 = this.getDatabase().getSchemaNamed("TEST1");
		assertNotNull(schema1);
		Catalog catalog = this.getDatabase().getCatalogNamed("");  // Derby's only catalog
		Schema schema1a = catalog.getSchemaNamed("TEST1");
		assertNotNull(schema1a);
		assertSame(schema1, schema1a);  // same schema should be returned by both database and catalog

		this.executeUpdate("CREATE SCHEMA TEST2");
		Schema schema2 = this.getDatabase().getSchemaNamed("TEST2");
		assertNull(schema2);  // should be null until refresh

		((ICatalogObject) getDTPCatalog(catalog)).refresh();
		assertSame(catalog, listener.changedCatalog);
		assertSame(catalog, this.getDatabase().getCatalogNamed(""));

		schema2 = this.getDatabase().getSchemaNamed("TEST2");
		assertNotNull(schema2);
		Schema schema2a = catalog.getSchemaNamed("TEST2");
		assertNotNull(schema2a);
		assertSame(schema2, schema2a);
		assertNotSame(schema1, catalog.getSchemaNamed("TEST1"));  // we should have a new schema after the refresh

		this.dropSchema("TEST2");
		this.dropSchema("TEST1");
		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testSchemaLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dumpDatabaseContainers();
		this.dropSchema("LOOKUP_TEST");
		this.dropSchema("\"lookup_TEST\"");
		((ICatalogObject) this.getDTPDatabase()).refresh();
		this.dumpDatabaseContainers();

		this.executeUpdate("CREATE SCHEMA LOOKUP_TEST");
		((ICatalogObject) this.getDTPDatabase()).refresh();
		this.dumpDatabaseContainers();

		assertNotNull(this.getDatabase().getSchemaNamed("LOOKUP_TEST"));
		assertNotNull(this.getDatabase().getSchemaForIdentifier("LOOKUP_TEST"));

		assertNull(this.getDatabase().getSchemaNamed("lookup_test"));
		assertNotNull(this.getDatabase().getSchemaForIdentifier("lookup_test"));

		assertNull(this.getDatabase().getSchemaNamed("lookup_TEST"));
		assertNotNull(this.getDatabase().getSchemaForIdentifier("lookup_TEST"));

		assertNotNull(this.getDatabase().getSchemaForIdentifier("\"LOOKUP_TEST\""));
		assertNull(this.getDatabase().getSchemaForIdentifier("\"lookup_TEST\""));

		this.dropSchema("LOOKUP_TEST");

		this.executeUpdate("CREATE SCHEMA \"lookup_TEST\"");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		assertNull(this.getDatabase().getSchemaNamed("LOOKUP_TEST"));
		assertNull(this.getDatabase().getSchemaForIdentifier("LOOKUP_TEST"));

		assertNull(this.getDatabase().getSchemaNamed("lookup_test"));
		assertNull(this.getDatabase().getSchemaForIdentifier("lookup_test"));

		assertNotNull(this.getDatabase().getSchemaNamed("lookup_TEST"));
		assertNull(this.getDatabase().getSchemaForIdentifier("lookup_TEST"));

		assertNull(this.getDatabase().getSchemaForIdentifier("\"LOOKUP_TEST\""));
		assertNotNull(this.getDatabase().getSchemaForIdentifier("\"lookup_TEST\""));

		this.dropSchema("\"lookup_TEST\"");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testSchemaIdentifier() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropSchema("LOOKUP_TEST");
		this.dropSchema("\"lookup_TEST\"");

		this.executeUpdate("CREATE SCHEMA lookup_test");  // this gets folded to uppercase
		this.executeUpdate("CREATE SCHEMA \"lookup_TEST\"");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getSchemaForIdentifier("LOOKUP_TEST");
		assertEquals("LOOKUP_TEST", schema.getIdentifier());
		assertEquals("LOOKUP_TEST", schema.getIdentifier("LookupTest"));
		assertNull(schema.getIdentifier("Lookup_Test"));

		schema = this.getDatabase().getSchemaForIdentifier("lookup_test");
		assertEquals("LOOKUP_TEST", schema.getIdentifier());

		schema = this.getDatabase().getSchemaForIdentifier("\"lookup_TEST\"");
		assertEquals("\"lookup_TEST\"", schema.getIdentifier());
		assertEquals("\"lookup_TEST\"", schema.getIdentifier("lookup_TEST"));

		this.dropSchema("\"lookup_TEST\"");
		this.dropSchema("LOOKUP_TEST");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testTable() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropTable("TABLE_TEST", "FOO_BAZ");
		this.dropTable("TABLE_TEST", "BAZ");
		this.dropTable("TABLE_TEST", "FOO");
		this.dropTable("TABLE_TEST", "BAR");
		this.dropSchema("TABLE_TEST");

		this.executeUpdate("CREATE SCHEMA TABLE_TEST");
		this.executeUpdate("SET SCHEMA = TABLE_TEST");

		this.executeUpdate(this.buildBarDDL());
		this.executeUpdate(this.buildFooDDL());
		this.executeUpdate(this.buildBazDDL());
		this.executeUpdate(this.buildFooBazDDL());
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getSchemaNamed("TABLE_TEST");

		// FOO
		Table fooTable = schema.getTableNamed("FOO");
		assertEquals(3, fooTable.columnsSize());
		assertEquals(1, fooTable.primaryKeyColumnsSize());
		assertEquals(1, fooTable.foreignKeysSize());

		Column pkColumn = fooTable.getPrimaryKeyColumn();
		assertEquals("ID", pkColumn.getName());
		Column idColumn = fooTable.getColumnNamed("ID");
		assertSame(pkColumn, idColumn);
		assertEquals("INTEGER", idColumn.getDataTypeName());
		assertSame(fooTable, idColumn.getTable());
		assertTrue(idColumn.isPrimaryKeyColumn());
		assertFalse(idColumn.isForeignKeyColumn());
		assertEquals("int", idColumn.getJavaTypeDeclaration());

		Column nameColumn = fooTable.getColumnNamed("NAME");
		assertEquals("VARCHAR", nameColumn.getDataTypeName());
		assertEquals("java.lang.String", nameColumn.getJavaTypeDeclaration());
		assertFalse(nameColumn.isPrimaryKeyColumn());

		Column barColumn = fooTable.getColumnNamed("BAR_ID");
		assertEquals("INTEGER", barColumn.getDataTypeName());
		assertTrue(barColumn.isForeignKeyColumn());
		assertFalse(barColumn.isPrimaryKeyColumn());

		ForeignKey barFK = fooTable.foreignKeys().next();  // there should only be 1 foreign key
		assertEquals(1, barFK.columnPairsSize());
		assertEquals("BAR", barFK.getAttributeName());
		assertNull(barFK.getJoinColumnAnnotationIdentifier("bar"));
		assertEquals("BAR_ID", barFK.getJoinColumnAnnotationIdentifier("primaryBar"));
		assertSame(fooTable, barFK.getBaseTable());

		assertFalse(fooTable.isPossibleJoinTable());
		assertSame(schema, fooTable.getSchema());

		// BAR
		Table barTable = schema.getTableNamed("BAR");
		assertEquals(2, barTable.columnsSize());
		assertEquals(1, barTable.primaryKeyColumnsSize());
		assertEquals(0, barTable.foreignKeysSize());
		assertEquals("ID", barTable.getPrimaryKeyColumn().getName());
		assertFalse(barTable.isPossibleJoinTable());
		assertEquals("BLOB", barTable.getColumnNamed("CHUNK").getDataTypeName());
		assertEquals("byte[]", barTable.getColumnNamed("CHUNK").getJavaTypeDeclaration());
		assertTrue(barTable.getColumnNamed("CHUNK").dataTypeIsLOB());
		assertSame(barTable, barFK.getReferencedTable());

		// FOO_BAZ
		Table foo_bazTable = schema.getTableNamed("FOO_BAZ");
		assertEquals(2, foo_bazTable.columnsSize());
		assertEquals(0, foo_bazTable.primaryKeyColumnsSize());
		assertEquals(2, foo_bazTable.foreignKeysSize());
		assertTrue(foo_bazTable.isPossibleJoinTable());
		assertTrue(foo_bazTable.joinTableNameIsDefault());
		assertTrue(foo_bazTable.getColumnNamed("FOO_ID").isForeignKeyColumn());

		this.dropTable("TABLE_TEST", "FOO_BAZ");
		this.dropTable("TABLE_TEST", "BAZ");
		this.dropTable("TABLE_TEST", "FOO");
		this.dropTable("TABLE_TEST", "BAR");
		this.dropSchema("TABLE_TEST");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	private static final String CR = System.getProperty("line.separator");  //$NON-NLS-1$

	private String buildBarDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE BAR (").append(CR);
		sb.append("    ID INT PRIMARY KEY,").append(CR);
		sb.append("    CHUNK BLOB(100K)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE FOO (").append(CR);
		sb.append("    ID INT PRIMARY KEY,").append(CR);
		sb.append("    NAME VARCHAR(20),").append(CR);
		sb.append("    BAR_ID INT REFERENCES BAR(ID)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE BAZ (").append(CR);
		sb.append("    ID INT PRIMARY KEY,").append(CR);
		sb.append("    NAME VARCHAR(20)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE FOO_BAZ (").append(CR);
		sb.append("    FOO_ID INT REFERENCES FOO(ID),").append(CR);
		sb.append("    BAZ_ID INT REFERENCES BAZ(ID)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	public void testColumnLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropTable("COLUMN_TEST", "test");
		this.dropSchema("COLUMN_TEST");

		this.executeUpdate("CREATE SCHEMA COLUMN_TEST");
		this.executeUpdate("SET SCHEMA = COLUMN_TEST");

		// lowercase
		this.executeUpdate("CREATE TABLE test (id INTEGER, name VARCHAR(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Table table = this.getDatabase().getSchemaNamed("COLUMN_TEST").getTableForIdentifier("test");
		assertNotNull(table.getColumnForIdentifier("id"));
		assertNotNull(table.getColumnForIdentifier("name"));

		this.dropTable("COLUMN_TEST", "test");

		// uppercase
		this.executeUpdate("CREATE TABLE test (ID INTEGER, NAME VARCHAR(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getSchemaNamed("COLUMN_TEST").getTableForIdentifier("test");
		assertNotNull(table.getColumnForIdentifier("ID"));
		assertNotNull(table.getColumnForIdentifier("NAME"));

		this.dropTable("COLUMN_TEST", "test");

		// mixed case
		this.executeUpdate("CREATE TABLE test (Id INTEGER, Name VARCHAR(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getSchemaNamed("COLUMN_TEST").getTableForIdentifier("test");
		assertNotNull(table.getColumnForIdentifier("Id"));
		assertNotNull(table.getColumnForIdentifier("Name"));

		this.dropTable("COLUMN_TEST", "test");

		// delimited
		this.executeUpdate("CREATE TABLE test (\"Id\" INTEGER, \"Name\" VARCHAR(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getSchemaNamed("COLUMN_TEST").getTableForIdentifier("test");
		assertNotNull(table.getColumnForIdentifier("\"Id\""));
		assertNotNull(table.getColumnForIdentifier("\"Name\""));

		this.dropTable("COLUMN_TEST", "test");
		this.dropSchema("COLUMN_TEST");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	private void dropTable(String schemaName, String tableName) throws Exception {
		Schema schema= this.getSchemaForIdentifier(schemaName);
		if (schema != null) {
			if (schema.getTableForIdentifier(tableName) != null) {
				this.executeUpdate("DROP TABLE " + schemaName + '.' + tableName);
			}
		}
	}

	/**
	 * NB: A Derby schema must be empty before it can be dropped.
	 */
	private void dropSchema(String name) throws Exception {
		if (this.getSchemaForIdentifier(name) != null) {
			this.executeUpdate("DROP SCHEMA " + name + " RESTRICT");
		}
	}

}
