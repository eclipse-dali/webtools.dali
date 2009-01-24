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
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class OrmEmbeddableTests extends ContextModelTestCase
{
	public OrmEmbeddableTests(String name) {
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

	public void testUpdateClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable embeddableResource = getOrmXmlResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", ormEmbeddable.getClass_());
		assertEquals("model.Foo", embeddableResource.getClassName());
		
		//set class in the resource model, verify context model updated
		embeddableResource.setClassName("com.Bar");
		assertEquals("com.Bar", ormEmbeddable.getClass_());
		assertEquals("com.Bar", embeddableResource.getClassName());
	
		//set class to null in the resource model
		embeddableResource.setClassName(null);
		assertNull(ormEmbeddable.getClass_());
		assertNull(embeddableResource.getClassName());
	}
	
	public void testModifyClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable embeddableResource = getOrmXmlResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", ormEmbeddable.getClass_());
		assertEquals("model.Foo", embeddableResource.getClassName());
		
		//set class in the context model, verify resource model modified
		ormEmbeddable.setClass("com.Bar");
		assertEquals("com.Bar", ormEmbeddable.getClass_());
		assertEquals("com.Bar", embeddableResource.getClassName());
		
		//set class to null in the context model
		ormEmbeddable.setClass(null);
		assertNull(ormEmbeddable.getClass_());
		assertNull(embeddableResource.getClassName());
	}
	//TODO add tests for setting the className when the package is set on entity-mappings
	
	public void testUpdateSpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable embeddableResource = getOrmXmlResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(ormEmbeddable.getSpecifiedAccess());
		assertNull(embeddableResource.getAccess());
		
		//set access in the resource model, verify context model updated
		embeddableResource.setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, ormEmbeddable.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.FIELD, embeddableResource.getAccess());
	
		//set access to null in the resource model
		embeddableResource.setAccess(null);
		assertNull(ormEmbeddable.getSpecifiedAccess());
		assertNull(embeddableResource.getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable embeddableResource = getOrmXmlResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(ormEmbeddable.getSpecifiedAccess());
		assertNull(embeddableResource.getAccess());
		
		//set access in the context model, verify resource model modified
		ormEmbeddable.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, ormEmbeddable.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, embeddableResource.getAccess());
		
		//set access to null in the context model
		ormEmbeddable.setSpecifiedAccess(null);
		assertNull(ormEmbeddable.getSpecifiedAccess());
		assertNull(embeddableResource.getAccess());
	}
	//TODO test default access from
		//underlying java
		//persistence-unit-defaults
		//entity-mappings
		//with xml-mapping-metadata-complete set
	
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable embeddableResource = getOrmXmlResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(embeddableResource.getMetadataComplete());
		
		//set metadata-complete in the resource model, verify context model updated
		embeddableResource.setMetadataComplete(true);
		assertTrue(ormEmbeddable.getSpecifiedMetadataComplete());
		assertTrue(embeddableResource.getMetadataComplete());
	
		//set access to false in the resource model
		embeddableResource.setMetadataComplete(false);
		assertFalse(ormEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(embeddableResource.getMetadataComplete());
		
		embeddableResource.setMetadataComplete(null);
		assertNull(ormEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
	}
	
	public void testModifySpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable embeddableResource = getOrmXmlResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(embeddableResource.getMetadataComplete());
		
		//set access in the context model, verify resource model modified
		ormEmbeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertTrue(ormEmbeddable.getSpecifiedMetadataComplete());
		assertTrue(embeddableResource.getMetadataComplete());
		
		//set access to null in the context model
		ormEmbeddable.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertFalse(ormEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(embeddableResource.getMetadataComplete());
		
		ormEmbeddable.setSpecifiedMetadataComplete(null);
		assertNull(ormEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
	}
	
	public void testUpdateDefaultMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable embeddableResource = getOrmXmlResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(ormEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(ormEmbeddable.isDefaultMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
		
		getOrmXmlResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getOrmXmlResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(ormEmbeddable.isDefaultMetadataComplete());
		assertNull(ormEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
		
		getOrmXmlResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(ormEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(ormEmbeddable.isDefaultMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
	}
	
	public void testUpdateMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable embeddableResource = getOrmXmlResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(ormEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(ormEmbeddable.isMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
		
		getOrmXmlResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getOrmXmlResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(ormEmbeddable.isMetadataComplete());
		assertNull(ormEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
		
		getOrmXmlResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(ormEmbeddable.isMetadataComplete());
		assertNull(ormEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
	}
	
	public void testMakeEmbeddableEntity() throws Exception {
		OrmPersistentType embeddablePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable embeddable = (OrmEmbeddable) embeddablePersistentType.getMapping();
		embeddable.setSpecifiedAccess(AccessType.PROPERTY);
		embeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
	
		embeddablePersistentType.setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		XmlEntity entity = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", entity.getClassName());
		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, entity.getAccess());
		assertNull(entity.getDiscriminatorValue());
		assertNull(entity.getName());
		
		OrmEntity ormEntity = (OrmEntity) embeddablePersistentType.getMapping();
		assertEquals("model.Foo", ormEntity.getClass_());
		assertEquals(Boolean.TRUE, ormEntity.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, ormEntity.getSpecifiedAccess());
	}
		
	//test with 2 Embeddables, make the second one an Entity so it has to move to the front of the list
	public void testMakeEmbeddableEntity2() throws Exception {
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo2");
		OrmPersistentType embeddablePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable embeddable = (OrmEmbeddable) embeddablePersistentType.getMapping();
		embeddable.setSpecifiedAccess(AccessType.PROPERTY);
		embeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
	
		embeddablePersistentType.setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		XmlEntity entity = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", entity.getClassName());
		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, entity.getAccess());
		assertNull(entity.getDiscriminatorValue());
		assertNull(entity.getName());
		
		OrmEntity ormEntity = (OrmEntity) embeddablePersistentType.getMapping();
		assertEquals("model.Foo", ormEntity.getClass_());
		assertEquals(Boolean.TRUE, ormEntity.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, ormEntity.getSpecifiedAccess());
		
		ListIterator<OrmPersistentType> persistentTypes = getEntityMappings().persistentTypes();
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, persistentTypes.next().getMappingKey());
		assertEquals(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, persistentTypes.next().getMappingKey());
	}
	
	public void testMakeEmbeddableMappedSuperclass() throws Exception {
		OrmPersistentType embeddablePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable embeddable = (OrmEmbeddable) embeddablePersistentType.getMapping();
		embeddable.setSpecifiedAccess(AccessType.PROPERTY);
		embeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
	
		embeddablePersistentType.setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		
		XmlMappedSuperclass  mappedSuperclass = getOrmXmlResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
	
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) embeddablePersistentType.getMapping();
		assertEquals("model.Foo", ormMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, ormMappedSuperclass.getSpecifiedAccess());
	}
	
	//test with 2 Embeddables, make the second one a MappedSuperclass so it has to move to the front of the list
	public void testMakeEmbeddableMappedSuperclass2() throws Exception {
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo2");
		OrmPersistentType embeddablePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmEmbeddable embeddable = (OrmEmbeddable) embeddablePersistentType.getMapping();
		embeddable.setSpecifiedAccess(AccessType.PROPERTY);
		embeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
	
		embeddablePersistentType.setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		
		XmlMappedSuperclass  mappedSuperclass = getOrmXmlResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
	
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) embeddablePersistentType.getMapping();
		assertEquals("model.Foo", ormMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, ormMappedSuperclass.getSpecifiedAccess());
		
		ListIterator<OrmPersistentType> persistentTypes = getEntityMappings().persistentTypes();
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, persistentTypes.next().getMappingKey());
		assertEquals(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, persistentTypes.next().getMappingKey());
	}

}