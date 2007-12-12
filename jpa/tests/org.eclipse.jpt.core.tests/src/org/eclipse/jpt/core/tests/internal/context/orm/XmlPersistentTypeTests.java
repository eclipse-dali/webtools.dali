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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.Embedded;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.Id;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.Transient;
import org.eclipse.jpt.core.internal.resource.orm.Version;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlPersistentTypeTests extends ContextModelTestCase
{
	public XmlPersistentTypeTests(String name) {
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
//		assertFalse(entityMappings().xmlPersistentTypes().hasNext());
//		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		
//		//add embeddable in the resource model, verify context model updated
//		Embeddable embeddable = OrmFactory.eINSTANCE.createEmbeddable();
//		ormResource().getEntityMappings().getEmbeddables().add(embeddable);
//		embeddable.setClassName("model.Foo");
//		assertTrue(entityMappings().xmlPersistentTypes().hasNext());
//		assertEquals("model.Foo", entityMappings().xmlPersistentTypes().next().getMapping().getClass_());
//		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		assertEquals("model.Foo", ormResource().getEntityMappings().getEmbeddables().get(0).getClassName());
//		
//		//add entity in the resource model, verify context model updated
//		Entity entity = OrmFactory.eINSTANCE.createEntity();
//		ormResource().getEntityMappings().getEntities().add(entity);
//		entity.setClassName("model.Foo2");
//		assertTrue(entityMappings().xmlPersistentTypes().hasNext());
//		assertEquals("model.Foo2", entityMappings().xmlPersistentTypes().next().getMapping().getClass_());
//		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		assertEquals("model.Foo2", ormResource().getEntityMappings().getEntities().get(0).getClassName());
//
//		//add mapped-superclass in the resource model, verify context model updated
//		MappedSuperclass mappedSuperclass = OrmFactory.eINSTANCE.createMappedSuperclass();
//		ormResource().getEntityMappings().getMappedSuperclasses().add(mappedSuperclass);
//		mappedSuperclass.setClassName("model.Foo3");
//		assertTrue(entityMappings().xmlPersistentTypes().hasNext());
//		assertEquals("model.Foo3", entityMappings().xmlPersistentTypes().next().getMapping().getClass_());
//		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		assertEquals("model.Foo3", ormResource().getEntityMappings().getMappedSuperclasses().get(0).getClassName());
//	}
//	
	
	public void testMorphXmlTypeMapping() throws Exception {
		assertFalse(entityMappings().xmlPersistentTypes().hasNext());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		XmlPersistentType embeddablePersistentType = entityMappings().addXmlPersistentType(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlPersistentType mappedSuperclassPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
		ormResource().save(null);
	
		XmlPersistentType xmlPersistentType = entityMappings().xmlPersistentTypes().next();
		assertEquals(mappedSuperclassPersistentType, xmlPersistentType);
		assertEquals(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, xmlPersistentType.getMapping().getKey());
	
		xmlPersistentType.setMappingKey(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		ormResource().save(null);
		assertEquals(0, ormResource().getEntityMappings().getMappedSuperclasses().size());
		assertEquals(1, ormResource().getEntityMappings().getEntities().size());
		assertEquals(2, ormResource().getEntityMappings().getEmbeddables().size());
		
		Iterator<XmlPersistentType> xmlPersistentTypes = entityMappings().xmlPersistentTypes();
		//the same XmlPersistentTypes should still be in the context model
		assertEquals(xmlPersistentTypes.next(), entityPersistentType);
		assertEquals(xmlPersistentTypes.next(), embeddablePersistentType);
		assertEquals(xmlPersistentTypes.next(), mappedSuperclassPersistentType);
		
		assertEquals("model.Foo", ormResource().getEntityMappings().getEmbeddables().get(0).getClassName());
		assertEquals("model.Foo3", ormResource().getEntityMappings().getEmbeddables().get(1).getClassName());
	}
	
	public void testAddSpecifiedPersistentAttribute() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicAttribute");
		ormResource().save(null);
	
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entity.getAttributes().getBasics().get(0);
		assertEquals("basicAttribute", basic.getName());
		
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedAttribute");
		ormResource().save(null);
	
		Embedded embedded = entity.getAttributes().getEmbeddeds().get(0);
		assertEquals("embeddedAttribute", embedded.getName());
		
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientAttribute");
		ormResource().save(null);
	
		Transient transientResource = entity.getAttributes().getTransients().get(0);
		assertEquals("transientAttribute", transientResource.getName());

		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionAttribute");
		ormResource().save(null);
	
		Version version = entity.getAttributes().getVersions().get(0);
		assertEquals("versionAttribute", version.getName());

		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idAttribute");
		ormResource().save(null);
	
		Id id = entity.getAttributes().getIds().get(0);
		assertEquals("idAttribute", id.getName());
		
		
		ListIterator<XmlPersistentAttribute> persistentAttributes = entityPersistentType.specifiedAttributes();
		assertEquals("idAttribute", persistentAttributes.next().getName());
		assertEquals("basicAttribute", persistentAttributes.next().getName());
		assertEquals("versionAttribute", persistentAttributes.next().getName());
		assertEquals("embeddedAttribute", persistentAttributes.next().getName());
		assertEquals("transientAttribute", persistentAttributes.next().getName());
		assertFalse(persistentAttributes.hasNext());
	}
	
	public void testRemoveSpecifiedPersistentAttribute() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientAttribute");
		ormResource().save(null);
	
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("basicAttribute",  entity.getAttributes().getBasics().get(0).getName());
		assertEquals("embeddedAttribute",  entity.getAttributes().getEmbeddeds().get(0).getName());
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("basicAttribute"));
		assertEquals("embeddedAttribute",  entity.getAttributes().getEmbeddeds().get(0).getName());
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("embeddedAttribute"));
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("versionAttribute"));
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("idAttribute"));
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("transientAttribute"));
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveId() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idAttribute");
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());

		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("idAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveBasic() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicAttribute");
		assertEquals("basicAttribute",  entity.getAttributes().getBasics().get(0).getName());

		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("basicAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveVersion() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionAttribute");
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());

		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("versionAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveEmbedded() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedAttribute");
		assertEquals("embeddedAttribute",  entity.getAttributes().getEmbeddeds().get(0).getName());

		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("embeddedAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveTransient() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientAttribute");
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());

		entityPersistentType.removeSpecifiedXmlPersistentAttribute(entityPersistentType.attributeNamed("transientAttribute"));	
		assertNull(entity.getAttributes());
	}

	public void testUpdateSpecifiedPersistentAttributes() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		Entity entity = ormResource().getEntityMappings().getEntities().get(0);

		entity.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		Basic basic = OrmFactory.eINSTANCE.createBasic();
		entity.getAttributes().getBasics().add(basic);
		basic.setName("basicAttribute");
			
		XmlPersistentAttribute xmlPersistentAttribute = entityPersistentType.attributes().next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		
		Embedded embedded = OrmFactory.eINSTANCE.createEmbedded();
		entity.getAttributes().getEmbeddeds().add(embedded);
		embedded.setName("embeddedAttribute");
		
		ListIterator<XmlPersistentAttribute> attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
	
		Version version = OrmFactory.eINSTANCE.createVersion();
		entity.getAttributes().getVersions().add(version);
		version.setName("versionAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		Id id = OrmFactory.eINSTANCE.createId();
		entity.getAttributes().getIds().add(id);
		id.setName("idAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("idAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		Transient transientResource = OrmFactory.eINSTANCE.createTransient();
		entity.getAttributes().getTransients().add(transientResource);
		transientResource.setName("transientAttribute");
		
		attributes = entityPersistentType.attributes();
		xmlPersistentAttribute = attributes.next();
		assertEquals("idAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		xmlPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", xmlPersistentAttribute.getName());
		assertEquals(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, xmlPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		entity.getAttributes().getBasics().remove(0);
		entity.getAttributes().getEmbeddeds().remove(0);
		entity.getAttributes().getTransients().remove(0);
		entity.getAttributes().getIds().remove(0);
		entity.getAttributes().getVersions().remove(0);
		assertFalse(entityPersistentType.attributes().hasNext());
		assertNotNull(entity.getAttributes());
	}
}