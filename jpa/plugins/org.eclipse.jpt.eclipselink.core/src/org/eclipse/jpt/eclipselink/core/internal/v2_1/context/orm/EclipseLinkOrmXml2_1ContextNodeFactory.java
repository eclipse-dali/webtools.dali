/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.AbstractOrmEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.AbstractOrmEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.orm.EclipseLinkOrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlElementCollection;

public class EclipseLinkOrmXml2_1ContextNodeFactory
	extends EclipseLinkOrmXml2_0ContextNodeFactory
{
	@Override
	public OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlElementCollection resourceMapping) {
		return new EclipseLinkOrmElementCollectionMapping2_1(parent, (XmlElementCollection) resourceMapping);
	}

	@Override
	public AbstractOrmEclipseLinkBasicCollectionMapping buildOrmEclipseLinkBasicCollectionMapping(OrmPersistentAttribute parent, XmlBasicCollection resourceMapping) {
		return new OrmEclipseLinkBasicCollectionMapping2_1(parent, resourceMapping);
	}

	@Override
	public AbstractOrmEclipseLinkBasicMapMapping buildOrmEclipseLinkBasicMapMapping(OrmPersistentAttribute parent, XmlBasicMap resourceMapping) {
		return new OrmEclipseLinkBasicMapMapping2_1(parent, resourceMapping);
	}
}
