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
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JoinColumn;
import org.eclipse.jpt.core.internal.resource.java.JoinTable;
import org.eclipse.jpt.core.internal.resource.java.UniqueConstraint;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JoinTableTests extends JavaResourceModelTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public JoinTableTests(String name) {
		super(name);
	}

	private void createJoinColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name() default \"\";" +
				"String referencedColumnName() default \"\";" +
				"boolean unique() default false;" +
				"boolean nullable() default true;" +
				"boolean insertable() default true;" +
				"boolean updatable() default true;" +
				"String columnDefinition() default \"\";" +
				"String table() default \"\";");
		
	}

	private void createUniqueConstraintAnnotation() throws Exception {
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
	}
	
	private void createJoinTableAnnotation() throws Exception {
		createJoinColumnAnnotation();
		createUniqueConstraintAnnotation();
		this.createAnnotationAndMembers("JoinTable", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\";JoinColumn[] joinColumns() default {}; JoinColumn[] inverseJoinColumns() default {}; UniqueConstraint[] uniqueConstraints() default {};");
	}
	
	private IType createTestJoinTable() throws Exception {
		createJoinTableAnnotation();
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
	
	private IType createTestJoinTableWithName() throws Exception {
		createJoinTableAnnotation();
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
	
	private IType createTestJoinTableWithSchema() throws Exception {
		createJoinTableAnnotation();
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
	
	private IType createTestJoinTableWithCatalog() throws Exception {
		createJoinTableAnnotation();
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
	
	private IType createTestJoinTableWithUniqueConstraints() throws Exception {
		createJoinTableAnnotation();
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
	
	private IType createTestJoinTableWithJoinColumns() throws Exception {
		createJoinTableAnnotation();
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
	
	private IType createTestJoinTableWithInverseJoinColumns() throws Exception {
		createJoinTableAnnotation();
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
		IType testType = this.createTestJoinTableWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertEquals(TABLE_NAME, table.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getName());
		assertNull(table.getCatalog());
		assertNull(table.getSchema());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getName());

		table.setName("Foo");
		assertEquals("Foo", table.getName());
		
		assertSourceContains("@JoinTable(name=\"Foo\")");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestJoinTableWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertEquals(TABLE_NAME, table.getName());
		
		table.setName(null);
		assertNull(table.getName());
		
		assertSourceDoesNotContain("@JoinTable");
	}

	public void testGetCatalog() throws Exception {
		IType testType = this.createTestJoinTableWithCatalog();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertEquals(CATALOG_NAME, table.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getCatalog());

		table.setCatalog("Foo");
		assertEquals("Foo", table.getCatalog());
		
		assertSourceContains("@JoinTable(catalog=\"Foo\")");
	}
	
	public void testSetCatalogNull() throws Exception {
		IType testType = this.createTestJoinTableWithCatalog();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertEquals(CATALOG_NAME, table.getCatalog());
		
		table.setCatalog(null);
		assertNull(table.getCatalog());
		
		assertSourceDoesNotContain("@JoinTable");
	}
	
	public void testGetSchema() throws Exception {
		IType testType = this.createTestJoinTableWithSchema();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertEquals(SCHEMA_NAME, table.getSchema());
	}

	public void testSetSchema() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertNotNull(table);
		assertNull(table.getSchema());

		table.setSchema("Foo");
		assertEquals("Foo", table.getSchema());
		
		assertSourceContains("@JoinTable(schema=\"Foo\")");
	}
	
	public void testSetSchemaNull() throws Exception {
		IType testType = this.createTestJoinTableWithSchema();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertEquals(SCHEMA_NAME, table.getSchema());
		
		table.setSchema(null);
		assertNull(table.getSchema());
		
		assertSourceDoesNotContain("@JoinTable");
	}
	
	public void testUniqueConstraints() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		ListIterator<UniqueConstraint> iterator = table.uniqueConstraints();
		
		assertEquals(0, CollectionTools.size(iterator));
	}
	
	public void testUniqueConstraints2() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);

		
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1);
		table.updateFromJava(JDTTools.buildASTRoot(testType));
		
		ListIterator<UniqueConstraint> iterator = table.uniqueConstraints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testUniqueConstraints3() throws Exception {
		IType testType = this.createTestJoinTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
				
		ListIterator<UniqueConstraint> iterator = table.uniqueConstraints();
		
		assertEquals(3, CollectionTools.size(iterator));
	}
	
	public void testAddUniqueConstraint() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);
		table.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNames().next());
		assertEquals(0, table.uniqueConstraintAt(2).columnNamesSize());

		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames=\"BAR\"),@UniqueConstraint(columnNames=\"FOO\"), @UniqueConstraint})");
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		IType testType = this.createTestJoinTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNames().next());
		assertEquals("BAZ", table.uniqueConstraintAt(2).columnNames().next());
		assertEquals(3, table.uniqueConstraintsSize());
		
		table.removeUniqueConstraint(1);
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals("BAZ", table.uniqueConstraintAt(1).columnNames().next());
		assertEquals(2, table.uniqueConstraintsSize());		
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
		
		table.removeUniqueConstraint(0);
		assertEquals("BAZ", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals(1, table.uniqueConstraintsSize());		
		assertSourceContains("@JoinTable(uniqueConstraints=@UniqueConstraint(columnNames={\"BAZ\"}))");
		
		table.removeUniqueConstraint(0);
		assertEquals(0, table.uniqueConstraintsSize());		
		assertSourceDoesNotContain("@JoinTable");
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		IType testType = this.createTestJoinTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
		
		table.moveUniqueConstraint(2, 0);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"})})");
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		IType testType = this.createTestJoinTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
		
		table.moveUniqueConstraint(0, 2);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"})})");
	}
	
	public void testJoinColumns() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		ListIterator<JoinColumn> iterator = table.joinColumns();
		
		assertEquals(0, CollectionTools.size(iterator));
	}
	
	public void testJoinColumns2() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);

		
		table.addJoinColumn(0);
		table.addJoinColumn(1);
		table.updateFromJava(JDTTools.buildASTRoot(testType));
		
		ListIterator<JoinColumn> iterator = table.joinColumns();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testJoinColumns3() throws Exception {
		IType testType = this.createTestJoinTableWithJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
				
		ListIterator<JoinColumn> iterator = table.joinColumns();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testAddJoinColumn() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.addJoinColumn(0).setName("FOO");
		table.addJoinColumn(1);
		table.addJoinColumn(0).setName("BAR");

		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"BAR\"),@JoinColumn(name=\"FOO\"), @JoinColumn})");
	}
	
	public void testRemoveJoinColumn() throws Exception {
		IType testType = this.createTestJoinTableWithJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		table.addJoinColumn(0).setName("FOO");
		
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertEquals("BAR", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertEquals(3, table.joinColumnsSize());
		
		table.removeJoinColumn(1);
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals(2, table.joinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn})");	

		table.removeJoinColumn(0);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals(1, table.joinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns=@JoinColumn)");	

		
		table.removeJoinColumn(0);
		assertEquals(0, table.joinColumnsSize());
		assertSourceDoesNotContain("@JoinTable");
	}
	
	public void testMoveJoinColumn() throws Exception {
		IType testType = this.createTestJoinTableWithJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		JoinColumn joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})");

		table.moveJoinColumn(2, 0);
		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals("FOO", table.joinColumnAt(2).getName());
		assertEquals(3, table.joinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn, @JoinColumn(name=\"FOO\")})");
	}
	
	public void testMoveJoinColumn2() throws Exception {
		IType testType = this.createTestJoinTableWithJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		JoinColumn joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})");


		table.moveJoinColumn(0, 2);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertEquals("BAR", table.joinColumnAt(2).getName());
		assertEquals(3, table.joinColumnsSize());
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn, @JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")})");
	}
	
	public void testSetJoinColumnName() throws Exception {
		IType testType = this.createTestJoinTableWithJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
				
		ListIterator<JoinColumn> iterator = table.joinColumns();
		assertEquals(2, CollectionTools.size(iterator));
		
		JoinColumn joinColumn = table.joinColumns().next();
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"foo\"), @JoinColumn})");
	}

	public void testInverseJoinColumns() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		ListIterator<JoinColumn> iterator = table.inverseJoinColumns();
		
		assertEquals(0, CollectionTools.size(iterator));
	}
	
	public void testInverseJoinColumns2() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);

		
		table.addInverseJoinColumn(0);
		table.addInverseJoinColumn(1);
		table.updateFromJava(JDTTools.buildASTRoot(testType));
		
		ListIterator<JoinColumn> iterator = table.inverseJoinColumns();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testInverseJoinColumns3() throws Exception {
		IType testType = this.createTestJoinTableWithInverseJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
				
		ListIterator<JoinColumn> iterator = table.inverseJoinColumns();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testAddInverseJoinColumn() throws Exception {
		IType testType = this.createTestJoinTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.addInverseJoinColumn(0).setName("FOO");
		table.addInverseJoinColumn(1);
		table.addInverseJoinColumn(0).setName("BAR");

		assertEquals("BAR", table.inverseJoinColumnAt(0).getName());
		assertEquals("FOO", table.inverseJoinColumnAt(1).getName());
		assertNull(table.inverseJoinColumnAt(2).getName());
		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn(name=\"BAR\"),@JoinColumn(name=\"FOO\"), @JoinColumn})");
	}
	
	public void testRemoveInverseJoinColumn() throws Exception {
		IType testType = this.createTestJoinTableWithInverseJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		table.addInverseJoinColumn(2).setName("FOO");
		
		Iterator<JoinColumn> inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		table.removeInverseJoinColumn(1);
		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn(name=\"BAR\"), @JoinColumn(name=\"FOO\")})");	
		inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());

		table.removeInverseJoinColumn(0);
		assertSourceContains("@JoinTable(inverseJoinColumns=@JoinColumn(name=\"FOO\"))");	
		inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		table.removeInverseJoinColumn(0);
		assertSourceDoesNotContain("@JoinTable");
	}
	
	public void testMoveInverseJoinColumn() throws Exception {
		IType testType = this.createTestJoinTableWithInverseJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		table.addInverseJoinColumn(0).setName("FOO");
		
		Iterator<JoinColumn> inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		
		table.moveInverseJoinColumn(2, 0);
		inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		
		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn(name=\"BAR\"), @JoinColumn, @JoinColumn(name=\"FOO\")})");
	}
	
	public void testMoveInverseJoinColumn2() throws Exception {
		IType testType = this.createTestJoinTableWithInverseJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		table.addInverseJoinColumn(1).setName("FOO");
		
		Iterator<JoinColumn> inverseJoinColumns = table.inverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		
		table.moveInverseJoinColumn(0, 2);
		inverseJoinColumns = table.inverseJoinColumns();
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn, @JoinColumn(name=\"BAR\"), @JoinColumn(name=\"FOO\")})");
	}
	
	public void testSetInverseJoinColumnName() throws Exception {
		IType testType = this.createTestJoinTableWithInverseJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
				
		ListIterator<JoinColumn> iterator = table.inverseJoinColumns();
		assertEquals(2, CollectionTools.size(iterator));
		
		JoinColumn joinColumn = table.inverseJoinColumns().next();
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@JoinTable(inverseJoinColumns={@JoinColumn(name=\"foo\"), @JoinColumn})");
	}

}
