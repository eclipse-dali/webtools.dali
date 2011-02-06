/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueriesAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;

@SuppressWarnings("nls")
public class NamedQueriesTests extends JpaJavaResourceModelTestCase {

	private static final String QUERY_NAME = "myQuery";
	private static final String QUERY_QUERY = "SELECT name FROM Employee";
	
	public NamedQueriesTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestNamedQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERIES, JPA.NAMED_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQueries(@NamedQuery)");
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
				return new ArrayIterator<String>(JPA.NAMED_QUERIES, JPA.NAMED_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQueries(@NamedQuery(" + elementName + " = \"" + value + "\"))");
			}
		});
	}

	private ICompilationUnit createTestNamedQueryWithQueryHints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERIES, JPA.NAMED_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQueries(@NamedQuery(hints = {@QueryHint(name = \"BAR\", value = \"FOO\"), @QueryHint}))");
			}
		});
	}


	private ICompilationUnit createTestNamedQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQuery(name = \"foo\", query = \"bar\", hints = @QueryHint(name = \"BAR\", value = \"FOO\"))");
			}
		});
	}

	public void testNamedQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueries();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		assertNotNull(namedQuery);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		assertEquals(QUERY_NAME, namedQuery.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
	}

	public void testSetQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQuery();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
		
		namedQuery.setQuery("foo");
		assertEquals("foo", namedQuery.getQuery());
		
		assertSourceContains("@NamedQuery(query = \"foo\")", cu);
		
		namedQuery.setQuery(null);
		assertNull(namedQuery.getQuery());
		
		assertSourceDoesNotContain("@NamedQuery(", cu);
	}
	
	public void testHints() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueries();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		
		assertEquals(0, namedQuery.hintsSize());
	}
	
	public void testHints2() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueries();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		
		namedQuery.addHint(0);
		namedQuery.addHint(1);
		
		assertEquals(2, namedQuery.hintsSize());
	}
	
	public void testHints3() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQueryHints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		
		assertEquals(2, namedQuery.hintsSize());
	}
	
	public void testAddHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueries();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		
		namedQuery.addHint(0).setName("FOO");
		namedQuery.addHint(1);
		namedQuery.addHint(0).setName("BAR");

		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals("FOO", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());

		assertSourceContains("@NamedQuery(hints = {@QueryHint(name = \"BAR\"),@QueryHint(name = \"FOO\"), @QueryHint})", cu);
	}
	
	public void testRemoveHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQueryHints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
		
		namedQuery.removeHint(2);
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertEquals(2, namedQuery.hintsSize());
		assertSourceContains("@NamedQueries(@NamedQuery(hints = {@QueryHint(name = \"BAZ\"), @QueryHint(name = \"BAR\", value = \"FOO\")}))", cu);
		
		namedQuery.removeHint(0);
		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals(1, namedQuery.hintsSize());
		assertSourceContains("@NamedQueries(@NamedQuery(hints = @QueryHint(name = \"BAR\", value = \"FOO\")))", cu);
		
		namedQuery.removeHint(0);
		assertEquals(0, namedQuery.hintsSize());
		assertSourceDoesNotContain("@NamedQuery(", cu);
	}
	
	public void testMoveHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQueryHints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
	
		namedQuery.moveHint(2, 0);
		
		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertNull(namedQuery.hintAt(1).getName());
		assertEquals("BAZ", namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
		assertSourceContains("@NamedQueries(@NamedQuery(hints = {@QueryHint(name = \"BAR\", value = \"FOO\"), @QueryHint, @QueryHint(name = \"BAZ\")}))", cu);
	}
	
	public void testMoveHint2() throws Exception {
		ICompilationUnit cu = this.createTestNamedQueryWithQueryHints();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		NamedQueriesAnnotation namedQueries = (NamedQueriesAnnotation) typeResource.getAnnotation(JPA.NAMED_QUERIES);
		NamedQueryAnnotation namedQuery = namedQueries.getNestedAnnotations().iterator().next();
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
	
		namedQuery.moveHint(0, 2);
		
		assertNull(namedQuery.hintAt(0).getName());
		assertEquals("BAZ", namedQuery.hintAt(1).getName());
		assertEquals("BAR", namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
		assertSourceContains("@NamedQueries(@NamedQuery(hints = {@QueryHint, @QueryHint(name = \"BAZ\"), @QueryHint(name = \"BAR\", value = \"FOO\")}))", cu);
	}
	
	public void testAddNamedQueryCopyExisting() throws Exception {
		ICompilationUnit cu = createTestNamedQuery();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) typeResource.addAnnotation(1, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAR");
		assertSourceContains("@NamedQueries({@NamedQuery(name = \"foo\", query = \"bar\", hints = @QueryHint(name = \"BAR\", value = \"FOO\")),@NamedQuery(name = \"BAR\")})", cu);
		
		assertNull(typeResource.getAnnotation(JPA.NAMED_QUERY));
		assertNotNull(typeResource.getAnnotation(JPA.NAMED_QUERIES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.NAMED_QUERY, JPA.NAMED_QUERIES)));
	}
	
	public void testAddNamedQueryToBeginningOfList() throws Exception {
		ICompilationUnit cu = createTestNamedQuery();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) typeResource.addAnnotation(1, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAR");
		assertSourceContains("@NamedQueries({@NamedQuery(name = \"foo\", query = \"bar\", hints = @QueryHint(name = \"BAR\", value = \"FOO\")),@NamedQuery(name = \"BAR\")})", cu);
		
		
		namedQuery = (NamedQueryAnnotation) typeResource.addAnnotation(0, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAZ");
		assertSourceContains("@NamedQueries({@NamedQuery(name = \"BAZ\"),@NamedQuery(name = \"foo\", query = \"bar\", hints = @QueryHint(name = \"BAR\", value = \"FOO\")), @NamedQuery(name = \"BAR\")})", cu);

		Iterator<NestableAnnotation> namedQueries = typeResource.annotations(JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		assertEquals("BAZ", ((NamedQueryAnnotation) namedQueries.next()).getName());
		assertEquals("foo", ((NamedQueryAnnotation) namedQueries.next()).getName());
		assertEquals("BAR", ((NamedQueryAnnotation) namedQueries.next()).getName());

		assertNull(typeResource.getAnnotation(JPA.NAMED_QUERY));
		assertNotNull(typeResource.getAnnotation(JPA.NAMED_QUERIES));
		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.NAMED_QUERY, JPA.NAMED_QUERIES)));
	}

	public void testRemoveNamedQueryCopyExisting() throws Exception {
		ICompilationUnit cu = createTestNamedQuery();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		NamedQueryAnnotation namedQuery = (NamedQueryAnnotation) typeResource.addAnnotation(1, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAR");
		assertSourceContains("@NamedQueries({@NamedQuery(name = \"foo\", query = \"bar\", hints = @QueryHint(name = \"BAR\", value = \"FOO\")),@NamedQuery(name = \"BAR\")})", cu);
		
		typeResource.removeAnnotation(1, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		assertSourceContains("@NamedQuery(name = \"foo\", query = \"bar\", hints = @QueryHint(name = \"BAR\", value = \"FOO\"))", cu);
	}
	
}
