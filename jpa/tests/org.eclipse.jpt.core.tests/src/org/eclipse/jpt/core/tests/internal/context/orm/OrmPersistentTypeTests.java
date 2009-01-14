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
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlBasicImpl;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedIdImpl;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedImpl;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlIdImpl;
import org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl;
import org.eclipse.jpt.core.resource.orm.XmlManyToOneImpl;
import org.eclipse.jpt.core.resource.orm.XmlOneToManyImpl;
import org.eclipse.jpt.core.resource.orm.XmlOneToOneImpl;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlTransientImpl;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.core.resource.orm.XmlVersionImpl;
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
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceResource().save(null);
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
		assertFalse(getEntityMappings().ormPersistentTypes().hasNext());
		assertTrue(getOrmResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getOrmResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(getOrmResource().getEntityMappings().getEmbeddables().isEmpty());
		
		OrmPersistentType embeddablePersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentType entityPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		OrmPersistentType mappedSuperclassPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
	
		OrmPersistentType ormPersistentType = getEntityMappings().ormPersistentTypes().next();
		assertEquals(mappedSuperclassPersistentType, ormPersistentType);
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, ormPersistentType.getMapping().getKey());
	
		ormPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertEquals(0, getOrmResource().getEntityMappings().getMappedSuperclasses().size());
		assertEquals(1, getOrmResource().getEntityMappings().getEntities().size());
		assertEquals(2, getOrmResource().getEntityMappings().getEmbeddables().size());
		
		Iterator<OrmPersistentType> ormPersistentTypes = getEntityMappings().ormPersistentTypes();
		//the same OrmPersistentTypes should still be in the context model
		assertEquals(ormPersistentTypes.next(), entityPersistentType);
		assertEquals(ormPersistentTypes.next(), embeddablePersistentType);
		assertEquals(ormPersistentTypes.next(), mappedSuperclassPersistentType);
		
		assertEquals("model.Foo", getOrmResource().getEntityMappings().getEmbeddables().get(0).getClassName());
		assertEquals("model.Foo3", getOrmResource().getEntityMappings().getEmbeddables().get(1).getClassName());
	}
	
	public void testAddSpecifiedPersistentAttribute() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicAttribute");
	
		XmlEntity entity = getOrmResource().getEntityMappings().getEntities().get(0);
		XmlBasic basic = entity.getAttributes().getBasics().get(0);
		assertEquals("basicAttribute", basic.getName());
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedAttribute");
	
		XmlEmbedded embedded = entity.getAttributes().getEmbeddeds().get(0);
		assertEquals("embeddedAttribute", embedded.getName());
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientAttribute");
	
		XmlTransient transientResource = entity.getAttributes().getTransients().get(0);
		assertEquals("transientAttribute", transientResource.getName());

		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionAttribute");
	
		XmlVersion version = entity.getAttributes().getVersions().get(0);
		assertEquals("versionAttribute", version.getName());

		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idAttribute");
	
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
		OrmPersistentType entityPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idAttribute");
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientAttribute");
	
		XmlEntity entity = getOrmResource().getEntityMappings().getEntities().get(0);
		assertEquals("basicAttribute",  entity.getAttributes().getBasics().get(0).getName());
		assertEquals("embeddedAttribute",  entity.getAttributes().getEmbeddeds().get(0).getName());
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("basicAttribute"));
		assertEquals("embeddedAttribute",  entity.getAttributes().getEmbeddeds().get(0).getName());
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("embeddedAttribute"));
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("versionAttribute"));
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("idAttribute"));
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());
		
		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("transientAttribute"));
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveId() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = getOrmResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idAttribute");
		assertEquals("idAttribute",  entity.getAttributes().getIds().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("idAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveBasic() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = getOrmResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicAttribute");
		assertEquals("basicAttribute",  entity.getAttributes().getBasics().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("basicAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveVersion() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = getOrmResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionAttribute");
		assertEquals("versionAttribute",  entity.getAttributes().getVersions().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("versionAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveEmbedded() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = getOrmResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedAttribute");
		assertEquals("embeddedAttribute",  entity.getAttributes().getEmbeddeds().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("embeddedAttribute"));	
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveTransient() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = getOrmResource().getEntityMappings().getEntities().get(0);
		
		entityPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientAttribute");
		assertEquals("transientAttribute",  entity.getAttributes().getTransients().get(0).getName());

		entityPersistentType.removeSpecifiedPersistentAttribute(entityPersistentType.getAttributeNamed("transientAttribute"));	
		assertNull(entity.getAttributes());
	}

	public void testUpdateSpecifiedPersistentAttributes() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = getOrmResource().getEntityMappings().getEntities().get(0);

		entity.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		XmlBasicImpl basic = OrmFactory.eINSTANCE.createXmlBasicImpl();
		entity.getAttributes().getBasics().add(basic);
		basic.setName("basicAttribute");
			
		OrmPersistentAttribute ormPersistentAttribute = entityPersistentType.attributes().next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		
		XmlEmbeddedImpl embedded = OrmFactory.eINSTANCE.createXmlEmbeddedImpl();
		entity.getAttributes().getEmbeddeds().add(embedded);
		embedded.setName("embeddedAttribute");
		
		ListIterator<OrmPersistentAttribute> attributes = entityPersistentType.attributes();
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
	
		XmlVersionImpl version = OrmFactory.eINSTANCE.createXmlVersionImpl();
		entity.getAttributes().getVersions().add(version);
		version.setName("versionAttribute");
		
		attributes = entityPersistentType.attributes();
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		XmlIdImpl id = OrmFactory.eINSTANCE.createXmlIdImpl();
		entity.getAttributes().getIds().add(id);
		id.setName("idAttribute");
		
		attributes = entityPersistentType.attributes();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		XmlTransientImpl transientResource = OrmFactory.eINSTANCE.createXmlTransientImpl();
		entity.getAttributes().getTransients().add(transientResource);
		transientResource.setName("transientAttribute");
		
		attributes = entityPersistentType.attributes();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		XmlManyToOneImpl manyToOneResource = OrmFactory.eINSTANCE.createXmlManyToOneImpl();
		entity.getAttributes().getManyToOnes().add(manyToOneResource);
		manyToOneResource.setName("manyToOneAttribute");
		
		attributes = entityPersistentType.attributes();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		XmlManyToManyImpl manyToManyResource = OrmFactory.eINSTANCE.createXmlManyToManyImpl();
		entity.getAttributes().getManyToManys().add(manyToManyResource);
		manyToManyResource.setName("manyToManyAttribute");
		
		attributes = entityPersistentType.attributes();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		XmlOneToManyImpl oneToManyResource = OrmFactory.eINSTANCE.createXmlOneToManyImpl();
		entity.getAttributes().getOneToManys().add(oneToManyResource);
		oneToManyResource.setName("oneToManyAttribute");
		
		attributes = entityPersistentType.attributes();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		XmlOneToOneImpl oneToOneResource = OrmFactory.eINSTANCE.createXmlOneToOneImpl();
		entity.getAttributes().getOneToOnes().add(oneToOneResource);
		oneToOneResource.setName("oneToOneAttribute");
		
		attributes = entityPersistentType.attributes();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());


		XmlEmbeddedIdImpl embeddedIdResource = OrmFactory.eINSTANCE.createXmlEmbeddedIdImpl();
		entity.getAttributes().getEmbeddedIds().add(embeddedIdResource);
		embeddedIdResource.setName("embeddedIdAttribute");
		
		attributes = entityPersistentType.attributes();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedIdAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
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