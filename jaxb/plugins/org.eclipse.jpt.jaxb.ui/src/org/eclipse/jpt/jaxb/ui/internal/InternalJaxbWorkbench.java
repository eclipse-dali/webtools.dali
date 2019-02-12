/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.internal.platform.InternalJaxbPlatformUiManager;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;
import org.eclipse.ui.IWorkbench;

public class InternalJaxbWorkbench
	implements JaxbWorkbench
{
	private final IWorkbench workbench;

	private final JaxbWorkspace jaxbWorkspace;
	private final InternalJaxbPlatformUiManager jaxbPlatformUiManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJaxbUiPlugin#buildJaxbWorkbench(IWorkbench) Dali JAXB UI plug-in}.
	 */
	public InternalJaxbWorkbench(IWorkbench workbench) {
		super();
		this.workbench = workbench;
		this.jaxbWorkspace = this.buildJaxbWorkspace();
		this.jaxbPlatformUiManager = this.buildJaxbPlatformUiManager();
	}


	// ********** JAXB platform UI manager **********

	public JaxbWorkspace getJaxbWorkspace() {
		return this.jaxbWorkspace;
	}

	private JaxbWorkspace buildJaxbWorkspace() {
		return (JaxbWorkspace) this.getWorkspace().getAdapter(JaxbWorkspace.class);
	}


	// ********** JAXB platform UI manager **********

	public InternalJaxbPlatformUiManager getJaxbPlatformUiManager() {
		return this.jaxbPlatformUiManager;
	}

	private InternalJaxbPlatformUiManager buildJaxbPlatformUiManager() {
		return new InternalJaxbPlatformUiManager(this);
	}


	// ********** misc **********

	public IWorkbench getWorkbench() {
		return this.workbench;
	}

	private IWorkspace getWorkspace() {
		// I would like to think the workbench held a reference to the workspace;
		// but it just uses hard-coded references to singletons :-(
		// (e.g. IDEWorkbenchPlugin.getPluginWorkspace())
		return ResourcesPlugin.getWorkspace();
	}

	public void dispose() {
		// nothing yet...
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workbench);
	}
}
