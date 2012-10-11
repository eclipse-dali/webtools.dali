/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.plugin;

import java.util.HashMap;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.jaxb.core.internal.InternalJaxbWorkspace;

public class JptJaxbCorePlugin
	extends JptPlugin
{
	// NB: the plug-in must be synchronized whenever accessing any of this state
	private final HashMap<IWorkspace, InternalJaxbWorkspace> jaxbWorkspaces = new HashMap<IWorkspace, InternalJaxbWorkspace>();


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
	protected void stop_() throws Exception {
		try {
			for (InternalJaxbWorkspace jaxbWorkspace : this.jaxbWorkspaces.values()) {
				try {
					jaxbWorkspace.stop();
				} catch (Throwable ex) {
					this.logError(ex);  // keep going
				}
			}
			this.jaxbWorkspaces.clear();
		} finally {
			super.stop_();
		}
	}


	// ********** JAXB workspaces **********

	/**
	 * Return the JAXB workspace corresponding to the specified Eclipse workspace.
	 * <p>
	 * The preferred way to retrieve a JAXB workspace is via the Eclipse
	 * adapter framework:
	 * <pre>
	 * JaxbWorkspace jaxbWorkspace = (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class)
	 * </pre>
	 * @see org.eclipse.jpt.jaxb.core.internal.WorkspaceAdapterFactory#getJaxbWorkspace(IWorkspace)
	 */
	public synchronized InternalJaxbWorkspace getJaxbWorkspace(IWorkspace workspace) {
		InternalJaxbWorkspace jaxbWorkspace = this.jaxbWorkspaces.get(workspace);
		if ((jaxbWorkspace == null) && this.isActive()) {
			jaxbWorkspace = this.buildJaxbWorkspace(workspace);
			this.jaxbWorkspaces.put(workspace, jaxbWorkspace);
		}
		return jaxbWorkspace;
	}

	private InternalJaxbWorkspace buildJaxbWorkspace(IWorkspace workspace) {
		return new InternalJaxbWorkspace(workspace);
	}
}
