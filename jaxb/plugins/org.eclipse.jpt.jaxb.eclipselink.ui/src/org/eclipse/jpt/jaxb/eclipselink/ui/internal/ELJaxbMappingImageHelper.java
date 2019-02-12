/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.ui.JptJaxbEclipseLinkUiImages;
import org.eclipse.jpt.jaxb.ui.internal.JaxbMappingImageHelper;


public class ELJaxbMappingImageHelper {
	
	public static ImageDescriptor imageDescriptorForAttributeMapping(String mappingKey) {
		if (ELJaxbMappingKeys.XML_INVERSE_REFERENCE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbEclipseLinkUiImages.XML_INVERSE_REFERENCE;
		}
		if (ELJaxbMappingKeys.XML_JOIN_NODES_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbEclipseLinkUiImages.XML_JOIN_NODES;
		}
		if (ELJaxbMappingKeys.XML_TRANSFORMATION_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbEclipseLinkUiImages.XML_TRANSFORMATION;
		}
		
		return JaxbMappingImageHelper.imageDescriptorForAttributeMapping(mappingKey);
	}
}
