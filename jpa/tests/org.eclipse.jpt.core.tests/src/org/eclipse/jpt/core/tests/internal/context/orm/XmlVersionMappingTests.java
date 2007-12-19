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
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlVersionMapping;
import org.eclipse.jpt.core.internal.resource.orm.Version;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlVersionMappingTests extends ContextModelTestCase
{
	public XmlVersionMappingTests(String name) {
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
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		Version versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
		assertEquals("versionMapping", xmlVersionMapping.getName());
		assertEquals("versionMapping", versionResource.getName());
				
		//set name in the resource model, verify context model updated
		versionResource.setName("newName");
		assertEquals("newName", xmlVersionMapping.getName());
		assertEquals("newName", versionResource.getName());
	
		//set name to null in the resource model
		versionResource.setName(null);
		assertNull(xmlVersionMapping.getName());
		assertNull(versionResource.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		Version versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
		assertEquals("versionMapping", xmlVersionMapping.getName());
		assertEquals("versionMapping", versionResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlVersionMapping.setName("newName");
		assertEquals("newName", xmlVersionMapping.getName());
		assertEquals("newName", versionResource.getName());
	
		//set name to null in the context model
		xmlVersionMapping.setName(null);
		assertNull(xmlVersionMapping.getName());
		assertNull(versionResource.getName());
	}	
	
	public void testUpdateTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		Version versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		ormResource().save(null);
		
		assertNull(xmlVersionMapping.getTemporal());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		versionResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE);
		ormResource().save(null);
		assertEquals(TemporalType.DATE, xmlVersionMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, versionResource.getTemporal());
	
		versionResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME);
		ormResource().save(null);
		assertEquals(TemporalType.TIME, xmlVersionMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, versionResource.getTemporal());

		versionResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(TemporalType.TIMESTAMP, xmlVersionMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());

		//set temporal to null in the resource model
		versionResource.setTemporal(null);
		ormResource().save(null);
		assertNull(xmlVersionMapping.getTemporal());
		assertNull(versionResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		Version versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		ormResource().save(null);
		
		assertNull(xmlVersionMapping.getTemporal());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		xmlVersionMapping.setTemporal(TemporalType.DATE);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, versionResource.getTemporal());
		assertEquals(TemporalType.DATE, xmlVersionMapping.getTemporal());
	
		xmlVersionMapping.setTemporal(TemporalType.TIME);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, versionResource.getTemporal());
		assertEquals(TemporalType.TIME, xmlVersionMapping.getTemporal());

		xmlVersionMapping.setTemporal(TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, xmlVersionMapping.getTemporal());

		//set temporal to null in the context model
		xmlVersionMapping.setTemporal(null);
		ormResource().save(null);
		assertNull(versionResource.getTemporal());
		assertNull(xmlVersionMapping.getTemporal());
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