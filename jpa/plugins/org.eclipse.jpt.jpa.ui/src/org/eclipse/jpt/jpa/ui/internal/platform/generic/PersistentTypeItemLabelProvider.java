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
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;

public class PersistentTypeItemLabelProvider
	extends AbstractItemExtendedLabelProvider<PersistentType>
{
	public PersistentTypeItemLabelProvider(PersistentType persistentType, ItemExtendedLabelProvider.Manager manager) {
		super(persistentType, manager);
	}
	

	// ********** image **********

	@Override
	protected PropertyValueModel<ImageDescriptor> buildImageDescriptorModel() {
		return new TransformationPropertyValueModel<TypeMapping, ImageDescriptor>(this.buildMappingModel(), IMAGE_DESCRIPTOR_TRANSFORMER);
	}
	
	protected PropertyValueModel<TypeMapping> buildMappingModel() {
		return new MappingModel(this.item);
	}

	protected static class MappingModel
		extends PropertyAspectAdapter<PersistentType, TypeMapping>
	{
		public MappingModel(PersistentType subject) {
			super(PersistentType.MAPPING_PROPERTY, subject);
		}
		@Override
		protected TypeMapping buildValue_() {
			return this.subject.getMapping();
		}
	}

	protected static final Transformer<TypeMapping, ImageDescriptor> IMAGE_DESCRIPTOR_TRANSFORMER = new ImageDescriptorTransformer();

	/**
	 * Transform a type mapping into the appropriate image descriptor.
	 */
	protected static class ImageDescriptorTransformer
		extends TransformerAdapter<TypeMapping, ImageDescriptor>
	{
		@Override
		public ImageDescriptor transform(TypeMapping typeMapping) {
			MappingUiDefinition definition = this.getTypeMappingUiDefinition(typeMapping);
			return (definition == null) ? null : definition.getImageDescriptor();
		}

		private MappingUiDefinition getTypeMappingUiDefinition(TypeMapping typeMapping) {
			JpaPlatformUi ui = this.getJpaPlatformUi(typeMapping);
			return (ui == null) ? null : ui.getTypeMappingUiDefinition(typeMapping.getResourceType(), typeMapping.getKey());
		}

		private JpaPlatformUi getJpaPlatformUi(TypeMapping typeMapping) {
			return (JpaPlatformUi) typeMapping.getJpaProject().getJpaPlatform().getAdapter(JpaPlatformUi.class);
		}
	}


	// ********** text **********

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
	

	// ********** description **********

	@Override
	@SuppressWarnings("unchecked")
	protected PropertyValueModel<String> buildDescriptionModel() {
		return PersistenceUnitItemLabelProvider.buildNonQuotedComponentDescriptionModel(
					this.item,
					this.textModel
				);
	}
}
