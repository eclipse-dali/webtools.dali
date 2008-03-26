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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class QueryHintTests extends JavaResourceModelTestCase {

	private static final String QUERY_HINT_NAME = "myHint";
	private static final String QUERY_HINT_VALUE = "myValue";
	
	public QueryHintTests(String name) {
		super(name);
	}

	private void createNamedQueryAnnotation() throws Exception {
		createQueryHintAnnotation();
		this.createAnnotationAndMembers("NamedQuery", "String name(); " +
			"String query();" + 
			"QueryHint[] hints() default{};");
	}
	
	private void createQueryHintAnnotation() throws Exception {
		this.createAnnotationAndMembers("QueryHint", "String name(); " +
			"String value();");
	}
	
	private IType createTestNamedQueryWithQueryHints() throws Exception {
		createNamedQueryAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQuery(hints={@QueryHint(name=\"" + QUERY_HINT_NAME + "\", value=\"" + QUERY_HINT_VALUE + "\"), @QueryHint})");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestNamedQueryWithQueryHints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERY);
		QueryHintAnnotation queryHint = namedQuery.hints().next();
		assertEquals(QUERY_HINT_NAME, queryHint.getName());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestNamedQueryWithQueryHints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERY);
		QueryHintAnnotation queryHint = namedQuery.hints().next();
		assertEquals(QUERY_HINT_NAME, queryHint.getName());
		
		queryHint.setName("foo");
		assertEquals("foo", queryHint.getName());
		
		assertSourceContains("@QueryHint(name=\"foo\", value=\"" + QUERY_HINT_VALUE + "\")");
	}
}
