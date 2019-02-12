/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.core.libval.LibraryValidator;

/**
 * The <code>org.eclipse.jpt.common.core.resourceLocators</code> extension point
 * corresponding to a {@link JptWorkspace Dali workspace}.
 * <p>
 * See <code>org.eclipse.jpt.common.core/plugin.xml:resourceLocators</code>.
 * <p>
 * Not intended to be implemented by clients.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see LibraryValidator
 * @version 3.3
 * @since 3.3
 */
public interface ResourceLocatorManager {
	/**
	 * Return the manager's Dali workspace.
	 */
	JptWorkspace getJptWorkspace();


	// ********** resource locators **********

	/**
	 * Return the defined resource locators.
	 */
	Iterable<ResourceLocator> getResourceLocators();

	/**
	 * Return the resource locators for the specified project,
	 * sorted by priority.
	 */
	Iterable<ResourceLocator> getResourceLocators(IProject project);

	/**
	 * Return the resource locator with the highest priority
	 * for the specified project
	 */
	ResourceLocator getResourceLocator(IProject project);
}
