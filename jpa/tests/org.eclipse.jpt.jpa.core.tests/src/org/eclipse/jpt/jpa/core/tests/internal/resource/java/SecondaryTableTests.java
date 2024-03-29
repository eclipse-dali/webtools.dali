/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

@SuppressWarnings("nls")
public class SecondaryTableTests extends JpaJavaResourceModelTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public SecondaryTableTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestSecondaryTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable");
			}
		});
	}
	
	private ICompilationUnit createTestSecondaryTableWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(name = \"" + TABLE_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestSecondaryTableWithSchema() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(schema = \"" + SCHEMA_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestSecondaryTableWithCatalog() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(catalog = \"" + CATALOG_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestSecondaryTableWithUniqueConstraints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.SECONDARY_TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})");
			}
		});
	}
	
	private ICompilationUnit createTestSecondaryTableWithPkJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.SECONDARY_TABLE, JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAR\"), @PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn(name = \"BAZ\")})");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertEquals(TABLE_NAME, table.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertNull(table.getName());
		assertNull(table.getCatalog());
		assertNull(table.getSchema());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertNull(table.getName());

		table.setName("Foo");
		assertEquals("Foo", table.getName());
		
		assertSourceContains("@SecondaryTable(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals(TABLE_NAME, table.getName());
		
		table.setName(null);
		assertNull(table.getName());
		
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertEquals(CATALOG_NAME, table.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertNull(table.getCatalog());

		table.setCatalog("Foo");
		assertEquals("Foo", table.getCatalog());
		
		assertSourceContains("@SecondaryTable(catalog = \"Foo\")", cu);
	}
	
	public void testSetCatalogNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals(CATALOG_NAME, table.getCatalog());
		
		table.setCatalog(null);
		assertNull(table.getCatalog());
		
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}
	
	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertEquals(SCHEMA_NAME, table.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertNotNull(table);
		assertNull(table.getSchema());

		table.setSchema("Foo");
		assertEquals("Foo", table.getSchema());
		
		assertSourceContains("@SecondaryTable(schema = \"Foo\")", cu);
	}
	
	public void testSetSchemaNull() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		assertEquals(SCHEMA_NAME, table.getSchema());
		
		table.setSchema(null);
		assertNull(table.getSchema());
		
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}


	public void testUniqueConstraints() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertEquals(0, table.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1);
		
		assertEquals(2, table.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
				
		assertEquals(3, table.getUniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);
		table.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(0, table.uniqueConstraintAt(2).getColumnNamesSize());

		assertSourceContains("@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = \"BAR\"),@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint})", cu);
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		table.removeUniqueConstraint(1);
		Iterator<UniqueConstraintAnnotation> uniqueConstraints = table.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().columnNameAt(0));
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
		assertSourceContains("@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})", cu);
		
		table.removeUniqueConstraint(0);
		uniqueConstraints = table.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
		assertSourceContains("@SecondaryTable(uniqueConstraints = @UniqueConstraint(columnNames = {\"BAZ\"}))", cu);
		
		table.removeUniqueConstraint(0);
		uniqueConstraints = table.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals("BAZ", secondaryTable.uniqueConstraintAt(2).columnNameAt(0));
		assertEquals(3, secondaryTable.getUniqueConstraintsSize());
		
		secondaryTable.moveUniqueConstraint(2, 0);
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("BAZ", secondaryTable.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(2).columnNameAt(0));
		assertEquals(3, secondaryTable.getUniqueConstraintsSize());
		assertSourceContains("@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"})})", cu);
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation secondaryTable = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals("BAZ", secondaryTable.uniqueConstraintAt(2).columnNameAt(0));
		assertEquals(3, secondaryTable.getUniqueConstraintsSize());
		
		secondaryTable.moveUniqueConstraint(0, 2);
		assertEquals("BAZ", secondaryTable.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(2).columnNameAt(0));
		assertEquals(3, secondaryTable.getUniqueConstraintsSize());
		assertSourceContains("@SecondaryTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"})})", cu);
	}
	
	
	public void testPkJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
				
		assertEquals(0, table.getPkJoinColumnsSize());
	}
	
	public void testPkJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		table.addPkJoinColumn(0);
		table.addPkJoinColumn(1);
		
		assertEquals(2, table.getPkJoinColumnsSize());
	}
	
	public void testPkJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
				
		assertEquals(3, table.getPkJoinColumnsSize());
	}
	
	public void testAddPkJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);

		
		table.addPkJoinColumn(0).setName("FOO");
		table.addPkJoinColumn(0);
		table.addPkJoinColumn(0).setName("BAR");//test adding a pkJoinColumn in front of 2 other joinColumns

		assertEquals("BAR", table.pkJoinColumnAt(0).getName());
		assertNull(table.pkJoinColumnAt(1).getName());
		assertEquals("FOO", table.pkJoinColumnAt(2).getName());

		assertEquals(3, table.getPkJoinColumnsSize());
		assertSourceContains("@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAR\"),@PrimaryKeyJoinColumn, @PrimaryKeyJoinColumn(name = \"FOO\")})", cu);
	}
	
	public void testRemovePkJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		
		table.removePkJoinColumn(1);
		assertSourceContains("@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAR\"), @PrimaryKeyJoinColumn(name = \"BAZ\")})", cu);

		table.removePkJoinColumn(0);
		assertSourceContains("@SecondaryTable(pkJoinColumns = @PrimaryKeyJoinColumn(name = \"BAZ\"))", cu);

		
		table.removePkJoinColumn(0);
		assertSourceDoesNotContain("@SecondaryTable(", cu);
	}
	
	public void testMovePkJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
		PrimaryKeyJoinColumnAnnotation joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(2, 0);
		assertSourceContains("@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn(name = \"BAZ\"), @PrimaryKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\")})", cu);
	}
	
	public void testMovePkJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);

		
		PrimaryKeyJoinColumnAnnotation joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(0, 2);
		assertSourceContains("@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"BAZ\"), @PrimaryKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\"), @PrimaryKeyJoinColumn(name = \"FOO\")})", cu);
	}
	
	public void testSetPkJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestSecondaryTableWithPkJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		SecondaryTableAnnotation table = (SecondaryTableAnnotation) resourceType.getAnnotation(0, JPA.SECONDARY_TABLE);
				
		assertEquals(3, table.getPkJoinColumnsSize());
		
		PrimaryKeyJoinColumnAnnotation joinColumn = table.pkJoinColumnAt(0);
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@SecondaryTable(pkJoinColumns = {@PrimaryKeyJoinColumn(name = \"foo\"), @PrimaryKeyJoinColumn(name = \"FOO\"), @PrimaryKeyJoinColumn(name = \"BAZ\")})", cu);
	}

}
