/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicMap;

public class EclipseLinkOrmXmlContextModelFactory2_1
	extends EclipseLinkOrmXmlContextModelFactory2_0
{
	@Override
	public EclipseLinkAbstractOrmBasicCollectionMapping buildOrmEclipseLinkBasicCollectionMapping(OrmSpecifiedPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return new EclipseLinkOrmBasicCollectionMapping2_1(parent, resourceMapping);
	}

	@Override
	public EclipseLinkAbstractOrmBasicMapMapping buildOrmEclipseLinkBasicMapMapping(OrmSpecifiedPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return new EclipseLinkOrmBasicMapMapping2_1(parent, resourceMapping);
	}
}
