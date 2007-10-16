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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.platform.generic.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueries;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQuery;
import org.eclipse.jpt.core.internal.resource.java.QueryHint;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class NamedNativeQueriesTests extends AnnotationTestCase {

	private static final String QUERY_NAME = "myQuery";
	private static final String QUERY_QUERY = "SELECT name FROM Employee";
	private static final String QUERY_RESULT_CLASS = "Result";
	private static final String QUERY_RESULT_SET_MAPPING = "resultSetMapping";
	
	public NamedNativeQueriesTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private void createNamedNativeQueryAnnotation() throws Exception {
		createQueryHintAnnotation();
		this.createAnnotationAndMembers("NamedNativeQuery", "String name(); " +
			"String query();" + 
			"QueryHint[] hints() default{};");
	}
	
	private void createNamedNativeQueriesAnnotation() throws Exception {
		createNamedNativeQueryAnnotation();
		this.createAnnotationAndMembers("NamedNativeQueries", 
			"NamedNativeQuery[] value();");
	}
	
	private void createQueryHintAnnotation() throws Exception {
		this.createAnnotationAndMembers("QueryHint", "String name(); " +
			"String value();");
	}
	
	private IType createTestNamedNativeQuery() throws Exception {
		createNamedNativeQueriesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_NATIVE_QUERIES, JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@NamedNativeQueries(@NamedNativeQuery)");
			}
		});
	}
	
	private IType createTestNamedNativeQueryWithName() throws Exception {
		return createTestNamedNativeQueryWithStringElement("name", QUERY_NAME);
	}
	
	private IType createTestNamedNativeQueryWithQuery() throws Exception {
		return createTestNamedNativeQueryWithStringElement("query", QUERY_QUERY);
	}
	
	private IType createTestNamedNativeQueryWithResultSetMapping() throws Exception {
		return createTestNamedNativeQueryWithStringElement("resultSetMapping", QUERY_RESULT_SET_MAPPING);
	}
	

	private IType createTestNamedNativeQueryWithStringElement(final String elementName, final String value) throws Exception {
		createNamedNativeQueriesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_NATIVE_QUERIES, JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@NamedNativeQueries(@NamedNativeQuery(" + elementName + "=\"" + value + "\"))");
			}
		});
	}

	private IType createTestNamedNativeQueryWithResultClass() throws Exception {
		createNamedNativeQueriesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_NATIVE_QUERIES, JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@NamedNativeQueries(@NamedNativeQuery(resultClass=" + QUERY_RESULT_CLASS + ".class))");
			}
		});
	}
	private IType createTestNamedNativeQueryWithQueryHints() throws Exception {
		createNamedNativeQueriesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.NAMED_NATIVE_QUERIES, JPA.NAMED_NATIVE_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@NamedNativeQueries(@NamedNativeQuery(hints={@QueryHint(name=\"BAR\", value=\"FOO\"), @QueryHint}))");
			}
		});
	}


	protected JavaResource buildParentResource(final IJpaPlatform jpaPlatform) {
		return new JavaResource() {
			public void updateFromJava(CompilationUnit astRoot) {
			}
			public IJpaPlatform jpaPlatform() {
				return jpaPlatform;
			}
		};
	}
	
	protected IJpaPlatform buildJpaPlatform() {
		return new GenericJpaPlatform();
	}

	protected JavaPersistentTypeResource buildJavaTypeResource(IType testType) {
		JavaPersistentTypeResource typeResource = new JavaPersistentTypeResourceImpl(buildParentResource(buildJpaPlatform()), new Type(testType, MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER));
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		return typeResource;
	}

	public void testNamedNativeQuery() throws Exception {
		IType testType = this.createTestNamedNativeQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertNotNull(namedQuery);
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_NAME, namedQuery.getName());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_NAME, namedQuery.getName());
		
		namedQuery.setName("foo");
		assertEquals("foo", namedQuery.getName());
		
		assertSourceContains("@NamedNativeQuery(name=\"foo\")");
		
		namedQuery.setName(null);
		assertNull(namedQuery.getName());
		
		assertSourceDoesNotContain("@NamedNativeQuery");
	}

	public void testGetQuery() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
	}

	public void testSetQuery() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
		
		namedQuery.setQuery("foo");
		assertEquals("foo", namedQuery.getQuery());
		
		assertSourceContains("@NamedNativeQuery(query=\"foo\")");
		
		namedQuery.setQuery(null);
		assertNull(namedQuery.getQuery());
		
		assertSourceDoesNotContain("@NamedNativeQuery");
	}
	
	public void testGetResultClass() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithResultClass();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_RESULT_CLASS, namedQuery.getResultClass());
	}

	public void testSetResultClass() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithResultClass();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_RESULT_CLASS, namedQuery.getResultClass());
		
		namedQuery.setResultClass("foo");
		assertEquals("foo", namedQuery.getResultClass());
		
		assertSourceContains("@NamedNativeQuery(resultClass=foo.class)");
		
		namedQuery.setResultClass(null);
		assertNull(namedQuery.getResultClass());
		
		assertSourceDoesNotContain("@NamedNativeQuery");
	}

	public void testGetFullyQualifiedClass() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithResultClass();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);

		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertNotNull(namedQuery.getResultClass());
		assertNull(namedQuery.getFullyQualifiedResultClass());

		namedQuery.setResultClass(TYPE_NAME);		
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, namedQuery.getFullyQualifiedResultClass());				
		assertSourceContains("@NamedNativeQuery(resultClass=" + TYPE_NAME + ".class)");
	}
	
	public void testGetResultSetMapping() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithResultSetMapping();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_RESULT_SET_MAPPING, namedQuery.getResultSetMapping());
	}

	public void testSetResultSetMapping() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithResultSetMapping();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		assertEquals(QUERY_RESULT_SET_MAPPING, namedQuery.getResultSetMapping());
		
		namedQuery.setResultSetMapping("foo");
		assertEquals("foo", namedQuery.getResultSetMapping());
		
		assertSourceContains("@NamedNativeQuery(resultSetMapping=\"foo\")");
		
		namedQuery.setResultSetMapping(null);
		assertNull(namedQuery.getResultSetMapping());
		
		assertSourceDoesNotContain("@NamedNativeQuery");
	}

	public void testHints() throws Exception {
		IType testType = this.createTestNamedNativeQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		
		ListIterator<QueryHint> iterator = namedQuery.hints();
		
		assertEquals(0, CollectionTools.size(iterator));
	}
	
	public void testHints2() throws Exception {
		IType testType = this.createTestNamedNativeQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		
		namedQuery.addHint(0);
		namedQuery.addHint(1);
		namedQuery.updateFromJava(JDTTools.buildASTRoot(testType));
		
		ListIterator<QueryHint> iterator = namedQuery.hints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testHints3() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithQueryHints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		
		ListIterator<QueryHint> iterator = namedQuery.hints();
		
		assertEquals(2, CollectionTools.size(iterator));
	}
	
	public void testAddHint() throws Exception {
		IType testType = this.createTestNamedNativeQuery();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		
		namedQuery.addHint(0).setName("FOO");
		namedQuery.addHint(1);

		assertSourceContains("@NamedNativeQuery(hints={@QueryHint(name=\"FOO\"),@QueryHint})");
	}
	
	public void testRemoveHint() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithQueryHints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		
		namedQuery.removeHint(1);
		assertSourceContains("@NamedNativeQuery(hints=@QueryHint(name=\"BAR\", value=\"FOO\"))");
		
		namedQuery.removeHint(0);
		assertSourceDoesNotContain("@NamedNativeQuery");
	}
	
	public void testMoveHint() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithQueryHints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		
		namedQuery.moveHint(0, 1);
		assertSourceContains("@NamedNativeQuery(hints={@QueryHint, @QueryHint(name=\"BAR\", value=\"FOO\")})");
	}
	
	public void testMoveHint2() throws Exception {
		IType testType = this.createTestNamedNativeQueryWithQueryHints();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		NamedNativeQueries namedQueries = (NamedNativeQueries) typeResource.annotation(JPA.NAMED_NATIVE_QUERIES);
		NamedNativeQuery namedQuery = (NamedNativeQuery) namedQueries.nestedAnnotations().next();
		
		namedQuery.moveHint(1, 0);
		assertSourceContains("@NamedNativeQuery(hints={@QueryHint, @QueryHint(name=\"BAR\", value=\"FOO\")})");
	}
	
}
