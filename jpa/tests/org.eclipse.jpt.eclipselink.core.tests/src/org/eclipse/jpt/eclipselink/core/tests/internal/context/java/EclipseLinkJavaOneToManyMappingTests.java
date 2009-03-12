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
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.PrivateOwnedAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
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
	
	private ICompilationUnit createTestEntityWithDefaultOneToMany() throws Exception {
		createJoinFetchAnnotation();
		
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
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwned privateOwnable = oneToManyMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
	}

	public void testSetPrivateOwned() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwned privateOwnable = oneToManyMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
		
		privateOwnable.setPrivateOwned(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getSupportingAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(false, privateOwnable.isPrivateOwned());

		privateOwnable.setPrivateOwned(true);
		assertNotNull(attributeResource.getSupportingAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME));
		assertEquals(true, privateOwnable.isPrivateOwned());
	}
	
	public void testPrivateOwnedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithPrivateOwnedOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getSpecifiedMapping();
		PrivateOwned privateOwnable = oneToManyMapping.getPrivateOwned();
		assertEquals(true, privateOwnable.isPrivateOwned());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeSupportingAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, privateOwnable.isPrivateOwned());
		
		attributeResource.addSupportingAnnotation(PrivateOwnedAnnotation.ANNOTATION_NAME);
		assertEquals(true, privateOwnable.isPrivateOwned());
	}
	
	public void testGetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
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
		createTestEntityWithJoinFetchOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getSpecifiedMapping();
		JoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
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
	
	
	public void testDefaultOneToMany() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, attributes.next().getDefaultMappingKey());
	}
	
	public void testDefaultOneToManySetFetch() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(FetchType.LAZY, specifiedOneToManyMapping.getFetch());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToManyAnnotation);
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, ((OneToManyAnnotation) attributeResource.getMappingAnnotation()).getFetch());
	}
	
	public void testDefaultOneToManySetTargetEntity() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setSpecifiedTargetEntity("Foo");
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals("Foo", specifiedOneToManyMapping.getSpecifiedTargetEntity());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToManyAnnotation);
		assertEquals("Foo", ((OneToManyAnnotation) attributeResource.getMappingAnnotation()).getTargetEntity());
	}
	
	public void testDefaultOneToManySetMappedBy() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("Foo");
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals("Foo", specifiedOneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToManyAnnotation);
		assertEquals("Foo", ((OneToManyAnnotation) attributeResource.getMappingAnnotation()).getMappedBy());
	}
	
	public void testDefaultOneToManySetCascadeAll() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setAll(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isAll());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToManyAnnotation);
		assertEquals(true, ((OneToManyAnnotation) attributeResource.getMappingAnnotation()).isCascadeAll());
	}
	
	public void testDefaultOneToManySetCascadeMerge() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setMerge(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isMerge());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToManyAnnotation);
		assertEquals(true, ((OneToManyAnnotation) attributeResource.getMappingAnnotation()).isCascadeMerge());
	}
	
	public void testDefaultOneToManySetCascadePersist() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setPersist(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isPersist());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToManyAnnotation);
		assertEquals(true, ((OneToManyAnnotation) attributeResource.getMappingAnnotation()).isCascadePersist());
	}
	
	public void testDefaultOneToManySetCascadeRefresh() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setRefresh(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isRefresh());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToManyAnnotation);
		assertEquals(true, ((OneToManyAnnotation) attributeResource.getMappingAnnotation()).isCascadeRefresh());
	}
	
	public void testDefaultOneToManySetCascadeRemove() throws Exception {
		createTestEntityWithDefaultOneToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaPersistentAttribute> attributes = getJavaPersistentType().attributes();
		JavaPersistentAttribute persistentAttribute = attributes.next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		
		EclipseLinkOneToManyMapping oneToManyMapping = (EclipseLinkOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.getCascade().setRemove(true);
		
		EclipseLinkOneToManyMapping specifiedOneToManyMapping = (EclipseLinkOneToManyMapping) getJavaPersistentType().attributes().next().getSpecifiedMapping();
		assertEquals(true, specifiedOneToManyMapping.getCascade().isRemove());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getMappingAnnotation());
		assertTrue(attributeResource.getMappingAnnotation() instanceof OneToManyAnnotation);
		assertEquals(true, ((OneToManyAnnotation) attributeResource.getMappingAnnotation()).isCascadeRemove());
	}
}
