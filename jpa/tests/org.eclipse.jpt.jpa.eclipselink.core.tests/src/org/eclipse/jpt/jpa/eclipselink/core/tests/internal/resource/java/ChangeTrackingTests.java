/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingAnnotation;

@SuppressWarnings("nls")
public class ChangeTrackingTests extends EclipseLinkJavaResourceModelTestCase {

	public ChangeTrackingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestChangeTracking() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(EclipseLink.CHANGE_TRACKING, EclipseLink.CHANGE_TRACKING_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@ChangeTracking");
			}
		});
	}
	
	private ICompilationUnit createTestChangeTrackingWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(EclipseLink.CHANGE_TRACKING, EclipseLink.CHANGE_TRACKING_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@ChangeTracking(ChangeTrackingType.DEFERRED)");
			}
		});
	}

	public void testChangeTracking() throws Exception {
		ICompilationUnit cu = this.createTestChangeTracking();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		ChangeTrackingAnnotation existenceChecking = (ChangeTrackingAnnotation) resourceType.getAnnotation(EclipseLink.CHANGE_TRACKING);
		assertNotNull(existenceChecking);
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestChangeTrackingWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		ChangeTrackingAnnotation existenceChecking = (ChangeTrackingAnnotation) resourceType.getAnnotation(EclipseLink.CHANGE_TRACKING);
		assertEquals(ChangeTrackingType.DEFERRED, existenceChecking.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestChangeTrackingWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		ChangeTrackingAnnotation existenceChecking = (ChangeTrackingAnnotation) resourceType.getAnnotation(EclipseLink.CHANGE_TRACKING);
		assertEquals(ChangeTrackingType.DEFERRED, existenceChecking.getValue());
		
		existenceChecking.setValue(ChangeTrackingType.ATTRIBUTE);
		assertEquals(ChangeTrackingType.ATTRIBUTE, existenceChecking.getValue());
		
		assertSourceContains("@ChangeTracking(ATTRIBUTE)", cu);
		
		existenceChecking.setValue(null);
		assertNull(existenceChecking.getValue());
		
		assertSourceDoesNotContain("(ATTRIBUTE)", cu);
		assertSourceContains("@ChangeTracking", cu);
		assertSourceDoesNotContain("@ChangeTracking(", cu);
	}
}
