/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.libval;

import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;

/**
 * The <code>org.eclipse.jpt.common.core.libraryValidators</code> extension point
 * corresponding to a {@link JptWorkspace Dali workspace}.
 * <p>
 * See <code>org.eclipse.jpt.common.core/plugin.xml:libraryValidators</code>.
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
public interface LibraryValidatorManager {
	/**
	 * Return the manager's Dali workspace.
	 */
	JptWorkspace getJptWorkspace();


	// ********** library validators **********

	/**
	 * Return the defined library validators.
	 */
	Iterable<LibraryValidator> getLibraryValidators();

	/**
	 * Return the defined library validators for the specified install config.
	 */
	Iterable<LibraryValidator> getLibraryValidators(JptLibraryProviderInstallOperationConfig installConfig);
}
