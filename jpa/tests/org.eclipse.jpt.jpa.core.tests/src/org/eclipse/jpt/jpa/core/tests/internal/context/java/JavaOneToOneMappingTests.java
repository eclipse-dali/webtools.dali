/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.MappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneRelationship;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
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
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaOneToOneMappingTests extends ContextModelTestCase
{

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
	
	private ICompilationUnit createTestEntityWithOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne").append(CR);
			}
		});
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
	
	private ICompilationUnit createTestEntityWithCollectionOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne").append(CR);				
				sb.append("    private Collection address;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithGenericizedCollectionOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne").append(CR);				
				sb.append("    private Collection<Address> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	public JavaOneToOneMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}

	
	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation oneToOneAnnotation = (OneToOneAnnotation) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOneAnnotation.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		oneToOneAnnotation.setTargetEntity("newTargetEntity");
		this.getJpaProject().synchronizeContextModel();
		assertEquals("newTargetEntity", oneToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToOneAnnotation.getTargetEntity());
	
		//set target entity to null in the resource model
		oneToOneAnnotation.setTargetEntity(null);
		this.getJpaProject().synchronizeContextModel();
		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOneAnnotation.getTargetEntity());
	}
	
	public void testModifySpecifiedTargetEntity() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		oneToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", oneToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
	
		//set target entity to null in the context model
		oneToOneMapping.setSpecifiedTargetEntity(null);
		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
	}
	
	public void testUpdateMappedBy() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		MappedByRelationshipStrategy mappedBy = oneToOneMapping.getRelationship().getMappedByStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(mappedBy.getMappedByAttribute());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		oneToOne.setMappedBy("newMappedBy");
		getJpaProject().synchronizeContextModel();
		assertEquals("newMappedBy", mappedBy.getMappedByAttribute());
		assertEquals("newMappedBy", oneToOne.getMappedBy());
	
		//set mappedBy to null in the resource model
		oneToOne.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertNull(mappedBy.getMappedByAttribute());
		assertNull(oneToOne.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		MappedByRelationshipStrategy mappedBy = oneToOneMapping.getRelationship().getMappedByStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(mappedBy.getMappedByAttribute());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedByJoiningStrategy in the context model, verify resource model updated
		mappedBy.setMappedByAttribute("newTargetEntity");
		assertEquals("newTargetEntity", mappedBy.getMappedByAttribute());
		assertEquals("newTargetEntity", oneToOne.getMappedBy());
	
		//set mappedByJoiningStrategy to null in the context model
		mappedBy.setMappedByAttribute(null);
		assertNull(mappedBy.getMappedByAttribute());
		assertNull(oneToOne.getMappedBy());
	}

	public void testUpdateSpecifiedOptional() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(oneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOne.getOptional());
				
		//set optional in the resource model, verify context model updated
		oneToOne.setOptional(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, oneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, oneToOne.getOptional());
	
		oneToOne.setOptional(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.FALSE, oneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, oneToOne.getOptional());

		
		//set optional to null in the resource model
		oneToOne.setOptional(null);
		getJpaProject().synchronizeContextModel();
		assertNull(oneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOne.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(oneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOne.getOptional());
				
		//set optional in the context model, verify resource model updated
		oneToOneMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, oneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, oneToOne.getOptional());
		
		oneToOneMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, oneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, oneToOne.getOptional());

		
		//set optional to null in the context model
		oneToOneMapping.setSpecifiedOptional(null);
		assertNull(oneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOne.getOptional());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToOne.setFetch(org.eclipse.jpt.jpa.core.resource.java.FetchType.EAGER);
		getJpaProject().synchronizeContextModel();
		assertEquals(FetchType.EAGER, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.FetchType.EAGER, oneToOne.getFetch());
		
		oneToOne.setFetch(org.eclipse.jpt.jpa.core.resource.java.FetchType.LAZY);
		getJpaProject().synchronizeContextModel();
		assertEquals(FetchType.LAZY, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.FetchType.LAZY, oneToOne.getFetch());
		
		//set fetch to null in the resource model
		oneToOne.setFetch(null);
		getJpaProject().synchronizeContextModel();
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
				
		//set fetch in the context model, verify resource model updated
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(FetchType.EAGER, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.FetchType.EAGER, oneToOne.getFetch());
	
		oneToOneMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.FetchType.LAZY, oneToOne.getFetch());
		
		//set fetch to null in the context model
		oneToOneMapping.setSpecifiedFetch(null);
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
	}
	
	public void testSpecifiedJoinColumns() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		ListIterator<? extends JoinColumn> specifiedJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();
		
		assertFalse(specifiedJoinColumns.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		JoinColumnAnnotation joinColumn = (JoinColumnAnnotation) resourceField.addAnnotation(0, JPA.JOIN_COLUMN);
		joinColumn.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();	
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		joinColumn = (JoinColumnAnnotation) resourceField.addAnnotation(0, JPA.JOIN_COLUMN);
		joinColumn.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();		
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());


		joinColumn = (JoinColumnAnnotation) resourceField.addAnnotation(0, JPA.JOIN_COLUMN);
		joinColumn.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();		
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceField.moveAnnotation(1, 0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();		
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		resourceField.removeAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();		
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());
	
		resourceField.removeAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();		
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		
		resourceField.removeAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();		
		assertFalse(specifiedJoinColumns.hasNext());
	}
	
	public void testGetDefaultJoin() {
		//TODO
	}
	
	public void testSpecifiedJoinColumnsSize() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
		
		joinColumns.addSpecifiedJoinColumn(0);
		assertEquals(1, joinColumns.getSpecifiedJoinColumnsSize());
		
		joinColumns.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
	}

	public void testJoinColumnsSize() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		assertEquals(1, joinColumns.getJoinColumnsSize());
		
		joinColumns.addSpecifiedJoinColumn(0);
		assertEquals(1, joinColumns.getJoinColumnsSize());
		
		joinColumns.addSpecifiedJoinColumn(0);
		assertEquals(2, joinColumns.getJoinColumnsSize());

		joinColumns.removeSpecifiedJoinColumn(0);
		joinColumns.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinColumns.getJoinColumnsSize());
		
		//if non-owning side of the relationship then no default join column
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("foo");
		assertEquals(0, joinColumns.getJoinColumnsSize());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> joinColumnsIterator = 
			resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertFalse(joinColumnsIterator.hasNext());
	}
	
	public void testAddSpecifiedJoinColumn2() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> joinColumnsIterator = 
			resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertFalse(joinColumnsIterator.hasNext());
	}
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertEquals(3, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));

		joinColumns.removeSpecifiedJoinColumn(1);
		
		Iterator<NestableAnnotation> joinColumnResources = resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumnResources.next()).getName());
		assertFalse(joinColumnResources.hasNext());
		
		Iterator<? extends JoinColumn> joinColumnsIterator = joinColumns.getSpecifiedJoinColumns().iterator();
		assertEquals("FOO", joinColumnsIterator.next().getName());		
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
	
		
		joinColumns.removeSpecifiedJoinColumn(1);
		joinColumnResources = resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertFalse(joinColumnResources.hasNext());

		joinColumnsIterator = joinColumns.getSpecifiedJoinColumns().iterator();
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());

		
		joinColumns.removeSpecifiedJoinColumn(0);
		joinColumnResources = resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		assertFalse(joinColumnResources.hasNext());
		joinColumnsIterator = joinColumns.getSpecifiedJoinColumns().iterator();
		assertFalse(joinColumnsIterator.hasNext());

		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		Iterator<NestableAnnotation> javaJoinColumns = resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		assertEquals(3, CollectionTools.size(javaJoinColumns));
		
		
		joinColumns.moveSpecifiedJoinColumn(2, 0);
		ListIterator<? extends JoinColumn> primaryKeyJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		assertEquals("BAR", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("BAZ", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());


		joinColumns.moveSpecifiedJoinColumn(0, 1);
		primaryKeyJoinColumns = joinColumns.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		assertEquals("BAZ", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
	}
	
	public void testUpdateSpecifiedJoinColumns() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		((JoinColumnAnnotation) resourceField.addAnnotation(0, JPA.JOIN_COLUMN)).setName("FOO");
		((JoinColumnAnnotation) resourceField.addAnnotation(1, JPA.JOIN_COLUMN)).setName("BAR");
		((JoinColumnAnnotation) resourceField.addAnnotation(2, JPA.JOIN_COLUMN)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<? extends JoinColumn> joinColumnsIterator = joinColumns.getSpecifiedJoinColumns().iterator();
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertEquals("BAR", joinColumnsIterator.next().getName());
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
		
		resourceField.moveAnnotation(2, 0, JoinColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumnsIterator.next().getName());
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
	
		resourceField.moveAnnotation(0, 1, JoinColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertEquals("BAR", joinColumnsIterator.next().getName());
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
	
		resourceField.removeAnnotation(1, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
	
		resourceField.removeAnnotation(1, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
		
		resourceField.removeAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.getSpecifiedJoinColumns().iterator();
		assertFalse(joinColumnsIterator.hasNext());
	}
	
	public void testJoinColumnIsVirtual() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = oneToOneMapping.getRelationship().getJoinColumnStrategy();
		
		assertTrue(joinColumns.getDefaultJoinColumn().isDefault());

		joinColumns.addSpecifiedJoinColumn(0);
		JoinColumn specifiedJoinColumn = joinColumns.getSpecifiedJoinColumns().iterator().next();
		assertFalse(specifiedJoinColumn.isDefault());
		
		assertNull(joinColumns.getDefaultJoinColumn());
	}

	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		AttributeMapping stateFooMapping = oneToOneMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertNull(stateFooMapping);
	}

	public void testDefaultTargetEntity() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit, default still set, handled by validation
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());
		
		//add targetEntity to the persistence unit
		addXmlClassRef(PACKAGE_NAME + ".Address");
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());

		//test default still the same when specified target entity it set
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef addressClassRef = classRefs.next();
		JavaPersistentType addressPersistentType = addressClassRef.getJavaPersistentType();

		//test target is not an Entity, default target entity still exists, this case handled with validation
		addressPersistentType.setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityCollectionType() throws Exception {
		createTestEntityWithCollectionOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertNull(oneToOneMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityGenericizedCollectionType() throws Exception {
		createTestEntityWithGenericizedCollectionOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertNull(oneToOneMapping.getDefaultTargetEntity());
	}
	
	public void testTargetEntity() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getTargetEntity());

		oneToOneMapping.setSpecifiedTargetEntity("foo");
		assertEquals("foo", oneToOneMapping.getTargetEntity());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getTargetEntity());
	}
	
	public void testResolvedTargetEntity() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit
		assertNull(oneToOneMapping.getResolvedTargetEntity());
		
		//add targetEntity to the persistence unit, now target entity should resolve
		createTestTargetEntityAddress();
		addXmlClassRef(PACKAGE_NAME + ".Address");
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef addressClassRef = classRefs.next();
		TypeMapping addressTypeMapping = addressClassRef.getJavaPersistentType().getMapping();
		assertEquals(addressTypeMapping, oneToOneMapping.getResolvedTargetEntity());

		//test default still the same when specified target entity it set
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		assertNull(oneToOneMapping.getResolvedTargetEntity());
		
		
		oneToOneMapping.setSpecifiedTargetEntity(PACKAGE_NAME + ".Address");
		assertEquals(addressTypeMapping, oneToOneMapping.getResolvedTargetEntity());
		

		oneToOneMapping.setSpecifiedTargetEntity(null);
		assertEquals(addressTypeMapping, oneToOneMapping.getResolvedTargetEntity());
	}
	
	public void testPrimaryKeyJoinColumns() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnRelationshipStrategy strategy = 
			oneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		Iterator<? extends PrimaryKeyJoinColumn> primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		
		assertFalse(primaryKeyJoinColumns.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		joinColumn.setName("FOO");
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();	
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		joinColumn = (PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		joinColumn.setName("BAR");
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());


		joinColumn = (PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		joinColumn.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceField.moveAnnotation(1, 0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		resourceField.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();		
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		resourceField.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();		
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		
		resourceField.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();		
		assertFalse(primaryKeyJoinColumns.hasNext());
	}
	
	public void testPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnRelationshipStrategy strategy = 
			oneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		
		assertEquals(0, strategy.getPrimaryKeyJoinColumnsSize());
		
		strategy.addPrimaryKeyJoinColumn(0);
		assertEquals(1, strategy.getPrimaryKeyJoinColumnsSize());
		
		strategy.removePrimaryKeyJoinColumn(0);
		assertEquals(0, strategy.getPrimaryKeyJoinColumnsSize());
	}

	public void testAddPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnRelationshipStrategy strategy = 
			oneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> joinColumns = resourceField.getAnnotations(JPA.PRIMARY_KEY_JOIN_COLUMN).iterator();
		
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertFalse(joinColumns.hasNext());
	}
	
	public void testAddPrimaryKeyJoinColumn2() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnRelationshipStrategy strategy = 
			oneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> joinColumns = resourceField.getAnnotations(JPA.PRIMARY_KEY_JOIN_COLUMN).iterator();
		
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertFalse(joinColumns.hasNext());
	}
	
	public void testRemovePrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnRelationshipStrategy strategy = 
			oneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertEquals(3, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));

		strategy.removePrimaryKeyJoinColumn(1);
		
		Iterator<NestableAnnotation> joinColumnResources = resourceField.getAnnotations(JPA.PRIMARY_KEY_JOIN_COLUMN).iterator();
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) joinColumnResources.next()).getName());
		assertFalse(joinColumnResources.hasNext());
		
		Iterator<? extends PrimaryKeyJoinColumn> joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());		
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		
		strategy.removePrimaryKeyJoinColumn(1);
		joinColumnResources = resourceField.getAnnotations(JPA.PRIMARY_KEY_JOIN_COLUMN).iterator();
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertFalse(joinColumnResources.hasNext());

		joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		
		strategy.removePrimaryKeyJoinColumn(0);
		joinColumnResources = resourceField.getAnnotations(JPA.PRIMARY_KEY_JOIN_COLUMN).iterator();
		assertFalse(joinColumnResources.hasNext());
		joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertFalse(joinColumns.hasNext());

		assertNull(resourceField.getAnnotation(0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMovePrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnRelationshipStrategy strategy = 
			oneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		Iterator<NestableAnnotation> javaJoinColumns = resourceField.getAnnotations(JPA.PRIMARY_KEY_JOIN_COLUMN).iterator();
		assertEquals(3, CollectionTools.size(javaJoinColumns));
		
		
		strategy.movePrimaryKeyJoinColumn(2, 0);
		ListIterator<? extends PrimaryKeyJoinColumn> primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = resourceField.getAnnotations(JPA.PRIMARY_KEY_JOIN_COLUMN).iterator();
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());


		strategy.movePrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = resourceField.getAnnotations(JPA.PRIMARY_KEY_JOIN_COLUMN).iterator();
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
	}
	
	public void testUpdatePrimaryKeyJoinColumns() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnRelationshipStrategy strategy = 
			oneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		
		((PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN)).setName("FOO");
		((PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN)).setName("BAR");
		((PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(2, JPA.PRIMARY_KEY_JOIN_COLUMN)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<? extends PrimaryKeyJoinColumn> joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		resourceField.moveAnnotation(2, 0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		resourceField.moveAnnotation(0, 1, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		resourceField.removeAnnotation(1,  JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		resourceField.removeAnnotation(1,  JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		resourceField.removeAnnotation(0,  JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertFalse(joinColumns.hasNext());
	}
	
	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) resourceField.getAnnotation(JPA.ONE_TO_ONE);
		PersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping mapping = (OneToOneMapping) contextAttribute.getMapping();
		OneToOneRelationship rel = mapping.getRelationship();
		
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		rel.setStrategyToPrimaryKeyJoinColumn();
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		rel.setStrategyToMappedBy();
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		
		rel.setStrategyToJoinColumn();
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		OneToOneAnnotation annotation = (OneToOneAnnotation) resourceField.getAnnotation(JPA.ONE_TO_ONE);
		PersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToOneMapping mapping = (OneToOneMapping) contextAttribute.getMapping();
		OneToOneRelationship rel = mapping.getRelationship();
		
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceField.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		annotation.setMappedBy("foo");
		getJpaProject().synchronizeContextModel();
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceField.addAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceField.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		
		annotation.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceField.removeAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());	
	}
}
