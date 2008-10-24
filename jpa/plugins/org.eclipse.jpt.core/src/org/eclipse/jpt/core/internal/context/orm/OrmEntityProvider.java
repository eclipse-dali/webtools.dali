/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;


public class OrmEntityProvider implements OrmTypeMappingProvider
{	
	public OrmEntity buildMapping(OrmPersistentType parent, JpaFactory factory) {
		return factory.buildOrmEntity(parent);
	}

	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
}
