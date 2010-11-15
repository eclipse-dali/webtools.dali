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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

@SuppressWarnings("nls")
public class XmlJavaTypeAdapterPackageAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
	private static final String TEST_CLASS = "TestClass";
	private static final String TEST_CLASS_2 = "TestClass2";
	private static final String FQ_TEST_CLASS = PACKAGE_NAME + "." + TEST_CLASS;
	private static final String FQ_TEST_CLASS_2 = PACKAGE_NAME + "." + TEST_CLASS_2;
	
	
	public XmlJavaTypeAdapterPackageAnnotationTests(String name) {
		super(name);
	}
	
	
	private void createTestClass() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append(TEST_CLASS).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, TEST_CLASS + ".java", sourceWriter);
	}
	
	private void createTestClass2() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append(TEST_CLASS_2).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, TEST_CLASS_2 + ".java", sourceWriter);
	}
	
	private ICompilationUnit createPackageInfoWithJavaTypeAdapter() throws CoreException {
		return createTestPackageInfo(
				"@XmlJavaTypeAdapter",
				JAXB.XML_JAVA_TYPE_ADAPTER);
	}
	
	private ICompilationUnit createPackageInfoWithJavaTypeAdapterAndValue() throws CoreException {
		createTestClass();
		return createTestPackageInfo(
				"@XmlJavaTypeAdapter(" + TEST_CLASS + ".class)",
				JAXB.XML_JAVA_TYPE_ADAPTER, FQ_TEST_CLASS);
	}
	
	private ICompilationUnit createPackageInfoWithJavaTypeAdapterAndType() throws CoreException {
		createTestClass();
		return createTestPackageInfo(
				"@XmlJavaTypeAdapter(type = " + TEST_CLASS + ".class)",
				JAXB.XML_JAVA_TYPE_ADAPTER, FQ_TEST_CLASS);
	}
	
	private ICompilationUnit createPackageInfoWithJavaTypeAdapters() throws CoreException {
		return createTestPackageInfo(
				"@XmlJavaTypeAdapters({@XmlJavaTypeAdapter,@XmlJavaTypeAdapter})",
				JAXB.XML_JAVA_TYPE_ADAPTERS, JAXB.XML_JAVA_TYPE_ADAPTER);
	}
	
	public void testValue() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithJavaTypeAdapterAndValue();
		JavaResourcePackage resourcePackage = buildJavaResourcePackage(cu); 
		createTestClass2();
		
		XmlJavaTypeAdapterAnnotation annotation = 
				(XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertTrue(annotation != null);
		assertEquals(TEST_CLASS, annotation.getValue());
		assertEquals(FQ_TEST_CLASS, annotation.getFullyQualifiedValue());
		assertSourceContains("@XmlJavaTypeAdapter(" + TEST_CLASS + ".class)", cu);
		
		annotation.setValue(TEST_CLASS_2);
		assertEquals(TEST_CLASS_2, annotation.getValue());
		assertEquals(FQ_TEST_CLASS_2, annotation.getFullyQualifiedValue());
		assertSourceContains("@XmlJavaTypeAdapter(" + TEST_CLASS_2 + ".class)", cu);
		
		annotation.setValue(null);
		assertEquals(null, annotation.getValue());
		assertEquals(null, annotation.getFullyQualifiedValue());
		assertSourceContains("@XmlJavaTypeAdapter", cu);
		
		annotation.setValue(TEST_CLASS);
		assertEquals(TEST_CLASS, annotation.getValue());
		assertEquals(FQ_TEST_CLASS, annotation.getFullyQualifiedValue());
		assertSourceContains("@XmlJavaTypeAdapter(" + TEST_CLASS + ".class)", cu);
	}
	
	public void testType() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithJavaTypeAdapterAndType();
		JavaResourcePackage resourcePackage = buildJavaResourcePackage(cu); 
		createTestClass2();
		
		XmlJavaTypeAdapterAnnotation annotation = 
				(XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertTrue(annotation != null);
		assertEquals(TEST_CLASS, annotation.getType());
		assertEquals(FQ_TEST_CLASS, annotation.getFullyQualifiedType());
		assertSourceContains("@XmlJavaTypeAdapter(type = " + TEST_CLASS + ".class)", cu);
		
		annotation.setType(TEST_CLASS_2);
		assertEquals(TEST_CLASS_2, annotation.getType());
		assertEquals(FQ_TEST_CLASS_2, annotation.getFullyQualifiedType());
		assertSourceContains("@XmlJavaTypeAdapter(type = " + TEST_CLASS_2 + ".class)", cu);
		
		annotation.setType(null);
		assertEquals(null, annotation.getType());
		assertEquals(null, annotation.getFullyQualifiedType());
		assertSourceContains("@XmlJavaTypeAdapter", cu);
		
		annotation.setType(TEST_CLASS);
		assertEquals(TEST_CLASS, annotation.getType());
		assertEquals(FQ_TEST_CLASS, annotation.getFullyQualifiedType());
		assertSourceContains("@XmlJavaTypeAdapter(type = " + TEST_CLASS + ".class)", cu);
	}
	
	public void testTypeWithValue()
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithJavaTypeAdapterAndValue();
		JavaResourcePackage resourcePackage = buildJavaResourcePackage(cu); 
		createTestClass2();
		
		XmlJavaTypeAdapterAnnotation annotation = 
				(XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertTrue(annotation != null);
		assertEquals(TEST_CLASS, annotation.getValue());
		assertSourceContains("@XmlJavaTypeAdapter(" + TEST_CLASS + ".class)", cu);
		
		annotation.setType(TEST_CLASS_2);
		assertEquals(TEST_CLASS, annotation.getValue());
		assertEquals(TEST_CLASS_2, annotation.getType());
		assertSourceContains("@XmlJavaTypeAdapter(value = " + TEST_CLASS + ".class, type = " + TEST_CLASS_2 + ".class)", cu);
		
		annotation.setValue(null);
		assertEquals(null, annotation.getValue());
		assertEquals(TEST_CLASS_2, annotation.getType());
		assertSourceContains("@XmlJavaTypeAdapter(type = " + TEST_CLASS_2 + ".class)", cu);
		
		annotation.setValue(TEST_CLASS);
		assertEquals(TEST_CLASS, annotation.getValue());
		assertEquals(TEST_CLASS_2, annotation.getType());
		assertSourceContains("@XmlJavaTypeAdapter(type = " + TEST_CLASS_2 + ".class, value = " + TEST_CLASS + ".class)", cu);
		
		annotation.setType(null);
		assertEquals(TEST_CLASS, annotation.getValue());
		assertEquals(null, annotation.getType());
		assertSourceContains("@XmlJavaTypeAdapter(" + TEST_CLASS + ".class)", cu);
	}
	
	public void testContainedWithValue()
			throws Exception {
		// test contained annotation value setting/updating
		
		ICompilationUnit cu = createPackageInfoWithJavaTypeAdapters();
		JavaResourcePackage resourcePackage = buildJavaResourcePackage(cu);
		createTestClass();
		
		XmlJavaTypeAdapterAnnotation adapterAnnotation = 
			(XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		
		adapterAnnotation.setValue(TEST_CLASS);
		assertEquals(TEST_CLASS, adapterAnnotation.getValue());
		assertEquals(FQ_TEST_CLASS, adapterAnnotation.getFullyQualifiedValue());
		assertSourceContains(
				"@XmlJavaTypeAdapters({@XmlJavaTypeAdapter(" + TEST_CLASS + ".class),@XmlJavaTypeAdapter})", cu);
		
		adapterAnnotation.setValue(null);
		assertNull(adapterAnnotation.getValue());
		assertNull(FQ_TEST_CLASS, adapterAnnotation.getFullyQualifiedValue());
		assertSourceContains(
				"@XmlJavaTypeAdapters({@XmlJavaTypeAdapter,@XmlJavaTypeAdapter})", cu);
	}
	
	public void testContainedWithType()
			throws Exception {
		// test contained annotation type setting/updating
		
		ICompilationUnit cu = createPackageInfoWithJavaTypeAdapters();
		JavaResourcePackage resourcePackage = buildJavaResourcePackage(cu);
		createTestClass();
		
		XmlJavaTypeAdapterAnnotation adapterAnnotation = 
			(XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(1, JAXB.XML_JAVA_TYPE_ADAPTER);
		
		adapterAnnotation.setType(TEST_CLASS);
		assertEquals(TEST_CLASS, adapterAnnotation.getType());
		assertEquals(FQ_TEST_CLASS, adapterAnnotation.getFullyQualifiedType());
		assertSourceContains(
				"@XmlJavaTypeAdapters({@XmlJavaTypeAdapter,@XmlJavaTypeAdapter(type = " + TEST_CLASS + ".class)})", cu);
		
		resourcePackage.moveAnnotation(0, 1, JAXB.XML_JAVA_TYPE_ADAPTER);
		adapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertEquals(TEST_CLASS, adapterAnnotation.getType());
		assertEquals(FQ_TEST_CLASS, adapterAnnotation.getFullyQualifiedType());
		assertSourceContains(
				"@XmlJavaTypeAdapters({@XmlJavaTypeAdapter(type = " + TEST_CLASS + ".class),@XmlJavaTypeAdapter})", cu);
		
		adapterAnnotation.setType(null);
		assertNull(adapterAnnotation.getType());
		assertNull(FQ_TEST_CLASS, adapterAnnotation.getFullyQualifiedType());
		assertSourceContains(
				"@XmlJavaTypeAdapters({@XmlJavaTypeAdapter,@XmlJavaTypeAdapter})", cu);
	}
	
	public void testContained()
			throws Exception {
		// test adding/removing/moving
		
		ICompilationUnit cu = createPackageInfoWithJavaTypeAdapter();
		JavaResourcePackage resourcePackage = buildJavaResourcePackage(cu);
		createTestClass();
		createTestClass2();
		
		assertEquals(1, resourcePackage.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
		
		resourcePackage.addAnnotation(1, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertEquals(2, resourcePackage.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
		assertSourceContains("@XmlJavaTypeAdapters({ @XmlJavaTypeAdapter, @XmlJavaTypeAdapter })", cu);
		
		XmlJavaTypeAdapterAnnotation adapterAnnotation1 = (XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		adapterAnnotation1.setValue(TEST_CLASS);
		XmlJavaTypeAdapterAnnotation adapterAnnotation2 = (XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(1, JAXB.XML_JAVA_TYPE_ADAPTER);
		adapterAnnotation2.setValue(TEST_CLASS_2);
		assertSourceContains(
				"@XmlJavaTypeAdapters({ @XmlJavaTypeAdapter(" + TEST_CLASS
					+ ".class), @XmlJavaTypeAdapter(" + TEST_CLASS_2
					+ ".class) })", cu);
		
		resourcePackage.moveAnnotation(0, 1, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertSourceContains(
				"@XmlJavaTypeAdapters({ @XmlJavaTypeAdapter(" + TEST_CLASS_2
					+ ".class), @XmlJavaTypeAdapter(" + TEST_CLASS
					+ ".class) })", cu);
		
		resourcePackage.removeAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertEquals(1, resourcePackage.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
		assertSourceContains(
				"@XmlJavaTypeAdapter(" + TEST_CLASS + ".class)", cu);
		assertSourceDoesNotContain("@XmlJavaTypeAdapters", cu);
	}
}
