/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.listeners;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;

public final class SWTListenerTools {

	// ********** property **********

	/**
	 * Wrap the specified property change listener and forward events to it on
	 * the SWT UI thread, asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTPropertyChangeListenerWrapper
	 */
	public static PropertyChangeListener wrap(PropertyChangeListener listener) {
		return wrap(listener, DisplayTools.getDisplay());
	}

	/**
	 * Wrap the specified property change listener and forward events to it on
	 * the SWT UI thread associated with the specified viewer,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTPropertyChangeListenerWrapper
	 */
	public static PropertyChangeListener wrap(PropertyChangeListener listener, Viewer viewer) {
		return wrap(listener, viewer.getControl());
	}

	/**
	 * Wrap the specified property change listener and forward events to it on
	 * the SWT UI thread associated with the specified widget,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTPropertyChangeListenerWrapper
	 */
	public static PropertyChangeListener wrap(PropertyChangeListener listener, Widget widget) {
		return wrap(listener, widget.getDisplay());
	}

	/**
	 * Wrap the specified property change listener and forward events to it on
	 * the SWT UI thread associated with the specified display,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTPropertyChangeListenerWrapper
	 */
	public static PropertyChangeListener wrap(PropertyChangeListener listener, Display display) {
		return wrap(listener, display, JptCommonUiPlugin.exceptionHandler());
	}

	/**
	 * Wrap the specified property change listener and forward events to it on
	 * the SWT UI thread associated with the specified display,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be forwarded to the
	 * specified exception handler.
	 * @see SWTPropertyChangeListenerWrapper
	 */
	public static PropertyChangeListener wrap(PropertyChangeListener listener, Display display, ExceptionHandler exceptionHandler) {
		return new SWTPropertyChangeListenerWrapper(listener, display, exceptionHandler);
	}


	// ********** collection **********

	/**
	 * Wrap the specified collection change listener and forward events to it on
	 * the SWT UI thread, asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTCollectionChangeListenerWrapper
	 */
	public static CollectionChangeListener wrap(CollectionChangeListener listener) {
		return wrap(listener, DisplayTools.getDisplay());
	}

	/**
	 * Wrap the specified collection change listener and forward events to it on
	 * the SWT UI thread associated with the specified viewer,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTCollectionChangeListenerWrapper
	 */
	public static CollectionChangeListener wrap(CollectionChangeListener listener, Viewer viewer) {
		return wrap(listener, viewer.getControl());
	}

	/**
	 * Wrap the specified collection change listener and forward events to it on
	 * the SWT UI thread associated with the specified widget,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTCollectionChangeListenerWrapper
	 */
	public static CollectionChangeListener wrap(CollectionChangeListener listener, Widget widget) {
		return wrap(listener, widget.getDisplay());
	}

	/**
	 * Wrap the specified collection change listener and forward events to it on
	 * the SWT UI thread associated with the specified display,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTCollectionChangeListenerWrapper
	 */
	public static CollectionChangeListener wrap(CollectionChangeListener listener, Display display) {
		return wrap(listener, display, JptCommonUiPlugin.exceptionHandler());
	}

	/**
	 * Wrap the specified collection change listener and forward events to it on
	 * the SWT UI thread associated with the specified display,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be forwarded to the
	 * specified exception handler.
	 * @see SWTCollectionChangeListenerWrapper
	 */
	public static CollectionChangeListener wrap(CollectionChangeListener listener, Display display, ExceptionHandler exceptionHandler) {
		return new SWTCollectionChangeListenerWrapper(listener, display, exceptionHandler);
	}


	// ********** list **********

	/**
	 * Wrap the specified list change listener and forward events to it on
	 * the SWT UI thread, asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTListChangeListenerWrapper
	 */
	public static ListChangeListener wrap(ListChangeListener listener) {
		return wrap(listener, DisplayTools.getDisplay());
	}

	/**
	 * Wrap the specified list change listener and forward events to it on
	 * the SWT UI thread associated with the specified viewer,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTListChangeListenerWrapper
	 */
	public static ListChangeListener wrap(ListChangeListener listener, Viewer viewer) {
		return wrap(listener, viewer.getControl());
	}

	/**
	 * Wrap the specified list change listener and forward events to it on
	 * the SWT UI thread associated with the specified widget,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTListChangeListenerWrapper
	 */
	public static ListChangeListener wrap(ListChangeListener listener, Widget widget) {
		return wrap(listener, widget.getDisplay());
	}

	/**
	 * Wrap the specified list change listener and forward events to it on
	 * the SWT UI thread associated with the specified display,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTListChangeListenerWrapper
	 */
	public static ListChangeListener wrap(ListChangeListener listener, Display display) {
		return wrap(listener, display, JptCommonUiPlugin.exceptionHandler());
	}

	/**
	 * Wrap the specified list change listener and forward events to it on
	 * the SWT UI thread associated with the specified display,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be forwarded to the
	 * specified exception handler.
	 * @see SWTListChangeListenerWrapper
	 */
	public static ListChangeListener wrap(ListChangeListener listener, Display display, ExceptionHandler exceptionHandler) {
		return new SWTListChangeListenerWrapper(listener, display, exceptionHandler);
	}


	// ********** state **********

	/**
	 * Wrap the specified state change listener and forward events to it on
	 * the SWT UI thread, asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTStateChangeListenerWrapper
	 */
	public static StateChangeListener wrap(StateChangeListener listener) {
		return wrap(listener, DisplayTools.getDisplay());
	}

	/**
	 * Wrap the specified state change listener and forward events to it on
	 * the SWT UI thread associated with the specified viewer,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTStateChangeListenerWrapper
	 */
	public static StateChangeListener wrap(StateChangeListener listener, Viewer viewer) {
		return wrap(listener, viewer.getControl());
	}

	/**
	 * Wrap the specified state change listener and forward events to it on
	 * the SWT UI thread associated with the specified widget,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTStateChangeListenerWrapper
	 */
	public static StateChangeListener wrap(StateChangeListener listener, Widget widget) {
		return wrap(listener, widget.getDisplay());
	}

	/**
	 * Wrap the specified state change listener and forward events to it on
	 * the SWT UI thread associated with the specified display,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be handled by the
	 * {@link JptCommonUiPlugin#exceptionHandler() default exception handler}.
	 * @see SWTStateChangeListenerWrapper
	 */
	public static StateChangeListener wrap(StateChangeListener listener, Display display) {
		return wrap(listener, display, JptCommonUiPlugin.exceptionHandler());
	}

	/**
	 * Wrap the specified state change listener and forward events to it on
	 * the SWT UI thread associated with the specified display,
	 * asynchronously if necessary.
	 * Any exceptions thrown by the specified listener will be forwarded to the
	 * specified exception handler.
	 * @see SWTStateChangeListenerWrapper
	 */
	public static StateChangeListener wrap(StateChangeListener listener, Display display, ExceptionHandler exceptionHandler) {
		return new SWTStateChangeListenerWrapper(listener, display, exceptionHandler);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private SWTListenerTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
