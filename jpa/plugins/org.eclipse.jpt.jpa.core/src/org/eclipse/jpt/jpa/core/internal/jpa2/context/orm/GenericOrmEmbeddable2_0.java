/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmEmbeddable;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaEmbeddable2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable;

/**
 * JPA 2.0
 * <code>orm.xml</code> embeddable type mapping
 */
public class GenericOrmEmbeddable2_0
	extends AbstractOrmEmbeddable<XmlEmbeddable>
{
	public GenericOrmEmbeddable2_0(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		super(parent, resourceMapping);
	}

	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return ArrayTools.contains(GenericJavaEmbeddable2_0.ALLOWED_ATTRIBUTE_MAPPING_KEYS, attributeMappingKey);
	}
}
