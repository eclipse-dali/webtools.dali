/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.listeners;

import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another property change listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited. (Typically, the adapter(s) between a <em>property</em> and
 * its corresponding UI widget are read-write; as opposed to the adapter(s)
 * between a <em>collection</em> (or <em>list</em>) and its UI widget, which
 * is read-only.)
 */
public class SWTPropertyChangeListenerWrapper
	implements PropertyChangeListener
{
	private final PropertyChangeListener listener;

	public SWTPropertyChangeListenerWrapper(PropertyChangeListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void propertyChanged(PropertyChangeEvent event) {
		if (this.isExecutingOnUIThread()) {
			this.propertyChanged_(event);
		} else {
			this.executeOnUIThread(this.buildPropertyChangedRunnable(event));
		}
	}

	private Runnable buildPropertyChangedRunnable(final PropertyChangeEvent event) {
		return new Runnable() {
			public void run() {
				SWTPropertyChangeListenerWrapper.this.propertyChanged_(event);
			}
			@Override
			public String toString() {
				return "property changed runnable"; //$NON-NLS-1$
			}
		};
	}

	private boolean isExecutingOnUIThread() {
		return Display.getCurrent() != null;
	}

	/**
	 * {@link Display#asyncExec(Runnable)} seems to work OK;
	 * but using {@link Display#syncExec(Runnable)} can somtimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void executeOnUIThread(Runnable r) {
		Display.getDefault().asyncExec(r);
//		Display.getDefault().syncExec(r);
	}

	void propertyChanged_(PropertyChangeEvent event) {
		this.listener.propertyChanged(event);
	}

	@Override
	public String toString() {
		return "SWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}

}
