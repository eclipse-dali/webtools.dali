/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
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
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AccessAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollectionAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsIdAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OneToOneAnnotation2_0;
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_ONE);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_ONE, JPA2_0.MAPS_ID);
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
		for (OrmPersistentAttribute each : ormPersistentType.getAttributes()) {
			each.addToXml();
		}
	}
	
	private ICompilationUnit createTestEntityWithValidOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_ONE);
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

	private void createTestTargetEntityAddressWithElementCollection() throws Exception {
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
					sb.append(JPA2_0.ELEMENT_COLLECTION);
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
				sb.append("    @ElementCollection").append(CR);
				sb.append("    private java.util.Collection<State> state;").append(CR);
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
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testMorphToElementCollectionMapping() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SpecifiedJoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		resourceField.addAnnotation(AccessAnnotation2_0.ANNOTATION_NAME);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ElementCollectionMapping2_0);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ElementCollectionAnnotation2_0.ANNOTATION_NAME));
		assertEquals(0, resourceField.getAnnotationsSize(JoinColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(AccessAnnotation2_0.ANNOTATION_NAME));
	}
		
	public void testUpdateId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OneToOneMapping2_0 contextMapping = (OneToOneMapping2_0) contextAttribute.getMapping();
		
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
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OneToOneMapping2_0 contextMapping = (OneToOneMapping2_0) contextAttribute.getMapping();
		
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
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OneToOneMapping2_0 contextMapping = (OneToOneMapping2_0) contextAttribute.getMapping();
		
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
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OneToOneMapping2_0 contextMapping = (OneToOneMapping2_0) contextAttribute.getMapping();
		
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
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OneToOneMapping2_0 contextMapping = (OneToOneMapping2_0) contextAttribute.getMapping();
		DerivedIdentity2_0 derivedIdentity = contextMapping.getDerivedIdentity();
		
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceField.addAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceField.removeAnnotation(JPA2_0.MAPS_ID);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceField.removeAnnotation(JPA.ID);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
	}
	
	public void testSetPredominantDerivedIdentityStrategy() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OneToOneMapping2_0 contextMapping = (OneToOneMapping2_0) contextAttribute.getMapping();
		DerivedIdentity2_0 derivedIdentity = contextMapping.getDerivedIdentity();
		
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setIdDerivedIdentityStrategy();
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNotNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setNullDerivedIdentityStrategy();
		assertNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setMapsIdDerivedIdentityStrategy();
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		JavaPersistentType contextType = getJavaPersistentType();
		JavaSpecifiedPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		
		((MapsIdAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAPS_ID)).setValue("foo");
		getJpaProject().synchronizeContextModel();
		
		assertNull(resourceField.getAnnotation(JPA.ID));
		assertFalse(((OneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsIdAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertEquals("foo", ((OneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		contextAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertFalse(((ManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsIdAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertEquals("foo", ((ManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
		
		contextAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertFalse(((OneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertNotNull(resourceField.getAnnotation(JPA2_0.MAPS_ID));
		assertEquals("foo", ((MapsIdAnnotation2_0) resourceField.getAnnotation(JPA2_0.MAPS_ID)).getValue());
		assertEquals("foo", ((OneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedIdAttributeName());
	}
	
	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		SpecifiedPersistentAttribute persistentAttribute = (getJavaPersistentType()).getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
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
	
	public void testCandidateMappedByAttributeNamesElementCollection() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddressWithElementCollection();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		SpecifiedPersistentAttribute persistentAttribute = (getJavaPersistentType()).getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
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
	
	private OrphanRemovable2_0 getOrphanRemovalOf(OneToOneMapping2_0 oneToOneMapping) {
		return ((OrphanRemovalMapping2_0) oneToOneMapping).getOrphanRemoval();
	}

	public void testDefaultOneToOneGetDefaultOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		assertEquals(false, this.getOrphanRemovalOf(oneToOneMapping).isDefaultOrphanRemoval());
	}
	
	public void testSpecifiedOneToOneGetDefaultOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		assertEquals(false, this.getOrphanRemovalOf(oneToOneMapping).isDefaultOrphanRemoval());
	}
	
	public void testGetOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);
		
		assertEquals(false, mappingsOrphanRemoval.isOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		assertEquals(true, mappingsOrphanRemoval.isOrphanRemoval());
	}
	
	public void testGetSpecifiedOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);

		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		OneToOneAnnotation2_0 oneToOne = (OneToOneAnnotation2_0) resourceField.getAnnotation(JPA.ONE_TO_ONE);
		oneToOne.setOrphanRemoval(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	}
	
	public void testGetSpecifiedOrphanRemoval2() throws Exception {
		this.createTestEntityWithValidOneToOneMappingOrphanRemovalSpecified();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();

		assertEquals(Boolean.FALSE, this.getOrphanRemovalOf(oneToOneMapping).getSpecifiedOrphanRemoval());
	}

	public void testSetSpecifiedOrphanRemoval() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);
		 
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		OneToOneAnnotation2_0 oneToOne = (OneToOneAnnotation2_0) resourceField.getAnnotation(JPA.ONE_TO_ONE);
		
		assertEquals(Boolean.TRUE, oneToOne.getOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(null);
		assertNotNull(resourceField.getAnnotation(JPA.ONE_TO_ONE)); 	// .getElement);
	}
	
	public void testSetSpecifiedOrphanRemoval2() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);
		 
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		OneToOneAnnotation2_0 oneToOne = (OneToOneAnnotation2_0) resourceField.getAnnotation(JPA.ONE_TO_ONE);
		
		assertEquals(Boolean.TRUE, oneToOne.getOrphanRemoval());
		
		oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);
		assertEquals(Boolean.TRUE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());

		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(null);
		assertNotNull(resourceField.getAnnotation(JPA.ONE_TO_ONE));
	}

	public void testGetSpecifiedOrphanRemovalUpdatesFromResourceModelChange() throws Exception {
		this.createTestEntity();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 oneToOneMapping = (OneToOneMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToOneMapping);

		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		OneToOneAnnotation2_0 oneToOne = (OneToOneAnnotation2_0) resourceField.getAnnotation(JPA.ONE_TO_ONE);
		oneToOne.setOrphanRemoval(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();

		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		oneToOne.setOrphanRemoval(null);
		getJpaProject().synchronizeContextModel();
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertSame(oneToOneMapping, persistentAttribute.getMapping());
		
		oneToOne.setOrphanRemoval(Boolean.FALSE);
		resourceField.setPrimaryAnnotation(null, EmptyIterable.<String>instance());
		getJpaProject().synchronizeContextModel();
		
		assertTrue(persistentAttribute.getMapping().isDefault());
	}

	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) resourceField.getAnnotation(JPA.ONE_TO_ONE);
		SpecifiedPersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 mapping = (OneToOneMapping2_0) contextAttribute.getMapping();
		OneToOneRelationship2_0 rel = (OneToOneRelationship2_0) mapping.getRelationship();

		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());

		rel.setStrategyToPrimaryKeyJoinColumn();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		
		rel.setStrategyToMappedBy();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(annotation.getMappedBy());
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		
		rel.setStrategyToJoinTable();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		assertTrue(rel.strategyIsJoinTable());
		
		rel.setStrategyToJoinColumn();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) resourceField.getAnnotation(JPA.ONE_TO_ONE);
		SpecifiedPersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping2_0 mapping = (OneToOneMapping2_0) contextAttribute.getMapping();
		OneToOneRelationship2_0 rel = (OneToOneRelationship2_0) mapping.getRelationship();

		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());

		resourceField.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		
		annotation.setMappedBy("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(annotation.getMappedBy());
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		
		resourceField.addAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(annotation.getMappedBy());
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		
		resourceField.addAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(annotation.getMappedBy());
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		
		resourceField.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(annotation.getMappedBy());
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		
		annotation.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		assertTrue(rel.strategyIsJoinTable());
		
		resourceField.removeAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());	
		assertTrue(rel.strategyIsJoinTable());
		
		resourceField.removeAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertEquals(0, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		assertFalse(rel.strategyIsJoinTable());
	}
}
