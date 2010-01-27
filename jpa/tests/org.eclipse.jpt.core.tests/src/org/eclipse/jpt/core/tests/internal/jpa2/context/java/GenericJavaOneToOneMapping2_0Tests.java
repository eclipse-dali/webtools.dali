/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.OneToOne2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericJavaOneToOneMapping2_0Tests
	extends Generic2_0ContextModelTestCase
{
	public GenericJavaOneToOneMapping2_0Tests(String name) {
		super(name);
	}
	
	
	private void createTestEntity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne");
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
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne @Id").append(CR);				
				sb.append("    private " + TYPE_NAME + " oneToOne;").append(CR);
				sb.append(CR);				
			}
		});
	}
	
	private void createTestEntityWithMapsIdDerivedIdentity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA2_0.MAPS_ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne @MapsId").append(CR);				
				sb.append("    private " + TYPE_NAME + " oneToOne;").append(CR);
				sb.append(CR);				
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.makeSpecified();
		}
	}
	
	private ICompilationUnit createTestEntityWithValidOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne").append(CR);				
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	private void createTestEntityWithValidOneToOneMappingOrphanRemovalSpecified() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne(orphanRemoval=false)");
				sb.append(CR);
				sb.append("    private Address address;");
				sb.append(CR);
				sb.append(CR);
				sb.append("    @Id");			
			}
		});
	}
	
	private void createTestTargetEntityAddress() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("Address").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    @Embedded").append(CR);
				sb.append("    private State state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}
	
	private void createTestEmbeddableState() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("State").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String foo;").append(CR);
				sb.append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "State.java", sourceWriter);
	}
	
	public void testUpdateId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		JavaOneToOneMapping2_0 contextMapping = (JavaOneToOneMapping2_0) contextAttribute.getMapping();
		
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
	
	public void testSetId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		JavaOneToOneMapping2_0 contextMapping = (JavaOneToOneMapping2_0) contextAttribute.getMapping();
		
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
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		JavaOneToOneMapping2_0 contextMapping = (JavaOneToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
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
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		JavaOneToOneMapping2_0 contextMapping = (JavaOneToOneMapping2_0) contextAttribute.getMapping();
		
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
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
	
	public void testUpdatePredominantDerivedIdentityStrategy() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		JavaOneToOneMapping2_0 contextMapping = (JavaOneToOneMapping2_0) contextAttribute.getMapping();
		JavaDerivedIdentity2_0 derivedIdentity = contextMapping.getDerivedIdentity();
		
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceAttribute.addAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceAttribute.removeAnnotation(JPA2_0.MAPS_ID);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceAttribute.removeAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
	}
	
	public void testSetPredominantDerivedIdentityStrategy() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		JavaOneToOneMapping2_0 contextMapping = (JavaOneToOneMapping2_0) contextAttribute.getMapping();
		JavaDerivedIdentity2_0 derivedIdentity = contextMapping.getDerivedIdentity();
		
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setIdDerivedIdentityStrategy();
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setNullDerivedIdentityStrategy();
		assertNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setMapsIdDerivedIdentityStrategy();
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentType resourceType = 
				getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute resourceAttribute = resourceType.persistableAttributes().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		
		((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).setValue("foo");
		getJpaProject().synchronizeContextModel();
		
		assertNull(resourceAttribute.getAnnotation(JPA.ID));
		assertFalse(((JavaOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertEquals("foo", ((JavaOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertFalse(((JavaManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertEquals("foo", ((JavaManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertFalse(((JavaOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNotNull(resourceAttribute.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsId2_0Annotation) resourceAttribute.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertEquals("foo", ((JavaOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}
	
	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = (getJavaPersistentType()).attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		AttributeMapping stateFooMapping = oneToOneMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertEquals("foo", stateFooMapping.getName());
	}
	
	private JavaOrphanRemovable2_0 getOrphanRemovalOf(OneToOneMapping2_0 oneToOneMapping) {
		return ((JavaOrphanRemovalHolder2_0) oneToOneMapping).getOrphanRemoval();
	}

	public void testDefaultOneToOneGetDefaultOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		assertEquals(false, this.getOrphanRemovalOf(oneToOneMapping).isDefaultOrphanRemoval());
	}
	
	public void testSpecifiedOneToOneGetDefaultOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getSpecifiedMapping();
		assertEquals(false, this.getOrphanRemovalOf(oneToOneMapping).isDefaultOrphanRemoval());
	}
	
	public void testGetOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getSpecifiedMapping();
		JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);
		
		assertEquals(false, mappingsOrphanRemoval.isOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		assertEquals(true, mappingsOrphanRemoval.isOrphanRemoval());
	}
	
	public void testGetSpecifiedOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getSpecifiedMapping();
		 JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);

		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOne2_0Annotation oneToOne = (OneToOne2_0Annotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		oneToOne.setOrphanRemoval(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	}
	
	public void testGetSpecifiedOrphanRemoval2() throws Exception {
		this.createTestEntityWithValidOneToOneMappingOrphanRemovalSpecified();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getSpecifiedMapping();

		assertEquals(Boolean.FALSE, this.getOrphanRemovalOf(oneToOneMapping).getSpecifiedOrphanRemoval());
	}

	public void testSetSpecifiedOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		 JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);
		 
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOne2_0Annotation oneToOne = (OneToOne2_0Annotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		
		assertEquals(Boolean.TRUE, oneToOne.getOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(null);
		assertNotNull(attributeResource.getAnnotation(JPA.ONE_TO_ONE)); 	// .getElement);
	}
	
	public void testSetSpecifiedOrphanRemoval2() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		 JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);
		 
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOne2_0Annotation oneToOne = (OneToOne2_0Annotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		
		assertEquals(Boolean.TRUE, oneToOne.getOrphanRemoval());
		
		oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);
		assertEquals(Boolean.TRUE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());

		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(null);
		assertNotNull(attributeResource.getAnnotation(JPA.ONE_TO_ONE));
	}

	public void testGetSpecifiedOrphanRemovalUpdatesFromResourceModelChange() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getSpecifiedMapping();
		 JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);

		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOne2_0Annotation oneToOne = (OneToOne2_0Annotation) attributeResource.getAnnotation(JPA.ONE_TO_ONE);
		oneToOne.setOrphanRemoval(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();

		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		oneToOne.setOrphanRemoval(null);
		getJpaProject().synchronizeContextModel();
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertSame(oneToOneMapping, persistentAttribute.getSpecifiedMapping());
		
		oneToOne.setOrphanRemoval(Boolean.FALSE);
		attributeResource.setPrimaryAnnotation(null, EmptyIterable.<String>instance());
		getJpaProject().synchronizeContextModel();
		
		assertNull(persistentAttribute.getSpecifiedMapping());
	}
	
}
