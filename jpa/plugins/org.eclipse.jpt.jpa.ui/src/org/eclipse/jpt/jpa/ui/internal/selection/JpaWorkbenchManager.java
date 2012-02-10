/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.selection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.jpt.jpa.ui.selection.JpaViewManager;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Maintain a collection of
 * {@link JpaSelectionManager JPA selection managers}
 * keyed by {@link IWorkbenchWindow workbench window}.
 * Forward the selection to the manager for the active window.
 * 
 * @see WorkbenchWindowAdapterFactory
 */
class JpaWorkbenchManager
	implements JpaSelectionManager
{
	/**
	 * The manager's workbench.
	 */
	private final IWorkbench workbench;

	/**
	 * Map workbench windows to JPA managers.
	 * This is lazily populated and cleaned up as necessary.
	 */
	private final Hashtable<IWorkbenchWindow, JpaWindowManager> windowManagers = new Hashtable<IWorkbenchWindow, JpaWindowManager>();


	/**
	 * @see #forWorkbench(IWorkbench)
	 * @see #getPageManager(IViewPart)
	 */
	private JpaWorkbenchManager(IWorkbench workbench) {
		super();
		this.workbench = workbench;
	}


	// ********** selection **********

	/**
	 * Forward to the manager for the workbench's active window.
	 */
	public void setSelection(JpaStructureNode selection) {
		this.getWindowManager(this.workbench.getActiveWorkbenchWindow()).setSelection(selection);
	}


	// ********** window managers **********

	/**
	 * Return the JPA selection manager for the specified
	 * workbench window. Return a "null" manager if the specified window
	 * is <code>null</code> or contains no JPA views.
	 * 
	 * @see #getPageManager_(IViewPart)
	 */
	private JpaSelectionManager getWindowManager(IWorkbenchWindow window) {
		return (window == null) ?
				JpaSelectionManager.Null.instance() :
				this.getWindowManager_(window);
	}

	private JpaSelectionManager getWindowManager_(IWorkbenchWindow window) {
		JpaWindowManager manager = this.windowManagers.get(window);
		return (manager != null) ? manager : JpaSelectionManager.Null.instance();
	}

	/**
	 * Return the page manager for the specified view.
	 * Construct a new manager for the view's window and page if necessary.
	 * 
	 * @see #getWindowManager(IWorkbenchWindow)
	 */
	private JpaViewManager.PageManager getPageManager_(IViewPart view) {
		return this.getWindowManager(view).getPageManager(view);
	}

	/**
	 * <strong>NB:</strong> May trigger construction of window manager.
	 */
	private JpaWindowManager getWindowManager(IViewPart view) {
		IWorkbenchWindow window = view.getSite().getWorkbenchWindow();
		synchronized (this.windowManagers) {
			JpaWindowManager manager = this.windowManagers.get(window);
			if (manager == null) {
				debug("add window manager:", window); //$NON-NLS-1$
				manager = new JpaWindowManager(this, window);
				this.windowManagers.put(window, manager);
			}
			return manager;
		}
	}

	/**
	 * @see JpaWindowManager#dispose()
	 */
	void removeWindowManager(IWorkbenchWindow window) {
		synchronized (this.windowManagers) {
			debug("remove window manager:", window); //$NON-NLS-1$
			this.windowManagers.remove(window);
			if (this.windowManagers.isEmpty()) {
				this.dispose();
			}
		}
	}


	// ********** misc **********

	private void dispose() {
		debug("remove workbench manager:", this.workbench); //$NON-NLS-1$
		WORKBENCH_MANAGERS.remove(this.workbench);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.workbench);
	}


	// ********** static cache **********

	/**
	 * Probably only a single manager in this cache....
	 */
	private static final Hashtable<IWorkbench, JpaWorkbenchManager> WORKBENCH_MANAGERS = new Hashtable<IWorkbench, JpaWorkbenchManager>();

	/**
	 * Return a <em>null</em> manager if a manager does not exist.
	 * @see WorkbenchAdapterFactory
	 */
	static JpaSelectionManager forWorkbench(IWorkbench workbench) {
		return (workbench == null) ?
			JpaSelectionManager.Null.instance() :
			forWorkbench_(workbench);
	}

	private static JpaSelectionManager forWorkbench_(IWorkbench workbench) {
		JpaWorkbenchManager manager = WORKBENCH_MANAGERS.get(workbench);
		return (manager != null) ? manager : JpaSelectionManager.Null.instance();
	}

	/**
	 * Return a <em>null</em> manager if a manager does not exist.
	 * @see WorkbenchWindowAdapterFactory
	 */
	static JpaSelectionManager forWindow(IWorkbenchWindow window) {
		return (window == null) ?
			JpaSelectionManager.Null.instance() :
			forWindow_(window);
	}

	private static JpaSelectionManager forWindow_(IWorkbenchWindow window) {
		JpaWorkbenchManager manager = WORKBENCH_MANAGERS.get(window.getWorkbench());
		return (manager == null) ?
				JpaSelectionManager.Null.instance() :
				manager.getWindowManager_(window);
	}

	/**
	 * Construct a new manager if a manager does not exist.
	 * @see ViewPartAdapterFactory
	 */
	static JpaViewManager.PageManager getPageManager(IViewPart view) {
		if (view == null) {
			throw new NullPointerException();
		}
		return getWorkbenchManager(view).getPageManager_(view);
	}

	/**
	 * <strong>NB:</strong> May trigger construction of workbench manager.
	 */
	private static JpaWorkbenchManager getWorkbenchManager(IViewPart view) {
		IWorkbench workbench = view.getSite().getWorkbenchWindow().getWorkbench();
		synchronized (WORKBENCH_MANAGERS) {
			JpaWorkbenchManager manager = WORKBENCH_MANAGERS.get(workbench);
			if (manager == null) {
				debug("add workbench manager:", workbench); //$NON-NLS-1$
				manager = new JpaWorkbenchManager(workbench);
				WORKBENCH_MANAGERS.put(workbench, manager);
			}
			return manager;
		}
	}


	// ********** DEBUG **********

	private static final boolean DEBUG = false;

	static void debug(String message) {
		debug(message, null);
	}

	static void debug(String message, Object object) {
		debug(message, object, null);
	}

	static void debug(String message, Object object, Object additionalInfo) {
		if (DEBUG) {
			// lock System.out so the strings are printed out contiguously
			synchronized (System.out) {
				debug_(message, object, additionalInfo);
			}
		}
	}

	private static void debug_(String message, Object object, Object additionalInfo) {
		System.out.print(buildTimestamp());
		System.out.print(" "); //$NON-NLS-1$
		System.out.print(Thread.currentThread().getName());
		System.out.print(": "); //$NON-NLS-1$
		System.out.print(message);
		if (object != null) {
			System.out.print(" "); //$NON-NLS-1$
			System.out.print(object);
			if (additionalInfo != null) {
				System.out.print(" ("); //$NON-NLS-1$
				System.out.print(additionalInfo);
				System.out.print(")"); //$NON-NLS-1$
			}
		}
		System.out.println();
	}

	private static synchronized String buildTimestamp() {
		return DATE_FORMAT.format(new Date());
	}
	private static final String DATE_FORMAT_PATTERN = "yyyy.MM.dd HH:mm:ss.SSS"; //$NON-NLS-1$
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN);
}
