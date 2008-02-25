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

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.platform.JpaFactory;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;


public class OrmMappedSuperclassProvider implements OrmTypeMappingProvider
{	
	public OrmTypeMapping<XmlMappedSuperclass> buildTypeMapping(JpaFactory factory, OrmPersistentType parent) {
		return factory.buildXmlMappedSuperclass(parent);
	}

	public String key() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}
}
