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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlMixedAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlMixedAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlMixed() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_MIXED);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlMixed");
			}
		});
	}

	public void testGetXmlMixed() throws Exception {
		ICompilationUnit cu = this.createTestXmlMixed();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlMixedAnnotation xmlMixedAnnotation = (XmlMixedAnnotation) attributeResource.getAnnotation(JAXB.XML_MIXED);
		assertTrue(xmlMixedAnnotation != null);

		attributeResource.removeAnnotation(JAXB.XML_MIXED);
		assertSourceDoesNotContain("@XmlMixed", cu);
	}
}
