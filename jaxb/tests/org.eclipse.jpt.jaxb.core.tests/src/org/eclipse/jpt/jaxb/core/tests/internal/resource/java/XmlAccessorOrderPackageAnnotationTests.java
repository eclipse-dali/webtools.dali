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
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;

@SuppressWarnings("nls")
public class XmlAccessorOrderPackageAnnotationTests
		extends JaxbJavaResourceModelTestCase {
	
	public XmlAccessorOrderPackageAnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createPackageInfoWithAccessorOrder() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)",
				JAXB.XML_ACCESS_ORDER, JAXB.XML_ACCESSOR_ORDER);
	}
	
	public void testValue() 
			throws Exception {
		
		ICompilationUnit cu = createPackageInfoWithAccessorOrder();
		JavaResourcePackage resourcePackage = buildJavaResourcePackage(cu); 
		
		XmlAccessorOrderAnnotation annotation = 
				(XmlAccessorOrderAnnotation) resourcePackage.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertTrue(annotation != null);
		assertEquals(XmlAccessOrder.UNDEFINED, annotation.getValue());
		
		annotation.setValue(XmlAccessOrder.ALPHABETICAL);
		assertEquals(XmlAccessOrder.ALPHABETICAL, annotation.getValue());
		assertSourceContains("@XmlAccessorOrder(ALPHABETICAL)", cu);
		
		annotation.setValue(null);
		assertNull(resourcePackage.getAnnotation(JAXB.XML_ACCESSOR_ORDER));
		assertSourceDoesNotContain("@XmlAccessorOrder", cu);
		
//		TODO uncomment when bug 328400 is addressed
//		annotation = (XmlAccessorOrderAnnotation) packageResource.addAnnotation(JAXB.XML_ACCESSOR_ORDER);
//		annotation.setValue(XmlAccessOrder.UNDEFINED);
//		assertEquals(XmlAccessOrder.UNDEFINED, annotation.getValue());
//		assertSourceContains("@XmlAccessorOrder(UNDEFINED)", cu);
	}
}
