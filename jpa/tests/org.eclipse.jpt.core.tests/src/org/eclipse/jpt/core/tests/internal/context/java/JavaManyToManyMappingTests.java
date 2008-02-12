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

public class JavaManyToManyMappingTests extends ContextModelTestCase
{

	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createManyToManyAnnotation() throws Exception{
		this.createAnnotationAndMembers("ManyToMany", "");		
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

	private IType createTestEntityWithManyToManyMapping() throws Exception {
		createEntityAnnotation();
		createManyToManyAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany").append(CR);
			}
		});
	}
	
	private IType createTestEntityWithValidManyToManyMapping() throws Exception {
		createEntityAnnotation();
		createManyToManyAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
				sb.append("    private Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	private IType createTestEntityWithCollectionManyToManyMapping() throws Exception {
		createEntityAnnotation();
		createManyToManyAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
				sb.append("    private Collection addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	private IType createTestEntityWithNonCollectionManyToManyMapping() throws Exception {
		createEntityAnnotation();
		createManyToManyAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
				sb.append("    private Address addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	public JavaManyToManyMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IBasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IVersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Id.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ITransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IOneToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IOneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.setOrderBy("asdf");
		manyToManyMapping.getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(ManyToOne.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}

	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation();
		
		assertNull(manyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		manyToMany.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", manyToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToMany.getTargetEntity());
	
		//set target entity to null in the resource model
		manyToMany.setTargetEntity(null);
		assertNull(manyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
	}
	
	public void testModifySpecifiedTargetEntity() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation();
		
		assertNull(manyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		manyToManyMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", manyToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToMany.getTargetEntity());
	
		//set target entity to null in the context model
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertNull(manyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation();
		
		assertNull(manyToManyMapping.getSpecifiedFetch());
		assertNull(manyToMany.getFetch());
				
		//set fetch in the resource model, verify context model updated
		manyToMany.setFetch(org.eclipse.jpt.core.internal.resource.java.FetchType.EAGER);
		assertEquals(FetchType.EAGER, manyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.EAGER, manyToMany.getFetch());
	
		manyToMany.setFetch(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY);
		assertEquals(FetchType.LAZY, manyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY, manyToMany.getFetch());

		
		//set fetch to null in the resource model
		manyToMany.setFetch(null);
		assertNull(manyToManyMapping.getSpecifiedFetch());
		assertNull(manyToMany.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation();
		
		assertNull(manyToManyMapping.getSpecifiedFetch());
		assertNull(manyToMany.getFetch());
				
		//set fetch in the context model, verify resource model updated
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(FetchType.EAGER, manyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.EAGER, manyToMany.getFetch());
	
		manyToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, manyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY, manyToMany.getFetch());

		
		//set fetch to null in the context model
		manyToManyMapping.setSpecifiedFetch(null);
		assertNull(manyToManyMapping.getSpecifiedFetch());
		assertNull(manyToMany.getFetch());
	}

	public void testUpdateMappedBy() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation();
		
		assertNull(manyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		manyToMany.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", manyToManyMapping.getMappedBy());
		assertEquals("newMappedBy", manyToMany.getMappedBy());
	
		//set mappedBy to null in the resource model
		manyToMany.setMappedBy(null);
		assertNull(manyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToMany manyToMany = (ManyToMany) attributeResource.mappingAnnotation();
		
		assertNull(manyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		manyToManyMapping.setMappedBy("newTargetEntity");
		assertEquals("newTargetEntity", manyToManyMapping.getMappedBy());
		assertEquals("newTargetEntity", manyToMany.getMappedBy());
	
		//set mappedBy to null in the context model
		manyToManyMapping.setMappedBy(null);
		assertNull(manyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
	}


	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = manyToManyMapping.candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = manyToManyMapping.candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = manyToManyMapping.candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
	}

	public void testDefaultTargetEntity() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit, default still set, handled by validation
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getDefaultTargetEntity());
		
		//add targetEntity to the persistence unit
		addXmlClassRef(PACKAGE_NAME + ".Address");
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getDefaultTargetEntity());

		//test default still the same when specified target entity it set
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getDefaultTargetEntity());
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		IClassRef addressClassRef = classRefs.next();
		IJavaPersistentType addressPersistentType = addressClassRef.getJavaPersistentType();

		//test target is not an Entity, default target entity still exists, this case handled with validation
		addressPersistentType.setMappingKey(IMappingKeys.NULL_TYPE_MAPPING_KEY);
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityCollectionType() throws Exception {
		createTestEntityWithCollectionManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();

		assertNull(manyToManyMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityNonCollectionType() throws Exception {
		createTestEntityWithNonCollectionManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();

		assertNull(manyToManyMapping.getDefaultTargetEntity());
	}
	
	public void testTargetEntity() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getTargetEntity());

		manyToManyMapping.setSpecifiedTargetEntity("foo");
		assertEquals("foo", manyToManyMapping.getTargetEntity());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getTargetEntity());
	}
	
	public void testResolvedTargetEntity() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit
		assertNull(manyToManyMapping.getResolvedTargetEntity());
		
		//add targetEntity to the persistence unit, now target entity should resolve
		addXmlClassRef(PACKAGE_NAME + ".Address");
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		IClassRef addressClassRef = classRefs.next();
		ITypeMapping addressTypeMapping = addressClassRef.getJavaPersistentType().getMapping();
		assertEquals(addressTypeMapping, manyToManyMapping.getResolvedTargetEntity());

		//test default still the same when specified target entity it set
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		assertNull(manyToManyMapping.getResolvedTargetEntity());
		
		
		manyToManyMapping.setSpecifiedTargetEntity(PACKAGE_NAME + ".Address");
		assertEquals(addressTypeMapping, manyToManyMapping.getResolvedTargetEntity());
		

		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(addressTypeMapping, manyToManyMapping.getResolvedTargetEntity());
	}

	
	public void testUpdateMapKey() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertNull(manyToManyMapping.getMapKey());
		assertNull(attributeResource.annotation(MapKey.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		attributeResource.addAnnotation(MapKey.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getMapKey());
		MapKey mapKey = (MapKey) attributeResource.annotation(MapKey.ANNOTATION_NAME);
		assertNotNull(mapKey);
				
		//set mapKey name in the resource model, verify context model updated
		mapKey.setName("myMapKey");
		assertEquals("myMapKey", manyToManyMapping.getMapKey());
		assertEquals("myMapKey", mapKey.getName());
		
		//set mapKey name to null in the resource model
		mapKey.setName(null);
		assertNull(manyToManyMapping.getMapKey());
		assertNull(mapKey.getName());
		
		mapKey.setName("myMapKey");
		attributeResource.removeAnnotation(MapKey.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getMapKey());
		assertNull(attributeResource.annotation(MapKey.ANNOTATION_NAME));
	}
	
	public void testModifyMapKey() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertNull(manyToManyMapping.getMapKey());
		assertNull(attributeResource.annotation(MapKey.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		manyToManyMapping.setMapKey("myMapKey");
		MapKey mapKey = (MapKey) attributeResource.annotation(MapKey.ANNOTATION_NAME);
		assertEquals("myMapKey", manyToManyMapping.getMapKey());
		assertEquals("myMapKey", mapKey.getName());
	
		//set mapKey to null in the context model
		manyToManyMapping.setMapKey(null);
		assertNull(manyToManyMapping.getMapKey());
		assertNull(attributeResource.annotation(MapKey.ANNOTATION_NAME));
	}

	public void testUpdateOrderBy() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertNull(manyToManyMapping.getOrderBy());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
				
		//set orderBy in the resource model, verify context model updated
		attributeResource.addAnnotation(OrderBy.ANNOTATION_NAME);
		OrderBy orderBy = (OrderBy) attributeResource.annotation(OrderBy.ANNOTATION_NAME);
		orderBy.setValue("newOrderBy");
		assertEquals("newOrderBy", manyToManyMapping.getOrderBy());
		assertEquals("newOrderBy", orderBy.getValue());
	
		//set orderBy to null in the resource model
		attributeResource.removeAnnotation(OrderBy.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getOrderBy());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testModifyOrderBy() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertNull(manyToManyMapping.getOrderBy());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
				
		//set mappedBy in the context model, verify resource model updated
		manyToManyMapping.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", manyToManyMapping.getOrderBy());
		OrderBy orderBy = (OrderBy) attributeResource.annotation(OrderBy.ANNOTATION_NAME);
		assertEquals("newOrderBy", orderBy.getValue());
	
		//set mappedBy to null in the context model
		manyToManyMapping.setOrderBy(null);
		assertNull(manyToManyMapping.getOrderBy());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testUpdateNoOrdering()  throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertTrue(manyToManyMapping.isNoOrdering());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
				
		//set orderBy in the resource model, verify context model updated
		attributeResource.addAnnotation(OrderBy.ANNOTATION_NAME);
		assertFalse(manyToManyMapping.isNoOrdering());
		
		OrderBy orderBy = (OrderBy) attributeResource.annotation(OrderBy.ANNOTATION_NAME);
		orderBy.setValue("newOrderBy");
		assertFalse(manyToManyMapping.isNoOrdering());
	
		//set orderBy to null in the resource model
		attributeResource.removeAnnotation(OrderBy.ANNOTATION_NAME);
		assertTrue(manyToManyMapping.isNoOrdering());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}
	
	public void testModifyNoOrdering() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertTrue(manyToManyMapping.isNoOrdering());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
				
//		manyToManyMapping.setNoOrdering(false); //this does nothing
//		//set mappedBy in the context model, verify resource model updated
//		manyToManyMapping.setOrderBy("newOrderBy");
//		assertEquals("newOrderBy", manyToManyMapping.getOrderBy());
//		OrderBy orderBy = (OrderBy) attributeResource.annotation(OrderBy.ANNOTATION_NAME);
//		assertEquals("newOrderBy", orderBy.getValue());
//	
//		//set mappedBy to null in the context model
//		manyToManyMapping.setOrderBy(null);
//		assertNull(manyToManyMapping.getOrderBy());
//		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));		
	}
	
	public void testUpdatePkOrdering() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertFalse(manyToManyMapping.isPkOrdering());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
				
		//set orderBy in the resource model, verify context model updated
		attributeResource.addAnnotation(OrderBy.ANNOTATION_NAME);
		assertTrue(manyToManyMapping.isPkOrdering());
		
		OrderBy orderBy = (OrderBy) attributeResource.annotation(OrderBy.ANNOTATION_NAME);
		orderBy.setValue("newOrderBy");
		assertFalse(manyToManyMapping.isPkOrdering());
	
		//set orderBy to null in the resource model
		attributeResource.removeAnnotation(OrderBy.ANNOTATION_NAME);
		assertFalse(manyToManyMapping.isPkOrdering());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}

	public void testUpdateCustomOrdering()  throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		
		assertFalse(manyToManyMapping.isCustomOrdering());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
				
		//set orderBy in the resource model, verify context model updated
		attributeResource.addAnnotation(OrderBy.ANNOTATION_NAME);
		assertFalse(manyToManyMapping.isCustomOrdering());
		
		OrderBy orderBy = (OrderBy) attributeResource.annotation(OrderBy.ANNOTATION_NAME);
		orderBy.setValue("newOrderBy");
		assertTrue(manyToManyMapping.isCustomOrdering());
	
		//set orderBy to null in the resource model
		attributeResource.removeAnnotation(OrderBy.ANNOTATION_NAME);
		assertFalse(manyToManyMapping.isCustomOrdering());
		assertNull(attributeResource.annotation(OrderBy.ANNOTATION_NAME));
	}

}
