/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class SelectionManagerFactory
{
	private static SelectionManagerFactory INSTANCE;

	private static Object MUTEX = new Object();


	/**
	 * Each <code>IWorkbenchWindow</code> has its own <code>JpaSelectionManager</code>
	 * to track the selection events in the <code>IWorkbenchWindow</code>. All
	 * <code>ISelectionListener</code>s in the same <code>IWorkbenchWindow</code>
	 * share the same <code>JpaSelectionManager</code>.
	 *
	 * @return The <code>JpaSelectionManager</code> associated with the current
	 * <code>IWorkbenchWindow</code>
	 */
	public static JpaSelectionManager getSelectionManager(IWorkbenchWindow window) {
		if (INSTANCE == null) {
			// this is thread safe for now. you never know whats comming
			synchronized (MUTEX) {
				if(INSTANCE == null) {
					INSTANCE = new SelectionManagerFactory();
					// if we do the init inside the constructor we end up in a loop
					// because the addWindowListener(this) does a callback to us
					INSTANCE.init();
				}
			}
		}
		return INSTANCE.internalGetSelectionManager(window);
	}


	private Map<IWorkbenchWindow, DefaultJpaSelectionManager> managers;

	private WindowListener windowListener;


	private SelectionManagerFactory() {
		managers = new HashMap<IWorkbenchWindow, DefaultJpaSelectionManager>();
		windowListener = new WindowListener();
	}

	private void init() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWindowListener(windowListener);
	}

	/**
	 * Returns the JpaSelectionManager for the IWorkbenchWindow.
	 * Creates a new one if none exists yet.
	 */
	private DefaultJpaSelectionManager internalGetSelectionManager(IWorkbenchWindow window) {
		if (window == null) {
			throw new IllegalArgumentException("The IWorkbenchWindow cannot be null");
		}

		if (! managers.containsKey(window)) {
			DefaultJpaSelectionManager manager = new DefaultJpaSelectionManager();
			this.managers.put(window, manager);
			manager.init(window);
		}

		return managers.get(window);
	}


	private class WindowListener implements IWindowListener
	{
		public void windowOpened(IWorkbenchWindow aWindow) {}

		public void windowClosed(IWorkbenchWindow aWindow) {
			DefaultJpaSelectionManager manager = internalGetSelectionManager(aWindow);
			manager.dispose();
			managers.remove(aWindow);
		}

		public void windowActivated(IWorkbenchWindow aWindow) {}

		public void windowDeactivated(IWorkbenchWindow aWindow) {}
	}
}
