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
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlTransientAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlTransientAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlTransient() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TRANSIENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlTransient");
			}
		});
	}

	public void testGetXmlTransient() throws Exception {
		ICompilationUnit cu = this.createTestXmlTransient();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlTransientAnnotation xmlTransientAnnotation = (XmlTransientAnnotation) typeResource.getAnnotation(JAXB.XML_TRANSIENT);
		assertTrue(xmlTransientAnnotation != null);

		typeResource.removeAnnotation(JAXB.XML_TRANSIENT);
		assertSourceDoesNotContain("@XmlTransient", cu);
	}
}
