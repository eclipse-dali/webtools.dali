/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkExistenceCheckingAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ExistenceType;

@SuppressWarnings("nls")
public class ExistenceCheckingTests extends EclipseLinkJavaResourceModelTestCase {

	public ExistenceCheckingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestExistenceChecking() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.EXISTENCE_CHECKING, EclipseLink.EXISTENCE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@ExistenceChecking");
			}
		});
	}
	
	private ICompilationUnit createTestExistenceCheckingWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.EXISTENCE_CHECKING, EclipseLink.EXISTENCE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@ExistenceChecking(ExistenceType.ASSUME_EXISTENCE)");
			}
		});
	}

	public void testExistenceChecking() throws Exception {
		ICompilationUnit cu = this.createTestExistenceChecking();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkExistenceCheckingAnnotation existenceChecking = (EclipseLinkExistenceCheckingAnnotation) resourceType.getAnnotation(EclipseLink.EXISTENCE_CHECKING);
		assertNotNull(existenceChecking);
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestExistenceCheckingWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkExistenceCheckingAnnotation existenceChecking = (EclipseLinkExistenceCheckingAnnotation) resourceType.getAnnotation(EclipseLink.EXISTENCE_CHECKING);
		assertEquals(ExistenceType.ASSUME_EXISTENCE, existenceChecking.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestExistenceCheckingWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkExistenceCheckingAnnotation existenceChecking = (EclipseLinkExistenceCheckingAnnotation) resourceType.getAnnotation(EclipseLink.EXISTENCE_CHECKING);
		assertEquals(ExistenceType.ASSUME_EXISTENCE, existenceChecking.getValue());
		
		existenceChecking.setValue(ExistenceType.ASSUME_NON_EXISTENCE);
		assertEquals(ExistenceType.ASSUME_NON_EXISTENCE, existenceChecking.getValue());
		
		assertSourceContains("@ExistenceChecking(ASSUME_NON_EXISTENCE)", cu);
		
		existenceChecking.setValue(null);
		assertNull(existenceChecking.getValue());
		
		assertSourceDoesNotContain("(ASSUME_NON_EXISTENCE)", cu);
		assertSourceContains("@ExistenceChecking", cu);
		assertSourceDoesNotContain("@ExistenceChecking(", cu);
	}
}
