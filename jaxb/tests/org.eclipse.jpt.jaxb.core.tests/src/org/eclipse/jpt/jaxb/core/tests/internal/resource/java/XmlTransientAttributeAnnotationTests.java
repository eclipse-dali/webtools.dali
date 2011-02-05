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
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;

@SuppressWarnings("nls")
public class XmlTransientAttributeAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlTransientAttributeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlTransient() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TRANSIENT);
			}
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@XmlTransient");
			}
		});
	}

	public void testGetXmlTransient() throws Exception {
		ICompilationUnit cu = this.createTestXmlTransient();
		JavaResourceType resourceType = this.buildJavaResourceType(cu); 
		JavaResourceMethod resourceMethod = this.getMethod(resourceType, 0);

		XmlTransientAnnotation xmlTransientAnnotation = (XmlTransientAnnotation) resourceMethod.getAnnotation(JAXB.XML_TRANSIENT);
		assertTrue(xmlTransientAnnotation != null);

		resourceMethod.removeAnnotation(JAXB.XML_TRANSIENT);
		assertSourceDoesNotContain("@XmlTransient", cu);
	}
}
