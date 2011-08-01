/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmPrimaryKeyJoinColumnTests extends ContextModelTestCase
{
	public OrmPrimaryKeyJoinColumnTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmPrimaryKeyJoinColumn ormPrimaryKeyJoinColumn = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);
		
		assertNull(ormPrimaryKeyJoinColumn.getSpecifiedName());
		assertNull(primaryKeyJoinColumnResource.getName());
		
		//set name in the resource model, verify context model updated
		primaryKeyJoinColumnResource.setName("FOO");
		assertEquals("FOO", ormPrimaryKeyJoinColumn.getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumnResource.getName());
	
		//set name to null in the resource model
		primaryKeyJoinColumnResource.setName(null);
		assertNull(ormPrimaryKeyJoinColumn.getSpecifiedName());
		assertNull(primaryKeyJoinColumnResource.getName());
		
		primaryKeyJoinColumnResource.setName("FOO");
		assertEquals("FOO", ormPrimaryKeyJoinColumn.getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumnResource.getName());

		entityResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(ormEntity.getSpecifiedPrimaryKeyJoinColumns().iterator().hasNext());
		assertTrue(entityResource.getPrimaryKeyJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmPrimaryKeyJoinColumn ormPrimaryKeyJoinColumn = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);

		assertNull(ormPrimaryKeyJoinColumn.getSpecifiedName());
		assertNull(primaryKeyJoinColumnResource.getName());
		
		//set name in the context model, verify resource model modified
		ormPrimaryKeyJoinColumn.setSpecifiedName("foo");
		assertEquals("foo", ormPrimaryKeyJoinColumn.getSpecifiedName());
		assertEquals("foo", primaryKeyJoinColumnResource.getName());
		
		//set name to null in the context model
		ormPrimaryKeyJoinColumn.setSpecifiedName(null);
		assertNull(ormPrimaryKeyJoinColumn.getSpecifiedName());
		assertNull(entityResource.getPrimaryKeyJoinColumns().get(0).getName());
	}
	
	public void testUpdateSpecifiedReferencedColumnName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmPrimaryKeyJoinColumn ormPrimaryKeyJoinColumn = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);
		
		assertNull(ormPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertNull(primaryKeyJoinColumnResource.getReferencedColumnName());
		
		//set name in the resource model, verify context model updated
		primaryKeyJoinColumnResource.setReferencedColumnName("FOO");
		assertEquals("FOO", ormPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals("FOO", primaryKeyJoinColumnResource.getReferencedColumnName());
	
		//set name to null in the resource model
		primaryKeyJoinColumnResource.setReferencedColumnName(null);
		assertNull(ormPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertNull(primaryKeyJoinColumnResource.getReferencedColumnName());
		
		primaryKeyJoinColumnResource.setReferencedColumnName("FOO");
		assertEquals("FOO", ormPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals("FOO", primaryKeyJoinColumnResource.getReferencedColumnName());

		entityResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(ormEntity.getSpecifiedPrimaryKeyJoinColumns().iterator().hasNext());
		assertTrue(entityResource.getPrimaryKeyJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedReferencedColumnName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmPrimaryKeyJoinColumn ormPrimaryKeyJoinColumn = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);

		assertNull(ormPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertNull(primaryKeyJoinColumnResource.getReferencedColumnName());
		
		//set name in the context model, verify resource model modified
		ormPrimaryKeyJoinColumn.setSpecifiedReferencedColumnName("foo");
		assertEquals("foo", ormPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals("foo", primaryKeyJoinColumnResource.getReferencedColumnName());
		
		//set name to null in the context model
		ormPrimaryKeyJoinColumn.setSpecifiedReferencedColumnName(null);
		assertNull(ormPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertNull(entityResource.getPrimaryKeyJoinColumns().get(0).getReferencedColumnName());
	}

//	public void testUpdateDefaultNameFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity ormEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//		
//		ormEntity.javaEntity().getTable().setSpecifiedName("Foo");
//		assertEquals("Foo", ormEntity.getTable().getDefaultName());
//		
//		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//
//		ormEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
//		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//	
//		ormEntity.setSpecifiedMetadataComplete(null);
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//		
//		ormEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
//		assertEquals("Foo", ormEntity.getTable().getDefaultName());
//		
//		ormEntity.getTable().setSpecifiedName("Bar");
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameNoJava() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity ormEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertEquals("Foo", ormEntity.getTable().getDefaultName());
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmPrimaryKeyJoinColumn ormPrimaryKeyJoinColumn = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);
		
		assertNull(ormPrimaryKeyJoinColumn.getColumnDefinition());
		assertNull(primaryKeyJoinColumnResource.getColumnDefinition());
		
		//set name in the resource model, verify context model updated
		primaryKeyJoinColumnResource.setColumnDefinition("FOO");
		assertEquals("FOO", ormPrimaryKeyJoinColumn.getColumnDefinition());
		assertEquals("FOO", primaryKeyJoinColumnResource.getColumnDefinition());
	
		//set name to null in the resource model
		primaryKeyJoinColumnResource.setColumnDefinition(null);
		assertNull(ormPrimaryKeyJoinColumn.getColumnDefinition());
		assertNull(primaryKeyJoinColumnResource.getColumnDefinition());
		
		primaryKeyJoinColumnResource.setColumnDefinition("FOO");
		assertEquals("FOO", ormPrimaryKeyJoinColumn.getColumnDefinition());
		assertEquals("FOO", primaryKeyJoinColumnResource.getColumnDefinition());

		entityResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(ormEntity.getSpecifiedPrimaryKeyJoinColumns().iterator().hasNext());
		assertTrue(entityResource.getPrimaryKeyJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmPrimaryKeyJoinColumn ormPrimaryKeyJoinColumn = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);

		assertNull(ormPrimaryKeyJoinColumn.getColumnDefinition());
		assertNull(primaryKeyJoinColumnResource.getColumnDefinition());
		
		//set name in the context model, verify resource model modified
		ormPrimaryKeyJoinColumn.setColumnDefinition("foo");
		assertEquals("foo", ormPrimaryKeyJoinColumn.getColumnDefinition());
		assertEquals("foo", primaryKeyJoinColumnResource.getColumnDefinition());
		
		//set name to null in the context model
		ormPrimaryKeyJoinColumn.setColumnDefinition(null);
		assertNull(ormPrimaryKeyJoinColumn.getColumnDefinition());
		assertNull(entityResource.getPrimaryKeyJoinColumns().get(0).getColumnDefinition());
	}
	

}