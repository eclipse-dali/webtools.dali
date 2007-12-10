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
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;


public class XmlMappedSuperclassProvider implements IXmlTypeMappingProvider
{	
	public XmlTypeMapping<MappedSuperclass> buildTypeMapping(IJpaBaseContextFactory factory, XmlPersistentType parent) {
		return factory.createXmlMappedSuperclass(parent);
	}

	public String key() {
		return IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}
	
	public void createAndAddOrmResourceMapping(XmlPersistentType xmlPersistentType, EntityMappings entityMappings, String className) {
		MappedSuperclass mappedSuperclass = OrmFactory.eINSTANCE.createMappedSuperclass();
		xmlPersistentType.initialize(mappedSuperclass);
		entityMappings.getMappedSuperclasses().add(mappedSuperclass);
		mappedSuperclass.setClassName(className);
	}
}
