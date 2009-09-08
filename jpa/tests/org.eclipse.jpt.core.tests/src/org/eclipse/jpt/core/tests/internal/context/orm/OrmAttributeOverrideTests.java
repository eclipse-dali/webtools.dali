/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmAttributeOverrideTests extends ContextModelTestCase
{
	public OrmAttributeOverrideTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		XmlAttributeOverride attributeOverrideResource = entityResource.getAttributeOverrides().get(0);
		OrmAttributeOverride ormAttributeOverride = overrideContainer.specifiedAttributeOverrides().next();
		
		assertNull(ormAttributeOverride.getName());
		assertNull(attributeOverrideResource.getName());
		assertTrue(overrideContainer.attributeOverrides().hasNext());
		assertFalse(entityResource.getAttributeOverrides().isEmpty());
		
		//set name in the resource model, verify context model updated
		attributeOverrideResource.setName("FOO");
		assertEquals("FOO", ormAttributeOverride.getName());
		assertEquals("FOO", attributeOverrideResource.getName());
	
		//set name to null in the resource model
		attributeOverrideResource.setName(null);
		assertNull(ormAttributeOverride.getName());
		assertNull(attributeOverrideResource.getName());
		
		attributeOverrideResource.setName("FOO");
		assertEquals("FOO", ormAttributeOverride.getName());
		assertEquals("FOO", attributeOverrideResource.getName());

		entityResource.getAttributeOverrides().remove(0);
		assertFalse(overrideContainer.attributeOverrides().hasNext());
		assertTrue(entityResource.getAttributeOverrides().isEmpty());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		XmlAttributeOverride attributeOverrideResource = entityResource.getAttributeOverrides().get(0);
		OrmAttributeOverride ormAttributeOverride = overrideContainer.specifiedAttributeOverrides().next();

		assertNull(ormAttributeOverride.getName());
		assertNull(attributeOverrideResource.getName());
		
		//set name in the context model, verify resource model modified
		ormAttributeOverride.setName("foo");
		assertEquals("foo", ormAttributeOverride.getName());
		assertEquals("foo", attributeOverrideResource.getName());
		
		//set name to null in the context model
		ormAttributeOverride.setName(null);
		assertNull(ormAttributeOverride.getName());
		assertNull(entityResource.getAttributeOverrides().get(0).getName());
	}

}