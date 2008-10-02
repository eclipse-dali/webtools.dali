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

import java.sql.SQLException;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;

@SuppressWarnings("nls")
public class Oracle10gTests extends DTPPlatformTests {

	public Oracle10gTests( String name) {
		super( name);
	}

	@Override
	protected String getPlatformPropertiesFileName() {
		return "oracle10g.properties";
	}

	@Override
	protected String getDriverName() {
		return "Oracle 10g Thin Driver";
	}

	@Override
	protected String getDriverDefinitionID() {
		return "DriverDefn.Oracle Thin Driver";
	}

	@Override
	protected String getDriverDefinitionType() {
		return "org.eclipse.datatools.enablement.oracle.10.driverTemplate";
	}

	@Override
	protected String getDatabaseVendor() {
		return "Oracle";
	}

	@Override
	protected String getDatabaseVersion() {
		return "10";
	}

	@Override
	protected String getDriverClass() {
		return "oracle.jdbc.OracleDriver";
	}

	@Override
	protected String getProfileName() {
		return "Oracle10g_10.1.0.4";
	}

	@Override
	protected String getProfileDescription() {
		return "Oracle10g (10.1.0.4) JDBC Profile [Test]";
	}

	@Override
	protected boolean supportsCatalogs() {
		return false;
	}

	@Override
	protected boolean executeOfflineTests() {
		// working offline is pretty ugly
		return false;
	}

	public void testDatabase() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		// Oracle should have a schema with the same name as the user
		Schema schema = this.getDatabase().getSchemaForIdentifier(this.getUserID());
		assertNotNull(schema);
		assertSame(this.getDatabase().getDefaultSchema(), schema);

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testTable() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropTable("foo_baz");
		this.dropTable("baz");
		this.dropTable("foo");
		this.dropTable("bar");

		this.executeUpdate(this.buildBarDDL());
		this.executeUpdate(this.buildFooDDL());
		this.executeUpdate(this.buildBazDDL());
		this.executeUpdate(this.buildFooBazDDL());
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getDefaultSchema();

		// foo
		Table fooTable = schema.getTableForIdentifier("foo");
		assertEquals(3, fooTable.columnsSize());
		assertEquals(1, fooTable.primaryKeyColumnsSize());
		assertEquals(1, fooTable.foreignKeysSize());

		Column pkColumn = fooTable.getPrimaryKeyColumn();
		assertEquals("ID", pkColumn.getName());
		Column idColumn = fooTable.getColumnForIdentifier("id");
		assertSame(pkColumn, idColumn);
		assertEquals("NUMBER", idColumn.getDataTypeName());
		assertSame(fooTable, idColumn.getTable());
		assertTrue(idColumn.isPartOfPrimaryKey());
		assertFalse(idColumn.isPartOfForeignKey());
		assertEquals("java.math.BigDecimal", idColumn.getJavaTypeDeclaration());

		Column nameColumn = fooTable.getColumnForIdentifier("name");
		assertEquals("VARCHAR2", nameColumn.getDataTypeName());
		assertEquals("java.lang.String", nameColumn.getJavaTypeDeclaration());
		assertFalse(nameColumn.isPartOfPrimaryKey());

		Column barColumn = fooTable.getColumnForIdentifier("bar_id");
		assertEquals("NUMBER", barColumn.getDataTypeName());
		assertTrue(barColumn.isPartOfForeignKey());
		assertFalse(barColumn.isPartOfPrimaryKey());

		ForeignKey barFK = fooTable.foreignKeys().next();  // there should only be 1 foreign key
		assertEquals(1, barFK.columnPairsSize());
		assertEquals("BAR", barFK.getAttributeName());
		assertNull(barFK.getJoinColumnAnnotationIdentifier("bar"));
		assertEquals("BAR_ID", barFK.getJoinColumnAnnotationIdentifier("primaryBar"));
		assertSame(fooTable, barFK.getBaseTable());

		assertFalse(fooTable.isPossibleJoinTable());
		assertSame(schema, fooTable.getSchema());

		// BAR
		Table barTable = schema.getTableForIdentifier("bar");
		assertEquals(2, barTable.columnsSize());
		assertEquals(1, barTable.primaryKeyColumnsSize());
		assertEquals(0, barTable.foreignKeysSize());
		assertEquals("ID", barTable.getPrimaryKeyColumn().getName());
		assertFalse(barTable.isPossibleJoinTable());
		assertEquals("BLOB", barTable.getColumnForIdentifier("chunk").getDataTypeName());
		assertEquals("byte[]", barTable.getColumnForIdentifier("chunk").getJavaTypeDeclaration());
		assertTrue(barTable.getColumnForIdentifier("chunk").isLOB());
		assertSame(barTable, barFK.getReferencedTable());

		// FOO_BAZ
		Table foo_bazTable = schema.getTableForIdentifier("foo_baz");
		assertEquals(2, foo_bazTable.columnsSize());
		assertEquals(0, foo_bazTable.primaryKeyColumnsSize());
		assertEquals(2, foo_bazTable.foreignKeysSize());
		assertTrue(foo_bazTable.isPossibleJoinTable());
		assertTrue(foo_bazTable.joinTableNameIsDefault());
		assertTrue(foo_bazTable.getColumnForIdentifier("foo_id").isPartOfForeignKey());

