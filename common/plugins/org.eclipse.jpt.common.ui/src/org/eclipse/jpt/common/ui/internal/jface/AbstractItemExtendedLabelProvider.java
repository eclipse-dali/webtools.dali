/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

/**
 * Abstract {@link ItemLabelProvider} that provides support for listening to an
 * {@link #item} and notifying the
 * {@link org.eclipse.jpt.common.ui.jface.ItemLabelProvider.Manager manager}
 * whenever the item changes in a significant way.
 * <p>
 * Subclasses can implement the following methods if the corresponding aspects
 * of the {@link #item} change:<ul>
 * <li>{@link #buildImageModel()}<br>
 * 	   Return a {@link PropertyValueModel} that represents the item's image
 * <li>{@link #buildTextModel()}<br>
 * 	   Return a {@link PropertyValueModel} that represents the item's text
 * <li>{@link #buildDescriptionModel()}<br>
 * 	   Return a {@link PropertyValueModel} that represents the item's description
 * </ul>
 * Alternatively, subclasses can implement the following methods if the
 * corresponding aspects of the {@link #item} do <em>not</em> change:<ul>
 * <li>{@link #getImage()}<br>
 * 	   Return the item's image
 * <li>{@link #getText()}<br>
 * 	   Return the item's text
 * <li>{@link #getDescription()}<br>
 * 	   Return the item's description
 * </ul>
 * For each aspect (image, text, and description) one and only one of the two
 * methods must be overridden.
 * 
 * @see StaticItemExtendedLabelProvider
 */
public abstract class AbstractItemExtendedLabelProvider<I>
	implements ItemExtendedLabelProvider
{
	protected final ItemLabelProvider.Manager manager;

	protected final I item;

	protected volatile PropertyChangeListener listener;

	protected volatile PropertyValueModel<Image> imageModel;

	protected volatile PropertyValueModel<String> textModel;

	protected volatile PropertyValueModel<String> descriptionModel;


	protected AbstractItemExtendedLabelProvider(I item, ItemLabelProvider.Manager manager) {
		this.item = item;
		this.manager = manager;
	}


	// ********** image **********

	public Image getImage() {
		return this.getImageModel().getValue();
	}

	/**
	 * Return the image model (lazy-initialized).
	 */
	protected synchronized PropertyValueModel<Image> getImageModel() {
		if (this.imageModel == null) {
			this.imageModel = this.buildImageModel();
			this.engageImageModel();
		}
		return this.imageModel;
	}

	/**
	 * Construct an image model.
	 */
	protected PropertyValueModel<Image> buildImageModel() {
		throw new UnsupportedOperationException();
	}

	protected void engageImageModel() {
		this.imageModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.getListener());
	}

	protected void disengageImageModel() {
		this.imageModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener);
	}

	protected void disposeImageModel() {
		if (this.imageModel != null) {
			this.disengageImageModel();
			this.imageModel = null;
		}
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
		this.textModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.getListener());
	}

	protected void disengageTextModel() {
		this.textModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener);
	}

	protected void disposeTextModel() {
		if (this.textModel != null) {
			this.disengageTextModel();
			this.textModel = null;
		}
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
		this.descriptionModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.getListener());
	}

	protected void disengageDescriptionModel() {
		this.descriptionModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener);
	}

	protected void disposeDescriptionModel() {
		if (this.descriptionModel != null) {
			this.disengageDescriptionModel();
			this.descriptionModel = null;
		}
	}


	// ********** listener **********

	protected synchronized PropertyChangeListener getListener() {
		if (this.listener == null) {
			this.listener = this.buildListener();
		}
		return this.listener;
	}

	/**
	 * Build a listener that will listen to the {@link #imageModel},
	 * {@link #textModel}, and {@link #descriptionModel}.
	 */
	protected PropertyChangeListener buildListener() {
		return new Listener();
	}

	/* CU private */ class Listener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			AbstractItemExtendedLabelProvider.this.itemChanged();
		}
	}

	/* CU private */ void itemChanged() {
		this.manager.updateLabel(this.item);
	}


	// ********** dispose **********

	public synchronized void dispose() {
		this.disposeImageModel();
		this.disposeTextModel();
		this.disposeDescriptionModel();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.item);
	}
}
