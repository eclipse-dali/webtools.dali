/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;


public class GenericOrmElementCollectionMapping2_0
	extends AbstractOrmElementCollectionMapping2_0<XmlElementCollection>
{

	public GenericOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		super(parent, resourceMapping);
	}

}
