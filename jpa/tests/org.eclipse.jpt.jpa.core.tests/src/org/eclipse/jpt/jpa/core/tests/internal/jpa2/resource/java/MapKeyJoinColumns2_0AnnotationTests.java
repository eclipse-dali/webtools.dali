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
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyJoinColumn2_0Annotation;

@SuppressWarnings("nls")
public class MapKeyJoinColumns2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_TABLE = "MY_TABLE";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	private static final String COLUMN_REFERENCED_COLUMN_NAME = "MY_REF_COLUMN_NAME";
	
	public MapKeyJoinColumns2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumns(@MapKeyJoinColumn)");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumns(@MapKeyJoinColumn(name = \"" + COLUMN_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumns(@MapKeyJoinColumn(table = \"" + COLUMN_TABLE + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithReferencedColumnName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumns(@MapKeyJoinColumn(referencedColumnName = \"" + COLUMN_REFERENCED_COLUMN_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithColumnDefinition() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumns(@MapKeyJoinColumn(columnDefinition = \"" + COLUMN_COLUMN_DEFINITION + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumns(@MapKeyJoinColumn(" + booleanElement + " = true))");
			}
		});
	}

	private ICompilationUnit createTestJoinColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")");
			}
		});
	}
	
	private MapKeyJoinColumn2_0Annotation mapKeyJoinColumnAt(int index, JavaResourceMember resourceMember) {
		return (MapKeyJoinColumn2_0Annotation) resourceMember.getAnnotation(index, JPA2_0.MAP_KEY_JOIN_COLUMN);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);
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
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@MapKeyJoinColumns(@MapKeyJoinColumn(name = \"Foo\"))", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	public void testGetTable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);
		assertEquals(COLUMN_TABLE, column.getTable());
	}

	public void testSetTable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertNotNull(column);
		assertNull(column.getTable());

		column.setTable("Foo");
		assertEquals("Foo", column.getTable());
		
		assertSourceContains("@MapKeyJoinColumns(@MapKeyJoinColumn(table = \"Foo\"))", cu);

		
		column.setTable(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	public void testGetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithReferencedColumnName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertNotNull(column);
		assertNull(column.getReferencedColumnName());

		column.setReferencedColumnName("Foo");
		assertEquals("Foo", column.getReferencedColumnName());
		
		assertSourceContains("@MapKeyJoinColumns(@MapKeyJoinColumn(referencedColumnName = \"Foo\"))", cu);

		
		column.setReferencedColumnName(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithColumnDefinition();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@MapKeyJoinColumns(@MapKeyJoinColumn(columnDefinition = \"Foo\"))", cu);

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}

	public void testGetUnique() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithBooleanElement("unique");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertTrue(column.getUnique().booleanValue());
	}
	
	public void testSetUnique() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertNotNull(column);
		assertNull(column.getUnique());

		column.setUnique(Boolean.FALSE);
		assertFalse(column.getUnique().booleanValue());
		
		assertSourceContains("@MapKeyJoinColumns(@MapKeyJoinColumn(unique = false))", cu);
		
		column.setUnique(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	public void testGetNullable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithBooleanElement("nullable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertTrue(column.getNullable().booleanValue());
	}
	
	public void testSetNullable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertNotNull(column);
		assertNull(column.getNullable());

		column.setNullable(Boolean.FALSE);
		assertFalse(column.getNullable().booleanValue());
		
		assertSourceContains("@MapKeyJoinColumns(@MapKeyJoinColumn(nullable = false))", cu);
		
		column.setNullable(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}

	public void testGetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithBooleanElement("insertable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertTrue(column.getInsertable().booleanValue());
	}
	
	public void testSetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertNotNull(column);
		assertNull(column.getInsertable());

		column.setInsertable(Boolean.FALSE);
		assertFalse(column.getInsertable().booleanValue());
		
		assertSourceContains("@MapKeyJoinColumns(@MapKeyJoinColumn(insertable = false))", cu);
		
		column.setInsertable(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	public void testGetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithBooleanElement("updatable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertTrue(column.getUpdatable().booleanValue());
	}
	
	public void testSetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		MapKeyJoinColumn2_0Annotation column = this.mapKeyJoinColumnAt(0, resourceField);

		assertNotNull(column);
		assertNull(column.getUpdatable());

		column.setUpdatable(Boolean.FALSE);
		assertFalse(column.getUpdatable().booleanValue());
		
		assertSourceContains("@MapKeyJoinColumns(@MapKeyJoinColumn(updatable = false))", cu);
		
		column.setUpdatable(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	
	public void testAddJoinColumnCopyExisting() throws Exception {
		ICompilationUnit cu = createTestJoinColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		String expected1 = "@MapKeyJoinColumns({";
		String expected2 = "@MapKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"),";
		String expected3 = "@MapKeyJoinColumn(name = \"FOO\") })";
		MapKeyJoinColumn2_0Annotation joinColumn = (MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN);
		joinColumn.setName("FOO");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		assertNull(resourceField.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMNS));
		assertNotNull(resourceField.getAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN));
		assertEquals(2, resourceField.getAnnotationsSize(JPA2_0.MAP_KEY_JOIN_COLUMN));
	}
	
	public void testAddJoinColumnToBeginningOfList() throws Exception {
		ICompilationUnit cu = createTestJoinColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		String expected1 = "@MapKeyJoinColumns({";
		String expected2 = "@MapKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"),";
		String expected3 = "@MapKeyJoinColumn(name = \"FOO\") })";
		MapKeyJoinColumn2_0Annotation joinColumn = (MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN);
		joinColumn.setName("FOO");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
				
		expected2 = "@MapKeyJoinColumn(name = \"BAZ\"),";
		expected3 = "@MapKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @MapKeyJoinColumn(name = \"FOO\") })";
		joinColumn = (MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN);
		joinColumn.setName("BAZ");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);

		Iterator<NestableAnnotation> joinColumns = resourceField.getAnnotations(JPA2_0.MAP_KEY_JOIN_COLUMN).iterator();
		assertEquals("BAZ", ((MapKeyJoinColumn2_0Annotation) joinColumns.next()).getName());
		assertEquals("BAR", ((MapKeyJoinColumn2_0Annotation) joinColumns.next()).getName());
		assertEquals("FOO", ((MapKeyJoinColumn2_0Annotation) joinColumns.next()).getName());
		
		assertNull(resourceField.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMNS));
		assertNotNull(resourceField.getAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN));
		assertEquals(3, resourceField.getAnnotationsSize(JPA2_0.MAP_KEY_JOIN_COLUMN));
	}


	public void testRemoveJoinColumnCopyExisting() throws Exception {
		ICompilationUnit cu = createTestJoinColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		String expected1 = "@MapKeyJoinColumns({";
		String expected2 = "@MapKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"),";
		String expected3 = "@MapKeyJoinColumn(name = \"FOO\") })";
		MapKeyJoinColumn2_0Annotation joinColumn = (MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN);
		joinColumn.setName("FOO");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected2 = "@MapKeyJoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")";
		resourceField.removeAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN);
		assertSourceContains(expected2, cu);
		assertSourceDoesNotContain("@MapKeyJoinColumns", cu);
	}

}
