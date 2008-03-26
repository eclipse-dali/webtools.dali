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
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class PrimaryKeyJoinColumnTests extends JavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	private static final String COLUMN_REFERENCED_COLUMN_NAME = "MY_REF_COLUMN_NAME";
	
	public PrimaryKeyJoinColumnTests(String name) {
		super(name);
	}
	
	private void createPrimaryKeyJoinColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("PrimaryKeyJoinColumn", 
			"String name() default \"\"; " +
			"String referencedColumnName() default \"\"; " +
			"String columnDefinition() default \"\"; ");
	}

	private IType createTestPrimaryKeyJoinColumn() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumn");
			}
		});
	}
	
	private IType createTestPrimaryKeyJoinColumnWithName() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumn(name=\"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private IType createTestPrimaryKeyJoinColumnWithReferencedColumnName() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumn(referencedColumnName=\"" + COLUMN_REFERENCED_COLUMN_NAME + "\")");
			}
		});
	}
	
	private IType createTestPrimaryKeyJoinColumnWithColumnDefinition() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumn(columnDefinition=\"" + COLUMN_COLUMN_DEFINITION + "\")");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertNull(column.getReferencedColumnName());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@PrimaryKeyJoinColumn(name=\"Foo\")");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn");
	}


	
	public void testGetReferencedColumnName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithReferencedColumnName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getReferencedColumnName());

		column.setReferencedColumnName("Foo");
		assertEquals("Foo", column.getReferencedColumnName());
		
		assertSourceContains("@PrimaryKeyJoinColumn(referencedColumnName=\"Foo\")");

		
		column.setReferencedColumnName(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn");
	}

	public void testGetColumnDefinition() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithColumnDefinition();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@PrimaryKeyJoinColumn(columnDefinition=\"Foo\")");

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn");
	}

}
