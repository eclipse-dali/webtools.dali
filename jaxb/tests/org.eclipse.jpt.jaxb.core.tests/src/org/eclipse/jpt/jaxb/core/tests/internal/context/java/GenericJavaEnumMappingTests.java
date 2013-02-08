/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnumMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaEnumMappingTests
		extends JaxbContextModelTestCase {
	
	public GenericJavaEnumMappingTests(String name) {
		super(name);
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
		return declaration.getAnnotationNamed(JAXB.XML_TYPE);
	}

	protected Annotation getXmlEnumAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_ENUM);
	}

	public void testModifySchemaTypeName() throws Exception {
		createEnumWithXmlType();
		
		JavaEnum jaxbEnum = (JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbEnumMapping enumMapping = jaxbEnum.getMapping();
		JavaResourceEnum resourceEnum = jaxbEnum.getJavaResourceType();
		String defaultXmlTypeName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(enumMapping.getQName().getSpecifiedName());
		assertEquals(defaultXmlTypeName, enumMapping.getQName().getDefaultName());
		assertEquals(defaultXmlTypeName, enumMapping.getQName().getName());
		
		enumMapping.getQName().setSpecifiedName("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", enumMapping.getQName().getSpecifiedName());
		assertEquals("foo", enumMapping.getQName().getName());
		
		enumMapping.getQName().setSpecifiedName(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getName());
		assertNull(enumMapping.getQName().getSpecifiedName());
		assertEquals(defaultXmlTypeName, enumMapping.getQName().getName());
		
		resourceEnum.removeAnnotation(JAXB.XML_TYPE);
		
		//set name again, this time starting with no XmlType annotation
		enumMapping.getQName().setSpecifiedName("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", enumMapping.getQName().getSpecifiedName());
		assertEquals("foo", enumMapping.getQName().getName());
	}
	
	public void testUpdateSchemaTypeName() throws Exception {
		createEnumWithXmlType();
		
		JavaEnum jaxbEnum = (JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbEnumMapping enumMapping = jaxbEnum.getMapping();
		JavaResourceEnum resourceEnum = jaxbEnum.getJavaResourceType();
		String defaultXmlTypeName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(enumMapping.getQName().getSpecifiedName());
		assertEquals(defaultXmlTypeName, enumMapping.getQName().getDefaultName());
		assertEquals(defaultXmlTypeName, enumMapping.getQName().getName());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaEnumMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
				GenericJavaEnumMappingTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAME, "foo");
			}
		});
		assertEquals("foo", enumMapping.getQName().getSpecifiedName());
		assertEquals("foo", enumMapping.getQName().getName());
		
		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaEnumMappingTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaEnumMappingTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(enumMapping.getQName().getSpecifiedName());
		assertEquals(defaultXmlTypeName, enumMapping.getQName().getName());
	}

	public void testModifyNamespace() throws Exception {
		createEnumWithXmlType();
		
		JavaEnum jaxbEnum = (JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbEnumMapping enumMapping = jaxbEnum.getMapping();
		JavaResourceEnum resourceEnum = jaxbEnum.getJavaResourceType();
		
		assertNull(enumMapping.getQName().getSpecifiedNamespace());
		assertEquals("", enumMapping.getQName().getDefaultNamespace());
		assertEquals("", enumMapping.getQName().getNamespace());
		
		enumMapping.getQName().setSpecifiedNamespace("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", enumMapping.getQName().getSpecifiedNamespace());
		assertEquals("foo", enumMapping.getQName().getNamespace());
		
		enumMapping.getQName().setSpecifiedNamespace(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getNamespace());
		assertNull(enumMapping.getQName().getSpecifiedNamespace());
		assertEquals("", enumMapping.getQName().getNamespace());
		
		resourceEnum.removeAnnotation(JAXB.XML_TYPE);
		
		//set namespace again, this time starting with no XmlType annotation
		enumMapping.getQName().setSpecifiedNamespace("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceEnum.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", enumMapping.getQName().getSpecifiedNamespace());
		assertEquals("foo", enumMapping.getQName().getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		createEnumWithXmlType();
		
		JavaEnum jaxbEnum = (JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbEnumMapping enumMapping = jaxbEnum.getMapping();
		JavaResourceEnum resourceEnum = jaxbEnum.getJavaResourceType();
		
		assertNull(enumMapping.getQName().getSpecifiedNamespace());
		assertEquals("", enumMapping.getQName().getDefaultNamespace());
		assertEquals("", enumMapping.getQName().getNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaEnumMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
				GenericJavaEnumMappingTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", enumMapping.getQName().getSpecifiedNamespace());
		assertEquals("foo", enumMapping.getQName().getNamespace());
		
		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaEnumMappingTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaEnumMappingTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(enumMapping.getQName().getSpecifiedNamespace());
		assertEquals("", enumMapping.getQName().getNamespace());
	}
	
	public void testModifyXmlRootElement() throws Exception {
		createEnumWithXmlType();
		
		JavaEnum javaEnum = (JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaEnumMapping enumMapping = javaEnum.getMapping();
		JavaResourceEnum resourceEnum = javaEnum.getJavaResourceType();
		
		assertNull(enumMapping.getXmlRootElement());
		
		enumMapping.addXmlRootElement().getQName().setSpecifiedName("foo");
		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceEnum.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertEquals("foo", xmlRootElementAnnotation.getName());
		assertEquals("foo", enumMapping.getXmlRootElement().getQName().getName());
		
		enumMapping.removeXmlRootElement();
		xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceEnum.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertNull(xmlRootElementAnnotation);
		assertNull(enumMapping.getXmlRootElement());
	}

	public void testUpdateXmlRootElement() throws Exception {
		createEnumWithXmlType();
		
		JavaEnum jaxbEnum = (JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbEnumMapping enumMapping = jaxbEnum.getMapping();
		JavaResourceEnum resourceEnum = jaxbEnum.getJavaResourceType();
		
		assertNull(enumMapping.getXmlRootElement());
		
		//add a XmlRootElement annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation annotation = GenericJavaEnumMappingTests.this.addNormalAnnotation(declaration.getDeclaration(), JAXB.XML_ROOT_ELEMENT);
				GenericJavaEnumMappingTests.this.addMemberValuePair(annotation, JAXB.XML_ROOT_ELEMENT__NAME, "foo");
			}
		});
		assertEquals("foo", enumMapping.getXmlRootElement().getQName().getName());
		
		//remove the XmlRootElement annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaEnumMappingTests.this.removeAnnotation(declaration, JAXB.XML_ROOT_ELEMENT);
			}
		});
		assertNull(enumMapping.getXmlRootElement());
	}

	public void testModifyValue() throws Exception {
		createEnumWithXmlType();
		
		JavaEnum jaxbEnum = (JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbEnumMapping enumMapping = jaxbEnum.getMapping();
		JavaResourceEnum resourceEnum = jaxbEnum.getJavaResourceType();
		
		assertNull(enumMapping.getSpecifiedValue());
		assertEquals(JaxbEnumMapping.DEFAULT_VALUE, enumMapping.getValue());
		
		enumMapping.setSpecifiedValue("Integer");
		XmlEnumAnnotation xmlEnumAnnotation = (XmlEnumAnnotation) resourceEnum.getAnnotation(JAXB.XML_ENUM);
		assertEquals("Integer", xmlEnumAnnotation.getValue());
		assertEquals("Integer", enumMapping.getSpecifiedValue());
		assertEquals("java.lang.Integer", enumMapping.getValue());
		
		enumMapping.setSpecifiedValue(null);
		xmlEnumAnnotation = (XmlEnumAnnotation) resourceEnum.getAnnotation(JAXB.XML_ENUM);
		assertNull(enumMapping.getSpecifiedValue());
		assertEquals(JaxbEnumMapping.DEFAULT_VALUE, enumMapping.getValue());
		
		resourceEnum.addAnnotation(JAXB.XML_TYPE);
		resourceEnum.removeAnnotation(JAXB.XML_ENUM);
		enumMapping = ((JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0)).getMapping();
		assertNull(enumMapping.getSpecifiedValue());
		assertEquals(JaxbEnumMapping.DEFAULT_VALUE, enumMapping.getValue());
	}
	
	public void testUpdateValue() throws Exception {
		createEnumWithXmlType();
		
		JavaEnum jaxbEnum = (JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbEnumMapping enumMapping = jaxbEnum.getMapping();
		JavaResourceEnum resourceEnum = jaxbEnum.getJavaResourceType();
		
		assertNull(enumMapping.getSpecifiedValue());
		assertEquals(JaxbEnumMapping.DEFAULT_VALUE, enumMapping.getValue());
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaEnumMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ENUM);
						GenericJavaEnumMappingTests.this.addXmlEnumTypeMemberValuePair(declaration, JAXB.XML_ENUM__VALUE, "String");
					}
				});
		assertEquals("String", enumMapping.getSpecifiedValue());
		assertEquals("java.lang.String", enumMapping.getValue());
		
		//remove the factoryClass member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlEnumAnnotation = (NormalAnnotation) GenericJavaEnumMappingTests.this.getXmlEnumAnnotation(declaration);
				GenericJavaEnumMappingTests.this.values(xmlEnumAnnotation).remove(0);
			}
		});
		assertNull(enumMapping.getSpecifiedValue());
		assertEquals(JaxbEnumMapping.DEFAULT_VALUE, enumMapping.getValue());
	}

	public void testUpdateEnumConstants() throws Exception {
		createEnumWithXmlType();
		
		JavaEnum jaxbEnum = (JavaEnum) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbEnumMapping enumMapping = jaxbEnum.getMapping();
		JavaResourceEnum resourceEnum = jaxbEnum.getJavaResourceType();
		
		assertEquals(2, enumMapping.getEnumConstantsSize());
		Iterator<JaxbEnumConstant> enumConstants = enumMapping.getEnumConstants().iterator();
		JaxbEnumConstant enumConstant = enumConstants.next();
		assertEquals("SUNDAY", enumConstant.getName());
		enumConstant = enumConstants.next();
		assertEquals("MONDAY", enumConstant.getName());
		assertFalse(enumConstants.hasNext());

		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaEnumMappingTests.this.addEnumConstant((EnumDeclaration) declaration.getDeclaration(), "TUESDAY");
				GenericJavaEnumMappingTests.this.addEnumConstant((EnumDeclaration) declaration.getDeclaration(), "WEDNESDAY");
			}
		});
		assertEquals(4, enumMapping.getEnumConstantsSize());
		enumConstants = enumMapping.getEnumConstants().iterator();
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
				GenericJavaEnumMappingTests.this.removeEnumConstant((EnumDeclaration) declaration.getDeclaration(), "SUNDAY");
			}
		});
		assertEquals(3, enumMapping.getEnumConstantsSize());
		enumConstants = enumMapping.getEnumConstants().iterator();
		enumConstant = enumConstants.next();
		assertEquals("MONDAY", enumConstant.getName());
		enumConstant = enumConstants.next();
		assertEquals("TUESDAY", enumConstant.getName());
		enumConstant = enumConstants.next();
		assertEquals("WEDNESDAY", enumConstant.getName());
		assertFalse(enumConstants.hasNext());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaEnumMappingTests.this.removeEnumConstant((EnumDeclaration) declaration.getDeclaration(), "TUESDAY");
				GenericJavaEnumMappingTests.this.removeEnumConstant((EnumDeclaration) declaration.getDeclaration(), "MONDAY");
			}
		});
		assertEquals(1, enumMapping.getEnumConstantsSize());
		enumConstants = enumMapping.getEnumConstants().iterator();
		enumConstant = enumConstants.next();
		assertEquals("WEDNESDAY", enumConstant.getName());
		assertFalse(enumConstants.hasNext());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaEnumMappingTests.this.removeEnumConstant((EnumDeclaration) declaration.getDeclaration(), "WEDNESDAY");
			}
		});
		assertEquals(0, enumMapping.getEnumConstantsSize());
		assertFalse(enumMapping.getEnumConstants().iterator().hasNext());
	}
}