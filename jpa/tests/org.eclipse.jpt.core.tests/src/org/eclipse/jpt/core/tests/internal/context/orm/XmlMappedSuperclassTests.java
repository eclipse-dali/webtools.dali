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
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlMappedSuperclassTests extends ContextModelTestCase
{
	public XmlMappedSuperclassTests(String name) {
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", xmlMappedSuperclass.getClass_());
		assertEquals("model.Foo", mappedSuperclassResource.getClassName());
		
		//set class in the resource model, verify context model updated
		mappedSuperclassResource.setClassName("com.Bar");
		ormResource().save(null);
		assertEquals("com.Bar", xmlMappedSuperclass.getClass_());
		assertEquals("com.Bar", mappedSuperclassResource.getClassName());
	
		//set class to null in the resource model
		mappedSuperclassResource.setClassName(null);
		assertNull(xmlMappedSuperclass.getClass_());
		assertNull(mappedSuperclassResource.getClassName());
	}
	
	public void testModifyClass() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", xmlMappedSuperclass.getClass_());
		assertEquals("model.Foo", mappedSuperclassResource.getClassName());
		
		//set class in the context model, verify resource model modified
		xmlMappedSuperclass.setClass("com.Bar");
		assertEquals("com.Bar", xmlMappedSuperclass.getClass_());
		assertEquals("com.Bar", mappedSuperclassResource.getClassName());
		
		//set class to null in the context model
		xmlMappedSuperclass.setClass(null);
		assertNull(xmlMappedSuperclass.getClass_());
		assertNull(mappedSuperclassResource.getClassName());
	}
	//TODO add tests for setting the className when the package is set on entity-mappings
	
	public void testUpdateSpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertNull(xmlMappedSuperclass.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
		
		//set access in the resource model, verify context model updated
		mappedSuperclassResource.setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, xmlMappedSuperclass.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.FIELD, mappedSuperclassResource.getAccess());
	
		//set access to null in the resource model
		mappedSuperclassResource.setAccess(null);
		assertNull(xmlMappedSuperclass.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertNull(xmlMappedSuperclass.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
		
		//set access in the context model, verify resource model modified
		xmlMappedSuperclass.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, xmlMappedSuperclass.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, mappedSuperclassResource.getAccess());
		
		//set access to null in the context model
		xmlMappedSuperclass.setSpecifiedAccess(null);
		assertNull(xmlMappedSuperclass.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
	}
	//TODO test default access from
		//underlying java
		//persistence-unit-defaults
		//entity-mappings
		//with xml-mapping-metadata-complete set
	
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		//set metadata-complete in the resource model, verify context model updated
		mappedSuperclassResource.setMetadataComplete(true);
		assertTrue(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertTrue(mappedSuperclassResource.getMetadataComplete());
	
		//set access to false in the resource model
		mappedSuperclassResource.setMetadataComplete(false);
		assertFalse(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(mappedSuperclassResource.getMetadataComplete());
		
		mappedSuperclassResource.setMetadataComplete(null);
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}
	
	public void testModifySpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		//set access in the context model, verify resource model modified
		xmlMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertTrue(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertTrue(mappedSuperclassResource.getMetadataComplete());
		
		//set access to null in the context model
		xmlMappedSuperclass.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertFalse(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(mappedSuperclassResource.getMetadataComplete());
		
		xmlMappedSuperclass.setSpecifiedMetadataComplete(null);
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}
	
	public void testUpdateDefaultMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(xmlMappedSuperclass.isDefaultMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlMappedSuperclass.isDefaultMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(xmlMappedSuperclass.isDefaultMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}
	
	public void testUpdateMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(xmlMappedSuperclass.isMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlMappedSuperclass.isMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(xmlMappedSuperclass.isMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}

	
	public void testMakeMappedSuperclassEntity() throws Exception {
		OrmPersistentType mappedSuperclassPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass mappedSuperclass = (GenericOrmMappedSuperclass) mappedSuperclassPersistentType.getMapping();
		mappedSuperclass.setSpecifiedAccess(AccessType.PROPERTY);
		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		ormResource().save(null);
	
		mappedSuperclassPersistentType.setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", entity.getClassName());
		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, entity.getAccess());
		assertNull(entity.getDiscriminatorValue());
		assertNull(entity.getName());
		
		GenericOrmEntity xmlEntity = (GenericOrmEntity) mappedSuperclassPersistentType.getMapping();
		assertEquals("model.Foo", xmlEntity.getClass_());
		assertEquals(Boolean.TRUE, xmlEntity.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlEntity.getSpecifiedAccess());
	}
		
	//test with 2 MappedSuperclasses, make the first one an Entity so it has to move to the end of the list
	public void testMakeMappedSuperclassEntity2() throws Exception {
		OrmPersistentType mappedSuperclassPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo2");
		GenericOrmMappedSuperclass mappedSuperclass = (GenericOrmMappedSuperclass) mappedSuperclassPersistentType.getMapping();
		mappedSuperclass.setSpecifiedAccess(AccessType.PROPERTY);
		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		ormResource().save(null);
	
		mappedSuperclassPersistentType.setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", entity.getClassName());
		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, entity.getAccess());
		assertNull(entity.getDiscriminatorValue());
		assertNull(entity.getName());
		
		GenericOrmEntity xmlEntity = (GenericOrmEntity) mappedSuperclassPersistentType.getMapping();
		assertEquals("model.Foo", xmlEntity.getClass_());
		assertEquals(Boolean.TRUE, xmlEntity.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlEntity.getSpecifiedAccess());
		
		ListIterator<OrmPersistentType> persistentTypes = entityMappings().ormPersistentTypes();
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
	}
	
	public void testMakeMappedSuperclassEmbeddable() throws Exception {
		OrmPersistentType mappedSuperclassPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass mappedSuperclass = (GenericOrmMappedSuperclass) mappedSuperclassPersistentType.getMapping();
		mappedSuperclass.setSpecifiedAccess(AccessType.PROPERTY);
		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		ormResource().save(null);
	
		mappedSuperclassPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		XmlEmbeddable embeddable = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
		
		GenericOrmEmbeddable xmlEmbeddable = (GenericOrmEmbeddable) mappedSuperclassPersistentType.getMapping();
		assertEquals("model.Foo", xmlEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, xmlEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlEmbeddable.getSpecifiedAccess());
	}
	//test with 2 MappedSuperclasses, make the first one an Embeddable so it has to move to the end of the list
	public void testMakeMappedSuperclassEmbeddable2() throws Exception {
		OrmPersistentType mappedSuperclassPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo2");
		GenericOrmMappedSuperclass mappedSuperclass = (GenericOrmMappedSuperclass) mappedSuperclassPersistentType.getMapping();
		mappedSuperclass.setSpecifiedAccess(AccessType.PROPERTY);
		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		ormResource().save(null);
	
		mappedSuperclassPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		XmlEmbeddable embeddable = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
		
		GenericOrmEmbeddable xmlEmbeddable = (GenericOrmEmbeddable) mappedSuperclassPersistentType.getMapping();
		assertEquals("model.Foo", xmlEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, xmlEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlEmbeddable.getSpecifiedAccess());
		
		ListIterator<OrmPersistentType> persistentTypes = entityMappings().ormPersistentTypes();
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
		assertEquals(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, persistentTypes.next().mappingKey());
	}
	
	public void testUpdateIdClass() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());

		assertNull(xmlMappedSuperclass.getIdClass());
		assertNull(mappedSuperclassResource.getIdClass());
		
		mappedSuperclassResource.setIdClass(OrmFactory.eINSTANCE.createIdClass());
		
		assertNull(xmlMappedSuperclass.getIdClass());
		assertNotNull(mappedSuperclassResource.getIdClass());
		
		mappedSuperclassResource.getIdClass().setClassName("model.Foo");
		assertEquals("model.Foo", xmlMappedSuperclass.getIdClass());
		assertEquals("model.Foo", mappedSuperclassResource.getIdClass().getClassName());
		
		//test setting  @IdClass value to null, id-class tag is not removed
		mappedSuperclassResource.getIdClass().setClassName(null);
		assertNull(xmlMappedSuperclass.getIdClass());
		assertNotNull(mappedSuperclassResource.getIdClass());
		
		//reset @IdClass value and then remove id-class tag
		mappedSuperclassResource.setIdClass(OrmFactory.eINSTANCE.createIdClass());
		mappedSuperclassResource.getIdClass().setClassName("model.Foo");
		mappedSuperclassResource.setIdClass(null);
		
		assertNull(xmlMappedSuperclass.getIdClass());
		assertNull(mappedSuperclassResource.getIdClass());
	}
	
	public void testModifyIdClass() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmMappedSuperclass xmlMappedSuperclass = (GenericOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());

		assertNull(xmlMappedSuperclass.getIdClass());
		assertNull(mappedSuperclassResource.getIdClass());
			
		xmlMappedSuperclass.setIdClass("model.Foo");
		assertEquals("model.Foo", mappedSuperclassResource.getIdClass().getClassName());
		assertEquals("model.Foo", xmlMappedSuperclass.getIdClass());
		
		xmlMappedSuperclass.setIdClass(null);
		assertNull(xmlMappedSuperclass.getIdClass());
		assertNull(mappedSuperclassResource.getIdClass());
	}
}