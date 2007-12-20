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
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlTransientMapping;
import org.eclipse.jpt.core.internal.resource.orm.Transient;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlTransientMappingTests extends ContextModelTestCase
{
	public XmlTransientMappingTests(String name) {
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
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientMapping");
		XmlTransientMapping xmlTransientnMapping = (XmlTransientMapping) xmlPersistentAttribute.getMapping();
		Transient transientResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getTransients().get(0);
		
		assertEquals("transientMapping", xmlTransientnMapping.getName());
		assertEquals("transientMapping", transientResource.getName());
				
		//set name in the resource model, verify context model updated
		transientResource.setName("newName");
		assertEquals("newName", xmlTransientnMapping.getName());
		assertEquals("newName", transientResource.getName());
	
		//set name to null in the resource model
		transientResource.setName(null);
		assertNull(xmlTransientnMapping.getName());
		assertNull(transientResource.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientMapping");
		XmlTransientMapping xmlTransientnMapping = (XmlTransientMapping) xmlPersistentAttribute.getMapping();
		Transient transientResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getTransients().get(0);
		
		assertEquals("transientMapping", xmlTransientnMapping.getName());
		assertEquals("transientMapping", transientResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlTransientnMapping.setName("newName");
		assertEquals("newName", xmlTransientnMapping.getName());
		assertEquals("newName", transientResource.getName());
	
		//set name to null in the context model
		xmlTransientnMapping.setName(null);
		assertNull(xmlTransientnMapping.getName());
		assertNull(transientResource.getName());
	}
}