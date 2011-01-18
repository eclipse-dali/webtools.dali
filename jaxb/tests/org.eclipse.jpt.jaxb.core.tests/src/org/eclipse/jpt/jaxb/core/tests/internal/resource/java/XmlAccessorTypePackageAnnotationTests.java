/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;

@SuppressWarnings("nls")
public class XmlAccessorTypePackageAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
	public XmlAccessorTypePackageAnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createPackageInfoWithAccessorType() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorType(value = XmlAccessType.PROPERTY)",
				JAXB.XML_ACCESS_TYPE, JAXB.XML_ACCESSOR_TYPE);
	}
	
	public void testValue() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithAccessorType();
		JavaResourcePackage resourcePackage = buildJavaResourcePackage(cu); 
		
		XmlAccessorTypeAnnotation annotation = 
				(XmlAccessorTypeAnnotation) resourcePackage.getAnnotation(JAXB.XML_ACCESSOR_TYPE);
		assertTrue(annotation != null);
		assertEquals(XmlAccessType.PROPERTY, annotation.getValue());
		
		annotation.setValue(XmlAccessType.FIELD);
		assertEquals(XmlAccessType.FIELD, annotation.getValue());
		assertSourceContains("@XmlAccessorType(value = FIELD)", cu);
		
		annotation.setValue(XmlAccessType.NONE);
		assertEquals(XmlAccessType.NONE, annotation.getValue());
		assertSourceContains("@XmlAccessorType(value = NONE)", cu);
		
		annotation.setValue(XmlAccessType.PUBLIC_MEMBER);
		assertEquals(XmlAccessType.PUBLIC_MEMBER, annotation.getValue());
		assertSourceContains("@XmlAccessorType(value = PUBLIC_MEMBER)", cu);
		
		annotation.setValue(null);
		assertSourceDoesNotContain("@XmlAccessorType(", cu);
		
//		TODO uncomment when bug 328400 is addressed
//		annotation = (XmlAccessorTypeAnnotation) packageResource.addAnnotation(JAXB.XML_ACCESSOR_TYPE);
//		annotation.setValue(XmlAccessType.PROPERTY);
//		assertEquals(XmlAccessType.PROPERTY, annotation.getValue());
//		assertSourceContains("@XmlAccessorType(PROPERTY)", cu);
	}
}
