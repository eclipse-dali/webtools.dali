/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefsMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaXmlElementRefsMappingTests
		extends JaxbContextModelTestCase {
	
	public GenericJavaXmlElementRefsMappingTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTypeWithXmlElementRefs() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_TYPE, JAXB.XML_ELEMENT_REFS);
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
	
	
	// ***** XmlElementRefs *****
	
	protected NormalAnnotation newXmlElementRefAnnotation(AST ast, String name) {
		NormalAnnotation annotation = newNormalAnnotation(ast, JAXB.XML_ELEMENT_REF);
		addMemberValuePair(annotation, JAXB.XML_ELEMENT_REF__NAME, name);
		return annotation;
	}
	
	protected void addXmlElementRef(ModifiedDeclaration declaration, int index, String name) {
		NormalAnnotation arrayElement = newXmlElementRefAnnotation(declaration.getAst(), name);
		addArrayElement(declaration, JAXB.XML_ELEMENT_REFS, index, JAXB.XML_ELEMENT_REFS__VALUE, arrayElement);		
	}
	
	protected void moveXmlElementRef(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
		moveArrayElement((NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_ELEMENT_REFS), JAXB.XML_ELEMENT_REFS__VALUE, targetIndex, sourceIndex);
	}
	
	protected void removeXmlElementRef(ModifiedDeclaration declaration, int index) {
		removeArrayElement((NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_ELEMENT_REFS), JAXB.XML_ELEMENT_REFS__VALUE, index);
	}
	
	public void testSyncXmlElementRefs() throws Exception {
		createTypeWithXmlElementRefs();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefsMapping mapping = (XmlElementRefsMapping) IterableTools.get(classMapping.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		Iterable<XmlElementRef> xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		assertTrue(IterableTools.isEmpty(xmlElementRefs));
		assertEquals(0, mapping.getXmlElementRefs().getXmlElementRefsSize());
		
		//add 2 XmlElementRef annotations
		AnnotatedElement annotatedElement = annotatedElement(resourceAttribute);
		annotatedElement.edit(
				new Member.Editor() {
					
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementRefsMappingTests.this.addXmlElementRef(declaration, 0, "foo");
						GenericJavaXmlElementRefsMappingTests.this.addXmlElementRef(declaration, 1, "bar");
					}
				});
		
		xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		
		assertFalse(IterableTools.isEmpty(mapping.getXmlElementRefs().getXmlElementRefs()));
		assertEquals(2, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("foo", IterableTools.get(xmlElementRefs, 0).getQName().getName());
		assertEquals("bar", IterableTools.get(xmlElementRefs, 1).getQName().getName());
		
		// switch XmlElementRef annotations
		annotatedElement.edit(
				new Member.Editor() {
					
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementRefsMappingTests.this.moveXmlElementRef(declaration, 0, 1);
					}
				});
		
		xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		
		assertFalse(IterableTools.isEmpty(mapping.getXmlElementRefs().getXmlElementRefs()));
		assertEquals(2, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("bar", IterableTools.get(xmlElementRefs, 0).getQName().getName());
		assertEquals("foo", IterableTools.get(xmlElementRefs, 1).getQName().getName());
		
		// remove XmlElementRef annotations
		annotatedElement.edit(
				new Member.Editor() {
					
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementRefsMappingTests.this.removeXmlElementRef(declaration, 1);
						GenericJavaXmlElementRefsMappingTests.this.removeXmlElementRef(declaration, 0);
					}
				});
		
		xmlElementRefs = mapping.getXmlElementRefs().getXmlElementRefs();
		
		assertTrue(IterableTools.isEmpty(xmlElementRefs));
		assertEquals(0, mapping.getXmlElementRefs().getXmlElementRefsSize());
	}

	public void testModifyXmlElementRefs() throws Exception {
		createTypeWithXmlElementRefs();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefsMapping mapping = (XmlElementRefsMapping) IterableTools.get(classMapping.getAttributes(), 0).getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		XmlElementRefsAnnotation annotation = (XmlElementRefsAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REFS);
		
		Iterable<XmlElementRefAnnotation> annotations = annotation.getXmlElementRefs();
		
		assertEquals(0, annotation.getXmlElementRefsSize());
		assertEquals(0, mapping.getXmlElementRefs().getXmlElementRefsSize());
		
		mapping.getXmlElementRefs().addXmlElementRef(0).getQName().setSpecifiedName("foo");
		mapping.getXmlElementRefs().addXmlElementRef(1).getQName().setSpecifiedName("baz");
		mapping.getXmlElementRefs().addXmlElementRef(1).getQName().setSpecifiedName("bar");
		
		annotations = annotation.getXmlElementRefs();
		
		assertEquals(3, annotation.getXmlElementRefsSize());
		assertEquals(3, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("foo", IterableTools.get(annotations, 0).getName());
		assertEquals("bar", IterableTools.get(annotations, 1).getName());
		assertEquals("baz", IterableTools.get(annotations, 2).getName());
		
		mapping.getXmlElementRefs().moveXmlElementRef(1, 2);
		
		annotations = annotation.getXmlElementRefs();
		
		assertEquals(3, annotation.getXmlElementRefsSize());
		assertEquals(3, mapping.getXmlElementRefs().getXmlElementRefsSize());
		assertEquals("foo", IterableTools.get(annotations, 0).getName());
		assertEquals("baz", IterableTools.get(annotations, 1).getName());
		assertEquals("bar", IterableTools.get(annotations, 2).getName());
		
		mapping.getXmlElementRefs().removeXmlElementRef(2);
		mapping.getXmlElementRefs().removeXmlElementRef(0);
		mapping.getXmlElementRefs().removeXmlElementRef(0);
		
		assertEquals(0, annotation.getXmlElementRefsSize());
		assertEquals(0, mapping.getXmlElementRefs().getXmlElementRefsSize());
	}

	public void testChangeMappingType() throws Exception {
		createTypeWithXmlElementRefs();

		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
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
	
	public void testModifyXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlElementRefs();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementRefsMapping mapping = (XmlElementRefsMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(mapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
		
		mapping.addXmlJavaTypeAdapter();
		
		assertNotNull(mapping.getXmlJavaTypeAdapter());
		assertNotNull(resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER));
		
		mapping.removeXmlJavaTypeAdapter();
		
		assertNull(mapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
	}

	public void testUpdateXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlElementRefs();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementRefsMapping mapping = (XmlElementRefsMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(mapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
		
		//add an XmlJavaTypeAdapter annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefsMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		
		assertNotNull(mapping.getXmlJavaTypeAdapter());
		assertNotNull(resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER));
		
		//remove the XmlJavaTypeAdapter annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefsMappingTests.this.removeAnnotation(declaration, JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		
		assertNull(mapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
	}
	
	public void testModifyXmlElementWrapper() throws Exception {
		createTypeWithXmlElementRefs();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementRefsMapping mapping = (XmlElementRefsMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(mapping.getXmlElementWrapper());
		assertNull(xmlElementWrapperAnnotation);
		
		mapping.addXmlElementWrapper();
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNotNull(mapping.getXmlElementWrapper());
		assertNotNull(xmlElementWrapperAnnotation);
		
		mapping.removeXmlElementWrapper();
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
	}
	
	public void testUpdateXmlElementWrapper() throws Exception {
		createTypeWithXmlElementRefs();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementRefsMapping mapping = (XmlElementRefsMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(mapping.getXmlElementWrapper());
		assertNull(xmlElementWrapperAnnotation);
		
		//add an XmlElementWrapper annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefsMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT_WRAPPER);
			}
		});
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNotNull(mapping.getXmlElementWrapper());
		assertNotNull(xmlElementWrapperAnnotation);
		
		//remove the XmlElementWrapper annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefsMappingTests.this.removeAnnotation(declaration, JAXB.XML_ELEMENT_WRAPPER);
			}
		});
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(mapping.getXmlElementWrapper());
		assertNull(xmlElementWrapperAnnotation);
	}
	
	public void testModifyXmlMixed() throws Exception {
		createTypeWithXmlElementRefs();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
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
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
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
