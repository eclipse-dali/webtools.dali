/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumn2_0Annotation;

@SuppressWarnings("nls")
public class MapKeyColumn2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_TABLE = "MY_TABLE";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	
	public MapKeyColumn2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyColumn");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyColumn(name = \"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyColumn(table = \"" + COLUMN_TABLE + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithColumnDefinition() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyColumn(columnDefinition = \"" + COLUMN_COLUMN_DEFINITION + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyColumn(" + booleanElement + " = true)");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithIntElement(final String intElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyColumn(" + intElement + " = 5)");
			}
		});
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);
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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@MapKeyColumn(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}
	
	public void testGetTable() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);
		assertEquals(COLUMN_TABLE, column.getTable());
	}

	public void testSetTable() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertNull(column.getTable());

		column.setTable("Foo");
		assertEquals("Foo", column.getTable());
		
		assertSourceContains("@MapKeyColumn(table = \"Foo\")", cu);

		
		column.setTable(null);
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithColumnDefinition();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@MapKeyColumn(columnDefinition = \"Foo\")", cu);

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}

	public void testGetUnique() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithBooleanElement("unique");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertEquals(Boolean.TRUE, column.getUnique());
	}
	
	public void testSetUnique() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertNull(column.getUnique());

		column.setUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getUnique());
		
		assertSourceContains("@MapKeyColumn(unique = false)", cu);
		
		column.setUnique(null);
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}
	
	public void testGetNullable() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithBooleanElement("nullable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertEquals(Boolean.TRUE, column.getNullable());
	}
	
	public void testSetNullable() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertNull(column.getNullable());

		column.setNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getNullable());
		
		assertSourceContains("@MapKeyColumn(nullable = false)", cu);
		
		column.setNullable(null);
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}

	public void testGetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithBooleanElement("insertable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertEquals(Boolean.TRUE, column.getInsertable());
	}
	
	public void testSetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertNull(column.getInsertable());

		column.setInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getInsertable());
		
		assertSourceContains("@MapKeyColumn(insertable = false)", cu);
		
		column.setInsertable(null);
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}
	
	public void testGetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithBooleanElement("updatable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertEquals(Boolean.TRUE, column.getUpdatable());
	}
	
	public void testSetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertNull(column.getUpdatable());

		column.setUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getUpdatable());
		
		assertSourceContains("@MapKeyColumn(updatable = false)", cu);
		
		column.setUpdatable(null);
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}

	public void testGetLength() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithIntElement("length");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertEquals(Integer.valueOf(5), column.getLength());
	}
	
	public void testSetLength() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertNull(column.getLength());

		column.setLength(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), column.getLength());
		
		assertSourceContains("@MapKeyColumn(length = 5)", cu);
		
		column.setLength(null);
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}

	public void testGetPrecision() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithIntElement("precision");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertEquals(Integer.valueOf(5), column.getPrecision());
	}
	
	public void testSetPrecision() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertEquals(null, column.getPrecision());

		column.setPrecision(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), column.getPrecision());
		
		assertSourceContains("@MapKeyColumn(precision = 5)", cu);
		
		column.setPrecision(null);
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}

	public void testGetScale() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithIntElement("scale");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertEquals(Integer.valueOf(5), column.getScale());
	}
	
	public void testSetScale() throws Exception {
		ICompilationUnit cu = this.createTestColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.MAP_KEY_COLUMN);

		assertNotNull(column);
		assertNull(column.getScale());

		column.setScale(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), column.getScale());
		
		assertSourceContains("@MapKeyColumn(scale = 5)", cu);
		
		column.setScale(null);
		assertSourceDoesNotContain("@MapKeyColumn(", cu);
	}
}
