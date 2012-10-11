/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlCDATAAnnotation;


public class XmlCDATAAnnotationTests
		extends ELJaxbJavaResourceModelTestCase {
	
	public XmlCDATAAnnotationTests(String name) {
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
	
	
	private ICompilationUnit createTestXmlCDATA() 
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(ELJaxb.XML_CDATA);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlCDATA");
			}
		});
	}
	
	private XmlCDATAAnnotation getXmlCDATAAnnotation(JavaResourceAttribute resourceAttribute) {
		return (XmlCDATAAnnotation) resourceAttribute.getAnnotation(ELJaxb.XML_CDATA);
	}
	
	
	public void testGetNull() throws Exception {
		ICompilationUnit cu = createTestXmlCDATA();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlCDATAAnnotation annotation = getXmlCDATAAnnotation(resourceAttribute);
		
		assertTrue(annotation != null);
	}
}
