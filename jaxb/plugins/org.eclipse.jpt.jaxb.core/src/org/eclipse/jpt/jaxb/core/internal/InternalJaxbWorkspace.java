/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.internal.platform.InternalJaxbPlatformManager;

public class InternalJaxbWorkspace
	implements JaxbWorkspace
{
	private final IWorkspace workspace;

	private final JptWorkspace jptWorkspace;
	private final InternalJaxbPlatformManager jaxbPlatformManager;
	private final InternalJaxbProjectManager jaxbProjectManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin#buildJaxbWorkspace(IWorkspace)
	 * Dali JAXB plug-in}.
	 */
	public InternalJaxbWorkspace(IWorkspace workspace) {
		super();
		this.workspace = workspace;
		this.jptWorkspace = this.buildJptWorkspace();
		this.jaxbPlatformManager = this.buildJaxbPlatformManager();
		this.jaxbProjectManager = this.buildJaxbProjectManager();
		this.jaxbProjectManager.start();
	}


	// ********** Dali workspace **********

	public JptWorkspace getJptWorkspace() {
		return this.jptWorkspace;
	}

	private JptWorkspace buildJptWorkspace() {
		return (JptWorkspace) this.workspace.getAdapter(JptWorkspace.class);
	}


	// ********** JAXB platform manager **********

	public InternalJaxbPlatformManager getJaxbPlatformManager() {
		return this.jaxbPlatformManager;
	}

	private InternalJaxbPlatformManager buildJaxbPlatformManager() {
		return new InternalJaxbPlatformManager(this);
	}


	// ********** JAXB project manager **********

	public InternalJaxbProjectManager getJaxbProjectManager() {
		return this.jaxbProjectManager;
	}

	private InternalJaxbProjectManager buildJaxbProjectManager() {
		return new InternalJaxbProjectManager(this);
	}


	// ********** misc **********

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

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin#stop(org.osgi.framework.BundleContext)
	 * Dali JAXB plug-in}.
	 */
	public void dispose() {
		this.jaxbProjectManager.stop();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workspace);
	}
}
