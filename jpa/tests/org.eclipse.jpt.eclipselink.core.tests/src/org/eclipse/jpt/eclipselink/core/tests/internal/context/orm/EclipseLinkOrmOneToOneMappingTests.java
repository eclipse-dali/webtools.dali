/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne;

@SuppressWarnings("nls")
public class EclipseLinkOrmOneToOneMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmOneToOneMappingTests(String name) {
		super(name);
	}
	
	
	public void testUpdatePrivateOwned() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		EclipseLinkOneToOneMapping contextOneToOne = 
			(EclipseLinkOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlOneToOne resourceOneToOne = 
			(XmlOneToOne) resourceEntity.getAttributes().getOneToOnes().get(0);
		
		// check defaults
		
		assertFalse(resourceOneToOne.isPrivateOwned());
		assertFalse(contextOneToOne.getPrivateOwned().isPrivateOwned());
		
		// set xml private owned to true, check context
		
		resourceOneToOne.setPrivateOwned(true);
		
		assertTrue(resourceOneToOne.isPrivateOwned());
		assertTrue(contextOneToOne.getPrivateOwned().isPrivateOwned());
		
		// set xml private owned back to false, check context
		
		resourceOneToOne.setPrivateOwned(false);
		
		assertFalse(resourceOneToOne.isPrivateOwned());
		assertFalse(contextOneToOne.getPrivateOwned().isPrivateOwned());
	}
	
	public void testModifyPrivateOwned() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		EclipseLinkOneToOneMapping contextOneToOne = 
			(EclipseLinkOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlOneToOne resourceOneToOne = 
			(XmlOneToOne) resourceEntity.getAttributes().getOneToOnes().get(0);
		
		// check defaults
		
		assertFalse(resourceOneToOne.isPrivateOwned());
		assertFalse(contextOneToOne.getPrivateOwned().isPrivateOwned());
		
		// set context private owned  to true, check resource
		
		contextOneToOne.getPrivateOwned().setPrivateOwned(true);
		
		assertTrue(resourceOneToOne.isPrivateOwned());
		assertTrue(contextOneToOne.getPrivateOwned().isPrivateOwned());
		
		// set context private owned back to false, check resource
		
		contextOneToOne.getPrivateOwned().setPrivateOwned(false);
	}
	
	public void testUpdateJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		EclipseLinkOneToOneMapping contextOneToOne = 
			(EclipseLinkOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) getXmlEntityMappings().getEntities().get(0);
		XmlOneToOne resourceOneToOne = 
			(XmlOneToOne) resourceEntity.getAttributes().getOneToOnes().get(0);
		
		// check defaults
		
		assertNull(resourceOneToOne.getJoinFetch());
		assertNull(contextOneToOne.getJoinFetch().getValue());
		
		// set xml join fetch to INNER, check context
		
		resourceOneToOne.setJoinFetch(XmlJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToOne.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextOneToOne.getJoinFetch().getValue());
		
		// set xml join fetch to OUTER, check context
		
		resourceOneToOne.setJoinFetch(XmlJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToOne.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextOneToOne.getJoinFetch().getValue());
		
		// set xml join fetch to null, check context
		
		resourceOneToOne.setJoinFetch(null);
		
		assertNull(resourceOneToOne.getJoinFetch());
		assertNull(contextOneToOne.getJoinFetch().getValue());
	}
	
	public void testModifyJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		EclipseLinkOneToOneMapping contextOneToOne = 
			(EclipseLinkOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlOneToOne resourceOneToOne = 
			(XmlOneToOne) resourceEntity.getAttributes().getOneToOnes().get(0);
		
		// check defaults
		
		assertNull(resourceOneToOne.getJoinFetch());
		assertNull(contextOneToOne.getJoinFetch().getValue());
		
		// set context join fetch to INNER, check resource
		
		contextOneToOne.getJoinFetch().setValue(JoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToOne.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextOneToOne.getJoinFetch().getValue());
		
		// set context join fetch to OUTER, check resource
		
		contextOneToOne.getJoinFetch().setValue(JoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToOne.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextOneToOne.getJoinFetch().getValue());
		
		// set context join fetch to null, check resource
		
		contextOneToOne.getJoinFetch().setValue(null);
		
		assertNull(resourceOneToOne.getJoinFetch());
		assertNull(contextOneToOne.getJoinFetch().getValue());
	}
}