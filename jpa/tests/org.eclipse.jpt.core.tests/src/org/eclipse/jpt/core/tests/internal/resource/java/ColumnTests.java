/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class ColumnTests extends JpaJavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_TABLE = "MY_TABLE";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	
	public ColumnTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestColumn() throws Exception {
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
	
	private ICompilationUnit createTestColumnWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(name = \"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(table = \"" + COLUMN_TABLE + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithColumnDefinition() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(columnDefinition = \"" + COLUMN_COLUMN_DEFINITION + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(" + booleanElement + " = true)");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithIntElement(final String intElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(" + intElement + " = 5)");
			}
		});
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);
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
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@Column(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@Column", cu);
	}
	
	public void testGetTable() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);
		assertEquals(COLUMN_TABLE, column.getTable());
	}

	public void testSetTable() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getTable());

		column.setTable("Foo");
		assertEquals("Foo", column.getTable());
		
		assertSourceContains("@Column(table = \"Foo\")", cu);

		
		column.setTable(null);
		assertSourceDoesNotContain("@Column", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithColumnDefinition();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@Column(columnDefinition = \"Foo\")", cu);

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@Column", cu);
	}

	public void testGetUnique() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithBooleanElement("unique");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertEquals(Boolean.TRUE, column.getUnique());
	}
	
	public void testSetUnique() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getUnique());

		column.setUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getUnique());
		
		assertSourceContains("@Column(unique = false)", cu);
		
		column.setUnique(null);
		assertSourceDoesNotContain("@Column", cu);
	}
	
	public void testGetNullable() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithBooleanElement("nullable");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertEquals(Boolean.TRUE, column.getNullable());
	}
	
	public void testSetNullable() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getNullable());

		column.setNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getNullable());
		
		assertSourceContains("@Column(nullable = false)", cu);
		
		column.setNullable(null);
		assertSourceDoesNotContain("@Column", cu);
	}

	public void testGetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithBooleanElement("insertable");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertEquals(Boolean.TRUE, column.getInsertable());
	}
	
	public void testSetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getInsertable());

		column.setInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getInsertable());
		
		assertSourceContains("@Column(insertable = false)", cu);
		
		column.setInsertable(null);
		assertSourceDoesNotContain("@Column", cu);
	}
	
	public void testGetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithBooleanElement("updatable");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertEquals(Boolean.TRUE, column.getUpdatable());
	}
	
	public void testSetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getUpdatable());

		column.setUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getUpdatable());
		
		assertSourceContains("@Column(updatable = false)", cu);
		
		column.setUpdatable(null);
		assertSourceDoesNotContain("@Column", cu);
	}

	public void testGetLength() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithIntElement("length");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertEquals(Integer.valueOf(5), column.getLength());
	}
	
	public void testSetLength() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getLength());

		column.setLength(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), column.getLength());
		
		assertSourceContains("@Column(length = 5)", cu);
		
		column.setLength(null);
		assertSourceDoesNotContain("@Column", cu);
	}

	public void testGetPrecision() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithIntElement("precision");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertEquals(Integer.valueOf(5), column.getPrecision());
	}
	
	public void testSetPrecision() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertEquals(null, column.getPrecision());

		column.setPrecision(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), column.getPrecision());
		
		assertSourceContains("@Column(precision = 5)", cu);
		
		column.setPrecision(null);
		assertSourceDoesNotContain("@Column", cu);
	}

	public void testGetScale() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithIntElement("scale");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertEquals(Integer.valueOf(5), column.getScale());
	}
	
	public void testSetScale() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.getAnnotation(JPA.COLUMN);

		assertNotNull(column);
		assertNull(column.getScale());

		column.setScale(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), column.getScale());
		
		assertSourceContains("@Column(scale = 5)", cu);
		
		column.setScale(null);
		assertSourceDoesNotContain("@Column", cu);
	}
}
