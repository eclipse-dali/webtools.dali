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
import org.eclipse.jpt.jaxb.core.resource.java.XmlMimeTypeAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlMimeTypeAnnotationTests extends JaxbJavaResourceModelTestCase {

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

	public void testGetXmlMimeType() throws Exception {
		ICompilationUnit cu = this.createTestXmlMimeType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		XmlMimeTypeAnnotation xmlMimeTypeAnnotation = (XmlMimeTypeAnnotation) attributeResource.getAnnotation(JAXB.XML_MIME_TYPE);
		assertTrue(xmlMimeTypeAnnotation != null);

		attributeResource.removeAnnotation(JAXB.XML_MIME_TYPE);
		assertSourceDoesNotContain("@XmlMimeType", cu);
	}
}
