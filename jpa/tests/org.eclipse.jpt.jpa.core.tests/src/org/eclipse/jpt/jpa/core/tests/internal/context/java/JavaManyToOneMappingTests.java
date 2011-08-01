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
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneRelationship;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
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
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaManyToOneMappingTests extends ContextModelTestCase
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
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}

	private ICompilationUnit createTestEntityWithManyToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE);
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
	
	private ICompilationUnit createTestEntityWithValidManyToOneMapping() throws Exception {
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
				sb.append("    @ManyToOne").append(CR);				
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithCollectionManyToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne").append(CR);				
				sb.append("    private Collection address;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithGenericizedCollectionManyToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne").append(CR);				
				sb.append("    private Collection<Address> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	public JavaManyToOneMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(manyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		manyToOne.setTargetEntity("newTargetEntity");
		this.getJpaProject().synchronizeContextModel();
		assertEquals("newTargetEntity", manyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
	
		//set target entity to null in the resource model
		manyToOne.setTargetEntity(null);
		this.getJpaProject().synchronizeContextModel();
		assertNull(manyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testModifySpecifiedTargetEntity() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(manyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		manyToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", manyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
		
		//set target entity to null in the context model
		manyToOneMapping.setSpecifiedTargetEntity(null);
		assertNull(manyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testUpdateSpecifiedOptional() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(manyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOne.getOptional());
				
		//set optional in the resource model, verify context model updated
		manyToOne.setOptional(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, manyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, manyToOne.getOptional());
		
		manyToOne.setOptional(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.FALSE, manyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, manyToOne.getOptional());
		
		//set optional to null in the resource model
		manyToOne.setOptional(null);
		getJpaProject().synchronizeContextModel();
		assertNull(manyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOne.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ManyToOneAnnotation manyToOne = (ManyToOneAnnotation) resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(manyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOne.getOptional());
				
		//set optional in the context model, verify resource model updated
		manyToOneMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, manyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, manyToOne.getOptional());
	
		manyToOneMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, manyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, manyToOne.getOptional());

		
		//set optional to null in the context model
		manyToOneMapping.setSpecifiedOptional(null);
		assertNull(manyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOne.getOptional());
	}
	
	
	public void testSpecifiedJoinColumns() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		
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
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
		
		joinColumns.addSpecifiedJoinColumn(0);
		assertEquals(1, joinColumns.getSpecifiedJoinColumnsSize());
		
		joinColumns.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinColumns.getSpecifiedJoinColumnsSize());
	}

	public void testJoinColumnsSize() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		
		assertEquals(1, joinColumns.getJoinColumnsSize());
		
		joinColumns.addSpecifiedJoinColumn(0);
		assertEquals(1, joinColumns.getJoinColumnsSize());
		
		joinColumns.addSpecifiedJoinColumn(0);
		assertEquals(2, joinColumns.getJoinColumnsSize());

		joinColumns.removeSpecifiedJoinColumn(0);
		joinColumns.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinColumns.getJoinColumnsSize());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> joinColumnsIterator = resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertFalse(joinColumnsIterator.hasNext());
	}
	
	public void testAddSpecifiedJoinColumn2() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> joinColumnsIterator = resourceField.getAnnotations(JPA.JOIN_COLUMN).iterator();
		
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertFalse(joinColumnsIterator.hasNext());
	}
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		
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
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		
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
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
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
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();
		JoinColumnRelationshipStrategy joinColumns = manyToOneMapping.getRelationship().getJoinColumnStrategy();
		
		assertTrue(joinColumns.getDefaultJoinColumn().isDefault());
		
		joinColumns.addSpecifiedJoinColumn(0);
		JoinColumn specifiedJoinColumn = joinColumns.getSpecifiedJoinColumns().iterator().next();
		assertFalse(specifiedJoinColumn.isDefault());
		
		assertNull(joinColumns.getDefaultJoinColumn());
	}
	
	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		PersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping mapping = (ManyToOneMapping) contextAttribute.getMapping();
		ManyToOneRelationship rel = mapping.getRelationship();
		
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertTrue(rel.strategyIsJoinColumn());
		
		rel.setStrategyToJoinColumn();
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertTrue(rel.strategyIsJoinColumn());
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		PersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping mapping = (ManyToOneMapping) contextAttribute.getMapping();
		ManyToOneRelationship rel = mapping.getRelationship();
		
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertTrue(rel.strategyIsJoinColumn());
		
		resourceField.addAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertTrue(rel.strategyIsJoinColumn());
		
		resourceField.removeAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertTrue(rel.strategyIsJoinColumn());
	}
	
	public void testDefaultTargetEntity() throws Exception {
		createTestEntityWithValidManyToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit, default still set, handled by validation
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getDefaultTargetEntity());
		
		//add targetEntity to the persistence unit
		addXmlClassRef(PACKAGE_NAME + ".Address");
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getDefaultTargetEntity());

		//test default still the same when specified target entity it set
		manyToOneMapping.setSpecifiedTargetEntity("foo");
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getDefaultTargetEntity());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef addressClassRef = classRefs.next();
		JavaPersistentType addressPersistentType = addressClassRef.getJavaPersistentType();

		//test target is not an Entity, default target entity still exists, this case handled with validation
		addressPersistentType.setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityCollectionType() throws Exception {
		createTestEntityWithCollectionManyToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();

		assertNull(manyToOneMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityGenericizedCollectionType() throws Exception {
		createTestEntityWithGenericizedCollectionManyToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();

		assertNull(manyToOneMapping.getDefaultTargetEntity());
	}
	
	public void testTargetEntity() throws Exception {
		createTestEntityWithValidManyToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getTargetEntity());

		manyToOneMapping.setSpecifiedTargetEntity("foo");
		assertEquals("foo", manyToOneMapping.getTargetEntity());
		
		manyToOneMapping.setSpecifiedTargetEntity(null);
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getTargetEntity());
	}
	
	public void testResolvedTargetEntity() throws Exception {
		createTestEntityWithValidManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit
		assertNull(manyToOneMapping.getResolvedTargetEntity());
		
		//add targetEntity to the persistence unit, now target entity should resolve
		createTestTargetEntityAddress();
		addXmlClassRef(PACKAGE_NAME + ".Address");
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef addressClassRef = classRefs.next();
		TypeMapping addressTypeMapping = addressClassRef.getJavaPersistentType().getMapping();
		assertEquals(addressTypeMapping, manyToOneMapping.getResolvedTargetEntity());

		//test default still the same when specified target entity it set
		manyToOneMapping.setSpecifiedTargetEntity("foo");
		assertNull(manyToOneMapping.getResolvedTargetEntity());
		
		
		manyToOneMapping.setSpecifiedTargetEntity(PACKAGE_NAME + ".Address");
		assertEquals(addressTypeMapping, manyToOneMapping.getResolvedTargetEntity());
		

		manyToOneMapping.setSpecifiedTargetEntity(null);
		assertEquals(addressTypeMapping, manyToOneMapping.getResolvedTargetEntity());
	}

}
