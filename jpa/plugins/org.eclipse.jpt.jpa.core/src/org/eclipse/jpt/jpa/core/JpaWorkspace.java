/*******************************************************************************
 * Copyright (c) 2012, 2103 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;

/**
 * The Dali JPA state corresponding to an {@link IWorkspace Eclipse workspace}.
 * <p>
 * To retrieve the JPA workspace corresponding to an Eclipse workspace:
 * <pre>
 * IWorkspace workspace = ResourcesPlugin.getWorkspace();
 * JpaWorkspace jpaWorkspace = (JpaWorkspace) workspace.getAdapter(JpaWorkspace.class);
 * </pre>
 * <p>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
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
public interface JpaWorkspace {
	/**
	 * Return the corresponding Eclipse workspace.
	 */
	IWorkspace getWorkspace();

	/**
	 * Return the manager for the workspace's JPA platforms.
	 */
	JpaPlatformManager getJpaPlatformManager();

	/**
	 * Return the manager for all the workspace's JPA projects.
	 */
	JpaProjectManager getJpaProjectManager();

	/**
	 * Return the workspace's connection profile factory.
	 */
	ConnectionProfileFactory getConnectionProfileFactory();
}
