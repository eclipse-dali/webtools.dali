/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.jpa2_1.ParameterMode2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.QueryContainer2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.StoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ParameterMode_2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameterAnnotation2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.Generic2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaStoredProcedureParameter2_1Tests
	extends Generic2_1ContextModelTestCase
{
	public GenericJavaStoredProcedureParameter2_1Tests(String name) {
		super(name);
	}


	private static final String QUERY_NAME = "QUERY_NAME";
		
	private ICompilationUnit createTestEntityWithNamedStoredProcedureQuery() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA2_1.NAMED_STORED_PROCEDURE_QUERY, JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedStoredProcedureQuery(name=\"" + QUERY_NAME + "\", parameters=@StoredProcedureParameter())");
			}
		});
	}
	
	// ************ name ***********
	
	public void testUpdateName() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		StoredProcedureParameter2_1 parameter = ((QueryContainer2_1)entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next().getParameters().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 queryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));
		StoredProcedureParameterAnnotation2_1 parameterAnnotation = queryAnnotation.getParameters().iterator().next();
		
		assertNull(parameterAnnotation.getName());
		assertNull(parameter.getName());

		//set name in the resource model, verify context model updated
		parameterAnnotation.setName("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", parameterAnnotation.getName());
		assertEquals("foo", parameter.getName());
		
		//set name to null in the resource model
		parameterAnnotation.setName(null); 
		getJpaProject().synchronizeContextModel();
		assertNull(parameter.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		StoredProcedureParameter2_1 parameter = ((QueryContainer2_1)entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next().getParameters().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 queryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));
		StoredProcedureParameterAnnotation2_1 parameterAnnotation = queryAnnotation.getParameters().iterator().next();
		
		assertNull(parameterAnnotation.getName());
		assertNull(parameter.getName());

		//set name in the context model, verify resource model updated
		parameter.setName("foo");
		assertEquals("foo", parameterAnnotation.getName());
		assertEquals("foo", parameter.getName());
		
		//set name to null in the context model
		parameter.setName(null);
		assertNull(parameterAnnotation.getName());
		assertNull(parameter.getName());
	}
	
	// ************ mode **********
	
	public void testUpdateMode() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		StoredProcedureParameter2_1 parameter = ((QueryContainer2_1)entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next().getParameters().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 queryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));
		StoredProcedureParameterAnnotation2_1 parameterAnnotation = queryAnnotation.getParameters().iterator().next();
		
		assertNull(parameterAnnotation.getMode());
		assertNull(parameter.getSpecifiedMode());
		assertEquals(ParameterMode2_1.IN, parameter.getMode());

		//set mode in the resource model, verify context model updated
		parameterAnnotation.setMode(ParameterMode_2_1.IN);
		getJpaProject().synchronizeContextModel();
		assertEquals(ParameterMode_2_1.IN, parameterAnnotation.getMode());
		assertEquals(ParameterMode2_1.IN, parameter.getMode());
		
		//set name to null in the resource model
		parameterAnnotation.setMode(null); 
		getJpaProject().synchronizeContextModel();
		assertNull(parameterAnnotation.getMode());
		assertNull(parameter.getSpecifiedMode());
		assertEquals(ParameterMode2_1.IN, parameter.getMode());

	}
	
	public void testModifyMode() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		StoredProcedureParameter2_1 parameter = ((QueryContainer2_1)entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next().getParameters().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 queryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));
		StoredProcedureParameterAnnotation2_1 parameterAnnotation = queryAnnotation.getParameters().iterator().next();
		
		assertNull(parameterAnnotation.getMode());
		assertNull(parameter.getSpecifiedMode());
		assertEquals(ParameterMode2_1.IN, parameter.getMode());

		//set mode in the context model, verify resource model updated
		parameter.setSpecifiedMode(ParameterMode2_1.INOUT);
		assertEquals(ParameterMode_2_1.INOUT, parameterAnnotation.getMode());
		assertEquals(ParameterMode2_1.INOUT, parameter.getMode());
		
		//set mode to null in the context model
		parameter.setSpecifiedMode(null);
		assertNull(parameterAnnotation.getMode());
		assertNull(parameter.getSpecifiedMode());
		assertEquals(ParameterMode2_1.IN, parameter.getMode());

	}

	// ************ type name **********
	
	public void testUpdateTypeName() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		StoredProcedureParameter2_1 parameter = ((QueryContainer2_1)entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next().getParameters().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 queryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));
		StoredProcedureParameterAnnotation2_1 parameterAnnotation = queryAnnotation.getParameters().iterator().next();
		
		assertNull(parameterAnnotation.getTypeName());
		assertNull(parameter.getTypeName());

		//set type in the resource model, verify context model updated
		parameterAnnotation.setTypeName("Foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("Foo", parameterAnnotation.getTypeName());
		assertEquals("Foo", parameter.getTypeName());
		
		//set type to null in the resource model
		parameterAnnotation.setTypeName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(parameter.getTypeName());
	}
	
	public void testModifyTypeName() throws Exception {
		createTestEntityWithNamedStoredProcedureQuery();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		StoredProcedureParameter2_1 parameter = ((QueryContainer2_1)entity.getQueryContainer()).getNamedStoredProcedureQueries().iterator().next().getParameters().iterator().next();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		NamedStoredProcedureQueryAnnotation2_1 queryAnnotation = ((NamedStoredProcedureQueryAnnotation2_1) resourceType.getAnnotation(0, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME));
		StoredProcedureParameterAnnotation2_1 parameterAnnotation = queryAnnotation.getParameters().iterator().next();
		
		assertNull(parameterAnnotation.getTypeName());
		assertNull(parameter.getTypeName());

		//set type in the context model, verify resource model updated
		parameter.setTypeName("Foo");
		assertEquals("Foo", parameterAnnotation.getTypeName());
		assertEquals("Foo", parameter.getTypeName());
		
		//set type to null in the context model
		parameter.setTypeName(null);
		assertNull(parameterAnnotation.getTypeName());
		assertNull(parameter.getTypeName());
	}

}
