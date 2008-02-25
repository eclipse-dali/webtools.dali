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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.BasicImpl;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.EmbeddedIdImpl;
import org.eclipse.jpt.core.resource.orm.EmbeddedImpl;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.IdImpl;
import org.eclipse.jpt.core.resource.orm.ManyToManyImpl;
import org.eclipse.jpt.core.resource.orm.ManyToOneImpl;
import org.eclipse.jpt.core.resource.orm.OneToManyImpl;
import org.eclipse.jpt.core.resource.orm.OneToOneImpl;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.TransientImpl;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.core.resource.orm.VersionImpl;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class OrmPersistentTypeTests extends ContextModelTestCase
{
	public OrmPersistentTypeTests(String name) {
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
	
//	public void testUpdateXmlTypeMapping() throws Exception {
//		assertFalse(entityMappings().ormPersistentTypes().hasNext());
//		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		
//		//add embeddable in the resource model, verify context model updated
//		Embeddable embeddable = OrmFactory.eINSTANCE.createEmbeddable();
//		ormResource().getEntityMappings().getEmbeddables().add(embeddable);
//		embeddable.setClassName("model.Foo");
//		assertTrue(entityMappings().ormPersistentTypes().hasNext());
//		assertEquals("model.Foo", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
//		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		assertEquals("model.Foo", ormResource().getEntityMappings().getEmbeddables().get(0).getClassName());
//		
//		//add entity in the resource model, verify context model updated
//		Entity entity = OrmFactory.eINSTANCE.createEntity();
//		ormResource().getEntityMappings().getEntities().add(entity);
//		entity.setClassName("model.Foo2");
//		assertTrue(entityMappings().ormPersistentTypes().hasNext());
//		assertEquals("model.Foo2", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
//		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		assertEquals("model.Foo2", ormResource().getEntityMappings().getEntities().get(0).getClassName());
//
//		//add mapped-superclass in the resource model, verify context model updated
//		MappedSuperclass mappedSuperclass = OrmFactory.eINSTANCE.createMappedSuperclass();
//		ormResource().getEntityMappings().getMappedSuperclasses().add(mappedSuperclass);
//		mappedSuperclass.setClassName("model.Foo3");
//		assertTrue(entityMappings().ormPersistentTypes().hasNext());
//		assertEquals("model.Foo3", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
//		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		assertEquals("model.Foo3", ormResource().getEntityMappings().getMappedSuperclasses().get(0).getClassName());
//	}
//	
	
	public void testMorphXmlTypeMapping() throws Exception {
		assertFalse(entityMappings().ormPersistentTypes().hasNext());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		OrmPersistentType embeddablePersistentType = entityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		OrmPersistentType mappedSuperclassPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
		ormResource().save(null);
	
		OrmPersistentType ormPersistentType = entityMappings().ormPersistentTypes().next();
		assertEquals(mappedSuperclassPersistentType, ormPersistentType);
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, ormPersistentType.getMapping().getKey());
	
		ormPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		ormResource().save(null);
		assertEquals(0, ormResource().getEntityMappings().getMappedSuperclasses().size());
		assertEquals(1, ormResource().getEntityMappings().getEntities().size());
		assertEquals(2, ormResource().getEntityMappings().getEmbeddables().size());
		
		Iterator<OrmPersistentType> ormPersistentTypes = entityMappings().ormPersistentTypes();
		//the same OrmPersistentTypes should still be in the context model
		assertEquals(ormPersistentTypes.next(), entityPersistentType);
		assertEquals(ormPersistentTypes.next(), embeddablePersistentType);
		assertEquals(ormPersistentTypes.next(), mappedSuperclassPersistentType);
		
		assertEquals("model.Foo", ormResource().getEntityMappings().getEmbeddables().get(0).getClassName());
		assertEquals("model.Foo3", ormResource().getEntityMappings().getEmbeddables().get(1).getClassName());
	}
	
	public void testAddSpecifiedPersistentAttribute() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicAttribute");
		ormResource().save(null);
	
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);
		XmlBasic basic = entity.getAttributes().getBasics().get(0);
		assertEquals("basicAttribute", basic.getName());
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedAttribute");
		ormResource().save(null);
	
		XmlEmbedded embedded = entity.getAttributes().getEmbeddeds().get(0);
		assertEquals("embeddedAttribute", embedded.getName());
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientAttribute");
		ormResource().save(null);
	
		XmlTransient transientResource = entity.getAttributes().getTransients().get(0);
		assertEquals("transientAttribute", transientResource.getName());

		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionAttribute");
		ormResource().save(null);
	
		XmlVersion version = entity.getAttributes().getVersions().get(0);
		assertEquals("versionAttribute", version.getName());

		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idAttribute");
		ormResource().save(null);
	
		XmlId id = entity.getAttributes().getIds().get(0);
		assertEquals("idAttribute", id.getName());
		
		
		ListIterator<OrmPersistentAttribute> persistentAttributes = entityPersistentType.specifiedAttributes();
		assertEquals("idAttribute", persistentAttributes.next().getName());
		assertEquals("basicAttribute", persistentAttributes.next().getName());
		assertEquals("versionAttribute", persistentAttributes.next().getName());
		assertEquals("embeddedAttribute", persistentAttributes.next().getName());
		assertEquals("transientAttribute", persistentAttributes.next().getName());
		assertFalse(persistentAttributes.hasNext());
	}
	
	public void testRemoveSpecifiedPersistentAttribute() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientAttribute");
		ormResource().save(null);
	
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("basicAttribute",  entity.getAttributes().getBasics().get(0).getName());
		assertEquals("embeddedAttribute",  entity.getAttributes().getEmbeddeds().get(0).getName());
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("basicAttribute"));
		assertEquals("embeddedAttribute",  entity.getAttributes().getEmbeddeds().get(0).getName());
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("embeddedAttribute"));
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("versionAttribute"));
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("idAttribute"));
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("transientAttribute"));
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveId() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idAttribute");
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("idAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveBasic() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicAttribute");
		assertEquals("basicAttribute",  entity.getAttributes().getBasics().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("basicAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveVersion() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionAttribute");
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("versionAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveEmbedded() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedAttribute");
		assertEquals("embeddedAttribute",  entity.getAttributes().getEmbeddeds().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("embeddedAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveTransient() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientAttribute");
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.attributeNamed("transientAttribute"));	
		assertNull(entity.getAttributes());
	}

	public void testUpdateSpecifiedPersistentAttributes() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = ormResource().getEntityMappings().getEntities().get(0);

		entity.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		BasicImpl basic = OrmFactory.eINSTANCE.createBasicImpl();
		entity.getAttributes().getBasics().add(basic);
		basic.setName("basicAttribute");
			
		OrmPersistentAttribute xmlPersistentAttribute = entityPersistentType.attributes().next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		
		EmbeddedImpl embedded = OrmFactory.eINSTANCE.createEmbeddedImpl();
		entity.getAttributes().getEmbeddeds().add(embedded);
		embedded.setName("embeddedAttribute");
		
		ListIterator<OrmPersistentAttribute> attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
	
		VersionImpl version = OrmFactory.eINSTANCE.createVersionImpl();
		entity.getAttributes().getVersions().add(version);
		version.setName("versionAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		IdImpl id = OrmFactory.eINSTANCE.createIdImpl();
		entity.getAttributes().getIds().add(id);
		id.setName("idAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("idAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		TransientImpl transientResource = OrmFactory.eINSTANCE.createTransientImpl();
		entity.getAttributes().getTransients().add(transientResource);
		transientResource.setName("transientAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("idAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		ManyToOneImpl manyToOneResource = OrmFactory.eINSTANCE.createManyToOneImpl();
		entity.getAttributes().getManyToOnes().add(manyToOneResource);
		manyToOneResource.setName("manyToOneAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("idAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		ManyToManyImpl manyToManyResource = OrmFactory.eINSTANCE.createManyToManyImpl();
		entity.getAttributes().getManyToManys().add(manyToManyResource);
		manyToManyResource.setName("manyToManyAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("idAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		OneToManyImpl oneToManyResource = OrmFactory.eINSTANCE.createOneToManyImpl();
		entity.getAttributes().getOneToManys().add(oneToManyResource);
		oneToManyResource.setName("oneToManyAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("idAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("oneToManyAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		OneToOneImpl oneToOneResource = OrmFactory.eINSTANCE.createOneToOneImpl();
		entity.getAttributes().getOneToOnes().add(oneToOneResource);
		oneToOneResource.setName("oneToOneAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("idAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("oneToManyAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("oneToOneAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());


		EmbeddedIdImpl embeddedIdResource = OrmFactory.eINSTANCE.createEmbeddedIdImpl();
		entity.getAttributes().getEmbeddedIds().add(embeddedIdResource);
		embeddedIdResource.setName("embeddedIdAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("idAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedIdAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("oneToManyAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("oneToOneAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", xmlPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		entity.getAttributes().getBasics().remove(0);
		entity.getAttributes().getEmbeddeds().remove(0);
		entity.getAttributes().getTransients().remove(0);
		entity.getAttributes().getIds().remove(0);
		entity.getAttributes().getVersions().remove(0);
		entity.getAttributes().getManyToOnes().remove(0);
		entity.getAttributes().getManyToManys().remove(0);
		entity.getAttributes().getOneToManys().remove(0);
		entity.getAttributes().getOneToOnes().remove(0);
		entity.getAttributes().getEmbeddedIds().remove(0);
		assertFalse(entityPersistentType.attributes().hasNext());
		assertNotNull(entity.getAttributes());
	}
}