package org.eclipse.jpt.ui.internal.jface;

import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

/**
 * Implementation of {@link IItemLabelProvider} that provides updating
 * label information for a Model object.
 * 
 * The typical subclass will override the following methods:
 * #buildTextModel()
 *     return a {@link PropertyValueModel} that represents the text for the represented
 *     model object.
 * #buildImageModel()
 * 	   return a {@link PropertyValueModel} that represents the image for the represented
 * 	   model object
 * 
 * Other methods may be overridden, but take care to preserve the logic provided 
 * by this class.
 */
public abstract class AbstractItemLabelProvider implements IItemLabelProvider
{
	private DelegatingLabelProvider labelProvider;
	
	private Model model;
	
	private PropertyValueModel<String> textModel;
	
	private PropertyValueModel<Image> imageModel;
	
	private PropertyChangeListener labelChangeListener;
	
	
	protected AbstractItemLabelProvider(Model model, DelegatingLabelProvider labelProvider) {
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
	 * Return the text value model
	 * (lazy and just-in-time initialized)
	 */
	protected PropertyValueModel<String> textModel() {
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
		if (textModel != null) {
			textModel.removePropertyChangeListener(PropertyValueModel.VALUE, labelChangeListener);
		}
	}
	
	/**
	 * Return the image value model
	 * (lazy and just-in-time initialized)
	 */
	protected PropertyValueModel<Image> imageModel() {
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
		if (imageModel != null) {
			imageModel.removePropertyChangeListener(PropertyValueModel.VALUE, labelChangeListener);
		}
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
	public DelegatingLabelProvider labelProvider() {
		return labelProvider;
	}
	
	public String text() {
		return textModel().value();
	}
	
	public Image image() {
		return imageModel().value();
	}
	
	public void dispose() {
		disengageTextModel();
		disengageImageModel();
	}
}
