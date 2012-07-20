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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.internal.platform.InternalJpaPlatformManager;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;

public class InternalJpaWorkspace
	implements JpaWorkspace
{
	private final IWorkspace workspace;

	// NB: the JPA workspace must be synchronized whenever accessing any of this state
	private InternalJpaPlatformManager jpaPlatformManager;
	private InternalJpaProjectManager jpaProjectManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJpaCorePlugin#buildJpaWorkspace(IWorkspace) Dali JPA plug-in}.
	 */
	public InternalJpaWorkspace(IWorkspace workspace) {
		super();
		this.workspace = workspace;
	}

	public IWorkspace getWorkspace() {
		return workspace;
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JpaPreferenceInitializer#initializeDefaultPreferences()
	 * JPA preferences initializer}.
	 */
	void initializeDefaultPreferences() {
		this.getJpaPlatformManager().initializeDefaultPreferences();
	}


	// ********** JPA platform manager **********

	public synchronized InternalJpaPlatformManager getJpaPlatformManager() {
		if ((this.jpaPlatformManager == null) && this.isActive()) {
			this.jpaPlatformManager = this.buildJpaPlatformManager();
		}
		return this.jpaPlatformManager;
	}

	private InternalJpaPlatformManager buildJpaPlatformManager() {
		return new InternalJpaPlatformManager(this);
	}


	// ********** JPA project manager **********

	public synchronized InternalJpaProjectManager getJpaProjectManager() {
		if ((this.jpaProjectManager == null) && this.isActive()) {
			this.jpaProjectManager = this.buildJpaProjectManager();
			this.jpaProjectManager.start();
		}
		return this.jpaProjectManager;
	}

	private InternalJpaProjectManager buildJpaProjectManager() {
		return new InternalJpaProjectManager(this);
	}


	// ********** misc **********

	private boolean isActive() {
		return JptJpaCorePlugin.instance().isActive();
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJpaCorePlugin#stop_() Dali plug-in}.
	 */
	public synchronized void stop() {
		if (this.jpaPlatformManager != null) {
			this.jpaPlatformManager = null;
		}
		if (this.jpaProjectManager != null) {
			this.jpaProjectManager.stop();
			this.jpaProjectManager = null;
		}
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.workspace);
	}
}
