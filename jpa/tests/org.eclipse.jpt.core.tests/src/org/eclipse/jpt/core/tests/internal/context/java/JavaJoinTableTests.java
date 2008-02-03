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
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.core.internal.context.java.IJavaManyToManyMapping;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JoinTable;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaJoinTableTests extends ContextModelTestCase
{
	public JavaJoinTableTests(String name) {
		super(name);
	}
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createManyToManyAnnotation() throws Exception{
		this.createAnnotationAndMembers("ManyToMany", "");		
	}
	
	private void createJoinTableAnnotation() throws Exception{
		//TODO
		this.createAnnotationAndMembers("JoinTable", 
			"String name() default \"\"; " +
			"String catalog() default \"\"; " +
			"String schema() default \"\";");		
	}
		

	private IType createTestEntityWithManyToMany() throws Exception {
		createEntityAnnotation();
		createManyToManyAnnotation();
		createJoinTableAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany").append(CR);
			}
		});
	}


	public void testUpdateSpecifiedName() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		JoinTable javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedName());
		assertNull(javaJoinTable);
		
		
		//set name in the resource model, verify context model updated
		attributeResource.addAnnotation(JoinTable.ANNOTATION_NAME);
		javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		javaJoinTable.setName("FOO");
		assertEquals("FOO", joinTable.getSpecifiedName());
		assertEquals("FOO", javaJoinTable.getName());
	
		//set name to null in the resource model
		javaJoinTable.setName(null);
		assertNull(joinTable.getSpecifiedName());
		assertNull(javaJoinTable.getName());
		
		javaJoinTable.setName("FOO");
		assertEquals("FOO", joinTable.getSpecifiedName());
		assertEquals("FOO", javaJoinTable.getName());

		attributeResource.removeAnnotation(JoinTable.ANNOTATION_NAME);
		assertNull(joinTable.getSpecifiedName());
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedName() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		JoinTable javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedName());
		assertNull(javaJoinTable);
	
		//set name in the context model, verify resource model modified
		joinTable.setSpecifiedName("foo");
		javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		assertEquals("foo", joinTable.getSpecifiedName());
		assertEquals("foo", javaJoinTable.getName());
		
		//set name to null in the context model
		joinTable.setSpecifiedName(null);
		assertNull(joinTable.getSpecifiedName());
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
	}
	
////	public void testUpdateDefaultNameFromJavaTable() throws Exception {
////		createTestEntity();
////		
////		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
////		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
////		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
////		
////		xmlEntity.javaEntity().getTable().setSpecifiedName("Foo");
////		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
////		
////		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
////		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
////
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
////		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
////		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
////	
////		xmlEntity.setSpecifiedMetadataComplete(null);
////		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
////		
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
////		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
////		
////		xmlEntity.getTable().setSpecifiedName("Bar");
////		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
////	}
////	
////	public void testUpdateDefaultNameNoJava() throws Exception {
////		createTestEntity();
////		
////		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
////		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
////		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
////	}
////	
////	public void testUpdateDefaultNameFromParent() throws Exception {
////		createTestEntity();
////		createTestSubType();
////		
////		XmlPersistentType parentXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
////		XmlPersistentType childXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
////		XmlEntity parentXmlEntity = (XmlEntity) parentXmlPersistentType.getMapping();
////		XmlEntity childXmlEntity = (XmlEntity) childXmlPersistentType.getMapping();
////		
////		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
////		assertEquals(TYPE_NAME, childXmlEntity.getTable().getDefaultName());
////		
////		parentXmlEntity.getTable().setSpecifiedName("FOO");
////		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
////		assertEquals("FOO", childXmlEntity.getTable().getDefaultName());
////
////		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
////		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
////		assertEquals("AnnotationTestTypeChild", childXmlEntity.getTable().getDefaultName());
////	}

	public void testUpdateSpecifiedSchema() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		JoinTable javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(javaJoinTable);
		
		
		//set schema in the resource model, verify context model updated
		attributeResource.addAnnotation(JoinTable.ANNOTATION_NAME);
		javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		javaJoinTable.setSchema("FOO");
		assertEquals("FOO", joinTable.getSpecifiedSchema());
		assertEquals("FOO", javaJoinTable.getSchema());
	
		//set schema to null in the resource model
		javaJoinTable.setSchema(null);
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(javaJoinTable.getSchema());
		
		javaJoinTable.setSchema("FOO");
		assertEquals("FOO", joinTable.getSpecifiedSchema());
		assertEquals("FOO", javaJoinTable.getSchema());

		attributeResource.removeAnnotation(JoinTable.ANNOTATION_NAME);
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		JoinTable javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(javaJoinTable);
	
		//set schema in the context model, verify resource model modified
		joinTable.setSpecifiedSchema("foo");
		javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		assertEquals("foo", joinTable.getSpecifiedSchema());
		assertEquals("foo", javaJoinTable.getSchema());
		
		//set schema to null in the context model
		joinTable.setSpecifiedSchema(null);
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
	}
	
