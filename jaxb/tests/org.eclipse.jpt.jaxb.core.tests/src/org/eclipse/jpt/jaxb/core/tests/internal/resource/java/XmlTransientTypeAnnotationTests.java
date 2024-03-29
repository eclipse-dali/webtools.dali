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
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;

@SuppressWarnings("nls")
public class XmlTransientTypeAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlTransientTypeAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlTransient() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_TRANSIENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlTransient");
			}
		});
	}

	public void testGetXmlTransient() throws Exception {
		ICompilationUnit cu = this.createTestXmlTransient();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlTransientAnnotation xmlTransientAnnotation = (XmlTransientAnnotation) resourceType.getAnnotation(JAXB.XML_TRANSIENT);
		assertTrue(xmlTransientAnnotation != null);

		resourceType.removeAnnotation(JAXB.XML_TRANSIENT);
		assertSourceDoesNotContain("@XmlTransient", cu);
	}
}
