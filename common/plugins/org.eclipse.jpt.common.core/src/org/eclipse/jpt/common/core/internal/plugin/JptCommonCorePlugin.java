/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.plugin;

import java.util.HashMap;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.internal.InternalJptWorkspace;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class JptCommonCorePlugin
	extends JptPlugin
{
	// NB: the plug-in must be synchronized whenever accessing any of this state
	private final HashMap<IWorkspace, InternalJptWorkspace> jptWorkspaces = new HashMap<IWorkspace, InternalJptWorkspace>();


	// ********** singleton **********

	private static volatile JptCommonCorePlugin INSTANCE;

	/**
	 * Return the singleton Dali common core plug-in.
	 */
	public static JptCommonCorePlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptCommonCorePlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptCommonCorePlugin) plugin;
	}

	@Override
	protected void stop_() throws Exception {
		try {
			for (InternalJptWorkspace jptWorkspace : this.jptWorkspaces.values()) {
				try {
					jptWorkspace.stop();
				} catch (Throwable ex) {
					this.logError(ex);  // keep going
				}
			}
			this.jptWorkspaces.clear();
		} finally {
			super.stop_();
		}
	}


	// ********** Dali workspaces **********

	/**
	 * Return the Dali workspace corresponding to the specified Eclipse workspace.
	 * <p>
	 * The preferred way to retrieve a Dali workspace is via the Eclipse
	 * adapter framework:
	 * <pre>
	 * JptWorkspace jptWorkspace = (JptWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JptWorkspace.class)
	 * </pre>
	 * @see org.eclipse.jpt.common.core.internal.WorkspaceAdapterFactory#getJptWorkspace(IWorkspace)
	 */
	public synchronized InternalJptWorkspace getJptWorkspace(IWorkspace workspace) {
		InternalJptWorkspace jptWorkspace = this.jptWorkspaces.get(workspace);
		if ((jptWorkspace == null) && this.isActive()) {
			jptWorkspace = this.buildJptWorkspace(workspace);
			this.jptWorkspaces.put(workspace, jptWorkspace);
		}
		return jptWorkspace;
	}

	private InternalJptWorkspace buildJptWorkspace(IWorkspace workspace) {
		return new InternalJptWorkspace(workspace);
	}
}
