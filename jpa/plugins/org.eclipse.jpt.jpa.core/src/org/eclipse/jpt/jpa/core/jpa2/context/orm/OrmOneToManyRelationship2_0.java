/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmMappingJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyRelationship;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyRelationship2_0;

/**
 * JPA 2.0 <code>orm.xml</code> 1:m relationship
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OrmOneToManyRelationship2_0
	extends OneToManyRelationship2_0,
			OrmOneToManyRelationship,
			OrmMappingJoinColumnRelationship
{
	// combine various interfaces
}
