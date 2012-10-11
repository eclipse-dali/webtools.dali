/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.plugin;

import java.util.HashMap;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.internal.InternalJpaWorkspace;

/**
 * Dali JPA core plug-in.
 */
public class JptJpaCorePlugin
	extends JptPlugin
{
	// NB: the plug-in must be synchronized whenever accessing any of this state
	private final HashMap<IWorkspace, InternalJpaWorkspace> jpaWorkspaces = new HashMap<IWorkspace, InternalJpaWorkspace>();


	// ********** singleton **********

	private static volatile JptJpaCorePlugin INSTANCE;

	/**
	 * Return the Dali JPA core plug-in.
	 */
	public static JptJpaCorePlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJpaCorePlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaCorePlugin) plugin;
	}

	@Override
	protected void stop_() throws Exception {
		try {
			for (InternalJpaWorkspace jpaWorkspace : this.jpaWorkspaces.values()) {
				try {
					jpaWorkspace.stop();
				} catch (Throwable ex) {
					this.logError(ex);  // keep going
				}
			}
			this.jpaWorkspaces.clear();
		} finally {
			super.stop_();
		}
	}

	/**
	 * The qualification for persistent properties was mistakenly changed
	 * when the plug-in ID changed; so keep it that way.
	 */
	@Override
	protected String getPersistentPropertyPluginID() {
		return this.getPluginID();
	}

	/**
	 * With the addition of JAXB support to Dali, an additional JPA scope was
	 * added to the original plug-in ID; so use the old plug-in ID for prefs.
	 */
	@Override
	protected String getOriginalPluginID_() {
		String pluginID = this.getPluginID();
		return (pluginID == null) ? null : pluginID.replace(".jpa", StringTools.EMPTY_STRING); //$NON-NLS-1$
	}


	// ********** JPA workspaces **********

	/**
	 * Return the JPA workspace corresponding to the specified Eclipse workspace.
	 * <p>
	 * The preferred way to retrieve a JPA workspace is via the Eclipse
	 * adapter framework:
	 * <pre>
	 * JpaWorkspace jpaWorkspace = (JpaWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JpaWorkspace.class)
	 * </pre>
	 * @see org.eclipse.jpt.jpa.core.internal.WorkspaceAdapterFactory#getJpaWorkspace(IWorkspace)
	 */
	public synchronized InternalJpaWorkspace getJpaWorkspace(IWorkspace workspace) {
		InternalJpaWorkspace jpaWorkspace = this.jpaWorkspaces.get(workspace);
		if ((jpaWorkspace == null) && this.isActive()) {
			jpaWorkspace = this.buildJpaWorkspace(workspace);
			this.jpaWorkspaces.put(workspace, jpaWorkspace);
		}
		return jpaWorkspace;
	}

	private InternalJpaWorkspace buildJpaWorkspace(IWorkspace workspace) {
		return new InternalJpaWorkspace(workspace);
	}
}
