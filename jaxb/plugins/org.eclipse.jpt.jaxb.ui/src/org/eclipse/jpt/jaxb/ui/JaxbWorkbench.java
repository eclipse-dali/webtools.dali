/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui;

import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUiManager;
import org.eclipse.ui.IWorkbench;

/**
 * The Dali JAXB state corresponding to an {@link IWorkbench Eclipse workbench}.
 * <p>
 * To retrieve the JAXB workbench corresponding to an Eclipse workbench:
 * <pre>
 *     IWorkbench workbench = PlatformUI.getWorkbench();
 *     // even though IWorkbench extends IAdaptable, it does not delegate to the
 *     // Platform adapter manager; so registered adapter factories are *not* used... :-(
 *     // JaxbWorkbench jaxbWorkbench = (JaxbWorkbench) workbench.getAdapter(JaxbWorkbench.class);
 *     JaxbWorkbench jaxbWorkbench = PlatformTools.getAdapter(workbench, JaxbWorkbench.class);
 * </pre>
 * <p>
 * See <code>org.eclipse.jpt.jaxb.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
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
public interface JaxbWorkbench {
	/**
	 * Return the corresponding Eclipse workbench.
	 */
	IWorkbench getWorkbench();

	/**
	 * Return the workbench's JAXB workspace.
	 */
	JaxbWorkspace getJaxbWorkspace();

	/**
	 * Return the manager for the workspace's JAXB platform UIs.
	 */
	JaxbPlatformUiManager getJaxbPlatformUiManager();
}
