/*******************************************************************************
 *  Copyright (c) 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;

@SuppressWarnings("nls")
public class EclipseLinkPrimaryKeyAnnotationTests
	extends EclipseLinkJavaResourceModelTestCase
{
	public EclipseLinkPrimaryKeyAnnotationTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestTypeWithPrimaryKeyAnnotation() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.PRIMARY_KEY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKey");
			}
		});
	}
	
	
	public void testAddRemoveAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestTypeWithPrimaryKeyAnnotation();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		assertNotNull(resourceType.getAnnotation(EclipseLink.PRIMARY_KEY));
		
		resourceType.removeAnnotation(EclipseLink.PRIMARY_KEY);
		assertNull(resourceType.getAnnotation(EclipseLink.PRIMARY_KEY));
		
		resourceType.addAnnotation(EclipseLink.PRIMARY_KEY);
		assertNotNull(resourceType.getAnnotation(EclipseLink.PRIMARY_KEY));
	}
}
