/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTablesAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class SecondaryTablesTests extends JavaResourceModelTestCase {
	
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLE);
		assertNull(table);
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		assertNotNull(secondaryTables);
		
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		assertEquals(TABLE_NAME, secondaryTable.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		assertNull(secondaryTable.getName());
		assertNull(secondaryTable.getCatalog());
		assertNull(secondaryTable.getSchema());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
	
		assertNull(secondaryTable.getName());

		secondaryTable.setName("Foo");
		assertEquals("Foo", secondaryTable.getName());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(name = \"Foo\"))", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		assertEquals(TABLE_NAME, secondaryTable.getName());
		
		secondaryTable.setName(null);
		assertNull(secondaryTable.getName());
		
		assertSourceDoesNotContain("@SecondaryTable", cu);
	}

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		assertEquals(CATALOG_NAME, secondaryTable.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		assertNull(secondaryTable.getCatalog());

		secondaryTable.setCatalog("Foo");
		assertEquals("Foo", secondaryTable.getCatalog());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(catalog = \"Foo\"))", cu);
	}
	
	public void testSetCatalogNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		assertEquals(CATALOG_NAME, secondaryTable.getCatalog());
		
		secondaryTable.setCatalog(null);
		assertNull(secondaryTable.getCatalog());
		
		assertSourceDoesNotContain("@SecondaryTable", cu);
	}
	
	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithSchema();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		assertNotNull(secondaryTable);
		assertEquals(SCHEMA_NAME, secondaryTable.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		assertNull(secondaryTable.getSchema());

		secondaryTable.setSchema("Foo");
		assertEquals("Foo", secondaryTable.getSchema());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(schema = \"Foo\"))", cu);
	}
	
	public void testSetSchemaNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithSchema();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		assertEquals(SCHEMA_NAME, secondaryTable.getSchema());
		
		secondaryTable.setSchema(null);
		assertNull(secondaryTable.getSchema());
		
		assertSourceDoesNotContain("@SecondaryTable", cu);
	}


	public void testUniqueConstraints() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		
		assertEquals(0, secondaryTable.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		
		secondaryTable.addUniqueConstraint(0);
		secondaryTable.addUniqueConstraint(1);
		
		assertEquals(2, secondaryTable.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
				
		assertEquals(2, secondaryTable.uniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		secondaryTable.addUniqueConstraint(1);
		secondaryTable.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertEquals(0, secondaryTable.uniqueConstraintAt(2).columnNamesSize());

		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = \"BAR\"),@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint}))", cu);
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		
		secondaryTable.removeUniqueConstraint(2);
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint(columnNames = {\"BAR\"})})", cu);
		
		secondaryTable.removeUniqueConstraint(0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = @UniqueConstraint(columnNames = {\"BAR\"}))", cu);
		
		secondaryTable.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@SecondaryTable", cu);
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
	
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertFalse(secondaryTable.uniqueConstraintAt(2).columnNames().hasNext());
		
		secondaryTable.moveUniqueConstraint(2, 0);
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertFalse(secondaryTable.uniqueConstraintAt(1).columnNames().hasNext());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(2).columnNames().next());
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint, @UniqueConstraint(columnNames = \"FOO\")}))", cu);
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTablesAnnotation secondaryTables = (SecondaryTablesAnnotation) typeResource.getAnnotation(JPA.SECONDARY_TABLES);
		SecondaryTableAnnotation secondaryTable = secondaryTables.getNestedAnnotations().iterator().next();
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertFalse(secondaryTable.uniqueConstraintAt(2).columnNames().hasNext());
		
		secondaryTable.moveUniqueConstraint(0, 2);
		assertFalse(secondaryTable.uniqueConstraintAt(0).columnNames().hasNext());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(2).columnNames().next());
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints = {@UniqueConstraint, @UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint(columnNames = {\"BAR\"})}))", cu);
	}
	
	
	//  @Entity     				-->>    @Entity
	//	@SecondaryTable(name="FOO")			@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})	
	public void testAddSecondaryTableCopyExisting() throws Exception {
		ICompilationUnit cu = createTestSecondaryTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"FOO\", schema = \"BAZ\", catalog = \"BAR\", uniqueConstraints = @UniqueConstraint(columnNames = \"BAR\")),@SecondaryTable(name = \"BAR\")})", cu);
		
		assertNull(typeResource.getAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	public void testAddSecondaryTable() throws Exception {
		ICompilationUnit cu = createTestSecondaryTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"FOO\", schema = \"BAZ\", catalog = \"BAR\", uniqueConstraints = @UniqueConstraint(columnNames = \"BAR\")),@SecondaryTable(name = \"BAR\")})", cu);
		
		secondaryTable = (SecondaryTableAnnotation) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"BAZ\"),@SecondaryTable(name = \"FOO\", schema = \"BAZ\", catalog = \"BAR\", uniqueConstraints = @UniqueConstraint(columnNames = \"BAR\")), @SecondaryTable(name = \"BAR\")})", cu);

		Iterator<NestableAnnotation> secondaryTables = typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertEquals("BAZ", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTableAnnotation) secondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTableAnnotation) secondaryTables.next()).getName());

		assertNull(typeResource.getAnnotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.getAnnotation(JPA.SECONDARY_TABLES));
		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}

	public void testRemoveSecondaryTableCopyExisting() throws Exception {
		ICompilationUnit cu = createTestSecondaryTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name = \"FOO\", schema = \"BAZ\", catalog = \"BAR\", uniqueConstraints = @UniqueConstraint(columnNames = \"BAR\")),@SecondaryTable(name = \"BAR\")})", cu);
		
		typeResource.removeAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTable(name = \"FOO\", schema = \"BAZ\", catalog = \"BAR\", uniqueConstraints = @UniqueConstraint(columnNames = \"BAR\"))", cu);
	}

	
	public void testPkJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		assertEquals(0, table.pkJoinColumnsSize());
	}
	
	public void testPkJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		table.addPkJoinColumn(0);
		table.addPkJoinColumn(1);
		
		assertEquals(2, table.pkJoinColumnsSize());
	}
	
	public void testPkJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
				
		assertEquals(3, table.pkJoinColumnsSize());
	}
	
	public void testAddPkJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTables();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		table.addPkJoinColumn(0).setName("FOO");
		table.addPkJoinColumn(1);
		table.addPkJoinColumn(0).setName("BAR");

		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAR\"),@PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn}))", cu);
	}
	
	public void testRemovePkJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		table.removePkJoinColumn(1);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAR\"), @PrimaryKeyJoinColumn(name = \"BAZ\")}))", cu);

		table.removePkJoinColumn(0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = @PrimaryKeyJoinColumn(name = \"BAZ\")))", cu);
		
		table.removePkJoinColumn(0);
		assertSourceDoesNotContain("@SecondaryTable", cu);
	}
	
	public void testMovePkJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		PrimaryKeyJoinColumnAnnotation joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(2, 0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn(name = \"BAZ\"), @PrimaryKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\")}))", cu);
	}
	
	public void testMovePkJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();

		
		PrimaryKeyJoinColumnAnnotation joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(0, 2);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAZ\"), @PrimaryKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\"), @PrimaryKeyJoinColumn(name = \"FOO\")}))", cu);
	}
	
	public void testSetPkJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
				
		assertEquals(3, table.pkJoinColumnsSize());
		
		PrimaryKeyJoinColumnAnnotation joinColumn = table.pkJoinColumns().next();
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"foo\"), @PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn(name = \"BAZ\")}))", cu);
	}

}
