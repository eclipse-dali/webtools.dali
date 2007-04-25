/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
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
	 * Each <code>IWorkbenchWindow</code> has its own <code>SelectionManager</code> 
	 * to track the selection events in the <code>IWorkbenchWindow</code>. All 
	 * <code>ISelectionListener</code>s in the same <code>IWorkbenchWindow</code> 
	 * share the same <code>SelectionManager</code>.
	 * 
	 * @return The <code>SelectionManager</code> associated with the current 
	 * <code>IWorkbenchWindow</code>
	 */
	public static ISelectionManager getSelectionManager(IWorkbenchWindow window) {
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
	
	
	private Map managers;
	
	private WindowListener windowListener;
	
	
	private SelectionManagerFactory() {
		managers = new HashMap();
		windowListener = new WindowListener();
	}
	
	private void init() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWindowListener(windowListener);
	}
	
	/**
	 * Returns the SelectionManager for the IWorkbenchWindow.
	 * Creates a new one if none exists yet.
	 */
	private SelectionManager internalGetSelectionManager(IWorkbenchWindow window) {
		if (window == null) {
			throw new IllegalArgumentException(window.toString());
		}
		
		if (! managers.containsKey(window)) {
			SelectionManager manager = new SelectionManager();
			this.managers.put(window, manager);
			manager.init(window);
		}
		
		return (SelectionManager) managers.get(window);
	}
	
	
	private class WindowListener implements IWindowListener
	{
		/* @see IWindowListener#windowOpened(IWorkbenchWindow) */
		public void windowOpened(IWorkbenchWindow aWindow) {}
		
		/* @see IWindowListener#windowClosed(IWorkbenchWindow) */
		public void windowClosed(IWorkbenchWindow aWindow) {
			SelectionManager manager = internalGetSelectionManager(aWindow);
			manager.dispose();
			managers.remove(aWindow);
		}
		
		/* @see IWindowListener#windowActivated(IWorkbenchWindow) */
		public void windowActivated(IWorkbenchWindow aWindow) {}
		
		/* @see IWindowListener#windowDeactivated(IWorkbenchWindow) */
		public void windowDeactivated(IWorkbenchWindow aWindow) {}
	}
}
