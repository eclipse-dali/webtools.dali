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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Embedded;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedId;
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JoinTable;
import org.eclipse.jpt.core.internal.resource.java.ManyToMany;
import org.eclipse.jpt.core.internal.resource.java.ManyToOne;
import org.eclipse.jpt.core.internal.resource.java.MapKey;
import org.eclipse.jpt.core.internal.resource.java.OneToMany;
import org.eclipse.jpt.core.internal.resource.java.OneToOne;
import org.eclipse.jpt.core.internal.resource.java.OrderBy;
import org.eclipse.jpt.core.internal.resource.java.Transient;
import org.eclipse.jpt.core.internal.resource.java.Version;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaOneToManyMappingTests extends ContextModelTestCase
{

	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createOneToManyAnnotation() throws Exception{
		this.createAnnotationAndMembers("OneToMany", "");		
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

	private IType createTestEntityWithOneToManyMapping() throws Exception {
		createEntityAnnotation();
		createOneToManyAnnotation();
	
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
	
	private IType createTestEntityWithValidOneToManyMapping() throws Exception {
		createEntityAnnotation();
		createOneToManyAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	private IType createTestEntityWithCollectionOneToManyMapping() throws Exception {
		createEntityAnnotation();
		createOneToManyAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private Collection addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	private IType createTestEntityWithNonCollectionOneToManyMapping() throws Exception {
		createEntityAnnotation();
		createOneToManyAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private Address addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	
	public JavaOneToManyMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IBasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IVersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Id.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ITransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IOneToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		oneToManyMapping.setOrderBy("asdf");
		oneToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(ManyToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}

	
	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation();
		
		assertNull(oneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		oneToMany.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", oneToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToMany.getTargetEntity());
	
		//set target entity to null in the resource model
		oneToMany.setTargetEntity(null);
		assertNull(oneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
	}
	
	public void testModifySpecifiedTargetEntity() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation();
		
		assertNull(oneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		oneToManyMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", oneToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToMany.getTargetEntity());
	
		//set target entity to null in the context model
		oneToManyMapping.setSpecifiedTargetEntity(null);
		assertNull(oneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation();
		
		assertNull(oneToManyMapping.getSpecifiedFetch());
		assertNull(oneToMany.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToMany.setFetch(org.eclipse.jpt.core.internal.resource.java.FetchType.EAGER);
		assertEquals(FetchType.EAGER, oneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.EAGER, oneToMany.getFetch());
	
		oneToMany.setFetch(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY, oneToMany.getFetch());

		
		//set fetch to null in the resource model
		oneToMany.setFetch(null);
		assertNull(oneToManyMapping.getSpecifiedFetch());
		assertNull(oneToMany.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation();
		
		assertNull(oneToManyMapping.getSpecifiedFetch());
		assertNull(oneToMany.getFetch());
				
		//set fetch in the context model, verify resource model updated
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(FetchType.EAGER, oneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.EAGER, oneToMany.getFetch());
	
		oneToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, oneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY, oneToMany.getFetch());

		
		//set fetch to null in the context model
		oneToManyMapping.setSpecifiedFetch(null);
		assertNull(oneToManyMapping.getSpecifiedFetch());
		assertNull(oneToMany.getFetch());
	}

	public void testUpdateMappedBy() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation();
		
		assertNull(oneToManyMapping.getMappedBy());
		assertNull(oneToMany.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		oneToMany.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", oneToManyMapping.getMappedBy());
		assertEquals("newMappedBy", oneToMany.getMappedBy());
	
		//set mappedBy to null in the resource model
		oneToMany.setMappedBy(null);
		assertNull(oneToManyMapping.getMappedBy());
		assertNull(oneToMany.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		OneToMany oneToMany = (OneToMany) attributeResource.mappingAnnotation();
		
		assertNull(oneToManyMapping.getMappedBy());
		assertNull(oneToMany.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		oneToManyMapping.setMappedBy("newTargetEntity");
		assertEquals("newTargetEntity", oneToManyMapping.getMappedBy());
		assertEquals("newTargetEntity", oneToMany.getMappedBy());
	
		//set mappedBy to null in the context model
		oneToManyMapping.setMappedBy(null);
		assertNull(oneToManyMapping.getMappedBy());
		assertNull(oneToMany.getMappedBy());
	}


	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = oneToManyMapping.candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = oneToManyMapping.candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = oneToManyMapping.candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
	}

	public void testDefaultTargetEntity() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit, default still set, handled by validation
		assertEquals(PACKAGE_NAME + ".Address", oneToManyMapping.getDefaultTargetEntity());
		
		//add targetEntity to the persistence unit
		addXmlClassRef(PACKAGE_NAME + ".Address");
		assertEquals(PACKAGE_NAME + ".Address", oneToManyMapping.getDefaultTargetEntity());

		//test default still the same when specified target entity it set
		oneToManyMapping.setSpecifiedTargetEntity("foo");
		assertEquals(PACKAGE_NAME + ".Address", oneToManyMapping.getDefaultTargetEntity());
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
		classRefs.next();
		IClassRef addressClassRef = classRefs.next();
		IJavaPersistentType addressPersistentType = addressClassRef.getJavaPersistentType();

		//test target is not an Entity, default target entity still exists, this case handled with validation
		addressPersistentType.setMappingKey(IMappingKeys.NULL_TYPE_MAPPING_KEY);
		assertEquals(PACKAGE_NAME + ".Address", oneToManyMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityCollectionType() throws Exception {
		createTestEntityWithCollectionOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();

		assertNull(oneToManyMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityNonCollectionType() throws Exception {
		createTestEntityWithNonCollectionOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();

		assertNull(oneToManyMapping.getDefaultTargetEntity());
	}
	
	public void testTargetEntity() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", oneToManyMapping.getTargetEntity());

		oneToManyMapping.setSpecifiedTargetEntity("foo");
		assertEquals("foo", oneToManyMapping.getTargetEntity());
		
		oneToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(PACKAGE_NAME + ".Address", oneToManyMapping.getTargetEntity());
	}
	
	public void testResolvedTargetEntity() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit
		assertNull(oneToManyMapping.getResolvedTargetEntity());
		
		//add targetEntity to the persistence unit, now target entity should resolve
		addXmlClassRef(PACKAGE_NAME + ".Address");
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
		classRefs.next();
		IClassRef addressClassRef = classRefs.next();
		ITypeMapping addressTypeMapping = addressClassRef.getJavaPersistentType().getMapping();
		assertEquals(addressTypeMapping, oneToManyMapping.getResolvedTargetEntity());

		//test default still the same when specified target entity it set
		oneToManyMapping.setSpecifiedTargetEntity("foo");
		assertNull(oneToManyMapping.getResolvedTargetEntity());
		
		
		oneToManyMapping.setSpecifiedTargetEntity(PACKAGE_NAME + ".Address");
		assertEquals(addressTypeMapping, oneToManyMapping.getResolvedTargetEntity());
		

		oneToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(addressTypeMapping, oneToManyMapping.getResolvedTargetEntity());
	}

	
	public void testUpdateMapKey() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertNull(oneToManyMapping.getMapKey());
		assertNull(attributeResource.annotation(MapKey.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		attributeResource.addAnnotation(MapKey.ANNOTATION_NAME);
		assertNull(oneToManyMapping.getMapKey());
		MapKey mapKey = (MapKey) attributeResource.annotation(MapKey.ANNOTATION_NAME);
		assertNotNull(mapKey);
				
		//set mapKey name in the resource model, verify context model updated
		mapKey.setName("myMapKey");
		assertEquals("myMapKey", oneToManyMapping.getMapKey());
		assertEquals("myMapKey", mapKey.getName());
		
		//set mapKey name to null in the resource model
		mapKey.setName(null);
		assertNull(oneToManyMapping.getMapKey());
		assertNull(mapKey.getName());
		
		mapKey.setName("myMapKey");
		attributeResource.removeAnnotation(MapKey.ANNOTATION_NAME);
		assertNull(oneToManyMapping.getMapKey());
		assertNull(attributeResource.annotation(MapKey.ANNOTATION_NAME));
	}
	
	public void testModifyMapKey() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertNull(oneToManyMapping.getMapKey());
		assertNull(attributeResource.annotation(MapKey.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		oneToManyMapping.setMapKey("myMapKey");
		MapKey mapKey = (MapKey) attributeResource.annotation(MapKey.ANNOTATION_NAME);
		assertEquals("myMapKey", oneToManyMapping.getMapKey());
		assertEquals("myMapKey", mapKey.getName());
	
		//set mapKey to null in the context model
		oneToManyMapping.setMapKey(null);
		assertNull(oneToManyMapping.getMapKey());
		assertNull(attributeResource.annotation(MapKey.ANNOTATION_NAME));
	}

	public void testUpdateOrderBy() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertNull(oneToManyMapping.getOrderBy());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
				
		//set orderBy in the resource model, verify context model updated
		attributeResource.addAnnotation(OrderBy.ANNOTATION_NAME);
		OrderBy orderBy = (OrderBy) attributeResource.annotation(OrderBy.ANNOTATION_NAME);
		orderBy.setValue("newOrderBy");
		assertEquals("newOrderBy", oneToManyMapping.getOrderBy());
		assertEquals("newOrderBy", orderBy.getValue());
	
		//set orderBy to null in the resource model
		attributeResource.removeAnnotation(OrderBy.ANNOTATION_NAME);
		assertNull(oneToManyMapping.getOrderBy());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testModifyOrderBy() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertNull(oneToManyMapping.getOrderBy());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
				
		//set mappedBy in the context model, verify resource model updated
		oneToManyMapping.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", oneToManyMapping.getOrderBy());
		OrderBy orderBy = (OrderBy) attributeResource.annotation(OrderBy.ANNOTATION_NAME);
		assertEquals("newOrderBy", orderBy.getValue());
	
		//set mappedBy to null in the context model
		oneToManyMapping.setOrderBy(null);
		assertNull(oneToManyMapping.getOrderBy());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testIsNoOrdering() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		assertTrue(oneToManyMapping.isNoOrdering());

		oneToManyMapping.setOrderBy("foo");
		assertFalse(oneToManyMapping.isNoOrdering());
		
		oneToManyMapping.setOrderBy(null);
		assertTrue(oneToManyMapping.isNoOrdering());
	}
	
	public void testSetNoOrdering() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
		
		assertTrue(oneToManyMapping.isNoOrdering());

		oneToManyMapping.setOrderBy("foo");
		assertFalse(oneToManyMapping.isNoOrdering());
		
		oneToManyMapping.setNoOrdering();
		assertTrue(oneToManyMapping.isNoOrdering());
		assertNull(oneToManyMapping.getOrderBy());
	}
//TODO
//	public boolean isOrderByPk() {
//		return "".equals(getOrderBy());
//	}
//
//	public void setOrderByPk() {
//		setOrderBy("");
//	}

	public void testIsCustomOrdering() throws Exception {
		createTestEntityWithOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) persistentAttribute.getMapping();
				
		assertFalse(oneToManyMapping.isCustomOrdering());

		oneToManyMapping.setOrderBy("foo");
		assertTrue(oneToManyMapping.isCustomOrdering());
		
		oneToManyMapping.setOrderBy(null);
		assertFalse(oneToManyMapping.isCustomOrdering());
	}

}
