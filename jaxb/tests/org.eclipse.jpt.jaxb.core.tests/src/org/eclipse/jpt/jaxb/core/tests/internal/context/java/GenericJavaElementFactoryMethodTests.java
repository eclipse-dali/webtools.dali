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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaElementFactoryMethodTests extends JaxbContextModelTestCase
{
	
	public GenericJavaElementFactoryMethodTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlRegistry() throws Exception {
		return this.createTestType(PACKAGE_NAME, "ObjectFactory.java", "ObjectFactory", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_REGISTRY, JAXB.XML_ELEMENT_DECL, "javax.xml.bind.JAXBElement");
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

	private ICompilationUnit createTypeWithXmlType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
			}
		});
	}
	
	public void testModifyElementName() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getElementName());

		elementFactoryMethod.setElementName("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertEquals("bar", elementDeclAnnotation.getName());
		assertEquals("bar", elementFactoryMethod.getElementName());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.setElementName(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertNull(elementDeclAnnotation.getName());
		assertNull(elementFactoryMethod.getElementName());
	}
	
	public void testUpdateElementName() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		assertEquals(2, contextRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getElementName());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__NAME, "foo");
			}
		});
		assertEquals("foo", elementFactoryMethod.getElementName());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, contextRegistry.getElementFactoryMethodsSize());
	}
	
	public void testModifyDefaultValue() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getDefaultValue());

		elementFactoryMethod.setDefaultValue("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertEquals("bar", elementDeclAnnotation.getDefaultValue());
		assertEquals("bar", elementFactoryMethod.getDefaultValue());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.setDefaultValue(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertNull(elementDeclAnnotation.getDefaultValue());
		assertNull(elementFactoryMethod.getDefaultValue());
	}
	
	public void testUpdateDefaultValue() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		assertEquals(2, contextRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
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
		assertEquals(1, contextRegistry.getElementFactoryMethodsSize());
	}

	public void testModifyScope() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getScope());

		elementFactoryMethod.setScope("Bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertEquals("Bar", elementDeclAnnotation.getScope());
		assertEquals("Bar", elementFactoryMethod.getScope());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.setScope(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertNull(elementDeclAnnotation.getScope());
		assertNull(elementFactoryMethod.getScope());
	}
	
	public void testUpdateScope() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		assertEquals(2, contextRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getScope());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclTypeMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__SCOPE, "Foo");
			}
		});
		assertEquals("Foo", elementFactoryMethod.getScope());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, contextRegistry.getElementFactoryMethodsSize());
	}

	public void testModifySubstitutionHeadName() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getSubstitutionHeadName());

		elementFactoryMethod.setSubstitutionHeadName("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertEquals("bar", elementDeclAnnotation.getSubstitutionHeadName());
		assertEquals("bar", elementFactoryMethod.getSubstitutionHeadName());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.setSubstitutionHeadName(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertNull(elementDeclAnnotation.getSubstitutionHeadName());
		assertNull(elementFactoryMethod.getSubstitutionHeadName());
	}
	
	public void testUpdateSubstitutionHeadName() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		assertEquals(2, contextRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getSubstitutionHeadName());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME, "foo");
			}
		});
		assertEquals("foo", elementFactoryMethod.getSubstitutionHeadName());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, contextRegistry.getElementFactoryMethodsSize());
	}

	public void testModifySubstitutionHeadNamespace() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getSubstitutionHeadNamespace());

		elementFactoryMethod.setSubstitutionHeadNamespace("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertEquals("bar", elementDeclAnnotation.getSubstitutionHeadNamespace());
		assertEquals("bar", elementFactoryMethod.getSubstitutionHeadNamespace());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.setSubstitutionHeadNamespace(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertNull(elementDeclAnnotation.getSubstitutionHeadNamespace());
		assertNull(elementFactoryMethod.getSubstitutionHeadNamespace());
	}
	
	public void testUpdateSubstitutionHeadNamespace() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		assertEquals(2, contextRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getSubstitutionHeadNamespace());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE, "foo");
			}
		});
		assertEquals("foo", elementFactoryMethod.getSubstitutionHeadNamespace());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, contextRegistry.getElementFactoryMethodsSize());
	}

	public void testModifyNamespace() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getNamespace());

		elementFactoryMethod.setNamespace("bar");
		XmlElementDeclAnnotation elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertEquals("bar", elementDeclAnnotation.getNamespace());
		assertEquals("bar", elementFactoryMethod.getNamespace());

		 //verify the xml element decl annotation is not removed when the element name is set to null
		elementFactoryMethod.setNamespace(null);
		elementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
		assertNull(elementDeclAnnotation.getNamespace());
		assertNull(elementFactoryMethod.getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		createTypeWithXmlType();
		createTypeWithXmlRegistry();

		JaxbRegistry contextRegistry = CollectionTools.get(getContextRoot().getRegistries(), 0);

		assertEquals(2, contextRegistry.getElementFactoryMethodsSize());
		Iterator<JaxbElementFactoryMethod> elementFactoryMethods = contextRegistry.getElementFactoryMethods().iterator();
		JaxbElementFactoryMethod elementFactoryMethod = elementFactoryMethods.next();
		JavaResourceMethod resourceMethod = elementFactoryMethod.getResourceMethod();
		assertNull(elementFactoryMethod.getNamespace());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.addXmlElementDeclMemberValuePair(declaration, JAXB.XML_ELEMENT_DECL__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", elementFactoryMethod.getNamespace());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaElementFactoryMethodTests.this.removeXmlElementDeclAnnotation(declaration);
			}
		});
		assertEquals(1, contextRegistry.getElementFactoryMethodsSize());
	}

	protected void addXmlElementDeclMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlElementDeclAnnotation(declaration), name, value);
	}

	protected Annotation getXmlElementDeclAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlElementDeclAnnotation.ANNOTATION_NAME);
	}

	protected void removeXmlElementDeclAnnotation(ModifiedDeclaration declaration) {
		this.removeAnnotation(declaration, XmlElementDeclAnnotation.ANNOTATION_NAME);		
	}

	protected void addXmlElementDeclTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlElementDeclAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

}