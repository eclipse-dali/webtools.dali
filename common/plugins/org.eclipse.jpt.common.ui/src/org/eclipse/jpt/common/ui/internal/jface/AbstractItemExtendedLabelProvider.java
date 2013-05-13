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
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

/**
 * Abstract {@link ItemLabelProvider} that provides support for listening to an
 * {@link #item} and notifying the
 * {@link org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider.Manager manager}
 * whenever the item changes in a significant way.
 * <p>
 * Subclasses can implement the following methods if the corresponding aspects
 * of the {@link #item} change:<ul>
 * <li>{@link #buildImageDescriptorModel()}<br>
 * 	   Return a {@link PropertyValueModel} that represents the item's image descriptor
 * <li>{@link #buildTextModel()}<br>
 * 	   Return a {@link PropertyValueModel} that represents the item's text
 * <li>{@link #buildDescriptionModel()}<br>
 * 	   Return a {@link PropertyValueModel} that represents the item's description
 * </ul>
 * Alternatively, subclasses can implement the following methods if the
 * corresponding aspects of the {@link #item} do <em>not</em> change:<ul>
 * <li>{@link #getImageDescriptor()}<br>
 * 	   Return the item's image descriptor
 * <li>{@link #getText()}<br>
 * 	   Return the item's text
 * <li>{@link #getDescription()}<br>
 * 	   Return the item's description
 * </ul>
 * For each aspect (image descriptor, text, and description) one and only one
 * of the two methods must be overridden.
 * 
 * @see StaticItemExtendedLabelProvider
 */
public abstract class AbstractItemExtendedLabelProvider<I>
	implements ItemExtendedLabelProvider
{
	protected final I item;

	protected final ItemExtendedLabelProvider.Manager manager;

	protected volatile PropertyValueModel<ImageDescriptor> imageDescriptorModel;

	protected volatile PropertyChangeListener imageDescriptorListener;

	protected volatile PropertyValueModel<String> textModel;

	protected volatile PropertyChangeListener textListener;

	protected volatile PropertyValueModel<String> descriptionModel;

	protected volatile PropertyChangeListener descriptionListener;


	protected AbstractItemExtendedLabelProvider(I item, ItemExtendedLabelProvider.Manager manager) {
		super();
		if (item == null) {
			throw new NullPointerException();
		}
		if (manager == null) {
			throw new NullPointerException();
		}
		this.item = item;
		this.manager = manager;
	}


	// ********** image **********

	public final Image getImage() {
		ImageDescriptor descriptor = this.getImageDescriptor();
		return (descriptor == null) ? null : this.manager.getResourceManager().createImage(descriptor);
	}

	protected ImageDescriptor getImageDescriptor() {
		return this.getImageDescriptorModel().getValue();
	}

	/**
	 * Return the image descriptor model (lazy-initialized).
	 */
	protected synchronized PropertyValueModel<ImageDescriptor> getImageDescriptorModel() {
		if (this.imageDescriptorModel == null) {
			this.imageDescriptorModel = this.buildImageDescriptorModel();
			this.engageImageDescriptorModel();
		}
		return this.imageDescriptorModel;
	}

	/**
	 * Construct an image descriptor model.
	 */
	protected PropertyValueModel<ImageDescriptor> buildImageDescriptorModel() {
		throw new UnsupportedOperationException();
	}

	protected void engageImageDescriptorModel() {
		this.imageDescriptorModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.getImageDescriptorListener());
	}

	protected void disengageImageDescriptorModel() {
		this.imageDescriptorModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.imageDescriptorListener);
	}

	protected void disposeImageDescriptorModel() {
		if (this.imageDescriptorModel != null) {
			// for now, leave the image in the resource manager cache
			// to be disposed when the view is disposed
			this.disengageImageDescriptorModel();
			this.imageDescriptorModel = null;
		}
	}


	// ********** image descriptor listener **********

	protected synchronized PropertyChangeListener getImageDescriptorListener() {
		if (this.imageDescriptorListener == null) {
			this.imageDescriptorListener = this.buildImageDescriptorListener();
		}
		return this.imageDescriptorListener;
	}

	/**
	 * Build a listener that will listen to the {@link #imageDescriptorModel}.
	 */
	protected PropertyChangeListener buildImageDescriptorListener() {
		return new ImageDescriptorListener();
	}

	/* CU private */ class ImageDescriptorListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			AbstractItemExtendedLabelProvider.this.imageDescriptorChanged();
		}
	}

	/* CU private */ void imageDescriptorChanged() {
		this.manager.updateLabel(this.item);
	}


	// ********** text **********

	public String getText() {
		return this.getTextModel().getValue();
	}

	/**
	 * Return the text model (lazy-initialized).
	 */
	protected synchronized PropertyValueModel<String> getTextModel() {
		if (this.textModel == null) {
			this.textModel = this.buildTextModel();
			this.engageTextModel();
		}
		return this.textModel;
	}

	/**
	 * Construct a text model.
	 */
	protected PropertyValueModel<String> buildTextModel() {
		throw new UnsupportedOperationException();
	}

	protected void engageTextModel() {
		this.textModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.getTextListener());
	}

	protected void disengageTextModel() {
		this.textModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.textListener);
	}

	protected void disposeTextModel() {
		if (this.textModel != null) {
			this.disengageTextModel();
			this.textModel = null;
		}
	}


	// ********** text listener **********

	protected synchronized PropertyChangeListener getTextListener() {
		if (this.textListener == null) {
			this.textListener = this.buildTextListener();
		}
		return this.textListener;
	}

	/**
	 * Build a listener that will listen to the {@link #textModel}.
	 */
	protected PropertyChangeListener buildTextListener() {
		return new TextListener();
	}

	/* CU private */ class TextListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			AbstractItemExtendedLabelProvider.this.textChanged();
		}
	}

	/* CU private */ void textChanged() {
		this.manager.updateLabel(this.item);
	}


	// ********** description **********

	public String getDescription() {
		return this.getDescriptionModel().getValue();
	}

	/**
	 * Return the description model (lazy-initialized).
	 */
	protected synchronized PropertyValueModel<String> getDescriptionModel() {
		if (this.descriptionModel == null) {
			this.descriptionModel = this.buildDescriptionModel();
			this.engageDescriptionModel();
		}
		return this.descriptionModel;
	}

	/**
	 * Construct a description model.
	 */
	protected PropertyValueModel<String> buildDescriptionModel() {
		throw new UnsupportedOperationException();
	}

	protected void engageDescriptionModel() {
		this.descriptionModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.getDescriptionListener());
	}

	protected void disengageDescriptionModel() {
		this.descriptionModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.descriptionListener);
	}

	protected void disposeDescriptionModel() {
		if (this.descriptionModel != null) {
			this.disengageDescriptionModel();
			this.descriptionModel = null;
		}
	}


	// ********** description listener **********

	protected synchronized PropertyChangeListener getDescriptionListener() {
		if (this.descriptionListener == null) {
			this.descriptionListener = this.buildDescriptionListener();
		}
		return this.descriptionListener;
	}

	/**
	 * Build a listener that will listen to the {@link #descriptionModel}.
	 */
	protected PropertyChangeListener buildDescriptionListener() {
		return new DescriptionListener();
	}

	/* CU private */ class DescriptionListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			AbstractItemExtendedLabelProvider.this.descriptionChanged();
		}
	}

	/* CU private */ void descriptionChanged() {
		this.manager.updateDescription(this.item);
	}


	// ********** dispose **********

	public synchronized void dispose() {
		this.disposeImageDescriptorModel();
		this.disposeTextModel();
		this.disposeDescriptionModel();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.item);
	}
}
