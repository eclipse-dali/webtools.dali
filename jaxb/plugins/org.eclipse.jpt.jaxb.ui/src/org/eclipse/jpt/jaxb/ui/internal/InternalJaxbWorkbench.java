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

	private final InternalJaxbPlatformUiManager jaxbPlatformUiManager;


	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJaxbUiPlugin#buildJaxbWorkbench(IWorkbench) Dali JAXB UI plug-in}.
	 */
	public InternalJaxbWorkbench(IWorkbench workbench) {
		super();
		this.workbench = workbench;
		this.jaxbPlatformUiManager = this.buildJaxbPlatformUiManager();
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

	public void dispose() {
		// nothing yet...
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.workbench);
	}
}
