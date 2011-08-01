/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class AttributeOverridesTests extends JpaJavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String ATTRIBUTE_OVERRIDE_NAME = "MY_ATTRIBUTE_OVERRIDE";
	
	public AttributeOverridesTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestAttributeOverrideOnField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AttributeOverrides(@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestAttributeOverrideWithColumnOnField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AttributeOverrides(@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column(name = \"" + COLUMN_NAME + "\")))");
			}
		});
	}
	
	private ICompilationUnit createTestAttributeOverride() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@AttributeOverride(name = \"FOO\", column = @Column(name = \"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1))");
			}
		});
	}

	private AttributeOverrideAnnotation getAttributeOverrideAnnotationAt(JavaResourceMember resourceMember, int index) {
		return (AttributeOverrideAnnotation) resourceMember.getAnnotation(index, JPA.ATTRIBUTE_OVERRIDE);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
	}

	public void testGetNullColumn() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);

		ColumnAnnotation column = attributeOverride.getColumn();
		assertNotNull(attributeOverride);
		assertNull(column);
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());

		attributeOverride.setName("Foo");
		assertEquals("Foo", attributeOverride.getName());
		assertSourceContains("@AttributeOverrides(@AttributeOverride(name = \"Foo\"))", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);

		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
		
		attributeOverride.setName(null);
		assertNull(attributeOverride.getName());
		
		assertSourceDoesNotContain("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")", cu);
		assertSourceContains("@AttributeOverride", cu);
		assertSourceContains("@AttributeOverrides", cu);
	}
	
	public void testColumnGetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideWithColumnOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);

		ColumnAnnotation column = attributeOverride.getColumn();
		assertEquals(COLUMN_NAME, column.getName());
	}
	
	public void testColumnSetName() throws Exception {
		ICompilationUnit cu = this.createTestAttributeOverrideWithColumnOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AttributeOverrideAnnotation attributeOverride = this.getAttributeOverrideAnnotationAt(resourceField, 0);

		ColumnAnnotation column = attributeOverride.getColumn();
		
		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName("Foo");
		
		assertSourceContains("@AttributeOverrides(@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column(name = \"Foo\")))", cu);
		
		column.setName(null);
		assertNull(attributeOverride.getColumn().getName());
		attributeOverride.removeColumn();
		assertNull(attributeOverride.getColumn());
		assertSourceContains("@AttributeOverride(name = \"" + ATTRIBUTE_OVERRIDE_NAME + "\")", cu);
	}
	
	public void testAddAttributeOverrideCopyExisting() throws Exception {
		ICompilationUnit cu = createTestAttributeOverride();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		String expected1 = "@AttributeOverrides({";
		String expected2 = "@AttributeOverride(name = \"FOO\", column = @Column(name = \"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1)),";
		String expected3 = "@AttributeOverride(name = \"BAR\") })";
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		assertNull(resourceType.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(resourceType.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertNotNull(resourceType.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE));
		assertEquals(2, resourceType.getAnnotationsSize(JPA.ATTRIBUTE_OVERRIDE));
	}
	
	public void testAddAttributeOverrideToBeginningOfList() throws Exception {
		ICompilationUnit cu = createTestAttributeOverride();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		String expected1 = "@AttributeOverrides({";
		String expected2 = "@AttributeOverride(name = \"FOO\", column = @Column(name = \"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1)),";
		String expected3 = "@AttributeOverride(name = \"BAR\") })";
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected2 = "@AttributeOverride(name = \"BAZ\"),";
		expected3 = "@AttributeOverride(name = \"FOO\", column = @Column(name = \"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1)), @AttributeOverride(name = \"BAR\") })";
		attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAZ");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);

		Iterator<NestableAnnotation> attributeOverrides = resourceType.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();
		assertEquals("BAZ", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("FOO", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("BAR", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());

		assertNull(resourceType.getAnnotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(resourceType.getAnnotation(JPA.ATTRIBUTE_OVERRIDES));
		assertNotNull(resourceType.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE));
		assertEquals(3, resourceType.getAnnotationsSize(JPA.ATTRIBUTE_OVERRIDE));
	}

	public void testRemoveAttributeOverrideCopyExisting() throws Exception {
		ICompilationUnit cu = createTestAttributeOverride();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		String expected1 = "@AttributeOverrides({";
		String expected2 = "@AttributeOverride(name = \"FOO\", column = @Column(name = \"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1)),";
		String expected3 = "@AttributeOverride(name = \"BAR\") })";
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected1 = "@AttributeOverride(name = \"FOO\", column = @Column(name = \"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1))";
		resourceType.removeAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		assertSourceContains(expected1, cu);
		assertSourceDoesNotContain("@AttributeOverrides", cu);
	}
	//not sure i want to test this api, how can we keep ContainerAnnotation.add, move, remove from being public?
	//users should go throught AbstractJavapersistenceResource. this gets confusing because you would handle it differently
	//for non top-level annotations
//	public void testAdd() throws Exception {
//		ICompilationUnit cu = this.createTestType();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(cu); 
//		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
//		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
//		assertNull(attributeOverrides);
//		
//		attributeResource.addAnnotation(JPA.ATTRIBUTE_OVERRIDES);
//		attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
//		assertNotNull(attributeOverrides);
//		
//		assertSourceContains("@AttributeOverrides");
//		
//		AttributeOverride fooAttributeOverride = attributeOverrides.add(0);
//		fooAttributeOverride.setName("Foo");
//	
//		assertSourceContains("@AttributeOverrides(@AttributeOverride(name=\"Foo\"))");
//
//		AttributeOverride barAttributeOverride = attributeOverrides.add(0);
//		barAttributeOverride.setName("Bar");
//	
//		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"Bar\"), @AttributeOverride(name=\"Foo\")})");
//
//	}
//	
//	public void testMove() throws Exception {
//		ICompilationUnit cu = this.createTestAttributeOverrideWithColumnOnField();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(cu); 
//		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
//		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
//		
//		AttributeOverride fooAttributeOverride = attributeOverrides.add(1);
//		fooAttributeOverride.setName("Foo");
//		
//		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\"), @AttributeOverride(name=\"Foo\")})");
//		
//		attributeOverrides.move(0, 1);
//		
//		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"Foo\"), @AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")})");
//	}
//	
//	public void testRemove() throws Exception {
//		ICompilationUnit cu = this.createTestAttributeOverrideWithColumnOnField();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(cu); 
//		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
//		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
//		
//		AttributeOverride fooAttributeOverride = attributeOverrides.add(1);
//		fooAttributeOverride.setName("Foo");
//		
//		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\"), @AttributeOverride(name=\"Foo\")})");
//		
//		attributeOverrides.remove(0);
//
//		assertSourceContains("@AttributeOverrides(@AttributeOverride(name=\"Foo\"))");
//
//		attributeOverrides.remove(0);
//		
//		assertSourceContains("@AttributeOverrides()");
//
//	}
	


}
