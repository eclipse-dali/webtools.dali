/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3;

@SuppressWarnings("nls")
public class EclipseLinkMultitenantAnnotation2_3Tests
	extends EclipseLink2_3JavaResourceModelTestCase
{
	public EclipseLinkMultitenantAnnotation2_3Tests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestMultitenant() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(EclipseLink.MULTITENANT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Multitenant");
			}
		});
	}
	
	private ICompilationUnit createTestMultitenantWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(EclipseLink.MULTITENANT, EclipseLink.MULTITENANT_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Multitenant(MultitenantType.SINGLE_TABLE)");
			}
		});
	}

	private ICompilationUnit createTestCacheWithIncludeCriteria() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(EclipseLink.MULTITENANT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Multitenant(includeCriteria=true)");
			}
		});
	}

	public void testMultitenant() throws Exception {
		ICompilationUnit cu = this.createTestMultitenant();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		MultitenantAnnotation2_3 multitenant = (MultitenantAnnotation2_3) resourceType.getAnnotation(EclipseLink.MULTITENANT);
		assertNotNull(multitenant);
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestMultitenantWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		MultitenantAnnotation2_3 multitenant = (MultitenantAnnotation2_3) resourceType.getAnnotation(EclipseLink.MULTITENANT);
		assertEquals(MultitenantType2_3.SINGLE_TABLE, multitenant.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestMultitenantWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		MultitenantAnnotation2_3 multitenant = (MultitenantAnnotation2_3) resourceType.getAnnotation(EclipseLink.MULTITENANT);
		assertEquals(MultitenantType2_3.SINGLE_TABLE, multitenant.getValue());
		
		multitenant.setValue(MultitenantType2_3.TABLE_PER_TENANT);
		assertEquals(MultitenantType2_3.TABLE_PER_TENANT, multitenant.getValue());
		
		assertSourceContains("@Multitenant(TABLE_PER_TENANT)", cu);
		
		multitenant.setValue(null);
		assertNull(multitenant.getValue());
		
		assertSourceDoesNotContain("(TABLE_PER_TENANT)", cu);
		assertSourceContains("@Multitenant", cu);
		assertSourceDoesNotContain("@Multitenant(", cu);
	}

//
//	public void testGetIncludeCriteria() throws Exception {
//		ICompilationUnit cu = this.createTestCacheWithIncludeCriteria();
//		JavaResourceType resourceType = buildJavaResourceType(cu);
//		
//		EclipseLinkMultitenantAnnotation2_3 multitenant = (EclipseLinkMultitenantAnnotation2_3) resourceType.getAnnotation(EclipseLink.MULTITENANT);
//		assertEquals(Boolean.TRUE, multitenant.getIncludeCriteria());
//	}
//	
//	public void testSetIncludeCriteria() throws Exception {
//		ICompilationUnit cu = this.createTestCacheWithIncludeCriteria();
//		JavaResourceType resourceType = buildJavaResourceType(cu);
//		
//		EclipseLinkMultitenantAnnotation2_3 multitenant = (EclipseLinkMultitenantAnnotation2_3) resourceType.getAnnotation(EclipseLink.MULTITENANT);
//		assertEquals(Boolean.TRUE, multitenant.getIncludeCriteria());
//		
//		multitenant.setIncludeCriteria(Boolean.FALSE);
//		assertEquals(Boolean.FALSE, multitenant.getIncludeCriteria());
//		
//		assertSourceContains("@Multitenant(includeCriteria=false)", cu);		
//	}
//	
//	public void testSetIncludeCriteriaNull() throws Exception {
//		ICompilationUnit cu = this.createTestCacheWithIncludeCriteria();
//		JavaResourceType resourceType = buildJavaResourceType(cu);
//		
//		EclipseLinkMultitenantAnnotation2_3 multitenant = (EclipseLinkMultitenantAnnotation2_3) resourceType.getAnnotation(EclipseLink.MULTITENANT);
//		assertEquals(Boolean.TRUE, multitenant.getIncludeCriteria());
//		
//		multitenant.setIncludeCriteria(null);
//		multitenant = (EclipseLinkMultitenantAnnotation2_3) resourceType.getAnnotation(EclipseLink.MULTITENANT);
//		assertNull(multitenant.getIncludeCriteria());
//		
//		assertSourceContains("@Multitenant", cu);
//		assertSourceDoesNotContain("@Multitenant(", cu);
//	}
}
