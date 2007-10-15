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
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.internal.resource.java.UniqueConstraint;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class TableGeneratorTests extends AnnotationTestCase {

	private static final String GENERATOR_NAME = "MY_GENERATOR";
	private static final String GENERATOR_TABLE = "MY_TABLE";
	private static final String GENERATOR_CATALOG = "MY_CATALOG";
	private static final String GENERATOR_SCHEMA = "MY_SCHEMA";
	private static final String GENERATOR_PK_COLUMN_NAME = "MY_PK_COLUMN_NAME";
	private static final String GENERATOR_VALUE_COLUMN_NAME = "MY_VALUE_COLUMN_NAME";
	private static final String GENERATOR_PK_COLUMN_VALUE = "MY_PK_COLUMN_VALUE";
	private static final int GENERATOR_ALLOCATION_SIZE = 5;
	private static final int GENERATOR_INITIAL_VALUE = 5;
	
	public TableGeneratorTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private void createTableGeneratorAnnotation() throws Exception {
		this.createAnnotationAndMembers("TableGenerator", "String name(); " +
			"String table() default \"\"" +
			"String catalog() default \"\"" +
			"String schema() default \"\"" +
			"String pkColumnName() default \"\"" +
			"String valueColumnName() default \"\"" +
			"String pkColumnValue() default \"\"" +
			"int initialValue() default 0" +
			"int allocationSize() default 50" + 
			"UniqueConstraint[] uniqueConstraints() default{}");
	}
	
	private IType createTestTableGeneratorOnField() throws Exception {
		createTableGeneratorAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@TableGenerator");
			}
		});
	}
	
	private IType createTestTableGeneratorOnType() throws Exception {
		createTableGeneratorAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE_GENERATOR);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@TableGenerator");
			}
		});
	}
	
	private IType createTestTableGeneratorWithName() throws Exception {
		return createTestTableGeneratorWithStringElement("name", GENERATOR_NAME);
	}
	
	private IType createTestTableGeneratorWithTable() throws Exception {
		return createTestTableGeneratorWithStringElement("table", GENERATOR_TABLE);
	}
	
	private IType createTestTableGeneratorWithCatalog() throws Exception {
		return createTestTableGeneratorWithStringElement("catalog", GENERATOR_CATALOG);
	}
	
	private IType createTestTableGeneratorWithSchema() throws Exception {
		return createTestTableGeneratorWithStringElement("schema", GENERATOR_SCHEMA);
	}
	private IType createTestTableGeneratorWithPkColumnName() throws Exception {
		return createTestTableGeneratorWithStringElement("pkColumnName", GENERATOR_PK_COLUMN_NAME);
	}
	
	private IType createTestTableGeneratorWithValueColumnName() throws Exception {
		return createTestTableGeneratorWithStringElement("valueColumnName", GENERATOR_VALUE_COLUMN_NAME);
	}
	
	private IType createTestTableGeneratorWithPkColumnValue() throws Exception {
		return createTestTableGeneratorWithStringElement("pkColumnValue", GENERATOR_PK_COLUMN_VALUE);
	}

	private IType createTestTableGeneratorWithStringElement(final String elementName, final String value) throws Exception {
		createTableGeneratorAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@TableGenerator(" + elementName + "=\"" + value + "\")");
			}
		});
	}
	
	private IType createTestTableGeneratorWithAllocationSize() throws Exception {
		return createTestTableGeneratorWithIntElement("allocationSize", GENERATOR_ALLOCATION_SIZE);
	}
	
	private IType createTestTableGeneratorWithInitialValue() throws Exception {
		return createTestTableGeneratorWithIntElement("initialValue", GENERATOR_INITIAL_VALUE);
	}
	
	private IType createTestTableGeneratorWithIntElement(final String elementName, final int value) throws Exception {
		createTableGeneratorAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@TableGenerator(" + elementName + "=" + value + ")");
			}
		});
	}

	private IType createTestTableGeneratorWithUniqueConstraints() throws Exception {
		createTableGeneratorAnnotation();
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.TABLE_GENERATOR, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@TableGenerator(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint})");
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

	public void testTableGeneratorOnField() throws Exception {
		IType testType = this.createTestTableGeneratorOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertNotNull(tableGenerator);
	}
	
	public void testTableGeneratorOnType() throws Exception {
		IType testType = this.createTestTableGeneratorOnType();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		TableGenerator tableGenerator = (TableGenerator) typeResource.annotation(JPA.TABLE_GENERATOR);
		assertNotNull(tableGenerator);
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestTableGeneratorWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_NAME, tableGenerator.getName());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestTableGeneratorWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_NAME, tableGenerator.getName());
		
		tableGenerator.setName("foo");
		assertEquals("foo", tableGenerator.getName());
		
		assertSourceContains("@TableGenerator(name=\"foo\")");
		
		tableGenerator.setName(null);
		assertNull(tableGenerator.getName());
		
		assertSourceDoesNotContain("@TableGenerator");
	}

	public void testGetTable() throws Exception {
		IType testType = this.createTestTableGeneratorWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_TABLE, tableGenerator.getTable());
	}

	public void testSetTable() throws Exception {
		IType testType = this.createTestTableGeneratorWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_TABLE, tableGenerator.getTable());
		
		tableGenerator.setTable("foo");
		assertEquals("foo", tableGenerator.getTable());
		
		assertSourceContains("@TableGenerator(table=\"foo\")");
		
		tableGenerator.setTable(null);
		assertNull(tableGenerator.getTable());
		
		assertSourceDoesNotContain("@TableGenerator");
	}

	public void testGetCatalog() throws Exception {
		IType testType = this.createTestTableGeneratorWithCatalog();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_CATALOG, tableGenerator.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		IType testType = this.createTestTableGeneratorWithCatalog();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_CATALOG, tableGenerator.getCatalog());
		
		tableGenerator.setCatalog("foo");
		assertEquals("foo", tableGenerator.getCatalog());
		
		assertSourceContains("@TableGenerator(catalog=\"foo\")");
		
		tableGenerator.setCatalog(null);
		assertNull(tableGenerator.getCatalog());
		
		assertSourceDoesNotContain("@TableGenerator");
	}

	public void testGetSchema() throws Exception {
		IType testType = this.createTestTableGeneratorWithSchema();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_SCHEMA, tableGenerator.getSchema());
	}

	public void testSetSchema() throws Exception {
		IType testType = this.createTestTableGeneratorWithSchema();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_SCHEMA, tableGenerator.getSchema());
		
		tableGenerator.setSchema("foo");
		assertEquals("foo", tableGenerator.getSchema());
		
		assertSourceContains("@TableGenerator(schema=\"foo\")");
		
		tableGenerator.setSchema(null);
		assertNull(tableGenerator.getSchema());
		
		assertSourceDoesNotContain("@TableGenerator");
	}

	public void testGetPkColumnName() throws Exception {
		IType testType = this.createTestTableGeneratorWithPkColumnName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_NAME, tableGenerator.getPkColumnName());
	}

	public void testSetPkColumnName() throws Exception {
		IType testType = this.createTestTableGeneratorWithPkColumnName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_NAME, tableGenerator.getPkColumnName());
		
		tableGenerator.setPkColumnName("foo");
		assertEquals("foo", tableGenerator.getPkColumnName());
		
		assertSourceContains("@TableGenerator(pkColumnName=\"foo\")");
		
		tableGenerator.setPkColumnName(null);
		assertNull(tableGenerator.getPkColumnName());
		
		assertSourceDoesNotContain("@TableGenerator");
	}
	
	public void testGetValueColumnName() throws Exception {
		IType testType = this.createTestTableGeneratorWithValueColumnName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_VALUE_COLUMN_NAME, tableGenerator.getValueColumnName());
	}

	public void testSetValueColumnName() throws Exception {
		IType testType = this.createTestTableGeneratorWithValueColumnName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_VALUE_COLUMN_NAME, tableGenerator.getValueColumnName());
		
		tableGenerator.setValueColumnName("foo");
		assertEquals("foo", tableGenerator.getValueColumnName());
		
		assertSourceContains("@TableGenerator(valueColumnName=\"foo\")");
		
		tableGenerator.setValueColumnName(null);
		assertNull(tableGenerator.getValueColumnName());
		
		assertSourceDoesNotContain("@TableGenerator");
	}

	public void testGetPkColumnValue() throws Exception {
		IType testType = this.createTestTableGeneratorWithPkColumnValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_VALUE, tableGenerator.getPkColumnValue());
	}

	public void testSetPkColumnValue() throws Exception {
		IType testType = this.createTestTableGeneratorWithPkColumnValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_VALUE, tableGenerator.getPkColumnValue());
		
		tableGenerator.setPkColumnValue("foo");
		assertEquals("foo", tableGenerator.getPkColumnValue());
		
		assertSourceContains("@TableGenerator(pkColumnValue=\"foo\")");
		
		tableGenerator.setPkColumnValue(null);
		assertNull(tableGenerator.getPkColumnValue());
		
		assertSourceDoesNotContain("@TableGenerator");
	}

	public void testGetAllocationSize() throws Exception {
		IType testType = this.createTestTableGeneratorWithAllocationSize();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, tableGenerator.getAllocationSize());
	}

	public void testSetAllocationSize() throws Exception {
		IType testType = this.createTestTableGeneratorWithAllocationSize();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, tableGenerator.getAllocationSize());
		
		tableGenerator.setAllocationSize(500);
		assertEquals(500, tableGenerator.getAllocationSize());
		
		assertSourceContains("@TableGenerator(allocationSize=500)");
		
		tableGenerator.setAllocationSize(-1);
		
		assertSourceDoesNotContain("@TableGenerator");

		tableGenerator.setAllocationSize(0);
		assertSourceContains("@TableGenerator(allocationSize=0)");
	}
	
	public void testGetInitialValue() throws Exception {
		IType testType = this.createTestTableGeneratorWithInitialValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, tableGenerator.getInitialValue());
	}

	public void testSetInitialValue() throws Exception {
		IType testType = this.createTestTableGeneratorWithInitialValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, tableGenerator.getInitialValue());
		
		tableGenerator.setInitialValue(500);
		assertEquals(500, tableGenerator.getInitialValue());
		
		assertSourceContains("@TableGenerator(initialValue=500)");
		
		tableGenerator.setInitialValue(-1);
		
		assertSourceDoesNotContain("@TableGenerator");

		tableGenerator.setInitialValue(0);
		assertSourceContains("@TableGenerator(initialValue=0)");

	}
	
	
	public void testUniqueConstraints() throws Exception {
		IType testType = this.createTestTableGeneratorOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		ListIterator<UniqueConstraint> iterator = tableGenerator.uniqueConstraints();
		
		assertEquals(0, CollectionTools.size(iterator));
	}
	
	public void testUniqueConstraints2() throws Exception {
		IType testType = this.createTestTableGeneratorOnField();
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.addUniqueConstraint(0);
		tableGenerator.addUniqueConstraint(1);
		tableGenerator.updateFromJava(JDTTools.buildASTRoot(testType));
		
		ListIterator<UniqueConstraint> iterator = tableGenerator.uniqueConstraints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testUniqueConstraints3() throws Exception {
		IType testType = this.createTestTableGeneratorWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
				
		ListIterator<UniqueConstraint> iterator = tableGenerator.uniqueConstraints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testAddUniqueConstraint() throws Exception {
		IType testType = this.createTestTableGeneratorOnField();
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.addUniqueConstraint(0).addColumnName("FOO");
		tableGenerator.addUniqueConstraint(1);

		assertSourceContains("@TableGenerator(uniqueConstraints={@UniqueConstraint(columnNames=\"FOO\"),@UniqueConstraint})");
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		IType testType = this.createTestTableGeneratorWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.removeUniqueConstraint(1);
		assertSourceContains("@TableGenerator(uniqueConstraints=@UniqueConstraint(columnNames={\"BAR\"}))");
		
		tableGenerator.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@TableGenerator");
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		IType testType = this.createTestTableGeneratorWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.moveUniqueConstraint(0, 1);
		assertSourceContains("@TableGenerator(uniqueConstraints={@UniqueConstraint, @UniqueConstraint(columnNames={\"BAR\"})})");
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		IType testType = this.createTestTableGeneratorWithUniqueConstraints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.moveUniqueConstraint(1, 0);
		assertSourceContains("@TableGenerator(uniqueConstraints={@UniqueConstraint, @UniqueConstraint(columnNames={\"BAR\"})})");
	}
	
}
