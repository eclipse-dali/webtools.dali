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
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.context.orm.XmlIdMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.Id;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlIdMappingTests extends ContextModelTestCase
{
	public XmlIdMappingTests(String name) {
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
	
	public void testUpdateTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		ormResource().save(null);
		
		assertNull(xmlIdMapping.getTemporal());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		versionResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE);
		ormResource().save(null);
		assertEquals(TemporalType.DATE, xmlIdMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, versionResource.getTemporal());
	
		versionResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME);
		ormResource().save(null);
		assertEquals(TemporalType.TIME, xmlIdMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, versionResource.getTemporal());

		versionResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(TemporalType.TIMESTAMP, xmlIdMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());

		//set temporal to null in the resource model
		versionResource.setTemporal(null);
		ormResource().save(null);
		assertNull(xmlIdMapping.getTemporal());
		assertNull(versionResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		ormResource().save(null);
		
		assertNull(xmlIdMapping.getTemporal());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		xmlIdMapping.setTemporal(TemporalType.DATE);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, versionResource.getTemporal());
		assertEquals(TemporalType.DATE, xmlIdMapping.getTemporal());
	
		xmlIdMapping.setTemporal(TemporalType.TIME);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, versionResource.getTemporal());
		assertEquals(TemporalType.TIME, xmlIdMapping.getTemporal());

		xmlIdMapping.setTemporal(TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, xmlIdMapping.getTemporal());

		//set temporal to null in the context model
		xmlIdMapping.setTemporal(null);
		ormResource().save(null);
		assertNull(versionResource.getTemporal());
		assertNull(xmlIdMapping.getTemporal());
	}
	
	//TODO test morphing to other mapping types
	//TODO test defaults
	
//	public void testMakeMappedSuperclassEntity() throws Exception {
//		XmlPersistentType mappedSuperclassPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
//		XmlMappedSuperclass mappedSuperclass = (XmlMappedSuperclass) mappedSuperclassPersistentType.getMapping();
//		mappedSuperclass.setSpecifiedAccess(AccessType.PROPERTY);
//		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
//		ormResource().save(null);
//	
//		mappedSuperclassPersistentType.setMappingKey(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
//		ormResource().save(null);
//		
//		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
//		assertEquals("model.Foo", entity.getClassName());
//		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
//		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, entity.getAccess());
//		assertNull(entity.getDiscriminatorValue());
//		assertNull(entity.getName());
//		
//		XmlEntity xmlEntity = (XmlEntity) mappedSuperclassPersistentType.getMapping();
//		assertEquals("model.Foo", xmlEntity.getClass_());
//		assertEquals(Boolean.TRUE, xmlEntity.getSpecifiedMetadataComplete());
//		assertEquals(AccessType.PROPERTY, xmlEntity.getSpecifiedAccess());
//	}
//		
//	//test with 2 MappedSuperclasses, make the first one an Entity so it has to move to the end of the list
//	public void testMakeMappedSuperclassEntity2() throws Exception {
//		XmlPersistentType mappedSuperclassPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
//		entityMappings().addXmlPersistentType(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo2");
//		XmlMappedSuperclass mappedSuperclass = (XmlMappedSuperclass) mappedSuperclassPersistentType.getMapping();
//		mappedSuperclass.setSpecifiedAccess(AccessType.PROPERTY);
//		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
//		ormResource().save(null);
//	
//		mappedSuperclassPersistentType.setMappingKey(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
//		ormResource().save(null);
//		
//		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
//		assertEquals("model.Foo", entity.getClassName());
//		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
//		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, entity.getAccess());
//		assertNull(entity.getDiscriminatorValue());
//		assertNull(entity.getName());
//		
//		XmlEntity xmlEntity = (XmlEntity) mappedSuperclassPersistentType.getMapping();
//		assertEquals("model.Foo", xmlEntity.getClass_());
//		assertEquals(Boolean.TRUE, xmlEntity.getSpecifiedMetadataComplete());
//		assertEquals(AccessType.PROPERTY, xmlEntity.getSpecifiedAccess());
//		
//		ListIterator<XmlPersistentType> persistentTypes = entityMappings().xmlPersistentTypes();
//		assertEquals(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
//		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
//	}

	
	public void testAddSequenceGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
		
		xmlIdMapping.addSequenceGenerator();
		
		assertNotNull(idResource.getSequenceGenerator());
		assertNotNull(xmlIdMapping.getSequenceGenerator());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			xmlIdMapping.addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());

		xmlIdMapping.addSequenceGenerator();
		assertNotNull(idResource.getSequenceGenerator());
		assertNotNull(xmlIdMapping.getSequenceGenerator());

		xmlIdMapping.removeSequenceGenerator();
		
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			xmlIdMapping.removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateSequenceGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
		
		idResource.setSequenceGenerator(OrmFactory.eINSTANCE.createSequenceGenerator());
				
		assertNotNull(xmlIdMapping.getSequenceGenerator());
		assertNotNull(idResource.getSequenceGenerator());
				
		idResource.setSequenceGenerator(null);
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
	}
	
	public void testAddTableGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());
		
		xmlIdMapping.addTableGenerator();
		
		assertNotNull(idResource.getTableGenerator());
		assertNotNull(xmlIdMapping.getTableGenerator());
				
		//try adding another table generator, should get an IllegalStateException
		try {
			xmlIdMapping.addTableGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());

		xmlIdMapping.addTableGenerator();
		assertNotNull(idResource.getTableGenerator());
		assertNotNull(xmlIdMapping.getTableGenerator());

		xmlIdMapping.removeTableGenerator();
		
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());

		//try removing the table generator again, should get an IllegalStateException
		try {
			xmlIdMapping.removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testUpdateTableGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());
		
		idResource.setTableGenerator(OrmFactory.eINSTANCE.createTableGenerator());
				
		assertNotNull(xmlIdMapping.getTableGenerator());
		assertNotNull(idResource.getTableGenerator());
				
		idResource.setTableGenerator(null);
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());
	}

	public void testAddGeneratedValue() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());
		
		xmlIdMapping.addGeneratedValue();
		
		assertNotNull(idResource.getGeneratedValue());
		assertNotNull(xmlIdMapping.getGeneratedValue());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			xmlIdMapping.addGeneratedValue();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveGeneratedValue() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());

		xmlIdMapping.addGeneratedValue();
		assertNotNull(idResource.getGeneratedValue());
		assertNotNull(xmlIdMapping.getGeneratedValue());

		xmlIdMapping.removeGeneratedValue();
		
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			xmlIdMapping.removeGeneratedValue();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateGeneratedValue() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());
		
		idResource.setGeneratedValue(OrmFactory.eINSTANCE.createGeneratedValue());
				
		assertNotNull(xmlIdMapping.getGeneratedValue());
		assertNotNull(idResource.getGeneratedValue());
				
		idResource.setGeneratedValue(null);
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());
	}
	
}