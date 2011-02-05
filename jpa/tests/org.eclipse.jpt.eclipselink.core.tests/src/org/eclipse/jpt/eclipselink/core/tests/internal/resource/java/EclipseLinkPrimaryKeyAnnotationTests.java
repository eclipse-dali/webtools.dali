/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;

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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		assertNotNull(typeResource.getAnnotation(EclipseLink.PRIMARY_KEY));
		
		typeResource.removeAnnotation(EclipseLink.PRIMARY_KEY);
		assertNull(typeResource.getAnnotation(EclipseLink.PRIMARY_KEY));
		
		typeResource.addAnnotation(EclipseLink.PRIMARY_KEY);
		assertNotNull(typeResource.getAnnotation(EclipseLink.PRIMARY_KEY));
	}
}
