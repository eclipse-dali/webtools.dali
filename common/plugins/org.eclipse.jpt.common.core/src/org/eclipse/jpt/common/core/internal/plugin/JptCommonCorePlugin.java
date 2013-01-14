/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.plugin;

import java.util.Hashtable;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.internal.InternalJptWorkspace;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.osgi.framework.BundleContext;

public class JptCommonCorePlugin
	extends JptPlugin
{
	private final Hashtable<IWorkspace, InternalJptWorkspace> jptWorkspaces = new Hashtable<IWorkspace, InternalJptWorkspace>();


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
	public void stop(BundleContext context) throws Exception {
		try {
			// force the workspaces to be rebuilt if the plug-in is restarted
			this.jptWorkspaces.clear();
		} finally {
			super.stop(context);
		}
	}


	// ********** Dali workspaces **********

	/**
	 * Return the Dali workspace corresponding to the specified Eclipse workspace.
	 * <p>
	 * The preferred way to retrieve a Dali workspace is via the Eclipse
	 * adapter framework:
	 * <pre>
	 * IWorkspace workspace = ResourcesPlugin.getWorkspace();
	 * JptWorkspace jptWorkspace = (JptWorkspace) workspace.getAdapter(JptWorkspace.class)
	 * </pre>
	 * @see org.eclipse.jpt.common.core.internal.WorkspaceAdapterFactory#getJptWorkspace(IWorkspace)
	 */
	public InternalJptWorkspace getJptWorkspace(IWorkspace workspace) {
		synchronized (this.jptWorkspaces) {
			return this.getJptWorkspace_(workspace);
		}
	}

	/**
	 * Pre-condition: {@link #jptWorkspaces} is <code>synchronized</code>
	 */
	private InternalJptWorkspace getJptWorkspace_(IWorkspace workspace) {
		InternalJptWorkspace jptWorkspace = this.jptWorkspaces.get(workspace);
		if ((jptWorkspace == null) && this.isActive()) {  // no new workspaces can be built during "start" or "stop"...
			jptWorkspace = this.buildJptWorkspace(workspace);
			this.jptWorkspaces.put(workspace, jptWorkspace);
		}
		return jptWorkspace;
	}

	private InternalJptWorkspace buildJptWorkspace(IWorkspace workspace) {
		return new InternalJptWorkspace(workspace);
	}
}
