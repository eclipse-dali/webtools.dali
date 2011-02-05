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
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

@SuppressWarnings("nls")
public class AttributeOverrideTests extends JpaJavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String ATTRIBUTE_OVERRIDE_NAME = "MY_ATTRIBUTE_OVERRIDE";
	
	public AttributeOverrideTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestAttributeOverrideOnField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestAttributeOverrideWithColumnOnField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column(name = \"" + COLUMN_NAME + "\"))");
			}
		});
	}
		
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE);

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
	}

	public void testGetNullColumn() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE);
		ColumnAnnotation column = attributeOverride.getColumn();
		assertNotNull(attributeOverride);
		assertNull(column);
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE);

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());

		attributeOverride.setName("Foo");
		assertEquals("Foo", attributeOverride.getName());
		assertSourceContains("@AttributeOverride(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE);

		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
		
		attributeOverride.setName(null);
		assertNull(attributeOverride.getName());
		
		assertSourceDoesNotContain("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")", cu);
		assertSourceContains("@AttributeOverride", cu);
	}
	
	public void testColumnGetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideWithColumnOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE);
		ColumnAnnotation column = attributeOverride.getColumn();
		assertEquals(COLUMN_NAME, column.getName());
	}
	
	public void testColumnSetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideWithColumnOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE);
		ColumnAnnotation column = attributeOverride.getColumn();
		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName("Foo");
		
		assertSourceContains("@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column(name = \"Foo\"))", cu);
		
		column.setName(null);
		assertNull(attributeOverride.getColumn().getName());
		attributeOverride.removeColumn();
		assertNull(attributeOverride.getColumn());
		assertSourceContains("@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\")", cu);
	}
	
	public void testAddColumn() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE);
		ColumnAnnotation column = attributeOverride.getColumn();
		assertNull(column);
		
		attributeOverride.addColumn();
		column = attributeOverride.getColumn();
		assertNotNull(column);
		assertSourceContains("@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column)", cu);		
	}
	
	public void testRemoveColumn() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.getAnnotation(JPA.ATTRIBUTE_OVERRIDE);
		ColumnAnnotation column = attributeOverride.getColumn();
		assertNull(column);
	}

}
