/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.tests.internal.platforms;

import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;

@SuppressWarnings("nls")
public class SybaseTests extends DTPPlatformTests {

	public SybaseTests( String name) {
		super( name);
	}

	@Override
	protected String getPlatformPropertiesFileName() {
		return "sybase.properties";
	}

	@Override
	protected String getDriverName() {
		return "Sybase JDBC Driver for Sybase ASE 15.x";
	}

	@Override
	protected String getDriverDefinitionID() {
		return "DriverDefn.Sybase JDBC Driver for Sybase ASE 15.x";
	}

	@Override
	protected String getDriverDefinitionType() {
		return "org.eclipse.datatools.connectivity.db.sybase.ase.genericDriverTemplate_15";
	}

	@Override
	protected String getDatabaseVendor() {
		return "Sybase_ASE";
	}

	@Override
	protected String getDatabaseVersion() {
		return "15.x";
	}

	@Override
	protected String getDriverClass() {
		return "com.sybase.jdbc3.jdbc.SybDriver";
	}

	@Override
	protected String getProfileName() {
		return "Sybase_15";
	}

	@Override
	protected String getProfileDescription() {
		return "Sybase ASE 15 jConnect JDBC Profile [Test]";
	}

	@Override
	protected boolean supportsCatalogs() {
		return true;
	}

	@Override
	protected boolean executeOfflineTests() {
		// working offline is pretty ugly
		return false;
	}

	/**
	 * Sybase "databases" become DTP "catalogs"
	 */
	public void testCatalog() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdateIgnoreErrors("drop database test1");
		this.executeUpdateIgnoreErrors("drop database test2");

		this.executeUpdate("create database test1");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Catalog catalog1 = this.getDatabase().getCatalogNamed("test1");
		assertNotNull(catalog1);
		Schema schema1 = catalog1.getSchemaNamed("dbo");
		assertNotNull(schema1);
		assertSame(schema1, catalog1.getDefaultSchema());

		this.executeUpdate("create database test2");
		Catalog catalog2 = this.getDatabase().getCatalogNamed("test2");
		assertNull(catalog2);  // should be null until refresh

		((ICatalogObject) this.getDTPDatabase()).refresh();
		assertSame(this.getDatabase(), listener.changedDatabase);

		catalog2 = this.getDatabase().getCatalogNamed("test2");
		assertNotNull(catalog2);
		Schema schema2 = catalog2.getDefaultSchema();
		assertNotNull(schema2);

		assertNotSame(catalog1, this.getDatabase().getCatalogNamed("test1"));  // we should have a new catalog after the refresh
		assertNotSame(schema1, this.getDatabase().getCatalogNamed("test1").getDefaultSchema());  // we should have a new schema after the refresh

		this.executeUpdate("drop database test2");
		this.executeUpdate("drop database test1");
		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testTable() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdateIgnoreErrors("drop database table_test");
		this.executeUpdate("create database table_test");
		this.getJDBCConnection().setCatalog("table_test");

		this.executeUpdate(this.buildBarDDL());
		this.executeUpdate(this.buildFooDDL());
		this.executeUpdate(this.buildBazDDL());
		this.executeUpdate(this.buildFooBazDDL());
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getCatalogNamed("table_test").getDefaultSchema();

		// foo
		Table fooTable = schema.getTableNamed("foo");
		assertEquals(3, fooTable.getColumnsSize());
		assertEquals(1, fooTable.getPrimaryKeyColumnsSize());
		assertEquals(1, fooTable.getForeignKeysSize());

		Column pkColumn = fooTable.getPrimaryKeyColumn();
		assertEquals("id", pkColumn.getName());
		Column idColumn = fooTable.getColumnNamed("id");
		assertSame(fooTable, idColumn.getTable());
		assertSame(pkColumn, idColumn);
		assertTrue(idColumn.isPartOfPrimaryKey());
		assertFalse(idColumn.isPartOfForeignKey());
		assertTrue(idColumn.isPartOfUniqueConstraint());
		assertFalse(idColumn.isNullable());

		assertEquals("INT", idColumn.getDataTypeName());
		assertTrue(idColumn.isNumeric());
		assertEquals(0, idColumn.getPrecision());
		assertEquals(0, idColumn.getScale());
		assertEquals(-1, idColumn.getLength());
		assertFalse(idColumn.isLOB());
		assertEquals("int", idColumn.getJavaTypeDeclaration());

