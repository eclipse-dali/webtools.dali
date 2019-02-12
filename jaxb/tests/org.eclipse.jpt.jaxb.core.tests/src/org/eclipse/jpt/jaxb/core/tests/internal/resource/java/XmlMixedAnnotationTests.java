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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMixedAnnotation;

@SuppressWarnings("nls")
public class XmlMixedAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlMixedAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlMixed() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_MIXED);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlMixed");
			}
		});
	}

	public void testGetXmlMixed() throws Exception {
		ICompilationUnit cu = this.createTestXmlMixed();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlMixedAnnotation xmlMixedAnnotation = (XmlMixedAnnotation) resourceAttribute.getAnnotation(JAXB.XML_MIXED);
		assertTrue(xmlMixedAnnotation != null);

		resourceAttribute.removeAnnotation(JAXB.XML_MIXED);
		assertSourceDoesNotContain("@XmlMixed", cu);
	}
}
