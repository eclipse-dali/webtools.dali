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
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlValueAnnotation;

@SuppressWarnings("nls")
public class XmlValueAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlValueAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_VALUE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlValue");
			}
		});
	}

	public void testGetXmlValue() throws Exception {
		ICompilationUnit cu = this.createTestXmlValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlValueAnnotation xmlValueAnnotation = (XmlValueAnnotation) resourceAttribute.getAnnotation(JAXB.XML_VALUE);
		assertTrue(xmlValueAnnotation != null);

		resourceAttribute.removeAnnotation(JAXB.XML_VALUE);
		assertSourceDoesNotContain("@XmlValue", cu);
	}
}
