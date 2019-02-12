/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.jpt.jpa.core.JpaPlatform;

/**
 * The <code>org.eclipse.jpt.jpa.ui.jpaPlatformUis</code> extension point
 * corresponding to a {@link JpaWorkbench Dali JPA workbench}.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:jpaPlatformUis</code>.
 * <p>
 * Not intended to be implemented by clients.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see JpaPlatformUi
 * @version 3.3
 * @since 3.3
 */
public interface JpaPlatformUiManager {
	/**
	 * Return the manager's Dali JPA workbench.
	 */
	JpaWorkbench getJpaWorkbench();


	// ********** JPA platform UIs **********

	/**
	 * Return the UI for the specified JPA platform.
	 */
	JpaPlatformUi getJpaPlatformUi(JpaPlatform jpaPlatform);
}
