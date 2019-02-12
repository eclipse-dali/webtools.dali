/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollectionAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class ElementCollection2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	public ElementCollection2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestElementCollection() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.ELEMENT_COLLECTION);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection");
			}
		});
	}
	
	private ICompilationUnit createTestElementCollectionWithFetch() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.ELEMENT_COLLECTION, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection(fetch = FetchType.EAGER)");
			}
		});
	}

	private ICompilationUnit createTestElementCollectionWithTargetClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.ELEMENT_COLLECTION);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection(targetClass = AnnotationTestType.class)");
			}
		});
	}

	private ICompilationUnit createTestElementCollectionWithPrimitiveTargetClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.ELEMENT_COLLECTION);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection(targetClass = byte.class)");
			}
		});
	}

	public void testElementCollection() throws Exception {
		ICompilationUnit cu = this.createTestElementCollection();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertNotNull(elementCollection);
	}
	
	public void testGetFetch() throws Exception {
		ICompilationUnit cu = this.createTestElementCollectionWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertEquals(FetchType.EAGER, elementCollection.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestElementCollectionWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertEquals(FetchType.EAGER, elementCollection.getFetch());
		
		elementCollection.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, elementCollection.getFetch());
		
		assertSourceContains("@ElementCollection(fetch = LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestElementCollectionWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertEquals(FetchType.EAGER, elementCollection.getFetch());
		
		elementCollection.setFetch(null);
		assertNull(elementCollection.getFetch());
		
		assertSourceContains("@ElementCollection", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
	
	
	public void testGetTargetClass() throws Exception {
		ICompilationUnit cu = this.createTestElementCollectionWithTargetClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertEquals(TYPE_NAME, elementCollection.getTargetClass());
	}
	
	public void testSetTargetClass() throws Exception {
		ICompilationUnit cu = this.createTestElementCollectionWithTargetClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertEquals(TYPE_NAME, elementCollection.getTargetClass());
		
		elementCollection.setTargetClass("Foo");
		
		assertSourceContains("@ElementCollection(targetClass = Foo.class)", cu);
	}
	
	public void testSetTargetClassNull() throws Exception {
		ICompilationUnit cu = this.createTestElementCollectionWithTargetClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertEquals(TYPE_NAME, elementCollection.getTargetClass());
		
		elementCollection.setTargetClass(null);
		
		assertSourceContains("@ElementCollection", cu);
		assertSourceDoesNotContain("targetClass", cu);
	}
	
	
	public void testGetFullyQualifiedTargetClass() throws Exception {
		ICompilationUnit cu = this.createTestElementCollectionWithTargetClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, elementCollection.getFullyQualifiedTargetClassName());
		
		elementCollection.setTargetClass("Foo");
		
		assertSourceContains("@ElementCollection(targetClass = Foo.class)", cu);
		
		assertEquals("Foo", elementCollection.getTargetClass());
		
		assertEquals("Foo", elementCollection.getFullyQualifiedTargetClassName()); //bug 196200 changed this
	}

	public void testGetPrimitiveTargetClass() throws Exception {
		ICompilationUnit cu = this.createTestElementCollectionWithPrimitiveTargetClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertEquals("byte", elementCollection.getTargetClass());
	}

	public void testSetPrimitiveTargetClass() throws Exception {
		ICompilationUnit cu = this.createTestElementCollection();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		ElementCollectionAnnotation2_0 elementCollection = (ElementCollectionAnnotation2_0) resourceField.getAnnotation(JPA2_0.ELEMENT_COLLECTION);
		assertEquals(null, elementCollection.getTargetClass());

		elementCollection.setTargetClass("int");

		assertSourceContains("@ElementCollection(targetClass = int.class)", cu);
	}
}
