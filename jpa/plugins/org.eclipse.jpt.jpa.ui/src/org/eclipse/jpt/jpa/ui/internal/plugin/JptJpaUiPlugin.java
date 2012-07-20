/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.plugin;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.common.utility.internal.AbstractBooleanReference;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Dali JPA UI plug-in.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class JptJpaUiPlugin
	extends JptUIPlugin
{
	/**
	 * @see #focusIn(Control)
	 */
	private final AsyncEventListenerFlag asyncEventListenerFlag = new AsyncEventListenerFlag();
	private Display display;
	private final Listener focusListener = new FocusListener();


	// ********** singleton **********

	private static JptJpaUiPlugin INSTANCE;

	/**
	 * Returns the singleton JPT UI plug-in.
	 */
	public static JptJpaUiPlugin instance() {
		return INSTANCE;
	}


	// ********** Dali plug-in **********

	public JptJpaUiPlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptJpaUiPlugin) plugin;
	}

	/**
	 * Register our SWT listener with the display so we receive notification
	 * of every "focus in" event.
	 */
	@Override
	public void start_() throws Exception {
		super.start_();
		// no leak here - the flag has no backpointer to the plug-in
		this.getJpaProjectManager().addJavaEventListenerFlag(this.asyncEventListenerFlag);

		// must be on UI thread...
		this.display = Display.getCurrent();
		// a little pre-construction leakage, but it should be OK as the listener
		// interacts mostly with the 'asyncEventListenerFlag' and only once the
		// plug-in is "active"
		if ((this.display != null) && ( ! this.display.isDisposed())) {
			this.display.addFilter(SWT.FocusIn, this.focusListener);
		}
	}

	private JpaProjectManager getJpaProjectManager() {
		return this.getJpaWorkspace().getJpaProjectManager();
	}

	private JpaWorkspace getJpaWorkspace() {
		return (JpaWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JpaWorkspace.class);
	}

	/**
	 * Unregister our SWT listener with the display.
	 */
	@Override
	public void stop_() throws Exception {
		try {
			// must be on UI thread...
			if ((this.display != null) && ( ! this.display.isDisposed())) {
				this.display.removeFilter(SWT.FocusIn, this.focusListener);
			}
			this.getJpaProjectManager().removeJavaEventListenerFlag(this.asyncEventListenerFlag);
		} finally {
			this.display = null;
			super.stop_();
		}
	}


	// ********** focus listener **********

	/**
	 * This listener is registered to receive only {@link SWT#FocusIn} events.
	 */
	/* CU private */ class FocusListener
		implements Listener
	{
		public void handleEvent(Event event) {
			JptJpaUiPlugin.this.focusIn((Control) event.widget);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** focus event handling **********

	/**
	 * This method is called whenever a {@link SWT#FocusIn} event is generated.
	 * <p>
	 * If the control gaining focus is part of one the Dali composites
	 * (typically the JPA Details View), we deactivate the Dali Java change
	 * listener so we ignore any changes to the Java source code that probably
	 * originated from Dali. This means we will miss any changes to the Java
	 * source code that is caused by non-UI activity; but, we hope, these
	 * changes are unrelated to JPA annotations etc.
	 * <p>
	 * If the control gaining focus is <em>not</em> part of one of the Dali
	 * composites, we start listening to the Java change events again. This
	 * method is called whenever a non-Dali UI control gains the UI focus. When
	 * this happens we activate the Dali Java change listener so that we begin
	 * to keep the Dali model synchronized with the Java source code.
	 */
	/* CU private */ void focusIn(Control control) {
		this.asyncEventListenerFlag.setValue(this.controlIsNonDali(control));
	}

	/**
	 * Return whether the specified control is <em><b>neither</b></em>
	 * a Dali UI component <em><b>nor</b></em> contained by a Dali UI component.
	 */
	private boolean controlIsNonDali(Control control) {
		return ! this.controlIsDali(control);
	}

	/**
	 * Return whether the specified control, or any of its ancestors, is a Dali
	 * UI component.
	 * 
	 * @see #controlAffectsJavaSource(Control)
	 */
	private boolean controlIsDali(Control control) {
		String id = this.getPluginID();
		if (id == null) {
			return false;
		}
		while (control != null) {
			if (control.getData(id) == DALI_UI_DATA) {
				return true;
			}
			control = control.getParent();
		}
		return false;
	}

	/**
	 * Tag the specified control so that whenever it (or any of its descendants)
	 * has the focus, the Dali model ignores any Java change events. This method
	 * is to be called when the control is first constructed.
	 * 
	 * @see #controlIsDali(Control)
	 */
	public void controlAffectsJavaSource(Control control) {
		String id = this.getPluginID();
		if (id != null) {
			control.setData(id, DALI_UI_DATA);
		}
	}

	private static final Object DALI_UI_DATA = new Object();


	// ********** async event listener flag **********

	/**
	 * This flag's value depends on the current thread. If the current thread is
	 * the background Java Reconciler thread, the flag's value is determined by
	 * the current UI focus (i.e. whether the focus is somewhere other than a
	 * Dali view); otherwise the flag's value is <code>true</code>.
	 * In other words, if a Dali view has the focus and a Java event is fired
	 * on the Java Reconciler thread, the event is ignored; if the event is
	 * fired on some other thread (typically synchronously on the Main thread),
	 * the event is forwarded to the JPA projects.
	 */
	/* CU private */ static class AsyncEventListenerFlag
		extends AbstractBooleanReference
	{
		private volatile boolean value = true;

		@SuppressWarnings("restriction")
		private static final String JAVA_RECONCILER_THREAD_NAME = org.eclipse.jdt.internal.ui.text.JavaReconciler.class.getName();

		AsyncEventListenerFlag() {
			super();
		}

		public boolean getValue() {
			if (Thread.currentThread().getName().equals(JAVA_RECONCILER_THREAD_NAME)) {
				return this.value;
			}
			return true;
		}

		public boolean setValue(boolean value) {
			boolean old = this.value;
			this.value = value;
			return old;
		}
	}
}
