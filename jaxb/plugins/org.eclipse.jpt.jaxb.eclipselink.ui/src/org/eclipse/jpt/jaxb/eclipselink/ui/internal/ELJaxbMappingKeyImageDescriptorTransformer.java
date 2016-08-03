/*******************************************************************************
 * Copyright (c) 2011, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.ui.JptJaxbEclipseLinkUiImages;
import org.eclipse.jpt.jaxb.ui.internal.JaxbMappingKeyImageDescriptorTransformer;


public class ELJaxbMappingKeyImageDescriptorTransformer
	extends TransformerAdapter<String, ImageDescriptor>
{
	private static final Transformer<String, ImageDescriptor> INSTANCE = new ELJaxbMappingKeyImageDescriptorTransformer();

	public static Transformer<String, ImageDescriptor> instance() {
		return INSTANCE;
	}

	private ELJaxbMappingKeyImageDescriptorTransformer() {
		super();
	}

	@Override
	public ImageDescriptor transform(String mappingKey) {
		if (ELJaxbMappingKeys.XML_INVERSE_REFERENCE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbEclipseLinkUiImages.XML_INVERSE_REFERENCE;
		}
		if (ELJaxbMappingKeys.XML_JOIN_NODES_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbEclipseLinkUiImages.XML_JOIN_NODES;
		}
		if (ELJaxbMappingKeys.XML_TRANSFORMATION_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbEclipseLinkUiImages.XML_TRANSFORMATION;
		}
		
		return JaxbMappingKeyImageDescriptorTransformer.instance().transform(mappingKey);
	}
}
