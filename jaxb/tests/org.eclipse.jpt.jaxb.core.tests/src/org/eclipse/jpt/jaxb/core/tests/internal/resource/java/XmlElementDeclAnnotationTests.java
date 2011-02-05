/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;

@SuppressWarnings("nls")
public class XmlElementDeclAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_ELEMENT_DECL_NAME = "elementName";
	private static final String XML_ELEMENT_DECL_NAMESPACE = "XmlElementDeclNamespace";
	private static final String XML_ELEMENT_DECL_DEFAULT_VALUE = "myDefaultValue";
	private static final String XML_ELEMENT_DECL_SCOPE = "XmlElementDecl.GLOBAL";

	public XmlElementDeclAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlElementDecl() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_DECL);
			}
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementDecl");
			}
		});
	}

	private ICompilationUnit createTestXmlElementDeclWithName() throws Exception {
		return this.createTestXmlElementDeclWithStringElementDecl("name", XML_ELEMENT_DECL_NAME);
	}

	private ICompilationUnit createTestXmlElementDeclWithNamespace() throws Exception {
		return this.createTestXmlElementDeclWithStringElementDecl("namespace", XML_ELEMENT_DECL_NAMESPACE);
	}

	private ICompilationUnit createTestXmlElementDeclWithDefaultValue() throws Exception {
		return this.createTestXmlElementDeclWithStringElementDecl("defaultValue", XML_ELEMENT_DECL_DEFAULT_VALUE);
	}

	private ICompilationUnit createTestXmlElementDeclWithSubstitutionHeadName() throws Exception {
		return this.createTestXmlElementDeclWithStringElementDecl("substitutionHeadName", XML_ELEMENT_DECL_NAME);
	}

	private ICompilationUnit createTestXmlElementDeclWithSubstitutionHeadNamespace() throws Exception {
		return this.createTestXmlElementDeclWithStringElementDecl("substitutionHeadNamespace", XML_ELEMENT_DECL_NAME);
	}

	private ICompilationUnit createTestXmlElementDeclWithStringElementDecl(final String element, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_DECL);
			}
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementDecl(" + element + " = \"" + value + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlElementDeclWithScope() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ELEMENT_DECL);
			}
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElementDecl(scope = " + XML_ELEMENT_DECL_SCOPE  + ".class)");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDeclWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertTrue(xmlElementDeclAnnotation != null);
		assertEquals(XML_ELEMENT_DECL_NAME, xmlElementDeclAnnotation.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDecl();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertTrue(xmlElementDeclAnnotation != null);
		assertNull(xmlElementDeclAnnotation.getName());
		assertNull(xmlElementDeclAnnotation.getNamespace());
		assertNull(xmlElementDeclAnnotation.getDefaultValue());
		assertNull(xmlElementDeclAnnotation.getScope());
		assertNull(xmlElementDeclAnnotation.getSubstitutionHeadName());
		assertNull(xmlElementDeclAnnotation.getSubstitutionHeadNamespace());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDecl();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(xmlElementDeclAnnotation.getName());
		xmlElementDeclAnnotation.setName(XML_ELEMENT_DECL_NAME);
		assertEquals(XML_ELEMENT_DECL_NAME, xmlElementDeclAnnotation.getName());

		assertSourceContains("@XmlElementDecl(name = \"" + XML_ELEMENT_DECL_NAME + "\")", cu);

		xmlElementDeclAnnotation.setName(null);
		assertNull(xmlElementDeclAnnotation.getName());

		assertSourceContains("@XmlElementDecl", cu);
		assertSourceDoesNotContain("@XmlElementDecl(name = \"" + XML_ELEMENT_DECL_NAME + "\")", cu);
	}

	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDeclWithNamespace();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertTrue(xmlElementDeclAnnotation != null);
		assertEquals(XML_ELEMENT_DECL_NAMESPACE, xmlElementDeclAnnotation.getNamespace());
	}

	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDecl();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(xmlElementDeclAnnotation.getNamespace());
		xmlElementDeclAnnotation.setNamespace(XML_ELEMENT_DECL_NAMESPACE);
		assertEquals(XML_ELEMENT_DECL_NAMESPACE, xmlElementDeclAnnotation.getNamespace());

		assertSourceContains("@XmlElementDecl(namespace = \"" + XML_ELEMENT_DECL_NAMESPACE + "\")", cu);

		xmlElementDeclAnnotation.setNamespace(null);
		assertNull(xmlElementDeclAnnotation.getNamespace());

		assertSourceContains("@XmlElementDecl", cu);
		assertSourceDoesNotContain("@XmlElementDecl(namespace = \"" + XML_ELEMENT_DECL_NAMESPACE + "\")", cu);
	}

	public void testGetDefaultValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDeclWithDefaultValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertTrue(xmlElementDeclAnnotation != null);
		assertEquals(XML_ELEMENT_DECL_DEFAULT_VALUE, xmlElementDeclAnnotation.getDefaultValue());
	}

	public void testSetDefaultValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDecl();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(xmlElementDeclAnnotation.getDefaultValue());
		xmlElementDeclAnnotation.setDefaultValue(XML_ELEMENT_DECL_DEFAULT_VALUE);
		assertEquals(XML_ELEMENT_DECL_DEFAULT_VALUE, xmlElementDeclAnnotation.getDefaultValue());

		assertSourceContains("@XmlElementDecl(defaultValue = \"" + XML_ELEMENT_DECL_DEFAULT_VALUE + "\")", cu);

		xmlElementDeclAnnotation.setDefaultValue(null);
		assertNull(xmlElementDeclAnnotation.getDefaultValue());

		assertSourceContains("@XmlElementDecl", cu);
		assertSourceDoesNotContain("@XmlElementDecl(defaultValue = \"" + XML_ELEMENT_DECL_DEFAULT_VALUE + "\")", cu);
	}

	public void testGetScope() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDeclWithScope();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertTrue(xmlElementDeclAnnotation != null);
		assertEquals(XML_ELEMENT_DECL_SCOPE, xmlElementDeclAnnotation.getScope());
		assertEquals("javax.xml.bind.annotation." + XML_ELEMENT_DECL_SCOPE, xmlElementDeclAnnotation.getFullyQualifiedScopeClassName());
	}

	public void testSetScope() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDecl();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(xmlElementDeclAnnotation.getScope());
		xmlElementDeclAnnotation.setScope(XML_ELEMENT_DECL_SCOPE);
		assertEquals(XML_ELEMENT_DECL_SCOPE, xmlElementDeclAnnotation.getScope());

		assertSourceContains("@XmlElementDecl(scope = " + XML_ELEMENT_DECL_SCOPE  + ".class", cu);

		xmlElementDeclAnnotation.setScope(null);
		assertNull(xmlElementDeclAnnotation.getScope());

		assertSourceContains("@XmlElementDecl", cu);
		assertSourceDoesNotContain("@XmlElementDecl(scope = " + XML_ELEMENT_DECL_SCOPE  + ".class", cu);
	}

	public void testGetSubstitutionHeadName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDeclWithSubstitutionHeadName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertTrue(xmlElementDeclAnnotation != null);
		assertEquals(XML_ELEMENT_DECL_NAME, xmlElementDeclAnnotation.getSubstitutionHeadName());
	}

	public void testSetSubstitutionHeadName() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDecl();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(xmlElementDeclAnnotation.getSubstitutionHeadName());
		xmlElementDeclAnnotation.setSubstitutionHeadName(XML_ELEMENT_DECL_NAME);
		assertEquals(XML_ELEMENT_DECL_NAME, xmlElementDeclAnnotation.getSubstitutionHeadName());

		assertSourceContains("@XmlElementDecl(substitutionHeadName = \"" + XML_ELEMENT_DECL_NAME + "\")", cu);

		xmlElementDeclAnnotation.setSubstitutionHeadName(null);
		assertNull(xmlElementDeclAnnotation.getSubstitutionHeadName());

		assertSourceContains("@XmlElementDecl", cu);
		assertSourceDoesNotContain("@XmlElementDecl(substitutionHeadName = \"" + XML_ELEMENT_DECL_NAME + "\")", cu);
	}

	public void testGetSubstitutionHeadNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDeclWithSubstitutionHeadNamespace();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertTrue(xmlElementDeclAnnotation != null);
		assertEquals(XML_ELEMENT_DECL_NAME, xmlElementDeclAnnotation.getSubstitutionHeadNamespace());
	}

	public void testSetSubstitutionHeadNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlElementDecl();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = getMethod(resourceType, 0);

		XmlElementDeclAnnotation xmlElementDeclAnnotation = (XmlElementDeclAnnotation) resourceMethod.getAnnotation(JAXB.XML_ELEMENT_DECL);
		assertNull(xmlElementDeclAnnotation.getSubstitutionHeadNamespace());
		xmlElementDeclAnnotation.setSubstitutionHeadNamespace(XML_ELEMENT_DECL_NAME);
		assertEquals(XML_ELEMENT_DECL_NAME, xmlElementDeclAnnotation.getSubstitutionHeadNamespace());

		assertSourceContains("@XmlElementDecl(substitutionHeadNamespace = \"" + XML_ELEMENT_DECL_NAME + "\")", cu);

		xmlElementDeclAnnotation.setSubstitutionHeadNamespace(null);
		assertNull(xmlElementDeclAnnotation.getSubstitutionHeadNamespace());

		assertSourceContains("@XmlElementDecl", cu);
		assertSourceDoesNotContain("@XmlElementDecl(substitutionHeadNamespace = \"" + XML_ELEMENT_DECL_NAME + "\")", cu);
	}
}
