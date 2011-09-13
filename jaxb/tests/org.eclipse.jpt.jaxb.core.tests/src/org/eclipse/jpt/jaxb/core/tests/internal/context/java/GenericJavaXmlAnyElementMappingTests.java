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
import org.eclipse.jpt.jaxb.core.context.XmlAnyElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaXmlAnyElementMappingTests extends JaxbContextModelTestCase
{

	public GenericJavaXmlAnyElementMappingTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlAnyElement() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ANY_ELEMENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAnyElement");
			}
		});
	}

	public void testChangeMappingType() throws Exception {
		createTypeWithXmlAnyElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAnyElementMapping xmlAnyElementMapping = (XmlAnyElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNotNull(xmlAnyElementMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT));

		persistentAttribute.setMappingKey(MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAttributeMapping);
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT));
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));

		persistentAttribute.setMappingKey(MappingKeys.XML_ANY_ELEMENT_ATTRIBUTE_MAPPING_KEY);
		xmlAnyElementMapping = (XmlAnyElementMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAnyElementMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));


		persistentAttribute.setMappingKey(MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		assertNotNull(xmlElementMapping);
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT));
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT));

		persistentAttribute.setMappingKey(MappingKeys.XML_ANY_ELEMENT_ATTRIBUTE_MAPPING_KEY);
		xmlAnyElementMapping = (XmlAnyElementMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAnyElementMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT));
	}

	public void testModifyXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlAnyElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAnyElementMapping xmlAnyElementMapping = (XmlAnyElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlAnyElementMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);

		xmlAnyElementMapping.addXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNotNull(xmlAnyElementMapping.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		xmlAnyElementMapping.removeXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
	}

	public void testUpdateXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlAnyElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAnyElementMapping xmlAnyElementMapping = (XmlAnyElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlAnyElementMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);


		//add an XmlJavaTypeAdapter annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAnyElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNotNull(xmlAnyElementMapping.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		//remove the XmlJavaTypeAdapter annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAnyElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlAnyElementMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);
	}

	public void testModifyLax() throws Exception {
		createTypeWithXmlAnyElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAnyElementMapping xmlAnyElementMapping = (XmlAnyElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAnyElementMapping.getSpecifiedLax());
		assertEquals(false, xmlAnyElementMapping.isDefaultLax());
		assertEquals(false, xmlAnyElementMapping.isLax());

		xmlAnyElementMapping.setSpecifiedLax(Boolean.TRUE);
		XmlAnyElementAnnotation xmlAnyElementAnnotation = (XmlAnyElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT);
		assertEquals(Boolean.TRUE, xmlAnyElementAnnotation.getLax());
		assertEquals(Boolean.TRUE, xmlAnyElementMapping.getSpecifiedLax());
		assertEquals(false, xmlAnyElementMapping.isDefaultLax());
		assertEquals(true, xmlAnyElementMapping.isLax());

		xmlAnyElementMapping.setSpecifiedLax(null);
		xmlAnyElementAnnotation = (XmlAnyElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT);
		assertNull(xmlAnyElementMapping.getSpecifiedLax());
		assertEquals(false, xmlAnyElementMapping.isDefaultLax());
		assertEquals(false, xmlAnyElementMapping.isLax());
	}

	public void testUpdateLax() throws Exception {
		createTypeWithXmlAnyElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAnyElementMapping xmlAnyElementMapping = (XmlAnyElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAnyElementMapping.getSpecifiedLax());
		assertEquals(false, xmlAnyElementMapping.isDefaultLax());
		assertEquals(false, xmlAnyElementMapping.isLax());


		//add a nillable member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAnyElementMappingTests.this.addXmlAnyElementMemberValuePair(declaration, JAXB.XML_ANY_ELEMENT__LAX, true);
			}
		});
		assertEquals(Boolean.TRUE, xmlAnyElementMapping.getSpecifiedLax());
		assertEquals(false, xmlAnyElementMapping.isDefaultLax());
		assertEquals(true, xmlAnyElementMapping.isLax());

		//remove the lax member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlAnyElementMappingTests.this.getXmlAnyElementAnnotation(declaration);
				GenericJavaXmlAnyElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlAnyElementMapping.getSpecifiedLax());
		assertEquals(false, xmlAnyElementMapping.isDefaultLax());
		assertEquals(false, xmlAnyElementMapping.isLax());
	}

	public void testModifyValue() throws Exception {
		createTypeWithXmlAnyElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAnyElementMapping xmlAnyElementMapping = (XmlAnyElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAnyElementMapping.getSpecifiedValue());

		xmlAnyElementMapping.setSpecifiedValue("Foo");
		XmlAnyElementAnnotation xmlElementAnnotation = (XmlAnyElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT);
		assertEquals("Foo", xmlAnyElementMapping.getSpecifiedValue());
		assertEquals("Foo", xmlAnyElementMapping.getValue());

		xmlAnyElementMapping.setSpecifiedValue(null);
		xmlElementAnnotation = (XmlAnyElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ELEMENT);
		assertNull(xmlElementAnnotation.getValue());
		assertNull(xmlAnyElementMapping.getSpecifiedValue());
	}

	public void testUpdateValue() throws Exception {
		createTypeWithXmlAnyElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAnyElementMapping xmlAnyElementMapping = (XmlAnyElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlAnyElementMapping.getSpecifiedValue());


		//add a Value member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAnyElementMappingTests.this.addXmlAnyElementTypeMemberValuePair(declaration, JAXB.XML_ANY_ELEMENT__VALUE, "Foo");
			}
		});
		assertEquals("Foo", xmlAnyElementMapping.getValue());

		//remove the Value member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlAnyElementMappingTests.this.getXmlAnyElementAnnotation(declaration);
				GenericJavaXmlAnyElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlAnyElementMapping.getSpecifiedValue());
	}

	protected Annotation getXmlAnyElementAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_ANY_ELEMENT);
	}
	
	protected void addXmlAnyElementMemberValuePair(ModifiedDeclaration declaration, String name, boolean value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlAnyElementAnnotation(declaration), name, value);
	}

	protected void addXmlAnyElementTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlAnyElementAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}
	
	
	// ***** XmlElementRefs *****
	
	public void testSyncXmlElementRefs() throws Exception {
		createTypeWithXmlAnyElement();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAnyElementMapping mapping = (XmlAnyElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		Iterable<XmlElementRef> xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		assertTrue(CollectionTools.isEmpty(xmlElementRefs));
		assertEquals(0, mapping.getXmlElementRefs().getXmlElementRefsSize());
		
		//add XmlElementRef annotation
		AnnotatedElement annotatedElement = annotatedElement(resourceAttribute);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlAnyElementMappingTests.this.addMemberValuePair(
								GenericJavaXmlAnyElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT_REF),
								JAXB.XML_ELEMENT_REF__NAME, "foo");
					}
				});
		xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		
		assertFalse(CollectionTools.isEmpty(mapping.getXmlElementRefs().getXmlElementRefs()));
		assertEquals(1, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("foo", CollectionTools.get(xmlElementRefs, 0).getQName().getName());
		
		// add XmlElementRefs annotation with nested annotation
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlAnyElementMappingTests.this.addMarkerAnnotation(
								declaration.getDeclaration(), JAXB.XML_ELEMENT_REFS);
						NormalAnnotation annotation = 
								GenericJavaXmlAnyElementMappingTests.this.newNormalAnnotation(declaration.getAst(), JAXB.XML_ELEMENT_REF);
						GenericJavaXmlAnyElementMappingTests.this.addMemberValuePair(
								annotation, JAXB.XML_ELEMENT_REF__NAME, "bar");
						GenericJavaXmlAnyElementMappingTests.this.addArrayElement(
								declaration, JAXB.XML_ELEMENT_REFS, 0, JAXB.XML_ELEMENT_REFS__VALUE, annotation);		
					}
				});
		xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		
		assertFalse(CollectionTools.isEmpty(mapping.getXmlElementRefs().getXmlElementRefs()));
		assertEquals(1, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("bar", CollectionTools.get(xmlElementRefs, 0).getQName().getName());
				
		// add second nested XmlElementRef annotation
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						NormalAnnotation annotation = 
								GenericJavaXmlAnyElementMappingTests.this.newNormalAnnotation(declaration.getAst(), JAXB.XML_ELEMENT_REF);
						GenericJavaXmlAnyElementMappingTests.this.addMemberValuePair(
								annotation, JAXB.XML_ELEMENT_REF__NAME, "baz");
						GenericJavaXmlAnyElementMappingTests.this.addArrayElement(
								declaration, JAXB.XML_ELEMENT_REFS, 1, JAXB.XML_ELEMENT_REFS__VALUE, annotation);		
					}
				});
		xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		
		assertFalse(CollectionTools.isEmpty(mapping.getXmlElementRefs().getXmlElementRefs()));
		assertEquals(2, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("bar", CollectionTools.get(xmlElementRefs, 0).getQName().getName());
		assertEquals("baz", CollectionTools.get(xmlElementRefs, 1).getQName().getName());
		
		// switch nested XmlElementRef annotations
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlAnyElementMappingTests.this.moveArrayElement(
								(NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_ELEMENT_REFS), 
								JAXB.XML_ELEMENT_REFS__VALUE, 0, 1);
					}
				});
		xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		
		assertFalse(CollectionTools.isEmpty(mapping.getXmlElementRefs().getXmlElementRefs()));
		assertEquals(2, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("baz", CollectionTools.get(xmlElementRefs, 0).getQName().getName());
		assertEquals("bar", CollectionTools.get(xmlElementRefs, 1).getQName().getName());
		
		// remove XmlElementRefs annotation
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlAnyElementMappingTests.this.removeArrayElement(
								(NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_ELEMENT_REFS), 
								JAXB.XML_ELEMENT_REFS__VALUE, 1);
						GenericJavaXmlAnyElementMappingTests.this.removeArrayElement(
								(NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_ELEMENT_REFS), 
								JAXB.XML_ELEMENT_REFS__VALUE, 0);
						GenericJavaXmlAnyElementMappingTests.this.removeAnnotation(
								declaration, JAXB.XML_ELEMENT_REFS);
					}
				});
		xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		
		assertFalse(CollectionTools.isEmpty(mapping.getXmlElementRefs().getXmlElementRefs()));
		assertEquals(1, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("foo", CollectionTools.get(xmlElementRefs, 0).getQName().getName());
		
		// remove XmlElementRef annotation
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlAnyElementMappingTests.this.removeAnnotation(
								declaration, JAXB.XML_ELEMENT_REF);
					}
				});
		xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		
		assertTrue(CollectionTools.isEmpty(mapping.getXmlElementRefs().getXmlElementRefs()));
		assertEquals(0, mapping.getXmlElementRefs().getXmlElementRefsSize());
	}

	public void testModifyXmlElementRefs() throws Exception {
		createTypeWithXmlAnyElement();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlAnyElementMapping mapping = (XmlAnyElementMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		XmlElementRefsAnnotation xmlElementRefsAnnotation;
		XmlElementRefAnnotation xmlElementRefAnnotation;
		
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF));
		assertEquals(0, mapping.getXmlElementRefs().getXmlElementRefsSize());
		
		mapping.getXmlElementRefs().addXmlElementRef(0).getQName().setSpecifiedName("foo");
		xmlElementRefsAnnotation = (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertNull(xmlElementRefsAnnotation);
		assertNotNull(xmlElementRefAnnotation);
		assertEquals("foo", xmlElementRefAnnotation.getName());
		
		mapping.getXmlElementRefs().addXmlElementRef(1).getQName().setSpecifiedName("bar");
		xmlElementRefsAnnotation = (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertNotNull(xmlElementRefsAnnotation);
		assertNull(xmlElementRefAnnotation);
		assertEquals(2, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("foo", CollectionTools.get(xmlElementRefsAnnotation.getXmlElementRefs(), 0).getName());
		assertEquals("bar", CollectionTools.get(xmlElementRefsAnnotation.getXmlElementRefs(), 1).getName());
		
		mapping.getXmlElementRefs().moveXmlElementRef(0, 1);
		xmlElementRefsAnnotation = (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertNotNull(xmlElementRefsAnnotation);
		assertNull(xmlElementRefAnnotation);
		assertEquals(2, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("bar", CollectionTools.get(xmlElementRefsAnnotation.getXmlElementRefs(), 0).getName());
		assertEquals("foo", CollectionTools.get(xmlElementRefsAnnotation.getXmlElementRefs(), 1).getName());
		
		mapping.getXmlElementRefs().removeXmlElementRef(0);
		xmlElementRefsAnnotation = (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertNull(xmlElementRefsAnnotation);
		assertNotNull(xmlElementRefAnnotation);
		assertEquals(1, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("foo", xmlElementRefAnnotation.getName());
		
		mapping.getXmlElementRefs().removeXmlElementRef(0);
		xmlElementRefsAnnotation = (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF));
		assertEquals(0, mapping.getXmlElementRefs().getXmlElementRefsSize());
	}

	public void testModifyXmlMixed() throws Exception {
		createTypeWithXmlAnyElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAnyElementMapping xmlAnyElementMapping = (XmlAnyElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlMixedAnnotation xmlListAnnotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(xmlAnyElementMapping.getXmlMixed());
		assertNull(xmlListAnnotation);

		xmlAnyElementMapping.addXmlMixed();
		xmlListAnnotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNotNull(xmlAnyElementMapping.getXmlMixed());
		assertNotNull(xmlListAnnotation);

		xmlAnyElementMapping.removeXmlMixed();
		xmlListAnnotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
	}

	public void testUpdateXmlMixed() throws Exception {
		createTypeWithXmlAnyElement();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAnyElementMapping xmlAnyElementMapping = (XmlAnyElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlMixedAnnotation xmlListAnnotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(xmlAnyElementMapping.getXmlMixed());
		assertNull(xmlListAnnotation);


		//add an XmlMixed annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAnyElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_MIXED);
			}
		});
		xmlListAnnotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNotNull(xmlAnyElementMapping.getXmlMixed());
		assertNotNull(xmlListAnnotation);

		//remove the XmlMixed annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAnyElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_MIXED);
			}
		});
		xmlListAnnotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(xmlAnyElementMapping.getXmlMixed());
		assertNull(xmlListAnnotation);
	}

}