////	public void testUpdateDefaultSchemaFromJavaTable() throws Exception {
////		createTestEntity();
////		
////		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
////		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
////		assertNull(xmlEntity.getTable().getDefaultSchema());
////		
////		xmlEntity.javaEntity().getTable().setSpecifiedSchema("Foo");
////		assertEquals("Foo", xmlEntity.getTable().getDefaultSchema());
////		
////		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
////		assertNull(xmlEntity.getTable().getDefaultSchema());
////
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
////		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
////		assertNull(xmlEntity.getTable().getDefaultSchema());
////	
////		xmlEntity.setSpecifiedMetadataComplete(null);
////		assertNull(xmlEntity.getTable().getDefaultSchema());
////		
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
////		assertEquals("Foo", xmlEntity.getTable().getDefaultSchema());
////		
////		xmlEntity.getTable().setSpecifiedName("Bar");
////		assertNull(xmlEntity.getTable().getDefaultSchema());
////	}
////	
////	public void testUpdateDefaultSchemaNoJava() throws Exception {
////		createTestEntity();
////		
////		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
////		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
////		assertNull(xmlEntity.getTable().getDefaultSchema());
////	}
////	
////	public void testUpdateDefaultSchemaFromParent() throws Exception {
////		createTestEntity();
////		createTestSubType();
////		
////		XmlPersistentType parentXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
////		XmlPersistentType childXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
////		XmlEntity parentXmlEntity = (XmlEntity) parentXmlPersistentType.getMapping();
////		XmlEntity childXmlEntity = (XmlEntity) childXmlPersistentType.getMapping();
////		
////		assertNull(parentXmlEntity.getTable().getDefaultSchema());
////		assertNull(childXmlEntity.getTable().getDefaultSchema());
////		
////		parentXmlEntity.getTable().setSpecifiedSchema("FOO");
////		assertNull(parentXmlEntity.getTable().getDefaultSchema());
////		assertEquals("FOO", childXmlEntity.getTable().getDefaultSchema());
////
////		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
////		assertNull(parentXmlEntity.getTable().getDefaultSchema());
////		assertNull(childXmlEntity.getTable().getDefaultSchema());
////	}
////	
////	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
////		createTestEntity();
////		
////		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
////		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
////		assertNull(xmlEntity.getTable().getDefaultSchema());
////		
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("FOO");
////		assertEquals("FOO", xmlEntity.getTable().getDefaultSchema());
////		
////		xmlEntity.entityMappings().setSpecifiedSchema("BAR");
////		assertEquals("BAR", xmlEntity.getTable().getDefaultSchema());
////		
////		xmlEntity.javaEntity().getTable().setSpecifiedSchema("JAVA_SCHEMA");
////		assertEquals("JAVA_SCHEMA", xmlEntity.getTable().getDefaultSchema());
////		
////		xmlEntity.getTable().setSpecifiedName("BLAH");
////		//xml entity now has a table element so default schema is not taken from java
////		assertEquals("BAR", xmlEntity.getTable().getDefaultSchema());
////
////		
////		xmlEntity.entityMappings().setSpecifiedSchema(null);
////		assertEquals("FOO", xmlEntity.getTable().getDefaultSchema());
////
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema(null);
////		assertNull(xmlEntity.getTable().getDefaultSchema());
////		
////		xmlEntity.getTable().setSpecifiedName(null);
////		assertEquals("JAVA_SCHEMA", xmlEntity.getTable().getDefaultSchema());
////	}

	public void testUpdateSpecifiedCatalog() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		JoinTable javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(javaJoinTable);
		
		
		//set catalog in the resource model, verify context model updated
		attributeResource.addAnnotation(JoinTable.ANNOTATION_NAME);
		javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		javaJoinTable.setCatalog("FOO");
		assertEquals("FOO", joinTable.getSpecifiedCatalog());
		assertEquals("FOO", javaJoinTable.getCatalog());
	
		//set catalog to null in the resource model
		javaJoinTable.setCatalog(null);
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(javaJoinTable.getCatalog());
		
		javaJoinTable.setCatalog("FOO");
		assertEquals("FOO", joinTable.getSpecifiedCatalog());
		assertEquals("FOO", javaJoinTable.getCatalog());

		attributeResource.removeAnnotation(JoinTable.ANNOTATION_NAME);
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		JoinTable javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(javaJoinTable);
	
		//set catalog in the context model, verify resource model modified
		joinTable.setSpecifiedCatalog("foo");
		javaJoinTable = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		assertEquals("foo", joinTable.getSpecifiedCatalog());
		assertEquals("foo", javaJoinTable.getCatalog());
		
		//set catalog to null in the context model
		joinTable.setSpecifiedCatalog(null);
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
	}
	
