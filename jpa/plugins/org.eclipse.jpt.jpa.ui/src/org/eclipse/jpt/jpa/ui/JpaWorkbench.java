/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;

/**
 * The Dali JPA state corresponding to an {@link IWorkbench Eclipse workbench}.
 * <p>
 * To retrieve the JPA workbench corresponding to an Eclipse workbench:
 * <pre>
 *     IWorkbench workbench = PlatformUI.getWorkbench();
 *     // even though IWorkbench extends IAdaptable, it does not delegate to the
 *     // Platform adapter manager; so registered adapter factories are *not* used... :-(
 *     // JpaWorkbench jpaWorkbench = (JpaWorkbench) workbench.getAdapter(JpaWorkbench.class);
 *     JpaWorkbench jpaWorkbench = PlatformTools.getAdapter(workbench, JpaWorkbench.class);
 * </pre>
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml:org.eclipse.core.runtime.adapters</code>.
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
public interface JpaWorkbench {
	/**
	 * Return the corresponding Eclipse workbench.
	 */
	IWorkbench getWorkbench();

	/**
	 * Return the workbench's JPA workspace.
	 */
	JpaWorkspace getJpaWorkspace();

	/**
	 * Return the manager for the workbench's JPA platform UIs.
	 */
	JpaPlatformUiManager getJpaPlatformUiManager();


	// ********** resources **********

	/**
	 * Return a new "local" resource manager. The resource manager's parent
	 * will be the JPA workbench's resource manager, allowing any resources
	 * to be shared across the JPA workbench. The client must dispose the
	 * resource manager as appropriate.
	 * 
	 * @see #getResourceManager(Control)
	 * @see org.eclipse.jpt.common.ui.internal.JptUIPlugin#buildImageDescriptor(String)
	 */
	ResourceManager buildLocalResourceManager();

	/**
	 * Return the resource manager associated with the specified control. The
	 * control's resource manager's parent will be the JPA workbench's resource
	 * manager, allowing any resources to be shared across the JPA workbench.
	 * The returned resource manager will dispose itself when the specified
	 * control is disposed, freeing the client from the obligation of disposing
	 * the manager.
	 * 
	 * @see #buildLocalResourceManager()
	 * @see org.eclipse.jface.resource.LocalResourceManager#LocalResourceManager(ResourceManager, Control)
	 * @see org.eclipse.jpt.common.ui.internal.JptUIPlugin#buildImageDescriptor(String)
	 * @see Control#getData(String)
	 */
	ResourceManager getResourceManager(Control control);
}
