/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.util;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * A suite of utility methods related to the user interface.
 *
 * @version 2.0
 * @since 1.0
 */
public class SWTUtil {

	/**
	 * @see Display#asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 * @see #execute(Runnable)
	 */
	public static void asyncExec(Runnable runnable) {
		getDisplay().asyncExec(runnable);
	}

	/**
	 * @see Display#syncExec(Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 * @see #execute(Runnable)
	 */
	public static void syncExec(Runnable runnable) {
		getDisplay().syncExec(runnable);
	}

	/**
	 * Use the standard delay.
	 * @see OpenStrategy#getPostSelectionDelay()
	 * @see #timerExec(int, Runnable)
	 */
	public static void delayedExec(Runnable runnable) {
		timerExec(OpenStrategy.getPostSelectionDelay(), runnable);
	}

	/**
	 * @see Display#timerExec(int, Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #execute(Runnable)
	 */
	public static void timerExec(int milliseconds, Runnable runnable) {
		getDisplay().timerExec(milliseconds, runnable);
	}

	/**
	 * Execute the specified runnable if the current thread is the UI thread;
	 * otherwise asynchrounously dispatch the runnable to the UI thread,
	 * returning immediately. This is useful for event handlers when it is not
	 * obviously whether the events are fired on the UI thread.
	 * 
	 * @see Display#asyncExec(Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 */
	public static void execute(Runnable runnable) {
		Display display = Display.getCurrent();
		if (display != null) {
			// the current thread is the UI thread
			runnable.run();
		} else {
			Display.getDefault().asyncExec(runnable);
		}
	}

	/**
	 * Execute the specified runnable if the current thread is the specified
	 * viewer's thread;
	 * otherwise asynchrounously dispatch the runnable to the viewer's thread,
	 * returning immediately. This is useful for event handlers when it is not
	 * obviously whether the events are fired on the viewer's thread.
	 * 
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
	 * 
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
	 * 
	 * @see #execute(Runnable)
	 * @see Display#asyncExec(Runnable)
	 * @see #asyncExec(Runnable)
	 * @see #syncExec(Runnable)
	 * @see #timerExec(int, Runnable)
	 */
	public static void execute(Display display, Runnable runnable) {
		if (display.getThread() == Thread.currentThread()) {
			// the current thread is the display's thread
			runnable.run();
		} else {
			display.asyncExec(runnable);
		}
	}

	/**
	 * Convenience method for getting the current shell. If the current thread is
	 * not the UI thread, then an invalid thread access exception will be thrown.
	 *
	 * @return The shell, never <code>null</code>
	 */
	public static Shell getShell() {
		// Retrieve the active shell, which can be the shell from any window
		Shell shell = getDisplay().getActiveShell();
		if (shell != null) {
			return shell;
		}

		// No shell could be found, revert back to the active workbench window
		shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		return (shell != null) ? shell : new Shell();
	}

	/**
	 * Return the most appropriate {@link Display display}; i.e. return the
	 * {@link Display#getCurrent() display associated with the current thread}
	 * if it is present; otherwise return the
	 * {@link Display#getDefault() default display}.
	 */
	public static Display getDisplay() {
		Display display = Display.getCurrent();
		return (display != null) ? display : Display.getDefault();
	}

	/**
	 * Center the specified shell within its display.
	 * <p>
	 * <strong>NB:</strong> This will not look too good on a dual monitor system.
	 */
	public static void center(Shell shell) {
		shell.setBounds(calculateCenteredBounds(shell.getBounds(), shell.getDisplay().getBounds()));
	}

	/**
	 * Center the specified shell within the specified parent shell.
	 */
	public static void center(Shell shell, Shell parentShell) {
		shell.setBounds(calculateCenteredBounds(shell.getBounds(), parentShell.getBounds()));
	}

