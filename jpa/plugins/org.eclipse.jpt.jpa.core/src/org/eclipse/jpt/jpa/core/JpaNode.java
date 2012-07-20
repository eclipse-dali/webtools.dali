/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jpt.common.utility.model.Model;

/**
 * JPA-specific protocol. All JPA objects belong to a JPA project, are
 * associated with a resource, and have a parent (excepting the JPA project).
 * <p>
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
	extends Model, IAdaptable
{
	/**
	 * Return the JPA project manager.
	 */
	JpaProject.Manager getJpaProjectManager();

	/**
	 * Return the JPA project the node belongs to.
	 */
	JpaProject getJpaProject();

	/**
	 * Return the JPA node's parent. The JPA project will not have a parent.
	 */
	JpaNode getParent();

	/**
	 * Some state or child (or grandchild etc.) of the JPA node changed.
	 * Fire a state change event. Implied by this behavior is that any change
	 * to any JPA node in a JPA project will trigger the JPA project to fire a
	 * state change event.
	 * 
	 * @see Model#addStateChangeListener(org.eclipse.jpt.common.utility.model.listener.StateChangeListener)
	 * @see org.eclipse.jpt.common.utility.model.event.StateChangeEvent
	 * @see org.eclipse.jpt.common.utility.model.listener.StateChangeListener
	 */
	void stateChanged();

	/**
	 * Return the node's resource, typically for validation messages.
	 */
	IResource getResource();

	/**
	 * Return the model's JPA platform.
	 */
	JpaPlatform getJpaPlatform();
}
