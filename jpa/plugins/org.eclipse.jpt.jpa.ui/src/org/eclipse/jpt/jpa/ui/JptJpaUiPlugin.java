/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.jpa.ui.navigator.JpaNavigatorProvider;
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
	private final Listener focusListener;


	// ********** constants **********

	/**
	 * The plug-in identifier of JPA UI support (value {@value}).
	 */
	public static final String PLUGIN_ID = "org.eclipse.jpt.jpa.ui";
	public static final String PLUGIN_ID_ = PLUGIN_ID + '.';

	private static final String FOCUS_DATA_KEY = PLUGIN_ID_ + "focus";
	private static final Object FOCUS_DATA = new Object();


	// ********** Preference keys **********

	/**
	 * The preference key used to retrieve the case used for JPQL identifiers.
	 */
	public static final String JPQL_IDENTIFIER_CASE_PREF_KEY = PLUGIN_ID + ".jpqlIdentifier.case";
	public static final String JPQL_IDENTIFIER_LOWERCASE_PREF_VALUE = "lowercase";
	public static final String JPQL_IDENTIFIER_UPPERCASE_PREF_VALUE = "uppercase";
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
	 * This method is called whenever a "focus in" event is generated.
	 * If the control gaining focus is part of one of our composites (typically
	 * a JPA Details View), we stop listening to Java change events
	 * (and assume all changes to the Java model are generated by us).
	 * If the control gaining focus is *not* part of one of our composites,
	 * we start listening to the Java change events again.
	 */
	void focusIn(Control control) {
		while (control != null) {
			if (control.getData(FOCUS_DATA_KEY) == FOCUS_DATA) {
				this.focusIn();
				return;
			}
			control = control.getParent();
		}
		this.focusOut();
	}

	/**
	 * This method is called whenever a Dali UI control that affects Java
	 * source code gains the UI focus. When this happens we deactivate
	 * the Dali Java change listener so we ignore any changes to the Java
	 * source code that probably originated from Dali. This means we will miss
	 * any changes to the Java source code that is caused by non-UI activity;
	 * but, we hope, these changes are unrelated to JPA annotations etc.
	 * @see #focusOut()
	 */
	private void focusIn() {
		JptJpaCorePlugin.setJavaElementChangeListenerIsActive(false);
	}

	/**
	 * This method is called whenever a non-Dali UI control gains the UI focus.
	 * When this happens we activate the Dali Java change listener so that we
	 * begin to keep the Dali model synchronized with the Java source code.
	 * @see #focusIn()
	 */
	private void focusOut() {
		JptJpaCorePlugin.setJavaElementChangeListenerIsActive(true);
	}

	/**
	 * Tag the specified control so that whenever it (or any of its children,
	 * grandchildren, etc.) has the focus, the Dali model ignores any Java
	 * change events. This method is to be called when the control is first
	 * constructed.
	 */
	public void controlAffectsJavaSource(Control control) {
		control.setData(FOCUS_DATA_KEY, FOCUS_DATA);
	}


	// ********** platform **********

	/**
	 * Return the JPA platform UI corresponding to the specified JPA platform.
	 */
	public JpaPlatformUi getJpaPlatformUi(JpaPlatform jpaPlatform) {
		return JpaPlatformUiRegistry.instance().getJpaPlatformUi(jpaPlatform.getId());
	}

	public JpaNavigatorProvider getJpaNavigatorProvider(JpaPlatform jpaPlatform) {
		JpaPlatformUi platform = this.getJpaPlatformUi(jpaPlatform);
		return (platform == null) ? null : platform.getNavigatorProvider();
	}


	// ********** plug-in implementation **********

	/**
	 * Register our SWT listener with the display so we receive notification
	 * of every "focus in" event.
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Display.getDefault().addFilter(SWT.FocusIn, this.focusListener);
	}

	/**
	 * Unregister our SWT listener with the display.
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			Display.getDefault().removeFilter(SWT.FocusIn, this.focusListener);
		} finally {
			super.stop(context);
		}
	}

}