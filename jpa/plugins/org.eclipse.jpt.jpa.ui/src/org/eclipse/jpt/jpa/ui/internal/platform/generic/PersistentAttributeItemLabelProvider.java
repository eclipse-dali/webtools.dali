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
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.graphics.Image;

public class PersistentAttributeItemLabelProvider
	extends AbstractItemExtendedLabelProvider<ReadOnlyPersistentAttribute>
{
	public PersistentAttributeItemLabelProvider(ReadOnlyPersistentAttribute persistentAttribute, ItemLabelProvider.Manager manager) {
		super(persistentAttribute, manager);
	}


	// ********** image **********

	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new TransformationPropertyValueModel<AttributeMapping, Image>(this.buildMappingModel(), IMAGE_TRANSFORMER);
	}

	protected PropertyValueModel<AttributeMapping> buildMappingModel() {
		return new MappingModel(this.item);
	}

	protected static class MappingModel
		extends PropertyAspectAdapter<ReadOnlyPersistentAttribute, AttributeMapping>
	{
		public MappingModel(ReadOnlyPersistentAttribute subject) {
			super(ReadOnlyPersistentAttribute.MAPPING_PROPERTY, subject);
		}
		@Override
		protected AttributeMapping buildValue_() {
			return this.subject.getMapping();
		}
	}

	protected static final Transformer<AttributeMapping, Image> IMAGE_TRANSFORMER = new ImageTransformer();

	/**
	 * Transform an attribute mapping into the appropriate image.
	 */
	protected static class ImageTransformer
		extends TransformerAdapter<AttributeMapping, Image>
	{
		@Override
		public Image transform(AttributeMapping attributeMapping) {
			MappingUiDefinition<? extends ReadOnlyPersistentAttribute, ?> definition = this.getAttributeMappingUiDefinition(attributeMapping);
			return attributeMapping.getPersistentAttribute().isVirtual() ?
					definition.getGhostImage() :
					definition.getImage();
		}

		private MappingUiDefinition<? extends ReadOnlyPersistentAttribute, ?> getAttributeMappingUiDefinition(AttributeMapping attributeMapping) {
			return this.getJpaPlatformUi(attributeMapping).getAttributeMappingUiDefinition(attributeMapping.getResourceType(), attributeMapping.getKey());
		}

		private JpaPlatformUi getJpaPlatformUi(AttributeMapping attributeMapping) {
			return (JpaPlatformUi) attributeMapping.getJpaProject().getJpaPlatform().getAdapter(JpaPlatformUi.class);
		}
	}


	// ********** text **********

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


	// ********** description **********

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
