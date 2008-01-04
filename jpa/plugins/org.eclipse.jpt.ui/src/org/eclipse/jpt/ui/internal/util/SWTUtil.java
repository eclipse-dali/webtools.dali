/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.ui.internal.util;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * A suite of utility methods related to the user interface.
 *
 * @version 2.0
 * @since 1.0
 */
public class SWTUtil {

	/**
	 * Causes the <code>run()</code> method of the given runnable to be invoked
	 * by the user-interface thread at the next reasonable opportunity. The caller
	 * of this method continues to run in parallel, and is not notified when the
	 * runnable has completed.
	 *
	 * @param runnable Code to run on the user-interface thread
	 * @exception org.eclipse.swt.SWTException
	 * <ul>
	 * <li>ERROR_DEVICE_DISPOSED - if the receiver has been disposed</li>
	 * </ul>
	 * @see #syncExec
	 */
	public static void asyncExec(Runnable runnable) {
		getStandardDisplay().asyncExec(runnable);
	}

	/**
	 * Convenience method for getting the current shell. If the current thread is
	 * not the UI thread, then an invalid thread access exception will be thrown.
	 *
	 * @return The shell, never <code>null</code>
	 */
	public static Shell getShell() {

		// Retrieve the active shell, which can be the shell from any window
		Shell shell = getStandardDisplay().getActiveShell();

		// No shell could be found, revert back to the active workbench window
		if (shell == null) {
			shell = getWorkbench().getActiveWorkbenchWindow().getShell();
		}

		// Make sure it's never null
		if (shell == null) {
			shell = new Shell(getStandardDisplay().getActiveShell());
		}

		return shell;
	}

	/**
	 * Returns the standard display to be used. The method first checks, if the
	 * thread calling this method has an associated display. If so, this display
	 * is returned. Otherwise the method returns the default display.
	 *
	 * @return The current display if not <code>null</code> otherwise the default
	 * display is returned
	 */
	public static Display getStandardDisplay()
	{
		Display display = Display.getCurrent();

		if (display == null) {
			display = Display.getDefault();
		}

		return display;
	}

	public static int getTableHeightHint(Table table, int rows) {
		if (table.getFont().equals(JFaceResources.getDefaultFont()))
			table.setFont(JFaceResources.getDialogFont());
		int result= table.getItemHeight() * rows + table.getHeaderHeight();
		if (table.getLinesVisible())
			result+= table.getGridLineWidth() * (rows - 1);
		return result;
	}

   /**
	 * Returns the Platform UI workbench.
	 *
	 * @return The workbench for this plug-in
	 */
	public static IWorkbench getWorkbench() {
		return PlatformUI.getWorkbench();
	}

	/**
	 * Causes the <code>run()</code> method of the given runnable to be invoked
	 * by the user-interface thread at the next reasonable opportunity. The
	 * thread which calls this method is suspended until the runnable completes.
	 *
	 * @param runnable code to run on the user-interface thread.
	 * @see #asyncExec
	 */
	public static void syncExec(Runnable runnable) {
		getStandardDisplay().syncExec(runnable);
	}

	/**
	 * Determines if the current thread is the UI event thread.
	 *
	 * @return <code>true</code> if it's the UI event thread, <code>false</code>
	 * otherwise
	 */
	public static boolean uiThread() {
		return getStandardDisplay().getThread() == Thread.currentThread();
	}

	/**
	 * Determines if the current thread is the UI event thread by using the
	 * thread from which the given viewer's display was instantiated.
	 *
	 * @param viewer The viewer used to determine if the current thread
	 * is the UI event thread
	 * @return <code>true</code> if the current thread is the UI event thread;
	 * <code>false</code> otherwise
	 */
	public static boolean uiThread(Viewer viewer) {
		return uiThread(viewer.getControl());
	}

	/**
	 * Determines if the current thread is the UI event thread by using the
	 * thread from which the given widget's display was instantiated.
	 *
	 * @param widget The widget used to determine if the current thread
	 * is the UI event thread
	 * @return <code>true</code> if the current thread is the UI event thread;
	 * <code>false</code> otherwise
	 */
	public static boolean uiThread(Widget widget) {
		return widget.getDisplay().getThread() == Thread.currentThread();
	}
}