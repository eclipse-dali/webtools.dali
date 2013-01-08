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

import java.util.Hashtable;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.jaxb.ui.internal.InternalJaxbWorkbench;
import org.eclipse.ui.IWorkbench;
import org.osgi.framework.BundleContext;

/**
 * Dali JAXB UI plug-in.
 */
public class JptJaxbUiPlugin
	extends JptUIPlugin
{
	private final Hashtable<IWorkbench, InternalJaxbWorkbench> jaxbWorkbenches = new Hashtable<IWorkbench, InternalJaxbWorkbench>();


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
	public void stop(BundleContext context) throws Exception {
		try {
			this.disposeJaxbWorkbenches();
		} finally {
			super.stop(context);
		}
	}


	// ********** JAXB workbenches **********

	/**
	 * Return the JAXB workbench corresponding to the specified Eclipse workbench.
	 * <p>
	 * The preferred way to retrieve a JAXB workbench is via the Eclipse
	 * adapter framework:
	 * <pre>
	 * IWorkbench workbench = ...;
	 * JaxbWorkbench jaxbWorkbench = PlatformTools.getAdapter(workbench, JaxbWorkbench.class);
	 * </pre>
	 * @see org.eclipse.jpt.jaxb.ui.internal.WorkbenchAdapterFactory#getJaxbWorkbench(IWorkbench)
	 */
	public InternalJaxbWorkbench getJaxbWorkbench(IWorkbench workbench) {
		synchronized (this.jaxbWorkbenches) {
			return this.getJaxbWorkbench_(workbench);
		}
	}

	/**
	 * Pre-condition: {@link #jaxbWorkbenches} is <code>synchronized</code>
	 */
	private InternalJaxbWorkbench getJaxbWorkbench_(IWorkbench workbench) {
		InternalJaxbWorkbench jaxbWorkbench = this.jaxbWorkbenches.get(workbench);
		if ((jaxbWorkbench == null) && this.isActive()) {  // no new workbenches can be built during "start" or "stop"...
			jaxbWorkbench = this.buildJaxbWorkbench(workbench);
			this.jaxbWorkbenches.put(workbench, jaxbWorkbench);
		}
		return jaxbWorkbench;
	}

	private InternalJaxbWorkbench buildJaxbWorkbench(IWorkbench workbench) {
		return new InternalJaxbWorkbench(workbench);
	}

	private void disposeJaxbWorkbenches() {
		// the list will not change during "stop"
		for (InternalJaxbWorkbench jaxbWorkbench : this.jaxbWorkbenches.values()) {
			try {
				jaxbWorkbench.dispose();
			} catch (Throwable ex) {
				this.logError(ex);  // keep going
			}
		}
		this.jaxbWorkbenches.clear();
	}
}
