/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class TableTests extends JavaResourceModelTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public TableTests(String name) {
		super(name);
	}

	private void createTableAnnotation() throws Exception {
		createUniqueConstraintAnnotation();
		this.createAnnotationAndMembers("Table", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\"; UniqueConstraint[] uniqueConstraints() default {};");
	}
	
	private void createUniqueConstraintAnnotation() throws Exception {
		createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
	}
	
	private IType createTestTable() throws Exception {
		createTableAnnotation();
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
	
	private IType createTestTableWithName() throws Exception {
		createTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Table(name=\"" + TABLE_NAME + "\")");
			}
		});
	}
	
	private IType createTestTableWithSchema() throws Exception {
		createTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Table(schema=\"" + SCHEMA_NAME + "\")");
			}
		});
	}
	
	private IType createTestTableWithCatalog() throws Exception {
		createTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Table(catalog=\"" + CATALOG_NAME + "\")");
			}
		});
	}
	
	private IType createTestTableWithUniqueConstraints() throws Exception {
		createTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Table(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestTableWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertEquals(TABLE_NAME, table.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertNull(table.getName());
		assertNull(table.getCatalog());
		assertNull(table.getSchema());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertNull(table.getName());

		table.setName("Foo");
		assertEquals("Foo", table.getName());
		
		assertSourceContains("@Table(name=\"Foo\")");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestTableWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertEquals(TABLE_NAME, table.getName());
		
		table.setName(null);
		assertNull(table.getName());
		
		assertSourceDoesNotContain("@Table");
	}

	public void testGetCatalog() throws Exception {
		IType testType = this.createTestTableWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertEquals(CATALOG_NAME, table.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertNull(table.getCatalog());

		table.setCatalog("Foo");
		assertEquals("Foo", table.getCatalog());
		
		assertSourceContains("@Table(catalog=\"Foo\")");
	}
	
	public void testSetCatalogNull() throws Exception {
		IType testType = this.createTestTableWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertEquals(CATALOG_NAME, table.getCatalog());
		
		table.setCatalog(null);
		assertNull(table.getCatalog());
		
		assertSourceDoesNotContain("@Table");
	}
	
	public void testGetSchema() throws Exception {
		IType testType = this.createTestTableWithSchema();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertEquals(SCHEMA_NAME, table.getSchema());
	}

	public void testSetSchema() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertNotNull(table);
		assertNull(table.getSchema());

		table.setSchema("Foo");
		assertEquals("Foo", table.getSchema());
		
		assertSourceContains("@Table(schema=\"Foo\")");
	}
	
	public void testSetSchemaNull() throws Exception {
		IType testType = this.createTestTableWithSchema();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		assertEquals(SCHEMA_NAME, table.getSchema());
		
		table.setSchema(null);
		assertNull(table.getSchema());
		
		assertSourceDoesNotContain("@Table");
	}
	
	public void testUniqueConstraints() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		
		assertEquals(0, table.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1);
		
		assertEquals(2, table.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		IType testType = this.createTestTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
				
		assertEquals(3, table.uniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);
		table.addUniqueConstraint(0).addColumnName("BAR");
		
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNames().next());
		assertEquals(0, table.uniqueConstraintAt(2).columnNamesSize());
		
		assertSourceContains("@Table(uniqueConstraints={@UniqueConstraint(columnNames=\"BAR\"),@UniqueConstraint(columnNames=\"FOO\"), @UniqueConstraint})");
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1).addColumnName("BAR");
		table.uniqueConstraintAt(1).addColumnName("BAZ");
		
		assertSourceContains("@Table(uniqueConstraints={@UniqueConstraint,@UniqueConstraint(columnNames={ \"BAR\", \"BAZ\" }), @UniqueConstraint(columnNames=\"FOO\")})");
		
		assertEquals("FOO", table.uniqueConstraintAt(2).columnNames().next());
		ListIterator<String> columnNames = table.uniqueConstraintAt(1).columnNames();
		assertEquals("BAR", columnNames.next());
		assertEquals("BAZ", columnNames.next());
	}	
	public void testRemoveUniqueConstraint() throws Exception {
		IType testType = this.createTestTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		
		table.removeUniqueConstraint(1);
		assertSourceContains("@Table(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
		
		table.removeUniqueConstraint(0);
		assertSourceContains("@Table(uniqueConstraints=@UniqueConstraint(columnNames={\"BAZ\"}))");
		
		table.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@Table");
	}
	
	public void testRemoveUniqueConstraint2() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1).addColumnName("BAR");
		table.addUniqueConstraint(2).addColumnName("BAZ");
		assertSourceContains("@Table(uniqueConstraints={@UniqueConstraint(columnNames=\"FOO\"),@UniqueConstraint(columnNames=\"BAR\"), @UniqueConstraint(columnNames=\"BAZ\")})");
		
		table.removeUniqueConstraint(0);
		assertSourceContains("@Table(uniqueConstraints={@UniqueConstraint(columnNames=\"BAR\"),@UniqueConstraint(columnNames=\"BAZ\")})");
		
		table.removeUniqueConstraint(0);
		assertSourceContains("@Table(uniqueConstraints=@UniqueConstraint(columnNames=\"BAZ\"))");
		
		table.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@Table");
	}
	
	public void testRemoveUniqueConstraint3() throws Exception {
		IType testType = this.createTestTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1).addColumnName("BAR");
		table.addUniqueConstraint(2).addColumnName("BAZ");
		assertSourceContains("@Table(uniqueConstraints={@UniqueConstraint(columnNames=\"FOO\"),@UniqueConstraint(columnNames=\"BAR\"), @UniqueConstraint(columnNames=\"BAZ\")})");
		
		table.removeUniqueConstraint(2);
		assertSourceContains("@Table(uniqueConstraints={@UniqueConstraint(columnNames=\"FOO\"),@UniqueConstraint(columnNames=\"BAR\")})");
		
		table.removeUniqueConstraint(1);
		assertSourceContains("@Table(uniqueConstraints=@UniqueConstraint(columnNames=\"FOO\"))");
		
		table.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@Table");
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		IType testType = this.createTestTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		
		table.moveUniqueConstraint(2, 0);
		assertSourceContains("@Table(uniqueConstraints={@UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"})})");
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		IType testType = this.createTestTableWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		TableAnnotation table = (TableAnnotation) typeResource.getAnnotation(JPA.TABLE);
		
		table.moveUniqueConstraint(0, 2);
		assertSourceContains("@Table(uniqueConstraints={@UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"})})");
	}
	
}
