/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

@SuppressWarnings("nls")
public class NamedQueryTests extends JpaJavaResourceModelTestCase {

	private static final String QUERY_NAME = "myQuery";
	private static final String QUERY_QUERY = "SELECT name FROM Employee";
	
	public NamedQueryTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestNamedQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQuery");
			}
		});
	}
	
	private ICompilationUnit createTestNamedQueryWithName() throws Exception {
		return createTestNamedQueryWithStringElement("name", QUERY_NAME);
	}
	
	private ICompilationUnit createTestNamedQueryWithQuery() throws Exception {
		return createTestNamedQueryWithStringElement("query", QUERY_QUERY);
	}
	
	private ICompilationUnit createTestNamedQueryWithStringElement(final String elementName, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQuery(" + elementName + " = \"" + value + "\")");
			}
		});
	}

	private ICompilationUnit createTestNamedQueryWithQueryHints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQuery(hints = {@QueryHint(name = \"BAR\", value = \"FOO\"), @QueryHint})");
			}
		});
	}

	public void testNamedQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		assertNotNull(namedQuery);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		assertEquals(QUERY_NAME, namedQuery.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		assertEquals(QUERY_NAME, namedQuery.getName());
		
		namedQuery.setName("foo");
		assertEquals("foo", namedQuery.getName());
		
		assertSourceContains("@NamedQuery(name = \"foo\")", cu);
		
		namedQuery.setName(null);
		assertNull(namedQuery.getName());
		
		assertSourceDoesNotContain("@NamedQuery(", cu);
	}

	public void testGetQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
	}

	public void testSetQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
		
		namedQuery.setQuery("foo");
		assertEquals("foo", namedQuery.getQuery());
		
		assertSourceContains("@NamedQuery(query = \"foo\")", cu);
		
		namedQuery.setQuery(null);
		assertNull(namedQuery.getQuery());
		
		assertSourceDoesNotContain("@NamedQuery(", cu);
	}
	
	public void testHints() throws Exception {
		ICompilationUnit cu = this.createTestNamedQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		
		assertEquals(0, namedQuery.getHintsSize());
	}
	
	public void testHints2() throws Exception {
		ICompilationUnit cu = this.createTestNamedQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		
		namedQuery.addHint(0);
		namedQuery.addHint(1);
		
		assertEquals(2, namedQuery.getHintsSize());
	}
	
	public void testHints3() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQueryHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		
		assertEquals(2, namedQuery.getHintsSize());
		
		ListIterator<QueryHintAnnotation> iterator = namedQuery.getHints().iterator();
		assertEquals("BAR", iterator.next().getName());
		assertNull(iterator.next().getName());
	}
	
	public void testAddHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
	
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		
		namedQuery.addHint(0).setName("FOO");
		namedQuery.addHint(1);
		QueryHintAnnotation queryHint = namedQuery.addHint(0);
		queryHint.setName("BAR");

		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals("FOO", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertSourceContains("@NamedQuery(hints = {@QueryHint(name = \"BAR\"),@QueryHint(name = \"FOO\"), @QueryHint})", cu);
	}
	
	public void testRemoveHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQueryHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.getHintsSize());
		
		namedQuery.removeHint(2);
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertEquals(2, namedQuery.getHintsSize());
		assertSourceContains("@NamedQuery(hints = {@QueryHint(name = \"BAZ\"), @QueryHint(name = \"BAR\", value = \"FOO\")})", cu);
		
		namedQuery.removeHint(0);
		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals(1, namedQuery.getHintsSize());
		assertSourceContains("@NamedQuery(hints = @QueryHint(name = \"BAR\", value = \"FOO\"))", cu);
		
		namedQuery.removeHint(0);
		assertEquals(0, namedQuery.getHintsSize());
		assertSourceDoesNotContain("@NamedQuery(", cu);
	}
	
	public void testMoveHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQueryHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.getHintsSize());
	
		namedQuery.moveHint(2, 0);
		
		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertNull(namedQuery.hintAt(1).getName());
		assertEquals("BAZ", namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.getHintsSize());
		assertSourceContains("@NamedQuery(hints = {@QueryHint(name = \"BAR\", value = \"FOO\"), @QueryHint, @QueryHint(name = \"BAZ\")})", cu);
	}
	
	public void testMoveHint2() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQueryHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_QUERY);
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.getHintsSize());
	
		namedQuery.moveHint(0, 2);
		
		assertNull(namedQuery.hintAt(0).getName());
		assertEquals("BAZ", namedQuery.hintAt(1).getName());
		assertEquals("BAR", namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.getHintsSize());
		assertSourceContains("@NamedQuery(hints = {@QueryHint, @QueryHint(name = \"BAZ\"), @QueryHint(name = \"BAR\", value = \"FOO\")})", cu);
	}
	
}
