/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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

	private final InternalJptResourceTypeManager resourceTypeManager;
	private final InternalLibraryValidatorManager libraryValidatorManager;
	private final InternalResourceLocatorManager resourceLocatorManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptCommonCorePlugin#buildJptWorkspace(IWorkspace) Dali plug-in}.
	 */
	public InternalJptWorkspace(IWorkspace workspace) {
		super();
		this.workspace = workspace;
		this.resourceTypeManager = this.buildResourceTypeManager();
		this.libraryValidatorManager = this.buildLibraryValidatorManager();
		this.resourceLocatorManager = this.buildResourceLocatorManager();
	}


	// ********** Dali resource type manager **********

	public InternalJptResourceTypeManager getResourceTypeManager() {
		return this.resourceTypeManager;
	}

	private InternalJptResourceTypeManager buildResourceTypeManager() {
		return new InternalJptResourceTypeManager(this);
	}


	// ********** Dali library validator manager **********

	public InternalLibraryValidatorManager getLibraryValidatorManager() {
		return this.libraryValidatorManager;
	}

	private InternalLibraryValidatorManager buildLibraryValidatorManager() {
		return new InternalLibraryValidatorManager(this);
	}


	// ********** Dali resource locator manager **********

	public InternalResourceLocatorManager getResourceLocatorManager() {
		return this.resourceLocatorManager;
	}

	private InternalResourceLocatorManager buildResourceLocatorManager() {
		return new InternalResourceLocatorManager(this);
	}


	// ********** misc **********

	public IWorkspace getWorkspace() {
		return this.workspace;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workspace);
	}
}
