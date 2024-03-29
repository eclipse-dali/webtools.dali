/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchAnnotation;
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
				return IteratorTools.iterator(EclipseLink.JOIN_FETCH, EclipseLink.JOIN_FETCH_TYPE);
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
				return IteratorTools.iterator(EclipseLink.JOIN_FETCH, EclipseLink.JOIN_FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@JoinFetch(JoinFetchType.OUTER)");
			}
		});
	}

	public void testJoinFetch() throws Exception {
		ICompilationUnit cu = this.createTestJoinFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);

		JoinFetchAnnotation joinFetch = (JoinFetchAnnotation) resourceField.getAnnotation(EclipseLink.JOIN_FETCH);
		assertNotNull(joinFetch);
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestJoinFetchWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		JoinFetchAnnotation joinFetch = (JoinFetchAnnotation) resourceField.getAnnotation(EclipseLink.JOIN_FETCH);
		assertEquals(JoinFetchType.OUTER, joinFetch.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestJoinFetchWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		JoinFetchAnnotation joinFetch = (JoinFetchAnnotation) resourceField.getAnnotation(EclipseLink.JOIN_FETCH);
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
