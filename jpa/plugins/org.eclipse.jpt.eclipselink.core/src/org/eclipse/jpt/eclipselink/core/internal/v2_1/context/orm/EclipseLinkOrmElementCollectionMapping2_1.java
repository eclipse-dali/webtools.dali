/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa2.context.orm.AbstractOrmElementCollectionMapping2_0;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkElementCollectionMapping2_0;


public class EclipseLinkOrmElementCollectionMapping2_1
	extends AbstractOrmElementCollectionMapping2_0<XmlElementCollection>
	implements EclipseLinkElementCollectionMapping2_0
{
	protected OrmEclipseLinkJoinFetch joinFetch;

	public EclipseLinkOrmElementCollectionMapping2_1(OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		super(parent, resourceMapping);
		this.joinFetch = new OrmEclipseLinkJoinFetch(this, this.resourceAttributeMapping);
	}
	
	@Override
	public void update() {
		super.update();
		this.joinFetch.update();
	}
	
	public EclipseLinkJoinFetch getJoinFetch() {
		return this.joinFetch;
	}
}
