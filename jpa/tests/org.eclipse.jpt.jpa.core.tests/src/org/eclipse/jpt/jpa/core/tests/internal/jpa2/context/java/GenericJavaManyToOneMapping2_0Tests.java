/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AccessAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollectionAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsIdAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaManyToOneMapping2_0Tests
	extends Generic2_0ContextModelTestCase
{
	public GenericJavaManyToOneMapping2_0Tests(String name) {
		super(name);
	}
	

	private ICompilationUnit createTestEntityWithManyToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithIdDerivedIdentity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_ONE, JPA2_0.MAPS_ID);
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
		for (OrmPersistentAttribute each : ormPersistentType.getAttributes()) {
			each.addToXml();
		}
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToElementCollectionMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ElementCollectionMapping2_0);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ElementCollectionAnnotation2_0.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testUpdateId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		ManyToOneMapping2_0 contextMapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceField.removeAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceField.addAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
	}
	
	public void testSetId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		ManyToOneMapping2_0 contextMapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().setValue(false);
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().setValue(true);
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
	}
	
	public void testUpdateMapsId() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		ManyToOneMapping2_0 contextMapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		MapsIdAnnotation2_0 annotation = 
				(MapsIdAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAPS_ID);
		annotation.setValue("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		annotation.setValue("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("bar", annotation.getValue());
		assertEquals("bar", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		resourceField.removeAnnotation(JPA2_0.MAPS_ID);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
	}
	
	public void testSetMapsId() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		ManyToOneMapping2_0 contextMapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedIdAttributeName("foo");
		MapsIdAnnotation2_0 annotation = 
				(MapsIdAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAPS_ID);
		assertNotNull(annotation);
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedIdAttributeName("bar");
		assertEquals("bar", annotation.getValue());
		assertEquals("bar", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedIdAttributeName(null);
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
	}
	
	public void testUpdatePredominantDerivedIdentityStrategy() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		ManyToOneMapping2_0 contextMapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		DerivedIdentity2_0 derivedIdentity = contextMapping.getDerivedIdentity();
		
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceField.addAnnotation(JPA2_0.MAPS_ID);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceField.removeAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceField.removeAnnotation(JPA2_0.MAPS_ID);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
	}
	
	public void testSetPredominantDerivedIdentityStrategy() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		ManyToOneMapping2_0 contextMapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		DerivedIdentity2_0 derivedIdentity = contextMapping.getDerivedIdentity();
		
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setMapsIdDerivedIdentityStrategy();
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setNullDerivedIdentityStrategy();
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setIdDerivedIdentityStrategy();
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(((ManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(((ManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		contextAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(((OneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(((OneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		contextAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(((ManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(((ManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		contextAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertTrue(contextAttribute.getMapping() instanceof JavaIdMapping);
		
		contextAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertTrue(contextAttribute.getMapping() instanceof JavaManyToOneMapping);	
		
		contextAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertTrue(contextAttribute.getMapping() instanceof JavaBasicMapping);
	}

	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		SpecifiedPersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping2_0 mapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		ManyToOneRelationship2_0 rel = (ManyToOneRelationship2_0) mapping.getRelationship();

		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());

		rel.setStrategyToJoinColumn();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		
		rel.setStrategyToJoinTable();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		
		rel.setStrategyToJoinColumn();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
	}

	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		SpecifiedPersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping2_0 mapping = (ManyToOneMapping2_0) contextAttribute.getMapping();
		ManyToOneRelationship2_0 rel = (ManyToOneRelationship2_0) mapping.getRelationship();

		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());

		resourceField.addAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());

		resourceField.addAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());

		resourceField.removeAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		
		resourceField.removeAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
	}
}
