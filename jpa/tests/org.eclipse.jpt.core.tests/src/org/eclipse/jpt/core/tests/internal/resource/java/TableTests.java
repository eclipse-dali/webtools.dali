/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class TableTests extends JavaResourceModelTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public TableTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Table");
			}
		});
	}
	
	private ICompilationUnit createTestTableWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Table(name = \"" + TABLE_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestTableWithSchema() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Table(schema = \"" + SCHEMA_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestTableWithCatalog() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Table(catalog = \"" + CATALOG_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestTableWithUniqueConstraints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestTableWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertEquals(TABLE_NAME, table.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertNull(table.getName());
		assertNull(table.getCatalog());
		assertNull(table.getSchema());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertNull(table.getName());

		table.setName("Foo");
		assertEquals("Foo", table.getName());
		
		assertSourceContains("@Table(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestTableWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertEquals(TABLE_NAME, table.getName());
		
		table.setName(null);
		assertNull(table.getName());
		
		assertSourceDoesNotContain("@Table", cu);
	}

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestTableWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertEquals(CATALOG_NAME, table.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertNull(table.getCatalog());

		table.setCatalog("Foo");
		assertEquals("Foo", table.getCatalog());
		
		assertSourceContains("@Table(catalog = \"Foo\")", cu);
	}
	
	public void testSetCatalogNull() throws Exception {
		ICompilationUnit cu = this.createTestTableWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertEquals(CATALOG_NAME, table.getCatalog());
		
		table.setCatalog(null);
		assertNull(table.getCatalog());
		
		assertSourceDoesNotContain("@Table", cu);
	}
	
	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestTableWithSchema();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertEquals(SCHEMA_NAME, table.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertNull(table.getSchema());

		table.setSchema("Foo");
		assertEquals("Foo", table.getSchema());
		
		assertSourceContains("@Table(schema = \"Foo\")", cu);
	}
	
	public void testSetSchemaNull() throws Exception {
		ICompilationUnit cu = this.createTestTableWithSchema();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		assertEquals(SCHEMA_NAME, table.getSchema());
		
		table.setSchema(null);
		assertNull(table.getSchema());
		
		assertSourceDoesNotContain("@Table", cu);
	}
	
	public void testUniqueConstraints() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		assertEquals(0, table.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1);
		
		assertEquals(2, table.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		ICompilationUnit cu = this.createTestTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
				
		assertEquals(3, table.uniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);
		table.addUniqueConstraint(0).addColumnName("BAR");
		
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNames().next());
		assertEquals(0, table.uniqueConstraintAt(2).columnNamesSize());
		
		assertSourceContains("@Table(uniqueConstraints = {@UniqueConstraint(columnNames = \"BAR\"),@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint})", cu);
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1).addColumnName("BAR");
		table.uniqueConstraintAt(1).addColumnName("BAZ");
		
		assertSourceContains("@Table(uniqueConstraints = {@UniqueConstraint,@UniqueConstraint(columnNames = { \"BAR\", \"BAZ\" }), @UniqueConstraint(columnNames = \"FOO\")})", cu);
		
		assertEquals("FOO", table.uniqueConstraintAt(2).columnNames().next());
		ListIterator<String> columnNames = table.uniqueConstraintAt(1).columnNames();
		assertEquals("BAR", columnNames.next());
		assertEquals("BAZ", columnNames.next());
	}	
	public void testRemoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		table.removeUniqueConstraint(1);
		assertSourceContains("@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})", cu);
		
		table.removeUniqueConstraint(0);
		assertSourceContains("@Table(uniqueConstraints = @UniqueConstraint(columnNames = {\"BAZ\"}))", cu);
		
		table.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@Table", cu);
	}
	
	public void testRemoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1).addColumnName("BAR");
		table.addUniqueConstraint(2).addColumnName("BAZ");
		assertSourceContains("@Table(uniqueConstraints = {@UniqueConstraint(columnNames = \"FOO\"),@UniqueConstraint(columnNames = \"BAR\"), @UniqueConstraint(columnNames = \"BAZ\")})", cu);
		
		table.removeUniqueConstraint(0);
		assertSourceContains("@Table(uniqueConstraints = {@UniqueConstraint(columnNames = \"BAR\"),@UniqueConstraint(columnNames = \"BAZ\")})", cu);
		
		table.removeUniqueConstraint(0);
		assertSourceContains("@Table(uniqueConstraints = @UniqueConstraint(columnNames = \"BAZ\"))", cu);
		
		table.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@Table", cu);
	}
	
	public void testRemoveUniqueConstraint3() throws Exception {
		ICompilationUnit cu = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1).addColumnName("BAR");
		table.addUniqueConstraint(2).addColumnName("BAZ");
		assertSourceContains("@Table(uniqueConstraints = {@UniqueConstraint(columnNames = \"FOO\"),@UniqueConstraint(columnNames = \"BAR\"), @UniqueConstraint(columnNames = \"BAZ\")})", cu);
		
		table.removeUniqueConstraint(2);
		assertSourceContains("@Table(uniqueConstraints = {@UniqueConstraint(columnNames = \"FOO\"),@UniqueConstraint(columnNames = \"BAR\")})", cu);
		
		table.removeUniqueConstraint(1);
		assertSourceContains("@Table(uniqueConstraints = @UniqueConstraint(columnNames = \"FOO\"))", cu);
		
		table.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@Table", cu);
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		table.moveUniqueConstraint(2, 0);
		assertSourceContains("@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"})})", cu);
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		table.moveUniqueConstraint(0, 2);
		assertSourceContains("@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"})})", cu);
	}
	
}
