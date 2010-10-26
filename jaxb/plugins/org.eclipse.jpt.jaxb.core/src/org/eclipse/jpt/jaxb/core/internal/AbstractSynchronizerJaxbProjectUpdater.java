/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.synchronizers.CallbackSynchronizer;

/**
 * Adapt the "callback synchronizer" interface to the JAXB project "updater"
 * interface.
 * <p>
 * Subclasses are to build the appropriate callback synchronizer.
 */
public abstract class AbstractSynchronizerJaxbProjectUpdater
	implements JaxbProject.Updater
{
	protected final JaxbProject jaxbProject;
	protected final CallbackSynchronizer synchronizer;


	protected AbstractSynchronizerJaxbProjectUpdater(JaxbProject jaxbProject) {
		super();
		this.jaxbProject = jaxbProject;
		this.synchronizer = this.buildSynchronizer();
		this.synchronizer.addListener(this.buildCallbackSynchronizerListener());
	}

	protected abstract CallbackSynchronizer buildSynchronizer();

	protected CallbackSynchronizer.Listener buildCallbackSynchronizerListener() {
		return new CallbackSynchronizer.Listener() {
			public void synchronizationQuiesced(CallbackSynchronizer s) {
				AbstractSynchronizerJaxbProjectUpdater.this.jaxbProject.updateQuiesced();
			}
		};
	}


	// ********** JaxbProject implementation **********

	public void start() {
		this.synchronizer.start();
	}

	// recursion: we come back here if JaxbProject#update() is called again, during the "update"
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
