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
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.orm.XmlJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.XmlJoinTable;
import org.eclipse.jpt.core.internal.context.orm.XmlManyToManyMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.JoinTable;
import org.eclipse.jpt.core.internal.resource.orm.ManyToMany;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlJoinTableTests extends ContextModelTestCase
{
	public XmlJoinTableTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		
		assertNull(xmlJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable());
		
		
		//set name in the resource model, verify context model updated
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createJoinTable());
		manyToMany.getJoinTable().setName("FOO");
		assertEquals("FOO", xmlJoinTable.getSpecifiedName());
		assertEquals("FOO", manyToMany.getJoinTable().getName());
	
		//set name to null in the resource model
		manyToMany.getJoinTable().setName(null);
		assertNull(xmlJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable().getName());
		
		manyToMany.getJoinTable().setName("FOO");
		assertEquals("FOO", xmlJoinTable.getSpecifiedName());
		assertEquals("FOO", manyToMany.getJoinTable().getName());

		manyToMany.setJoinTable(null);
		assertNull(xmlJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable());
	}
	
	public void testModifySpecifiedName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		
		assertNull(xmlJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable());
		
		//set name in the context model, verify resource model modified
		xmlJoinTable.setSpecifiedName("foo");
		assertEquals("foo", xmlJoinTable.getSpecifiedName());
		assertEquals("foo", manyToMany.getJoinTable().getName());
		
		//set name to null in the context model
		xmlJoinTable.setSpecifiedName(null);
		assertNull(xmlJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable());
	}
	
//	public void testUpdateDefaultNameFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedName("Foo");
//		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
//		
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//	
//		xmlEntity.setSpecifiedMetadataComplete(null);
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
//		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
//		
//		xmlEntity.getTable().setSpecifiedName("Bar");
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameNoJava() throws Exception {
//		createTestEntity();
//		
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		XmlPersistentType parentXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlPersistentType childXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//		XmlEntity parentXmlEntity = (XmlEntity) parentXmlPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childXmlPersistentType.getMapping();
//		
//		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//		assertEquals(TYPE_NAME, childXmlEntity.getTable().getDefaultName());
//		
//		parentXmlEntity.getTable().setSpecifiedName("FOO");
//		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//		assertEquals("FOO", childXmlEntity.getTable().getDefaultName());
//
//		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
//		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//		assertEquals("AnnotationTestTypeChild", childXmlEntity.getTable().getDefaultName());
//	}

	public void testUpdateSpecifiedSchema() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		
		assertNull(xmlJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable());
		
		//set schema in the resource model, verify context model updated
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createJoinTable());
		manyToMany.getJoinTable().setSchema("FOO");
		assertEquals("FOO", xmlJoinTable.getSpecifiedSchema());
		assertEquals("FOO", manyToMany.getJoinTable().getSchema());
	
		//set Schema to null in the resource model
		manyToMany.getJoinTable().setSchema(null);
		assertNull(xmlJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable().getSchema());
		
		manyToMany.getJoinTable().setSchema("FOO");
		assertEquals("FOO", xmlJoinTable.getSpecifiedSchema());
		assertEquals("FOO", manyToMany.getJoinTable().getSchema());

		manyToMany.setJoinTable(null);
		assertNull(xmlJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable());
	}
	
//	public void testUpdateDefaultSchemaFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedSchema("Foo");
//		assertEquals("Foo", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//	
//		xmlEntity.setSpecifiedMetadataComplete(null);
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
//		assertEquals("Foo", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.getTable().setSpecifiedName("Bar");
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//	}
//	
//	public void testUpdateDefaultSchemaNoJava() throws Exception {
//		createTestEntity();
//		
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//	}
//	
//	public void testUpdateDefaultSchemaFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		XmlPersistentType parentXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlPersistentType childXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//		XmlEntity parentXmlEntity = (XmlEntity) parentXmlPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childXmlPersistentType.getMapping();
//		
//		assertNull(parentXmlEntity.getTable().getDefaultSchema());
//		assertNull(childXmlEntity.getTable().getDefaultSchema());
//		
//		parentXmlEntity.getTable().setSpecifiedSchema("FOO");
//		assertNull(parentXmlEntity.getTable().getDefaultSchema());
//		assertEquals("FOO", childXmlEntity.getTable().getDefaultSchema());
//
//		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
//		assertNull(parentXmlEntity.getTable().getDefaultSchema());
//		assertNull(childXmlEntity.getTable().getDefaultSchema());
//	}
//	
//	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
//		createTestEntity();
//		
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("FOO");
//		assertEquals("FOO", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.entityMappings().setSpecifiedSchema("BAR");
//		assertEquals("BAR", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedSchema("JAVA_SCHEMA");
//		assertEquals("JAVA_SCHEMA", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.getTable().setSpecifiedName("BLAH");
//		//xml entity now has a table element so default schema is not taken from java
//		assertEquals("BAR", xmlEntity.getTable().getDefaultSchema());
//
//		
//		xmlEntity.entityMappings().setSpecifiedSchema(null);
//		assertEquals("FOO", xmlEntity.getTable().getDefaultSchema());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema(null);
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.getTable().setSpecifiedName(null);
//		assertEquals("JAVA_SCHEMA", xmlEntity.getTable().getDefaultSchema());
//	}

	public void testModifySpecifiedSchema() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		
		assertNull(xmlJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable());
		
		//set Schema in the context model, verify resource model modified
		xmlJoinTable.setSpecifiedSchema("foo");
		assertEquals("foo", xmlJoinTable.getSpecifiedSchema());
		assertEquals("foo", manyToMany.getJoinTable().getSchema());
		
		//set Schema to null in the context model
		xmlJoinTable.setSpecifiedSchema(null);
		assertNull(xmlJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable());
	}
	
	public void testUpdateSpecifiedCatalog() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		
		assertNull(xmlJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable());
		
		//set Catalog in the resource model, verify context model updated
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createJoinTable());
		manyToMany.getJoinTable().setCatalog("FOO");
		assertEquals("FOO", xmlJoinTable.getSpecifiedCatalog());
		assertEquals("FOO", manyToMany.getJoinTable().getCatalog());
	
		//set Catalog to null in the resource model
		manyToMany.getJoinTable().setCatalog(null);
		assertNull(xmlJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable().getCatalog());
		
		manyToMany.getJoinTable().setCatalog("FOO");
		assertEquals("FOO", xmlJoinTable.getSpecifiedCatalog());
		assertEquals("FOO", manyToMany.getJoinTable().getCatalog());

		manyToMany.setJoinTable(null);
		assertNull(xmlJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		
		assertNull(xmlJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable());
		
		//set Catalog in the context model, verify resource model modified
		xmlJoinTable.setSpecifiedCatalog("foo");
		assertEquals("foo", xmlJoinTable.getSpecifiedCatalog());
		assertEquals("foo", manyToMany.getJoinTable().getCatalog());
		
		//set Catalog to null in the context model
		xmlJoinTable.setSpecifiedCatalog(null);
		assertNull(xmlJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable());
	}
	
//	public void testUpdateDefaultCatalogFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedCatalog("Foo");
//		assertEquals("Foo", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//	
//		xmlEntity.setSpecifiedMetadataComplete(null);
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
//		assertEquals("Foo", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.getTable().setSpecifiedName("Bar");
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//	}
//	
//	public void testUpdateDefaultCatalogNoJava() throws Exception {
//		createTestEntity();
//		
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//	}
//	
//	public void testUpdateDefaultCatalogFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		XmlPersistentType parentXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlPersistentType childXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//		XmlEntity parentXmlEntity = (XmlEntity) parentXmlPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childXmlPersistentType.getMapping();
//		
//		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
//		assertNull(childXmlEntity.getTable().getDefaultCatalog());
//		
//		parentXmlEntity.getTable().setSpecifiedCatalog("FOO");
//		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
//		assertEquals("FOO", childXmlEntity.getTable().getDefaultCatalog());
//
//		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
//		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
//		assertNull(childXmlEntity.getTable().getDefaultCatalog());
//	}
//	
//	public void testUpdateDefaultCatalogFromPersistenceUnitDefaults() throws Exception {
//		createTestEntity();
//		
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("FOO");
//		assertEquals("FOO", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.entityMappings().setSpecifiedCatalog("BAR");
//		assertEquals("BAR", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedCatalog("JAVA_CATALOG");
//		assertEquals("JAVA_CATALOG", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.getTable().setSpecifiedName("BLAH");
//		//xml entity now has a table element so default schema is not taken from java
//		assertEquals("BAR", xmlEntity.getTable().getDefaultCatalog());
//
//		
//		xmlEntity.entityMappings().setSpecifiedCatalog(null);
//		assertEquals("FOO", xmlEntity.getTable().getDefaultCatalog());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog(null);
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.getTable().setSpecifiedName(null);
//		assertEquals("JAVA_CATALOG", xmlEntity.getTable().getDefaultCatalog());
//}

