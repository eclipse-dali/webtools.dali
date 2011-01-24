/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyRelationship;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkPrivateOwnedAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaOneToManyMappingTests extends EclipseLinkContextModelTestCase
{
	private ICompilationUnit createTestEntityWithOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithPrivateOwnedOneToMany() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, EclipseLink.PRIVATE_OWNED);
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
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, EclipseLink.JOIN_FETCH);
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
	
	private ICompilationUnit createTestEntityWithDefaultOneToMany() throws Exception {
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
				sb.append("private java.util.Collection<" + TYPE_NAME + "> myTypes;").append(CR);
				sb.append(CR);
			}
		});
	}

	public EclipseLinkJavaOneToManyMappingTests(String name) {
		super(name);
	}


	public void testGetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		EclipseLinkPrivateOwned privateOwnable = oneToManyMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
	}

	public void testSetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		EclipseLinkPrivateOwned privateOwnable = oneToManyMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
		
		privateOwnable.setPrivateOwned(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(false, privateOwnable.isPrivateOwned());

		privateOwnable.setPrivateOwned(true);
		assertNotNull(attributeResource.getAnnotation(EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(true, privateOwnable.isPrivateOwned());
	}
	
	public void testPrivateOwnedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		EclipseLinkPrivateOwned privateOwnable = oneToManyMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeAnnotation(EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(false, privateOwnable.isPrivateOwned());
		
		attributeResource.addAnnotation(EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(true, privateOwnable.isPrivateOwned());
	}
	
	public void testGetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getMapping();
		EclipseLinkJoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkJoinFetchAnnotation joinFetchAnnotation = (EclipseLinkJoinFetchAnnotation) attributeResource.getAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
		// base annotated, test context value
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change resource to INNER specifically, test context
		
		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change resource to OUTER, test context
		
		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextJoinFetch.getValue());
		
		// remove value from resource, test context
		
		joinFetchAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// remove annotation, text context
		
		attributeResource.removeAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(joinFetchAnnotation.getValue());
		assertNull(contextJoinFetch.getValue());
	}
	
	public void testSetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getMapping();
		EclipseLinkJoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkJoinFetchAnnotation joinFetchAnnotation = (EclipseLinkJoinFetchAnnotation) attributeResource.getAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
		// base annotated, test resource value
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change context to INNER specifically, test resource
		
		contextJoinFetch.setValue(EclipseLinkJoinFetchType.INNER);
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change context to OUTER, test resource
		
		contextJoinFetch.setValue(EclipseLinkJoinFetchType.OUTER);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextJoinFetch.getValue());
		
		// set context to null, test resource
		
		contextJoinFetch.setValue(null);
		
		assertNull(attributeResource.getAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME));
		assertNull(contextJoinFetch.getValue());
		
		// change context to INNER specifically (this time from no annotation), test resource
		
		contextJoinFetch.setValue(EclipseLinkJoinFetchType.INNER);
		joinFetchAnnotation = (EclipseLinkJoinFetchAnnotation) attributeResource.getAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
	}
	
	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		OneToManyAnnotation annotation = (OneToManyAnnotation) resourceAttribute.getAnnotation(JPA.ONE_TO_MANY);
		PersistentAttribute contextAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping mapping = (EclipseLinkOneToManyMapping) contextAttribute.getMapping();
		EclipseLinkOneToManyRelationship rel = mapping.getRelationship();
		
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertTrue(rel.usesJoinTableJoiningStrategy());
		assertFalse(rel.usesMappedByJoiningStrategy());
		
		rel.setJoinColumnJoiningStrategy();
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());
		assertFalse(rel.usesMappedByJoiningStrategy());
		
		rel.setMappedByJoiningStrategy();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());
		assertTrue(rel.usesMappedByJoiningStrategy());
		
		rel.setJoinTableJoiningStrategy();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertTrue(rel.usesJoinTableJoiningStrategy());
		assertFalse(rel.usesMappedByJoiningStrategy());
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		OneToManyAnnotation annotation = (OneToManyAnnotation) resourceAttribute.getAnnotation(JPA.ONE_TO_MANY);
		PersistentAttribute contextAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping mapping = (EclipseLinkOneToManyMapping) contextAttribute.getMapping();
		EclipseLinkOneToManyRelationship rel = mapping.getRelationship();
		
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertTrue(rel.usesJoinTableJoiningStrategy());
		assertFalse(rel.usesMappedByJoiningStrategy());
		
		annotation.setMappedBy("foo");
		getJpaProject().synchronizeContextModel();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());
		assertTrue(rel.usesMappedByJoiningStrategy());
		
		resourceAttribute.addAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());
		assertTrue(rel.usesMappedByJoiningStrategy());
		
		resourceAttribute.addAnnotation(JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());
		assertTrue(rel.usesMappedByJoiningStrategy());
		
		annotation.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());
		assertFalse(rel.usesMappedByJoiningStrategy());
		
		resourceAttribute.removeAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());
		assertFalse(rel.usesMappedByJoiningStrategy());
		
		resourceAttribute.removeAnnotation(JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertTrue(rel.usesJoinTableJoiningStrategy());
		assertFalse(rel.usesMappedByJoiningStrategy());
	}
	
	public void testDefaultOneToMany() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, attributes.next().getDefaultMappingKey());
	}
	
	public void testDefaultOneToManySetFetch() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getMapping();
		assertEquals(FetchType.LAZY, specifiedOneToManyMapping.getFetch());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToManyAnnotation annotation = 
				(OneToManyAnnotation) attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(annotation);
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, annotation.getFetch());
	}
	
	public void testDefaultOneToManySetTargetEntity() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setSpecifiedTargetEntity("Foo");
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getMapping();
		assertEquals("Foo", specifiedOneToManyMapping.getSpecifiedTargetEntity());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToManyAnnotation annotation = 
				(OneToManyAnnotation) attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(annotation);
		assertEquals("Foo", annotation.getTargetEntity());
	}
	
	public void testDefaultOneToManySetMappedBy() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getRelationship().getMappedByJoiningStrategy().setMappedByAttribute("Foo");
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getMapping();
		assertEquals("Foo", specifiedOneToManyMapping.getRelationship().getMappedByJoiningStrategy().getMappedByAttribute());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToManyAnnotation annotation = 
				(OneToManyAnnotation) attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(annotation);
		assertEquals("Foo", annotation.getMappedBy());
	}
	
	public void testDefaultOneToManySetCascadeAll() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setAll(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isAll());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToManyAnnotation annotation = 
				(OneToManyAnnotation) attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(annotation);
		assertEquals(true, annotation.isCascadeAll());
	}
	
	public void testDefaultOneToManySetCascadeMerge() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setMerge(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isMerge());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToManyAnnotation annotation = 
				(OneToManyAnnotation) attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(annotation);
		assertEquals(true, annotation.isCascadeMerge());
	}
	
	public void testDefaultOneToManySetCascadePersist() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setPersist(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isPersist());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToManyAnnotation annotation = 
				(OneToManyAnnotation) attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(annotation);
		assertEquals(true, annotation.isCascadePersist());
	}
	
	public void testDefaultOneToManySetCascadeRefresh() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setRefresh(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isRefresh());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToManyAnnotation annotation = 
				(OneToManyAnnotation) attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertEquals(true, annotation.isCascadeRefresh());
	}
	
	public void testDefaultOneToManySetCascadeRemove() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertTrue(persistentAttribute.getMapping().isDefault());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setRemove(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isRemove());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToManyAnnotation annotation = 
				(OneToManyAnnotation) attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(annotation);
		assertEquals(true, annotation.isCascadeRemove());
	}
}
