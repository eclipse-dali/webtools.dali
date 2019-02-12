/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;

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
				return IteratorTools.iterator(JAXB.XML_ACCESSOR_ORDER);
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
				return IteratorTools.iterator(JAXB.XML_ACCESSOR_ORDER, JAXB.XML_ACCESS_ORDER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAccessorOrder(value = XmlAccessOrder.ALPHABETICAL)");
			}
		});
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorOrder();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlAccessorOrderAnnotation xmlAccessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertTrue(xmlAccessorOrderAnnotation != null);
		assertNull(xmlAccessorOrderAnnotation.getValue());
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorOrderWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);

		XmlAccessorOrderAnnotation xmlAccessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertEquals(XmlAccessOrder.ALPHABETICAL, xmlAccessorOrderAnnotation.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorOrder();
		JavaResourceType resourceType = buildJavaResourceType(cu);

		XmlAccessorOrderAnnotation xmlAccessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertEquals(null, xmlAccessorOrderAnnotation.getValue());

		xmlAccessorOrderAnnotation.setValue(XmlAccessOrder.UNDEFINED);
		assertEquals(XmlAccessOrder.UNDEFINED, xmlAccessorOrderAnnotation.getValue());

		assertSourceContains("@XmlAccessorOrder(UNDEFINED)", cu);
	}

	public void testSetValueNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlAccessorOrderWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);

		XmlAccessorOrderAnnotation xmlAccessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertEquals(XmlAccessOrder.ALPHABETICAL, xmlAccessorOrderAnnotation.getValue());

		xmlAccessorOrderAnnotation.setValue(null);
		assertNull(xmlAccessorOrderAnnotation.getValue());
	}
}
