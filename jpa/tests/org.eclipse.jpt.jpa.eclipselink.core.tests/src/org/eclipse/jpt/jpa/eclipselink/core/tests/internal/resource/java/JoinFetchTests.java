/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkJoinFetchAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType;

@SuppressWarnings("nls")
public class JoinFetchTests extends EclipseLinkJavaResourceModelTestCase {

	public JoinFetchTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestJoinFetch() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.JOIN_FETCH, EclipseLink.JOIN_FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinFetch");
			}
		});
	}
	
	private ICompilationUnit createTestJoinFetchWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.JOIN_FETCH, EclipseLink.JOIN_FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinFetch(JoinFetchType.OUTER)");
			}
		});
	}

	public void testJoinFetch() throws Exception {
		ICompilationUnit cu = this.createTestJoinFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		EclipseLinkJoinFetchAnnotation joinFetch = (EclipseLinkJoinFetchAnnotation) attributeResource.getAnnotation(EclipseLink.JOIN_FETCH);
		assertNotNull(joinFetch);
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestJoinFetchWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkJoinFetchAnnotation joinFetch = (EclipseLinkJoinFetchAnnotation) attributeResource.getAnnotation(EclipseLink.JOIN_FETCH);
		assertEquals(JoinFetchType.OUTER, joinFetch.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestJoinFetchWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkJoinFetchAnnotation joinFetch = (EclipseLinkJoinFetchAnnotation) attributeResource.getAnnotation(EclipseLink.JOIN_FETCH);
		assertEquals(JoinFetchType.OUTER, joinFetch.getValue());
		
		joinFetch.setValue(JoinFetchType.INNER);
		assertEquals(JoinFetchType.INNER, joinFetch.getValue());
		
		assertSourceContains("@JoinFetch(INNER)", cu);
		
		joinFetch.setValue(null);
		assertNull(joinFetch.getValue());
		
		assertSourceDoesNotContain("(INNER)", cu);
		assertSourceContains("@JoinFetch", cu);
		assertSourceDoesNotContain("@JoinFetch(", cu);
	}
}
