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
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OrderColumn2_0Annotation;

@SuppressWarnings("nls")
public class OrderColumn2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	
	public OrderColumn2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestOrderColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.ORDER_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OrderColumn");
			}
		});
	}
	
	private ICompilationUnit createTestOrderColumnWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.ORDER_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OrderColumn(name = \"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestOrderColumnWithColumnDefinition() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.ORDER_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OrderColumn(columnDefinition = \"" + COLUMN_COLUMN_DEFINITION + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestOrderColumnWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.ORDER_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OrderColumn(" + booleanElement + " = true)");
			}
		});
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.getNullable());
		assertNull(column.getInsertable());
		assertNull(column.getUpdatable());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@OrderColumn(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);
		assertNotNull(column);
		assertSourceContains("@OrderColumn", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumnWithColumnDefinition();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@OrderColumn(columnDefinition = \"Foo\")", cu);

		
		column.setColumnDefinition(null);
		column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);
		assertNotNull(column);
		assertSourceContains("@OrderColumn", cu);
	}
	
	public void testGetNullable() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumnWithBooleanElement("nullable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);

		assertEquals(Boolean.TRUE, column.getNullable());
	}
	
	public void testSetNullable() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);

		assertNotNull(column);
		assertNull(column.getNullable());

		column.setNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getNullable());
		
		assertSourceContains("@OrderColumn(nullable = false)", cu);
		
		column.setNullable(null);
		column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);
		assertNotNull(column);
		assertSourceContains("@OrderColumn", cu);
	}

	public void testGetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumnWithBooleanElement("insertable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);

		assertEquals(Boolean.TRUE, column.getInsertable());
	}
	
	public void testSetInsertable() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);

		assertNotNull(column);
		assertNull(column.getInsertable());

		column.setInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getInsertable());
		
		assertSourceContains("@OrderColumn(insertable = false)", cu);
		
		column.setInsertable(null);
		column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);
		assertNotNull(column);
		assertSourceContains("@OrderColumn", cu);
	}
	
	public void testGetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumnWithBooleanElement("updatable");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);

		assertEquals(Boolean.TRUE, column.getUpdatable());
	}
	
	public void testSetUpdatable() throws Exception {
		ICompilationUnit cu = this.createTestOrderColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		OrderColumn2_0Annotation column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);

		assertNotNull(column);
		assertNull(column.getUpdatable());

		column.setUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getUpdatable());
		
		assertSourceContains("@OrderColumn(updatable = false)", cu);
		
		column.setUpdatable(null);
		
		column = (OrderColumn2_0Annotation) resourceField.getAnnotation(JPA2_0.ORDER_COLUMN);
		assertNotNull(column);
		assertSourceContains("@OrderColumn", cu);
	}

}
