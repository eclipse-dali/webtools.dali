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
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.orm.TableGenerator;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
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
		org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults persistenceUnitDefaults = OrmFactory.eINSTANCE.createPersistenceUnitDefaults();
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
		org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults persistenceUnitDefaults = OrmFactory.eINSTANCE.createPersistenceUnitDefaults();
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
		ormResource().getEntityMappings().setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, entityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, ormResource().getEntityMappings().getAccess());
		
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
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, ormResource().getEntityMappings().getAccess());
		
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
		org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults persistenceUnitDefaults = OrmFactory.eINSTANCE.createPersistenceUnitDefaults();
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(persistenceUnitDefaults);
		persistenceUnitDefaults.setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, entityMappings().getDefaultAccess());
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
		
		persistenceUnitDefaults.setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD);
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
	
		ormResource().getEntityMappings().setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD);
		assertNull(entityMappings().getDefaultAccess());
		assertEquals(AccessType.FIELD, entityMappings().getAccess());
		assertEquals(AccessType.FIELD, entityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD, ormResource().getEntityMappings().getAccess());
		
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

	
	public void testUpdateXmlPersistentTypes() throws Exception {
		assertFalse(entityMappings().xmlPersistentTypes().hasNext());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		//add embeddable in the resource model, verify context model updated
		Embeddable embeddable = OrmFactory.eINSTANCE.createEmbeddable();
		ormResource().getEntityMappings().getEmbeddables().add(embeddable);
		embeddable.setClassName("model.Foo");
		assertTrue(entityMappings().xmlPersistentTypes().hasNext());
		assertEquals("model.Foo", entityMappings().xmlPersistentTypes().next().getMapping().getClass_());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo", ormResource().getEntityMappings().getEmbeddables().get(0).getClassName());
		
		//add entity in the resource model, verify context model updated
		Entity entity = OrmFactory.eINSTANCE.createEntity();
		ormResource().getEntityMappings().getEntities().add(entity);
		entity.setClassName("model.Foo2");
		assertTrue(entityMappings().xmlPersistentTypes().hasNext());
		assertEquals("model.Foo2", entityMappings().xmlPersistentTypes().next().getMapping().getClass_());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo2", ormResource().getEntityMappings().getEntities().get(0).getClassName());

		//add mapped-superclass in the resource model, verify context model updated
		MappedSuperclass mappedSuperclass = OrmFactory.eINSTANCE.createMappedSuperclass();
		ormResource().getEntityMappings().getMappedSuperclasses().add(mappedSuperclass);
		mappedSuperclass.setClassName("model.Foo3");
		assertTrue(entityMappings().xmlPersistentTypes().hasNext());
		assertEquals("model.Foo3", entityMappings().xmlPersistentTypes().next().getMapping().getClass_());
		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo3", ormResource().getEntityMappings().getMappedSuperclasses().get(0).getClassName());
	}
	
	
	public void testAddXmlPersistentType() throws Exception {
		assertFalse(entityMappings().xmlPersistentTypes().hasNext());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		//add embeddable in the context model, verify resource model modified
		entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		assertTrue(entityMappings().xmlPersistentTypes().hasNext());
		assertEquals("model.Foo", entityMappings().xmlPersistentTypes().next().getMapping().getClass_());
		assertEquals(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, entityMappings().xmlPersistentTypes().next().getMapping().getKey());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo", ormResource().getEntityMappings().getEmbeddables().get(0).getClassName());

		//add entity in the context model, verify resource model modified
		entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		assertTrue(entityMappings().xmlPersistentTypes().hasNext());
		assertEquals("model.Foo2", entityMappings().xmlPersistentTypes().next().getMapping().getClass_());
		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, entityMappings().xmlPersistentTypes().next().getMapping().getKey());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo2", ormResource().getEntityMappings().getEntities().get(0).getClassName());

		//add mapped-superclass in the context model, verify resource model modified
		entityMappings().addXmlPersistentType(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
		assertTrue(entityMappings().xmlPersistentTypes().hasNext());
		assertEquals("model.Foo3", entityMappings().xmlPersistentTypes().next().getMapping().getClass_());
		assertEquals(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, entityMappings().xmlPersistentTypes().next().getMapping().getKey());
		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo3", ormResource().getEntityMappings().getMappedSuperclasses().get(0).getClassName());
	}
	
	public void testRemoveXmlPersistentType() throws Exception {
		assertFalse(entityMappings().xmlPersistentTypes().hasNext());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		entityMappings().addXmlPersistentType(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
		
		ormResource().save(null);
		//remove xmlPersistentType from the context model, verify resource model modified
		entityMappings().removeXmlPersistentType(1);
		ormResource().save(null);
		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		entityMappings().removeXmlPersistentType(1);
		ormResource().save(null);
		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		entityMappings().removeXmlPersistentType(0);
		ormResource().save(null);
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
	}
	
	public void testUpdateTableGenerators() throws Exception {
		assertEquals(0, entityMappings().tableGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getTableGenerators().size());
		ormResource().save(null);
		TableGenerator tableGeneratorResource = OrmFactory.eINSTANCE.createTableGenerator();
		ormResource().getEntityMappings().getTableGenerators().add(tableGeneratorResource);
		ormResource().save(null);
		tableGeneratorResource.setName("FOO");
		ormResource().save(null);
		
		ITableGenerator tableGenerator = entityMappings().tableGenerators().next();
		assertEquals("FOO", tableGenerator.getName());
		
		
		TableGenerator tableGeneratorResource2 = OrmFactory.eINSTANCE.createTableGenerator();
		ormResource().getEntityMappings().getTableGenerators().add(0, tableGeneratorResource2);
		tableGeneratorResource2.setName("BAR");
		ormResource().save(null);

		ListIterator<ITableGenerator> tableGenerators = entityMappings().tableGenerators();
		assertEquals("BAR", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());

		
		TableGenerator tableGeneratorResource3 = OrmFactory.eINSTANCE.createTableGenerator();
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
		
		ListIterator<ITableGenerator> tableGenerators = entityMappings().tableGenerators();
		assertEquals("BAR", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
	}
	
	public void testRemoveTableGenerator() throws Exception {
		assertEquals(0, entityMappings().tableGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getTableGenerators().size());
		
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);
		tableGenerator.setName("FOO");
		ITableGenerator tableGenerator2 = entityMappings().addTableGenerator(1);
		tableGenerator2.setName("BAR");
		ITableGenerator tableGenerator3 = entityMappings().addTableGenerator(2);
		tableGenerator3.setName("BAZ");
		assertEquals("FOO", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getTableGenerators().get(1).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getTableGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getTableGenerators().size());
		
		entityMappings().removeTableGenerator(0);
		assertEquals("BAR", ormResource().getEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getTableGenerators().get(1).getName());
		
		ListIterator<ITableGenerator> tableGenerators = entityMappings().tableGenerators();
		ITableGenerator xmlTableGenerator = tableGenerators.next();
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
		
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);
		tableGenerator.setName("FOO");
		ITableGenerator tableGenerator2 = entityMappings().addTableGenerator(1);
		tableGenerator2.setName("BAR");
		ITableGenerator tableGenerator3 = entityMappings().addTableGenerator(2);
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
		
		
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);
		tableGenerator.setName("FOO");
		ITableGenerator tableGenerator2 = entityMappings().addTableGenerator(1);
		tableGenerator2.setName("BAR");
		ITableGenerator tableGenerator3 = entityMappings().addTableGenerator(2);
		tableGenerator3.setName("BAZ");
	
		assertEquals(3, entityMappings().tableGeneratorsSize());
		
		ormResource().getEntityMappings().getTableGenerators().remove(0);
		assertEquals(2, entityMappings().tableGeneratorsSize());
	}

	public void testUpdateSequenceGenerators() throws Exception {
		assertEquals(0, entityMappings().sequenceGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getSequenceGenerators().size());
		ormResource().save(null);
		
		SequenceGenerator sequenceGeneratorResource = OrmFactory.eINSTANCE.createSequenceGenerator();
		ormResource().getEntityMappings().getSequenceGenerators().add(sequenceGeneratorResource);
		ormResource().save(null);
		sequenceGeneratorResource.setName("FOO");
		ormResource().save(null);
		
		ISequenceGenerator sequenceGenerator = entityMappings().sequenceGenerators().next();
		assertEquals("FOO", sequenceGenerator.getName());
		
		
		SequenceGenerator sequenceGeneratorResource2 = OrmFactory.eINSTANCE.createSequenceGenerator();
		ormResource().getEntityMappings().getSequenceGenerators().add(0, sequenceGeneratorResource2);
		sequenceGeneratorResource2.setName("BAR");
		ormResource().save(null);

		ListIterator<ISequenceGenerator> sequenceGenerators = entityMappings().sequenceGenerators();
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());

		
		SequenceGenerator sequenceGeneratorResource3 = OrmFactory.eINSTANCE.createSequenceGenerator();
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
		
		ListIterator<ISequenceGenerator> sequenceGenerators = entityMappings().sequenceGenerators();
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		assertEquals(0, entityMappings().sequenceGeneratorsSize());
		assertEquals(0, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		ISequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);
		sequenceGenerator.setName("FOO");
		ISequenceGenerator sequenceGenerator2 = entityMappings().addSequenceGenerator(1);
		sequenceGenerator2.setName("BAR");
		ISequenceGenerator sequenceGenerator3 = entityMappings().addSequenceGenerator(2);
		sequenceGenerator3.setName("BAZ");
		assertEquals("FOO", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAR", ormResource().getEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getSequenceGenerators().get(2).getName());
		assertEquals(3, ormResource().getEntityMappings().getSequenceGenerators().size());
		
		entityMappings().removeSequenceGenerator(0);
		assertEquals("BAR", ormResource().getEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAZ", ormResource().getEntityMappings().getSequenceGenerators().get(1).getName());
		
		ListIterator<ISequenceGenerator> sequenceGenerators = entityMappings().sequenceGenerators();
		ISequenceGenerator xmlSequenceGenerator = sequenceGenerators.next();
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
		
		ISequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);
		sequenceGenerator.setName("FOO");
		ISequenceGenerator sequenceGenerator2 = entityMappings().addSequenceGenerator(1);
		sequenceGenerator2.setName("BAR");
		ISequenceGenerator sequenceGenerator3 = entityMappings().addSequenceGenerator(2);
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
		
		
		ISequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);
		sequenceGenerator.setName("FOO");
		ISequenceGenerator sequenceGenerator2 = entityMappings().addSequenceGenerator(1);
		sequenceGenerator2.setName("BAR");
		ISequenceGenerator sequenceGenerator3 = entityMappings().addSequenceGenerator(2);
		sequenceGenerator3.setName("BAZ");
	
		assertEquals(3, entityMappings().sequenceGeneratorsSize());
		
		ormResource().getEntityMappings().getSequenceGenerators().remove(0);
		assertEquals(2, entityMappings().sequenceGeneratorsSize());
	}

}