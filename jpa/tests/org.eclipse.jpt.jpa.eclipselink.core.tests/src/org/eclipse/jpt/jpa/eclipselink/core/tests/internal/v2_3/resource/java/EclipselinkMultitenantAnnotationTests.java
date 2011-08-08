/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkMultitenantAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.MultitenantType;

@SuppressWarnings("nls")
public class EclipselinkMultitenantAnnotationTests extends EclipseLink2_3JavaResourceModelTestCase {

	public EclipselinkMultitenantAnnotationTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestMultitenant() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_3.MULTITENANT);
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
				return new ArrayIterator<String>(EclipseLink2_3.MULTITENANT, EclipseLink2_3.MULTITENANT_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Multitenant(MultitenantType.SINGLE_TABLE)");
			}
		});
	}

	public void testMultitenant() throws Exception {
		ICompilationUnit cu = this.createTestMultitenant();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		EclipseLinkMultitenantAnnotation multitenant = (EclipseLinkMultitenantAnnotation) resourceType.getAnnotation(EclipseLink2_3.MULTITENANT);
		assertNotNull(multitenant);
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestMultitenantWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkMultitenantAnnotation multitenant = (EclipseLinkMultitenantAnnotation) resourceType.getAnnotation(EclipseLink2_3.MULTITENANT);
		assertEquals(MultitenantType.SINGLE_TABLE, multitenant.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestMultitenantWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkMultitenantAnnotation multitenant = (EclipseLinkMultitenantAnnotation) resourceType.getAnnotation(EclipseLink2_3.MULTITENANT);
		assertEquals(MultitenantType.SINGLE_TABLE, multitenant.getValue());
		
		multitenant.setValue(MultitenantType.TABLE_PER_TENANT);
		assertEquals(MultitenantType.TABLE_PER_TENANT, multitenant.getValue());
		
		assertSourceContains("@Multitenant(TABLE_PER_TENANT)", cu);
		
		multitenant.setValue(null);
		assertNull(multitenant.getValue());
		
		assertSourceDoesNotContain("(TABLE_PER_TENANT)", cu);
		assertSourceContains("@Multitenant", cu);
		assertSourceDoesNotContain("@Multitenant(", cu);
	}
}
