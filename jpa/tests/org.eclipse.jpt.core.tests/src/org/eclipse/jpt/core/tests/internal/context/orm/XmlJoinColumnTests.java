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

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmJoinTable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmManyToManyMapping;
import org.eclipse.jpt.core.internal.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlJoinColumnTests extends ContextModelTestCase
{
	public XmlJoinColumnTests(String name) {
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn joinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);
				
		assertNull(joinColumn.getSpecifiedName());
		assertNull(joinColumnResource.getName());
		
		//set name in the resource model, verify context model updated
		joinColumnResource.setName("FOO");
		assertEquals("FOO", joinColumn.getSpecifiedName());
		assertEquals("FOO", joinColumnResource.getName());
	
		//set name to null in the resource model
		joinColumnResource.setName(null);
		assertNull(joinColumn.getSpecifiedName());
		assertNull(joinColumnResource.getName());
		
		joinColumnResource.setName("FOO");
		assertEquals("FOO", joinColumn.getSpecifiedName());
		assertEquals("FOO", joinColumnResource.getName());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(xmlJoinTable.joinColumns().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn joinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);
				
		assertNull(joinColumn.getSpecifiedName());
		assertNull(joinColumnResource.getName());
		
		//set name in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		assertEquals("foo", joinColumn.getSpecifiedName());
		assertEquals("foo", joinColumnResource.getName());
		
		//set name to null in the context model
		joinColumn.setSpecifiedName(null);
		assertNull(joinColumn.getSpecifiedName());
		assertNull(joinTableResource.getJoinColumns().get(0).getName());
	}
	
	public void testUpdateSpecifiedReferencedColumnName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn joinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);
				
		assertNull(joinColumn.getSpecifiedReferencedColumnName());
		assertNull(joinColumnResource.getReferencedColumnName());
				
		//set name in the resource model, verify context model updated
		joinColumnResource.setReferencedColumnName("FOO");
		ormResource().save(null);
		assertEquals("FOO", joinColumn.getSpecifiedReferencedColumnName());
		assertEquals("FOO", joinColumnResource.getReferencedColumnName());
	
		//set name to null in the resource model
		joinColumnResource.setReferencedColumnName(null);
		assertNull(joinColumn.getSpecifiedReferencedColumnName());
		assertNull(joinColumnResource.getReferencedColumnName());
		
		joinColumnResource.setReferencedColumnName("FOO");
		assertEquals("FOO", joinColumn.getSpecifiedReferencedColumnName());
		assertEquals("FOO", joinColumnResource.getReferencedColumnName());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(xmlJoinTable.joinColumns().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedReferencedColumnName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn joinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);
				
		assertNull(joinColumn.getSpecifiedReferencedColumnName());
		assertNull(joinColumnResource.getReferencedColumnName());
		
		//set name in the context model, verify resource model modified
		joinColumn.setSpecifiedReferencedColumnName("foo");
		assertEquals("foo", joinColumn.getSpecifiedReferencedColumnName());
		assertEquals("foo", joinColumnResource.getReferencedColumnName());
		
		//set name to null in the context model
		joinColumn.setSpecifiedReferencedColumnName(null);
		assertNull(joinColumn.getSpecifiedReferencedColumnName());
		assertNull(joinTableResource.getJoinColumns().get(0).getReferencedColumnName());
	}

