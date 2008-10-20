/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmIdMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlId;

public class EclipseLinkOrmIdMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmIdMappingTests(String name) {
		super(name);
	}
	
	
	public void testUpdateMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		EclipseLinkOrmIdMapping contextId = 
			(EclipseLinkOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)ormResource().getEntityMappings().getEntities().get(0);
		XmlId resourceId = 
			(XmlId) resourceEntity.getAttributes().getIds().get(0);
		
		// check defaults
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceId.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceId.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceId.setMutable(null);
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// TODO - test defaults for java serializable and date/time types, 
		// with and without persistence property
	}
	
	public void testModifyMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		EclipseLinkOrmIdMapping contextId = 
			(EclipseLinkOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)ormResource().getEntityMappings().getEntities().get(0);
		XmlId resourceId = 
			(XmlId) resourceEntity.getAttributes().getIds().get(0);
		
		// check defaults
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set context mutable to true, check resource
		
		contextId.getMutable().setSpecifiedMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// set context mutable to false, check resource
		
		contextId.getMutable().setSpecifiedMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set context read only to null, check resource
		
		contextId.getMutable().setSpecifiedMutable(null);
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
	}
}
