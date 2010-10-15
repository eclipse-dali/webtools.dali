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
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlTypeAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_TYPE_NAME = "XmlTypeName";
	private static final String XML_TYPE_NAMESPACE = "XmlTypeNamespace";
	private static final String XML_TYPE_FACTORY_METHOD = "myFactoryMethod";
	private static final String XML_TYPE_FACTORY_CLASS = "MyFactoryClass";

	public XmlTypeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
		});
	}

	private ICompilationUnit createTestXmlTypeWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType(name = \"" + XML_TYPE_NAME + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlTypeWithNamespace() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType(namespace = \"" + XML_TYPE_NAMESPACE + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlTypeWithFactoryMethod() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType(factoryMethod = \"" + XML_TYPE_FACTORY_METHOD + "\")");
			}
		});
	}

	private ICompilationUnit createTestXmlTypeWithFactoryClass() throws Exception {
		this.createTestFactoryClass();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType(factoryClass = " + XML_TYPE_FACTORY_CLASS  + ".class)");
			}
		});
	}

	private void createTestFactoryClass() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append(XML_TYPE_FACTORY_CLASS).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "MyFactoryClass.java", sourceWriter);
	}

	private ICompilationUnit createTestXmlTypeWithPropOrder() throws Exception {
		this.createTestFactoryClass();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType(propOrder = {\"foo\", \"bar\"})");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);
		assertEquals(XML_TYPE_NAME, xmlTypeAnnotation.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);
		assertNull(xmlTypeAnnotation.getName());
		assertNull(xmlTypeAnnotation.getNamespace());
		assertNull(xmlTypeAnnotation.getFactoryClass());
		assertNull(xmlTypeAnnotation.getFullyQualifiedFactoryClassName());
		assertNull(xmlTypeAnnotation.getFactoryMethod());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestXmlType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getName());
		xmlTypeAnnotation.setName(XML_TYPE_NAME);
		assertEquals(XML_TYPE_NAME, xmlTypeAnnotation.getName());

		assertSourceContains("@XmlType(name = \"" + XML_TYPE_NAME + "\")", cu);
	}

	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertEquals(XML_TYPE_NAME, xmlTypeAnnotation.getName());

		xmlTypeAnnotation.setName(null);
		assertNull(xmlTypeAnnotation.getName());

		assertSourceContains("@XmlType", cu);
		assertSourceDoesNotContain("@XmlType(name = \"" + XML_TYPE_NAME + "\")", cu);
	}

	public void testGetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithNamespace();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);
		assertEquals(XML_TYPE_NAMESPACE, xmlTypeAnnotation.getNamespace());
	}

	public void testSetNamespace() throws Exception {
		ICompilationUnit cu = this.createTestXmlType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getNamespace());
		xmlTypeAnnotation.setNamespace(XML_TYPE_NAMESPACE);
		assertEquals(XML_TYPE_NAMESPACE, xmlTypeAnnotation.getNamespace());

		assertSourceContains("@XmlType(namespace = \"" + XML_TYPE_NAMESPACE + "\")", cu);
	}

	public void testSetNamespaceNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithNamespace();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertEquals(XML_TYPE_NAMESPACE, xmlTypeAnnotation.getNamespace());

		xmlTypeAnnotation.setNamespace(null);
		assertNull(xmlTypeAnnotation.getNamespace());

		assertSourceContains("@XmlType", cu);
		assertSourceDoesNotContain("@XmlType(namespace = \"" + XML_TYPE_NAMESPACE + "\")", cu);
	}

	public void testGetFactoryMethod() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithFactoryMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);
		assertEquals(XML_TYPE_FACTORY_METHOD, xmlTypeAnnotation.getFactoryMethod());
	}

	public void testSetFactoryMethod() throws Exception {
		ICompilationUnit cu = this.createTestXmlType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getFactoryMethod());
		xmlTypeAnnotation.setFactoryMethod(XML_TYPE_FACTORY_METHOD);
		assertEquals(XML_TYPE_FACTORY_METHOD, xmlTypeAnnotation.getFactoryMethod());

		assertSourceContains("@XmlType(factoryMethod = \"" + XML_TYPE_FACTORY_METHOD + "\")", cu);
	}

	public void testSetFactoryMethodNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithFactoryMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertEquals(XML_TYPE_FACTORY_METHOD, xmlTypeAnnotation.getFactoryMethod());

		xmlTypeAnnotation.setFactoryMethod(null);
		assertNull(xmlTypeAnnotation.getFactoryMethod());

		assertSourceContains("@XmlType", cu);
		assertSourceDoesNotContain("@XmlType(factoryMethod = \"" + XML_TYPE_FACTORY_METHOD + "\")", cu);
	}

	public void testGetFactoryClass() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithFactoryClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);
		assertEquals(XML_TYPE_FACTORY_CLASS, xmlTypeAnnotation.getFactoryClass());
		assertEquals("test." + XML_TYPE_FACTORY_CLASS, xmlTypeAnnotation.getFullyQualifiedFactoryClassName());
	}

	public void testSetFactoryClass() throws Exception {
		ICompilationUnit cu = this.createTestXmlType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getFactoryClass());
		xmlTypeAnnotation.setFactoryClass(XML_TYPE_FACTORY_CLASS);
		assertEquals(XML_TYPE_FACTORY_CLASS, xmlTypeAnnotation.getFactoryClass());

		assertSourceContains("@XmlType(factoryClass = " + XML_TYPE_FACTORY_CLASS  + ".class", cu);
	}

	public void testSetFactoryClassNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithFactoryClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertEquals(XML_TYPE_FACTORY_CLASS, xmlTypeAnnotation.getFactoryClass());

		xmlTypeAnnotation.setFactoryClass(null);
		assertNull(xmlTypeAnnotation.getFactoryClass());

		assertSourceContains("@XmlType", cu);
		assertSourceDoesNotContain("@XmlType(factoryClass = " + XML_TYPE_FACTORY_CLASS  + ".class", cu);
	}

	public void testGetPropOrder() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithPropOrder();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);
		ListIterator<String> propOrder = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("foo", propOrder.next());
		assertEquals("bar", propOrder.next());
	}

	public void testGetPropOrderSize() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithPropOrder();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);
		assertEquals(2, xmlTypeAnnotation.getPropOrderSize());
	}

	public void testAddProp() throws Exception {
		ICompilationUnit cu = this.createTestXmlType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);

		xmlTypeAnnotation.addProp("fooo");
		xmlTypeAnnotation.addProp("barr");

		assertSourceContains("@XmlType(propOrder = { \"fooo\", \"barr\" })", cu);
	}

	public void testAddPropIndex() throws Exception {
		ICompilationUnit cu = this.createTestXmlType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);

		xmlTypeAnnotation.addProp(0, "fooo");
		xmlTypeAnnotation.addProp(0, "barr");
		xmlTypeAnnotation.addProp(1, "blah");

		assertSourceContains("@XmlType(propOrder = { \"barr\", \"blah\", \"fooo\" })", cu);
	}

	public void testRemoveProp() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithPropOrder();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);

		xmlTypeAnnotation.removeProp("foo");
		assertSourceContains("@XmlType(propOrder = \"bar\")", cu);

		xmlTypeAnnotation.removeProp("bar");
		assertSourceContains("@XmlType", cu);
		assertSourceDoesNotContain("propOrder", cu);
	}

	public void testRemovePropIndex() throws Exception {
		ICompilationUnit cu = this.createTestXmlTypeWithPropOrder();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);

		xmlTypeAnnotation.removeProp(0);
		assertSourceContains("@XmlType(propOrder = \"bar\")", cu);

		xmlTypeAnnotation.removeProp(0);
		assertSourceContains("@XmlType", cu);
		assertSourceDoesNotContain("propOrder", cu);
	}

	public void testMoveProp() throws Exception {
		ICompilationUnit cu = this.createTestXmlType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) typeResource.getAnnotation(JAXB.XML_TYPE);
		assertTrue(xmlTypeAnnotation != null);

		xmlTypeAnnotation.addProp("fooo");
		xmlTypeAnnotation.addProp("barr");
		xmlTypeAnnotation.addProp("blah");
		assertSourceContains("@XmlType(propOrder = { \"fooo\", \"barr\", \"blah\" })", cu);

		xmlTypeAnnotation.moveProp(0, 1);
		assertSourceContains("@XmlType(propOrder = { \"barr\", \"fooo\", \"blah\" })", cu);

		xmlTypeAnnotation.moveProp(2, 1);
		assertSourceContains("@XmlType(propOrder = { \"barr\", \"blah\", \"fooo\" })", cu);		
	}
}
