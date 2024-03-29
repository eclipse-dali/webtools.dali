/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

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
				return IteratorTools.iterator(JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
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
				return IteratorTools.iterator(JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column(name = \"" + COLUMN_NAME + "\"))");
			}
		});
	}

	private AttributeOverrideAnnotation getAttributeOverrideAnnotationAt(JavaResourceMember resourceMember, int index) {
		return (AttributeOverrideAnnotation) resourceMember.getAnnotation(index, JPA.ATTRIBUTE_OVERRIDE);
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
	}

	public void testGetNullColumn() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);
		ColumnAnnotation column = attributeOverride.getColumn();
		assertNotNull(attributeOverride);
		assertNull(column);
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());

		attributeOverride.setName("Foo");
		assertEquals("Foo", attributeOverride.getName());
		assertSourceContains("@AttributeOverride(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);

		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
		
		attributeOverride.setName(null);
		assertNull(attributeOverride.getName());
		
		assertSourceDoesNotContain("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")", cu);
		assertSourceContains("@AttributeOverride", cu);
	}
	
	public void testColumnGetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideWithColumnOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);
		ColumnAnnotation column = attributeOverride.getColumn();
		assertEquals(COLUMN_NAME, column.getName());
	}
	
	public void testColumnSetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideWithColumnOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);
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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);
		ColumnAnnotation column = attributeOverride.getColumn();
		assertNull(column);
		
		attributeOverride.addColumn();
		column = attributeOverride.getColumn();
		assertNotNull(column);
		assertSourceContains("@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column)", cu);		
	}
	
	public void testRemoveColumn() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);
		ColumnAnnotation column = attributeOverride.getColumn();
		assertNull(column);
	}

}
