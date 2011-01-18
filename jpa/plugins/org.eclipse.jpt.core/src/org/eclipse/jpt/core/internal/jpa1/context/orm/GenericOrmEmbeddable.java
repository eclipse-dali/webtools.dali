/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmEmbeddable;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.utility.internal.ArrayTools;

/**
 * <code>orm.xml</code> embeddable type mapping
 */
public class GenericOrmEmbeddable
	extends AbstractOrmEmbeddable<XmlEmbeddable>
{
	public GenericOrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		super(parent, resourceMapping);
	}

	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return ArrayTools.contains(GenericJavaEmbeddable.ALLOWED_ATTRIBUTE_MAPPING_KEYS, attributeMappingKey);
	}
}
