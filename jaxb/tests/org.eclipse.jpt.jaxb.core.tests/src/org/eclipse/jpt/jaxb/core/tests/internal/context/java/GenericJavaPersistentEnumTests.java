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

import java.beans.Introspector;
import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaPersistentEnumTests extends JaxbContextModelTestCase
{
	
	public GenericJavaPersistentEnumTests(String name) {
		super(name);
	}

	private ICompilationUnit createEnumWithXmlEnum() throws Exception {
		return createTestEnum(new DefaultEnumAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ENUM);
			}
			@Override
			public void appendEnumAnnotationTo(StringBuilder sb) {
				sb.append("@XmlEnum");
			}
		});
	}

	public void testModifyFactoryClass() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
	
		assertNull(contextEnum.getFactoryClass());
		
		contextEnum.setFactoryClass("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryClass());
		assertEquals("foo", contextEnum.getFactoryClass());
		
		contextEnum.setFactoryClass(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getFactoryClass());
		assertNull(contextEnum.getFactoryClass());
	
		resourceEnum.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(contextEnum.getFactoryClass());
	}
	
	public void testUpdateFactoryClass() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
	
		assertNull(contextEnum.getFactoryClass());
		
		
		//add a factoryClass member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
				GenericJavaPersistentEnumTests.this.addXmlTypeTypeMemberValuePair(declaration, JAXB.XML_TYPE__FACTORY_CLASS, "Foo");
			}
		});
		assertEquals("Foo", contextEnum.getFactoryClass());

		//remove the factoryClass member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentEnumTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentEnumTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(contextEnum.getFactoryClass());
	}

	public void testModifyFactoryMethod() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
	
		assertNull(contextEnum.getFactoryMethod());
		
		contextEnum.setFactoryMethod("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryMethod());
		assertEquals("foo", contextEnum.getFactoryMethod());
		
		contextEnum.setFactoryMethod(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getFactoryMethod());
		assertNull(contextEnum.getFactoryMethod());
	
		resourceEnum.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set factoryMethod again, this time starting with no XmlType annotation
		contextEnum.setFactoryMethod("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryMethod());
		assertEquals("foo", contextEnum.getFactoryMethod());
	}
	
	public void testUpdateFactoryMethod() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
	
		assertNull(contextEnum.getFactoryMethod());
		
		
		//add a factoryMethod member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
				GenericJavaPersistentEnumTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__FACTORY_METHOD, "foo");
			}
		});
		assertEquals("foo", contextEnum.getFactoryMethod());

		//remove the factoryMethod member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentEnumTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentEnumTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(contextEnum.getFactoryMethod());
	}

	public void testModifySchemaTypeName() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
		String defaultXmlTypeName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(contextEnum.getSpecifiedXmlTypeName());
		assertEquals(defaultXmlTypeName, contextEnum.getDefaultXmlTypeName());
		assertEquals(defaultXmlTypeName, contextEnum.getXmlTypeName());
		
		contextEnum.setSpecifiedXmlTypeName("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", contextEnum.getSpecifiedXmlTypeName());
		assertEquals("foo", contextEnum.getXmlTypeName());
		
		contextEnum.setSpecifiedXmlTypeName(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getName());
		assertNull(contextEnum.getSpecifiedXmlTypeName());
		assertEquals(defaultXmlTypeName, contextEnum.getXmlTypeName());
		
		resourceEnum.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set name again, this time starting with no XmlType annotation
		contextEnum.setSpecifiedXmlTypeName("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", contextEnum.getSpecifiedXmlTypeName());
		assertEquals("foo", contextEnum.getXmlTypeName());
	}
	
	public void testUpdateSchemaTypeName() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
		String defaultXmlTypeName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(contextEnum.getSpecifiedXmlTypeName());
		assertEquals(defaultXmlTypeName, contextEnum.getDefaultXmlTypeName());
		assertEquals(defaultXmlTypeName, contextEnum.getXmlTypeName());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
				GenericJavaPersistentEnumTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAME, "foo");
			}
		});
		assertEquals("foo", contextEnum.getSpecifiedXmlTypeName());
		assertEquals("foo", contextEnum.getXmlTypeName());
		
		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentEnumTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentEnumTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(contextEnum.getSpecifiedXmlTypeName());
		assertEquals(defaultXmlTypeName, contextEnum.getXmlTypeName());
	}

	public void testModifyNamespace() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
		
		assertNull(contextEnum.getSpecifiedNamespace());
		assertEquals("", contextEnum.getDefaultNamespace());
		assertEquals("", contextEnum.getNamespace());
		
		contextEnum.setSpecifiedNamespace("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", contextEnum.getSpecifiedNamespace());
		assertEquals("foo", contextEnum.getNamespace());
		
		contextEnum.setSpecifiedNamespace(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getNamespace());
		assertNull(contextEnum.getSpecifiedNamespace());
		assertEquals("", contextEnum.getNamespace());
		
		resourceEnum.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set namespace again, this time starting with no XmlType annotation
		contextEnum.setSpecifiedNamespace("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", contextEnum.getSpecifiedNamespace());
		assertEquals("foo", contextEnum.getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
		
		assertNull(contextEnum.getSpecifiedNamespace());
		assertEquals("", contextEnum.getDefaultNamespace());
		assertEquals("", contextEnum.getNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
				GenericJavaPersistentEnumTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", contextEnum.getSpecifiedNamespace());
		assertEquals("foo", contextEnum.getNamespace());
		
		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentEnumTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentEnumTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(contextEnum.getSpecifiedNamespace());
		assertEquals("", contextEnum.getNamespace());
	}

	public void testGetPropOrder() throws Exception {
		this.createEnumWithXmlEnum();
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();

		ListIterator<String> props = contextEnum.getPropOrder().iterator();
		assertFalse(props.hasNext());

		//add 2 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.addProp(declaration, 0, "bar");
				GenericJavaPersistentEnumTests.this.addProp(declaration, 1, "foo");
			}
		});

		props = contextEnum.getPropOrder().iterator();
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertFalse(props.hasNext());
	}

	protected void addProp(ModifiedDeclaration declaration, int index, String prop) {
		this.addArrayElement(declaration, JAXB.XML_TYPE, index, JAXB.XML_TYPE__PROP_ORDER, this.newStringLiteral(declaration.getAst(), prop));		
	}

	public void testGetPropOrderSize() throws Exception {
		this.createEnumWithXmlEnum();
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();

		assertEquals(0, contextEnum.getPropOrderSize());

		//add 2 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.addProp(declaration, 0, "bar");
				GenericJavaPersistentEnumTests.this.addProp(declaration, 1, "foo");
			}
		});
		assertEquals(2, contextEnum.getPropOrderSize());
	}

	public void testAddProp() throws Exception {
		this.createEnumWithXmlEnum();
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();

		contextEnum.addProp(0, "bar");
		contextEnum.addProp(0, "foo");
		contextEnum.addProp(0, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		ListIterator<String> props = xmlTypeAnnotation.getPropOrder().iterator();

		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());
	}

	public void testAddProp2() throws Exception {
		this.createEnumWithXmlEnum();
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();

		contextEnum.addProp(0, "bar");
		contextEnum.addProp(1, "foo");
		contextEnum.addProp(0, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		ListIterator<String> props = xmlTypeAnnotation.getPropOrder().iterator();

		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertFalse(props.hasNext());
	}

	public void testRemoveProp() throws Exception {
		this.createEnumWithXmlEnum();
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();

		contextEnum.addProp(0, "bar");
		contextEnum.addProp(1, "foo");
		contextEnum.addProp(2, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);

		contextEnum.removeProp(1);

		ListIterator<String> resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("bar", resourceProps.next());		
		assertEquals("baz", resourceProps.next());
		assertFalse(resourceProps.hasNext());

		contextEnum.removeProp(1);
		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("bar", resourceProps.next());
		assertFalse(resourceProps.hasNext());

		contextEnum.removeProp(0);
		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertFalse(resourceProps.hasNext());
	}

	public void testMoveProp() throws Exception {
		this.createEnumWithXmlEnum();
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();

		contextEnum.addProp(0, "bar");
		contextEnum.addProp(1, "foo");
		contextEnum.addProp(2, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);

		assertEquals(3, xmlTypeAnnotation.getPropOrderSize());		

		contextEnum.moveProp(2, 0);
		ListIterator<String> props = contextEnum.getPropOrder().iterator();
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());		
		assertFalse(props.hasNext());

		ListIterator<String> resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("foo", resourceProps.next());
		assertEquals("baz", resourceProps.next());
		assertEquals("bar", resourceProps.next());


		contextEnum.moveProp(0, 1);
		props = contextEnum.getPropOrder().iterator();
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
		this.createEnumWithXmlEnum();
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();

		ListIterator<String> props = contextEnum.getPropOrder().iterator();
		assertFalse(props.hasNext());

		//add 3 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.addProp(declaration, 0, "bar");
				GenericJavaPersistentEnumTests.this.addProp(declaration, 1, "foo");
				GenericJavaPersistentEnumTests.this.addProp(declaration, 2, "baz");
			}
		});

		props = contextEnum.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.moveProp(declaration, 2, 0);
			}
		});

		props = contextEnum.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.moveProp(declaration, 0, 1);
			}
		});

		props = contextEnum.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.removeProp(declaration, 1);
			}
		});

		props = contextEnum.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.removeProp(declaration, 1);
			}
		});

		props = contextEnum.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.removeProp(declaration, 0);
			}
		});

		props = contextEnum.getPropOrder().iterator();
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

	protected void addXmlEnumTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlEnumAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

	protected Annotation getXmlTypeAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlTypeAnnotation.ANNOTATION_NAME);
	}

	protected Annotation getXmlEnumAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlEnumAnnotation.ANNOTATION_NAME);
	}

	protected void moveProp(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
		this.moveArrayElement((NormalAnnotation) getXmlTypeAnnotation(declaration), JAXB.XML_TYPE__PROP_ORDER, targetIndex, sourceIndex);
	}

	protected void removeProp(ModifiedDeclaration declaration, int index) {
		this.removeArrayElement((NormalAnnotation) getXmlTypeAnnotation(declaration), JAXB.XML_TYPE__PROP_ORDER, index);
	}

	public void testModifyXmlRootElement() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();

		assertNull(contextEnum.getRootElement());
		assertFalse(contextEnum.isRootElement());

		contextEnum.setRootElement("foo");
		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceEnum.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlRootElementAnnotation.getName());
		assertEquals("foo", contextEnum.getRootElement().getName());
		assertTrue(contextEnum.isRootElement());

		contextEnum.setRootElement(null);
		xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceEnum.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertNull(xmlRootElementAnnotation);
		assertNull(contextEnum.getRootElement());
		assertFalse(contextEnum.isRootElement());
	}

	public void testUpdateXmlRootElement() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
	
		assertNull(contextEnum.getRootElement());
		assertFalse(contextEnum.isRootElement());
		
		
		//add a XmlRootElement annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation annotation = GenericJavaPersistentEnumTests.this.addNormalAnnotation(declaration.getDeclaration(), JAXB.XML_ROOT_ELEMENT);
				GenericJavaPersistentEnumTests.this.addMemberValuePair(annotation, JAXB.XML_ROOT_ELEMENT__NAME, "foo");
			}
		});
		assertEquals("foo", contextEnum.getRootElement().getName());
		assertTrue(contextEnum.isRootElement());

		//remove the XmlRootElement annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.removeAnnotation(declaration, JAXB.XML_ROOT_ELEMENT);
			}
		});
		assertNull(contextEnum.getRootElement());
		assertFalse(contextEnum.isRootElement());
	}

	public void testModifyEnumType() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
	
		assertNull(contextEnum.getEnumType());
		
		contextEnum.setEnumType("Integer");
		XmlEnumAnnotation xmlEnumAnnotation = (XmlEnumAnnotation) resourceEnum.getAnnotation(XmlEnumAnnotation.ANNOTATION_NAME);
		assertEquals("Integer", xmlEnumAnnotation.getValue());
		assertEquals("Integer", contextEnum.getEnumType());
		
		contextEnum.setEnumType(null);
		xmlEnumAnnotation = (XmlEnumAnnotation) resourceEnum.getAnnotation(XmlEnumAnnotation.ANNOTATION_NAME);
		assertNull(xmlEnumAnnotation.getValue());
		assertNull(contextEnum.getEnumType());
	
		resourceEnum.addAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		resourceEnum.removeAnnotation(XmlEnumAnnotation.ANNOTATION_NAME);
		contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		assertNull(contextEnum.getEnumType());
	}
	
	public void testUpdateEnumType() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
	
		assertNull(contextEnum.getEnumType());
		
		
		//add a factoryClass member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.addXmlEnumTypeMemberValuePair(declaration, JAXB.XML_ENUM__VALUE, "String");
			}
		});
		assertEquals("String", contextEnum.getEnumType());

		//remove the factoryClass member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlEnumAnnotation = (NormalAnnotation) GenericJavaPersistentEnumTests.this.getXmlEnumAnnotation(declaration);
				GenericJavaPersistentEnumTests.this.values(xmlEnumAnnotation).remove(0);
			}
		});
		assertNull(contextEnum.getEnumType());
	}

	public void testUpdateEnumConstants() throws Exception {
		createEnumWithXmlEnum();

		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
		
		assertEquals(2, contextEnum.getEnumConstantsSize());
		Iterator<JaxbEnumConstant> enumConstants = contextEnum.getEnumConstants().iterator();
		JaxbEnumConstant enumConstant = enumConstants.next();
		assertEquals("SUNDAY", enumConstant.getName());
		enumConstant = enumConstants.next();
		assertEquals("MONDAY", enumConstant.getName());
		assertFalse(enumConstants.hasNext());

		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.addEnumConstant((EnumDeclaration) declaration.getDeclaration(), "TUESDAY");
				GenericJavaPersistentEnumTests.this.addEnumConstant((EnumDeclaration) declaration.getDeclaration(), "WEDNESDAY");
			}
		});
		assertEquals(4, contextEnum.getEnumConstantsSize());
		enumConstants = contextEnum.getEnumConstants().iterator();
		enumConstant = enumConstants.next();
		assertEquals("SUNDAY", enumConstant.getName());
		enumConstant = enumConstants.next();
		assertEquals("MONDAY", enumConstant.getName());
		enumConstant = enumConstants.next();
		assertEquals("TUESDAY", enumConstant.getName());
		enumConstant = enumConstants.next();
		assertEquals("WEDNESDAY", enumConstant.getName());
		assertFalse(enumConstants.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.removeEnumConstant((EnumDeclaration) declaration.getDeclaration(), "SUNDAY");
			}
		});
		assertEquals(3, contextEnum.getEnumConstantsSize());
		enumConstants = contextEnum.getEnumConstants().iterator();
		enumConstant = enumConstants.next();
		assertEquals("MONDAY", enumConstant.getName());
		enumConstant = enumConstants.next();
		assertEquals("TUESDAY", enumConstant.getName());
		enumConstant = enumConstants.next();
		assertEquals("WEDNESDAY", enumConstant.getName());
		assertFalse(enumConstants.hasNext());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.removeEnumConstant((EnumDeclaration) declaration.getDeclaration(), "TUESDAY");
				GenericJavaPersistentEnumTests.this.removeEnumConstant((EnumDeclaration) declaration.getDeclaration(), "MONDAY");
			}
		});
		assertEquals(1, contextEnum.getEnumConstantsSize());
		enumConstants = contextEnum.getEnumConstants().iterator();
		enumConstant = enumConstants.next();
		assertEquals("WEDNESDAY", enumConstant.getName());
		assertFalse(enumConstants.hasNext());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentEnumTests.this.removeEnumConstant((EnumDeclaration) declaration.getDeclaration(), "WEDNESDAY");
			}
		});
		assertEquals(0, contextEnum.getEnumConstantsSize());
		assertFalse(contextEnum.getEnumConstants().iterator().hasNext());

	}
}