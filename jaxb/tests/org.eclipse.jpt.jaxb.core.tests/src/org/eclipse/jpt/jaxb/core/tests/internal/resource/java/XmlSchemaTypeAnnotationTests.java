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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

@SuppressWarnings("nls")
public class XmlSchemaTypeAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
	private static final String TEST_NAME = "foo";
	private static final String TEST_NAME_2 = "bar";
	
	private static final String TEST_NAMESPACE = "http://www.eclipse.org/test/schema";
	private static final String TEST_NAMESPACE_2 = "http://www.eclipse.org/test/schema2";
	
	private static final String TEST_CLASS = GregorianCalendar.class.getSimpleName();
	private static final String TEST_CLASS_2 = Date.class.getSimpleName();
	private static final String FQ_TEST_CLASS = GregorianCalendar.class.getName();
	private static final String FQ_TEST_CLASS_2 = Date.class.getName();
	
	
	public XmlSchemaTypeAnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createPackageInfoWithSchemaType() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchemaType",
				JAXB.XML_SCHEMA_TYPE);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaTypeAndName() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchemaType(name = \"" + TEST_NAME + "\")",
				JAXB.XML_SCHEMA_TYPE);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaTypeAndNamespace() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchemaType(namespace = \"" + TEST_NAMESPACE + "\")",
				JAXB.XML_SCHEMA_TYPE);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaTypeAndType() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchemaType(type = " + TEST_CLASS + ".class)",
				JAXB.XML_SCHEMA_TYPE, FQ_TEST_CLASS, FQ_TEST_CLASS_2);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaTypes() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchemaTypes({@XmlSchemaType,@XmlSchemaType})",
				JAXB.XML_SCHEMA_TYPES, JAXB.XML_SCHEMA_TYPE, FQ_TEST_CLASS, FQ_TEST_CLASS_2);
	}
	
	public void testName() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaTypeAndName();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaTypeAnnotation annotation = 
				(XmlSchemaTypeAnnotation) packageResource.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
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
	
	public void testNamespace() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaTypeAndNamespace();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaTypeAnnotation annotation = 
				(XmlSchemaTypeAnnotation) packageResource.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
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
	
	public void testType() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaTypeAndType();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaTypeAnnotation annotation = 
				(XmlSchemaTypeAnnotation) packageResource.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
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
	
	public void testContainedWithName()
			throws Exception {
		// test contained annotation value setting/updating
		
		ICompilationUnit cu = createPackageInfoWithSchemaTypes();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu);
		
		XmlSchemaTypeAnnotation containedAnnotation = 
				(XmlSchemaTypeAnnotation) packageResource.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		
		containedAnnotation.setName(TEST_NAME);
		assertEquals(TEST_NAME, containedAnnotation.getName());
		assertSourceContains(
				"@XmlSchemaTypes({@XmlSchemaType(name = \"" + TEST_NAME + "\"),@XmlSchemaType})", cu);
		
		containedAnnotation.setName(null);
		assertNull(containedAnnotation.getName());
		assertSourceContains(
				"@XmlSchemaTypes({@XmlSchemaType,@XmlSchemaType})", cu);
	}
	
	public void testContainedWithNamespace()
			throws Exception {
		// test contained annotation value setting/updating
		
		ICompilationUnit cu = createPackageInfoWithSchemaTypes();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu);
		
		XmlSchemaTypeAnnotation containedAnnotation = 
			(XmlSchemaTypeAnnotation) packageResource.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		
		containedAnnotation.setNamespace(TEST_NAMESPACE);
		assertEquals(TEST_NAMESPACE, containedAnnotation.getNamespace());
		assertSourceContains(
				"@XmlSchemaTypes({@XmlSchemaType(namespace = \"" + TEST_NAMESPACE + "\"),@XmlSchemaType})", cu);
		
		containedAnnotation.setNamespace(null);
		assertNull(containedAnnotation.getNamespace());
		assertSourceContains(
				"@XmlSchemaTypes({@XmlSchemaType,@XmlSchemaType})", cu);
	}
	
	public void testContainedWithType()
			throws Exception {
		// test contained annotation type setting/updating
		
		ICompilationUnit cu = createPackageInfoWithSchemaTypes();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu);
		
		XmlSchemaTypeAnnotation containedAnnotation = 
			(XmlSchemaTypeAnnotation) packageResource.getAnnotation(1, JAXB.XML_SCHEMA_TYPE);
		
		containedAnnotation.setType(TEST_CLASS);
		assertEquals(TEST_CLASS, containedAnnotation.getType());
		assertEquals(FQ_TEST_CLASS, containedAnnotation.getFullyQualifiedType());
		assertSourceContains(
				"@XmlSchemaTypes({@XmlSchemaType,@XmlSchemaType(type = " + TEST_CLASS + ".class)})", cu);
		
		containedAnnotation.setType(null);
		assertNull(containedAnnotation.getType());
		assertNull(FQ_TEST_CLASS, containedAnnotation.getFullyQualifiedType());
		assertSourceContains(
				"@XmlSchemaTypes({@XmlSchemaType,@XmlSchemaType})", cu);
	}
	
	public void testContained()
			throws Exception {
		// test adding/removing/moving
		
		ICompilationUnit cu = createPackageInfoWithSchemaType();
		JavaResourcePackage resourcePackage = buildJavaResourcePackage(cu);
		
		assertEquals(1, resourcePackage.getAnnotationsSize(JAXB.XML_SCHEMA_TYPE));
		
		resourcePackage.addAnnotation(1, JAXB.XML_SCHEMA_TYPE);

		assertEquals(2, resourcePackage.getAnnotationsSize(JAXB.XML_SCHEMA_TYPE));
		assertSourceContains("@XmlSchemaTypes({ @XmlSchemaType, @XmlSchemaType })", cu);
		
		XmlSchemaTypeAnnotation containedAnnotation1 = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		containedAnnotation1.setName(TEST_NAME);
		XmlSchemaTypeAnnotation containedAnnotation2 = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(1, JAXB.XML_SCHEMA_TYPE);
		containedAnnotation2.setName(TEST_NAME_2);
		assertSourceContains(
				"@XmlSchemaTypes({ @XmlSchemaType(name = \"" + TEST_NAME
					+ "\"), @XmlSchemaType(name = \"" + TEST_NAME_2
					+ "\") })", cu);
		
		resourcePackage.moveAnnotation(0, 1, JAXB.XML_SCHEMA_TYPE);
		assertSourceContains(
				"@XmlSchemaTypes({ @XmlSchemaType(name = \"" + TEST_NAME_2
					+ "\"), @XmlSchemaType(name = \"" + TEST_NAME
					+ "\") })", cu);
		
		resourcePackage.removeAnnotation(1, JAXB.XML_SCHEMA_TYPE);
		assertEquals(1, resourcePackage.getAnnotationsSize(JAXB.XML_SCHEMA_TYPE));
		assertSourceContains(
				"@XmlSchemaType(name = \"" + TEST_NAME_2 + "\")", cu);
		assertSourceDoesNotContain("@XmlSchemaTypes", cu);
	}
}
