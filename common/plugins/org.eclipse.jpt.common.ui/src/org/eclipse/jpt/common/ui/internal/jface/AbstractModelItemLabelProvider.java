/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.listeners.SWTListenerWrapperTools;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

/**
 * @param <M> the type of the provider's manager
 */
public abstract class AbstractModelItemLabelProvider<M extends ItemLabelProvider.Manager>
	implements ItemLabelProvider
{
	/* private-protected */ final Object item;

	/* private-protected */ final M manager;

	private final PropertyValueModel<ImageDescriptor> imageDescriptorModel;
	private final PropertyChangeListener imageDescriptorListener;
	private ImageDescriptor imageDescriptor;
	private boolean refreshImage = true;  // image can be null - must be accessed on the manager's UI thread
	private Image image;  // must be accessed on the manager's UI thread

	private final PropertyValueModel<String> textModel;
	private final PropertyChangeListener textListener;
	private String text;  // must be accessed on the manager's UI thread

	private boolean disposed = false;


	protected AbstractModelItemLabelProvider(Object item, M manager, PropertyValueModel<ImageDescriptor> imageDescriptorModel, PropertyValueModel<String> textModel) {
		super();
		if (item == null) {
			throw new NullPointerException();
		}
		this.item = item;
		if (manager == null) {
			throw new NullPointerException();
		}
		this.manager = manager;
		if (imageDescriptorModel == null) {
			throw new NullPointerException();
		}
		this.imageDescriptorModel = imageDescriptorModel;
		if (textModel == null) {
			throw new NullPointerException();
		}
		this.textModel = textModel;

		this.imageDescriptorListener = this.buildImageDescriptorListener();
		this.imageDescriptorModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.imageDescriptorListener);
		this.imageDescriptor = this.imageDescriptorModel.getValue();

		this.textListener = this.buildTextListener();
		this.textModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.textListener);
		this.text = this.textModel.getValue();
	}


	// ********** image **********

	/**
	 * Return the image (lazy-initialized).
	 * <p><strong>NB:</strong>
	 * Wait until the UI requests a new image to dispose of the old image;
	 * because the old image is held and used by the UI (i.e. the UI does not
	 * call this method <em>every</em> time it accesses the image).
	 * Also, there should be no chance of a concurrent access to the image
	 * (as held elsewhere) while this method is executing, as this method is
	 * called from the UI thread and the other references to the image are
	 * accessed only from the UI thread (and those references will be replaced
	 * by the image returned by this method, while continuing on the UI thread).
	 */
	public Image getImage() {
		if (this.refreshImage) {
			if (this.image != null) {
				this.manager.getResourceManager().destroyImage(this.imageDescriptor);
			}
			this.image = this.buildImage();
			this.refreshImage = false;
		}
		return this.image;
	}

	private Image buildImage() {
		return (this.imageDescriptor == null) ? null : this.manager.getResourceManager().createImage(this.imageDescriptor);
	}

	private PropertyChangeListener buildImageDescriptorListener() {
		return SWTListenerWrapperTools.wrap(this.buildImageDescriptorListener_(), this.manager.getViewer());
	}

	private PropertyChangeListener buildImageDescriptorListener_() {
		return new ImageDescriptorListener();
	}

	/* CU private */ class ImageDescriptorListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			AbstractModelItemLabelProvider.this.imageDescriptorChanged((ImageDescriptor) event.getNewValue());
		}
	}

	/* CU private */ void imageDescriptorChanged(ImageDescriptor newValue) {
		if (this.isAlive()) {
			this.imageDescriptorChanged_(newValue);
		}
	}

	private void imageDescriptorChanged_(ImageDescriptor newValue) {
		this.refreshImage = true;
		this.imageDescriptor = newValue;
		this.manager.labelChanged(this.item);
	}


	// ********** text **********

	public String getText() {
		return this.text;
	}

	private PropertyChangeListener buildTextListener() {
		return SWTListenerWrapperTools.wrap(this.buildTextListener_(), this.manager.getViewer());
	}

	private PropertyChangeListener buildTextListener_() {
		return new TextListener();
	}

	/* CU private */ class TextListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			AbstractModelItemLabelProvider.this.textChanged((String) event.getNewValue());
		}
	}

	/* CU private */ void textChanged(String newValue) {
		if (this.isAlive()) {
			this.textChanged_(newValue);
		}
	}

	private void textChanged_(String newValue) {
		this.text = newValue;
		this.manager.labelChanged(this.item);
	}


	// ********** dispose **********

	public void dispose() {
		this.textModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.textListener);
		this.text = null;

		if (this.image != null) {
			this.manager.getResourceManager().destroyImage(this.imageDescriptor);
			this.image = null;
		}
		this.refreshImage = true;
		this.imageDescriptorModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.imageDescriptorListener);
		this.imageDescriptor = null;
		this.disposed = true;
	}


	// ********** misc **********

	/**
	 * Check whether the provider was disposed between the time an event was
	 * fired and the time the event is handled on the UI thread.
	 */
	/* private-protected */ boolean isAlive() {
		return ! this.disposed;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.item);
	}
}
