/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;

public interface EclipseLinkEntityMappings 
	extends EntityMappings
{
	
	ConverterHolder getConverterHolder();

	// hmmm - these suppress various warnings in EclipseLinkEntityMappingImpl...
	ListIterator<OrmNamedQuery> namedQueries();
	ListIterator<OrmNamedNativeQuery> namedNativeQueries();

}
