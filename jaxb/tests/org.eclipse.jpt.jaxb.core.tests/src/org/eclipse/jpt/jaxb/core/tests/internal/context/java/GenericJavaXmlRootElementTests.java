/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.beans.Introspector;
import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaXmlRootElementTests
		extends JaxbContextModelTestCase {
	
	public GenericJavaXmlRootElementTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlTypeWithXmlRootElement() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_TYPE, JAXB.XML_ROOT_ELEMENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
				sb.append("@XmlRootElement").append(CR);
			}
		});
	}

	
	public void testModifyNamespace() throws Exception {
		createTypeWithXmlTypeWithXmlRootElement();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlRootElement contextRootElement = classMapping.getXmlRootElement();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(contextRootElement.getQName().getSpecifiedNamespace());
		assertEquals("", contextRootElement.getQName().getDefaultNamespace());
		assertEquals("", contextRootElement.getQName().getNamespace());
		
		contextRootElement.getQName().setSpecifiedNamespace("foo");
		XmlRootElementAnnotation rootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertEquals("foo", rootElementAnnotation.getNamespace());
		assertEquals("foo", contextRootElement.getQName().getSpecifiedNamespace());
		assertEquals("foo", contextRootElement.getQName().getNamespace());
		
		contextRootElement.getQName().setSpecifiedNamespace(null);
		rootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertNull(rootElementAnnotation.getNamespace());
		assertNull(contextRootElement.getQName().getSpecifiedNamespace());
		assertEquals("", contextRootElement.getQName().getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		createTypeWithXmlTypeWithXmlRootElement();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlRootElement contextRootElement = classMapping.getXmlRootElement();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(contextRootElement.getQName().getSpecifiedNamespace());
		assertEquals("", contextRootElement.getQName().getDefaultNamespace());
		assertEquals("", contextRootElement.getQName().getNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlRootElementTests.this.addXmlRootElementMemberValuePair(declaration, JAXB.XML_ROOT_ELEMENT__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", contextRootElement.getQName().getSpecifiedNamespace());
		assertEquals("foo", contextRootElement.getQName().getNamespace());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlRootElementTests.this.removeAnnotation(declaration, JAXB.XML_ROOT_ELEMENT);
			}
		});
		assertNull(classMapping.getXmlRootElement());
	}

	public void testModifyName() throws Exception {
		createTypeWithXmlTypeWithXmlRootElement();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlRootElement contextRootElement = classMapping.getXmlRootElement();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		String defaultName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(contextRootElement.getQName().getSpecifiedName());
		assertEquals(defaultName, contextRootElement.getQName().getDefaultName());
		assertEquals(defaultName, contextRootElement.getQName().getName());
		
		contextRootElement.getQName().setSpecifiedName("foo");
		XmlRootElementAnnotation rootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertEquals("foo", rootElementAnnotation.getName());
		assertEquals("foo", contextRootElement.getQName().getSpecifiedName());
		assertEquals("foo", contextRootElement.getQName().getName());
		
		contextRootElement.getQName().setSpecifiedName(null);
		rootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertNull(rootElementAnnotation.getName());
		assertNull(contextRootElement.getQName().getSpecifiedName());
		assertEquals(defaultName, contextRootElement.getQName().getName());
	}
	
	public void testUpdateName() throws Exception {
		createTypeWithXmlTypeWithXmlRootElement();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlRootElement contextRootElement = classMapping.getXmlRootElement();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		String defaultName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(contextRootElement.getQName().getSpecifiedName());
		assertEquals(defaultName, contextRootElement.getQName().getDefaultName());
		assertEquals(defaultName, contextRootElement.getQName().getName());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlRootElementTests.this.addXmlRootElementMemberValuePair(declaration, JAXB.XML_ROOT_ELEMENT__NAME, "foo");
			}
		});
		assertEquals("foo", contextRootElement.getQName().getSpecifiedName());
		assertEquals("foo", contextRootElement.getQName().getName());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlRootElementTests.this.removeAnnotation(declaration, JAXB.XML_ROOT_ELEMENT);
			}
		});
		assertNull(classMapping.getXmlRootElement());
	}

	protected void addXmlRootElementMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlRootElementAnnotation(declaration), name, value);
	}

	protected Annotation getXmlRootElementAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_ROOT_ELEMENT);
	}
}
