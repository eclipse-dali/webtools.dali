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
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMimeTypeAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlMimeTypeAnnotationTests extends JaxbJavaResourceModelTestCase {

	private static final String XML_MIME_TYPE_VALUE = "myMimeType";

	public XmlMimeTypeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlMimeType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_MIME_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlMimeType");
			}
		});
	}

	private ICompilationUnit createTestXmlMimeTypeWithValue() throws Exception {
		return this.createTestXmlMimeTypeWithStringElement("value", XML_MIME_TYPE_VALUE);
	}

	private ICompilationUnit createTestXmlMimeTypeWithStringElement(final String element, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_MIME_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlMimeType(" + element + " = \"" + value + "\")");
			}
		});
	}

	public void testGetXmlMimeType() throws Exception {
		ICompilationUnit cu = this.createTestXmlMimeType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlMimeTypeAnnotation xmlMimeTypeAnnotation = (XmlMimeTypeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIME_TYPE);
		assertTrue(xmlMimeTypeAnnotation != null);

		resourceAttribute.removeAnnotation(JAXB.XML_MIME_TYPE);
		assertSourceDoesNotContain("@XmlMimeType", cu);
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlMimeTypeWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlMimeTypeAnnotation xmlMimeTypeAnnotation = (XmlMimeTypeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIME_TYPE);
		assertTrue(xmlMimeTypeAnnotation != null);
		assertEquals(XML_MIME_TYPE_VALUE, xmlMimeTypeAnnotation.getValue());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlMimeType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlMimeTypeAnnotation xmlMimeTypeAnnotation = (XmlMimeTypeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIME_TYPE);
		assertTrue(xmlMimeTypeAnnotation != null);
		assertNull(xmlMimeTypeAnnotation.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlMimeType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlMimeTypeAnnotation xmlMimeTypeAnnotation = (XmlMimeTypeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIME_TYPE);
		assertNull(xmlMimeTypeAnnotation.getValue());
		xmlMimeTypeAnnotation.setValue(XML_MIME_TYPE_VALUE);
		assertEquals(XML_MIME_TYPE_VALUE, xmlMimeTypeAnnotation.getValue());

		assertSourceContains("@XmlMimeType(\"" + XML_MIME_TYPE_VALUE + "\")", cu);

		xmlMimeTypeAnnotation.setValue(null);
		assertNull(xmlMimeTypeAnnotation.getValue());

		assertSourceDoesNotContain("@XmlMimeType", cu);
	}
}
