/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0 which accompanies this distribution, and is
 * available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmEmbeddable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEmbeddable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable;

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
