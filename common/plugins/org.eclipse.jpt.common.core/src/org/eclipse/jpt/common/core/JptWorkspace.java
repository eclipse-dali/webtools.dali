/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.libval.LibraryValidatorManager;
import org.eclipse.jpt.common.core.resource.ResourceLocatorManager;

/**
 * The Dali state corresponding to an {@link IWorkspace Eclipse workspace}.
 * <p>
 * To retrieve the Dali workspace corresponding to an Eclipse workspace:
 * <pre>
 * IWorkspace workspace = ResourcesPlugin.getWorkspace();
 * JptWorkspace jptWorkspace = (JptWorkspace) workspace.getAdapter(JptWorkspace.class);
 * </pre>
 * <p>
 * See <code>org.eclipse.jpt.common.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * <p>
 * Not intended to be implemented by clients.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface JptWorkspace {
	/**
	 * Return the corresponding Eclipse workspace.
	 */
	IWorkspace getWorkspace();

	/**
	 * Return the manager for the workspace's Dali resource types.
	 */
	JptResourceTypeManager getResourceTypeManager();

	/**
	 * Return the manager for the workspace's library validators.
	 */
	LibraryValidatorManager getLibraryValidatorManager();

	/**
	 * Return the manager for the workspace's resource locators.
	 */
	ResourceLocatorManager getResourceLocatorManager();
}
