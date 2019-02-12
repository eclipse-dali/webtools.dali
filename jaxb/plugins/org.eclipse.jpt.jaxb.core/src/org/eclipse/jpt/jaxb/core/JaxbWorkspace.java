/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;

/**
 * The Dali JAXB state corresponding to an {@link IWorkspace Eclipse workspace}.
 * <p>
 * To retrieve the JAXB workspace corresponding to an Eclipse workspace:
 * <pre>
 * IWorkspace workspace = ResourcesPlugin.getWorkspace();
 * JaxbWorkspace jaxbWorkspace = (JaxbWorkspace) workspace.getAdapter(JaxbWorkspace.class);
 * </pre>
 * <p>
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * <p>
 * Not intended to be implemented by clients.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface JaxbWorkspace {
	/**
	 * Return the corresponding Eclipse workspace.
	 */
	IWorkspace getWorkspace();

	/**
	 * Return the workspace's Dali workspace.
	 */
	JptWorkspace getJptWorkspace();

	/**
	 * Return the manager for the workspace's JAXB platforms.
	 */
	JaxbPlatformManager getJaxbPlatformManager();

	/**
	 * Return the manager for all the workspace's JAXB projects.
	 */
	JaxbProjectManager getJaxbProjectManager();
}