	/**
	 * Calculate the bounds (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Rectangle calculateCenteredBounds(Rectangle rectangle1, Rectangle rectangle2) {
		return calculateCenteredBounds(rectangle1.width, rectangle1.height, rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
	}

	/**
	 * Calculate the bounds (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Rectangle calculateCenteredBounds(Point size1, Point size2) {
		return calculateCenteredBounds(size1.x, size1.y, size2.x, size2.y);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Rectangle calculateCenteredBounds(int width1, int height1, int width2, int height2) {
		return calculateCenteredBounds(width1, height1, 0, 0, width2, height2);
	}


	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Rectangle calculateCenteredBounds(int width1, int height1, int x2, int y2, int width2, int height2) {
		return new Rectangle(x2 + ((width2 - width1) / 2), y2 + ((height2 - height1) / 2), width1, height1);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Point calculateCenteredPosition(Rectangle rectangle1, Rectangle rectangle2) {
		return calculateCenteredPosition(rectangle1.width, rectangle1.height, rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Point calculateCenteredPosition(Point size1, Point size2) {
		return calculateCenteredPosition(size1.x, size1.y, size2.x, size2.y);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Point calculateCenteredPosition(int width1, int height1, int width2, int height2) {
		return calculateCenteredPosition(width1, height1, 0, 0, width2, height2);
	}

	/**
	 * Calculate the point (within the second specified rectangle's coordinate
	 * system) that would center the first specified rectangle
	 * with respect to the second specified rectangle.
	 */
	public static Point calculateCenteredPosition(int width1, int height1, int x2, int y2, int width2, int height2) {
		return new Point(x2 + ((width2 - width1) / 2), y2 + ((height2 - height1) / 2));
	}

	/**
	 * Tweaks the given <code>Combo</code> to remove the default value when the
	 * widget receives the focus and to show the default when the widget loses
	 * the focus.
	 *
	 * @param combo The widget having a default value that is always at the
	 * beginning of the list
	 */
	public static void attachDefaultValueHandler(Combo combo) {
		ComboHandler handler = new ComboHandler();
		combo.addFocusListener(handler);
		combo.addModifyListener(handler);
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
	 * Relays out the parents of the <code>Control</code>. This was taken from
	 * the widget <code>Section</code>.
	 *
	 * @param pane The pane to revalidate as well as its parents
	 */
	public static void reflow(Composite pane) {

		for (Composite composite = pane; composite != null; ) {
			composite.setRedraw(false);
			composite = composite.getParent();

			if (composite instanceof ScrolledForm || composite instanceof Shell) {
				break;
			}
		}

		for (Composite composite = pane; composite != null; ) {
			composite.layout(true);
			composite = composite.getParent();

			if (composite instanceof ScrolledForm) {
				((ScrolledForm) composite).reflow(true);
				break;
			}
		}

		for (Composite composite = pane; composite != null; ) {
			composite.setRedraw(true);
			composite = composite.getParent();

			if (composite instanceof ScrolledForm || composite instanceof Shell) {
				break;
			}
		}
	}


	/**
	 * Determines if the current thread is the UI event thread.
	 *
	 * @return <code>true</code> if it's the UI event thread, <code>false</code>
	 * otherwise
	 */
	public static boolean uiThread() {
		return Display.getCurrent() != null;
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


	/**
	 * This handler is responsible for removing the default value when the combo
	 * has the focus or when the selected item is the default value and to select
	 * it when the combo loses the focus.
	 */
	private static class ComboHandler implements ModifyListener,
	                                             FocusListener {

		public void focusGained(FocusEvent e) {
			Combo combo = (Combo) e.widget;

			if (combo.getSelectionIndex() == 0) {	
				// The text selection has to be changed outside of the context of this
				// listener otherwise the combo won't update because it's currently
				// notifying its listeners
				asyncExec(new SelectText(combo));
			}
		}

		public void focusLost(FocusEvent e) {
			//do nothing
		}

		public void modifyText(ModifyEvent e) {

			Combo combo = (Combo) e.widget;

			if (combo.isFocusControl() &&
			    combo.getSelectionIndex() == 0) {

				// The text has to be changed outside of the context of this
				// listener otherwise the combo won't update because it's currently
				// notifying its listeners
				asyncExec(new ModifyText(combo));
			}
		}

		private class ModifyText implements Runnable {
			private final Combo combo;

			public ModifyText(Combo combo) {
				super();
				this.combo = combo;
			}

			public void run() {
				if (this.combo.isDisposed()) {
					return;
				}
				String text = this.combo.getText();

				if (text.length() == 0) {
					text = this.combo.getItem(0);
					this.combo.setText(text);
				}

				this.combo.setSelection(new Point(0, text.length()));
			}
		}

		private class SelectText implements Runnable {
			private final Combo combo;

			public SelectText(Combo combo) {
				super();
				this.combo = combo;
			}

			public void run() {
				if (this.combo.isDisposed()) {
					return;
				}
				String text = this.combo.getText();
				this.combo.setSelection(new Point(0, text.length()));
			}
		}
	}
}