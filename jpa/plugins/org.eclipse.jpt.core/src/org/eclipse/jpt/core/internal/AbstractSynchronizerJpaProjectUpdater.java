/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.synchronizers.CallbackSynchronizer;

/**
 * Adapt the "callback synchronizer" interface to the JPA project "updater"
 * interface.
 * <p>
 * Subclasses are to build the appropriate callback synchronizer.
 */
public abstract class AbstractSynchronizerJpaProjectUpdater
	implements JpaProject.Updater
{
	protected final JpaProject jpaProject;
	protected final CallbackSynchronizer synchronizer;


	protected AbstractSynchronizerJpaProjectUpdater(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
		this.synchronizer = this.buildSynchronizer();
		this.synchronizer.addListener(this.buildCallbackSynchronizerListener());
	}

	protected abstract CallbackSynchronizer buildSynchronizer();

	protected CallbackSynchronizer.Listener buildCallbackSynchronizerListener() {
		return new CallbackSynchronizer.Listener() {
			public void synchronizationQuiesced(CallbackSynchronizer s) {
				AbstractSynchronizerJpaProjectUpdater.this.jpaProject.updateQuiesced();
			}
		};
	}


	// ********** JpaProject.Updater implementation **********

	public void start() {
		this.synchronizer.start();
	}

	// recursion: we come back here if IJpaProject#update() is called again, during the "update"
	public void update() {
		this.synchronizer.synchronize();
	}

	public void stop() {
		this.synchronizer.stop();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.synchronizer);
	}

}
