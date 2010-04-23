/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap;

public class OrmEclipseLinkBasicMapMapping
	extends AbstractOrmEclipseLinkBasicMapMapping
{
	public OrmEclipseLinkBasicMapMapping(OrmPersistentAttribute parent, XmlBasicMap resourceMapping) {
		super(parent, resourceMapping);
	}
}
