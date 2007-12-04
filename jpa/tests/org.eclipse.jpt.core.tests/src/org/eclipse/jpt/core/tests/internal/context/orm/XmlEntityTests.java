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

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlEntityTests extends ContextModelTestCase
{
	public XmlEntityTests(String name) {
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
		
	protected XmlPersistence xmlPersistence() {
		return persistenceResource().getPersistence();
	}
	
	protected EntityMappings entityMappings() {
		return persistenceUnit().mappingFileRefs().next().getOrmXml().getEntityMappings();
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedName());
		assertNull(entityResource.getName());
		
		//set name in the resource model, verify context model updated
		entityResource.setName("foo");
		assertEquals("foo", xmlEntity.getSpecifiedName());
		assertEquals("foo", entityResource.getName());
	
		//set name to null in the resource model
		entityResource.setName(null);
		assertNull(xmlEntity.getSpecifiedName());
		assertNull(entityResource.getName());
	}
	
	public void testModifySpecifiedName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedName());
		assertNull(entityResource.getName());
		
		//set name in the context model, verify resource model modified
		xmlEntity.setSpecifiedName("foo");
		assertEquals("foo", xmlEntity.getSpecifiedName());
		assertEquals("foo", entityResource.getName());
		
		//set name to null in the context model
		xmlEntity.setSpecifiedName(null);
		assertNull(xmlEntity.getSpecifiedName());
		assertNull(entityResource.getName());
	}

	public void testUpdateDefaultName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("Foo", xmlEntity.getDefaultName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("Bar", xmlEntity.getDefaultName());
		
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertNull(xmlEntity.getDefaultName());
	}
	
	public void testUpdateName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("Foo", xmlEntity.getName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("Bar", xmlEntity.getName());
		
		entityResource.setName("Baz");
		assertEquals("Baz", xmlEntity.getName());
		
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertEquals("Baz", xmlEntity.getName());
		
		entityResource.setName(null);
		assertNull(xmlEntity.getName());
	}

	public void testUpdateClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", xmlEntity.getClass_());
		assertEquals("model.Foo", entityResource.getClassName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("com.Bar", xmlEntity.getClass_());
		assertEquals("com.Bar", entityResource.getClassName());
	
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertNull(xmlEntity.getClass_());
		assertNull(entityResource.getClassName());
	}
	
	public void testModifyClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", xmlEntity.getClass_());
		assertEquals("model.Foo", entityResource.getClassName());
		
		//set class in the context model, verify resource model modified
		xmlEntity.setClass("com.Bar");
		assertEquals("com.Bar", xmlEntity.getClass_());
		assertEquals("com.Bar", entityResource.getClassName());
		
		//set class to null in the context model
		xmlEntity.setClass(null);
		assertNull(xmlEntity.getClass_());
		assertNull(entityResource.getClassName());
	}
	//TODO add tests for setting the className when the package is set on entity-mappings
	
	public void testUpdateSpecifiedAccess() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		//set access in the resource model, verify context model updated
		entityResource.setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD);
		ormResource().save(null);
		assertEquals(AccessType.FIELD, xmlEntity.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD, entityResource.getAccess());
	
		//set access to null in the resource model
		entityResource.setAccess(null);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		//set access in the context model, verify resource model modified
		xmlEntity.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, xmlEntity.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, entityResource.getAccess());
		
		//set access to null in the context model
		xmlEntity.setSpecifiedAccess(null);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
	}
	//TODO test default access from
		//underlying java
		//persistence-unit-defaults
		//entity-mappings
		//with xml-mapping-metadata-complete set
	//defaultAccess
	//access
	
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		//set metadata-complete in the resource model, verify context model updated
		entityResource.setMetadataComplete(true);
		ormResource().save(null);
		assertTrue(xmlEntity.getSpecifiedMetadataComplete());
		assertTrue(entityResource.getMetadataComplete());
	
		//set access to false in the resource model
		entityResource.setMetadataComplete(false);
		ormResource().save(null);
		assertFalse(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(entityResource.getMetadataComplete());
		
		entityResource.setMetadataComplete(null);
		ormResource().save(null);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	public void testModifySpecifiedMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		//set access in the context model, verify resource model modified
		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertTrue(xmlEntity.getSpecifiedMetadataComplete());
		assertTrue(entityResource.getMetadataComplete());
		
		//set access to null in the context model
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertFalse(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(entityResource.getMetadataComplete());
		
		xmlEntity.setSpecifiedMetadataComplete(null);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	public void testUpdateDefaultMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(xmlEntity.getDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlEntity.getDefaultMetadataComplete());
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(xmlEntity.getDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}

	public void testUpdateMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(xmlEntity.getMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlEntity.getMetadataComplete());
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(xmlEntity.getMetadataComplete());
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
}