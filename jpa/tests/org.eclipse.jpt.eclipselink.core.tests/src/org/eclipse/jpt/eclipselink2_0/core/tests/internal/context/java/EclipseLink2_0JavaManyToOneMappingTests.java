/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.ManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.ManyToOneRelationship2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.EclipseLink2_0ContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink2_0JavaManyToOneMappingTests
	extends EclipseLink2_0ContextModelTestCase
{
	public EclipseLink2_0JavaManyToOneMappingTests(String name) {
		super(name);
	}
	
	
	private void createTestEntity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private Address address;");
				sb.append(CR);
				sb.append(CR);
				sb.append("    @Id");			
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithIdDerivedIdentity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne @Id").append(CR);				
				sb.append("    private " + TYPE_NAME + " manyToOne;").append(CR);
				sb.append(CR);				
			}
		});
	}
	
	private void createTestEntityWithMapsIdDerivedIdentity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA2_0.MAPS_ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne @MapsId").append(CR);				
				sb.append("    private " + TYPE_NAME + " manyToOne;").append(CR);
				sb.append(CR);				
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmReadOnlyPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.convertToSpecified();
		}
	}
	
	public void testUpdateDerivedId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		JavaManyToOneMapping2_0 contextMapping = (JavaManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceAttribute.removeAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceAttribute.addAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
	}
	
	public void testSetDerivedId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		JavaManyToOneMapping2_0 contextMapping = (JavaManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().setValue(false);
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().setValue(true);
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
	}
	
	public void testUpdateMapsId() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		JavaManyToOneMapping2_0 contextMapping = (JavaManyToOneMapping2_0) contextAttribute.getMapping();
		
		MapsId2_0Annotation annotation = 
				(MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID);
		annotation.setValue("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		annotation.setValue("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("bar", annotation.getValue());
		assertEquals("bar", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		resourceAttribute.removeAnnotation(JPA2_0.MAPS_ID);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}
	
	public void testSetMapsId() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("address");
		JavaManyToOneMapping2_0 contextMapping = (JavaManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue("foo");
		MapsId2_0Annotation annotation = 
				(MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID);
		assertNotNull(annotation);
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue("bar");
		assertEquals("bar", annotation.getValue());
		assertEquals("bar", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue(null);
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		
		((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).setValue("foo");
		getJpaProject().synchronizeContextModel();
		
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(((JavaManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertEquals("foo", ((JavaManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertFalse(((JavaOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertEquals("foo", ((JavaOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertFalse(((JavaManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertEquals("foo", ((JavaManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}

	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		PersistentAttribute contextAttribute = getJavaPersistentType().attributes().next();
		ManyToOneMapping2_0 mapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		ManyToOneRelationship2_0 rel = (ManyToOneRelationship2_0) mapping.getRelationship();

		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());

		rel.setJoinColumnJoiningStrategy();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());

		rel.setJoinTableJoiningStrategy();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertTrue(rel.usesJoinTableJoiningStrategy());

		rel.setJoinColumnJoiningStrategy();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());
	}

	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		PersistentAttribute contextAttribute = getJavaPersistentType().attributes().next();
		ManyToOneMapping2_0 mapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		ManyToOneRelationship2_0 rel = (ManyToOneRelationship2_0) mapping.getRelationship();

		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());

		resourceAttribute.addAnnotation(JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());

		resourceAttribute.addAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertTrue(rel.usesJoinTableJoiningStrategy());

		resourceAttribute.removeAnnotation(JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNotNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.usesJoinColumnJoiningStrategy());
		assertTrue(rel.usesJoinTableJoiningStrategy());

		resourceAttribute.removeAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_COLUMN));
		assertNull(resourceAttribute.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.usesJoinColumnJoiningStrategy());
		assertFalse(rel.usesJoinTableJoiningStrategy());
	}
}
