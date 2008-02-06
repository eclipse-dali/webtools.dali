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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTables;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class SecondaryTablesTests extends JavaResourceModelTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public SecondaryTablesTests(String name) {
		super(name);
	}


	private void createUniqueConstraintAnnotation() throws Exception {
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
	}
	
	private void createPrimaryKeyJoinColumnAnnotation()  throws Exception {
		this.createAnnotationAndMembers("PrimaryKeyJoinColumn", "String name() default \"\"; String referencedColumnName() default \"\";String columnDefinition() default \"\";");
	}

	private void createSecondaryTableAnnotation() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		createUniqueConstraintAnnotation();
		this.createAnnotationAndMembers("SecondaryTable", "String name() default \"\"; " +
				"String catalog() default \"\"; " +
				"String schema() default \"\";" +
				"PrimaryKeyJoinColumn[] pkJoinColumns() default {};" +
				"UniqueConstraint[] uniqueConstraints() default {};");
		
	}
	private void createSecondaryTablesAnnotation() throws Exception {
		createSecondaryTableAnnotation();
		this.createAnnotationAndMembers("SecondaryTables", "SecondaryTable[] value()");		
	}
	
	private IType createTestSecondaryTables() throws Exception {
		createSecondaryTablesAnnotation();
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
	
	private IType createTestSecondaryTableWithName() throws Exception {
		createSecondaryTablesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(name=\"" + TABLE_NAME + "\"))");
			}
		});
	}
	
	private IType createTestSecondaryTableWithSchema() throws Exception {
		createSecondaryTablesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(schema=\"" + SCHEMA_NAME + "\"))");
			}
		});
	}
	private IType createTestSecondaryTableWithCatalog() throws Exception {
		createSecondaryTablesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(catalog=\"" + CATALOG_NAME + "\"))");
			}
		});
	}

	
	private IType createTestSecondaryTableWithUniqueConstraints() throws Exception {
		createSecondaryTablesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint}))");
			}
		});
	}
	
	private IType createTestSecondaryTable() throws Exception {
		createSecondaryTablesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTable(name=\"FOO\", catalog=\"BAR\", schema=\"BAZ\", uniqueConstraints=@UniqueConstraint(columnNames={\"BAR\"}))");
			}
		});
	}

	private IType createTestSecondaryTablesWithPkJoinColumns() throws Exception {
		createSecondaryTablesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLES, JPA.SECONDARY_TABLE, JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@SecondaryTables(@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"BAR\"), @PrimaryKeyJoinColumn(name=\"FOO\"), @PrimaryKeyJoinColumn(name=\"BAZ\")}))");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestSecondaryTableWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTable table = (SecondaryTable) typeResource.annotation(JPA.SECONDARY_TABLE);
		assertNull(table);
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		assertNotNull(secondaryTables);
		
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		assertEquals(TABLE_NAME, secondaryTable.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		assertNull(secondaryTable.getName());
		assertNull(secondaryTable.getCatalog());
		assertNull(secondaryTable.getSchema());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
	
		assertNull(secondaryTable.getName());

		secondaryTable.setName("Foo");
		assertEquals("Foo", secondaryTable.getName());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(name=\"Foo\"))");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestSecondaryTableWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		assertEquals(TABLE_NAME, secondaryTable.getName());
		
		secondaryTable.setName(null);
		assertNull(secondaryTable.getName());
		
		assertSourceDoesNotContain("@SecondaryTable");
	}

	public void testGetCatalog() throws Exception {
		IType testType = this.createTestSecondaryTableWithCatalog();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		assertEquals(CATALOG_NAME, secondaryTable.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		assertNull(secondaryTable.getCatalog());

		secondaryTable.setCatalog("Foo");
		assertEquals("Foo", secondaryTable.getCatalog());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(catalog=\"Foo\"))");
	}
	
	public void testSetCatalogNull() throws Exception {
		IType testType = this.createTestSecondaryTableWithCatalog();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		assertEquals(CATALOG_NAME, secondaryTable.getCatalog());
		
		secondaryTable.setCatalog(null);
		assertNull(secondaryTable.getCatalog());
		
		assertSourceDoesNotContain("@SecondaryTable");
	}
	
	public void testGetSchema() throws Exception {
		IType testType = this.createTestSecondaryTableWithSchema();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		assertNotNull(secondaryTable);
		assertEquals(SCHEMA_NAME, secondaryTable.getSchema());
	}

	public void testSetSchema() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		assertNull(secondaryTable.getSchema());

		secondaryTable.setSchema("Foo");
		assertEquals("Foo", secondaryTable.getSchema());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(schema=\"Foo\"))");
	}
	
	public void testSetSchemaNull() throws Exception {
		IType testType = this.createTestSecondaryTableWithSchema();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		assertEquals(SCHEMA_NAME, secondaryTable.getSchema());
		
		secondaryTable.setSchema(null);
		assertNull(secondaryTable.getSchema());
		
		assertSourceDoesNotContain("@SecondaryTable");
	}


	public void testUniqueConstraints() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		
		assertEquals(0, secondaryTable.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		
		secondaryTable.addUniqueConstraint(0);
		secondaryTable.addUniqueConstraint(1);
		secondaryTable.updateFromJava(JDTTools.buildASTRoot(testType));
		
		assertEquals(2, secondaryTable.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
				
		assertEquals(2, secondaryTable.uniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		secondaryTable.addUniqueConstraint(1);
		secondaryTable.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertEquals(0, secondaryTable.uniqueConstraintAt(2).columnNamesSize());

		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames=\"BAR\"),@UniqueConstraint(columnNames=\"FOO\"), @UniqueConstraint}))");
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		
		secondaryTable.removeUniqueConstraint(2);
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames=\"FOO\"), @UniqueConstraint(columnNames={\"BAR\"})})");	
		
		secondaryTable.removeUniqueConstraint(0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints=@UniqueConstraint(columnNames={\"BAR\"}))");	
		
		secondaryTable.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@SecondaryTable");
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
	
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertFalse(secondaryTable.uniqueConstraintAt(2).columnNames().hasNext());
		
		secondaryTable.moveUniqueConstraint(2, 0);
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertFalse(secondaryTable.uniqueConstraintAt(1).columnNames().hasNext());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(2).columnNames().next());
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint, @UniqueConstraint(columnNames=\"FOO\")}))");	
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(0).columnNames().next());
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertFalse(secondaryTable.uniqueConstraintAt(2).columnNames().hasNext());
		
		secondaryTable.moveUniqueConstraint(0, 2);
		assertFalse(secondaryTable.uniqueConstraintAt(0).columnNames().hasNext());
		assertEquals("FOO", secondaryTable.uniqueConstraintAt(1).columnNames().next());
		assertEquals("BAR", secondaryTable.uniqueConstraintAt(2).columnNames().next());
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints={@UniqueConstraint, @UniqueConstraint(columnNames=\"FOO\"), @UniqueConstraint(columnNames={\"BAR\"})}))");	
	}
	
	
	//  @Entity     				-->>    @Entity
	//	@SecondaryTable(name="FOO")			@SecondaryTables({@SecondaryTable(name="FOO"), @SecondaryTable(name="BAR")})	
	public void testAddSecondaryTableCopyExisting() throws Exception {
		IType jdtType = createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTable = (SecondaryTable) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames=\"BAR\")),@SecondaryTable(name=\"BAR\")})");
		
		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}
	
	public void testAddSecondaryTable() throws Exception {
		IType jdtType = createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTable = (SecondaryTable) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames=\"BAR\")),@SecondaryTable(name=\"BAR\")})");
		
		secondaryTable = (SecondaryTable) typeResource.addAnnotation(0, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("BAZ");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"BAZ\"),@SecondaryTable(name=\"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames=\"BAR\")), @SecondaryTable(name=\"BAR\")})");

		Iterator<JavaResource> secondaryTables = typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertEquals("BAZ", ((SecondaryTable) secondaryTables.next()).getName());
		assertEquals("FOO", ((SecondaryTable) secondaryTables.next()).getName());
		assertEquals("BAR", ((SecondaryTable) secondaryTables.next()).getName());

		assertNull(typeResource.annotation(JPA.SECONDARY_TABLE));
		assertNotNull(typeResource.annotation(JPA.SECONDARY_TABLES));
		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES)));
	}

	public void testRemoveSecondaryTableCopyExisting() throws Exception {
		IType jdtType = createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTable = (SecondaryTable) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames=\"BAR\")),@SecondaryTable(name=\"BAR\")})");
		
		typeResource.removeAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTable(name=\"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames=\"BAR\"))");
	}

	
	public void testPkJoinColumns() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		assertEquals(0, table.pkJoinColumnsSize());
	}
	
	public void testPkJoinColumns2() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		table.addPkJoinColumn(0);
		table.addPkJoinColumn(1);
		table.updateFromJava(JDTTools.buildASTRoot(testType));
		
		assertEquals(2, table.pkJoinColumnsSize());
	}
	
	public void testPkJoinColumns3() throws Exception {
		IType testType = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
				
		assertEquals(3, table.pkJoinColumnsSize());
	}
	
	public void testAddPkJoinColumn() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		table.addPkJoinColumn(0).setName("FOO");
		table.addPkJoinColumn(1);
		table.addPkJoinColumn(0).setName("BAR");

		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"BAR\"),@PrimaryKeyJoinColumn(name=\"FOO\"), @PrimaryKeyJoinColumn}))");
	}
	
	public void testRemovePkJoinColumn() throws Exception {
		IType testType = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		
		table.removePkJoinColumn(1);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"BAR\"), @PrimaryKeyJoinColumn(name=\"BAZ\")}))");	

		table.removePkJoinColumn(0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns=@PrimaryKeyJoinColumn(name=\"BAZ\")))");	
		
		table.removePkJoinColumn(0);
		assertSourceDoesNotContain("@SecondaryTable");
	}
	
	public void testMovePkJoinColumn() throws Exception {
		IType testType = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
		PrimaryKeyJoinColumn joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(2, 0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"FOO\"), @PrimaryKeyJoinColumn(name=\"BAZ\"), @PrimaryKeyJoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\")}))");
	}
	
	public void testMovePkJoinColumn2() throws Exception {
		IType testType = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();

		
		PrimaryKeyJoinColumn joinColumn = table.pkJoinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		table.movePkJoinColumn(0, 2);
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"BAZ\"), @PrimaryKeyJoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", columnDefinition = \"COLUMN_DEF\"), @PrimaryKeyJoinColumn(name=\"FOO\")}))");
	}
	
	public void testSetPkJoinColumnName() throws Exception {
		IType testType = this.createTestSecondaryTablesWithPkJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTable table = (SecondaryTable) typeResource.annotations(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES).next();
				
		assertEquals(3, table.pkJoinColumnsSize());
		
		PrimaryKeyJoinColumn joinColumn = table.pkJoinColumns().next();
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@SecondaryTables(@SecondaryTable(pkJoinColumns={@PrimaryKeyJoinColumn(name=\"foo\"), @PrimaryKeyJoinColumn(name=\"FOO\"), @PrimaryKeyJoinColumn(name=\"BAZ\")}))");
	}

}
