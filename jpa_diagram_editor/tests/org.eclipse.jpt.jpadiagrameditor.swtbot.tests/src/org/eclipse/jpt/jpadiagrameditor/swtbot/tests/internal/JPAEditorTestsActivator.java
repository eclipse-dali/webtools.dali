/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.osgi.framework.BundleContext;

public class JPAEditorTestsActivator extends Plugin {

	/**
	 * 
	 */
	public JPAEditorTestsActivator() {
	}

	// The shared instance
	private static JPAEditorTestsActivator plugin;
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		JpaProjectManager jpaProjectManager = this.getJpaProjectManager();
//		ReflectionTools.executeMethod(jpaProjectManager, "executeCommandsSynchronously"); //$NON-NLS-1$
	}

	protected JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static JPAEditorTestsActivator getDefault() {
		return plugin;
	}

	
	
}
