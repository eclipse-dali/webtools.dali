/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EclipseLinkJavaManyToOneMappingTests extends EclipseLinkJavaContextModelTestCase
{

	private void createJoinFetchAnnotation() throws Exception{
		createJoinFetchTypeEnum();
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "JoinFetch", "JoinFetchType value() default JoinFetchType.INNER");		
	}
	
	private void createJoinFetchTypeEnum() throws Exception {
		this.createEnumAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, "JoinFetchType", "INNER, OUTER;");	
	}
		
	private ICompilationUnit createTestEntityWithJoinFetchManyToOne() throws Exception {
		createJoinFetchAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, EclipseLinkJPA.JOIN_FETCH);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne").append(CR);
				sb.append("@JoinFetch").append(CR);
			}
		});
	}

	public EclipseLinkJavaManyToOneMappingTests(String name) {
		super(name);
	}

	
	public void testGetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchManyToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		JoinFetchAnnotation joinFetchAnnotation = (JoinFetchAnnotation) attributeResource.getSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		
		// base annotated, test context value
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(JoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change resource to INNER specifically, test context
		
		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());
		assertEquals(JoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change resource to OUTER, test context
		
		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		assertEquals(JoinFetchType.OUTER, contextJoinFetch.getValue());
		
		// remove value from resource, test context
		
		joinFetchAnnotation.setValue(null);
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(JoinFetchType.INNER, contextJoinFetch.getValue());
		
		// remove annotation, text context
		
		attributeResource.removeSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		
		assertNull(joinFetchAnnotation.getValue());
		assertNull(contextJoinFetch.getValue());
	}
	
	public void testSetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchManyToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		JoinFetchAnnotation joinFetchAnnotation = (JoinFetchAnnotation) attributeResource.getSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		
		// base annotated, test resource value
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(JoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change context to INNER specifically, test resource
		
		contextJoinFetch.setValue(JoinFetchType.INNER);
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(JoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change context to OUTER, test resource
		
		contextJoinFetch.setValue(JoinFetchType.OUTER);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		assertEquals(JoinFetchType.OUTER, contextJoinFetch.getValue());
		
		// set context to null, test resource
		
		contextJoinFetch.setValue(null);
		
		assertNull(attributeResource.getSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME));
		assertNull(contextJoinFetch.getValue());
		
		// change context to INNER specifically (this time from no annotation), test resource
		
		contextJoinFetch.setValue(JoinFetchType.INNER);
		joinFetchAnnotation = (JoinFetchAnnotation) attributeResource.getSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());
		assertEquals(JoinFetchType.INNER, contextJoinFetch.getValue());
	}
}
