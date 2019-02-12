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
import org.eclipse.jpt.jaxb.core.resource.java.XmlRegistryAnnotation;

@SuppressWarnings("nls")
public class XmlRegistryAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlRegistryAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlRegistry() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_REGISTRY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlRegistry");
			}
		});
	}

	public void testGetXmlRegistry() throws Exception {
		ICompilationUnit cu = this.createTestXmlRegistry();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlRegistryAnnotation xmlRegistryAnnotation = (XmlRegistryAnnotation) resourceType.getAnnotation(JAXB.XML_REGISTRY);
		assertTrue(xmlRegistryAnnotation != null);

		resourceType.removeAnnotation(JAXB.XML_REGISTRY);
		assertSourceDoesNotContain("@XmlRegistry", cu);
	}
}
