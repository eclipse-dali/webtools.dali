/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.ModelTools;

/**
 * Provide support for {@link ISelectionChangedListener selection change
 * listeners} and {@link #fireSelectionChanged(ISelection) the safe firing of
 * events}.
 */
public abstract class AbstractSelectionProvider
	implements ISelectionProvider
{
	private final ExceptionHandler exceptionHandler;
	private final ListenerList<ISelectionChangedListener> listenerList = ModelTools.listenerList();


	protected AbstractSelectionProvider(ExceptionHandler exceptionHandler) {
		super();
		this.exceptionHandler = exceptionHandler;
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		this.listenerList.add(listener);
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		this.listenerList.remove(listener);
	}

	protected void fireSelectionChanged(ISelection selection) {
		SelectionChangedEvent event = new SelectionChangedEvent(this, selection);
		for (ISelectionChangedListener listener : this.listenerList) {
			this.fireSelectionChanged(listener, event);
        }
	}

	private void fireSelectionChanged(ISelectionChangedListener listener, SelectionChangedEvent event) {
		try {
			listener.selectionChanged(event);
		} catch (Throwable t) {
			this.exceptionHandler.handleException(t);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
