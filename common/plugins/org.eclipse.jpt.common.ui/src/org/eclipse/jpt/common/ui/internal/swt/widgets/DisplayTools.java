/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.widgets;

import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;

/**
 * {@link Display} convenience methods.
 */
public final class DisplayTools {

	// ********** UI thread execution **********

	/**
	 * <strong>NB:</strong> The runnable will not be executed if the workbench
	 * is not running (i.e. the runnable is treated as ephemeral, and not
	 * executing it, probably during workbench shutdown, is not a problem because
	 * it is UI-related and the UI is gone).
	 * @see Display#asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 * @see #execute(Runnable)
	 */
	public static void asyncExec(Runnable runnable) {
		Display display = getDisplay();
		if (display != null) {
			display.asyncExec(runnable);
		}
	}

	/**
	 * <strong>NB:</strong> The runnable will not be executed if the workbench
	 * is not running (i.e. the runnable is treated as ephemeral, and not
	 * executing it, probably during workbench shutdown, is not a problem because
	 * it is UI-related and the UI is gone).
	 * @see Display#syncExec(Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 * @see #execute(Runnable)
	 */
	public static void syncExec(Runnable runnable) {
		Display display = getDisplay();
		if (display != null) {
			display.syncExec(runnable);
		}
	}

	/**
	 * Use the standard delay.
	 * <p>
	 * <strong>NB:</strong> The runnable will not be executed if the workbench
	 * is not running (i.e. the runnable is treated as ephemeral, and not
	 * executing it, probably during workbench shutdown, is not a problem because
	 * it is UI-related and the UI is gone).
	 * @see OpenStrategy#getPostSelectionDelay()
	 * @see #timerExec(int, Runnable)
	 */
	public static void delayedExec(Runnable runnable) {
		timerExec(OpenStrategy.getPostSelectionDelay(), runnable);
	}

	/**
	 * <strong>NB:</strong> The runnable will not be executed if the workbench
	 * is not running (i.e. the runnable is treated as ephemeral, and not
	 * executing it, probably during workbench shutdown, is not a problem because
	 * it is UI-related and the UI is gone).
	 * @see Display#timerExec(int, Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #execute(Runnable)
	 */
	public static void timerExec(int milliseconds, Runnable runnable) {
		Display display = getDisplay();
		if (display != null) {
			display.timerExec(milliseconds, runnable);
		}
	}

	/**
	 * Execute the specified runnable if the current thread is the UI thread;
	 * otherwise asynchrounously dispatch the runnable to the UI thread,
	 * returning immediately. This is useful for event handlers when it is not
	 * obvious whether the events are fired on the UI thread.
	 * <p>
	 * <strong>NB:</strong> The runnable will not be executed if the workbench
	 * is not running (i.e. the runnable is treated as ephemeral, and not
	 * executing it, probably during workbench shutdown, is not a problem because
	 * it is UI-related and the UI is gone).
	 * @see Display#asyncExec(Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 * @see IWorkbench#getDisplay()
	 */
	public static void execute(Runnable runnable) {
		Display display = Display.getCurrent();
		if (display != null) {
			// the current thread is the UI thread
			runnable.run();
		} else {
			display = WorkbenchTools.getDisplay();
			if (display != null) {
				display.asyncExec(runnable);
			}
		}
	}

	/**
	 * Execute the specified runnable if the current thread is the specified
	 * viewer's thread;
	 * otherwise asynchrounously dispatch the runnable to the viewer's thread,
	 * returning immediately. This is useful for event handlers when it is not
	 * obviously whether the events are fired on the viewer's thread.
	 * @see #execute(Runnable)
	 * @see Display#asyncExec(Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 */
	public static void execute(Viewer viewer, Runnable runnable) {
		execute(viewer.getControl(), runnable);
	}

	/**
	 * Execute the specified runnable if the current thread is the specified
	 * control's thread;
	 * otherwise asynchrounously dispatch the runnable to the control's thread,
	 * returning immediately. This is useful for event handlers when it is not
	 * obviously whether the events are fired on the control's thread.
	 * @see #execute(Runnable)
	 * @see Display#asyncExec(Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 */
	public static void execute(Control control, Runnable runnable) {
		execute(control.getDisplay(), runnable);
	}