////	public void testUpdateDefaultCatalogFromJavaTable() throws Exception {
////		createTestEntity();
////		
////		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
////		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
////		assertNull(xmlEntity.getTable().getDefaultCatalog());
////		
////		xmlEntity.javaEntity().getTable().setSpecifiedCatalog("Foo");
////		assertEquals("Foo", xmlEntity.getTable().getDefaultCatalog());
////		
////		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
////		assertNull(xmlEntity.getTable().getDefaultCatalog());
////
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
////		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
////		assertNull(xmlEntity.getTable().getDefaultCatalog());
////	
////		xmlEntity.setSpecifiedMetadataComplete(null);
////		assertNull(xmlEntity.getTable().getDefaultCatalog());
////		
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
////		assertEquals("Foo", xmlEntity.getTable().getDefaultCatalog());
////		
////		xmlEntity.getTable().setSpecifiedName("Bar");
////		assertNull(xmlEntity.getTable().getDefaultCatalog());
////	}
////	
////	public void testUpdateDefaultCatalogNoJava() throws Exception {
////		createTestEntity();
////		
////		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
////		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
////		assertNull(xmlEntity.getTable().getDefaultCatalog());
////	}
////	
////	public void testUpdateDefaultCatalogFromParent() throws Exception {
////		createTestEntity();
////		createTestSubType();
////		
////		XmlPersistentType parentXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
////		XmlPersistentType childXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
////		XmlEntity parentXmlEntity = (XmlEntity) parentXmlPersistentType.getMapping();
////		XmlEntity childXmlEntity = (XmlEntity) childXmlPersistentType.getMapping();
////		
////		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
////		assertNull(childXmlEntity.getTable().getDefaultCatalog());
////		
////		parentXmlEntity.getTable().setSpecifiedCatalog("FOO");
////		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
////		assertEquals("FOO", childXmlEntity.getTable().getDefaultCatalog());
////
////		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
////		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
////		assertNull(childXmlEntity.getTable().getDefaultCatalog());
////	}
////	
////	public void testUpdateDefaultCatalogFromPersistenceUnitDefaults() throws Exception {
////		createTestEntity();
////		
////		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
////		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
////		assertNull(xmlEntity.getTable().getDefaultCatalog());
////		
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("FOO");
////		assertEquals("FOO", xmlEntity.getTable().getDefaultCatalog());
////		
////		xmlEntity.entityMappings().setSpecifiedCatalog("BAR");
////		assertEquals("BAR", xmlEntity.getTable().getDefaultCatalog());
////		
////		xmlEntity.javaEntity().getTable().setSpecifiedCatalog("JAVA_CATALOG");
////		assertEquals("JAVA_CATALOG", xmlEntity.getTable().getDefaultCatalog());
////		
////		xmlEntity.getTable().setSpecifiedName("BLAH");
////		//xml entity now has a table element so default schema is not taken from java
////		assertEquals("BAR", xmlEntity.getTable().getDefaultCatalog());
////
////		
////		xmlEntity.entityMappings().setSpecifiedCatalog(null);
////		assertEquals("FOO", xmlEntity.getTable().getDefaultCatalog());
////
////		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog(null);
////		assertNull(xmlEntity.getTable().getDefaultCatalog());
////		
////		xmlEntity.getTable().setSpecifiedName(null);
////		assertEquals("JAVA_CATALOG", xmlEntity.getTable().getDefaultCatalog());
////}
//
////	
////	public void testUpdateName() throws Exception {
////		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
////		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
////		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
////		assertEquals("Foo", xmlEntity.getName());
////		
////		//set class in the resource model, verify context model updated
////		entityResource.setClassName("com.Bar");
////		assertEquals("Bar", xmlEntity.getName());
////		
////		entityResource.setName("Baz");
////		assertEquals("Baz", xmlEntity.getName());
////		
////		//set class to null in the resource model
////		entityResource.setClassName(null);
////		assertEquals("Baz", xmlEntity.getName());
////		
////		entityResource.setName(null);
////		assertNull(xmlEntity.getName());
////	}
//

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		
		IJoinColumn joinColumn = joinTable.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		JoinTable joinTableResource = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);

		assertEquals("FOO", joinTableResource.joinColumnAt(0).getName());
		
		IJoinColumn joinColumn2 = joinTable.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(1).getName());
		
		IJoinColumn joinColumn3 = joinTable.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());
		
		ListIterator<IJoinColumn> joinColumns = joinTable.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();

		joinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTable joinTableResource = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.joinColumnsSize());
		
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(2, joinTableResource.joinColumnsSize());
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());

		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinTableResource.joinColumnsSize());
		assertEquals("BAZ", joinTableResource.joinColumnAt(0).getName());
		
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinTableResource.joinColumnsSize());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();

		joinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTable joinTableResource = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.joinColumnsSize());
		
		
		joinTable.moveSpecifiedJoinColumn(2, 0);
		ListIterator<IJoinColumn> joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());


		joinTable.moveSpecifiedJoinColumn(0, 1);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAR", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		JoinTable joinTableResource = (JoinTable) attributeResource.addAnnotation(JoinTable.ANNOTATION_NAME);
	
		joinTableResource.addJoinColumn(0);
		joinTableResource.addJoinColumn(1);
		joinTableResource.addJoinColumn(2);
		
		joinTableResource.joinColumnAt(0).setName("FOO");
		joinTableResource.joinColumnAt(1).setName("BAR");
		joinTableResource.joinColumnAt(2).setName("BAZ");
	
		ListIterator<IJoinColumn> joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.moveJoinColumn(2, 0);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.moveJoinColumn(0, 1);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.removeJoinColumn(0);
		assertFalse(joinTable.specifiedJoinColumns().hasNext());
	}
	
	public void testAddSpecifiedInverseJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		
		IJoinColumn inverseJoinColumn = joinTable.addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("FOO");
				
		JoinTable joinTableResource = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);

		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(0).getName());
		
		IJoinColumn inverseJoinColumn2 = joinTable.addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(1).getName());
		
		IJoinColumn inverseJoinColumn3 = joinTable.addSpecifiedInverseJoinColumn(1);
		inverseJoinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(2).getName());
		
		ListIterator<IJoinColumn> inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals(inverseJoinColumn2, inverseJoinColumns.next());
		assertEquals(inverseJoinColumn3, inverseJoinColumns.next());
		assertEquals(inverseJoinColumn, inverseJoinColumns.next());
		
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedInverseJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();

		joinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTable joinTableResource = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.inverseJoinColumnsSize());
		
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(2, joinTableResource.inverseJoinColumnsSize());
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(1).getName());

		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTableResource.inverseJoinColumnsSize());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(0).getName());
		
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(0, joinTableResource.inverseJoinColumnsSize());
	}
	
	public void testMoveSpecifiedInverseJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();

		joinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTable joinTableResource = (JoinTable) attributeResource.annotation(JoinTable.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.inverseJoinColumnsSize());
		
		
		joinTable.moveSpecifiedInverseJoinColumn(2, 0);
		ListIterator<IJoinColumn> inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(2).getName());


		joinTable.moveSpecifiedInverseJoinColumn(0, 1);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(2).getName());
	}
	
	public void testUpdateInverseJoinColumns() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IJavaManyToManyMapping manyToManyMapping = (IJavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		IJoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		JoinTable joinTableResource = (JoinTable) attributeResource.addAnnotation(JoinTable.ANNOTATION_NAME);
	
		joinTableResource.addInverseJoinColumn(0);
		joinTableResource.addInverseJoinColumn(1);
		joinTableResource.addInverseJoinColumn(2);
		
		joinTableResource.inverseJoinColumnAt(0).setName("FOO");
		joinTableResource.inverseJoinColumnAt(1).setName("BAR");
		joinTableResource.inverseJoinColumnAt(2).setName("BAZ");
	
		ListIterator<IJoinColumn> inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		joinTableResource.moveInverseJoinColumn(2, 0);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
	
		joinTableResource.moveInverseJoinColumn(0, 1);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
	
		joinTableResource.removeInverseJoinColumn(1);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
	
		joinTableResource.removeInverseJoinColumn(1);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		joinTableResource.removeInverseJoinColumn(0);
		assertFalse(joinTable.specifiedInverseJoinColumns().hasNext());
	}


}