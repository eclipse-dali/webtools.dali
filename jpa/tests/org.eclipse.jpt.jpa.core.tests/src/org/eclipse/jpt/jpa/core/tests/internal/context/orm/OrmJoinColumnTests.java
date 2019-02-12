/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmJoinColumnTests extends ContextModelTestCase
{
	public OrmJoinColumnTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn joinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
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
		assertFalse(ormJoinTable.getSpecifiedJoinColumns().iterator().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn joinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn joinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);
				
		assertNull(joinColumn.getSpecifiedReferencedColumnName());
		assertNull(joinColumnResource.getReferencedColumnName());
				
		//set name in the resource model, verify context model updated
		joinColumnResource.setReferencedColumnName("FOO");
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
		assertFalse(ormJoinTable.getSpecifiedJoinColumns().iterator().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedReferencedColumnName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn joinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
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

	public void testUpdateSpecifiedColumnDefinition() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn joinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
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
		assertFalse(ormJoinTable.getSpecifiedJoinColumns().iterator().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedColumnDefinition() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn joinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedTableName());
		assertNull(joinColumnResource.getTable());
		
		//set table in the resource model, verify context model updated
		joinColumnResource.setTable("FOO");
		assertEquals("FOO", ormJoinColumn.getSpecifiedTableName());
		assertEquals("FOO", joinColumnResource.getTable());
	
		//set table to null in the resource model
		joinColumnResource.setTable(null);
		assertNull(ormJoinColumn.getSpecifiedTableName());
		assertNull(joinColumnResource.getTable());
		
		joinColumnResource.setTable("FOO");
		assertEquals("FOO", ormJoinColumn.getSpecifiedTableName());
		assertEquals("FOO", joinColumnResource.getTable());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(ormJoinTable.getSpecifiedJoinColumns().iterator().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedTable() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedTableName());
		assertNull(joinColumnResource.getTable());
		
		//set table in the context model, verify resource model modified
		ormJoinColumn.setSpecifiedTableName("foo");
		assertEquals("foo", ormJoinColumn.getSpecifiedTableName());
		assertEquals("foo", joinColumnResource.getTable());
		
		//set table to null in the context model
		ormJoinColumn.setSpecifiedTableName(null);
		assertNull(ormJoinColumn.getSpecifiedTableName());
		assertNull(joinTableResource.getJoinColumns().get(0).getTable());
	}

	public void testUpdateSpecifiedNullable() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedNullable());
		assertNull(joinColumnResource.getNullable());
		
		//set nullable in the resource model, verify context model updated
		joinColumnResource.setNullable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, joinColumnResource.getNullable());
	
		//set nullable to null in the resource model
		joinColumnResource.setNullable(null);
		assertNull(ormJoinColumn.getSpecifiedNullable());
		assertNull(joinColumnResource.getNullable());
		
		joinColumnResource.setNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, joinColumnResource.getNullable());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(ormJoinTable.getSpecifiedJoinColumns().iterator().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedNullable() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedNullable());
		assertNull(joinColumnResource.getNullable());
		
		//set nullable in the context model, verify resource model modified
		ormJoinColumn.setSpecifiedNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, joinColumnResource.getNullable());
		
		//set nullable to null in the context model
		ormJoinColumn.setSpecifiedNullable(null);
		assertNull(ormJoinColumn.getSpecifiedNullable());
		assertNull(joinTableResource.getJoinColumns().get(0).getNullable());
	}

	public void testUpdateSpecifiedUpdatable() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedUpdatable());
		assertNull(joinColumnResource.getUpdatable());
		
		//set updatable in the resource model, verify context model updated
		joinColumnResource.setUpdatable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, joinColumnResource.getUpdatable());
	
		//set updatable to null in the resource model
		joinColumnResource.setUpdatable(null);
		assertNull(ormJoinColumn.getSpecifiedUpdatable());
		assertNull(joinColumnResource.getUpdatable());
		
		joinColumnResource.setUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, joinColumnResource.getUpdatable());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(ormJoinTable.getSpecifiedJoinColumns().iterator().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedUpdatable() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedUpdatable());
		assertNull(joinColumnResource.getUpdatable());
		
		//set updatable in the context model, verify resource model modified
		ormJoinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, joinColumnResource.getUpdatable());
		
		//set updatable to null in the context model
		ormJoinColumn.setSpecifiedUpdatable(null);
		assertNull(ormJoinColumn.getSpecifiedUpdatable());
		assertNull(joinTableResource.getJoinColumns().get(0).getUpdatable());
	}

	public void testUpdateSpecifiedInsertable() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedInsertable());
		assertNull(joinColumnResource.getInsertable());
		
		//set insertable in the resource model, verify context model updated
		joinColumnResource.setInsertable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, joinColumnResource.getInsertable());
	
		//set insertable to null in the resource model
		joinColumnResource.setInsertable(null);
		assertNull(ormJoinColumn.getSpecifiedInsertable());
		assertNull(joinColumnResource.getInsertable());
		
		joinColumnResource.setInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, joinColumnResource.getInsertable());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(ormJoinTable.getSpecifiedJoinColumns().iterator().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedInsertable() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedInsertable());
		assertNull(joinColumnResource.getInsertable());
		
		//set insertable in the context model, verify resource model modified
		ormJoinColumn.setSpecifiedInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, joinColumnResource.getInsertable());
		
		//set insertable to null in the context model
		ormJoinColumn.setSpecifiedInsertable(null);
		assertNull(ormJoinColumn.getSpecifiedInsertable());
		assertNull(joinTableResource.getJoinColumns().get(0).getInsertable());
	}
	
	public void testUpdateSpecifiedUnique() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedUnique());
		assertNull(joinColumnResource.getUnique());
		
		//set unique in the resource model, verify context model updated
		joinColumnResource.setUnique(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, joinColumnResource.getUnique());
	
		//set unique to null in the resource model
		joinColumnResource.setUnique(null);
		assertNull(ormJoinColumn.getSpecifiedUnique());
		assertNull(joinColumnResource.getUnique());
		
		joinColumnResource.setUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, joinColumnResource.getUnique());

		joinTableResource.getJoinColumns().remove(0);
		assertFalse(ormJoinTable.getSpecifiedJoinColumns().iterator().hasNext());
		assertTrue(joinTableResource.getJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedUnique() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmSpecifiedJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();		
		OrmSpecifiedJoinColumn ormJoinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		XmlJoinColumn joinColumnResource = joinTableResource.getJoinColumns().get(0);

		assertNull(ormJoinColumn.getSpecifiedUnique());
		assertNull(joinColumnResource.getUnique());
		
		//set unique in the context model, verify resource model modified
		ormJoinColumn.setSpecifiedUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, joinColumnResource.getUnique());
		
		//set unique to null in the context model
		ormJoinColumn.setSpecifiedUnique(null);
		assertNull(ormJoinColumn.getSpecifiedUnique());
		assertNull(joinTableResource.getJoinColumns().get(0).getUnique());
	}

}