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
import java.util.ListIterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchable;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.PrivateOwnedAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaOneToOneMappingTests extends EclipseLinkJavaContextModelTestCase
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
	

	private ICompilationUnit createTestEntityWithPrivateOwnedOneToOne() throws Exception {
		createPrivateOwnedAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, EclipseLinkJPA.PRIVATE_OWNED);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne").append(CR);
				sb.append("@PrivateOwned").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithJoinFetchOneToOne() throws Exception {
		createJoinFetchAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, EclipseLinkJPA.JOIN_FETCH);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne").append(CR);
				sb.append("@JoinFetch").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithDefaultOneToOne() throws Exception {
		createJoinFetchAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("private " + TYPE_NAME + " myType;").append(CR);
				sb.append(CR);
			}
		});
	}

	public EclipseLinkJavaOneToOneMappingTests(String name) {
		super(name);
	}


	public void testGetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwned privateOwnable = oneToOneMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
	}

	public void testSetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwned privateOwnable = oneToOneMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
		
		privateOwnable.setPrivateOwned(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getSupportingAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(false, privateOwnable.isPrivateOwned());

		privateOwnable.setPrivateOwned(true);
		assertNotNull(attributeResource.getSupportingAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(true, privateOwnable.isPrivateOwned());
	}
	
	public void testPrivateOwnedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithPrivateOwnedOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwned privateOwnable = oneToOneMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeSupportingAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, privateOwnable.isPrivateOwned());
		
		attributeResource.addSupportingAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME);
		assertEquals(true, privateOwnable.isPrivateOwned());
	}
	
	public void testHasJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToOneMapping.getJoinFetchable();
		assertEquals(true, joinFetchable.hasJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, joinFetchable.hasJoinFetch());
		
		attributeResource.addSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(true, joinFetchable.hasJoinFetch());
	}
	
	public void testSetJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToOneMapping.getJoinFetchable();
		assertEquals(true, joinFetchable.hasJoinFetch());
		
		joinFetchable.setJoinFetch(false);
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME));
		assertFalse(joinFetchable.hasJoinFetch());
		
		joinFetchable.setJoinFetch(true);
		assertNotNull(attributeResource.getSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME));
		assertTrue(joinFetchable.hasJoinFetch());
	}
	
	public void testGetSpecifiedJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToOneMapping.getJoinFetchable();
		assertEquals(null, joinFetchable.getSpecifiedJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		JoinFetchAnnotation joinFetchAnnotation = (JoinFetchAnnotation) attributeResource.getSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER);
		
		assertEquals(JoinFetchType.INNER, joinFetchable.getSpecifiedJoinFetch());

		joinFetchAnnotation.setValue(null);
		assertEquals(null, joinFetchable.getSpecifiedJoinFetch());

		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER);
		assertEquals(JoinFetchType.OUTER, joinFetchable.getSpecifiedJoinFetch());
		
		attributeResource.removeSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(null, joinFetchable.getSpecifiedJoinFetch());
	}
	
	public void testSetSpecifiedJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToOneMapping.getJoinFetchable();
		assertEquals(null, joinFetchable.getSpecifiedJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		JoinFetchAnnotation joinFetchAnnotation = (JoinFetchAnnotation) attributeResource.getSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(null, joinFetchAnnotation.getValue());
		
		joinFetchable.setSpecifiedJoinFetch(JoinFetchType.INNER);	
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());

		joinFetchable.setSpecifiedJoinFetch(null);
		assertEquals(null, joinFetchAnnotation.getValue());
		
		joinFetchable.setSpecifiedJoinFetch(JoinFetchType.OUTER);	
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		
		joinFetchable.setJoinFetch(false);
		assertNull(attributeResource.getSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetDefaultJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToOneMapping.getJoinFetchable();
		assertEquals(JoinFetchable.DEFAULT_JOIN_FETCH_TYPE, joinFetchable.getDefaultJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(null, joinFetchable.getDefaultJoinFetch());
		
		joinFetchable.setSpecifiedJoinFetch(JoinFetchType.INNER);	
		assertEquals(JoinFetchable.DEFAULT_JOIN_FETCH_TYPE, joinFetchable.getDefaultJoinFetch());
	}
	
	public void testGetJoinFetch() throws Exception {
		createTestEntityWithJoinFetchOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetchable joinFetchable = oneToOneMapping.getJoinFetchable();
		assertEquals(JoinFetchable.DEFAULT_JOIN_FETCH_TYPE, joinFetchable.getJoinFetch());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeSupportingAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		assertEquals(null, joinFetchable.getJoinFetch());
		
		joinFetchable.setSpecifiedJoinFetch(JoinFetchType.INNER);	
		assertEquals(JoinFetchType.INNER, joinFetchable.getJoinFetch());
	}
	
	public void testDefaultOneToOne() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaPersistentAttribute> attributes = javaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());	
		
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, attributes.next().getDefaultMappingKey());
	}
}
