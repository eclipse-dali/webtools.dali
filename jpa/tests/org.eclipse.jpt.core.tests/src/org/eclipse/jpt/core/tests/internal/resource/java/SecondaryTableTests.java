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
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.UniqueConstraint;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class SecondaryTableTests extends JavaResourceModelTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public SecondaryTableTests(String name) {
		super(name);
	}
	
	private void createUniqueConstraintAnnotation()  throws Exception {
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
	}
	
	private void createPrimaryKeyJoinColumnAnnotation()  throws Exception {
		this.createAnnotationAndMembers("PrimaryKeyJoinColumn", "String name() default \"\"; String referencedColumnName() default \"\";String columnDefinition() default \"\";");
	}

	private void createSecondaryTableAnnotation()  throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		createUniqueConstraintAnnotation();
		this.createAnnotationAndMembers("SecondaryTable", "String name() default \"\"; " +
				"String catalog() default \"\";" +
				"String schema() default \"\";" +
				"PrimaryKeyJoinColumn[] pkJoinColumns() default {};" +
				"UniqueConstraint[] uniqueConstraints() default {};");
	}
	
	private IType createTestSecondaryTable() throws Exception {
		createSecondaryTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable");
			}
		});
	}
	
	private IType createTestSecondaryTableWithName() throws Exception {
		createSecondaryTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(name=\"" + TABLE_NAME + "\")");
			}
		});
	}
	
	private IType createTestSecondaryTableWithSchema() throws Exception {
		createSecondaryTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(schema=\"" + SCHEMA_NAME + "\")");
			}
		});
	}
	private IType createTestSecondaryTableWithCatalog() throws Exception {
		createSecondaryTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(catalog=\"" + CATALOG_NAME + "\")");
			}
		});
	}

	
	private IType createTestSecondaryTableWithUniqueConstraints() throws Exception {
		createSecondaryTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
			}
		});
	}
	
	private IType createTestSecondaryTableWithPkJoinColumns() throws Exception {
		createSecondaryTableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"BAR\"), @PrimaryKeyJoinColumn(name=\"FOO\"), @PrimaryKeyJoinColumn(name=\"BAZ\")})");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestSecondaryTableWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertEquals(TABLE_NAME, table.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertNull(table.getName());
		assertNull(table.getCatalog());
		assertNull(table.getSchema());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertNull(table.getName());

		table.setName("Foo");
		assertEquals("Foo", table.getName());
		
		assertSourceContains("@SecondaryTable(name=\"Foo\")");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestSecondaryTableWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertEquals(TABLE_NAME, table.getName());
		
		table.setName(null);
		assertNull(table.getName());
		
		assertSourceDoesNotContain("@SecondaryTable");
	}

	public void testGetCatalog() throws Exception {
		IType testType = this.createTestSecondaryTableWithCatalog();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertEquals(CATALOG_NAME, table.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertNull(table.getCatalog());

		table.setCatalog("Foo");
		assertEquals("Foo", table.getCatalog());
		
		assertSourceContains("@SecondaryTable(catalog=\"Foo\")");
	}
	
	public void testSetCatalogNull() throws Exception {
		IType testType = this.createTestSecondaryTableWithCatalog();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertEquals(CATALOG_NAME, table.getCatalog());
		
		table.setCatalog(null);
		assertNull(table.getCatalog());
		
		assertSourceDoesNotContain("@SecondaryTable");
	}
	
	public void testGetSchema() throws Exception {
		IType testType = this.createTestSecondaryTableWithSchema();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertEquals(SCHEMA_NAME, table.getSchema());
	}

	public void testSetSchema() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertNull(table.getSchema());

		table.setSchema("Foo");
		assertEquals("Foo", table.getSchema());
		
		assertSourceContains("@SecondaryTable(schema=\"Foo\")");
	}
	
	public void testSetSchemaNull() throws Exception {
		IType testType = this.createTestSecondaryTableWithSchema();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertEquals(SCHEMA_NAME, table.getSchema());
		
		table.setSchema(null);
		assertNull(table.getSchema());
		
		assertSourceDoesNotContain("@SecondaryTable");
	}


	public void testUniqueConstraints() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		ListIterator<UniqueConstraint> iterator = table.uniqueConstraints();
		
		assertEquals(0, CollectionTools.size(iterator));
	}
	
	public void testUniqueConstraints2() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1);
		table.updateFromJava(JDTTools.buildASTRoot(testType));
		
		ListIterator<UniqueConstraint> iterator = table.uniqueConstraints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testUniqueConstraints3() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
				
		ListIterator<UniqueConstraint> iterator = table.uniqueConstraints();
		
		assertEquals(3, CollectionTools.size(iterator));
	}
	
	public void testAddUniqueConstraint() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);
		table.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", table.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNames().next());
		assertEquals(0, table.uniqueConstraintAt(2).columnNamesSize());

		assertSourceContains("@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames=\"BAR\"),@UniqueConstraint(columnNames=\"FOO\"), @UniqueConstraint})");
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		table.removeUniqueConstraint(1);
		Iterator<UniqueConstraint> uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		assertSourceContains("@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
		
		table.removeUniqueConstraint(0);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		assertSourceContains("@SecondaryTable(uniqueConstraints=@UniqueConstraint(columnNames={\"BAZ\"}))");
		
		table.removeUniqueConstraint(0);
		uniqueConstraints = table.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
		assertSourceDoesNotContain("@SecondaryTable");
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable secondaryTable = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertEquals("BAZ", secondaryTable.uniqueConstraintAt(2).columnNames().next());
		assertEquals(3, secondaryTable.uniqueConstraintsSize());
		
		secondaryTable.moveUniqueConstraint(0, 2);
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("BAZ", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(2).columnNames().next());
		assertEquals(3, secondaryTable.uniqueConstraintsSize());
		assertSourceContains("@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"})})");	
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable secondaryTable = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertEquals("BAZ", secondaryTable.uniqueConstraintAt(2).columnNames().next());
		assertEquals(3, secondaryTable.uniqueConstraintsSize());
		
		secondaryTable.moveUniqueConstraint(2, 0);
		assertEquals("BAZ", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(2).columnNames().next());
		assertEquals(3, secondaryTable.uniqueConstraintsSize());
		assertSourceContains("@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"})})");	
	}
	
	
	public void testPkJoinColumns() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		ListIterator<PrimaryKeyJoinColumn> iterator = table.pkJoinColumns();
		
		assertEquals(0, CollectionTools.size(iterator));
	}
	
	public void testPkJoinColumns2() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		table.addPkJoinColumn(0);
		table.addPkJoinColumn(1);
		table.updateFromJava(JDTTools.buildASTRoot(testType));
		
		ListIterator<PrimaryKeyJoinColumn> iterator = table.pkJoinColumns();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testPkJoinColumns3() throws Exception {
		IType testType = this.createTestSecondaryTableWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
				
		ListIterator<PrimaryKeyJoinColumn> iterator = table.pkJoinColumns();
		
		assertEquals(3, CollectionTools.size(iterator));
	}
	
	public void testAddPkJoinColumn() throws Exception {
		IType testType = this.createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);

		
		table.addPkJoinColumn(0).setName("FOO");
		table.addPkJoinColumn(0);
		table.addPkJoinColumn(0).setName("BAR");//test adding a pkJoinColumn in front of 2 other joinColumns

		assertEquals("BAR", table.pkJoinColumnAt(0).getName());
		assertNull(table.pkJoinColumnAt(1).getName());
		assertEquals("FOO", table.pkJoinColumnAt(2).getName());

		assertEquals(3, table.pkJoinColumnsSize());
		assertSourceContains("@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"BAR\"),@PrimaryKeyJoinColumn, @PrimaryKeyJoinColumn(name=\"FOO\")})");
	}
	
	public void testRemovePkJoinColumn() throws Exception {
		IType testType = this.createTestSecondaryTableWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		
		table.removePkJoinColumn(1);
		assertSourceContains("@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"BAR\"), @PrimaryKeyJoinColumn(name=\"BAZ\")})");	

		table.removePkJoinColumn(0);
		assertSourceContains("@SecondaryTable(pkJoinColumns=@PrimaryKeyJoinColumn(name=\"BAZ\"))");	

		
		table.removePkJoinColumn(0);
		assertSourceDoesNotContain("@SecondaryTable");
	}
	
	public void testMovePkJoinColumn() throws Exception {
		IType testType = this.createTestSecondaryTableWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		PrimaryKeyJoinColumn joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(0, 2);
		assertSourceContains("@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"FOO\"), @PrimaryKeyJoinColumn(name=\"BAZ\"), @PrimaryKeyJoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\")})");
	}
	
	public void testMovePkJoinColumn2() throws Exception {
		IType testType = this.createTestSecondaryTableWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);

		
		PrimaryKeyJoinColumn joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(2, 0);
		assertSourceContains("@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"BAZ\"), @PrimaryKeyJoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\"), @PrimaryKeyJoinColumn(name=\"FOO\")})");
	}
	
	public void testSetPkJoinColumnName() throws Exception {
		IType testType = this.createTestSecondaryTableWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
				
		ListIterator<PrimaryKeyJoinColumn> iterator = table.pkJoinColumns();
		assertEquals(3, CollectionTools.size(iterator));
		
		PrimaryKeyJoinColumn joinColumn = table.pkJoinColumns().next();
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"foo\"), @PrimaryKeyJoinColumn(name=\"FOO\"), @PrimaryKeyJoinColumn(name=\"BAZ\")})");
	}

}
