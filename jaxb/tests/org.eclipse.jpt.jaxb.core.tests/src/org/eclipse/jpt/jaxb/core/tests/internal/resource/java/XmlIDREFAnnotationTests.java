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
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;

@SuppressWarnings("nls")
public class XmlIDREFAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlIDREFAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlIDREF() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_IDREF);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlIDREF");
			}
		});
	}

	public void testGetXmlIDREF() throws Exception {
		ICompilationUnit cu = this.createTestXmlIDREF();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlIDREFAnnotation xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertTrue(xmlIDREFAnnotation != null);

		resourceAttribute.removeAnnotation(JAXB.XML_IDREF);
		assertSourceDoesNotContain("@XmlIDREF", cu);
	}
}
