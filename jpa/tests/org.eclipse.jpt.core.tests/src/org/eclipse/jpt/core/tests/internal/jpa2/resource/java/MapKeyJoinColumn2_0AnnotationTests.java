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
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class MapKeyJoinColumn2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_TABLE = "MY_TABLE";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	private static final String COLUMN_REFERENCED_COLUMN_NAME = "MY_REF_COLUMN_NAME";
	
	public MapKeyJoinColumn2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestJoinColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumn");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumn(name = \"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumn(table = \"" + COLUMN_TABLE + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithReferencedColumnName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumn(referencedColumnName = \"" + COLUMN_REFERENCED_COLUMN_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithColumnDefinition() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumn(columnDefinition = \"" + COLUMN_COLUMN_DEFINITION + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestJoinColumnWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.MAP_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@MapKeyJoinColumn(" + booleanElement + " = true)");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.getNullable());
		assertNull(column.getInsertable());
		assertNull(column.getUnique());
		assertNull(column.getUpdatable());
		assertNull(column.getTable());
		assertNull(column.getReferencedColumnName());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@MapKeyJoinColumn(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	public void testGetTable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithTable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_TABLE, column.getTable());
	}

	public void testSetTable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getTable());

		column.setTable("Foo");
		assertEquals("Foo", column.getTable());
		
		assertSourceContains("@MapKeyJoinColumn(table = \"Foo\")", cu);

		
		column.setTable(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	public void testGetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithReferencedColumnName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getReferencedColumnName());

		column.setReferencedColumnName("Foo");
		assertEquals("Foo", column.getReferencedColumnName());
		
		assertSourceContains("@MapKeyJoinColumn(referencedColumnName = \"Foo\")", cu);

		
		column.setReferencedColumnName(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithColumnDefinition();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@MapKeyJoinColumn(columnDefinition = \"Foo\")", cu);

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}

	public void testGetUnique() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithBooleanElement("unique");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertEquals(Boolean.TRUE, column.getUnique());
	}
	
	public void testSetUnique() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getUnique());

		column.setUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getUnique());
		
		assertSourceContains("@MapKeyJoinColumn(unique = false)", cu);
		
		column.setUnique(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	public void testGetNullable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithBooleanElement("nullable");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertEquals(Boolean.TRUE, column.getNullable());
	}
	
	public void testSetNullable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getNullable());

		column.setNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getNullable());
		
		assertSourceContains("@MapKeyJoinColumn(nullable = false)", cu);
		
		column.setNullable(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}

	public void testGetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithBooleanElement("insertable");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertEquals(Boolean.TRUE, column.getInsertable());
	}
	
	public void testSetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getInsertable());

		column.setInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getInsertable());
		
		assertSourceContains("@MapKeyJoinColumn(insertable = false)", cu);
		
		column.setInsertable(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
	
	public void testGetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumnWithBooleanElement("updatable");
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertEquals(Boolean.TRUE, column.getUpdatable());
	}
	
	public void testSetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		MapKeyJoinColumn2_0Annotation column = (MapKeyJoinColumn2_0Annotation) attributeResource.getAnnotation(JPA2_0.MAP_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getUpdatable());

		column.setUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getUpdatable());
		
		assertSourceContains("@MapKeyJoinColumn(updatable = false)", cu);
		
		column.setUpdatable(null);
		assertSourceDoesNotContain("@MapKeyJoinColumn(", cu);
	}
}
