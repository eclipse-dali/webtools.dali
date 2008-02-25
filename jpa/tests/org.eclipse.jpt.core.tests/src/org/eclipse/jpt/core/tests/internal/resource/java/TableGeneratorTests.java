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
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class TableGeneratorTests extends JavaResourceModelTestCase {

	private static final String GENERATOR_NAME = "MY_GENERATOR";
	private static final String GENERATOR_TABLE = "MY_TABLE";
	private static final String GENERATOR_CATALOG = "MY_CATALOG";
	private static final String GENERATOR_SCHEMA = "MY_SCHEMA";
	private static final String GENERATOR_PK_COLUMN_NAME = "MY_PK_COLUMN_NAME";
	private static final String GENERATOR_VALUE_COLUMN_NAME = "MY_VALUE_COLUMN_NAME";
	private static final String GENERATOR_PK_COLUMN_VALUE = "MY_PK_COLUMN_VALUE";
	private static final Integer GENERATOR_ALLOCATION_SIZE = Integer.valueOf(5);
	private static final Integer GENERATOR_INITIAL_VALUE = Integer.valueOf(5);
	
	public TableGeneratorTests(String name) {
		super(name);
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
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
			public void appendTypeAnnotationTo(StringBuilder sb) {
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@TableGenerator(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
			}
		});
	}

	public void testTableGeneratorOnField() throws Exception {
		IType testType = this.createTestTableGeneratorOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertNotNull(tableGenerator);
	}
	
	public void testTableGeneratorOnType() throws Exception {
		IType testType = this.createTestTableGeneratorOnType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) typeResource.annotation(JPA.TABLE_GENERATOR);
		assertNotNull(tableGenerator);
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestTableGeneratorWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_NAME, tableGenerator.getName());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestTableGeneratorWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_TABLE, tableGenerator.getTable());
	}

	public void testSetTable() throws Exception {
		IType testType = this.createTestTableGeneratorWithTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_CATALOG, tableGenerator.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		IType testType = this.createTestTableGeneratorWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_SCHEMA, tableGenerator.getSchema());
	}

	public void testSetSchema() throws Exception {
		IType testType = this.createTestTableGeneratorWithSchema();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_NAME, tableGenerator.getPkColumnName());
	}

	public void testSetPkColumnName() throws Exception {
		IType testType = this.createTestTableGeneratorWithPkColumnName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_VALUE_COLUMN_NAME, tableGenerator.getValueColumnName());
	}

	public void testSetValueColumnName() throws Exception {
		IType testType = this.createTestTableGeneratorWithValueColumnName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_VALUE, tableGenerator.getPkColumnValue());
	}

	public void testSetPkColumnValue() throws Exception {
		IType testType = this.createTestTableGeneratorWithPkColumnValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, tableGenerator.getAllocationSize());
	}

	public void testSetAllocationSize() throws Exception {
		IType testType = this.createTestTableGeneratorWithAllocationSize();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, tableGenerator.getAllocationSize());
		
		tableGenerator.setAllocationSize(Integer.valueOf(500));
		assertEquals(Integer.valueOf(500), tableGenerator.getAllocationSize());
		
		assertSourceContains("@TableGenerator(allocationSize=500)");
		
		tableGenerator.setAllocationSize(null);
		
		assertSourceDoesNotContain("@TableGenerator");

		tableGenerator.setAllocationSize(Integer.valueOf(0));
		assertSourceContains("@TableGenerator(allocationSize=0)");
	}
	
	public void testGetInitialValue() throws Exception {
		IType testType = this.createTestTableGeneratorWithInitialValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, tableGenerator.getInitialValue());
	}

	public void testSetInitialValue() throws Exception {
		IType testType = this.createTestTableGeneratorWithInitialValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, tableGenerator.getInitialValue());
		
		tableGenerator.setInitialValue(Integer.valueOf(500));
		assertEquals(Integer.valueOf(500), tableGenerator.getInitialValue());
		
		assertSourceContains("@TableGenerator(initialValue=500)");
		
		tableGenerator.setInitialValue(null);
		
		assertSourceDoesNotContain("@TableGenerator");

		tableGenerator.setInitialValue(Integer.valueOf(0));
		assertSourceContains("@TableGenerator(initialValue=0)");
	}
	
	public void testUniqueConstraints() throws Exception {
		IType testType = this.createTestTableGeneratorOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		assertEquals(0, tableGenerator.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		IType testType = this.createTestTableGeneratorOnField();
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.addUniqueConstraint(0);
		tableGenerator.addUniqueConstraint(1);
		tableGenerator.updateFromJava(JDTTools.buildASTRoot(testType));
		
		assertEquals(2, tableGenerator.uniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		IType testType = this.createTestTableGeneratorWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
				
		assertEquals(3, tableGenerator.uniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		IType testType = this.createTestTableGeneratorOnField();
		this.createAnnotationAndMembers("UniqueConstraint", "String[] columnNames();");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.addUniqueConstraint(0).addColumnName("FOO");
		tableGenerator.addUniqueConstraint(1);
		tableGenerator.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", tableGenerator.uniqueConstraintAt(0).columnNames().next());
		assertEquals("FOO", tableGenerator.uniqueConstraintAt(1).columnNames().next());
		assertEquals(0, tableGenerator.uniqueConstraintAt(2).columnNamesSize());

		assertEquals(3, tableGenerator.uniqueConstraintsSize());
		assertSourceContains("@TableGenerator(uniqueConstraints={@UniqueConstraint(columnNames=\"BAR\"),@UniqueConstraint(columnNames=\"FOO\"), @UniqueConstraint})");
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		IType testType = this.createTestTableGeneratorWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.removeUniqueConstraint(1);
		assertSourceContains("@TableGenerator(uniqueConstraints={@UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"BAZ\"})})");
		
		tableGenerator.removeUniqueConstraint(0);
		assertSourceContains("@TableGenerator(uniqueConstraints=@UniqueConstraint(columnNames={\"BAZ\"}))");
		
		tableGenerator.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@TableGenerator");
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		IType testType = this.createTestTableGeneratorWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.moveUniqueConstraint(2, 0);
		assertSourceContains("@TableGenerator(uniqueConstraints={@UniqueConstraint(columnNames={\"FOO\"}), @UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"})})");
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		IType testType = this.createTestTableGeneratorWithUniqueConstraints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.moveUniqueConstraint(0, 2);
		assertSourceContains("@TableGenerator(uniqueConstraints={@UniqueConstraint(columnNames={\"BAZ\"}), @UniqueConstraint(columnNames={\"BAR\"}), @UniqueConstraint(columnNames={\"FOO\"})})");
	}
	
}
