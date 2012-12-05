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

import java.util.HashMap;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.common.ui.internal.swt.Adapter;
import org.eclipse.jpt.common.utility.internal.reference.AbstractBooleanReference;
import org.eclipse.jpt.common.utility.reference.BooleanReference;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.ui.internal.InternalJpaWorkbench;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;

/**
 * Dali JPA UI plug-in.
 */
public class JptJpaUiPlugin
	extends JptUIPlugin
{
	// NB: the plug-in must be synchronized whenever accessing any of this state
	private final HashMap<IWorkbench, InternalJpaWorkbench> jpaWorkbenchs = new HashMap<IWorkbench, InternalJpaWorkbench>();

	/**
	 * @see #focusIn(Control)
	 */
	private final ControlIsNonDaliListenerFlag controlIsNonDaliFlag = new ControlIsNonDaliListenerFlag();
	private final AsyncEventListenerFlag asyncEventListenerFlag = new AsyncEventListenerFlag(this.controlIsNonDaliFlag);
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
			for (InternalJpaWorkbench jpaWorkbench : this.jpaWorkbenchs.values()) {
				try {
					jpaWorkbench.stop();
				} catch (Throwable ex) {
					this.logError(ex);  // keep going
				}
			}
			this.jpaWorkbenchs.clear();

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


	// ********** JPA workbenchs **********

	/**
	 * Return the JPA workbench corresponding to the specified Eclipse workbench.
	 * <p>
	 * The preferred way to retrieve a JPA workbench is via the Eclipse
	 * adapter framework:
	 * <pre>
	 * JpaWorkbench jpaWorkbench = PlatformTools.getAdapter(PlatformUI.getWorkbench(), JpaWorkbench.class);
	 * </pre>
	 * @see org.eclipse.jpt.jpa.ui.internal.WorkbenchAdapterFactory#getJpaWorkbench(IWorkbench)
	 */
	public synchronized InternalJpaWorkbench getJpaWorkbench(IWorkbench workbench) {
		InternalJpaWorkbench jpaWorkbench = this.jpaWorkbenchs.get(workbench);
		if ((jpaWorkbench == null) && this.isActive()) {
			jpaWorkbench = this.buildJpaWorkbench(workbench);
			this.jpaWorkbenchs.put(workbench, jpaWorkbench);
		}
		return jpaWorkbench;
	}

	private InternalJpaWorkbench buildJpaWorkbench(IWorkbench workbench) {
		return new InternalJpaWorkbench(workbench);
	}


	// ********** focus listener **********

	/**
	 * This listener is registered to receive only {@link SWT#FocusIn} events.
	 */
	/* CU private */ class FocusListener
		extends Adapter
	{
		@Override
		public void handleEvent(Event event) {
			JptJpaUiPlugin.this.focusIn((Control) event.widget);
		}
	}


	// ********** focus event handling **********

	/**
	 * Return true if the focus is not in a Dali view.
	 * <p>
	 * Currently only the JpaTextEditorManager is using this listen
	 * to TextEditor selection change events if the focus is in the JPA Details view.
	 */
	public boolean getFocusIsNonDali() {
		return this.controlIsNonDaliFlag.getValue();
	}

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
		this.controlIsNonDaliFlag.setValue(this.controlIsNonDali(control));
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
		private final BooleanReference controlIsNonDaliListenerFlag;

		@SuppressWarnings("restriction")
		private static final String JAVA_RECONCILER_THREAD_NAME = org.eclipse.jdt.internal.ui.text.JavaReconciler.class.getName();

		AsyncEventListenerFlag(BooleanReference controlIsNonDaliListenerFlag) {
			super();
			this.controlIsNonDaliListenerFlag = controlIsNonDaliListenerFlag;
		}

		public boolean getValue() {
			if (Thread.currentThread().getName().equals(JAVA_RECONCILER_THREAD_NAME)) {
				return this.controlIsNonDaliListenerFlag.getValue();
			}
			return true;
		}
	}

	// ********** control is non dali listener listener flag **********

	/**
	 * This flag's value is determined by the current UI focus (i.e. whether the 
	 * focus is somewhere other than a Dali view, currently only the JPA Details view); 
	 * otherwise the flag's value is <code>true</code>.
	 */
	/* CU private */ static class ControlIsNonDaliListenerFlag
		extends AbstractBooleanReference
	{
		private volatile boolean value = true;

		ControlIsNonDaliListenerFlag() {
			super();
		}

		public boolean getValue() {
			return this.value;
		}

		public boolean setValue(boolean value) {
			boolean old = this.value;
			this.value = value;
			return old;
		}
	}
}
