/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.orm;

import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEntity;

public interface OrmEclipseLinkEntity extends EclipseLinkEntity, OrmEntity
{
	EclipseLinkConverterHolder getConverterHolder();
	
	OrmEclipseLinkCaching getCaching();
}
