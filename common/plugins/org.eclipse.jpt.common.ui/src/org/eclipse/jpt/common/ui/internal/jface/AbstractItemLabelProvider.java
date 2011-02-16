/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
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
				getLabelProvider().updateLabel(getModel());
			}
		};
	}
	
	/**
	 * Return the image value model
	 * (lazy and just-in-time initialized)
	 */
	protected synchronized PropertyValueModel<Image> getImageModel() {
		if (this.imageModel == null) {
			this.imageModel = buildImageModel();
			engageImageModel();
		}
		return this.imageModel;
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
		this.imageModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.labelChangeListener);
	}
	
	/** 
	 * Should only be overridden with a call to super.disengageImageModel() after 
	 * subclass logic 
	 */
	protected void disengageImageModel() {
		this.imageModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.labelChangeListener);
	}
	
	/**
	 * Return the text value model
	 * (lazy and just-in-time initialized)
	 */
	protected synchronized PropertyValueModel<String> getTextModel() {
		if (this.textModel == null) {
			this.textModel = buildTextModel();
			engageTextModel();
		}
		return this.textModel;
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
		this.textModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.labelChangeListener);
	}
	
	/** 
	 * Should only be overridden with a call to super.disengageTextModel() after 
	 * subclass logic 
	 */
	protected void disengageTextModel() {
		this.textModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.labelChangeListener);
	}
	
	/**
	 * Return the description value model
	 * (lazy and just-in-time initialized)
	 */
	protected synchronized PropertyValueModel<String> getDescriptionModel() {
		if (this.descriptionModel == null) {
			this.descriptionModel = buildDescriptionModel();
			engageDescriptionModel();
		}
		return this.descriptionModel;
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
		this.descriptionModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.labelChangeListener);
	}
	
	/** 
	 * Should only be overridden with a call to super.disengageDescriptionModel() after 
	 * subclass logic 
	 */
	protected void disengageDescriptionModel() {
		this.descriptionModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.labelChangeListener);
	}
	
	/**
	 * Return the model object represented by this item
	 */
	public Model getModel() {
		return this.model;
	}
	
	/**
	 * Return the label provider that delegates to this item
	 */
	public DelegatingContentAndLabelProvider getLabelProvider() {
		return this.labelProvider;
	}
	
	public Image getImage() {
		return getImageModel().getValue();
	}
	
	public String getText() {
		return getTextModel().getValue();
	}
	
	public String getDescription() {
		return getDescriptionModel().getValue();
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
