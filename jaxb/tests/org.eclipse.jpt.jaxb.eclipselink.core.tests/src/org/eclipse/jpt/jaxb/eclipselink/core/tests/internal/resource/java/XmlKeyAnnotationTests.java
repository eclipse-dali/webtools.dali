/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlKeyAnnotation;

@SuppressWarnings("nls")
public class XmlKeyAnnotationTests
		extends ELJaxbJavaResourceModelTestCase {
	
	public XmlKeyAnnotationTests(String name) {
		super(name);
	}
	
	
	@Override
	protected AnnotationDefinition[] annotationDefinitions() {
		return ELJaxb_2_2_PlatformDefinition.instance().getAnnotationDefinitions();
	}
	
	@Override
	protected NestableAnnotationDefinition[] nestableAnnotationDefinitions() {
		return ELJaxb_2_2_PlatformDefinition.instance().getNestableAnnotationDefinitions();
	}
	
	
	private ICompilationUnit createTestXmlKey() 
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(ELJaxb.XML_KEY);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlKey");
			}
		});
	}
	
	private XmlKeyAnnotation getXmlKeyAnnotation(JavaResourceAttribute resourceAttribute) {
		return (XmlKeyAnnotation) resourceAttribute.getAnnotation(ELJaxb.XML_KEY);
	}
	
	
	public void testGetNull() throws Exception {
		ICompilationUnit cu = createTestXmlKey();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlKeyAnnotation annotation = getXmlKeyAnnotation(resourceAttribute);
		
		assertTrue(annotation != null);
	}
}
