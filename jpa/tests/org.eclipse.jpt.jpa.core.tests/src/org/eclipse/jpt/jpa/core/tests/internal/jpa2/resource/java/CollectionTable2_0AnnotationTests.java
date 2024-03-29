/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CollectionTableAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;

@SuppressWarnings("nls")
public class CollectionTable2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public CollectionTable2_0AnnotationTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestCollectionTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.COLLECTION_TABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@CollectionTable");
			}
		});
	}
	
	private ICompilationUnit createTestCollectionTableWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.COLLECTION_TABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@CollectionTable(name = \"" + TABLE_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestCollectionTableWithSchema() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.COLLECTION_TABLE);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@CollectionTable(schema = \"" + SCHEMA_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestCollectionTableWithCatalog() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.COLLECTION_TABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@CollectionTable(catalog = \"" + CATALOG_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestCollectionTableWithUniqueConstraints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.COLLECTION_TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@CollectionTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})");
			}
		});
	}
	
	private ICompilationUnit createTestCollectionTableWithJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.COLLECTION_TABLE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@CollectionTable(joinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn})");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertNotNull(table);
		assertEquals(TABLE_NAME, table.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertNotNull(table);
		assertNull(table.getName());
		assertNull(table.getCatalog());
		assertNull(table.getSchema());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertNotNull(table);
		assertNull(table.getName());

		table.setName("Foo");
		assertEquals("Foo", table.getName());
		
		assertSourceContains("@CollectionTable(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertEquals(TABLE_NAME, table.getName());
		
		table.setName(null);
		assertNull(table.getName());
		
		assertSourceDoesNotContain("@CollectionTable(", cu);
	}

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertNotNull(table);
		assertEquals(CATALOG_NAME, table.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertNotNull(table);
		assertNull(table.getCatalog());

		table.setCatalog("Foo");
		assertEquals("Foo", table.getCatalog());
		
		assertSourceContains("@CollectionTable(catalog = \"Foo\")", cu);
	}
	
	public void testSetCatalogNull() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertEquals(CATALOG_NAME, table.getCatalog());
		
		table.setCatalog(null);
		assertNull(table.getCatalog());
		
		assertSourceDoesNotContain("@CollectionTable(", cu);
	}
	
	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertNotNull(table);
		assertEquals(SCHEMA_NAME, table.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertNotNull(table);
		assertNull(table.getSchema());

		table.setSchema("Foo");
		assertEquals("Foo", table.getSchema());
		
		assertSourceContains("@CollectionTable(schema = \"Foo\")", cu);
	}
	
	public void testSetSchemaNull() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertEquals(SCHEMA_NAME, table.getSchema());
		
		table.setSchema(null);
		assertNull(table.getSchema());
		
		assertSourceDoesNotContain("@CollectionTable(", cu);
	}
	
	public void testUniqueConstraints() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		
		assertEquals(0, table.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);

		
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1);
		
		assertEquals(2, table.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
				
		assertEquals(3, table.getUniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);
		table.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(0, table.uniqueConstraintAt(2).getColumnNamesSize());

		assertSourceContains("@CollectionTable(uniqueConstraints = {@UniqueConstraint(columnNames = \"BAR\"),@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint})", cu);
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals("BAZ", table.uniqueConstraintAt(2).columnNameAt(0));
		assertEquals(3, table.getUniqueConstraintsSize());
		
		table.removeUniqueConstraint(1);
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("BAZ", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(2, table.getUniqueConstraintsSize());		
		assertSourceContains("@CollectionTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})", cu);
		
		table.removeUniqueConstraint(0);
		assertEquals("BAZ", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals(1, table.getUniqueConstraintsSize());		
		assertSourceContains("@CollectionTable(uniqueConstraints = @UniqueConstraint(columnNames = {\"BAZ\"}))", cu);
		
		table.removeUniqueConstraint(0);
		assertEquals(0, table.getUniqueConstraintsSize());		
		assertSourceDoesNotContain("@CollectionTable(", cu);
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertSourceContains("@CollectionTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})", cu);
		
		table.moveUniqueConstraint(2, 0);
		assertSourceContains("@CollectionTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"})})", cu);
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		assertSourceContains("@CollectionTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})", cu);
		
		table.moveUniqueConstraint(0, 2);
		assertSourceContains("@CollectionTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"})})", cu);
	}
	
	public void testJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
				
		assertEquals(0, table.getJoinColumnsSize());
	}
	
	public void testJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);

		
		table.addJoinColumn(0);
		table.addJoinColumn(1);
		
		assertEquals(2, table.getJoinColumnsSize());
	}
	
	public void testJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
				
		assertEquals(2, table.getJoinColumnsSize());
	}
	
	public void testAddJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		
		table.addJoinColumn(0).setName("FOO");
		table.addJoinColumn(1);
		table.addJoinColumn(0).setName("BAR");

		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertSourceContains("@CollectionTable(joinColumns = {@JoinColumn(name = \"BAR\"),@JoinColumn(name = \"FOO\"), @JoinColumn})", cu);
	}
	
	public void testRemoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		table.addJoinColumn(0).setName("FOO");
		
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertEquals("BAR", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertEquals(3, table.getJoinColumnsSize());
		
		table.removeJoinColumn(1);
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals(2, table.getJoinColumnsSize());
		assertSourceContains("@CollectionTable(joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn})", cu);

		table.removeJoinColumn(0);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals(1, table.getJoinColumnsSize());
		assertSourceContains("@CollectionTable(joinColumns = @JoinColumn)", cu);

		
		table.removeJoinColumn(0);
		assertEquals(0, table.getJoinColumnsSize());
		assertSourceDoesNotContain("@CollectionTable(", cu);
	}
	
	public void testMoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@CollectionTable(joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);

		table.moveJoinColumn(2, 0);
		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals("FOO", table.joinColumnAt(2).getName());
		assertEquals(3, table.getJoinColumnsSize());
		assertSourceContains("@CollectionTable(joinColumns = {@JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn, @JoinColumn(name = \"FOO\")})", cu);
	}
	
	public void testMoveJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
		
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@CollectionTable(joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);


		table.moveJoinColumn(0, 2);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertEquals("BAR", table.joinColumnAt(2).getName());
		assertEquals(3, table.getJoinColumnsSize());
		assertSourceContains("@CollectionTable(joinColumns = {@JoinColumn, @JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")})", cu);
	}
	
	public void testSetJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestCollectionTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		CollectionTableAnnotation2_0 table = (CollectionTableAnnotation2_0) resourceField.getAnnotation(JPA2_0.COLLECTION_TABLE);
				
		assertEquals(2, table.getJoinColumnsSize());
		
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@CollectionTable(joinColumns = {@JoinColumn(name = \"foo\"), @JoinColumn})", cu);
	}
}
