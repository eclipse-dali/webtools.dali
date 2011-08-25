/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
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
import org.eclipse.jpt.jaxb.core.context.XmlElementRefsMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


public class GenericJavaXmlElementRefsMappingTests
		extends JaxbContextModelTestCase {
	
	public GenericJavaXmlElementRefsMappingTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTypeWithXmlElementRefs() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ELEMENT_REFS);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRefs");
			}
		});
	}
	
//	private ICompilationUnit createTypeWithJAXBElementXmlElementRefs() throws Exception {
//		return this.createTestType(TYPE_NAME + "2", new DefaultAnnotationWriter() {
//			
//			@Override
//			public Iterator<String> imports() {
//				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ELEMENT_REFS, JAXB.JAXB_ELEMENT);
//			}
//			
//			@Override
//			public void appendTypeAnnotationTo(StringBuilder sb) {
//				sb.append("@XmlType");
//			}
//			
//			@Override
//			public void appendIdFieldAnnotationTo(StringBuilder sb) {
//				sb.append("@XmlElementRefs").append(CR);
//				sb.append("private JAXBElement foo;").append(CR).append(CR);
//			}
//		});
//	}
//	
//	private ICompilationUnit createTypeWithRootElementXmlElementRefs() throws Exception {
//		return this.createTestType(TYPE_NAME + "3", new DefaultAnnotationWriter() {
//			
//			@Override
//			public Iterator<String> imports() {
//				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ELEMENT_REFS, JAXB.XML_ROOT_ELEMENT);
//			}
//			
//			@Override
//			public void appendTypeAnnotationTo(StringBuilder sb) {
//				sb.append("@XmlType").append(CR);
//				sb.append("@XmlRootElement(name=\"foo\")");
//			}
//			
//			@Override
//			public void appendIdFieldAnnotationTo(StringBuilder sb) {
//				sb.append("@XmlElementRefs").append(CR);
//				sb.append("private " + TYPE_NAME + "3 foo;").append(CR).append(CR);
//			}
//		});
//	}
	
	protected Annotation getXmlElementRefsAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_ELEMENT_REFS);
	}
	
	protected void addXmlElementRefsMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		addMemberValuePair((MarkerAnnotation) this.getXmlElementRefsAnnotation(declaration), name, value);
	}

	protected void addXmlElementRefsMemberValuePair(ModifiedDeclaration declaration, String name, boolean value) {
		addMemberValuePair((MarkerAnnotation) getXmlElementRefsAnnotation(declaration), name, value);
	}

	protected void addXmlElementRefsTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		addMemberValuePair(
				(MarkerAnnotation) getXmlElementRefsAnnotation(declaration), 
				name, 
				newTypeLiteral(declaration.getAst(), typeName));
	}
	
	
