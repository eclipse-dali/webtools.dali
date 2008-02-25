/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.resource.java.Basic;
import org.eclipse.jpt.core.resource.java.Embedded;
import org.eclipse.jpt.core.resource.java.EmbeddedId;
import org.eclipse.jpt.core.resource.java.Id;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumns;
import org.eclipse.jpt.core.resource.java.ManyToMany;
import org.eclipse.jpt.core.resource.java.ManyToOne;
import org.eclipse.jpt.core.resource.java.OneToMany;
import org.eclipse.jpt.core.resource.java.OneToOne;
import org.eclipse.jpt.core.resource.java.Transient;
import org.eclipse.jpt.core.resource.java.Version;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaOneToOneMappingTests extends ContextModelTestCase
{

	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createOneToOneAnnotation() throws Exception{
		this.createAnnotationAndMembers("OneToOne", "");		
	}

	private IType createTestTargetEntityAddress() throws Exception {
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
		return this.javaProject.createType(PACKAGE_NAME, "Address.java", sourceWriter);
	}

	
	private IType createTestEntityWithOneToOneMapping() throws Exception {
		createEntityAnnotation();
		createOneToOneAnnotation();
	
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
	
	private IType createTestEntityWithValidOneToOneMapping() throws Exception {
		createEntityAnnotation();
		createOneToOneAnnotation();
	
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
	
	private IType createTestEntityWithCollectionOneToOneMapping() throws Exception {
		createEntityAnnotation();
		createOneToOneAnnotation();
	
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
	
	private IType createTestEntityWithGenericizedCollectionOneToOneMapping() throws Exception {
		createEntityAnnotation();
		createOneToOneAnnotation();
	
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
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Id.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(ManyToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertFalse(oneToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}

	
	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation();
		
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
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation();
		
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
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation();
		
		assertNull(oneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		oneToOne.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", oneToOneMapping.getMappedBy());
		assertEquals("newMappedBy", oneToOne.getMappedBy());
	
		//set mappedBy to null in the resource model
		oneToOne.setMappedBy(null);
		assertNull(oneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation();
		
		assertNull(oneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		oneToOneMapping.setMappedBy("newTargetEntity");
		assertEquals("newTargetEntity", oneToOneMapping.getMappedBy());
		assertEquals("newTargetEntity", oneToOne.getMappedBy());
	
		//set mappedBy to null in the context model
		oneToOneMapping.setMappedBy(null);
		assertNull(oneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
	}

	public void testUpdateSpecifiedOptional() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation();
		
		assertNull(oneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOne.getOptional());
				
		//set optional in the resource model, verify context model updated
		oneToOne.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, oneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, oneToOne.getOptional());
	
		oneToOne.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, oneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, oneToOne.getOptional());

		
		//set optional to null in the resource model
		oneToOne.setOptional(null);
		assertNull(oneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOne.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation();
		
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
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation();
		
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToOne.setFetch(org.eclipse.jpt.core.resource.java.FetchType.EAGER);
		assertEquals(FetchType.EAGER, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.EAGER, oneToOne.getFetch());
	
		oneToOne.setFetch(org.eclipse.jpt.core.resource.java.FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, oneToOne.getFetch());

		
		//set fetch to null in the resource model
		oneToOne.setFetch(null);
		assertNull(oneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOne.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		OneToOne oneToOne = (OneToOne) attributeResource.mappingAnnotation();
		
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
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		
		ListIterator<JavaJoinColumn> specifiedJoinColumns = oneToOneMapping.specifiedJoinColumns();
		
		assertFalse(specifiedJoinColumns.hasNext());

		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		//add an annotation to the resource model and verify the context model is updated
		JoinColumnAnnotation joinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("FOO");
		specifiedJoinColumns = oneToOneMapping.specifiedJoinColumns();	
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		joinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("BAR");
		specifiedJoinColumns = oneToOneMapping.specifiedJoinColumns();		
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());


		joinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("BAZ");
		specifiedJoinColumns = oneToOneMapping.specifiedJoinColumns();		
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		attributeResource.move(1, 0, JPA.JOIN_COLUMNS);
		specifiedJoinColumns = oneToOneMapping.specifiedJoinColumns();		
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		attributeResource.removeAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		specifiedJoinColumns = oneToOneMapping.specifiedJoinColumns();		
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());
	
		attributeResource.removeAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		specifiedJoinColumns = oneToOneMapping.specifiedJoinColumns();		
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		
		attributeResource.removeAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		specifiedJoinColumns = oneToOneMapping.specifiedJoinColumns();		
		assertFalse(specifiedJoinColumns.hasNext());
	}
	
	public void testGetDefaultJoin() {
		//TODO
	}
	
	public void testSpecifiedJoinColumnsSize() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
		
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertEquals(1, oneToOneMapping.specifiedJoinColumnsSize());
		
		oneToOneMapping.removeSpecifiedJoinColumn(0);
		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
	}

	public void testJoinColumnsSize() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertEquals(1, oneToOneMapping.joinColumnsSize());
		
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertEquals(1, oneToOneMapping.joinColumnsSize());
		
		oneToOneMapping.addSpecifiedJoinColumn(0);
		assertEquals(2, oneToOneMapping.joinColumnsSize());

		oneToOneMapping.removeSpecifiedJoinColumn(0);
		oneToOneMapping.removeSpecifiedJoinColumn(0);
		assertEquals(1, oneToOneMapping.joinColumnsSize());
		
		//if non-owning side of the relationship then no default join column
		oneToOneMapping.setMappedBy("foo");
		assertEquals(0, oneToOneMapping.joinColumnsSize());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		oneToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		oneToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("BAR");
		oneToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		Iterator<JavaResourceNode> joinColumns = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumns.next()).getName());
		assertFalse(joinColumns.hasNext());
	}
	
	public void testAddSpecifiedJoinColumn2() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		oneToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		oneToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		oneToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		Iterator<JavaResourceNode> joinColumns = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("BAR", ((JoinColumnAnnotation) joinColumns.next()).getName());
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumns.next()).getName());
		assertFalse(joinColumns.hasNext());
	}
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		oneToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		oneToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		oneToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		
		assertEquals(3, CollectionTools.size(attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME)));

		oneToOneMapping.removeSpecifiedJoinColumn(1);
		
		Iterator<JavaResourceNode> joinColumnResources = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertEquals("BAZ", ((JoinColumnAnnotation) joinColumnResources.next()).getName());
		assertFalse(joinColumnResources.hasNext());
		
		Iterator<JoinColumn> joinColumns = oneToOneMapping.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());		
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		
		oneToOneMapping.removeSpecifiedJoinColumn(1);
		joinColumnResources = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals("FOO", ((JoinColumnAnnotation) joinColumnResources.next()).getName());		
		assertFalse(joinColumnResources.hasNext());

		joinColumns = oneToOneMapping.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		
		oneToOneMapping.removeSpecifiedJoinColumn(0);
		joinColumnResources = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertFalse(joinColumnResources.hasNext());
		joinColumns = oneToOneMapping.specifiedJoinColumns();
		assertFalse(joinColumns.hasNext());

		assertNull(attributeResource.annotation(JoinColumns.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		oneToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		oneToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		oneToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaResourcePersistentAttribute attributeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME).attributes().next();
		
		ListIterator<JoinColumnAnnotation> javaJoinColumns = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaJoinColumns));
		
		
		oneToOneMapping.moveSpecifiedJoinColumn(2, 0);
		ListIterator<JoinColumn> primaryKeyJoinColumns = oneToOneMapping.specifiedJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals("BAR", javaJoinColumns.next().getName());
		assertEquals("BAZ", javaJoinColumns.next().getName());
		assertEquals("FOO", javaJoinColumns.next().getName());


		oneToOneMapping.moveSpecifiedJoinColumn(0, 1);
		primaryKeyJoinColumns = oneToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = attributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals("BAZ", javaJoinColumns.next().getName());
		assertEquals("BAR", javaJoinColumns.next().getName());
		assertEquals("FOO", javaJoinColumns.next().getName());
	}
	
	public void testUpdateSpecifiedJoinColumns() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
		JavaResourcePersistentAttribute attributeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME).attributes().next();
	
		((JoinColumnAnnotation) attributeResource.addAnnotation(0, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME)).setName("FOO");
		((JoinColumnAnnotation) attributeResource.addAnnotation(1, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME)).setName("BAR");
		((JoinColumnAnnotation) attributeResource.addAnnotation(2, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<JoinColumn> joinColumns = oneToOneMapping.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		attributeResource.move(2, 0, JoinColumns.ANNOTATION_NAME);
		joinColumns = oneToOneMapping.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		attributeResource.move(0, 1, JoinColumns.ANNOTATION_NAME);
		joinColumns = oneToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		attributeResource.removeAnnotation(1,  JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		joinColumns = oneToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		attributeResource.removeAnnotation(1,  JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		joinColumns = oneToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		attributeResource.removeAnnotation(0,  JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		joinColumns = oneToOneMapping.specifiedJoinColumns();
		assertFalse(joinColumns.hasNext());
	}
	
	public void testJoinColumnIsVirtual() throws Exception {
		createTestEntityWithOneToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertTrue(oneToOneMapping.getDefaultJoinColumn().isVirtual());

		oneToOneMapping.addSpecifiedJoinColumn(0);
		JoinColumn specifiedJoinColumn = oneToOneMapping.specifiedJoinColumns().next();
		assertFalse(specifiedJoinColumn.isVirtual());
		
		assertNull(oneToOneMapping.getDefaultJoinColumn());
	}

	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = oneToOneMapping.candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		attributeNames = oneToOneMapping.candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		attributeNames = oneToOneMapping.candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
	}

	public void testDefaultTargetEntity() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit, default still set, handled by validation
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());
		
		//add targetEntity to the persistence unit
		addXmlClassRef(PACKAGE_NAME + ".Address");
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());

		//test default still the same when specified target entity it set
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getDefaultTargetEntity());
		
		ListIterator<ClassRef> classRefs = persistenceUnit().specifiedClassRefs();
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
	
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertNull(oneToOneMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityGenericizedCollectionType() throws Exception {
		createTestEntityWithGenericizedCollectionOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertNull(oneToOneMapping.getDefaultTargetEntity());
	}
	
	public void testTargetEntity() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getTargetEntity());

		oneToOneMapping.setSpecifiedTargetEntity("foo");
		assertEquals("foo", oneToOneMapping.getTargetEntity());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		assertEquals(PACKAGE_NAME + ".Address", oneToOneMapping.getTargetEntity());
	}
	
	public void testResolvedTargetEntity() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit
		assertNull(oneToOneMapping.getResolvedTargetEntity());
		
		//add targetEntity to the persistence unit, now target entity should resolve
		addXmlClassRef(PACKAGE_NAME + ".Address");
		ListIterator<ClassRef> classRefs = persistenceUnit().specifiedClassRefs();
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

}
