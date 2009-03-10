/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model;

import java.util.EventListener;

import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.model.Model;

/**
 * This change support class will notify listeners whenever one of the source's
 * aspects has changed. Only the aspect name is passed to the listener; no
 * event is generated. This allows the listeners to delegate to the change
 * support object verification that an aspect as actually changed. This is
 * useful for simple things like setting dirty flags, blanket validation, and
 * blanket sychronization; i.e. things that might be interested in the *name*
 * of the aspect that changed but not so much *how* the aspect changed.
 */
public class CallbackChangeSupport
	extends ChangeSupport
{
	protected final ListenerList<Listener> listenerList;
	private static final long serialVersionUID = 1L;


	public CallbackChangeSupport(Model source, Listener listener) {
		this(source);
		this.listenerList.add(listener);
	}

	public CallbackChangeSupport(Model source) {
		super(source);
		this.listenerList = new ListenerList<Listener>(Listener.class);
	}

	@Override
	protected void sourceChanged(String aspectName) {
		super.sourceChanged(aspectName);
		for (Listener listener : this.listenerList.getListeners()) {
			listener.aspectChanged(aspectName);
		}
	}

	public void addListener(Listener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerList.remove(listener);
	}


	// ********** listener interface **********

	/**
	 * Listener that will be notified of any aspect changes.
	 */
	public interface Listener extends EventListener {

		/**
		 * The specified aspect changed.
		 */
		void aspectChanged(String aspectName);

	}

}
