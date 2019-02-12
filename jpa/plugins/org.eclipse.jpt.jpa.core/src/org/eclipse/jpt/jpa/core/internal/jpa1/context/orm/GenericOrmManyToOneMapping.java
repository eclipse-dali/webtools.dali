/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToOne;

public class GenericOrmManyToOneMapping 
	extends AbstractOrmManyToOneMapping<XmlManyToOne>
{
	public GenericOrmManyToOneMapping(OrmSpecifiedPersistentAttribute parent, XmlManyToOne xmlMapping) {
		super(parent, xmlMapping);
	}
}
