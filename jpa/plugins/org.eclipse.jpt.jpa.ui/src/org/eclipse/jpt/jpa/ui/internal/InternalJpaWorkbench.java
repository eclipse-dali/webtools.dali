/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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

	private final InternalJpaPlatformUiManager jpaPlatformUiManager;
	private volatile JpaSelectionManager jpaSelectionManager;
	private final ResourceManager resourceManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJpaUiPlugin#buildJpaWorkbench(IWorkbench) Dali JPA UI plug-in}.
	 */
	public InternalJpaWorkbench(IWorkbench workbench) {
		super();
		this.workbench = workbench;
		this.jpaPlatformUiManager = this.buildJpaPlatformUiManager();
		this.resourceManager = this.buildResourceManager();
	}


	// ********** JPA platform UI manager **********

	public InternalJpaPlatformUiManager getJpaPlatformUiManager() {
		return this.jpaPlatformUiManager;
	}

	private InternalJpaPlatformUiManager buildJpaPlatformUiManager() {
		return new InternalJpaPlatformUiManager(this);
	}


	// ********** JPA selection manager **********

	public JpaSelectionManager getJpaSelectionManager() {
		return this.jpaSelectionManager;
	}

	/**
	 * The JPA workbench selection manager is controlled by
	 * {@link org.eclipse.jpt.jpa.ui.internal.selection.JpaWorkbenchSelectionManager}.
	 * @see org.eclipse.jpt.jpa.ui.internal.selection.JpaWorkbenchSelectionManager#dispose()
	 * @see org.eclipse.jpt.jpa.ui.internal.selection.JpaWorkbenchSelectionManager#forWorkbench_(IWorkbench)
	 */
	public void setJpaSelectionManager(JpaSelectionManager jpaSelectionManager) {
		this.jpaSelectionManager = jpaSelectionManager;
	}


	// ********** resource manager **********

	public ResourceManager buildLocalResourceManager() {
		return new LocalResourceManager(this.resourceManager);
	}

	/**
	 * The local resource manager is stored in the
	 * {@link Control#getData(String) control's "custom properties"}
	 * and will dispose itself when the control is disposed.
	 */
	public synchronized ResourceManager getResourceManager(Control control) {
		ResourceManager controlRM = (ResourceManager) control.getData(RESOURCE_MANAGER_KEY);
		if (controlRM == null) {
			controlRM = new LocalResourceManager(this.resourceManager, control);
			control.setData(RESOURCE_MANAGER_KEY, controlRM);
		}
		return controlRM;
	}
	private static final String RESOURCE_MANAGER_KEY = JptJpaUiPlugin.instance().getPluginID() + ".ResourceManager"; //$NON-NLS-1$

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

	public IWorkbench getWorkbench() {
		return this.workbench;
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJpaUiPlugin#stop(org.osgi.framework.BundleContext)
	 * Dali JPA UI plug-in}.
	 */
	public void dispose() {
		this.resourceManager.dispose();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workbench);
	}
}