	/**
	 * Execute the specified runnable if the current thread is the specified
	 * display's thread;
	 * otherwise asynchrounously dispatch the runnable to the display's thread,
	 * returning immediately. This is useful for event handlers when it is not
	 * obviously whether the events are fired on the display's thread.
	 * @see #execute(Runnable)
	 * @see Display#asyncExec(Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 */
	public static void execute(Display display, Runnable runnable) {
		if (display.getThread() == Thread.currentThread()) {
			// the current thread is the the UI thread
			runnable.run();
		} else {
			display.asyncExec(runnable);
		}
	}


	// ********** active UI components **********

	/**
	 * Return the current shell. Return <code>null</code> if there is none.
	 * @exception org.eclipse.swt.SWTException if not called from the UI thread
	 */
	public static Shell getShell() {
		Shell shell = getActiveShell();
		return (shell != null) ? shell : WorkbenchTools.getActiveShell();
	}

	/**
	 * Return the current "active" shell, which can be the shell from any
	 * window. Return <code>null</code> if there is no display.
	 * @see Display#getActiveShell()
	 * @exception org.eclipse.swt.SWTException if not called from the UI thread
	 */
	public static Shell getActiveShell() {
		// Retrieve the active shell, which can be the shell from any window
		Display display = getDisplay();
		return (display == null) ? null : display.getActiveShell();
	}

	/**
	 * Return the most appropriate {@link Display display}; i.e. return the
	 * {@link Display#getCurrent() display associated with the current thread}
	 * if it is present; otherwise return the
	 * {@link IWorkbench#getDisplay() Eclipse workbench
	 * display} (but, <em>explicitly</em>, not the
	 * {@link Display#getDefault() "default display"} -
	 * see the comment at {@link IWorkbench#getDisplay()}).
	 * Return <code>null</code> if the workbench is not running.
	 */
	public static Display getDisplay() {
		Display display = Display.getCurrent();
		return (display != null) ? display : WorkbenchTools.getDisplay();
	}


	// ********** UI thread check **********

	/**
	 * Check whether the current thread is a UI event thread.
	 * Throw an exception if it is not.
	 * @see #uiThread()
	 * @see Display#getCurrent()
	 */
	public static void checkUIThread() {
		checkUIThread(Display.getCurrent());
	}

	/**
	 * Check whether the current thread is the UI event thread associated
	 * with the specified viewer.
	 * @exception IllegalStateException if the current thread is not the UI
	 * thread associated with the specified viewer
	 * @exception NullPointerException if the specified viewer is disposed
	 * @see #uiThread(Viewer)
	 */
	public static void checkUIThread(Viewer viewer) {
		checkUIThread(viewer.getControl());
	}

	/**
	 * Check whether the current thread is the UI event thread associated
	 * with the specified widget.
	 * @exception IllegalStateException if the current thread is not the UI
	 * thread associated with the specified widget
	 * @exception NullPointerException if the specified widget is disposed
	 * @see #uiThread(Widget)
	 */
	public static void checkUIThread(Widget widget) {
		checkUIThread(widget.getDisplay());
	}

	/**
	 * Check whether the current thread is the UI event thread associated
	 * with the specified display.
	 * @exception IllegalStateException if the current thread is not the UI
	 * thread associated with the specified display
	 * @exception NullPointerException if the specified display is
	 * <code>null</code>
	 * @see #uiThread(Display)
	 */
	public static void checkUIThread(Display display) {
		if ( ! uiThread(display)) {
			throw new IllegalStateException("Invalid thread"); //$NON-NLS-1$
		}
	}

	/**
	 * Return whether the current thread is a UI event thread.
	 * @see #checkUIThread()
	 * @see Display#getCurrent()
	 */
	public static boolean uiThread() {
		return Display.getCurrent() != null;
	}

	/**
	 * Return whether the current thread is the UI event thread associated
	 * with the specified viewer.
	 * @exception NullPointerException if the specified viewer is disposed
	 * @see #checkUIThread(Viewer)
	 */
	public static boolean uiThread(Viewer viewer) {
		return uiThread(viewer.getControl());
	}

	/**
	 * Return whether the current thread is the UI event thread associated
	 * with the specified widget.
	 * @exception NullPointerException if the specified widget is disposed
	 * @see #checkUIThread(Widget)
	 */
	public static boolean uiThread(Widget widget) {
		return uiThread(widget.getDisplay());
	}

	/**
	 * Return whether the current thread is the UI event thread associated
	 * with the specified display.
	 * @exception NullPointerException if the specified display
	 * is <code>null</code>
	 * @see #checkUIThread(Display)
	 */
	public static boolean uiThread(Display display) {
		return display.getThread() == Thread.currentThread();
	}


	// ********** disabled constructor **********

	private DisplayTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
