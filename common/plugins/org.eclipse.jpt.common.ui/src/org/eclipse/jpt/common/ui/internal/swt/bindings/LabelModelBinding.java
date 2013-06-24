/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import org.eclipse.jpt.common.ui.internal.swt.events.DisposeAdapter;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Widget;

/**
 * This binding can be used to keep a <em>label</em>
 * synchronized with image and text models.
 * 
 * @see PropertyValueModel
 * @see WidgetLabelAdapter
 */
class LabelModelBinding {

	// ***** model
	/** A value model on the underlying model image. */
	private final PropertyValueModel<Image> imageModel;

	/**
	 * A listener that allows us to synchronize the label's image with
	 * the model image.
	 */
	private final PropertyChangeListener imageListener;

	/** A value model on the underlying model text. */
	private final PropertyValueModel<String> textModel;

	/**
	 * A listener that allows us to synchronize the label's text with
	 * the model text.
	 */
	private final PropertyChangeListener textListener;

	// ***** UI
	/** The <em>label</em> we synchronize with the image and text models. */
	private final WidgetLabelAdapter labelAdapter;

	/**
	 * A listener that allows us to stop listening to stuff when the widget
	 * is disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener widgetDisposeListener;


	// ********** construction **********

	/**
	 * Constructor - the models and label adapter are required.
	 */
	LabelModelBinding(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, WidgetLabelAdapter labelAdapter) {
		super();
		if ((imageModel == null) || (textModel == null) || (labelAdapter == null)) {
			throw new NullPointerException();
		}
		this.imageModel = imageModel;
		this.textModel = textModel;
		this.labelAdapter = labelAdapter;

		this.imageListener = this.buildImageListener();
		this.imageModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.imageListener);

		this.textListener = this.buildTextListener();
		this.textModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.textListener);

		this.widgetDisposeListener = this.buildWidgetDisposeListener();
		this.getWidget().addDisposeListener(this.widgetDisposeListener);

		this.setImage(this.imageModel.getValue());
		this.setText(this.textModel.getValue());
	}

	private PropertyChangeListener buildImageListener() {
		return SWTListenerTools.wrap(new ImageListener(), this.labelAdapter.getWidget());
	}

	/* CU private */ class ImageListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			LabelModelBinding.this.imageChanged(event);
		}
	}

	private PropertyChangeListener buildTextListener() {
		return SWTListenerTools.wrap(new TextListener(), this.labelAdapter.getWidget());
	}

	/* CU private */ class TextListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			LabelModelBinding.this.textChanged(event);
		}
	}

	private DisposeListener buildWidgetDisposeListener() {
		return new WidgetDisposeListener();
	}

	/* CU private */ class WidgetDisposeListener
		extends DisposeAdapter
	{
		@Override
		public void widgetDisposed(DisposeEvent event) {
			LabelModelBinding.this.widgetDisposed();
		}
	}


	// ********** model events **********

	/* CU private */ void imageChanged(PropertyChangeEvent event) {
		if ( ! this.getWidget().isDisposed()) {
			this.setImage((Image) event.getNewValue());
		}
	}

	private void setImage(Image image) {
		this.labelAdapter.setImage(image);
	}

	/* CU private */ void textChanged(PropertyChangeEvent event) {
		if ( ! this.getWidget().isDisposed()) {
			this.setText((String) event.getNewValue());
		}
	}

	private void setText(String text) {
		this.labelAdapter.setText((text != null) ? text : StringTools.EMPTY_STRING);
	}

	private Widget getWidget() {
		return this.labelAdapter.getWidget();
	}


	// ********** UI events **********

	/* CU private */ void widgetDisposed() {
		// the widget is not yet "disposed" when we receive this event
		// so we can still remove our listener
		this.getWidget().removeDisposeListener(this.widgetDisposeListener);
		this.imageModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.imageListener);
		this.textModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.textListener);
	}


	// ********** misc **********

    @Override
	public String toString() {
		return ObjectTools.toString(this, this.textModel);
	}
}
