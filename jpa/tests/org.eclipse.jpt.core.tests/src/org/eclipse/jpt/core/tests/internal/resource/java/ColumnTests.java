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
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class ColumnTests extends JavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_TABLE = "MY_TABLE";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	
	public ColumnTests(String name) {
		super(name);
	}

	private void createColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("Column", 
			"String name() default \"\"; " +
			"boolean unique() default false; " +
			"boolean nullable() default true; " +
			"boolean insertable() default true; " +
			"boolean updatable() default true; " +
			"String columnDefinition() default \"\"; " +
			"String table() default \"\"; " +
			"int length() default 255; " +
			"int precision() default 0; " +
			"int scale() default 0;");
	}

	private IType createTestColumn() throws Exception {
		createColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column");
			}
		});
	}
	
	private IType createTestColumnWithName() throws Exception {
		createColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(name=\"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private IType createTestColumnWithTable() throws Exception {
		createColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(table=\"" + COLUMN_TABLE + "\")");
			}
		});
	}
	
	private IType createTestColumnWithColumnDefinition() throws Exception {
		createColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(columnDefinition=\"" + COLUMN_COLUMN_DEFINITION + "\")");
			}
		});
	}
	
	private IType createTestColumnWithBooleanElement(final String booleanElement) throws Exception {
		createColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(" + booleanElement + "=true)");
			}
		});
	}
	
	private IType createTestColumnWithIntElement(final String intElement) throws Exception {
		createColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(" + intElement + "=5)");
			}
		});
	}
	
	public void testGetName() throws Exception {
		IType testType = this.createTestColumnWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.getNullable());
		assertNull(column.getInsertable());
		assertNull(column.getUnique());
		assertNull(column.getUpdatable());
		assertNull(column.getTable());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@Column(name=\"Foo\")");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestColumnWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@Column");
	}
	
	public void testGetTable() throws Exception {
		IType testType = this.createTestColumnWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);
		assertEquals(COLUMN_TABLE, column.getTable());
	}

	public void testSetTable() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getTable());

		column.setTable("Foo");
		assertEquals("Foo", column.getTable());
		
		assertSourceContains("@Column(table=\"Foo\")");

		
		column.setTable(null);
		assertSourceDoesNotContain("@Column");
	}

	public void testGetColumnDefinition() throws Exception {
		IType testType = this.createTestColumnWithColumnDefinition();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@Column(columnDefinition=\"Foo\")");

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@Column");
	}

	public void testGetUnique() throws Exception {
		IType testType = this.createTestColumnWithBooleanElement("unique");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertTrue(column.getUnique());
	}
	
	public void testSetUnique() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getUnique());

		column.setUnique(false);
		assertFalse(column.getUnique());
		
		assertSourceContains("@Column(unique=false)");
		
		column.setUnique(null);
		assertSourceDoesNotContain("@Column");
	}
	
	public void testGetNullable() throws Exception {
		IType testType = this.createTestColumnWithBooleanElement("nullable");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertTrue(column.getNullable());
	}
	
	public void testSetNullable() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getNullable());

		column.setNullable(false);
		assertFalse(column.getNullable());
		
		assertSourceContains("@Column(nullable=false)");
		
		column.setNullable(null);
		assertSourceDoesNotContain("@Column");
	}

	public void testGetInsertable() throws Exception {
		IType testType = this.createTestColumnWithBooleanElement("insertable");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertTrue(column.getInsertable());
	}
	
	public void testSetInsertable() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getInsertable());

		column.setInsertable(false);
		assertFalse(column.getInsertable());
		
		assertSourceContains("@Column(insertable=false)");
		
		column.setInsertable(null);
		assertSourceDoesNotContain("@Column");
	}
	
	public void testGetUpdatable() throws Exception {
		IType testType = this.createTestColumnWithBooleanElement("updatable");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertTrue(column.getUpdatable());
	}
	
	public void testSetUpdatable() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getUpdatable());

		column.setUpdatable(false);
		assertFalse(column.getUpdatable());
		
		assertSourceContains("@Column(updatable=false)");
		
		column.setUpdatable(null);
		assertSourceDoesNotContain("@Column");
	}

	public void testGetLength() throws Exception {
		IType testType = this.createTestColumnWithIntElement("length");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertEquals(5, column.getLength());
	}
	
	public void testSetLength() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertEquals(-1, column.getLength());

		column.setLength(5);
		assertEquals(5, column.getLength());
		
		assertSourceContains("@Column(length=5)");
		
		column.setLength(-1);
		assertSourceDoesNotContain("@Column");
	}

	public void testGetPrecision() throws Exception {
		IType testType = this.createTestColumnWithIntElement("precision");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertEquals(5, column.getPrecision());
	}
	
	public void testSetPrecision() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertEquals(-1, column.getPrecision());

		column.setPrecision(5);
		assertEquals(5, column.getPrecision());
		
		assertSourceContains("@Column(precision=5)");
		
		column.setPrecision(-1);
		assertSourceDoesNotContain("@Column");
	}

	public void testGetScale() throws Exception {
		IType testType = this.createTestColumnWithIntElement("scale");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertEquals(5, column.getScale());
	}
	
	public void testSetScale() throws Exception {
		IType testType = this.createTestColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		Column column = (Column) attributeResource.annotation(JPA.COLUMN);

		assertNotNull(column);
		assertEquals(-1, column.getScale());

		column.setScale(5);
		assertEquals(5, column.getScale());
		
		assertSourceContains("@Column(scale=5)");
		
		column.setScale(-1);
		assertSourceDoesNotContain("@Column");
	}
}
