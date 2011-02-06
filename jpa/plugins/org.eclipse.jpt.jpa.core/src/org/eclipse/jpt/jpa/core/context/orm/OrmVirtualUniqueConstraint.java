/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualUniqueConstraint;

/**
 * <code>orm.xml</code> virtual database unique constraint
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmVirtualUniqueConstraint
	extends VirtualUniqueConstraint, OrmReadOnlyUniqueConstraint
{
	/**
	 * The overridden unique constraint can be either a Java unique constraint
	 * or an <code>orm.xml</code> unique constraint; so we don't change the
	 * return type here.
	 */
	UniqueConstraint getOverriddenUniqueConstraint();
}
