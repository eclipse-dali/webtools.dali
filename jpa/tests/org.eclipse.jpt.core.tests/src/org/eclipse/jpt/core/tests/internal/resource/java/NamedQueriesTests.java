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
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.NamedQueries;
import org.eclipse.jpt.core.internal.resource.java.NamedQuery;
import org.eclipse.jpt.core.internal.resource.java.QueryHint;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class NamedQueriesTests extends JavaResourceModelTestCase {

	private static final String QUERY_NAME = "myQuery";
	private static final String QUERY_QUERY = "SELECT name FROM Employee";
	
	public NamedQueriesTests(String name) {
		super(name);
	}

	private void createNamedQueryAnnotation() throws Exception {
		createQueryHintAnnotation();
		this.createAnnotationAndMembers("NamedQuery", "String name(); " +
			"String query();" + 
			"QueryHint[] hints() default{};");
	}
	
	private void createNamedQueriesAnnotation() throws Exception {
		createNamedQueryAnnotation();
		this.createAnnotationAndMembers("NamedQueries", 
			"NamedQuery[] value();");
	}
	
	private void createQueryHintAnnotation() throws Exception {
		this.createAnnotationAndMembers("QueryHint", "String name(); " +
			"String value();");
	}
	
	private IType createTestNamedQueries() throws Exception {
		createNamedQueriesAnnotation();
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
	
	private IType createTestNamedQueryWithName() throws Exception {
		return createTestNamedQueryWithStringElement("name", QUERY_NAME);
	}
	
	private IType createTestNamedQueryWithQuery() throws Exception {
		return createTestNamedQueryWithStringElement("query", QUERY_QUERY);
	}
	
	private IType createTestNamedQueryWithStringElement(final String elementName, final String value) throws Exception {
		createNamedQueriesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERIES, JPA.NAMED_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQueries(@NamedQuery(" + elementName + "=\"" + value + "\"))");
			}
		});
	}

	private IType createTestNamedQueryWithQueryHints() throws Exception {
		createNamedQueriesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERIES, JPA.NAMED_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQueries(@NamedQuery(hints={@QueryHint(name=\"BAR\", value=\"FOO\"), @QueryHint}))");
			}
		});
	}


	private IType createTestNamedQuery() throws Exception {
		createNamedQueryAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedQuery(name=\"foo\", query=\"bar\", hints=@QueryHint(name=\"BAR\", value=\"FOO\"))");
			}
		});
	}

	public void testNamedQuery() throws Exception {
		IType testType = this.createTestNamedQueries();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		assertNotNull(namedQuery);
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestNamedQueryWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_NAME, namedQuery.getName());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestNamedQueryWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_NAME, namedQuery.getName());
		
		namedQuery.setName("foo");
		assertEquals("foo", namedQuery.getName());
		
		assertSourceContains("@NamedQuery(name=\"foo\")");
		
		namedQuery.setName(null);
		assertNull(namedQuery.getName());
		
		assertSourceDoesNotContain("@NamedQuery");
	}

	public void testGetQuery() throws Exception {
		IType testType = this.createTestNamedQueryWithQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
	}

	public void testSetQuery() throws Exception {
		IType testType = this.createTestNamedQueryWithQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
		
		namedQuery.setQuery("foo");
		assertEquals("foo", namedQuery.getQuery());
		
		assertSourceContains("@NamedQuery(query=\"foo\")");
		
		namedQuery.setQuery(null);
		assertNull(namedQuery.getQuery());
		
		assertSourceDoesNotContain("@NamedQuery");
	}
	
	public void testHints() throws Exception {
		IType testType = this.createTestNamedQueries();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		
		ListIterator<QueryHint> iterator = namedQuery.hints();
		
		assertEquals(0, CollectionTools.size(iterator));
	}
	
	public void testHints2() throws Exception {
		IType testType = this.createTestNamedQueries();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		
		namedQuery.addHint(0);
		namedQuery.addHint(1);
		namedQuery.updateFromJava(JDTTools.buildASTRoot(testType));
		
		ListIterator<QueryHint> iterator = namedQuery.hints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testHints3() throws Exception {
		IType testType = this.createTestNamedQueryWithQueryHints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		
		ListIterator<QueryHint> iterator = namedQuery.hints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testAddHint() throws Exception {
		IType testType = this.createTestNamedQueries();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		
		namedQuery.addHint(0).setName("FOO");
		namedQuery.addHint(1);
		namedQuery.addHint(0).setName("BAR");

		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals("FOO", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());

		assertSourceContains("@NamedQuery(hints={@QueryHint(name=\"BAR\"),@QueryHint(name=\"FOO\"), @QueryHint})");
	}
	
	public void testRemoveHint() throws Exception {
		IType testType = this.createTestNamedQueryWithQueryHints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
		
		namedQuery.removeHint(2);
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertEquals(2, namedQuery.hintsSize());
		assertSourceContains("@NamedQueries(@NamedQuery(hints={@QueryHint(name=\"BAZ\"), @QueryHint(name=\"BAR\", value=\"FOO\")}))");
		
		namedQuery.removeHint(0);
		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals(1, namedQuery.hintsSize());
		assertSourceContains("@NamedQueries(@NamedQuery(hints=@QueryHint(name=\"BAR\", value=\"FOO\")))");
		
		namedQuery.removeHint(0);
		assertEquals(0, namedQuery.hintsSize());
		assertSourceDoesNotContain("@NamedQueries");
	}
	
	public void testMoveHint() throws Exception {
		IType testType = this.createTestNamedQueryWithQueryHints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
	
		namedQuery.moveHint(0, 2);
		
		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertNull(namedQuery.hintAt(1).getName());
		assertEquals("BAZ", namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
		assertSourceContains("@NamedQueries(@NamedQuery(hints={@QueryHint(name=\"BAR\", value=\"FOO\"), @QueryHint, @QueryHint(name=\"BAZ\")}))");
	}
	
	public void testMoveHint2() throws Exception {
		IType testType = this.createTestNamedQueryWithQueryHints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedQueries namedQueries = (NamedQueries) typeResource.annotation(JPA.NAMED_QUERIES);
		NamedQuery namedQuery = namedQueries.nestedAnnotations().next();
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
	
		namedQuery.moveHint(2, 0);
		
		assertNull(namedQuery.hintAt(0).getName());
		assertEquals("BAZ", namedQuery.hintAt(1).getName());
		assertEquals("BAR", namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.hintsSize());
		assertSourceContains("@NamedQueries(@NamedQuery(hints={@QueryHint, @QueryHint(name=\"BAZ\"), @QueryHint(name=\"BAR\", value=\"FOO\")}))");
	}
	
	public void testAddNamedQueryCopyExisting() throws Exception {
		IType jdtType = createTestNamedQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		NamedQuery namedQuery = (NamedQuery) typeResource.addAnnotation(1, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAR");
		assertSourceContains("@NamedQueries({@NamedQuery(name=\"foo\", query = \"bar\", hints = @QueryHint(name=\"BAR\", value = \"FOO\")),@NamedQuery(name=\"BAR\")})");
		
		assertNull(typeResource.annotation(JPA.NAMED_QUERY));
		assertNotNull(typeResource.annotation(JPA.NAMED_QUERIES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.NAMED_QUERY, JPA.NAMED_QUERIES)));
	}
	
	public void testAddNamedQueryToBeginningOfList() throws Exception {
		IType jdtType = createTestNamedQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		NamedQuery namedQuery = (NamedQuery) typeResource.addAnnotation(1, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAR");
		assertSourceContains("@NamedQueries({@NamedQuery(name=\"foo\", query = \"bar\", hints = @QueryHint(name=\"BAR\", value = \"FOO\")),@NamedQuery(name=\"BAR\")})");
		
		
		namedQuery = (NamedQuery) typeResource.addAnnotation(0, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAZ");
		assertSourceContains("@NamedQueries({@NamedQuery(name=\"BAZ\"),@NamedQuery(name=\"foo\", query = \"bar\", hints = @QueryHint(name=\"BAR\", value = \"FOO\")), @NamedQuery(name=\"BAR\")})");

		Iterator<JavaResource> namedQueries = typeResource.annotations(JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		assertEquals("BAZ", ((NamedQuery) namedQueries.next()).getName());
		assertEquals("foo", ((NamedQuery) namedQueries.next()).getName());
		assertEquals("BAR", ((NamedQuery) namedQueries.next()).getName());

		assertNull(typeResource.annotation(JPA.NAMED_QUERY));
		assertNotNull(typeResource.annotation(JPA.NAMED_QUERIES));
		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.NAMED_QUERY, JPA.NAMED_QUERIES)));
	}

	public void testRemoveNamedQueryCopyExisting() throws Exception {
		IType jdtType = createTestNamedQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		
		NamedQuery namedQuery = (NamedQuery) typeResource.addAnnotation(1, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		namedQuery.setName("BAR");
		assertSourceContains("@NamedQueries({@NamedQuery(name=\"foo\", query = \"bar\", hints = @QueryHint(name=\"BAR\", value = \"FOO\")),@NamedQuery(name=\"BAR\")})");
		
		typeResource.removeAnnotation(1, JPA.NAMED_QUERY, JPA.NAMED_QUERIES);
		assertSourceContains("@NamedQuery(name=\"foo\", query = \"bar\", hints = @QueryHint(name=\"BAR\", value = \"FOO\"))");
	}
	
}
