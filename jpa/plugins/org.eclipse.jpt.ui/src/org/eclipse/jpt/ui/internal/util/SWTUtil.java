/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.ui.internal.widgets.NullPostExecution;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * A suite of utility methods related to the user interface.
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
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
	 * Tweaks the given <code>CCombo</code> to remove the default value when the
	 * widget receives the focus and to show the default when the widget loses
	 * the focus.
	 *
	 * @param combo The widget having a default value that is always at the
	 * beginning of the list
	 */
	public static void attachDefaultValueHandler(CCombo combo) {
		CComboHandler handler = new CComboHandler();
		combo.addFocusListener(handler);
		combo.addModifyListener(handler);
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

	/**
	 * Retrieves the localized string from the given NLS class by creating the
	 * key. That key is the concatenation of the composite's short class name
	 * with the toString() of the given value separated by an underscore.
	 *
	 * @param nlsClass The NLS class used to retrieve the localized text
	 * @param compositeClass The class used for creating the key, its short class
	 * name is the beginning of the key
	 * @param value The value used to append its toString() to the generated key
	 * @return The localized text associated with the value
	 */
	public static String buildDisplayString(Class<?> nlsClass,
	                                        Class<?> compositeClass,
	                                        Object value) {

		StringBuilder sb = new StringBuilder();
		sb.append(ClassTools.shortNameFor(compositeClass));
		sb.append("_");
		sb.append(value.toString().toLowerCase());

		return (String) ClassTools.staticFieldValue(nlsClass, sb.toString());
	}

	/**
	 * Retrieves the localized string from the given NLS class by creating the
	 * key. That key is the concatenation of the composite's short class name
	 * with the toString() of the given value separated by an underscore.
	 *
	 * @param nlsClass The NLS class used to retrieve the localized text
	 * @param composite The object used to retrieve the short class name that is
	 * the beginning of the key
	 * @param value The value used to append its toString() to the generated key
	 * @return The localized text associated with the value
	 */
	public static final String buildDisplayString(Class<?> nlsClass,
	                                              Object composite,
	                                              Object value) {

		return buildDisplayString(nlsClass, composite.getClass(), value);
	}

	/**
	 * Creates the <code>Runnable</code> that will invoke the given
	 * <code>PostExecution</code> in order to its execution to be done in the
	 * UI thread.
	 *
	 * @param dialog The dialog that was just diposed
	 * @param postExecution The post execution once the dialog is disposed
	 * @return The <code>Runnable</code> that will invoke
	 * {@link PostExecution#execute(Dialog)}
	 */
	@SuppressWarnings("unchecked")
	private static <D1 extends Dialog, D2 extends D1>
		Runnable buildPostExecutionRunnable(
			final D1 dialog,
			final PostExecution<D2> postExecution) {

		return new Runnable() {
			public void run() {
				setUserInterfaceActive(false);
				try {
					postExecution.execute((D2) dialog);
				}
				finally {
					setUserInterfaceActive(true);
				}
			}
		};
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
	 * Relays out the parents of the <code>Control</code>. This was taken from
	 * the widget <code>Section</code>.
	 *
	 * @param pane The pane to revalidate as well as its parents
	 */
	public static void reflow(Composite pane) {

		for (Composite composite = pane; composite != null; ) {
			composite.setRedraw(false);
			composite = composite.getParent();

			if (composite instanceof ScrolledForm) {
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

			if (composite instanceof ScrolledForm) {
				break;
			}
		}
	}

	/**
	 * Sets whether the entire shell and its widgets should be enabled or
	 * everything should be unaccessible.
	 *
	 * @param active <code>true</code> to make all the UI active otherwise
	 * <code>false</code> to deactivate it
	 */
	public static void setUserInterfaceActive(boolean active) {
		Shell[] shells = getStandardDisplay().getShells();

		for (Shell shell : shells) {
			shell.setEnabled(active);
		}
	}

	/**
	 * Asynchronously launches the specified dialog in the UI thread.
	 *
	 * @param dialog The dialog to show on screen
	 * @param postExecution This interface let the caller to invoke a piece of
	 * code once the dialog is disposed
	 */
	public static <D1 extends Dialog, D2 extends D1>
		void show(final D1 dialog, final PostExecution<D2> postExecution) {

		try {
			Assert.isNotNull(dialog,        "The dialog cannot be null");
			Assert.isNotNull(postExecution, "The PostExecution cannot be null");
		}
		catch (AssertionFailedException e) {
			// Make sure the UI is interactive
			setUserInterfaceActive(true);
			throw e;
		}

		new Thread() {
			@Override
			public void run() {
				asyncExec(
					new Runnable() { public void run() {
						showImp(dialog, postExecution);
					}
				}
			);
		}}.start();
	}

	/**
	 * Asynchronously launches the specified dialog in the UI thread.
	 *
	 * @param dialog The dialog to show on screen
	 */
	public static void show(Dialog dialog) {
		show(dialog, NullPostExecution.<Dialog>instance());
	}

	/**
	 * Asynchronously launches the specified dialog in the UI thread.
	 *
	 * @param dialog The dialog to show on screen
	 * @param postExecution This interface let the caller to invoke a piece of
	 * code once the dialog is disposed
	 */
	private static <D1 extends Dialog, D2 extends D1>
		void showImp(D1 dialog, PostExecution<D2> postExecution) {

		setUserInterfaceActive(true);
		dialog.open();

		if (postExecution != NullPostExecution.<D2>instance()) {
			asyncExec(buildPostExecutionRunnable(dialog, postExecution));
		}
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

	/**
	 * This handler is responsible for removing the default value when the combo
	 * has the focus or when the selected item is the default value and to select
	 * it when the combo loses the focus.
	 */
	private static class CComboHandler implements ModifyListener,
	                                              FocusListener {

		/**
		 * This flag is used to prevent the methods of this handler from
		 * interacting with each other.
		 */
		private boolean locked;

		/*
		 * (non-Javadoc)
		 */
		public void focusGained(FocusEvent e) {

			if (locked) {
				return;
			}

			CCombo combo = (CCombo) e.widget;

			if (combo.getSelectionIndex() == 0) {
				combo.setData("populating", Boolean.TRUE);
				locked = true;

				// The text has to be changed outside of the context of this
				// listener otherwise the combo won't update because it's currently
				// notifying its listeners
				asyncExec(new TextUpdater(combo, Boolean.FALSE));
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public void focusLost(FocusEvent e) {

			if (locked) {
				return;
			}

			CCombo combo = (CCombo) e.widget;

			if (combo.getText().length() == 0) {
				combo.setData("populating", Boolean.TRUE);
				locked = true;

				try {
					combo.setText(combo.getItem(0));
				}
				finally {
					combo.setData("populating", Boolean.FALSE);
					locked = false;
				}
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public void modifyText(ModifyEvent e) {

			if (locked) {
				return;
			}

			CCombo combo = (CCombo) e.widget;

			if (combo.isFocusControl() &&
			    combo.getSelectionIndex() <= 0) {

				// Make sure the current text is the default value
				String currentValue = combo.getText();

				if (combo.getItemCount() > 0 &&
				    !currentValue.equals(combo.getItem(0))) {

					return;
				}

				// Remove the default value
				Object populating = combo.getData("populating");
				combo.setData("populating", Boolean.TRUE);
				locked = true;

				// The text has to be changed outside of the context of this
				// listener otherwise the combo won't update because it's currently
				// notifying its listeners
				asyncExec(new TextUpdater(combo, populating));
			}
		}

		private class TextUpdater implements Runnable {
			private final CCombo combo;
			private final Object populating;

			public TextUpdater(CCombo combo, Object populating) {
				super();
				this.combo      = combo;
				this.populating = populating;
			}

			public void run() {
				if (this.combo.isDisposed()) {
					CComboHandler.this.locked = false;
				}
				else {
					try {
						this.combo.setText("");
					}
					finally {
						this.combo.setData("populating", this.populating);
						CComboHandler.this.locked = false;
					}
				}
			}
		}
	}

	/**
	 * This handler is responsible for removing the default value when the combo
	 * has the focus or when the selected item is the default value and to select
	 * it when the combo loses the focus.
	 */
	private static class ComboHandler implements ModifyListener,
	                                             FocusListener {

		/**
		 * This flag is used to prevent the methods of this handler from
		 * interacting with each other.
		 */
		private boolean locked;

		/*
		 * (non-Javadoc)
		 */
		public void focusGained(FocusEvent e) {

			if (locked) {
				return;
			}

			Combo combo = (Combo) e.widget;

			if (combo.getSelectionIndex() == 0) {
				combo.setData("populating", Boolean.TRUE);
				locked = true;

				// The text has to be changed outside of the context of this
				// listener otherwise the combo won't update because it's currently
				// notifying its listeners
				asyncExec(new TextUpdater(combo, Boolean.FALSE));
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public void focusLost(FocusEvent e) {

			if (locked) {
				return;
			}

			Combo combo = (Combo) e.widget;

			if (combo.getText().length() == 0) {
				combo.setData("populating", Boolean.TRUE);
				locked = true;

				try {
					combo.select(0);
				}
				finally {
					combo.setData("populating", Boolean.FALSE);
					locked = false;
				}
			}
		}

		/*
		 * (non-Javadoc)
		 */
		public void modifyText(ModifyEvent e) {

			if (locked) {
				return;
			}

			Combo combo = (Combo) e.widget;

			if (combo.isFocusControl() &&
			    combo.getSelectionIndex() == 0) {

				Object populating = combo.getData("populating");
				combo.setData("populating", Boolean.TRUE);
				locked = true;

				// The text has to be changed outside of the context of this
				// listener otherwise the combo won't update because it's currently
				// notifying its listeners
				asyncExec(new TextUpdater(combo, populating));
			}
		}

		private class TextUpdater implements Runnable {
			private final Combo combo;
			private final Object populating;

			public TextUpdater(Combo combo, Object populating) {
				super();
				this.combo      = combo;
				this.populating = populating;
			}

			public void run() {
				if (this.combo.isDisposed()) {
					ComboHandler.this.locked = false;
				}
				else {
					try {
						this.combo.setText("");
					}
					finally {
						this.combo.setData("populating", this.populating);
						ComboHandler.this.locked = false;
					}
				}
			}
		}
	}
}