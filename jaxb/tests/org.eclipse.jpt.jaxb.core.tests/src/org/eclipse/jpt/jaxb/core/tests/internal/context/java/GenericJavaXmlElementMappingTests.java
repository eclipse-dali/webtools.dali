/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


@SuppressWarnings("nls")
public class GenericJavaXmlElementMappingTests extends JaxbContextModelTestCase
{

	public GenericJavaXmlElementMappingTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlElement() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ELEMENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElement");
			}
		});
	}

	public void testModifyName() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedName());
		assertEquals("id", xmlElementMapping.getDefaultName());
		assertEquals("id", xmlElementMapping.getName());

		xmlElementMapping.setSpecifiedName("foo");
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlElementAnnotation.getName());
		assertEquals("foo", xmlElementMapping.getSpecifiedName());
		assertEquals("id", xmlElementMapping.getDefaultName());
		assertEquals("foo", xmlElementMapping.getName());

		xmlElementMapping.setSpecifiedName(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertNull(xmlElementAnnotation.getName());
		assertNull(xmlElementMapping.getSpecifiedName());
	}

	public void testUpdateName() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedName());


		//add a Name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__NAME, "foo");
			}
		});
		assertEquals("foo", xmlElementMapping.getName());

		//remove the Name member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElementMapping.getSpecifiedName());
	}

	public void testModifyNamespace() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedNamespace());

		xmlElementMapping.setSpecifiedNamespace("foo");
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlElementAnnotation.getNamespace());
		assertEquals("foo", xmlElementMapping.getNamespace());

		xmlElementMapping.setSpecifiedNamespace(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertNull(xmlElementAnnotation.getNamespace());
		assertNull(xmlElementMapping.getSpecifiedNamespace());
	}

	public void testUpdateNamespace() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedNamespace());


		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", xmlElementMapping.getNamespace());

		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElementMapping.getSpecifiedNamespace());
	}

	public void testModifyRequired() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedRequired());
		assertEquals(false, xmlElementMapping.isDefaultRequired());
		assertEquals(false, xmlElementMapping.isRequired());

		xmlElementMapping.setSpecifiedRequired(Boolean.TRUE);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.TRUE, xmlElementAnnotation.getRequired());
		assertEquals(Boolean.TRUE, xmlElementMapping.getSpecifiedRequired());
		assertEquals(false, xmlElementMapping.isDefaultRequired());
		assertEquals(true, xmlElementMapping.isRequired());

		xmlElementMapping.setSpecifiedRequired(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertNull(xmlElementAnnotation.getName());
		assertNull(xmlElementMapping.getSpecifiedRequired());
		assertEquals(false, xmlElementMapping.isDefaultRequired());
		assertEquals(false, xmlElementMapping.isRequired());
	}

	public void testUpdateRequired() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedRequired());
		assertEquals(false, xmlElementMapping.isDefaultRequired());
		assertEquals(false, xmlElementMapping.isRequired());


		//add a required member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__REQUIRED, true);
			}
		});
		assertEquals(Boolean.TRUE, xmlElementMapping.getSpecifiedRequired());
		assertEquals(false, xmlElementMapping.isDefaultRequired());
		assertEquals(true, xmlElementMapping.isRequired());

		//remove the required member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElementMapping.getSpecifiedRequired());
		assertEquals(false, xmlElementMapping.isDefaultRequired());
		assertEquals(false, xmlElementMapping.isRequired());
	}

	public void testModifyNillable() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedNillable());
		assertEquals(false, xmlElementMapping.isDefaultNillable());
		assertEquals(false, xmlElementMapping.isNillable());

		xmlElementMapping.setSpecifiedNillable(Boolean.TRUE);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.TRUE, xmlElementAnnotation.getNillable());
		assertEquals(Boolean.TRUE, xmlElementMapping.getSpecifiedNillable());
		assertEquals(false, xmlElementMapping.isDefaultNillable());
		assertEquals(true, xmlElementMapping.isNillable());

		xmlElementMapping.setSpecifiedNillable(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertNull(xmlElementAnnotation.getName());
		assertNull(xmlElementMapping.getSpecifiedNillable());
		assertEquals(false, xmlElementMapping.isDefaultNillable());
		assertEquals(false, xmlElementMapping.isNillable());
	}

	public void testUpdateNillable() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedNillable());
		assertEquals(false, xmlElementMapping.isDefaultNillable());
		assertEquals(false, xmlElementMapping.isNillable());


		//add a nillable member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__NILLABLE, true);
			}
		});
		assertEquals(Boolean.TRUE, xmlElementMapping.getSpecifiedNillable());
		assertEquals(false, xmlElementMapping.isDefaultNillable());
		assertEquals(true, xmlElementMapping.isNillable());

		//remove the nillable member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElementMapping.getSpecifiedNillable());
		assertEquals(false, xmlElementMapping.isDefaultNillable());
		assertEquals(false, xmlElementMapping.isNillable());
	}

	public void testModifyDefaultValue() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getDefaultValue());

		xmlElementMapping.setDefaultValue("foo");
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlElementAnnotation.getDefaultValue());
		assertEquals("foo", xmlElementMapping.getDefaultValue());

		xmlElementMapping.setDefaultValue(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertNull(xmlElementAnnotation.getDefaultValue());
		assertNull(xmlElementMapping.getDefaultValue());
	}

	public void testUpdateDefaultValue() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getDefaultValue());


		//add a DefaultValue member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__DEFAULT_VALUE, "foo");
			}
		});
		assertEquals("foo", xmlElementMapping.getDefaultValue());

		//remove the DefaultValue member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElementMapping.getDefaultValue());
	}

	public void testModifyType() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedType());

		xmlElementMapping.setSpecifiedType("Foo");
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertEquals("Foo", xmlElementMapping.getSpecifiedType());
		assertEquals("Foo", xmlElementMapping.getType());

		xmlElementMapping.setSpecifiedType(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME);
		assertNull(xmlElementAnnotation.getType());
		assertNull(xmlElementMapping.getSpecifiedType());
	}

	public void testUpdateType() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getSpecifiedType());


		//add a Type member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementTypeMemberValuePair(declaration, JAXB.XML_ELEMENT__TYPE, "Foo");
			}
		});
		assertEquals("Foo", xmlElementMapping.getType());

		//remove the Type member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElementMapping.getSpecifiedType());
	}

	public void testChangeMappingType() throws Exception {
		createTypeWithXmlElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getParent().getJavaResourceAttribute();

		assertNotNull(xmlElementMapping);
		assertNotNull(resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME));

		persistentAttribute.setMappingKey(MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAttributeMapping);
		assertNull(resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceAttribute.getAnnotation(XmlAttributeAnnotation.ANNOTATION_NAME));


		persistentAttribute.setMappingKey(MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY);
		xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		assertNotNull(xmlElementMapping);
		assertNotNull(resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME));
		assertNull(resourceAttribute.getAnnotation(XmlAttributeAnnotation.ANNOTATION_NAME));
	}

	protected void addXmlElementMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlElementAnnotation(declaration), name, value);
	}

	protected void addXmlElementMemberValuePair(ModifiedDeclaration declaration, String name, boolean value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlElementAnnotation(declaration), name, value);
	}

	protected void addXmlElementTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlElementAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

	protected Annotation getXmlElementAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlElementAnnotation.ANNOTATION_NAME);
	}
}