/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import java.io.Serializable;

import org.eclipse.jpt.utility.internal.model.ChangeEventDispatcher;
import org.eclipse.jpt.utility.internal.model.UIChangeEventDispatcher;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * SWT-specific implementation of ChangeEventDispatcher.PlatformAdapter.
 */
public class SWTChangeEventDispatcherPlatformAdapter
	implements UIChangeEventDispatcher.PlatformAdapter, Serializable
{
	// singleton
	private static final UIChangeEventDispatcher.PlatformAdapter INSTANCE = new SWTChangeEventDispatcherPlatformAdapter();

	private static final ChangeEventDispatcher DISPATCHER = new UIChangeEventDispatcher(INSTANCE);

	private static final long serialVersionUID = 1L;


	/**
	 * Return the singleton.
	 */
	public static UIChangeEventDispatcher.PlatformAdapter instance() {
		return INSTANCE;
	}

	/**
	 * Return an SWT change event dispatcher.
	 */
	public static ChangeEventDispatcher dispatcher() {
		return DISPATCHER;
	}

	/**
	 * Ensure single instance.
	 */
	private SWTChangeEventDispatcherPlatformAdapter() {
		super();
	}

	/**
	 * SWT check
	 */
	public boolean currentThreadIsUIThread() {
		return Thread.currentThread() == this.display().getThread();
	}

	/**
	 * Display.asyncExec(Runnable) seems to work OK;
	 * but using #syncExec() can somtimes make things
	 * more predictable when debugging.
	 */
	public void executeOnUIThread(Runnable r) {
		this.display().asyncExec(r);
//		this.display().syncExec(r);
	}

	private Display display() {
		return PlatformUI.getWorkbench().getDisplay();
	}

	/**
	 * Serializable singleton support
	 */
	private Object readResolve() {
		return instance();
	}

}
