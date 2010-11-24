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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


@SuppressWarnings("nls")
public class GenericJavaPersistentClassTests extends JaxbContextModelTestCase
{
	
	public GenericJavaPersistentClassTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
		});
	}
	
	private ICompilationUnit createXmlTypeWithAccessorType() throws CoreException {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ACCESS_TYPE, JAXB.XML_ACCESSOR_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
				sb.append("@XmlAccessorType(value = XmlAccessType.PROPERTY)");
			}
		});
	}
	
	private ICompilationUnit createXmlTypeWithAccessorOrder() throws CoreException {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ACCESS_ORDER, JAXB.XML_ACCESSOR_ORDER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
				sb.append("@XmlAccessorOrder(value = XmlAccessOrder.ALPHABETICAL)");
			}
		});
	}

	public void testModifyFactoryClass() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(persistentClass.getFactoryClass());
		
		persistentClass.setFactoryClass("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryClass());
		assertEquals("foo", persistentClass.getFactoryClass());
		
		persistentClass.setFactoryClass(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getFactoryClass());
		assertNull(persistentClass.getFactoryClass());
	
		//add another annotation so that the context model does not get blown away
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set factoryClass again, this time starting with no XmlType annotation
		persistentClass.setFactoryClass("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryClass());
		assertEquals("foo", persistentClass.getFactoryClass());
	}
	
	public void testUpdateFactoryClass() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(persistentClass.getFactoryClass());
		
		
		//add a factoryClass member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addXmlTypeTypeMemberValuePair(declaration, JAXB.XML_TYPE__FACTORY_CLASS, "Foo");
			}
		});
		assertEquals("Foo", persistentClass.getFactoryClass());

		//remove the factoryClass member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentClassTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentClassTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(persistentClass.getFactoryClass());
	}

	public void testModifyFactoryMethod() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(persistentClass.getFactoryMethod());
		
		persistentClass.setFactoryMethod("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryMethod());
		assertEquals("foo", persistentClass.getFactoryMethod());
		
		persistentClass.setFactoryMethod(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getFactoryMethod());
		assertNull(persistentClass.getFactoryMethod());
	
		//add another annotation so that the context model does not get blown away
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set factoryMethod again, this time starting with no XmlType annotation
		persistentClass.setFactoryMethod("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryMethod());
		assertEquals("foo", persistentClass.getFactoryMethod());
	}
	
	public void testUpdateFactoryMethod() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(persistentClass.getFactoryMethod());
		
		
		//add a factoryMethod member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__FACTORY_METHOD, "foo");
			}
		});
		assertEquals("foo", persistentClass.getFactoryMethod());

		//remove the factoryMethod member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentClassTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentClassTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(persistentClass.getFactoryMethod());
	}

	public void testModifySchemaTypeName() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(persistentClass.getSchemaTypeName());
		
		persistentClass.setSchemaTypeName("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", persistentClass.getSchemaTypeName());
		
		persistentClass.setSchemaTypeName(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getName());
		assertNull(persistentClass.getSchemaTypeName());
	
		//add another annotation so that the context model does not get blown away
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set name again, this time starting with no XmlType annotation
		persistentClass.setSchemaTypeName("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", persistentClass.getSchemaTypeName());
	}
	
	public void testUpdateSchemaTypeName() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(persistentClass.getSchemaTypeName());
		
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAME, "foo");
			}
		});
		assertEquals("foo", persistentClass.getSchemaTypeName());

		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentClassTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentClassTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(persistentClass.getSchemaTypeName());
	}

	public void testModifyNamespace() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(persistentClass.getNamespace());
		
		persistentClass.setNamespace("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", persistentClass.getNamespace());
		
		persistentClass.setNamespace(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getNamespace());
		assertNull(persistentClass.getNamespace());
	
		//add another annotation so that the context model does not get blown away
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set namespace again, this time starting with no XmlType annotation
		persistentClass.setNamespace("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", persistentClass.getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(persistentClass.getNamespace());
		
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", persistentClass.getNamespace());

		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentClassTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentClassTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(persistentClass.getNamespace());
	}
	
	//TODO test super type with @XmlAccessorType
	//TODO test package-info.java with @XmlAccessorType
	public void testModifyAccessType() throws Exception {
		createXmlTypeWithAccessorType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertEquals(XmlAccessType.PROPERTY, persistentClass.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, persistentClass.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getDefaultAccessType());
		
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		XmlAccessorTypeAnnotation accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.FIELD, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.FIELD, persistentClass.getAccessType());

		persistentClass.setSpecifiedAccessType(XmlAccessType.PUBLIC_MEMBER);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PUBLIC_MEMBER, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getAccessType());

		persistentClass.setSpecifiedAccessType(XmlAccessType.NONE);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.NONE, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.NONE, persistentClass.getAccessType());
		
		persistentClass.setSpecifiedAccessType(null);
		accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
		assertNull(accessorTypeAnnotation);
		assertNull(persistentClass.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getDefaultAccessType());
	}
	
	public void testUpdateAccessType() throws Exception {
		createXmlTypeWithAccessorType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertEquals(XmlAccessType.PROPERTY, persistentClass.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, persistentClass.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getDefaultAccessType());
		
		//set the accesser type value to FIELD
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__FIELD);
			}
		});
		assertEquals(XmlAccessType.FIELD, persistentClass.getAccessType());

		//set the accesser type value to PUBLIC_MEMBER
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
			}
		});
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getAccessType());

		//set the accesser type value to NONE
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__NONE);
			}
		});
		assertEquals(XmlAccessType.NONE, persistentClass.getAccessType());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME);
			}
		});
		assertNull(persistentClass.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getDefaultAccessType());
	}
	
	public void testModifyAccessOrder() throws Exception {
		createXmlTypeWithAccessorOrder();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertEquals(XmlAccessOrder.ALPHABETICAL, persistentClass.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, persistentClass.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getDefaultAccessOrder());
		
		persistentClass.setSpecifiedAccessOrder(XmlAccessOrder.UNDEFINED);
		XmlAccessorOrderAnnotation accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.UNDEFINED, accessorOrderAnnotation.getValue());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getAccessOrder());
		
		persistentClass.setSpecifiedAccessOrder(null);
		accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		assertNull(accessorOrderAnnotation);
		assertNull(persistentClass.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getDefaultAccessOrder());
	}
	
	public void testUpdateAccessOrder() throws Exception {
		createXmlTypeWithAccessorOrder();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertEquals(XmlAccessOrder.ALPHABETICAL, persistentClass.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, persistentClass.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getDefaultAccessOrder());
		
		//set the access order value to UNDEFINED
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.setEnumMemberValuePair(declaration, XmlAccessorOrderAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_ORDER__UNDEFINED);
			}
		});
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getAccessOrder());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAccessorOrderAnnotation.ANNOTATION_NAME);
			}
		});
		assertNull(persistentClass.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getDefaultAccessOrder());
	}

	public void testGetPropOrder() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		ListIterator<String> props = persistentClass.getPropOrder().iterator();
		assertFalse(props.hasNext());

		//add 2 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addProp(declaration, 0, "bar");
				GenericJavaPersistentClassTests.this.addProp(declaration, 1, "foo");
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertFalse(props.hasNext());
	}
	
	protected void addProp(ModifiedDeclaration declaration, int index, String prop) {
		this.addArrayElement(declaration, JAXB.XML_TYPE, index, JAXB.XML_TYPE__PROP_ORDER, this.newStringLiteral(declaration.getAst(), prop));		
	}

	public void testGetPropOrderSize() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		assertEquals(0, persistentClass.getPropOrderSize());

		//add 2 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addProp(declaration, 0, "bar");
				GenericJavaPersistentClassTests.this.addProp(declaration, 1, "foo");
			}
		});
		assertEquals(2, persistentClass.getPropOrderSize());
	}

	public void testAddProp() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		persistentClass.addProp(0, "bar");
		persistentClass.addProp(0, "foo");
		persistentClass.addProp(0, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		ListIterator<String> props = xmlTypeAnnotation.getPropOrder().iterator();

		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());
	}

	public void testAddProp2() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		persistentClass.addProp(0, "bar");
		persistentClass.addProp(1, "foo");
		persistentClass.addProp(0, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		ListIterator<String> props = xmlTypeAnnotation.getPropOrder().iterator();

		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertFalse(props.hasNext());
	}

	public void testRemoveProp() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		persistentClass.addProp(0, "bar");
		persistentClass.addProp(1, "foo");
		persistentClass.addProp(2, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);

		persistentClass.removeProp(1);

		ListIterator<String> resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("bar", resourceProps.next());		
		assertEquals("baz", resourceProps.next());
		assertFalse(resourceProps.hasNext());

		persistentClass.removeProp(1);
		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("bar", resourceProps.next());
		assertFalse(resourceProps.hasNext());

		persistentClass.removeProp(0);
		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertFalse(resourceProps.hasNext());
	}

	public void testMoveProp() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		persistentClass.addProp(0, "bar");
		persistentClass.addProp(1, "foo");
		persistentClass.addProp(2, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);

		assertEquals(3, xmlTypeAnnotation.getPropOrderSize());		

		persistentClass.moveProp(2, 0);
		ListIterator<String> props = persistentClass.getPropOrder().iterator();
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());		
		assertFalse(props.hasNext());

		ListIterator<String> resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("foo", resourceProps.next());
		assertEquals("baz", resourceProps.next());
		assertEquals("bar", resourceProps.next());


		persistentClass.moveProp(0, 1);
		props = persistentClass.getPropOrder().iterator();
		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());		
		assertFalse(props.hasNext());

		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("baz", resourceProps.next());
		assertEquals("foo", resourceProps.next());
		assertEquals("bar", resourceProps.next());
	}

	public void testSyncXmlNsPrefixes() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		ListIterator<String> props = persistentClass.getPropOrder().iterator();
		assertFalse(props.hasNext());

		//add 3 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addProp(declaration, 0, "bar");
				GenericJavaPersistentClassTests.this.addProp(declaration, 1, "foo");
				GenericJavaPersistentClassTests.this.addProp(declaration, 2, "baz");
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.moveProp(declaration, 2, 0);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.moveProp(declaration, 0, 1);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeProp(declaration, 1);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeProp(declaration, 1);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeProp(declaration, 0);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertFalse(props.hasNext());
	}

	protected void addXmlTypeMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlTypeAnnotation(declaration), name, value);
	}

	protected void addXmlTypeTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlTypeAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

	protected Annotation getXmlTypeAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlTypeAnnotation.ANNOTATION_NAME);
	}

	protected void moveProp(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
		this.moveArrayElement((NormalAnnotation) getXmlTypeAnnotation(declaration), JAXB.XML_TYPE__PROP_ORDER, targetIndex, sourceIndex);
	}

	protected void removeProp(ModifiedDeclaration declaration, int index) {
		this.removeArrayElement((NormalAnnotation) getXmlTypeAnnotation(declaration), JAXB.XML_TYPE__PROP_ORDER, index);
	}

	public void testModifyXmlRootElement() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		assertNull(persistentClass.getRootElement());
		assertFalse(persistentClass.isRootElement());

		persistentClass.setRootElement("foo");
		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlRootElementAnnotation.getName());
		assertEquals("foo", persistentClass.getRootElement().getName());
		assertTrue(persistentClass.isRootElement());

		persistentClass.setRootElement(null);
		xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertNull(xmlRootElementAnnotation);
		assertNull(persistentClass.getRootElement());
		assertFalse(persistentClass.isRootElement());
	}

	public void testUpdateXmlRootElement() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(persistentClass.getRootElement());
		assertFalse(persistentClass.isRootElement());
		
		
		//add a XmlRootElement annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation annotation = GenericJavaPersistentClassTests.this.addNormalAnnotation(declaration.getDeclaration(), JAXB.XML_ROOT_ELEMENT);
				GenericJavaPersistentClassTests.this.addMemberValuePair(annotation, JAXB.XML_ROOT_ELEMENT__NAME, "foo");
			}
		});
		assertEquals("foo", persistentClass.getRootElement().getName());
		assertTrue(persistentClass.isRootElement());

		//remove the XmlRootElement annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, JAXB.XML_ROOT_ELEMENT);
			}
		});
		assertNull(persistentClass.getRootElement());
		assertFalse(persistentClass.isRootElement());
	}
}