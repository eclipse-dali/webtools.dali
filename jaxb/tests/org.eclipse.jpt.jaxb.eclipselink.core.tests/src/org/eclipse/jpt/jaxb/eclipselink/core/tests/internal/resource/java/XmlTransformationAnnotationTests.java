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
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlTransformationAnnotation;

@SuppressWarnings("nls")
public class XmlTransformationAnnotationTests
		extends ELJaxbJavaResourceModelTestCase {
	
	public XmlTransformationAnnotationTests(String name) {
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
	
	
	private ICompilationUnit createTestXmlTransformation() 
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(ELJaxb.XML_TRANSFORMATION);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlTransformation");
			}
		});
	}
	
	private ICompilationUnit createTestXmlTransformationWithOptional()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(ELJaxb.XML_TRANSFORMATION);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlTransformation(optional = true)");
			}
		});
	}
	
	private XmlTransformationAnnotation getXmlTransformationAnnotation(JavaResourceAttribute resourceAttribute) {
		return (XmlTransformationAnnotation) resourceAttribute.getAnnotation(ELJaxb.XML_TRANSFORMATION);
	}
	
	
	public void testGetNull() throws Exception {
		ICompilationUnit cu = createTestXmlTransformation();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlTransformationAnnotation annotation = getXmlTransformationAnnotation(resourceAttribute);
		
		assertTrue(annotation != null);
		assertNull(annotation.getOptional());
	}
	
	public void testGetOptional() throws Exception {
		ICompilationUnit cu = createTestXmlTransformationWithOptional();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlTransformationAnnotation annotation = getXmlTransformationAnnotation(resourceAttribute);
		
		assertEquals(Boolean.TRUE, annotation.getOptional());
	}
	
	public void testSetOptional() throws Exception {
		ICompilationUnit cu = createTestXmlTransformation();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceAttribute = getField(resourceType, 0);
		XmlTransformationAnnotation annotation = getXmlTransformationAnnotation(resourceAttribute);
		
		assertNull(annotation.getOptional());
		
		annotation.setOptional(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, annotation.getOptional());
		assertSourceContains("@XmlTransformation(optional = false)", cu);
		
		annotation.setOptional(null);
		
		assertNull(annotation.getOptional());
		assertSourceContains("@XmlTransformation", cu);
		assertSourceDoesNotContain("optional", cu);
	}
}
