/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.platform.base.IJpaBaseContextFactory;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;


public class XmlEntityProvider implements IXmlTypeMappingProvider
{	
	public XmlTypeMapping<Entity> buildTypeMapping(IJpaBaseContextFactory factory, XmlPersistentType parent) {
		return factory.createXmlEntity(parent);
	}

	public String key() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
	
	public void createAndAddOrmResourceMapping(EntityMappings entityMappings, String className) {
		Entity entity = OrmFactory.eINSTANCE.createEntity();
		entityMappings.getEntities().add(entity);
		entity.setClassName(className);
	}
}
