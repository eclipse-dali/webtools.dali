/*******************************************************************************
 *  Copyright (c) 2011 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal;

import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.swt.graphics.Image;

public class JaxbMappingImageHelper
{
//
//	public static Image imageForTypeMapping(String mappingKey) {
//		return JptUiPlugin.getImage(iconKeyForTypeMapping(mappingKey));
//	}
//
//	public static String iconKeyForTypeMapping(String mappingKey) {
//		if (MappingKeys.NULL_TYPE_MAPPING_KEY == mappingKey) {
//			return JptUiIcons.NULL_TYPE_MAPPING;
//		}
//		else if (MappingKeys.ENTITY_TYPE_MAPPING_KEY.equals(mappingKey)) {
//			return JptUiIcons.ENTITY;
//		}
//		else if (MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY.equals(mappingKey)) {
//			return JptUiIcons.EMBEDDABLE;
//		}
//		else if (MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY.equals(mappingKey)) {
//			return JptUiIcons.MAPPED_SUPERCLASS;
//		}
//		return null;
//	}

	public static Image imageForAttributeMapping(String mappingKey) {
		return JptJaxbUiPlugin.getImage(iconKeyForAttributeMapping(mappingKey));
	}

	public static String iconKeyForAttributeMapping(String mappingKey) {
		if (MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY == mappingKey) {
			return JptJaxbUiIcons.NULL_ATTRIBUTE_MAPPING;
		}
		else if (MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiIcons.XML_ATTRIBUTE;
		}
		else if (MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiIcons.XML_ELEMENT;
		}
		else if (MappingKeys.XML_TRANSIENT_ATTRIBUTE_MAPPING_KEY.equals(mappingKey)) {
			return JptJaxbUiIcons.XML_TRANSIENT;
		}
		//return the JAXB_CONTENT icon instead of null, might as well have an icon if one is not defined
		return JptJaxbUiIcons.JAXB_CONTENT;
	}
}