//	public void testDefaultName() throws Exception {
//		createTypeWithXmlElementRefs();
//		createTypeWithJAXBElementXmlElementRefs(); // FULLY_QUALIFIED_TYPE_NAME + "2"
//		createTypeWithRootElementXmlElementRefs(); // FULLY_QUALIFIED_TYPE_NAME + "3"
//		
//		JaxbPersistentClass persistentClass = getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME);
//		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
//		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
//		
//		// XmlElementRef type is java.lang.String -> no default name or namespace
//		assertEquals("", xmlElementRef.getQName().getName());
//		assertEquals("", xmlElementRef.getQName().getNamespace());
//		
//		persistentClass = getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME + "2");
//		xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
//		xmlElementRef = xmlElementRefMapping.getXmlElementRef();
//		
//		// XmlElementRef type is JAXBElement -> default name is name of attribute
//		assertEquals("foo", xmlElementRef.getQName().getName());
//		assertEquals("", xmlElementRef.getQName().getNamespace());
//		
//		persistentClass = getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME + "3");
//		xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
//		xmlElementRef = xmlElementRefMapping.getXmlElementRef();
//		
//		// XmlElementRef type is type with root element -> default name is root element name
//		assertEquals("foo", xmlElementRef.getQName().getName());
//		assertEquals("", xmlElementRef.getQName().getNamespace());
//	}
//	
//	public void testModifyName() throws Exception {
//		createTypeWithXmlElementRef();
//
//		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
//		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
//		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
//		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
//		
//		assertNull(xmlElementRef.getQName().getSpecifiedName());
//		assertEquals("", xmlElementRef.getQName().getDefaultName());
//		assertEquals("", xmlElementRef.getQName().getName());
//
//		xmlElementRef.getQName().setSpecifiedName("foo");
//		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(XmlElementRefAnnotation.ANNOTATION_NAME);
//		assertEquals("foo", xmlElementRefAnnotation.getName());
//		assertEquals("foo", xmlElementRef.getQName().getSpecifiedName());
//		assertEquals("foo", xmlElementRef.getQName().getName());
//
//		xmlElementRef.getQName().setSpecifiedName(null);
//		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(XmlElementRefAnnotation.ANNOTATION_NAME);
//		assertNull(xmlElementRefAnnotation.getName());
//		assertNull(xmlElementRef.getQName().getSpecifiedName());
//	}
//
//	public void testUpdateName() throws Exception {
//		createTypeWithXmlElementRef();
//
//		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
//		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
//		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
//		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
//
//		assertNull(xmlElementRef.getQName().getSpecifiedName());
//
//
//		//add a Name member value pair
//		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericJavaXmlElementRefMappingTests.this.addXmlElementRefMemberValuePair(declaration, JAXB.XML_ELEMENT_REF__NAME, "foo");
//			}
//		});
//		assertEquals("foo", xmlElementRef.getQName().getName());
//
//		//remove the Name member value pair
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				NormalAnnotation xmlElementRefAnnotation = (NormalAnnotation) GenericJavaXmlElementRefMappingTests.this.getXmlElementRefAnnotation(declaration);
//				GenericJavaXmlElementRefMappingTests.this.values(xmlElementRefAnnotation).remove(0);
//			}
//		});
//		assertNull(xmlElementRef.getQName().getSpecifiedName());
//	}
//
//	public void testModifyNamespace() throws Exception {
//		createTypeWithXmlElementRef();
//
//		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
//		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
//		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
//		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
//
//		assertNull(xmlElementRef.getQName().getSpecifiedNamespace());
//
//		xmlElementRef.getQName().setSpecifiedNamespace("foo");
//		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(XmlElementRefAnnotation.ANNOTATION_NAME);
//		assertEquals("foo", xmlElementRefAnnotation.getNamespace());
//		assertEquals("foo", xmlElementRef.getQName().getNamespace());
//
//		xmlElementRef.getQName().setSpecifiedNamespace(null);
//		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(XmlElementRefAnnotation.ANNOTATION_NAME);
//		assertNull(xmlElementRefAnnotation.getNamespace());
//		assertNull(xmlElementRef.getQName().getSpecifiedNamespace());
//	}
//
//	public void testUpdateNamespace() throws Exception {
//		createTypeWithXmlElementRef();
//
//		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
//		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(persistentClass.getAttributes(), 0).getMapping();
//		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
//		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
//
//		assertNull(xmlElementRef.getQName().getSpecifiedNamespace());
//
//
//		//add a namespace member value pair
//		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericJavaXmlElementRefMappingTests.this.addXmlElementRefMemberValuePair(declaration, JAXB.XML_ELEMENT_REF__NAMESPACE, "foo");
//			}
//		});
//		assertEquals("foo", xmlElementRef.getQName().getNamespace());
//
//		//remove the namespace member value pair
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				NormalAnnotation xmlElementRefAnnotation = (NormalAnnotation) GenericJavaXmlElementRefMappingTests.this.getXmlElementRefAnnotation(declaration);
//				GenericJavaXmlElementRefMappingTests.this.values(xmlElementRefAnnotation).remove(0);
//			}
//		});
//		assertNull(xmlElementRef.getQName().getSpecifiedNamespace());
//	}

	public void testChangeMappingType() throws Exception {
		createTypeWithXmlElementRefs();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlElementRefsMapping mapping = (XmlElementRefsMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNotNull(mapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS));
		
		persistentAttribute.setMappingKey(MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAttributeMapping);
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS));
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));
		
		persistentAttribute.setMappingKey(MappingKeys.XML_ELEMENT_REFS_ATTRIBUTE_MAPPING_KEY);
		mapping = (XmlElementRefsMapping) persistentAttribute.getMapping();
		assertNotNull(mapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));
	}
	
