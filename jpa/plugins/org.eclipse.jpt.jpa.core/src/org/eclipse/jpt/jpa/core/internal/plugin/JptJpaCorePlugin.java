/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.plugin;

import java.util.Hashtable;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.internal.InternalJpaWorkspace;
import org.osgi.framework.BundleContext;

/**
 * Dali JPA core plug-in.
 */
public class JptJpaCorePlugin
	extends JptPlugin
{
	private final Hashtable<IWorkspace, InternalJpaWorkspace> jpaWorkspaces = new Hashtable<IWorkspace, InternalJpaWorkspace>();


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
	public void stop(BundleContext context) throws Exception {
		try {
			this.disposeJpaWorkspaces();
		} finally {
			super.stop(context);
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
	 * IWorkspace workspace = ResourcesPlugin.getWorkspace();
	 * JpaWorkspace jpaWorkspace = (JpaWorkspace) workspace.getAdapter(JpaWorkspace.class)
	 * </pre>
	 * @see org.eclipse.jpt.jpa.core.internal.WorkspaceAdapterFactory#getJpaWorkspace(IWorkspace)
	 */
	public InternalJpaWorkspace getJpaWorkspace(IWorkspace workspace) {
		synchronized (this.jpaWorkspaces) {
			return this.getJpaWorkspace_(workspace);
		}
	}

	/**
	 * Pre-condition: {@link #jpaWorkspaces} is <code>synchronized</code>
	 */
	private InternalJpaWorkspace getJpaWorkspace_(IWorkspace workspace) {
		InternalJpaWorkspace jpaWorkspace = this.jpaWorkspaces.get(workspace);
		if ((jpaWorkspace == null) && this.isActive()) {  // no new workspaces can be built during "start" or "stop"...
			jpaWorkspace = this.buildJpaWorkspace(workspace);
			this.jpaWorkspaces.put(workspace, jpaWorkspace);
		}
		return jpaWorkspace;
	}

	private InternalJpaWorkspace buildJpaWorkspace(IWorkspace workspace) {
		return new InternalJpaWorkspace(workspace);
	}

	/**
	 * This will suspend the current thread until all the JPA projects are
	 * disposed etc.
	 */
	private void disposeJpaWorkspaces() {
		// the list will not change during "stop"
		for (InternalJpaWorkspace jpaWorkspace : this.jpaWorkspaces.values()) {
			try {
				jpaWorkspace.dispose();
			} catch (Throwable ex) {
				this.logError(ex);  // keep going
			}
		}
		this.jpaWorkspaces.clear();
	}
}
