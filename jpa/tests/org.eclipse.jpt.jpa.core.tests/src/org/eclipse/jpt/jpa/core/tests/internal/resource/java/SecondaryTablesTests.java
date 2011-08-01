/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;

@SuppressWarnings("nls")
public class SecondaryTablesTests extends JpaJavaResourceModelTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public SecondaryTablesTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestSecondaryTables() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable)");
			}
		});
	}
	
	private ICompilationUnit createTestSecondaryTableWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(name = \"" + TABLE_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestSecondaryTableWithSchema() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(schema = \"" + SCHEMA_NAME + "\"))");
			}
		});
	}
	private ICompilationUnit createTestSecondaryTableWithCatalog() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(catalog = \"" + CATALOG_NAME + "\"))");
			}
		});
	}

	
	private ICompilationUnit createTestSecondaryTableWithUniqueConstraints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint}))");
			}
		});
	}
	
	private ICompilationUnit createTestSecondaryTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTable(name = \"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames = {\"BAR\"}))");
			}
		});
	}

	private ICompilationUnit createTestSecondaryTablesWithPkJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLES, JPA.SECONDARY_TABLE, JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAR\"), @PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn(name = \"BAZ\")}))");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		Annotation secondaryTables = resourceType.getAnnotation(JPA.SECONDARY_TABLES);
		assertNotNull(secondaryTables);
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals(TABLE_NAME, secondaryTable.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNull(secondaryTable.getName());
		assertNull(secondaryTable.getCatalog());
		assertNull(secondaryTable.getSchema());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
	
		assertNull(secondaryTable.getName());

		secondaryTable.setName("Foo");
		assertEquals("Foo", secondaryTable.getName());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(name = \"Foo\"))", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals(TABLE_NAME, secondaryTable.getName());
		
		secondaryTable.setName(null);
		assertNull(secondaryTable.getName());
		
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals(CATALOG_NAME, secondaryTable.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNull(secondaryTable.getCatalog());

		secondaryTable.setCatalog("Foo");
		assertEquals("Foo", secondaryTable.getCatalog());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(catalog = \"Foo\"))", cu);
	}
	
	public void testSetCatalogNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals(CATALOG_NAME, secondaryTable.getCatalog());
		
		secondaryTable.setCatalog(null);
		assertNull(secondaryTable.getCatalog());
		
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}
	
	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNotNull(secondaryTable);
		assertEquals(SCHEMA_NAME, secondaryTable.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNull(secondaryTable.getSchema());

		secondaryTable.setSchema("Foo");
		assertEquals("Foo", secondaryTable.getSchema());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(schema = \"Foo\"))", cu);
	}
	
	public void testSetSchemaNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals(SCHEMA_NAME, secondaryTable.getSchema());
		
		secondaryTable.setSchema(null);
		assertNull(secondaryTable.getSchema());
		
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}


	public void testUniqueConstraints() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertEquals(0, secondaryTable.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		secondaryTable.addUniqueConstraint(0);
		secondaryTable.addUniqueConstraint(1);
		
		assertEquals(2, secondaryTable.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
				
		assertEquals(2, secondaryTable.getUniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		secondaryTable.addUniqueConstraint(1);
		secondaryTable.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(0, secondaryTable.uniqueConstraintAt(2).getColumnNamesSize());

		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = \"BAR\"),@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint}))", cu);
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		
		secondaryTable.removeUniqueConstraint(2);
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint(columnNames = {\"BAR\"})})", cu);
		
		secondaryTable.removeUniqueConstraint(0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = @UniqueConstraint(columnNames = {\"BAR\"}))", cu);
		
		secondaryTable.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
	
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(0, secondaryTable.uniqueConstraintAt(2).getColumnNamesSize());
		
		secondaryTable.moveUniqueConstraint(2, 0);
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals(0, secondaryTable.uniqueConstraintAt(1).getColumnNamesSize());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(2).columnNameAt(0));
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint, @UniqueConstraint(columnNames = \"FOO\")}))", cu);
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(0, secondaryTable.uniqueConstraintAt(2).getColumnNamesSize());
		
		secondaryTable.moveUniqueConstraint(0, 2);
		assertEquals(0, secondaryTable.uniqueConstraintAt(0).getColumnNamesSize());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(2).columnNameAt(0));
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = {@UniqueConstraint, @UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint(columnNames = {\"BAR\"})}))", cu);
	}
	
	
	//  @Entity     				-->>    @Entity
	//	@SecondaryTable(name="FOO")			@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})	
	public void testAddSecondaryTableCopyExisting() throws Exception {
		ICompilationUnit cu = createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		String expected1 = "@SecondaryTables({";
		String expected2 = "@SecondaryTable(name = \"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames = { \"BAR\" })),";
		String expected3 = "@SecondaryTable(name = \"BAR\") })";
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.addAnnotation(1, JPA.SECONDARY_TABLE);
		secondaryTable.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		assertNotNull(resourceType.getAnnotation(0, JPA.SECONDARY_TABLE));
		assertEquals(2, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));
	}
	
	public void testAddSecondaryTable() throws Exception {
		ICompilationUnit cu = createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		String expected1 = "@SecondaryTables({";
		String expected2 = "@SecondaryTable(name = \"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames = { \"BAR\" })),";
		String expected3 = "@SecondaryTable(name = \"BAR\") })";
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.addAnnotation(1, JPA.SECONDARY_TABLE);
		secondaryTable.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected2 = "@SecondaryTable(name = \"BAZ\"),";
		expected3 = "@SecondaryTable(name = \"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames = { \"BAR\" })), @SecondaryTable(name = \"BAR\") })";
		secondaryTable = (SecondaryTableAnnotation) resourceType.addAnnotation(0, JPA.SECONDARY_TABLE);
		secondaryTable.setName("BAZ");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);

		Iterator<NestableAnnotation> secondaryTables = resourceType.getAnnotations(JPA.SECONDARY_TABLE).iterator();
		assertEquals("BAZ", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTableAnnotation) secondaryTables.next()).getName());

		assertNotNull(resourceType.getAnnotation(0, JPA.SECONDARY_TABLE));
		assertEquals(3, resourceType.getAnnotationsSize(JPA.SECONDARY_TABLE));
	}

	public void testRemoveSecondaryTableCopyExisting() throws Exception {
		ICompilationUnit cu = createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		String expected1 = "@SecondaryTables({";
		String expected2 = "@SecondaryTable(name = \"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames = { \"BAR\" })),";
		String expected3 = "@SecondaryTable(name = \"BAR\") })";
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.addAnnotation(1, JPA.SECONDARY_TABLE);
		secondaryTable.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected1 = "@SecondaryTable(name = \"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames = { \"BAR\" }))";
		resourceType.removeAnnotation(1, JPA.SECONDARY_TABLE);
		assertSourceContains(expected1, cu);
		assertSourceDoesNotContain("@SecondaryTables", cu);
	}

	
	public void testPkJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertEquals(0, table.getPkJoinColumnsSize());
	}
	
	public void testPkJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		table.addPkJoinColumn(0);
		table.addPkJoinColumn(1);
		
		assertEquals(2, table.getPkJoinColumnsSize());
	}
	
	public void testPkJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
				
		assertEquals(3, table.getPkJoinColumnsSize());
	}
	
	public void testAddPkJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		table.addPkJoinColumn(0).setName("FOO");
		table.addPkJoinColumn(1);
		table.addPkJoinColumn(0).setName("BAR");

		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAR\"),@PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn}))", cu);
	}
	
	public void testRemovePkJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		table.removePkJoinColumn(1);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAR\"), @PrimaryKeyJoinColumn(name = \"BAZ\")}))", cu);

		table.removePkJoinColumn(0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = @PrimaryKeyJoinColumn(name = \"BAZ\")))", cu);
		
		table.removePkJoinColumn(0);
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}
	
	public void testMovePkJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		PrimaryKeyJoinColumnAnnotation joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(2, 0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn(name = \"BAZ\"), @PrimaryKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\")}))", cu);
	}
	
	public void testMovePkJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);

		
		PrimaryKeyJoinColumnAnnotation joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(0, 2);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAZ\"), @PrimaryKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\"), @PrimaryKeyJoinColumn(name = \"FOO\")}))", cu);
	}
	
	public void testSetPkJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
				
		assertEquals(3, table.getPkJoinColumnsSize());
		
		PrimaryKeyJoinColumnAnnotation joinColumn = table.pkJoinColumnAt(0);
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"foo\"), @PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn(name = \"BAZ\")}))", cu);
	}

}
