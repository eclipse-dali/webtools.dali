/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.resource.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameter2_1Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

@SuppressWarnings("nls")
public class NamedStoredProcedureQueries2_1AnnotationTests
	extends JavaResourceModel2_1TestCase
{

	private static final String QUERY_NAME = "myQuery";
	private static final String QUERY_PROCEDURE_NAME = "myProcedure";
	
	public NamedStoredProcedureQueries2_1AnnotationTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestNamedStoredProcedureQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQueries(@NamedStoredProcedureQuery)");
			}
		});
	}
	
	private ICompilationUnit createTestNamedStoredProcedureQueryWithName() throws Exception {
		return createTestQueryWithStringElement("name", QUERY_NAME);
	}
	
	private ICompilationUnit createTestNamedStoredProcedureQueryWithProcedureName() throws Exception {
		return createTestQueryWithStringElement("procedureName", QUERY_PROCEDURE_NAME);
	}
	
	private ICompilationUnit createTestQueryWithStringElement(final String elementName, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQueries(@NamedStoredProcedureQuery(" + elementName + " = \"" + value + "\"))");
			}
		});
	}

	private ICompilationUnit createTestNamedStoredProcedureQueryWithParameters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY, JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER, JPA2_1.PARAMETER_MODE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQueries(@NamedStoredProcedureQuery(parameters = {@StoredProcedureParameter(name = \"BAR\", " +
						"mode=ParameterMode.IN, type=Integer.class), @StoredProcedureParameter}))");
			}
		});
	}
	
	private ICompilationUnit createTestNamedStoredProcedureQueryWithResultClasses() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQueries(@NamedStoredProcedureQuery(resultClasses={Employee.class, Address.class})");
			}
		});
	}
	
	private ICompilationUnit createTestNamedStoredProcedureQueryWithResultSetMappings() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQueries(@NamedStoredProcedureQuery(resultSetMappings={\"EmployeeResultSetMapping\", \"AddressResultSetMapping\"})");
			}
		});
	}
	
	private ICompilationUnit createTestNamedStoredProcedureQueryWithHints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQueries(@NamedStoredProcedureQuery(hints = {@QueryHint(name = \"BAR\", value = \"FOO\"), @QueryHint}))");
			}
		});
	}

	private ICompilationUnit createTestNamedStoredProcedureQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQuery(name = \"foo\", hints = @QueryHint(name = \"BAR\", value = \"FOO\"))");
			}
		});
	}

	public void testNamedStoredProcedureQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueries();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertNotNull(namedQuery);
	}

	// ******** name *********
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertEquals(QUERY_NAME, namedQuery.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertEquals(QUERY_NAME, namedQuery.getName());
		
		namedQuery.setName("foo");
		assertEquals("foo", namedQuery.getName());
		
		assertSourceContains("@NamedStoredProcedureQuery(name = \"foo\")", cu);
		
		namedQuery.setName(null);
		assertNull(namedQuery.getName());
		
		assertSourceDoesNotContain("@NamedStoredProcedureQuery(", cu);
	}

	// ********** procedure name ************
	
	public void testGetProcedureName() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithProcedureName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertEquals(QUERY_PROCEDURE_NAME, namedQuery.getProcedureName());
	}

	public void testSetProcedureName() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithProcedureName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertEquals(QUERY_PROCEDURE_NAME, namedQuery.getProcedureName());
		
		namedQuery.setProcedureName("foo");
		assertEquals("foo", namedQuery.getProcedureName());
		
		assertSourceContains("@NamedStoredProcedureQuery(procedureName = \"foo\")", cu);
		
		namedQuery.setProcedureName(null);
		assertNull(namedQuery.getProcedureName());
		
		assertSourceDoesNotContain("@NamedStoredProcedureQuery(", cu);
	}

	// *********** parameters *********
	
	public void testParameters1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueries();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(0, namedQuery.getParametersSize());
	}
	
	public void testParameters2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(2, namedQuery.getParametersSize());
		
		ListIterator<StoredProcedureParameter2_1Annotation> iterator = namedQuery.getParameters().iterator();
		assertEquals("BAR", iterator.next().getName());
		assertNull(iterator.next().getName());
	}

	// *********** result classes *******
	
	public void testResultClasses1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueries();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(0, namedQuery.getResultClassesSize());
	}
	
	public void testResultClasses2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultClasses();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(2, namedQuery.getResultClassesSize());
		
		ListIterator<String> iterator = namedQuery.getResultClasses().iterator();
		assertEquals("Employee", iterator.next());
		assertEquals("Address", iterator.next());
	
	}
	
	// ********** result set mappings ********
	
	public void testResultSetMappings1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueries();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(0, namedQuery.getResultSetMappingsSize());
	}
	
	public void testResultSetMappings2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultSetMappings();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(2, namedQuery.getResultSetMappingsSize());
		
		ListIterator<String> iterator = namedQuery.getResultSetMappings().iterator();
		assertEquals("EmployeeResultSetMapping", iterator.next());
		assertEquals("AddressResultSetMapping", iterator.next());
	
	}

	// *********** hints ********
	
	public void testHints1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueries();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(0, namedQuery.getHintsSize());
	}
	
	public void testHints2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(2, namedQuery.getHintsSize());
		
		ListIterator<QueryHintAnnotation> iterator = namedQuery.getHints().iterator();
		assertEquals("BAR", iterator.next().getName());
		assertNull(iterator.next().getName());
	}
	
	// ********** other tests ********
	
	public void testAddNamedQueryCopyExisting() throws Exception {
		ICompilationUnit cu = createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		String expected1 = "@NamedStoredProcedureQueries({";
		String expected2 = "@NamedStoredProcedureQuery(name = \"foo\", hints = @QueryHint(name = \"BAR\", value = \"FOO\")),";
		String expected3 = "@NamedStoredProcedureQuery(name = \"BAR\") })";
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.addAnnotation(1, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		namedQuery.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		assertNull(resourceType.getAnnotation(JPA2_1.NAMED_STORED_PROCEDURE_QUERY));
		assertNotNull(resourceType.getContainerAnnotation(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES));
		assertNotNull(resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY));
		assertEquals(2, resourceType.getAnnotationsSize(JPA2_1.NAMED_STORED_PROCEDURE_QUERY));
	}
	
	public void testAddNamedQueryToBeginningOfList() throws Exception {
		ICompilationUnit cu = createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		String expected1 = "@NamedStoredProcedureQueries({";
		String expected2 = "@NamedStoredProcedureQuery(name = \"foo\", hints = @QueryHint(name = \"BAR\", value = \"FOO\")),";
		String expected3 = "@NamedStoredProcedureQuery(name = \"BAR\") })";
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.addAnnotation(1, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		namedQuery.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected2 = "@NamedStoredProcedureQuery(name = \"BAZ\"),";
		expected3 = "@NamedStoredProcedureQuery(name = \"foo\", hints = @QueryHint(name = \"BAR\", value = \"FOO\")), @NamedStoredProcedureQuery(name = \"BAR\") })";
		namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.addAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		namedQuery.setName("BAZ");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);

		Iterator<NestableAnnotation> namedQueries = resourceType.getAnnotations(JPA2_1.NAMED_STORED_PROCEDURE_QUERY).iterator();
		assertEquals("BAZ", ((NamedStoredProcedureQueryAnnotation2_1) namedQueries.next()).getName());
		assertEquals("foo", ((NamedStoredProcedureQueryAnnotation2_1) namedQueries.next()).getName());
		assertEquals("BAR", ((NamedStoredProcedureQueryAnnotation2_1) namedQueries.next()).getName());

		assertNull(resourceType.getAnnotation(JPA2_1.NAMED_STORED_PROCEDURE_QUERY));
		assertNotNull(resourceType.getContainerAnnotation(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES));
		assertNotNull(resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY));
		assertEquals(3, resourceType.getAnnotationsSize(JPA2_1.NAMED_STORED_PROCEDURE_QUERY));
	}

	public void testRemoveNamedQueryCopyExisting() throws Exception {
		ICompilationUnit cu = createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		String expected1 = "@NamedStoredProcedureQueries({";
		String expected2 = "@NamedStoredProcedureQuery(name = \"foo\", hints = @QueryHint(name = \"BAR\", value = \"FOO\")),";
		String expected3 = "@NamedStoredProcedureQuery(name = \"BAR\") })";
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.addAnnotation(1, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		namedQuery.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected1 = "@NamedStoredProcedureQuery(name = \"foo\", hints = @QueryHint(name = \"BAR\", value = \"FOO\"))";
		resourceType.removeAnnotation(1, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertSourceContains(expected1, cu);
		assertSourceDoesNotContain("@NamedStoredProcedureQueries", cu);
	}

}
