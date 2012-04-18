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
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementRef;
import org.eclipse.jpt.jaxb.core.context.XmlElementRefMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


public class GenericJavaXmlElementRefMappingTests
		extends JaxbContextModelTestCase {
	
	public GenericJavaXmlElementRefMappingTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTypeWithXmlElementRef() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ELEMENT_REF);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRef");
			}
		});
	}
	
	private ICompilationUnit createTypeWithJAXBElementXmlElementRef() throws Exception {
		return this.createTestType(TYPE_NAME + "2", new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ELEMENT_REF, JAXB.JAXB_ELEMENT);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRef").append(CR);
				sb.append("private JAXBElement foo;").append(CR).append(CR);
			}
		});
	}
	
	private ICompilationUnit createTypeWithRootElementXmlElementRef() throws Exception {
		return this.createTestType(TYPE_NAME + "3", new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ELEMENT_REF, JAXB.XML_ROOT_ELEMENT);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
				sb.append("@XmlRootElement(name=\"foo\")");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementRef").append(CR);
				sb.append("private " + TYPE_NAME + "3 foo;").append(CR).append(CR);
			}
		});
	}
	
	
	public void testDefaultName() throws Exception {
		createTypeWithXmlElementRef();
		createTypeWithJAXBElementXmlElementRef(); // FULLY_QUALIFIED_TYPE_NAME + "2"
		createTypeWithRootElementXmlElementRef(); // FULLY_QUALIFIED_TYPE_NAME + "3"
		
		JaxbClass jaxbClass = (JaxbClass) getContextRoot().getType(FULLY_QUALIFIED_TYPE_NAME);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		
		// XmlElementRef type is java.lang.String -> no default name or namespace
		assertEquals("", xmlElementRef.getQName().getName());
		assertEquals("", xmlElementRef.getQName().getNamespace());
		
		classMapping = ((JaxbClass) getContextRoot().getType(FULLY_QUALIFIED_TYPE_NAME + "2")).getMapping();
		xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		
		// XmlElementRef type is JAXBElement -> default name is name of attribute
		assertEquals("foo", xmlElementRef.getQName().getName());
		assertEquals("", xmlElementRef.getQName().getNamespace());
		
		classMapping = ((JaxbClass) getContextRoot().getType(FULLY_QUALIFIED_TYPE_NAME + "3")).getMapping();
		xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		
		// XmlElementRef type is type with root element -> default name is root element name
		assertEquals("foo", xmlElementRef.getQName().getName());
		assertEquals("", xmlElementRef.getQName().getNamespace());
	}
	
	public void testModifyName() throws Exception {
		createTypeWithXmlElementRef();

		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(xmlElementRef.getQName().getSpecifiedName());
		assertEquals("", xmlElementRef.getQName().getDefaultName());
		assertEquals("", xmlElementRef.getQName().getName());

		xmlElementRef.getQName().setSpecifiedName("foo");
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertEquals("foo", xmlElementRefAnnotation.getName());
		assertEquals("foo", xmlElementRef.getQName().getSpecifiedName());
		assertEquals("foo", xmlElementRef.getQName().getName());

		xmlElementRef.getQName().setSpecifiedName(null);
		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertNull(xmlElementRefAnnotation.getName());
		assertNull(xmlElementRef.getQName().getSpecifiedName());
	}

	public void testUpdateName() throws Exception {
		createTypeWithXmlElementRef();

		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElementRef.getQName().getSpecifiedName());


		//add a Name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefMappingTests.this.addXmlElementRefMemberValuePair(declaration, JAXB.XML_ELEMENT_REF__NAME, "foo");
			}
		});
		assertEquals("foo", xmlElementRef.getQName().getName());

		//remove the Name member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementRefAnnotation = (NormalAnnotation) GenericJavaXmlElementRefMappingTests.this.getXmlElementRefAnnotation(declaration);
				GenericJavaXmlElementRefMappingTests.this.values(xmlElementRefAnnotation).remove(0);
			}
		});
		assertNull(xmlElementRef.getQName().getSpecifiedName());
	}

	public void testModifyNamespace() throws Exception {
		createTypeWithXmlElementRef();

		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElementRef.getQName().getSpecifiedNamespace());

		xmlElementRef.getQName().setSpecifiedNamespace("foo");
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertEquals("foo", xmlElementRefAnnotation.getNamespace());
		assertEquals("foo", xmlElementRef.getQName().getNamespace());

		xmlElementRef.getQName().setSpecifiedNamespace(null);
		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertNull(xmlElementRefAnnotation.getNamespace());
		assertNull(xmlElementRef.getQName().getSpecifiedNamespace());
	}

	public void testUpdateNamespace() throws Exception {
		createTypeWithXmlElementRef();

		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElementRef.getQName().getSpecifiedNamespace());


		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefMappingTests.this.addXmlElementRefMemberValuePair(declaration, JAXB.XML_ELEMENT_REF__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", xmlElementRef.getQName().getNamespace());

		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementRefAnnotation = (NormalAnnotation) GenericJavaXmlElementRefMappingTests.this.getXmlElementRefAnnotation(declaration);
				GenericJavaXmlElementRefMappingTests.this.values(xmlElementRefAnnotation).remove(0);
			}
		});
		assertNull(xmlElementRef.getQName().getSpecifiedNamespace());
	}

	public void testModifyRequired() throws Exception {
		createTypeWithXmlElementRef();

		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElementRef.getSpecifiedRequired());
		assertEquals(false, xmlElementRef.isDefaultRequired());
		assertEquals(false, xmlElementRef.isRequired());

		xmlElementRef.setSpecifiedRequired(Boolean.TRUE);
		XmlElementRefAnnotation xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertEquals(Boolean.TRUE, xmlElementRefAnnotation.getRequired());
		assertEquals(Boolean.TRUE, xmlElementRef.getSpecifiedRequired());
		assertEquals(false, xmlElementRef.isDefaultRequired());
		assertEquals(true, xmlElementRef.isRequired());

		xmlElementRef.setSpecifiedRequired(null);
		xmlElementRefAnnotation = (XmlElementRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF);
		assertNull(xmlElementRefAnnotation.getName());
		assertNull(xmlElementRef.getSpecifiedRequired());
		assertEquals(false, xmlElementRef.isDefaultRequired());
		assertEquals(false, xmlElementRef.isRequired());
	}

	public void testUpdateRequired() throws Exception {
		createTypeWithXmlElementRef();

		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElementRef.getSpecifiedRequired());
		assertEquals(false, xmlElementRef.isDefaultRequired());
		assertEquals(false, xmlElementRef.isRequired());


		//add a required member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefMappingTests.this.addXmlElementRefMemberValuePair(declaration, JAXB.XML_ELEMENT_REF__REQUIRED, true);
			}
		});
		assertEquals(Boolean.TRUE, xmlElementRef.getSpecifiedRequired());
		assertEquals(false, xmlElementRef.isDefaultRequired());
		assertEquals(true, xmlElementRef.isRequired());

		//remove the required member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementRefAnnotation = (NormalAnnotation) GenericJavaXmlElementRefMappingTests.this.getXmlElementRefAnnotation(declaration);
				GenericJavaXmlElementRefMappingTests.this.values(xmlElementRefAnnotation).remove(0);
			}
		});
		assertNull(xmlElementRef.getSpecifiedRequired());
		assertEquals(false, xmlElementRef.isDefaultRequired());
		assertEquals(false, xmlElementRef.isRequired());
	}

	public void testModifyType() throws Exception {
		createTypeWithXmlElementRef();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		
		assertNull(xmlElementRef.getSpecifiedType());
		assertEquals("int", xmlElementRef.getType());
		assertEquals("int", xmlElementRef.getDefaultType());
		
		xmlElementRef.setSpecifiedType("Foo");
		assertEquals("Foo", xmlElementRef.getSpecifiedType());
		assertEquals("Foo", xmlElementRef.getType());
		
		xmlElementRef.setSpecifiedType(null);
		assertNull(xmlElementRef.getSpecifiedType());
		assertEquals("int", xmlElementRef.getType());
	}
	
	public void testUpdateType() throws Exception {
		createTypeWithXmlElementRef();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlElementRef xmlElementRef = xmlElementRefMapping.getXmlElementRef();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(xmlElementRef.getSpecifiedType());
		assertEquals("int", xmlElementRef.getDefaultType());
		assertEquals("int", xmlElementRef.getType());
		
		//add a Type member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefMappingTests.this.addXmlElementRefTypeMemberValuePair(declaration, JAXB.XML_ELEMENT_REF__TYPE, "Foo");
			}
		});
		assertEquals("Foo", xmlElementRef.getSpecifiedType());
		assertEquals("Foo", xmlElementRef.getType());
		
		//remove the Type member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementRefAnnotation = (NormalAnnotation) GenericJavaXmlElementRefMappingTests.this.getXmlElementRefAnnotation(declaration);
				GenericJavaXmlElementRefMappingTests.this.values(xmlElementRefAnnotation).remove(0);
			}
		});
		assertNull(xmlElementRef.getSpecifiedType());
		assertEquals("int", xmlElementRef.getType());
	}

	public void testChangeMappingType() throws Exception {
		createTypeWithXmlElementRef();

		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNotNull(xmlElementRefMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF));

		persistentAttribute.setMappingKey(MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAttributeMapping);
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF));
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));


		persistentAttribute.setMappingKey(MappingKeys.XML_ELEMENT_REF_ATTRIBUTE_MAPPING_KEY);
		xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
		assertNotNull(xmlElementRefMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_REF));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));
	}

	public void testModifyXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlElementRef();

		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
		
		xmlElementRefMapping.addXmlJavaTypeAdapter();
		
		assertNotNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
		assertNotNull(resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER));

		xmlElementRefMapping.removeXmlJavaTypeAdapter();
		
		assertNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
	}

	public void testUpdateXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlElementRef();

		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
		
		//add an XmlJavaTypeAdapter annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		
		assertNotNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
		assertNotNull(resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER));
		
		//remove the XmlJavaTypeAdapter annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefMappingTests.this.removeAnnotation(declaration, JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		
		assertNull(xmlElementRefMapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
	}

	protected void addXmlElementRefMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlElementRefAnnotation(declaration), name, value);
	}

	protected void addXmlElementRefMemberValuePair(ModifiedDeclaration declaration, String name, boolean value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlElementRefAnnotation(declaration), name, value);
	}

	protected void addXmlElementRefTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlElementRefAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

	protected Annotation getXmlElementRefAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_ELEMENT_REF);
	}


	public void testModifyXmlElementWrapper() throws Exception {
		createTypeWithXmlElementRef();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(xmlElementRefMapping.getXmlElementWrapper());
		assertNull(xmlElementWrapperAnnotation);
		
		xmlElementRefMapping.addXmlElementWrapper();
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNotNull(xmlElementRefMapping.getXmlElementWrapper());
		assertNotNull(xmlElementWrapperAnnotation);
		
		xmlElementRefMapping.removeXmlElementWrapper();
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
	}
	
	public void testUpdateXmlElementRefWrapper() throws Exception {
		createTypeWithXmlElementRef();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlElementRefMapping xmlElementRefMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementRefMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(xmlElementRefMapping.getXmlElementWrapper());
		assertNull(xmlElementWrapperAnnotation);
		
		//add an XmlElementWrapper annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT_WRAPPER);
			}
		});
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNotNull(xmlElementRefMapping.getXmlElementWrapper());
		assertNotNull(xmlElementWrapperAnnotation);
		
		//remove the XmlElementWrapper annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementRefMappingTests.this.removeAnnotation(declaration, JAXB.XML_ELEMENT_WRAPPER);
			}
		});
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(xmlElementRefMapping.getXmlElementWrapper());
		assertNull(xmlElementWrapperAnnotation);
	}
	
	public void testModifyXmlMixed() throws Exception {
		createTypeWithXmlElementRef();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlElementRefMapping attributeMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = attributeMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		XmlMixedAnnotation annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(attributeMapping.getXmlMixed());
		assertNull(annotation);
		
		attributeMapping.addXmlMixed();
		annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNotNull(attributeMapping.getXmlMixed());
		assertNotNull(annotation);
		
		attributeMapping.removeXmlMixed();
		annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(attributeMapping.getXmlMixed());
		assertNull(annotation);
	}
	
	public void testUpdateXmlMixed() throws Exception {
		createTypeWithXmlElementRef();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlElementRefMapping attributeMapping = (XmlElementRefMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = attributeMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		XmlMixedAnnotation annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(attributeMapping.getXmlMixed());
		assertNull(annotation);
		
		//add an XmlMixed annotation
		AnnotatedElement annotatedElement = annotatedElement(resourceAttribute);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementRefMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_MIXED);
					}
				});
		annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNotNull(attributeMapping.getXmlMixed());
		assertNotNull(annotation);
		
		//remove the XmlMixed annotation
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementRefMappingTests.this.removeAnnotation(declaration, JAXB.XML_MIXED);
					}
				});
		annotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertNull(attributeMapping.getXmlMixed());
		assertNull(annotation);
	}
}
