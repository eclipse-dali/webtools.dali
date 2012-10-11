/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.core.internal.libval.InternalLibraryValidatorManager;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.resource.InternalResourceLocatorManager;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

public class InternalJptWorkspace
	implements JptWorkspace
{
	private final IWorkspace workspace;

	// NB: the Dali workspace must be synchronized whenever accessing any of this state
	private InternalJptResourceTypeManager resourceTypeManager;
	private InternalLibraryValidatorManager libraryValidatorManager;
	private InternalResourceLocatorManager resourceLocatorManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptCommonCorePlugin#buildJptWorkspace(IWorkspace) Dali plug-in}.
	 */
	public InternalJptWorkspace(IWorkspace workspace) {
		super();
		this.workspace = workspace;
	}

	public IWorkspace getWorkspace() {
		return this.workspace;
	}


	// ********** Dali resource type manager **********

	public synchronized InternalJptResourceTypeManager getResourceTypeManager() {
		if ((this.resourceTypeManager == null) && this.isActive()) {
			this.resourceTypeManager = this.buildResourceTypeManager();
		}
		return this.resourceTypeManager;
	}

	private InternalJptResourceTypeManager buildResourceTypeManager() {
		return new InternalJptResourceTypeManager(this);
	}


	// ********** Dali library validator manager **********

	public synchronized InternalLibraryValidatorManager getLibraryValidatorManager() {
		if ((this.libraryValidatorManager == null) && this.isActive()) {
			this.libraryValidatorManager = this.buildLibraryValidatorManager();
		}
		return this.libraryValidatorManager;
	}

	private InternalLibraryValidatorManager buildLibraryValidatorManager() {
		return new InternalLibraryValidatorManager(this);
	}


	// ********** Dali resource locator manager **********

	public synchronized InternalResourceLocatorManager getResourceLocatorManager() {
		if ((this.resourceLocatorManager == null) && this.isActive()) {
			this.resourceLocatorManager = this.buildResourceLocatorManager();
		}
		return this.resourceLocatorManager;
	}

	private InternalResourceLocatorManager buildResourceLocatorManager() {
		return new InternalResourceLocatorManager(this);
	}


	// ********** misc **********

	private boolean isActive() {
		return JptCommonCorePlugin.instance().isActive();
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptCommonCorePlugin#stop_() Dali plug-in}.
	 */
	public synchronized void stop() {
		if (this.resourceTypeManager != null) {
			this.resourceTypeManager = null;
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workspace);
	}
}
