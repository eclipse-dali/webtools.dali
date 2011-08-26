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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttachmentRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlListAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaXmlAttributeMappingTests extends JaxbContextModelTestCase
{

	public GenericJavaXmlAttributeMappingTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlAttribute() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ATTRIBUTE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAttribute");
			}
		});
	}

	public void testModifyName() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAttributeMapping.getQName().getSpecifiedName());
		assertEquals("id", xmlAttributeMapping.getQName().getDefaultName());
		assertEquals("id", xmlAttributeMapping.getQName().getName());

		xmlAttributeMapping.getQName().setSpecifiedName("foo");
		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertEquals("foo", xmlAttributeAnnotation.getName());
		assertEquals("foo", xmlAttributeMapping.getQName().getSpecifiedName());
		assertEquals("id", xmlAttributeMapping.getQName().getDefaultName());
		assertEquals("foo", xmlAttributeMapping.getQName().getName());

		xmlAttributeMapping.getQName().setSpecifiedName(null);
		xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertNull(xmlAttributeAnnotation.getName());
		assertNull(xmlAttributeMapping.getQName().getSpecifiedName());
	}

	public void testUpdateName() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAttributeMapping.getQName().getSpecifiedName());


		//add a Name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.addXmlAttributeMemberValuePair(declaration, JAXB.XML_ATTRIBUTE__NAME, "foo");
			}
		});
		assertEquals("foo", xmlAttributeMapping.getQName().getName());

		//remove the Name member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlAttributeAnnotation = (NormalAnnotation) GenericJavaXmlAttributeMappingTests.this.getXmlAttributeAnnotation(declaration);
				GenericJavaXmlAttributeMappingTests.this.values(xmlAttributeAnnotation).remove(0);
			}
		});
		assertNull(xmlAttributeMapping.getQName().getSpecifiedName());
	}

	public void testModifyNamespace() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAttributeMapping.getQName().getSpecifiedNamespace());

		xmlAttributeMapping.getQName().setSpecifiedNamespace("foo");
		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertEquals("foo", xmlAttributeAnnotation.getNamespace());
		assertEquals("foo", xmlAttributeMapping.getQName().getNamespace());

		xmlAttributeMapping.getQName().setSpecifiedNamespace(null);
		xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertNull(xmlAttributeAnnotation.getNamespace());
		assertNull(xmlAttributeMapping.getQName().getSpecifiedNamespace());
	}

	public void testUpdateNamespace() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAttributeMapping.getQName().getSpecifiedNamespace());


		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.addXmlAttributeMemberValuePair(declaration, JAXB.XML_ATTRIBUTE__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", xmlAttributeMapping.getQName().getNamespace());
		
		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlAttributeAnnotation = (NormalAnnotation) GenericJavaXmlAttributeMappingTests.this.getXmlAttributeAnnotation(declaration);
				GenericJavaXmlAttributeMappingTests.this.values(xmlAttributeAnnotation).remove(0);
			}
		});
		assertNull(xmlAttributeMapping.getQName().getSpecifiedNamespace());
	}

	public void testModifyRequired() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAttributeMapping.getSpecifiedRequired());
		assertEquals(false, xmlAttributeMapping.isDefaultRequired());
		assertEquals(false, xmlAttributeMapping.isRequired());

		xmlAttributeMapping.setSpecifiedRequired(Boolean.TRUE);
		XmlAttributeAnnotation xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertEquals(Boolean.TRUE, xmlAttributeAnnotation.getRequired());
		assertEquals(Boolean.TRUE, xmlAttributeMapping.getSpecifiedRequired());
		assertEquals(false, xmlAttributeMapping.isDefaultRequired());
		assertEquals(true, xmlAttributeMapping.isRequired());

		xmlAttributeMapping.setSpecifiedRequired(null);
		xmlAttributeAnnotation = (XmlAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE);
		assertNull(xmlAttributeAnnotation.getName());
		assertNull(xmlAttributeMapping.getSpecifiedRequired());
		assertEquals(false, xmlAttributeMapping.isDefaultRequired());
		assertEquals(false, xmlAttributeMapping.isRequired());
	}

	public void testUpdateRequired() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAttributeMapping.getSpecifiedRequired());
		assertEquals(false, xmlAttributeMapping.isDefaultRequired());
		assertEquals(false, xmlAttributeMapping.isRequired());


		//add a required member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.addXmlAttributeMemberValuePair(declaration, JAXB.XML_ATTRIBUTE__REQUIRED, true);
			}
		});
		assertEquals(Boolean.TRUE, xmlAttributeMapping.getSpecifiedRequired());
		assertEquals(false, xmlAttributeMapping.isDefaultRequired());
		assertEquals(true, xmlAttributeMapping.isRequired());

		//remove the required member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlAttributeAnnotation = (NormalAnnotation) GenericJavaXmlAttributeMappingTests.this.getXmlAttributeAnnotation(declaration);
				GenericJavaXmlAttributeMappingTests.this.values(xmlAttributeAnnotation).remove(0);
			}
		});
		assertNull(xmlAttributeMapping.getSpecifiedRequired());
		assertEquals(false, xmlAttributeMapping.isDefaultRequired());
		assertEquals(false, xmlAttributeMapping.isRequired());
	}

	public void testChangeMappingType() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNotNull(xmlAttributeMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));

		persistentAttribute.setMappingKey(MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		assertNotNull(xmlElementMapping);
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT));


		persistentAttribute.setMappingKey(MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAttributeMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT));
	}

	public void testModifyXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlAttributeMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);

		xmlAttributeMapping.addXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNotNull(xmlAttributeMapping.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		xmlAttributeMapping.removeXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
	}

	public void testUpdateXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlAttributeMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);


		//add an XmlJavaTypeAdapter annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNotNull(xmlAttributeMapping.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		//remove the XmlJavaTypeAdapter annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.removeAnnotation(declaration, JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlAttributeMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);
	}

	public void testModifyXmlSchemaType() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertNull(xmlAttributeMapping.getXmlSchemaType());
		assertNull(xmlSchemaTypeAnnotation);

		xmlAttributeMapping.addXmlSchemaType();
		xmlSchemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertNotNull(xmlAttributeMapping.getXmlSchemaType());
		assertNotNull(xmlSchemaTypeAnnotation);

		xmlAttributeMapping.removeXmlSchemaType();
		xmlSchemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
	}

	public void testUpdateXmlSchemaType() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlSchemaTypeAnnotation xmlSchemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertNull(xmlAttributeMapping.getXmlSchemaType());
		assertNull(xmlSchemaTypeAnnotation);


		//add an XmlSchemaType annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_SCHEMA_TYPE);
			}
		});
		xmlSchemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertNotNull(xmlAttributeMapping.getXmlSchemaType());
		assertNotNull(xmlSchemaTypeAnnotation);

		//remove the XmlSchemaType annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.removeAnnotation(declaration, JAXB.XML_SCHEMA_TYPE);
			}
		});
		xmlSchemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertNull(xmlAttributeMapping.getXmlSchemaType());
		assertNull(xmlSchemaTypeAnnotation);
	}

	protected void addXmlAttributeMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlAttributeAnnotation(declaration), name, value);
	}

	protected void addXmlAttributeMemberValuePair(ModifiedDeclaration declaration, String name, boolean value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlAttributeAnnotation(declaration), name, value);
	}

	protected void addXmlAttributeTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlAttributeAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

	protected Annotation getXmlAttributeAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_ATTRIBUTE);
	}

	public void testModifyXmlList() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlListAnnotation xmlListAnnotation = (XmlListAnnotation) resourceAttribute.getAnnotation(JAXB.XML_LIST);
		assertNull(xmlAttributeMapping.getXmlList());
		assertNull(xmlListAnnotation);

		xmlAttributeMapping.addXmlList();
		xmlListAnnotation = (XmlListAnnotation) resourceAttribute.getAnnotation(JAXB.XML_LIST);
		assertNotNull(xmlAttributeMapping.getXmlList());
		assertNotNull(xmlListAnnotation);

		xmlAttributeMapping.removeXmlList();
		xmlListAnnotation = (XmlListAnnotation) resourceAttribute.getAnnotation(JAXB.XML_LIST);
	}

	public void testUpdateXmlList() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlListAnnotation xmlListAnnotation = (XmlListAnnotation) resourceAttribute.getAnnotation(JAXB.XML_LIST);
		assertNull(xmlAttributeMapping.getXmlList());
		assertNull(xmlListAnnotation);


		//add an XmlList annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_LIST);
			}
		});
		xmlListAnnotation = (XmlListAnnotation) resourceAttribute.getAnnotation(JAXB.XML_LIST);
		assertNotNull(xmlAttributeMapping.getXmlList());
		assertNotNull(xmlListAnnotation);

		//remove the XmlList annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.removeAnnotation(declaration, JAXB.XML_LIST);
			}
		});
		xmlListAnnotation = (XmlListAnnotation) resourceAttribute.getAnnotation(JAXB.XML_LIST);
		assertNull(xmlAttributeMapping.getXmlList());
		assertNull(xmlListAnnotation);
	}

	public void testModifyXmlID() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlIDAnnotation xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNull(xmlAttributeMapping.getXmlID());
		assertNull(xmlIDAnnotation);

		xmlAttributeMapping.addXmlID();
		xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNotNull(xmlAttributeMapping.getXmlID());
		assertNotNull(xmlIDAnnotation);

		xmlAttributeMapping.removeXmlID();
		xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
	}

	public void testUpdateXmlID() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlIDAnnotation xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNull(xmlAttributeMapping.getXmlID());
		assertNull(xmlIDAnnotation);


		//add an XmlID annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ID);
			}
		});
		xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNotNull(xmlAttributeMapping.getXmlID());
		assertNotNull(xmlIDAnnotation);

		//remove the XmlID annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.removeAnnotation(declaration, JAXB.XML_ID);
			}
		});
		xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNull(xmlAttributeMapping.getXmlID());
		assertNull(xmlIDAnnotation);
	}

	public void testModifyXmlIDREF() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlIDREFAnnotation xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNull(xmlAttributeMapping.getXmlIDREF());
		assertNull(xmlIDREFAnnotation);

		xmlAttributeMapping.addXmlIDREF();
		xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNotNull(xmlAttributeMapping.getXmlIDREF());
		assertNotNull(xmlIDREFAnnotation);

		xmlAttributeMapping.removeXmlIDREF();
		xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
	}

	public void testUpdateXmlIDREF() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlIDREFAnnotation xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNull(xmlAttributeMapping.getXmlIDREF());
		assertNull(xmlIDREFAnnotation);


		//add an XmlIDREF annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_IDREF);
			}
		});
		xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNotNull(xmlAttributeMapping.getXmlIDREF());
		assertNotNull(xmlIDREFAnnotation);

		//remove the XmlIDREF annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.removeAnnotation(declaration, JAXB.XML_IDREF);
			}
		});
		xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNull(xmlAttributeMapping.getXmlIDREF());
		assertNull(xmlIDREFAnnotation);
	}

	public void testModifyXmlAttachmentRef() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlAttachmentRefAnnotation xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNull(xmlAttributeMapping.getXmlAttachmentRef());
		assertNull(xmlAttachmentRefAnnotation);

		xmlAttributeMapping.addXmlAttachmentRef();
		xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNotNull(xmlAttributeMapping.getXmlAttachmentRef());
		assertNotNull(xmlAttachmentRefAnnotation);

		xmlAttributeMapping.removeXmlAttachmentRef();
		xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
	}

	public void testUpdateXmlAttachmentRef() throws Exception {
		createTypeWithXmlAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAttributeMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlAttachmentRefAnnotation xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNull(xmlAttributeMapping.getXmlAttachmentRef());
		assertNull(xmlAttachmentRefAnnotation);


		//add an XmlAttachmentRef annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTACHMENT_REF);
			}
		});
		xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNotNull(xmlAttributeMapping.getXmlAttachmentRef());
		assertNotNull(xmlAttachmentRefAnnotation);

		//remove the XmlAttachmentRef annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAttributeMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTACHMENT_REF);
			}
		});
		xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNull(xmlAttributeMapping.getXmlAttachmentRef());
		assertNull(xmlAttachmentRefAnnotation);
	}
}