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
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;

@SuppressWarnings("nls")
public class XmlSchemaAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
	private static final String TEST_LOCATION = "http://www.eclipse.org/test/schema.xsd";
	
	private static final String TEST_NAMESPACE = "http://www.eclipse.org/test/schema";
	
	private static final String TEST_PREFIX = "ts";
	
	private static final String TEST_NAMESPACE_2 = "http://www.eclipse.org/test/schema2";
	
	private static final String TEST_PREFIX_2 = "ts2";
	
	
	public XmlSchemaAnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createPackageInfoWithSchemaAndAttributeFormDefault() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchema(attributeFormDefault = XmlNsForm.QUALIFIED)",
				JAXB.XML_SCHEMA, JAXB.XML_NS_FORM);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaAndElementFormDefault() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchema(elementFormDefault = XmlNsForm.QUALIFIED)",
				JAXB.XML_SCHEMA, JAXB.XML_NS_FORM);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaAndLocation() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchema(location = \"" + TEST_LOCATION + "\")",
				JAXB.XML_SCHEMA);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaAndNamespace() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchema(namespace = \"" + TEST_NAMESPACE + "\")",
				JAXB.XML_SCHEMA);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaAndXmlns() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchema(xmlns = @XmlNs)",
				JAXB.XML_SCHEMA, JAXB.XML_NS);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaAndXmlnsWithNamespace() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchema(xmlns = @XmlNs(namespaceURI = \"" + TEST_NAMESPACE + "\"))",
				JAXB.XML_SCHEMA, JAXB.XML_NS);
	}
	
	private ICompilationUnit createPackageInfoWithSchemaAndXmlnsWithPrefix() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchema(xmlns = @XmlNs(prefix = \"" + TEST_PREFIX + "\"))",
				JAXB.XML_SCHEMA, JAXB.XML_NS);
	}
	
	public void testAttributeFormDefault() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaAndAttributeFormDefault();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		assertTrue(schemaAnnotation != null);
		assertEquals(XmlNsForm.QUALIFIED, schemaAnnotation.getAttributeFormDefault());
		assertSourceContains("@XmlSchema(attributeFormDefault = XmlNsForm.QUALIFIED)", cu);
		
		schemaAnnotation.setAttributeFormDefault(XmlNsForm.UNQUALIFIED);
		assertEquals(XmlNsForm.UNQUALIFIED, schemaAnnotation.getAttributeFormDefault());
		assertSourceContains("@XmlSchema(attributeFormDefault = UNQUALIFIED)", cu);
		
		schemaAnnotation.setAttributeFormDefault(XmlNsForm.UNSET);
		assertEquals(XmlNsForm.UNSET, schemaAnnotation.getAttributeFormDefault());
		assertSourceContains("@XmlSchema(attributeFormDefault = UNSET)", cu);
		
		schemaAnnotation.setAttributeFormDefault(null);
		schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		assertNull(schemaAnnotation.getAttributeFormDefault());
		assertSourceDoesNotContain("@XmlSchema(", cu);
		
		schemaAnnotation = (XmlSchemaAnnotation) packageResource.addAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		schemaAnnotation.setAttributeFormDefault(XmlNsForm.QUALIFIED);
		assertEquals(XmlNsForm.QUALIFIED, schemaAnnotation.getAttributeFormDefault());
		assertSourceContains("@XmlSchema(attributeFormDefault = QUALIFIED)", cu);
	}
	
	public void testElementFormDefault() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaAndElementFormDefault();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		assertTrue(schemaAnnotation != null);
		assertEquals(XmlNsForm.QUALIFIED, schemaAnnotation.getElementFormDefault());
		assertSourceContains("@XmlSchema(elementFormDefault = XmlNsForm.QUALIFIED)", cu);
		
		schemaAnnotation.setElementFormDefault(XmlNsForm.UNQUALIFIED);
		assertEquals(XmlNsForm.UNQUALIFIED, schemaAnnotation.getElementFormDefault());
		assertSourceContains("@XmlSchema(elementFormDefault = UNQUALIFIED)", cu);
		
		schemaAnnotation.setElementFormDefault(XmlNsForm.UNSET);
		assertEquals(XmlNsForm.UNSET, schemaAnnotation.getElementFormDefault());
		assertSourceContains("@XmlSchema(elementFormDefault = UNSET)", cu);
		
		schemaAnnotation.setElementFormDefault(null);
		schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		assertNull(schemaAnnotation.getElementFormDefault());
		assertSourceDoesNotContain("@XmlSchema(", cu);
		
		schemaAnnotation = (XmlSchemaAnnotation) packageResource.addAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		schemaAnnotation.setElementFormDefault(XmlNsForm.QUALIFIED);
		assertEquals(XmlNsForm.QUALIFIED, schemaAnnotation.getElementFormDefault());
		assertSourceContains("@XmlSchema(elementFormDefault = QUALIFIED)", cu);
	}
	
	public void testLocation() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaAndLocation();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		assertNotNull(schemaAnnotation.getLocation());
		
		schemaAnnotation.setLocation(null);
		schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		assertNull(schemaAnnotation.getLocation());
		assertSourceDoesNotContain("@XmlSchema(", cu);
		
		schemaAnnotation = (XmlSchemaAnnotation) packageResource.addAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		schemaAnnotation.setLocation(TEST_LOCATION);
		assertEquals(TEST_LOCATION, schemaAnnotation.getLocation());
		assertSourceContains("@XmlSchema(location = \"" + TEST_LOCATION + "\")", cu);
	}
	
	public void testNamespace() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaAndNamespace();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		assertNotNull(schemaAnnotation.getNamespace());
		
		schemaAnnotation.setNamespace(null);
		schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		assertNull(schemaAnnotation.getNamespace());
		assertSourceDoesNotContain("@XmlSchema(", cu);
		
		schemaAnnotation = (XmlSchemaAnnotation) packageResource.addAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		schemaAnnotation.setNamespace(TEST_NAMESPACE);
		assertEquals(TEST_NAMESPACE, schemaAnnotation.getNamespace());
		assertSourceContains("@XmlSchema(namespace = \"" + TEST_NAMESPACE + "\")", cu);
	}
	
	public void testXmlnsNamespace()
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaAndXmlnsWithNamespace();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		XmlNsAnnotation xmlnsAnnotation = schemaAnnotation.xmlnsAt(0);
		assertNotNull(xmlnsAnnotation.getNamespaceURI());
		
		xmlnsAnnotation.setNamespaceURI(null);
		assertNull(xmlnsAnnotation.getNamespaceURI());
		assertSourceContains("@XmlSchema(xmlns = @XmlNs)", cu);
		
		xmlnsAnnotation.setNamespaceURI(TEST_NAMESPACE_2);
		assertEquals(TEST_NAMESPACE_2, xmlnsAnnotation.getNamespaceURI());
		assertSourceContains("@XmlSchema(xmlns = @XmlNs(namespaceURI = \"" + TEST_NAMESPACE_2 + "\"))", cu);
	}
	
	public void testXmlnsPrefix()
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaAndXmlnsWithPrefix();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		XmlNsAnnotation xmlnsAnnotation = schemaAnnotation.xmlnsAt(0);
		assertNotNull(xmlnsAnnotation.getPrefix());
		
		xmlnsAnnotation.setPrefix(null);
		assertNull(xmlnsAnnotation.getPrefix());
		assertSourceContains("@XmlSchema(xmlns = @XmlNs)", cu);
		
		xmlnsAnnotation.setPrefix(TEST_PREFIX_2);
		assertEquals(TEST_PREFIX_2, xmlnsAnnotation.getPrefix());
		assertSourceContains("@XmlSchema(xmlns = @XmlNs(prefix = \"" + TEST_PREFIX_2 + "\"))", cu);
	}
	
	public void testXmlns()
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithSchemaAndXmlns();
		JavaResourcePackage packageResource = buildJavaResourcePackage(cu); 
		
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) packageResource.getAnnotation(JAXB.XML_SCHEMA);
		assertFalse(CollectionTools.isEmpty(schemaAnnotation.getXmlns()));
		assertEquals(1, schemaAnnotation.getXmlnsSize());
		
		schemaAnnotation.addXmlns(1);
		assertEquals(2, schemaAnnotation.getXmlnsSize());
		assertSourceContains("@XmlSchema(xmlns = {@XmlNs,@XmlNs})", cu);
		
		XmlNsAnnotation xmlnsAnnotation1 = schemaAnnotation.xmlnsAt(0);
		xmlnsAnnotation1.setNamespaceURI(TEST_NAMESPACE);
		xmlnsAnnotation1.setPrefix(TEST_PREFIX);
		XmlNsAnnotation xmlnsAnnotation2 = schemaAnnotation.xmlnsAt(1);
		xmlnsAnnotation2.setNamespaceURI(TEST_NAMESPACE_2);
		xmlnsAnnotation2.setPrefix(TEST_PREFIX_2);
		assertSourceContains(
				"@XmlSchema(xmlns = {@XmlNs(namespaceURI = \"" + TEST_NAMESPACE 
					+ "\", prefix = \"" + TEST_PREFIX 
					+ "\"),@XmlNs(namespaceURI = \"" + TEST_NAMESPACE_2
					+ "\", prefix = \"" + TEST_PREFIX_2
					+ "\")})", cu);
		
		schemaAnnotation.moveXmlns(0, 1);
		assertSourceContains(
				"@XmlSchema(xmlns = {@XmlNs(namespaceURI = \"" + TEST_NAMESPACE_2
					+ "\", prefix = \"" + TEST_PREFIX_2
					+ "\"),@XmlNs(namespaceURI = \"" + TEST_NAMESPACE
					+ "\", prefix = \"" + TEST_PREFIX
					+ "\")})", cu);
		
		schemaAnnotation.removeXmlns(1);
		assertEquals(1, schemaAnnotation.getXmlnsSize());
		assertSourceContains(
				"@XmlSchema(xmlns = @XmlNs(namespaceURI = \"" + TEST_NAMESPACE_2
					+ "\", prefix = \"" + TEST_PREFIX_2
					+ "\"))", cu);
	}
}
