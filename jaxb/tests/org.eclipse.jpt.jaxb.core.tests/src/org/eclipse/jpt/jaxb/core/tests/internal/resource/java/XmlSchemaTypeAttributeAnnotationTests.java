/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

@SuppressWarnings("nls")
public class XmlSchemaTypeAttributeAnnotationTests
		extends JaxbJavaResourceModelTestCase {

	private static final String TEST_NAME = "foo";
	private static final String TEST_NAME_2 = "bar";

	private static final String TEST_NAMESPACE = "http://www.eclipse.org/test/schema";
	private static final String TEST_NAMESPACE_2 = "http://www.eclipse.org/test/schema2";

	private static final String TEST_CLASS = GregorianCalendar.class.getSimpleName();
	private static final String TEST_CLASS_2 = Date.class.getSimpleName();
	private static final String FQ_TEST_CLASS = GregorianCalendar.class.getName();
	private static final String FQ_TEST_CLASS_2 = Date.class.getName();


	public XmlSchemaTypeAttributeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlAttributeWithSchemaTypeAndName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_SCHEMA_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlSchemaType(name = \"" + TEST_NAME + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlAttributeWithSchemaTypeAndNamespace() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_SCHEMA_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlSchemaType(namespace = \"" + TEST_NAMESPACE + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlAttributeWithSchemaTypeAndType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_SCHEMA_TYPE, FQ_TEST_CLASS, FQ_TEST_CLASS_2);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlSchemaType(type = " + TEST_CLASS + ".class)");
			}
		});
	}

	public void testName() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttributeWithSchemaTypeAndName();
		JavaResourceType resourceType = this.buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = this.getField(resourceType, 0);

		XmlSchemaTypeAnnotation annotation = 
				(XmlSchemaTypeAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertTrue(annotation != null);
		assertEquals(TEST_NAME, annotation.getName());
		assertSourceContains("@XmlSchemaType(name = \"" + TEST_NAME + "\")", cu);

		annotation.setName(TEST_NAME_2);
		assertEquals(TEST_NAME_2, annotation.getName());
		assertSourceContains("@XmlSchemaType(name = \"" + TEST_NAME_2 + "\")", cu);

		annotation.setName(null);
		assertEquals(null, annotation.getName());
		assertSourceContains("@XmlSchemaType", cu);

		annotation.setName(TEST_NAME);
		assertEquals(TEST_NAME, annotation.getName());
		assertSourceContains("@XmlSchemaType(name = \"" + TEST_NAME + "\")", cu);
	}

	public void testNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttributeWithSchemaTypeAndNamespace();
		JavaResourceType resourceType = this.buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = this.getField(resourceType, 0);

		XmlSchemaTypeAnnotation annotation = 
				(XmlSchemaTypeAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertTrue(annotation != null);
		assertEquals(TEST_NAMESPACE, annotation.getNamespace());
		assertSourceContains("@XmlSchemaType(namespace = \"" + TEST_NAMESPACE + "\")", cu);

		annotation.setNamespace(TEST_NAMESPACE_2);
		assertEquals(TEST_NAMESPACE_2, annotation.getNamespace());
		assertSourceContains("@XmlSchemaType(namespace = \"" + TEST_NAMESPACE_2 + "\")", cu);

		annotation.setNamespace(null);
		assertEquals(null, annotation.getNamespace());
		assertSourceContains("@XmlSchemaType", cu);

		annotation.setNamespace(TEST_NAMESPACE);
		assertEquals(TEST_NAMESPACE, annotation.getNamespace());
		assertSourceContains("@XmlSchemaType(namespace = \"" + TEST_NAMESPACE + "\")", cu);
	}

	public void testType() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttributeWithSchemaTypeAndType();
		JavaResourceType resourceType = this.buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = this.getField(resourceType, 0);

		XmlSchemaTypeAnnotation annotation = 
				(XmlSchemaTypeAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertTrue(annotation != null);
		assertEquals(TEST_CLASS, annotation.getType());
		assertEquals(FQ_TEST_CLASS, annotation.getFullyQualifiedType());
		assertSourceContains("@XmlSchemaType(type = " + TEST_CLASS + ".class)", cu);

		annotation.setType(TEST_CLASS_2);
		assertEquals(TEST_CLASS_2, annotation.getType());
		assertEquals(FQ_TEST_CLASS_2, annotation.getFullyQualifiedType());
		assertSourceContains("@XmlSchemaType(type = " + TEST_CLASS_2 + ".class)", cu);

		annotation.setType(null);
		assertEquals(null, annotation.getType());
		assertEquals(null, annotation.getFullyQualifiedType());
		assertSourceContains("@XmlSchemaType", cu);

		annotation.setType(TEST_CLASS);
		assertEquals(TEST_CLASS, annotation.getType());
		assertEquals(FQ_TEST_CLASS, annotation.getFullyQualifiedType());
		assertSourceContains("@XmlSchemaType(type = " + TEST_CLASS + ".class)", cu);
	}
}
