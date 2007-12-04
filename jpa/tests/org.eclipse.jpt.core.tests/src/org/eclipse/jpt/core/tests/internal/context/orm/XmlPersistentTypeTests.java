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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
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
		
	protected XmlPersistence xmlPersistence() {
		return persistenceResource().getPersistence();
	}
	
	protected EntityMappings entityMappings() {
		return persistenceUnit().mappingFileRefs().next().getOrmXml().getEntityMappings();
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
	
	public void testModifyXmlTypeMapping() throws Exception {
		assertFalse(entityMappings().xmlPersistentTypes().hasNext());
		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
		
		XmlPersistentType embeddablePersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType("model.Foo2", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlPersistentType mappedSuperclassPersistentType = entityMappings().addXmlPersistentType("model.Foo3", IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
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

}