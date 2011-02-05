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
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyAttributeAnnotation;

@SuppressWarnings("nls")
public class XmlAnyAttributeAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlAnyAttributeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlAnyAttribute() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ANY_ATTRIBUTE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAnyAttribute");
			}
		});
	}

	public void testGetXmlAnyAttribute() throws Exception {
		ICompilationUnit cu = this.createTestXmlAnyAttribute();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAnyAttributeAnnotation xmlAnyAttributeAnnotation = (XmlAnyAttributeAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ANY_ATTRIBUTE);
		assertTrue(xmlAnyAttributeAnnotation != null);

		resourceAttribute.removeAnnotation(JAXB.XML_ANY_ATTRIBUTE);
		assertSourceDoesNotContain("@XmlAnyAttribute", cu);
	}
}
