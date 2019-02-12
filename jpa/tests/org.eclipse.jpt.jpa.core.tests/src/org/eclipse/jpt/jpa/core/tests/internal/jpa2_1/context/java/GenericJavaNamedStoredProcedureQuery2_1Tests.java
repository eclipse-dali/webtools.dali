/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.jpa2_1.context.NamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.QueryContainer2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.StoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.Generic2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaNamedStoredProcedureQuery2_1Tests
	extends Generic2_1ContextModelTestCase
{
	public GenericJavaNamedStoredProcedureQuery2_1Tests(String name) {
		super(name);
	}


	private static final String QUERY_NAME = "QUERY_NAME";
	private static final String PROCEDURE_NAME = "MY_PROCEDURE";
		
	private ICompilationUnit createTestEntityWithNamedStoredProcedureQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA2_1.NAMED_STORED_PROCEDURE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedStoredProcedureQuery(name=\"" + QUERY_NAME + "\", procedureName=\"" + PROCEDURE_NAME + "\")");
			}
		});
	}


	// ********** name ***********
	
	public void testUpdateName() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));

		assertEquals(QUERY_NAME, procedureQueryAnnotation.getName());
		assertEquals(QUERY_NAME, procedureQuery.getName());

		//set name to null in the resource model
		procedureQueryAnnotation.setName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(procedureQueryAnnotation.getName());
		assertNull(procedureQuery.getName());

		//set name in the resource model, verify context model updated
		procedureQueryAnnotation.setName("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", procedureQueryAnnotation.getName());
		assertEquals("foo", procedureQuery.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));

		assertEquals(QUERY_NAME, procedureQueryAnnotation.getName());
		assertEquals(QUERY_NAME, procedureQuery.getName());
				
		//set name to null in the context model
		procedureQuery.setName(null);
		assertNull(procedureQueryAnnotation.getName());
		assertNull(procedureQuery.getName());

		//set name in the context model, verify resource model updated
		procedureQuery.setName("foo");
		assertEquals("foo", procedureQueryAnnotation.getName());
		assertEquals("foo", procedureQuery.getName());
	}
	
	// ********** procedure name ***********
	
	public void testUpdateProcedureName() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));

		assertEquals(PROCEDURE_NAME, procedureQueryAnnotation.getProcedureName());
		assertEquals(PROCEDURE_NAME, procedureQuery.getProcedureName());

		//set procedure name to null in the resource model
		procedureQueryAnnotation.setProcedureName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(procedureQueryAnnotation.getProcedureName());
		assertNull(procedureQuery.getProcedureName());

		//set procedure name in the resource model, verify context model updated
		procedureQueryAnnotation.setProcedureName("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", procedureQueryAnnotation.getProcedureName());
		assertEquals("foo", procedureQuery.getProcedureName());
	}
	
	public void testModifyProcedureName() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));

		assertEquals(PROCEDURE_NAME, procedureQueryAnnotation.getProcedureName());
		assertEquals(PROCEDURE_NAME, procedureQuery.getProcedureName());
				
		//set procedure name to null in the context model
		procedureQuery.setProcedureName(null);
		assertNull(procedureQueryAnnotation.getProcedureName());
		assertNull(procedureQuery.getProcedureName());

		//set procedure name in the context model, verify resource model updated
		procedureQuery.setProcedureName("foo");
		assertEquals("foo", procedureQueryAnnotation.getProcedureName());
		assertEquals("foo", procedureQuery.getProcedureName());
	}


	// ******** parameters **********

	public void testAddParameter() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));	
		
		StoredProcedureParameter2_1 parameter1 = procedureQuery.addParameter(0);
		parameter1.setName("FOO");

		assertEquals("FOO", procedureQueryAnnotation.parameterAt(0).getName());
		
		StoredProcedureParameter2_1 parameter2 = procedureQuery.addParameter(0);
		parameter2.setName("BAR");
		
		assertEquals("BAR", procedureQueryAnnotation.parameterAt(0).getName());
		assertEquals("FOO", procedureQueryAnnotation.parameterAt(1).getName());
		
		StoredProcedureParameter2_1 parameter3 = procedureQuery.addParameter(1);
		parameter3.setName("BAZ");
		
		assertEquals("BAR", procedureQueryAnnotation.parameterAt(0).getName());
		assertEquals("BAZ", procedureQueryAnnotation.parameterAt(1).getName());
		assertEquals("FOO", procedureQueryAnnotation.parameterAt(2).getName());
		
		ListIterator<? extends StoredProcedureParameter2_1> parameters = procedureQuery.getParameters().iterator();
		assertEquals(parameter2, parameters.next());
		assertEquals(parameter3, parameters.next());
		assertEquals(parameter1, parameters.next());
		
		parameters = procedureQuery.getParameters().iterator();
		assertEquals("BAR", parameters.next().getName());
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());
	}
	
	public void testRemoveParameter() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));	

		procedureQuery.addParameter(0).setName("FOO");
		procedureQuery.addParameter(1).setName("BAR");
		procedureQuery.addParameter(2).setName("BAZ");
		
		assertEquals(3, procedureQueryAnnotation.getParametersSize());
		
		procedureQuery.removeParameter(0);
		assertEquals(2, procedureQueryAnnotation.getParametersSize());
		assertEquals("BAR", procedureQueryAnnotation.parameterAt(0).getName());
		assertEquals("BAZ", procedureQueryAnnotation.parameterAt(1).getName());

		procedureQuery.removeParameter(0);
		assertEquals(1, procedureQueryAnnotation.getParametersSize());
		assertEquals("BAZ", procedureQueryAnnotation.parameterAt(0).getName());
		
		procedureQuery.removeParameter(0);
		assertEquals(0, procedureQueryAnnotation.getParametersSize());
	}
	
	public void testMoveParameter() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));	

		procedureQuery.addParameter(0).setName("FOO");
		procedureQuery.addParameter(1).setName("BAR");
		procedureQuery.addParameter(2).setName("BAZ");
		
		assertEquals(3, procedureQueryAnnotation.getParametersSize());
		
		
		procedureQuery.moveParameter(2, 0);
		ListIterator<? extends StoredProcedureParameter2_1> parameters = procedureQuery.getParameters().iterator();
		assertEquals("BAR", parameters.next().getName());
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());

		assertEquals("BAR", procedureQueryAnnotation.parameterAt(0).getName());
		assertEquals("BAZ", procedureQueryAnnotation.parameterAt(1).getName());
		assertEquals("FOO", procedureQueryAnnotation.parameterAt(2).getName());


		procedureQuery.moveParameter(0, 1);
		parameters = procedureQuery.getParameters().iterator();
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("BAR", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());

		assertEquals("BAZ", procedureQueryAnnotation.parameterAt(0).getName());
		assertEquals("BAR", procedureQueryAnnotation.parameterAt(1).getName());
		assertEquals("FOO", procedureQueryAnnotation.parameterAt(2).getName());
	}
	
	public void testUpdateParameters() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));	
		
		procedureQueryAnnotation.addParameter(0).setName("FOO");
		procedureQueryAnnotation.addParameter(1).setName("BAR");
		procedureQueryAnnotation.addParameter(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();
	
		ListIterator<? extends StoredProcedureParameter2_1> parameters = procedureQuery.getParameters().iterator();
		assertEquals("FOO", parameters.next().getName());
		assertEquals("BAR", parameters.next().getName());
		assertEquals("BAZ", parameters.next().getName());
		assertFalse(parameters.hasNext());
		
		procedureQueryAnnotation.moveParameter(2, 0);
		getJpaProject().synchronizeContextModel();
		parameters = procedureQuery.getParameters().iterator();
		assertEquals("BAR", parameters.next().getName());
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());
		assertFalse(parameters.hasNext());
	
		procedureQueryAnnotation.moveParameter(0, 1);
		getJpaProject().synchronizeContextModel();
		parameters = procedureQuery.getParameters().iterator();
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("BAR", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());
		assertFalse(parameters.hasNext());
	
		procedureQueryAnnotation.removeParameter(1);
		getJpaProject().synchronizeContextModel();
		parameters = procedureQuery.getParameters().iterator();
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());
		assertFalse(parameters.hasNext());
	
		procedureQueryAnnotation.removeParameter(1);
		getJpaProject().synchronizeContextModel();
		parameters = procedureQuery.getParameters().iterator();
		assertEquals("BAZ", parameters.next().getName());
		assertFalse(parameters.hasNext());
		
		procedureQueryAnnotation.removeParameter(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(procedureQuery.getParameters().iterator().hasNext());
	}

	public void testParametersSize() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();
		assertEquals(0, procedureQuery.getParametersSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));	

		procedureQueryAnnotation.addParameter(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(1, procedureQuery.getParametersSize());
		
		procedureQueryAnnotation.addParameter(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(2, procedureQuery.getParametersSize());
		
		procedureQueryAnnotation.removeParameter(0);
		procedureQueryAnnotation.removeParameter(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, procedureQuery.getParametersSize());
	}
	
	// ********** result classes **********
	
	public void testAddResultClass() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		
		
		procedureQuery.addResultClass("Employee");
		assertEquals("Employee", procedureQueryAnnotation.resultClassAt(0));
		
		procedureQuery.addResultClass("Address");
		assertEquals("Employee", procedureQueryAnnotation.resultClassAt(0));
		assertEquals("Address", procedureQueryAnnotation.resultClassAt(1));
		
		procedureQuery.addResultClass(1, "Project");
		assertEquals("Employee", procedureQueryAnnotation.resultClassAt(0));
		assertEquals("Project", procedureQueryAnnotation.resultClassAt(1));
		assertEquals("Address", procedureQueryAnnotation.resultClassAt(2));
		
		ListIterator<String> resultClasses = procedureQuery.getResultClasses().iterator();
		assertEquals("Employee", resultClasses.next());
		assertEquals("Project", resultClasses.next());
		assertEquals("Address", resultClasses.next());
	}
	
	public void testRemoveResultClass() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		

		procedureQuery.addResultClass("Employee");
		procedureQuery.addResultClass("Address");
		procedureQuery.addResultClass("Project");
		
		assertEquals(3, procedureQueryAnnotation.getResultClassesSize());
		
		procedureQuery.removeResultClass(0);
		assertEquals(2, procedureQueryAnnotation.getResultClassesSize());
		assertEquals("Address", procedureQueryAnnotation.resultClassAt(0));
		assertEquals("Project", procedureQueryAnnotation.resultClassAt(1));

		procedureQuery.removeResultClass("Project");
		assertEquals(1, procedureQueryAnnotation.getResultClassesSize());
		assertEquals("Address", procedureQueryAnnotation.resultClassAt(0));
		
		procedureQuery.removeResultClass(0);
		assertEquals(0, procedureQueryAnnotation.getResultClassesSize());
	}
	
	public void testMoveResultClass() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		

		procedureQuery.addResultClass("Employee");
		procedureQuery.addResultClass("Address");
		procedureQuery.addResultClass("Project");
			
		assertEquals(3, procedureQueryAnnotation.getResultClassesSize());
		
		procedureQuery.moveResultClass(2, 0);
		ListIterator<String> resultClasses = procedureQuery.getResultClasses().iterator();
		assertEquals("Address", resultClasses.next());
		assertEquals("Project", resultClasses.next());
		assertEquals("Employee", resultClasses.next());

		assertEquals("Address", procedureQueryAnnotation.resultClassAt(0));
		assertEquals("Project", procedureQueryAnnotation.resultClassAt(1));
		assertEquals("Employee", procedureQueryAnnotation.resultClassAt(2));

		procedureQuery.moveResultClass(0, 1);
		resultClasses = procedureQuery.getResultClasses().iterator();
		assertEquals("Project", resultClasses.next());
		assertEquals("Address", resultClasses.next());
		assertEquals("Employee", resultClasses.next());

		assertEquals("Project", procedureQueryAnnotation.resultClassAt(0));
		assertEquals("Address", procedureQueryAnnotation.resultClassAt(1));
		assertEquals("Employee", procedureQueryAnnotation.resultClassAt(2));
	}
	
	public void testUpdateResultClass() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		

		procedureQuery.addResultClass("Employee");
		procedureQuery.addResultClass("Address");
		procedureQuery.addResultClass("Project");
		getJpaProject().synchronizeContextModel();
	
		ListIterator<String> resultClasses = procedureQuery.getResultClasses().iterator();
		assertEquals("Employee", resultClasses.next());
		assertEquals("Address", resultClasses.next());
		assertEquals("Project", resultClasses.next());
		assertFalse(resultClasses.hasNext());
		
		procedureQueryAnnotation.moveResultClass(2, 0);
		getJpaProject().synchronizeContextModel();
		resultClasses = procedureQuery.getResultClasses().iterator();
		assertEquals("Address", resultClasses.next());
		assertEquals("Project", resultClasses.next());
		assertEquals("Employee", resultClasses.next());
		assertFalse(resultClasses.hasNext());
	
		procedureQueryAnnotation.moveResultClass(0, 1);
		getJpaProject().synchronizeContextModel();
		resultClasses = procedureQuery.getResultClasses().iterator();
		assertEquals("Project", resultClasses.next());
		assertEquals("Address", resultClasses.next());
		assertEquals("Employee", resultClasses.next());
		assertFalse(resultClasses.hasNext());
	
		procedureQueryAnnotation.removeResultClass(1);
		getJpaProject().synchronizeContextModel();
		resultClasses = procedureQuery.getResultClasses().iterator();
		assertEquals("Project", resultClasses.next());
		assertEquals("Employee", resultClasses.next());
		assertFalse(resultClasses.hasNext());
	
		procedureQueryAnnotation.removeResultClass(1);
		getJpaProject().synchronizeContextModel();
		resultClasses = procedureQuery.getResultClasses().iterator();
		assertEquals("Project", resultClasses.next());
		assertFalse(resultClasses.hasNext());
		
		procedureQueryAnnotation.removeResultClass(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(procedureQuery.getResultClasses().iterator().hasNext());
	}

	public void testResultClassesSize() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();
		assertEquals(0, procedureQuery.getResultClassesSize());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));
		
		procedureQueryAnnotation.addResultClass("Employee");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, procedureQuery.getResultClassesSize());
		
		procedureQueryAnnotation.addResultClass(0, "Address");
		getJpaProject().synchronizeContextModel();
		assertEquals(2, procedureQuery.getResultClassesSize());
		
		procedureQueryAnnotation.removeResultClass(0);
		procedureQueryAnnotation.removeResultClass(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, procedureQuery.getResultClassesSize());
	}

	// ********** result set mappings **********
		
	public void testAddResultSetMapping() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		
		
		procedureQuery.addResultSetMapping("Employee");
		assertEquals("Employee", procedureQueryAnnotation.resultSetMappingAt(0));
		
		procedureQuery.addResultSetMapping("Address");
		assertEquals("Employee", procedureQueryAnnotation.resultSetMappingAt(0));
		assertEquals("Address", procedureQueryAnnotation.resultSetMappingAt(1));
		
		procedureQuery.addResultSetMapping(1, "Project");
		assertEquals("Employee", procedureQueryAnnotation.resultSetMappingAt(0));
		assertEquals("Project", procedureQueryAnnotation.resultSetMappingAt(1));
		assertEquals("Address", procedureQueryAnnotation.resultSetMappingAt(2));
		
		ListIterator<String> resultSetMapping = procedureQuery.getResultSetMappings().iterator();
		assertEquals("Employee", resultSetMapping.next());
		assertEquals("Project", resultSetMapping.next());
		assertEquals("Address", resultSetMapping.next());
	}
	
	public void testRemoveResultSetMapping() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		

		procedureQuery.addResultSetMapping("Employee");
		procedureQuery.addResultSetMapping("Address");
		procedureQuery.addResultSetMapping("Project");
		
		assertEquals(3, procedureQueryAnnotation.getResultSetMappingsSize());
		
		procedureQuery.removeResultSetMapping(0);
		assertEquals(2, procedureQueryAnnotation.getResultSetMappingsSize());
		assertEquals("Address", procedureQueryAnnotation.resultSetMappingAt(0));
		assertEquals("Project", procedureQueryAnnotation.resultSetMappingAt(1));

		procedureQuery.removeResultSetMapping("Project");
		assertEquals(1, procedureQueryAnnotation.getResultSetMappingsSize());
		assertEquals("Address", procedureQueryAnnotation.resultSetMappingAt(0));
		
		procedureQuery.removeResultSetMapping(0);
		assertEquals(0, procedureQueryAnnotation.getResultSetMappingsSize());
	}
	
	public void testMoveResultSetMapping() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		

		procedureQuery.addResultSetMapping("Employee");
		procedureQuery.addResultSetMapping("Address");
		procedureQuery.addResultSetMapping("Project");
			
		assertEquals(3, procedureQueryAnnotation.getResultSetMappingsSize());
		
		procedureQuery.moveResultSetMapping(2, 0);
		ListIterator<String> resultSetMapping = procedureQuery.getResultSetMappings().iterator();
		assertEquals("Address", resultSetMapping.next());
		assertEquals("Project", resultSetMapping.next());
		assertEquals("Employee", resultSetMapping.next());

		assertEquals("Address", procedureQueryAnnotation.resultSetMappingAt(0));
		assertEquals("Project", procedureQueryAnnotation.resultSetMappingAt(1));
		assertEquals("Employee", procedureQueryAnnotation.resultSetMappingAt(2));

		procedureQuery.moveResultSetMapping(0, 1);
		resultSetMapping = procedureQuery.getResultSetMappings().iterator();
		assertEquals("Project", resultSetMapping.next());
		assertEquals("Address", resultSetMapping.next());
		assertEquals("Employee", resultSetMapping.next());

		assertEquals("Project", procedureQueryAnnotation.resultSetMappingAt(0));
		assertEquals("Address", procedureQueryAnnotation.resultSetMappingAt(1));
		assertEquals("Employee", procedureQueryAnnotation.resultSetMappingAt(2));
	}
	
	public void testUpdateResultSetMapping() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		

		procedureQuery.addResultSetMapping("Employee");
		procedureQuery.addResultSetMapping("Address");
		procedureQuery.addResultSetMapping("Project");
		getJpaProject().synchronizeContextModel();
	
		ListIterator<String> resultSetMapping = procedureQuery.getResultSetMappings().iterator();
		assertEquals("Employee", resultSetMapping.next());
		assertEquals("Address", resultSetMapping.next());
		assertEquals("Project", resultSetMapping.next());
		assertFalse(resultSetMapping.hasNext());
		
		procedureQueryAnnotation.moveResultSetMapping(2, 0);
		getJpaProject().synchronizeContextModel();
		resultSetMapping = procedureQuery.getResultSetMappings().iterator();
		assertEquals("Address", resultSetMapping.next());
		assertEquals("Project", resultSetMapping.next());
		assertEquals("Employee", resultSetMapping.next());
		assertFalse(resultSetMapping.hasNext());
	
		procedureQueryAnnotation.moveResultSetMapping(0, 1);
		getJpaProject().synchronizeContextModel();
		resultSetMapping = procedureQuery.getResultSetMappings().iterator();
		assertEquals("Project", resultSetMapping.next());
		assertEquals("Address", resultSetMapping.next());
		assertEquals("Employee", resultSetMapping.next());
		assertFalse(resultSetMapping.hasNext());
	
		procedureQueryAnnotation.removeResultSetMapping(1);
		getJpaProject().synchronizeContextModel();
		resultSetMapping = procedureQuery.getResultSetMappings().iterator();
		assertEquals("Project", resultSetMapping.next());
		assertEquals("Employee", resultSetMapping.next());
		assertFalse(resultSetMapping.hasNext());
	
		procedureQueryAnnotation.removeResultSetMapping(1);
		getJpaProject().synchronizeContextModel();
		resultSetMapping = procedureQuery.getResultSetMappings().iterator();
		assertEquals("Project", resultSetMapping.next());
		assertFalse(resultSetMapping.hasNext());
		
		procedureQueryAnnotation.removeResultSetMapping(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(procedureQuery.getResultSetMappings().iterator().hasNext());
	}

	public void testResultSetMappingsSize() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();
		assertEquals(0, procedureQuery.getResultSetMappingsSize());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));
		
		procedureQueryAnnotation.addResultSetMapping("Employee");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, procedureQuery.getResultSetMappingsSize());
		
		procedureQueryAnnotation.addResultSetMapping(0, "Address");
		getJpaProject().synchronizeContextModel();
		assertEquals(2, procedureQuery.getResultSetMappingsSize());
		
		procedureQueryAnnotation.removeResultSetMapping(0);
		procedureQueryAnnotation.removeResultSetMapping(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, procedureQuery.getResultSetMappingsSize());
	}
	
	// ************ hints ************
	
	public void testAddHint() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));	
		
		QueryHint queryHint = procedureQuery.addHint(0);
		queryHint.setName("FOO");

		assertEquals("FOO", procedureQueryAnnotation.hintAt(0).getName());
		
		QueryHint queryHint2 = procedureQuery.addHint(0);
		queryHint2.setName("BAR");
		
		assertEquals("BAR", procedureQueryAnnotation.hintAt(0).getName());
		assertEquals("FOO", procedureQueryAnnotation.hintAt(1).getName());
		
		QueryHint queryHint3 = procedureQuery.addHint(1);
		queryHint3.setName("BAZ");
		
		assertEquals("BAR", procedureQueryAnnotation.hintAt(0).getName());
		assertEquals("BAZ", procedureQueryAnnotation.hintAt(1).getName());
		assertEquals("FOO", procedureQueryAnnotation.hintAt(2).getName());
		
		ListIterator<? extends QueryHint> hints = procedureQuery.getHints().iterator();
		assertEquals(queryHint2, hints.next());
		assertEquals(queryHint3, hints.next());
		assertEquals(queryHint, hints.next());
		
		hints = procedureQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
	}
	
	public void testRemoveHint() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		

		procedureQuery.addHint(0).setName("FOO");
		procedureQuery.addHint(1).setName("BAR");
		procedureQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, procedureQueryAnnotation.getHintsSize());
		
		procedureQuery.removeHint(0);
		assertEquals(2, procedureQueryAnnotation.getHintsSize());
		assertEquals("BAR", procedureQueryAnnotation.hintAt(0).getName());
		assertEquals("BAZ", procedureQueryAnnotation.hintAt(1).getName());

		procedureQuery.removeHint(0);
		assertEquals(1, procedureQueryAnnotation.getHintsSize());
		assertEquals("BAZ", procedureQueryAnnotation.hintAt(0).getName());
		
		procedureQuery.removeHint(0);
		assertEquals(0, procedureQueryAnnotation.getHintsSize());
	}
	
	public void testMoveHint() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		

		procedureQuery.addHint(0).setName("FOO");
		procedureQuery.addHint(1).setName("BAR");
		procedureQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, procedureQueryAnnotation.getHintsSize());
		
		procedureQuery.moveHint(2, 0);
		ListIterator<? extends QueryHint> hints = procedureQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAR", procedureQueryAnnotation.hintAt(0).getName());
		assertEquals("BAZ", procedureQueryAnnotation.hintAt(1).getName());
		assertEquals("FOO", procedureQueryAnnotation.hintAt(2).getName());


		procedureQuery.moveHint(0, 1);
		hints = procedureQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAZ", procedureQueryAnnotation.hintAt(0).getName());
		assertEquals("BAR", procedureQueryAnnotation.hintAt(1).getName());
		assertEquals("FOO", procedureQueryAnnotation.hintAt(2).getName());
	}
	
	public void testUpdateHints() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));		
		
		procedureQueryAnnotation.addHint(0).setName("FOO");
		procedureQueryAnnotation.addHint(1).setName("BAR");
		procedureQueryAnnotation.addHint(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();
	
		ListIterator<? extends QueryHint> hints = procedureQuery.getHints().iterator();
		assertEquals("FOO", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		procedureQueryAnnotation.moveHint(2, 0);
		getJpaProject().synchronizeContextModel();
		hints = procedureQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());
	
		procedureQueryAnnotation.moveHint(0, 1);
		getJpaProject().synchronizeContextModel();
		hints = procedureQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());
	
		procedureQueryAnnotation.removeHint(1);
		getJpaProject().synchronizeContextModel();
		hints = procedureQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());
	
		procedureQueryAnnotation.removeHint(1);
		getJpaProject().synchronizeContextModel();
		hints = procedureQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		procedureQueryAnnotation.removeHint(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(procedureQuery.getHints().iterator().hasNext());
	}

	public void testHintsSize() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity entity = getJavaEntity();
		NamedStoredProcedureQuery2_1 procedureQuery = ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next();
		assertEquals(0, procedureQuery.getHintsSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 procedureQueryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));
		
		procedureQueryAnnotation.addHint(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(1, procedureQuery.getHintsSize());
		
		procedureQueryAnnotation.addHint(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(2, procedureQuery.getHintsSize());
		
		procedureQueryAnnotation.removeHint(0);
		procedureQueryAnnotation.removeHint(0);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, procedureQuery.getHintsSize());
	}
	
}
