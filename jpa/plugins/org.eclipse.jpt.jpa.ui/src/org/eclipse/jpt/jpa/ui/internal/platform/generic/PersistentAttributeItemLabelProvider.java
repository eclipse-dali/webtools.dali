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
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.swt.graphics.Image;

public class PersistentAttributeItemLabelProvider
	extends AbstractItemExtendedLabelProvider<ReadOnlyPersistentAttribute>
{
	public PersistentAttributeItemLabelProvider(ReadOnlyPersistentAttribute persistentAttribute, ItemLabelProvider.Manager manager) {
		super(persistentAttribute, manager);
	}

	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new ImageModel(this.item);
	}

	protected static class ImageModel
		extends PropertyAspectAdapter<ReadOnlyPersistentAttribute, Image>
	{
		public ImageModel(ReadOnlyPersistentAttribute subject) {
			super(ReadOnlyPersistentAttribute.MAPPING_PROPERTY, subject);
		}
		@Override
		protected Image buildValue_() {
			String mappingKey = this.subject.getMappingKey();
			return this.subject.isVirtual() ?
					JptUiIcons.ghost(JpaMappingImageHelper.iconKeyForAttributeMapping(mappingKey)) :
					JpaMappingImageHelper.imageForAttributeMapping(mappingKey);
		}
	}

	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new TextModel(this.item);
	}

	protected static class TextModel
		extends PropertyAspectAdapter<ReadOnlyPersistentAttribute, String>
	{
		public TextModel(ReadOnlyPersistentAttribute subject) {
			super(ReadOnlyPersistentAttribute.NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getName();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected PropertyValueModel<String> buildDescriptionModel() {
		return PersistenceUnitItemLabelProvider.buildNonQuotedComponentDescriptionModel(
					this.item,
					this.buildTypeTextModel(),
					this.textModel
				);
	}

	protected PropertyValueModel<String> buildTypeTextModel() {
		return new PersistentTypeItemLabelProvider.TextModel(this.item.getOwningPersistentType());
	}
}
