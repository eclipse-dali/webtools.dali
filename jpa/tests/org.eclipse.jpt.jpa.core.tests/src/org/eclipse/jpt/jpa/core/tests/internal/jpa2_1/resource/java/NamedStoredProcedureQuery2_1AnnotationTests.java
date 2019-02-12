/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.resource.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameterAnnotation2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;

@SuppressWarnings("nls")
public class NamedStoredProcedureQuery2_1AnnotationTests
	extends JavaResourceModel2_1TestCase
{

	private static final String QUERY_NAME = "myQuery";
	private static final String PROCEDURE_NAME = "myProcedure";

	public NamedStoredProcedureQuery2_1AnnotationTests(String name) {
		super(name);
		}
	
	private ICompilationUnit createTestNamedStoredProcedureQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQuery");
			}
		});
	}
	
	private ICompilationUnit createTestNamedStoredProcedureQueryWithName() throws Exception {
		return createTestNamedStoredProcedureQueryWithStringElement("name", QUERY_NAME);
	}
	
	private ICompilationUnit createTestNamedNativeQueryWithProcedureName() throws Exception {
		return createTestNamedStoredProcedureQueryWithStringElement("procedureName", PROCEDURE_NAME);
	}
	

	private ICompilationUnit createTestNamedStoredProcedureQueryWithStringElement(final String elementName, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQuery(" + elementName + " = \"" + value + "\")");
			}
		});
	}

	private ICompilationUnit createTestNamedStoredProcedureQueryWithParameters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERY, JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER, JPA2_1.PARAMETER_MODE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQuery(parameters = {@StoredProcedureParameter(name = \"BAR\", " +
						"mode=ParameterMode.IN, type=Integer.class), @StoredProcedureParameter})");
			}
		});
	}

	private ICompilationUnit createTestNamedStoredProcedureQueryWithResultClasses() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQuery(resultClasses = { Employee.class, Address.class })");
			}
		});
	}
	
	private ICompilationUnit createTestNamedStoredProcedureQueryWithResultSetMappings() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQuery(resultSetMappings = { \"EmpRSMapping\", \"AddrRSMapping\" })");
			}
		});
	}

	private ICompilationUnit createTestNamedStoredProcedureQueryWithHints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQuery(hints = {@QueryHint(name = \"BAR\", value = \"FOO\"), @QueryHint})");
			}
		});
	}

	public void testNamedStoredProcedureQuery() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertNotNull(namedQuery);
	}

	// ************ name ***********
	
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

	// ************ procedure name ***************
	
	public void testGetProcedureName() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithProcedureName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertEquals(PROCEDURE_NAME, namedQuery.getProcedureName());
	}

	public void testSetProcedureName() throws Exception {
		ICompilationUnit cu = this.createTestNamedNativeQueryWithProcedureName();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertEquals(PROCEDURE_NAME, namedQuery.getProcedureName());
		
		namedQuery.setProcedureName("foo");
		assertEquals("foo", namedQuery.getProcedureName());
		
		assertSourceContains("@NamedStoredProcedureQuery(procedureName = \"foo\")", cu);
		
		namedQuery.setProcedureName(null);
		assertNull(namedQuery.getProcedureName());
		
		assertSourceDoesNotContain("@NamedStoredProcedureQuery(", cu);
	}
	
	// ************ parameters *********
	
	public void testParameters1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(0, namedQuery.getParametersSize());
	}
	
	public void testParameters2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addParameter(0);
		namedQuery.addParameter(1);
		
		assertEquals(2, namedQuery.getParametersSize());
	}
	
	public void testParameters3() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(2, namedQuery.getParametersSize());
		
		ListIterator<StoredProcedureParameterAnnotation2_1> iterator = namedQuery.getParameters().iterator();
		assertEquals("BAR", iterator.next().getName());
		assertNull(iterator.next().getName());
	}
	
	public void testAddParameter() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addParameter(0).setName("FOO");
		namedQuery.addParameter(1);
		namedQuery.addParameter(0).setName("BAR");

		assertEquals("BAR", namedQuery.parameterAt(0).getName());
		assertEquals("FOO", namedQuery.parameterAt(1).getName());
		assertNull(namedQuery.parameterAt(2).getName());
		assertSourceContains("@NamedStoredProcedureQuery(parameters = {@StoredProcedureParameter(name = \"BAR\")," +
				"@StoredProcedureParameter(name = \"FOO\"), @StoredProcedureParameter})", cu);
	}
	
	public void testRemoveParameter() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);

		namedQuery.addParameter(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.parameterAt(0).getName());
		assertEquals("BAR", namedQuery.parameterAt(1).getName());
		assertNull(namedQuery.parameterAt(2).getName());
		assertEquals(3, namedQuery.getParametersSize());
		
		namedQuery.removeParameter(2);
		assertEquals("BAZ", namedQuery.parameterAt(0).getName());
		assertEquals("BAR", namedQuery.parameterAt(1).getName());
		assertEquals(2, namedQuery.getParametersSize());
		assertSourceContains("@NamedStoredProcedureQuery(parameters = {@StoredProcedureParameter(name = \"BAZ\"), " +
				"@StoredProcedureParameter(name = \"BAR\", mode=ParameterMode.IN, type=Integer.class)})", cu);
		
		namedQuery.removeParameter(0);
		assertEquals("BAR", namedQuery.parameterAt(0).getName());
		assertEquals(1, namedQuery.getParametersSize());
		assertSourceContains("@NamedStoredProcedureQuery(parameters = " +
				"@StoredProcedureParameter(name = \"BAR\", mode=ParameterMode.IN, type=Integer.class))", cu);
		
		namedQuery.removeParameter(0);
		assertEquals(0, namedQuery.getParametersSize());
		assertSourceDoesNotContain("@NamedStoredProcedureQuery(", cu);
	}
	
	public void testMoveParameter1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addParameter(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.parameterAt(0).getName());
		assertEquals("BAR", namedQuery.parameterAt(1).getName());
		assertNull(namedQuery.parameterAt(2).getName());
		assertEquals(3, namedQuery.getParametersSize());
	
		namedQuery.moveParameter(2, 0);
		
		assertEquals("BAR", namedQuery.parameterAt(0).getName());
		assertNull(namedQuery.parameterAt(1).getName());
		assertEquals("BAZ", namedQuery.parameterAt(2).getName());
		assertEquals(3, namedQuery.getParametersSize());
		assertSourceContains("@NamedStoredProcedureQuery(parameters = {@StoredProcedureParameter(name = \"BAR\", mode=ParameterMode.IN, type=Integer.class), " +
				"@StoredProcedureParameter, @StoredProcedureParameter(name = \"BAZ\")})", cu);
	}
	
	public void testMoveParameter2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		namedQuery.addParameter(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.parameterAt(0).getName());
		assertEquals("BAR", namedQuery.parameterAt(1).getName());
		assertNull(namedQuery.parameterAt(2).getName());
		assertEquals(3, namedQuery.getParametersSize());
	
		namedQuery.moveParameter(0, 2);
		
		assertNull(namedQuery.parameterAt(0).getName());
		assertEquals("BAZ", namedQuery.parameterAt(1).getName());
		assertEquals("BAR", namedQuery.parameterAt(2).getName());
		assertEquals(3, namedQuery.getParametersSize());
		assertSourceContains("@NamedStoredProcedureQuery(parameters = {@StoredProcedureParameter, @StoredProcedureParameter(name = \"BAZ\"), " +
				"@StoredProcedureParameter(name = \"BAR\", mode=ParameterMode.IN, type=Integer.class)})", cu);
	}
	
	// ************ result classes *********
	
	public void testResultClasses1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
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
	
	public void testAddResultClass() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addResultClass("Project");
		namedQuery.addResultClass("Phone");
		
		assertEquals(2, namedQuery.getResultClassesSize());

		assertEquals("Project", namedQuery.resultClassAt(0));
		assertEquals("Phone", namedQuery.resultClassAt(1));
		assertSourceContains("@NamedStoredProcedureQuery(resultClasses = { Project.class, Phone.class })", cu);
	}
	
	public void testRemoveResultClass1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultClasses();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);

		namedQuery.addResultClass("Project");
		
		assertEquals("Employee", namedQuery.resultClassAt(0));
		assertEquals("Address", namedQuery.resultClassAt(1));
		assertEquals("Project", namedQuery.resultClassAt(2));
		assertEquals(3, namedQuery.getResultClassesSize());
		
		namedQuery.removeResultClass(1);
		assertEquals("Employee", namedQuery.resultClassAt(0));
		assertEquals("Project", namedQuery.resultClassAt(1));
		assertEquals(2, namedQuery.getResultClassesSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultClasses = { Employee.class, Project.class })", cu);
		
		namedQuery.removeResultClass(0);
		assertEquals("Project", namedQuery.resultClassAt(0));
		assertEquals(1, namedQuery.getResultClassesSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultClasses = Project.class)", cu);
		
		namedQuery.removeResultClass(0);
		assertEquals(0, namedQuery.getResultClassesSize());
		assertSourceDoesNotContain("@NamedStoredProcedureQuery(", cu);
	}
	
	public void testRemoveResultClass2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultClasses();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);

		namedQuery.addResultClass("Project");
		
		assertEquals("Employee", namedQuery.resultClassAt(0));
		assertEquals("Address", namedQuery.resultClassAt(1));
		assertEquals("Project", namedQuery.resultClassAt(2));
		assertEquals(3, namedQuery.getResultClassesSize());
		
		namedQuery.removeResultClass("Address");
		assertEquals("Employee", namedQuery.resultClassAt(0));
		assertEquals("Project", namedQuery.resultClassAt(1));
		assertEquals(2, namedQuery.getResultClassesSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultClasses = { Employee.class, Project.class })", cu);
		
		namedQuery.removeResultClass("Employee");
		assertEquals("Project", namedQuery.resultClassAt(0));
		assertEquals(1, namedQuery.getResultClassesSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultClasses = Project.class)", cu);
		
		namedQuery.removeResultClass("Project");
		assertEquals(0, namedQuery.getResultClassesSize());
		assertSourceDoesNotContain("@NamedStoredProcedureQuery(", cu);
	}
	
	public void testMoveResultClass1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultClasses();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addResultClass("Project");
		
		assertEquals("Employee", namedQuery.resultClassAt(0));
		assertEquals("Address", namedQuery.resultClassAt(1));
		assertEquals("Project", namedQuery.resultClassAt(2));
		assertEquals(3, namedQuery.getResultClassesSize());
	
		namedQuery.moveResultClass(2, 0);
		
		assertEquals("Address", namedQuery.resultClassAt(0));
		assertEquals("Project", namedQuery.resultClassAt(1));
		assertEquals("Employee", namedQuery.resultClassAt(2));
		assertEquals(3, namedQuery.getResultClassesSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultClasses = { Address.class, Project.class, Employee.class })", cu);
	}
	
	public void testMoveResultClass2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultClasses();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		namedQuery.addResultClass("Project");
		
		assertEquals("Employee", namedQuery.resultClassAt(0));
		assertEquals("Address", namedQuery.resultClassAt(1));
		assertEquals("Project", namedQuery.resultClassAt(2));
		assertEquals(3, namedQuery.getResultClassesSize());
	
		namedQuery.moveResultClass(0, 2);
		
		assertEquals("Project", namedQuery.resultClassAt(0));
		assertEquals("Employee", namedQuery.resultClassAt(1));
		assertEquals("Address", namedQuery.resultClassAt(2));
		assertEquals(3, namedQuery.getResultClassesSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultClasses = { Project.class, Employee.class, Address.class })", cu);
	}
	
	
	// ************ result set mappings *********
	
	public void testResultSetMappings1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
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
		assertEquals("EmpRSMapping", iterator.next());
		assertEquals("AddrRSMapping", iterator.next());
		assertSourceContains("@NamedStoredProcedureQuery(resultSetMappings = { \"EmpRSMapping\", \"AddrRSMapping\" })", cu);
	}
	
	public void testAddResultSetMapping() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addResultSetMapping("ProjRSMapping");
		namedQuery.addResultSetMapping("PhRSMapping");
		
		assertEquals(2, namedQuery.getResultSetMappingsSize());

		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals("PhRSMapping", namedQuery.resultSetMappingAt(1));
		assertSourceContains("@NamedStoredProcedureQuery(resultSetMappings = { \"ProjRSMapping\", \"PhRSMapping\" })", cu);
	}
	
	public void testRemoveResultSetMapping1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultSetMappings();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addResultSetMapping("ProjRSMapping");
		
		assertEquals("EmpRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals("AddrRSMapping", namedQuery.resultSetMappingAt(1));
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(2));
		assertEquals(3, namedQuery.getResultSetMappingsSize());
		
		namedQuery.removeResultSetMapping(1);
		assertEquals("EmpRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(1));
		assertEquals(2, namedQuery.getResultSetMappingsSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultSetMappings = { \"EmpRSMapping\", \"ProjRSMapping\" })", cu);
		
		namedQuery.removeResultSetMapping(0);
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals(1, namedQuery.getResultSetMappingsSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultSetMappings = \"ProjRSMapping\")", cu);
		
		namedQuery.removeResultSetMapping(0);
		assertEquals(0, namedQuery.getResultSetMappingsSize());
		assertSourceDoesNotContain("@NamedStoredProcedureQuery(", cu);
	}
	
	public void testRemoveResultSetMapping2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultSetMappings();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addResultSetMapping("ProjRSMapping");
		
		assertEquals("EmpRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals("AddrRSMapping", namedQuery.resultSetMappingAt(1));
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(2));
		assertEquals(3, namedQuery.getResultSetMappingsSize());
		
		namedQuery.removeResultSetMapping("AddrRSMapping");
		assertEquals("EmpRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(1));
		assertEquals(2, namedQuery.getResultSetMappingsSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultSetMappings = { \"EmpRSMapping\", \"ProjRSMapping\" })", cu);
		
		namedQuery.removeResultSetMapping("EmpRSMapping");
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals(1, namedQuery.getResultSetMappingsSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultSetMappings = \"ProjRSMapping\")", cu);
		
		namedQuery.removeResultSetMapping("ProjRSMapping");
		assertEquals(0, namedQuery.getResultSetMappingsSize());
		assertSourceDoesNotContain("@NamedStoredProcedureQuery(", cu);
	}
	public void testMoveResultSetMapping1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultSetMappings();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addResultSetMapping("ProjRSMapping");
		
		assertEquals("EmpRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals("AddrRSMapping", namedQuery.resultSetMappingAt(1));
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(2));
		assertEquals(3, namedQuery.getResultSetMappingsSize());
	
		namedQuery.moveResultSetMapping(2, 0);
		
		assertEquals("AddrRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(1));
		assertEquals("EmpRSMapping", namedQuery.resultSetMappingAt(2));
		assertEquals(3, namedQuery.getResultSetMappingsSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultSetMappings = { \"AddrRSMapping\", \"ProjRSMapping\", \"EmpRSMapping\" })", cu);
	}
	
	public void testMoveResultSetMapping2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithResultSetMappings();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		namedQuery.addResultSetMapping("ProjRSMapping");
		
		assertEquals("EmpRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals("AddrRSMapping", namedQuery.resultSetMappingAt(1));
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(2));
		assertEquals(3, namedQuery.getResultSetMappingsSize());
	
		namedQuery.moveResultSetMapping(0, 2);
		
		assertEquals("ProjRSMapping", namedQuery.resultSetMappingAt(0));
		assertEquals("EmpRSMapping", namedQuery.resultSetMappingAt(1));
		assertEquals("AddrRSMapping", namedQuery.resultSetMappingAt(2));
		assertEquals(3, namedQuery.getResultSetMappingsSize());
		assertSourceContains("@NamedStoredProcedureQuery(resultSetMappings = { \"ProjRSMapping\", \"EmpRSMapping\", \"AddrRSMapping\" })", cu);
		}

	// *********** hints ************
	
	public void testHints1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(0, namedQuery.getHintsSize());
	}
	
	public void testHints2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addHint(0);
		namedQuery.addHint(1);
		
		assertEquals(2, namedQuery.getHintsSize());
	}
	
	public void testHints3() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		assertEquals(2, namedQuery.getHintsSize());
		
		ListIterator<QueryHintAnnotation> iterator = namedQuery.getHints().iterator();
		assertEquals("BAR", iterator.next().getName());
		assertNull(iterator.next().getName());
	}
	
	public void testAddHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQuery();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
		namedQuery.addHint(0).setName("FOO");
		namedQuery.addHint(1);
		namedQuery.addHint(0).setName("BAR");

		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals("FOO", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertSourceContains("@NamedStoredProcedureQuery(hints = {@QueryHint(name = \"BAR\"),@QueryHint(name = \"FOO\"), @QueryHint})", cu);
	}
	
	public void testRemoveHint() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		namedQuery.addHint(0).setName("BAZ");
		
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertNull(namedQuery.hintAt(2).getName());
		assertEquals(3, namedQuery.getHintsSize());
		
		namedQuery.removeHint(2);
		assertEquals("BAZ", namedQuery.hintAt(0).getName());
		assertEquals("BAR", namedQuery.hintAt(1).getName());
		assertEquals(2, namedQuery.getHintsSize());
		assertSourceContains("@NamedStoredProcedureQuery(hints = {@QueryHint(name = \"BAZ\"), @QueryHint(name = \"BAR\", value = \"FOO\")})", cu);
		
		namedQuery.removeHint(0);
		assertEquals("BAR", namedQuery.hintAt(0).getName());
		assertEquals(1, namedQuery.getHintsSize());
		assertSourceContains("@NamedStoredProcedureQuery(hints = @QueryHint(name = \"BAR\", value = \"FOO\"))", cu);
		
	
		namedQuery.removeHint(0);
		assertEquals(0, namedQuery.getHintsSize());
		assertSourceDoesNotContain("@NamedStoredProcedureQuery(", cu);
	}
	
	public void testMoveHint1() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		
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
		assertSourceContains("@NamedStoredProcedureQuery(hints = {@QueryHint(name = \"BAR\", value = \"FOO\"), @QueryHint, @QueryHint(name = \"BAZ\")})", cu);
	}
	
	public void testMoveHint2() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithHints();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
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
		assertSourceContains("@NamedStoredProcedureQuery(hints = {@QueryHint, @QueryHint(name = \"BAZ\"), @QueryHint(name = \"BAR\", value = \"FOO\")})", cu);
	}
	
}
