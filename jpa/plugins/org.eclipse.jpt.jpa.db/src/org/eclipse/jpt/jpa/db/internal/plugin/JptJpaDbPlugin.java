/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.plugin;

import java.util.Hashtable;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.jpa.db.internal.DTPConnectionProfileFactory;
import org.osgi.framework.BundleContext;

public class JptJpaDbPlugin
	extends JptPlugin
{
	private final Hashtable<IWorkspace, DTPConnectionProfileFactory> connectionProfileFactories = new Hashtable<IWorkspace, DTPConnectionProfileFactory>();


	// ********** singleton **********

	private static volatile JptJpaDbPlugin INSTANCE;  // sorta-final

	/**
	 * Return the Dali JPA DB plug-in.
	 */
	public static JptJpaDbPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJpaDbPlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaDbPlugin) plugin;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			this.disposeConnectionProfileFactories();
		} finally {
			super.stop(context);
		}
	}


	// ********** connection profile factories **********

	/**
	 * Return the connection profile factory corresponding to the specified
	 * Eclipse workspace.
	 * <p>
	 * The preferred way to retrieve a connection profile factory is via the
	 * Eclipse adapter framework:
	 * <pre>
	 * IWorkspace workspace = ...;
	 * ConnectionProfileFactory factory = (ConnectionProfileFactory) workspace.getAdapter(ConnectionProfileFactory.class)
	 * </pre>
	 * @see org.eclipse.jpt.jpa.db.internal.WorkspaceAdapterFactory#getConnectionProfileFactory(IWorkspace)
	 */
	public DTPConnectionProfileFactory getConnectionProfileFactory(IWorkspace workspace) {
		synchronized (this.connectionProfileFactories) {
			return this.getConnectionProfileFactory_(workspace);
		}
	}

	/**
	 * Pre-condition: {@link #connectionProfileFactories} is <code>synchronized</code>
	 */
	private DTPConnectionProfileFactory getConnectionProfileFactory_(IWorkspace workspace) {
		DTPConnectionProfileFactory factory = this.connectionProfileFactories.get(workspace);
		if ((factory == null) && this.isActive()) {  // no new factories can be built during "start" or "stop"...
			factory = this.buildConnectionProfileFactory(workspace);
	        this.connectionProfileFactories.put(workspace, factory);
		}
		return factory;
	}

	private DTPConnectionProfileFactory buildConnectionProfileFactory(IWorkspace workspace) {
		return new DTPConnectionProfileFactory(workspace);
	}

	private void disposeConnectionProfileFactories() {
		// the list will not change during "stop"
		for (DTPConnectionProfileFactory factory : this.connectionProfileFactories.values()) {
			try {
				factory.dispose();
			} catch (Throwable ex) {
				this.logError(ex);  // keep going
			}
		}
		this.connectionProfileFactories.clear();
	}
}
