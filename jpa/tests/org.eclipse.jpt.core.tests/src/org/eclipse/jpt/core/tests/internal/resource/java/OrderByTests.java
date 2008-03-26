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
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.OrderBy;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class OrderByTests extends JavaResourceModelTestCase {

	public OrderByTests(String name) {
		super(name);
	}

	private IType createTestOrderBy() throws Exception {
		this.createAnnotationAndMembers("OrderBy", "String value() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ORDER_BY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OrderBy");
			}
		});
	}
	
	private IType createTestOrderByWithValue() throws Exception {
		this.createAnnotationAndMembers("OrderBy", "String value() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ORDER_BY);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OrderBy(value=\"key\")");
			}
		});
	}

	public void testOrderBy() throws Exception {
		IType testType = this.createTestOrderBy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OrderBy orderBy = (OrderBy) attributeResource.getAnnotation(JPA.ORDER_BY);
		assertNotNull(orderBy);
	}
	
	public void testGetValue() throws Exception {
		IType testType = this.createTestOrderByWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		OrderBy orderBy = (OrderBy) attributeResource.getAnnotation(JPA.ORDER_BY);
		assertEquals("key", orderBy.getValue());
	}
	
	public void testSetValue() throws Exception {
		IType testType = this.createTestOrderBy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		OrderBy orderBy = (OrderBy) attributeResource.getAnnotation(JPA.ORDER_BY);

		orderBy.setValue("foo");
		
		assertSourceContains("@OrderBy(\"foo\")");
		
		orderBy.setValue(null);
		
		assertSourceContains("@OrderBy");
	}

}
