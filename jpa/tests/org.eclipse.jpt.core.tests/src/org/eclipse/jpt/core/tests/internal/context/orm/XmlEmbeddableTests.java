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
import org.eclipse.jpt.core.internal.context.orm.XmlEmbeddable;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlEmbeddableTests extends ContextModelTestCase
{
	public XmlEmbeddableTests(String name) {
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

	public void testUpdateClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) xmlPersistentType.getMapping();
		Embeddable embeddableResource = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", xmlEmbeddable.getClass_());
		assertEquals("model.Foo", embeddableResource.getClassName());
		
		//set class in the resource model, verify context model updated
		embeddableResource.setClassName("com.Bar");
		assertEquals("com.Bar", xmlEmbeddable.getClass_());
		assertEquals("com.Bar", embeddableResource.getClassName());
	
		//set class to null in the resource model
		embeddableResource.setClassName(null);
		assertNull(xmlEmbeddable.getClass_());
		assertNull(embeddableResource.getClassName());
	}
	
	public void testModifyClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) xmlPersistentType.getMapping();
		Embeddable embeddableResource = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", xmlEmbeddable.getClass_());
		assertEquals("model.Foo", embeddableResource.getClassName());
		
		//set class in the context model, verify resource model modified
		xmlEmbeddable.setClass("com.Bar");
		assertEquals("com.Bar", xmlEmbeddable.getClass_());
		assertEquals("com.Bar", embeddableResource.getClassName());
		
		//set class to null in the context model
		xmlEmbeddable.setClass(null);
		assertNull(xmlEmbeddable.getClass_());
		assertNull(embeddableResource.getClassName());
	}
	//TODO add tests for setting the className when the package is set on entity-mappings
	
	public void testUpdateSpecifiedAccess() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) xmlPersistentType.getMapping();
		Embeddable embeddableResource = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(xmlEmbeddable.getSpecifiedAccess());
		assertNull(embeddableResource.getAccess());
		
		//set access in the resource model, verify context model updated
		embeddableResource.setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, xmlEmbeddable.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD, embeddableResource.getAccess());
	
		//set access to null in the resource model
		embeddableResource.setAccess(null);
		assertNull(xmlEmbeddable.getSpecifiedAccess());
		assertNull(embeddableResource.getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) xmlPersistentType.getMapping();
		Embeddable embeddableResource = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(xmlEmbeddable.getSpecifiedAccess());
		assertNull(embeddableResource.getAccess());
		
		//set access in the context model, verify resource model modified
		xmlEmbeddable.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, xmlEmbeddable.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, embeddableResource.getAccess());
		
		//set access to null in the context model
		xmlEmbeddable.setSpecifiedAccess(null);
		assertNull(xmlEmbeddable.getSpecifiedAccess());
		assertNull(embeddableResource.getAccess());
	}
	//TODO test default access from
		//underlying java
		//persistence-unit-defaults
		//entity-mappings
		//with xml-mapping-metadata-complete set
	
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) xmlPersistentType.getMapping();
		Embeddable embeddableResource = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(embeddableResource.getMetadataComplete());
		
		//set metadata-complete in the resource model, verify context model updated
		embeddableResource.setMetadataComplete(true);
		assertTrue(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertTrue(embeddableResource.getMetadataComplete());
	
		//set access to false in the resource model
		embeddableResource.setMetadataComplete(false);
		assertFalse(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(embeddableResource.getMetadataComplete());
		
		embeddableResource.setMetadataComplete(null);
		assertNull(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
	}
	
	public void testModifySpecifiedMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) xmlPersistentType.getMapping();
		Embeddable embeddableResource = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(embeddableResource.getMetadataComplete());
		
		//set access in the context model, verify resource model modified
		xmlEmbeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertTrue(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertTrue(embeddableResource.getMetadataComplete());
		
		//set access to null in the context model
		xmlEmbeddable.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertFalse(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(embeddableResource.getMetadataComplete());
		
		xmlEmbeddable.setSpecifiedMetadataComplete(null);
		assertNull(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
	}
	
	public void testUpdateDefaultMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) xmlPersistentType.getMapping();
		Embeddable embeddableResource = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(xmlEmbeddable.isDefaultMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlEmbeddable.isDefaultMetadataComplete());
		assertNull(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(xmlEmbeddable.isDefaultMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
	}
	
	public void testUpdateMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) xmlPersistentType.getMapping();
		Embeddable embeddableResource = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertNull(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertFalse(xmlEmbeddable.isMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlEmbeddable.isMetadataComplete());
		assertNull(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(xmlEmbeddable.isMetadataComplete());
		assertNull(xmlEmbeddable.getSpecifiedMetadataComplete());
		assertNull(embeddableResource.getMetadataComplete());
	}
	
	public void testMakeEmbeddableEntity() throws Exception {
		XmlPersistentType embeddablePersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable embeddable = (XmlEmbeddable) embeddablePersistentType.getMapping();
		embeddable.setSpecifiedAccess(AccessType.PROPERTY);
		embeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		ormResource().save(null);
	
		embeddablePersistentType.setMappingKey(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", entity.getClassName());
		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, entity.getAccess());
		assertNull(entity.getDiscriminatorValue());
		assertNull(entity.getName());
		
		XmlEntity xmlEntity = (XmlEntity) embeddablePersistentType.getMapping();
		assertEquals("model.Foo", xmlEntity.getClass_());
		assertEquals(Boolean.TRUE, xmlEntity.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlEntity.getSpecifiedAccess());
	}
		
	//test with 2 Embeddables, make the second one an Entity so it has to move to the front of the list
	public void testMakeEmbeddableEntity2() throws Exception {
		entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo2");
		XmlPersistentType embeddablePersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable embeddable = (XmlEmbeddable) embeddablePersistentType.getMapping();
		embeddable.setSpecifiedAccess(AccessType.PROPERTY);
		embeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		ormResource().save(null);
	
		embeddablePersistentType.setMappingKey(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", entity.getClassName());
		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, entity.getAccess());
		assertNull(entity.getDiscriminatorValue());
		assertNull(entity.getName());
		
		XmlEntity xmlEntity = (XmlEntity) embeddablePersistentType.getMapping();
		assertEquals("model.Foo", xmlEntity.getClass_());
		assertEquals(Boolean.TRUE, xmlEntity.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlEntity.getSpecifiedAccess());
		
		ListIterator<XmlPersistentType> persistentTypes = entityMappings().xmlPersistentTypes();
		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
		assertEquals(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
	}
	
	public void testMakeEmbeddableMappedSuperclass() throws Exception {
		XmlPersistentType embeddablePersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable embeddable = (XmlEmbeddable) embeddablePersistentType.getMapping();
		embeddable.setSpecifiedAccess(AccessType.PROPERTY);
		embeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		ormResource().save(null);
	
		embeddablePersistentType.setMappingKey(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		MappedSuperclass  mappedSuperclass = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
	
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) embeddablePersistentType.getMapping();
		assertEquals("model.Foo", xmlMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlMappedSuperclass.getSpecifiedAccess());
	}
	
	//test with 2 Embeddables, make the second one a MappedSuperclass so it has to move to the front of the list
	public void testMakeEmbeddableMappedSuperclass2() throws Exception {
		entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo2");
		XmlPersistentType embeddablePersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlEmbeddable embeddable = (XmlEmbeddable) embeddablePersistentType.getMapping();
		embeddable.setSpecifiedAccess(AccessType.PROPERTY);
		embeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		ormResource().save(null);
	
		embeddablePersistentType.setMappingKey(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		MappedSuperclass  mappedSuperclass = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
	
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) embeddablePersistentType.getMapping();
		assertEquals("model.Foo", xmlMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlMappedSuperclass.getSpecifiedAccess());
		
		ListIterator<XmlPersistentType> persistentTypes = entityMappings().xmlPersistentTypes();
		assertEquals(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
		assertEquals(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
	}

}