//	public void testUpdateDefaultNameFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
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
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//		XmlEntity parentXmlEntity = (XmlEntity) parentOrmPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childOrmPersistentType.getMapping();
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
	

	public void testUpdateSpecifiedColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn joinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);
		
		assertNull(joinColumn.getColumnDefinition());
		assertNull(joinColumnResource.getColumnDefinition());
		
		//set name in the resource model, verify context model updated
		joinColumnResource.setColumnDefinition("FOO");
		assertEquals("FOO", joinColumn.getColumnDefinition());
		assertEquals("FOO", joinColumnResource.getColumnDefinition());
	
		//set name to null in the resource model
		joinColumnResource.setColumnDefinition(null);
		assertNull(joinColumn.getColumnDefinition());
		assertNull(joinColumnResource.getColumnDefinition());
		
		joinColumnResource.setColumnDefinition("FOO");
		assertEquals("FOO", joinColumn.getColumnDefinition());
		assertEquals("FOO", joinColumnResource.getColumnDefinition());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(xmlJoinTable.joinColumns().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn joinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);
		
		assertNull(joinColumn.getColumnDefinition());
		assertNull(joinColumnResource.getColumnDefinition());
		
		//set name in the context model, verify resource model modified
		joinColumn.setColumnDefinition("foo");
		assertEquals("foo", joinColumn.getColumnDefinition());
		assertEquals("foo", joinColumnResource.getColumnDefinition());
		
		//set name to null in the context model
		joinColumn.setColumnDefinition(null);
		assertNull(joinColumn.getColumnDefinition());
		assertNull(joinTableResource.getJoinColumns().get(0).getColumnDefinition());
	}
	
	public void testUpdateSpecifiedTable() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedTable());
		assertNull(joinColumnResource.getTable());
		
		//set table in the resource model, verify context model updated
		joinColumnResource.setTable("FOO");
		assertEquals("FOO", xmlJoinColumn.getSpecifiedTable());
		assertEquals("FOO", joinColumnResource.getTable());
	
		//set table to null in the resource model
		joinColumnResource.setTable(null);
		assertNull(xmlJoinColumn.getSpecifiedTable());
		assertNull(joinColumnResource.getTable());
		
		joinColumnResource.setTable("FOO");
		assertEquals("FOO", xmlJoinColumn.getSpecifiedTable());
		assertEquals("FOO", joinColumnResource.getTable());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(xmlJoinTable.joinColumns().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedTable() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedTable());
		assertNull(joinColumnResource.getTable());
		
		//set table in the context model, verify resource model modified
		xmlJoinColumn.setSpecifiedTable("foo");
		assertEquals("foo", xmlJoinColumn.getSpecifiedTable());
		assertEquals("foo", joinColumnResource.getTable());
		
		//set table to null in the context model
		xmlJoinColumn.setSpecifiedTable(null);
		assertNull(xmlJoinColumn.getSpecifiedTable());
		assertNull(joinTableResource.getJoinColumns().get(0).getTable());
	}

	public void testUpdateSpecifiedNullable() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedNullable());
		assertNull(joinColumnResource.getNullable());
		
		//set nullable in the resource model, verify context model updated
		joinColumnResource.setNullable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, joinColumnResource.getNullable());
	
		//set nullable to null in the resource model
		joinColumnResource.setNullable(null);
		assertNull(xmlJoinColumn.getSpecifiedNullable());
		assertNull(joinColumnResource.getNullable());
		
		joinColumnResource.setNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, joinColumnResource.getNullable());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(xmlJoinTable.joinColumns().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedNullable() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedNullable());
		assertNull(joinColumnResource.getNullable());
		
		//set nullable in the context model, verify resource model modified
		xmlJoinColumn.setSpecifiedNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, joinColumnResource.getNullable());
		
		//set nullable to null in the context model
		xmlJoinColumn.setSpecifiedNullable(null);
		assertNull(xmlJoinColumn.getSpecifiedNullable());
		assertNull(joinTableResource.getJoinColumns().get(0).getNullable());
	}

	public void testUpdateSpecifiedUpdatable() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedUpdatable());
		assertNull(joinColumnResource.getUpdatable());
		
		//set updatable in the resource model, verify context model updated
		joinColumnResource.setUpdatable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlJoinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, joinColumnResource.getUpdatable());
	
		//set updatable to null in the resource model
		joinColumnResource.setUpdatable(null);
		assertNull(xmlJoinColumn.getSpecifiedUpdatable());
		assertNull(joinColumnResource.getUpdatable());
		
		joinColumnResource.setUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, joinColumnResource.getUpdatable());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(xmlJoinTable.joinColumns().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedUpdatable() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedUpdatable());
		assertNull(joinColumnResource.getUpdatable());
		
		//set updatable in the context model, verify resource model modified
		xmlJoinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, joinColumnResource.getUpdatable());
		
		//set updatable to null in the context model
		xmlJoinColumn.setSpecifiedUpdatable(null);
		assertNull(xmlJoinColumn.getSpecifiedUpdatable());
		assertNull(joinTableResource.getJoinColumns().get(0).getUpdatable());
	}

	public void testUpdateSpecifiedInsertable() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedInsertable());
		assertNull(joinColumnResource.getInsertable());
		
		//set insertable in the resource model, verify context model updated
		joinColumnResource.setInsertable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, joinColumnResource.getInsertable());
	
		//set insertable to null in the resource model
		joinColumnResource.setInsertable(null);
		assertNull(xmlJoinColumn.getSpecifiedInsertable());
		assertNull(joinColumnResource.getInsertable());
		
		joinColumnResource.setInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, joinColumnResource.getInsertable());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(xmlJoinTable.joinColumns().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedInsertable() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedInsertable());
		assertNull(joinColumnResource.getInsertable());
		
		//set insertable in the context model, verify resource model modified
		xmlJoinColumn.setSpecifiedInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, joinColumnResource.getInsertable());
		
		//set insertable to null in the context model
		xmlJoinColumn.setSpecifiedInsertable(null);
		assertNull(xmlJoinColumn.getSpecifiedInsertable());
		assertNull(joinTableResource.getJoinColumns().get(0).getInsertable());
	}
	
	public void testUpdateSpecifiedUnique() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedUnique());
		assertNull(joinColumnResource.getUnique());
		
		//set unique in the resource model, verify context model updated
		joinColumnResource.setUnique(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, joinColumnResource.getUnique());
	
		//set unique to null in the resource model
		joinColumnResource.setUnique(null);
		assertNull(xmlJoinColumn.getSpecifiedUnique());
		assertNull(joinColumnResource.getUnique());
		
		joinColumnResource.setUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, joinColumnResource.getUnique());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(xmlJoinTable.joinColumns().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedUnique() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		GenericOrmManyToManyMapping xmlManyToManyMapping = (GenericOrmManyToManyMapping) xmlPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		GenericOrmJoinTable xmlJoinTable = xmlManyToManyMapping.getJoinTable();		
		GenericOrmJoinColumn xmlJoinColumn = xmlJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(xmlJoinColumn.getSpecifiedUnique());
		assertNull(joinColumnResource.getUnique());
		
		//set unique in the context model, verify resource model modified
		xmlJoinColumn.setSpecifiedUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, joinColumnResource.getUnique());
		
		//set unique to null in the context model
		xmlJoinColumn.setSpecifiedUnique(null);
		assertNull(xmlJoinColumn.getSpecifiedUnique());
		assertNull(joinTableResource.getJoinColumns().get(0).getUnique());
	}

}