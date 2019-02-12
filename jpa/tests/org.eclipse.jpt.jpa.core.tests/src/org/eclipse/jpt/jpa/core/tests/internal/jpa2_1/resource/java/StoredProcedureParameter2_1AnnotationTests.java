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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameterAnnotation2_1;

@SuppressWarnings("nls")
public class StoredProcedureParameter2_1AnnotationTests
	extends JavaResourceModel2_1TestCase
{

	public StoredProcedureParameter2_1AnnotationTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestNamedStoredProcedureQueryWithParameters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.NAMED_STORED_PROCEDURE_QUERY, JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER, JPA2_1.PARAMETER_MODE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@NamedStoredProcedureQuery(parameters = {@StoredProcedureParameter(name = \"MyParameter\", mode = ParameterMode.IN, type = MyType.class), @StoredProcedureParameter})");
			}
		});
	}

	public void testNamedStoredProcedureQueryWithParameters() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		assertEquals(2, namedQuery.getParametersSize());
		StoredProcedureParameterAnnotation2_1 parameter = namedQuery.parameterAt(0);
		assertEquals("MyParameter", parameter.getName());
		parameter = namedQuery.parameterAt(1);
		assertNull(parameter.getName());
	}
	
	// ******** name *********
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		StoredProcedureParameterAnnotation2_1 parameter = namedQuery.parameterAt(0);
		assertEquals("MyParameter", parameter.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		StoredProcedureParameterAnnotation2_1 parameter = namedQuery.parameterAt(0);
		assertEquals("MyParameter", parameter.getName());
		
		parameter.setName("foo");
		assertEquals("foo", parameter.getName());
		
		assertSourceContains("@StoredProcedureParameter(name = \"foo\", mode = ParameterMode.IN, type = MyType.class)", cu);
	}
	
	// ******* mode *********
	
	public void testGetMode() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		StoredProcedureParameterAnnotation2_1 parameter = namedQuery.parameterAt(0);
		assertEquals(ParameterMode_2_1.IN, parameter.getMode());
	}

	public void testSetMode() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		StoredProcedureParameterAnnotation2_1 parameter = namedQuery.parameterAt(0);
		assertEquals(ParameterMode_2_1.IN, parameter.getMode());
		
		parameter.setMode(ParameterMode_2_1.INOUT);
		assertEquals(ParameterMode_2_1.INOUT, parameter.getMode());
		assertSourceContains("@StoredProcedureParameter(name = \"MyParameter\", mode = INOUT, type = MyType.class)", cu);
	
		parameter.setMode(null);
		assertNull(parameter.getMode());
		assertSourceDoesNotContain("mode", cu);
		assertSourceContains("@StoredProcedureParameter(name = \"MyParameter\", type = MyType.class)", cu);
	}

	// ******* type *********
	public void testGetType() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		StoredProcedureParameterAnnotation2_1 parameter = namedQuery.parameterAt(0);

		assertEquals("MyType", parameter.getTypeName());
	}

	public void testSetType() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		StoredProcedureParameterAnnotation2_1 parameter = namedQuery.parameterAt(0);

		assertEquals("MyType", parameter.getTypeName());
		
		parameter.setTypeName("Foo");
		assertEquals("Foo", parameter.getTypeName());	
		assertSourceContains("@StoredProcedureParameter(name = \"MyParameter\", mode = ParameterMode.IN, type = Foo.class", cu);
		
		parameter.setTypeName(null);
		assertNull(parameter.getTypeName());
		assertSourceDoesNotContain("type", cu);
		assertSourceContains("@StoredProcedureParameter(name = \"MyParameter\", mode = ParameterMode.IN", cu);
	}

	public void testGetFullyQualifiedClass() throws Exception {
		ICompilationUnit cu = this.createTestNamedStoredProcedureQueryWithParameters();
		JavaResourceType resourceType = buildJavaResourceType(cu);

		NamedStoredProcedureQueryAnnotation2_1 namedQuery = (NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
		StoredProcedureParameterAnnotation2_1 parameter = namedQuery.parameterAt(0);

		assertNotNull(parameter.getTypeName());
		assertEquals("MyType", parameter.getFullyQualifiedTypeName());

		parameter.setTypeName(TYPE_NAME);		
		
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, parameter.getFullyQualifiedTypeName());				
		assertSourceContains("@StoredProcedureParameter(name = \"MyParameter\", mode = ParameterMode.IN, type = " + TYPE_NAME + ".class)", cu);
	}
}
