/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.internal.platform.InternalJpaPlatformUiManager;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;

public class InternalJpaWorkbench
	implements JpaWorkbench
{
	private final IWorkbench workbench;

	// NB: the JPA workbench must be synchronized whenever accessing any of this state
	private InternalJpaPlatformUiManager jpaPlatformUiManager;
	private JpaSelectionManager jpaSelectionManager;
	private ResourceManager resourceManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJpaUiPlugin#buildJpaWorkbench(IWorkbench) Dali JPA UI plug-in}.
	 */
	public InternalJpaWorkbench(IWorkbench workbench) {
		super();
		this.workbench = workbench;
	}

	public IWorkbench getWorkbench() {
		return this.workbench;
	}


	// ********** JPA platform UI manager **********

	public synchronized InternalJpaPlatformUiManager getJpaPlatformUiManager() {
		if ((this.jpaPlatformUiManager == null) && this.isActive()) {
			this.jpaPlatformUiManager = this.buildJpaPlatformUiManager();
		}
		return this.jpaPlatformUiManager;
	}

	private InternalJpaPlatformUiManager buildJpaPlatformUiManager() {
		return new InternalJpaPlatformUiManager(this);
	}


	// ********** JPA selection manager **********

	public synchronized JpaSelectionManager getJpaSelectionManager() {
		return this.jpaSelectionManager;
	}

	/**
	 * The JPA workbench selection manager is controlled by
	 * {@link org.eclipse.jpt.jpa.ui.internal.selection.JpaWorkbenchSelectionManager}.
	 * @see org.eclipse.jpt.jpa.ui.internal.selection.JpaWorkbenchSelectionManager#dispose()
	 * @see org.eclipse.jpt.jpa.ui.internal.selection.JpaWorkbenchSelectionManager#forWorkbench_(IWorkbench)
	 */
	public synchronized void setJpaSelectionManager(JpaSelectionManager jpaSelectionManager) {
		this.jpaSelectionManager = jpaSelectionManager;
	}


	// ********** resource manager **********

	public ResourceManager buildLocalResourceManager() {
		ResourceManager rm = this.getResourceManager();
		return (rm == null) ? null : new LocalResourceManager(rm);
	}

	/**
	 * The local resource manager is stored in the
	 * {@link Control#getData(String) control's "custom properties"}
	 * and will dispose itself when the control is disposed.
	 */
	public synchronized ResourceManager getResourceManager(Control control) {
		ResourceManager controlRM = (ResourceManager) control.getData(RESOURCE_MANAGER_KEY);
		if (controlRM == null) {
			ResourceManager rm = this.getResourceManager();
			if (rm == null) {
				return null;
			}
			controlRM = new LocalResourceManager(rm, control);
			control.setData(RESOURCE_MANAGER_KEY, controlRM);
		}
		return controlRM;
	}
	private static final String RESOURCE_MANAGER_KEY = JptJpaUiPlugin.instance().getPluginID() + ".ResourceManager"; //$NON-NLS-1$

	private synchronized ResourceManager getResourceManager() {
		if ((this.resourceManager == null) && this.isActive()) {
			this.resourceManager = this.buildResourceManager();
		}
		return this.resourceManager;
	}

	private ResourceManager buildResourceManager() {
		return new LocalResourceManager(this.getParentResourceManager());
	}

	private ResourceManager getParentResourceManager() {
		return JFaceResources.getResources(this.getDisplay());
	}

	private Display getDisplay() {
		return this.workbench.getDisplay();
	}


	// ********** misc **********

	private boolean isActive() {
		return JptJpaUiPlugin.instance().isActive();
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJpaUiPlugin#stop_() Dali plug-in}.
	 */
	public synchronized void stop() {
		if (this.resourceManager != null) {
			this.resourceManager.dispose();
			this.resourceManager = null;
		}
		if (this.jpaPlatformUiManager != null) {
			this.jpaPlatformUiManager = null;
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workbench);
	}
}