		Column nameColumn = fooTable.getColumnNamed("name");
		assertFalse(nameColumn.isPartOfPrimaryKey());
		assertFalse(nameColumn.isPartOfForeignKey());
		assertTrue(nameColumn.isPartOfUniqueConstraint());
		assertFalse(nameColumn.isNullable());  // implied "NOT NULL" ?

		assertEquals("VARCHAR", nameColumn.getDataTypeName());
		assertFalse(nameColumn.isNumeric());
		assertEquals(-1, nameColumn.getPrecision());
		assertEquals(-1, nameColumn.getScale());
		assertEquals(20, nameColumn.getLength());
		assertFalse(nameColumn.isLOB());
		assertEquals("java.lang.String", nameColumn.getJavaTypeDeclaration());

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
		Column chunkColumn = barTable.getColumnNamed("chunk");
		assertEquals("IMAGE", chunkColumn.getDataTypeName());
		assertFalse(chunkColumn.isNumeric());
		assertTrue(chunkColumn.isLOB());
		assertEquals("byte[]", chunkColumn.getJavaTypeDeclaration());
		assertSame(barTable, barFK.getReferencedTable());

		// BAZ
		Table bazTable = schema.getTableNamed("baz");
		assertEquals(4, bazTable.getColumnsSize());
		assertEquals(1, bazTable.getPrimaryKeyColumnsSize());
		assertEquals(0, bazTable.getForeignKeysSize());

		Column nicknameColumn = bazTable.getColumnNamed("nickname");
		assertTrue(nicknameColumn.isNullable());

		Column songColumn = bazTable.getColumnNamed("song");
		assertFalse(songColumn.isNullable());

		Column salaryColumn = bazTable.getColumnNamed("salary");
		assertFalse(salaryColumn.isPartOfUniqueConstraint());
		assertEquals("DECIMAL", salaryColumn.getDataTypeName());
		assertTrue(salaryColumn.isNumeric());
		assertEquals(10, salaryColumn.getPrecision());
		assertEquals(2, salaryColumn.getScale());
		assertEquals(-1, salaryColumn.getLength());
		assertFalse(salaryColumn.isLOB());

		// FOO_BAZ
		Table foo_bazTable = schema.getTableNamed("foo_baz");
		assertEquals(2, foo_bazTable.getColumnsSize());
		assertEquals(0, foo_bazTable.getPrimaryKeyColumnsSize());
		assertEquals(2, foo_bazTable.getForeignKeysSize());
		assertTrue(foo_bazTable.isPossibleJoinTable());
		assertTrue(foo_bazTable.joinTableNameIsDefault());
		assertTrue(foo_bazTable.getColumnNamed("foo_id").isPartOfForeignKey());

		this.executeUpdate("drop table foo_baz");
		this.executeUpdate("drop table baz");
		this.executeUpdate("drop table foo");
		this.executeUpdate("drop table bar");

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdate("drop database table_test");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	private static final String CR = System.getProperty("line.separator");  //$NON-NLS-1$

