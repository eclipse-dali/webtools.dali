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

import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;


/**
 *  Oracle 10g Thin Driver Test
 */
@SuppressWarnings("nls")
public class PostgreSQLTests extends DTPPlatformTests {

	public PostgreSQLTests( String name) {
		super( name);
	}

	@Override
	protected String getPlatformPropertiesFileName() {
		return "postgresql.properties";
	}

	@Override
	protected String getDriverName() {
		return "PostgreSQL JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionID() {
		return "DriverDefn.PostgreSQL JDBC Driver";
	}

	@Override
	protected String getDriverDefinitionType() {
		return "org.eclipse.datatools.enablement.postgresql.postgresqlDriverTemplate";
	}

	@Override
	protected String getDatabaseVendor() {
		return "postgres";
	}

	@Override
	protected String getDatabaseVersion() {
		return "8.x";
	}

	@Override
	protected String getDriverClass() {
		return "org.postgresql.Driver";
	}

	@Override
	protected String getDefaultJDBCURL() {
		return "jdbc:postgresql";
	}

	@Override
	protected String getProfileName() {
		return "PostgreSQL";
	}

	@Override
	protected String getProfileDescription() {
		return "PostgreSQL 8.2 JDBC Profile [Test]";
	}

	@Override
	public void testOffline() {
		// DTP does not support PostgreSQL off-line - see 226704/241558
	}

