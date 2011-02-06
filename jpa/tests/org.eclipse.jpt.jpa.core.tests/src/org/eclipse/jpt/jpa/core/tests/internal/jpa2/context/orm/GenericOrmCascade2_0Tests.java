/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmCascade2_0Tests extends Generic2_0ContextModelTestCase
{
	public GenericOrmCascade2_0Tests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	public void testUpdateCascadeDetach() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		Cascade2_0 cascade = (Cascade2_0) ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isDetach());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeType());
		assertEquals(false, cascade.isDetach());
		assertNotNull(oneToOne.getCascade());
		
		//set detach in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeDetach(true);
		assertEquals(true, cascade.isDetach());
		assertEquals(true, oneToOne.getCascade().isCascadeDetach());
		
		//set detach to false in the resource model
		oneToOne.getCascade().setCascadeDetach(false);
		assertEquals(false, cascade.isDetach());
		assertEquals(false, oneToOne.getCascade().isCascadeDetach());
	}
	
	public void testModifyCascadeDetach() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		Cascade2_0 cascade = (Cascade2_0) ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isDetach());
		assertNull(oneToOne.getCascade());
		
		//set detach in the context model, verify resource model updated
		cascade.setDetach(true);
		assertEquals(true, cascade.isDetach());
		assertEquals(true, oneToOne.getCascade().isCascadeDetach());
		
		//set detach to false in the context model
		cascade.setDetach(false);
		assertEquals(false, cascade.isDetach());
		assertNull(oneToOne.getCascade());
	}
}
