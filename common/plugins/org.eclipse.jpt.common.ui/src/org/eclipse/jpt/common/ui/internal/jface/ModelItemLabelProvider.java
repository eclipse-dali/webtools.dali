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
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * Model {@link ItemLabelProvider label provider} that provides
 * support for listening to an {@link #item} and notifying the
 * {@link org.eclipse.jpt.common.ui.jface.ItemLabelProvider.Manager manager}
 * whenever the item changes in a significant way. Clients must supply an
 * {@link PropertyValueModel image model} and a {@link PropertyValueModel text
 * model}. Either of the models can be <em>static</em> for the aspects
 * of the {@link #item} that do <em>not</em> change.
 * 
 * @see StaticItemLabelProvider
 */
public class ModelItemLabelProvider
	extends AbstractModelItemLabelProvider<ItemLabelProvider.Manager>
{
	public ModelItemLabelProvider(Object item, ItemLabelProvider.Manager manager, PropertyValueModel<ImageDescriptor> imageDescriptorModel, PropertyValueModel<String> textModel) {
		super(item, manager, imageDescriptorModel, textModel);
	}
}
