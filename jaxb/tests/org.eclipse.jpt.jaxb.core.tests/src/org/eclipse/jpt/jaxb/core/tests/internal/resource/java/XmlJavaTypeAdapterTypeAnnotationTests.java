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
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlJavaTypeAdapterTypeAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_JAVA_TYPE_ADAPTER_CLASS = "MyAdapterClass";

	public XmlJavaTypeAdapterTypeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlJavaTypeAdapter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_JAVA_TYPE_ADAPTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlJavaTypeAdapter");
			}
		});
	}

	private ICompilationUnit createTestXmlJavaTypeAdapterWithValue() throws Exception {
		this.createTestAdapterClass();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_JAVA_TYPE_ADAPTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlJavaTypeAdapter(value = " + XML_JAVA_TYPE_ADAPTER_CLASS  + ".class)");
			}
		});
	}

	private void createTestAdapterClass() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append(XML_JAVA_TYPE_ADAPTER_CLASS).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "MyAdapterClass.java", sourceWriter);
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlJavaTypeAdapter();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertTrue(xmlJavaTypeAdapterAnnotation != null);
		assertNull(xmlJavaTypeAdapterAnnotation.getValue());
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlJavaTypeAdapterWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertTrue(xmlJavaTypeAdapterAnnotation != null);
		assertEquals(XML_JAVA_TYPE_ADAPTER_CLASS, xmlJavaTypeAdapterAnnotation.getValue());
		assertEquals("test." + XML_JAVA_TYPE_ADAPTER_CLASS, xmlJavaTypeAdapterAnnotation.getFullyQualifiedValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlJavaTypeAdapter();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlJavaTypeAdapterAnnotation.getValue());
		xmlJavaTypeAdapterAnnotation.setValue(XML_JAVA_TYPE_ADAPTER_CLASS);
		assertEquals(XML_JAVA_TYPE_ADAPTER_CLASS, xmlJavaTypeAdapterAnnotation.getValue());

		assertSourceContains("@XmlJavaTypeAdapter(" + XML_JAVA_TYPE_ADAPTER_CLASS + ".class)", cu);
	}

	public void testSetValueNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlJavaTypeAdapterWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertEquals(XML_JAVA_TYPE_ADAPTER_CLASS, xmlJavaTypeAdapterAnnotation.getValue());

		xmlJavaTypeAdapterAnnotation.setValue(null);
		assertNull(xmlJavaTypeAdapterAnnotation.getValue());

		assertSourceContains("@XmlJavaTypeAdapter", cu);
		assertSourceDoesNotContain("@XmlJavaTypeAdapter(" + XML_JAVA_TYPE_ADAPTER_CLASS + ".class)", cu);
	}
}
