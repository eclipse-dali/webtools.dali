/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.ui.internal.ELJaxbMappingImageHelper;

public class OxmJavaAttributeLabelProvider
		extends AbstractItemExtendedLabelProvider<OxmJavaAttribute> {
	
	public OxmJavaAttributeLabelProvider(OxmJavaAttribute oxmJavaAttribute, ItemExtendedLabelProvider.Manager manager) {
		super(oxmJavaAttribute, manager);
	}
	
	
	@Override
	protected PropertyValueModel<ImageDescriptor> buildImageDescriptorModel() {
		return new PropertyAspectAdapter<OxmJavaAttribute, ImageDescriptor>(OxmJavaAttribute.MAPPING_PROPERTY, this.item) {
			@Override
			protected ImageDescriptor buildValue_() {
				return OxmJavaAttributeLabelProvider.this.buildImageDescriptor(this.subject.getMappingKey());
			}
		};
	}

	protected ImageDescriptor buildImageDescriptor(String mappingKey) {
		return ELJaxbMappingImageHelper.imageDescriptorForAttributeMapping(mappingKey);
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<OxmJavaAttribute, String>(OxmJavaAttribute.JAVA_ATTRIBUTE_NAME_PROPERTY, this.item) {
			@Override
			protected String buildValue_() {
				return this.subject.getJavaAttributeName();
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return buildTextModel();
	}
}
