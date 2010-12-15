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
import org.eclipse.jpt.jaxb.core.resource.java.XmlListAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlListAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlListAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlList() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_LIST);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlList");
			}
		});
	}

	public void testGetXmlList() throws Exception {
		ICompilationUnit cu = this.createTestXmlList();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlListAnnotation xmlListAnnotation = (XmlListAnnotation) resourceAttribute.getAnnotation(JAXB.XML_LIST);
		assertTrue(xmlListAnnotation != null);

		resourceAttribute.removeAnnotation(JAXB.XML_LIST);
		assertSourceDoesNotContain("@XmlList", cu);
	}
}
