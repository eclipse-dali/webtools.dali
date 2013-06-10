/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.jface.ModelItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlEnum;
import org.eclipse.jpt.jaxb.eclipselink.ui.JptJaxbEclipseLinkUiImages;
import org.eclipse.jpt.jaxb.eclipselink.ui.internal.ELJaxbMappingImageHelper;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;
import org.eclipse.jpt.jaxb.ui.internal.AbstractNavigatorItemLabelProviderFactory;


public class ELJaxbNavigatorItemLabelProviderFactory
	extends AbstractNavigatorItemLabelProviderFactory
{
	private static ItemExtendedLabelProvider.Factory INSTANCE = new ELJaxbNavigatorItemLabelProviderFactory();
	
	
	public static ItemExtendedLabelProvider.Factory instance() {
		return INSTANCE;
	}
	
	
	private ELJaxbNavigatorItemLabelProviderFactory() {
		super();
	}
	
	
	@Override
	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		
		if (item instanceof OxmFile) {
			return buildOxmFileLabelProvider((OxmFile) item, manager);
		}
		else if (item instanceof OxmXmlEnum) {
			return buildOxmXmlEnumLabelProvider((OxmXmlEnum) item, manager);
		}
		else if (item instanceof OxmJavaType) {
			return buildOxmJavaTypeLabelProvider((OxmJavaType) item, manager);
		}
		else if (item instanceof OxmJavaAttribute) {
			return buildOxmJavaAttributeLabelProvider((OxmJavaAttribute) item, manager);
		}
		
		return super.buildProvider(item, manager);
	}

	@Override
	protected ImageDescriptor buildJavaPersistentAttributeImageDescriptor(String mappingKey) {
		return ELJaxbMappingImageHelper.imageDescriptorForAttributeMapping(mappingKey);
	}

	protected ItemExtendedLabelProvider buildOxmFileLabelProvider(OxmFile file, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJaxbEclipseLinkUiImages.OXM_FILE,
					buildOxmFileText(file),
					buildOxmFileDescription(file),
					manager
				);
	}
	
	protected String buildOxmFileText(OxmFile file) {
		StringBuffer text = new StringBuffer();
		IPath path = file.getOxmResource().getFile().getRawLocation();
		text.append(path.lastSegment());
		text.append(" - "); //$NON-NLS-1$
		text.append(path.removeLastSegments(1).toOSString());
		return text.toString();
	}
	
	protected String buildOxmFileDescription(OxmFile file) {
		// the same, for now
		return buildOxmFileText(file);
	}
	

	// ********** oxm java type **********

	protected ItemExtendedLabelProvider buildOxmJavaTypeLabelProvider(OxmJavaType item, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				item,
				manager,
				this.buildOxmJavaTypeImageDescriptorModel(item),
				this.buildOxmJavaTypeTextModel(item),
				this.buildOxmJavaTypeDescriptionModel(item)
			);
	}

	protected PropertyValueModel<ImageDescriptor> buildOxmJavaTypeImageDescriptorModel(@SuppressWarnings("unused") OxmJavaType item) {
		return new StaticPropertyValueModel<ImageDescriptor>(JptJaxbUiImages.JAXB_CLASS);
	}

	protected PropertyValueModel<String> buildOxmJavaTypeTextModel(OxmJavaType item) {
		return new PropertyAspectAdapter<OxmJavaType, String>(OxmTypeMapping.TYPE_NAME_PROPERTY, item) {
			@Override
			protected String buildValue_() {
				return this.subject.getTypeName().getTypeQualifiedName();
			}
		};
	}

	protected PropertyValueModel<String> buildOxmJavaTypeDescriptionModel(OxmJavaType item) {
		return new PropertyAspectAdapter<OxmJavaType, String>(OxmTypeMapping.TYPE_NAME_PROPERTY, item) {
			@Override
			protected String buildValue_() {
				return this.subject.getTypeName().getFullyQualifiedName();
			}
		};
	}
	

	// ********** oxm xml enum **********

	protected ItemExtendedLabelProvider buildOxmXmlEnumLabelProvider(OxmXmlEnum item, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				item,
				manager,
				this.buildOxmXmlEnumImageDescriptorModel(item),
				this.buildOxmXmlEnumTextModel(item),
				this.buildOxmXmlEnumDescriptionModel(item)
			);
	}

	protected PropertyValueModel<ImageDescriptor> buildOxmXmlEnumImageDescriptorModel(@SuppressWarnings("unused") OxmXmlEnum item) {
		return new StaticPropertyValueModel<ImageDescriptor>(JptJaxbUiImages.JAXB_ENUM);
	}
	
	protected PropertyValueModel<String> buildOxmXmlEnumTextModel(OxmXmlEnum item) {
		return new PropertyAspectAdapter<OxmXmlEnum, String>(OxmTypeMapping.TYPE_NAME_PROPERTY, item) {
			@Override
			protected String buildValue_() {
				return this.subject.getTypeName().getTypeQualifiedName();
			}
		};
	}

	protected PropertyValueModel<String> buildOxmXmlEnumDescriptionModel(OxmXmlEnum item) {
		return new PropertyAspectAdapter<OxmXmlEnum, String>(OxmTypeMapping.TYPE_NAME_PROPERTY, item) {
			@Override
			protected String buildValue_() {
				return this.subject.getTypeName().getFullyQualifiedName();
			}
		};
	}
	

	// ********** oxm java attribute **********

	protected ItemExtendedLabelProvider buildOxmJavaAttributeLabelProvider(OxmJavaAttribute item, ItemExtendedLabelProvider.Manager manager) {
		return new ModelItemExtendedLabelProvider(
				item,
				manager,
				this.buildOxmJavaAttributeImageDescriptorModel(item),
				this.buildOxmJavaAttributeTextModel(item),
				this.buildOxmJavaAttributeDescriptionModel(item)
			);
	}

	protected PropertyValueModel<ImageDescriptor> buildOxmJavaAttributeImageDescriptorModel(OxmJavaAttribute item) {
		return new PropertyAspectAdapter<OxmJavaAttribute, ImageDescriptor>(JaxbPersistentAttribute.MAPPING_PROPERTY, item) {
			@Override
			protected ImageDescriptor buildValue_() {
				return ELJaxbNavigatorItemLabelProviderFactory.this.buildOxmJavaAttributeImageDescriptor(this.subject.getMappingKey());
			}
		};
	}

	protected ImageDescriptor buildOxmJavaAttributeImageDescriptor(String mappingKey) {
		return ELJaxbMappingImageHelper.imageDescriptorForAttributeMapping(mappingKey);
	}
	
	protected PropertyValueModel<String> buildOxmJavaAttributeTextModel(OxmJavaAttribute item) {
		return new PropertyAspectAdapter<OxmJavaAttribute, String>(OxmJavaAttribute.JAVA_ATTRIBUTE_NAME_PROPERTY, item) {
			@Override
			protected String buildValue_() {
				return this.subject.getJavaAttributeName();
			}
		};
	}

	protected PropertyValueModel<String> buildOxmJavaAttributeDescriptionModel(OxmJavaAttribute item) {
		return buildOxmJavaAttributeTextModel(item);
	}
}