//	public void testModifyXmlJavaTypeAdapter() throws Exception {
//		createTypeWithXmlElementRef();
//
//		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
//		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
//		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
//		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
//
//		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
//		assertNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
//		assertNull(xmlJavaTypeAdapterAnnotation);
//
//		xmlElementRefMapping.addXmlJavaTypeAdapter();
//		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
//		assertNotNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
//		assertNotNull(xmlJavaTypeAdapterAnnotation);
//
//		xmlElementRefMapping.removeXmlJavaTypeAdapter();
//		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
//		assertNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
//		assertNull(xmlJavaTypeAdapterAnnotation);
//	}
//
//	public void testUpdateXmlJavaTypeAdapter() throws Exception {
//		createTypeWithXmlElementRef();
//
//		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
//		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
//		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
//		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
//
//		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
//		assertNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
//		assertNull(xmlJavaTypeAdapterAnnotation);
//
//
//		//add an XmlJavaTypeAdapter annotation
//		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericJavaXmlElementRefMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
//			}
//		});
//		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
//		assertNotNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
//		assertNotNull(xmlJavaTypeAdapterAnnotation);
//
//		//remove the XmlJavaTypeAdapter annotation
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericJavaXmlElementRefMappingTests.this.removeAnnotation(declaration, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
//			}
//		});
//		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
//		assertNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
//		assertNull(xmlJavaTypeAdapterAnnotation);
//	}
//	
//	public void testModifyXmlElementWrapper() throws Exception {
//		createTypeWithXmlElementRef();
//
//		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
//		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
//		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
//		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
//
//		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
//		assertNull(xmlElementRefMapping.getXmlElementWrapper());
//		assertNull(xmlElementWrapperAnnotation);
//		
//		xmlElementRefMapping.addXmlElementWrapper();
//		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
//		assertNotNull(xmlElementRefMapping.getXmlElementWrapper());
//		assertNotNull(xmlElementWrapperAnnotation);
//		
//		xmlElementRefMapping.removeXmlElementWrapper();
//		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
//	}
//	
//	public void testUpdateXmlElementRefWrapper() throws Exception {
//		createTypeWithXmlElementRef();
//
//		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
//		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
//		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
//		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
//
//		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
//		assertNull(xmlElementRefMapping.getXmlElementWrapper());
//		assertNull(xmlElementWrapperAnnotation);
//		
//		//add an XmlElementWrapper annotation
//		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericJavaXmlElementRefMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT_WRAPPER);
//			}
//		});
//		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
//		assertNotNull(xmlElementRefMapping.getXmlElementWrapper());
//		assertNotNull(xmlElementWrapperAnnotation);
//		
//		//remove the XmlElementWrapper annotation
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericJavaXmlElementRefMappingTests.this.removeAnnotation(declaration, JAXB.XML_ELEMENT_WRAPPER);
//			}
//		});
//		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
//		assertNull(xmlElementRefMapping.getXmlElementWrapper());
//		assertNull(xmlElementWrapperAnnotation);
//	}
	
	public void testModifyXmlMixed() throws Exception {
		createTypeWithXmlElementRefs();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlElementRefsMapping mapping = (XmlElementRefsMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		XmlMixedAnnotation annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(mapping.getXmlMixed());
		assertNull(annotation);
		
		mapping.addXmlMixed();
		annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNotNull(mapping.getXmlMixed());
		assertNotNull(annotation);
		
		mapping.removeXmlMixed();
		annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(mapping.getXmlMixed());
		assertNull(annotation);
	}

	public void testUpdateXmlMixed() throws Exception {
		createTypeWithXmlElementRefs();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlElementRefsMapping mapping = (XmlElementRefsMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		XmlMixedAnnotation annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(mapping.getXmlMixed());
		assertNull(annotation);
		
		//add an XmlMixed annotation
		AnnotatedElement annotatedElement = annotatedElement(resourceAttribute);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementRefsMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_MIXED);
					}
				});
		annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNotNull(mapping.getXmlMixed());
		assertNotNull(annotation);
		
		//remove the XmlMixed annotation
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementRefsMappingTests.this.removeAnnotation(declaration, JAXB.XML_MIXED);
					}
				});
		annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(mapping.getXmlMixed());
		assertNull(annotation);
	}
}
