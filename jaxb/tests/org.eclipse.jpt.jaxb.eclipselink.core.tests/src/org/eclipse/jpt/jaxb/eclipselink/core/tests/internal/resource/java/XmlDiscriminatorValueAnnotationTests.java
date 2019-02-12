/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorValueAnnotation;

@SuppressWarnings("nls")
public class XmlDiscriminatorValueAnnotationTests
		extends ELJaxbJavaResourceModelTestCase {
	
	public XmlDiscriminatorValueAnnotationTests(String name) {
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
	
	
	private ICompilationUnit createTestXmlDiscriminatorValueWithValue()
			throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(ELJaxb.XML_DISCRIMINATOR_VALUE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlDiscriminatorValue(\"foo\")");
			}
		});
	}
	
	
	private XmlDiscriminatorValueAnnotation getXmlDiscriminatorValueAnnotation(JavaResourceType resourceType) {
		return (XmlDiscriminatorValueAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
	}
	
	
	public void testValue() throws Exception {
		ICompilationUnit cu = createTestXmlDiscriminatorValueWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		XmlDiscriminatorValueAnnotation annotation = getXmlDiscriminatorValueAnnotation(resourceType);
		
		assertEquals("foo", annotation.getValue());
		assertSourceContains("@XmlDiscriminatorValue(\"foo\")", cu);
		
		annotation.setValue("bar");
		
		assertEquals("bar", annotation.getValue());
		assertSourceContains("@XmlDiscriminatorValue(\"bar\")", cu);
		
		annotation.setValue("");
		
		assertEquals("", annotation.getValue());
		assertSourceContains("@XmlDiscriminatorValue(\"\")", cu);
		
		annotation.setValue(null);
		
		assertNull(annotation.getValue());
		assertSourceContains("@XmlDiscriminatorValue", cu);
		assertSourceDoesNotContain("@XmlDiscriminatorValue(", cu);
	}
}
