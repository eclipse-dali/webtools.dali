/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class BaseJpaController<T>
{
	//put in the populating flag to stop the circular population of the entity name combo
	//populateEntityNameCombo is calling select() which causes entityNameComboModified() to be called
	//this sets the name in the model which starts the circle over again. We should probably
	//short-circuit this differently, like in the emf model, keep the property change from being fired if
	//a change did not actually occur - KFM
	private boolean populating;

	/**
	 * The subject of this controller.
	 */
	private WritablePropertyValueModel<T> subjectHolder;

	protected TabbedPropertySheetWidgetFactory widgetFactory;

	public BaseJpaController(Composite parent, int style, TabbedPropertySheetWidgetFactory widgetFactory) {
		super();
		this.widgetFactory = widgetFactory;
		buildWidget(parent, style);
	}

	public BaseJpaController(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super();
		this.widgetFactory = widgetFactory;
		buildWidget(parent);
	}


	/**
	 * Override this method if using the constructor without the style bit
	 */
	protected void buildWidget(Composite parent) {
		// no op
	}

	/**
	 * Override this method if using the constructor with the style bit
	 */
	protected void buildWidget(Composite parent, int style) {
		// no op
	}

	/**
	 * Uninstalls any listeners from the subject in order to stop being notified
	 * for changes made outside of this controllers.
	 */
	protected abstract void disengageListeners();

	/**
	 * Notifies this controller is should dispose itself.
	 */
	public void dispose() {
		try {
			disengageListeners();
		}
		finally {
			subjectHolder.setValue(null);
		}
	}

	protected abstract void doPopulate();

	/**
	 * Installs the listeners on the subject in order to be notified from changes
	 * made outside of this controllers.
	 */
	protected abstract void engageListeners();

	public abstract Control getControl();

	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return this.widgetFactory;
	}

	protected final boolean isPopulating() {
		return this.populating;
	}

	/**
	 * This method is called (perhaps internally) when this needs to repopulate
	 * but the object of interest has not changed
	 */
	public final void populate() {
		if (getControl().isDisposed()) {
			return;
		}

		this.populating = true;

		try {
			doPopulate();
		}
		finally {
			this.populating = false;
		}
	}

	/**
	 * This method is called from outside when setting the object of interest
	 */
	public final void populate(T model) {
		if (!getControl().isDisposed()) {
			this.subjectHolder.setValue(model);
			this.populate();
		}
	}

	/**
	 * Returns the subject of this controller.
	 *
	 * @return The subject if this controller was not disposed; <code>null</code>
	 * if it was
	 */
	protected final T subject() {
		return subjectHolder.value();
	}

	/**
	 * Returns the subject holder used by this controller.
	 *
	 * @return The holder of the subject
	 */
	protected final PropertyValueModel<T> getSubjectHolder() {
		return subjectHolder;
	}
}