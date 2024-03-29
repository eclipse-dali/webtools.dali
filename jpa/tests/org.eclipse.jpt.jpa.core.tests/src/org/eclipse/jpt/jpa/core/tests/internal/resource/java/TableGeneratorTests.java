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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;

@SuppressWarnings("nls")
public class TableGeneratorTests extends JpaJavaResourceModelTestCase {

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
	
	private ICompilationUnit createTestTableGeneratorOnField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.TABLE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@TableGenerator");
			}
		});
	}
	
	private ICompilationUnit createTestTableGeneratorOnType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.TABLE_GENERATOR);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@TableGenerator");
			}
		});
	}
	
	private ICompilationUnit createTestTableGeneratorWithName() throws Exception {
		return createTestTableGeneratorWithStringElement("name", GENERATOR_NAME);
	}
	
	private ICompilationUnit createTestTableGeneratorWithTable() throws Exception {
		return createTestTableGeneratorWithStringElement("table", GENERATOR_TABLE);
	}
	
	private ICompilationUnit createTestTableGeneratorWithCatalog() throws Exception {
		return createTestTableGeneratorWithStringElement("catalog", GENERATOR_CATALOG);
	}
	
	private ICompilationUnit createTestTableGeneratorWithSchema() throws Exception {
		return createTestTableGeneratorWithStringElement("schema", GENERATOR_SCHEMA);
	}
	private ICompilationUnit createTestTableGeneratorWithPkColumnName() throws Exception {
		return createTestTableGeneratorWithStringElement("pkColumnName", GENERATOR_PK_COLUMN_NAME);
	}
	
	private ICompilationUnit createTestTableGeneratorWithValueColumnName() throws Exception {
		return createTestTableGeneratorWithStringElement("valueColumnName", GENERATOR_VALUE_COLUMN_NAME);
	}
	
	private ICompilationUnit createTestTableGeneratorWithPkColumnValue() throws Exception {
		return createTestTableGeneratorWithStringElement("pkColumnValue", GENERATOR_PK_COLUMN_VALUE);
	}

	private ICompilationUnit createTestTableGeneratorWithStringElement(final String elementName, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.TABLE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@TableGenerator(" + elementName + " = \"" + value + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestTableGeneratorWithAllocationSize() throws Exception {
		return createTestTableGeneratorWithIntElement("allocationSize", GENERATOR_ALLOCATION_SIZE.intValue());
	}
	
	private ICompilationUnit createTestTableGeneratorWithInitialValue() throws Exception {
		return createTestTableGeneratorWithIntElement("initialValue", GENERATOR_INITIAL_VALUE.intValue());
	}
	
	private ICompilationUnit createTestTableGeneratorWithIntElement(final String elementName, final int value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.TABLE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@TableGenerator(" + elementName + " = " + value + ")");
			}
		});
	}

	private ICompilationUnit createTestTableGeneratorWithUniqueConstraints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.TABLE_GENERATOR, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@TableGenerator(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})");
			}
		});
	}

	public void testTableGeneratorOnField() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertNotNull(tableGenerator);
	}
	
	public void testTableGeneratorOnType() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorOnType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceType.getAnnotation(JPA.TABLE_GENERATOR);
		assertNotNull(tableGenerator);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_NAME, tableGenerator.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_NAME, tableGenerator.getName());
		
		tableGenerator.setName("foo");
		assertEquals("foo", tableGenerator.getName());
		
		assertSourceContains("@TableGenerator(name = \"foo\")", cu);
		
		tableGenerator.setName(null);
		assertNull(tableGenerator.getName());
		
		assertSourceDoesNotContain("@TableGenerator(", cu);
	}

	public void testGetTable() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_TABLE, tableGenerator.getTable());
	}

	public void testSetTable() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_TABLE, tableGenerator.getTable());
		
		tableGenerator.setTable("foo");
		assertEquals("foo", tableGenerator.getTable());
		
		assertSourceContains("@TableGenerator(table = \"foo\")", cu);
		
		tableGenerator.setTable(null);
		assertNull(tableGenerator.getTable());
		
		assertSourceDoesNotContain("@TableGenerator(", cu);
	}

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_CATALOG, tableGenerator.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_CATALOG, tableGenerator.getCatalog());
		
		tableGenerator.setCatalog("foo");
		assertEquals("foo", tableGenerator.getCatalog());
		
		assertSourceContains("@TableGenerator(catalog = \"foo\")", cu);
		
		tableGenerator.setCatalog(null);
		assertNull(tableGenerator.getCatalog());
		
		assertSourceDoesNotContain("@TableGenerator(", cu);
	}

	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_SCHEMA, tableGenerator.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_SCHEMA, tableGenerator.getSchema());
		
		tableGenerator.setSchema("foo");
		assertEquals("foo", tableGenerator.getSchema());
		
		assertSourceContains("@TableGenerator(schema = \"foo\")", cu);
		
		tableGenerator.setSchema(null);
		assertNull(tableGenerator.getSchema());
		
		assertSourceDoesNotContain("@TableGenerator(", cu);
	}

	public void testGetPkColumnName() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithPkColumnName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_NAME, tableGenerator.getPkColumnName());
	}

	public void testSetPkColumnName() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithPkColumnName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_NAME, tableGenerator.getPkColumnName());
		
		tableGenerator.setPkColumnName("foo");
		assertEquals("foo", tableGenerator.getPkColumnName());
		
		assertSourceContains("@TableGenerator(pkColumnName = \"foo\")", cu);
		
		tableGenerator.setPkColumnName(null);
		assertNull(tableGenerator.getPkColumnName());
		
		assertSourceDoesNotContain("@TableGenerator(", cu);
	}
	
	public void testGetValueColumnName() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithValueColumnName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_VALUE_COLUMN_NAME, tableGenerator.getValueColumnName());
	}

	public void testSetValueColumnName() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithValueColumnName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_VALUE_COLUMN_NAME, tableGenerator.getValueColumnName());
		
		tableGenerator.setValueColumnName("foo");
		assertEquals("foo", tableGenerator.getValueColumnName());
		
		assertSourceContains("@TableGenerator(valueColumnName = \"foo\")", cu);
		
		tableGenerator.setValueColumnName(null);
		assertNull(tableGenerator.getValueColumnName());
		
		assertSourceDoesNotContain("@TableGenerator(", cu);
	}

	public void testGetPkColumnValue() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithPkColumnValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_VALUE, tableGenerator.getPkColumnValue());
	}

	public void testSetPkColumnValue() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithPkColumnValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_PK_COLUMN_VALUE, tableGenerator.getPkColumnValue());
		
		tableGenerator.setPkColumnValue("foo");
		assertEquals("foo", tableGenerator.getPkColumnValue());
		
		assertSourceContains("@TableGenerator(pkColumnValue = \"foo\")", cu);
		
		tableGenerator.setPkColumnValue(null);
		assertNull(tableGenerator.getPkColumnValue());
		
		assertSourceDoesNotContain("@TableGenerator(", cu);
	}

	public void testGetAllocationSize() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithAllocationSize();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, tableGenerator.getAllocationSize());
	}

	public void testSetAllocationSize() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithAllocationSize();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, tableGenerator.getAllocationSize());
		
		tableGenerator.setAllocationSize(Integer.valueOf(500));
		assertEquals(Integer.valueOf(500), tableGenerator.getAllocationSize());
		
		assertSourceContains("@TableGenerator(allocationSize = 500)", cu);
		
		tableGenerator.setAllocationSize(null);
		
		assertSourceDoesNotContain("@TableGenerator(", cu);

		tableGenerator.setAllocationSize(Integer.valueOf(0));
		assertSourceContains("@TableGenerator(allocationSize = 0)", cu);
	}
	
	public void testGetInitialValue() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithInitialValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, tableGenerator.getInitialValue());
	}

	public void testSetInitialValue() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithInitialValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, tableGenerator.getInitialValue());
		
		tableGenerator.setInitialValue(Integer.valueOf(500));
		assertEquals(Integer.valueOf(500), tableGenerator.getInitialValue());
		
		assertSourceContains("@TableGenerator(initialValue = 500)", cu);
		
		tableGenerator.setInitialValue(null);
		
		assertSourceDoesNotContain("@TableGenerator(", cu);

		tableGenerator.setInitialValue(Integer.valueOf(0));
		assertSourceContains("@TableGenerator(initialValue = 0)", cu);
	}
	
	public void testUniqueConstraints() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		
		assertEquals(0, tableGenerator.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints2() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.addUniqueConstraint(0);
		tableGenerator.addUniqueConstraint(1);
		
		assertEquals(2, tableGenerator.getUniqueConstraintsSize());
	}
	
	public void testUniqueConstraints3() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
				
		assertEquals(3, tableGenerator.getUniqueConstraintsSize());
	}
	
	public void testAddUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.addUniqueConstraint(0).addColumnName("FOO");
		tableGenerator.addUniqueConstraint(1);
		tableGenerator.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", tableGenerator.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", tableGenerator.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(0, tableGenerator.uniqueConstraintAt(2).getColumnNamesSize());

		assertEquals(3, tableGenerator.getUniqueConstraintsSize());
		assertSourceContains("@TableGenerator(uniqueConstraints = {@UniqueConstraint(columnNames = \"BAR\"),@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint})", cu);
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.removeUniqueConstraint(1);
		assertSourceContains("@TableGenerator(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"BAZ\"})})", cu);
		
		tableGenerator.removeUniqueConstraint(0);
		assertSourceContains("@TableGenerator(uniqueConstraints = @UniqueConstraint(columnNames = {\"BAZ\"}))", cu);
		
		tableGenerator.removeUniqueConstraint(0);
		assertSourceDoesNotContain("@TableGenerator(", cu);
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.moveUniqueConstraint(2, 0);
		assertSourceContains("@TableGenerator(uniqueConstraints = {@UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"})})", cu);
	}
	
	public void testMoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestTableGeneratorWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.moveUniqueConstraint(0, 2);
		assertSourceContains("@TableGenerator(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"})})", cu);
	}
	
}
