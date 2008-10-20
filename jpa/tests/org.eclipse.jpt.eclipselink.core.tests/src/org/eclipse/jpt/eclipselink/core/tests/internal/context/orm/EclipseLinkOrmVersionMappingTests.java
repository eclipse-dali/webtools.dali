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
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmVersionMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion;

public class EclipseLinkOrmVersionMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmVersionMappingTests(String name) {
		super(name);
	}
	
	
	public void testUpdateMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		EclipseLinkOrmVersionMapping contextVersion = 
			(EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)ormResource().getEntityMappings().getEntities().get(0);
		XmlVersion resourceVersion = 
			(XmlVersion) resourceEntity.getAttributes().getVersions().get(0);
		
		// check defaults
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceVersion.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceVersion.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceVersion.setMutable(null);
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// TODO - test defaults for java serializable and date/time types, 
		// with and without persistence property
	}
	
	public void testModifyMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		EclipseLinkOrmVersionMapping contextVersion = 
			(EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)ormResource().getEntityMappings().getEntities().get(0);
		XmlVersion resourceVersion = 
			(XmlVersion) resourceEntity.getAttributes().getVersions().get(0);
		
		// check defaults
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set context mutable to true, check resource
		
		contextVersion.getMutable().setSpecifiedMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// set context mutable to false, check resource
		
		contextVersion.getMutable().setSpecifiedMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set context read only to null, check resource
		
		contextVersion.getMutable().setSpecifiedMutable(null);
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
	}
}
