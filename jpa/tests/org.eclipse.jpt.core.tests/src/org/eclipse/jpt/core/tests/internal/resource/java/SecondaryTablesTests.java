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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.platform.generic.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTables;
import org.eclipse.jpt.core.internal.resource.java.UniqueConstraint;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class SecondaryTablesTests extends AnnotationTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public SecondaryTablesTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private void createUniqueConstraintAnnotation() throws Exception {
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
		
	}
	private void createSecondaryTableAnnotation() throws Exception {
		createUniqueConstraintAnnotation();
		this.createAnnotationAndMembers("SecondaryTable", "String name() default \"\"; " +
				"String catalog() default \"\"; " +
				"String schema() default \"\";" +
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@SecondaryTable(name=\"FOO\", catalog=\"BAR\", schema=\"BAZ\", uniqueConstraints=@UniqueConstraint(columnNames={\"BAR\"}))");
			}
		});
	}

	protected JavaResource buildParentResource(final IJpaPlatform jpaPlatform) {
		return new JavaResource() {
			public void updateFromJava(CompilationUnit astRoot) {
			}
			public IJpaPlatform jpaPlatform() {
				return jpaPlatform;
			}
		};
	}
	
	protected IJpaPlatform buildJpaPlatform() {
		return new GenericJpaPlatform();
	}

	protected JavaPersistentTypeResource buildJavaTypeResource(IType testType) {
		JavaPersistentTypeResource typeResource = new JavaPersistentTypeResourceImpl(buildParentResource(buildJpaPlatform()), new Type(testType, MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER));
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		return typeResource;
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
		
		ListIterator<UniqueConstraint> iterator = secondaryTable.uniqueConstraints();
		
		assertEquals(0, CollectionTools.size(iterator));
	}
	
	public void testUniqueConstraints2() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		
		secondaryTable.addUniqueConstraint(0);
		secondaryTable.addUniqueConstraint(1);
		secondaryTable.updateFromJava(JDTTools.buildASTRoot(testType));
		
		ListIterator<UniqueConstraint> iterator = secondaryTable.uniqueConstraints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testUniqueConstraints3() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
				
		ListIterator<UniqueConstraint> iterator = secondaryTable.uniqueConstraints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testAddUniqueConstraint() throws Exception {
		IType testType = this.createTestSecondaryTables();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		
		secondaryTable.addUniqueConstraint(0).addColumnName("FOO");
		secondaryTable.addUniqueConstraint(1);

		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints={@UniqueConstraint(columnNames=\"FOO\"),@UniqueConstraint}))");
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		
		secondaryTable.removeUniqueConstraint(1);
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints=@UniqueConstraint(columnNames={\"BAR\"}))");	
		
		secondaryTable.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@SecondaryTable");
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		
		secondaryTable.moveUniqueConstraint(0, 1);
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints={@UniqueConstraint, @UniqueConstraint(columnNames={\"BAR\"})}))");
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		IType testType = this.createTestSecondaryTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		SecondaryTables secondaryTables = (SecondaryTables) typeResource.annotation(JPA.SECONDARY_TABLES);
		SecondaryTable secondaryTable = secondaryTables.nestedAnnotationAt(0);
		
		secondaryTable.moveUniqueConstraint(1, 0);
		assertSourceContains("@SecondaryTables(@SecondaryTable(uniqueConstraints={@UniqueConstraint, @UniqueConstraint(columnNames={\"BAR\"})}))");
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

	public void testRemoveSecondaryTableCopyExisting() throws Exception {
		IType jdtType = createTestSecondaryTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		SecondaryTable secondaryTable = (SecondaryTable) typeResource.addAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		secondaryTable.setName("BAR");
		assertSourceContains("@SecondaryTables({@SecondaryTable(name=\"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames=\"BAR\")),@SecondaryTable(name=\"BAR\")})");
		
		typeResource.removeAnnotation(1, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
		assertSourceContains("@SecondaryTable(name=\"FOO\", catalog = \"BAR\", schema = \"BAZ\", uniqueConstraints = @UniqueConstraint(columnNames=\"BAR\"))");
	}

}
