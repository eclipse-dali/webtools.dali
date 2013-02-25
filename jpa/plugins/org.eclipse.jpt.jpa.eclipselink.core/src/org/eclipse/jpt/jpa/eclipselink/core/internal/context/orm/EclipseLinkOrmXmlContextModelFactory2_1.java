/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap;

public class EclipseLinkOrmXmlContextModelFactory2_1
	extends EclipseLinkOrmXmlContextModelFactory2_0
{

	@Override
	public AbstractOrmEclipseLinkBasicCollectionMapping buildOrmEclipseLinkBasicCollectionMapping(OrmPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return new OrmEclipseLinkBasicCollectionMapping2_1(parent, resourceMapping);
	}

	@Override
	public AbstractOrmEclipseLinkBasicMapMapping buildOrmEclipseLinkBasicMapMapping(OrmPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return new OrmEclipseLinkBasicMapMapping2_1(parent, resourceMapping);
	}
}
