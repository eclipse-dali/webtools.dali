/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.common.utility.model.Model;

/**
 * JAXB-specific protocol. All JAXB objects belong to a JAXB project, are
 * associated with a resource, and have a parent (excepting the JAXB project).
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
	extends Model, IAdaptable
{
	/**
	 * Return the JAXB project the node belongs to.
	 */
	JaxbProject getJaxbProject();

	/**
	 * Return the JAXB node's parent. The JAXB project will not have a parent.
	 */
	JaxbNode getParent();

	void stateChanged();

	/**
	 * Return the node's resource, typically for validation messages.
	 */
	IResource getResource();
}
