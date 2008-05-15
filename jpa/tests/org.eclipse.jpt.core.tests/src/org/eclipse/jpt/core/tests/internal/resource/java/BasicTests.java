/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class BasicTests extends JavaResourceModelTestCase {
	
	public BasicTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestBasic() throws Exception {
		this.createAnnotationAndMembers("Basic", "boolean optional() default true;");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.BASIC);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic");
			}
		});
	}
	
	private ICompilationUnit createTestBasicWithOptional() throws Exception {
		this.createAnnotationAndMembers("Basic", "boolean optional() default true;");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.BASIC);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic(optional=true)");
			}
		});
	}
	
	private ICompilationUnit createTestBasicWithFetch() throws Exception {
		this.createAnnotationAndMembers("Basic", "boolean optional() default true; FetchType fetch() default FetchType.EAGER;");
		this.createEnumAndMembers("FetchType", "EAGER, LAZY");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.BASIC, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic(fetch=FetchType.EAGER)");
			}
		});
	}

	public void testBasic() throws Exception {
		ICompilationUnit cu = this.createTestBasic();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		assertNotNull(basic);
	}

	public void testGetOptional() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		assertTrue(basic.getOptional());
	}

	public void testSetOptional() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		assertTrue(basic.getOptional());
		
		basic.setOptional(false);
		assertFalse(basic.getOptional());
		
		assertSourceContains("@Basic(optional=false)", cu);
	}
	
	public void testSetOptionalNull() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		assertTrue(basic.getOptional());
		
		basic.setOptional(null);
		assertNull(basic.getOptional());
		
		assertSourceContains("@Basic", cu);
		assertSourceDoesNotContain("optional", cu);
	}
	
	public void testGetFetch() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		assertEquals(FetchType.EAGER, basic.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		assertEquals(FetchType.EAGER, basic.getFetch());
		
		basic.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, basic.getFetch());
		
		assertSourceContains("@Basic(fetch=LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestBasicWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		assertEquals(FetchType.EAGER, basic.getFetch());
		
		basic.setFetch(null);
		assertNull(basic.getFetch());
		
		assertSourceContains("@Basic", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
}
