/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;

/**
 * JPA 2.0 <code>orm.xml</code> single (1:1, m:1) mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.3
 */
public interface OrmSingleRelationshipMapping2_0
	extends SingleRelationshipMapping2_0, OrmSingleRelationshipMapping
{
	OrmDerivedIdentity2_0 getDerivedIdentity();

	OrmCascade2_0 getCascade();
}
