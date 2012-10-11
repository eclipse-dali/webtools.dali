/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.plugin;

import java.util.HashMap;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.jaxb.ui.internal.InternalJaxbWorkbench;
import org.eclipse.ui.IWorkbench;

/**
 * Dali JAXB UI plug-in.
 */
public class JptJaxbUiPlugin
	extends JptUIPlugin
{
	// NB: the plug-in must be synchronized whenever accessing any of this state
	private final HashMap<IWorkbench, InternalJaxbWorkbench> jaxbWorkbenchs = new HashMap<IWorkbench, InternalJaxbWorkbench>();


	// ********** singleton **********

	private static JptJaxbUiPlugin INSTANCE;

	/**
	 * Return the singleton Dali JAXB UI plug-in.
	 */
	public static JptJaxbUiPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJaxbUiPlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJaxbUiPlugin) plugin;
	}

	@Override
	public void stop_() throws Exception {
		try {
			for (InternalJaxbWorkbench jaxbWorkbench : this.jaxbWorkbenchs.values()) {
				try {
					jaxbWorkbench.stop();
				} catch (Throwable ex) {
					this.logError(ex);  // keep going
				}
			}
			this.jaxbWorkbenchs.clear();
		} finally {
			super.stop_();
		}
	}


	// ********** JAXB workbenchs **********

	/**
	 * Return the JAXB workbench corresponding to the specified Eclipse workbench.
	 * <p>
	 * The preferred way to retrieve a JAXB workbench is via the Eclipse
	 * adapter framework:
	 * <pre>
	 * JaxbWorkbench jaxbWorkbench = PlatformTools.getAdapter(PlatformUI.getWorkbench(), JaxbWorkbench.class);
	 * </pre>
	 * @see org.eclipse.jpt.jaxb.ui.internal.WorkbenchAdapterFactory#getJaxbWorkbench(IWorkbench)
	 */
	public synchronized InternalJaxbWorkbench getJaxbWorkbench(IWorkbench workbench) {
		InternalJaxbWorkbench jaxbWorkbench = this.jaxbWorkbenchs.get(workbench);
		if ((jaxbWorkbench == null) && this.isActive()) {
			jaxbWorkbench = this.buildJaxbWorkbench(workbench);
			this.jaxbWorkbenchs.put(workbench, jaxbWorkbench);
		}
		return jaxbWorkbench;
	}

	private InternalJaxbWorkbench buildJaxbWorkbench(IWorkbench workbench) {
		return new InternalJaxbWorkbench(workbench);
	}
}
