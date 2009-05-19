/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

/**
 * Implementation of {@link ItemLabelProvider} that provides updating
 * label information for a Model object.
 * 
 * The typical subclass will override the following methods:
 * #buildImageModel()
 * 	   return a {@link PropertyValueModel} that represents the image for the 
 *     represented model object
 * #buildTextModel()
 *     return a {@link PropertyValueModel} that represents the text for the 
 *     represented model object.
 * #buildDescriptionModel()
 * 	   return a {@link PropertyValueModel} that represents the description for 
 *     the represented model object
 * 
 * Other methods may be overridden, but take care to preserve the logic provided 
 * by this class.
 */
public abstract class AbstractItemLabelProvider implements ItemLabelProvider
{
	private DelegatingContentAndLabelProvider labelProvider;
	
	private Model model;
	
	private PropertyValueModel<Image> imageModel;
	
	private PropertyValueModel<String> textModel;
	
	private PropertyValueModel<String> descriptionModel;
	
	private PropertyChangeListener labelChangeListener;
	
	
	protected AbstractItemLabelProvider(
			Model model, DelegatingContentAndLabelProvider labelProvider) {
		this.model = model;
		this.labelProvider = labelProvider;
		this.labelChangeListener = buildLabelChangeListener();
	}
	
	
	/**
	 * Construct a listener to update the viewer (through the label provider)
	 * if the text or image changes
	 */
	protected PropertyChangeListener buildLabelChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				labelProvider().updateLabel(model());
			}
		};
	}
	
	/**
	 * Return the image value model
	 * (lazy and just-in-time initialized)
	 */
	protected synchronized PropertyValueModel<Image> imageModel() {
		if (imageModel == null) {
			imageModel = buildImageModel();
			engageImageModel();
		}
		return imageModel;
	}
	
	/**
	 * Construct an image model
	 */
	protected abstract PropertyValueModel<Image> buildImageModel();
	
	/** 
	 * Should only be overridden with a call to super.engageImageModel() before 
	 * subclass logic 
	 */
	protected void engageImageModel() {
		imageModel.addPropertyChangeListener(PropertyValueModel.VALUE, labelChangeListener);
	}
	
	/** 
	 * Should only be overridden with a call to super.disengageImageModel() after 
	 * subclass logic 
	 */
	protected void disengageImageModel() {
		imageModel.removePropertyChangeListener(PropertyValueModel.VALUE, labelChangeListener);
	}
	
	/**
	 * Return the text value model
	 * (lazy and just-in-time initialized)
	 */
	protected synchronized PropertyValueModel<String> textModel() {
		if (textModel == null) {
			textModel = buildTextModel();
			engageTextModel();
		}
		return textModel;
	}
	
	/**
	 * Construct a text value model
	 */
	protected abstract PropertyValueModel<String> buildTextModel();
	
	/** 
	 * Should only be overridden with a call to super.engageTextModel() before 
	 * subclass logic 
	 */
	protected void engageTextModel() {
		textModel.addPropertyChangeListener(PropertyValueModel.VALUE, labelChangeListener);
	}
	
	/** 
	 * Should only be overridden with a call to super.disengageTextModel() after 
	 * subclass logic 
	 */
	protected void disengageTextModel() {
		textModel.removePropertyChangeListener(PropertyValueModel.VALUE, labelChangeListener);
	}
	
	/**
	 * Return the description value model
	 * (lazy and just-in-time initialized)
	 */
	protected synchronized PropertyValueModel<String> descriptionModel() {
		if (descriptionModel == null) {
			descriptionModel = buildDescriptionModel();
			engageDescriptionModel();
		}
		return descriptionModel;
	}
	
	/**
	 * Construct a description value model
	 */
	protected abstract PropertyValueModel<String> buildDescriptionModel();
	
	/** 
	 * Should only be overridden with a call to super.engageDescriptionModel() before 
	 * subclass logic 
	 */
	protected void engageDescriptionModel() {
		descriptionModel.addPropertyChangeListener(PropertyValueModel.VALUE, labelChangeListener);
	}
	
	/** 
	 * Should only be overridden with a call to super.disengageDescriptionModel() after 
	 * subclass logic 
	 */
	protected void disengageDescriptionModel() {
		descriptionModel.removePropertyChangeListener(PropertyValueModel.VALUE, labelChangeListener);
	}
	
	/**
	 * Return the model object represented by this item
	 */
	public Model model() {
		return model;
	}
	
	/**
	 * Return the label provider that delegates to this item
	 */
	public DelegatingContentAndLabelProvider labelProvider() {
		return labelProvider;
	}
	
	public Image getImage() {
		return imageModel().getValue();
	}
	
	public String getText() {
		return textModel().getValue();
	}
	
	public String getDescription() {
		return descriptionModel().getValue();
	}
	
	public void dispose() {
		disposeTextModel();
		disposeImageModel();
		disposeDescriptionModel();	
	}
	
	protected synchronized void disposeTextModel() {
		if (this.textModel != null) {
			disengageTextModel();
			this.textModel = null;
		}
	}
	
	protected synchronized void disposeImageModel() {
		if (this.imageModel != null) {
			disengageImageModel();
			this.imageModel = null;
		}
	}
	
	protected synchronized void disposeDescriptionModel() {
		if (this.descriptionModel != null) {
			disengageDescriptionModel();
			this.descriptionModel = null;
		}
	}
}
