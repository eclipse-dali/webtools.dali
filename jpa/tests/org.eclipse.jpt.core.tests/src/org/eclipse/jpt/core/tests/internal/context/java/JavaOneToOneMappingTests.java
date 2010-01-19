/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.MappedByJoiningStrategy;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		joinColumns.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}

	
	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		oneToOne.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", oneToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
	
		//set target entity to null in the resource model
		oneToOne.setTargetEntity(null);
		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
	}
	
	public void testModifySpecifiedTargetEntity() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		MappedByJoiningStrategy mappedBy = oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		MappedByJoiningStrategy mappedBy = oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToOne.setFetch(org.eclipse.jpt.core.resource.java.FetchType.EAGER);
		getJpaProject().synchronizeContextModel();
		assertEquals(FetchType.EAGER, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.EAGER, oneToOne.getFetch());
		
		oneToOne.setFetch(org.eclipse.jpt.core.resource.java.FetchType.LAZY);
		getJpaProject().synchronizeContextModel();
		assertEquals(FetchType.LAZY, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, oneToOne.getFetch());
		
		//set fetch to null in the resource model
		oneToOne.setFetch(null);
		getJpaProject().synchronizeContextModel();
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
				
		//set fetch in the context model, verify resource model updated
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(FetchType.EAGER, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.EAGER, oneToOne.getFetch());
	
		oneToOneMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, oneToOne.getFetch());
		
		//set fetch to null in the context model
		oneToOneMapping.setSpecifiedFetch(null);
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
	}
	
	public void testSpecifiedJoinColumns() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		ListIterator<? extends JoinColumn> specifiedJoinColumns = joinColumns.specifiedJoinColumns();
		
		assertFalse(specifiedJoinColumns.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		//add an annotation to the resource model and verify the context model is updated
		JoinColumnAnnotation joinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.specifiedJoinColumns();	
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		joinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.specifiedJoinColumns();		
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());


		joinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.specifiedJoinColumns();		
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		attributeResource.moveAnnotation(1, 0, JPA.JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.specifiedJoinColumns();		
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		attributeResource.removeAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.specifiedJoinColumns();		
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());
	
		attributeResource.removeAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.specifiedJoinColumns();		
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		
		attributeResource.removeAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		specifiedJoinColumns = joinColumns.specifiedJoinColumns();		
		assertFalse(specifiedJoinColumns.hasNext());
	}
	
	public void testGetDefaultJoin() {
		//TODO
	}
	
	public void testSpecifiedJoinColumnsSize() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		assertEquals(0, joinColumns.specifiedJoinColumnsSize());
		
		joinColumns.addSpecifiedJoinColumn(0);
		assertEquals(1, joinColumns.specifiedJoinColumnsSize());
		
		joinColumns.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinColumns.specifiedJoinColumnsSize());
	}

	public void testJoinColumnsSize() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		assertEquals(1, joinColumns.joinColumnsSize());
		
		joinColumns.addSpecifiedJoinColumn(0);
		assertEquals(1, joinColumns.joinColumnsSize());
		
		joinColumns.addSpecifiedJoinColumn(0);
		assertEquals(2, joinColumns.joinColumnsSize());

		joinColumns.removeSpecifiedJoinColumn(0);
		joinColumns.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinColumns.joinColumnsSize());
		
		//if non-owning side of the relationship then no default join column
		oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("foo");
		assertEquals(0, joinColumns.joinColumnsSize());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		Iterator<NestableAnnotation> joinColumnsIterator = 
			attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertFalse(joinColumnsIterator.hasNext());
	}
	
	public void testAddSpecifiedJoinColumn2() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		Iterator<NestableAnnotation> joinColumnsIterator = 
			attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumnsIterator.next()).getName());
		assertFalse(joinColumnsIterator.hasNext());
	}
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertEquals(3, CollectionTools.size(attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME)));

		joinColumns.removeSpecifiedJoinColumn(1);
		
		Iterator<NestableAnnotation> joinColumnResources = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumnResources.next()).getName());
		assertFalse(joinColumnResources.hasNext());
		
		Iterator<? extends JoinColumn> joinColumnsIterator = joinColumns.specifiedJoinColumns();
		assertEquals("FOO", joinColumnsIterator.next().getName());		
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
	
		
		joinColumns.removeSpecifiedJoinColumn(1);
		joinColumnResources = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertFalse(joinColumnResources.hasNext());

		joinColumnsIterator = joinColumns.specifiedJoinColumns();
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());

		
		joinColumns.removeSpecifiedJoinColumn(0);
		joinColumnResources = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		assertFalse(joinColumnResources.hasNext());
		joinColumnsIterator = joinColumns.specifiedJoinColumns();
		assertFalse(joinColumnsIterator.hasNext());

		assertNull(attributeResource.getAnnotation(JoinColumnsAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		joinColumns.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinColumns.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinColumns.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentAttribute attributeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		
		Iterator<NestableAnnotation> javaJoinColumns = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaJoinColumns));
		
		
		joinColumns.moveSpecifiedJoinColumn(2, 0);
		ListIterator<? extends JoinColumn> primaryKeyJoinColumns = joinColumns.specifiedJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("BAZ", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());


		joinColumns.moveSpecifiedJoinColumn(0, 1);
		primaryKeyJoinColumns = joinColumns.specifiedJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((JoinColumnAnnotation) javaJoinColumns.next()).getName());
	}
	
	public void testUpdateSpecifiedJoinColumns() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		JavaResourcePersistentAttribute attributeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
	
		((JoinColumnAnnotation) attributeResource.addAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME)).setName("FOO");
		((JoinColumnAnnotation) attributeResource.addAnnotation(1, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME)).setName("BAR");
		((JoinColumnAnnotation) attributeResource.addAnnotation(2, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<? extends JoinColumn> joinColumnsIterator = joinColumns.specifiedJoinColumns();
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertEquals("BAR", joinColumnsIterator.next().getName());
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
		
		attributeResource.moveAnnotation(2, 0, JoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.specifiedJoinColumns();
		assertEquals("BAR", joinColumnsIterator.next().getName());
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
	
		attributeResource.moveAnnotation(0, 1, JoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.specifiedJoinColumns();
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertEquals("BAR", joinColumnsIterator.next().getName());
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
	
		attributeResource.removeAnnotation(1,  JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.specifiedJoinColumns();
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
	
		attributeResource.removeAnnotation(1,  JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.specifiedJoinColumns();
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());
		
		attributeResource.removeAnnotation(0,  JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = joinColumns.specifiedJoinColumns();
		assertFalse(joinColumnsIterator.hasNext());
	}
	
	public void testJoinColumnIsVirtual() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JoinColumnJoiningStrategy joinColumns = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy();
		
		assertTrue(joinColumns.getDefaultJoinColumn().isVirtual());

		joinColumns.addSpecifiedJoinColumn(0);
		JoinColumn specifiedJoinColumn = joinColumns.specifiedJoinColumns().next();
		assertFalse(specifiedJoinColumn.isVirtual());
		
		assertNull(joinColumns.getDefaultJoinColumn());
	}

	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
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
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		AttributeMapping stateFooMapping = oneToOneMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertNull(stateFooMapping);
	}

	public void testDefaultTargetEntity() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit, default still set, handled by validation
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());
		
		//add targetEntity to the persistence unit
		addXmlClassRef(PACKAGE_NAME + ".Address");
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());

		//test default still the same when specified target entity it set
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
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
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertNull(oneToOneMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityGenericizedCollectionType() throws Exception {
		createTestEntityWithGenericizedCollectionOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertNull(oneToOneMapping.getDefaultTargetEntity());
	}
	
	public void testTargetEntity() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit
		assertNull(oneToOneMapping.getResolvedTargetEntity());
		
		//add targetEntity to the persistence unit, now target entity should resolve
		createTestTargetEntityAddress();
		addXmlClassRef(PACKAGE_NAME + ".Address");
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnJoiningStrategy strategy = 
			oneToOneMapping.getRelationshipReference().getPrimaryKeyJoinColumnJoiningStrategy();
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = 
			strategy.primaryKeyJoinColumns();
		
		assertFalse(primaryKeyJoinColumns.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		//add an annotation to the resource model and verify the context model is updated
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.primaryKeyJoinColumns();	
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("BAR");
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.primaryKeyJoinColumns();		
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());


		joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.primaryKeyJoinColumns();		
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		attributeResource.moveAnnotation(1, 0, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.primaryKeyJoinColumns();		
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		attributeResource.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.primaryKeyJoinColumns();		
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
	
		attributeResource.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.primaryKeyJoinColumns();		
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		
		attributeResource.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		getJpaProject().synchronizeContextModel();
		primaryKeyJoinColumns = strategy.primaryKeyJoinColumns();		
		assertFalse(primaryKeyJoinColumns.hasNext());
	}
	
	public void testPrimaryKeyJoinColumnsSize() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnJoiningStrategy strategy = 
			oneToOneMapping.getRelationshipReference().getPrimaryKeyJoinColumnJoiningStrategy();
		
		assertEquals(0, strategy.primaryKeyJoinColumnsSize());
		
		strategy.addPrimaryKeyJoinColumn(0);
		assertEquals(1, strategy.primaryKeyJoinColumnsSize());
		
		strategy.removePrimaryKeyJoinColumn(0);
		assertEquals(0, strategy.primaryKeyJoinColumnsSize());
	}

	public void testAddPrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnJoiningStrategy strategy = 
			oneToOneMapping.getRelationshipReference().getPrimaryKeyJoinColumnJoiningStrategy();
		
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		Iterator<NestableAnnotation> joinColumns = attributeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertFalse(joinColumns.hasNext());
	}
	
	public void testAddPrimaryKeyJoinColumn2() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnJoiningStrategy strategy = 
			oneToOneMapping.getRelationshipReference().getPrimaryKeyJoinColumnJoiningStrategy();
		
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		Iterator<NestableAnnotation> joinColumns = attributeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) joinColumns.next()).getName());
		assertFalse(joinColumns.hasNext());
	}
	
	public void testRemovePrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnJoiningStrategy strategy = 
			oneToOneMapping.getRelationshipReference().getPrimaryKeyJoinColumnJoiningStrategy();
		
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertEquals(3, CollectionTools.size(attributeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)));

		strategy.removePrimaryKeyJoinColumn(1);
		
		Iterator<NestableAnnotation> joinColumnResources = attributeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) joinColumnResources.next()).getName());
		assertFalse(joinColumnResources.hasNext());
		
		Iterator<PrimaryKeyJoinColumn> joinColumns = strategy.primaryKeyJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());		
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		
		strategy.removePrimaryKeyJoinColumn(1);
		joinColumnResources = attributeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertFalse(joinColumnResources.hasNext());

		joinColumns = strategy.primaryKeyJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		
		strategy.removePrimaryKeyJoinColumn(0);
		joinColumnResources = attributeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertFalse(joinColumnResources.hasNext());
		joinColumns = strategy.primaryKeyJoinColumns();
		assertFalse(joinColumns.hasNext());

		assertNull(attributeResource.getAnnotation(PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME));
	}
	
	public void testMovePrimaryKeyJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnJoiningStrategy strategy = 
			oneToOneMapping.getRelationshipReference().getPrimaryKeyJoinColumnJoiningStrategy();
		
		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentAttribute attributeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		
		Iterator<NestableAnnotation> javaJoinColumns = attributeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaJoinColumns));
		
		
		strategy.movePrimaryKeyJoinColumn(2, 0);
		ListIterator<PrimaryKeyJoinColumn> primaryKeyJoinColumns = strategy.primaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = attributeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());


		strategy.movePrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = strategy.primaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = attributeResource.annotations(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) javaJoinColumns.next()).getName());
	}
	
	public void testUpdatePrimaryKeyJoinColumns() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentAttribute attributeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		PrimaryKeyJoinColumnJoiningStrategy strategy = 
			oneToOneMapping.getRelationshipReference().getPrimaryKeyJoinColumnJoiningStrategy();
		
		((PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(0, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)).setName("FOO");
		((PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(1, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)).setName("BAR");
		((PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(2, PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<PrimaryKeyJoinColumn> joinColumns = strategy.primaryKeyJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		attributeResource.moveAnnotation(2, 0, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.primaryKeyJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		attributeResource.moveAnnotation(0, 1, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.primaryKeyJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		attributeResource.removeAnnotation(1,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.primaryKeyJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		attributeResource.removeAnnotation(1,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.primaryKeyJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		attributeResource.removeAnnotation(0,  PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumns = strategy.primaryKeyJoinColumns();
		assertFalse(joinColumns.hasNext());
	}
}
