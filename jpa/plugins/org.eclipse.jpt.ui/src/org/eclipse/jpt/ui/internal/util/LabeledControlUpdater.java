/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

/**
 * This updater is responsible to update the <code>LabeledControl</code> when
 * the text and the icon need to change.
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class LabeledControlUpdater {

	/**
	 * The wrapper around a control that has text and icon.
	 */
	private LabeledControl labeledControl;

	/**
	 * Creates a new <code>LabeledControlUpdater</code>.
	 *
	 * @param labeledControl The wrapper around the control that needs to
	 * have its text updated
	 * @param textHolder The holder this class will listen for changes
	 */
	public LabeledControlUpdater(LabeledControl labeledControl,
	                             PropertyValueModel<String> textHolder)
	{
		this(labeledControl, textHolder, null);
	}

	/**
	 * Creates a new <code>LabeledControlUpdater</code>.
	 *
	 * @param labeledControl The wrapper around the control that needs to
	 * have its image and text updated
	 * @param imageHolder The holder this class will listen for changes or
	 * <code>null</code> if the text never changes
	 * @param textHolder The holder this class will listen for changes or
	 * <code>null</code> if the image never changes
	 */
	public LabeledControlUpdater(LabeledControl labeledControl,
	                             PropertyValueModel<String> textHolder,
	                             PropertyValueModel<Image> imageHolder)
	{
		super();
		initialize(labeledControl, textHolder, imageHolder);
	}

	private PropertyChangeListener buildIconListener() {
		return new SWTPropertyChangeListenerWrapper(buildIconListener_());
	}

	private PropertyChangeListener buildIconListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				LabeledControlUpdater.this.setImage((Image) e.newValue());
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
				LabeledControlUpdater.this.setText((String) e.newValue());
			}

			@Override
			public String toString() {
				return "LabeledControlUpdater.textListener";
			}
		};
	}

	private void initialize(LabeledControl labeledControl,
	                        PropertyValueModel<String> textHolder,
	                        PropertyValueModel<Image> imageHolder)
	{
		Assert.isNotNull(labeledControl, "The LabeledControl cannot be null");

		this.labeledControl = labeledControl;

		if (textHolder != null) {
			textHolder.addPropertyChangeListener(PropertyValueModel.VALUE, buildTextListener());
			setText(textHolder.value());
		}

		if (imageHolder != null) {
			imageHolder.addPropertyChangeListener(PropertyValueModel.VALUE, buildIconListener());
			setImage(imageHolder.value());
		}
	}

	private void setImage(Image icon) {
		labeledControl.setImage(icon);
	}

	private void setText(String text) {

		if (text == null) {
			text = "";
		}

		labeledControl.setText(text);
	}
}