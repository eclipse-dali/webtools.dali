/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.internal.context.orm.SpecifiedOrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;

/**
 * <code>orm.xml</code> persistent type:<ul>
 * <li>mapping
 * <li>access
 * <li>attributes
 * <li>super persistent type
 * <li>Java persistent type
 * </ul>
 */
public class GenericOrmPersistentType
	extends SpecifiedOrmPersistentType
{

	public GenericOrmPersistentType(EntityMappings parent, XmlTypeMapping xmlTypeMapping) {
		super(parent, xmlTypeMapping);
	}
}
