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
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.JoinColumn;
import org.eclipse.jpt.core.internal.resource.java.JoinTable;
import org.eclipse.jpt.core.internal.resource.java.UniqueConstraint;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JoinTableTests extends AnnotationTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public JoinTableTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private void createJoinColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name() default \"\";");
	}
	
	private IType createTestJoinTable() throws Exception {
		this.createAnnotationAndMembers("JoinTable", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinTable");
			}
		});
	}
	
	private IType createTestJoinTableWithName() throws Exception {
		this.createAnnotationAndMembers("JoinTable", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinTable(name=\"" + TABLE_NAME + "\")");
			}
		});
	}
	
	private IType createTestJoinTableWithSchema() throws Exception {
		this.createAnnotationAndMembers("JoinTable", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinTable(schema=\"" + SCHEMA_NAME + "\")");
			}
		});
	}
	
	private IType createTestJoinTableWithCatalog() throws Exception {
		this.createAnnotationAndMembers("JoinTable", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinTable(catalog=\"" + CATALOG_NAME + "\")");
			}
		});
	}
	
	private IType createTestJoinTableWithUniqueConstraints() throws Exception {
		this.createAnnotationAndMembers("JoinTable", "UniqueConstraint[] uniqueConstraints() default {}");
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint})");
			}
		});
	}
	
	private IType createTestJoinTableWithJoinColumns() throws Exception {
		this.createAnnotationAndMembers("JoinTable", "JoinColumn[] joinColumns() default {}");
		this.createJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_TABLE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinTable(joinColumns={@JoinColumn(name=\"BAR\"), @JoinColumn})");
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
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
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
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testAddUniqueConstraint() throws Exception {
		IType testType = this.createTestJoinTable();
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);

		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint(columnNames=\"FOO\"),@UniqueConstraint})");
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		IType testType = this.createTestJoinTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.removeUniqueConstraint(1);
		assertSourceContains("@JoinTable(uniqueConstraints=@UniqueConstraint(columnNames={\"BAR\"}))");	

		table.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@JoinTable");
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		IType testType = this.createTestJoinTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.moveUniqueConstraint(0, 1);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint, @UniqueConstraint(columnNames={\"BAR\"})})");
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		IType testType = this.createTestJoinTableWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.moveUniqueConstraint(1, 0);
		assertSourceContains("@JoinTable(uniqueConstraints={@UniqueConstraint, @UniqueConstraint(columnNames={\"BAR\"})})");
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
		this.createJoinColumnAnnotation();
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
		this.createJoinColumnAnnotation();		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.addJoinColumn(0).setName("FOO");
		table.addJoinColumn(1);

		assertSourceContains("@JoinTable(joinColumns={@JoinColumn(name=\"FOO\"),@JoinColumn})");
	}
	
	public void testRemoveJoinColumn() throws Exception {
		IType testType = this.createTestJoinTableWithJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.removeJoinColumn(1);
		assertSourceContains("@JoinTable(joinColumns=@JoinColumn(name=\"BAR\"))");	

		table.removeJoinColumn(0);
		assertSourceDoesNotContain("@JoinTable");
	}
	
	public void testMoveJoinColumn() throws Exception {
		IType testType = this.createTestJoinTableWithJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.moveJoinColumn(0, 1);
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn, @JoinColumn(name=\"BAR\")})");
	}
	
	public void testMoveJoinColumn2() throws Exception {
		IType testType = this.createTestJoinTableWithJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinTable table = (JoinTable) attributeResource.annotation(JPA.JOIN_TABLE);
		
		table.moveJoinColumn(1, 0);
		assertSourceContains("@JoinTable(joinColumns={@JoinColumn, @JoinColumn(name=\"BAR\")})");
	}
}
