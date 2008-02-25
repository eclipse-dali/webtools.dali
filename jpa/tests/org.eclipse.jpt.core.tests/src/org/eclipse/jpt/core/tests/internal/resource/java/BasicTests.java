/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.resource.java.Basic;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class BasicTests extends JavaResourceModelTestCase {
	
	public BasicTests(String name) {
		super(name);
	}

	private IType createTestBasic() throws Exception {
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
	
	private IType createTestBasicWithOptional() throws Exception {
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
	
	private IType createTestBasicWithFetch() throws Exception {
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
		IType testType = this.createTestBasic();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		assertNotNull(basic);
	}

	public void testGetOptional() throws Exception {
		IType testType = this.createTestBasicWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		assertTrue(basic.getOptional());
	}

	public void testSetOptional() throws Exception {
		IType testType = this.createTestBasicWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		assertTrue(basic.getOptional());
		
		basic.setOptional(false);
		assertFalse(basic.getOptional());
		
		assertSourceContains("@Basic(optional=false)");
	}
	
	public void testSetOptionalNull() throws Exception {
		IType testType = this.createTestBasicWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		assertTrue(basic.getOptional());
		
		basic.setOptional(null);
		assertNull(basic.getOptional());
		
		assertSourceContains("@Basic");
		assertSourceDoesNotContain("optional");
	}
	
	public void testGetFetch() throws Exception {
		IType testType = this.createTestBasicWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		assertEquals(FetchType.EAGER, basic.getFetch());
	}

	public void testSetFetch() throws Exception {
		IType testType = this.createTestBasicWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		assertEquals(FetchType.EAGER, basic.getFetch());
		
		basic.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, basic.getFetch());
		
		assertSourceContains("@Basic(fetch=LAZY)");
	}
	
	public void testSetFetchNull() throws Exception {
		IType testType = this.createTestBasicWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		assertEquals(FetchType.EAGER, basic.getFetch());
		
		basic.setFetch(null);
		assertNull(basic.getFetch());
		
		assertSourceContains("@Basic");
		assertSourceDoesNotContain("fetch");
	}
}
