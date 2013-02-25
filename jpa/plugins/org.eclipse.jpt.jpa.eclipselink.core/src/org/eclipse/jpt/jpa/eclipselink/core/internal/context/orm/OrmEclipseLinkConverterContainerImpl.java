/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterContainer;

public final class OrmEclipseLinkConverterContainerImpl
	extends AbstractOrmEclipseLinkConverterContainerImpl
{

	public OrmEclipseLinkConverterContainerImpl(JpaContextModel parent, Owner owner,  XmlConverterContainer xmlConverterContainer) {
		super(parent, owner, xmlConverterContainer);
	}
}
