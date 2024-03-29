/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedOverrideRelationship;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedOverrideRelationship2_0;

/**
 * JPA 2.0 <code>orm.xml</code> association override relationship
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.3
 */
public interface OrmSpecifiedOverrideRelationship2_0 
	extends SpecifiedOverrideRelationship2_0,
			OrmSpecifiedOverrideRelationship,
			OrmJoinTableRelationship
{
	OrmSpecifiedJoinTableRelationshipStrategy getJoinTableStrategy();
}