		this.dropTable("foo_baz");
		this.dropTable("baz");
		this.dropTable("foo");
		this.dropTable("bar");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	private static final String CR = System.getProperty("line.separator");  //$NON-NLS-1$

	private String buildBarDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE bar (").append(CR);
		sb.append("    id NUMBER(10) PRIMARY KEY,").append(CR);
		sb.append("    chunk BLOB").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE foo (").append(CR);
		sb.append("    id NUMBER(10) PRIMARY KEY,").append(CR);
		sb.append("    name VARCHAR2(20),").append(CR);
		sb.append("    bar_id REFERENCES bar(id)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE baz (").append(CR);
		sb.append("    id NUMBER(10) PRIMARY KEY,").append(CR);
		sb.append("    name VARCHAR2(20)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("CREATE TABLE foo_baz (").append(CR);
		sb.append("    foo_id NUMBER(10) REFERENCES foo(id),").append(CR);
		sb.append("    baz_id NUMBER(10) REFERENCES baz(id)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	public void testTableLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropTable("test1");
		this.dropTable("TEST2");
		this.dropTable("\"test3\"");

		this.executeUpdate("CREATE TABLE test1 (id NUMBER(10), name VARCHAR2(20))");
		this.executeUpdate("CREATE TABLE TEST2 (id NUMBER(10), name VARCHAR2(20))");
		this.executeUpdate("CREATE TABLE \"test3\" (id NUMBER(10), name VARCHAR2(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getDefaultSchema();

		Table test1Table = schema.getTableForIdentifier("test1");
		assertNotNull(test1Table);
		test1Table = schema.getTableForIdentifier("TEST1");
		assertNotNull(test1Table);

		Table test2Table = schema.getTableForIdentifier("test2");
		assertNotNull(test2Table);
		test2Table = schema.getTableForIdentifier("TEST2");
		assertNotNull(test2Table);

		Table test3Table = schema.getTableForIdentifier("\"test3\"");
		assertNotNull(test3Table);
		test3Table = schema.getTableForIdentifier("test3");
		assertNull(test3Table);

		this.dropTable("test1");
		this.dropTable("TEST2");
		this.dropTable("\"test3\"");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testColumnLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.dropTable("test");

		// lowercase
		this.executeUpdate("CREATE TABLE test (id NUMBER(10), name VARCHAR2(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Table table = this.getDatabase().getDefaultSchema().getTableForIdentifier("test");
		assertNotNull(table.getColumnForIdentifier("id"));
		assertNotNull(table.getColumnForIdentifier("name"));

		this.dropTable("test");

		// uppercase
		this.executeUpdate("CREATE TABLE test (ID NUMBER(10), NAME VARCHAR2(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getDefaultSchema().getTableForIdentifier("test");
		assertNotNull(table.getColumnForIdentifier("ID"));
		assertNotNull(table.getColumnForIdentifier("NAME"));

		this.dropTable("test");

		// mixed case
		this.executeUpdate("CREATE TABLE test (Id NUMBER(10), Name VARCHAR2(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getDefaultSchema().getTableForIdentifier("test");
		assertNotNull(table.getColumnForIdentifier("Id"));
		assertNotNull(table.getColumnForIdentifier("Name"));

		this.dropTable("test");

		// delimited
		this.executeUpdate("CREATE TABLE test (\"Id\" NUMBER(10), \"Name\" VARCHAR2(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getDefaultSchema().getTableForIdentifier("test");
		assertNotNull(table.getColumnForIdentifier("\"Id\""));
		assertNotNull(table.getColumnForIdentifier("\"Name\""));

		this.dropTable("test");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	private void dropTable(String tableName) throws Exception {
		this.executeUpdateIgnoreErrors("DROP TABLE " + tableName + " CASCADE CONSTRAINTS");
	}

// need Oracle enablement plug-in
//	public void testSequence() throws Exception {
//		this.connectionProfile.connect();
//		TestConnectionListener listener = new TestConnectionListener();
//		this.connectionProfile.addConnectionListener(listener);
//
//		this.dropSequence("FOO_SEQ");
//
//		this.executeUpdate("CREATE SEQUENCE FOO_SEQ");
//		((ICatalogObject) this.getDTPDatabase()).refresh();
//
//		Sequence sequence = this.getDatabase().getDefaultSchema().getSequenceForIdentifier("FOO");
//		assertNotNull(sequence);
//		assertEquals("FOO_SEQ", sequence.getName());
//
//		this.dropSequence("FOO_SEQ");
//
//		this.connectionProfile.removeConnectionListener(listener);
//		this.connectionProfile.disconnect();
//	}
//
//	private void dropSequence(String sequenceName) throws Exception {
//		this.executeUpdateIgnoreErrors("DROP SEQUENCE " + sequenceName);
//	}
//
	protected void dumpUserObjects() throws SQLException {
		this.dump("select * from user_objects");
	}

}
