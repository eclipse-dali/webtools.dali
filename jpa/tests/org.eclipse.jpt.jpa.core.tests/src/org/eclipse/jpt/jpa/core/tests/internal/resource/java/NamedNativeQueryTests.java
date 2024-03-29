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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;

@SuppressWarnings("nls")
public class NamedNativeQueryTests extends JpaJavaResourceModelTestCase {

	private static final String QUERY_NAME = "myQuery";
	private static final String QUERY_QUERY = "SELECT name FROM Employee";
	private static final String QUERY_RESULT_CLASS = "Result";
	private static final String QUERY_RESULT_SET_MAPPING = "resultSetMapping";
	
	public NamedNativeQueryTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestNamedNativeQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedNativeQuery");
			}
		});
	}
	
	private ICompilationUnit createTestNamedNativeQueryWithName() throws Exception {
		return createTestNamedNativeQueryWithStringElement("name", QUERY_NAME);
	}
	
	private ICompilationUnit createTestNamedNativeQueryWithQuery() throws Exception {
		return createTestNamedNativeQueryWithStringElement("query", QUERY_QUERY);
	}
	
	private ICompilationUnit createTestNamedNativeQueryWithResultSetMapping() throws Exception {
		return createTestNamedNativeQueryWithStringElement("resultSetMapping", QUERY_RESULT_SET_MAPPING);
	}
	

	private ICompilationUnit createTestNamedNativeQueryWithStringElement(final String elementName, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedNativeQuery(" + elementName + " = \"" + value + "\")");
			}
		});
	}

	private ICompilationUnit createTestNamedNativeQueryWithResultClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedNativeQuery(resultClass = " + QUERY_RESULT_CLASS + ".class)");
			}
		});
	}
	
	private ICompilationUnit createTestNamedNativeQueryWithQueryHints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.NAMED_NATIVE_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedNativeQuery(hints = {@QueryHint(name = \"BAR\", value = \"FOO\"), @QueryHint})");
			}
		});
	}

	public void testNamedNativeQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertNotNull(namedQuery);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertEquals(QUERY_NAME, namedQuery.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertEquals(QUERY_NAME, namedQuery.getName());
		
		namedQuery.setName("foo");
		assertEquals("foo", namedQuery.getName());
		
		assertSourceContains("@NamedNativeQuery(name = \"foo\")", cu);
		
		namedQuery.setName(null);
		assertNull(namedQuery.getName());
		
		assertSourceDoesNotContain("@NamedNativeQuery(", cu);
	}

	public void testGetQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
	}

	public void testSetQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertEquals(QUERY_QUERY, namedQuery.getQuery());
		
		namedQuery.setQuery("foo");
		assertEquals("foo", namedQuery.getQuery());
		
		assertSourceContains("@NamedNativeQuery(query = \"foo\")", cu);
		
		namedQuery.setQuery(null);
		assertNull(namedQuery.getQuery());
		
		assertSourceDoesNotContain("@NamedNativeQuery(", cu);
	}
	
	public void testGetResultClass() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithResultClass();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertEquals(QUERY_RESULT_CLASS, namedQuery.getResultClass());
	}

	public void testSetResultClass() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithResultClass();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertEquals(QUERY_RESULT_CLASS, namedQuery.getResultClass());
		
		namedQuery.setResultClass("foo");
		assertEquals("foo", namedQuery.getResultClass());
		
		assertSourceContains("@NamedNativeQuery(resultClass = foo.class)", cu);
		
		namedQuery.setResultClass(null);
		assertNull(namedQuery.getResultClass());
		
		assertSourceDoesNotContain("@NamedNativeQuery(", cu);
	}

	public void testGetFullyQualifiedClass() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithResultClass();
		JavaResourceType resourceType = buildJavaResourceType(cu);

		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertNotNull(namedQuery.getResultClass());
		assertEquals("Result", namedQuery.getFullyQualifiedResultClassName());//bug 196200 changed this

		namedQuery.setResultClass(TYPE_NAME);		
		
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, namedQuery.getFullyQualifiedResultClassName());				
		assertSourceContains("@NamedNativeQuery(resultClass = " + TYPE_NAME + ".class)", cu);
	}
	
	public void testGetResultSetMapping() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithResultSetMapping();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertEquals(QUERY_RESULT_SET_MAPPING, namedQuery.getResultSetMapping());
	}

	public void testSetResultSetMapping() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithResultSetMapping();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		assertEquals(QUERY_RESULT_SET_MAPPING, namedQuery.getResultSetMapping());
		
		namedQuery.setResultSetMapping("foo");
		assertEquals("foo", namedQuery.getResultSetMapping());
		
		assertSourceContains("@NamedNativeQuery(resultSetMapping = \"foo\")", cu);
		
		namedQuery.setResultSetMapping(null);
		assertNull(namedQuery.getResultSetMapping());
		
		assertSourceDoesNotContain("@NamedNativeQuery(", cu);
	}

	public void testHints() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		
		assertEquals(0, namedQuery.getHintsSize());
	}
	
	public void testHints2() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		
		namedQuery.addHint(0);
		namedQuery.addHint(1);
		
		assertEquals(2, namedQuery.getHintsSize());
	}
	
	public void testHints3() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithQueryHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		
		assertEquals(2, namedQuery.getHintsSize());
	}
	
	
	public void testAddHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		
		namedQuery.addHint(0).setName("FOO");
		namedQuery.addHint(1);
		namedQuery.addHint(0).setName("BAR");

		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals("FOO", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertSourceContains("@NamedNativeQuery(hints = {@QueryHint(name = \"BAR\"),@QueryHint(name = \"FOO\"), @QueryHint})", cu);
	}
	
	public void testRemoveHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithQueryHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.getHintsSize());
		
		namedQuery.removeHint(2);
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertEquals(2, namedQuery.getHintsSize());
		assertSourceContains("@NamedNativeQuery(hints = {@QueryHint(name = \"BAZ\"), @QueryHint(name = \"BAR\", value = \"FOO\")})", cu);
		
		namedQuery.removeHint(0);
		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals(1, namedQuery.getHintsSize());
		assertSourceContains("@NamedNativeQuery(hints = @QueryHint(name = \"BAR\", value = \"FOO\"))", cu);
		
	
		namedQuery.removeHint(0);
		assertEquals(0, namedQuery.getHintsSize());
		assertSourceDoesNotContain("@NamedNativeQuery(", cu);
	}
	
	public void testMoveHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithQueryHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
		
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
		assertSourceContains("@NamedNativeQuery(hints = {@QueryHint(name = \"BAR\", value = \"FOO\"), @QueryHint, @QueryHint(name = \"BAZ\")})", cu);
	}
	
	public void testMoveHint2() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithQueryHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedNativeQueryAnnotation namedQuery = (NamedNativeQueryAnnotation) resourceType.getAnnotation(0, JPA.NAMED_NATIVE_QUERY);
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
		assertSourceContains("@NamedNativeQuery(hints = {@QueryHint, @QueryHint(name = \"BAZ\"), @QueryHint(name = \"BAR\", value = \"FOO\")})", cu);
	}
	
}
