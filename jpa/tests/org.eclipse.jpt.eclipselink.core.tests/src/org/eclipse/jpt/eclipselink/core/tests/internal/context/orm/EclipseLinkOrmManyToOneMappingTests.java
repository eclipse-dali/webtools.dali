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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne;

@SuppressWarnings("nls")
public class EclipseLinkOrmManyToOneMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmManyToOneMappingTests(String name) {
		super(name);
	}
	
	
	public void testUpdateJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOne");
		EclipseLinkRelationshipMapping contextManyToOne = 
			(EclipseLinkRelationshipMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) getXmlEntityMappings().getEntities().get(0);
		XmlManyToOne resourceManyToOne = 
			(XmlManyToOne) resourceEntity.getAttributes().getManyToOnes().get(0);
		
		// check defaults
		
		assertNull(resourceManyToOne.getJoinFetch());
		assertNull(contextManyToOne.getJoinFetch().getValue());
		
		// set xml join fetch to INNER, check context
		
		resourceManyToOne.setJoinFetch(XmlJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceManyToOne.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextManyToOne.getJoinFetch().getValue());
		
		// set xml join fetch to OUTER, check context
		
		resourceManyToOne.setJoinFetch(XmlJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceManyToOne.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextManyToOne.getJoinFetch().getValue());
		
		// set xml join fetch to null, check context
		
		resourceManyToOne.setJoinFetch(null);
		
		assertNull(resourceManyToOne.getJoinFetch());
		assertNull(contextManyToOne.getJoinFetch().getValue());
	}
	
	public void testModifyJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOne");
		EclipseLinkRelationshipMapping contextManyToOne = 
			(EclipseLinkRelationshipMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlManyToOne resourceManyToOne = 
			(XmlManyToOne) resourceEntity.getAttributes().getManyToOnes().get(0);
		
		// check defaults
		
		assertNull(resourceManyToOne.getJoinFetch());
		assertNull(contextManyToOne.getJoinFetch().getValue());
		
		// set context join fetch to INNER, check resource
		
		contextManyToOne.getJoinFetch().setValue(JoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceManyToOne.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextManyToOne.getJoinFetch().getValue());
		
		// set context join fetch to OUTER, check resource
		
		contextManyToOne.getJoinFetch().setValue(JoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceManyToOne.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextManyToOne.getJoinFetch().getValue());
		
		// set context join fetch to null, check resource
		
		contextManyToOne.getJoinFetch().setValue(null);
		
		assertNull(resourceManyToOne.getJoinFetch());
		assertNull(contextManyToOne.getJoinFetch().getValue());
	}
}
