/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JoinTableTests extends JavaResourceModelTestCase {
	
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
				sb.append("@JoinTable(name=\"" + TABLE_NAME + "\")");
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
				sb.append("@JoinTable(schema=\"" + SCHEMA_NAME + "\")");
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
				sb.append("@JoinTable(catalog=\"" + CATALOG_NAME + "\")");
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
				sb.append("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
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
				sb.append("@JoinTable(joinColumns={@JoinColumn(name=\"BAR\"), @JoinColumn})");
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
				sb.append("@JoinTable(inverseJoinColumns={@JoinColumn(name=\"BAR\"), @JoinColumn})");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertEquals(TABLE_NAME, table.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getName());
		assertNull(table.getCatalog());
		assertNull(table.getSchema());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getName());

		table.setName("Foo");
		assertEquals("Foo", table.getName());
		
		assertSourceContains("@JoinTable(name=\"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertEquals(TABLE_NAME, table.getName());
		
		table.setName(null);
		assertNull(table.getName());
		
		assertSourceDoesNotContain("@JoinTable", cu);
	}

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertEquals(CATALOG_NAME, table.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getCatalog());

		table.setCatalog("Foo");
		assertEquals("Foo", table.getCatalog());
		
		assertSourceContains("@JoinTable(catalog=\"Foo\")", cu);
	}
	
	public void testSetCatalogNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertEquals(CATALOG_NAME, table.getCatalog());
		
		table.setCatalog(null);
		assertNull(table.getCatalog());
		
		assertSourceDoesNotContain("@JoinTable", cu);
	}
	
	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithSchema();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertEquals(SCHEMA_NAME, table.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getSchema());

		table.setSchema("Foo");
		assertEquals("Foo", table.getSchema());
		
		assertSourceContains("@JoinTable(schema=\"Foo\")", cu);
	}
	
	public void testSetSchemaNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithSchema();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertEquals(SCHEMA_NAME, table.getSchema());
		
		table.setSchema(null);
		assertNull(table.getSchema());
		
		assertSourceDoesNotContain("@JoinTable", cu);
	}
	
	public void testUniqueConstraints() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		
		assertEquals(0, table.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);

		
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1);
		
		assertEquals(2, table.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(3, table.uniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);
		table.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNames().next());
		assertEquals(0, table.uniqueConstraintAt(2).columnNamesSize());

		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames=\"BAR\"),@UniqueConstraint(columnNames=\"FOO\"), @UniqueConstraint})", cu);
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNames().next());
		assertEquals("BAZ", table.uniqueConstraintAt(2).columnNames().next());
		assertEquals(3, table.uniqueConstraintsSize());
		
		table.removeUniqueConstraint(1);
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals("BAZ", table.uniqueConstraintAt(1).columnNames().next());
		assertEquals(2, table.uniqueConstraintsSize());		
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"BAZ\"})})", cu);
		
		table.removeUniqueConstraint(0);
		assertEquals("BAZ", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals(1, table.uniqueConstraintsSize());		
		assertSourceContains("@JoinTable(uniqueConstraints=@UniqueConstraint(columnNames={\"BAZ\"}))", cu);
		
		table.removeUniqueConstraint(0);
		assertEquals(0, table.uniqueConstraintsSize());		
		assertSourceDoesNotContain("@JoinTable", cu);
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"})})", cu);
		
		table.moveUniqueConstraint(2, 0);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"})})", cu);
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"})})", cu);
		
		table.moveUniqueConstraint(0, 2);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"})})", cu);
	}
	
	public void testJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(0, table.joinColumnsSize());
	}
	
	public void testJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);

		
		table.addJoinColumn(0);
		table.addJoinColumn(1);
		
		assertEquals(2, table.joinColumnsSize());
	}
	
	public void testJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(2, table.joinColumnsSize());
	}
	
	public void testAddJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		
		table.addJoinColumn(0).setName("FOO");
		table.addJoinColumn(1);
		table.addJoinColumn(0).setName("BAR");

		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"BAR\"),@JoinColumn(name=\"FOO\"), @JoinColumn})", cu);
	}
	
	public void testRemoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		table.addJoinColumn(0).setName("FOO");
		
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertEquals("BAR", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertEquals(3, table.joinColumnsSize());
		
		table.removeJoinColumn(1);
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals(2, table.joinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn})", cu);

		table.removeJoinColumn(0);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals(1, table.joinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns=@JoinColumn)", cu);

		
		table.removeJoinColumn(0);
		assertEquals(0, table.joinColumnsSize());
		assertSourceDoesNotContain("@JoinTable", cu);
	}
	
	public void testMoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);

		table.moveJoinColumn(2, 0);
		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals("FOO", table.joinColumnAt(2).getName());
		assertEquals(3, table.joinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn, @JoinColumn(name=\"FOO\")})", cu);
	}
	
	public void testMoveJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);


		table.moveJoinColumn(0, 2);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertEquals("BAR", table.joinColumnAt(2).getName());
		assertEquals(3, table.joinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn, @JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")})", cu);
	}
	
	public void testSetJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(2, table.joinColumnsSize());
		
		JoinColumnAnnotation joinColumn = table.joinColumns().next();
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"foo\"), @JoinColumn})", cu);
	}

	public void testInverseJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		
		assertEquals(0, table.inverseJoinColumnsSize());
	}
	
	public void testInverseJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);

		
		table.addInverseJoinColumn(0);
		table.addInverseJoinColumn(1);
		
		assertEquals(2, table.inverseJoinColumnsSize());
	}
	
	public void testInverseJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(2, table.inverseJoinColumnsSize());
	}
	
	public void testAddInverseJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		
		table.addInverseJoinColumn(0).setName("FOO");
		table.addInverseJoinColumn(1);
		table.addInverseJoinColumn(0).setName("BAR");

		assertEquals("BAR", table.inverseJoinColumnAt(0).getName());
		assertEquals("FOO", table.inverseJoinColumnAt(1).getName());
		assertNull(table.inverseJoinColumnAt(2).getName());
		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn(name=\"BAR\"),@JoinColumn(name=\"FOO\"), @JoinColumn})", cu);
	}
	
	public void testRemoveInverseJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		table.addInverseJoinColumn(2).setName("FOO");
		
		Iterator<JoinColumnAnnotation> inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		table.removeInverseJoinColumn(1);
		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn(name=\"BAR\"), @JoinColumn(name=\"FOO\")})", cu);
		inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());

		table.removeInverseJoinColumn(0);
		assertSourceContains("@JoinTable(inverseJoinColumns=@JoinColumn(name=\"FOO\"))", cu);
		inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		table.removeInverseJoinColumn(0);
		assertSourceDoesNotContain("@JoinTable", cu);
	}
	
	public void testMoveInverseJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		table.addInverseJoinColumn(0).setName("FOO");
		
		Iterator<JoinColumnAnnotation> inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		
		table.moveInverseJoinColumn(2, 0);
		inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		
		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn(name=\"BAR\"), @JoinColumn, @JoinColumn(name=\"FOO\")})", cu);
	}
	
	public void testMoveInverseJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
		table.addInverseJoinColumn(1).setName("FOO");
		
		Iterator<JoinColumnAnnotation> inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		
		table.moveInverseJoinColumn(0, 2);
		inverseJoinColumns = table.inverseJoinColumns();
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn, @JoinColumn(name=\"BAR\"), @JoinColumn(name=\"FOO\")})", cu);
	}
	
	public void testSetInverseJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestJoinTableWithInverseJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinTableAnnotation table = (JoinTableAnnotation) attributeResource.getSupportingAnnotation(JPA.JOIN_TABLE);
				
		assertEquals(2, table.inverseJoinColumnsSize());
		
		JoinColumnAnnotation joinColumn = table.inverseJoinColumns().next();
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn(name=\"foo\"), @JoinColumn})", cu);
	}

}
