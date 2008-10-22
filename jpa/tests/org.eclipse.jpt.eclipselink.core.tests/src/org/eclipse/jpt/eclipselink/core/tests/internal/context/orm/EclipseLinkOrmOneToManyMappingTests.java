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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;

public class EclipseLinkOrmOneToManyMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmOneToManyMappingTests(String name) {
		super(name);
	}
	
	
	public void testUpdatePrivateOwned() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) ormResource().getEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertFalse(resourceOneToMany.isPrivateOwned());
		assertFalse(contextOneToMany.getPrivateOwned().isPrivateOwned());
		
		// set xml private owned to true, check context
		
		resourceOneToMany.setPrivateOwned(true);
		
		assertTrue(resourceOneToMany.isPrivateOwned());
		assertTrue(contextOneToMany.getPrivateOwned().isPrivateOwned());
		
		// set xml private owned back to false, check context
		
		resourceOneToMany.setPrivateOwned(false);
		
		assertFalse(resourceOneToMany.isPrivateOwned());
		assertFalse(contextOneToMany.getPrivateOwned().isPrivateOwned());
	}
	
	public void testModifyPrivateOwned() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)ormResource().getEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertFalse(resourceOneToMany.isPrivateOwned());
		assertFalse(contextOneToMany.getPrivateOwned().isPrivateOwned());
		
		// set context private owned  to true, check resource
		
		contextOneToMany.getPrivateOwned().setPrivateOwned(true);
		
		assertTrue(resourceOneToMany.isPrivateOwned());
		assertTrue(contextOneToMany.getPrivateOwned().isPrivateOwned());
		
		// set context private owned back to false, check resource
		
		contextOneToMany.getPrivateOwned().setPrivateOwned(false);
		
		assertFalse(resourceOneToMany.isPrivateOwned());
		assertFalse(contextOneToMany.getPrivateOwned().isPrivateOwned());
	}
	
	public void testUpdateJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) ormResource().getEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
		
		// set xml join fetch to INNER, check context
		
		resourceOneToMany.setJoinFetch(XmlJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToMany.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextOneToMany.getJoinFetch().getValue());
		
		// set xml join fetch to OUTER, check context
		
		resourceOneToMany.setJoinFetch(XmlJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToMany.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextOneToMany.getJoinFetch().getValue());
		
		// set xml join fetch to null, check context
		
		resourceOneToMany.setJoinFetch(null);
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
	}
	
	public void testModifyJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)ormResource().getEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
		
		// set context join fetch to INNER, check resource
		
		contextOneToMany.getJoinFetch().setValue(JoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToMany.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextOneToMany.getJoinFetch().getValue());
		
		// set context join fetch to OUTER, check resource
		
		contextOneToMany.getJoinFetch().setValue(JoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToMany.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextOneToMany.getJoinFetch().getValue());
		
		// set context join fetch to null, check resource
		
		contextOneToMany.getJoinFetch().setValue(null);
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
	}
}
