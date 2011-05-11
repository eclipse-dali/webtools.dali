/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal;

import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.ui.JptJaxbEclipseLinkUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JaxbMappingImageHelper;
import org.eclipse.swt.graphics.Image;


public class ELJaxbMappingImageHelper {
	
	public static Image imageForAttributeMapping(String mappingKey) {
		String iconKey = iconKeyForAttributeMapping(mappingKey);
		return (iconKey == null) ? 
				JaxbMappingImageHelper.imageForAttributeMapping(mappingKey) 
				: JptJaxbEclipseLinkUiPlugin.getImage(iconKey);
	}
	
	public static String iconKeyForAttributeMapping(String mappingKey) {
		if (ELJaxbMappingKeys.XML_INVERSE_REFERENCE_ATTRIBUTE_MAPPING_KEY == mappingKey) {
			return JptJaxbEclipseLinkUiIcons.XML_INVERSE_REFERENCE;
		}
		else if (ELJaxbMappingKeys.XML_TRANSFORMATION_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbEclipseLinkUiIcons.XML_TRANSFORMATION;
		}
		
		return null;
	}
}
