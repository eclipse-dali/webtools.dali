/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.internal.platform.InternalJaxbPlatformUiManager;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;
import org.eclipse.ui.IWorkbench;

public class InternalJaxbWorkbench
	implements JaxbWorkbench
{
	private final IWorkbench workbench;

	// NB: the JAXB workbench must be synchronized whenever accessing any of this state
	private InternalJaxbPlatformUiManager jaxbPlatformUiManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJaxbUiPlugin#buildJaxbWorkbench(IWorkbench) Dali JAXB UI plug-in}.
	 */
	public InternalJaxbWorkbench(IWorkbench workbench) {
		super();
		this.workbench = workbench;
	}

	public IWorkbench getWorkbench() {
		return this.workbench;
	}


	// ********** JAXB platform UI manager **********

	public synchronized InternalJaxbPlatformUiManager getJaxbPlatformUiManager() {
		if ((this.jaxbPlatformUiManager == null) && this.isActive()) {
			this.jaxbPlatformUiManager = this.buildJaxbPlatformUiManager();
		}
		return this.jaxbPlatformUiManager;
	}

	private InternalJaxbPlatformUiManager buildJaxbPlatformUiManager() {
		return new InternalJaxbPlatformUiManager(this);
	}


	// ********** misc **********

	private boolean isActive() {
		return JptJaxbUiPlugin.instance().isActive();
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJaxbUiPlugin#stop_() Dali plug-in}.
	 */
	public synchronized void stop() {
		if (this.jaxbPlatformUiManager != null) {
			this.jaxbPlatformUiManager = null;
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workbench);
	}
}
