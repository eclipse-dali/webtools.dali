/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jpt.common.utility.internal.AbstractBooleanReference;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Dali UI plug-in.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
@SuppressWarnings("nls")
public class JptJpaUiPlugin
	extends AbstractUIPlugin
{
	/**
	 * @see #focusIn(Control)
	 */
	private final AsyncEventListenerFlag asyncEventListenerFlag = new AsyncEventListenerFlag();
	private final Listener focusListener;


	// ********** constants **********

	/**
	 * The plug-in identifier of JPA UI support (value {@value}).
	 */
	public static final String PLUGIN_ID = "org.eclipse.jpt.jpa.ui";
	public static final String PLUGIN_ID_ = PLUGIN_ID + '.';

	private static final String DALI_UI_KEY = PLUGIN_ID;
	private static final Object DALI_UI_DATA = new Object();


	// ********** Preference keys **********
	/**
	 * The preference key used to retrieve the case used for JPQL identifiers.
	 * @deprecated - Use JpaJpqlPreferencesManager instead
	 */
	@Deprecated
	public static final String JPQL_IDENTIFIER_CASE_PREF_KEY = PLUGIN_ID + ".jpqlIdentifier.case";
	@Deprecated
	public static final String JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE = "lowercase";
	@Deprecated
	public static final String JPQL_IDENTIFIER_UPPERCASE_PREF_VALUE = "uppercase";
	@Deprecated
	public static final String JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_PREF_KEY = PLUGIN_ID + ".jpqlIdentifier.matchFirstCharacterCase";


	// ********** singleton **********

	private static JptJpaUiPlugin INSTANCE;

	/**
	 * Returns the singleton JPT UI plug-in.
	 */
	public static JptJpaUiPlugin instance() {
		return INSTANCE;
	}


	// ********** logging **********

	/**
	 * Log the specified message.
	 */
	public static void log(String msg) {
        log(msg, null);
    }

	/**
	 * Log the specified exception or error.
	 */
	public static void log(Throwable throwable) {
		log(throwable.getLocalizedMessage(), throwable);
	}

	/**
	 * Log the specified message and exception or error.
	 */
	public static void log(String msg, Throwable throwable) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, throwable));
	}

	/**
	 * Log the specified status.
	 */
	public static void log(IStatus status) {
        INSTANCE.getLog().log(status);
    }


	// ********** images **********

	/**
	 * Return an image descriptor for the specified <code>.gif<code>
	 * file in the icons folder.
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		if ( ! key.startsWith("icons/")) {
			key = "icons/" + key;
		}
		if ( ! key.endsWith(".gif")) {
			key = key + ".gif";
		}
		return imageDescriptorFromPlugin(PLUGIN_ID, key);
	}

	/**
	 * Return an image for the specified <code>.gif<code>
	 * file in the icons folder.
	 */
	//TODO we are using the ImageRegistry here and storing all our icons for the life of the plugin,
	//which means until the workspace is closed.  This is better than before where we constantly
	//created new images. Bug 306437 is about cleaning this up and using Local Resource Managers
	//on our views so that closing the JPA perspective would mean our icons are disposed.
	public static Image getImage(String key) {
		ImageRegistry imageRegistry = instance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null) {
			imageRegistry.put(key, getImageDescriptor(key));
			image = imageRegistry.get(key);
		}
		return image;
	}


	// ********** construction **********

	public JptJpaUiPlugin() {
		super();
		this.focusListener = this.buildFocusListener();
		if (INSTANCE != null) {
			throw new IllegalStateException();
		}
		INSTANCE = this;
	}

	/**
	 * We are registered to receive only {@link SWT#FocusIn} events
	 */
	private Listener buildFocusListener() {
		return new Listener() {
			public void handleEvent(Event event) {
				JptJpaUiPlugin.this.focusIn((Control) event.widget);
			}
		};
	}


	// ********** focus handling **********

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
		while (control != null) {
			if (control.getData(DALI_UI_KEY) == DALI_UI_DATA) {
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
		control.setData(DALI_UI_KEY, DALI_UI_DATA);
	}


	// ********** plug-in implementation **********

	/**
	 * Register our SWT listener with the display so we receive notification
	 * of every "focus in" event.
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		this.getJpaProjectManager().addAsyncEventListenerFlag(this.asyncEventListenerFlag);
		Display.getDefault().addFilter(SWT.FocusIn, this.focusListener);
	}

	private JpaProjectManager getJpaProjectManager() {
		return (JpaProjectManager) ResourcesPlugin.getWorkspace().getAdapter(JpaProjectManager.class);
	}

	/**
	 * Unregister our SWT listener with the display.
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			Display.getDefault().removeFilter(SWT.FocusIn, this.focusListener);
			this.getJpaProjectManager().removeAsyncEventListenerFlag(this.asyncEventListenerFlag);
		} finally {
			super.stop(context);
		}
	}


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
