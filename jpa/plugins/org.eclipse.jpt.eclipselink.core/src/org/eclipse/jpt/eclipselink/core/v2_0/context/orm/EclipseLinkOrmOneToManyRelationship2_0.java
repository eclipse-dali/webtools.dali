/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.v2_0.context.orm;

import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToManyRelationship2_0;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkOrmOneToManyRelationship;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkOneToManyRelationship2_0;

/**
 * EclipseLink 2.0 <code>orm.xml</code> 1:m relationship
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface EclipseLinkOrmOneToManyRelationship2_0
	extends EclipseLinkOneToManyRelationship2_0,
			EclipseLinkOrmOneToManyRelationship,
			OrmOneToManyRelationship2_0
{
	// combine various interfaces
}
