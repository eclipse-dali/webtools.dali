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
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttachmentRefAnnotation;

@SuppressWarnings("nls")
public class XmlAttachmentRefAnnotationTests extends JaxbJavaResourceModelTestCase {

	public XmlAttachmentRefAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlAttachmentRef() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_ATTACHMENT_REF);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAttachmentRef");
			}
		});
	}

	public void testGetXmlAttachmentRef() throws Exception {
		ICompilationUnit cu = this.createTestXmlAttachmentRef();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);

		XmlAttachmentRefAnnotation xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertTrue(xmlAttachmentRefAnnotation != null);

		resourceAttribute.removeAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertSourceDoesNotContain("@XmlAttachmentRef", cu);
	}
}
