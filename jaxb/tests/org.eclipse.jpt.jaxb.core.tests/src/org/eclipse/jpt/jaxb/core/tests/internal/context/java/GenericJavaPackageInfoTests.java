/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;


@SuppressWarnings("nls")
public class GenericJavaPackageInfoTests extends JaxbContextModelTestCase
{
	
	public GenericJavaPackageInfoTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createPackageInfoWithAccessorType() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorType(value = XmlAccessType.PROPERTY)",
				JAXB.XML_ACCESS_TYPE, JAXB.XML_ACCESSOR_TYPE);
	}
	
	private ICompilationUnit createPackageInfoWithAccessorOrder() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorOrder(value = XmlAccessOrder.ALPHABETICAL)",
				JAXB.XML_ACCESS_ORDER, JAXB.XML_ACCESSOR_ORDER);
	}
	
	public void testModifyAccessType() throws Exception {
		createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlAccessType.PROPERTY, contextPackageInfo.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, contextPackageInfo.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getDefaultAccessType());
		
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		XmlAccessorTypeAnnotation accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourcePackage.getAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.FIELD, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.FIELD, contextPackageInfo.getAccessType());

		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.PUBLIC_MEMBER);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PUBLIC_MEMBER, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getAccessType());

		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.NONE);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.NONE, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.NONE, contextPackageInfo.getAccessType());
		
		contextPackageInfo.setSpecifiedAccessType(null);
		accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourcePackage.getAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
		assertNull(accessorTypeAnnotation);
		assertNull(contextPackageInfo.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getDefaultAccessType());
	}
	
	public void testUpdateAccessType() throws Exception {
		createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlAccessType.PROPERTY, contextPackageInfo.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, contextPackageInfo.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getDefaultAccessType());
		
		//set the accesser type value to FIELD
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__FIELD);
			}
		});
		assertEquals(XmlAccessType.FIELD, contextPackageInfo.getAccessType());

		//set the accesser type value to PUBLIC_MEMBER
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
			}
		});
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getAccessType());

		//set the accesser type value to NONE
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__NONE);
			}
		});
		assertEquals(XmlAccessType.NONE, contextPackageInfo.getAccessType());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.removeXmlAccessorTypeAnnotation(declaration);
			}
		});
		assertNull(contextPackageInfo.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getDefaultAccessType());
	}
	
	public void testModifyAccessOrder() throws Exception {
		createPackageInfoWithAccessorOrder();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlAccessOrder.ALPHABETICAL, contextPackageInfo.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, contextPackageInfo.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getDefaultAccessOrder());
		
		contextPackageInfo.setSpecifiedAccessOrder(XmlAccessOrder.UNDEFINED);
		XmlAccessorOrderAnnotation accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourcePackage.getAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.UNDEFINED, accessorOrderAnnotation.getValue());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getAccessOrder());
		
		contextPackageInfo.setSpecifiedAccessOrder(null);
		accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourcePackage.getAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		assertNull(accessorOrderAnnotation);
		assertNull(contextPackageInfo.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getDefaultAccessOrder());
	}
	
	public void testUpdateAccessOrder() throws Exception {
		createPackageInfoWithAccessorOrder();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlAccessOrder.ALPHABETICAL, contextPackageInfo.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, contextPackageInfo.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getDefaultAccessOrder());
		
		//set the access order value to UNDEFINED
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.setEnumMemberValuePair(declaration, XmlAccessorOrderAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_ORDER__UNDEFINED);
			}
		});
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getAccessOrder());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.removeXmlAccessorOrderAnnotation(declaration);
			}
		});
		assertNull(contextPackageInfo.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getDefaultAccessOrder());
	}

	protected void setEnumMemberValuePair(ModifiedDeclaration declaration, String annotationName, String enumValue) {
		this.setEnumMemberValuePair((NormalAnnotation) declaration.getAnnotationNamed(annotationName), "value", enumValue);

	}

	//add another package annotation so that the context model object doesn't get removed when 
	//removing the XmlAccessorType annotation. Only "annotated" packages are added to the context model
	protected void removeXmlAccessorTypeAnnotation(ModifiedDeclaration declaration) {
		this.addMarkerAnnotation(declaration.getDeclaration(), XmlSchemaAnnotation.ANNOTATION_NAME);
		this.removeAnnotation(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME);		
	}

	//add another package annotation so that the context model object doesn't get removed when 
	//removing the XmlAccessorOrder annotation. Only "annotated" packages are added to the context model
	protected void removeXmlAccessorOrderAnnotation(ModifiedDeclaration declaration) {
		this.addMarkerAnnotation(declaration.getDeclaration(), XmlSchemaAnnotation.ANNOTATION_NAME);
		this.removeAnnotation(declaration, XmlAccessorOrderAnnotation.ANNOTATION_NAME);		
	}

	public void testGetXmlSchemaTypes() throws Exception {
		this.createPackageInfoWithAccessorOrder();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		ListIterable<XmlSchemaType> xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
		assertFalse(xmlSchemaTypes.iterator().hasNext());

		//add 2 XmlSchemaTypes
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.addXmlSchemaType(declaration, 0, "bar");
				GenericJavaPackageInfoTests.this.addXmlSchemaType(declaration, 1, "foo");
			}
		});

		xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
		ListIterator<XmlSchemaType> xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
		assertTrue(xmlSchemaTypesIterator.hasNext());
		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
		assertFalse(xmlSchemaTypesIterator.hasNext());
	}
	
	protected void addXmlSchemaType(ModifiedDeclaration declaration, int index, String name) {
		NormalAnnotation arrayElement = this.newXmlSchemaTypeAnnotation(declaration.getAst(), name);
		this.addArrayElement(declaration, JAXB.XML_SCHEMA_TYPES, index, JAXB.XML_SCHEMA_TYPES__VALUE, arrayElement);		
	}

	protected NormalAnnotation newXmlSchemaTypeAnnotation(AST ast, String name) {
		NormalAnnotation annotation = this.newNormalAnnotation(ast, JAXB.XML_SCHEMA_TYPE);
		this.addMemberValuePair(annotation, JAXB.XML_SCHEMA_TYPE__NAME, name);
		return annotation;
	}

	public void testGetXmlSchemaTypesSize() throws Exception {
		this.createPackageInfoWithAccessorOrder();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertEquals(0, contextPackageInfo.getXmlSchemaTypesSize());

		//add 2 XmlSchemaTypes
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.addXmlSchemaType(declaration, 0, "bar");
				GenericJavaPackageInfoTests.this.addXmlSchemaType(declaration, 1, "foo");
			}
		});
		assertEquals(2, contextPackageInfo.getXmlSchemaTypesSize());
	}

	public void testAddXmlSchemaType() throws Exception {
		//create a package info with an annotation other than XmlSchema to test
		//adding things to the null schema annotation
		this.createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		contextPackageInfo.addXmlSchemaType(0).setName("bar");
		contextPackageInfo.addXmlSchemaType(0).setName("foo");
		contextPackageInfo.addXmlSchemaType(0).setName("baz");

		Iterator<XmlSchemaTypeAnnotation> xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);

		assertEquals("baz", xmlSchemaTypes.next().getName());
		assertEquals("foo", xmlSchemaTypes.next().getName());
		assertEquals("bar", xmlSchemaTypes.next().getName());
		assertFalse(xmlSchemaTypes.hasNext());
	}

	@SuppressWarnings("unchecked")
	protected Iterator<XmlSchemaTypeAnnotation> getSchemaTypeAnnotations(JavaResourcePackage resourcePackage) {
		return (Iterator<XmlSchemaTypeAnnotation>) resourcePackage.getAnnotations(JAXB.XML_SCHEMA_TYPE, JAXB.XML_SCHEMA_TYPES).iterator();
	}

	public void testAddXmlSchemaType2() throws Exception {
		//create a package info with an annotation other than XmlSchema to test
		//adding things to the null schema annotation
		this.createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		contextPackageInfo.addXmlSchemaType(0).setName("bar");
		contextPackageInfo.addXmlSchemaType(1).setName("foo");
		contextPackageInfo.addXmlSchemaType(0).setName("baz");

		Iterator<XmlSchemaTypeAnnotation> xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);

		assertEquals("baz", xmlSchemaTypes.next().getName());
		assertEquals("bar", xmlSchemaTypes.next().getName());
		assertEquals("foo", xmlSchemaTypes.next().getName());
		assertFalse(xmlSchemaTypes.hasNext());
	}

	public void testRemoveXmlSchemaType() throws Exception {
		this.createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		contextPackageInfo.addXmlSchemaType(0).setName("bar");
		contextPackageInfo.addXmlSchemaType(1).setName("foo");
		contextPackageInfo.addXmlSchemaType(2).setName("baz");

		Iterator<XmlSchemaTypeAnnotation> xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
		assertEquals("bar", xmlSchemaTypes.next().getName());		
		assertEquals("foo", xmlSchemaTypes.next().getName());		
		assertEquals("baz", xmlSchemaTypes.next().getName());
		assertFalse(xmlSchemaTypes.hasNext());

		contextPackageInfo.removeXmlSchemaType(1);

		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
		assertEquals("bar", xmlSchemaTypes.next().getName());		
		assertEquals("baz", xmlSchemaTypes.next().getName());
		assertFalse(xmlSchemaTypes.hasNext());

		contextPackageInfo.removeXmlSchemaType(1);
		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
		assertEquals("bar", xmlSchemaTypes.next().getName());
		assertFalse(xmlSchemaTypes.hasNext());

		contextPackageInfo.removeXmlSchemaType(0);
		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
		assertFalse(xmlSchemaTypes.hasNext());
	}

	public void testMoveXmlSchemaType() throws Exception {
		this.createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		contextPackageInfo.addXmlSchemaType(0).setName("bar");
		contextPackageInfo.addXmlSchemaType(1).setName("foo");
		contextPackageInfo.addXmlSchemaType(2).setName("baz");

		Iterator<XmlSchemaTypeAnnotation> xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
		assertEquals("bar", xmlSchemaTypes.next().getName());		
		assertEquals("foo", xmlSchemaTypes.next().getName());		
		assertEquals("baz", xmlSchemaTypes.next().getName());
		assertFalse(xmlSchemaTypes.hasNext());

		contextPackageInfo.moveXmlSchemaType(2, 0);
		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
		assertEquals("foo", xmlSchemaTypes.next().getName());
		assertEquals("baz", xmlSchemaTypes.next().getName());
		assertEquals("bar", xmlSchemaTypes.next().getName());		
		assertFalse(xmlSchemaTypes.hasNext());

		contextPackageInfo.moveXmlSchemaType(0, 1);
		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
		assertEquals("baz", xmlSchemaTypes.next().getName());
		assertEquals("foo", xmlSchemaTypes.next().getName());
		assertEquals("bar", xmlSchemaTypes.next().getName());		
		assertFalse(xmlSchemaTypes.hasNext());
	}

	public void testSyncXmlSchemaTypes() throws Exception {
		this.createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		ListIterable<XmlSchemaType> xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
		assertFalse(xmlSchemaTypes.iterator().hasNext());

		//add 3 XmlSchemaTypes
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.addXmlSchemaType(declaration, 0, "bar");
				GenericJavaPackageInfoTests.this.addXmlSchemaType(declaration, 1, "foo");
				GenericJavaPackageInfoTests.this.addXmlSchemaType(declaration, 2, "baz");
			}
		});

		xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
		ListIterator<XmlSchemaType> xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
		assertTrue(xmlSchemaTypesIterator.hasNext());
		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
		assertFalse(xmlSchemaTypesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.moveXmlSchemaType(declaration, 2, 0);
			}
		});

		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
		assertTrue(xmlSchemaTypesIterator.hasNext());
		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
		assertFalse(xmlSchemaTypesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.moveXmlSchemaType(declaration, 0, 1);
			}
		});

		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
		assertTrue(xmlSchemaTypesIterator.hasNext());
		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
		assertFalse(xmlSchemaTypesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.removeXmlSchemaType(declaration, 1);
			}
		});

		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
		assertTrue(xmlSchemaTypesIterator.hasNext());
		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
		assertFalse(xmlSchemaTypesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.removeXmlSchemaType(declaration, 1);
			}
		});

		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
		assertTrue(xmlSchemaTypesIterator.hasNext());
		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
		assertFalse(xmlSchemaTypesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.removeXmlSchemaType(declaration, 0);
			}
		});

		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
		assertFalse(xmlSchemaTypesIterator.hasNext());
	}


	protected void moveXmlSchemaType(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
		this.moveArrayElement((NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_SCHEMA_TYPES), JAXB.XML_SCHEMA_TYPES__VALUE, targetIndex, sourceIndex);
	}

	protected void removeXmlSchemaType(ModifiedDeclaration declaration, int index) {
		this.removeArrayElement((NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_SCHEMA_TYPES), JAXB.XML_SCHEMA_TYPES__VALUE, index);
	}
}
