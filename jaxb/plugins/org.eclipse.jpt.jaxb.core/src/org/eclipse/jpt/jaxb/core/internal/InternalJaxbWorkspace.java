/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.internal.platform.InternalJaxbPlatformManager;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;

public class InternalJaxbWorkspace
	implements JaxbWorkspace
{
	private final IWorkspace workspace;

	// NB: the JAXB workspace must be synchronized whenever accessing any of this state
	private InternalJaxbPlatformManager jaxbPlatformManager;
	private InternalJaxbProjectManager jaxbProjectManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJaxbCorePlugin#buildJaxbWorkspace(IWorkspace) Dali JAXB plug-in}.
	 */
	public InternalJaxbWorkspace(IWorkspace workspace) {
		super();
		this.workspace = workspace;
	}

	public IWorkspace getWorkspace() {
		return this.workspace;
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JaxbPreferenceInitializer#initializeDefaultPreferences()
	 * JAXB preferences initializer}.
	 */
	void initializeDefaultPreferences() {
		this.getJaxbPlatformManager().initializeDefaultPreferences();
	}


	// ********** JAXB platform manager **********

	public synchronized InternalJaxbPlatformManager getJaxbPlatformManager() {
		if ((this.jaxbPlatformManager == null) && this.isActive()) {
			this.jaxbPlatformManager = this.buildJaxbPlatformManager();
		}
		return this.jaxbPlatformManager;
	}

	private InternalJaxbPlatformManager buildJaxbPlatformManager() {
		return new InternalJaxbPlatformManager(this);
	}


	// ********** JAXB project manager **********

	public synchronized InternalJaxbProjectManager getJaxbProjectManager() {
		if ((this.jaxbProjectManager == null) && this.isActive()) {
			this.jaxbProjectManager = this.buildJaxbProjectManager();
			this.jaxbProjectManager.start();
		}
		return this.jaxbProjectManager;
	}

	private InternalJaxbProjectManager buildJaxbProjectManager() {
		return new InternalJaxbProjectManager(this);
	}


	// ********** misc **********

	private boolean isActive() {
		return JptJaxbCorePlugin.instance().isActive();
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJaxbCorePlugin#stop_() Dali plug-in}.
	 */
	public synchronized void stop() {
		if (this.jaxbPlatformManager != null) {
			this.jaxbPlatformManager = null;
		}
		if (this.jaxbProjectManager != null) {
			this.jaxbProjectManager.stop();
			this.jaxbProjectManager = null;
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workspace);
	}
}
