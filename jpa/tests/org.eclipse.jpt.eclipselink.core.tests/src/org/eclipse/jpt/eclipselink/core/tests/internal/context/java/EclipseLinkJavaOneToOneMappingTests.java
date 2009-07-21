/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkPrivateOwnedAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaOneToOneMappingTests extends EclipseLinkContextModelTestCase
{	

	private ICompilationUnit createTestEntityWithPrivateOwnedOneToOne() throws Exception {
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
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			
			@Override
			public void appendExtendsImplementsTo(StringBuilder sb) {
				sb.append("implements java.io.Serializable");
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwned privateOwnable = oneToOneMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
	}

	public void testSetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwned privateOwnable = oneToOneMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
		
		privateOwnable.setPrivateOwned(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getSupportingAnnotation(EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(false, privateOwnable.isPrivateOwned());

		privateOwnable.setPrivateOwned(true);
		assertNotNull(attributeResource.getSupportingAnnotation(EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(true, privateOwnable.isPrivateOwned());
	}
	
	public void testPrivateOwnedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithPrivateOwnedOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwned privateOwnable = oneToOneMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeSupportingAnnotation(EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, privateOwnable.isPrivateOwned());
		
		attributeResource.addSupportingAnnotation(EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME);
		assertEquals(true, privateOwnable.isPrivateOwned());
	}
	
	public void testGetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkJoinFetchAnnotation joinFetchAnnotation = (EclipseLinkJoinFetchAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
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
		
		attributeResource.removeSupportingAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
		assertNull(joinFetchAnnotation.getValue());
		assertNull(contextJoinFetch.getValue());
	}
	
	public void testSetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkJoinFetchAnnotation joinFetchAnnotation = (EclipseLinkJoinFetchAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
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
		
		assertNull(attributeResource.getSupportingAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME));
		assertNull(contextJoinFetch.getValue());
		
		// change context to INNER specifically (this time from no annotation), test resource
		
		contextJoinFetch.setValue(JoinFetchType.INNER);
		joinFetchAnnotation = (EclipseLinkJoinFetchAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());
		assertEquals(JoinFetchType.INNER, contextJoinFetch.getValue());
	}
	
	public void testDefaultOneToOne() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());	
		
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, attributes.next().getDefaultMappingKey());
	}
	
	public void testDefaultOneToOneSetFetch() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.setSpecifiedFetch(FetchType.LAZY);
		
		EclipseLinkOneToOneMapping specifiedOneToOneMapping = (EclipseLinkOneToOneMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(FetchType.LAZY, specifiedOneToOneMapping.getFetch());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToOneAnnotation);
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, ((OneToOneAnnotation) attributeResource.getMappingAnnotation()).getFetch());
	}
	
	public void testDefaultOneToOneSetTargetEntity() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.setSpecifiedTargetEntity("Foo");
		
		EclipseLinkOneToOneMapping specifiedOneToOneMapping = (EclipseLinkOneToOneMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals("Foo", specifiedOneToOneMapping.getSpecifiedTargetEntity());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToOneAnnotation);
		assertEquals("Foo", ((OneToOneAnnotation) attributeResource.getMappingAnnotation()).getTargetEntity());
	}
	
	public void testDefaultOneToOneSetMappedBy() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("Foo");
		
		EclipseLinkOneToOneMapping specifiedOneToOneMapping = (EclipseLinkOneToOneMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals("Foo", specifiedOneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToOneAnnotation);
		assertEquals("Foo", ((OneToOneAnnotation) attributeResource.getMappingAnnotation()).getMappedBy());
	}
	
	public void testDefaultOneToOneSetCascadeAll() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.getCascade().setAll(true);
		
		EclipseLinkOneToOneMapping specifiedOneToOneMapping = (EclipseLinkOneToOneMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToOneMapping.getCascade().isAll());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToOneAnnotation);
		assertEquals(true, ((OneToOneAnnotation) attributeResource.getMappingAnnotation()).isCascadeAll());
	}
	
	public void testDefaultOneToOneSetCascadeMerge() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.getCascade().setMerge(true);
		
		EclipseLinkOneToOneMapping specifiedOneToOneMapping = (EclipseLinkOneToOneMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToOneMapping.getCascade().isMerge());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToOneAnnotation);
		assertEquals(true, ((OneToOneAnnotation) attributeResource.getMappingAnnotation()).isCascadeMerge());
	}
	
	public void testDefaultOneToOneSetCascadePersist() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.getCascade().setPersist(true);
		
		EclipseLinkOneToOneMapping specifiedOneToOneMapping = (EclipseLinkOneToOneMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToOneMapping.getCascade().isPersist());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToOneAnnotation);
		assertEquals(true, ((OneToOneAnnotation) attributeResource.getMappingAnnotation()).isCascadePersist());
	}
	
	public void testDefaultOneToOneSetCascadeRefresh() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.getCascade().setRefresh(true);
		
		EclipseLinkOneToOneMapping specifiedOneToOneMapping = (EclipseLinkOneToOneMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToOneMapping.getCascade().isRefresh());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToOneAnnotation);
		assertEquals(true, ((OneToOneAnnotation) attributeResource.getMappingAnnotation()).isCascadeRefresh());
	}
	
	public void testDefaultOneToOneSetCascadeRemove() throws Exception {
		createTestEntityWithDefaultOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToOneMapping oneToOneMapping = (EclipseLinkOneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.getCascade().setRemove(true);
		
		EclipseLinkOneToOneMapping specifiedOneToOneMapping = (EclipseLinkOneToOneMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToOneMapping.getCascade().isRemove());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToOneAnnotation);
		assertEquals(true, ((OneToOneAnnotation) attributeResource.getMappingAnnotation()).isCascadeRemove());
	}
}
