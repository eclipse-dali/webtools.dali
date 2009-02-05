/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
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
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	public void testGetVersion() throws Exception {
		assertEquals("1.0", getEntityMappings().getVersion());
	}
	
	public void testUpdateDescription() throws Exception {
		assertNull(getEntityMappings().getDescription());
		assertNull(getXmlEntityMappings().getDescription());
		
		//set description in the resource model, verify context model updated
		getXmlEntityMappings().setDescription("newDescription");
		assertEquals("newDescription", getEntityMappings().getDescription());
		assertEquals("newDescription", getXmlEntityMappings().getDescription());
	
		//set description to null in the resource model
		getXmlEntityMappings().setDescription(null);
		assertNull(getEntityMappings().getDescription());
		assertNull(getXmlEntityMappings().getDescription());
	}
	
	public void testModifyDescription() throws Exception {
		assertNull(getEntityMappings().getDescription());
		assertNull(getXmlEntityMappings().getDescription());
		
		//set description in the context model, verify resource model modified
		getEntityMappings().setDescription("newDescription");
		assertEquals("newDescription", getEntityMappings().getDescription());
		assertEquals("newDescription", getXmlEntityMappings().getDescription());
		
		//set description to null in the context model
		getEntityMappings().setDescription(null);
		assertNull(getEntityMappings().getDescription());
		assertNull(getXmlEntityMappings().getDescription());
	}
	
	public void testUpdatePackage() throws Exception {
		assertNull(getEntityMappings().getPackage());
		assertNull(getXmlEntityMappings().getPackage());
		
		//set package in the resource model, verify context model updated
		getXmlEntityMappings().setPackage("foo.model");
		assertEquals("foo.model", getEntityMappings().getPackage());
		assertEquals("foo.model", getXmlEntityMappings().getPackage());
		
		//set package to null in the resource model
		getXmlEntityMappings().setPackage(null);
		assertNull(getEntityMappings().getPackage());
		assertNull(getXmlEntityMappings().getPackage());
	}
	
	public void testModifyPackage() throws Exception {
		assertNull(getEntityMappings().getPackage());
		assertNull(getXmlEntityMappings().getPackage());
		
		//set package in the context model, verify resource model modified
		getEntityMappings().setPackage("foo.model");
		assertEquals("foo.model", getEntityMappings().getPackage());
		assertEquals("foo.model", getXmlEntityMappings().getPackage());

		//set package to null in the context model
		getEntityMappings().setPackage(null);
		assertNull(getEntityMappings().getPackage());
		assertNull(getXmlEntityMappings().getPackage());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
		
		//set schema in the resource model, verify context model updated
		getXmlEntityMappings().setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", getEntityMappings().getSpecifiedSchema());
		assertEquals("MY_SCHEMA", getXmlEntityMappings().getSchema());

		//set schema to null in the resource model
		getXmlEntityMappings().setSchema(null);
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
		
		//set schema in the context model, verify resource model modified
		getEntityMappings().setSpecifiedSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", getEntityMappings().getSpecifiedSchema());
		assertEquals("MY_SCHEMA", getXmlEntityMappings().getSchema());

		//set schema to null in the context model
		getEntityMappings().setSpecifiedSchema(null);
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
	}

	public void testUpdateSpecifiedCatalog() throws Exception {
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
		
		//set catalog in the resource model, verify context model updated
		getXmlEntityMappings().setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", getEntityMappings().getSpecifiedCatalog());
		assertEquals("MY_CATALOG", getXmlEntityMappings().getCatalog());

		//set catalog to null in the resource model
		getXmlEntityMappings().setCatalog(null);
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
	}
	
	public void testUpdateDefaultSchema() throws Exception {
		assertNull(getEntityMappings().getDefaultSchema());
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
	
		getXmlEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults persistenceUnitDefaults = OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(persistenceUnitDefaults);
		persistenceUnitDefaults.setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", getEntityMappings().getDefaultSchema());
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
		assertEquals("MY_SCHEMA", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	
		persistenceUnitDefaults.setSchema(null);
		assertNull(getEntityMappings().getDefaultSchema());
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	}
	
	public void testUpdateSchema() throws Exception {
		assertNull(getEntityMappings().getDefaultSchema());
		assertNull(getEntityMappings().getSchema());
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
	
		getXmlEntityMappings().setSchema("MY_SCHEMA");
		assertNull(getEntityMappings().getDefaultSchema());
		assertEquals("MY_SCHEMA", getEntityMappings().getSchema());
		assertEquals("MY_SCHEMA", getEntityMappings().getSpecifiedSchema());
		assertEquals("MY_SCHEMA", getXmlEntityMappings().getSchema());
		
		getXmlEntityMappings().setSchema(null);
		assertNull(getEntityMappings().getDefaultSchema());
		assertNull(getEntityMappings().getSchema());
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("DEFAULT_SCHEMA");
		assertEquals("DEFAULT_SCHEMA", getEntityMappings().getDefaultSchema());
		assertEquals("DEFAULT_SCHEMA", getEntityMappings().getSchema());
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema(null);
		assertNull(getEntityMappings().getDefaultSchema());
		assertNull(getEntityMappings().getSchema());
		assertNull(getEntityMappings().getSpecifiedSchema());
		assertNull(getXmlEntityMappings().getSchema());
	}	
	
	public void testModifySpecifiedCatalog() throws Exception {
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
		
		//set catalog in the context model, verify resource model modified
		getEntityMappings().setSpecifiedCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", getEntityMappings().getSpecifiedCatalog());
		assertEquals("MY_CATALOG", getXmlEntityMappings().getCatalog());
		
		//set catalog to null in the context model
		getEntityMappings().setSpecifiedCatalog(null);
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
	}
	
	public void testUpdateDefaultCatalog() throws Exception {
		assertNull(getEntityMappings().getDefaultCatalog());
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
	
		getXmlEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults persistenceUnitDefaults = OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(persistenceUnitDefaults);
		persistenceUnitDefaults.setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", getEntityMappings().getDefaultCatalog());
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
		assertEquals("MY_CATALOG", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	
		persistenceUnitDefaults.setCatalog(null);
		assertNull(getEntityMappings().getDefaultCatalog());
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	}
	
	public void testUpdateCatalog() throws Exception {
		assertNull(getEntityMappings().getDefaultCatalog());
		assertNull(getEntityMappings().getCatalog());
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
	
		getXmlEntityMappings().setCatalog("MY_CATALOG");
		assertNull(getEntityMappings().getDefaultCatalog());
		assertEquals("MY_CATALOG", getEntityMappings().getCatalog());
		assertEquals("MY_CATALOG", getEntityMappings().getSpecifiedCatalog());
		assertEquals("MY_CATALOG", getXmlEntityMappings().getCatalog());
		
		getXmlEntityMappings().setCatalog(null);
		assertNull(getEntityMappings().getDefaultCatalog());
		assertNull(getEntityMappings().getCatalog());
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog("DEFAULT_CATALOG");
		assertEquals("DEFAULT_CATALOG", getEntityMappings().getDefaultCatalog());
		assertEquals("DEFAULT_CATALOG", getEntityMappings().getCatalog());
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog(null);
		assertNull(getEntityMappings().getDefaultCatalog());
		assertNull(getEntityMappings().getCatalog());
		assertNull(getEntityMappings().getSpecifiedCatalog());
		assertNull(getXmlEntityMappings().getCatalog());
	}	

	public void testUpdateSpecifiedAccess() throws Exception {
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		//set access in the resource model, verify context model updated
		getXmlEntityMappings().setAccess(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, getEntityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, getXmlEntityMappings().getAccess());
		
		//set access to null in the resource model
		getXmlEntityMappings().setAccess(null);
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		//set access in the context model, verify resource model modified
		getEntityMappings().setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, getEntityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, getXmlEntityMappings().getAccess());
		
		//set access to null in the context model
		getEntityMappings().setSpecifiedAccess(null);
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	}
	
	public void testUpdateDefaultAccess() throws Exception {
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	
		getXmlEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults persistenceUnitDefaults = OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(persistenceUnitDefaults);
		persistenceUnitDefaults.setAccess(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		persistenceUnitDefaults.setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		persistenceUnitDefaults.setAccess(null);
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	}

	public void testUpdateAccess() throws Exception {
		assertNull(getEntityMappings().getAccess());
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	
		getXmlEntityMappings().setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertNull(getEntityMappings().getDefaultAccess());
		assertEquals(AccessType.FIELD, getEntityMappings().getAccess());
		assertEquals(AccessType.FIELD, getEntityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.FIELD, getXmlEntityMappings().getAccess());
		
		getXmlEntityMappings().setAccess(null);
		assertNull(getEntityMappings().getAccess());
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, getEntityMappings().getDefaultAccess());
		assertEquals(AccessType.FIELD, getEntityMappings().getAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	}	

	
	public void testUpdateOrmPersistentTypes() throws Exception {
		assertFalse(getEntityMappings().persistentTypes().hasNext());
		assertTrue(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getXmlEntityMappings().getEntities().isEmpty());
		assertTrue(getXmlEntityMappings().getEmbeddables().isEmpty());
		
		//add embeddable in the resource model, verify context model updated
		XmlEmbeddable embeddable = OrmFactory.eINSTANCE.createXmlEmbeddable();
		getXmlEntityMappings().getEmbeddables().add(embeddable);
		embeddable.setClassName("model.Foo");
		assertTrue(getEntityMappings().persistentTypes().hasNext());
		assertEquals("model.Foo", getEntityMappings().persistentTypes().next().getMapping().getClass_());
		assertTrue(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getXmlEntityMappings().getEntities().isEmpty());
		assertFalse(getXmlEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo", getXmlEntityMappings().getEmbeddables().get(0).getClassName());
		
		//add entity in the resource model, verify context model updated
		XmlEntity entity = OrmFactory.eINSTANCE.createXmlEntity();
		getXmlEntityMappings().getEntities().add(entity);
		entity.setClassName("model.Foo2");
		assertTrue(getEntityMappings().persistentTypes().hasNext());
		assertEquals("model.Foo2", getEntityMappings().persistentTypes().next().getMapping().getClass_());
		assertTrue(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(getXmlEntityMappings().getEntities().isEmpty());
		assertFalse(getXmlEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo2", getXmlEntityMappings().getEntities().get(0).getClassName());

		//add mapped-superclass in the resource model, verify context model updated
		XmlMappedSuperclass mappedSuperclass = OrmFactory.eINSTANCE.createXmlMappedSuperclass();
		getXmlEntityMappings().getMappedSuperclasses().add(mappedSuperclass);
		mappedSuperclass.setClassName("model.Foo3");
		assertTrue(getEntityMappings().persistentTypes().hasNext());
		assertEquals("model.Foo3", getEntityMappings().persistentTypes().next().getMapping().getClass_());
		assertFalse(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(getXmlEntityMappings().getEntities().isEmpty());
		assertFalse(getXmlEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo3", getXmlEntityMappings().getMappedSuperclasses().get(0).getClassName());
	}
	
	
	public void testAddOrmPersistentType() throws Exception {
		assertFalse(getEntityMappings().persistentTypes().hasNext());
		assertTrue(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getXmlEntityMappings().getEntities().isEmpty());
		assertTrue(getXmlEntityMappings().getEmbeddables().isEmpty());
		
		//add embeddable in the context model, verify resource model modified
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		assertTrue(getEntityMappings().persistentTypes().hasNext());
		assertEquals("model.Foo", getEntityMappings().persistentTypes().next().getMapping().getClass_());
		assertEquals(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, getEntityMappings().persistentTypes().next().getMapping().getKey());
		assertTrue(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getXmlEntityMappings().getEntities().isEmpty());
		assertFalse(getXmlEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo", getXmlEntityMappings().getEmbeddables().get(0).getClassName());

		//add entity in the context model, verify resource model modified
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		assertTrue(getEntityMappings().persistentTypes().hasNext());
		assertEquals("model.Foo2", getEntityMappings().persistentTypes().next().getMapping().getClass_());
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, getEntityMappings().persistentTypes().next().getMapping().getKey());
		assertTrue(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(getXmlEntityMappings().getEntities().isEmpty());
		assertFalse(getXmlEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo2", getXmlEntityMappings().getEntities().get(0).getClassName());

		//add mapped-superclass in the context model, verify resource model modified
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
		assertTrue(getEntityMappings().persistentTypes().hasNext());
		assertEquals("model.Foo3", getEntityMappings().persistentTypes().next().getMapping().getClass_());
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, getEntityMappings().persistentTypes().next().getMapping().getKey());
		assertFalse(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertFalse(getXmlEntityMappings().getEntities().isEmpty());
		assertFalse(getXmlEntityMappings().getEmbeddables().isEmpty());
		assertEquals("model.Foo3", getXmlEntityMappings().getMappedSuperclasses().get(0).getClassName());
	}
	
	public void testRemoveOrmPersistentType() throws Exception {
		assertFalse(getEntityMappings().persistentTypes().hasNext());
		assertTrue(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getXmlEntityMappings().getEntities().isEmpty());
		assertTrue(getXmlEntityMappings().getEmbeddables().isEmpty());
		
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
		
		//remove ormPersistentType from the context model, verify resource model modified
		getEntityMappings().removePersistentType(1);
		assertFalse(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getXmlEntityMappings().getEntities().isEmpty());
		assertFalse(getXmlEntityMappings().getEmbeddables().isEmpty());
		
		getEntityMappings().removePersistentType(1);
		assertFalse(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getXmlEntityMappings().getEntities().isEmpty());
		assertTrue(getXmlEntityMappings().getEmbeddables().isEmpty());
		
		getEntityMappings().removePersistentType(0);
		assertTrue(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getXmlEntityMappings().getEntities().isEmpty());
		assertTrue(getXmlEntityMappings().getEmbeddables().isEmpty());
	}
	
	public void testUpdateTableGenerators() throws Exception {
		assertEquals(0, getEntityMappings().tableGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getTableGenerators().size());
		assertEquals(0, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		XmlTableGenerator tableGeneratorResource = OrmFactory.eINSTANCE.createXmlTableGeneratorImpl();
		getXmlEntityMappings().getTableGenerators().add(tableGeneratorResource);
		tableGeneratorResource.setName("FOO");
		
		TableGenerator tableGenerator = getEntityMappings().tableGenerators().next();
		assertEquals("FOO", tableGenerator.getName());
		assertEquals(1, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		XmlTableGenerator tableGeneratorResource2 = OrmFactory.eINSTANCE.createXmlTableGeneratorImpl();
		getXmlEntityMappings().getTableGenerators().add(0, tableGeneratorResource2);
		tableGeneratorResource2.setName("BAR");
		
		ListIterator<OrmTableGenerator> tableGenerators = getEntityMappings().tableGenerators();
		assertEquals("BAR", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
		assertEquals(2, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		XmlTableGenerator tableGeneratorResource3 = OrmFactory.eINSTANCE.createXmlTableGeneratorImpl();
		getXmlEntityMappings().getTableGenerators().add(1, tableGeneratorResource3);
		tableGeneratorResource3.setName("BAZ");
		
		tableGenerators = getEntityMappings().tableGenerators();
		assertEquals("BAR", tableGenerators.next().getName());
		assertEquals("BAZ", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
		assertEquals(3, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		getXmlEntityMappings().getTableGenerators().move(2, 0);
		tableGenerators = getEntityMappings().tableGenerators();
		assertEquals("BAZ", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertEquals("BAR", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
		assertEquals(3, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		getXmlEntityMappings().getTableGenerators().remove(0);
		tableGenerators = getEntityMappings().tableGenerators();
		assertEquals("FOO", tableGenerators.next().getName());
		assertEquals("BAR", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
		assertEquals(2, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		getXmlEntityMappings().getTableGenerators().remove(1);
		tableGenerators = getEntityMappings().tableGenerators();
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
		assertEquals(1, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		getXmlEntityMappings().getTableGenerators().clear();
		tableGenerators = getEntityMappings().tableGenerators();
		assertFalse(tableGenerators.hasNext());
		assertEquals(0, getEntityMappings().getPersistenceUnit().generatorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		assertEquals(0, getEntityMappings().tableGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getTableGenerators().size());
		getEntityMappings().addTableGenerator(0).setName("FOO");
		
		assertEquals("FOO", getXmlEntityMappings().getTableGenerators().get(0).getName());
		
		getEntityMappings().addTableGenerator(0).setName("BAR");
		assertEquals("BAR", getXmlEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("FOO", getXmlEntityMappings().getTableGenerators().get(1).getName());
		assertEquals(2, getXmlEntityMappings().getTableGenerators().size());
		
		ListIterator<OrmTableGenerator> tableGenerators = getEntityMappings().tableGenerators();
		assertEquals("BAR", tableGenerators.next().getName());
		assertEquals("FOO", tableGenerators.next().getName());
		assertFalse(tableGenerators.hasNext());
	}
	
	public void testRemoveTableGenerator() throws Exception {
		assertEquals(0, getEntityMappings().tableGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getTableGenerators().size());
		
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);
		tableGenerator.setName("FOO");
		TableGenerator tableGenerator2 = getEntityMappings().addTableGenerator(1);
		tableGenerator2.setName("BAR");
		TableGenerator tableGenerator3 = getEntityMappings().addTableGenerator(2);
		tableGenerator3.setName("BAZ");
		assertEquals("FOO", getXmlEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAR", getXmlEntityMappings().getTableGenerators().get(1).getName());
		assertEquals("BAZ", getXmlEntityMappings().getTableGenerators().get(2).getName());
		assertEquals(3, getXmlEntityMappings().getTableGenerators().size());
		
		getEntityMappings().removeTableGenerator(0);
		assertEquals("BAR", getXmlEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getTableGenerators().get(1).getName());
		
		ListIterator<OrmTableGenerator> tableGenerators = getEntityMappings().tableGenerators();
		OrmTableGenerator xmlTableGenerator = tableGenerators.next();
		assertEquals("BAR", xmlTableGenerator.getName());
		assertEquals(tableGenerator2, xmlTableGenerator);
		xmlTableGenerator = tableGenerators.next();
		assertEquals("BAZ", xmlTableGenerator.getName());
		assertEquals(tableGenerator3, xmlTableGenerator);
		assertFalse(tableGenerators.hasNext());

		
		getEntityMappings().removeTableGenerator(1);
		assertEquals("BAR", getXmlEntityMappings().getTableGenerators().get(0).getName());
		tableGenerators = getEntityMappings().tableGenerators();
		xmlTableGenerator = tableGenerators.next();
		assertEquals("BAR", xmlTableGenerator.getName());
		assertEquals(tableGenerator2, xmlTableGenerator);
		assertFalse(tableGenerators.hasNext());

		
		getEntityMappings().removeTableGenerator(0);
		assertEquals(0, getXmlEntityMappings().getTableGenerators().size());
		tableGenerators = getEntityMappings().tableGenerators();
		assertFalse(tableGenerators.hasNext());		
	}
	
	public void testMoveTableGenerator() throws Exception {
		assertEquals(0, getEntityMappings().tableGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getTableGenerators().size());
		
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);
		tableGenerator.setName("FOO");
		TableGenerator tableGenerator2 = getEntityMappings().addTableGenerator(1);
		tableGenerator2.setName("BAR");
		TableGenerator tableGenerator3 = getEntityMappings().addTableGenerator(2);
		tableGenerator3.setName("BAZ");
		assertEquals("FOO", getXmlEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAR", getXmlEntityMappings().getTableGenerators().get(1).getName());
		assertEquals("BAZ", getXmlEntityMappings().getTableGenerators().get(2).getName());
		assertEquals(3, getXmlEntityMappings().getTableGenerators().size());
		
		getEntityMappings().moveTableGenerator(2, 0);
		assertEquals("BAR", getXmlEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getTableGenerators().get(1).getName());
		assertEquals("FOO", getXmlEntityMappings().getTableGenerators().get(2).getName());
		assertEquals(3, getXmlEntityMappings().getTableGenerators().size());
		
		getEntityMappings().moveTableGenerator(0, 2);
		assertEquals("FOO", getXmlEntityMappings().getTableGenerators().get(0).getName());
		assertEquals("BAR", getXmlEntityMappings().getTableGenerators().get(1).getName());
		assertEquals("BAZ", getXmlEntityMappings().getTableGenerators().get(2).getName());
		assertEquals(3, getXmlEntityMappings().getTableGenerators().size());
	}
	
	public void testTableGeneratorsSize() throws Exception {
		assertEquals(0, getEntityMappings().tableGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getTableGenerators().size());
		
		
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);
		tableGenerator.setName("FOO");
		TableGenerator tableGenerator2 = getEntityMappings().addTableGenerator(1);
		tableGenerator2.setName("BAR");
		TableGenerator tableGenerator3 = getEntityMappings().addTableGenerator(2);
		tableGenerator3.setName("BAZ");
	
		assertEquals(3, getEntityMappings().tableGeneratorsSize());
		
		getXmlEntityMappings().getTableGenerators().remove(0);
		assertEquals(2, getEntityMappings().tableGeneratorsSize());
	}

	public void testUpdateSequenceGenerators() throws Exception {
		assertEquals(0, getEntityMappings().sequenceGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getSequenceGenerators().size());
		assertEquals(0, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		XmlSequenceGenerator sequenceGeneratorResource = OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl();
		getXmlEntityMappings().getSequenceGenerators().add(sequenceGeneratorResource);
		sequenceGeneratorResource.setName("FOO");
		
		SequenceGenerator sequenceGenerator = getEntityMappings().sequenceGenerators().next();
		assertEquals("FOO", sequenceGenerator.getName());
		assertEquals(1, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		XmlSequenceGenerator sequenceGeneratorResource2 = OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl();
		getXmlEntityMappings().getSequenceGenerators().add(0, sequenceGeneratorResource2);
		sequenceGeneratorResource2.setName("BAR");
		
		ListIterator<OrmSequenceGenerator> sequenceGenerators = getEntityMappings().sequenceGenerators();
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
		assertEquals(2, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		XmlSequenceGenerator sequenceGeneratorResource3 = OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl();
		getXmlEntityMappings().getSequenceGenerators().add(1, sequenceGeneratorResource3);
		sequenceGeneratorResource3.setName("BAZ");

		sequenceGenerators = getEntityMappings().sequenceGenerators();
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertEquals("BAZ", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
		assertEquals(3, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		getXmlEntityMappings().getSequenceGenerators().move(2, 0);
		sequenceGenerators = getEntityMappings().sequenceGenerators();
		assertEquals("BAZ", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
		
		getXmlEntityMappings().getSequenceGenerators().remove(0);
		sequenceGenerators = getEntityMappings().sequenceGenerators();
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
		assertEquals(2, getEntityMappings().getPersistenceUnit().generatorsSize());

		getXmlEntityMappings().getSequenceGenerators().remove(1);
		sequenceGenerators = getEntityMappings().sequenceGenerators();
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
		assertEquals(1, getEntityMappings().getPersistenceUnit().generatorsSize());
		
		getXmlEntityMappings().getSequenceGenerators().clear();
		sequenceGenerators = getEntityMappings().sequenceGenerators();
		assertFalse(sequenceGenerators.hasNext());
		assertEquals(0, getEntityMappings().getPersistenceUnit().generatorsSize());
	}
	
	public void testAddSequenceGenerator() throws Exception {
		assertEquals(0, getEntityMappings().sequenceGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getSequenceGenerators().size());
		
		getEntityMappings().addSequenceGenerator(0).setName("FOO");
		
		assertEquals("FOO", getXmlEntityMappings().getSequenceGenerators().get(0).getName());
		
		getEntityMappings().addSequenceGenerator(0).setName("BAR");
		assertEquals("BAR", getXmlEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("FOO", getXmlEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals(2, getXmlEntityMappings().getSequenceGenerators().size());
		
		ListIterator<OrmSequenceGenerator> sequenceGenerators = getEntityMappings().sequenceGenerators();
		assertEquals("BAR", sequenceGenerators.next().getName());
		assertEquals("FOO", sequenceGenerators.next().getName());
		assertFalse(sequenceGenerators.hasNext());
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		assertEquals(0, getEntityMappings().sequenceGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getSequenceGenerators().size());
		
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);
		sequenceGenerator.setName("FOO");
		SequenceGenerator sequenceGenerator2 = getEntityMappings().addSequenceGenerator(1);
		sequenceGenerator2.setName("BAR");
		SequenceGenerator sequenceGenerator3 = getEntityMappings().addSequenceGenerator(2);
		sequenceGenerator3.setName("BAZ");
		assertEquals("FOO", getXmlEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAR", getXmlEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals("BAZ", getXmlEntityMappings().getSequenceGenerators().get(2).getName());
		assertEquals(3, getXmlEntityMappings().getSequenceGenerators().size());
		
		getEntityMappings().removeSequenceGenerator(0);
		assertEquals("BAR", getXmlEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getSequenceGenerators().get(1).getName());
		
		ListIterator<OrmSequenceGenerator> sequenceGenerators = getEntityMappings().sequenceGenerators();
		SequenceGenerator xmlSequenceGenerator = sequenceGenerators.next();
		assertEquals("BAR", xmlSequenceGenerator.getName());
		assertEquals(sequenceGenerator2, xmlSequenceGenerator);
		xmlSequenceGenerator = sequenceGenerators.next();
		assertEquals("BAZ", xmlSequenceGenerator.getName());
		assertEquals(sequenceGenerator3, xmlSequenceGenerator);
		assertFalse(sequenceGenerators.hasNext());

		
		getEntityMappings().removeSequenceGenerator(1);
		assertEquals("BAR", getXmlEntityMappings().getSequenceGenerators().get(0).getName());
		sequenceGenerators = getEntityMappings().sequenceGenerators();
		xmlSequenceGenerator = sequenceGenerators.next();
		assertEquals("BAR", xmlSequenceGenerator.getName());
		assertEquals(sequenceGenerator2, xmlSequenceGenerator);
		assertFalse(sequenceGenerators.hasNext());

		
		getEntityMappings().removeSequenceGenerator(0);
		assertEquals(0, getXmlEntityMappings().getSequenceGenerators().size());
		sequenceGenerators = getEntityMappings().sequenceGenerators();
		assertFalse(sequenceGenerators.hasNext());		
	}
	
	public void testMoveSequenceGenerator() throws Exception {
		assertEquals(0, getEntityMappings().sequenceGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getSequenceGenerators().size());
		
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);
		sequenceGenerator.setName("FOO");
		SequenceGenerator sequenceGenerator2 = getEntityMappings().addSequenceGenerator(1);
		sequenceGenerator2.setName("BAR");
		SequenceGenerator sequenceGenerator3 = getEntityMappings().addSequenceGenerator(2);
		sequenceGenerator3.setName("BAZ");
		assertEquals("FOO", getXmlEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAR", getXmlEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals("BAZ", getXmlEntityMappings().getSequenceGenerators().get(2).getName());
		assertEquals(3, getXmlEntityMappings().getSequenceGenerators().size());
		
		getEntityMappings().moveSequenceGenerator(2, 0);
		assertEquals("BAR", getXmlEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals("FOO", getXmlEntityMappings().getSequenceGenerators().get(2).getName());
		assertEquals(3, getXmlEntityMappings().getSequenceGenerators().size());
		
		getEntityMappings().moveSequenceGenerator(0, 2);
		assertEquals("FOO", getXmlEntityMappings().getSequenceGenerators().get(0).getName());
		assertEquals("BAR", getXmlEntityMappings().getSequenceGenerators().get(1).getName());
		assertEquals("BAZ", getXmlEntityMappings().getSequenceGenerators().get(2).getName());
		assertEquals(3, getXmlEntityMappings().getSequenceGenerators().size());
		
	}
	
	public void testSequenceGeneratorsSize() throws Exception {
		assertEquals(0, getEntityMappings().sequenceGeneratorsSize());
		assertEquals(0, getXmlEntityMappings().getSequenceGenerators().size());
		
		
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);
		sequenceGenerator.setName("FOO");
		SequenceGenerator sequenceGenerator2 = getEntityMappings().addSequenceGenerator(1);
		sequenceGenerator2.setName("BAR");
		SequenceGenerator sequenceGenerator3 = getEntityMappings().addSequenceGenerator(2);
		sequenceGenerator3.setName("BAZ");
	
		assertEquals(3, getEntityMappings().sequenceGeneratorsSize());
		
		getXmlEntityMappings().getSequenceGenerators().remove(0);
		assertEquals(2, getEntityMappings().sequenceGeneratorsSize());
	}

	
	public void testAddNamedQuery() throws Exception {
		OrmNamedQuery namedQuery = getEntityMappings().addNamedQuery(0);
		namedQuery.setName("FOO");
				
		assertEquals("FOO", getXmlEntityMappings().getNamedQueries().get(0).getName());
		
		OrmNamedQuery namedQuery2 = getEntityMappings().addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		assertEquals("BAR", getXmlEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("FOO", getXmlEntityMappings().getNamedQueries().get(1).getName());
		
		OrmNamedQuery namedQuery3 = getEntityMappings().addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		assertEquals("BAR", getXmlEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getNamedQueries().get(1).getName());
		assertEquals("FOO", getXmlEntityMappings().getNamedQueries().get(2).getName());
		
		ListIterator<OrmNamedQuery> namedQueries = getEntityMappings().namedQueries();
		assertEquals(namedQuery2, namedQueries.next());
		assertEquals(namedQuery3, namedQueries.next());
		assertEquals(namedQuery, namedQueries.next());
		
		namedQueries = getEntityMappings().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
	}
	
	public void testRemoveNamedQuery() throws Exception {
		getEntityMappings().addNamedQuery(0).setName("FOO");
		getEntityMappings().addNamedQuery(1).setName("BAR");
		getEntityMappings().addNamedQuery(2).setName("BAZ");
		
		assertEquals(3, getXmlEntityMappings().getNamedQueries().size());
		
		getEntityMappings().removeNamedQuery(0);
		assertEquals(2, getXmlEntityMappings().getNamedQueries().size());
		assertEquals("BAR", getXmlEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getNamedQueries().get(1).getName());

		getEntityMappings().removeNamedQuery(0);
		assertEquals(1, getXmlEntityMappings().getNamedQueries().size());
		assertEquals("BAZ", getXmlEntityMappings().getNamedQueries().get(0).getName());
		
		getEntityMappings().removeNamedQuery(0);
		assertEquals(0, getXmlEntityMappings().getNamedQueries().size());
	}
	
	public void testMoveNamedQuery() throws Exception {
		getEntityMappings().addNamedQuery(0).setName("FOO");
		getEntityMappings().addNamedQuery(1).setName("BAR");
		getEntityMappings().addNamedQuery(2).setName("BAZ");
		
		assertEquals(3, getXmlEntityMappings().getNamedQueries().size());
		
		
		getEntityMappings().moveNamedQuery(2, 0);
		ListIterator<OrmNamedQuery> namedQueries = getEntityMappings().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		assertEquals("BAR", getXmlEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getNamedQueries().get(1).getName());
		assertEquals("FOO", getXmlEntityMappings().getNamedQueries().get(2).getName());


		getEntityMappings().moveNamedQuery(0, 1);
		namedQueries = getEntityMappings().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		assertEquals("BAZ", getXmlEntityMappings().getNamedQueries().get(0).getName());
		assertEquals("BAR", getXmlEntityMappings().getNamedQueries().get(1).getName());
		assertEquals("FOO", getXmlEntityMappings().getNamedQueries().get(2).getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		assertEquals(0, getEntityMappings().getPersistenceUnit().queriesSize());
		
		getXmlEntityMappings().getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		getXmlEntityMappings().getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		getXmlEntityMappings().getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		
		getXmlEntityMappings().getNamedQueries().get(0).setName("FOO");
		getXmlEntityMappings().getNamedQueries().get(1).setName("BAR");
		getXmlEntityMappings().getNamedQueries().get(2).setName("BAZ");
		
		ListIterator<OrmNamedQuery> namedQueries = getEntityMappings().namedQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, getEntityMappings().getPersistenceUnit().queriesSize());
		
		getXmlEntityMappings().getNamedQueries().move(2, 0);
		namedQueries = getEntityMappings().namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		getXmlEntityMappings().getNamedQueries().move(0, 1);
		namedQueries = getEntityMappings().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		getXmlEntityMappings().getNamedQueries().remove(1);
		namedQueries = getEntityMappings().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, getEntityMappings().getPersistenceUnit().queriesSize());
		
		getXmlEntityMappings().getNamedQueries().remove(1);
		namedQueries = getEntityMappings().namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, getEntityMappings().getPersistenceUnit().queriesSize());
		
		getXmlEntityMappings().getNamedQueries().remove(0);
		assertFalse(getEntityMappings().namedQueries().hasNext());
		assertEquals(0, getEntityMappings().getPersistenceUnit().queriesSize());
	}
	
	public void testAddNamedNativeQuery() throws Exception {
		OrmNamedNativeQuery namedNativeQuery = getEntityMappings().addNamedNativeQuery(0);
		namedNativeQuery.setName("FOO");
				
		assertEquals("FOO", getXmlEntityMappings().getNamedNativeQueries().get(0).getName());
		
		OrmNamedNativeQuery namedNativeQuery2 = getEntityMappings().addNamedNativeQuery(0);
		namedNativeQuery2.setName("BAR");
		
		assertEquals("BAR", getXmlEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("FOO", getXmlEntityMappings().getNamedNativeQueries().get(1).getName());
		
		OrmNamedNativeQuery namedNativeQuery3 = getEntityMappings().addNamedNativeQuery(1);
		namedNativeQuery3.setName("BAZ");
		
		assertEquals("BAR", getXmlEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", getXmlEntityMappings().getNamedNativeQueries().get(2).getName());
		
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = getEntityMappings().namedNativeQueries();
		assertEquals(namedNativeQuery2, namedNativeQueries.next());
		assertEquals(namedNativeQuery3, namedNativeQueries.next());
		assertEquals(namedNativeQuery, namedNativeQueries.next());
		
		namedNativeQueries = getEntityMappings().namedNativeQueries();
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
	}
	
	public void testRemoveNamedNativeQuery() throws Exception {
		getEntityMappings().addNamedNativeQuery(0).setName("FOO");
		getEntityMappings().addNamedNativeQuery(1).setName("BAR");
		getEntityMappings().addNamedNativeQuery(2).setName("BAZ");
		
		assertEquals(3, getXmlEntityMappings().getNamedNativeQueries().size());
		
		getEntityMappings().removeNamedNativeQuery(0);
		assertEquals(2, getXmlEntityMappings().getNamedNativeQueries().size());
		assertEquals("BAR", getXmlEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getNamedNativeQueries().get(1).getName());

		getEntityMappings().removeNamedNativeQuery(0);
		assertEquals(1, getXmlEntityMappings().getNamedNativeQueries().size());
		assertEquals("BAZ", getXmlEntityMappings().getNamedNativeQueries().get(0).getName());
		
		getEntityMappings().removeNamedNativeQuery(0);
		assertEquals(0, getXmlEntityMappings().getNamedNativeQueries().size());
	}
	
	public void testMoveNamedNativeQuery() throws Exception {
		getEntityMappings().addNamedNativeQuery(0).setName("FOO");
		getEntityMappings().addNamedNativeQuery(1).setName("BAR");
		getEntityMappings().addNamedNativeQuery(2).setName("BAZ");
		
		assertEquals(3, getXmlEntityMappings().getNamedNativeQueries().size());
		
		
		getEntityMappings().moveNamedNativeQuery(2, 0);
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = getEntityMappings().namedNativeQueries();
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());

		assertEquals("BAR", getXmlEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", getXmlEntityMappings().getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", getXmlEntityMappings().getNamedNativeQueries().get(2).getName());


		getEntityMappings().moveNamedNativeQuery(0, 1);
		namedNativeQueries = getEntityMappings().namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());

		assertEquals("BAZ", getXmlEntityMappings().getNamedNativeQueries().get(0).getName());
		assertEquals("BAR", getXmlEntityMappings().getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", getXmlEntityMappings().getNamedNativeQueries().get(2).getName());
	}
	
	public void testUpdateNamedNativeQueries() throws Exception {
		assertEquals(0, getEntityMappings().getPersistenceUnit().queriesSize());
		
		getXmlEntityMappings().getNamedNativeQueries().add(OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		getXmlEntityMappings().getNamedNativeQueries().add(OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		getXmlEntityMappings().getNamedNativeQueries().add(OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		
		getXmlEntityMappings().getNamedNativeQueries().get(0).setName("FOO");
		getXmlEntityMappings().getNamedNativeQueries().get(1).setName("BAR");
		getXmlEntityMappings().getNamedNativeQueries().get(2).setName("BAZ");
		
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = getEntityMappings().namedNativeQueries();
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		assertEquals(3, getEntityMappings().getPersistenceUnit().queriesSize());
		
		getXmlEntityMappings().getNamedNativeQueries().move(2, 0);
		namedNativeQueries = getEntityMappings().namedNativeQueries();
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		
		getXmlEntityMappings().getNamedNativeQueries().move(0, 1);
		namedNativeQueries = getEntityMappings().namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		
		getXmlEntityMappings().getNamedNativeQueries().remove(1);
		namedNativeQueries = getEntityMappings().namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		assertEquals(2, getEntityMappings().getPersistenceUnit().queriesSize());
		
		getXmlEntityMappings().getNamedNativeQueries().remove(1);
		namedNativeQueries = getEntityMappings().namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		assertEquals(1, getEntityMappings().getPersistenceUnit().queriesSize());
		
		getXmlEntityMappings().getNamedNativeQueries().remove(0);
		assertFalse(getEntityMappings().namedNativeQueries().hasNext());
		assertEquals(0, getEntityMappings().getPersistenceUnit().queriesSize());
	}
}