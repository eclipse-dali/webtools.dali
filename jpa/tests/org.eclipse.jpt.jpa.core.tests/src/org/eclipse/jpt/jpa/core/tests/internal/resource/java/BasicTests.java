/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class BasicTests extends JpaJavaResourceModelTestCase {
	
	public BasicTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestBasic() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.BASIC);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic");
			}
		});
	}
	
	private ICompilationUnit createTestBasicWithOptional() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.BASIC);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic(optional = true)");
			}
		});
	}
	
	private ICompilationUnit createTestBasicWithFetch() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.BASIC, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic(fetch = FetchType.EAGER)");
			}
		});
	}

	public void testBasic() throws Exception {
		ICompilationUnit cu = this.createTestBasic();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		BasicAnnotation basic = (BasicAnnotation) resourceField.getAnnotation(JPA.BASIC);
		assertNotNull(basic);
	}

	public void testGetOptional() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithOptional();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		BasicAnnotation basic = (BasicAnnotation) resourceField.getAnnotation(JPA.BASIC);
		assertEquals(Boolean.TRUE, basic.getOptional());
	}

	public void testSetOptional() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithOptional();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		BasicAnnotation basic = (BasicAnnotation) resourceField.getAnnotation(JPA.BASIC);
		assertEquals(Boolean.TRUE, basic.getOptional());
		
		basic.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, basic.getOptional());
		
		assertSourceContains("@Basic(optional = false)", cu);
	}
	
	public void testSetOptionalNull() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithOptional();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		BasicAnnotation basic = (BasicAnnotation) resourceField.getAnnotation(JPA.BASIC);
		assertEquals(Boolean.TRUE, basic.getOptional());
		
		basic.setOptional(null);
		assertNull(basic.getOptional());
		
		assertSourceContains("@Basic", cu);
		assertSourceDoesNotContain("optional", cu);
	}
	
	public void testGetFetch() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		BasicAnnotation basic = (BasicAnnotation) resourceField.getAnnotation(JPA.BASIC);
		assertEquals(FetchType.EAGER, basic.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		BasicAnnotation basic = (BasicAnnotation) resourceField.getAnnotation(JPA.BASIC);
		assertEquals(FetchType.EAGER, basic.getFetch());
		
		basic.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, basic.getFetch());
		
		assertSourceContains("@Basic(fetch = LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = IterableTools.get(resourceType.getFields(), 0);
		
		BasicAnnotation basic = (BasicAnnotation) resourceField.getAnnotation(JPA.BASIC);
		assertEquals(FetchType.EAGER, basic.getFetch());
		
		basic.setFetch(null);
		assertNull(basic.getFetch());
		
		assertSourceContains("@Basic", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
}
