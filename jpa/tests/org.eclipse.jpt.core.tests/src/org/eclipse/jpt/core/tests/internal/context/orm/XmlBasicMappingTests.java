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
import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.context.orm.XmlBasicMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlBasicMappingTests extends ContextModelTestCase
{
	public XmlBasicMappingTests(String name) {
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
	
	public void testUpdateName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertEquals("basicMapping", xmlBasicMapping.getName());
		assertEquals("basicMapping", basicResource.getName());
				
		//set name in the resource model, verify context model updated
		basicResource.setName("newName");
		assertEquals("newName", xmlBasicMapping.getName());
		assertEquals("newName", basicResource.getName());
	
		//set name to null in the resource model
		basicResource.setName(null);
		assertNull(xmlBasicMapping.getName());
		assertNull(basicResource.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertEquals("basicMapping", xmlBasicMapping.getName());
		assertEquals("basicMapping", basicResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlBasicMapping.setName("newName");
		assertEquals("newName", xmlBasicMapping.getName());
		assertEquals("newName", basicResource.getName());
	
		//set name to null in the context model
		xmlBasicMapping.setName(null);
		assertNull(xmlBasicMapping.getName());
		assertNull(basicResource.getName());
	}

	public void testUpdateSpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedFetch());
		assertNull(basicResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		basicResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, xmlBasicMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, basicResource.getFetch());
	
		basicResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, xmlBasicMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, basicResource.getFetch());

		//set fetch to null in the resource model
		basicResource.setFetch(null);
		assertNull(xmlBasicMapping.getSpecifiedFetch());
		assertNull(basicResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedFetch());
		assertNull(basicResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		xmlBasicMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, basicResource.getFetch());
		assertEquals(FetchType.EAGER, xmlBasicMapping.getSpecifiedFetch());
	
		xmlBasicMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, basicResource.getFetch());
		assertEquals(FetchType.LAZY, xmlBasicMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		xmlBasicMapping.setSpecifiedFetch(null);
		assertNull(basicResource.getFetch());
		assertNull(xmlBasicMapping.getSpecifiedFetch());
	}
	
	public void testUpdateSpecifiedEnumerated() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
		assertNull(basicResource.getEnumerated());
				
		//set enumerated in the resource model, verify context model updated
		basicResource.setEnumerated(org.eclipse.jpt.core.internal.resource.orm.EnumType.ORDINAL);
		assertEquals(EnumType.ORDINAL, xmlBasicMapping.getSpecifiedEnumerated());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.EnumType.ORDINAL, basicResource.getEnumerated());
	
		basicResource.setEnumerated(org.eclipse.jpt.core.internal.resource.orm.EnumType.STRING);
		assertEquals(EnumType.STRING, xmlBasicMapping.getSpecifiedEnumerated());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.EnumType.STRING, basicResource.getEnumerated());

		//set enumerated to null in the resource model
		basicResource.setEnumerated(null);
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
		assertNull(basicResource.getEnumerated());
	}
	
	public void testModifySpecifiedEnumerated() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
		assertNull(basicResource.getEnumerated());
				
		//set enumerated in the context model, verify resource model updated
		xmlBasicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.EnumType.ORDINAL, basicResource.getEnumerated());
		assertEquals(EnumType.ORDINAL, xmlBasicMapping.getSpecifiedEnumerated());
	
		xmlBasicMapping.setSpecifiedEnumerated(EnumType.STRING);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.EnumType.STRING, basicResource.getEnumerated());
		assertEquals(EnumType.STRING, xmlBasicMapping.getSpecifiedEnumerated());

		//set enumerated to null in the context model
		xmlBasicMapping.setSpecifiedEnumerated(null);
		assertNull(basicResource.getEnumerated());
		assertNull(xmlBasicMapping.getSpecifiedEnumerated());
	}
	
	public void testUpdateSpecifiedOptional() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedOptional());
		assertNull(basicResource.getOptional());
				
		//set enumerated in the resource model, verify context model updated
		basicResource.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlBasicMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, basicResource.getOptional());
	
		basicResource.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlBasicMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, basicResource.getOptional());

		//set enumerated to null in the resource model
		basicResource.setOptional(null);
		assertNull(xmlBasicMapping.getSpecifiedOptional());
		assertNull(basicResource.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		
		assertNull(xmlBasicMapping.getSpecifiedOptional());
		assertNull(basicResource.getOptional());
				
		//set enumerated in the context model, verify resource model updated
		xmlBasicMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, basicResource.getOptional());
		assertEquals(Boolean.TRUE, xmlBasicMapping.getSpecifiedOptional());
	
		xmlBasicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, basicResource.getOptional());
		assertEquals(Boolean.FALSE, xmlBasicMapping.getSpecifiedOptional());

		//set enumerated to null in the context model
		xmlBasicMapping.setSpecifiedOptional(null);
		assertNull(basicResource.getOptional());
		assertNull(xmlBasicMapping.getSpecifiedOptional());
	}
	
	public void testUpdateSpecifiedLob() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		ormResource().save(null);
		
		assertFalse(xmlBasicMapping.isLob());
		assertFalse(basicResource.isLob());
				
		//set lob in the resource model, verify context model updated
		basicResource.setLob(true);
		ormResource().save(null);
		assertTrue(xmlBasicMapping.isLob());
		assertTrue(basicResource.isLob());

		//set lob to null in the resource model
		basicResource.setLob(false);
		ormResource().save(null);
		assertFalse(xmlBasicMapping.isLob());
		assertFalse(basicResource.isLob());
	}
	
	public void testModifySpecifiedLob() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		ormResource().save(null);
	
		assertFalse(xmlBasicMapping.isLob());
		assertFalse(basicResource.isLob());
				
		//set lob in the context model, verify resource model updated
		xmlBasicMapping.setLob(true);
		ormResource().save(null);
		assertTrue(basicResource.isLob());
		assertTrue(xmlBasicMapping.isLob());
	
		//set lob to false in the context model
		xmlBasicMapping.setLob(false);
		ormResource().save(null);
		assertFalse(basicResource.isLob());
		assertFalse(xmlBasicMapping.isLob());
	}
	
	public void testUpdateTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		ormResource().save(null);
		
		assertNull(xmlBasicMapping.getTemporal());
		assertNull(basicResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		basicResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE);
		ormResource().save(null);
		assertEquals(TemporalType.DATE, xmlBasicMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, basicResource.getTemporal());
	
		basicResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME);
		ormResource().save(null);
		assertEquals(TemporalType.TIME, xmlBasicMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, basicResource.getTemporal());

		basicResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(TemporalType.TIMESTAMP, xmlBasicMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, basicResource.getTemporal());

		//set temporal to null in the resource model
		basicResource.setTemporal(null);
		ormResource().save(null);
		assertNull(xmlBasicMapping.getTemporal());
		assertNull(basicResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		Basic basicResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0);
		ormResource().save(null);
		
		assertNull(xmlBasicMapping.getTemporal());
		assertNull(basicResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		xmlBasicMapping.setTemporal(TemporalType.DATE);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, basicResource.getTemporal());
		assertEquals(TemporalType.DATE, xmlBasicMapping.getTemporal());
	
		xmlBasicMapping.setTemporal(TemporalType.TIME);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, basicResource.getTemporal());
		assertEquals(TemporalType.TIME, xmlBasicMapping.getTemporal());

		xmlBasicMapping.setTemporal(TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, basicResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, xmlBasicMapping.getTemporal());

		//set temporal to null in the context model
		xmlBasicMapping.setTemporal(null);
		ormResource().save(null);
		assertNull(basicResource.getTemporal());
		assertNull(xmlBasicMapping.getTemporal());
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

}