	private String buildBarDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("create table bar (").append(CR);
		sb.append("    id integer primary key,").append(CR);
		sb.append("    chunk image").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("create table foo (").append(CR);
		sb.append("    id integer primary key,").append(CR);
		sb.append("    name varchar(20) unique,").append(CR);
		sb.append("    bar_id integer references bar(id)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("create table baz (").append(CR);
		sb.append("    id integer primary key,").append(CR);
		sb.append("    nickname varchar(20) null,").append(CR);
		sb.append("    song varchar(20) not null,").append(CR);
		sb.append("    salary decimal(10, 2)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildFooBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("create table foo_baz (").append(CR);
		sb.append("    foo_id integer references foo(id),").append(CR);
		sb.append("    baz_id integer references baz(id)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	public void testTableLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdateIgnoreErrors("drop database table_lookup_test");
		this.executeUpdate("create database table_lookup_test");
		this.getJDBCConnection().setCatalog("table_lookup_test");

		this.executeUpdate("create table test1 (id integer, name varchar(20))");
		this.executeUpdate("create table TEST2 (id integer, name varchar(20))");
		this.executeUpdate("create table [Test3] (id integer, name varchar(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getCatalogNamed("table_lookup_test").getDefaultSchema();

		assertNotNull(schema.getTableNamed("test1"));
		assertNotNull(schema.getTableForIdentifier("test1"));

		assertNotNull(schema.getTableNamed("TEST2"));
		assertNotNull(schema.getTableForIdentifier("TEST2"));

		assertNotNull(schema.getTableForIdentifier("[Test3]"));

		this.executeUpdate("drop table [Test3]");
		this.executeUpdate("drop table TEST2");
		this.executeUpdate("drop table test1");

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdate("drop database table_lookup_test");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testColumnLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdateIgnoreErrors("drop database column_lookup_test");
		this.executeUpdate("create database column_lookup_test");
		this.getJDBCConnection().setCatalog("column_lookup_test");

		// lowercase
		this.executeUpdate("create table test (id integer, name varchar(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Table table = this.getDatabase().getCatalogNamed("column_lookup_test").getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnNamed("id"));
		assertNotNull(table.getColumnNamed("name"));

		this.executeUpdate("drop table test");

		// uppercase
		this.executeUpdate("create table test (ID integer, NAME varchar(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getCatalogNamed("column_lookup_test").getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnForIdentifier("ID"));
		assertNotNull(table.getColumnForIdentifier("NAME"));

		this.executeUpdate("drop table test");

		// mixed case
		this.executeUpdate("create table test (Id integer, Name varchar(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getCatalogNamed("column_lookup_test").getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnForIdentifier("Id"));
		assertNotNull(table.getColumnForIdentifier("Name"));

		this.executeUpdate("drop table test");

		// delimited
		this.executeUpdate("create table test ([Id] integer, [Name] varchar(20))");
		((ICatalogObject) this.getDTPDatabase()).refresh();

		table = this.getDatabase().getCatalogNamed("column_lookup_test").getDefaultSchema().getTableNamed("test");
		assertNotNull(table.getColumnForIdentifier("[Id]"));
		assertNotNull(table.getColumnForIdentifier("[Name]"));

		this.executeUpdate("drop table test");

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdate("drop database column_lookup_test");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testCrossSchemaReference() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdateIgnoreErrors("drop database xref_test2");
		this.executeUpdateIgnoreErrors("drop database xref_test1");

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdate("create database xref_test1");
		this.getJDBCConnection().setCatalog("xref_test1");
		this.executeUpdate("create table org (id integer primary key, name varchar(20))");

		this.getJDBCConnection().setCatalog("master");
		this.executeUpdate("create database xref_test2");
		this.getJDBCConnection().setCatalog("xref_test2");
		this.executeUpdate("create table emp (id integer primary key, name varchar(20), " +
				"org_id integer references xref_test1..org(id))");

		((ICatalogObject) this.getDTPDatabase()).refresh();
		Catalog catalog1 = this.getDatabase().getCatalogNamed("xref_test1");
		assertNotNull(catalog1);
		Schema schema1 = catalog1.getSchemaNamed("dbo");
		assertNotNull(schema1);
		Table orgTable = schema1.getTableNamed("org");
		assertNotNull(orgTable);

		Catalog catalog2 = this.getDatabase().getCatalogNamed("xref_test2");
		assertNotNull(catalog2);
		Schema schema2 = catalog2.getSchemaNamed("dbo");
		assertNotNull(schema2);
		Table empTable = schema2.getTableNamed("emp");
		assertNotNull(empTable);
		assertEquals(1, empTable.getForeignKeysSize());
		ForeignKey fk = empTable.getForeignKeys().iterator().next();
		Table refTable = fk.getReferencedTable();
		assertNotNull(refTable);
		assertEquals("org", refTable.getName());
		assertEquals(1, fk.getColumnPairsSize());
		ForeignKey.ColumnPair cp = fk.getColumnPairs().iterator().next();
		Column baseColumn = cp.getBaseColumn();
		assertEquals("org_id", baseColumn.getName());
		Column refColumn = cp.getReferencedColumn();
		assertEquals("id", refColumn.getName());

		this.getJDBCConnection().setCatalog("xref_test2");
		this.executeUpdate("drop table emp");
		this.getJDBCConnection().setCatalog("xref_test1");
		this.executeUpdate("drop table org");
		this.getJDBCConnection().setCatalog("master");
		this.executeUpdate("drop database xref_test2");
		this.executeUpdate("drop database xref_test1");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

}
