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
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * Model {@link ItemExtendedLabelProvider label provider} that provides
 * support for listening to an {@link #item} and notifying the
 * {@link org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider.Manager manager}
 * whenever the item changes in a significant way. Clients must supply an
 * {@link PropertyValueModel image model}, a {@link PropertyValueModel text
 * model}, and a {@link PropertyValueModel description model}.
 * Any of the models can be <em>static</em> for the aspects
 * of the {@link #item} that do <em>not</em> change.
 * 
 * @see StaticItemExtendedLabelProvider
 */
public class ModelItemExtendedLabelProvider
	extends AbstractModelItemLabelProvider<ItemExtendedLabelProvider.Manager>
	implements ItemExtendedLabelProvider
{
	private final PropertyValueModel<String> descriptionModel;
	private final PropertyChangeListener descriptionListener;
	private String description;  // must be accessed on the manager's UI thread


	public ModelItemExtendedLabelProvider(
			Object item,
			ItemExtendedLabelProvider.Manager manager,
			PropertyValueModel<ImageDescriptor> imageDescriptorModel,
			PropertyValueModel<String> textModel,
			PropertyValueModel<String> descriptionModel
	) {
		super(item, manager, imageDescriptorModel, textModel);

		if (descriptionModel == null) {
			throw new NullPointerException();
		}
		this.descriptionModel = descriptionModel;
		this.descriptionListener = this.buildDescriptionListener();
		this.descriptionModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.descriptionListener);
		this.description = this.descriptionModel.getValue();
	}


	// ********** description **********

	public String getDescription() {
		return this.description;
	}

	private PropertyChangeListener buildDescriptionListener() {
		return SWTListenerTools.wrap(this.buildDescriptionListener_(), this.manager.getViewer());
	}

	private PropertyChangeListener buildDescriptionListener_() {
		return new DescriptionListener();
	}

	/* CU private */ class DescriptionListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			ModelItemExtendedLabelProvider.this.descriptionChanged((String) event.getNewValue());
		}
	}

	/* CU private */ void descriptionChanged(String newValue) {
		if (this.isAlive()) {
			this.descriptionChanged_(newValue);
		}
	}

	private void descriptionChanged_(String newValue) {
		this.description = newValue;
		this.manager.descriptionChanged(this.item);
	}


	// ********** dispose **********

	@Override
	public void dispose() {
		this.descriptionModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.descriptionListener);
		this.description = null;
		super.dispose();
	}
}
