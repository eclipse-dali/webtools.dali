/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;

public class PersistentAttributeItemLabelProvider
	extends AbstractItemExtendedLabelProvider<PersistentAttribute>
{
	public PersistentAttributeItemLabelProvider(PersistentAttribute persistentAttribute, ItemExtendedLabelProvider.Manager manager) {
		super(persistentAttribute, manager);
	}


	// ********** image **********

	@Override
	protected PropertyValueModel<ImageDescriptor> buildImageDescriptorModel() {
		return new TransformationPropertyValueModel<AttributeMapping, ImageDescriptor>(this.buildMappingModel(), IMAGE_DESCRIPTOR_TRANSFORMER);
	}

	protected PropertyValueModel<AttributeMapping> buildMappingModel() {
		return new MappingModel(this.item);
	}

	protected static class MappingModel
		extends PropertyAspectAdapter<PersistentAttribute, AttributeMapping>
	{
		public MappingModel(PersistentAttribute subject) {
			super(PersistentAttribute.MAPPING_PROPERTY, subject);
		}
		@Override
		protected AttributeMapping buildValue_() {
			return this.subject.getMapping();
		}
	}

	protected static final Transformer<AttributeMapping, ImageDescriptor> IMAGE_DESCRIPTOR_TRANSFORMER = new ImageDescriptorTransformer();

	/**
	 * Transform an attribute mapping into the appropriate image descriptor.
	 */
	protected static class ImageDescriptorTransformer
		extends TransformerAdapter<AttributeMapping, ImageDescriptor>
	{
		@Override
		public ImageDescriptor transform(AttributeMapping attributeMapping) {
			MappingUiDefinition definition = this.getAttributeMappingUiDefinition(attributeMapping);
			return (definition == null) ? null : JptCommonUiImages.gray(definition.getImageDescriptor(), attributeMapping.getPersistentAttribute().isVirtual());
		}

		private MappingUiDefinition getAttributeMappingUiDefinition(AttributeMapping attributeMapping) {
			JpaPlatformUi ui = this.getJpaPlatformUi(attributeMapping);
			return (ui == null) ? null : ui.getAttributeMappingUiDefinition(attributeMapping.getResourceType(), attributeMapping.getKey());
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
		extends PropertyAspectAdapter<PersistentAttribute, String>
	{
		public TextModel(PersistentAttribute subject) {
			super(PersistentAttribute.NAME_PROPERTY, subject);
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
		return new PersistentTypeItemLabelProvider.TextModel(this.item.getDeclaringPersistentType());
	}
}
