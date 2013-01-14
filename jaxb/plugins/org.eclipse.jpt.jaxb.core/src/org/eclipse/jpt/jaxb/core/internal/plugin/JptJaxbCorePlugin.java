/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.plugin;

import java.util.Hashtable;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.jaxb.core.internal.InternalJaxbWorkspace;
import org.osgi.framework.BundleContext;

public class JptJaxbCorePlugin
	extends JptPlugin
{
	private final Hashtable<IWorkspace, InternalJaxbWorkspace> jaxbWorkspaces = new Hashtable<IWorkspace, InternalJaxbWorkspace>();


	// ********** singleton **********

	private static JptJaxbCorePlugin INSTANCE;

	/**
	 * Return the Dali JAXB core plug-in.
	 */
	public static JptJaxbCorePlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJaxbCorePlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJaxbCorePlugin) plugin;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			this.disposeJaxbWorkspaces();
		} finally {
			super.stop(context);
		}
	}


	// ********** JAXB workspaces **********

	/**
	 * Return the JAXB workspace corresponding to the specified Eclipse workspace.
	 * <p>
	 * The preferred way to retrieve a JAXB workspace is via the Eclipse
	 * adapter framework:
	 * <pre>
	 * IWorkspace workspace = ResourcesPlugin.getWorkspace();
	 * JaxbWorkspace jaxbWorkspace = (JaxbWorkspace) workspace.getAdapter(JaxbWorkspace.class)
	 * </pre>
	 * @see org.eclipse.jpt.jaxb.core.internal.WorkspaceAdapterFactory#getJaxbWorkspace(IWorkspace)
	 */
	public InternalJaxbWorkspace getJaxbWorkspace(IWorkspace workspace) {
		synchronized (this.jaxbWorkspaces) {
			return this.getJaxbWorkspace_(workspace);
		}
	}

	/**
	 * Pre-condition: {@link #jaxbWorkspaces} is <code>synchronized</code>
	 */
	private InternalJaxbWorkspace getJaxbWorkspace_(IWorkspace workspace) {
		InternalJaxbWorkspace jaxbWorkspace = this.jaxbWorkspaces.get(workspace);
		if ((jaxbWorkspace == null) && this.isActive()) {  // no new workspaces can be built during "start" or "stop"...
			jaxbWorkspace = this.buildJaxbWorkspace(workspace);
			this.jaxbWorkspaces.put(workspace, jaxbWorkspace);
		}
		return jaxbWorkspace;
	}

	private InternalJaxbWorkspace buildJaxbWorkspace(IWorkspace workspace) {
		return new InternalJaxbWorkspace(workspace);
	}

	private void disposeJaxbWorkspaces() {
		// the list will not change during "stop"
		for (InternalJaxbWorkspace jaxbWorkspace : this.jaxbWorkspaces.values()) {
			try {
				jaxbWorkspace.dispose();
			} catch (Throwable ex) {
				this.logError(ex);  // keep going
			}
		}
		this.jaxbWorkspaces.clear();
	}
}
