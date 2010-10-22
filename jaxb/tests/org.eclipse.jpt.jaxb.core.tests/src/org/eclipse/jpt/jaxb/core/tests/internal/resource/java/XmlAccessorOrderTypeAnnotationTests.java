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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlAccessorOrderTypeAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
	public XmlAccessorOrderTypeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlAccessorOrder() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ACCESSOR_ORDER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAccessorOrder");
			}
		});
	}

	private ICompilationUnit createTestXmlAccessorOrderWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ACCESSOR_ORDER, JAXB.XML_ACCESS_ORDER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAccessorOrder(value = XmlAccessOrder.ALPHABETICAL)");
			}
		});
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorOrder();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlAccessorOrderAnnotation xmlAccessorOrderAnnotation = (XmlAccessorOrderAnnotation) typeResource.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertTrue(xmlAccessorOrderAnnotation != null);
		assertNull(xmlAccessorOrderAnnotation.getValue());
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorOrderWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);

		XmlAccessorOrderAnnotation xmlAccessorOrderAnnotation = (XmlAccessorOrderAnnotation) typeResource.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertEquals(XmlAccessOrder.ALPHABETICAL, xmlAccessorOrderAnnotation.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorOrder();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);

		XmlAccessorOrderAnnotation xmlAccessorOrderAnnotation = (XmlAccessorOrderAnnotation) typeResource.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertEquals(null, xmlAccessorOrderAnnotation.getValue());

		xmlAccessorOrderAnnotation.setValue(XmlAccessOrder.UNDEFINED);
		assertEquals(XmlAccessOrder.UNDEFINED, xmlAccessorOrderAnnotation.getValue());

		assertSourceContains("@XmlAccessorOrder(UNDEFINED)", cu);
	}

	public void testSetValueNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorOrderWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);

		XmlAccessorOrderAnnotation xmlAccessorOrderAnnotation = (XmlAccessorOrderAnnotation) typeResource.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertEquals(XmlAccessOrder.ALPHABETICAL, xmlAccessorOrderAnnotation.getValue());

		xmlAccessorOrderAnnotation.setValue(null);
		assertNull(xmlAccessorOrderAnnotation.getValue());

		assertSourceDoesNotContain("@XmlAccessorOrder", cu);
	}
}
