/*******************************************************************************
 * Copyright (c) 2011, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;

public class JaxbMappingKeyImageDescriptorTransformer
	extends TransformerAdapter<String, ImageDescriptor>
{
	private static final Transformer<String, ImageDescriptor> INSTANCE = new JaxbMappingKeyImageDescriptorTransformer();

	public static Transformer<String, ImageDescriptor> instance() {
		return INSTANCE;
	}

	private JaxbMappingKeyImageDescriptorTransformer() {
		super();
	}

	@Override
	public ImageDescriptor transform(String mappingKey) {
		if (MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY == mappingKey) {
			return JptJaxbUiImages.NULL_ATTRIBUTE_MAPPING;
		}
		if (MappingKeys.XML_ANY_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiImages.XML_ANY_ATTRIBUTE;
		}
		if (MappingKeys.XML_ANY_ELEMENT_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiImages.XML_ANY_ELEMENT;
		}
		if (MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiImages.XML_ATTRIBUTE;
		}
		if (MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiImages.XML_ELEMENT;
		}
		if (MappingKeys.XML_ELEMENT_REF_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiImages.XML_ELEMENT_REF;
		}
		if (MappingKeys.XML_ELEMENT_REFS_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiImages.XML_ELEMENT_REFS;
		}
		if (MappingKeys.XML_ELEMENTS_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiImages.XML_ELEMENTS;
		}
		if (MappingKeys.XML_TRANSIENT_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiImages.XML_TRANSIENT;
		}
		if (MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiImages.XML_VALUE;
		}
		
		//return the JAXB_CONTENT icon instead of null, might as well have an icon if one is not defined
		return JptJaxbUiImages.JAXB_CONTENT;
	}
}
