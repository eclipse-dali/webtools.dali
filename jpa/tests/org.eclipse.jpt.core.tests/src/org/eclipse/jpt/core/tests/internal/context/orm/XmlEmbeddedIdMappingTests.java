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
import org.eclipse.jpt.core.internal.context.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.internal.context.orm.XmlEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.EmbeddedId;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlEmbeddedIdMappingTests extends ContextModelTestCase
{
	public XmlEmbeddedIdMappingTests(String name) {
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
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		
		assertEquals("embeddedIdMapping", xmlEmbeddedIdMapping.getName());
		assertEquals("embeddedIdMapping", embeddedIdResource.getName());
				
		//set name in the resource model, verify context model updated
		embeddedIdResource.setName("newName");
		assertEquals("newName", xmlEmbeddedIdMapping.getName());
		assertEquals("newName", embeddedIdResource.getName());
	
		//set name to null in the resource model
		embeddedIdResource.setName(null);
		assertNull(xmlEmbeddedIdMapping.getName());
		assertNull(embeddedIdResource.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		
		assertEquals("embeddedIdMapping", xmlEmbeddedIdMapping.getName());
		assertEquals("embeddedIdMapping", embeddedIdResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlEmbeddedIdMapping.setName("newName");
		assertEquals("newName", xmlEmbeddedIdMapping.getName());
		assertEquals("newName", embeddedIdResource.getName());
	
		//set name to null in the context model
		xmlEmbeddedIdMapping.setName(null);
		assertNull(xmlEmbeddedIdMapping.getName());
		assertNull(embeddedIdResource.getName());
	}
	
	public void testAddSpecifiedAttributeOverride() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		
		XmlAttributeOverride attributeOverride = xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(0);
		ormResource().save(null);
		attributeOverride.setName("FOO");
		ormResource().save(null);
				
		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(0).getName());
		
		XmlAttributeOverride attributeOverride2 = xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(0);
		ormResource().save(null);
		attributeOverride2.setName("BAR");
		ormResource().save(null);
		
		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(0).getName());
		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(1).getName());
		
		XmlAttributeOverride attributeOverride3 = xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(1);
		ormResource().save(null);
		attributeOverride3.setName("BAZ");
		ormResource().save(null);
		
		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(2).getName());
		
		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
		assertEquals(attributeOverride2, attributeOverrides.next());
		assertEquals(attributeOverride3, attributeOverrides.next());
		assertEquals(attributeOverride, attributeOverrides.next());
		
		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
	}
	
	public void testRemoveSpecifiedAttributeOverride() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);

		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		assertEquals(3, embeddedIdResource.getAttributeOverrides().size());
		
		xmlEmbeddedIdMapping.removeSpecifiedAttributeOverride(0);
		assertEquals(2, embeddedIdResource.getAttributeOverrides().size());
		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(1).getName());

		xmlEmbeddedIdMapping.removeSpecifiedAttributeOverride(0);
		assertEquals(1, embeddedIdResource.getAttributeOverrides().size());
		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(0).getName());
		
		xmlEmbeddedIdMapping.removeSpecifiedAttributeOverride(0);
		assertEquals(0, embeddedIdResource.getAttributeOverrides().size());
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);

		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		assertEquals(3, embeddedIdResource.getAttributeOverrides().size());
		
		
		xmlEmbeddedIdMapping.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(2).getName());


		xmlEmbeddedIdMapping.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverrideImpl());
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverrideImpl());
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverrideImpl());
		
		embeddedIdResource.getAttributeOverrides().get(0).setName("FOO");
		embeddedIdResource.getAttributeOverrides().get(1).setName("BAR");
		embeddedIdResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedIdResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedIdResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedIdResource.getAttributeOverrides().remove(1);
		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedIdResource.getAttributeOverrides().remove(1);
		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedIdResource.getAttributeOverrides().remove(0);
		assertFalse(xmlEmbeddedIdMapping.specifiedAttributeOverrides().hasNext());
	}
}