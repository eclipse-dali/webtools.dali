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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmNamedNativeQuery;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class EntityMappingsTests extends ContextModelTestCase
{
	public EntityMappingsTests(String name) {
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
	
	public void testGetVersion() throws Exception {
		assertEquals("1.0", entityMappings().getVersion());
	}
	
	public void testUpdateDescription() throws Exception {
		assertNull(entityMappings().getDescription());
		assertNull(ormResource().getEntityMappings().getDescription());
		
		//set description in the resource model, verify context model updated
		ormResource().getEntityMappings().setDescription("newDescription");
		assertEquals("newDescription", entityMappings().getDescription());
		assertEquals("newDescription", ormResource().getEntityMappings().getDescription());
	
		//set description to null in the resource model
		ormResource().getEntityMappings().setDescription(null);
		assertNull(entityMappings().getDescription());
		assertNull(ormResource().getEntityMappings().getDescription());
	}
	
	public void testModifyDescription() throws Exception {
		assertNull(entityMappings().getDescription());
		assertNull(ormResource().getEntityMappings().getDescription());
		
		//set description in the context model, verify resource model modified
		entityMappings().setDescription("newDescription");
		assertEquals("newDescription", entityMappings().getDescription());
		assertEquals("newDescription", ormResource().getEntityMappings().getDescription());
		
		//set description to null in the context model
		entityMappings().setDescription(null);
		assertNull(entityMappings().getDescription());
		assertNull(ormResource().getEntityMappings().getDescription());
	}
	
	public void testUpdatePackage() throws Exception {
		assertNull(entityMappings().getPackage());
		assertNull(ormResource().getEntityMappings().getPackage());
		
		//set package in the resource model, verify context model updated
		ormResource().getEntityMappings().setPackage("foo.model");
		assertEquals("foo.model", entityMappings().getPackage());
		assertEquals("foo.model", ormResource().getEntityMappings().getPackage());
		
		//set package to null in the resource model
		ormResource().getEntityMappings().setPackage(null);
		assertNull(entityMappings().getPackage());
		assertNull(ormResource().getEntityMappings().getPackage());
	}
	
	public void testModifyPackage() throws Exception {
		assertNull(entityMappings().getPackage());
		assertNull(ormResource().getEntityMappings().getPackage());
		
		//set package in the context model, verify resource model modified
		entityMappings().setPackage("foo.model");
		assertEquals("foo.model", entityMappings().getPackage());
		assertEquals("foo.model", ormResource().getEntityMappings().getPackage());

		//set package to null in the context model
		entityMappings().setPackage(null);
		assertNull(entityMappings().getPackage());
		assertNull(ormResource().getEntityMappings().getPackage());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
		
		//set schema in the resource model, verify context model updated
		ormResource().getEntityMappings().setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", entityMappings().getSpecifiedSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getSchema());

		//set schema to null in the resource model
		ormResource().getEntityMappings().setSchema(null);
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
		
		//set schema in the context model, verify resource model modified
		entityMappings().setSpecifiedSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", entityMappings().getSpecifiedSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getSchema());

		//set schema to null in the context model
		entityMappings().setSpecifiedSchema(null);
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
	}

	public void testUpdateSpecifiedCatalog() throws Exception {
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
		
		//set catalog in the resource model, verify context model updated
		ormResource().getEntityMappings().setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", entityMappings().getSpecifiedCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getCatalog());

		//set catalog to null in the resource model
		ormResource().getEntityMappings().setCatalog(null);
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
	}
	
	public void testUpdateDefaultSchema() throws Exception {
		assertNull(entityMappings().getDefaultSchema());
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
	
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		org.eclipse.jpt.core.resource.orm.PersistenceUnitDefaults persistenceUnitDefaults = OrmFactory.eINSTANCE.createPersistenceUnitDefaults();
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(persistenceUnitDefaults);
		persistenceUnitDefaults.setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", entityMappings().getDefaultSchema());
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	
		persistenceUnitDefaults.setSchema(null);
		ormResource().save(null);
		assertNull(entityMappings().getDefaultSchema());
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	}
	
	public void testUpdateSchema() throws Exception {
		assertNull(entityMappings().getDefaultSchema());
		assertNull(entityMappings().getSchema());
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
	
		ormResource().getEntityMappings().setSchema("MY_SCHEMA");
		assertNull(entityMappings().getDefaultSchema());
		assertEquals("MY_SCHEMA", entityMappings().getSchema());
		assertEquals("MY_SCHEMA", entityMappings().getSpecifiedSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getSchema());
		
		ormResource().getEntityMappings().setSchema(null);
		assertNull(entityMappings().getDefaultSchema());
		assertNull(entityMappings().getSchema());
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());

		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("DEFAULT_SCHEMA");
		assertEquals("DEFAULT_SCHEMA", entityMappings().getDefaultSchema());
		assertEquals("DEFAULT_SCHEMA", entityMappings().getSchema());
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema(null);
		assertNull(entityMappings().getDefaultSchema());
		assertNull(entityMappings().getSchema());
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
	}	
	
	public void testModifySpecifiedCatalog() throws Exception {
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
		
		//set catalog in the context model, verify resource model modified
		entityMappings().setSpecifiedCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", entityMappings().getSpecifiedCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getCatalog());
		
		//set catalog to null in the context model
		entityMappings().setSpecifiedCatalog(null);
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
	}
	
	public void testUpdateDefaultCatalog() throws Exception {
		assertNull(entityMappings().getDefaultCatalog());
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
	
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		org.eclipse.jpt.core.resource.orm.PersistenceUnitDefaults persistenceUnitDefaults = OrmFactory.eINSTANCE.createPersistenceUnitDefaults();
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(persistenceUnitDefaults);
		persistenceUnitDefaults.setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", entityMappings().getDefaultCatalog());
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	
		persistenceUnitDefaults.setCatalog(null);
		assertNull(entityMappings().getDefaultCatalog());
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	}
	
	public void testUpdateCatalog() throws Exception {
		assertNull(entityMappings().getDefaultCatalog());
		assertNull(entityMappings().getCatalog());
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
	
		ormResource().getEntityMappings().setCatalog("MY_CATALOG");
		assertNull(entityMappings().getDefaultCatalog());
		assertEquals("MY_CATALOG", entityMappings().getCatalog());
		assertEquals("MY_CATALOG", entityMappings().getSpecifiedCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getCatalog());
		
		ormResource().getEntityMappings().setCatalog(null);
		assertNull(entityMappings().getDefaultCatalog());
		assertNull(entityMappings().getCatalog());
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());

		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("DEFAULT_CATALOG");
		assertEquals("DEFAULT_CATALOG", entityMappings().getDefaultCatalog());
		assertEquals("DEFAULT_CATALOG", entityMappings().getCatalog());
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog(null);
		assertNull(entityMappings().getDefaultCatalog());
		assertNull(entityMappings().getCatalog());
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
	}	

	public void testUpdateSpecifiedAccess() throws Exception {
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
		
		//set access in the resource model, verify context model updated
		ormResource().getEntityMappings().setAccess(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, entityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, ormResource().getEntityMappings().getAccess());
		
		//set access to null in the resource model
		ormResource().getEntityMappings().setAccess(null);
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
		
		//set access in the context model, verify resource model modified
		entityMappings().setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, entityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, ormResource().getEntityMappings().getAccess());
		
		//set access to null in the context model
		entityMappings().setSpecifiedAccess(null);
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
	}
	
	public void testUpdateDefaultAccess() throws Exception {
		assertNull(entityMappings().getDefaultAccess());
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
	
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		org.eclipse.jpt.core.resource.orm.PersistenceUnitDefaults persistenceUnitDefaults = OrmFactory.eINSTANCE.createPersistenceUnitDefaults();
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(persistenceUnitDefaults);
		persistenceUnitDefaults.setAccess(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, entityMappings().getDefaultAccess());
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
		
		persistenceUnitDefaults.setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, entityMappings().getDefaultAccess());
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
		
		persistenceUnitDefaults.setAccess(null);
		assertNull(entityMappings().getDefaultAccess());
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
	}

	public void testUpdateAccess() throws Exception {
		assertNull(entityMappings().getAccess());
		assertNull(entityMappings().getDefaultAccess());
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
	
		ormResource().getEntityMappings().setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertNull(entityMappings().getDefaultAccess());
		assertEquals(AccessType.FIELD, entityMappings().getAccess());
		assertEquals(AccessType.FIELD, entityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.FIELD, ormResource().getEntityMappings().getAccess());
		
		ormResource().getEntityMappings().setAccess(null);
		assertNull(entityMappings().getAccess());
		assertNull(entityMappings().getDefaultAccess());
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());

		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, entityMappings().getDefaultAccess());
		assertEquals(AccessType.FIELD, entityMappings().getAccess());
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertNull(entityMappings().getDefaultAccess());
		assertNull(entityMappings().getAccess());
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
	}	

	
	public void testUpdateOrmPersistentTypes() throws Exception {
		assertFalse(entityMappings().ormPersistentTypes().hasNext());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		//add embeddable in the resource model, verify context model updated
		XmlEmbeddable embeddable = OrmFactory.eINSTANCE.createEmbeddable();
		ormResource().getEntityMappings().getEmbeddables().add(embeddable);
		embeddable.setClassName("model.Foo");
		assertTrue(entityMappings().ormPersistentTypes().hasNext());
		assertEquals("model.Foo", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo", ormResource().getEntityMappings().getEmbeddables().get(0).getClassName());
		
		//add entity in the resource model, verify context model updated
		XmlEntity entity = OrmFactory.eINSTANCE.createEntity();
		ormResource().getEntityMappings().getEntities().add(entity);
		entity.setClassName("model.Foo2");
		assertTrue(entityMappings().ormPersistentTypes().hasNext());
		assertEquals("model.Foo2", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo2", ormResource().getEntityMappings().getEntities().get(0).getClassName());

		//add mapped-superclass in the resource model, verify context model updated
		XmlMappedSuperclass mappedSuperclass = OrmFactory.eINSTANCE.createMappedSuperclass();
		ormResource().getEntityMappings().getMappedSuperclasses().add(mappedSuperclass);
		mappedSuperclass.setClassName("model.Foo3");
		assertTrue(entityMappings().ormPersistentTypes().hasNext());
		assertEquals("model.Foo3", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo3", ormResource().getEntityMappings().getMappedSuperclasses().get(0).getClassName());
	}
	
	
	public void testAddOrmPersistentType() throws Exception {
		assertFalse(entityMappings().ormPersistentTypes().hasNext());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		//add embeddable in the context model, verify resource model modified
		entityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		assertTrue(entityMappings().ormPersistentTypes().hasNext());
		assertEquals("model.Foo", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
		assertEquals(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, entityMappings().ormPersistentTypes().next().getMapping().getKey());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo", ormResource().getEntityMappings().getEmbeddables().get(0).getClassName());

		//add entity in the context model, verify resource model modified
		entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		assertTrue(entityMappings().ormPersistentTypes().hasNext());
		assertEquals("model.Foo2", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, entityMappings().ormPersistentTypes().next().getMapping().getKey());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo2", ormResource().getEntityMappings().getEntities().get(0).getClassName());

		//add mapped-superclass in the context model, verify resource model modified
		entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
		assertTrue(entityMappings().ormPersistentTypes().hasNext());
		assertEquals("model.Foo3", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, entityMappings().ormPersistentTypes().next().getMapping().getKey());
		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo3", ormResource().getEntityMappings().getMappedSuperclasses().get(0).getClassName());
	}
	
	public void testRemoveOrmPersistentType() throws Exception {
		assertFalse(entityMappings().ormPersistentTypes().hasNext());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		entityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
		
		ormResource().save(null);
		//remove ormPersistentType from the context model, verify resource model modified
		entityMappings().removeOrmPersistentType(1);
		ormResource().save(null);
		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		entityMappings().removeOrmPersistentType(1);
		ormResource().save(null);
		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		entityMappings().removeOrmPersistentType(0);
		ormResource().save(null);
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
	}
	
	public void testUpdateTableGenerators() throws Exception {
		assertEquals(0, entityMappings().tableGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getTableGenerators().size());
		ormResource().save(null);
		XmlTableGenerator tableGeneratorResource = OrmFactory.eINSTANCE.createTableGeneratorImpl();
		ormResource().getEntityMappings().getTableGenerators().add(tableGeneratorResource);
		ormResource().save(null);
		tableGeneratorResource.setName("FOO");
		ormResource().save(null);
		
		TableGenerator tableGenerator = entityMappings().tableGenerators().next();
		assertEquals("FOO", tableGenerator.getName());
		
		
		XmlTableGenerator tableGeneratorResource2 = OrmFactory.eINSTANCE.createTableGeneratorImpl();
		ormResource().getEntityMappings().getTableGenerators().add(0, tableGeneratorResource2);
		tableGeneratorResource2.setName("BAR");
		ormResource().save(null);

		ListIterator<TableGenerator> tableGenerators = entityMappings().tableGenerators();
		assertEquals("BAR", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());

		
		XmlTableGenerator tableGeneratorResource3 = OrmFactory.eINSTANCE.createTableGeneratorImpl();
		ormResource().getEntityMappings().getTableGenerators().add(1, tableGeneratorResource3);
		tableGeneratorResource3.setName("BAZ");
		ormResource().save(null);

		tableGenerators = entityMappings().tableGenerators();
		assertEquals("BAR", tableGenerators.next().getName());
		assertEquals("BAZ", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
		
		ormResource().getEntityMappings().getTableGenerators().move(2, 0);
		ormResource().save(null);
		tableGenerators = entityMappings().tableGenerators();
		assertEquals("BAZ", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertEquals("BAR", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
	
		
		ormResource().getEntityMappings().getTableGenerators().remove(0);
		ormResource().save(null);
		tableGenerators = entityMappings().tableGenerators();
		assertEquals("FOO", tableGenerators.next().getName());
		assertEquals("BAR", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());

		ormResource().getEntityMappings().getTableGenerators().remove(1);
		ormResource().save(null);
		tableGenerators = entityMappings().tableGenerators();
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());

		ormResource().getEntityMappings().getTableGenerators().clear();
		ormResource().save(null);
		tableGenerators = entityMappings().tableGenerators();
		assertFalse(tableGenerators.hasNext());				
	}
	
	public void testAddTableGenerator() throws Exception {
		assertEquals(0, entityMappings().tableGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getTableGenerators().size());
		ormResource().save(null);
		entityMappings().addTableGenerator(0).setName("FOO");
		
		assertEquals("FOO", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		
		entityMappings().addTableGenerator(0).setName("BAR");
		assertEquals("BAR", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getTableGenerators().get(1).getName());
		assertEquals(2, ormResource().getEntityMappings().getTableGenerators().size());
		
		ListIterator<TableGenerator> tableGenerators = entityMappings().tableGenerators();
		assertEquals("BAR", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
	}
	
	public void testRemoveTableGenerator() throws Exception {
		assertEquals(0, entityMappings().tableGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getTableGenerators().size());
		
		TableGenerator tableGenerator = entityMappings().addTableGenerator(0);
		tableGenerator.setName("FOO");
		TableGenerator tableGenerator2 = entityMappings().addTableGenerator(1);
		tableGenerator2.setName("BAR");
		TableGenerator tableGenerator3 = entityMappings().addTableGenerator(2);
		tableGenerator3.setName("BAZ");
		assertEquals("FOO", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getTableGenerators().get(1).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getTableGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getTableGenerators().size());
		
		entityMappings().removeTableGenerator(0);
		assertEquals("BAR", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getTableGenerators().get(1).getName());
		
		ListIterator<TableGenerator> tableGenerators = entityMappings().tableGenerators();
		TableGenerator xmlTableGenerator = tableGenerators.next();
		assertEquals("BAR", xmlTableGenerator.getName());
		assertEquals(tableGenerator2, xmlTableGenerator);
		xmlTableGenerator = tableGenerators.next();
		assertEquals("BAZ", xmlTableGenerator.getName());
		assertEquals(tableGenerator3, xmlTableGenerator);
		assertFalse(tableGenerators.hasNext());

		
		entityMappings().removeTableGenerator(1);
		assertEquals("BAR", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		tableGenerators = entityMappings().tableGenerators();
		xmlTableGenerator = tableGenerators.next();
		assertEquals("BAR", xmlTableGenerator.getName());
		assertEquals(tableGenerator2, xmlTableGenerator);
		assertFalse(tableGenerators.hasNext());

		
		entityMappings().removeTableGenerator(0);
		assertEquals(0, ormResource().getEntityMappings().getTableGenerators().size());
		tableGenerators = entityMappings().tableGenerators();
		assertFalse(tableGenerators.hasNext());		
	}
	
	public void testMoveTableGenerator() throws Exception {
		assertEquals(0, entityMappings().tableGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getTableGenerators().size());
		
		TableGenerator tableGenerator = entityMappings().addTableGenerator(0);
		tableGenerator.setName("FOO");
		TableGenerator tableGenerator2 = entityMappings().addTableGenerator(1);
		tableGenerator2.setName("BAR");
		TableGenerator tableGenerator3 = entityMappings().addTableGenerator(2);
		tableGenerator3.setName("BAZ");
		assertEquals("FOO", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getTableGenerators().get(1).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getTableGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getTableGenerators().size());
		
		entityMappings().moveTableGenerator(2, 0);
		assertEquals("BAR", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getTableGenerators().get(1).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getTableGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getTableGenerators().size());
		
		entityMappings().moveTableGenerator(0, 2);
		assertEquals("FOO", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getTableGenerators().get(1).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getTableGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getTableGenerators().size());
	}
	
	public void testTableGeneratorsSize() throws Exception {
		assertEquals(0, entityMappings().tableGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getTableGenerators().size());
		
		
		TableGenerator tableGenerator = entityMappings().addTableGenerator(0);
		tableGenerator.setName("FOO");
		TableGenerator tableGenerator2 = entityMappings().addTableGenerator(1);
		tableGenerator2.setName("BAR");
		TableGenerator tableGenerator3 = entityMappings().addTableGenerator(2);
		tableGenerator3.setName("BAZ");
	
		assertEquals(3, entityMappings().tableGeneratorsSize());
		
		ormResource().getEntityMappings().getTableGenerators().remove(0);
		assertEquals(2, entityMappings().tableGeneratorsSize());
	}

	public void testUpdateSequenceGenerators() throws Exception {
		assertEquals(0, entityMappings().sequenceGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getSequenceGenerators().size());
		ormResource().save(null);
		
		XmlSequenceGenerator sequenceGeneratorResource = OrmFactory.eINSTANCE.createSequenceGeneratorImpl();
		ormResource().getEntityMappings().getSequenceGenerators().add(sequenceGeneratorResource);
		ormResource().save(null);
		sequenceGeneratorResource.setName("FOO");
		ormResource().save(null);
		
		SequenceGenerator sequenceGenerator = entityMappings().sequenceGenerators().next();
		assertEquals("FOO", sequenceGenerator.getName());
		
		
		XmlSequenceGenerator sequenceGeneratorResource2 = OrmFactory.eINSTANCE.createSequenceGeneratorImpl();
		ormResource().getEntityMappings().getSequenceGenerators().add(0, sequenceGeneratorResource2);
		sequenceGeneratorResource2.setName("BAR");
		ormResource().save(null);

		ListIterator<SequenceGenerator> sequenceGenerators = entityMappings().sequenceGenerators();
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());

		
		XmlSequenceGenerator sequenceGeneratorResource3 = OrmFactory.eINSTANCE.createSequenceGeneratorImpl();
		ormResource().getEntityMappings().getSequenceGenerators().add(1, sequenceGeneratorResource3);
		sequenceGeneratorResource3.setName("BAZ");
		ormResource().save(null);

		sequenceGenerators = entityMappings().sequenceGenerators();
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertEquals("BAZ", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
		
		ormResource().getEntityMappings().getSequenceGenerators().move(2, 0);
		ormResource().save(null);
		sequenceGenerators = entityMappings().sequenceGenerators();
		assertEquals("BAZ", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
	
		
		ormResource().getEntityMappings().getSequenceGenerators().remove(0);
		ormResource().save(null);
		sequenceGenerators = entityMappings().sequenceGenerators();
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());

		ormResource().getEntityMappings().getSequenceGenerators().remove(1);
		ormResource().save(null);
		sequenceGenerators = entityMappings().sequenceGenerators();
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());

		ormResource().getEntityMappings().getSequenceGenerators().clear();
		ormResource().save(null);
		sequenceGenerators = entityMappings().sequenceGenerators();
		assertFalse(sequenceGenerators.hasNext());				
	}
	
	public void testAddSequenceGenerator() throws Exception {
		assertEquals(0, entityMappings().sequenceGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		entityMappings().addSequenceGenerator(0).setName("FOO");
		
		assertEquals("FOO", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		
		entityMappings().addSequenceGenerator(0).setName("BAR");
		assertEquals("BAR", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals(2, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		ListIterator<SequenceGenerator> sequenceGenerators = entityMappings().sequenceGenerators();
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		assertEquals(0, entityMappings().sequenceGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);
		sequenceGenerator.setName("FOO");
		SequenceGenerator sequenceGenerator2 = entityMappings().addSequenceGenerator(1);
		sequenceGenerator2.setName("BAR");
		SequenceGenerator sequenceGenerator3 = entityMappings().addSequenceGenerator(2);
		sequenceGenerator3.setName("BAZ");
		assertEquals("FOO", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getSequenceGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		entityMappings().removeSequenceGenerator(0);
		assertEquals("BAR", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getSequenceGenerators().get(1).getName());
		
		ListIterator<SequenceGenerator> sequenceGenerators = entityMappings().sequenceGenerators();
		SequenceGenerator xmlSequenceGenerator = sequenceGenerators.next();
		assertEquals("BAR", xmlSequenceGenerator.getName());
		assertEquals(sequenceGenerator2, xmlSequenceGenerator);
		xmlSequenceGenerator = sequenceGenerators.next();
		assertEquals("BAZ", xmlSequenceGenerator.getName());
		assertEquals(sequenceGenerator3, xmlSequenceGenerator);
		assertFalse(sequenceGenerators.hasNext());

		
		entityMappings().removeSequenceGenerator(1);
		assertEquals("BAR", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		sequenceGenerators = entityMappings().sequenceGenerators();
		xmlSequenceGenerator = sequenceGenerators.next();
		assertEquals("BAR", xmlSequenceGenerator.getName());
		assertEquals(sequenceGenerator2, xmlSequenceGenerator);
		assertFalse(sequenceGenerators.hasNext());

		
		entityMappings().removeSequenceGenerator(0);
		assertEquals(0, ormResource().getEntityMappings().getSequenceGenerators().size());
		sequenceGenerators = entityMappings().sequenceGenerators();
		assertFalse(sequenceGenerators.hasNext());		
	}
	
	public void testMoveSequenceGenerator() throws Exception {
		assertEquals(0, entityMappings().sequenceGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);
		sequenceGenerator.setName("FOO");
		SequenceGenerator sequenceGenerator2 = entityMappings().addSequenceGenerator(1);
		sequenceGenerator2.setName("BAR");
		SequenceGenerator sequenceGenerator3 = entityMappings().addSequenceGenerator(2);
		sequenceGenerator3.setName("BAZ");
		assertEquals("FOO", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getSequenceGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		entityMappings().moveSequenceGenerator(2, 0);
		assertEquals("BAR", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getSequenceGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		entityMappings().moveSequenceGenerator(0, 2);
		assertEquals("FOO", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getSequenceGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getSequenceGenerators().size());
		
	}
	
	public void testSequenceGeneratorsSize() throws Exception {
		assertEquals(0, entityMappings().sequenceGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);
		sequenceGenerator.setName("FOO");
		SequenceGenerator sequenceGenerator2 = entityMappings().addSequenceGenerator(1);
		sequenceGenerator2.setName("BAR");
		SequenceGenerator sequenceGenerator3 = entityMappings().addSequenceGenerator(2);
		sequenceGenerator3.setName("BAZ");
	
		assertEquals(3, entityMappings().sequenceGeneratorsSize());
		
		ormResource().getEntityMappings().getSequenceGenerators().remove(0);
		assertEquals(2, entityMappings().sequenceGeneratorsSize());
	}

	
	public void testAddNamedQuery() throws Exception {
		GenericOrmNamedQuery namedQuery = entityMappings().addNamedQuery(0);
		namedQuery.setName("FOO");
				
		assertEquals("FOO", ormResource().getEntityMappings().getNamedQueries().get(0).getName());
		
		GenericOrmNamedQuery namedQuery2 = entityMappings().addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		assertEquals("BAR", ormResource().getEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getNamedQueries().get(1).getName());
		
		GenericOrmNamedQuery namedQuery3 = entityMappings().addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		assertEquals("BAR", ormResource().getEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getNamedQueries().get(1).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getNamedQueries().get(2).getName());
		
		ListIterator<GenericOrmNamedQuery> namedQuerys = entityMappings().namedQueries();
		assertEquals(namedQuery2, namedQuerys.next());
		assertEquals(namedQuery3, namedQuerys.next());
		assertEquals(namedQuery, namedQuerys.next());
		
		namedQuerys = entityMappings().namedQueries();
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());
	}
	
	public void testRemoveNamedQuery() throws Exception {
		entityMappings().addNamedQuery(0).setName("FOO");
		entityMappings().addNamedQuery(1).setName("BAR");
		entityMappings().addNamedQuery(2).setName("BAZ");
		
		assertEquals(3, ormResource().getEntityMappings().getNamedQueries().size());
		
		entityMappings().removeNamedQuery(0);
		assertEquals(2, ormResource().getEntityMappings().getNamedQueries().size());
		assertEquals("BAR", ormResource().getEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getNamedQueries().get(1).getName());

		entityMappings().removeNamedQuery(0);
		assertEquals(1, ormResource().getEntityMappings().getNamedQueries().size());
		assertEquals("BAZ", ormResource().getEntityMappings().getNamedQueries().get(0).getName());
		
		entityMappings().removeNamedQuery(0);
		assertEquals(0, ormResource().getEntityMappings().getNamedQueries().size());
	}
	
	public void testMoveNamedQuery() throws Exception {
		entityMappings().addNamedQuery(0).setName("FOO");
		entityMappings().addNamedQuery(1).setName("BAR");
		entityMappings().addNamedQuery(2).setName("BAZ");
		
		assertEquals(3, ormResource().getEntityMappings().getNamedQueries().size());
		
		
		entityMappings().moveNamedQuery(2, 0);
		ListIterator<GenericOrmNamedQuery> namedQuerys = entityMappings().namedQueries();
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());

		assertEquals("BAR", ormResource().getEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getNamedQueries().get(1).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getNamedQueries().get(2).getName());


		entityMappings().moveNamedQuery(0, 1);
		namedQuerys = entityMappings().namedQueries();
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());

		assertEquals("BAZ", ormResource().getEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getNamedQueries().get(1).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getNamedQueries().get(2).getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {		
		ormResource().getEntityMappings().getNamedQueries().add(OrmFactory.eINSTANCE.createNamedQuery());
		ormResource().getEntityMappings().getNamedQueries().add(OrmFactory.eINSTANCE.createNamedQuery());
		ormResource().getEntityMappings().getNamedQueries().add(OrmFactory.eINSTANCE.createNamedQuery());
		
		ormResource().getEntityMappings().getNamedQueries().get(0).setName("FOO");
		ormResource().getEntityMappings().getNamedQueries().get(1).setName("BAR");
		ormResource().getEntityMappings().getNamedQueries().get(2).setName("BAZ");

		ListIterator<GenericOrmNamedQuery> namedQuerys = entityMappings().namedQueries();
		assertEquals("FOO", namedQuerys.next().getName());
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("BAZ", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());
		
		ormResource().getEntityMappings().getNamedQueries().move(2, 0);
		namedQuerys = entityMappings().namedQueries();
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());

		ormResource().getEntityMappings().getNamedQueries().move(0, 1);
		namedQuerys = entityMappings().namedQueries();
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());

		ormResource().getEntityMappings().getNamedQueries().remove(1);
		namedQuerys = entityMappings().namedQueries();
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());

		ormResource().getEntityMappings().getNamedQueries().remove(1);
		namedQuerys = entityMappings().namedQueries();
		assertEquals("BAZ", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());
		
		ormResource().getEntityMappings().getNamedQueries().remove(0);
		assertFalse(entityMappings().namedQueries().hasNext());
	}
	
	public void testAddNamedNativeQuery() throws Exception {
		GenericOrmNamedNativeQuery namedNativeQuery = entityMappings().addNamedNativeQuery(0);
		namedNativeQuery.setName("FOO");
				
		assertEquals("FOO", ormResource().getEntityMappings().getNamedNativeQueries().get(0).getName());
		
		GenericOrmNamedNativeQuery namedNativeQuery2 = entityMappings().addNamedNativeQuery(0);
		namedNativeQuery2.setName("BAR");
		
		assertEquals("BAR", ormResource().getEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getNamedNativeQueries().get(1).getName());
		
		GenericOrmNamedNativeQuery namedNativeQuery3 = entityMappings().addNamedNativeQuery(1);
		namedNativeQuery3.setName("BAZ");
		
		assertEquals("BAR", ormResource().getEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getNamedNativeQueries().get(2).getName());
		
		ListIterator<GenericOrmNamedNativeQuery> namedNativeQuerys = entityMappings().namedNativeQueries();
		assertEquals(namedNativeQuery2, namedNativeQuerys.next());
		assertEquals(namedNativeQuery3, namedNativeQuerys.next());
		assertEquals(namedNativeQuery, namedNativeQuerys.next());
		
		namedNativeQuerys = entityMappings().namedNativeQueries();
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());
	}
	
	public void testRemoveNamedNativeQuery() throws Exception {
		entityMappings().addNamedNativeQuery(0).setName("FOO");
		entityMappings().addNamedNativeQuery(1).setName("BAR");
		entityMappings().addNamedNativeQuery(2).setName("BAZ");
		
		assertEquals(3, ormResource().getEntityMappings().getNamedNativeQueries().size());
		
		entityMappings().removeNamedNativeQuery(0);
		assertEquals(2, ormResource().getEntityMappings().getNamedNativeQueries().size());
		assertEquals("BAR", ormResource().getEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getNamedNativeQueries().get(1).getName());

		entityMappings().removeNamedNativeQuery(0);
		assertEquals(1, ormResource().getEntityMappings().getNamedNativeQueries().size());
		assertEquals("BAZ", ormResource().getEntityMappings().getNamedNativeQueries().get(0).getName());
		
		entityMappings().removeNamedNativeQuery(0);
		assertEquals(0, ormResource().getEntityMappings().getNamedNativeQueries().size());
	}
	
	public void testMoveNamedNativeQuery() throws Exception {
		entityMappings().addNamedNativeQuery(0).setName("FOO");
		entityMappings().addNamedNativeQuery(1).setName("BAR");
		entityMappings().addNamedNativeQuery(2).setName("BAZ");
		
		assertEquals(3, ormResource().getEntityMappings().getNamedNativeQueries().size());
		
		
		entityMappings().moveNamedNativeQuery(2, 0);
		ListIterator<GenericOrmNamedNativeQuery> namedNativeQuerys = entityMappings().namedNativeQueries();
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());

		assertEquals("BAR", ormResource().getEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getNamedNativeQueries().get(2).getName());


		entityMappings().moveNamedNativeQuery(0, 1);
		namedNativeQuerys = entityMappings().namedNativeQueries();
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());

		assertEquals("BAZ", ormResource().getEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", ormResource().getEntityMappings().getNamedNativeQueries().get(2).getName());
	}
	
	public void testUpdateNamedNativeQueries() throws Exception {
		ormResource().getEntityMappings().getNamedNativeQueries().add(OrmFactory.eINSTANCE.createNamedNativeQuery());
		ormResource().getEntityMappings().getNamedNativeQueries().add(OrmFactory.eINSTANCE.createNamedNativeQuery());
		ormResource().getEntityMappings().getNamedNativeQueries().add(OrmFactory.eINSTANCE.createNamedNativeQuery());
		
		ormResource().getEntityMappings().getNamedNativeQueries().get(0).setName("FOO");
		ormResource().getEntityMappings().getNamedNativeQueries().get(1).setName("BAR");
		ormResource().getEntityMappings().getNamedNativeQueries().get(2).setName("BAZ");

		ListIterator<GenericOrmNamedNativeQuery> namedNativeQuerys = entityMappings().namedNativeQueries();
		assertEquals("FOO", namedNativeQuerys.next().getName());
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());
		
		ormResource().getEntityMappings().getNamedNativeQueries().move(2, 0);
		namedNativeQuerys = entityMappings().namedNativeQueries();
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());

		ormResource().getEntityMappings().getNamedNativeQueries().move(0, 1);
		namedNativeQuerys = entityMappings().namedNativeQueries();
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());

		ormResource().getEntityMappings().getNamedNativeQueries().remove(1);
		namedNativeQuerys = entityMappings().namedNativeQueries();
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());

		ormResource().getEntityMappings().getNamedNativeQueries().remove(1);
		namedNativeQuerys = entityMappings().namedNativeQueries();
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());
		
		ormResource().getEntityMappings().getNamedNativeQueries().remove(0);
		assertFalse(entityMappings().namedNativeQueries().hasNext());
	}
}