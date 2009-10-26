/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToManyMapping2_0;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;

/**
 *  EclipseLink2_0OrmOneToManyMappingTests
 */
public class EclipseLink2_0OrmOneToManyMappingTests
	extends EclipseLink2_0OrmContextModelTestCase
{
	public EclipseLink2_0OrmOneToManyMappingTests(String name) {
		super(name);
	}
	
	public void testUpdateSpecifiedOrphanRemoval() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping2_0 ormOneToManyMapping = (OrmOneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals(OrmOneToManyMapping2_0.DEFAULT_ORPHAN_REMOVAL, ormOneToManyMapping.getSpecifiedOrphanRemoval().booleanValue());
		assertEquals(OrmOneToManyMapping2_0.DEFAULT_ORPHAN_REMOVAL, oneToManyResource.isOrphanRemoval());
				
		//set enumerated in the resource model, verify context model updated
		oneToManyResource.setOrphanRemoval(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormOneToManyMapping.getSpecifiedOrphanRemoval());
		assertEquals(true, oneToManyResource.isOrphanRemoval());
	
		oneToManyResource.setOrphanRemoval(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormOneToManyMapping.getSpecifiedOrphanRemoval());
		assertEquals(false, oneToManyResource.isOrphanRemoval());
	}
	
	public void testModifySpecifiedOrphanRemoval() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping2_0 ormOneToManyMapping = (OrmOneToManyMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals(OrmOneToManyMapping2_0.DEFAULT_ORPHAN_REMOVAL, ormOneToManyMapping.getSpecifiedOrphanRemoval().booleanValue());
		assertEquals(OrmOneToManyMapping2_0.DEFAULT_ORPHAN_REMOVAL, oneToManyResource.isOrphanRemoval());
				
		//set enumerated in the context model, verify resource model updated
		ormOneToManyMapping.setSpecifiedOrphanRemoval(Boolean.TRUE);
		assertEquals(true, oneToManyResource.isOrphanRemoval());
		assertEquals(Boolean.TRUE, ormOneToManyMapping.getSpecifiedOrphanRemoval());
	
		ormOneToManyMapping.setSpecifiedOrphanRemoval(Boolean.FALSE);
		assertEquals(false, oneToManyResource.isOrphanRemoval());
		assertEquals(Boolean.FALSE, ormOneToManyMapping.getSpecifiedOrphanRemoval());
	}
}
