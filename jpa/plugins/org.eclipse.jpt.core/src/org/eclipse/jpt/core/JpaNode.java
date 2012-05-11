/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.utility.model.Model;

/**
 * JPA-specific protocol. All JPA objects belong to a JPA project, are
 * associated with a resource, and have a parent (excepting the JPA project).
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.0
 */
public interface JpaNode
	extends Model, IAdaptable, IResourcePart
{
	/**
	 * Return the JPA project the node belongs to.
	 */
	JpaProject getJpaProject();

	/**
	 * Return the JPA node's parent. The JPA project will not have a parent.
	 */
	JpaNode getParent();
}
