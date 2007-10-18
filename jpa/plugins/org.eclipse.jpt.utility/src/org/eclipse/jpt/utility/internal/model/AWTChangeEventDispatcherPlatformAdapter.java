/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model;

import java.awt.EventQueue;
import java.io.Serializable;

/**
 * AWT-specific implementation of ChangeEventDispatcher.PlatformAdapter.
 */
public class AWTChangeEventDispatcherPlatformAdapter
	implements UIChangeEventDispatcher.PlatformAdapter, Serializable
{
	// singleton
	private static final UIChangeEventDispatcher.PlatformAdapter INSTANCE = new AWTChangeEventDispatcherPlatformAdapter();

	private static final ChangeEventDispatcher DISPATCHER = new UIChangeEventDispatcher(INSTANCE);

	private static final long serialVersionUID = 1L;


	/**
	 * Return the singleton.
	 */
	public static UIChangeEventDispatcher.PlatformAdapter instance() {
		return INSTANCE;
	}

	/**
	 * Return an AWT change event dispatcher.
	 */
	public static ChangeEventDispatcher dispatcher() {
		return DISPATCHER;
	}

	/**
	 * Ensure single instance.
	 */
	private AWTChangeEventDispatcherPlatformAdapter() {
		super();
	}

	/**
	 * AWT check
	 */
	public boolean currentThreadIsUIThread() {
		return EventQueue.isDispatchThread();
	}

	/**
	 * EventQueue.invokeLater(Runnable) seems to work OK;
	 * but using #invokeAndWait() can somtimes make things
	 * more predictable when debugging.
	 */
	public void executeOnUIThread(Runnable r) {
		EventQueue.invokeLater(r);
//		try {
//			EventQueue.invokeAndWait(r);
//		} catch (InterruptedException ex) {
//			throw new RuntimeException(ex);
//		} catch (java.lang.reflect.InvocationTargetException ex) {
//			throw new RuntimeException(ex);
//		}
	}

	/**
	 * Serializable singleton support
	 */
	private Object readResolve() {
		return instance();
	}

}
