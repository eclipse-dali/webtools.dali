/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;


public class GenericOrmEmbeddable extends AbstractOrmEmbeddable implements OrmEmbeddable
{
	public GenericOrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		super(parent, resourceMapping);
	}

	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		//generic only allows basic and transient within an Embeddable
		return attributeMappingKey == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY || attributeMappingKey == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

}
