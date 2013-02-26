/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbedded;

/**
 * <code>orm.xml</code> embedded mapping
 */
public class GenericOrmEmbeddedMapping
	extends AbstractOrmEmbeddedMapping<XmlEmbedded>
{
	public GenericOrmEmbeddedMapping(OrmModifiablePersistentAttribute parent, XmlEmbedded xmlMapping) {
		super(parent, xmlMapping);
	}
}
