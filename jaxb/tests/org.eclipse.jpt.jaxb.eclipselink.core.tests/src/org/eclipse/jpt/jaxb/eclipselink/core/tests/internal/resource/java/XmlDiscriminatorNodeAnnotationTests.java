/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorNodeAnnotation;

@SuppressWarnings("nls")
public class XmlDiscriminatorNodeAnnotationTests
		extends ELJaxbJavaResourceModelTestCase {
	
	public XmlDiscriminatorNodeAnnotationTests(String name) {
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
	
	
	private ICompilationUnit createTestXmlDiscriminatorNodeWithValue()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(ELJaxb.XML_DISCRIMINATOR_NODE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlDiscriminatorNode(\"foo\")");
			}
		});
	}
	
	
	private XmlDiscriminatorNodeAnnotation getXmlDiscriminatorNodeAnnotation(JavaResourceType resourceType) {
		return (XmlDiscriminatorNodeAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
	}
	
	
	public void testValue() throws Exception {
		ICompilationUnit cu = createTestXmlDiscriminatorNodeWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		XmlDiscriminatorNodeAnnotation annotation = getXmlDiscriminatorNodeAnnotation(resourceType);
		
		assertEquals("foo", annotation.getValue());
		assertSourceContains("@XmlDiscriminatorNode(\"foo\")", cu);
		
		annotation.setValue("bar");
		
		assertEquals("bar", annotation.getValue());
		assertSourceContains("@XmlDiscriminatorNode(\"bar\")", cu);
		
		annotation.setValue("");
		
		assertEquals("", annotation.getValue());
		assertSourceContains("@XmlDiscriminatorNode(\"\")", cu);
		
		annotation.setValue(null);
		
		assertNull(annotation.getValue());
		assertSourceContains("@XmlDiscriminatorNode", cu);
		assertSourceDoesNotContain("@XmlDiscriminatorNode(", cu);
	}
}
