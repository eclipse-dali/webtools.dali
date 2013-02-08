/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.XmlRegistry;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaElementFactoryMethodTests
		extends JaxbContextModelTestCase {
	
	public GenericJavaElementFactoryMethodTests(String name) {
		super(name);
	}

	protected ICompilationUnit createClassWithXmlRegistryAndCreateMethods() throws Exception {
		return this.createTestType(PACKAGE_NAME, "ObjectFactory.java", "ObjectFactory", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_REGISTRY, JAXB.XML_ELEMENT_DECL, "javax.xml.bind.JAXBElement");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlRegistry");
			}
			
			@Override
			public void appendGetNameMethodAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementDecl").append(CR);
				sb.append("    JAXBElement<AnnotationTestType> createFoo(AnnotationTestType value) {return null}").append(CR);
				sb.append(CR);
				sb.append("    @XmlElementDecl(name=\"bar\")").append(CR);
				sb.append("    JAXBElement createBar(Object value) {return null}").append(CR);
				sb.append(CR);
			}
		});
	}

	public void testModifyElementName() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();
		
		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getQName().getName());

		elementFactoryMethod.getQName().setSpecifiedName("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertEquals("bar", elementDeclAnnotation.getName());
		assertEquals("bar", elementFactoryMethod.getQName().getName());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.getQName().setSpecifiedName(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(elementDeclAnnotation.getName());
		assertNull(elementFactoryMethod.getQName().getName());
	}
	
	public void testUpdateElementName() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		assertEquals(2, xmlRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getQName().getName());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__NAME, "foo");
			}
		});
		assertEquals("foo", elementFactoryMethod.getQName().getName());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, xmlRegistry.getElementFactoryMethodsSize());
	}
	
	public void testModifyDefaultValue() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getDefaultValue());

		elementFactoryMethod.setDefaultValue("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertEquals("bar", elementDeclAnnotation.getDefaultValue());
		assertEquals("bar", elementFactoryMethod.getDefaultValue());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.setDefaultValue(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(elementDeclAnnotation.getDefaultValue());
		assertNull(elementFactoryMethod.getDefaultValue());
	}
	
	public void testUpdateDefaultValue() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		assertEquals(2, xmlRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getDefaultValue());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__DEFAULT_VALUE, "foo");
			}
		});
		assertEquals("foo", elementFactoryMethod.getDefaultValue());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, xmlRegistry.getElementFactoryMethodsSize());
	}

	public void testModifyScope() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getScope());
		assertTrue(elementFactoryMethod.isGlobalScope());
		
		elementFactoryMethod.setScope("Bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertEquals("Bar", elementDeclAnnotation.getScope());
		assertEquals("Bar", elementFactoryMethod.getScope());
		assertFalse(elementFactoryMethod.isGlobalScope());
		
		elementFactoryMethod.setScope(JaxbElementFactoryMethod.DEFAULT_SCOPE_CLASS_NAME);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertEquals(JaxbElementFactoryMethod.DEFAULT_SCOPE_CLASS_NAME, elementDeclAnnotation.getScope());
		assertEquals(JaxbElementFactoryMethod.DEFAULT_SCOPE_CLASS_NAME, elementFactoryMethod.getScope());
		assertTrue(elementFactoryMethod.isGlobalScope());
		
		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.setScope(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(elementDeclAnnotation.getScope());
		assertNull(elementFactoryMethod.getScope());
	}
	
	public void testUpdateScope() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		assertEquals(2, xmlRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getScope());
		assertTrue(elementFactoryMethod.isGlobalScope());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclTypeMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__SCOPE, "Foo");
			}
		});
		assertEquals("Foo", elementFactoryMethod.getScope());
		assertFalse(elementFactoryMethod.isGlobalScope());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, xmlRegistry.getElementFactoryMethodsSize());
	}

	public void testModifySubstitutionHeadName() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getSubstitutionHeadQName().getName());

		elementFactoryMethod.getSubstitutionHeadQName().setSpecifiedName("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertEquals("bar", elementDeclAnnotation.getSubstitutionHeadName());
		assertEquals("bar", elementFactoryMethod.getSubstitutionHeadQName().getName());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.getSubstitutionHeadQName().setSpecifiedName(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(elementDeclAnnotation.getSubstitutionHeadName());
		assertNull(elementFactoryMethod.getSubstitutionHeadQName().getName());
	}
	
	public void testUpdateSubstitutionHeadName() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		assertEquals(2, xmlRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getSubstitutionHeadQName().getName());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME, "foo");
			}
		});
		assertEquals("foo", elementFactoryMethod.getSubstitutionHeadQName().getName());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, xmlRegistry.getElementFactoryMethodsSize());
	}

	public void testModifySubstitutionHeadNamespace() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertEquals("", elementFactoryMethod.getSubstitutionHeadQName().getNamespace());

		elementFactoryMethod.getSubstitutionHeadQName().setSpecifiedNamespace("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertEquals("bar", elementDeclAnnotation.getSubstitutionHeadNamespace());
		assertEquals("bar", elementFactoryMethod.getSubstitutionHeadQName().getNamespace());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.getSubstitutionHeadQName().setSpecifiedNamespace(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(elementDeclAnnotation.getSubstitutionHeadNamespace());
		assertEquals("", elementFactoryMethod.getSubstitutionHeadQName().getNamespace());
	}
	
	public void testUpdateSubstitutionHeadNamespace() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		assertEquals(2, xmlRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertEquals("", elementFactoryMethod.getSubstitutionHeadQName().getNamespace());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE, "foo");
			}
		});
		assertEquals("foo", elementFactoryMethod.getSubstitutionHeadQName().getNamespace());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, xmlRegistry.getElementFactoryMethodsSize());
	}

	public void testModifyNamespace() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertEquals("", elementFactoryMethod.getQName().getNamespace());

		elementFactoryMethod.getQName().setSpecifiedNamespace("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertEquals("bar", elementDeclAnnotation.getNamespace());
		assertEquals("bar", elementFactoryMethod.getQName().getNamespace());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.getQName().setSpecifiedNamespace(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(elementDeclAnnotation.getNamespace());
		assertEquals("", elementFactoryMethod.getQName().getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		createClassWithXmlType();
		createClassWithXmlRegistryAndCreateMethods();

		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType("test.ObjectFactory");
		XmlRegistry xmlRegistry = jaxbClass.getXmlRegistry();
		
		assertEquals(2, xmlRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = xmlRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertEquals("", elementFactoryMethod.getQName().getNamespace());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", elementFactoryMethod.getQName().getNamespace());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, xmlRegistry.getElementFactoryMethodsSize());
	}

	protected void addXmlElementDeclMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlElementDeclAnnotation(declaration), name, value);
	}

	protected Annotation getXmlElementDeclAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_ELEMENT_DECL);
	}

	protected void removeXmlElementDeclAnnotation(ModifiedDeclaration declaration) {
		this.removeAnnotation(declaration, JAXB.XML_ELEMENT_DECL);		
	}

	protected void addXmlElementDeclTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlElementDeclAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

}