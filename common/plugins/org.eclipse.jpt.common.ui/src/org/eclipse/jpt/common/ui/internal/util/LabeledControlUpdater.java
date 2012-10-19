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

import org.eclipse.core.runtime.Assert;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;

/**
 * This updater is responsible to update the <code>LabeledControl</code> when
 * the text and the icon need to change.
 *
 * @version 3.3
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class LabeledControlUpdater {

	/**
	 * The wrapper around a control that has text and icon.
	 */
	private LabeledControl labeledControl;

	/**
	 * A listener that allows us to stop listening to stuff when the control
	 * is disposed. (Critical for preventing memory leaks.)
	 */
	protected DisposeListener disposeListener;

	/** A value model on the underlying model text. */
	protected final PropertyValueModel<String> textModel;

	protected PropertyChangeListener textListener;

	/** A value model on the underlying model image. */
	protected final PropertyValueModel<Image> imageModel;

	protected PropertyChangeListener imageListener;

	/**
	 * Creates a new <code>LabeledControlUpdater</code>.
	 *
	 * @param labeledControl The wrapper around the control that needs to
	 * have its text updated
	 * @param textModel The holder this class will listen for changes
	 */
	public LabeledControlUpdater(LabeledControl labeledControl,
	                             PropertyValueModel<String> textModel)
	{
		this(labeledControl, textModel, null);
	}

	/**
	 * Creates a new <code>LabeledControlUpdater</code>.
	 *
	 * @param labeledControl The wrapper around the control that needs to
	 * have its image and text updated
	 * @param imageModel The holder this class will listen for changes or
	 * <code>null</code> if the text never changes
	 * @param textModel The holder this class will listen for changes or
	 * <code>null</code> if the image never changes
	 */
	public LabeledControlUpdater(LabeledControl labeledControl,
	                             PropertyValueModel<String> textModel,
	                             PropertyValueModel<Image> imageModel)
	{
		super();
		Assert.isNotNull(labeledControl, "The LabeledControl cannot be null");
		this.labeledControl = labeledControl;
		this.textModel = textModel;
		this.imageModel = imageModel;

		if (this.textModel != null) {
			this.textListener = this.buildTextListener();
			this.textModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.textListener);
			this.setText(this.textModel.getValue());
		}

		if (this.imageModel != null) {
			this.imageListener = this.buildImageListener();
			this.imageModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.imageListener);
			this.setImage(this.imageModel.getValue());
		}

		this.disposeListener = this.buildDisposeListener();
		this.labeledControl.addDisposeListener(this.disposeListener);
	}

	private PropertyChangeListener buildImageListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildImageListener_());
	}

	private PropertyChangeListener buildImageListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				LabeledControlUpdater.this.setImage((Image) e.getNewValue());
			}

			@Override
			public String toString() {
				return "LabeledControlUpdater.imageListener";
			}
		};
	}

	private PropertyChangeListener buildTextListener() {
		return new SWTPropertyChangeListenerWrapper(buildTextListener_());
	}

	private PropertyChangeListener buildTextListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				LabeledControlUpdater.this.setText((String) e.getNewValue());
			}

			@Override
			public String toString() {
				return "LabeledControlUpdater.textListener";
			}
		};
	}

	private void setImage(Image image) {
		this.labeledControl.setImage(image);
	}

	private void setText(String text) {
		if (text == null) {
			text = "";
		}

		this.labeledControl.setText(text);
	}

	// ********** dispose **********

	private void controlDisposed(@SuppressWarnings("unused") DisposeEvent event) {
		// the control is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.labeledControl.removeDisposeListener(this.disposeListener);

		if (this.imageModel != null) {
			this.imageModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.imageListener);
		}

		if (this.textModel != null) {
			this.textModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.textListener);
		}
	}

	private DisposeListener buildDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				LabeledControlUpdater.this.controlDisposed(event);
			}
		    @Override
			public String toString() {
				return "LabeledControlUpdater.disposeListener";
			}
		};
	}
}