	@Override
	public void testConnectionListenerOffline() {
		// DTP does not support PostgreSQL off-line - see 226704/241558
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

		this.executeUpdate("CREATE SCHEMA TEST2");
		Schema schema2 = this.getDatabase().getSchemaNamed("TEST2");
		assertNull(schema2);  // should be null until refresh

		((ICatalogObject) this.getDTPDatabase()).refresh();
		assertSame(this.getDatabase(), listener.changedDatabase);

		schema2 = this.getDatabase().getSchemaNamed("TEST2");
		assertNotNull(schema2);
		assertNotSame(schema1, this.getDatabase().getSchemaNamed("TEST1"));  // we should have a new schema after the refresh

		this.dropSchema("TEST2");
		this.dropSchema("TEST1");
		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testSchemaLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropSchema("LOOKUP_TEST");
		this.dropSchema("\"lookup_TEST\"");

		this.executeUpdate("CREATE SCHEMA LOOKUP_TEST");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		assertNotNull(this.getDatabase().getSchemaNamed("LOOKUP_TEST"));
		assertNotNull(this.getDatabase().getSchemaNamed("lookup_test"));
		assertNotNull(this.getDatabase().getSchemaNamed("lookup_TEST"));
		assertNotNull(this.getDatabase().getSchemaNamed("\"lookup_test\""));
		assertNull(this.getDatabase().getSchemaNamed("\"lookup_TEST\""));
		assertNull(this.getDatabase().getSchemaNamed("\"LOOKUP_TEST\""));

		this.dropSchema("LOOKUP_TEST");

		this.executeUpdate("CREATE SCHEMA \"lookup_TEST\"");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		assertNull(this.getDatabase().getSchemaNamed("LOOKUP_TEST"));
		assertNull(this.getDatabase().getSchemaNamed("lookup_test"));
		assertNull(this.getDatabase().getSchemaNamed("lookup_TEST"));
		assertNull(this.getDatabase().getSchemaNamed("\"LOOKUP_TEST\""));
		assertNotNull(this.getDatabase().getSchemaNamed("\"lookup_TEST\""));

		this.dropSchema("\"lookup_TEST\"");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testSchemaAnnotationIdentifier() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropSchema("LOOKUP_TEST");
		this.dropSchema("\"lookup_TEST\"");

		this.executeUpdate("CREATE SCHEMA lookup_test");  // this gets folded to uppercase
		this.executeUpdate("CREATE SCHEMA \"lookup_TEST\"");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getSchemaNamed("LOOKUP_TEST");
		assertEquals("lookup_test", schema.getAnnotationIdentifier());
		assertEquals("lookup_test", schema.getAnnotationIdentifier("LookupTest"));
		assertNull(schema.getAnnotationIdentifier("Lookup_Test"));

		schema = this.getDatabase().getSchemaNamed("lookup_test");
		assertEquals("lookup_test", schema.getAnnotationIdentifier());

		schema = this.getDatabase().getSchemaNamed("\"lookup_TEST\"");
		assertEquals("\"lookup_TEST\"", schema.getAnnotationIdentifier());
		assertEquals("\"lookup_TEST\"", schema.getAnnotationIdentifier("lookup_TEST"));

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
		this.executeUpdate("SET search_path TO TABLE_TEST");

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
		assertEquals("id", pkColumn.getName());
		Column idColumn = fooTable.getColumnNamed("ID");
		assertSame(pkColumn, idColumn);
		assertEquals("INT4", idColumn.getDataTypeName());
		assertSame(fooTable, idColumn.getTable());
		assertTrue(idColumn.isPrimaryKeyColumn());
		assertFalse(idColumn.isForeignKeyColumn());
		assertEquals("java.lang.Integer", idColumn.getJavaTypeDeclaration());

		Column nameColumn = fooTable.getColumnNamed("NAME");
		assertEquals("VARCHAR", nameColumn.getDataTypeName());
		assertEquals("java.lang.String", nameColumn.getJavaTypeDeclaration());
		assertFalse(nameColumn.isPrimaryKeyColumn());

		Column barColumn = fooTable.getColumnNamed("BAR_ID");
		assertEquals("INT4", barColumn.getDataTypeName());
		assertTrue(barColumn.isForeignKeyColumn());
		assertFalse(barColumn.isPrimaryKeyColumn());

		ForeignKey barFK = fooTable.foreignKeys().next();  // there should only be 1 foreign key
		assertEquals(1, barFK.columnPairsSize());
		assertEquals("bar", barFK.getAttributeName());
		assertNull(barFK.getJoinColumnAnnotationIdentifier("bar"));
		assertEquals("bar_id", barFK.getJoinColumnAnnotationIdentifier("primaryBar"));
		assertSame(fooTable, barFK.getBaseTable());

		assertFalse(fooTable.isPossibleJoinTable());
		assertSame(schema, fooTable.getSchema());

		// BAR
		Table barTable = schema.getTableNamed("BAR");
		assertEquals(2, barTable.columnsSize());
		assertEquals(1, barTable.primaryKeyColumnsSize());
		assertEquals(0, barTable.foreignKeysSize());
		assertEquals("id", barTable.getPrimaryKeyColumn().getName());
		assertFalse(barTable.isPossibleJoinTable());
		assertEquals("BYTEA", barTable.getColumnNamed("CHUNK").getDataTypeName());
		assertEquals("byte[]", barTable.getColumnNamed("CHUNK").getJavaTypeDeclaration());
		// assertTrue(barTable.getColumnNamed("CHUNK").dataTypeIsLOB());
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
		sb.append("    ID integer PRIMARY KEY,").append(CR);
		sb.append("    CHUNK bytea").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE FOO (").append(CR);
		sb.append("    ID integer PRIMARY KEY,").append(CR);
		sb.append("    NAME varchar(20),").append(CR);
		sb.append("    BAR_ID integer REFERENCES BAR(ID)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE BAZ (").append(CR);
		sb.append("    ID integer PRIMARY KEY,").append(CR);
		sb.append("    NAME varchar(20)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE FOO_BAZ (").append(CR);
		sb.append("    FOO_ID int REFERENCES FOO(ID),").append(CR);
		sb.append("    BAZ_ID int REFERENCES BAZ(ID)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	public void testColumnLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropTable("TABLE_TEST", "test");
		this.dropSchema("TABLE_TEST");

		this.executeUpdate("CREATE SCHEMA TABLE_TEST");
		this.executeUpdate("SET search_path TO TABLE_TEST");

		// lowercase
		this.executeUpdate("CREATE TABLE test (id int, name varchar(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Table table = this.getDatabase().getSchemaNamed("TABLE_TEST").getTableNamed("test");
		assertNotNull(table.getColumnNamed("id"));
		assertNotNull(table.getColumnNamed("name"));

		this.dropTable("TABLE_TEST", "test");

		// uppercase
		this.executeUpdate("CREATE TABLE test (ID int, NAME varchar(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getSchemaNamed("TABLE_TEST").getTableNamed("test");
		assertNotNull(table.getColumnNamed("ID"));
		assertNotNull(table.getColumnNamed("NAME"));

		this.dropTable("TABLE_TEST", "test");

		// mixed case
		this.executeUpdate("CREATE TABLE test (Id int, Name varchar(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getSchemaNamed("TABLE_TEST").getTableNamed("test");
		assertNotNull(table.getColumnNamed("Id"));
		assertNotNull(table.getColumnNamed("Name"));

		this.dropTable("TABLE_TEST", "test");

		// delimited
		this.executeUpdate("CREATE TABLE test (\"Id\" int, \"Name\" varchar(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getSchemaNamed("TABLE_TEST").getTableNamed("test");
		assertNotNull(table.getColumnNamed("\"Id\""));
		assertNotNull(table.getColumnNamed("\"Name\""));

		this.dropTable("TABLE_TEST", "test");
		this.dropSchema("TABLE_TEST");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	private void dropTable(String schemaName, String tableName) throws Exception {
		Schema schema= this.getSchemaNamed(schemaName);
		if (schema != null) {
			if (schema.getTableNamed(tableName) != null) {
				this.executeUpdate("DROP TABLE " + schemaName + '.' + tableName);
			}
		}
	}

	private void dropSchema(String name) throws Exception {
		if (this.getSchemaNamed(name) != null) {
			this.executeUpdate("DROP SCHEMA " + name + " CASCADE");
		}
	}

// see 241578/241557
//	public void testSequence() throws Exception {
//		this.connectionProfile.connect();
//		TestConnectionListener listener = new TestConnectionListener();
//		this.connectionProfile.addConnectionListener(listener);
//
//		this.dropSequence("SEQUENCE_TEST", "FOO");
//		this.dropSchema("SEQUENCE_TEST");
//
//		this.executeUpdate("CREATE SCHEMA SEQUENCE_TEST");
//		this.executeUpdate("SET search_path TO SEQUENCE_TEST");
//
//		this.executeUpdate(this.buildBarDDL());
//		this.executeUpdate("CREATE SEQUENCE FOO START 1");
////		List<Object[]> list = this.execute("SELECT nextval('foo')");
////		System.out.println(list);
//		((ICatalogObject) this.getDTPDatabase()).refresh();
//
//		Schema schema = this.getDatabase().getSchemaNamed("SEQUENCE_TEST");
//		Sequence sequence = schema.getSequenceNamed("FOO");
//		assertNotNull(sequence);
//		assertEquals("foo_seq", sequence.getName());
//
//		this.dropSequence("SEQUENCE_TEST", "FOO");
//		this.dropSchema("SEQUENCE_TEST");
//
//		this.connectionProfile.removeConnectionListener(listener);
//		this.connectionProfile.disconnect();
//	}
//
//	private void dropSequence(String schemaName, String sequenceName) throws Exception {
//		Schema schema= this.getSchemaNamed(schemaName);
//		if (schema != null) {
//			if (schema.getSequenceNamed(sequenceName) != null) {
//				this.executeUpdate("DROP SEQUENCE " + schemaName + '.' + sequenceName);
//			}
//		}
//	}
//
}
