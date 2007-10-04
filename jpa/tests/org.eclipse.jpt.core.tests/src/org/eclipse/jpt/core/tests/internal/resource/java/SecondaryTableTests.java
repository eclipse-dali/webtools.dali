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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.resource.java.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.Table;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class SecondaryTableTests extends AnnotationTestCase {
	
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	private static final String CATALOG_NAME = "MY_CATALOG";
	
	public SecondaryTableTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private IType createTestSecondaryTable() throws Exception {
		this.createAnnotationAndMembers("SecondaryTable", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@SecondaryTable");
			}
		});
	}
	
	private IType createTestSecondaryTableWithName() throws Exception {
		this.createAnnotationAndMembers("SecondaryTable", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@SecondaryTable(name=\"" + TABLE_NAME + "\")");
			}
		});
	}
	
	private IType createTestSecondaryTableWithSchema() throws Exception {
		this.createAnnotationAndMembers("SecondaryTable", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@SecondaryTable(schema=\"" + SCHEMA_NAME + "\")");
			}
		});
	}
	private IType createTestSecondaryTableWithCatalog() throws Exception {
		this.createAnnotationAndMembers("SecondaryTable", "String name() default \"\"; String catalog() default \"\"; String schema() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@SecondaryTable(catalog=\"" + CATALOG_NAME + "\")");
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
}
