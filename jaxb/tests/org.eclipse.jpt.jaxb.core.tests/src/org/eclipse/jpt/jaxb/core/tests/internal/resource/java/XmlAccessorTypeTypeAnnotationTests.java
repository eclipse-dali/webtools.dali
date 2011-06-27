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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;

@SuppressWarnings("nls")
public class XmlAccessorTypeTypeAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
	public XmlAccessorTypeTypeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlAccessorType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ACCESSOR_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAccessorType");
			}
		});
	}

	private ICompilationUnit createTestXmlAccessorTypeWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ACCESSOR_TYPE, JAXB.XML_ACCESS_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAccessorType(value = XmlAccessType.FIELD)");
			}
		});
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlAccessorTypeAnnotation xmlAccessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_TYPE);
		assertTrue(xmlAccessorTypeAnnotation != null);
		assertNull(xmlAccessorTypeAnnotation.getValue());
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorTypeWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);

		XmlAccessorTypeAnnotation xmlAccessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_TYPE);
		assertEquals(XmlAccessType.FIELD, xmlAccessorTypeAnnotation.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorType();
		JavaResourceType resourceType = buildJavaResourceType(cu);

		XmlAccessorTypeAnnotation xmlAccessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_TYPE);
		assertEquals(null, xmlAccessorTypeAnnotation.getValue());

		xmlAccessorTypeAnnotation.setValue(XmlAccessType.PUBLIC_MEMBER);
		assertEquals(XmlAccessType.PUBLIC_MEMBER, xmlAccessorTypeAnnotation.getValue());

		assertSourceContains("@XmlAccessorType(PUBLIC_MEMBER)", cu);

		xmlAccessorTypeAnnotation.setValue(XmlAccessType.PROPERTY);
		assertEquals(XmlAccessType.PROPERTY, xmlAccessorTypeAnnotation.getValue());

		assertSourceContains("@XmlAccessorType(PROPERTY)", cu);

		xmlAccessorTypeAnnotation.setValue(XmlAccessType.NONE);
		assertEquals(XmlAccessType.NONE, xmlAccessorTypeAnnotation.getValue());

		assertSourceContains("@XmlAccessorType(NONE)", cu);
	}

	public void testSetValueNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorTypeWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);

		XmlAccessorTypeAnnotation xmlAccessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_TYPE);
		assertEquals(XmlAccessType.FIELD, xmlAccessorTypeAnnotation.getValue());

		xmlAccessorTypeAnnotation.setValue(null);
		assertNull(xmlAccessorTypeAnnotation.getValue());

		assertSourceDoesNotContain("@XmlAccessorType(", cu);
	}
}
