/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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

	private ICompilationUnit createTestPrimaryKeyJoinColumn() throws Exception {
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
	
	private ICompilationUnit createTestPrimaryKeyJoinColumnWithName() throws Exception {
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
	
	private ICompilationUnit createTestPrimaryKeyJoinColumnWithReferencedColumnName() throws Exception {
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
	
	private ICompilationUnit createTestPrimaryKeyJoinColumnWithColumnDefinition() throws Exception {
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
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertNull(column.getReferencedColumnName());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@PrimaryKeyJoinColumn(name=\"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn", cu);
	}


	
	public void testGetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithReferencedColumnName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getReferencedColumnName());

		column.setReferencedColumnName("Foo");
		assertEquals("Foo", column.getReferencedColumnName());
		
		assertSourceContains("@PrimaryKeyJoinColumn(referencedColumnName=\"Foo\")", cu);

		
		column.setReferencedColumnName(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithColumnDefinition();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@PrimaryKeyJoinColumn(columnDefinition=\"Foo\")", cu);

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn", cu);
	}

}
