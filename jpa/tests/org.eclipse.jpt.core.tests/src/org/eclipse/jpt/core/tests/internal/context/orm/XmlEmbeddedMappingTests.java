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
import org.eclipse.jpt.core.internal.context.orm.XmlEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.Embedded;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlEmbeddedMappingTests extends ContextModelTestCase
{
	public XmlEmbeddedMappingTests(String name) {
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

	
	public void testAddSpecifiedAttributeOverride() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		XmlEmbeddedMapping xmlEmbeddedMapping = (XmlEmbeddedMapping) xmlPersistentAttribute.getMapping();
		Embedded embeddedResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		XmlAttributeOverride attributeOverride = xmlEmbeddedMapping.addSpecifiedAttributeOverride(0);
		ormResource().save(null);
		attributeOverride.setName("FOO");
		ormResource().save(null);
				
		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(0).getName());
		
		XmlAttributeOverride attributeOverride2 = xmlEmbeddedMapping.addSpecifiedAttributeOverride(0);
		ormResource().save(null);
		attributeOverride2.setName("BAR");
		ormResource().save(null);
		
		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(0).getName());
		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(1).getName());
		
		XmlAttributeOverride attributeOverride3 = xmlEmbeddedMapping.addSpecifiedAttributeOverride(1);
		ormResource().save(null);
		attributeOverride3.setName("BAZ");
		ormResource().save(null);
		
		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(2).getName());
		
		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEmbeddedMapping.specifiedAttributeOverrides();
		assertEquals(attributeOverride2, attributeOverrides.next());
		assertEquals(attributeOverride3, attributeOverrides.next());
		assertEquals(attributeOverride, attributeOverrides.next());
		
		attributeOverrides = xmlEmbeddedMapping.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
	}
	
	public void testRemoveSpecifiedAttributeOverride() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		XmlEmbeddedMapping xmlEmbeddedMapping = (XmlEmbeddedMapping) xmlPersistentAttribute.getMapping();
		Embedded embeddedResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);

		xmlEmbeddedMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		xmlEmbeddedMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		xmlEmbeddedMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		assertEquals(3, embeddedResource.getAttributeOverrides().size());
		
		xmlEmbeddedMapping.removeSpecifiedAttributeOverride(0);
		assertEquals(2, embeddedResource.getAttributeOverrides().size());
		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(1).getName());

		xmlEmbeddedMapping.removeSpecifiedAttributeOverride(0);
		assertEquals(1, embeddedResource.getAttributeOverrides().size());
		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(0).getName());
		
		xmlEmbeddedMapping.removeSpecifiedAttributeOverride(0);
		assertEquals(0, embeddedResource.getAttributeOverrides().size());
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		XmlEmbeddedMapping xmlEmbeddedMapping = (XmlEmbeddedMapping) xmlPersistentAttribute.getMapping();
		Embedded embeddedResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);

		xmlEmbeddedMapping.addSpecifiedAttributeOverride(0).setName("FOO");
		xmlEmbeddedMapping.addSpecifiedAttributeOverride(1).setName("BAR");
		xmlEmbeddedMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		assertEquals(3, embeddedResource.getAttributeOverrides().size());
		
		
		xmlEmbeddedMapping.moveSpecifiedAttributeOverride(0, 2);
		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEmbeddedMapping.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(2).getName());


		xmlEmbeddedMapping.moveSpecifiedAttributeOverride(1, 0);
		attributeOverrides = xmlEmbeddedMapping.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		XmlEmbeddedMapping xmlEmbeddedMapping = (XmlEmbeddedMapping) xmlPersistentAttribute.getMapping();
		Embedded embeddedResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverride());
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverride());
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverride());
		
		embeddedResource.getAttributeOverrides().get(0).setName("FOO");
		embeddedResource.getAttributeOverrides().get(1).setName("BAR");
		embeddedResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEmbeddedMapping.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = xmlEmbeddedMapping.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = xmlEmbeddedMapping.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedResource.getAttributeOverrides().remove(1);
		attributeOverrides = xmlEmbeddedMapping.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedResource.getAttributeOverrides().remove(1);
		attributeOverrides = xmlEmbeddedMapping.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedResource.getAttributeOverrides().remove(0);
		assertFalse(xmlEmbeddedMapping.specifiedAttributeOverrides().hasNext());
	}
}