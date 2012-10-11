/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.utility.swt;

import java.util.HashSet;
import org.eclipse.jpt.common.utility.internal.iterable.SnapshotCloneIterable;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Control;

/**
 * This controller enables a boolean model to control either the
 * <em>enabled</em> or <em>visible</em> properties of a set of SWT controls;
 * i.e. the controls' properties are kept in synch with the boolean model,
 * but <em>not</em> vice-versa.
 * 
 * @see PropertyValueModel
 * @see CollectionValueModel
 * @see Control#setEnabled(boolean)
 * @see Control#setVisible(boolean)
 */
final class MultiControlBooleanStateController
	extends BooleanStateController
{
	/**
	 * The set of controls whose state is kept in sync with the boolean model.
	 */
	private final CollectionValueModel<? extends Control> controlsModel;

	/**
	 * A listener that allows clients to add/remove controls.
	 */
	private final CollectionChangeListener controlsListener;

	/**
	 * Cache of controls.
	 */
	private final HashSet<Control> controls = new HashSet<Control>();


	// ********** constructor **********

	/**
	 * Constructor - the boolean model, the controls model, and the adapter are required.
	 */
	MultiControlBooleanStateController(
			PropertyValueModel<Boolean> booleanModel,
			CollectionValueModel<? extends Control> controlsModel,
			boolean defaultValue,
			Adapter adapter
	) {
		super(booleanModel, defaultValue, adapter);
		if (controlsModel == null) {
			throw new NullPointerException();
		}
		this.controlsModel = controlsModel;
		this.controlsListener = this.buildControlsListener();
		this.addControls(controlsModel);
	}


	// ********** initialization **********

	private CollectionChangeListener buildControlsListener() {
		return new ControlsListener();
	}

	/* CU private */ class ControlsListener
		extends CollectionChangeAdapter
	{
		@Override
		@SuppressWarnings("unchecked")
		public void itemsAdded(CollectionAddEvent event) {
			MultiControlBooleanStateController.this.addControls((Iterable<? extends Control>) event.getItems());
		}
		@Override
		@SuppressWarnings("unchecked")
		public void itemsRemoved(CollectionRemoveEvent event) {
			MultiControlBooleanStateController.this.removeControls((Iterable<? extends Control>) event.getItems());
		}
		@Override
		public void collectionCleared(CollectionClearEvent event) {
			MultiControlBooleanStateController.this.clearControls();
		}
		@Override
		@SuppressWarnings("unchecked")
		public void collectionChanged(CollectionChangeEvent event) {
			MultiControlBooleanStateController.this.clearControls();
			MultiControlBooleanStateController.this.addControls((Iterable<? extends Control>) event.getCollection());
		}
	}


	// ********** controls **********

	@Override
	void setControlState(boolean controlState) {
		for (Control control : this.controls) {
			this.setControlState(control, controlState);
		}
	}

	/* CU private */ void addControls(Iterable<? extends Control> controls_) {
		for (Control control : controls_) {
			this.addControl(control);
		}
	}

	private void addControl(Control control) {
		if (this.controls.isEmpty()) {
			this.engageBooleanModel();
			this.controlsModel.addCollectionChangeListener(CollectionValueModel.VALUES, this.controlsListener);
		}
		if (this.controls.add(control)) {
			this.engageControl(control);
			// wait until the models are engaged to get the boolean value... :-)
			this.setControlState(control, this.getBooleanValue());
		} else {
			throw new IllegalArgumentException("duplicate control: " + control); //$NON-NLS-1$
		}
	}

	/* CU private */ void clearControls() {
		this.removeControls(new SnapshotCloneIterable<Control>(this.controls));
	}

	/* CU private */ void removeControls(Iterable<? extends Control> controls_) {
		for (Control control : controls_) {
			this.disengageControl(control);
			this.removeControl(control);
		}
	}

	private void removeControl(Control control) {
		this.controls.remove(control);
		if (this.controls.isEmpty()) {
			this.controlsModel.removeCollectionChangeListener(CollectionValueModel.VALUES, this.controlsListener);
			this.disengageBooleanModel();
		}
	}

	@Override
	void controlDisposed(Control control) {
		super.controlDisposed(control);
		this.removeControl(control);
	}
}
