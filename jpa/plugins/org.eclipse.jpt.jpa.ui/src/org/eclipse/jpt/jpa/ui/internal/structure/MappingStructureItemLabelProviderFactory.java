/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.NullItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;

/**
 * This factory builds item label providers for the persistent types and
 * attributes in a JPA Structure View.
 */
public abstract class MappingStructureItemLabelProviderFactory
	implements ItemExtendedLabelProvider.Factory
{
	protected MappingStructureItemLabelProviderFactory() {
		super();
	}

	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		if (item instanceof PersistentType) {
			return buildPersistentTypeProvider((PersistentType) item, manager);
		}
		if (item instanceof PersistentAttribute) {
			return buildPersistentAttributeProvider((PersistentAttribute) item, manager);
		}
		return NullItemExtendedLabelProvider.instance();
	}


	// ********** persistent type **********

	/**
	 * shared method
	 */
	public static ItemExtendedLabelProvider buildPersistentTypeProvider(PersistentType persistentType, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				persistentType,
				manager,
				buildPersistentTypeImageDescriptorModel(persistentType),
				buildPersistentTypeTextModel(persistentType),
				buildPersistentTypeDescriptionModel(persistentType)
			);
	}

	protected static PropertyValueModel<ImageDescriptor> buildPersistentTypeImageDescriptorModel(PersistentType persistentType) {
		return new TransformationPropertyValueModel<TypeMapping, ImageDescriptor>(buildPersistentTypeMappingModel(persistentType), TYPE_MAPPING_IMAGE_DESCRIPTOR_TRANSFORMER);
	}
	
	protected static PropertyValueModel<TypeMapping> buildPersistentTypeMappingModel(PersistentType persistentType) {
		return new PersistentTypeMappingModel(persistentType);
	}

	public static class PersistentTypeMappingModel
		extends PropertyAspectAdapter<PersistentType, TypeMapping>
	{
		public PersistentTypeMappingModel(PersistentType subject) {
			super(PersistentType.MAPPING_PROPERTY, subject);
		}
		@Override
		protected TypeMapping buildValue_() {
			return this.subject.getMapping();
		}
	}

	protected static final Transformer<TypeMapping, ImageDescriptor> TYPE_MAPPING_IMAGE_DESCRIPTOR_TRANSFORMER = new TypeMappingImageDescriptorTransformer();

	/**
	 * Transform a type mapping into the appropriate image descriptor.
	 */
	public static class TypeMappingImageDescriptorTransformer
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

	protected static PropertyValueModel<String> buildPersistentTypeTextModel(PersistentType persistentType) {
		return new PersistentTypeTextModel(persistentType);
	}
	
	public static class PersistentTypeTextModel
		extends PropertyAspectAdapter<PersistentType, String>
	{
		public PersistentTypeTextModel(PersistentType subject) {
			super(ManagedType.NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getTypeQualifiedName();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected static PropertyValueModel<String> buildPersistentTypeDescriptionModel(PersistentType persistentType) {
		return PersistenceStructureItemLabelProviderFactory.buildNonQuotedComponentDescriptionModel(
					persistentType,
					buildPersistentTypeTextModel(persistentType)
				);
	}


	// ********** persistent attribute **********

	/**
	 * shared method
	 */
	public static ItemExtendedLabelProvider buildPersistentAttributeProvider(PersistentAttribute persistentAttribute, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				persistentAttribute,
				manager,
				buildPersistentAttributeImageDescriptorModel(persistentAttribute),
				buildPersistentAttributeTextModel(persistentAttribute),
				buildPersistentAttributeDescriptionModel(persistentAttribute)
			);
	}

	protected static PropertyValueModel<ImageDescriptor> buildPersistentAttributeImageDescriptorModel(PersistentAttribute persistentAttribute) {
		return new TransformationPropertyValueModel<AttributeMapping, ImageDescriptor>(buildPersistentAttributeMappingModel(persistentAttribute), ATTRIBUTE_MAPPING_IMAGE_DESCRIPTOR_TRANSFORMER);
	}

	protected static PropertyValueModel<AttributeMapping> buildPersistentAttributeMappingModel(PersistentAttribute persistentAttribute) {
		return new PersistentAttributeMappingModel(persistentAttribute);
	}

	public static class PersistentAttributeMappingModel
		extends PropertyAspectAdapter<PersistentAttribute, AttributeMapping>
	{
		public PersistentAttributeMappingModel(PersistentAttribute subject) {
			super(PersistentAttribute.MAPPING_PROPERTY, subject);
		}
		@Override
		protected AttributeMapping buildValue_() {
			return this.subject.getMapping();
		}
	}

	protected static final Transformer<AttributeMapping, ImageDescriptor> ATTRIBUTE_MAPPING_IMAGE_DESCRIPTOR_TRANSFORMER = new AttributeMappingImageDescriptorTransformer();

	/**
	 * Transform an attribute mapping into the appropriate image descriptor.
	 */
	public static class AttributeMappingImageDescriptorTransformer
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

	protected static PropertyValueModel<String> buildPersistentAttributeTextModel(PersistentAttribute persistentAttribute) {
		return new PersistentAttributeTextModel(persistentAttribute);
	}

	public static class PersistentAttributeTextModel
		extends PropertyAspectAdapter<PersistentAttribute, String>
	{
		public PersistentAttributeTextModel(PersistentAttribute subject) {
			super(PersistentAttribute.NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getName();
		}
	}

	@SuppressWarnings("unchecked")
	protected static PropertyValueModel<String> buildPersistentAttributeDescriptionModel(PersistentAttribute persistentAttribute) {
		return PersistenceStructureItemLabelProviderFactory.buildNonQuotedComponentDescriptionModel(
					persistentAttribute,
					buildTypeTextModel(persistentAttribute),
					buildPersistentAttributeTextModel(persistentAttribute)
				);
	}

	protected static PropertyValueModel<String> buildTypeTextModel(PersistentAttribute persistentAttribute) {
		return new PersistentTypeTextModel(persistentAttribute.getDeclaringPersistentType());
	}

	@Override
	public String toString() {
		return ObjectTools.singletonToString(this);
	}
}
