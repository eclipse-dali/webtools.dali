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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;

public class EclipseLinkOrmManyToManyMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmManyToManyMappingTests(String name) {
		super(name);
	}
	
	
	public void testUpdateJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		EclipseLinkRelationshipMapping contextManyToMany = 
			(EclipseLinkRelationshipMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) ormResource().getEntityMappings().getEntities().get(0);
		XmlManyToMany resourceManyToMany = 
			(XmlManyToMany) resourceEntity.getAttributes().getManyToManys().get(0);
		
		// check defaults
		
		assertNull(resourceManyToMany.getJoinFetch());
		assertNull(contextManyToMany.getJoinFetch().getValue());
		
		// set xml join fetch to INNER, check context
		
		resourceManyToMany.setJoinFetch(XmlJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceManyToMany.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextManyToMany.getJoinFetch().getValue());
		
		// set xml join fetch to OUTER, check context
		
		resourceManyToMany.setJoinFetch(XmlJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceManyToMany.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextManyToMany.getJoinFetch().getValue());
		
		// set xml join fetch to null, check context
		
		resourceManyToMany.setJoinFetch(null);
		
		assertNull(resourceManyToMany.getJoinFetch());
		assertNull(contextManyToMany.getJoinFetch().getValue());
	}
	
	public void testModifyJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		EclipseLinkRelationshipMapping contextManyToMany = 
			(EclipseLinkRelationshipMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)ormResource().getEntityMappings().getEntities().get(0);
		XmlManyToMany resourceManyToMany = 
			(XmlManyToMany) resourceEntity.getAttributes().getManyToManys().get(0);
		
		// check defaults
		
		assertNull(resourceManyToMany.getJoinFetch());
		assertNull(contextManyToMany.getJoinFetch().getValue());
		
		// set context join fetch to INNER, check resource
		
		contextManyToMany.getJoinFetch().setValue(JoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceManyToMany.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextManyToMany.getJoinFetch().getValue());
		
		// set context join fetch to OUTER, check resource
		
		contextManyToMany.getJoinFetch().setValue(JoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceManyToMany.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextManyToMany.getJoinFetch().getValue());
		
		// set context join fetch to null, check resource
		
		contextManyToMany.getJoinFetch().setValue(null);
		
		assertNull(resourceManyToMany.getJoinFetch());
		assertNull(contextManyToMany.getJoinFetch().getValue());
	}
}
