/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.platform.InternalJpaPlatformManager;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;

public class InternalJpaWorkspace
	implements JpaWorkspace
{
	private final IWorkspace workspace;

	private final InternalJpaPlatformManager jpaPlatformManager;
	private final InternalJpaProjectManager jpaProjectManager;
	private final ConnectionProfileFactory connectionProfileFactory;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin#buildJpaWorkspace(IWorkspace)
	 * Dali JPA plug-in}.
	 */
	public InternalJpaWorkspace(IWorkspace workspace) {
		super();
		this.workspace = workspace;
		this.jpaPlatformManager = this.buildJpaPlatformManager();
		this.jpaProjectManager = this.buildJpaProjectManager();
		this.connectionProfileFactory = this.buildConnectionProfileFactory();
	}


	// ********** JPA platform manager **********

	public InternalJpaPlatformManager getJpaPlatformManager() {
		return this.jpaPlatformManager;
	}

	private InternalJpaPlatformManager buildJpaPlatformManager() {
		return new InternalJpaPlatformManager(this);
	}


	// ********** JPA project manager **********

	public InternalJpaProjectManager getJpaProjectManager() {
		return this.jpaProjectManager;
	}

	private InternalJpaProjectManager buildJpaProjectManager() {
		return new InternalJpaProjectManager(this);
	}


	// ********** connection profile factory **********

	public ConnectionProfileFactory getConnectionProfileFactory() {
		return this.connectionProfileFactory;
	}

	private ConnectionProfileFactory buildConnectionProfileFactory() {
		return (ConnectionProfileFactory) this.workspace.getAdapter(ConnectionProfileFactory.class);
	}


	// ********** misc **********

	public IWorkspace getWorkspace() {
		return this.workspace;
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JpaPreferenceInitializer#initializeDefaultPreferences()
	 * JPA preferences initializer}.
	 */
	void initializeDefaultPreferences() {
		this.getJpaPlatformManager().initializeDefaultPreferences();
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin#stop(org.osgi.framework.BundleContext)
	 * Dali plug-in}.
	 * <p>
	 * This will suspend the current thread until all the JPA projects are
	 * disposed etc.
	 */
	public void dispose() {
		this.jpaProjectManager.dispose();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workspace);
	}
}
