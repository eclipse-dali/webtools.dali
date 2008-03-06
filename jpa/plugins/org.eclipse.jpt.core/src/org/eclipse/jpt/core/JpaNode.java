/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.utility.internal.node.Node;

/**
 * Tweak the node interface with JPA-specific protocol.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaNode extends Node, IAdaptable
{

	/**
	 * Return the JPA project the node belongs to.
	 */
	JpaProject jpaProject();

	/**
	 * Return the resource that most directly contains the node.
	 * This is used by JpaHelper.
	 */
	IResource resource();
	
	
	// ********** covariant overrides **********
	
	JpaNode parent();
}
