/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany;

public class GenericOrmOneToManyMapping
	extends AbstractOrmOneToManyMapping<XmlOneToMany>
{
	public GenericOrmOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany xmlMapping) {
		super(parent, xmlMapping);
	}
}
