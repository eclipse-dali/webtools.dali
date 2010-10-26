/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.core.IResourcePart;
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
 * @version 3.0
 * @since 3.0
 */
public interface JaxbNode
	extends Model, IAdaptable, IResourcePart
{
	/**
	 * Return the JAXB project the node belongs to.
	 */
	JaxbProject getJaxbProject();

	/**
	 * Return the JAXB node's parent. The JAXB project will not have a parent.
	 */
	JaxbNode getParent();
}