//	
//	public void testUpdateName() throws Exception {
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
//		assertEquals("Foo", xmlEntity.getName());
//		
//		//set class in the resource model, verify context model updated
//		entityResource.setClassName("com.Bar");
//		assertEquals("Bar", xmlEntity.getName());
//		
//		entityResource.setName("Baz");
//		assertEquals("Baz", xmlEntity.getName());
//		
//		//set class to null in the resource model
//		entityResource.setClassName(null);
//		assertEquals("Baz", xmlEntity.getName());
//		
//		entityResource.setName(null);
//		assertNull(xmlEntity.getName());
//	}


	public void testAddSpecifiedJoinColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		
		XmlJoinColumn joinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		ormResource().save(null);
		joinColumn.setSpecifiedName("FOO");
		ormResource().save(null);
				
		JoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals("FOO", joinTableResource.getJoinColumns().get(0).getName());
		
		XmlJoinColumn joinColumn2 = xmlJoinTable.addSpecifiedJoinColumn(0);
		ormResource().save(null);
		joinColumn2.setSpecifiedName("BAR");
		ormResource().save(null);
		
		assertEquals("BAR", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("FOO", joinTableResource.getJoinColumns().get(1).getName());
		
		XmlJoinColumn joinColumn3 = xmlJoinTable.addSpecifiedJoinColumn(1);
		ormResource().save(null);
		joinColumn3.setSpecifiedName("BAZ");
		ormResource().save(null);
		
		assertEquals("BAR", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getJoinColumns().get(2).getName());
		
		ListIterator<XmlJoinColumn> joinColumns = xmlJoinTable.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = xmlJoinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();

		xmlJoinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		xmlJoinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		xmlJoinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals(3, joinTableResource.getJoinColumns().size());
		
		xmlJoinTable.removeSpecifiedJoinColumn(0);
		assertEquals(2, joinTableResource.getJoinColumns().size());
		assertEquals("BAR", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getJoinColumns().get(1).getName());

		xmlJoinTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinTableResource.getJoinColumns().size());
		assertEquals("BAZ", joinTableResource.getJoinColumns().get(0).getName());
		
		xmlJoinTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinTableResource.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();

		xmlJoinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		xmlJoinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		xmlJoinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals(3, joinTableResource.getJoinColumns().size());
		
		
		xmlJoinTable.moveSpecifiedJoinColumn(2, 0);
		ListIterator<XmlJoinColumn> joinColumns = xmlJoinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getJoinColumns().get(2).getName());


		xmlJoinTable.moveSpecifiedJoinColumn(0, 1);
		joinColumns = xmlJoinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("BAR", joinTableResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getJoinColumns().get(2).getName());
	}
	
	public void testUpdateInverseJoinColumns() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createJoinTable());
		JoinTable joinTableResource = manyToMany.getJoinTable();
	
		joinTableResource.getInverseJoinColumns().add(OrmFactory.eINSTANCE.createJoinColumn());
		joinTableResource.getInverseJoinColumns().add(OrmFactory.eINSTANCE.createJoinColumn());
		joinTableResource.getInverseJoinColumns().add(OrmFactory.eINSTANCE.createJoinColumn());
		
		joinTableResource.getInverseJoinColumns().get(0).setName("FOO");
		joinTableResource.getInverseJoinColumns().get(1).setName("BAR");
		joinTableResource.getInverseJoinColumns().get(2).setName("BAZ");

		ListIterator<XmlJoinColumn> joinColumns = xmlJoinTable.specifiedInverseJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.getInverseJoinColumns().move(2, 0);
		joinColumns = xmlJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getInverseJoinColumns().move(0, 1);
		joinColumns = xmlJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getInverseJoinColumns().remove(1);
		joinColumns = xmlJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getInverseJoinColumns().remove(1);
		joinColumns = xmlJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.getInverseJoinColumns().remove(0);
		assertFalse(xmlJoinTable.specifiedInverseJoinColumns().hasNext());
	}

	public void testAddSpecifiedInverseJoinColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		
		XmlJoinColumn joinColumn = xmlJoinTable.addSpecifiedInverseJoinColumn(0);
		ormResource().save(null);
		joinColumn.setSpecifiedName("FOO");
		ormResource().save(null);
				
		JoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(0).getName());
		
		XmlJoinColumn joinColumn2 = xmlJoinTable.addSpecifiedInverseJoinColumn(0);
		ormResource().save(null);
		joinColumn2.setSpecifiedName("BAR");
		ormResource().save(null);
		
		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(1).getName());
		
		XmlJoinColumn joinColumn3 = xmlJoinTable.addSpecifiedInverseJoinColumn(1);
		ormResource().save(null);
		joinColumn3.setSpecifiedName("BAZ");
		ormResource().save(null);
		
		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(2).getName());
		
		ListIterator<XmlJoinColumn> joinColumns = xmlJoinTable.specifiedInverseJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = xmlJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedInverseJoinColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();

		xmlJoinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		xmlJoinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		xmlJoinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals(3, joinTableResource.getInverseJoinColumns().size());
		
		xmlJoinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(2, joinTableResource.getInverseJoinColumns().size());
		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(1).getName());

		xmlJoinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTableResource.getInverseJoinColumns().size());
		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(0).getName());
		
		xmlJoinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(0, joinTableResource.getInverseJoinColumns().size());
	}
	
	public void testMoveSpecifiedInverseJoinColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();

		xmlJoinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		xmlJoinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		xmlJoinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals(3, joinTableResource.getInverseJoinColumns().size());
		
		
		xmlJoinTable.moveSpecifiedInverseJoinColumn(2, 0);
		ListIterator<XmlJoinColumn> joinColumns = xmlJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(2).getName());


		xmlJoinTable.moveSpecifiedInverseJoinColumn(0, 1);
		joinColumns = xmlJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		XmlJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createJoinTable());
		JoinTable joinTableResource = manyToMany.getJoinTable();
	
		joinTableResource.getJoinColumns().add(OrmFactory.eINSTANCE.createJoinColumn());
		joinTableResource.getJoinColumns().add(OrmFactory.eINSTANCE.createJoinColumn());
		joinTableResource.getJoinColumns().add(OrmFactory.eINSTANCE.createJoinColumn());
		
		joinTableResource.getJoinColumns().get(0).setName("FOO");
		joinTableResource.getJoinColumns().get(1).setName("BAR");
		joinTableResource.getJoinColumns().get(2).setName("BAZ");

		ListIterator<XmlJoinColumn> joinColumns = xmlJoinTable.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.getJoinColumns().move(2, 0);
		joinColumns = xmlJoinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getJoinColumns().move(0, 1);
		joinColumns = xmlJoinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getJoinColumns().remove(1);
		joinColumns = xmlJoinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getJoinColumns().remove(1);
		joinColumns = xmlJoinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.getJoinColumns().remove(0);
		assertFalse(xmlJoinTable.specifiedJoinColumns().hasNext());
	}
}