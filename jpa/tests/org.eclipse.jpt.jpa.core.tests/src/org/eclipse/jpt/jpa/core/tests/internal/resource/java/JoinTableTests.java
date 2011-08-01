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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

@SuppressWarnings("nls")
public class JoinTableTests extends JpaJavaResourceModelTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public JoinTableTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestJoinTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinTable");
			}
		});
	}
	
	private ICompilationUnit createTestJoinTableWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinTable(name = \"" + TABLE_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestJoinTableWithSchema() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinTable(schema = \"" + SCHEMA_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestJoinTableWithCatalog() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinTable(catalog = \"" + CATALOG_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestJoinTableWithUniqueConstraints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})");
			}
		});
	}
	
	private ICompilationUnit createTestJoinTableWithJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinTable(joinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn})");
			}
		});
	}
	
	private ICompilationUnit createTestJoinTableWithInverseJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinTable(inverseJoinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn})");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertEquals(TABLE_NAME, table.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getName());
		assertNull(table.getCatalog());
		assertNull(table.getSchema());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getName());

		table.setName("Foo");
		assertEquals("Foo", table.getName());
		
		assertSourceContains("@JoinTable(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertEquals(TABLE_NAME, table.getName());
		
		table.setName(null);
		assertNull(table.getName());
		
		assertSourceDoesNotContain("@JoinTable(", cu);
	}

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertEquals(CATALOG_NAME, table.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getCatalog());

		table.setCatalog("Foo");
		assertEquals("Foo", table.getCatalog());
		
		assertSourceContains("@JoinTable(catalog = \"Foo\")", cu);
	}
	
	public void testSetCatalogNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertEquals(CATALOG_NAME, table.getCatalog());
		
		table.setCatalog(null);
		assertNull(table.getCatalog());
		
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertEquals(SCHEMA_NAME, table.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getSchema());

		table.setSchema("Foo");
		assertEquals("Foo", table.getSchema());
		
		assertSourceContains("@JoinTable(schema = \"Foo\")", cu);
	}
	
	public void testSetSchemaNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertEquals(SCHEMA_NAME, table.getSchema());
		
		table.setSchema(null);
		assertNull(table.getSchema());
		
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testUniqueConstraints() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		
		assertEquals(0, table.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);

		
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1);
		
		assertEquals(2, table.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(3, table.getUniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);
		table.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(0, table.uniqueConstraintAt(2).getColumnNamesSize());

		assertSourceContains("@JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = \"BAR\"),@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint})", cu);
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals("BAZ", table.uniqueConstraintAt(2).columnNameAt(0));
		assertEquals(3, table.getUniqueConstraintsSize());
		
		table.removeUniqueConstraint(1);
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("BAZ", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(2, table.getUniqueConstraintsSize());		
		assertSourceContains("@JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})", cu);
		
		table.removeUniqueConstraint(0);
		assertEquals("BAZ", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals(1, table.getUniqueConstraintsSize());		
		assertSourceContains("@JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {\"BAZ\"}))", cu);
		
		table.removeUniqueConstraint(0);
		assertEquals(0, table.getUniqueConstraintsSize());		
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertSourceContains("@JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})", cu);
		
		table.moveUniqueConstraint(2, 0);
		assertSourceContains("@JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"})})", cu);
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		assertSourceContains("@JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})", cu);
		
		table.moveUniqueConstraint(0, 2);
		assertSourceContains("@JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"})})", cu);
	}
	
	public void testJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(0, table.getJoinColumnsSize());
	}
	
	public void testJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);

		
		table.addJoinColumn(0);
		table.addJoinColumn(1);
		
		assertEquals(2, table.getJoinColumnsSize());
	}
	
	public void testJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(2, table.getJoinColumnsSize());
	}
	
	public void testAddJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		
		table.addJoinColumn(0).setName("FOO");
		table.addJoinColumn(1);
		table.addJoinColumn(0).setName("BAR");

		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertSourceContains("@JoinTable(joinColumns = {@JoinColumn(name = \"BAR\"),@JoinColumn(name = \"FOO\"), @JoinColumn})", cu);
	}
	
	public void testRemoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		table.addJoinColumn(0).setName("FOO");
		
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertEquals("BAR", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertEquals(3, table.getJoinColumnsSize());
		
		table.removeJoinColumn(1);
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals(2, table.getJoinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn})", cu);

		table.removeJoinColumn(0);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals(1, table.getJoinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns = @JoinColumn)", cu);

		
		table.removeJoinColumn(0);
		assertEquals(0, table.getJoinColumnsSize());
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testMoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@JoinTable(joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);

		table.moveJoinColumn(2, 0);
		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals("FOO", table.joinColumnAt(2).getName());
		assertEquals(3, table.getJoinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns = {@JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn, @JoinColumn(name = \"FOO\")})", cu);
	}
	
	public void testMoveJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@JoinTable(joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);


		table.moveJoinColumn(0, 2);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertEquals("BAR", table.joinColumnAt(2).getName());
		assertEquals(3, table.getJoinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns = {@JoinColumn, @JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")})", cu);
	}
	
	public void testSetJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(2, table.getJoinColumnsSize());
		
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@JoinTable(joinColumns = {@JoinColumn(name = \"foo\"), @JoinColumn})", cu);
	}

	public void testInverseJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		
		assertEquals(0, table.getInverseJoinColumnsSize());
	}
	
	public void testInverseJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);

		
		table.addInverseJoinColumn(0);
		table.addInverseJoinColumn(1);
		
		assertEquals(2, table.getInverseJoinColumnsSize());
	}
	
	public void testInverseJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(2, table.getInverseJoinColumnsSize());
	}
	
	public void testAddInverseJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		
		table.addInverseJoinColumn(0).setName("FOO");
		table.addInverseJoinColumn(1);
		table.addInverseJoinColumn(0).setName("BAR");

		assertEquals("BAR", table.inverseJoinColumnAt(0).getName());
		assertEquals("FOO", table.inverseJoinColumnAt(1).getName());
		assertNull(table.inverseJoinColumnAt(2).getName());
		assertSourceContains("@JoinTable(inverseJoinColumns = {@JoinColumn(name = \"BAR\"),@JoinColumn(name = \"FOO\"), @JoinColumn})", cu);
	}
	
	public void testRemoveInverseJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		table.addInverseJoinColumn(2).setName("FOO");
		
		Iterator<JoinColumnAnnotation> inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		table.removeInverseJoinColumn(1);
		assertSourceContains("@JoinTable(inverseJoinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn(name = \"FOO\")})", cu);
		inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());

		table.removeInverseJoinColumn(0);
		assertSourceContains("@JoinTable(inverseJoinColumns = @JoinColumn(name = \"FOO\"))", cu);
		inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		table.removeInverseJoinColumn(0);
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testMoveInverseJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		table.addInverseJoinColumn(0).setName("FOO");
		
		Iterator<JoinColumnAnnotation> inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		
		table.moveInverseJoinColumn(2, 0);
		inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		
		assertSourceContains("@JoinTable(inverseJoinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn, @JoinColumn(name = \"FOO\")})", cu);
	}
	
	public void testMoveInverseJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
		table.addInverseJoinColumn(1).setName("FOO");
		
		Iterator<JoinColumnAnnotation> inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		
		table.moveInverseJoinColumn(0, 2);
		inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertSourceContains("@JoinTable(inverseJoinColumns = {@JoinColumn, @JoinColumn(name = \"BAR\"), @JoinColumn(name = \"FOO\")})", cu);
	}
	
	public void testSetInverseJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		JoinTableAnnotation table = (JoinTableAnnotation) resourceField.getAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(2, table.getInverseJoinColumnsSize());
		
		JoinColumnAnnotation joinColumn = table.inverseJoinColumnAt(0);
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@JoinTable(inverseJoinColumns = {@JoinColumn(name = \"foo\"), @JoinColumn})", cu);
	}

}
