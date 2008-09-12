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
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;


/**
 *  Oracle 10g Thin Driver Test
 */
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
	public void testOffline() {
		// working offline is pretty ugly
	}

	@Override
	public void testConnectionListenerOffline() {
		// working offline is pretty ugly
	}

	/**
	 * Sybase "databases" become DTP "catalogs"
	 */
	public void testCatalog() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.executeUpdate("use master");
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
		assertNotSame(catalog1, this.getDatabase().getSchemaNamed("test1"));  // we should have a new schema after the refresh

		this.executeUpdate("drop database test2");
		this.executeUpdate("drop database test1");
		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testTable() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.executeUpdate("use master");
		this.executeUpdateIgnoreErrors("drop database table_test");
		this.executeUpdate("create database table_test");
		this.executeUpdate("use table_test");

		this.executeUpdate(this.buildBarDDL());
		this.executeUpdate(this.buildFooDDL());
		this.executeUpdate(this.buildBazDDL());
		this.executeUpdate(this.buildFooBazDDL());
		((ICatalogObject) this.getDTPDatabase()).refresh();

		Schema schema = this.getDatabase().getCatalogNamed("table_test").getDefaultSchema();

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
		assertTrue(idColumn.isPrimaryKeyColumn());
		assertFalse(idColumn.isForeignKeyColumn());
		assertEquals("int", idColumn.getJavaTypeDeclaration());

		Column nameColumn = fooTable.getColumnNamed("name");
		assertEquals("VARCHAR", nameColumn.getDataTypeName());
		assertEquals("java.lang.String", nameColumn.getJavaTypeDeclaration());
		assertFalse(nameColumn.isPrimaryKeyColumn());

		Column barColumn = fooTable.getColumnNamed("bar_id");
		assertEquals("INT", barColumn.getDataTypeName());
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
		Table barTable = schema.getTableNamed("bar");
		assertEquals(2, barTable.columnsSize());
		assertEquals(1, barTable.primaryKeyColumnsSize());
		assertEquals(0, barTable.foreignKeysSize());
		assertEquals("id", barTable.getPrimaryKeyColumn().getName());
		assertFalse(barTable.isPossibleJoinTable());
		assertEquals("IMAGE", barTable.getColumnNamed("chunk").getDataTypeName());
		assertEquals("byte[]", barTable.getColumnNamed("chunk").getJavaTypeDeclaration());
		assertTrue(barTable.getColumnNamed("chunk").dataTypeIsLOB());
		assertSame(barTable, barFK.getReferencedTable());

		// FOO_BAZ
		Table foo_bazTable = schema.getTableNamed("foo_baz");
		assertEquals(2, foo_bazTable.columnsSize());
		assertEquals(0, foo_bazTable.primaryKeyColumnsSize());
		assertEquals(2, foo_bazTable.foreignKeysSize());
		assertTrue(foo_bazTable.isPossibleJoinTable());
		assertTrue(foo_bazTable.joinTableNameIsDefault());
		assertTrue(foo_bazTable.getColumnNamed("foo_id").isForeignKeyColumn());

		this.executeUpdate("drop table foo_baz");
		this.executeUpdate("drop table baz");
		this.executeUpdate("drop table foo");
		this.executeUpdate("drop table bar");

		this.executeUpdate("use master");
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
		sb.append("    name varchar(20),").append(CR);
		sb.append("    bar_id integer references bar(id)").append(CR);
		sb.append(")").append(CR);
		return sb.toString();
	}

	private String buildBazDDL() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("create table baz (").append(CR);
		sb.append("    id integer primary key,").append(CR);
		sb.append("    name varchar(20)").append(CR);
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

		this.executeUpdate("use master");
		this.executeUpdateIgnoreErrors("drop database table_lookup_test");
		this.executeUpdate("create database table_lookup_test");
		this.executeUpdate("use table_lookup_test");

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

		this.executeUpdate("use master");
		this.executeUpdate("drop database table_lookup_test");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

	public void testColumnLookup() throws Exception {
		this.connectionProfile.connect();
		TestConnectionListener listener = new TestConnectionListener();
		this.connectionProfile.addConnectionListener(listener);

		this.executeUpdate("use master");
		this.executeUpdateIgnoreErrors("drop database column_lookup_test");
		this.executeUpdate("create database column_lookup_test");
		this.executeUpdate("use column_lookup_test");

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

		this.executeUpdate("use master");
		this.executeUpdate("drop database column_lookup_test");

		this.connectionProfile.removeConnectionListener(listener);
		this.connectionProfile.disconnect();
	}

}
