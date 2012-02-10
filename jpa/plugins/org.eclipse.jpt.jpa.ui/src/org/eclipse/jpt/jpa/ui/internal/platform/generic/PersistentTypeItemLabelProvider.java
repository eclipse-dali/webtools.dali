/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.ui.internal.JpaMappingImageHelper;
import org.eclipse.swt.graphics.Image;

public class PersistentTypeItemLabelProvider
	extends AbstractItemExtendedLabelProvider<PersistentType>
{
	public PersistentTypeItemLabelProvider(PersistentType persistentType, ItemLabelProvider.Manager manager) {
		super(persistentType, manager);
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new ImageModel(this.item);
	}
	
	protected static class ImageModel
		extends PropertyAspectAdapter<PersistentType, Image>
	{
		public ImageModel(PersistentType subject) {
			super(PersistentType.MAPPING_PROPERTY, subject);
		}
		@Override
		protected Image buildValue_() {
			return JpaMappingImageHelper.imageForTypeMapping(this.subject.getMappingKey());
		}
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new TextModel(this.item);
	}
	
	protected static class TextModel
		extends PropertyAspectAdapter<PersistentType, String>
	{
		public TextModel(PersistentType subject) {
			super(PersistentType.NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getSimpleName();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected PropertyValueModel<String> buildDescriptionModel() {
		return PersistenceUnitItemLabelProvider.buildNonQuotedComponentDescriptionModel(
					this.item,
					this.textModel
				);
	}
}
