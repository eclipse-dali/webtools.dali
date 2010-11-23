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
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


@SuppressWarnings("nls")
public class GenericJavaXmlRootElementTests extends JaxbContextModelTestCase
{
	
	public GenericJavaXmlRootElementTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlTypeWithXmlRootElement() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ROOT_ELEMENT);
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
		JaxbPersistentClass persistentClass = CollectionTools.get(getRootContextNode().getPersistentClasses(), 0);
		XmlRootElement contextRootElement = persistentClass.getRootElement();
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(contextRootElement.getNamespace());
		
		contextRootElement.setNamespace("foo");
		XmlRootElementAnnotation rootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertEquals("foo", rootElementAnnotation.getNamespace());
		assertEquals("foo", contextRootElement.getNamespace());
		
		contextRootElement.setNamespace(null);
		rootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertNull(rootElementAnnotation.getNamespace());
		assertNull(contextRootElement.getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		createTypeWithXmlTypeWithXmlRootElement();
		JaxbPersistentClass persistentClass = CollectionTools.get(getRootContextNode().getPersistentClasses(), 0);
		XmlRootElement contextRootElement = persistentClass.getRootElement();
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		assertNull(contextRootElement.getNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlRootElementTests.this.addXmlRootElementMemberValuePair(declaration, JAXB.XML_ROOT_ELEMENT__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", contextRootElement.getNamespace());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlRootElementTests.this.removeAnnotation(declaration, XmlRootElementAnnotation.ANNOTATION_NAME);
			}
		});
		contextRootElement = persistentClass.getRootElement();
		assertNull(contextRootElement);
	}

	public void testModifyName() throws Exception {
		createTypeWithXmlTypeWithXmlRootElement();
		JaxbPersistentClass persistentClass = CollectionTools.get(getRootContextNode().getPersistentClasses(), 0);
		XmlRootElement contextRootElement = persistentClass.getRootElement();
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();
	
		assertNull(contextRootElement.getName());
		
		contextRootElement.setName("foo");
		XmlRootElementAnnotation rootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertEquals("foo", rootElementAnnotation.getName());
		assertEquals("foo", contextRootElement.getName());
		
		contextRootElement.setName(null);
		rootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertNull(rootElementAnnotation.getName());
		assertNull(contextRootElement.getName());
	}
	
	public void testUpdateName() throws Exception {
		createTypeWithXmlTypeWithXmlRootElement();
		JaxbPersistentClass persistentClass = CollectionTools.get(getRootContextNode().getPersistentClasses(), 0);
		XmlRootElement contextRootElement = persistentClass.getRootElement();
		JavaResourceType resourceType = persistentClass.getJaxbResourceType();

		assertNull(contextRootElement.getName());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlRootElementTests.this.addXmlRootElementMemberValuePair(declaration, JAXB.XML_ROOT_ELEMENT__NAME, "foo");
			}
		});
		assertEquals("foo", contextRootElement.getName());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlRootElementTests.this.removeAnnotation(declaration, XmlRootElementAnnotation.ANNOTATION_NAME);
			}
		});
		contextRootElement = persistentClass.getRootElement();
		assertNull(contextRootElement);
	}

	protected void addXmlRootElementMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlRootElementAnnotation(declaration), name, value);
	}

	protected Annotation getXmlRootElementAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlRootElementAnnotation.ANNOTATION_NAME);
	}
}
