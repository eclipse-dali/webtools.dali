/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
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
	protected void stop_() throws Exception {
		try {
			for (DTPConnectionProfileFactory factory : this.connectionProfileFactories.values()) {
				try {
					factory.stop();
				} catch (Throwable ex) {
					this.logError(ex);  // keep going
				}
			}
			this.connectionProfileFactories.clear();
		} finally {
			super.stop_();
		}
	}


	// ********** connection profile factories **********

	public synchronized DTPConnectionProfileFactory getConnectionProfileFactory(IWorkspace workspace) {
		DTPConnectionProfileFactory factory = this.connectionProfileFactories.get(workspace);
		if (this.isActive() && (factory == null)) {
			factory = this.buildConnectionProfileFactory(workspace);
	        factory.start();
	        this.connectionProfileFactories.put(workspace, factory);
		}
		return factory;
	}

	private DTPConnectionProfileFactory buildConnectionProfileFactory(IWorkspace workspace) {
		return new DTPConnectionProfileFactory(workspace);
	}
}
