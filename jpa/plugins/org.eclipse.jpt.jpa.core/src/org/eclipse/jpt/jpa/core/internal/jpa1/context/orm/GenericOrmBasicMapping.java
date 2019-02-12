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
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmBasicMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlBasic;

/**
 * <code>orm.xml</code> basic mapping
 */
public class GenericOrmBasicMapping
	extends AbstractOrmBasicMapping<XmlBasic>
{
	public GenericOrmBasicMapping(OrmSpecifiedPersistentAttribute parent, XmlBasic xmlMapping) {
		super(parent, xmlMapping);
	}
}
