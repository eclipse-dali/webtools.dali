/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0, which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection;

public class GenericOrmElementCollectionMapping2_0
	extends AbstractOrmElementCollectionMapping2_0<XmlElementCollection>
{
	public GenericOrmElementCollectionMapping2_0(OrmSpecifiedPersistentAttribute parent, XmlElementCollection resourceMapping) {
		super(parent, resourceMapping);
	}
}
