/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmEmbeddable;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddable;


public class Generic2_0OrmEmbeddable extends AbstractOrmEmbeddable
{
	public Generic2_0OrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		super(parent, resourceMapping);
	}
	
	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return attributeMappingKey == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY 
			|| attributeMappingKey == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY
			|| attributeMappingKey == MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY
			|| attributeMappingKey == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY
			|| attributeMappingKey == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY
			|| attributeMappingKey == MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY
			|| attributeMappingKey == MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY
			|| attributeMappingKey == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}
}
