/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JoinFetchTests extends EclipseLinkJavaResourceModelTestCase {

	public JoinFetchTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestJoinFetch() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.JOIN_FETCH, EclipseLinkJPA.JOIN_FETCH_TYPE);
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
				return new ArrayIterator<String>(EclipseLinkJPA.JOIN_FETCH, EclipseLinkJPA.JOIN_FETCH_TYPE);
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

		JoinFetchAnnotation joinFetch = (JoinFetchAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.JOIN_FETCH);
		assertNotNull(joinFetch);
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestJoinFetchWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinFetchAnnotation joinFetch = (JoinFetchAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.JOIN_FETCH);
		assertEquals(JoinFetchType.OUTER, joinFetch.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestJoinFetchWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		JoinFetchAnnotation joinFetch = (JoinFetchAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.JOIN_FETCH);
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
