/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyJoinColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

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
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@MapKeyJoinColumns(@MapKeyJoinColumn(name = \"Foo\"))", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	public void testGetTable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();
		assertEquals(COLUMN_TABLE, column.getTable());
	}

	public void testSetTable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

		assertTrue(column.getUnique().booleanValue());
	}
	
	public void testSetUnique() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

		assertTrue(column.getNullable().booleanValue());
	}
	
	public void testSetNullable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

		assertTrue(column.getInsertable().booleanValue());
	}
	
	public void testSetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

		assertTrue(column.getUpdatable().booleanValue());
	}
	
	public void testSetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS).next();

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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		MapKeyJoinColumn2_0Annotation joinColumn = (MapKeyJoinColumn2_0Annotation) attributeResource.addAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@MapKeyJoinColumns({@MapKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", table = \"TABLE\", unique = false, nullable = false, insertable = false, updatable = false, referencedColumnName = \"REF_NAME\"),@MapKeyJoinColumn(name = \"FOO\")})", cu);
		
		assertNull(attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN));
		assertNotNull(attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMNS));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS)));
	}
	
	public void testAddJoinColumnToBeginningOfList() throws Exception {
		ICompilationUnit cu = createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		MapKeyJoinColumn2_0Annotation joinColumn = (MapKeyJoinColumn2_0Annotation) attributeResource.addAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@MapKeyJoinColumns({@MapKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", table = \"TABLE\", unique = false, nullable = false, insertable = false, updatable = false, referencedColumnName = \"REF_NAME\"),@MapKeyJoinColumn(name = \"FOO\")})", cu);
				
		joinColumn = (MapKeyJoinColumn2_0Annotation) attributeResource.addAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
		joinColumn.setName("BAZ");
		assertSourceContains("@MapKeyJoinColumns({@MapKeyJoinColumn(name = \"BAZ\"),@MapKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", table = \"TABLE\", unique = false, nullable = false, insertable = false, updatable = false, referencedColumnName = \"REF_NAME\"), @MapKeyJoinColumn(name = \"FOO\")})", cu);

		Iterator<NestableAnnotation> joinColumns = attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
		assertEquals("BAZ", ((MapKeyJoinColumn2_0Annotation) joinColumns.next()).getName());
		assertEquals("BAR", ((MapKeyJoinColumn2_0Annotation) joinColumns.next()).getName());
		assertEquals("FOO", ((MapKeyJoinColumn2_0Annotation) joinColumns.next()).getName());
		
		assertNull(attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN));
		assertNotNull(attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMNS));
		assertEquals(3, CollectionTools.size(attributeResource.annotations(JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS)));
	}


	public void testRemoveJoinColumnCopyExisting() throws Exception {
		ICompilationUnit cu = createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		MapKeyJoinColumn2_0Annotation joinColumn = (MapKeyJoinColumn2_0Annotation) attributeResource.addAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@MapKeyJoinColumns({@MapKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", table = \"TABLE\", unique = false, nullable = false, insertable = false, updatable = false, referencedColumnName = \"REF_NAME\"),@MapKeyJoinColumn(name = \"FOO\")})", cu);
		
		attributeResource.removeAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN, JPA2_0.MAP_KEY_JOIN_COLUMNS);
		assertSourceContains("@MapKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", table = \"TABLE\", unique = false, nullable = false, insertable = false, updatable = false, referencedColumnName = \"REF_NAME\")", cu);
	}

}
