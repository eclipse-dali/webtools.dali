/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchable;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwnable;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.PrivateOwnedAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EclipseLinkJavaOneToManyMappingTests extends EclipseLinkJavaContextModelTestCase
{

	private void createPrivateOwnedAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "PrivateOwned", "");		
	}

	private void createJoinFetchAnnotation() throws Exception{
		createJoinFetchTypeEnum();
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "JoinFetch", "JoinFetchType value() default JoinFetchType.INNER");		
	}
	
	private void createJoinFetchTypeEnum() throws Exception {
		this.createEnumAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, "JoinFetchType", "INNER, OUTER;");	
	}
	
	private ICompilationUnit createTestEntityWithPrivateOwnedOneToMany() throws Exception {
		createPrivateOwnedAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, EclipseLinkJPA.PRIVATE_OWNED);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany").append(CR);
				sb.append("@PrivateOwned").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithJoinFetchOneToMany() throws Exception {
		createJoinFetchAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, EclipseLinkJPA.JOIN_FETCH);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany").append(CR);
				sb.append("@JoinFetch").append(CR);
			}
		});
	}

	public EclipseLinkJavaOneToManyMappingTests(String name) {
		super(name);
	}


	public void testGetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwnable privateOwnable = oneToManyMapping.getPrivateOwnable();
		assertEquals(true, privateOwnable.getPrivateOwned());
	}

	public void testSetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwnable privateOwnable = oneToManyMapping.getPrivateOwnable();
		assertEquals(true, privateOwnable.getPrivateOwned());
		
		privateOwnable.setPrivateOwned(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(false, privateOwnable.getPrivateOwned());

		privateOwnable.setPrivateOwned(true);
		assertNotNull(attributeResource.getAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(true, privateOwnable.getPrivateOwned());
	}
	
	public void testPrivateOwnedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwnable privateOwnable = oneToManyMapping.getPrivateOwnable();
		assertEquals(true, privateOwnable.getPrivateOwned());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, privateOwnable.getPrivateOwned());
		
		attributeResource.addAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME);
		assertEquals(true, privateOwnable.getPrivateOwned());
	}
	
	public void testHasJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToManyMapping.getJoinFetchable();
		assertEquals(true, joinFetchable.hasJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, joinFetchable.hasJoinFetch());
		
		attributeResource.addAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(true, joinFetchable.hasJoinFetch());
	}
	
	public void testSetJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToManyMapping.getJoinFetchable();
		assertEquals(true, joinFetchable.hasJoinFetch());
		
		joinFetchable.setJoinFetch(false);
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getAnnotation(JoinFetchAnnotation.ANNOTATION_NAME));
		assertFalse(joinFetchable.hasJoinFetch());
		
		joinFetchable.setJoinFetch(true);
		assertNotNull(attributeResource.getAnnotation(JoinFetchAnnotation.ANNOTATION_NAME));
		assertTrue(joinFetchable.hasJoinFetch());
	}
	
	public void testGetSpecifiedJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToManyMapping.getJoinFetchable();
		assertEquals(null, joinFetchable.getSpecifiedJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		JoinFetchAnnotation joinFetchAnnotation = (JoinFetchAnnotation) attributeResource.getAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER);
		
		assertEquals(JoinFetchType.INNER, joinFetchable.getSpecifiedJoinFetch());

		joinFetchAnnotation.setValue(null);
		assertEquals(null, joinFetchable.getSpecifiedJoinFetch());

		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER);
		assertEquals(JoinFetchType.OUTER, joinFetchable.getSpecifiedJoinFetch());
		
		attributeResource.removeAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(null, joinFetchable.getSpecifiedJoinFetch());
	}
	
	public void testSetSpecifiedJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToManyMapping.getJoinFetchable();
		assertEquals(null, joinFetchable.getSpecifiedJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		JoinFetchAnnotation joinFetchAnnotation = (JoinFetchAnnotation) attributeResource.getAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(null, joinFetchAnnotation.getValue());
		
		joinFetchable.setSpecifiedJoinFetch(JoinFetchType.INNER);	
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());

		joinFetchable.setSpecifiedJoinFetch(null);
		assertEquals(null, joinFetchAnnotation.getValue());
		
		joinFetchable.setSpecifiedJoinFetch(JoinFetchType.OUTER);	
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		
		joinFetchable.setJoinFetch(false);
		assertNull(attributeResource.getAnnotation(JoinFetchAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetDefaultJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToManyMapping.getJoinFetchable();
		assertEquals(JoinFetchable.DEFAULT_JOIN_FETCH_TYPE, joinFetchable.getDefaultJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(null, joinFetchable.getDefaultJoinFetch());
		
		joinFetchable.setSpecifiedJoinFetch(JoinFetchType.INNER);	
		assertEquals(JoinFetchable.DEFAULT_JOIN_FETCH_TYPE, joinFetchable.getDefaultJoinFetch());
	}
	
	public void testGetJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToManyMapping.getJoinFetchable();
		assertEquals(JoinFetchable.DEFAULT_JOIN_FETCH_TYPE, joinFetchable.getJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(null, joinFetchable.getJoinFetch());
		
		joinFetchable.setSpecifiedJoinFetch(JoinFetchType.INNER);	
		assertEquals(JoinFetchType.INNER, joinFetchable.getJoinFetch());
	}
}
