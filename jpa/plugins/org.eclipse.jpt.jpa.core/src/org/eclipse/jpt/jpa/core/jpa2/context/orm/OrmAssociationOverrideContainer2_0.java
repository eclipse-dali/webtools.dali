/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.jpa2.context.AssociationOverrideContainer2_0;

/**
 * JPA 2.0
 * <code>orm.xml</code> association override container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmAssociationOverrideContainer2_0
	extends OrmAssociationOverrideContainer, AssociationOverrideContainer2_0
{
	// combine interfaces


	// ********** parent adapter **********

	interface ParentAdapter
		extends OrmAssociationOverrideContainer.ParentAdapter, AssociationOverrideContainer2_0.ParentAdapter
	{		
		// combine interfaces
	}
}
