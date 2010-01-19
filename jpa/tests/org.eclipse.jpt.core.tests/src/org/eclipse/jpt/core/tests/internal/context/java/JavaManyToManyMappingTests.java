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
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.OrderByAnnotation;
import org.eclipse.jpt.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JavaManyToManyMappingTests extends ContextModelTestCase
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

	private ICompilationUnit createTestEntityWithManyToManyMapping() throws Exception {
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
	
	private ICompilationUnit createTestEntityWithValidManyToManyMapping() throws Exception {
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
	private ICompilationUnit createTestEntityWithCollectionManyToManyMapping() throws Exception {
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
	private ICompilationUnit createTestEntityWithNonCollectionManyToManyMapping() throws Exception {
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

	private void createTestDepartment() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ").append(JPA.ENTITY).append(";");
					sb.append(CR);
					sb.append("import ").append(JPA.ID).append(";");
					sb.append(CR);
					sb.append("import java.util.Map;");
					sb.append(CR);
					sb.append("import ").append(JPA.MANY_TO_MANY).append(";");
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("Department").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);
				sb.append("    private Map<Integer, Employee> employees;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Department.java", sourceWriter);
	}

	private void createTestEmployee() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ").append(JPA.ENTITY).append(";");
					sb.append(CR);
					sb.append("import ").append(JPA.ID).append(";");
					sb.append(CR);
			sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("Employee").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int empId;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Employee.java", sourceWriter);
	}

	public JavaManyToManyMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("FOO");
		assertFalse(manyToManyMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}

	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME);
		
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME);
		
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME);
		
		assertNull(manyToManyMapping.getSpecifiedFetch());
		assertNull(manyToMany.getFetch());
				
		//set fetch in the resource model, verify context model updated
		manyToMany.setFetch(org.eclipse.jpt.core.resource.java.FetchType.EAGER);
		getJpaProject().synchronizeContextModel();
		assertEquals(FetchType.EAGER, manyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.EAGER, manyToMany.getFetch());
	
		manyToMany.setFetch(org.eclipse.jpt.core.resource.java.FetchType.LAZY);
		getJpaProject().synchronizeContextModel();
		assertEquals(FetchType.LAZY, manyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, manyToMany.getFetch());

		
		//set fetch to null in the resource model
		manyToMany.setFetch(null);
		getJpaProject().synchronizeContextModel();
		assertNull(manyToManyMapping.getSpecifiedFetch());
		assertNull(manyToMany.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME);
		
		assertNull(manyToManyMapping.getSpecifiedFetch());
		assertNull(manyToMany.getFetch());
				
		//set fetch in the context model, verify resource model updated
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(FetchType.EAGER, manyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.EAGER, manyToMany.getFetch());
		
		manyToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, manyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, manyToMany.getFetch());
		
		//set fetch to null in the context model
		manyToManyMapping.setSpecifiedFetch(null);
		assertNull(manyToManyMapping.getSpecifiedFetch());
		assertNull(manyToMany.getFetch());
	}

	public void testUpdateMappedBy() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME);
		
		assertNull(manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());
		assertNull(manyToMany.getMappedBy());
				
		//set mappedByJoiningStrategy in the resource model, verify context model updated
		manyToMany.setMappedBy("newMappedBy");
		getJpaProject().synchronizeContextModel();
		assertEquals("newMappedBy", manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());
		assertEquals("newMappedBy", manyToMany.getMappedBy());
	
		//set mappedByJoiningStrategy to null in the resource model
		manyToMany.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertNull(manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());
		assertNull(manyToMany.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ManyToManyAnnotation manyToMany = (ManyToManyAnnotation) attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME);
		
		assertNull(manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());
		assertNull(manyToMany.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("newTargetEntity");
		assertEquals("newTargetEntity", manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());
		assertEquals("newTargetEntity", manyToMany.getMappedBy());
	
		//set mappedBy to null in the context model
		manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute(null);
		assertNull(manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());
		assertNull(manyToMany.getMappedBy());
	}


	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());

		AttributeMapping stateFooMapping = manyToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertNull(stateFooMapping);
	}

	public void testDefaultTargetEntity() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit, default still set, handled by validation
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getDefaultTargetEntity());
		
		//add targetEntity to the persistence unit
		addXmlClassRef(PACKAGE_NAME + ".Address");
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getDefaultTargetEntity());

		//test default still the same when specified target entity it set
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getDefaultTargetEntity());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		ClassRef addressClassRef = classRefs.next();
		JavaPersistentType addressPersistentType = addressClassRef.getJavaPersistentType();

		//test target is not an Entity, default target entity still exists, this case handled with validation
		addressPersistentType.setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityCollectionType() throws Exception {
		createTestEntityWithCollectionManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		assertNull(manyToManyMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityNonCollectionType() throws Exception {
		createTestEntityWithNonCollectionManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		assertNull(manyToManyMapping.getDefaultTargetEntity());
	}
	
	public void testTargetEntity() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getTargetEntity());

		manyToManyMapping.setSpecifiedTargetEntity("foo");
		assertEquals("foo", manyToManyMapping.getTargetEntity());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(PACKAGE_NAME + ".Address", manyToManyMapping.getTargetEntity());
	}
	
	public void testResolvedTargetEntity() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit
		assertNull(manyToManyMapping.getResolvedTargetEntity());
		
		//add targetEntity to the persistence unit, now target entity should resolve
		createTestTargetEntityAddress();
		addXmlClassRef(PACKAGE_NAME + ".Address");
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		ClassRef addressClassRef = classRefs.next();
		TypeMapping addressTypeMapping = addressClassRef.getJavaPersistentType().getMapping();
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		attributeResource.addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		MapKeyAnnotation mapKey = (MapKeyAnnotation) attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNotNull(mapKey);
				
		//set mapKey name in the resource model, verify context model updated
		mapKey.setName("myMapKey");
		getJpaProject().synchronizeContextModel();
		assertEquals("myMapKey", manyToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", mapKey.getName());
		
		//set mapKey name to null in the resource model
		mapKey.setName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(mapKey.getName());
		
		mapKey.setName("myMapKey");
		attributeResource.removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifyMapKey() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		manyToManyMapping.setSpecifiedMapKey("myMapKey");
		MapKeyAnnotation mapKey = (MapKeyAnnotation) attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertEquals("myMapKey", manyToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", mapKey.getName());
	
		//set mapKey to null in the context model
		manyToManyMapping.setSpecifiedMapKey(null);
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
	}

	public void testUpdateOrderBy() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertNull(manyToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
				
		//set orderBy in the resource model, verify context model updated
		attributeResource.addAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		OrderByAnnotation orderBy = (OrderByAnnotation) attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		orderBy.setValue("newOrderBy");
		getJpaProject().synchronizeContextModel();
		assertEquals("newOrderBy", manyToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertEquals("newOrderBy", orderBy.getValue());
	
		//set orderBy to null in the resource model
		attributeResource.removeAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(manyToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifyOrderBy() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertNull(manyToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
				
		//set mappedBy in the context model, verify resource model updated
		manyToManyMapping.getOrderable().setSpecifiedOrderBy("newOrderBy");
		assertEquals("newOrderBy", manyToManyMapping.getOrderable().getSpecifiedOrderBy());
		OrderByAnnotation orderBy = (OrderByAnnotation) attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		assertEquals("newOrderBy", orderBy.getValue());
	
		//set mappedBy to null in the context model
		manyToManyMapping.getOrderable().setSpecifiedOrderBy(null);
		assertNull(manyToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testUpdateNoOrdering()  throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertTrue(manyToManyMapping.getOrderable().isNoOrdering());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
				
		//set orderBy in the resource model, verify context model updated
		attributeResource.addAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(manyToManyMapping.getOrderable().isNoOrdering());
		
		OrderByAnnotation orderBy = (OrderByAnnotation) attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		orderBy.setValue("newOrderBy");
		getJpaProject().synchronizeContextModel();
		assertFalse(manyToManyMapping.getOrderable().isNoOrdering());
	
		//set orderBy to null in the resource model
		attributeResource.removeAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertTrue(manyToManyMapping.getOrderable().isNoOrdering());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifyNoOrdering() throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertTrue(manyToManyMapping.getOrderable().isNoOrdering());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
				
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertFalse(manyToManyMapping.getOrderable().isPkOrdering());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
				
		//set orderBy in the resource model, verify context model updated
		attributeResource.addAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertTrue(manyToManyMapping.getOrderable().isPkOrdering());
		
		OrderByAnnotation orderBy = (OrderByAnnotation) attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		orderBy.setValue("newOrderBy");
		getJpaProject().synchronizeContextModel();
		assertFalse(manyToManyMapping.getOrderable().isPkOrdering());
	
		//set orderBy to null in the resource model
		attributeResource.removeAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(manyToManyMapping.getOrderable().isPkOrdering());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}

	public void testUpdateCustomOrdering()  throws Exception {
		createTestEntityWithManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertFalse(manyToManyMapping.getOrderable().isCustomOrdering());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
				
		//set orderBy in the resource model, verify context model updated
		attributeResource.addAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		assertFalse(manyToManyMapping.getOrderable().isCustomOrdering());
		
		OrderByAnnotation orderBy = (OrderByAnnotation) attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		orderBy.setValue("newOrderBy");
		getJpaProject().synchronizeContextModel();
		assertTrue(manyToManyMapping.getOrderable().isCustomOrdering());
	
		//set orderBy to null in the resource model
		attributeResource.removeAnnotation(OrderByAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(manyToManyMapping.getOrderable().isCustomOrdering());
		assertNull(attributeResource.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
	}
	
	public void testDefaultTargetEntityForMap() throws Exception {
		createTestEmployee();
		createTestDepartment();
		addXmlClassRef(PACKAGE_NAME + ".Department");
		addXmlClassRef(PACKAGE_NAME + ".Employee");
		
		JavaPersistentType departmentPersistentType = getJavaPersistentType();
		ManyToManyMapping employeesMapping = (ManyToManyMapping) departmentPersistentType.getAttributeNamed("employees").getSpecifiedMapping();
		assertEquals(PACKAGE_NAME + ".Employee", employeesMapping.getTargetEntity());
	}
}
